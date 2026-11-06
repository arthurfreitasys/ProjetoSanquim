package com.lab.jpa.projetosanquim.domain.model;

import com.lab.jpa.projetosanquim.domain.enums.TipoAvaliacao;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "avaliacao")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Avaliacao {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoAvaliacao tipo;

    @Column(name = "nota_maxima", nullable = false, precision = 4, scale = 2)
    private BigDecimal notaMaxima;

    @Column(name = "peso", nullable = false, precision = 4, scale = 2)
    private BigDecimal peso;

    @Column(name = "data_entrega", nullable = true)
    private LocalDate dataEntrega;

    @JoinColumn(name = "turma_disciplina_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private TurmaDisciplina turmaDisciplina;

}
