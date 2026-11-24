package com.lab.jpa.projetosanquim.dto;

public record LoginResponseDTO(String token, long expiracaoMillis, String perfil) {
}