package com.lab.jpa.projetosanquim.dto;

import com.lab.jpa.projetosanquim.domain.enums.TipoUsuario;

public record UsuarioResponseDTO(
      Long id,
      String nome,
      String email,
      TipoUsuario tipoUsuario
) {
}
