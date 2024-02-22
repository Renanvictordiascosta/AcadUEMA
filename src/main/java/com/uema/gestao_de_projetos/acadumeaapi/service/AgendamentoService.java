package com.uema.gestao_de_projetos.acadumeaapi.service;

import java.util.List;
import java.util.Optional;

import com.uema.gestao_de_projetos.acadumeaapi.model.entity.Agendamento;
import com.uema.gestao_de_projetos.acadumeaapi.model.enums.TipoStatusAgendamento;

public interface AgendamentoService {

	Agendamento salvar(Agendamento agendamento);
	Agendamento atualizar(Agendamento agendamento);
	void deletar(Agendamento agendamento);
	List<Agendamento>buscarAgendamento(Agendamento agendamentofiltro);
	void atualizarStatus(Agendamento agendamento, TipoStatusAgendamento status);
	void validar(Agendamento agendamento);
	Optional<Agendamento>ObterPorid(Long id);
	boolean temAgendamentoSobrepostoAluno(Agendamento agendamento);
	boolean temAgendamentoSobrepostoFuncionario(Agendamento agendamento);
	
}
