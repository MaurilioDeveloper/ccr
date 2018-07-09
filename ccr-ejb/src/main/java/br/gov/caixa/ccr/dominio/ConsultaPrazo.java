package br.gov.caixa.ccr.dominio;

import java.util.ArrayList;
import java.util.List;

public class ConsultaPrazo extends Retorno {

	private List<TaxaJuro> listaTaxaJuro= new ArrayList<TaxaJuro>();
	
	private String faixaJuroPadrao;
	private String prazo;
	private String taxaJuro;


	public List<TaxaJuro> getListaTaxaJuro() {
		return listaTaxaJuro;
	}

	public void setListaTaxaJuro(List<TaxaJuro> listaTaxaJuro) {
		this.listaTaxaJuro = listaTaxaJuro;
	}

	public String getFaixaJuroPadrao() {
		return faixaJuroPadrao;
	}

	public void setFaixaJuroPadrao(String faixaJuroPadrao) {
		this.faixaJuroPadrao = faixaJuroPadrao;
	}

	public String getPrazo() {
		return prazo;
	}

	public void setPrazo(String prazo) {
		this.prazo = prazo;
	}

	public String getTaxaJuro() {
		return taxaJuro;
	}

	public void setTaxaJuro(String taxaJuro) {
		this.taxaJuro = taxaJuro;
	}
	
	
	
}
