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

import com.uema.gestao_de_projetos.acadumeaapi.api.dto.AtualizarStatusTurmaDTO;
import com.uema.gestao_de_projetos.acadumeaapi.api.dto.TurmaDTO;
import com.uema.gestao_de_projetos.acadumeaapi.exception.RegraNegocioException;
import com.uema.gestao_de_projetos.acadumeaapi.model.entity.Funcionario;
import com.uema.gestao_de_projetos.acadumeaapi.model.entity.Turma;
import com.uema.gestao_de_projetos.acadumeaapi.model.enums.Dia_da_Semana;
import com.uema.gestao_de_projetos.acadumeaapi.model.enums.StatusTurma;
import com.uema.gestao_de_projetos.acadumeaapi.model.enums.TipoFuncionario;
import com.uema.gestao_de_projetos.acadumeaapi.model.enums.TipoStatusPessoa;
import com.uema.gestao_de_projetos.acadumeaapi.service.FuncionarioService;
//import com.uema.gestao_de_projetos.acadumeaapi.service.MatriculaService;
import com.uema.gestao_de_projetos.acadumeaapi.service.TurmaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/turmas")
@RequiredArgsConstructor
public class TurmaResource {

	private final TurmaService service;
	private final FuncionarioService funcionarioservice;
//	private final MatriculaService matriculaservice;
	
	private Turma converter(TurmaDTO dto)
	{
		Turma turma = new Turma();
		turma.setHorario(dto.getHorario());
	  if (dto.getDia_da_semana() != null)
	  {
		  turma.setDia_da_semana(Dia_da_Semana.valueOf(dto.getDia_da_semana()));
	  }
	  if (dto.getStatus_turma() != null)
	  { 
		  turma.setStatus_turma(StatusTurma.valueOf(dto.getStatus_turma()));
	  }
	 
	  Funcionario funcionario = funcionarioservice.obterPorCpf(dto.getFuncionario()).orElseThrow( () -> new RegraNegocioException("Funcionario não encontrado para o CPF informado!"));
		turma.setFuncionario(funcionario);
		return turma;
	}
	
	@PostMapping
	public ResponseEntity salvar( @RequestBody TurmaDTO dto)
	{
		
		try {
			Turma entidade = converter(dto);
			  if(entidade.getFuncionario().getTipo() != TipoFuncionario.AVALIADOR)
			   {
				   return ResponseEntity.badRequest().body("Funcionario não é Avaliador para Criar Turmas.");
			   }
			   if(entidade.getFuncionario().getStatus() == TipoStatusPessoa.CANCELADO)
			   {
				   return ResponseEntity.badRequest().body("Funcionario está com Cadastro Cancelado. Não é possivel fazer Turmas para Ele!");
			   }
		
			   LocalDate data = LocalDate.now();
			   entidade.setDataCadastro(data);
			   boolean salvamentoduplo = service.temTurmaSobrepostaParaAvaliador(entidade);
			   if(!salvamentoduplo)
			   {
				   service.salvar(entidade);
				   return new ResponseEntity(entidade,HttpStatus.CREATED);}
				else
				{
					return ResponseEntity.badRequest().body("Já possui uma TURMA para a mesmo mesmo dia e horario para o  Funcionario. Refaça o Cadastro!");
				}
			   
		}
		catch(RegraNegocioException e)
		{
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@GetMapping
	public ResponseEntity buscarTurma(
			@RequestParam(value  ="funcionario", required = false) String funcionario,
			@RequestParam(value  ="dia_da_semana", required = false) String dia,
			@RequestParam(value  ="horario", required = false) LocalTime horario
			)
	{
			Turma filtro = new Turma();
			Optional<Funcionario> funcionariofiltro = funcionarioservice.obterPorCpf(funcionario);
			if(funcionariofiltro.isPresent())
			{
				filtro.setFuncionario(funcionariofiltro.get());
			}
			filtro.setHorario(horario);
			if(dia != null && !dia.isEmpty() )
			{
				filtro.setDia_da_semana(Dia_da_Semana.valueOf(dia));
			}
			
			List<Turma> turmas = service.buscarTurma(filtro);
			return ResponseEntity.ok(turmas);
	}
	
	

	@PutMapping("{id}/atualizar-status-turma")
	public ResponseEntity atualizarStatus(@PathVariable("id") Long id, @RequestBody AtualizarStatusTurmaDTO dto )
	{
		
		return service.obterporId(id).map( entity ->{
			StatusTurma statusselecionado = StatusTurma.valueOf(dto.getStatus());
			if(statusselecionado == null)
			{
				return ResponseEntity.badRequest().body("Não foi possivel atualizar o status da Turma, envie um status válido");
			}
			try {
			entity.setStatus_turma(statusselecionado);
			service.atualizar(entity);
			return ResponseEntity.ok(entity);}
			catch(RegraNegocioException e) {
				
				return ResponseEntity.badRequest().body(e.getMessage());
			}	
		
		}).orElseGet( () -> new ResponseEntity("Turma não encontrado na Base de Dados", HttpStatus.BAD_REQUEST));
		
	
	}
	
//	@DeleteMapping("{id}")
//	public ResponseEntity deletar( @PathVariable("id") Long id)
//	{
//		return service.obterporId(id).map( entity ->{
//		boolean temja = matriculaservice.TemTurmaMatriculadaJa(entity.getFuncionario().getId());
//			if(temja == true)
//			{
//				return ResponseEntity.badRequest().body("Não foi possivel deletar turma, ela já possui matriculas a ela vinculadas, Mude o Status da Turma pra CANCELADO");
//			}
//			else
//			{
//				service.deletar(entity);
//				return new ResponseEntity(HttpStatus.NO_CONTENT);
//			}
//			
//
//		}).orElseGet( () -> new ResponseEntity("Turma não encontrado na Base de Dados", HttpStatus.BAD_REQUEST));
//		
//	}
	
	@PutMapping("{id}/atualizar")
	public ResponseEntity atualizarPorId( @PathVariable("id") Long id, @RequestBody TurmaDTO dto)
	{
		
		return service.obterporId(id).map( entity ->{
			
			try {
				Turma turma = converter(dto);
				turma.setId(entity.getId());
				service.atualizar(turma);
				return ResponseEntity.ok(turma);
				
			}
			catch(RegraNegocioException e)
			{
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}).orElseGet( () -> new ResponseEntity("Turma não encontrado na Base de Dados", HttpStatus.BAD_REQUEST));
		
		
	}
	
	
	
}
