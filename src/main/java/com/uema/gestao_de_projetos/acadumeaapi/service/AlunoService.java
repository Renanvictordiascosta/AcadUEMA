package com.uema.gestao_de_projetos.acadumeaapi.service;

import java.util.List;
import java.util.Optional;

import com.uema.gestao_de_projetos.acadumeaapi.model.entity.Aluno;
import com.uema.gestao_de_projetos.acadumeaapi.model.enums.TipoStatusPessoa;

public interface AlunoService {

	Aluno salvarAluno(Aluno aluno);
	void validarCpf(String cpf);
	Optional <Aluno> obterporId(Long id);
	Aluno atualizar(Aluno aluno);
	Optional <Aluno> obterporCpf(String cpf);
	List<Aluno> buscarAlunos(Aluno alunofiltro) ;
	void atualizarStatusPessoal( Aluno aluno,TipoStatusPessoa status);
	void validar(Aluno aluno);
	void deletar(Aluno aluno);
}
