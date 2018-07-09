package br.gov.caixa.ccr.dominio.barramento.siric;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="CLIENTE")
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class Cliente implements Serializable {

	private static final long serialVersionUID = -729002091152634937L;

	private String tipo;	
	private String cpf;	
	private String cnpj;

	@XmlElement(name = "TP_PESSOA")
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	@XmlElement(name = "CPF")
	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	@XmlElement(name = "CNPJ")
	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	
}
