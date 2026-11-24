package com.lab.jpa.projetosanquim.service;

import com.lab.jpa.projetosanquim.domain.enums.TipoUsuario;
import com.lab.jpa.projetosanquim.domain.model.Aluno;
import com.lab.jpa.projetosanquim.domain.model.Disciplina;
import com.lab.jpa.projetosanquim.domain.model.Matricula;
import com.lab.jpa.projetosanquim.domain.model.Professor;
import com.lab.jpa.projetosanquim.domain.model.Turma;
import com.lab.jpa.projetosanquim.domain.model.Usuario;
import com.lab.jpa.projetosanquim.dto.AlunoRequestDTO;
import com.lab.jpa.projetosanquim.dto.AlunoResponseDTO;
import com.lab.jpa.projetosanquim.dto.DisciplinaRequestDTO;
import com.lab.jpa.projetosanquim.dto.DisciplinaResponseDTO;
import com.lab.jpa.projetosanquim.dto.MatriculaRequestDTO;
import com.lab.jpa.projetosanquim.dto.MatriculaResponseDTO;
import com.lab.jpa.projetosanquim.dto.ProfessorRequestDTO;
import com.lab.jpa.projetosanquim.dto.ProfessorResponseDTO;
import com.lab.jpa.projetosanquim.dto.TurmaRequestDTO;
import com.lab.jpa.projetosanquim.dto.TurmaResponseDTO;
import com.lab.jpa.projetosanquim.exception.RecursoNaoEncontradoException;
import com.lab.jpa.projetosanquim.exception.RegraNegocioException;
import com.lab.jpa.projetosanquim.mapper.AlunoMapper;
import com.lab.jpa.projetosanquim.mapper.DisciplinaMapper;
import com.lab.jpa.projetosanquim.mapper.MatriculaMapper;
import com.lab.jpa.projetosanquim.mapper.ProfessorMapper;
import com.lab.jpa.projetosanquim.mapper.TurmaMapper;
import com.lab.jpa.projetosanquim.repository.AlunoRepository;
import com.lab.jpa.projetosanquim.repository.DisciplinaRepository;
import com.lab.jpa.projetosanquim.repository.MatriculaRepository;
import com.lab.jpa.projetosanquim.repository.ProfessorRepository;
import com.lab.jpa.projetosanquim.repository.TurmaRepository;
import com.lab.jpa.projetosanquim.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UsuarioRepository usuarioRepository;
    private final ProfessorRepository professorRepository;
    private final AlunoRepository alunoRepository;
    private final TurmaRepository turmaRepository;
    private final DisciplinaRepository disciplinaRepository;
    private final MatriculaRepository matriculaRepository;
    private final ProfessorMapper professorMapper;
    private final AlunoMapper alunoMapper;
    private final TurmaMapper turmaMapper;
    private final DisciplinaMapper disciplinaMapper;
    private final MatriculaMapper matriculaMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public ProfessorResponseDTO cadastrarProfessor(ProfessorRequestDTO request) {
        Professor professor = professorMapper.toEntity(request);
        Usuario usuario = professor.getUsuario();
        prepararUsuario(usuario, TipoUsuario.PROFESSOR);
        professor.setUsuario(usuarioRepository.save(usuario));
        return professorMapper.toResponse(professorRepository.save(professor));
    }

    @Transactional(readOnly = true)
    public List<ProfessorResponseDTO> listarProfessores() {
        return professorMapper.toResponseList(professorRepository.findAll());
    }

    @Transactional
    public AlunoResponseDTO cadastrarAluno(AlunoRequestDTO request) {
        Aluno aluno = alunoMapper.toEntity(request);
        Usuario usuario = aluno.getUsuario();
        prepararUsuario(usuario, TipoUsuario.ALUNO);
        usuario = usuarioRepository.save(usuario);
        aluno.setUsuario(usuario);
        aluno.setRa(gerarRaUnico());
        return alunoMapper.toResponse(alunoRepository.save(aluno));
    }

    @Transactional(readOnly = true)
    public List<AlunoResponseDTO> listarAlunos() {
        return alunoMapper.toResponseList(alunoRepository.findAll());
    }

    @Transactional
    public TurmaResponseDTO criarTurma(TurmaRequestDTO request) {
        Turma turma = turmaMapper.toEntity(request);
        return turmaMapper.toResponse(turmaRepository.save(turma));
    }

    @Transactional
    public DisciplinaResponseDTO criarDisciplina(DisciplinaRequestDTO request) {
        Disciplina disciplina = disciplinaMapper.toEntity(request);
        return disciplinaMapper.toResponse(disciplinaRepository.save(disciplina));
    }

    @Transactional
    public MatriculaResponseDTO matricularAluno(MatriculaRequestDTO request) {
        Aluno aluno = alunoRepository.findById(request.alunoID())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Aluno não encontrado"));
        Turma turma = turmaRepository.findById(request.turmaID())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Turma não encontrada"));

        if (!Boolean.TRUE.equals(turma.getStatus())) {
            throw new RegraNegocioException("Não é possível matricular em uma turma inativa");
        }

        boolean matriculaExistente = matriculaRepository.findAll().stream()
                .anyMatch(matricula -> Objects.equals(matricula.getAluno().getId(), aluno.getId())
                        && Objects.equals(matricula.getTurma().getId(), turma.getId()));
        if (matriculaExistente) {
            throw new RegraNegocioException("O aluno já possui matrícula nesta turma");
        }

        Matricula matricula = matriculaMapper.toEntity(request);
        matricula.setAluno(aluno);
        matricula.setTurma(turma);
        return matriculaMapper.toResponse(matriculaRepository.save(matricula));
    }

    private void prepararUsuario(Usuario usuario, TipoUsuario tipoUsuario) {
        if (usuario == null) {
            throw new RegraNegocioException("Os dados do usuário são obrigatórios");
        }
        validarEmailUnico(usuario.getEmail());
        usuario.setTipoUsuario(tipoUsuario);
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
    }

    private void validarEmailUnico(String email) {
        boolean emailExistente = usuarioRepository.findAll().stream()
                .anyMatch(usuario -> usuario.getEmail().equalsIgnoreCase(email));
        if (emailExistente) {
            throw new RegraNegocioException("Já existe um usuário cadastrado com este e-mail");
        }
    }

    private String gerarRaUnico() {
        String ra;
        do {
            ra = "RA-" + UUID.randomUUID().toString().replace("-", "").toUpperCase();
            String raCandidato = ra;
            if (alunoRepository.findAll().stream().noneMatch(aluno -> raCandidato.equals(aluno.getRa()))) {
                break;
            }
        } while (true);
        return ra;
    }
}