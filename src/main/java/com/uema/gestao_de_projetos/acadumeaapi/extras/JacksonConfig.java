//package com.uema.gestao_de_projetos.acadumeaapi.extras;
//
//import java.text.SimpleDateFormat;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import com.fasterxml.jackson.databind.DeserializationFeature;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//
//@Configuration
//public class JacksonConfig {
//	 @Bean
//	    public ObjectMapper objectMapper() {
//	        ObjectMapper mapper = new ObjectMapper();
//	        mapper.registerModule(new JavaTimeModule());
//	        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//	        mapper.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
//	        return mapper;
//	    }
//}
