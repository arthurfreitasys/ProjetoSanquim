package com.lab.jpa.projetosanquim.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "matricula", uniqueConstraints = {
        @UniqueConstraint(
                columnNames = {"aluno_id", "turma_id"}
        )
    }
)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Matricula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "status", nullable = false)
    private Boolean status;

    @JoinColumn(name = "aluno_id", nullable = false)
    @ManyToOne
    private Aluno aluno;

    @JoinColumn(name = "turma_id", nullable = false)
    @ManyToOne
    private Turma turma;
}
