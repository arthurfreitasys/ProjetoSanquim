package com.lab.jpa.projetosanquim.controller;

import com.lab.jpa.projetosanquim.dto.AulaResponseDTO;
import com.lab.jpa.projetosanquim.dto.BoletimResponseDTO;
import com.lab.jpa.projetosanquim.dto.FrequenciaAlunoResponseDTO;
import com.lab.jpa.projetosanquim.service.AlunoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/aluno")
@RequiredArgsConstructor
@Tag(name = "Aluno", description = "Endpoints para consulta de dados pelo próprio aluno")
public class AlunoController {

    private final AlunoService alunoService;

    @Operation(summary = "Obtém o boletim do aluno", description = "Retorna as notas e médias parciais por disciplina do aluno")
    @GetMapping("/boletim")
    public ResponseEntity<BoletimResponseDTO> obterBoletim() {
        return ResponseEntity.ok(alunoService.boletim());
    }

    @Operation(summary = "Obtém a frequência do aluno", description = "Retorna o percentual total de presença e o total de faltas por disciplina do aluno")
    @GetMapping("/frequencia")
    public ResponseEntity<FrequenciaAlunoResponseDTO> obterFrequencia() {
        return ResponseEntity.ok(alunoService.frequencia());
    }

    @Operation(summary = "Lista os planos de aula das turmas do aluno", description = "Retorna os planos de aula e tópicos das disciplinas em que o aluno está matriculado")
    @GetMapping("/aulas")
    public ResponseEntity<List<AulaResponseDTO>> listarAulas() {
        return ResponseEntity.ok(alunoService.aulas());
    }
}
