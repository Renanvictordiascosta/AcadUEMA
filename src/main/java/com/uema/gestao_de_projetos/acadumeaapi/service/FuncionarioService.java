package com.uema.gestao_de_projetos.acadumeaapi.service;

import java.util.List;
import java.util.Optional;

import com.uema.gestao_de_projetos.acadumeaapi.model.entity.Funcionario;
import com.uema.gestao_de_projetos.acadumeaapi.model.enums.TipoStatusPessoa;

public interface FuncionarioService {

	Funcionario autenticar(String cpf, String senha); 
	Funcionario salvarFuncionario(Funcionario funcionario);
	void validarCpf(String cpf);
	Optional<Funcionario>obterPorId(Long id);
	Optional<Funcionario>obterPorCpf(String cpf);
	List<Funcionario>buscarFuncionarios(Funcionario funcionariofiltro);
	void atualizarStatusPessoal(Funcionario funcionario, TipoStatusPessoa status);
	void validar(Funcionario funcionario);
	Funcionario atualizar(Funcionario funcionario);
	void deletar(Funcionario funcionario);
}
