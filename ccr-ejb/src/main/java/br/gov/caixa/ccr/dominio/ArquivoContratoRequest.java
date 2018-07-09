package br.gov.caixa.ccr.dominio;

import java.io.Serializable;

public class ArquivoContratoRequest implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String identificador;
	private String dtInicioRemessa;
	private String dtFimRemessa;
	private int situacaoRetorno;
	private String nuContrato;
	
	public ArquivoContratoRequest() {
		
	}
	
	/**
	 * @return the identificador
	 */
	public String getIdentificador() {
		return identificador;
	}
	
	/**
	 * @param identificador the identificador to set
	 */
	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}
	
	/**
	 * @return the dtInicioRemessa
	 */
	public String getDtInicioRemessa() {
		return dtInicioRemessa;
	}
	
	/**
	 * @param dtInicioRemessa the dtInicioRemessa to set
	 */
	public void setDtInicioRemessa(String dtInicioRemessa) {
		this.dtInicioRemessa = dtInicioRemessa;
	}
	
	/**
	 * @return the dtFimRemessa
	 */
	public String getDtFimRemessa() {
		return dtFimRemessa;
	}
	
	/**
	 * @param dtFimRemessa the dtFimRemessa to set
	 */
	public void setDtFimRemessa(String dtFimRemessa) {
		this.dtFimRemessa = dtFimRemessa;
	}
	
	/**
	 * @return the situacaoRetorno
	 */
	public int getSituacaoRetorno() {
		return situacaoRetorno;
	}
	
	/**
	 * @param situacaoRetorno the situacaoRetorno to set
	 */
	public void setSituacaoRetorno(int situacaoRetorno) {
		this.situacaoRetorno = situacaoRetorno;
	}
	
	/**
	 * @return the nuContrato
	 */
	public String getNuContrato() {
		return nuContrato;
	}
	
	/**
	 * @param nuContrato the nuContrato to set
	 */
	public void setNuContrato(String nuContrato) {
		this.nuContrato = nuContrato;
	}

	public ArquivoContratoRequest(String identificador, String dtInicioRemessa, String dtFimRemessa,
			int situacaoRetorno, String nuContrato) {
		super();
		this.identificador = identificador;
		this.dtInicioRemessa = dtInicioRemessa;
		this.dtFimRemessa = dtFimRemessa;
		this.situacaoRetorno = situacaoRetorno;
		this.nuContrato = nuContrato;
	}
	
	
	
}
