package com.lab.jpa.projetosanquim.repository;

import com.lab.jpa.projetosanquim.domain.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {
}
