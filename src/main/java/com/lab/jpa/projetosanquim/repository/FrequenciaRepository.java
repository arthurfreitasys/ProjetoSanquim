package com.lab.jpa.projetosanquim.repository;

import com.lab.jpa.projetosanquim.domain.model.Frequencia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface FrequenciaRepository extends JpaRepository<Frequencia, Long> {
    List<Frequencia> findAllByAlunoId(Long alunoId);
    boolean existsByAulaIdIn(Collection<Long> aulaIds);
}
