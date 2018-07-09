package br.gov.caixa.ccr.dominio;

public class ContratoSituacaoRequest extends Retorno {
	private Long nuContrato;
	private Situacao situacao;
	private Long idAvaliacao;
	
	public Long getNuContrato() {
		return nuContrato;
	}
	public void setNuContrato(Long nuContrato) {
		this.nuContrato = nuContrato;
	}
	public Situacao getSituacao() {
		return situacao;
	}
	public void setSituacao(Situacao situacao) {
		this.situacao = situacao;
	}
	public Long getIdAvaliacao() {
		return idAvaliacao;
	}
	public void setIdAvaliacao(Long idAvaliacao) {
		this.idAvaliacao = idAvaliacao;
	}
}
