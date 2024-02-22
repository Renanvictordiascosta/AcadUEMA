package com.uema.gestao_de_projetos.acadumeaapi.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uema.gestao_de_projetos.acadumeaapi.model.entity.Funcionario;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long>{
	
	boolean existsByCpf(String cpf);
	
	Optional<Funcionario> findByCpf(String cpf);
}
