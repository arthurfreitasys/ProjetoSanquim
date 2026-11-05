package com.lab.jpa.projetosanquim.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

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
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "status", nullable = false)
    private Boolean status;

    @JoinColumn(name = "aluno_id", nullable = false)
    @ManyToOne
    private Aluno aluno;

    @JoinColumn(name = "turma_id", nullable = false)
    @ManyToOne
    private Turma turma;
}
