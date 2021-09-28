package br.edu.ifpb.pweb2.agiota.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifpb.pweb2.agiota.model.Sorteio;

public interface SorteioRepository extends JpaRepository<Sorteio, Integer> {
	Sorteio findByNumero(Integer numero);
}
