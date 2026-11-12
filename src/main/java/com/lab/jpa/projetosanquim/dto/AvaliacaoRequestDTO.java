package com.lab.jpa.projetosanquim.dto;

import com.lab.jpa.projetosanquim.domain.enums.TipoAvaliacao;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;


public record AvaliacaoRequestDTO(@NotBlank(message = "Titulo é obrigatorio") String titulo,
                                  @NotNull(message = "O tipo de avaliação é obrigatorio") TipoAvaliacao tipo,
                                  @Min(1) @Positive BigDecimal notaMaxima,
                                  @NotNull @Positive(message = "Peso deve ser um numero positivo") BigDecimal peso,
                                  @NotNull(message = "Data de entrega é obrigatoria") LocalDate dataEntrega,
                                  Long turmaDisciplinaID) {
}