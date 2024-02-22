package com.uema.gestao_de_projetos.acadumeaapi.api.resource;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.uema.gestao_de_projetos.acadumeaapi.api.dto.AgendamentoDTO;
import com.uema.gestao_de_projetos.acadumeaapi.api.dto.AtualizarTStatusAgendamentoDTO;
import com.uema.gestao_de_projetos.acadumeaapi.exception.RegraNegocioException;
import com.uema.gestao_de_projetos.acadumeaapi.model.entity.Agendamento;
import com.uema.gestao_de_projetos.acadumeaapi.model.entity.Aluno;
import com.uema.gestao_de_projetos.acadumeaapi.model.entity.Funcionario;
import com.uema.gestao_de_projetos.acadumeaapi.model.enums.TipoFuncionario;
import com.uema.gestao_de_projetos.acadumeaapi.model.enums.TipoStatusAgendamento;
import com.uema.gestao_de_projetos.acadumeaapi.model.enums.TipoStatusPessoa;
import com.uema.gestao_de_projetos.acadumeaapi.service.AgendamentoService;
import com.uema.gestao_de_projetos.acadumeaapi.service.AlunoService;
import com.uema.gestao_de_projetos.acadumeaapi.service.FuncionarioService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/agendamentos")
@RequiredArgsConstructor
public class AgendamentoResource {

	private final AlunoService alunoService;
	private final FuncionarioService funcionarioService;
	private final AgendamentoService agendamentoService;
	
	
	private Agendamento converter (AgendamentoDTO dto)
	{
		Agendamento agendamento = new Agendamento();
		agendamento.setHorario(dto.getHorario());
		agendamento.setDia(dto.getDia());
		agendamento.setMes(dto.getMes());
		agendamento.setAno(dto.getAno());
		if(dto.getStatus() != null)
		{
			agendamento.setStatus(TipoStatusAgendamento.valueOf(dto.getStatus()));
			
		}
		
		Aluno aluno = alunoService.obterporCpf(dto.getAluno()).orElseThrow( () -> new RegraNegocioException("Aluno não encontrado para o CPF informado!"));
		
		agendamento.setAlunos(aluno);
		
		Funcionario funcionario = funcionarioService.obterPorCpf(dto.getFuncionario()).orElseThrow( () -> new RegraNegocioException("Funcionario não encontrado para o CPF informado!"));
		
		agendamento.setFuncionario(funcionario);
		return agendamento;
	}
	
	@GetMapping
	public ResponseEntity buscarAgendamento(
			@RequestParam(value  = "aluno", required = false) String aluno,
			@RequestParam(value  ="funcionario", required = false) String funcionario,
			@RequestParam(value  ="mes", required = false) Integer mes,
			@RequestParam(value  ="ano", required = false) Integer ano,
			@RequestParam(value  ="dia", required = false) Integer dia,
			@RequestParam(value  ="horario", required = false) LocalTime horario
			)
	{
		Agendamento filtro = new Agendamento();
		filtro.setDia(dia);
		filtro.setMes(mes);
		filtro.setAno(ano);
		filtro.setHorario(horario);
		Optional<Aluno> alunofiltro = alunoService.obterporCpf(aluno);
		if(alunofiltro.isPresent())
		{
			
			filtro.setAlunos(alunofiltro.get());
		}
		
		Optional<Funcionario> funcionariofiltro = funcionarioService.obterPorCpf(funcionario);
		if(funcionariofiltro.isPresent())
		{
			filtro.setFuncionario(funcionariofiltro.get());
		}
		
		
		List<Agendamento> agendamentos = agendamentoService.buscarAgendamento(filtro);
		return ResponseEntity.ok(agendamentos);
	}
	
	
	@PostMapping
	public ResponseEntity salvar( @RequestBody AgendamentoDTO dto)
	{
		try {
			
			Agendamento entidade = converter(dto);
		   if(entidade.getAlunos().getStatus() == TipoStatusPessoa.CANCELADO)
		   {
			   return ResponseEntity.badRequest().body("Aluno está com Cadastro Cancelado , Não é possivel fazer agendamentos!");
		   }
		   if(entidade.getFuncionario().getTipo() == TipoFuncionario.OUTROS)
		   {
			   return ResponseEntity.badRequest().body("Funcionario não pode receber Consultas e Agendamentos.");
		   }
		   if(entidade.getFuncionario().getStatus() == TipoStatusPessoa.CANCELADO)
		   {
			   return ResponseEntity.badRequest().body("Funcionario está com Cadastro Cancelado. Não é possivel fazer agendamentos para Ele!");
		   }
		   
			LocalDate data = LocalDate.now();
			entidade.setDataCadastro(data);
			boolean paraleloAluno = agendamentoService.temAgendamentoSobrepostoAluno(entidade);
			boolean paraleloFuncionario = agendamentoService.temAgendamentoSobrepostoFuncionario(entidade);
			if(!paraleloAluno && !paraleloFuncionario)
			{agendamentoService.salvar(entidade);
			return new ResponseEntity(entidade,HttpStatus.CREATED);}
			else
			{
				return ResponseEntity.badRequest().body("Já possui um agendamento para a mesma data e horario para o Aluno ou Funcionario. Refaça o Cadastro!");
			}
		}
		catch(RegraNegocioException e)
		{
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity deletar( @PathVariable("id") Long id)
	{
		return agendamentoService.ObterPorid(id).map( entity -> {
			agendamentoService.deletar(entity);
return new ResponseEntity(HttpStatus.NO_CONTENT);
			
		}).orElseGet( () -> new ResponseEntity("Lancamento não encontrado na Base de Dados", HttpStatus.BAD_REQUEST));
	
	}
	
	@PutMapping("{id}/atualizar-status")
	public ResponseEntity atualizarStatus(@PathVariable("id") Long id, @RequestBody AtualizarTStatusAgendamentoDTO dto)
	{
		return agendamentoService.ObterPorid(id).map( entity -> {
		
		TipoStatusAgendamento statusSelecionado = TipoStatusAgendamento.valueOf(dto.getStatus());
		if(statusSelecionado == null)
		{
			return ResponseEntity.badRequest().body("Não foi possivel atualizar o status do Agendamento, envie um status válido");
		}
		try {
		entity.setStatus(statusSelecionado);
		agendamentoService.atualizar(entity);
		return ResponseEntity.ok(entity);}
		catch(RegraNegocioException e) {
			
			return ResponseEntity.badRequest().body(e.getMessage());
		}	
		
	}).orElseGet( () -> new ResponseEntity("Agendamento não encontrado na Base de Dados", HttpStatus.BAD_REQUEST));
			
	}
		
		
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
