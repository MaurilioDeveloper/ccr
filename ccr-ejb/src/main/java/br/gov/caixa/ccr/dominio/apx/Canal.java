package br.gov.caixa.ccr.dominio.apx;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import br.gov.caixa.arqrefcore.cobol.conversor.anotacao.Tamanho;

/**
 * Classe que representa as Canal 
 * Usada pelo APX
 * 
 * A ordem das propriedades do xml importa devido
 * ao parse no COBOL
 * 
 * @author c110503
 *
 */
@XmlRootElement
@XmlType(propOrder = { "codigoServico", "codigoCanal", "nomeCanal",
		"indicativoAgendamento", "indicativoContratacao",
		"indicativoOrgaoMilitar", "indicativoPagAero",
		"indicativoNumeroAdeAero", "indicativoCodigoLotacaoGovAL",
		"indicativoNumeroMatriculaGovAL", "indicativoNumeroAutorizacaoGovPR",
		"indicativoNumeroMatriculaSABESP", "indicativoNumeroMatriculaCVRD",
		"indicativoMatriculaInstitutoSERPRO",
		"indicativoTipoBeneficiarioSERPRO", "indicativoRecebeBeneficioConta",
		"indicativoNumeroBeneficio", "indicativoDiaBeneficio",
		"indicativoEstadoContaCreditoBeneficio",
		"indicativoCodigoOrgaoConvenio", })
