package com.lab.jpa.projetosanquim.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "professor")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Professor{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true, name = "usuario_id", nullable = false)
    private Usuario usuario;
}
