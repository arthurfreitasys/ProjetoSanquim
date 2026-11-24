package com.lab.jpa.projetosanquim.config;

import com.lab.jpa.projetosanquim.domain.enums.TipoAvaliacao;
import com.lab.jpa.projetosanquim.domain.enums.TipoUsuario;
import com.lab.jpa.projetosanquim.domain.model.*;
import com.lab.jpa.projetosanquim.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataLoader implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final GestorRepository gestorRepository;
    private final ProfessorRepository professorRepository;
    private final AlunoRepository alunoRepository;
    private final TurmaRepository turmaRepository;
    private final DisciplinaRepository disciplinaRepository;
    private final TurmaDisciplinaRepository turmaDisciplinaRepository;
    private final MatriculaRepository matriculaRepository;
    private final AulaRepository aulaRepository;
    private final FrequenciaRepository frequenciaRepository;
    private final AvaliacaoRepository avaliacaoRepository;
    private final NotaRepository notaRepository;
    private final PasswordEncoder passwordEncoder;

    private static final String TEST_PASSWORD = "123456";

    @Override
    @Transactional
    public void run(String... args) {
        if (usuarioRepository.count() > 0) {
            log.info("Banco de dados já contém dados. DataLoader ignorado.");
            return;
        }

        log.info("Iniciando carga de dados iniciais...");

        String encodedPassword = passwordEncoder.encode(TEST_PASSWORD);

        // 1. Criar Gestor
        Usuario userGestor = criarUsuario("Admin Gestor", "admin@sanquim.br", encodedPassword, TipoUsuario.GESTOR);
        gestorRepository.save(Gestor.builder().usuario(userGestor).build());

        // 2. Criar Disciplinas
        Disciplina matematica = disciplinaRepository.save(Disciplina.builder().nome("Matemática").build());
        Disciplina historia = disciplinaRepository.save(Disciplina.builder().nome("História").build());

        // 3. Criar Turmas
        Turma turmaA = turmaRepository.save(Turma.builder().identificacao("A").serie("1º Ano").anoLetivo(2026).status(true).build());
        Turma turmaB = turmaRepository.save(Turma.builder().identificacao("B").serie("1º Ano").anoLetivo(2026).status(true).build());

        // 4. Criar Professores
        Usuario userProf1 = criarUsuario("Professor de Matemática", "prof.mat@sanquim.br", encodedPassword, TipoUsuario.PROFESSOR);
        Professor prof1 = professorRepository.save(Professor.builder().usuario(userProf1).build());

        Usuario userProf2 = criarUsuario("Professor de História", "prof.his@sanquim.br", encodedPassword, TipoUsuario.PROFESSOR);
        Professor prof2 = professorRepository.save(Professor.builder().usuario(userProf2).build());

        // 5. Vincular Professores às Turmas e Disciplinas
        TurmaDisciplina td1 = turmaDisciplinaRepository.save(TurmaDisciplina.builder()
                .turma(turmaA)
                .disciplina(matematica)
                .professor(prof1)
                .build());

        TurmaDisciplina td2 = turmaDisciplinaRepository.save(TurmaDisciplina.builder()
                .turma(turmaB)
                .disciplina(historia)
                .professor(prof2)
                .build());

        // 6. Criar Alunos e Matrículas
        for (int i = 1; i <= 5; i++) {
            String nome = "Aluno " + i;
            String email = "aluno" + i + "@sanquim.br";
            Usuario userAluno = criarUsuario(nome, email, encodedPassword, TipoUsuario.ALUNO);
            Aluno aluno = alunoRepository.save(Aluno.builder()
                    .usuario(userAluno)
                    .ra("RA-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase())
                    .build());

            // Matricular alunos nas turmas (alternando)
            Turma turmaDestino = (i % 2 == 0) ? turmaB : turmaA;
            matriculaRepository.save(Matricula.builder()
                    .aluno(aluno)
                    .turma(turmaDestino)
                    .status(true)
                    .build());
            
            // 7. Criar 1 Chamada e 1 Avaliação para o primeiro aluno (que está na Turma A)
            if (i == 1) {
                // Chamada
                Aula aula = aulaRepository.save(Aula.builder()
                        .turmaDisciplina(td1)
                        .data(LocalDate.now())
                        .descricao("Aula Inaugural de Matemática")
                        .topicos("Introdução ao Cálculo")
                        .build());
                
                frequenciaRepository.save(Frequencia.builder()
                        .aluno(aluno)
                        .aula(aula)
                        .presente(true)
                        .build());

                // Avaliação e Nota
                Avaliacao aval = avaliacaoRepository.save(Avaliacao.builder()
                        .turmaDisciplina(td1)
                        .titulo("Prova 1")
                        .tipo(TipoAvaliacao.PROVA)
                        .notaMaxima(BigDecimal.valueOf(10))
                        .peso(BigDecimal.valueOf(1))
                        .dataEntrega(LocalDate.now())
                        .build());

                notaRepository.save(Nota.builder()
                        .aluno(aluno)
                        .avaliacao(aval)
                        .valor(BigDecimal.valueOf(8.5))
                        .build());
            }
        }

        log.info("Carga de dados finalizada com sucesso!");
        log.info("=== CREDENCIAIS DE TESTE ===");
        log.info("Senha única: {}", TEST_PASSWORD);
        log.info("GESTOR: admin@sanquim.br");
        log.info("PROFESSORES: prof.mat@sanquim.br, prof.his@sanquim.br");
        log.info("ALUNOS: aluno1@sanquim.br, aluno2@sanquim.br, aluno3@sanquim.br, aluno4@sanquim.br, aluno5@sanquim.br");
        log.info("============================");
    }

    private Usuario criarUsuario(String nome, String email, String encodedPassword, TipoUsuario tipo) {
        return usuarioRepository.save(Usuario.builder()
                .nome(nome)
                .email(email)
                .senha(encodedPassword)
                .tipoUsuario(tipo)
                .build());
    }
}
