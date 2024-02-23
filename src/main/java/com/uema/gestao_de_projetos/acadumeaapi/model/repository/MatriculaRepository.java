package com.uema.gestao_de_projetos.acadumeaapi.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uema.gestao_de_projetos.acadumeaapi.model.entity.Matricula;

public interface MatriculaRepository extends JpaRepository<Matricula,Long> {

		boolean existeMatriculaParaAluno(Long funcionarioId);
}
