package com.uema.gestao_de_projetos.acadumeaapi.api.dto;

import java.time.LocalTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TurmaDTO {
	 private Long id;
	 private  String dia_da_semana;
	 private LocalTime horario;
	 private String funcionario;
	 private  String status_turma;
}
