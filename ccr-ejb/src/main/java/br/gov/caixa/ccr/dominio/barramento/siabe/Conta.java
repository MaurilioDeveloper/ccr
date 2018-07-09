package br.gov.caixa.ccr.dominio.barramento.siabe;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="CONTA")
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class Conta implements Serializable {

	private static final long serialVersionUID = -5562523664805354613L;
	
	private String agencia;	
	private String operacao;	
	private String numero;	
	private String dv;

	@XmlElement(name="AGENCIA")
	public String getAgencia() {
		return agencia;
	}

	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}

	@XmlElement(name="OPERACAO")
	public String getOperacao() {
		return operacao;
	}

	public void setOperacao(String operacao) {
		this.operacao = operacao;
	}

	@XmlElement(name="NUMERO")
	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	@XmlElement(name="DV")
	public String getDv() {
		return dv;
	}

	public void setDv(String dv) {
		this.dv = dv;
	}
	
}
