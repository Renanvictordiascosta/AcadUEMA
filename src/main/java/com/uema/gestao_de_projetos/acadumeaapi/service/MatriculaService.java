package com.uema.gestao_de_projetos.acadumeaapi.service;

import java.util.List;
import java.util.Optional;

import com.uema.gestao_de_projetos.acadumeaapi.model.entity.Matricula;



public interface MatriculaService {

	Matricula salvar(Matricula matricula);
	void validar(Matricula matricula);
	void deletar(Matricula  matricula);
	Optional <Matricula> obterporId(Long id);
	List<Matricula> buscarMatricula (Matricula Matriculafiltro) ;
	boolean temMatriculaparaAlunoNaTurma(Matricula matricula);
	boolean TemTurmaMatriculadaJa(Long id);
}
