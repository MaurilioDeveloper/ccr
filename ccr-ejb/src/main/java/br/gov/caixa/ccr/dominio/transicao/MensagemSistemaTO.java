package br.gov.caixa.ccr.dominio.transicao;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * The persistent class for the CCRTB048_MENSAGEM_SISTEMA database table.
 * 
 */
@Entity
@Table(name="CCRTB048_MENSAGEM_SISTEMA")
public class MensagemSistemaTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -443651163489171642L;

	@Id
	@Column(name="CO_MENSAGEM")
	private String cosMensagem;

	@Column(name="DE_MENSAGEM")
	private String descMensagem;

	public MensagemSistemaTO() {
	}

	public String getCosMensagem() {
		return this.cosMensagem;
	}

	public void setCosMensagem(String cosMensagem) {
		this.cosMensagem = cosMensagem;
	}

	public String getDescMensagem() {
		return this.descMensagem;
	}

	public void setDescMensagem(String descMensagem) {
		this.descMensagem = descMensagem;
	}

}