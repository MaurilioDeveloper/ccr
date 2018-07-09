package br.gov.caixa.ccr.dominio;

import java.util.Date;

public class SituacaoContrato extends Retorno{


	private Long id;
	private Long contrato;
	private Integer situacao;
	private Integer tipoSituacao;
	private Date data;
	private Date hora;
	private String usuario;
	private String situacaoDesc;
	private String deJustificativa;
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the contrato
	 */
	public Long getContrato() {
		return contrato;
	}
	/**
	 * @param contrato the contrato to set
	 */
	public void setContrato(Long contrato) {
		this.contrato = contrato;
	}
	/**
	 * @return the situacao
	 */
	public Integer getSituacao() {
		return situacao;
	}
	/**
	 * @param situacao the situacao to set
	 */
	public void setSituacao(Integer situacao) {
		this.situacao = situacao;
	}
	/**
	 * @return the tipoSituacao
	 */
	public Integer getTipoSituacao() {
		return tipoSituacao;
	}
	/**
	 * @param tipoSituacao the tipoSituacao to set
	 */
	public void setTipoSituacao(Integer tipoSituacao) {
		this.tipoSituacao = tipoSituacao;
	}
	/**
	 * @return the data
	 */
	public Date getData() {
		return data;
	}
	/**
	 * @param data the data to set
	 */
	public void setData(Date data) {
		this.data = data;
	}
	/**
	 * @return the hora
	 */
	public Date getHora() {
		return hora;
	}
	/**
	 * @param hora the hora to set
	 */
	public void setHora(Date hora) {
		this.hora = hora;
	}
	/**
	 * @return the usuario
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
	 * @return the situacaoDesc
	 */
	public String getSituacaoDesc() {
		return situacaoDesc;
	}
	/**
	 * @param situacaoDesc the situacaoDesc to set
	 */
	public void setSituacaoDesc(String situacaoDesc) {
		this.situacaoDesc = situacaoDesc;
	}
	/**
	 * @param the deJustificativa
	 */
	public String getDeJustificativa() {
		return deJustificativa;
	}
	/**
	 * @param deJustificativa the deJustificativa to set
	 */
	public void setDeJustificativa(String deJustificativa) {
		this.deJustificativa = deJustificativa;
	}
	
	
	

}
