package com.lab.jpa.projetosanquim.dto;

import java.math.BigDecimal;
import java.util.List;

public record FrequenciaAlunoResponseDTO(BigDecimal percentualGlobal, List<FrequenciaDisciplinaDTO> porDisciplina) {
}