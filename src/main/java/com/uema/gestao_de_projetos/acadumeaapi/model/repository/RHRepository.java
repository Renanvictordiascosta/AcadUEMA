package com.uema.gestao_de_projetos.acadumeaapi.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uema.gestao_de_projetos.acadumeaapi.model.entity.RH;

public interface RHRepository extends JpaRepository<RH,Long> {
	boolean existsByCpf(String cpf);

	Optional<RH> findByCpf(String cpf);
	
}
