package com.lab.jpa.projetosanquim.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "turma")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Turma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "identificacao", nullable = false)
    private String identificacao; // A, B, C

    @Column(name = "serie", nullable = false)
    private String serie; // 1º, 2º, 3º

    @Column(name = "ano_letivo", nullable = false)
    private Integer anoLetivo;

    @Column(name = "status", nullable = false)
    private Boolean status;
}
