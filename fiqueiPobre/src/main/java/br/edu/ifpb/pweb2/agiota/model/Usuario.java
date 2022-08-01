package br.edu.ifpb.pweb2.agiota.model;
import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "usuarios")
public class Usuario implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@NotBlank(message="Campo obrigatório")
	private String nome;
	
	@Temporal(TemporalType.DATE) 
	@DateTimeFormat(pattern = "dd/MM/yyyy") 
	@Future(message="Data deve ser futura!")
	private Date data;
	
	@NotBlank(message="Campo obrigatório")
	private String cpf;
	
	/*@NotBlank(message="Campo obrigatório")
	@Size(min= 5, max=20, message="Senha deve ser entre 5 e 20")
	private String password;*/
	
	private boolean admin = false;
	
	@OneToMany(mappedBy="usuario", cascade=CascadeType.ALL, orphanRemoval=true, fetch = FetchType.LAZY)
	private List<Apostas> apostasFavoritas = new ArrayList<Apostas>();
	
	public Usuario() {}
	
	public Usuario( Integer id ,String nome, Date dataNascimento, String cpf) {
		super();
		this.id = id;
		this.nome = nome;
		this.data = dataNascimento;
		this.cpf = cpf;
	}
	
	public Usuario( Integer id ,String nome, Date dataNascimento, String cpf, Boolean admin) {
		super();
		this.id = id;
		this.nome = nome;
		this.data = dataNascimento;
		this.cpf = cpf;
		this.admin = admin;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public boolean isAdmin() {
		return admin;
	}
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	/*
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}*/
	public List<Apostas> getApostasFavoritas() {
		return apostasFavoritas;
	}
	public void setApostasFavoritas(List<Apostas> apostasFavoritas) {
		this.apostasFavoritas = apostasFavoritas;
	}
	public void addApostas(Apostas aposta) {
		this.apostasFavoritas.add(aposta);
	}
}
