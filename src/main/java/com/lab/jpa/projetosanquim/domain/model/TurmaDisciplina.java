package com.lab.jpa.projetosanquim.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "turma_disciplina")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class TurmaDisciplina { // representa uma disciplina sendo ministrada por um determinado professor para uma determinada turma

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "turma_id", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    private Turma turma;

    @JoinColumn(name = "disciplina_id", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    private Disciplina disciplina;

    @JoinColumn(name = "professor_id", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    private Professor professor;
}
