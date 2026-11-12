package com.lab.jpa.projetosanquim.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record GestorRequestDTO(@NotNull(message = "Usuarios do gestor são obrigatorios") @Valid UsuarioRequestDTO usuario) {
}