package com.lab.jpa.projetosanquim.domain.model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "frequencia")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Frequencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "presente", nullable = false)
    private Boolean presente;

    @JoinColumn(name = "aluno_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Aluno aluno;

    @JoinColumn(name = "aula_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Aula aula;
}
