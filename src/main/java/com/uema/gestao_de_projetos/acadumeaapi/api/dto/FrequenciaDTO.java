package com.uema.gestao_de_projetos.acadumeaapi.api.dto;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class FrequenciaDTO {
	private long id;
	private LocalDate data_da_frequencia;
	private long id_matricula;
	private String frequencia_tipo;
}

