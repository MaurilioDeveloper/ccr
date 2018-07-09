package br.gov.caixa.ccr.dominio;

import java.math.BigDecimal;
import java.util.Date;

public class Avaliacao extends Retorno {
	

	private long nuAvaliacao;
	private String coResultado;
	private String noResultado;
	private Date dtFimAvaliacao;
	private Date dtGeracao;
	private Date dtInicoAvaliacao;
	private String icAvaliacaoManual;
	private String icBloqueioAlcada;
	private String noProduto;
	private Long nuOperacao;
	private Long nuPrazoMxmoEmprestimo;
	private Long nuRatingAvaliacao;
	private BigDecimal vrMaximoEmprestimo;
	private BigDecimal vrMaximoPrestacao;
	/**
	 * @return the nuAvaliacao
	 */
	public long getNuAvaliacao() {
		return nuAvaliacao;
	}
	/**
	 * @param nuAvaliacao the nuAvaliacao to set
	 */
	public void setNuAvaliacao(long nuAvaliacao) {
		this.nuAvaliacao = nuAvaliacao;
	}
	/**
	 * @return the coResultado
	 */
	public String getCoResultado() {
		return coResultado;
	}
	/**
	 * @param coResultado the coResultado to set
	 */
	public void setCoResultado(String coResultado) {
		this.coResultado = coResultado;
	}
	/**
	 * @return the noResultado
	 */
	public String getNoResultado() {
		return noResultado;
	}
	/**
	 * @param noResultado the noResultado to set
	 */
	public void setNoResultado(String noResultado) {
		this.noResultado = noResultado;
	}
	/**
	 * @return the dtFimAvaliacao
	 */
	public Date getDtFimAvaliacao() {
		return dtFimAvaliacao;
	}
	/**
	 * @param dtFimAvaliacao the dtFimAvaliacao to set
	 */
	public void setDtFimAvaliacao(Date dtFimAvaliacao) {
		this.dtFimAvaliacao = dtFimAvaliacao;
	}
	/**
	 * @return the dtGeracao
	 */
	public Date getDtGeracao() {
		return dtGeracao;
	}
	/**
	 * @param dtGeracao the dtGeracao to set
	 */
	public void setDtGeracao(Date dtGeracao) {
		this.dtGeracao = dtGeracao;
	}
	/**
	 * @return the dtInicoAvaliacao
	 */
	public Date getDtInicoAvaliacao() {
		return dtInicoAvaliacao;
	}
	/**
	 * @param dtInicoAvaliacao the dtInicoAvaliacao to set
	 */
	public void setDtInicoAvaliacao(Date dtInicoAvaliacao) {
		this.dtInicoAvaliacao = dtInicoAvaliacao;
	}
	/**
	 * @return the icAvaliacaoManual
	 */
	public String getIcAvaliacaoManual() {
		return icAvaliacaoManual;
	}
	/**
	 * @param icAvaliacaoManual the icAvaliacaoManual to set
	 */
	public void setIcAvaliacaoManual(String icAvaliacaoManual) {
		this.icAvaliacaoManual = icAvaliacaoManual;
	}
	/**
	 * @return the icBloqueioAlcada
	 */
	public String getIcBloqueioAlcada() {
		return icBloqueioAlcada;
	}
	/**
	 * @param icBloqueioAlcada the icBloqueioAlcada to set
	 */
	public void setIcBloqueioAlcada(String icBloqueioAlcada) {
		this.icBloqueioAlcada = icBloqueioAlcada;
	}
	/**
	 * @return the noProduto
	 */
	public String getNoProduto() {
		return noProduto;
	}
	/**
	 * @param noProduto the noProduto to set
	 */
	public void setNoProduto(String noProduto) {
		this.noProduto = noProduto;
	}
	/**
	 * @return the nuOperacao
	 */
	public Long getNuOperacao() {
		return nuOperacao;
	}
	/**
	 * @param nuOperacao the nuOperacao to set
	 */
	public void setNuOperacao(Long nuOperacao) {
		this.nuOperacao = nuOperacao;
	}
	/**
	 * @return the nuPrazoMxmoEmprestimo
	 */
	public Long getNuPrazoMxmoEmprestimo() {
		return nuPrazoMxmoEmprestimo;
	}
	/**
	 * @param nuPrazoMxmoEmprestimo the nuPrazoMxmoEmprestimo to set
	 */
	public void setNuPrazoMxmoEmprestimo(Long nuPrazoMxmoEmprestimo) {
		this.nuPrazoMxmoEmprestimo = nuPrazoMxmoEmprestimo;
	}
	/**
	 * @return the nuRatingAvaliacao
	 */
	public Long getNuRatingAvaliacao() {
		return nuRatingAvaliacao;
	}
	/**
	 * @param nuRatingAvaliacao the nuRatingAvaliacao to set
	 */
	public void setNuRatingAvaliacao(Long nuRatingAvaliacao) {
		this.nuRatingAvaliacao = nuRatingAvaliacao;
	}
	/**
	 * @return the vrMaximoEmprestimo
	 */
	public BigDecimal getVrMaximoEmprestimo() {
		return vrMaximoEmprestimo;
	}
	/**
	 * @param vrMaximoEmprestimo the vrMaximoEmprestimo to set
	 */
	public void setVrMaximoEmprestimo(BigDecimal vrMaximoEmprestimo) {
		this.vrMaximoEmprestimo = vrMaximoEmprestimo;
	}
	/**
	 * @return the vrMaximoPrestacao
	 */
	public BigDecimal getVrMaximoPrestacao() {
		return vrMaximoPrestacao;
	}
	/**
	 * @param vrMaximoPrestacao the vrMaximoPrestacao to set
	 */
	public void setVrMaximoPrestacao(BigDecimal vrMaximoPrestacao) {
		this.vrMaximoPrestacao = vrMaximoPrestacao;
	}
}
