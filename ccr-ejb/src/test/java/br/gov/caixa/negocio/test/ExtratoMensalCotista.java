package br.gov.caixa.negocio.test;

import java.math.BigDecimal;

public class ExtratoMensalCotista {

	private String     nomeCotista;
	private String     nomefundoGarantidor;
	private String     docCotista;
	private String     cnpjFundo;
	private String     endCotista;
	private BigDecimal rentabilidadeMes;
	private BigDecimal rentabilidadeMesAnterior;
	private BigDecimal rentabilidadeUltimosDozeMeses;
	private BigDecimal rentabilidadeAno;
	private String     dataInicio;
	private String     dataFim;
	private BigDecimal vlCotaDataInicio;
	private BigDecimal vlCota;
	private BigDecimal integralizacoes;
	private BigDecimal honras;
	private BigDecimal saldoAnterior;
	private BigDecimal saldoFinal;
	
	
	
	public String getNomeCotista() {
		return nomeCotista;
	}
	public void setNomeCotista(String nomeCotista) {
		this.nomeCotista = nomeCotista;
	}
	public String getDocCotista() {
		return docCotista;
	}
	public void setDocCotista(String docCotista) {
		this.docCotista = docCotista;
	}
	public String getEndCotista() {
		return endCotista;
	}
	public void setEndCotista(String endCotista) {
		this.endCotista = endCotista;
	}
	public String getNomefundoGarantidor() {
		return nomefundoGarantidor;
	}
	public void setNomefundoGarantidor(String nomefundoGarantidor) {
		this.nomefundoGarantidor = nomefundoGarantidor;
	}
	public BigDecimal getRentabilidadeMes() {
		return rentabilidadeMes;
	}
	public void setRentabilidadeMes(BigDecimal rentabilidadeMes) {
		this.rentabilidadeMes = rentabilidadeMes;
	}
	public BigDecimal getRentabilidadeMesAnterior() {
		return rentabilidadeMesAnterior;
	}
	public void setRentabilidadeMesAnterior(BigDecimal rentabilidadeMesAnterior) {
		this.rentabilidadeMesAnterior = rentabilidadeMesAnterior;
	}
	public BigDecimal getRentabilidadeUltimosDozeMeses() {
		return rentabilidadeUltimosDozeMeses;
	}
	public void setRentabilidadeUltimosDozeMeses(BigDecimal rentabilidadeUltimosDozeMeses) {
		this.rentabilidadeUltimosDozeMeses = rentabilidadeUltimosDozeMeses;
	}
	public BigDecimal getRentabilidadeAno() {
		return rentabilidadeAno;
	}
	public void setRentabilidadeAno(BigDecimal rentabilidadeAno) {
		this.rentabilidadeAno = rentabilidadeAno;
	}
	public String getDataInicio() {
		return dataInicio;
	}
	public void setDataInicio(String dataInicio) {
		this.dataInicio = dataInicio;
	}
	public String getDataFim() {
		return dataFim;
	}
	public void setDataFim(String dataFim) {
		this.dataFim = dataFim;
	}
	public BigDecimal getVlCotaDataInicio() {
		return vlCotaDataInicio;
	}
	public void setVlCotaDataInicio(BigDecimal vlCotaDataInicio) {
		this.vlCotaDataInicio = vlCotaDataInicio;
	}
	public BigDecimal getVlCota() {
		return vlCota;
	}
	public void setVlCota(BigDecimal vlCota) {
		this.vlCota = vlCota;
	}
	public BigDecimal getIntegralizacoes() {
		return integralizacoes;
	}
	public void setIntegralizacoes(BigDecimal integralizacoes) {
		this.integralizacoes = integralizacoes;
	}
	public BigDecimal getHonras() {
		return honras;
	}
	public void setHonras(BigDecimal honras) {
		this.honras = honras;
	}
	public BigDecimal getSaldoAnterior() {
		return saldoAnterior;
	}
	public void setSaldoAnterior(BigDecimal saldoAnterior) {
		this.saldoAnterior = saldoAnterior;
	}
	public BigDecimal getSaldoFinal() {
		return saldoFinal;
	}
	public void setSaldoFinal(BigDecimal saldoFinal) {
		this.saldoFinal = saldoFinal;
	}
	public String getCnpjFundo() {
		return cnpjFundo;
	}
	public void setCnpjFundo(String cnpjFundo) {
		this.cnpjFundo = cnpjFundo;
	}
	
}
