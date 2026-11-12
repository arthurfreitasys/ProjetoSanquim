package com.lab.jpa.projetosanquim.dto;

public record TurmaResponseDTO(Long id, String identificacao, String serie, Integer anoLetivo, Boolean status) {
}