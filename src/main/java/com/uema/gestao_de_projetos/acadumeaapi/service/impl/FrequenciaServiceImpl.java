package com.uema.gestao_de_projetos.acadumeaapi.service.impl;

import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uema.gestao_de_projetos.acadumeaapi.exception.RegraNegocioException;
import com.uema.gestao_de_projetos.acadumeaapi.model.entity.Frequencia;
import com.uema.gestao_de_projetos.acadumeaapi.model.enums.TipoFrequencia;
import com.uema.gestao_de_projetos.acadumeaapi.model.repository.FrequenciaRepository;
import com.uema.gestao_de_projetos.acadumeaapi.service.FrequenciaService;

@Service
public class FrequenciaServiceImpl implements FrequenciaService{

	private FrequenciaRepository repository;
	public FrequenciaServiceImpl(FrequenciaRepository repository)
	{
		super();
		this.repository = repository;
	}
	@Override
	@Transactional
	public Frequencia salvar(Frequencia frequencia) {
		validar(frequencia);
		return repository.save(frequencia);
	}

	@Override
	@Transactional
	public Frequencia atualizar(Frequencia frequencia) {
		Objects.requireNonNull(frequencia.getId());
		validar(frequencia);
		return repository.save(frequencia);
	}

	@Override
	public void validar(Frequencia frequencia) {
		
		if(frequencia.getDataFrequncia() == null )
		{
			throw new RegraNegocioException("Informe um Data válido.");
		}
		if(frequencia.getMatricula() == null || frequencia.getMatricula().getId() == null)
		{
			throw new RegraNegocioException("Informe uma Matricula válida.");
		}
		if(frequencia.getFrequencia() == null)
		{
			throw new RegraNegocioException("Informe um Tipo de Frequencia válido.");
		}
	}

	@Override
	@Transactional
	public void deletar(Frequencia frequencia) {
		
		Objects.requireNonNull(frequencia.getId());
		repository.delete(frequencia);
	}

	@Override
	public Optional<Frequencia> obterporId(Long id) {
		
		return repository.findById(id);
	}

	@Override
	public void atualizarTipoFrequencia(Frequencia frequencia, TipoFrequencia status) {
		frequencia.setFrequencia(status);
		atualizar(frequencia);
		
	}

	@Override
	@Transactional(readOnly = true)
	public Long frequenciaPorTipo(TipoFrequencia status, Long idAluno, Long idMatricula) {
		// TODO Auto-generated method stub
		return null;
	}

}
