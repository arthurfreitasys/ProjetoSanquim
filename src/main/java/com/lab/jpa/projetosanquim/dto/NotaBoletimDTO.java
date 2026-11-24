package com.lab.jpa.projetosanquim.dto;

import java.math.BigDecimal;

public record NotaBoletimDTO(String avaliacao, BigDecimal nota, BigDecimal notaMaxima, BigDecimal peso) {
}