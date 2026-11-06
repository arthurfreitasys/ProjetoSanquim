package com.lab.jpa.projetosanquim.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "nota", uniqueConstraints = {
        @UniqueConstraint(
                columnNames = {"avaliacao_id", "aluno_id"}
        )
})
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Nota {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "valor", nullable = false, precision = 4, scale = 2)
    private BigDecimal valor;

    @JoinColumn(name = "avaliacao_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Avaliacao avaliacao;

    @JoinColumn(name = "aluno_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Aluno aluno;
}
