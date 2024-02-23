package com.uema.gestao_de_projetos.acadumeaapi.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.uema.gestao_de_projetos.acadumeaapi.model.entity.Turma;
import com.uema.gestao_de_projetos.acadumeaapi.model.enums.StatusTurma;
import com.uema.gestao_de_projetos.acadumeaapi.service.TurmaService;

@Service
public class TurmaServiceImpl implements TurmaService {

	@Override
	public Turma salvar(Turma turma) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Turma atualizar(Turma turma) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void validar(Turma turma) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deletar(Turma turma) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Optional<Turma> obterporId(Long id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public List<Turma> buscarTurma(Turma turmafiltro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void atualizarStatusTurma(Turma turma, StatusTurma status) {
		// TODO Auto-generated method stub
		
	}

	
}
