package com.uema.gestao_de_projetos.acadumeaapi.service;


import com.uema.gestao_de_projetos.acadumeaapi.model.entity.RH;

public interface RHService {
	RH autenticar(String cpf, String senha); 
}
