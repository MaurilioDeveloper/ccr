package br.gov.caixa.ccr.dominio;

import java.util.Date;

import br.gov.caixa.ccr.dominio.transicao.CanalAtendimentoTO;
import br.gov.caixa.ccr.dominio.transicao.ContratoTO;
import br.gov.caixa.ccr.dominio.transicao.ConvenioTO;

public class Auditoria extends Retorno {
	
	
	
	private Date dhTransacaoAuditada;
	private String coUsuarioAuditado;
	private Long nuTransacaoAuditada;
	private String icTipoAcao;
	private String noCampoAuditado;
	private String deValorAnterior;
	private String deValorAtual;
	private ConvenioTO nuConvenio;
	private ContratoTO nuContrato;
	private CanalAtendimentoTO nuCanal;
	private String convenente;
	private Long campoId;
	private String transacaoAuditada;
	/**
	 * @return the dhTransacaoAuditada
	 */
	public Date getDhTransacaoAuditada() {
		return dhTransacaoAuditada;
	}
	/**
	 * @param dhTransacaoAuditada the dhTransacaoAuditada to set
	 */
	public void setDhTransacaoAuditada(Date dhTransacaoAuditada) {
		this.dhTransacaoAuditada = dhTransacaoAuditada;
	}
	/**
	 * @return the coUsuarioAuditado
	 */
	public String getCoUsuarioAuditado() {
		return coUsuarioAuditado;
	}
	/**
	 * @param coUsuarioAuditado the coUsuarioAuditado to set
	 */
	public void setCoUsuarioAuditado(String coUsuarioAuditado) {
		this.coUsuarioAuditado = coUsuarioAuditado;
	}
	/**
	 * @return the nuTransacaoAuditada
	 */
	public Long getNuTransacaoAuditada() {
		return nuTransacaoAuditada;
	}
	/**
	 * @param nuTransacaoAuditada the nuTransacaoAuditada to set
	 */
	public void setNuTransacaoAuditada(Long nuTransacaoAuditada) {
		this.nuTransacaoAuditada = nuTransacaoAuditada;
	}
	/**
	 * @return the icTipoAcao
	 */
	public String getIcTipoAcao() {
		return icTipoAcao;
	}
	/**
	 * @param icTipoAcao the icTipoAcao to set
	 */
	public void setIcTipoAcao(String icTipoAcao) {
		this.icTipoAcao = icTipoAcao;
	}
	/**
	 * @return the noCampoAuditado
	 */
	public String getNoCampoAuditado() {
		return noCampoAuditado;
	}
	/**
	 * @param noCampoAuditado the noCampoAuditado to set
	 */
	public void setNoCampoAuditado(String noCampoAuditado) {
		this.noCampoAuditado = noCampoAuditado;
	}
	/**
	 * @return the deValorAnterior
	 */
	public String getDeValorAnterior() {
		return deValorAnterior;
	}
	/**
	 * @param deValorAnterior the deValorAnterior to set
	 */
	public void setDeValorAnterior(String deValorAnterior) {
		this.deValorAnterior = deValorAnterior;
	}
	/**
	 * @return the deValorAtual
	 */
	public String getDeValorAtual() {
		return deValorAtual;
	}
	/**
	 * @param deValorAtual the deValorAtual to set
	 */
	public void setDeValorAtual(String deValorAtual) {
		this.deValorAtual = deValorAtual;
	}
	/**
	 * @return the convenente
	 */
	public String getConvenente() {
		return convenente;
	}
	/**
	 * @param convenente the convenente to set
	 */
	public void setConvenente(String convenente) {
		this.convenente = convenente;
	}
	/**
	 * @return the nuConvenio
	 */
	public ConvenioTO getNuConvenio() {
		return nuConvenio;
	}
	/**
	 * @param nuConvenio the nuConvenio to set
	 */
	public void setNuConvenio(ConvenioTO nuConvenio) {
		this.nuConvenio = nuConvenio;
	}
	/**
	 * @return the nuContrato
	 */
	public ContratoTO getNuContrato() {
		return nuContrato;
	}
	/**
	 * @param nuContrato the nuContrato to set
	 */
	public void setNuContrato(ContratoTO nuContrato) {
		this.nuContrato = nuContrato;
	}
	/**
	 * @return the nuCanal
	 */
	public CanalAtendimentoTO getNuCanal() {
		return nuCanal;
	}
	/**
	 * @param nuCanal the nuCanal to set
	 */
	public void setNuCanal(CanalAtendimentoTO nuCanal) {
		this.nuCanal = nuCanal;
	}
	/**
	 * @return the campoId
	 */
	public Long getCampoId() {
		return campoId;
	}
	/**
	 * @param campoId the campoId to set
	 */
	public void setCampoId(Long campoId) {
		this.campoId = campoId;
	}
	/**
	 * @return the transacaoAuditada
	 */
	public String getTransacaoAuditada() {
		return transacaoAuditada;
	}
	/**
	 * @param transacaoAuditada the transacaoAuditada to set
	 */
	public void setTransacaoAuditada(String transacaoAuditada) {
		this.transacaoAuditada = transacaoAuditada;
	}
	
	
}
