package br.gov.caixa.ccr.dominio.transicao;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.gov.caixa.ccr.template.formatters.FormatEnum;
import br.gov.caixa.ccr.template.formatters.TemplateFormat;


/**
 * The persistent class for the CCRTB001_CONVENIO database table.
 * 
 */
@Entity
@Table(name="CCRTB001_CONVENIO")

public class ConvenioTO implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String QUERY_NEXT_ID = "SELECT NVL(MAX(NU_CONVENIO), 0)+1 AS NU_CONVENIO  FROM CCR.CCRTB001_CONVENIO ";
	public static final String QUERY_LIST = "SELECT * FROM CCR.CCRTB001_CONVENIO ";

	@Id
	@Column(name="NU_CONVENIO")
	private Long id;

	@Column(name="CO_DV_CONVENIO")
	private Long codDvConvenio;

	@Column(name="DD_CREDITO_SALARIO")
	private Integer diaCreditoSal;

	@Column(name="IC_ABRANGENCIA")
	private Integer indAbrangencia;

	@Column(name="IC_ACEITA_CANAL")
	private String indAceitaCanal;
	
	@Column(name="NO_CONVENIO")
	private String nome;

	@Column(name="NU_AGENCIA_CONTA_CREDITO")
	private Long numAgenciaContaCredito;

	@Column(name="NU_CONTA_CREDITO")
	private Long numContaCredito;

	@Column(name="NU_DV_CONTA_CREDITO")
	private Long numDvContaCredito;

	@Column(name="NU_NATURAL_PV_COBRANCA")
	private Long numNaturalPvCobranca;

	@Column(name="NU_NATURAL_PV_RESPONSAVEL")
	private Long numNaturalPvResponsavel;

	@Column(name="NU_NATURAL_SPRNA_COBRANCA")
	private Long numNaturalSprnaCobranca;

	@Column(name="NU_NATURAL_SPRNA_RESPONSAVEL")
	private Long numNaturalSprnaResponsavel;

	@Column(name="NU_OPERACAO_CONTA_CREDITO")
	private Long numOperacaoContaCredito;

	@Column(name="NU_PV_COBRANCA")
	private Long numPvCobranca;

	@Column(name="NU_PV_RESPONSAVEL")
	private Long numPvResponsavel;

	@Column(name="NU_SPRNA_COBRANCA")
	private Long numSprnaCobranca;

	@Column(name="NU_SPRNA_RESPONSAVEL")
	private Long numSprnaResponsavel;

	@Column(name="PZ_EMISSAO_EXTRATO")
	private Integer przEmissaoExtrato; 
	
//	@Column(name="PZ_VIGENCIA_CONVENIO")
//	private Integer przVigenciaConvenio; 
	
	//bi-directional many-to-one association to GrupoTaxa
	@ManyToOne
	@JoinColumn(name="NU_GRUPO_TAXA")
	private GrupoTaxaTO grupoTaxa;

	//bi-directional many-to-one association to GrupoAverbacao
	@ManyToOne
	@JoinColumn(name="NU_GRUPO_AVERBACAO")
	private GrupoAverbacaoTO grupoAverbacao;
	
	//bi-directional many-to-one association to Situacao
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="NU_SITUACAO", referencedColumnName="NU_SITUACAO"),
		@JoinColumn(name="NU_TIPO_SITUACAO", referencedColumnName="NU_TIPO_SITUACAO")
		})
	private SituacaoTO situacao;

	@Column(name="NU_CNPJ_CONVENENTE")
	@TemplateFormat(format=FormatEnum.CNPJ)
	private Long cnpjConvenente;
	
	@Column(name="QT_EMPREGADO")
	private Integer qtEmpregado;
	
	@Column(name="CO_ORGAO")
	private String codigoOrgao;
	
	@Column(name="QT_DIA_AGUARDAR_AUTORIZACAO")
	private Integer qtDiaAguardarAutorizacao;

