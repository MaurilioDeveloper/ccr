package br.gov.caixa.ccr.dominio;

import java.util.Date;

public class AutorizacaoContrato extends Retorno {

	private String descSituacao;
	private Date dtSituacao;
	private String usuario;
	private String tipoAutorizacao;
	private String codigoSituacao;
	private Long nuSituacaoContrato;
	
	/**
	 * @return the descSituacao
	 */
	public String getDescSituacao() {
		return descSituacao;
	}
	/**
	 * 
	 * @param descSituacao the descSituacao to set
	 */
	public void setDescSituacao(String descSituacao) {
		this.descSituacao = descSituacao;
	}
	/**
	 * @return dtSituacao
	 */
	public Date getDtSituacao() {
		return dtSituacao;
	}
	/**
	 * @param dtSituacao the dtSituacao to set
	 */
	public void setDtSituacao(Date dtSituacao) {
		this.dtSituacao = dtSituacao;
	}
	/**
	 * @return usuario
	 */
	public String getUsuario() {
		return usuario;
	}
	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	/**
	 * @return tipoAutorizacao
	 */
	public String getTipoAutorizacao() {
		return tipoAutorizacao;
	}
	/**
	 * @param tipoAutorizacao the tipoAutorizacao to set
	 */
	public void setTipoAutorizacao(String tipoAutorizacao) {
		this.tipoAutorizacao = tipoAutorizacao;
	}
	/**
	 * @return tipoAutorizacao
	 */
	public String getCodigoSituacao() {
		return codigoSituacao;
	}
	/**
	 * @param tipoAutorizacao the tipoAutorizacao to set
	 */
	public void setCodigoSituacao(String codigoSituacao) {
		this.codigoSituacao = codigoSituacao;
	}
	/**
	 * @param the nuSituacaoContrato
	 */
	public Long getNuSituacaoContrato() {
		return nuSituacaoContrato;
	}
	/**
	 * @param nuSituacaoContrato the NuSituacaoContrato to set
	 */
	public void setNuSituacaoContrato(Long nuSituacaoContrato) {
		this.nuSituacaoContrato = nuSituacaoContrato;
	}
	
	
	
}
