package br.gov.caixa.ccr.dominio.barramento.simulacao;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="SIMULACAO_COM_SEGURO")
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class SimulacaoComSeguro implements Serializable {

	private static final long serialVersionUID = 1168627116616384487L;

	private String id;
	private String vencimentoContrato;
	private String prazo; 
	private String taxaJuros;
	private String prestacao;
	private String juroAcerto;
	private String IOF;
	private String valorSeguro;
	private String valorLiquido;
	private String valorContrato;
	private String vrCetMensal;
	private String vrCetAnual;
	private String pcTaxaSeguro;
	private String valorTotalContrato;
	private String CETJuroAcerto;
	private String CETIOF;
	private String CETSeguro;
	private String CETValorContrato;
	private String CETMensal;
	private String CETAnual;
	private boolean possuiResultado;
	private String taxaEfetivaMensal;
	private String taxaEfetivaAnual;
	
	
	public String getValorTotalContrato() {
		return valorTotalContrato;
	}

	public void setValorTotalContrato(String valorTotalContrato) {
		this.valorTotalContrato = valorTotalContrato;
	}

	@XmlElement(name="NUMERO_SIMULACAO")
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	@XmlElement(name="DTA_VENC_CONTRATO")
	public String getVencimentoContrato() {
		return vencimentoContrato;
	}
	
	public void setVencimentoContrato(String vencimentoContrato) {
		this.vencimentoContrato = vencimentoContrato;
	}

	@XmlElement(name="PRZ_CONTRATO")
	public String getPrazo() {
		return prazo;
	}
	
	public void setPrazo(String prazo) {
		this.prazo = prazo;
	}

	@XmlElement(name="TAXA_JUROS")
	public String getTaxaJuros() {
		return taxaJuros;
	}
	
	public void setTaxaJuros(String taxaJuros) {
		this.taxaJuros = taxaJuros;
	}

	@XmlElement(name="VALOR_PRESTACAO_SEG")
	public String getPrestacao() {
		return prestacao;
	}
	
	public void setPrestacao(String prestacao) {
		this.prestacao = prestacao;
	}

	@XmlElement(name="VALOR_LIQUIDO_SEG")
	public String getValorLiquido() {
		return valorLiquido;
	}
	
	public void setValorLiquido(String valorLiquido) {
		this.valorLiquido = valorLiquido;
	}

	@XmlElement(name="VALOR_JURO_ACERTO_SEG")
	public String getJuroAcerto() {
		return juroAcerto;
	}
	
	public void setJuroAcerto(String juroAcerto) {
		this.juroAcerto = juroAcerto;
	}

	@XmlElement(name="VALOR_IOF_SEG")
	public String getIOF() {
		return IOF;
	}
	
	public void setIOF(String iOF) {
		IOF = iOF;
	}

	@XmlElement(name="VALOR_SEG_PRESTAMISTA")
	public String getValorSeguro() {
		return valorSeguro;
	}
	
	public void setValorSeguro(String valorSeguro) {
		this.valorSeguro = valorSeguro;
	}

	@XmlElement(name="VALOR_CONTRATO_SEG")
	public String getValorContrato() {
		return valorContrato;
	}
	
	public void setValorContrato(String valorContrato) {
		this.valorContrato = valorContrato;
	}

	@XmlElement(name="PCT_CET_JURO_ACERTO_SEG")
	public String getCETJuroAcerto() {
		return CETJuroAcerto;
	}
	
	public void setCETJuroAcerto(String cETJuroAcerto) {
		CETJuroAcerto = cETJuroAcerto;
	}

	@XmlElement(name="PCT_CET_IOF_SEG")
	public String getCETIOF() {
		return CETIOF;
	}
	
	public void setCETIOF(String cETIOF) {
		CETIOF = cETIOF;
	}

	@XmlElement(name="PCT_CET_SEG_PRESTAMISTA")
	public String getCETSeguro() {
		return CETSeguro;
	}
	
	public void setCETSeguro(String cETSeguro) {
		CETSeguro = cETSeguro;
	}

	@XmlElement(name="PCT_CET_VALOR_CONTRATO_SEG")
	public String getCETValorContrato() {
		return CETValorContrato;
	}
	
	public void setCETValorContrato(String cETValorContrato) {
		CETValorContrato = cETValorContrato;
	}

	@XmlElement(name="PCT_CET_MENSAL_SEG")
	public String getCETMensal() {
		return CETMensal;
	}
	
	public void setCETMensal(String cETMensal) {
		CETMensal = cETMensal;
	}

	@XmlElement(name="PCT_CET_ANUAL_SEG")
	public String getCETAnual() {
		return CETAnual;
	}
	
	public void setCETAnual(String cETAnual) {
		CETAnual = cETAnual;
	}

	public boolean getPossuiResultado() {
		return possuiResultado;
	}

	public void setPossuiResultado(boolean possuiResultado) {
		this.possuiResultado = possuiResultado;
	}

	public String getTaxaEfetivaMensal() {
		return taxaEfetivaMensal;
	}

	public void setTaxaEfetivaMensal(String taxaEfetivaMensal) {
		this.taxaEfetivaMensal = taxaEfetivaMensal;
	}

	public String getTaxaEfetivaAnual() {
		return taxaEfetivaAnual;
	}

	public void setTaxaEfetivaAnual(String taxaEfetivaAnual) {
		this.taxaEfetivaAnual = taxaEfetivaAnual;
	}

	public String getVrCetMensal() {
		return vrCetMensal;
	}

	public void setVrCetMensal(String vrCetMensal) {
		this.vrCetMensal = vrCetMensal;
	}

	public String getVrCetAnual() {
		return vrCetAnual;
	}

	public void setVrCetAnual(String vrCetAnual) {
		this.vrCetAnual = vrCetAnual;
	}

	public String getPcTaxaSeguro() {
		return pcTaxaSeguro;
	}

	public void setPcTaxaSeguro(String pcTaxaSeguro) {
		this.pcTaxaSeguro = pcTaxaSeguro;
	}	
	
	
	
	
	
}
