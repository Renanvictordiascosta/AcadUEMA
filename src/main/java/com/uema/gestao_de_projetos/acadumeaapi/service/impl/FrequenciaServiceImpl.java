package com.uema.gestao_de_projetos.acadumeaapi.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.uema.gestao_de_projetos.acadumeaapi.model.entity.Frequencia;
import com.uema.gestao_de_projetos.acadumeaapi.model.enums.TipoFrequencia;
import com.uema.gestao_de_projetos.acadumeaapi.service.FrequenciaService;

@Service
public class FrequenciaServiceImpl implements FrequenciaService{

	@Override
	public Frequencia salvar(Frequencia frequencia) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Frequencia atualizar(Frequencia frequencia) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void validar(Frequencia frequencia) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deletar(Frequencia frequencia) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Optional<Frequencia> obterporId(Long id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public void atualizarTipoFrequencia(Frequencia frequencia, TipoFrequencia status) {
		// TODO Auto-generated method stub
		
	}

}
