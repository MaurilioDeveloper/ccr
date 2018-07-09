package br.gov.caixa.ccr.dominio.barramento.siabe;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="TELEFONE")
public class Telefone implements Serializable {

	private static final long serialVersionUID = -2092868638370428070L;

	private String ddd;	
	private String numero;

	@XmlElement(name="DDD")
	public String getDdd() {
		return ddd;
	}

	public void setDdd(String ddd) {
		this.ddd = ddd;
	}

	@XmlElement(name="DDD")
	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}
	
}
