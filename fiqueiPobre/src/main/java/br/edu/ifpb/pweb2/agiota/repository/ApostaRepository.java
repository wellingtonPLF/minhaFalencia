package br.edu.ifpb.pweb2.agiota.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifpb.pweb2.agiota.model.Apostas;

public interface ApostaRepository extends JpaRepository<Apostas, Integer>{
	
}
