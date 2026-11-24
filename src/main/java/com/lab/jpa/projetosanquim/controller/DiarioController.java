package com.lab.jpa.projetosanquim.controller;

import com.lab.jpa.projetosanquim.dto.*;
import com.lab.jpa.projetosanquim.service.DiarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/diario")
@RequiredArgsConstructor
@Tag(name = "Diário de Classe", description = "Endpoints para gestão de turmas, chamadas, avaliações e planos de aula")
public class DiarioController {

    private final DiarioService diarioService;

    @Operation(summary = "Lista as turmas do professor logado", description = "Retorna o vínculo entre turmas e disciplinas do professor autenticado")
    @GetMapping("/minhas-turmas")
    public ResponseEntity<List<TurmaDisciplinaResponseDTO>> listarMinhasTurmas() {
        return ResponseEntity.ok(diarioService.minhasTurmas());
    }

    @Operation(summary = "Registra a chamada da aula", description = "Registra a presença ou falta de uma lista de alunos em uma aula específica")
    @PostMapping("/chamada")
    public ResponseEntity<List<FrequenciaResponseDTO>> registrarChamada(@Valid @RequestBody ChamadaRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(diarioService.registrarChamada(request));
    }

    @Operation(summary = "Cria uma nova avaliação", description = "Cadastra uma avaliação para uma disciplina e turma")
    @PostMapping("/avaliacoes")
    public ResponseEntity<AvaliacaoResponseDTO> criarAvaliacao(@Valid @RequestBody AvaliacaoRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(diarioService.criarAvaliacao(request));
    }

    @Operation(summary = "Lança notas de uma avaliação", description = "Registra a nota de um aluno para uma avaliação específica")
    @PostMapping("/avaliacoes/{avaliacaoId}/notas")
    public ResponseEntity<NotaResponseDTO> lancarNotas(@PathVariable Long avaliacaoId, @Valid @RequestBody NotaRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(diarioService.lancarNotas(avaliacaoId, request));
    }

    @Operation(summary = "Registra um plano de aula", description = "Cadastra o conteúdo e tópicos abordados em uma aula")
    @PostMapping("/planos-aula")
    public ResponseEntity<AulaResponseDTO> registrarPlanoAula(@Valid @RequestBody AulaRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(diarioService.registrarPlanoAula(request));
    }
}
