package com.uema.gestao_de_projetos.acadumeaapi.api.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MatriculaDTO {
	private long id;
	private String aluno;
	private long id_turma;
}
