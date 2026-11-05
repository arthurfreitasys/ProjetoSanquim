package com.lab.jpa.projetosanquim.domain.model;

import com.lab.jpa.projetosanquim.domain.enums.Cargo;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "usuario")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, name = "nome_usuario")
    private String nome;

    @Column(nullable = false, unique = true, name = "email")
    private String email;

    @Column(nullable = false, name = "senha")
    private String senha;

    @Enumerated(EnumType.STRING)
    private Cargo cargo;


}