public class Canal implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5628894837094739L;
	
	private String codigoServico;
	
	private String codigoCanal;
	
	private String nomeCanal;
	
	private String indicativoAgendamento;
	
	private String indicativoContratacao;
	
	private String indicativoOrgaoMilitar;
	
	private String indicativoPagAero;
	
	private String indicativoNumeroAdeAero;
	
	private String indicativoCodigoLotacaoGovAL;
	
	private String indicativoNumeroMatriculaGovAL;
	
	private String indicativoNumeroAutorizacaoGovPR;
	
	private String indicativoNumeroMatriculaSABESP;
	
	private String indicativoNumeroMatriculaCVRD;
	
	private String indicativoMatriculaInstitutoSERPRO;
	
	private String indicativoTipoBeneficiarioSERPRO;
	
	private String indicativoRecebeBeneficioConta;
	
	private String indicativoNumeroBeneficio;
	
	private String indicativoDiaBeneficio;
	
	private String indicativoEstadoContaCreditoBeneficio;
	
	private String indicativoCodigoOrgaoConvenio;
	
	@XmlAttribute(name = "CO-SERVICO")	
	public String getCodigoServico() {
		return codigoServico;
	}
	public void setCodigoServico(String codigoServico) {
		this.codigoServico = codigoServico;
	}
	
	@XmlAttribute(name = "CO-CANAL")
	public String getCodigoCanal() {
		return codigoCanal;
	}
	public void setCodigoCanal(String codigoCanal) {
		this.codigoCanal = codigoCanal;
	}
	
	@XmlAttribute(name = "NO-CANAL")
	public String getNomeCanal() {
		return nomeCanal;
	}
	public void setNomeCanal(String nomeCanal) {
		this.nomeCanal = nomeCanal;
	}
	
	@XmlAttribute(name = "IC-AGENDAMENTO")
	public String getIndicativoAgendamento() {
		return indicativoAgendamento;
	}
	public void setIndicativoAgendamento(String indicativoAgendamento) {
		this.indicativoAgendamento = indicativoAgendamento;
	}
	
	@XmlAttribute(name = "IC-CONTRATACAO")
	public String getIndicativoContratacao() {
		return indicativoContratacao;
	}
	public void setIndicativoContratacao(String indicativoContratacao) {
		this.indicativoContratacao = indicativoContratacao;
	}
	
	@XmlAttribute(name = "IC-ORG-MILITAR-EXERC")
	public String getIndicativoOrgaoMilitar() {
		return indicativoOrgaoMilitar;
	}
	public void setIndicativoOrgaoMilitar(String indicativoOrgaoMilitar) {
		this.indicativoOrgaoMilitar = indicativoOrgaoMilitar;
	}
	
	@XmlAttribute(name = "IC-UNID-PAG-AERO")
	public String getIndicativoPagAero() {
		return indicativoPagAero;
	}
	public void setIndicativoPagAero(String indicativoPagAero) {
		this.indicativoPagAero = indicativoPagAero;
	}
	
	@XmlAttribute(name = "IC-NUM-ADE-AERO")
	public String getIndicativoNumeroAdeAero() {
		return indicativoNumeroAdeAero;
	}
	public void setIndicativoNumeroAdeAero(String indicativoNumeroAdeAero) {
		this.indicativoNumeroAdeAero = indicativoNumeroAdeAero;
	}
	
	@XmlAttribute(name = "IC-COD-LOTACAO-GOV-AL")
	public String getIndicativoCodigoLotacaoGovAL() {
		return indicativoCodigoLotacaoGovAL;
	}
	public void setIndicativoCodigoLotacaoGovAL(String indicativoCodigoLotacaoGovAL) {
		this.indicativoCodigoLotacaoGovAL = indicativoCodigoLotacaoGovAL;
	}
	
	@XmlAttribute(name = "IC-NUM-MATR-GOV-AL")
	public String getIndicativoNumeroMatriculaGovAL() {
		return indicativoNumeroMatriculaGovAL;
	}
	public void setIndicativoNumeroMatriculaGovAL(
			String indicativoNumeroMatriculaGovAL) {
		this.indicativoNumeroMatriculaGovAL = indicativoNumeroMatriculaGovAL;
	}
	
	@XmlAttribute(name = "IC-NUM-AUTORIZ-GOV-PR")
	public String getIndicativoNumeroAutorizacaoGovPR() {
		return indicativoNumeroAutorizacaoGovPR;
	}
	public void setIndicativoNumeroAutorizacaoGovPR(
			String indicativoNumeroAutorizacaoGovPR) {
		this.indicativoNumeroAutorizacaoGovPR = indicativoNumeroAutorizacaoGovPR;
	}
	
	@XmlAttribute(name = "IC-NUM-MATR-SABESP")
	public String getIndicativoNumeroMatriculaSABESP() {
		return indicativoNumeroMatriculaSABESP;
	}
	public void setIndicativoNumeroMatriculaSABESP(
			String indicativoNumeroMatriculaSABESP) {
		this.indicativoNumeroMatriculaSABESP = indicativoNumeroMatriculaSABESP;
	}
	
	@XmlAttribute(name = "IC-NUM-MATR-CRVD")
	public String getIndicativoNumeroMatriculaCVRD() {
		return indicativoNumeroMatriculaCVRD;
	}
	public void setIndicativoNumeroMatriculaCVRD(
			String indicativoNumeroMatriculaCVRD) {
		this.indicativoNumeroMatriculaCVRD = indicativoNumeroMatriculaCVRD;
	}
	
	@XmlAttribute(name = "IC-MATR-INSTIT-SERPRO")
	public String getIndicativoMatriculaInstitutoSERPRO() {
		return indicativoMatriculaInstitutoSERPRO;
	}
	public void setIndicativoMatriculaInstitutoSERPRO(
			String indicativoMatriculaInstitutoSERPRO) {
		this.indicativoMatriculaInstitutoSERPRO = indicativoMatriculaInstitutoSERPRO;
	}
	
	@XmlAttribute(name = "IC-TIPO-BENEF-SERPRO")
	public String getIndicativoTipoBeneficiarioSERPRO() {
		return indicativoTipoBeneficiarioSERPRO;
	}
	public void setIndicativoTipoBeneficiarioSERPRO(
			String indicativoTipoBeneficiarioSERPRO) {
		this.indicativoTipoBeneficiarioSERPRO = indicativoTipoBeneficiarioSERPRO;
	}
	
	@XmlAttribute(name = "IC-TIPO-BENEF-CONTA")
	public String getIndicativoRecebeBeneficioConta() {
		return indicativoRecebeBeneficioConta;
	}
	public void setIndicativoRecebeBeneficioConta(
			String indicativoRecebeBeneficioConta) {
		this.indicativoRecebeBeneficioConta = indicativoRecebeBeneficioConta;
	}
	
	@XmlAttribute(name = "IC-NUM-BENEFICIO")
	public String getIndicativoNumeroBeneficio() {
		return indicativoNumeroBeneficio;
	}
	public void setIndicativoNumeroBeneficio(String indicativoNumeroBeneficio) {
		this.indicativoNumeroBeneficio = indicativoNumeroBeneficio;
	}
	
	@XmlAttribute(name = "IC-DIA-BENEFICIO")
	public String getIndicativoDiaBeneficio() {
		return indicativoDiaBeneficio;
	}
	public void setIndicativoDiaBeneficio(String indicativoDiaBeneficio) {
		this.indicativoDiaBeneficio = indicativoDiaBeneficio;
	}
	
	@XmlAttribute(name = "IC-EST-CTA-CRED-BENEF")
	public String getIndicativoEstadoContaCreditoBeneficio() {
		return indicativoEstadoContaCreditoBeneficio;
	}
	public void setIndicativoEstadoContaCreditoBeneficio(
			String indicativoEstadoContaCreditoBeneficio) {
		this.indicativoEstadoContaCreditoBeneficio = indicativoEstadoContaCreditoBeneficio;
	}
	
	@XmlAttribute(name = "IC-COD-ORG-CONVENIO")
	public String getIndicativoCodigoOrgaoConvenio() {
		return indicativoCodigoOrgaoConvenio;
	}
	public void setIndicativoCodigoOrgaoConvenio(
			String indicativoCodigoOrgaoConvenio) {
		this.indicativoCodigoOrgaoConvenio = indicativoCodigoOrgaoConvenio;
	}
	
	@Override
	public String toString() {
		return "Canal [codigoServico=" + codigoServico + ", codigoCanal="
				+ codigoCanal + ", nomeCanal=" + nomeCanal
				+ ", indicativoAgendamento=" + indicativoAgendamento
				+ ", indicativoContratacao=" + indicativoContratacao
				+ ", indicativoOrgaoMilitar=" + indicativoOrgaoMilitar
				+ ", indicativoPagAero=" + indicativoPagAero
				+ ", indicativoNumeroAdeAero=" + indicativoNumeroAdeAero
				+ ", indicativoCodigoLotacaoGovAL="
				+ indicativoCodigoLotacaoGovAL
				+ ", indicativoNumeroMatriculaGovAL="
				+ indicativoNumeroMatriculaGovAL
				+ ", indicativoNumeroAutorizacaoGovPR="
				+ indicativoNumeroAutorizacaoGovPR
				+ ", indicativoNumeroMatriculaSABESP="
				+ indicativoNumeroMatriculaSABESP
				+ ", indicativoNumeroMatriculaCVRD="
				+ indicativoNumeroMatriculaCVRD
				+ ", indicativoMatriculaInstitutoSERPRO="
				+ indicativoMatriculaInstitutoSERPRO
				+ ", indicativoTipoBeneficiarioSERPRO="
				+ indicativoTipoBeneficiarioSERPRO
				+ ", indicativoRecebeBeneficioConta="
				+ indicativoRecebeBeneficioConta
				+ ", indicativoNumeroBeneficio=" + indicativoNumeroBeneficio
				+ ", indicativoDiaBeneficio=" + indicativoDiaBeneficio
				+ ", indicativoEstadoContaCreditoBeneficio="
				+ indicativoEstadoContaCreditoBeneficio
				+ ", indicativoCodigoOrgaoConvenio="
				+ indicativoCodigoOrgaoConvenio + "]";
	}
	
	
	
	
	


}
