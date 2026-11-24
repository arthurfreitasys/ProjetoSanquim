package com.lab.jpa.projetosanquim.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record ChamadaRequestDTO(
        @NotNull(message = "A turma é obrigatória") Long turmaId,
        @NotNull(message = "A disciplina é obrigatória") Long disciplinaId,
        @NotNull(message = "A data da aula é obrigatória") LocalDate dataAula,
        @NotEmpty(message = "Informe ao menos um registro de presença")
        List<@Valid RegistroChamadaDTO> registros
) {
    public record RegistroChamadaDTO(
            @NotNull(message = "O aluno é obrigatório") Long alunoId,
            @NotNull(message = "A presença é obrigatória") Boolean presente
    ) {
    }
}