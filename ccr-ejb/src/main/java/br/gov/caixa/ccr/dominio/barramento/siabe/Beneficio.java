package br.gov.caixa.ccr.dominio.barramento.siabe;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="BENEFICIO")
public class Beneficio implements Serializable {

	private static final long serialVersionUID = -1513958138194851598L;

	private String numero;
	private String dv;
	
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
