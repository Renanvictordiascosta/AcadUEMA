package com.uema.gestao_de_projetos.acadumeaapi.api.resource;

import java.time.LocalDate;
import java.util.List;
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

import com.uema.gestao_de_projetos.acadumeaapi.api.dto.AlunoDTO;
import com.uema.gestao_de_projetos.acadumeaapi.api.dto.AtualizarStatusPessoalDTO;
import com.uema.gestao_de_projetos.acadumeaapi.exception.RegraNegocioException;
import com.uema.gestao_de_projetos.acadumeaapi.model.entity.Aluno;
import com.uema.gestao_de_projetos.acadumeaapi.model.enums.TipoAluno;
import com.uema.gestao_de_projetos.acadumeaapi.model.enums.TipoGenero;
import com.uema.gestao_de_projetos.acadumeaapi.service.AlunoService;
import com.uema.gestao_de_projetos.acadumeaapi.model.enums.TipoStatusPessoa;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/alunos")
@RequiredArgsConstructor
public class AlunoResource {

	private final AlunoService service;
	
	private Aluno converter(AlunoDTO dto)
	{
		Aluno aluno = new Aluno();
		
		aluno.setId(dto.getId());
		aluno.setNome(dto.getNome());
		aluno.setEmail(dto.getEmail());
//		aluno.setSenha(dto.getSenha());
		aluno.setCpf(dto.getCpf());
		aluno.setTelefone(dto.getTelefone());
//		aluno.setDataNascimento(dto.getDataNascimento());
		if(dto.getTipo()!= null)
		{
			aluno.setTipo(TipoAluno.valueOf(dto.getTipo()));
			
		}
		if(dto.getGenero()!=null)
		{
			aluno.setGenero(TipoGenero.valueOf(dto.getGenero()));
		}
		if(dto.getStatus()!=null)
		{
			aluno.setStatus(TipoStatusPessoa.valueOf(dto.getStatus()));
			
		}
		return aluno;
	}
	
	@PostMapping
	public ResponseEntity salvar( @RequestBody AlunoDTO dto)
	{
		try {
			Aluno entidade = converter(dto);
			LocalDate data = LocalDate.now();
			entidade.setDataCadastro(data);
			service.salvarAluno(entidade);
			return new ResponseEntity(entidade,HttpStatus.CREATED);
		}
		catch(RegraNegocioException e)
		{
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	
	}
	
	@PutMapping("{id}/atualizar-id")
	public ResponseEntity atualizarPorId( @PathVariable("id") Long id, @RequestBody AlunoDTO dto)
	{
		return service.obterporId(id).map( entity -> {
				
				try {
					
					Aluno aluno = converter(dto);
					aluno.setId(entity.getId());
					service.atualizar(aluno);
					return ResponseEntity.ok(aluno);
					
				}
				catch(RegraNegocioException e)
				{
					return ResponseEntity.badRequest().body(e.getMessage());
				}
			
				
	}).orElseGet( () -> new ResponseEntity("Aluno não encontrado na Base de Dados", HttpStatus.BAD_REQUEST));	
	}
	
	@PutMapping("{cpf}/atualizar-cpf")
	public ResponseEntity atualizarPorCPF( @PathVariable("cpf") String cpf, @RequestBody AlunoDTO dto)
	{
		return service.obterporCpf(cpf).map( entity -> {
				
				try {
					
					Aluno aluno = converter(dto);
					aluno.setId(entity.getId());
					aluno.setCpf(entity.getCpf());
					service.atualizar(aluno);
					return ResponseEntity.ok(aluno);
					
				}
				catch(RegraNegocioException e)
				{
					return ResponseEntity.badRequest().body(e.getMessage());
				}
			
				
	}).orElseGet( () -> new ResponseEntity("Aluno não encontrado na Base de Dados", HttpStatus.BAD_REQUEST));	
	}
	
	@DeleteMapping("{cpf}")
	public ResponseEntity deletarPorCPF( @PathVariable("cpf") String cpf)
	{
		return service.obterporCpf(cpf).map(entity -> {
						service.deletar(entity);
					return new ResponseEntity(HttpStatus.NO_CONTENT);
				
		}).orElseGet( () -> new ResponseEntity("Aluno não encontrado na Base de Dados", HttpStatus.BAD_REQUEST));		
	}

	
	
	@GetMapping("/buscarporCPF")
	public ResponseEntity buscarAlunoporCPF(@RequestParam(value  = "cpf") String cpf)
	{

		return service.obterporCpf(cpf).map(entity -> {
			
			return ResponseEntity.ok(entity);
		}).orElseThrow( () -> new RegraNegocioException("Aluno não encontrado para o CPF informado!"));
		
	}
	
	@GetMapping("/buscarporId")
	public ResponseEntity buscarAlunoporId(@RequestParam(value  = "id") Long id)
	{

		return service.obterporId(id).map(entity -> {
			
			return ResponseEntity.ok(entity);
		}).orElseThrow( () -> new RegraNegocioException("Aluno não encontrado para o CPF informado!"));
		
	}
	@GetMapping("/buscarAlunos")
	public ResponseEntity buscarAlunos(
			@RequestParam(value  = "nome", required = false) String nome,
			@RequestParam(value  = "genero", required = false) String genero,
			@RequestParam(value  = "tipo", required = false) String tipo,
			@RequestParam(value  = "status", required = false) String status
			)
	{
		
		Aluno alunofiltro = new Aluno();
		alunofiltro.setNome(nome);
		 if (genero != null && !genero.isEmpty()) {
		alunofiltro.setGenero(TipoGenero.valueOf(genero));}
		 if (tipo != null && !tipo.isEmpty()) {
		alunofiltro.setTipo(TipoAluno.valueOf(tipo));}
		 if (status != null && status.isEmpty()){
		alunofiltro.setStatus(TipoStatusPessoa.valueOf(status));}
		List<Aluno> alunos = service.buscarAlunos(alunofiltro);
		return ResponseEntity.ok(alunos);
		}
	
	@PutMapping("{cpf}/atualizar-status")
	public ResponseEntity atualizarStatusAluno(@PathVariable("cpf") String cpf, @RequestBody AtualizarStatusPessoalDTO dto)
	{
		return service.obterporCpf(cpf).map(entity -> {
			
			TipoStatusPessoa statusselecionado = TipoStatusPessoa.valueOf(dto.getStatus());
			if(statusselecionado == null)
			{
				
				return ResponseEntity.badRequest().body("Não foi possivel atualizar o status do aluno, envie um status válido");
				
			}
			try {
				entity.setStatus(statusselecionado);
				service.atualizarStatusPessoal(entity, statusselecionado);
				return ResponseEntity.ok(entity);
			}
			catch(RegraNegocioException e)
			{
				return ResponseEntity.badRequest().body(e.getMessage());
			}
			
			
		}).orElseGet( () -> new ResponseEntity("Aluno não encontrado na Base de Dados", HttpStatus.BAD_REQUEST));
	}
	
	

}
