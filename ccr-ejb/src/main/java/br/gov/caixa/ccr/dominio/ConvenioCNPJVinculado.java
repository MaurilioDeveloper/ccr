package br.gov.caixa.ccr.dominio;
//
import java.io.Serializable;


 
public class ConvenioCNPJVinculado implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nuConvenio;
	private String nuCNPJ;
	
	public String getNuConvenio() {
		return nuConvenio;
	}
	public void setNuConvenio(String nuConvenio) {
		this.nuConvenio = nuConvenio;
	}
	public String getNuCNPJ() {
		return nuCNPJ;
	}
	public void setNuCNPJ(String nuCNPJ) {
		this.nuCNPJ = nuCNPJ;
	}
	
	

}