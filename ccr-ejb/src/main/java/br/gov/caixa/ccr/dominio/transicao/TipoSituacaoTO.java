package br.gov.caixa.ccr.dominio.transicao;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * The persistent class for the CCRTB037_TIPO_SITUACAO database table.
 * 
 */
@Entity
@Table(name="CCRTB037_TIPO_SITUACAO")
public class TipoSituacaoTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="NU_TIPO_SITUACAO")
	private Long id;

	@Column(name="NO_TIPO_SITUACAO")
	private String nome;

	public TipoSituacaoTO() {
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