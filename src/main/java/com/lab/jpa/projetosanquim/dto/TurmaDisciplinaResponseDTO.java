package com.lab.jpa.projetosanquim.dto;

public record TurmaDisciplinaResponseDTO(
        Long id,
        TurmaResponseDTO turma,
        String nomeDisciplina,
        String nomeProfessor
) {
}
