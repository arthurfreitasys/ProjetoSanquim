package com.lab.jpa.projetosanquim.dto;

import jakarta.validation.constraints.NotNull;

public record FrequenciaRequestDTO(@NotNull(message = "Status de presença é obrigatorio") Boolean presente,
                                   @NotNull(message = "Aluno é obrigatorio") Long aluno,
                                   @NotNull(message = "Aula é obrigatoria") Long aula) {
}