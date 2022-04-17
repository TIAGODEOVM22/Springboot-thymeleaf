package com.springboot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.model.Pessoa;
import com.springboot.repository.PessoaRepository;

import ch.qos.logback.core.net.SyslogOutputStream;

@Service
public class PessoaService {

	@Autowired
	private PessoaRepository pessoaRepository;

	/* SALVA NO BD */
	public Pessoa salvar(Pessoa obj) {
		return pessoaRepository.save(obj);
	}
	
	/*RETORNA PESSOA POR NOME*/
	public List<Pessoa> findByNomePes(String name){
		return pessoaRepository.findPessoaByName(name);
	}

	/* PROCURA PESSOA */
	public Pessoa procurar(Long id) {
		return pessoaRepository.getOne(id);
	}


	/* RETORNA TODAS AS PESSOAS DO BD */
	public List<Pessoa> findAll() {
		return pessoaRepository.findAll();
	}

	/* RETORNA PESSOA PELO ID */
	public Pessoa findById(Long id) {
		Optional<Pessoa> obj = pessoaRepository.findById(id);
		return obj.orElseThrow();

	}

	/* ATUALIZA PESSOA ID */
	/*public Pessoa update(Pessoa obj) {
		findById(obj.getId());
		return obj;
	}*/

	/* DELETA PELO ID */
	public Pessoa deletar(Long id) {
		pessoaRepository.deleteById(id);
		return null;
	}

	/*ATUALIZA PESSOA*/
	public Pessoa atualiza(Long id, Pessoa obj) {
		Pessoa oldPessoa = findById(id);
		oldPessoa.setNome(obj.getNome());
		oldPessoa.setSobrenome(obj.getSobrenome());
		oldPessoa.setIdade(obj.getIdade());
		return pessoaRepository.save(oldPessoa);
	}
}
