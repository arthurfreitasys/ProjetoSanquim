package com.lab.jpa.projetosanquim.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record AlunoRequestDTO(
        @NotNull(message = "Dados do usuario são obrigatorios")
        @Valid
        UsuarioRequestDTO dadosUsuario

        //RA é gerado pelo Service

) {
}
