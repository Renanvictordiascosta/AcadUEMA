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

import com.uema.gestao_de_projetos.acadumeaapi.model.entity.Aluno;
import com.uema.gestao_de_projetos.acadumeaapi.model.enums.TipoAluno;
import com.uema.gestao_de_projetos.acadumeaapi.model.enums.TipoGenero;
import com.uema.gestao_de_projetos.acadumeaapi.model.enums.TipoStatusPessoa;

@ExtendWith( SpringExtension.class )
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class AlunoRepositoryTest {

	@Autowired
	AlunoRepository repository;
	@Autowired
	TestEntityManager entityManager;
	
	public static Aluno criarAluno()
	{
		return Aluno.builder()
				.nome("aluno teste")
				.email("alunotest@exemplo.com")
				.cpf("72244178234")
				.telefone("19984883189")
				.genero(TipoGenero.MASCULINO)
				.tipo(TipoAluno.UEMA)
				.status(TipoStatusPessoa.EFETIVADO)
				.dataCadastro(LocalDate.now()).build();
		
		
	}
	
	
	@Test
	public void deveVerificarAExistenciaDeumCPF()
	{
		//cenario
		Aluno teste = criarAluno();
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
public void DevePesistirUmAlunoNaBD()
{
	
	Aluno teste = criarAluno();
	Aluno testesalvo = repository.save(teste);
	assertThat(testesalvo.getId()).isNotNull();
}
	
@Test
public void deveBuscarAlunoporCPF()
{
	//cenario
	Aluno teste = criarAluno();
	entityManager.persist(teste);
	//acao
	Optional<Aluno> result = repository.findByCpf("72244178234");
	//verificacao
	assertThat(result.isPresent()).isTrue();
}
	
@Test
public void deveRetornaVaziorQuandoBuscarAlunoporCPFqueNaoTemnaBase()
{
	//cenario
	
		//acao
	Optional<Aluno> result = repository.findByCpf("72244178234");
		//verificacao
	assertThat(result.isPresent()).isFalse();


}
		
	
	
	
	
	
	
	
}
