package com.uema.gestao_de_projetos.acadumeaapi.model.repository;

import java.time.LocalTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.uema.gestao_de_projetos.acadumeaapi.model.entity.Agendamento;
import com.uema.gestao_de_projetos.acadumeaapi.model.entity.Aluno;
import com.uema.gestao_de_projetos.acadumeaapi.model.entity.Funcionario;

public interface AgendamentoRepository extends JpaRepository<Agendamento,Long> {
      
	 @Query("SELECT a FROM Agendamento a WHERE a.alunos.id = :alunoId AND a.horario = :horario AND a.dia = :dia AND a.mes = :mes AND a.ano = :ano")
	    Optional<Agendamento> findAgendamentoByAlunoAndData(@Param("alunoId") Long alunoId, @Param("horario") LocalTime horario, @Param("dia") Integer dia, @Param("mes") Integer mes, @Param("ano") Integer ano);

	    @Query("SELECT a FROM Agendamento a WHERE a.funcionario.id = :funcionarioId AND a.horario = :horario AND a.dia = :dia AND a.mes = :mes AND a.ano = :ano")
	    Optional<Agendamento> findAgendamentoByFuncionarioAndData(@Param("funcionarioId") Long funcionarioId, @Param("horario") LocalTime horario, @Param("dia") Integer dia, @Param("mes") Integer mes, @Param("ano") Integer ano);

}
