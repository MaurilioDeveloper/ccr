package br.gov.caixa.ccr.dominio.barramento.siric;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="AVALIACOES_VALIDAS")
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class AvaliacaoValidaEnvio implements Serializable {

	private static final long serialVersionUID = -956541454356574582L;	
	
	private String tipoPessoa;	
	private String cpf;

	public AvaliacaoValidaEnvio() {

	}
	
	public AvaliacaoValidaEnvio(String cpf) {
		this.tipoPessoa = "1";
		this.cpf = cpf;
	}
	
	@XmlElement(name="TP_PESSOA")
	public String getTipoPessoa() {
		return tipoPessoa;
	}

	public void setTipoPessoa(String tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}

	@XmlElement(name="CPF")
	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
}
