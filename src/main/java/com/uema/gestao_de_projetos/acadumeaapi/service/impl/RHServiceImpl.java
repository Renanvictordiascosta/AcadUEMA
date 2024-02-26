package com.uema.gestao_de_projetos.acadumeaapi.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.uema.gestao_de_projetos.acadumeaapi.exception.ErroAutenticacao;
import com.uema.gestao_de_projetos.acadumeaapi.model.entity.Funcionario;
import com.uema.gestao_de_projetos.acadumeaapi.model.entity.RH;
import com.uema.gestao_de_projetos.acadumeaapi.model.enums.TipoStatusPessoa;
import com.uema.gestao_de_projetos.acadumeaapi.model.repository.RHRepository;
import com.uema.gestao_de_projetos.acadumeaapi.service.RHService;

@Service
public class RHServiceImpl implements RHService{

	
	private RHRepository repository;
	public RHServiceImpl(RHRepository repository)
	{
		super();
		this.repository = repository;
	}
	
	@Override
	public RH autenticar(String cpf, String senha) {
		Optional<RH> admin = repository.findByCpf(cpf);
		if(!admin.isPresent())
		{
			
			throw new ErroAutenticacao("Admin não encontrado para o cpf informado.");
			
		}
		if(!admin.get().getSenha().equals(senha))
		{
			
			throw new ErroAutenticacao("Senha Invalida.");
		}
		
		return admin.get();
	}

}
