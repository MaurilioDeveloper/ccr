package br.gov.caixa.ccr.dominio.barramento.alteracaocliente;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="ERRO")
public class ManutencaoErro implements Serializable {

	private static final long serialVersionUID = -9067562418174760448L;

	private String codigoErro;
	
	private String sqlCode;
	
	private String campoIPS;
	
	private String mensagemErro;

	@XmlElement(name="COD_ERRO")
	public String getCodigoErro() {
		return this.codigoErro;
	}

	@XmlElement(name="SQLCODE")
	public String getSqlCode() {
		return this.sqlCode;
	}

	@XmlElement(name="CAMPO_IPS")
	public String getCampoIPS() {
		return this.campoIPS;
	}

	@XmlElement(name="MENSAGEM_ERRO")
	public String getMensagemErro() {
		return this.mensagemErro;
	}

	public void setCodigoErro(String codigoErro) {
		this.codigoErro = codigoErro;
	}

	public void setSqlCode(String sqlCode) {
		this.sqlCode = sqlCode;
	}

	public void setCampoIPS(String campoIPS) {
		this.campoIPS = campoIPS;
	}

	public void setMensagemErro(String mensagemErro) {
		this.mensagemErro = mensagemErro;
	}
	
	
	
	
	
}
