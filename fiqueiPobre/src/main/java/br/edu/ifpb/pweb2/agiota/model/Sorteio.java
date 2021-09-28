package br.edu.ifpb.pweb2.agiota.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "sorteio")
public class Sorteio implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private Integer numero;
	
	@ElementCollection
	@CollectionTable(name="resultado")
	private List<Integer> resultado;
	
	@Temporal(TemporalType.DATE)
	private Date horaSorteio;
	
	private Double precoPremio;

	public Sorteio() {}
	
	public Sorteio(Integer id, Integer numero, Date horaSorteio) {
		super();
		this.id = id;
		this.numero = numero;
		this.horaSorteio = horaSorteio;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public List<Integer> getResultado() {
		return resultado;
	}

	public void setResultado(List<Integer> resultado) {
		this.resultado = resultado;
	}

	public Date getHoraSorteio() {
		return horaSorteio;
	}

	public void setHoraSorteio(Date horaSorteio) {
		this.horaSorteio = horaSorteio;
	}

	public Double getPrecoPremio() {
		return precoPremio;
	}

	public void setPrecoPremio(Double precoPremio) {
		this.precoPremio = precoPremio;
	}
}