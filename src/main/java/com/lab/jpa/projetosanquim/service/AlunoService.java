package com.lab.jpa.projetosanquim.service;

import com.lab.jpa.projetosanquim.domain.model.Aluno;
import com.lab.jpa.projetosanquim.domain.model.Aula;
import com.lab.jpa.projetosanquim.domain.model.Disciplina;
import com.lab.jpa.projetosanquim.domain.model.Frequencia;
import com.lab.jpa.projetosanquim.domain.model.Matricula;
import com.lab.jpa.projetosanquim.domain.model.Nota;
import com.lab.jpa.projetosanquim.domain.model.TurmaDisciplina;
import com.lab.jpa.projetosanquim.domain.model.Usuario;
import com.lab.jpa.projetosanquim.dto.AulaResponseDTO;
import com.lab.jpa.projetosanquim.dto.BoletimResponseDTO;
import com.lab.jpa.projetosanquim.dto.DisciplinaBoletimDTO;
import com.lab.jpa.projetosanquim.dto.FrequenciaAlunoResponseDTO;
import com.lab.jpa.projetosanquim.dto.FrequenciaDisciplinaDTO;
import com.lab.jpa.projetosanquim.dto.NotaBoletimDTO;
import com.lab.jpa.projetosanquim.exception.RecursoNaoEncontradoException;
import com.lab.jpa.projetosanquim.exception.RegraNegocioException;
import com.lab.jpa.projetosanquim.mapper.AulaMapper;
import com.lab.jpa.projetosanquim.repository.AlunoRepository;
import com.lab.jpa.projetosanquim.repository.AulaRepository;
import com.lab.jpa.projetosanquim.repository.FrequenciaRepository;
import com.lab.jpa.projetosanquim.repository.MatriculaRepository;
import com.lab.jpa.projetosanquim.repository.NotaRepository;
import com.lab.jpa.projetosanquim.repository.TurmaDisciplinaRepository;
import com.lab.jpa.projetosanquim.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AlunoService {

    private final UsuarioRepository usuarioRepository;
    private final AlunoRepository alunoRepository;
    private final MatriculaRepository matriculaRepository;
    private final TurmaDisciplinaRepository turmaDisciplinaRepository;
    private final NotaRepository notaRepository;
    private final FrequenciaRepository frequenciaRepository;
    private final AulaRepository aulaRepository;
    private final AulaMapper aulaMapper;

    @Transactional(readOnly = true)
    public BoletimResponseDTO boletim() {
        Aluno aluno = obterAlunoAutenticado();
        Map<Long, Disciplina> disciplinas = obterDisciplinasMatriculadas(aluno);
        Map<Long, List<Nota>> notasPorDisciplina = new HashMap<>();

        notaRepository.findAllByAlunoId(aluno.getId())
                .forEach(nota -> {
                    Disciplina disciplina = nota.getAvaliacao().getTurmaDisciplina().getDisciplina();
                    disciplinas.put(disciplina.getId(), disciplina);
                    notasPorDisciplina.computeIfAbsent(disciplina.getId(), ignored -> new ArrayList<>()).add(nota);
                });

        List<Disciplina> disciplinasOrdenadas = disciplinas.values().stream()
                .sorted(Comparator.comparing(Disciplina::getNome, Comparator.nullsLast(String::compareTo)))
                .toList();
        List<DisciplinaBoletimDTO> resultado = disciplinasOrdenadas.stream()
                .map(disciplina -> montarBoletimDisciplina(disciplina, notasPorDisciplina.getOrDefault(disciplina.getId(), List.of())))
                .toList();
        return new BoletimResponseDTO(resultado);
    }

    @Transactional(readOnly = true)
    public FrequenciaAlunoResponseDTO frequencia() {
        Aluno aluno = obterAlunoAutenticado();
        List<Frequencia> frequencias = frequenciaRepository.findAllByAlunoId(aluno.getId());

        long totalRegistros = frequencias.size();
        long totalPresencas = frequencias.stream()
                .filter(item -> Boolean.TRUE.equals(item.getPresente()))
                .count();
        BigDecimal percentualGlobal = totalRegistros == 0
                ? BigDecimal.ZERO.setScale(2)
                : BigDecimal.valueOf(totalPresencas)
                .multiply(BigDecimal.valueOf(100))
                .divide(BigDecimal.valueOf(totalRegistros), 2, RoundingMode.HALF_UP);

        Map<Long, Disciplina> disciplinas = obterDisciplinasMatriculadas(aluno);
        Map<Long, Long> faltasPorDisciplina = new HashMap<>();
        disciplinas.keySet().forEach(id -> faltasPorDisciplina.put(id, 0L));
        for (Frequencia frequencia : frequencias) {
            Disciplina disciplina = frequencia.getAula().getTurmaDisciplina().getDisciplina();
            disciplinas.put(disciplina.getId(), disciplina);
            faltasPorDisciplina.putIfAbsent(disciplina.getId(), 0L);
            if (Boolean.FALSE.equals(frequencia.getPresente())) {
                faltasPorDisciplina.merge(disciplina.getId(), 1L, Long::sum);
            }
        }

        List<FrequenciaDisciplinaDTO> porDisciplina = disciplinas.values().stream()
                .sorted(Comparator.comparing(Disciplina::getNome, Comparator.nullsLast(String::compareTo)))
                .map(disciplina -> new FrequenciaDisciplinaDTO(
                        disciplina.getNome(), faltasPorDisciplina.getOrDefault(disciplina.getId(), 0L)
                ))
                .toList();
        return new FrequenciaAlunoResponseDTO(percentualGlobal, porDisciplina);
    }

    @Transactional(readOnly = true)
    public List<AulaResponseDTO> aulas() {
        Aluno aluno = obterAlunoAutenticado();
        Set<Long> turmas = obterTurmasAtivas(aluno);
        List<Aula> planos = aulaRepository.findAulasPlanificadasPorTurmas(turmas).stream()
                .sorted(Comparator.comparing(Aula::getData))
                .toList();
        return aulaMapper.toResponseList(planos);
    }

    private DisciplinaBoletimDTO montarBoletimDisciplina(Disciplina disciplina, List<Nota> notas) {
        List<Nota> notasOrdenadas = notas.stream()
                .sorted(Comparator.comparing(nota -> nota.getAvaliacao().getTitulo(), Comparator.nullsLast(String::compareTo)))
                .toList();
        List<NotaBoletimDTO> notasDTO = notasOrdenadas.stream()
                .map(nota -> new NotaBoletimDTO(
                        nota.getAvaliacao().getTitulo(),
                        nota.getValor(),
                        nota.getAvaliacao().getNotaMaxima(),
                        nota.getAvaliacao().getPeso()
                ))
                .toList();

        BigDecimal somaPonderada = BigDecimal.ZERO;
        BigDecimal somaPesos = BigDecimal.ZERO;
        for (Nota nota : notasOrdenadas) {
            BigDecimal peso = nota.getAvaliacao().getPeso();
            somaPonderada = somaPonderada.add(nota.getValor().multiply(peso));
            somaPesos = somaPesos.add(peso);
        }
        BigDecimal mediaParcial = notasOrdenadas.isEmpty()
                ? null
                : somaPonderada.divide(somaPesos, 2, RoundingMode.HALF_UP);
        return new DisciplinaBoletimDTO(disciplina.getNome(), notasDTO, mediaParcial);
    }

    private Map<Long, Disciplina> obterDisciplinasMatriculadas(Aluno aluno) {
        Set<Long> turmas = obterTurmasAtivas(aluno);
        Map<Long, Disciplina> disciplinas = new HashMap<>();
        turmaDisciplinaRepository.findAllByTurmaIdIn(turmas)
                .forEach(vinculo -> disciplinas.put(vinculo.getDisciplina().getId(), vinculo.getDisciplina()));
        return disciplinas;
    }

    private Set<Long> obterTurmasAtivas(Aluno aluno) {
        return matriculaRepository.findAllByAlunoIdAndStatusTrue(aluno.getId()).stream()
                .map(Matricula::getTurma)
                .map(turma -> turma.getId())
                .collect(java.util.stream.Collectors.toCollection(HashSet::new));
    }

    private Aluno obterAlunoAutenticado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication.getName() == null) {
            throw new RegraNegocioException("Usuário não autenticado");
        }

        Usuario usuario = usuarioRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário autenticado não encontrado"));
        return alunoRepository.findByUsuarioId(usuario.getId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Aluno não encontrado para o usuário autenticado"));
    }
}