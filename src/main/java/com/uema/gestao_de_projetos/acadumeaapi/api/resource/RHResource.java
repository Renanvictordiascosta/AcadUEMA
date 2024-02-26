package com.uema.gestao_de_projetos.acadumeaapi.api.resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uema.gestao_de_projetos.acadumeaapi.api.dto.RHDTO;
import com.uema.gestao_de_projetos.acadumeaapi.exception.ErroAutenticacao;
import com.uema.gestao_de_projetos.acadumeaapi.model.entity.RH;
import com.uema.gestao_de_projetos.acadumeaapi.service.RHService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class RHResource {

	private final RHService service;
	
	@PostMapping("/autenticar")
	public ResponseEntity autenticar( @RequestBody RHDTO dto)
			{
		try {
			RH adminAutenticado = service.autenticar(dto.getCpf(), dto.getSenha());
			return ResponseEntity.ok(adminAutenticado);
			
		}
		catch(ErroAutenticacao e)
				{return ResponseEntity.badRequest().body(e.getMessage());}
	
		
			}
	
	
}
