package com.uema.gestao_de_projetos.acadumeaapi.validation;

import org.springframework.stereotype.Component;

@Component
public class ValidationCPF {

	public boolean validarCPF(String cpf) {
		
		if(cpf == null)
			return false;
		if(cpf.trim().equals(""))
		  return false;
		 // Remover caracteres não numéricos
        cpf = cpf.replaceAll("[^0-9]", "");
		
        // Verificar se o CPF possui 11 dígitos
        if (cpf.length() != 11)
            return false;

        // Verificar se todos os dígitos são iguais (ex: 111.111.111-11)
        if (cpf.matches("(\\d)\\1*$"))
            return false;

        // Calcular o primeiro dígito verificador
        int soma = 0;
        for (int i = 0; i < 9; i++) {
            soma += (10 - i) * Character.getNumericValue(cpf.charAt(i));
        }
        int resto = soma % 11;
        int digitoVerificador1 = (resto < 2) ? 0 : (11 - resto);

        // Verificar o primeiro dígito verificador
        if (digitoVerificador1 != Character.getNumericValue(cpf.charAt(9)))
            return false;

        // Calcular o segundo dígito verificador
        soma = 0;
        for (int i = 0; i < 10; i++) {
            soma += (11 - i) * Character.getNumericValue(cpf.charAt(i));
        }
        resto = soma % 11;
        int digitoVerificador2 = (resto < 2) ? 0 : (11 - resto);

        // Verificar o segundo dígito verificador
        if (digitoVerificador2 != Character.getNumericValue(cpf.charAt(10)))
            return false;

        // CPF válido
        return true;
	
	}
	
}
