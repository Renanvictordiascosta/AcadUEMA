package com.uema.gestao_de_projetos.acadumeaapi.model.entity;

import java.time.LocalDate;

import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import com.uema.gestao_de_projetos.acadumeaapi.model.enums.TipoFrequencia;

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
@Table (name = "frequencia" , schema = "acaduemaapi")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Frequencia {
	
	@Id
	@Column(name = "id") 
	@GeneratedValue( strategy = GenerationType.IDENTITY) 
	 private Long id;
	
	@Column(name = "data_da_frequencia")
	@Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
	private LocalDate dataFrequncia;
	
	@ManyToOne
	@JoinColumn(name = "id_matricula")
	private Matricula matricula;
	
	@Column(name = "frequencias")
	@Enumerated(value = EnumType.STRING)
	private TipoFrequencia frequencia;
	
}
