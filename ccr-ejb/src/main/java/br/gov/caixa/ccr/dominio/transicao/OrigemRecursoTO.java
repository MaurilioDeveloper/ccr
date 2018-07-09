package br.gov.caixa.ccr.dominio.transicao;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * The persistent class for the CCRTB031_ORIGEM_RECURSO database table.
 * 
 */
@Entity
@Table(name="CCRTB031_ORIGEM_RECURSO")
public class OrigemRecursoTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="NU_ORIGEM_RECURSO")
	private Long id;

	@Column(name="NO_ORIGEM_RECURSO")
	private String nome;

	public OrigemRecursoTO() {
	}

	public OrigemRecursoTO(Long id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}