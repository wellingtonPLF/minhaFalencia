package br.edu.ifpb.pweb2.agiota.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import br.edu.ifpb.pweb2.agiota.model.Sorteio;

public interface SorteioRepository extends JpaRepository<Sorteio, Integer> {	
	@Query("SELECT s FROM Sorteio s WHERE (current_date - s.horaSorteio) < 7")
	Sorteio getSorteioByDate();
}
