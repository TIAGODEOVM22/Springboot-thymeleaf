package com.springboot.controller;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.springboot.model.Pessoa;
import com.springboot.repository.PessoaRepository;
import com.springboot.service.PessoaService;

@Controller
public class PessoaController {
	
	@Autowired
	private PessoaService pessoaService;
	
	@Autowired
	private PessoaRepository pessoaRepository;

	@GetMapping("/cadastroPessoa")
	public ModelAndView inicio() {
		ModelAndView andView = new ModelAndView("cadastro/cadastroPessoa");
		andView.addObject("pessoaobj", new Pessoa());
		return andView;/*redireciona para cadastroPessoa.html*/
	}
	
	
	@PostMapping("/salvarPessoa")
	public ModelAndView salvar(Pessoa pessoa) {
		pessoaService.salvar(pessoa);
		
		ModelAndView andView = new ModelAndView("cadastro/cadastroPessoa");
		Iterable<Pessoa> pessoasIt = pessoaService.findAll();
		andView.addObject("pessoas",pessoasIt);
		andView.addObject("pessoaobj", new Pessoa());
		
		return andView;
	}
	
	@GetMapping("/listaPessoas")/*mapeia a url*/
	public ModelAndView pessoas() {
		ModelAndView andView = new ModelAndView("cadastro/cadastroPessoa");/*mostra a mesma tela de cadastro*/
		Iterable<Pessoa> pessoasIt = pessoaService.findAll();/*faz a interação de pessoas do BD no Objto pessoasIt*/
		andView.addObject("pessoas",pessoasIt);/*adiciono os objetos a view*/
		andView.addObject("pessoaobj", new Pessoa());
		return andView;
	}
	
	@GetMapping("/editarPessoa/{idpessoa}")/*mapeia a url e o id*/
	public ModelAndView editar(@PathVariable("idpessoa") Long idpessoa) {
		
		Optional<Pessoa> pessoa = pessoaRepository.findById(idpessoa);
		ModelAndView andView = new ModelAndView("cadastro/cadastroPessoa");
		andView.addObject("pessoaobj", pessoa.get());
		return andView;
		
	}
	
	
}
