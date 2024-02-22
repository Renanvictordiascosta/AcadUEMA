package com.uema.gestao_de_projetos.acadumeaapi.exception;

public class ErroAutenticacao extends RuntimeException{
	public ErroAutenticacao (String mensagem) {
		super(mensagem);
	}
}
