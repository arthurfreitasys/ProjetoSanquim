package com.lab.jpa.projetosanquim.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "aula", uniqueConstraints = {
        @UniqueConstraint(
                columnNames = {"turma_disciplina_id", "data_aula"}
        )
})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Aula {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "data_aula", nullable = false)
    private LocalDate data;

    @Column(name = "descricao", nullable = true)
    private String descricao;

    @Column(name = "topico_aula", nullable = true)
    private String topicos;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "turma_disciplina_id", nullable = false)
    private TurmaDisciplina turmaDisciplina; // representa uma disciplina sendo ministrada por um determinado professor para uma determinada turma
}
