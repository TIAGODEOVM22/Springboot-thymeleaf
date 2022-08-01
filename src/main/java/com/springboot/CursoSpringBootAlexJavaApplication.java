package com.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CursoSpringBootAlexJavaApplication {

	public static void main(String[] args) {
		SpringApplication.run(CursoSpringBootAlexJavaApplication.class, args);
		//MANDAR RODAR ESSA CLASSE JAVA.RUN APLICATION
		//Ai vai gerar a criptografia da senha.
		/*BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String result = encoder.encode("123");
		System.out.println(result);*/
		
	
	}

}
