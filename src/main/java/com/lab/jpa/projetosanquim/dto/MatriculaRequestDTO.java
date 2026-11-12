package com.lab.jpa.projetosanquim.dto;

import jakarta.validation.constraints.NotNull;

public record MatriculaRequestDTO(@NotNull(message = "Status da matricula é obrigatorio") Boolean status,
                                  @NotNull(message = "O Aluno é obrigatorio") Long alunoID,
                                  @NotNull(message = "A turma é obrigatoria") Long turmaID) {
}