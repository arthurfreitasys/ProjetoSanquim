package com.lab.jpa.projetosanquim.dto;

import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

public record DisciplinaRequestDTO(
        @NotBlank(message = "Nome é obrigatorio") String nome) {
}