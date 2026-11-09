package com.lab.jpa.projetosanquim.repository;

import com.lab.jpa.projetosanquim.domain.model.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {
}
