package com.springboot.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
		return andView;/* redireciona para cadastroPessoa.html */
	}

	/* SALVA UMA PESSOA */
	@PostMapping("/**/salvarPessoa")
	public ModelAndView salvar(Pessoa pessoa) {
		pessoaService.salvar(pessoa);

		ModelAndView andView = new ModelAndView("cadastro/cadastroPessoa");
		Iterable<Pessoa> pessoasIt = pessoaService.findAll();
		andView.addObject("pessoas", pessoasIt);
		andView.addObject("pessoaobj", new Pessoa());

		return andView;
	}

	/* LISTA TODAS AS PESSOAS */
	@GetMapping("/listaPessoas") /* mapeia a url */
	public ModelAndView pessoas() {
		ModelAndView andView = new ModelAndView("cadastro/cadastroPessoa");/* mostra a mesma tela de cadastro */
		Iterable<Pessoa> pessoasIt = pessoaService.findAll();/* faz a interação de pessoas do BD no Objto pessoasIt */
		andView.addObject("pessoas", pessoasIt);/* adiciono os objetos a view */
		andView.addObject("pessoaobj", new Pessoa());
		return andView;
	}

	/* EDITAR PESSOA - ESTE FUNCIONA */
	/*@GetMapping("/editarPessoa/{idpessoa}") /* mapeia a url e o id */
	/*public ModelAndView editar(@PathVariable("idpessoa") Long idpessoa) {
		Optional<Pessoa> pessoa = pessoaRepository.findById(idpessoa);
		ModelAndView andView = new ModelAndView("cadastro/cadastroPessoa");
		andView.addObject("pessoaobj", pessoa.get());
		return andView;

	}*/

	
	
	/* EDITAR PESSOA - NÃO CONSEGUI USAR CAMADA SERVICE */
	@GetMapping("/editarPessoa/{idpessoa}") /* mapeia a url e o id */
	public ModelAndView editar(@PathVariable("idpessoa") Long id, Pessoa obj) {
		ModelAndView andView = new ModelAndView("cadastro/cadastroPessoa");
		andView.addObject("pessoas", pessoaService.atualiza(id, obj));
		andView.addObject("pessoas", pessoaService.findAll());
		andView.addObject("pessoaobj", new Pessoa());
		return andView;
	  }
	 

	
	
	/* EXCLUI PESSOA */
	@GetMapping("/excluirPessoa/{idpessoa}")
	public ModelAndView excluir(@PathVariable("idpessoa") Long idpessoa) {
		/* PathVariable recebe o valor do idpessoa */
		pessoaService.deletar(idpessoa);
		ModelAndView andView = new ModelAndView("cadastro/cadastroPessoa");
		andView.addObject("pessoas", pessoaRepository.findAll());
		andView.addObject("pessoaobj", new Pessoa());/*
														 * preciso recarregar a tela com um objeto vazio por causa do
														 * FORM th:object="${pessoaobj}"
														 */
		return andView;
	}
	
	/*PESQUISA POR NOME*/
	@PostMapping("**/pesquisaPessoa")/*ignora o que tem antes e intercepta o <form action="pesquisaPessoa"*/
	public ModelAndView pesquisar(@RequestParam("nomePesquisa") String nomePesquisa) {/*intercepta o input name="nomePesquisa" la do FORM*/
		ModelAndView modelAndView = new ModelAndView("cadastro/cadastroPessoa");
		modelAndView.addObject("pessoas",pessoaService.findByNomePes(nomePesquisa));
		modelAndView.addObject("pessoaobj", new Pessoa());
		return modelAndView;
	}

}
