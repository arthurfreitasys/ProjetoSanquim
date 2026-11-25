package com.lab.jpa.projetosanquim.repository;

import com.lab.jpa.projetosanquim.domain.model.TurmaDisciplina;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface TurmaDisciplinaRepository extends JpaRepository<TurmaDisciplina, Long> {
    List<TurmaDisciplina> findAllByTurmaIdIn(Collection<Long> turmaIds);
    List<TurmaDisciplina> findAllByProfessorId(Long professorId);
    List<TurmaDisciplina> findAllByTurmaIdAndDisciplinaId(Long turmaId, Long disciplinaId);
    List<TurmaDisciplina> findAllByTurmaIdAndDisciplinaIdAndProfessorId(Long turmaId, Long disciplinaId, Long professorId);
}
