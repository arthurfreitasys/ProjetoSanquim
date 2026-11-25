package com.lab.jpa.projetosanquim.repository;

import com.lab.jpa.projetosanquim.domain.model.Matricula;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatriculaRepository extends JpaRepository<Matricula, Long> {
    boolean existsByAlunoIdAndTurmaId(Long alunoId, Long turmaId);
    List<Matricula> findAllByAlunoIdAndStatusTrue(Long alunoId);
    List<Matricula> findAllByTurmaIdAndStatusTrue(Long turmaId);
    boolean existsByStatusTrueAndAlunoIdAndTurmaId(Long alunoId, Long turmaId);
}
