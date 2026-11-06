package com.lab.jpa.projetosanquim.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "turma_disciplina")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class TurmaDisciplina { // representa uma disciplina sendo ministrada por um determinado professor para uma determinada turma

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @JoinColumn(name = "turma_id", nullable = false)
    @ManyToOne
    private Turma turma;

    @JoinColumn(name = "disciplina_id", nullable = false)
    @ManyToOne
    private Disciplina disciplina;

    @JoinColumn(name = "professor_id", nullable = false)
    @ManyToOne
    private Professor professor;
}
