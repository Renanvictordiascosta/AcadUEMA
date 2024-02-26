package com.uema.gestao_de_projetos.acadumeaapi.resource;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uema.gestao_de_projetos.acadumeaapi.api.dto.AlunoDTO;
import com.uema.gestao_de_projetos.acadumeaapi.api.resource.AlunoResource;
import com.uema.gestao_de_projetos.acadumeaapi.exception.RegraNegocioException;
import com.uema.gestao_de_projetos.acadumeaapi.model.entity.Aluno;
import com.uema.gestao_de_projetos.acadumeaapi.model.enums.TipoAluno;
import com.uema.gestao_de_projetos.acadumeaapi.model.enums.TipoGenero;
import com.uema.gestao_de_projetos.acadumeaapi.model.enums.TipoStatusPessoa;
import com.uema.gestao_de_projetos.acadumeaapi.service.AlunoService;



@ExtendWith( SpringExtension.class )
@ActiveProfiles("test")
@WebMvcTest(controllers = AlunoResource.class)
@AutoConfigureMockMvc
public class AlunoResourceTest {

	static final String API = "/api/alunos";
	static final MediaType JSON = MediaType.APPLICATION_JSON;
	@Autowired
	MockMvc mvc;
	
	@MockBean
	AlunoService service;
	
	@Test
	public void deveCriarUmusuario() throws Exception
	{
		
		String nome="Martin Renan Raul Campos";
		String email="martin_campos@gmai.com";
		String cpf="51896343457";
		String genero="MASCULINO";
		String tipo="COMUNIDADE";
		String status="EFETIVADO";
	    String telefone="19992466459";
		
	    //cenario
	    AlunoDTO dto = dto = AlunoDTO.builder()
	    		.nome(nome)
	    		.email(email)
	    		.cpf(cpf)
	    		.genero(genero)
	    		.tipo(tipo)
	    		.status(status).telefone(telefone).build();
	    Aluno aluno = Aluno.builder()
	    		.id(0l)
	    		.nome(nome)
	    		.email(email)
	    		.cpf(cpf)
	    		.genero(TipoGenero.valueOf(genero))
	    		.tipo(TipoAluno.valueOf(tipo))
	    		.status(TipoStatusPessoa.valueOf(status))
	    		.telefone(telefone)
	    		.build();
	    Mockito.when(service.salvarAluno(Mockito.any(Aluno.class))).thenReturn(aluno);
	    String json = new ObjectMapper().writeValueAsString(dto);
	    
	    
	    //verificacacao
	    MockHttpServletRequestBuilder requisicao = MockMvcRequestBuilders
	    		.post(API)
	    		.accept(JSON).
	    		contentType(JSON)
	    		.content(json);
	    
	    mvc.perform(requisicao)
		.andExpect(MockMvcResultMatchers.status().isCreated())
		.andExpect(MockMvcResultMatchers.jsonPath("id").value(aluno.getId()))
		.andExpect(MockMvcResultMatchers.jsonPath("nome").value(aluno.getNome()))
		.andExpect(MockMvcResultMatchers.jsonPath("cpf").value(aluno.getCpf()))
		.andExpect(MockMvcResultMatchers.jsonPath("telefone").value(aluno.getTelefone()))
		.andExpect(MockMvcResultMatchers.jsonPath("email").value(aluno.getEmail()));
	    
	}
	
	@Test
	public void deveRetornarBadRequestaoCriarUmusuario() throws Exception
	{
		
		String nome="Martin Renan Raul Campos";
		String email="martin_campos@gmai.com";
		String cpf="51896343457";
		String genero="MASCULINO";
		String tipo="COMUNIDADE";
		String status="EFETIVADO";
	    String telefone="19992466459";
		
	    //cenario
	    AlunoDTO dto = dto = AlunoDTO.builder()
	    		.nome(nome)
	    		.email(email)
	    		.cpf(cpf)
	    		.genero(genero)
	    		.tipo(tipo)
	    		.status(status).telefone(telefone).build();

	    Mockito.when(service.salvarAluno(Mockito.any(Aluno.class))).thenThrow(RegraNegocioException.class);
	    String json = new ObjectMapper().writeValueAsString(dto);
	    
	    
	    //verificacacao
	    MockHttpServletRequestBuilder requisicao = MockMvcRequestBuilders
	    		.post(API)
	    		.accept(JSON).
	    		contentType(JSON)
	    		.content(json);
	    
	    mvc.perform(requisicao)
		.andExpect(MockMvcResultMatchers.status().isBadRequest());
		
	}
	
	
}
