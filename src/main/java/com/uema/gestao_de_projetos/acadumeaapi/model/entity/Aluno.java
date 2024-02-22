package com.uema.gestao_de_projetos.acadumeaapi.model.entity;

import java.time.LocalDate;

import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

//import com.fasterxml.jackson.annotation.JsonFormat;
//import com.fasterxml.jackson.annotation.JsonIgnore;
import com.uema.gestao_de_projetos.acadumeaapi.model.enums.TipoAluno;
import com.uema.gestao_de_projetos.acadumeaapi.model.enums.TipoGenero;
import com.uema.gestao_de_projetos.acadumeaapi.model.enums.TipoStatusPessoa;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//acaduemaapi
@Entity
@Table (name = "alunos" , schema = "acaduemaapi")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Aluno {

	@Id
	@Column(name = "id") 
	@GeneratedValue( strategy = GenerationType.IDENTITY) 
	 private Long id;
	@Column(name = "nome") 
	
	 private String nome;
	@Column(name = "email") 
	
	 private String email;
//	@Column(name = "senha") 
//	@JsonIgnore
//	 private String senha;

	@Column(name = "cpf")
	
	private String cpf;
	
	@Column(name = "genero")
	@Enumerated(value = EnumType.STRING)
	private TipoGenero genero;
	
	@Column(name = "tipo")
	@Enumerated(value = EnumType.STRING)
	private TipoAluno tipo;
	@Column(name = "status")
	@Enumerated(value = EnumType.STRING)
	private TipoStatusPessoa status;
	
	@Column(name = "telefone")
	
	private String telefone;
	
//	@Column(name = "data_nascimento")
//	@JsonFormat(pattern="dd-MM-yyyy",shape = JsonFormat.Shape.STRING)
//	@Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
//	private LocalDate dataNascimento;
	
	@Column(name = "data_cadastro")
	@Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
	private LocalDate dataCadastro;
	
	  @PrePersist
	    public void prePersist() {
	        this.dataCadastro = LocalDate.now();
	    }
	
}
