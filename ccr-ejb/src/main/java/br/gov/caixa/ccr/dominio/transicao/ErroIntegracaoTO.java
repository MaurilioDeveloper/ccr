package br.gov.caixa.ccr.dominio.transicao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="CCRTB020_ERRO_INTEGRACAO")
@Entity
public class ErroIntegracaoTO {

	@Id
	@Column(name="CO_ERRO_INTEGRACAO")
	private Long nuErroIntegracao;
	
	@Column(name="DE_ERRO_INTEGRACAO")
	private String descErroIntegracao;

	/**
	 * @return the nuErroIntegracao
	 */
	public Long getNuErroIntegracao() {
		return nuErroIntegracao;
	}

	/**
	 * @param nuErroIntegracao the nuErroIntegracao to set
	 */
	public void setNuErroIntegracao(Long nuErroIntegracao) {
		this.nuErroIntegracao = nuErroIntegracao;
	}

	/**
	 * @return the descErroIntegracao
	 */
	public String getDescErroIntegracao() {
		return descErroIntegracao;
	}

	/**
	 * @param descErroIntegracao the descErroIntegracao to set
	 */
	public void setDescErroIntegracao(String descErroIntegracao) {
		this.descErroIntegracao = descErroIntegracao;
	}
	
	
	
}
