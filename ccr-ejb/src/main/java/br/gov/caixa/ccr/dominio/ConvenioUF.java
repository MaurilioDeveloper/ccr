package br.gov.caixa.ccr.dominio;

import java.util.Date;

public class ConvenioUF {
	
	private Integer numConvenio;
	private String sgUF;
	private Date dtInclusaoUF;
	private Date dtExclusaoUF;
	private Long codUsuarioInclusao;
	private Long codUsuarioExclusao;
	
	public Integer getNumConvenio() {
		return numConvenio;
	}
	public void setNumConvenio(Integer numConvenio) {
		this.numConvenio = numConvenio;
	}
	public String getSgUF() {
		return sgUF;
	}
	public void setSgUF(String sgUF) {
		this.sgUF = sgUF;
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
	public Date getDtInclusaoUF() {
		return dtInclusaoUF;
	}
	public void setDtInclusaoUF(Date dtInclusaoUF) {
		this.dtInclusaoUF = dtInclusaoUF;
	}
	public Date getDtExclusaoUF() {
		return dtExclusaoUF;
	}
	public void setDtExclusaoUF(Date dtExclusaoUF) {
		this.dtExclusaoUF = dtExclusaoUF;
	}
	
	

}
