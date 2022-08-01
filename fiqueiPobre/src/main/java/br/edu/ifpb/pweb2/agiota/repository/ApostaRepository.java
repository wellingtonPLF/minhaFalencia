package br.edu.ifpb.pweb2.agiota.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import br.edu.ifpb.pweb2.agiota.model.Apostas;
import br.edu.ifpb.pweb2.agiota.model.Sorteio;

public interface ApostaRepository extends JpaRepository<Apostas, Integer>{
	
	@Query("SELECT a FROM Apostas a WHERE usuario_id = ?1")
	ArrayList<Apostas> getApostasByIdUser(Integer id);
	
	@Query("SELECT a FROM Apostas a WHERE fk_sorteio = ?1")
	ArrayList<Apostas> getApostasBySorteio(Integer id);
	
	//@Query("SELECT a FROM Apostas a JOIN a.aposta p WHERE p = ?2 and a.id = ?1")
	//Apostas getAposta(Integer id, Integer numero);
	
	//@Modifying
	//@Query("delete from Apostas a where a.id= ?1")
	//void deleteApostasById(Integer id);
}
