package br.gov.caixa.ccr.dominio.apx;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import br.gov.caixa.arqrefcore.cobol.conversor.anotacao.Tamanho;

/**
 * Classe de dominio
 * @author c110503
 *
 */
@XmlRootElement
public class Faixa  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5157155259573551950L;
	

	private String codigoFaixa;
	
	private String valorFaixa;

	@XmlAttribute(name="CO-FAIXA")
	public String getCodigoFaixa() {
		return codigoFaixa;
	}

	public void setCodigoFaixa(String codigoFaixa) {
		this.codigoFaixa = codigoFaixa;
	}

	@XmlAttribute(name="VR")
	public String getValorFaixa() {
		return valorFaixa;
	}

	public void setValorFaixa(String valorFaixa) {
		this.valorFaixa = valorFaixa;
	}

	@Override
	public String toString() {
		return "Faixa [codigoFaixa=" + codigoFaixa + ", valorFaixa="
				+ valorFaixa + "]";
	}

	
	
	
}
