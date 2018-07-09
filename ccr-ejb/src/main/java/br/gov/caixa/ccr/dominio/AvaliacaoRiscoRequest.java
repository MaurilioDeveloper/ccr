package br.gov.caixa.ccr.dominio;

import java.io.Serializable;

import br.gov.caixa.arqrefservices.dominio.siric.AvaliacaoValiada;

public class AvaliacaoRiscoRequest implements Serializable {

	private static final long serialVersionUID = -951661454351674582L;	
	
	AvaliacaoValiada avaliacao;

	public AvaliacaoValiada getAvaliacao() {
		return avaliacao;
	}

	public void setAvaliacao(AvaliacaoValiada avaliacao) {
		this.avaliacao = avaliacao;
	}
	
	
	
	
	
	
}

