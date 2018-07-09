package br.gov.caixa.ccr.dominio.apx;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Classe de dominio
 * @author c110503
 * 
 * 
 *
 */
@XmlRootElement
public class ContratoConsignado implements Serializable{	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1825995964236718280L;
	
	
	private String codigoContrato;
	
	private String dataPrevisaoLiberacao;
	
	private String dataBaseCalcPrimeiraPrestacao;
	
	private String dataVenciamentoPrimeitaPrestacao;
	
	private String dataVencimento;
	
	private String taxaJuros;
	
	private String valorContrato;
	
	private String cetValorContrato;
	
	private String valorIof;
	
	private String cetIof;
	
	private String valorJurosAcerto;
	
	private String cetJurosAcerto;
	
	private String quantidadeDiasJurosAcerto;
	
	private String valorBasePrimeiraPrestacao;
	
	private String valorLiquido;
	
	private String valorPrestacao;
	
	private String cetMensal;
	
	private String cetAnual;
	
	private String prazoContrato;
	
	private String valorMaximoPrestacao;
	
	private String prazoMaximoPermitido;
	
	private String dataRenovacao;
	
	private String quantidadeRenovacoes;
	
	private String indicativoExigiAgendamento;
	
	private String dataMinimaAgendamento;
	
	private String dataLiberacao;
	
	private String prazoRemanescente;
	
	private String indicativoRenovacao;
	
	private Convenente convenente;	
	
	private String valorIofTotal;
	
	//Usado somente quanto jah eh um contrato
	private String contaCredito;
	
	private String valorSaldoDevedor;
	
	private String mensagemInformativa;
	
	private String indicativoSeguroPrestamista;
	
	private String taxaJurosAnual;
	
	//Usado somente quanto jah eh um contrato
	private String contaDebito;
	
	private String indicativoCorrespondenteBancario;
	
	private CorrespondenteBancario correspondenteBancario;
	
	private String indicativoTipoSimulacao;	
	
	private SeguroPrestamista seguroPrestamista;
	
	private Double cetIofComplementar;
	
	private Double valorIofComplementar;

	
	private String valorBrutoRenovacao;

	@XmlAttribute(name="NU-CONTRATO")
	public String getCodigoContrato() {
		return codigoContrato;
	}
	public void setCodigoContrato(String codigoContrato) {
		this.codigoContrato = codigoContrato;
	}
	
	@XmlAttribute(name="DT-PREV-LIBERACAO")
	public String getDataPrevisaoLiberacao() {
		return dataPrevisaoLiberacao;
	}

	public void setDataPrevisaoLiberacao(String dataPrevisaoLiberacao) {
		this.dataPrevisaoLiberacao = dataPrevisaoLiberacao;
	}

	@XmlAttribute(name="DT-BASE-CAL-1A-PREST")
	public String getDataBaseCalcPrimeiraPrestacao() {
		return dataBaseCalcPrimeiraPrestacao;
	}

	public void setDataBaseCalcPrimeiraPrestacao(
			String dataBaseCalcPrimeiraPrestacao) {
		this.dataBaseCalcPrimeiraPrestacao = dataBaseCalcPrimeiraPrestacao;
	}

	@XmlAttribute(name="DT-VENC-1A-PREST")
	public String getDataVenciamentoPrimeitaPrestacao() {
		return dataVenciamentoPrimeitaPrestacao;
	}

	public void setDataVenciamentoPrimeitaPrestacao(
			String dataVenciamentoPrimeitaPrestacao) {
		this.dataVenciamentoPrimeitaPrestacao = dataVenciamentoPrimeitaPrestacao;
	}

