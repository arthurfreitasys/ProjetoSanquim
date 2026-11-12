package com.lab.jpa.projetosanquim.dto;

import java.math.BigDecimal;

public record NotaResponseDTO(Long id, BigDecimal valor, String tituloAvaliacao, String nomeAluno) {
}