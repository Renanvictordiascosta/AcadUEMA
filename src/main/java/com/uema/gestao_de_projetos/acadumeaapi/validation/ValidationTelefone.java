package com.uema.gestao_de_projetos.acadumeaapi.validation;
import java.util.regex.Matcher;

import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
@Component
public class ValidationTelefone {

	public  boolean validarNumeroCelular(String numero) {
        // Expressão regular para validar números de celular no Brasil
        String regex = "\\(?(\\d{2})\\)?[-\\.\\s]?(\\d{5})[-\\.\\s]?(\\d{4})";
        
        // Compilar a expressão regular em um padrão
        Pattern pattern = Pattern.compile(regex);
        
        // Criar um objeto Matcher para aplicar o padrão à string do número
        Matcher matcher = pattern.matcher(numero);
        
        // Verificar se houve correspondência com o padrão
        return matcher.matches();
	}
}
