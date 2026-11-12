package com.lab.jpa.projetosanquim.dto;

import jakarta.validation.constraints.NotNull;

public record TurmaDisciplinaRequestDTO(
        @NotNull(message = "Turma é obrigatorio")
        Long turma,
        @NotNull(message = "Disciplina é obrigatoria")
        Long disciplina,
        @NotNull(message = "Professor é obrigatorio")
        Long professor

) {
}
