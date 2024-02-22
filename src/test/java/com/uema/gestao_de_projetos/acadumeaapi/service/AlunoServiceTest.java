package com.uema.gestao_de_projetos.acadumeaapi.service;

import java.time.LocalDate;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.uema.gestao_de_projetos.acadumeaapi.model.entity.Aluno;
import com.uema.gestao_de_projetos.acadumeaapi.model.enums.TipoAluno;
import com.uema.gestao_de_projetos.acadumeaapi.model.enums.TipoGenero;
import com.uema.gestao_de_projetos.acadumeaapi.model.enums.TipoStatusPessoa;
import com.uema.gestao_de_projetos.acadumeaapi.model.repository.AlunoRepository;
import com.uema.gestao_de_projetos.acadumeaapi.service.impl.AlunoServiceImpl;

@ExtendWith( SpringExtension.class )
@ActiveProfiles("test")
public class AlunoServiceTest {
	public static Aluno criarAluno()
	{
		return Aluno.builder()
				.id(1l)
				.nome("aluno teste")
				.email("alunotest@exemplo.com")
				.cpf("72244178234")
				.telefone("19984883189")
				.genero(TipoGenero.MASCULINO)
				.tipo(TipoAluno.UEMA)
				.status(TipoStatusPessoa.EFETIVADO)
				.dataCadastro(LocalDate.now()).build();
		
		
	}
	@SpyBean
	AlunoServiceImpl service;
	
	@MockBean
	AlunoRepository repository;
	
	
	
}
