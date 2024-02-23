package com.uema.gestao_de_projetos.acadumeaapi.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.uema.gestao_de_projetos.acadumeaapi.model.entity.Matricula;

public interface MatriculaRepository extends JpaRepository<Matricula,Long> {

//		 @Query("SELECT a FROM Matricula a WHERE a.aluno.id = :id_aluno AND a.turma.id = :id_turma")
//		 Optional<Matricula> findTurmatoByALunoTurma(@Param("id_aluno") Long alunoId, @Param("id_turma") Long turmaId);		
//		 @Query("SELECT a FROM Matricula a WHERE a.turma.id = :id_turma")
//		    Optional<Matricula> findMatriculaPorTurma( @Param("id_turma") Long turmaId);

}
