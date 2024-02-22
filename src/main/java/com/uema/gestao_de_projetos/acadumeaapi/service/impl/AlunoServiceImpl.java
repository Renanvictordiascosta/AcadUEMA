package com.uema.gestao_de_projetos.acadumeaapi.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uema.gestao_de_projetos.acadumeaapi.exception.RegraNegocioException;
import com.uema.gestao_de_projetos.acadumeaapi.model.entity.Aluno;
import com.uema.gestao_de_projetos.acadumeaapi.model.enums.TipoStatusPessoa;
import com.uema.gestao_de_projetos.acadumeaapi.model.repository.AlunoRepository;
import com.uema.gestao_de_projetos.acadumeaapi.service.AlunoService;
import com.uema.gestao_de_projetos.acadumeaapi.validation.ValidationCPF;
//import com.uema.gestao_de_projetos.acadumeaapi.validation.ValidationData;
import com.uema.gestao_de_projetos.acadumeaapi.validation.ValidationEmail;
import com.uema.gestao_de_projetos.acadumeaapi.validation.ValidationTelefone;

@Service
public class AlunoServiceImpl implements AlunoService {

	
	private AlunoRepository repository;
	private  ValidationCPF validationcpf;
//	private ValidationData validationData;
	private  ValidationEmail validationEmail;
	private  ValidationTelefone validationTelefone;
	
	public AlunoServiceImpl(AlunoRepository repository,ValidationCPF validationcpf,ValidationEmail validationEmail,ValidationTelefone validationTelefone)
	{
		super();
		this.repository = repository;
		this.validationcpf = validationcpf;
		this.validationEmail = validationEmail;
		this.validationTelefone = validationTelefone;
	}
	
	
	
	@Override
	@Transactional
	public Aluno salvarAluno(Aluno aluno) {
		validarCpf(aluno.getCpf());
		validar(aluno);
		aluno.setStatus(TipoStatusPessoa.EFETIVADO);
		return repository.save(aluno);
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
	public Optional<Aluno> obterporId(Long id) {
		return repository.findById(id);
	}

	@Override
	public Optional<Aluno> obterporCpf(String cpf) {
		return repository.findByCpf(cpf);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Aluno> buscarAlunos(Aluno alunofiltro) {
		
		Example example = Example.of(alunofiltro , ExampleMatcher.
				matching().withIgnoreCase().withStringMatcher(StringMatcher.CONTAINING) );
		return repository.findAll(example);
	}

	@Override
	public void atualizarStatusPessoal(Aluno aluno, TipoStatusPessoa status) {
		
		aluno.setStatus(status);
		atualizar(aluno);
		
		
	}
	

	@Override
	@Transactional
	public Aluno atualizar(Aluno aluno) {
		Objects.requireNonNull(aluno.getId());
		validar(aluno);
		return repository.save(aluno);
	}



	@Override
	public void validar(Aluno aluno) {
	 if(aluno.getNome() == null || aluno.getNome().trim().equals(""))
	 {
		 throw new RegraNegocioException("Informe um Nome válido.");
		 
	 }
	 if(aluno.getEmail() == null || aluno.getEmail().trim().equals("") || !validationEmail.validarEmail(aluno.getEmail()))
	 {
		 throw new RegraNegocioException("Informe um Email válido.");
		 
	 }
	 
//	 if(aluno.getSenha() == null || aluno.getSenha().trim().equals(""))
//	 {
//		 throw new RegraNegocioException("Informe uma Senha válida.");
//		 
//	 }
	 if(!validationcpf.validarCPF(aluno.getCpf()))
	 {
		  throw new RegraNegocioException("Informe um CPF válido.");
	 }
	 
	 if(aluno.getTelefone() == null || aluno.getTelefone().trim().equals("") || !validationTelefone.validarNumeroCelular(aluno.getTelefone()))
	{
		 throw new RegraNegocioException("Informe um Telefone válido.");
		 
	}
	 
	if(aluno.getGenero() == null)
	{
		throw new RegraNegocioException("Informe um Genêro.");
	}
	if(aluno.getTipo() == null)
	{
		throw new RegraNegocioException("Informe um Tipo de Aluno.");
	}
//	if(aluno.getDataNascimento() == null || !validationData.validarData(aluno.getDataNascimento()))
//	{
//		throw new RegraNegocioException("Informe um data correta.");
//	}
	

	}



	@Override
	@Transactional
	public void deletar(Aluno aluno) {
		Objects.requireNonNull(aluno.getId());
		repository.delete(aluno);
		
	}

}
