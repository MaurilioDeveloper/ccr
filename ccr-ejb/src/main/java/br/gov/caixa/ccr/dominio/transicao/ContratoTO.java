package br.gov.caixa.ccr.dominio.transicao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import br.gov.caixa.ccr.template.formatters.FormatEnum;
import br.gov.caixa.ccr.template.formatters.TemplateFormat;


/**
 * The persistent class for the CCRTB013_CONTRATO database table.
 * 
 */
@Entity
@Table(name="CCRTB013_CONTRATO")
                
public class ContratoTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(sequenceName = "CCRSQ013_CONTRATO", allocationSize = 1, name="CCRTB013_CONTRATO_NUCONTRATO_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CCRTB013_CONTRATO_NUCONTRATO_GENERATOR")
	@Column(name="NU_CONTRATO", precision=19)
	private Long nuContrato;
	
	@OneToOne
	@JoinColumn(name="NU_CONVENIO")
	private ConvenioTO convenioTO;
	
	//bi-directional many-to-one association to Situacao
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="NU_SITUACAO", referencedColumnName="NU_SITUACAO"),
		@JoinColumn(name="NU_TIPO_SITUACAO", referencedColumnName="NU_TIPO_SITUACAO")
		})
	private SituacaoTO situacao;
	
	
	@Column(name="IC_TIPO_SIMULACAO")
	private String coTipoSimulacao;

	@Column(name="CO_USUARIO")
	private String coUsuario;
	
	@Column(name="CO_MATRICULA_CLIENTE")
	private String coMatriculaCliente ;

	@Column(name="DD_BENEFICIO", precision=2)
	private Long ddBeneficio;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_BASE_CALCULO")
	@TemplateFormat(format=FormatEnum.DATA)
	private Date dtBaseCalculo;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_COMPETENCIA")
	private Date dtCompetencia;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_CONTRATO")
	private Date dtContrato;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="TS_ULTIMA_SITUACAO")
	@TemplateFormat(format=FormatEnum.DATA_HORA)
	private Date dtSituacaoContrato;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_LIBERACAO_CREDITO")
	@TemplateFormat(format=FormatEnum.DATA)
	private Date dtLiberacaoCredito;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_VENCIMENTO")
	private Date dtVencimento;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_VNCMO_PRIMEIRA_PRESTACAO")
	@TemplateFormat(format=FormatEnum.DATA)
	private Date dtVncmoPrimeiraPrestacao;

	@Column(name="IC_EMPREGADO_TEMPORARIO")
	private String icEmpregadoTemporario;

	@Column(name="IC_RECEBE_SALARIO_CAIXA")
	private String icRecebeSalarioCaixa;

	@Column(name="IC_SEGURO_PRESTAMISTA")
	private String icSeguroPrestamista;

	@Column(name="IC_TIPO_CREDITO", precision=1)
	private Integer icTipoCredito;
	
	@Column(name="NU_AGENCIA_CONTA_CREDITO", precision=5)
	private Long nuAgenciaContaCredito;

	@Column(name="NU_AGENCIA_CONTA_DEBITO", precision=5)
	private Long nuAgenciaContaDebito;
	
	@Column(name="DD_VENCIMENTO_PRESTACAO", precision=2)
	private Long ddVencimentoPrestacoes;

