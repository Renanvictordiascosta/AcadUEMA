package com.uema.gestao_de_projetos.acadumeaapi.service;

import java.util.List;
import java.util.Optional;

import com.uema.gestao_de_projetos.acadumeaapi.model.entity.Turma;
import com.uema.gestao_de_projetos.acadumeaapi.model.enums.StatusTurma;


public interface TurmaService {
	Turma salvar(Turma turma);
	Turma  atualizar(Turma  turma);
	void validar(Turma turma);
	void deletar(Turma  turma);
	Optional <Turma > obterporId(Long id);
	List<Turma > buscarTurma (Turma  turmafiltro) ;
	void atualizarStatusTurma ( Turma  turma,StatusTurma status);
	
	
}
