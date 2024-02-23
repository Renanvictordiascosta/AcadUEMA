package com.uema.gestao_de_projetos.acadumeaapi.model.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import com.uema.gestao_de_projetos.acadumeaapi.model.enums.Dia_da_Semana;
import com.uema.gestao_de_projetos.acadumeaapi.model.enums.StatusTurma;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table (name = "turmas" , schema = "acaduemaapi")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Turma {
	@Id
	@Column(name = "id") 
	@GeneratedValue( strategy = GenerationType.IDENTITY) 
	 private Long id;
	@Column(name = "dia_da_semana") 
	@Enumerated(value = EnumType.STRING)
	private  Dia_da_Semana dia_da_semana;
	
	@Column(name = "horario")
	@Convert(converter = Jsr310JpaConverters.LocalTimeConverter.class)
	private LocalTime horario;
	
	@ManyToOne
	@JoinColumn(name = "id_funcionario")
	private Funcionario funcionario;
	
	@Column(name = "data_cadastro")
	@Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
	private LocalDate dataCadastro;
	
	@Column(name = "status_da_turma") 
	@Enumerated(value = EnumType.STRING)
	private  StatusTurma status_turma;
	
}
