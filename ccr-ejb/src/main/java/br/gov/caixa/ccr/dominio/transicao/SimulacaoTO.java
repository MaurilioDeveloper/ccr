package br.gov.caixa.ccr.dominio.transicao;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the CCRTB012_SIMULACAO database table.
 * 
 */
@Entity
@Table(name="CCRTB012_SIMULACAO_CONTRATO")
public class SimulacaoTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
//	@SequenceGenerator(sequenceName = "CCRSQ012_NU_SEQ_SIMULACAO", allocationSize = 1, name="CCRTB012_SIMULACAO_NUSIMULACAO_GENERATOR" )
//	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CCRTB012_SIMULACAO_NUSIMULACAO_GENERATOR")
	@Column(name="NU_SIMULACAO_CONTRATO")
	private Long nuSimulacao;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_BASE_CALCULO")
	private Date dtBaseCalculo;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_LIBERACAO_CREDITO")
	private Date dtLiberacaoCredito;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_SIMULACAO")
	private Date dtSimulacao;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_VENCIMENTO")
	private Date dtVencimento;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_VNCMO_PRIMEIRA_PRESTACAO")
	private Date dtVencimentoPrimeiraPrestacao;

	@Column(name="IC_SEGURO_PRESTAMISTA")
	private String icSeguroPrestamista;

	@Column(name="NU_BENEFICIO")
	private Long nuBeneficio;

	@Column(name="NU_CONVENIO")
	private Long nuConvenio;

	@Column(name="NU_CPF")
	private Long nuCpf;

	@Column(name="PC_ALIQUOTA_BASICA")
	private BigDecimal pcAliquotaBasica;

	@Column(name="PC_ALIQUOTA_COMPLEMENTAR")
	private BigDecimal pcAliquotaComplementar;

	@Column(name="PC_CET_ANUAL")
	private BigDecimal pcCetAnual;

	@Column(name="PC_CET_MENSAL")
	private BigDecimal pcCetMensal;

	@Column(name="PC_TAXA_JURO_SIMULACAO")
	private BigDecimal pcTaxaJuroSimulacao;

	@Column(name="PC_TAXA_SEGURO")
	private BigDecimal pcTaxaSeguro;

	@Column(name="PZ_CONTRATO")
	private Long pzContrato;

	@Column(name="QT_DIA_JURO_ACERTO")
	private Long qtDiaJuroAcerto;

	@Column(name="VR_CONTRATO")
	private BigDecimal vrContrato;

	@Column(name="VR_IOF")
	private BigDecimal vrIof;

	@Column(name="VR_JURO_ACERTO")
	private BigDecimal vrJuroAcerto;

	@Column(name="VR_LIQUIDO_CONTRATO")
	private BigDecimal vrLiquidoContrato;

	@Column(name="VR_MARGEM")
	private BigDecimal vrMargem;

	@Column(name="VR_PRESTACAO")
	private BigDecimal vrPrestacao;

	@Column(name="VR_SEGURO_PRESTAMISTA")
	private BigDecimal vrSeguroPrestamista;
	
	@Lob
	@Column(name="DE_AUDITORIA_SIMULACAO")
	private String auditoriaSimulacao;

	public SimulacaoTO() {
	}

	public Long getNuSimulacao() {
		return this.nuSimulacao;
	}

	public void setNuSimulacao(Long nuSimulacao) {
		this.nuSimulacao = nuSimulacao;
	}

	public Date getDtBaseCalculo() {
		return this.dtBaseCalculo;
	}

	public void setDtBaseCalculo(Date dtBaseCalculo) {
		this.dtBaseCalculo = dtBaseCalculo;
	}

	public Date getDtLiberacaoCredito() {
		return this.dtLiberacaoCredito;
	}

	public void setDtLiberacaoCredito(Date dtLiberacaoCredito) {
		this.dtLiberacaoCredito = dtLiberacaoCredito;
	}

	public Date getDtSimulacao() {
		return this.dtSimulacao;
	}

	public void setDtSimulacao(Date dtSimulacao) {
		this.dtSimulacao = dtSimulacao;
	}

	public Date getDtVencimento() {
		return this.dtVencimento;
	}

	public void setDtVencimento(Date dtVencimento) {
		this.dtVencimento = dtVencimento;
	}

	public Date getDtVencimentoPrimeiraPrestacao() {
		return this.dtVencimentoPrimeiraPrestacao;
	}

	public void setDtVencimentoPrimeiraPrestacao(Date dtVencimentoPrimeiraPrestacao) {
		this.dtVencimentoPrimeiraPrestacao = dtVencimentoPrimeiraPrestacao;
	}

	public String getIcSeguroPrestamista() {
		return this.icSeguroPrestamista;
	}

	public void setIcSeguroPrestamista(String icSeguroPrestamista) {
		this.icSeguroPrestamista = icSeguroPrestamista;
	}

	public Long getNuBeneficio() {
		return this.nuBeneficio;
	}

	public void setNuBeneficio(Long nuBeneficio) {
		this.nuBeneficio = nuBeneficio;
	}

	public Long getNuConvenio() {
		return this.nuConvenio;
	}

	public void setNuConvenio(Long nuConvenio) {
		this.nuConvenio = nuConvenio;
	}

	public Long getNuCpf() {
		return this.nuCpf;
	}

	public void setNuCpf(Long nuCpf) {
		this.nuCpf = nuCpf;
	}

	public BigDecimal getPcAliquotaBasica() {
		return this.pcAliquotaBasica;
	}

	public void setPcAliquotaBasica(BigDecimal pcAliquotaBasica) {
		this.pcAliquotaBasica = pcAliquotaBasica;
	}

	public BigDecimal getPcAliquotaComplementar() {
		return this.pcAliquotaComplementar;
	}

	public void setPcAliquotaComplementar(BigDecimal pcAliquotaComplementar) {
		this.pcAliquotaComplementar = pcAliquotaComplementar;
	}

	public BigDecimal getPcCetAnual() {
		return this.pcCetAnual;
	}

	public void setPcCetAnual(BigDecimal pcCetAnual) {
		this.pcCetAnual = pcCetAnual;
	}

	public BigDecimal getPcCetMensal() {
		return this.pcCetMensal;
	}

	public void setPcCetMensal(BigDecimal pcCetMensal) {
		this.pcCetMensal = pcCetMensal;
	}

	public BigDecimal getPcTaxaJuroSimulacao() {
		return this.pcTaxaJuroSimulacao;
	}

	public void setPcTaxaJuroSimulacao(BigDecimal pcTaxaJuroSimulacao) {
		this.pcTaxaJuroSimulacao = pcTaxaJuroSimulacao;
	}

	public BigDecimal getPcTaxaSeguro() {
		return this.pcTaxaSeguro;
	}

	public void setPcTaxaSeguro(BigDecimal pcTaxaSeguro) {
		this.pcTaxaSeguro = pcTaxaSeguro;
	}

	public Long getPzContrato() {
		return this.pzContrato;
	}

	public void setPzContrato(Long pzContrato) {
		this.pzContrato = pzContrato;
	}

	public Long getQtDiaJuroAcerto() {
		return this.qtDiaJuroAcerto;
	}

	public void setQtDiaJuroAcerto(Long qtDiaJuroAcerto) {
		this.qtDiaJuroAcerto = qtDiaJuroAcerto;
	}

	public BigDecimal getVrContrato() {
		return this.vrContrato;
	}

	public void setVrContrato(BigDecimal vrContrato) {
		this.vrContrato = vrContrato;
	}

	public BigDecimal getVrIof() {
		return this.vrIof;
	}

	public void setVrIof(BigDecimal vrIof) {
		this.vrIof = vrIof;
	}

	public BigDecimal getVrJuroAcerto() {
		return this.vrJuroAcerto;
	}

	public void setVrJuroAcerto(BigDecimal vrJuroAcerto) {
		this.vrJuroAcerto = vrJuroAcerto;
	}

	public BigDecimal getVrLiquidoContrato() {
		return this.vrLiquidoContrato;
	}

	public void setVrLiquidoContrato(BigDecimal vrLiquidoContrato) {
		this.vrLiquidoContrato = vrLiquidoContrato;
	}

	public BigDecimal getVrMargem() {
		return this.vrMargem;
	}

	public void setVrMargem(BigDecimal vrMargem) {
		this.vrMargem = vrMargem;
	}

	public BigDecimal getVrPrestacao() {
		return this.vrPrestacao;
	}

	public void setVrPrestacao(BigDecimal vrPrestacao) {
		this.vrPrestacao = vrPrestacao;
	}

	public BigDecimal getVrSeguroPrestamista() {
		return this.vrSeguroPrestamista;
	}

	public void setVrSeguroPrestamista(BigDecimal vrSeguroPrestamista) {
		this.vrSeguroPrestamista = vrSeguroPrestamista;
	}

	/**
	 * @return the auditoriaSimulacao
	 */
	public String getAuditoriaSimulacao() {
		return auditoriaSimulacao;
	}

	/**
	 * @param auditoriaSimulacao the auditoriaSimulacao to set
	 */
	public void setAuditoriaSimulacao(String auditoriaSimulacao) {
		this.auditoriaSimulacao = auditoriaSimulacao;
	}

}