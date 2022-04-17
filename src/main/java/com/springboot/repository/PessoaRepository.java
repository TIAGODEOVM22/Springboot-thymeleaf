package com.springboot.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.springboot.model.Pessoa;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

	public PessoaRepository findByNome(String Nome);
	
	/*@query do JPARepository
	 * dentro escrevemos nosso JPQL
	 * %?1% pega o primeiro parametro, no nosso o nome*/
	@Query("select p from Pessoa p where p.nome like %?1%")
	List<Pessoa> findPessoaByName(String nome);
	
	public Optional<Pessoa> findById(Long id);
	
	
	
	
}
