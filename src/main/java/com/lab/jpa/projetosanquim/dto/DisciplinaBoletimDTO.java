package com.lab.jpa.projetosanquim.dto;

import java.math.BigDecimal;
import java.util.List;

public record DisciplinaBoletimDTO(String disciplina, List<NotaBoletimDTO> notas, BigDecimal mediaParcial) {
}