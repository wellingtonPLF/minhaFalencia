package br.edu.ifpb.pweb2.agiota.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.edu.ifpb.pweb2.agiota.model.Favorito;

public interface FavoritoRepository  extends JpaRepository<Favorito, Integer>{
	
	@Query("SELECT f FROM Favorito f WHERE usuario_id = ?1")
	ArrayList<Favorito> getFavoritoByIdUser(Integer id);
}
