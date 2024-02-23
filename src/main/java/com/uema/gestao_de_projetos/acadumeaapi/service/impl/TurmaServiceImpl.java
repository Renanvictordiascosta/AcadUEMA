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
import com.uema.gestao_de_projetos.acadumeaapi.model.entity.Turma;
import com.uema.gestao_de_projetos.acadumeaapi.model.enums.StatusTurma;
import com.uema.gestao_de_projetos.acadumeaapi.model.enums.TipoFuncionario;
import com.uema.gestao_de_projetos.acadumeaapi.model.repository.TurmaRepository;
import com.uema.gestao_de_projetos.acadumeaapi.service.TurmaService;

@Service
public class TurmaServiceImpl implements TurmaService {

	private TurmaRepository repository;
	
	public TurmaServiceImpl (TurmaRepository repository)
	{
		super();
		this.repository = repository;
		
	}
	@Override
	@Transactional
	public Turma salvar(Turma turma) {
	    validar(turma);
	    turma.setStatus_turma(StatusTurma.EM_ANDAMENTO);
		return repository.save(turma);
	}

	@Override
	@Transactional
	public Turma atualizar(Turma turma) {
		Objects.requireNonNull(turma.getId());
		validar(turma);
		return repository.save(turma);
	}

	@Override
	public void validar(Turma turma) {
		if(turma.getHorario() == null)
		{
			throw new RegraNegocioException("Informe un Horario.");
		}
		if(turma.getDia_da_semana() == null){
			throw new RegraNegocioException("Informe um Dia da Semana");}
		if(turma.getFuncionario() == null || turma.getFuncionario().getId() == null || turma.getFuncionario().getTipo() != TipoFuncionario.AVALIADOR){
		throw new RegraNegocioException("Informe um Avaliador.");
		}
	}

	@Override
	@Transactional
	public void deletar(Turma turma) {
		Objects.requireNonNull(turma.getId());
		repository.delete(turma);
	}

	@Override
	public Optional<Turma> obterporId(Long id) {
		return repository.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Turma> buscarTurma(Turma turmafiltro) {
		Example example = Example.of(turmafiltro,ExampleMatcher.
				matching().withIgnoreCase().withStringMatcher(StringMatcher.CONTAINING) );
		return repository.findAll(example);
		
	}

	@Override
	public void atualizarStatusTurma(Turma turma, StatusTurma status) {
		turma.setStatus_turma(status);
		atualizar(turma);
	}
	@Override
	@Transactional(readOnly = true)
	public boolean temTurmaSobrepostaParaAvaliador(Turma turma) {
		Optional <Turma> existe = repository.findTurmatoByAvaliadorAnHorario(turma.getFuncionario().getId(), turma.getHorario(), turma.getDia_da_semana());
		if(existe.isPresent())
		{
			return true;
			
		}
		else {
			return false;
			
		}
			
			
			
			
	}

	
}
