package br.gov.caixa.ccr.dominio.barramento.simulacao;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="SIMULACAO_SEM_SEGURO")
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class SimulacaoSemSeguro implements Serializable {

	private static final long serialVersionUID = 8605552520806688807L;

	private String id;
	private String vencimentoContrato;
	private String prazo; 
	private String taxaJuros;
	private String prestacao;
	private String valorLiquido;
	private String juroAcerto;
	private String IOF;
	private String valorContrato;
	private String CETJuroAcerto;
	private String CETIOF;
	private String CETValorContrato;
	private String CETMensal;
	private String CETAnual;
	private String valorTotalContrato;
	private String taxaEfetivaMensal;
	private String taxaEfetivaAnual;
	private String vrCetMensal;
	private String vrCetAnual;
	private String pcTaxaSeguro;
	
	
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

	@XmlElement(name="VALOR_PRESTACAO")
	public String getPrestacao() {
		return prestacao;
	}
	
	public void setPrestacao(String prestacao) {
		this.prestacao = prestacao;
	}

	@XmlElement(name="VALOR_LIQUIDO")
	public String getValorLiquido() {
		return valorLiquido;
	}
	
	public void setValorLiquido(String valorLiquido) {
		this.valorLiquido = valorLiquido;
	}

	@XmlElement(name="VALOR_JURO_ACERTO")
	public String getJuroAcerto() {
		return juroAcerto;
	}
	
	public void setJuroAcerto(String juroAcerto) {
		this.juroAcerto = juroAcerto;
	}

	@XmlElement(name="VALOR_IOF")
	public String getIOF() {
		return IOF;
	}
	
	public void setIOF(String iOF) {
		IOF = iOF;
	}

	@XmlElement(name="VALOR_CONTRATO")
	public String getValorContrato() {
		return valorContrato;
	}
	
	public void setValorContrato(String valorContrato) {
		this.valorContrato = valorContrato;
	}

	@XmlElement(name="PCT_CET_JURO_ACERTO")
	public String getCETJuroAcerto() {
		return CETJuroAcerto;
	}
	
	public void setCETJuroAcerto(String cETJuroAcerto) {
		CETJuroAcerto = cETJuroAcerto;
	}

	@XmlElement(name="PCT_CET_IOF")
	public String getCETIOF() {
		return CETIOF;
	}
	
	public void setCETIOF(String cETIOF) {
		CETIOF = cETIOF;
	}

	@XmlElement(name="PCT_CET_VALOR_CONTRATO")
	public String getCETValorContrato() {
		return CETValorContrato;
	}
	
	public void setCETValorContrato(String cETValorContrato) {
		CETValorContrato = cETValorContrato;
	}

	@XmlElement(name="PCT_CET_MENSAL")
	public String getCETMensal() {
		return CETMensal;
	}
	
	public void setCETMensal(String cETMensal) {
		CETMensal = cETMensal;
	}

	@XmlElement(name="PCT_CET_ANUAL")
	public String getCETAnual() {
		return CETAnual;
	}
	
	public void setCETAnual(String cETAnual) {
		CETAnual = cETAnual;
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
