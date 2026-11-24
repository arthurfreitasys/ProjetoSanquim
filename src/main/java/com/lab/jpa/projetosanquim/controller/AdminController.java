package com.lab.jpa.projetosanquim.controller;

import com.lab.jpa.projetosanquim.dto.*;
import com.lab.jpa.projetosanquim.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Tag(name = "Administração", description = "Endpoints para usuários com perfil GESTAO")
public class AdminController {

    private final AdminService adminService;

    @Operation(summary = "Cadastra um novo professor", description = "Cria um registro de professor/usuário associado com perfil ROLE_PROFESSOR")
    @PostMapping("/professores")
    public ResponseEntity<ProfessorResponseDTO> cadastrarProfessor(@Valid @RequestBody ProfessorRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(adminService.cadastrarProfessor(request));
    }

    @Operation(summary = "Lista todos os professores", description = "Retorna uma lista com todos os professores cadastrados")
    @GetMapping("/professores")
    public ResponseEntity<List<ProfessorResponseDTO>> listarProfessores() {
        return ResponseEntity.ok(adminService.listarProfessores());
    }

    @Operation(summary = "Cadastra um novo aluno", description = "Cria um registro de aluno, gera um RA único e cria um usuário associado com perfil ROLE_ALUNO")
    @PostMapping("/alunos")
    public ResponseEntity<AlunoResponseDTO> cadastrarAluno(@Valid @RequestBody AlunoRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(adminService.cadastrarAluno(request));
    }

    @Operation(summary = "Lista todos os alunos", description = "Retorna uma lista com todos os alunos cadastrados")
    @GetMapping("/alunos")
    public ResponseEntity<List<AlunoResponseDTO>> listarAlunos() {
        return ResponseEntity.ok(adminService.listarAlunos());
    }

    @Operation(summary = "Cria uma nova turma", description = "Cadastra uma nova turma no sistema")
    @PostMapping("/turmas")
    public ResponseEntity<TurmaResponseDTO> criarTurma(@Valid @RequestBody TurmaRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(adminService.criarTurma(request));
    }

    @Operation(summary = "Cria uma nova disciplina", description = "Cadastra uma nova disciplina no sistema")
    @PostMapping("/disciplinas")
    public ResponseEntity<DisciplinaResponseDTO> criarDisciplina(@Valid @RequestBody DisciplinaRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(adminService.criarDisciplina(request));
    }

    @Operation(summary = "Matricula um aluno em uma turma", description = "Cria o vínculo de matrícula entre um aluno e uma turma ativa")
    @PostMapping("/matriculas")
    public ResponseEntity<MatriculaResponseDTO> matricularAluno(@Valid @RequestBody MatriculaRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(adminService.matricularAluno(request));
    }
}
