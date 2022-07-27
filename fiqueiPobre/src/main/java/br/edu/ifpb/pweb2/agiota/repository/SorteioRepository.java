package br.edu.ifpb.pweb2.agiota.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.edu.ifpb.pweb2.agiota.model.Sorteio;

public interface SorteioRepository extends JpaRepository<Sorteio, Integer> {
	Sorteio findByNumero(Integer numero);
	
	@Query("SELECT s FROM Sorteio s WHERE (current_date - s.horaSorteio) < 7")
	Sorteio getSorteioByDate();
}
