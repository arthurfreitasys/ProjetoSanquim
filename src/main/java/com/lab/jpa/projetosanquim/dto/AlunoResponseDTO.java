package com.lab.jpa.projetosanquim.dto;

public record AlunoResponseDTO(
        Long id,
        String ra,
        UsuarioResponseDTO dadosUsuario
) {
}