//	//FIXME SKMT - adicionar base (Ana)
//	@OneToOne()
//	@JoinColumn(name="NU_AVALIACAO_CLIENTE")
//	private AvaliacaoTO avaliacaoCliente;
//	
	
	//FIXME SKMT - Ao criar a coluna NU_AVALIACAO_CLIENTE salvar nela os dados e  descomentar a linha 132 e gerar getters e setters. (Corrigido Jungles)
	@OneToOne()
	@JoinColumn(name="NU_AVALIACAO_CLIENTE")
	private AvaliacaoRiscoTO avaliacaoCliente;
	//private Long nuOrgaoMilitar;
	
	@OneToOne()
	@JoinColumn(name="NU_AVALIACAO")
	private AvaliacaoRiscoTO avaliacaoOperacao;

	@Column(name="NU_BANCO_CONTA_CREDITO", precision=4)
	private Long nuBancoContaCredito;

	@Column(name="NU_BANCO_CONTA_DEBITO", precision=4)
	private Long nuBancoContaDebito;

	@Column(name="NU_BENEFICIO", precision=8)
	private Long nuBeneficio;

	@Column(name="NU_COCLI", precision=19)
	private Long nuCocli;

	@Column(name="NU_CONTA_CREDITO", precision=8)
	private Long nuContaCredito;

	@Column(name="NU_CONTA_DEBITO", precision=8)
	private Long nuContaDebito;

	@Column(name="NU_CONTRATO_APLICACAO", precision=19)
	private Long nuContratoAplicacao;

	@Column(name="NU_CPF")
	@TemplateFormat(format=FormatEnum.CPF)
	private Long nuCpf;
	
	@Column(name="NU_CNPJ_FONTE_PAGADORA", precision=14)
	private Long nuCnpjFontePagadora;

	@Column(name="NU_DV_CONTA_CREDITO", precision=1)
	private Long nuDvContaCredito;

	@Column(name="NU_DV_CONTA_DEBITO", precision=1)
	private Long nuDvContaDebito;

	//AGENCIA CONCESSORA
	@Column(name="NU_NATURAL_MOVIMENTO", precision=19)
	private Long nuNaturalMovimento;
	
	@Column(name="NU_AGENCIA_CONCESSORA", precision=5)
	private Long nuAgenciaConcessora;

	@Column(name="NU_OPERACAO_CONTA_CREDITO", precision=3)
	private Long nuOperacaoContaCredito;

	@Column(name="NU_OPERACAO_CONTA_DEBITO", precision=3)
	private Long nuOperacaoContaDebito;

	@Column(name="NU_RIC", precision=15)
	private Long nuRic;

	@Column(name="NU_UNIDADE_MOVIMENTO", precision=19)
	private Long nuUnidadeMovimento;

	@Column(name="PC_ALIQUOTA_BASICA", precision=8, scale=5)
	private BigDecimal pcAliquotaBasica;

	@Column(name="PC_ALIQUOTA_COMPLEMENTAR", precision=8, scale=5)
	private BigDecimal pcAliquotaComplementar;

	@Column(name="PC_CET_ANUAL", precision=8, scale=5)
	@TemplateFormat(format=FormatEnum.VALOR_PORCENTAGEM)
	private BigDecimal pcCetAnual;

	@Column(name="PC_CET_CAPITAL", precision=8, scale=5)
	private BigDecimal pcCetCapital;

	@Column(name="PC_CET_IOF", precision=8, scale=5)
	@TemplateFormat(format=FormatEnum.VALOR_PORCENTAGEM)
	private BigDecimal pcCetIof;

	@Column(name="PC_CET_JURO_ACERTO", precision=8, scale=5)
	private BigDecimal pcCetJuroAcerto;

	@Column(name="PC_CET_MENSAL", precision=8, scale=5)
	@TemplateFormat(format=FormatEnum.VALOR_PORCENTAGEM)
	private BigDecimal pcCetMensal;

	@Column(name="PC_CET_SEGURO", precision=8, scale=5)
	private BigDecimal pcCetSeguro;

	@Column(name="PC_TAXA_JURO_ANUAL", precision=8, scale=5)
	private BigDecimal pcTaxaJuroAnual;

	@Column(name="PC_TAXA_JURO_CONTRATO", precision=8, scale=5)
	private BigDecimal pcTaxaJuroContrato;

	@Column(name="PC_TAXA_SEGURO", precision=8, scale=5)
	private BigDecimal pcTaxaSeguro;

	@Column(name="PZ_VIGENCIA_CONTRATO", precision=4)
	private Long pzContrato;

	@Column(name="QT_DIA_JURO_ACERTO", precision=6)
	private Long qtDiaJuroAcerto;

	@Column(name="QT_EMPREGADO", precision=15)
	private Long qtEmpregado;

	@Column(name="VR_BRUTO_BENEFICIO", precision=15, scale=2)
	private BigDecimal vrBrutoBeneficio;

	@Column(name="VR_CET_ANUAL", precision=15, scale=2)
	private BigDecimal vrCetAnual;

	@Column(name="VR_CET_MENSAL", precision=15, scale=2)
	private BigDecimal vrCetMensal;

	@Column(name="VR_CONTRATO", precision=11, scale=2)
	@TemplateFormat(format=FormatEnum.VALOR_MONETARIO)
	private BigDecimal vrContrato;

	@Column(name="VR_IOF", precision=8, scale=2)
	@TemplateFormat(format=FormatEnum.VALOR_MONETARIO)
	private BigDecimal vrIof;

	@Column(name="VR_JURO_ACERTO", precision=8, scale=2)
	@TemplateFormat(format=FormatEnum.VALOR_MONETARIO)
	private BigDecimal vrJuroAcerto;

	@Column(name="VR_LIQUIDO_BENEFICIO", precision=15, scale=2)
	private BigDecimal vrLiquidoBeneficio;

	@Column(name="VR_LIQUIDO_CONTRATO", precision=11, scale=2)
	@TemplateFormat(format=FormatEnum.VALOR_MONETARIO)
	private BigDecimal vrLiquidoContrato;

	@Column(name="VR_TOTAL_CONTRATO", precision=11, scale=2)
	@TemplateFormat(format=FormatEnum.VALOR_MONETARIO)
	private BigDecimal vrTotalContrato;

	@Column(name="VR_PRESTACAO", precision=8, scale=2)
	@TemplateFormat(format=FormatEnum.VALOR_MONETARIO)
	private BigDecimal vrPrestacao;

	@Column(name="VR_SEGURO_PRESTAMISTA", precision=11, scale=2)
	@TemplateFormat(format=FormatEnum.VALOR_MONETARIO)
	private BigDecimal vrSeguroPrestamista;
	
	//TODO Criar na base , após crIação retirar Transient SKM
	
	@Transient
	@Column(name="PC_TAXA_EFETIVA_ANUAL")
	private BigDecimal pcTaxaEfetivaAnual;
	
	@Transient
	@Column(name="PC_TAXA_EFETIVA_MENSAL")
	private BigDecimal pcTaxaEfetivaMensal;
	
	//bi-directional many-to-one association to canalAtendimento
	@ManyToOne
	@JoinColumn(name="NU_CANAL_ATENDIMENTO")
	private CanalAtendimentoTO canalAtendimento;	

	public CanalAtendimentoTO getCanalAtendimento() {
		return canalAtendimento;
	}

	public void setCanalAtendimento(CanalAtendimentoTO canalAtendimento) {
		this.canalAtendimento = canalAtendimento;
	}

	public ContratoTO() {
	}

	/**
	 * @return the nuContrato
	 */
	public Long getNuContrato() {
		return nuContrato;
	}

	/**
	 * @param nuContrato the nuContrato to set
	 */
	public void setNuContrato(Long nuContrato) {
		this.nuContrato = nuContrato;
	}

	/**
	 * @return the coTipoSimulacao
	 */
	public String getCoTipoSimulacao() {
		return coTipoSimulacao;
	}

	/**
	 * @param coTipoSimulacao the coTipoSimulacao to set
	 */
	public void setCoTipoSimulacao(String coTipoSimulacao) {
		this.coTipoSimulacao = coTipoSimulacao;
	}

	/**
	 * @return the coUsuario
	 */
	public String getCoUsuario() {
		return coUsuario;
	}

	/**
	 * @param coUsuario the coUsuario to set
	 */
	public void setCoUsuario(String coUsuario) {
		this.coUsuario = coUsuario;
	}
	
	/**
	 * @return the coMatriculaCliente
	 */
	public String getCoMatriculaCliente() {
		return coMatriculaCliente;
	}

	/**
	 * @param coMatriculaCliente the coMatriculaCliente to set
	 */
	public void setCoMatriculaCliente(String coMatriculaCliente) {
		this.coMatriculaCliente = coMatriculaCliente;
	}

	/**
	 * @return the ddBeneficio
	 */
	public Long getDdBeneficio() {
		return ddBeneficio;
	}

	/**
	 * @param ddBeneficio the ddBeneficio to set
	 */
	public void setDdBeneficio(Long ddBeneficio) {
		this.ddBeneficio = ddBeneficio;
	}

	/**
	 * @return the dtBaseCalculo
	 */
	public Date getDtBaseCalculo() {
		return dtBaseCalculo;
	}

	/**
	 * @param dtBaseCalculo the dtBaseCalculo to set
	 */
	public void setDtBaseCalculo(Date dtBaseCalculo) {
		this.dtBaseCalculo = dtBaseCalculo;
	}

	/**
	 * @return the dtCompetencia
	 */
	public Date getDtCompetencia() {
		return dtCompetencia;
	}

	/**
	 * @param dtCompetencia the dtCompetencia to set
	 */
	public void setDtCompetencia(Date dtCompetencia) {
		this.dtCompetencia = dtCompetencia;
	}

	/**
	 * @return the dtContrato
	 */
	public Date getDtContrato() {
		return dtContrato;
	}

	/**
	 * @param dtContrato the dtContrato to set
	 */
	public void setDtContrato(Date dtContrato) {
		this.dtContrato = dtContrato;
	}

	/**
	 * @return the dtLiberacaoCredito
	 */
	public Date getDtLiberacaoCredito() {
		return dtLiberacaoCredito;
	}

	/**
	 * @param dtLiberacaoCredito the dtLiberacaoCredito to set
	 */
	public void setDtLiberacaoCredito(Date dtLiberacaoCredito) {
		this.dtLiberacaoCredito = dtLiberacaoCredito;
	}

	/**
	 * @return the dtVencimento
	 */
	public Date getDtVencimento() {
		return dtVencimento;
	}

	/**
	 * @param dtVencimento the dtVencimento to set
	 */
	public void setDtVencimento(Date dtVencimento) {
		this.dtVencimento = dtVencimento;
	}

	/**
	 * @return the dtVncmoPrimeiraPrestacao
	 */
	public Date getDtVncmoPrimeiraPrestacao() {
		return dtVncmoPrimeiraPrestacao;
	}

	/**
	 * @param dtVncmoPrimeiraPrestacao the dtVncmoPrimeiraPrestacao to set
	 */
	public void setDtVncmoPrimeiraPrestacao(Date dtVncmoPrimeiraPrestacao) {
		this.dtVncmoPrimeiraPrestacao = dtVncmoPrimeiraPrestacao;
	}

	/**
	 * @return the icEmpregadoTemporario
	 */
	public String getIcEmpregadoTemporario() {
		return icEmpregadoTemporario;
	}

	/**
	 * @param icEmpregadoTemporario the icEmpregadoTemporario to set
	 */
	public void setIcEmpregadoTemporario(String icEmpregadoTemporario) {
		this.icEmpregadoTemporario = icEmpregadoTemporario;
	}

	/**
	 * @return the icRecebeSalarioCaixa
	 */
	public String getIcRecebeSalarioCaixa() {
		return icRecebeSalarioCaixa;
	}

	/**
	 * @param icRecebeSalarioCaixa the icRecebeSalarioCaixa to set
	 */
	public void setIcRecebeSalarioCaixa(String icRecebeSalarioCaixa) {
		this.icRecebeSalarioCaixa = icRecebeSalarioCaixa;
	}

	/**
	 * @return the icSeguroPrestamista
	 */
	public String getIcSeguroPrestamista() {
		return icSeguroPrestamista;
	}

	/**
	 * @param icSeguroPrestamista the icSeguroPrestamista to set
	 */
	public void setIcSeguroPrestamista(String icSeguroPrestamista) {
		this.icSeguroPrestamista = icSeguroPrestamista;
	}

	/**
	 * @return the icTipoCredito
	 */
	public Integer getIcTipoCredito() {
		return icTipoCredito;
	}

	/**
	 * @param icTipoCredito the icTipoCredito to set
	 */
	public void setIcTipoCredito(Integer icTipoCredito) {
		this.icTipoCredito = icTipoCredito;
	}

	/**
	 * @return the nuAgenciaContaCredito
	 */
	public Long getNuAgenciaContaCredito() {
		return nuAgenciaContaCredito;
	}

	/**
	 * @param nuAgenciaContaCredito the nuAgenciaContaCredito to set
	 */
	public void setNuAgenciaContaCredito(Long nuAgenciaContaCredito) {
		this.nuAgenciaContaCredito = nuAgenciaContaCredito;
	}

	/**
	 * @return the nuAgenciaContaDebito
	 */
	public Long getNuAgenciaContaDebito() {
		return nuAgenciaContaDebito;
	}

	/**
	 * @param nuAgenciaContaDebito the nuAgenciaContaDebito to set
	 */
	public void setNuAgenciaContaDebito(Long nuAgenciaContaDebito) {
		this.nuAgenciaContaDebito = nuAgenciaContaDebito;
	}

