package com.uema.gestao_de_projetos.acadumeaapi.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uema.gestao_de_projetos.acadumeaapi.model.entity.Pagamento;

public interface PagamentoRepository extends JpaRepository<Pagamento,Long> {

}
