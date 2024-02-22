package com.uema.gestao_de_projetos.acadumeaapi.validation;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;	
@Component
public class ValidationEmail {
	 public boolean validarEmail(String email) {
	        // Expressão regular para validar endereços de email
	        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
	        
	        // Compilar a expressão regular em um padrão
	        Pattern pattern = Pattern.compile(regex);
	        
	        // Criar um objeto Matcher para aplicar o padrão à string do email
	        Matcher matcher = pattern.matcher(email);
	        
	        // Verificar se houve correspondência com o padrão
	        return matcher.matches();
	 }
}