//	/**
//	 * @return the avaliacaoCliente
//	 */
//	public AvaliacaoTO getAvaliacaoCliente() {
//		return avaliacaoCliente;
//	}
//
//	/**
//	 * @param avaliacaoCliente the avaliacaoCliente to set
//	 */
//	public void setAvaliacaoCliente(AvaliacaoTO avaliacaoCliente) {
//		this.avaliacaoCliente = avaliacaoCliente;
//	}
//
//	/**
//	 * @return the avaliacaoOperacao
//	 */
//	public AvaliacaoTO getAvaliacaoOperacao() {
//		return avaliacaoOperacao;
//	}
//
//	/**
//	 * @param avaliacaoOperacao the avaliacaoOperacao to set
//	 */
//	public void setAvaliacaoOperacao(AvaliacaoTO avaliacaoOperacao) {
//		this.avaliacaoOperacao = avaliacaoOperacao;
//	}



	/**
	 * @return the nuBancoContaCredito
	 */
	public Long getNuBancoContaCredito() {
		return nuBancoContaCredito;
	}

	/**
	 * @param nuBancoContaCredito the nuBancoContaCredito to set
	 */
	public void setNuBancoContaCredito(Long nuBancoContaCredito) {
		this.nuBancoContaCredito = nuBancoContaCredito;
	}

	/**
	 * @return the nuBancoContaDebito
	 */
	public Long getNuBancoContaDebito() {
		return nuBancoContaDebito;
	}

	/**
	 * @param nuBancoContaDebito the nuBancoContaDebito to set
	 */
	public void setNuBancoContaDebito(Long nuBancoContaDebito) {
		this.nuBancoContaDebito = nuBancoContaDebito;
	}

	/**
	 * @return the nuBeneficio
	 */
	public Long getNuBeneficio() {
		return nuBeneficio;
	}

	/**
	 * @param nuBeneficio the nuBeneficio to set
	 */
	public void setNuBeneficio(Long nuBeneficio) {
		this.nuBeneficio = nuBeneficio;
	}

	/**
	 * @return the nuCocli
	 */
	public Long getNuCocli() {
		return nuCocli;
	}

	/**
	 * @param nuCocli the nuCocli to set
	 */
	public void setNuCocli(Long nuCocli) {
		this.nuCocli = nuCocli;
	}

	/**
	 * @return the nuContaCredito
	 */
	public Long getNuContaCredito() {
		return nuContaCredito;
	}

	/**
	 * @param nuContaCredito the nuContaCredito to set
	 */
	public void setNuContaCredito(Long nuContaCredito) {
		this.nuContaCredito = nuContaCredito;
	}

	/**
	 * @return the nuContaDebito
	 */
	public Long getNuContaDebito() {
		return nuContaDebito;
	}

	/**
	 * @param nuContaDebito the nuContaDebito to set
	 */
	public void setNuContaDebito(Long nuContaDebito) {
		this.nuContaDebito = nuContaDebito;
	}

	/**
	 * @return the nuContratoAplicacao
	 */
	public Long getNuContratoAplicacao() {
		return nuContratoAplicacao;
	}

	/**
	 * @param nuContratoAplicacao the nuContratoAplicacao to set
	 */
	public void setNuContratoAplicacao(Long nuContratoAplicacao) {
		this.nuContratoAplicacao = nuContratoAplicacao;
	}

	/**
	 * @return the nuCpf
	 */
	public Long getNuCpf() {
		return nuCpf;
	}

	/**
	 * @param nuCpf the nuCpf to set
	 */
	public void setNuCpf(Long nuCpf) {
		this.nuCpf = nuCpf;
	}

	/**
	 * @return the nuDvContaCredito
	 */
	public Long getNuDvContaCredito() {
		return nuDvContaCredito;
	}

	/**
	 * @param nuDvContaCredito the nuDvContaCredito to set
	 */
	public void setNuDvContaCredito(Long nuDvContaCredito) {
		this.nuDvContaCredito = nuDvContaCredito;
	}

	/**
	 * @return the nuDvContaDebito
	 */
	public Long getNuDvContaDebito() {
		return nuDvContaDebito;
	}

	/**
	 * @param nuDvContaDebito the nuDvContaDebito to set
	 */
	public void setNuDvContaDebito(Long nuDvContaDebito) {
		this.nuDvContaDebito = nuDvContaDebito;
	}

	/**
	 * @return the nuNaturalMovimento
	 */
	public Long getNuNaturalMovimento() {
		return nuNaturalMovimento;
	}

	/**
	 * @param nuNaturalMovimento the nuNaturalMovimento to set
	 */
	public void setNuNaturalMovimento(Long nuNaturalMovimento) {
		this.nuNaturalMovimento = nuNaturalMovimento;
	}

	/**
	 * @return the nuOperacaoContaCredito
	 */
	public Long getNuOperacaoContaCredito() {
		return nuOperacaoContaCredito;
	}

	/**
	 * @param nuOperacaoContaCredito the nuOperacaoContaCredito to set
	 */
	public void setNuOperacaoContaCredito(Long nuOperacaoContaCredito) {
		this.nuOperacaoContaCredito = nuOperacaoContaCredito;
	}

	/**
	 * @return the nuOperacaoContaDebito
	 */
	public Long getNuOperacaoContaDebito() {
		return nuOperacaoContaDebito;
	}

	/**
	 * @param nuOperacaoContaDebito the nuOperacaoContaDebito to set
	 */
	public void setNuOperacaoContaDebito(Long nuOperacaoContaDebito) {
		this.nuOperacaoContaDebito = nuOperacaoContaDebito;
	}

	/**
	 * @return the avaliacaoCliente
	 */
	public AvaliacaoRiscoTO getAvaliacaoCliente() {
		return avaliacaoCliente;
	}

	/**
	 * @param avaliacaoCliente the avaliacaoCliente to set
	 */
	public void setAvaliacaoCliente(AvaliacaoRiscoTO avaliacaoCliente) {
		this.avaliacaoCliente = avaliacaoCliente;
	}

	/**
	 * @return the nuRic
	 */
	public Long getNuRic() {
		return nuRic;
	}

	/**
	 * @param nuRic the nuRic to set
	 */
	public void setNuRic(Long nuRic) {
		this.nuRic = nuRic;
	}

	/**
	 * @return the nuUnidadeMovimento
	 */
	public Long getNuUnidadeMovimento() {
		return nuUnidadeMovimento;
	}

	/**
	 * @param nuUnidadeMovimento the nuUnidadeMovimento to set
	 */
	public void setNuUnidadeMovimento(Long nuUnidadeMovimento) {
		this.nuUnidadeMovimento = nuUnidadeMovimento;
	}

	/**
	 * @return the pcAliquotaBasica
	 */
	public BigDecimal getPcAliquotaBasica() {
		return pcAliquotaBasica;
	}

	/**
	 * @param pcAliquotaBasica the pcAliquotaBasica to set
	 */
	public void setPcAliquotaBasica(BigDecimal pcAliquotaBasica) {
		this.pcAliquotaBasica = pcAliquotaBasica;
	}

	/**
	 * @return the pcAliquotaComplementar
	 */
	public BigDecimal getPcAliquotaComplementar() {
		return pcAliquotaComplementar;
	}

	/**
	 * @param pcAliquotaComplementar the pcAliquotaComplementar to set
	 */
	public void setPcAliquotaComplementar(BigDecimal pcAliquotaComplementar) {
		this.pcAliquotaComplementar = pcAliquotaComplementar;
	}

	/**
	 * @return the pcCetAnual
	 */
	public BigDecimal getPcCetAnual() {
		return pcCetAnual;
	}

	/**
	 * @param pcCetAnual the pcCetAnual to set
	 */
	public void setPcCetAnual(BigDecimal pcCetAnual) {
		this.pcCetAnual = pcCetAnual;
	}

	/**
	 * @return the pcCetCapital
	 */
	public BigDecimal getPcCetCapital() {
		return pcCetCapital;
	}

	/**
	 * @param pcCetCapital the pcCetCapital to set
	 */
	public void setPcCetCapital(BigDecimal pcCetCapital) {
		this.pcCetCapital = pcCetCapital;
	}

	/**
	 * @return the pcCetIof
	 */
	public BigDecimal getPcCetIof() {
		return pcCetIof;
	}

	/**
	 * @param pcCetIof the pcCetIof to set
	 */
	public void setPcCetIof(BigDecimal pcCetIof) {
		this.pcCetIof = pcCetIof;
	}

	/**
	 * @return the pcCetJuroAcerto
	 */
	public BigDecimal getPcCetJuroAcerto() {
		return pcCetJuroAcerto;
	}

	/**
	 * @param pcCetJuroAcerto the pcCetJuroAcerto to set
	 */
	public void setPcCetJuroAcerto(BigDecimal pcCetJuroAcerto) {
		this.pcCetJuroAcerto = pcCetJuroAcerto;
	}

	/**
	 * @return the pcCetMensal
	 */
	public BigDecimal getPcCetMensal() {
		return pcCetMensal;
	}

	/**
	 * @param pcCetMensal the pcCetMensal to set
	 */
	public void setPcCetMensal(BigDecimal pcCetMensal) {
		this.pcCetMensal = pcCetMensal;
	}

	/**
	 * @return the pcCetSeguro
	 */
	public BigDecimal getPcCetSeguro() {
		return pcCetSeguro;
	}

	/**
	 * @param pcCetSeguro the pcCetSeguro to set
	 */
	public void setPcCetSeguro(BigDecimal pcCetSeguro) {
		this.pcCetSeguro = pcCetSeguro;
	}

	/**
	 * @return the pcTaxaJuroAnual
	 */
	public BigDecimal getPcTaxaJuroAnual() {
		return pcTaxaJuroAnual;
	}

	/**
	 * @param pcTaxaJuroAnual the pcTaxaJuroAnual to set
	 */
	public void setPcTaxaJuroAnual(BigDecimal pcTaxaJuroAnual) {
		this.pcTaxaJuroAnual = pcTaxaJuroAnual;
	}

	/**
	 * @return the pcTaxaJuroContrato
	 */
	public BigDecimal getPcTaxaJuroContrato() {
		return pcTaxaJuroContrato;
	}

	/**
	 * @param pcTaxaJuroContrato the pcTaxaJuroContrato to set
	 */
	public void setPcTaxaJuroContrato(BigDecimal pcTaxaJuroContrato) {
		this.pcTaxaJuroContrato = pcTaxaJuroContrato;
	}

	/**
	 * @return the pcTaxaSeguro
	 */
	public BigDecimal getPcTaxaSeguro() {
		return pcTaxaSeguro;
	}

	/**
	 * @param pcTaxaSeguro the pcTaxaSeguro to set
	 */
	public void setPcTaxaSeguro(BigDecimal pcTaxaSeguro) {
		this.pcTaxaSeguro = pcTaxaSeguro;
	}

	/**
	 * @return the pzContrato
	 */
	public Long getPzContrato() {
		return pzContrato;
	}

	/**
	 * @param pzContrato the pzContrato to set
	 */
	public void setPzContrato(Long pzContrato) {
		this.pzContrato = pzContrato;
	}

	/**
	 * @return the qtDiaJuroAcerto
	 */
	public Long getQtDiaJuroAcerto() {
		return qtDiaJuroAcerto;
	}

	/**
	 * @param qtDiaJuroAcerto the qtDiaJuroAcerto to set
	 */
	public void setQtDiaJuroAcerto(Long qtDiaJuroAcerto) {
		this.qtDiaJuroAcerto = qtDiaJuroAcerto;
	}

	/**
	 * @return the qtEmpregado
	 */
	public Long getQtEmpregado() {
		return qtEmpregado;
	}

	/**
	 * @param qtEmpregado the qtEmpregado to set
	 */
	public void setQtEmpregado(Long qtEmpregado) {
		this.qtEmpregado = qtEmpregado;
	}

	/**
	 * @return the vrBrutoBeneficio
	 */
	public BigDecimal getVrBrutoBeneficio() {
		return vrBrutoBeneficio;
	}

	/**
	 * @param vrBrutoBeneficio the vrBrutoBeneficio to set
	 */
	public void setVrBrutoBeneficio(BigDecimal vrBrutoBeneficio) {
		this.vrBrutoBeneficio = vrBrutoBeneficio;
	}

	/**
	 * @return the vrCetAnual
	 */
	public BigDecimal getVrCetAnual() {
		return vrCetAnual;
	}

	/**
	 * @param vrCetAnual the vrCetAnual to set
	 */
	public void setVrCetAnual(BigDecimal vrCetAnual) {
		this.vrCetAnual = vrCetAnual;
	}

	/**
	 * @return the vrCetMensal
	 */
	public BigDecimal getVrCetMensal() {
		return vrCetMensal;
	}

	/**
	 * @param vrCetMensal the vrCetMensal to set
	 */
	public void setVrCetMensal(BigDecimal vrCetMensal) {
		this.vrCetMensal = vrCetMensal;
	}

	/**
	 * @return the vrContrato
	 */
	public BigDecimal getVrContrato() {
		return vrContrato;
	}

	/**
	 * @param vrContrato the vrContrato to set
	 */
	public void setVrContrato(BigDecimal vrContrato) {
		this.vrContrato = vrContrato;
	}

	/**
	 * @return the vrIof
	 */
	public BigDecimal getVrIof() {
		return vrIof;
	}

	/**
	 * @param vrIof the vrIof to set
	 */
	public void setVrIof(BigDecimal vrIof) {
		this.vrIof = vrIof;
	}

	/**
	 * @return the vrJuroAcerto
	 */
	public BigDecimal getVrJuroAcerto() {
		return vrJuroAcerto;
	}

	/**
	 * @param vrJuroAcerto the vrJuroAcerto to set
	 */
	public void setVrJuroAcerto(BigDecimal vrJuroAcerto) {
		this.vrJuroAcerto = vrJuroAcerto;
	}

	/**
	 * @return the vrLiquidoBeneficio
	 */
	public BigDecimal getVrLiquidoBeneficio() {
		return vrLiquidoBeneficio;
	}

	/**
	 * @param vrLiquidoBeneficio the vrLiquidoBeneficio to set
	 */
	public void setVrLiquidoBeneficio(BigDecimal vrLiquidoBeneficio) {
		this.vrLiquidoBeneficio = vrLiquidoBeneficio;
	}

	/**
	 * @return the vrLiquidoContrato
	 */
	public BigDecimal getVrLiquidoContrato() {
		return vrLiquidoContrato;
	}

	/**
	 * @param vrLiquidoContrato the vrLiquidoContrato to set
	 */
	public void setVrLiquidoContrato(BigDecimal vrLiquidoContrato) {
		this.vrLiquidoContrato = vrLiquidoContrato;
	}

	/**
	 * @return the vrPrestacao
	 */
	public BigDecimal getVrPrestacao() {
		return vrPrestacao;
	}

	/**
	 * @param vrPrestacao the vrPrestacao to set
	 */
	public void setVrPrestacao(BigDecimal vrPrestacao) {
		this.vrPrestacao = vrPrestacao;
	}

	/**
	 * @return the vrSeguroPrestamista
	 */
	public BigDecimal getVrSeguroPrestamista() {
		return vrSeguroPrestamista;
	}

	/**
	 * @param vrSeguroPrestamista the vrSeguroPrestamista to set
	 */
	public void setVrSeguroPrestamista(BigDecimal vrSeguroPrestamista) {
		this.vrSeguroPrestamista = vrSeguroPrestamista;
	}

	/**
	 * @return the nuCnpjFontePagadora
	 */
	public Long getNuCnpjFontePagadora() {
		return nuCnpjFontePagadora;
	}

	/**
	 * @param nuCnpjFontePagadora the nuCnpjFontePagadora to set
	 */
	public void setNuCnpjFontePagadora(Long nuCnpjFontePagadora) {
		this.nuCnpjFontePagadora = nuCnpjFontePagadora;
	}

	/**
	 * @return the vrTotalContrato
	 */
	public BigDecimal getVrTotalContrato() {
		return vrTotalContrato;
	}

	/**
	 * @param vrTotalContrato the vrTotalContrato to set
	 */
	public void setVrTotalContrato(BigDecimal vrTotalContrato) {
		this.vrTotalContrato = vrTotalContrato;
	}

	public ConvenioTO getConvenioTO() {
		return convenioTO;
	}

	public void setConvenioTO(ConvenioTO convenioTO) {
		this.convenioTO = convenioTO;
	}

	/**
	 * @return the situacao
	 */
	public SituacaoTO getSituacao() {
		return situacao;
	}

	/**
	 * @param situacao the situacao to set
	 */
	public void setSituacao(SituacaoTO situacao) {
		this.situacao = situacao;
	}

	public Date getDtSituacaoContrato() {
		return dtSituacaoContrato;
	}

	public void setDtSituacaoContrato(Date dtSituacaoContrato) {
		this.dtSituacaoContrato = dtSituacaoContrato;
	}


	public Long getDdVencimentoPrestacoes() {
		return ddVencimentoPrestacoes;
	}

	public void setDdVencimentoPrestacoes(Long ddVencimentoPrestacoes) {
		this.ddVencimentoPrestacoes = ddVencimentoPrestacoes;
	}

	/**
	 * @return the avaliacaoOperacao
	 */
	public AvaliacaoRiscoTO getAvaliacaoOperacao() {
		return avaliacaoOperacao;
	}

	/**
	 * @param avaliacaoOperacao the avaliacaoOperacao to set
	 */
	public void setAvaliacaoOperacao(AvaliacaoRiscoTO avaliacaoOperacao) {
		this.avaliacaoOperacao = avaliacaoOperacao;
	}

	/**
	 * @return the pcTaxaEfetivaAnual
	 */
	public BigDecimal getPcTaxaEfetivaAnual() {
		return pcTaxaEfetivaAnual;
	}

	/**
	 * @param pcTaxaEfetivaAnual the pcTaxaEfetivaAnual to set
	 */
	public void setPcTaxaEfetivaAnual(BigDecimal pcTaxaEfetivaAnual) {
		this.pcTaxaEfetivaAnual = pcTaxaEfetivaAnual;
	}

	/**
	 * @return the pcTaxaEfetivaMensal
	 */
	public BigDecimal getPcTaxaEfetivaMensal() {
		return pcTaxaEfetivaMensal;
	}

	/**
	 * @param pcTaxaEfetivaMensal the pcTaxaEfetivaMensal to set
	 */
	public void setPcTaxaEfetivaMensal(BigDecimal pcTaxaEfetivaMensal) {
		this.pcTaxaEfetivaMensal = pcTaxaEfetivaMensal;
	}

	/**
	 * @return the nuAgenciaConcessora
	 */
	public Long getNuAgenciaConcessora() {
		return nuAgenciaConcessora;
	}

	/**
	 * @param nuAgenciaConcessora the nuAgenciaConcessora to set
	 */
	public void setNuAgenciaConcessora(Long nuAgenciaConcessora) {
		this.nuAgenciaConcessora = nuAgenciaConcessora;
	}
	
	

}	
