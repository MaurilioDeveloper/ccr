package br.gov.caixa.ccr.dominio.transicao;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * The persistent class for the CCRTB004_CANAL_ATENDIMENTO database table.
 * 
 */
@Entity
@Table(name="CCRTB004_CANAL_ATENDIMENTO")
public class CanalAtendimentoTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="NU_CANAL_ATENDIMENTO")
	private Long id;

	@Column(name="NO_CANAL_ATENDIMENTO")
	private String nome;

	public CanalAtendimentoTO() {
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