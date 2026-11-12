package com.lab.jpa.projetosanquim.dto;

import com.lab.jpa.projetosanquim.domain.enums.TipoAvaliacao;

import java.math.BigDecimal;
import java.time.LocalDate;

public record AvaliacaoResponseDTO(Long id, String titulo, TipoAvaliacao tipo, BigDecimal notaMaxima, BigDecimal peso,
                                   LocalDate dataEntrega, TurmaDisciplinaResponseDTO turmaDisciplina) {
}