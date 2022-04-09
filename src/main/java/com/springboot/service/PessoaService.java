package com.springboot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.model.Pessoa;
import com.springboot.repository.PessoaRepository;

@Service
public class PessoaService {
	

	@Autowired
	private PessoaRepository pessoaRepository;
	
	/*SALVA NO BD*/
	public Pessoa salvar(Pessoa obj) {
		
		return pessoaRepository.save(obj);
		
	}
	public Pessoa procurar(Long id) {
		
		return pessoaRepository.getOne(id);
	}
	
	
	
	/*RETORNA TODAS AS PESSOAS DO BD*/
	public List<Pessoa> findAll() {
		return pessoaRepository.findAll();
	}
	
	/*RETORNA PESSOA PELO ID*/
	public Pessoa findById(Long id) {
		Optional<Pessoa> obj = pessoaRepository.findById(id);
		return obj.orElseThrow();
	}
	
	/*ATUALIZA PESSOA*/
	public Pessoa update(Pessoa obj) {
		
		findById(obj.getId());
		return obj;
	}
}
