package br.gov.caixa.ccr.dominio.barramento.alteracaocliente;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


@XmlRootElement(name="DADOS")
@XmlSeeAlso({ CamposSicli.class })
@XmlType(propOrder={"numeroCocli", "camposSicli"})
public class EnvioDadosAlteracaoSicli implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -4953125299958532896L;


	private String numeroCocli;
	
	private CamposSicli camposSicli;
	
	
	public EnvioDadosAlteracaoSicli(){
		
	}

	@XmlElement(name="COCLI")
	public String getNumeroCocli() {
		return numeroCocli;
	}
	
	public void setNumeroCocli(String numeroCocli) {
		this.numeroCocli = numeroCocli;
	}
	
	@XmlElement(name="CAMPOS")
	public CamposSicli getCamposSicli() {
		return camposSicli;
	}

	public void setCamposSicli(CamposSicli camposSicli) {
		this.camposSicli = camposSicli;
	}

	
}
