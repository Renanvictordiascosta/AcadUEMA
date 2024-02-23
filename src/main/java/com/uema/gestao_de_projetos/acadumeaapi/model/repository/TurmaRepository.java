package com.uema.gestao_de_projetos.acadumeaapi.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import com.uema.gestao_de_projetos.acadumeaapi.model.entity.Turma;

public interface TurmaRepository extends JpaRepository<Turma,Long> {

	  boolean existeTurmaAvaliador(Long funcionarioId);
	  
}
