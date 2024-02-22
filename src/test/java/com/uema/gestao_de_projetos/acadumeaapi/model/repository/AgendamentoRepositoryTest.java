package com.uema.gestao_de_projetos.acadumeaapi.model.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.uema.gestao_de_projetos.acadumeaapi.model.entity.Agendamento;
import com.uema.gestao_de_projetos.acadumeaapi.model.entity.Aluno;
import com.uema.gestao_de_projetos.acadumeaapi.model.enums.TipoAluno;
import com.uema.gestao_de_projetos.acadumeaapi.model.enums.TipoGenero;
import com.uema.gestao_de_projetos.acadumeaapi.model.enums.TipoStatusAgendamento;
import com.uema.gestao_de_projetos.acadumeaapi.model.enums.TipoStatusPessoa;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
@ExtendWith( SpringExtension.class )
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class AgendamentoRepositoryTest {

	@Autowired
	AgendamentoRepository repository;
	@Autowired
	AlunoRepository alunorepository;
	@Autowired
	TestEntityManager entityManager;
	
	public static Agendamento criarAgendamento(Aluno aluno)
	{
		return Agendamento.builder()
				.horario(LocalTime.now())
				.dia(13)
				.mes(5)
				.ano(2000)
				.status(TipoStatusAgendamento.EFETIVADO)
				.dataCadastro(LocalDate.now())
				.alunos(aluno)
				.build();
	}
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
	
	public Agendamento CriarePesistirUmAgendamento()
	{
		Aluno alunoteste = criarAluno();
		 entityManager.persist(alunoteste);
		Agendamento agendamento = criarAgendamento(alunoteste);
		 entityManager.persist(agendamento);
		return agendamento;
		
	}
	
	@Test
	public void deveSalvarUmAgendamento()
	{
		Aluno alunoteste = criarAluno();
		 entityManager.persist(alunoteste);
		Agendamento agendamento = criarAgendamento(alunoteste);
		
		agendamento = repository.save(agendamento);
		
		assertThat(agendamento.getId()).isNotNull();
		

	}
	
	@Test
	public void deveDeletrUmAgendamento()
	{
		Agendamento agendamento = CriarePesistirUmAgendamento();
		agendamento = entityManager.find(Agendamento.class, agendamento.getId());
		repository.delete(agendamento);
		Agendamento agendamentoApagado = entityManager.find(Agendamento.class, agendamento.getId());
		assertThat(agendamentoApagado).isNull();
	

	}
	
	@Test
	public void deveAtualizarUmAgendamento()
	{
		Agendamento agendamento = CriarePesistirUmAgendamento();
		
		agendamento.setAno(2020);
		agendamento.setStatus(TipoStatusAgendamento.CANCELADO);
		
		repository.save(agendamento);
		Agendamento agendamentoAtualizado = entityManager.find(Agendamento.class, agendamento.getId());
		assertThat(agendamentoAtualizado.getAno()).isEqualTo(2020);
		assertThat(agendamentoAtualizado.getStatus()).isEqualTo(TipoStatusAgendamento.CANCELADO);
	}
	
	
	
	@Test 
	public void deveBuscarUmAgendamentopord()
	{
		Agendamento agendamento = CriarePesistirUmAgendamento();	
		
		Optional<Agendamento> agendamentoBuscado = repository.findById(agendamento.getId());
		
		assertThat(agendamentoBuscado.isPresent()).isTrue();
	}
	
	@Test
	public void deveBuscarUmAgendamentoJaAgendadonomesmoHorarioParaoMesmlALuno()
	{
		Agendamento agendamento = CriarePesistirUmAgendamento();
		Optional<Agendamento> agendamentoFeito = repository.findById(agendamento.getId());
		Optional<Agendamento> agendamentoBuscado = repository.findAgendamentoByAlunoAndData(agendamentoFeito.get().getAlunos().getId(), agendamentoFeito.get().getHorario(),13 ,5 ,2000 );
		
		assertThat(agendamentoBuscado.isPresent()).isTrue();
	}
	
	
	
	
	
}
