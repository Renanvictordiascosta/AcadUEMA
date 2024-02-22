package com.uema.gestao_de_projetos.acadumeaapi.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uema.gestao_de_projetos.acadumeaapi.exception.ErroAutenticacao;
import com.uema.gestao_de_projetos.acadumeaapi.exception.RegraNegocioException;
import com.uema.gestao_de_projetos.acadumeaapi.model.entity.Funcionario;
import com.uema.gestao_de_projetos.acadumeaapi.model.enums.TipoStatusPessoa;
import com.uema.gestao_de_projetos.acadumeaapi.model.repository.FuncionarioRepository;
import com.uema.gestao_de_projetos.acadumeaapi.service.FuncionarioService;
import com.uema.gestao_de_projetos.acadumeaapi.validation.ValidationCPF;
import com.uema.gestao_de_projetos.acadumeaapi.validation.ValidationEmail;
import com.uema.gestao_de_projetos.acadumeaapi.validation.ValidationTelefone;

@Service
public class FuncionarioServiceImpl implements FuncionarioService {

	private  FuncionarioRepository repository;
	private  ValidationCPF validationcpf;
//	private ValidationData validationData;
	private  ValidationEmail validationEmail;
	private  ValidationTelefone validationTelefone;
	
	public FuncionarioServiceImpl(FuncionarioRepository repository,ValidationCPF validationcpf,ValidationEmail validationEmail,ValidationTelefone validationTelefone)
	{
		super();
		this.repository = repository;
		this.validationcpf = validationcpf;
		this.validationEmail = validationEmail;
		this.validationTelefone = validationTelefone;
	}
	
	@Override
	public Funcionario autenticar(String cpf, String senha) {
		Optional<Funcionario> funcionario = repository.findByCpf(cpf);
		if(!funcionario.isPresent())
		{
			
			throw new ErroAutenticacao("Funcionario não encontrado para o cpf informado.");
			
		}
		if(!funcionario.get().getSenha().equals(senha))
		{
			
			throw new ErroAutenticacao("Senha Invalida.");
		}
		if(funcionario.get().getStatus() == TipoStatusPessoa.CANCELADO)
		{
			throw new ErroAutenticacao("Usuario não possui mais acesso ao Sistema!");
			
		}
		return funcionario.get();
	}


	@Override
	public void validarCpf(String cpf) {
		
		boolean existe = repository.existsByCpf(cpf);
		if(existe)
		{
			 throw new RegraNegocioException("Já existe um usuário cadastrado com este CPF.");
		}
		
	}

	@Override
	public Optional<Funcionario> obterPorId(Long id) {
		
		return  repository.findById(id);
	}

	@Override
	public Optional<Funcionario> obterPorCpf(String cpf) {
	
		return repository.findByCpf(cpf);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Funcionario> buscarFuncionarios(Funcionario funcionariofiltro) {
		Example example = Example.of(funcionariofiltro, ExampleMatcher.
				matching().withIgnoreCase().withStringMatcher(StringMatcher.CONTAINING) );
		
		return repository.findAll(example);
	}

	@Override
	public void atualizarStatusPessoal(Funcionario funcionario, TipoStatusPessoa status) {
		funcionario.setStatus(status);
		atualizar(funcionario);
		
		
	}

	@Override
	public void validar(Funcionario funcionario) {
		
		 if(funcionario.getNome() == null || funcionario.getNome().trim().equals(""))
		 {
			 throw new RegraNegocioException("Informe um Nome válido.");
			 
		 }
		 if(funcionario.getEmail() == null || funcionario.getEmail().trim().equals("") || !validationEmail.validarEmail(funcionario.getEmail()))
		 {
			 throw new RegraNegocioException("Informe um Email válido.");
			 
		 }
		 
		 if(funcionario.getSenha() == null || funcionario.getSenha().trim().equals(""))
		 {
			 throw new RegraNegocioException("Informe uma Senha válida.");
			 
		 }
		 if(!validationcpf.validarCPF(funcionario.getCpf()))
		 {
			  throw new RegraNegocioException("Informe um CPF válido.");
		 }
		 
		 if(funcionario.getTelefone() == null || funcionario.getTelefone().trim().equals("") || !validationTelefone.validarNumeroCelular(funcionario.getTelefone()))
		{
			 throw new RegraNegocioException("Informe um Telefone válido.");
			 
		}
		 
		if(funcionario.getGenero() == null)
		{
			throw new RegraNegocioException("Informe um Genêro.");
		}
		if(funcionario.getTipo() == null)
		{
			throw new RegraNegocioException("Informe um Tipo de Aluno.");
		}
//		if(aluno.getDataNascimento() == null || !validationData.validarData(aluno.getDataNascimento()))
//		{
//			throw new RegraNegocioException("Informe um data correta.");
//		}
		
		
		
		
		
	}

	@Override
	@Transactional
	public Funcionario atualizar(Funcionario funcionario) {
		Objects.requireNonNull(funcionario.getId());
		validar(funcionario);
		
		return repository.save(funcionario);
	}

	@Override
	@Transactional
	public Funcionario salvarFuncionario(Funcionario funcionario) {
		validarCpf(funcionario.getCpf());
		validar(funcionario);
		funcionario.setStatus(TipoStatusPessoa.EFETIVADO);
		return repository.save(funcionario);
	}

	@Override
	@Transactional
	public void deletar(Funcionario funcionario) {
		Objects.requireNonNull(funcionario.getId());
		repository.delete(funcionario);
		
	}

}
