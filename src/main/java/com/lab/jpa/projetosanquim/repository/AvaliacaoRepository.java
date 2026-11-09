package com.lab.jpa.projetosanquim.repository;

import com.lab.jpa.projetosanquim.domain.model.Avaliacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {
}
