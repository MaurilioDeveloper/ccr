package br.gov.caixa.ccr.dominio;

import java.io.Serializable;
import br.gov.caixa.arqrefservices.dominio.sicli.Renda;
import br.gov.caixa.arqrefservices.dominio.sicli.CamposRetornados;
import br.gov.caixa.arqrefservices.dominio.siric.DadosOperacao;

public class SolicitaAvaliacaoOperacaoRequest implements Serializable {
	
	private static final long serialVersionUID = -9119363963268558997L;

	private CamposRetornados dadosSicli;
	private DadosOperacao dadosOperacao;
	private Renda renda;
	
	public CamposRetornados getDadosSicli() {
		return dadosSicli;
	}
	
	public void setDadosSicli(CamposRetornados dadosSicli) {
		this.dadosSicli = dadosSicli;
	}

	public DadosOperacao getDadosOperacao() {
		return dadosOperacao;
	}

	public void setDadosOperacao(DadosOperacao dadosOperacao) {
		this.dadosOperacao = dadosOperacao;
	}

	public Renda getRenda() {
		return renda;
	}

	public void setRenda(Renda renda) {
		this.renda = renda;
	}
	
	
}

