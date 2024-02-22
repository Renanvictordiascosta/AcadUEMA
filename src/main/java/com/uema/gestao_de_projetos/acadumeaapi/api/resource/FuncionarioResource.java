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

import com.uema.gestao_de_projetos.acadumeaapi.api.dto.AtualizarStatusPessoalDTO;
import com.uema.gestao_de_projetos.acadumeaapi.api.dto.FuncionarioDTO;
import com.uema.gestao_de_projetos.acadumeaapi.exception.ErroAutenticacao;
import com.uema.gestao_de_projetos.acadumeaapi.exception.RegraNegocioException;
import com.uema.gestao_de_projetos.acadumeaapi.model.entity.Funcionario;
import com.uema.gestao_de_projetos.acadumeaapi.model.enums.TipoFuncionario;
import com.uema.gestao_de_projetos.acadumeaapi.model.enums.TipoGenero;
import com.uema.gestao_de_projetos.acadumeaapi.model.enums.TipoStatusPessoa;
import com.uema.gestao_de_projetos.acadumeaapi.service.FuncionarioService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/funcionarios")
@RequiredArgsConstructor
public class FuncionarioResource {
	
	private final FuncionarioService service;

		private Funcionario converter (FuncionarioDTO dto)
		{
			Funcionario funcionario = new Funcionario();
			

			funcionario.setId(dto.getId());
			funcionario.setNome(dto.getNome());
			funcionario.setEmail(dto.getEmail());
			funcionario.setSenha(dto.getSenha());
			funcionario.setCpf(dto.getCpf());
			funcionario.setTelefone(dto.getTelefone());
//			aluno.setDataNascimento(dto.getDataNascimento());
			if(dto.getTipo()!= null || !dto.getTipo().trim().equals(""))
			{
				funcionario.setTipo(TipoFuncionario.valueOf(dto.getTipo()));
				
			}
			if(dto.getGenero()!=null || !dto.getGenero().trim().equals(""))
			{
				funcionario.setGenero(TipoGenero.valueOf(dto.getGenero()));
			}
			if(dto.getStatus()!=null || !dto.getStatus().trim().equals(""))
			{
				funcionario.setStatus(TipoStatusPessoa.valueOf(dto.getStatus()));
				
			}
			return funcionario;
			
			
		}
		
		@PostMapping
		public ResponseEntity salvar( @RequestBody FuncionarioDTO dto)
		{
			try {
				Funcionario entidade = converter(dto);
				LocalDate data = LocalDate.now();
				entidade.setDataCadastro(data);
				service.salvarFuncionario(entidade);
				return new ResponseEntity(entidade,HttpStatus.CREATED);
			}
			catch(RegraNegocioException e){
				return ResponseEntity.badRequest().body(e.getMessage());
			}
					
		}
		
		
		@PutMapping("{id}/atualizar-id")
		public ResponseEntity atualizarPorId( @PathVariable("id") Long id, @RequestBody FuncionarioDTO  dto)
		{
			return service.obterPorId(id).map( entity -> {
					
					try {
						
						Funcionario funcionario = converter(dto);
						funcionario.setId(entity.getId());
						service.atualizar(funcionario);
						return ResponseEntity.ok(funcionario);
						
					}
					catch(RegraNegocioException e)
					{
						return ResponseEntity.badRequest().body(e.getMessage());
					}
				
					
		}).orElseGet( () -> new ResponseEntity("Funcionario não encontrado na Base de Dados", HttpStatus.BAD_REQUEST));	
		}
		
		@PutMapping("{cpf}/atualizar-cpf")
		public ResponseEntity atualizarPorCPF( @PathVariable("cpf") String cpf, @RequestBody FuncionarioDTO dto)
		{
			return service.obterPorCpf(cpf).map( entity -> {
					
					try {
						
						Funcionario funcionario = converter(dto);
						funcionario.setId(entity.getId());
						funcionario.setCpf(entity.getCpf());
						service.atualizar(funcionario);
						return ResponseEntity.ok(funcionario);
						
					}
					catch(RegraNegocioException e)
					{
						return ResponseEntity.badRequest().body(e.getMessage());
					}
				
					
		}).orElseGet( () -> new ResponseEntity("Funcionario não encontrado na Base de Dados", HttpStatus.BAD_REQUEST));	
		}
		
		@DeleteMapping("{cpf}")
		public ResponseEntity deletarPorCPF( @PathVariable("cpf") String cpf)
		{
			return service.obterPorCpf(cpf).map(entity -> {
							service.deletar(entity);
						return new ResponseEntity(HttpStatus.NO_CONTENT);
					
			}).orElseGet( () -> new ResponseEntity("Funcionario não encontrado na Base de Dados", HttpStatus.BAD_REQUEST));		
		}

		
		
		@GetMapping("/buscarporCPF")
		public ResponseEntity buscarFuncionarioporCPF(@RequestParam(value  = "cpf") String cpf)
		{

			return service.obterPorCpf(cpf).map(entity -> {
				
				return ResponseEntity.ok(entity);
			}).orElseThrow( () -> new RegraNegocioException("Funcionario não encontrado para o CPF informado!"));
			
		}
		
		@PostMapping("/autenticar")
		public ResponseEntity autenticar( @RequestBody FuncionarioDTO dto)
				{
			try {
				Funcionario funcionarioAutenticado = service.autenticar(dto.getCpf(), dto.getSenha());
				return ResponseEntity.ok(funcionarioAutenticado);
				
			}
			catch(ErroAutenticacao e)
					{return ResponseEntity.badRequest().body(e.getMessage());}
		
			
				}

		
		@PutMapping("{cpf}/atualizar-status")
		public ResponseEntity atualizarStatusFuncionario(@PathVariable("cpf") String cpf, @RequestBody AtualizarStatusPessoalDTO dto)
		{
			return service.obterPorCpf(cpf).map(entity -> {
				
				TipoStatusPessoa statusselecionado = TipoStatusPessoa.valueOf(dto.getStatus());
				if(statusselecionado == null)
				{
					
					return ResponseEntity.badRequest().body("Não foi possivel atualizar o status do Funcionario, envie um status válido");
					
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
				
				
			}).orElseGet( () -> new ResponseEntity("Funcionario não encontrado na Base de Dados", HttpStatus.BAD_REQUEST));
		}
		
		
		@GetMapping("/buscarFuncionarios")
		public ResponseEntity buscarFuncionarios(
				@RequestParam(value  = "nome", required = false) String nome,
				@RequestParam(value  = "tipo", required = false) String tipo	
				
				)
		{
			
			Funcionario funcionariofiltro = new Funcionario();
			funcionariofiltro.setNome(nome);
			 if (tipo != null && !tipo.isEmpty()) {
				 funcionariofiltro.setTipo(TipoFuncionario.valueOf(tipo));
 
			 }
			List<Funcionario> funcionarios = service.buscarFuncionarios(funcionariofiltro);
			return ResponseEntity.ok(funcionarios);
			
		
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
}
