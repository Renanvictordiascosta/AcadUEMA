package com.uema.gestao_de_projetos.acadumeaapi.api.dto;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RHDTO {
	private long id;
	private String nome;
	private String email;
	private String senha;
	private String cpf;
	private String genero;
	private String telefone;
	private LocalDate dataNascimento;
}
