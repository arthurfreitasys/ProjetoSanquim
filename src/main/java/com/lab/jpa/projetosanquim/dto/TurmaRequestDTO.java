package com.lab.jpa.projetosanquim.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TurmaRequestDTO(
        @NotBlank(message = "Campo obrigatorio (Ex: A, B ou C)") String identificacao,
        @NotBlank(message = "Campo obrigatorio (Ex: 1º, 2º)") String serie,
        @NotNull(message = "Campo obrigatorio (Ex: 2025, 2026)") Integer anoLetivo,
        @NotNull(message = "Campo obrigatorio (Ex: Ativo") Boolean status) {
  }