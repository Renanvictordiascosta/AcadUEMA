package com.uema.gestao_de_projetos.acadumeaapi.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.uema.gestao_de_projetos.acadumeaapi.model.entity.Aluno;
public interface AlunoRepository extends JpaRepository<Aluno,Long>{

	boolean existsByCpf(String cpf);
	
	Optional<Aluno> findByCpf(String cpf);
}
