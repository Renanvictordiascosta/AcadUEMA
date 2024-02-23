package com.uema.gestao_de_projetos.acadumeaapi.service;


import java.util.Optional;

import com.uema.gestao_de_projetos.acadumeaapi.model.entity.Frequencia;
import com.uema.gestao_de_projetos.acadumeaapi.model.enums.TipoFrequencia;



public interface FrequenciaService {
	Frequencia salvar(Frequencia frequencia);
	Frequencia  atualizar(Frequencia  frequencia);
	void validar(Frequencia frequencia);
	void deletar(Frequencia  frequencia);
	Optional <Frequencia > obterporId(Long id);
	void atualizarTipoFrequencia ( Frequencia  frequencia,TipoFrequencia status);
	Long frequenciaPorTipo(TipoFrequencia status,Long idAluno, Long idMatricula);
}
