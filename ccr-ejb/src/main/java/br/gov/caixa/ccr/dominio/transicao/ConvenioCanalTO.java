package br.gov.caixa.ccr.dominio.transicao;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="CCRTB003_CONVENIO_CANAL")
@NamedNativeQuery(name = "ConvenioCanalTO.excluiCanaisVinculados", query = "DELETE FROM CCR.CCRTB003_CONVENIO_CANAL WHERE NU_CONVENIO = :pConvenio AND NU_CANAL_ATENDIMENTO = :pCanal", resultClass = ConvenioCanalTO.class)
public class ConvenioCanalTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static final Long SITUACAO_ATIVO = 1L;
	public static final Long SITUACAO_INATIVO = 2L;
	public static final Long TIPO_SITUACAO_CONVENIO_CANAL = 5L;

	@EmbeddedId
	private ConvenioCanalPK id;

	@Column(name="IC_AUTORIZA_MARGEM_CONTRATO")
	private String indAutorizaMargemContrato;

	@Column(name="IC_AUTORIZA_MARGEM_RENOVACAO")
	private String indAutorizaMargemRenovacao;
	
	@Column(name="IC_PERMITE_CONTRATACAO")
	private String indPermiteContratacao;

	@Column(name="IC_PERMITE_RENOVACAO")
	private String indPermiteRenovacao;

	@Column(name="PZ_MAXIMO_RENOVACAO")
	private Integer przMaximoRenovacao;
	
	@Column(name="PZ_MAXIMO_CONTRATACAO")
	private BigDecimal przMaximoContratacao;
	
	@Column(name="IC_FAIXA_JURO_RENOVACAO")
	private String indFaixaJuroRenovacao;	
	
	@Column(name="IC_EXIGE_ANUENCIA")
	private String indExigeAnuencia;
	
	@Column(name="IC_FAIXA_JURO_CONTRATACAO")
	private String indFaixaJuroContratacao;
	
	@Column(name="QT_DIA_GARANTIA_CONDICAO")
	private String qtdDiaGarantiaCondicao;
	
	@Column(name="PZ_MINIMO_CONTRATACAO")
	private String przMinimoContratacao;
	
	@Column(name="PZ_MINIMO_RENOVACAO")
	private String przMinimoRenovacao;
	
//	@ManyToOne
//	@JoinColumns({
//		@JoinColumn(name="NU_SITUACAO", referencedColumnName="NU_SITUACAO"),
//		@JoinColumn(name="NU_TIPO_SITUACAO", referencedColumnName="NU_TIPO_SITUACAO")
//		})
	@Transient
	private SituacaoTO situacao;
	

	public ConvenioCanalTO() {
	}

	public ConvenioCanalPK getId() {
		return this.id;
	}

	public void setId(ConvenioCanalPK id) {
		this.id = id;
	}

	public String getIndAutorizaMargemContrato() {
		return this.indAutorizaMargemContrato;
	}

	public void setIndAutorizaMargemContrato(String indAutorizaMargemContrato) {
		this.indAutorizaMargemContrato = indAutorizaMargemContrato;
	}

	public String getIndAutorizaMargemRenovacao() {
		return this.indAutorizaMargemRenovacao;
	}

	public void setIndAutorizaMargemRenovacao(String indAutorizaMargemRenovacao) {
		this.indAutorizaMargemRenovacao = indAutorizaMargemRenovacao;
	}

	public String getIndPermiteContratacao() {
		return this.indPermiteContratacao;
	}

	public void setIndPermiteContratacao(String indPermiteContratacao) {
		this.indPermiteContratacao = indPermiteContratacao;
	}

	public String getIndPermiteRenovacao() {
		return this.indPermiteRenovacao;
	}

	public void setIndPermiteRenovacao(String indPermiteRenovacao) {
		this.indPermiteRenovacao = indPermiteRenovacao;
	}

	public Integer getPrzMaximoRenovacao() {
		return this.przMaximoRenovacao;
	}

	public void setPrzMaximoRenovacao(Integer przMaximoRenovacao) {
		this.przMaximoRenovacao = przMaximoRenovacao;
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

	public String getQtdDiaGarantiaCondicao() {
		return qtdDiaGarantiaCondicao;
	}

	public void setQtdDiaGarantiaCondicao(String qtdDiaGarantiaCondicao) {
		this.qtdDiaGarantiaCondicao = qtdDiaGarantiaCondicao;
	}

	public SituacaoTO getSituacao() {
		return situacao;
	}

	public void setSituacao(SituacaoTO situacao) {
		this.situacao = situacao;
	}
	

}