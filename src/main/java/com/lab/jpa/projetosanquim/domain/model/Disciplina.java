package com.lab.jpa.projetosanquim.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Table(name = "Disciplina")
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Disciplina {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "nome", nullable = false, unique = true)
    private String nome;
}
