package br.edu.ifpb.pweb2.agiota.repository;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifpb.pweb2.agiota.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{
	
	Usuario findByNome(String nome);
}
