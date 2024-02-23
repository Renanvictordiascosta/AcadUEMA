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
import com.uema.gestao_de_projetos.acadumeaapi.model.entity.Matricula;
import com.uema.gestao_de_projetos.acadumeaapi.model.repository.MatriculaRepository;
import com.uema.gestao_de_projetos.acadumeaapi.service.MatriculaService;

@Service
public class MatriculaServiceImpl implements MatriculaService {

	private MatriculaRepository repository;
	public MatriculaServiceImpl(MatriculaRepository repository)
	{
		super();
		this.repository = repository;
	
	}
	@Override
	@Transactional
	public Matricula salvar(Matricula matricula) {
		 validar(matricula);
		 
		return repository.save(matricula);
	}

	@Override
	public void validar(Matricula matricula) {
		if(matricula.getAluno() == null || matricula.getAluno().getId() == null)
		{
			throw new RegraNegocioException("Informe um Aluno válido.");
		}
		if(matricula.getTurma() == null || matricula.getTurma().getId() == null)
		{
			throw new RegraNegocioException("Informe uma Turma válida.");
		}
		
	}

	@Override
	@Transactional
	public void deletar(Matricula matricula) {
		Objects.requireNonNull(matricula.getId());
		repository.delete(matricula);
	}

	@Override
	public Optional<Matricula> obterporId(Long id) {
		
		return repository.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Matricula> buscarMatricula(Matricula Matriculafiltro) {
		Example example = Example.of(Matriculafiltro,ExampleMatcher.
				matching().withIgnoreCase().withStringMatcher(StringMatcher.CONTAINING) );
		return repository.findAll(example);
		
		
	}
	@Override
	public boolean temMatriculaparaAlunoNaTurma(Matricula matricula) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean TemTurmaMatriculadaJa(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

//	@Override
//	@Transactional(readOnly = true)
//	public boolean temMatriculaparaAlunoNaTurma(Matricula matricula) {
//	Optional<Matricula> existe = repository.findTurmatoByALunoTurma(matricula.getAluno().getId(), matricula.getTurma().getId());
//	if(existe.isPresent())
//	{
//		return true;
//	}
//	else
//	{
//	return false;
//	}
//	}
//	@Override
//	@Transactional(readOnly = true)
//	public boolean TemTurmaMatriculadaJa(Long id) {
//		Optional<Matricula> existe = repository.findMatriculaPorTurma(id);
//		if(existe.isPresent())
//		{
//			return true;
//		}
//		else
//		{
//		return false;
//		}
//		
//	}

}
