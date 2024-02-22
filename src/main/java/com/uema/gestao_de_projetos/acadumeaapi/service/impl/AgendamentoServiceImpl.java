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
import com.uema.gestao_de_projetos.acadumeaapi.model.entity.Agendamento;
import com.uema.gestao_de_projetos.acadumeaapi.model.enums.TipoStatusAgendamento;
import com.uema.gestao_de_projetos.acadumeaapi.model.repository.AgendamentoRepository;
import com.uema.gestao_de_projetos.acadumeaapi.model.repository.AlunoRepository;
import com.uema.gestao_de_projetos.acadumeaapi.model.repository.FuncionarioRepository;
import com.uema.gestao_de_projetos.acadumeaapi.service.AgendamentoService;


@Service
public class AgendamentoServiceImpl implements AgendamentoService{

	private AgendamentoRepository repository;

	public AgendamentoServiceImpl(AgendamentoRepository repository)
	{
		this.repository = repository;
		
	}
	
	
	@Override
	@Transactional
	public Agendamento salvar(Agendamento agendamento) {
		validar(agendamento);
		agendamento.setStatus(TipoStatusAgendamento.PENDENTE);
		return repository.save(agendamento);
	}

	@Override
	@Transactional
	public Agendamento atualizar(Agendamento agendamento) {
		Objects.requireNonNull(agendamento.getId());
		validar(agendamento);
		return repository.save(agendamento);
	}

	@Override
	@Transactional
	public void deletar(Agendamento agendamento) {
		Objects.requireNonNull(agendamento.getId());
		repository.delete(agendamento);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Agendamento> buscarAgendamento(Agendamento agendamentofiltro) {
		Example example = Example.of(agendamentofiltro,ExampleMatcher.
				matching().withIgnoreCase().withStringMatcher(StringMatcher.CONTAINING) );
		return repository.findAll(example);
		
	}

	@Override
	public void atualizarStatus(Agendamento agendamento, TipoStatusAgendamento status) {
		agendamento.setStatus(status);
		atualizar(agendamento);
		
	}

	@Override
	public void validar(Agendamento agendamento) {
		if(agendamento.getHorario() == null)
		{
			throw new RegraNegocioException("Informe um horario válido.");
		}
		if(agendamento.getDia() == null || agendamento.getDia() > 31 || agendamento.getDia() < 1 )
		{
			throw new RegraNegocioException("Informe um Dia válido.");
			
		}
		if(agendamento.getMes()== null || agendamento.getMes() < 1 ||agendamento.getMes() > 12 )
		{
			
			throw new RegraNegocioException("Informe um Mes válido.");
		}
		
		if(agendamento.getAno() == null || agendamento.getAno().toString().length() != 4 )
		{
			throw new RegraNegocioException("Informe um Ano válido.");
		}
		if(agendamento.getAlunos() == null || agendamento.getAlunos().getId() == null)
		{
			throw new RegraNegocioException("Informe um Aluno.");
		}
		if(agendamento.getFuncionario() == null || agendamento.getFuncionario().getId() == null)
		{
			throw new RegraNegocioException("Informe um Funcionario.");
		}
		
	}

	@Override
	public Optional<Agendamento> ObterPorid(Long id) {
		return repository.findById(id) ;
	}


	@Override
	@Transactional(readOnly = true)
	public boolean temAgendamentoSobrepostoAluno(Agendamento agendamento) {
		Optional<Agendamento> existe = repository.findAgendamentoByAlunoAndData(agendamento.getAlunos().getId(), agendamento.getHorario(), agendamento.getDia(), agendamento.getMes(), agendamento.getAno()); 
		if(existe.isPresent())
		{
			return true;
		}
		else
		{
		return false;
		}
	}


	@Override
	@Transactional(readOnly = true)
	public boolean temAgendamentoSobrepostoFuncionario(Agendamento agendamento) {
		Optional<Agendamento> existe = repository.findAgendamentoByFuncionarioAndData(agendamento.getFuncionario().getId(),agendamento.getHorario(), agendamento.getDia(), agendamento.getMes(), agendamento.getAno()); 
				if(existe.isPresent())
				{
					return true;
				}
				else
				{
				return false;
				}
	}

}
