package com.lab.jpa.projetosanquim.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;


public record AulaRequestDTO(@NotNull(message = "Data é obrigatoria") LocalDate data,
                             @NotNull(message = "Descrição da aula é obrigatoria") String descricao,
                             @NotNull(message = "Topicos são obrigatorios") String topicos,
                             Long turmaDisciplinaID) {
}