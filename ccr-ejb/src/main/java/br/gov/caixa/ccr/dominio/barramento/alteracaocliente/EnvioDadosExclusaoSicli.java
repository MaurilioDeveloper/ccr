package br.gov.caixa.ccr.dominio.barramento.alteracaocliente;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="DADOS")
@XmlSeeAlso({ CampoExclusaoRendaSicli.class })
@XmlType(propOrder={"numeroCocli", "campoExclusaoRendaSicli"})
public class EnvioDadosExclusaoSicli implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String numeroCocli;
	
	private CampoExclusaoRendaSicli campoExclusaoRendaSicli; 
	
	@XmlElement(name = "COCLI")
	public String getNumeroCocli() {
		return numeroCocli;
	}

	public void setNumeroCocli(String numeroCocli) {
		this.numeroCocli = numeroCocli;
	}

	@XmlElement(name = "DADOS")
	public CampoExclusaoRendaSicli getCampoExclusaoRendaSicli() {
		return campoExclusaoRendaSicli;
	}

	public void setCampoExclusaoRendaSicli(
			CampoExclusaoRendaSicli campoExclusaoRendaSicli) {
		this.campoExclusaoRendaSicli = campoExclusaoRendaSicli;
	}

}
