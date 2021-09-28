package br.edu.ifpb.pweb2.agiota.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "apostas")
public class Apostas implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;	
	
	@ManyToOne
	private Usuario usuario;	
	
	@ElementCollection
	@CollectionTable(name="aposta")
	private List<Integer> aposta;	
	
	@OneToOne
	@JoinColumn (name="FK_sorteio")
	private Sorteio sorteio;

	public Apostas() {}
	
	public Apostas(Integer id, Usuario usuario, List<Integer> aposta) {
		super();
		this.id = id;
		this.usuario = usuario;
		this.aposta = aposta;
	}
	
	public Apostas(Integer id, Usuario usuario, List<Integer> aposta, Sorteio sorteio) {
		super();
		this.id = id;
		this.usuario = usuario;
		this.aposta = aposta;
		this.sorteio = sorteio;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setCliente(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Integer> getAposta() {
		return aposta;
	}

	public void setAposta(List<Integer> aposta) {
		this.aposta = aposta;
	}

	public Sorteio getSorteio() {
		return sorteio;
	}

	public void setSorteio(Sorteio sorteio) {
		this.sorteio = sorteio;
	}
}
