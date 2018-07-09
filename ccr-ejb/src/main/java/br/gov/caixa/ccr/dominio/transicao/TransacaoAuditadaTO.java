package br.gov.caixa.ccr.dominio.transicao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the CCRTB101_TRANSACAO_AUDITADA database table.
 * 
 */
@Entity
@Table(name="CCRTB101_TRANSACAO_AUDITADA")
public class TransacaoAuditadaTO {

	@Id
	@Column(name="NU_TRANSACAO_AUDITADA")
	private Long id;
	
	@Column(name="NO_TRANSACAO_AUDITADA")
	private String nome;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}
	
		
}
