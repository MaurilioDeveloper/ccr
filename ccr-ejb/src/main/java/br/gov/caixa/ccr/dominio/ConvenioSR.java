package br.gov.caixa.ccr.dominio;

import java.util.Date;

public class ConvenioSR {
	
	private Integer numConvenio;
	private String unidade;
	private Integer numNatural;
	private Date dtInclusaoSR;
	private Date dtExclusaoSR;
	private Long codUsuarioInclusao;
	private Long codUsuarioExclusao;
	
	public Integer getNumConvenio() {
		return numConvenio;
	}
	public void setNumConvenio(Integer numConvenio) {
		this.numConvenio = numConvenio;
	}
	public String getUnidade() {
		return unidade;
	}
	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}
	public Integer getNumNatural() {
		return numNatural;
	}
	public void setNumNatural(Integer numNatural) {
		this.numNatural = numNatural;
	}
	public Long getCodUsuarioInclusao() {
		return codUsuarioInclusao;
	}
	public void setCodUsuarioInclusao(Long codUsuarioInclusao) {
		this.codUsuarioInclusao = codUsuarioInclusao;
	}
	public Long getCodUsuarioExclusao() {
		return codUsuarioExclusao;
	}
	public void setCodUsuarioExclusao(Long codUsuarioExclusao) {
		this.codUsuarioExclusao = codUsuarioExclusao;
	}
	public Date getDtInclusaoSR() {
		return dtInclusaoSR;
	}
	public void setDtInclusaoSR(Date dtInclusaoSR) {
		this.dtInclusaoSR = dtInclusaoSR;
	}
	public Date getDtExclusaoSR() {
		return dtExclusaoSR;
	}
	public void setDtExclusaoSR(Date dtExclusaoSR) {
		this.dtExclusaoSR = dtExclusaoSR;
	}
	
	
	
	
}
