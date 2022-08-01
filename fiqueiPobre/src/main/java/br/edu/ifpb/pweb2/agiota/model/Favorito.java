package br.edu.ifpb.pweb2.agiota.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "favorito")
public class Favorito implements Serializable {
private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;	
	
	@ManyToOne
	private Usuario usuario;
	
	@ElementCollection
	@CollectionTable(name="numeros")
	private List<Integer> numeros;	
	
	public Favorito() {
	}
	
	public Favorito(Integer id) {
		super();
		this.id = id;
	}
	
	public Favorito(Usuario usuario, List<Integer> numeros) {
		super();
		this.usuario = usuario;
		this.numeros = numeros ;
	}
	
	public Favorito(Integer id, Usuario usuario, List<Integer> numeros) {
		super();
		this.id = id;
		this.usuario = usuario;
		this.numeros = numeros ;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Integer> getNumeros() {
		return numeros;
	}

	public void setNumeros(List<Integer> numeros) {
		this.numeros = numeros;
	}
}
