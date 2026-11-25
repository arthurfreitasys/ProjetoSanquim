package com.lab.jpa.projetosanquim.service;

import com.lab.jpa.projetosanquim.domain.model.Aluno;
import com.lab.jpa.projetosanquim.domain.model.Aula;
import com.lab.jpa.projetosanquim.domain.model.Avaliacao;
import com.lab.jpa.projetosanquim.domain.model.Frequencia;
import com.lab.jpa.projetosanquim.domain.model.Matricula;
import com.lab.jpa.projetosanquim.domain.model.Nota;
import com.lab.jpa.projetosanquim.domain.model.Professor;
import com.lab.jpa.projetosanquim.domain.model.Turma;
import com.lab.jpa.projetosanquim.domain.model.TurmaDisciplina;
import com.lab.jpa.projetosanquim.domain.model.Usuario;
import com.lab.jpa.projetosanquim.dto.AulaRequestDTO;
import com.lab.jpa.projetosanquim.dto.AulaResponseDTO;
import com.lab.jpa.projetosanquim.dto.AvaliacaoRequestDTO;
import com.lab.jpa.projetosanquim.dto.AvaliacaoResponseDTO;
import com.lab.jpa.projetosanquim.dto.ChamadaRequestDTO;
import com.lab.jpa.projetosanquim.dto.FrequenciaRequestDTO;
import com.lab.jpa.projetosanquim.dto.FrequenciaResponseDTO;
import com.lab.jpa.projetosanquim.dto.NotaRequestDTO;
import com.lab.jpa.projetosanquim.dto.NotaResponseDTO;
import com.lab.jpa.projetosanquim.dto.TurmaDisciplinaResponseDTO;
import com.lab.jpa.projetosanquim.exception.RecursoNaoEncontradoException;
import com.lab.jpa.projetosanquim.exception.RegraNegocioException;
import com.lab.jpa.projetosanquim.mapper.AulaMapper;
import com.lab.jpa.projetosanquim.mapper.AvaliacaoMapper;
import com.lab.jpa.projetosanquim.mapper.FrequenciaMapper;
import com.lab.jpa.projetosanquim.mapper.NotaMapper;
import com.lab.jpa.projetosanquim.mapper.TurmaDisciplinaMapper;
import com.lab.jpa.projetosanquim.repository.AlunoRepository;
import com.lab.jpa.projetosanquim.repository.AulaRepository;
import com.lab.jpa.projetosanquim.repository.AvaliacaoRepository;
import com.lab.jpa.projetosanquim.repository.DisciplinaRepository;
import com.lab.jpa.projetosanquim.repository.FrequenciaRepository;
import com.lab.jpa.projetosanquim.repository.MatriculaRepository;
import com.lab.jpa.projetosanquim.repository.NotaRepository;
import com.lab.jpa.projetosanquim.repository.ProfessorRepository;
import com.lab.jpa.projetosanquim.repository.TurmaDisciplinaRepository;
import com.lab.jpa.projetosanquim.repository.TurmaRepository;
import com.lab.jpa.projetosanquim.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class DiarioService {

    private final UsuarioRepository usuarioRepository;
    private final ProfessorRepository professorRepository;
    private final TurmaRepository turmaRepository;
    private final DisciplinaRepository disciplinaRepository;
    private final TurmaDisciplinaRepository turmaDisciplinaRepository;
    private final AlunoRepository alunoRepository;
    private final MatriculaRepository matriculaRepository;
    private final AulaRepository aulaRepository;
    private final FrequenciaRepository frequenciaRepository;
    private final AvaliacaoRepository avaliacaoRepository;
    private final NotaRepository notaRepository;
    private final TurmaDisciplinaMapper turmaDisciplinaMapper;
    private final AulaMapper aulaMapper;
    private final FrequenciaMapper frequenciaMapper;
    private final AvaliacaoMapper avaliacaoMapper;
    private final NotaMapper notaMapper;

    @Transactional(readOnly = true)
    public List<TurmaDisciplinaResponseDTO> minhasTurmas() {
        if (isGestao()) {
            return turmaDisciplinaMapper.toResponseList(turmaDisciplinaRepository.findAll());
        }

        Long professorId = obterProfessorAutenticado().getId();
        return turmaDisciplinaMapper.toResponseList(turmaDisciplinaRepository.findAllByProfessorId(professorId));
    }

    @Transactional
    public List<FrequenciaResponseDTO> registrarChamada(ChamadaRequestDTO request) {
        if (request == null || request.turmaId() == null || request.disciplinaId() == null
                || request.dataAula() == null || request.registros() == null || request.registros().isEmpty()) {
            throw new RegraNegocioException("Os dados da chamada são obrigatórios");
        }

        Turma turma = turmaRepository.findById(request.turmaId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Turma não encontrada"));
        disciplinaRepository.findById(request.disciplinaId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Disciplina não encontrada"));

        List<Aula> aulasDaData = aulaRepository.findAllByDataAndTurmaDisciplinaTurmaIdAndTurmaDisciplinaDisciplinaId(
                request.dataAula(), turma.getId(), request.disciplinaId());
        TurmaDisciplina vinculo = obterVinculoDaChamada(request.turmaId(), request.disciplinaId());
        Set<Long> aulasDaDataIds = aulasDaData.stream().map(Aula::getId).collect(java.util.stream.Collectors.toSet());
        boolean chamadaDuplicada = frequenciaRepository.existsByAulaIdIn(aulasDaDataIds);
        if (chamadaDuplicada) {
            throw new RegraNegocioException("Já existe chamada para esta turma, disciplina e data");
        }

        Aula aula = aulasDaData.stream()
                .filter(item -> Objects.equals(item.getTurmaDisciplina().getId(), vinculo.getId()))
                .findFirst()
                .orElse(null);
        if (!aulasDaData.isEmpty() && aula == null) {
            throw new RegraNegocioException("Já existe uma aula com outro vínculo para esta turma, disciplina e data");
        }

        Set<Long> alunosMatriculados = matriculaRepository.findAllByTurmaIdAndStatusTrue(turma.getId()).stream()
                .map(matricula -> matricula.getAluno().getId())
                .collect(java.util.stream.Collectors.toSet());

        Set<Long> alunosDaChamada = new HashSet<>();
        Map<Long, Aluno> alunos = new HashMap<>();
        for (ChamadaRequestDTO.RegistroChamadaDTO registro : request.registros()) {
            if (registro == null || registro.alunoId() == null || registro.presente() == null) {
                throw new RegraNegocioException("Cada registro deve informar aluno e presença");
            }
            if (!alunosDaChamada.add(registro.alunoId())) {
                throw new RegraNegocioException("A chamada contém aluno duplicado");
            }
            Aluno aluno = alunoRepository.findById(registro.alunoId())
                    .orElseThrow(() -> new RecursoNaoEncontradoException("Aluno não encontrado"));
            if (!alunosMatriculados.contains(registro.alunoId())) {
                throw new RegraNegocioException("Todos os alunos da chamada devem estar matriculados na turma");
            }
            alunos.put(aluno.getId(), aluno);
        }

        if (aula == null) {
            AulaRequestDTO aulaRequest = new AulaRequestDTO(request.dataAula(), null, null, vinculo.getId());
            aula = aulaMapper.toEntity(aulaRequest);
            aula.setTurmaDisciplina(vinculo);
            aula = aulaRepository.save(aula);
        }

        List<Frequencia> frequencias = new ArrayList<>();
        for (ChamadaRequestDTO.RegistroChamadaDTO registro : request.registros()) {
            Frequencia frequencia = frequenciaMapper.toEntity(
                    new FrequenciaRequestDTO(registro.presente(), registro.alunoId(), aula.getId())
            );
            frequencia.setAluno(alunos.get(registro.alunoId()));
            frequencia.setAula(aula);
            frequencias.add(frequencia);
        }
        return frequenciaMapper.toResponseList(frequenciaRepository.saveAll(frequencias));
    }

    @Transactional
    public AvaliacaoResponseDTO criarAvaliacao(AvaliacaoRequestDTO request) {
        if (request == null || request.turmaDisciplinaID() == null) {
            throw new RegraNegocioException("O vínculo entre turma e disciplina é obrigatório");
        }
        if (request.notaMaxima() == null || request.notaMaxima().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RegraNegocioException("A nota máxima deve ser positiva");
        }
        if (request.peso() == null || request.peso().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RegraNegocioException("O peso da avaliação deve ser positivo");
        }

        TurmaDisciplina vinculo = obterVinculo(request.turmaDisciplinaID());
        validarVinculoProfessor(vinculo);
        Avaliacao avaliacao = avaliacaoMapper.toEntity(request);
        avaliacao.setTurmaDisciplina(vinculo);
        return avaliacaoMapper.toResponse(avaliacaoRepository.save(avaliacao));
    }

    @Transactional
    public NotaResponseDTO lancarNotas(Long avaliacaoId, NotaRequestDTO request) {
        if (request == null || request.alunoID() == null || request.avaliacaoID() == null || request.valor() == null) {
            throw new RegraNegocioException("Os dados da nota são obrigatórios");
        }
        if (!Objects.equals(avaliacaoId, request.avaliacaoID())) {
            throw new RegraNegocioException("A avaliação informada no corpo não corresponde à avaliação da rota");
        }

        Avaliacao avaliacao = avaliacaoRepository.findById(avaliacaoId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Avaliação não encontrada"));
        validarVinculoProfessor(avaliacao.getTurmaDisciplina());

        Aluno aluno = alunoRepository.findById(request.alunoID())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Aluno não encontrado"));
        boolean matriculado = matriculaRepository.existsByStatusTrueAndAlunoIdAndTurmaId(
                aluno.getId(), avaliacao.getTurmaDisciplina().getTurma().getId());
        if (!matriculado) {
            throw new RegraNegocioException("O aluno não está matriculado na turma da avaliação");
        }
        if (request.valor().compareTo(BigDecimal.ZERO) < 0
                || request.valor().compareTo(avaliacao.getNotaMaxima()) > 0) {
            throw new RegraNegocioException("A nota deve estar entre zero e a nota máxima da avaliação");
        }

        Nota nota = notaRepository.findByAvaliacaoIdAndAlunoId(avaliacao.getId(), aluno.getId())
                .orElseGet(() -> {
                    Nota novaNota = notaMapper.toEntity(request);
                    novaNota.setAvaliacao(avaliacao);
                    novaNota.setAluno(aluno);
                    return novaNota;
                });
        nota.setValor(request.valor());
        return notaMapper.toResponse(notaRepository.save(nota));
    }

    @Transactional
    public AulaResponseDTO registrarPlanoAula(AulaRequestDTO request) {
        if (request == null || request.turmaDisciplinaID() == null || request.data() == null) {
            throw new RegraNegocioException("Os dados do plano de aula são obrigatórios");
        }
        TurmaDisciplina vinculo = obterVinculo(request.turmaDisciplinaID());
        validarVinculoProfessor(vinculo);

        boolean planoDuplicado = aulaRepository.existsByTurmaDisciplinaIdAndData(vinculo.getId(), request.data());
        if (planoDuplicado) {
            throw new RegraNegocioException("Já existe aula ou chamada nesta data para a turma e disciplina");
        }

        Aula aula = aulaMapper.toEntity(request);
        aula.setTurmaDisciplina(vinculo);
        return aulaMapper.toResponse(aulaRepository.save(aula));
    }

    private TurmaDisciplina obterVinculoDaChamada(Long turmaId, Long disciplinaId) {
        List<TurmaDisciplina> vinculos;
        if (isGestao()) {
            vinculos = turmaDisciplinaRepository.findAllByTurmaIdAndDisciplinaId(turmaId, disciplinaId);
        } else {
            Long professorId = obterProfessorAutenticado().getId();
            vinculos = turmaDisciplinaRepository.findAllByTurmaIdAndDisciplinaIdAndProfessorId(turmaId, disciplinaId, professorId);
        }
        if (vinculos.isEmpty()) {
            throw new RegraNegocioException("O professor não está vinculado a esta turma e disciplina");
        }
        if (vinculos.size() > 1) {
            throw new RegraNegocioException("Há mais de um vínculo para a turma e disciplina informadas");
        }
        return vinculos.get(0);
    }

    private TurmaDisciplina obterVinculo(Long vinculoId) {
        return turmaDisciplinaRepository.findById(vinculoId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Vínculo entre turma e disciplina não encontrado"));
    }

    private void validarVinculoProfessor(TurmaDisciplina vinculo) {
        if (isGestao()) {
            return;
        }
        Long professorId = obterProfessorAutenticado().getId();
        if (!Objects.equals(vinculo.getProfessor().getId(), professorId)) {
            throw new RegraNegocioException("O professor não está vinculado a esta turma e disciplina");
        }
    }

    private Professor obterProfessorAutenticado() {
        String email = obterEmailAutenticado();
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário autenticado não encontrado"));
        return professorRepository.findByUsuarioId(usuario.getId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Professor não encontrado para o usuário autenticado"));
    }

    private String obterEmailAutenticado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication.getName() == null) {
            throw new RegraNegocioException("Usuário não autenticado");
        }
        return authentication.getName();
    }

    private boolean isGestao() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.getAuthorities().stream()
                .anyMatch(authority -> "ROLE_GESTAO".equals(authority.getAuthority()));
    }
}