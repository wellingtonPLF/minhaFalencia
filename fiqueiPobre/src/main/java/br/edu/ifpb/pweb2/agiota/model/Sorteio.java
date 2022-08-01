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
	
	@ElementCollection
	@CollectionTable(name="resultado")
	private List<Integer> resultado;
	
	@Temporal(TemporalType.DATE)
	private Date horaSorteio;
	
	private Double precoPremio;

	public Sorteio() {}
	
	
	public Sorteio(Integer id, Date horaSorteio) {
		super();
		this.id = id;
		this.horaSorteio = horaSorteio;
	}
	
	public Sorteio(List<Integer> resultado, Date horaSorteio, Double precoPremio) {
		super();
		this.resultado = resultado;
		this.precoPremio = precoPremio;
		this.horaSorteio = horaSorteio;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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