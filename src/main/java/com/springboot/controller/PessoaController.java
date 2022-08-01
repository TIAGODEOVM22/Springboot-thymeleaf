package com.springboot.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.springboot.model.Pessoa;
import com.springboot.model.Telefone;
import com.springboot.repository.PessoaRepository;
import com.springboot.repository.TelefoneRepository;
import com.springboot.service.PessoaService;

@Controller
public class PessoaController {

	@Autowired
	private PessoaService pessoaService;

	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private TelefoneRepository telefoneRepository;

	@GetMapping("/cadastroPessoa")
	public ModelAndView inicio() {
		ModelAndView andView = new ModelAndView("cadastro/cadastroPessoa");
		andView.addObject("pessoaobj", new Pessoa());
		Iterable<Pessoa> pessoasIt = pessoaService.findAll();
		andView.addObject("pessoas", pessoasIt);
		return andView;/* redireciona para cadastroPessoa.html */
	}

	/* SALVA UMA PESSOA */
	@PostMapping("/**/salvarPessoa")
	public ModelAndView salvar( @Valid Pessoa pessoa, BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) { //bindingResult Captura os erros
			ModelAndView modelAndView = new ModelAndView("cadastro/cadastroPessoa");
			Iterable<Pessoa> pessoasIt = pessoaService.findAll();
			modelAndView.addObject("pessoas", pessoasIt);
			modelAndView.addObject("pessoaobj", pessoa);
			
			List<String> msg = new ArrayList<String>();
			for(ObjectError objectError : bindingResult.getAllErrors()) {
				msg.add(objectError.getDefaultMessage());//vem das anotações notnull e notempty
			}
			modelAndView.addObject("msg", msg);
			return modelAndView;
		}
		
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
	@GetMapping("/editarPessoa/{idpessoa}") /* mapeia a url e o id */
	public ModelAndView editar(@PathVariable("idpessoa") Long idpessoa) {
		Optional<Pessoa> pessoa = pessoaRepository.findById(idpessoa);
		ModelAndView andView = new ModelAndView("cadastro/cadastroPessoa");
		andView.addObject("pessoaobj", pessoa.get());
		return andView;

	}

	
	
	/*____________EDITAR PESSOA - NÃO CONSEGUI USAR CAMADA SERVICE________________ */
	
	/*@GetMapping("/editarPessoa/{idpessoa}") /* mapeia a url e o id */
	/*public ModelAndView editar(@PathVariable("idpessoa") Long id, Pessoa obj) {
		ModelAndView andView = new ModelAndView("cadastro/cadastroPessoa");
		andView.addObject("pessoas", pessoaService.atualiza(id, obj));
		andView.addObject("pessoas", pessoaService.findAll());
		andView.addObject("pessoaobj", new Pessoa());
		return andView;
	  }*/
	 

	
	
	/*_________EXCLUI PESSOA__________*/
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
	
	/*______iNSERIR TELEFONES______*/
	@GetMapping("/telefones/{idpessoa}") /* mapeia a url e o id */
	public ModelAndView telefones(@PathVariable("idpessoa") Long idpessoa) {
		Optional<Pessoa> pessoa = pessoaRepository.findById(idpessoa);
		ModelAndView andView = new ModelAndView("cadastro/telefones");/*tela telef. fica dentro de cadastro*/
		andView.addObject("telefones", telefoneRepository.getTelefones(idpessoa));/*wxibe os telefones da pessoa na tela*/
		andView.addObject("pessoaobj", pessoa.get());
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
	
	/*INSERIR TELEFONE*/
	@PostMapping("**/addfonepessoa/{pessoaid}")
	public ModelAndView addFonePessoa(Telefone telefone, @PathVariable("pessoaid") Long pessoaid) {
		
		Pessoa pessoa = pessoaRepository.findById(pessoaid).get();
		
		if(telefone != null && telefone.getNumero().isEmpty() || telefone.getTipo().isEmpty()) {
			ModelAndView andView = new ModelAndView("cadastro/telefones");
			andView.addObject("pessoaobj", pessoa);
			andView.addObject("telefones", telefoneRepository.getTelefones(pessoaid));
			
			List<String> msg = new ArrayList<String>();
			if(telefone.getNumero().isEmpty()) {
				msg.add("Número de Telefone deve ser informado");
			}
			if(telefone.getTipo().isEmpty()) {
				msg.add("Favor informar o tipo desse telefone");
			}
			andView.addObject("msg", msg);
			
			return andView;
		
		}
		
		telefone.setPessoa(pessoa);

		telefoneRepository.save(telefone);
		
		ModelAndView andView = new ModelAndView("cadastro/telefones");
		andView.addObject("pessoaobj", pessoa);
		andView.addObject("telefones", telefoneRepository.getTelefones(pessoaid));/**/
		return andView;
	}
	/*REMOVER TELEFONE*/
	@GetMapping("/excluirtelefone/{idtelefone}")
	public ModelAndView excluirTelefone(@PathVariable("idtelefone") Long idtelefone) {
	
		Pessoa pessoa = telefoneRepository.findById(idtelefone).get().getPessoa();
		
		telefoneRepository.deleteById(idtelefone);
		
		ModelAndView andView = new ModelAndView("cadastro/telefones");
		andView.addObject("pessoaobj", pessoa);
		andView.addObject("telefones", telefoneRepository.getTelefones(pessoa.getId()));/**/

		return andView;
	}
}
