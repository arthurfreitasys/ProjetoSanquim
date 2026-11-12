package com.lab.jpa.projetosanquim.dto;

public record MatriculaResponseDTO(Long id, Boolean status, String nomeAluno, TurmaResponseDTO turma) {
}