	@XmlAttribute(name="DT-VENCIMENTO")
	public String getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(String dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	@XmlAttribute(name="PR-TX-JUROS")
	public String getTaxaJuros() {
		return taxaJuros;
	}

	public void setTaxaJuros(String taxaJuros) {
		this.taxaJuros = taxaJuros;
	}

	@XmlAttribute(name="VR-CONTRATO")
	public String getValorContrato() {
		return valorContrato;
	}

	public void setValorContrato(String valorContrato) {
		this.valorContrato = valorContrato;
	}

	@XmlAttribute(name="PR-CET-VAL-CONTRATO")
	public String getCetValorContrato() {
		return cetValorContrato;
	}

	public void setCetValorContrato(String cetValorContrato) {
		this.cetValorContrato = cetValorContrato;
	}

	@XmlAttribute(name="VR-IOF")
	public String getValorIof() {
		return valorIof;
	}

	public void setValorIof(String valorIof) {
		this.valorIof = valorIof;
	}

	@XmlAttribute(name="PR-CET-IOF")
	public String getCetIof() {
		return cetIof;
	}

	public void setCetIof(String cetIof) {
		this.cetIof = cetIof;
	}

	@XmlAttribute(name="VR-JURO-ACERTO")
	public String getValorJurosAcerto() {
		return valorJurosAcerto;
	}

	public void setValorJurosAcerto(String valorJurosAcerto) {
		this.valorJurosAcerto = valorJurosAcerto;
	}

	@XmlAttribute(name="PR-CET-JURO-ACERTO")
	public String getCetJurosAcerto() {
		return cetJurosAcerto;
	}

	public void setCetJurosAcerto(String cetJurosAcerto) {
		this.cetJurosAcerto = cetJurosAcerto;
	}

	@XmlAttribute(name="QT-DIAS-JUROS-ACERTO")
	public String getQuantidadeDiasJurosAcerto() {
		return quantidadeDiasJurosAcerto;
	}

	public void setQuantidadeDiasJurosAcerto(String quantidadeDiasJurosAcerto) {
		this.quantidadeDiasJurosAcerto = quantidadeDiasJurosAcerto;
	}

	@XmlAttribute(name="VR-BASE-1A-PRESTACAO")
	public String getValorBasePrimeiraPrestacao() {
		return valorBasePrimeiraPrestacao;
	}

	public void setValorBasePrimeiraPrestacao(String valorBasePrimeiraPrestacao) {
		this.valorBasePrimeiraPrestacao = valorBasePrimeiraPrestacao;
	}

	@XmlAttribute(name="VR-LIQUIDO")
	public String getValorLiquido() {
		return valorLiquido;
	}

	public void setValorLiquido(String valorLiquido) {
		this.valorLiquido = valorLiquido;
	}

	@XmlAttribute(name="VR-PRESTACAO")
	public String getValorPrestacao() {
		return valorPrestacao;
	}

	public void setValorPrestacao(String valorPrestacao) {
		this.valorPrestacao = valorPrestacao;
	}

	@XmlAttribute(name="PR-CET-MENSAL")
	public String getCetMensal() {
		return cetMensal;
	}

	public void setCetMensal(String cetMensal) {
		this.cetMensal = cetMensal;
	}

	@XmlAttribute(name="PR-CET-ANUAL")
	public String getCetAnual() {
		return cetAnual;
	}

	public void setCetAnual(String cetAnual) {
		this.cetAnual = cetAnual;
	}

	@XmlAttribute(name="PZ-CONTRATO")
	public String getPrazoContrato() {
		return prazoContrato;
	}

	public void setPrazoContrato(String prazoContrato) {
		this.prazoContrato = prazoContrato;
	}

	@XmlAttribute(name="VR-MAX-PRESTACAO")
	public String getValorMaximoPrestacao() {
		return valorMaximoPrestacao;
	}

	public void setValorMaximoPrestacao(String valorMaximoPrestacao) {
		this.valorMaximoPrestacao = valorMaximoPrestacao;
	}

	@XmlAttribute(name="PZ-MAX-PERMITIDO")
	public String getPrazoMaximoPermitido() {
		return prazoMaximoPermitido;
	}

	public void setPrazoMaximoPermitido(String prazoMaximoPermitido) {
		this.prazoMaximoPermitido = prazoMaximoPermitido;
	}
	
	@XmlAttribute(name="DT-RENOVACAO")
	public String getDataRenovacao() {
		return dataRenovacao;
	}
	public void setDataRenovacao(String dataRenovacao) {
		this.dataRenovacao = dataRenovacao;
	}
	
	@XmlAttribute(name="QT-RENOVACOES")
	public String getQuantidadeRenovacoes() {
		return quantidadeRenovacoes;
	}
	public void setQuantidadeRenovacoes(String quantidadeRenovacoes) {
		this.quantidadeRenovacoes = quantidadeRenovacoes;
	}	

	@XmlAttribute(name="IC-EXIGE-AGENDAMENTO")
	public String getIndicativoExigiAgendamento() {
		return indicativoExigiAgendamento;
	}

	public void setIndicativoExigiAgendamento(String indicativoExigiAgendamento) {
		this.indicativoExigiAgendamento = indicativoExigiAgendamento;
	}

	@XmlAttribute(name="DT-MINIMA-AGENDAMENTO")
	public String getDataMinimaAgendamento() {
		return dataMinimaAgendamento;
	}

	public void setDataMinimaAgendamento(String dataMinimaAgendamento) {
		this.dataMinimaAgendamento = dataMinimaAgendamento;
	}

	@XmlAttribute(name="DT-LIBERACAO")
	public String getDataLiberacao() {
		return dataLiberacao;
	}

	public void setDataLiberacao(String dataLiberacao) {
		this.dataLiberacao = dataLiberacao;
	}

	@XmlAttribute(name="PZ-REMANESCENTE")
	public String getPrazoRemanescente() {
		return prazoRemanescente;
	}

	public void setPrazoRemanescente(String prazoRemanescente) {
		this.prazoRemanescente = prazoRemanescente;
	}

	@XmlAttribute(name="IC-RENOVACAO")
	public String getIndicativoRenovacao() {
		return indicativoRenovacao;
	}

	public void setIndicativoRenovacao(String indicativoRenovacao) {
		this.indicativoRenovacao = indicativoRenovacao;
	}
	
	@XmlElement(name="CONVENENTE")
	public Convenente getConvenente() {
		return convenente;
	}
	public void setConvenente(Convenente convenente) {
		this.convenente = convenente;
	}
	
	@XmlAttribute(name="VR-IOF-TOTAL")
	public String getValorIofTotal() {
		return valorIofTotal;
	}
	public void setValorIofTotal(String valorIofTotal) {
		this.valorIofTotal = valorIofTotal;
	}
	
	@XmlAttribute(name="NU-CONTA-CREDITO")
	public String getContaCredito() {
		return contaCredito;
	}
	public void setContaCredito(String contaCredito) {
		this.contaCredito = contaCredito;
	}	
	
	@XmlAttribute(name="VR-SALDO-DEVEDOR")
	public String getValorSaldoDevedor() {
		return valorSaldoDevedor;
	}
	public void setValorSaldoDevedor(String valorSaldoDevedor) {
		this.valorSaldoDevedor = valorSaldoDevedor;
	}	
	
	@XmlAttribute(name="MSG-INFORMATIVA")
	public String getMensagemInformativa() {
		return mensagemInformativa;
	}
	public void setMensagemInformativa(String mensagemInformativa) {
		this.mensagemInformativa = mensagemInformativa;
	}
	
	@XmlAttribute(name="IC-SEGURO-PRESTAMISTA")
	public String getIndicativoSeguroPrestamista() {
		return indicativoSeguroPrestamista;
	}
	public void setIndicativoSeguroPrestamista(String indicativoSeguroPrestamista) {
		this.indicativoSeguroPrestamista = indicativoSeguroPrestamista;
	}	
	
	@XmlAttribute(name="PR-TX-JUROS-ANUAL")
	public String getTaxaJurosAnual() {
		return taxaJurosAnual;
	}
	public void setTaxaJurosAnual(String taxaJurosAnual) {
		this.taxaJurosAnual = taxaJurosAnual;
	}	
	
	@XmlAttribute(name="NU-CONTA-DEBITO")
	public String getContaDebito() {
		return contaDebito;
	}
	public void setContaDebito(String contaDebito) {
		this.contaDebito = contaDebito;
	}
	
	@XmlAttribute(name = "IC-COR-BANCARIO")
	public String getIndicativoCorrespondenteBancario() {
		return indicativoCorrespondenteBancario;
	}
	public void setIndicativoCorrespondenteBancario(String indicativoCorrespondenteBancario) {
		this.indicativoCorrespondenteBancario = indicativoCorrespondenteBancario;
	}
	
	@XmlElement(name = "CORRESPONDENTE")
	public CorrespondenteBancario getCorrespondenteBancario() {
		return correspondenteBancario;
	}
	public void setCorrespondenteBancario(CorrespondenteBancario correspondenteBancario) {
		this.correspondenteBancario = correspondenteBancario;
	}
	
	@XmlElement(name = "SEGURO-PRESTAMISTA")
	public SeguroPrestamista getSeguroPrestamista() {
		return seguroPrestamista;
	}
	public void setSeguroPrestamista(SeguroPrestamista seguroPrestamista) {
		this.seguroPrestamista = seguroPrestamista;
	}
	
	@XmlAttribute(name = "IC-TP-SIMULACAO")
	public String getIndicativoTipoSimulacao() {
		return indicativoTipoSimulacao;
	}
	
	public void setIndicativoTipoSimulacao(String indicativoTipoSimulacao) {
		this.indicativoTipoSimulacao = indicativoTipoSimulacao;
	}
	
	@XmlAttribute(name = "PR-CET-IOF-COMPLEMENTAR")
	public Double getCetIofComplementar() {
		return cetIofComplementar;
	}
	
	public void setCetIofComplementar(Double cetIofComplementar) {
		this.cetIofComplementar = cetIofComplementar;
	}
	
	@XmlAttribute(name = "VR-IOF-COMPLEMENTAR")
	public Double getValorIofComplementar() {
		return valorIofComplementar;
	}
	
	public void setValorIofComplementar(Double valorIofComplementar) {
		this.valorIofComplementar = valorIofComplementar;
	}
	
	@Override
	public String toString() {
		return "ContratoConsignado [codigoContrato=" + codigoContrato
				+ ", dataPrevisaoLiberacao=" + dataPrevisaoLiberacao
				+ ", dataBaseCalcPrimeiraPrestacao="
				+ dataBaseCalcPrimeiraPrestacao
				+ ", dataVenciamentoPrimeitaPrestacao="
				+ dataVenciamentoPrimeitaPrestacao + ", dataVencimento="
				+ dataVencimento + ", taxaJuros=" + taxaJuros
				+ ", valorContrato=" + valorContrato + ", cetValorContrato="
				+ cetValorContrato + ", valorIof=" + valorIof + ", cetIof="
				+ cetIof + ", valorJurosAcerto=" + valorJurosAcerto
				+ ", cetJurosAcerto=" + cetJurosAcerto
				+ ", quantidadeDiasJurosAcerto=" + quantidadeDiasJurosAcerto
				+ ", valorBasePrimeiraPrestacao=" + valorBasePrimeiraPrestacao
				+ ", valorLiquido=" + valorLiquido + ", valorPrestacao="
				+ valorPrestacao + ", cetMensal=" + cetMensal + ", cetAnual="
				+ cetAnual + ", prazoContrato=" + prazoContrato
				+ ", valorMaximoPrestacao=" + valorMaximoPrestacao
				+ ", prazoMaximoPermitido=" + prazoMaximoPermitido
				+ ", dataRenovacao=" + dataRenovacao
				+ ", quantidadeRenovacoes=" + quantidadeRenovacoes
				+ ", indicativoExigiAgendamento=" + indicativoExigiAgendamento
				+ ", dataMinimaAgendamento=" + dataMinimaAgendamento
				+ ", dataLiberacao=" + dataLiberacao + ", prazoRemanescente="
				+ prazoRemanescente + ", indicativoRenovacao="
				+ indicativoRenovacao + ", convenente=" + convenente
				+ ", valorIofTotal=" + valorIofTotal + ", contaCredito="
				+ contaCredito + ", valorSaldoDevedor=" + valorSaldoDevedor
				+ ", mensagemInformativa=" + mensagemInformativa
				+ ", indicativoSeguroPrestamista="
				+ indicativoSeguroPrestamista + ", taxaJurosAnual="
				+ taxaJurosAnual + ", contaDebito=" + contaDebito
				+ ", indicativoCorrespondenteBancario="
				+ indicativoCorrespondenteBancario
				+ ", correspondenteBancario=" + correspondenteBancario
				+ ", indicativoTipoSimulacao=" + indicativoTipoSimulacao
				+ ", seguroPrestamista=" + seguroPrestamista
				+ ", cetIofComplementar=" + cetIofComplementar
				+ ", valorIofComplementar=" + valorIofComplementar
				+ ", valorBrutoRenovacao=" + valorBrutoRenovacao + "]";
	}
	
	@XmlAttribute(name="VR-BRUTO-RENOVACAO")
	public String getValorBrutoRenovacao() {
		return valorBrutoRenovacao;
	}
	public void setValorBrutoRenovacao(String valorBrutoRenovacao) {
		this.valorBrutoRenovacao = valorBrutoRenovacao;
	}
	
	
	
	

	
}
