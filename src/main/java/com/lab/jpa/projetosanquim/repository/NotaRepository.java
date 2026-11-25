package com.lab.jpa.projetosanquim.repository;

import com.lab.jpa.projetosanquim.domain.model.Nota;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NotaRepository extends JpaRepository<Nota, Long> {
    List<Nota> findAllByAlunoId(Long alunoId);
    Optional<Nota> findByAvaliacaoIdAndAlunoId(Long avaliacaoId, Long alunoId);
}
