package com.lab.jpa.projetosanquim.repository;

import com.lab.jpa.projetosanquim.domain.model.Nota;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotaRepository extends JpaRepository<Nota, Long> {
}
