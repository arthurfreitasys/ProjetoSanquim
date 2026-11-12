package com.lab.jpa.projetosanquim.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record NotaRequestDTO(@NotNull(message = "Valor da nota é obrigatoria") BigDecimal valor,
                             @NotNull(message = "Avaliação é obrigatoria") Long avaliacaoID,
                             @NotNull(message = "Aluno é obrigatorio") Long alunoID) {
}