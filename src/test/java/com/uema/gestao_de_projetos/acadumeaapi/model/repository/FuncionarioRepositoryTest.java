package com.uema.gestao_de_projetos.acadumeaapi.model.repository;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.uema.gestao_de_projetos.acadumeaapi.model.entity.Funcionario;
import com.uema.gestao_de_projetos.acadumeaapi.model.enums.TipoFuncionario;
import com.uema.gestao_de_projetos.acadumeaapi.model.enums.TipoGenero;
import com.uema.gestao_de_projetos.acadumeaapi.model.enums.TipoStatusPessoa;

@ExtendWith( SpringExtension.class )
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class FuncionarioRepositoryTest {

	@Autowired
	FuncionarioRepository repository;
	@Autowired
	TestEntityManager entityManager;
	
	public static Funcionario criarFuncionario()
	{
		return Funcionario.builder()
				.nome("aluno teste")
				.email("alunotest@exemplo.com")
				.cpf("72244178234")
				.telefone("19984883189")
				.genero(TipoGenero.MASCULINO)
				.tipo(TipoFuncionario.NUTRICIONISTA)
				.status(TipoStatusPessoa.EFETIVADO)
				.senha("teste1123")
				.dataCadastro(LocalDate.now()).build();
		
		
	}
	
	
	@Test
	public void deveVerificarAExistenciaDeumCPF()
	{
		//cenario
		Funcionario teste = criarFuncionario();
		entityManager.persist(teste);
		//acao
		boolean result = repository.existsByCpf("72244178234");
		
		//verificação
		
		assertThat(result).isTrue();
		
	}
	
	

	@Test
	public void deveRetornaFalsenaAExistenciaDeumCPF()
	{
		//cenario
	
		//acao
		boolean result = repository.existsByCpf("72244178234");
		
		//verificação
		
		assertThat(result).isFalse();
		
	}
	
@Test
public void DevePesistirUmFuncionarioNaBD()
{
	
	Funcionario teste = criarFuncionario();
	Funcionario testesalvo = repository.save(teste);
	assertThat(testesalvo.getId()).isNotNull();
}
	
@Test
public void deveBuscarFuncionarioporCPF()
{
	//cenario
	Funcionario teste = criarFuncionario();
	entityManager.persist(teste);
	//acao
	Optional<Funcionario> result = repository.findByCpf("72244178234");
	//verificacao
	assertThat(result.isPresent()).isTrue();
}
	
@Test
public void deveRetornaVaziorQuandoBuscarAlunoporCPFqueNaoTemnaBase()
{
	//cenario
	
		//acao
	Optional<Funcionario> result = repository.findByCpf("72244178234");
		//verificacao
	assertThat(result.isPresent()).isFalse();


}
	
	
	
}
