package br.gov.caixa.ccr.dominio;

import java.io.Serializable;

import br.gov.caixa.arqrefservices.dominio.sicli.CamposRetornados;

public class ConsultaAvaliacaoRequest implements Serializable {
	
	private static final long serialVersionUID = -9119363963268558997L;

	CamposRetornados dadosSicli;
	
	public CamposRetornados getDadosSicli() {
		return dadosSicli;
	}
	
	public void setDadosSicli(CamposRetornados dadosSicli) {
		this.dadosSicli = dadosSicli;
	}
}