//	@Column(name="DT_CRIACAO_CONVENIO")
//	private Date dtCriacaoConvenio;
	
	
//	//bi-directional many-to-one association to ConvenioCanal
	//@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	//private List<ConvenioCanalTO> convenioCanais;
 

	public ConvenioTO() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCodDvConvenio() {
		return this.codDvConvenio;
	}

	public void setCodDvConvenio(Long codDvConvenio) {
		this.codDvConvenio = codDvConvenio;
	}

	public Integer getDiaCreditoSal() {
		return this.diaCreditoSal;
	}

	public void setDiaCreditoSal(Integer diaCreditoSal) {
		this.diaCreditoSal = diaCreditoSal;
	}

	public Integer getIndAbrangencia() {
		return this.indAbrangencia;
	}

	public void setIndAbrangencia(Integer indAbrangencia) {
		this.indAbrangencia = indAbrangencia;
	}

	public String getIndAceitaCanal() {
		return this.indAceitaCanal;
	}

	public void setIndAceitaCanal(String indAceitaCanal) {
		this.indAceitaCanal = indAceitaCanal;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Long getNumAgenciaContaCredito() {
		return this.numAgenciaContaCredito;
	}

	public void setNumAgenciaContaCredito(Long numAgenciaContaCredito) {
		this.numAgenciaContaCredito = numAgenciaContaCredito;
	}

	public Long getNumContaCredito() {
		return this.numContaCredito;
	}

	public void setNumContaCredito(Long numContaCredito) {
		this.numContaCredito = numContaCredito;
	}

	public Long getNumDvContaCredito() {
		return this.numDvContaCredito;
	}

	public void setNumDvContaCredito(Long numDvContaCredito) {
		this.numDvContaCredito = numDvContaCredito;
	}

	public Long getNumNaturalPvCobranca() {
		return this.numNaturalPvCobranca;
	}

	public void setNumNaturalPvCobranca(Long numNaturalPvCobranca) {
		this.numNaturalPvCobranca = numNaturalPvCobranca;
	}

	public Long getNumNaturalPvResponsavel() {
		return this.numNaturalPvResponsavel;
	}

	public void setNumNaturalPvResponsavel(Long numNaturalPvResponsavel) {
		this.numNaturalPvResponsavel = numNaturalPvResponsavel;
	}

	public Long getNumNaturalSprnaCobranca() {
		return this.numNaturalSprnaCobranca;
	}

	public void setNumNaturalSprnaCobranca(Long numNaturalSprnaCobranca) {
		this.numNaturalSprnaCobranca = numNaturalSprnaCobranca;
	}

	public Long getNumNaturalSprnaResponsavel() {
		return this.numNaturalSprnaResponsavel;
	}

	public void setNumNaturalSprnaResponsavel(Long numNaturalSprnaResponsavel) {
		this.numNaturalSprnaResponsavel = numNaturalSprnaResponsavel;
	}

	public Long getNumOperacaoContaCredito() {
		return this.numOperacaoContaCredito;
	}

	public void setNumOperacaoContaCredito(Long numOperacaoContaCredito) {
		this.numOperacaoContaCredito = numOperacaoContaCredito;
	}

	public Long getNumPvCobranca() {
		return this.numPvCobranca;
	}

	public void setNumPvCobranca(Long numPvCobranca) {
		this.numPvCobranca = numPvCobranca;
	}

	public Long getNumPvResponsavel() {
		return this.numPvResponsavel;
	}

	public void setNumPvResponsavel(Long numPvResponsavel) {
		this.numPvResponsavel = numPvResponsavel;
	}

	public Long getNumSprnaCobranca() {
		return this.numSprnaCobranca;
	}

	public void setNumSprnaCobranca(Long numSprnaCobranca) {
		this.numSprnaCobranca = numSprnaCobranca;
	}

	public Long getNumSprnaResponsavel() {
		return this.numSprnaResponsavel;
	}

	public void setNumSprnaResponsavel(Long numSprnaResponsavel) {
		this.numSprnaResponsavel = numSprnaResponsavel;
	}

	public Integer getPrzEmissaoExtrato() {
		return this.przEmissaoExtrato;
	}

	public void setPrzEmissaoExtrato(Integer przEmissaoExtrato) {
		this.przEmissaoExtrato = przEmissaoExtrato;
	}


	public GrupoTaxaTO getGrupoTaxa() {
		return grupoTaxa;
	}

	public void setGrupoTaxa(GrupoTaxaTO grupoTaxa) {
		this.grupoTaxa = grupoTaxa;
	}

	public GrupoAverbacaoTO getGrupoAverbacao() {
		return grupoAverbacao;
	}

	public void setGrupoAverbacao(GrupoAverbacaoTO grupoAverbacao) {
		this.grupoAverbacao = grupoAverbacao;
	}

	public SituacaoTO getSituacao() {
		return situacao;
	}

	public void setSituacao(SituacaoTO situacao) {
		this.situacao = situacao;
	}

	public Long getCnpjConvenente() {
		return cnpjConvenente;
	}

	public void setCnpjConvenente(Long cnpjConvenente) {
		this.cnpjConvenente = cnpjConvenente;
	}

	public Integer getQtEmpregado() {
		return qtEmpregado;
	}

	public void setQtEmpregado(Integer qtEmpregado) {
		this.qtEmpregado = qtEmpregado;
	}

	public String getCodigoOrgao() {
		return codigoOrgao;
	}

	public void setCodigoOrgao(String codigoOrgao) {
		this.codigoOrgao = codigoOrgao;
	}

	public Integer getQtDiaAguardarAutorizacao() {
		return qtDiaAguardarAutorizacao;
	}
	public void setQtDiaAguardarAutorizacao(Integer qtDiaAguardarAutorizacao) {
		this.qtDiaAguardarAutorizacao = qtDiaAguardarAutorizacao;
	}

//	public Integer getPrzVigenciaConvenio() {
//		return przVigenciaConvenio;
//	}
//
//	public void setPrzVigenciaConvenio(Integer przVigenciaConvenio) {
//		this.przVigenciaConvenio = przVigenciaConvenio;
//	}

//	public Date getDtCriacaoConvenio() {
//		return dtCriacaoConvenio;
//	}
//
//	public void setDtCriacaoConvenio(Date dtCriacaoConvenio) {
//		this.dtCriacaoConvenio = dtCriacaoConvenio;
//	}

}