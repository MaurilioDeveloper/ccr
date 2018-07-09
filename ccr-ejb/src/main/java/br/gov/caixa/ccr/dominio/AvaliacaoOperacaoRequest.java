package br.gov.caixa.ccr.dominio;

import java.io.Serializable;

import br.gov.caixa.arqrefservices.dominio.sicli.CamposRetornados;
import br.gov.caixa.arqrefservices.dominio.sicli.Renda;
import br.gov.caixa.arqrefservices.dominio.siric.solicitacao.DadosOperacaoCreditoConsignado;

public class AvaliacaoOperacaoRequest implements Serializable {

	private static final long serialVersionUID = -951661454351674582L;	
	
	CamposRetornados dadosSicli;
	
	Renda renda;
	
	DadosOperacaoCreditoConsignado dadosOperacao;
	
	public CamposRetornados getDadosSicli() {
		return dadosSicli;
	}
	public void setDadosSicli(CamposRetornados dadosSicli) {
		this.dadosSicli = dadosSicli;
	}
	public Renda getRenda() {
		return renda;
	}
	public void setRenda(Renda renda) {
		this.renda = renda;
	}
	public DadosOperacaoCreditoConsignado getDadosOperacao() {
		return dadosOperacao;
	}
	public void setDadosOperacao(DadosOperacaoCreditoConsignado dadosOperacao) {
		this.dadosOperacao = dadosOperacao;
	}
	
	
	
	
	
	
}

