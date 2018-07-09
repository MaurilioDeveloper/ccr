package br.gov.caixa.ccr.dominio.transicao;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * The persistent class for the CCRTB011_GRUPO_AVERBACAO database table.
 * 
 */
@Entity
@Table(name="CCRTB011_GRUPO_AVERBACAO")
public class GrupoAverbacaoTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="NU_GRUPO_AVERBACAO")
	private Long id;

	@Column(name="NO_GRUPO_AVERBACAO")
	private String nome;

	public GrupoAverbacaoTO(Long id, String numGrupoAverbacao) {
		super();
		this.id = id;
		this.nome = numGrupoAverbacao;
	}
	
	public GrupoAverbacaoTO() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
 

}