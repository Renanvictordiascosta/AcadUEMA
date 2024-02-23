package com.uema.gestao_de_projetos.acadumeaapi.model.repository;

import java.time.LocalTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.uema.gestao_de_projetos.acadumeaapi.model.entity.Turma;
import com.uema.gestao_de_projetos.acadumeaapi.model.enums.Dia_da_Semana;

public interface TurmaRepository extends JpaRepository<Turma,Long> {

//	  boolean existeTurmaAvaliador(Long funcionarioId);
	  
	  @Query("SELECT a FROM Turma a WHERE a.funcionario.id = :funcionarioId AND a.horario = :horario AND a.dia_da_semana = :dia_da_semana")
	    Optional<Turma> findTurmatoByAvaliadorAnHorario(@Param("funcionarioId") Long funcionarioId, @Param("horario") LocalTime horario, @Param("dia_da_semana") Dia_da_Semana dia_da_semana);

}
