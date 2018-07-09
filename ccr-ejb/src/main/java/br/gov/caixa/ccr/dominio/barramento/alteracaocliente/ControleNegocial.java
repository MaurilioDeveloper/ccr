package br.gov.caixa.ccr.dominio.barramento.alteracaocliente;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="CONTROLE_NEGOCIAL")
public class ControleNegocial implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String origemRetorno;

	private String codigoRetorno;
	
	private String mensagem;

	@XmlElement(name = "ORIGEM_RETORNO")
	public String getOrigemRetorno() {
		return origemRetorno;
	}

	public void setOrigemRetorno(String origemRetorno) {
		this.origemRetorno = origemRetorno;
	}
	
	@XmlElement(name = "COD_RETORNO")
	public String getCodigoRetorno() {
		return codigoRetorno;
	}

	public void setCodigoRetorno(String codigoRetorno) {
		this.codigoRetorno = codigoRetorno;
	}

	@XmlElement(name = "MSG_RETORNO")
	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

}
