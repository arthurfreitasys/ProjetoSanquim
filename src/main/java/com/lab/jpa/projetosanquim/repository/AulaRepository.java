package com.lab.jpa.projetosanquim.repository;

import com.lab.jpa.projetosanquim.domain.model.Aula;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public interface AulaRepository extends JpaRepository<Aula, Long> {
    @Query("SELECT a FROM Aula a WHERE a.turmaDisciplina.turma.id IN :turmaIds AND (a.descricao IS NOT NULL OR a.topicos IS NOT NULL)")
    List<Aula> findAulasPlanificadasPorTurmas(@Param("turmaIds") Collection<Long> turmaIds);

    List<Aula> findAllByDataAndTurmaDisciplinaTurmaIdAndTurmaDisciplinaDisciplinaId(LocalDate data, Long turmaId, Long disciplinaId);

    boolean existsByTurmaDisciplinaIdAndData(Long turmaDisciplinaId, LocalDate data);
}
