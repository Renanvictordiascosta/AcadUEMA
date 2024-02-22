package com.uema.gestao_de_projetos.acadumeaapi.validation;
import java.time.LocalDate;

public class ValidationData {
	 public boolean validarData(LocalDate data) {
	        // Se a data for nula, considere-a inválida
	        if (data == null) {
	            return false;
	        }
	        // Verifique se a data está no passado ou presente (ou seja, não está no futuro)
	        LocalDate dataAtual = LocalDate.now();
	        return !data.isAfter(dataAtual);
	    }
}
