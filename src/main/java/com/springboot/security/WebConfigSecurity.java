package com.springboot.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebConfigSecurity extends WebSecurityConfigurerAdapter {

	@Override //configura as solicitações de acesso por http
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()//desabilita config padrão de memória do spring
		.authorizeRequests()//restringe acessos
		.antMatchers(HttpMethod.GET, "/").permitAll()//qualquer user acessa
		.anyRequest().authenticated()/*NOSSA CONFIGURAÇÃO*/
		.and().formLogin().permitAll()//permite qualquer user
		.and().logout()//mapeia URL de logout e invalida user autenticado
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
		
	}
	
	@Override //cria autenticação do user com BD ou em memoria
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
		.withUser("tiago")
		.password("$2a$10$xEbH2polOtVaaQeoe.rpperR0c8MbzUaBPBLDPYnMLc4YA6SwxIeO")
		.roles("ADMIN");
		
		
	}
	@Override //Ignora URL especificas, por exemplo, urls do css
	public void configure(WebSecurity web) throws Exception {

		web.ignoring().antMatchers("/materialize/**");
	
	}
	
}
