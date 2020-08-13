package br.com.isidrocorp.projetofinal.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="tbl_departamento")
public class Departamento {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_depto")
	private int idDepto;
	
	@Column(name="nome", length=100)
	private String nome;
	
	@Column(name="unidade", length=50)
	private String unidade;
	
	@Column(name="andar")
	private int    andar;
	
	@OneToMany(mappedBy="depto", cascade=CascadeType.ALL)
	@JsonIgnoreProperties("depto")
	private List<Usuario> listaUsers;
	
	
	public int getIdDepto() {
		return idDepto;
	}
	public void setIdDepto(int idDepto) {
		this.idDepto = idDepto;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getUnidade() {
		return unidade;
	}
	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}
	public int getAndar() {
		return andar;
	}
	public void setAndar(int andar) {
		this.andar = andar;
	}
	public List<Usuario> getListaUsers() {
		return listaUsers;
	}
	public void setListaUsers(List<Usuario> listaUsers) {
		this.listaUsers = listaUsers;
	}

	
	
	
}
