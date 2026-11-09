package com.lab.jpa.projetosanquim.repository;

import com.lab.jpa.projetosanquim.domain.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

}
