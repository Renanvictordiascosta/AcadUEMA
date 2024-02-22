package com.uema.gestao_de_projetos.acadumeaapi.model.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import com.uema.gestao_de_projetos.acadumeaapi.model.enums.TipoStatusAgendamento;

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

//acaduemaapi
@Entity
@Table(name = "agendamentos", schema = "acaduemaapi")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Agendamento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "horario")
	@Convert(converter = Jsr310JpaConverters.LocalTimeConverter.class)
	private LocalTime horario;
	@Column(name = "dia")
	private Integer dia;
	@Column(name = "mes")
	private Integer mes;
	@Column(name = "ano")
	private Integer ano;
	@Column(name = "data_cadastro")
	@Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
	private LocalDate dataCadastro;
	@Column(name = "status")
	@Enumerated(value = EnumType.STRING)
	private TipoStatusAgendamento status;
	
	@ManyToOne
	@JoinColumn(name = "id_aluno")
	private Aluno alunos;
	
	@ManyToOne
	@JoinColumn(name = "id_funcionario")
	private Funcionario funcionario;
	
}
