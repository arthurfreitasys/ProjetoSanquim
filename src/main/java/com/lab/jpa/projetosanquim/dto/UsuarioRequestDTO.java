package com.lab.jpa.projetosanquim.dto;

import com.lab.jpa.projetosanquim.domain.enums.TipoUsuario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UsuarioRequestDTO(
        @NotBlank(message = "O nome é obrigatorio")
        String nome,

        @NotBlank(message = "O email é obrigatorio")
        String email,

        @NotBlank(message = "Senha é obrigatoria")
        @Size(min = 8, max = 12, message = "Senha deve ter entre 8 e 12 caracteres")
        String senha,

        @NotNull(message = "Tipo do Usuario é obrigatorio")
        TipoUsuario tipoUsuario
) {
}
