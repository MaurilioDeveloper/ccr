package br.gov.caixa.ccr.dominio;

import java.math.BigDecimal;

/**
 * @author Sakamoto
 *
 */
public class ConvenioCanal extends Retorno {
	
	
	private Long idConvenio;	
	private Canal canal;	
	private String indAutorizaMargemContrato;

	private String indAutorizaMargemRenovacao;

	private String indExigeAutorizacaoContrato;

	private String indPermiteContratacao;

	private String indPermiteRenovacao;

	private String indTaxaJuroPadrao;

	private Integer przMaximoRenovacao;

	private Integer przMinimoAtrzaMrgmContrato;

	private Integer przMinimoAtrzaMrgmRenovacao;

	private Integer qtDiaMaximoSimulacaoFutura;

	private BigDecimal vlrMinimoExigeAutorizacao;
	
	private BigDecimal przMaximoContratacao;
	
	private String indFaixaJuroRenovacao;
	
	private String indExigeAnuencia;
	
	private String indFaixaJuroContratacao;
	
	private String qtdDiaGarantiaCondicao;
	
	private String przMinimoContratacao;
	
	private String przMinimoRenovacao;
	
	private Convenio convenio;
	
	private String indSituacaoConvenioCanal;
	
	public ConvenioCanal () {
		this.canal = new Canal();
	}
	
	public ConvenioCanal (Long codigo, String mensagem, String tipo) {
		super.setAll(codigo, mensagem, tipo);
	}
	
	public Long getIdConvenio() {
		return idConvenio;
	}

	public void setIdConvenio(Long idConvenio) {
		this.idConvenio = idConvenio;
	}

	public Canal getCanal() {
		return canal;
	}

	public void setCanal(Canal canal) {
		this.canal = canal;
	}

	public String getIndAutorizaMargemContrato() {
		return indAutorizaMargemContrato;
	}

	public void setIndAutorizaMargemContrato(String indAutorizaMargemContrato) {
		this.indAutorizaMargemContrato = indAutorizaMargemContrato;
	}

	public String getIndAutorizaMargemRenovacao() {
		return indAutorizaMargemRenovacao;
	}

	public void setIndAutorizaMargemRenovacao(String indAutorizaMargemRenovacao) {
		this.indAutorizaMargemRenovacao = indAutorizaMargemRenovacao;
	}

	public String getIndExigeAutorizacaoContrato() {
		return indExigeAutorizacaoContrato;
	}

	public void setIndExigeAutorizacaoContrato(String indExigeAutorizacaoContrato) {
		this.indExigeAutorizacaoContrato = indExigeAutorizacaoContrato;
	}

	public String getIndPermiteContratacao() {
		return indPermiteContratacao;
	}

	public void setIndPermiteContratacao(String indPermiteContratacao) {
		this.indPermiteContratacao = indPermiteContratacao;
	}

	public String getIndPermiteRenovacao() {
		return indPermiteRenovacao;
	}

	public void setIndPermiteRenovacao(String indPermiteRenovacao) {
		this.indPermiteRenovacao = indPermiteRenovacao;
	}

	public String getIndTaxaJuroPadrao() {
		return indTaxaJuroPadrao;
	}

	public void setIndTaxaJuroPadrao(String indTaxaJuroPadrao) {
		this.indTaxaJuroPadrao = indTaxaJuroPadrao;
	}

	public Integer getPrzMaximoRenovacao() {
		return przMaximoRenovacao;
	}

	public void setPrzMaximoRenovacao(Integer przMaximoRenovacao) {
		this.przMaximoRenovacao = przMaximoRenovacao;
	}

	public Integer getPrzMinimoAtrzaMrgmContrato() {
		return przMinimoAtrzaMrgmContrato;
	}

	public void setPrzMinimoAtrzaMrgmContrato(Integer przMinimoAtrzaMrgmContrato) {
		this.przMinimoAtrzaMrgmContrato = przMinimoAtrzaMrgmContrato;
	}

	public Integer getPrzMinimoAtrzaMrgmRenovacao() {
		return przMinimoAtrzaMrgmRenovacao;
	}

	public void setPrzMinimoAtrzaMrgmRenovacao(Integer przMinimoAtrzaMrgmRenovacao) {
		this.przMinimoAtrzaMrgmRenovacao = przMinimoAtrzaMrgmRenovacao;
	}

	public Integer getQtDiaMaximoSimulacaoFutura() {
		return qtDiaMaximoSimulacaoFutura;
	}

	public void setQtDiaMaximoSimulacaoFutura(Integer qtDiaMaximoSimulacaoFutura) {
		this.qtDiaMaximoSimulacaoFutura = qtDiaMaximoSimulacaoFutura;
	}

	public BigDecimal getVlrMinimoExigeAutorizacao() {
		return vlrMinimoExigeAutorizacao;
	}

	public void setVlrMinimoExigeAutorizacao(BigDecimal vlrMinimoExigeAutorizacao) {
		this.vlrMinimoExigeAutorizacao = vlrMinimoExigeAutorizacao;
	}

	public BigDecimal getPrzMaximoContratacao() {
		return przMaximoContratacao;
	}

	public void setPrzMaximoContratacao(BigDecimal przMaximoContratacao) {
		this.przMaximoContratacao = przMaximoContratacao;
	}

	public String getIndFaixaJuroRenovacao() {
		return indFaixaJuroRenovacao;
	}

	public void setIndFaixaJuroRenovacao(String indFaixaJuroRenovacao) {
		this.indFaixaJuroRenovacao = indFaixaJuroRenovacao;
	}

	public String getIndExigeAnuencia() {
		return indExigeAnuencia;
	}

	public void setIndExigeAnuencia(String indExigeAnuencia) {
		this.indExigeAnuencia = indExigeAnuencia;
	}

	public String getIndFaixaJuroContratacao() {
		return indFaixaJuroContratacao;
	}

	public void setIndFaixaJuroContratacao(String indFaixaJuroContratacao) {
		this.indFaixaJuroContratacao = indFaixaJuroContratacao;
	}

	public String getQtdDiaGarantiaCondicao() {
		return qtdDiaGarantiaCondicao;
	}

	public void setQtdDiaGarantiaCondicao(String qtdDiaGarantiaCondicao) {
		this.qtdDiaGarantiaCondicao = qtdDiaGarantiaCondicao;
	}

	public String getPrzMinimoContratacao() {
		return przMinimoContratacao;
	}

	public void setPrzMinimoContratacao(String przMinimoContratacao) {
		this.przMinimoContratacao = przMinimoContratacao;
	}

	public String getPrzMinimoRenovacao() {
		return przMinimoRenovacao;
	}

	public void setPrzMinimoRenovacao(String przMinimoRenovacao) {
		this.przMinimoRenovacao = przMinimoRenovacao;
	}

	/**
	 * @return the convenio
	 */
	public Convenio getConvenio() {
		return convenio;
	}

	/**
	 * @param convenio the convenio to set
	 */
	public void setConvenio(Convenio convenio) {
		this.convenio = convenio;
	}

	public String getIndSituacaoConvenioCanal() {
		return indSituacaoConvenioCanal;
	}

	public void setIndSituacaoConvenioCanal(String indSituacaoConvenioCanal) {
		this.indSituacaoConvenioCanal = indSituacaoConvenioCanal;
	}
	
}
