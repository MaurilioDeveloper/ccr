package br.gov.caixa.ccr.dominio.transicao;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * The persistent class for the CCRVW002_UNIDADE_FEDERATIVA database table.
 * 
 */
@Table(name="CCRVW002_UNIDADE_FEDERATIVA")


@Entity 
public class UnidadeFederativaTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6459622730589884992L;

	@Column(name="NO_UF")
	private String nomeUf;

	@Column(name="SG_UF")
	@Id
	private String uf;

	public UnidadeFederativaTO() {
	}

	public String getNomeUf() {
		return this.nomeUf;
	}

	public void setNomeUf(String nomeUf) {
		this.nomeUf = nomeUf;
	}

	public String getUf() {
		return this.uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

}