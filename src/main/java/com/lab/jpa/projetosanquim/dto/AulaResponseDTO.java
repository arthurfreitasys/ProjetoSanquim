package com.lab.jpa.projetosanquim.dto;

import java.time.LocalDate;

public record AulaResponseDTO(
        Long id,
        LocalDate data,
        String descricao,
        String topicos,
        TurmaDisciplinaResponseDTO turmaDisciplina
) {
}
