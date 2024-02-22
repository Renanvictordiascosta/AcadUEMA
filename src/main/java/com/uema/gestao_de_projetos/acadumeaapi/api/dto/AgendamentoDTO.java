package com.uema.gestao_de_projetos.acadumeaapi.api.dto;

import java.time.LocalTime;

import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class AgendamentoDTO {

	private long id;
	private LocalTime horario;
	private Integer dia;
	private Integer mes;
	private Integer ano;
	private String status;
	private String aluno;
	private String funcionario;
	
}
