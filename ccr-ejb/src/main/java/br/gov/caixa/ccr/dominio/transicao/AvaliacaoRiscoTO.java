package br.gov.caixa.ccr.dominio.transicao;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the CCRTB039_AVALIACAO database table.
 * 
 */
@Entity
@Table(name="CCRTB039_AVALIACAO_RISCO")
public class AvaliacaoRiscoTO implements Serializable {
	private static final long serialVersionUID = 1L;
	


	//FIXME VINICIUS arrumar a sequence (Corrigido Jungles)
	@Id
	@SequenceGenerator(sequenceName = "CCRSQ039_AVALIACAO_RISCO", allocationSize = 1, name="CCRTB039_AVALIACAO_NUAVALIACAO_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CCRTB039_AVALIACAO_NUAVALIACAO_GENERATOR")
	@Column(name="NU_AVALIACAO_RISCO")
	private long nuAvaliacao;

	@Column(name="CO_RESULTADO")
	private String coResultado;
	
	@Column(name="DE_RESULTADO_AVALIACAO")
	private String noResultado;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_FIM_VALIDADE_AVALIACAO")
	private Date dtFimAvaliacao;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_GERACAO")
	private Date dtGeracao;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_INICIO_VALIDADE_AVALIACAO")
	private Date dtInicoAvaliacao;

	@Column(name="IC_AVALIACAO_MANUAL")
	private String icAvaliacaoManual;

	@Column(name="IC_BLOQUEIO_ALCADA")
	private String icBloqueioAlcada;

	@Column(name="NO_PRODUTO_SIRIC")
	private String noProduto;

	@Column(name="NU_OPERACAO_PRODUTO")
	private Long nuOperacao;

	@Column(name="PZ_MAXIMO_EMPRESTIMO")
	private Long nuPrazoMxmoEmprestimo;

	@Column(name="IN_RATING_AVALIACAO")
	private Long nuRatingAvaliacao;

	@Column(name="VR_MAXIMO_EMPRESTIMO")
	private BigDecimal vrMaximoEmprestimo;

	@Column(name="VR_MAXIMO_PRESTACAO")
	private BigDecimal vrMaximoPrestacao;

	public AvaliacaoRiscoTO() {
	}

	public long getNuAvaliacao() {
		return this.nuAvaliacao;
	}

	public void setNuAvaliacao(long nuAvaliacao) {
		this.nuAvaliacao = nuAvaliacao;
	}

	public String getCoResultado() {
		return this.coResultado;
	}

	public void setCoResultado(String coResultado) {
		this.coResultado = coResultado;
	}

	public String getNoResultado() {
		return noResultado;
	}

	public void setNoResultado(String noResultado) {
		this.noResultado = noResultado;
	}

	public Date getDtFimAvaliacao() {
		return this.dtFimAvaliacao;
	}

	public void setDtFimAvaliacao(Date dtFimAvaliacao) {
		this.dtFimAvaliacao = dtFimAvaliacao;
	}

	public Date getDtGeracao() {
		return this.dtGeracao;
	}

	public void setDtGeracao(Date dtGeracao) {
		this.dtGeracao = dtGeracao;
	}

	public Date getDtInicoAvaliacao() {
		return this.dtInicoAvaliacao;
	}

	public void setDtInicoAvaliacao(Date dtInicoAvaliacao) {
		this.dtInicoAvaliacao = dtInicoAvaliacao;
	}

	public String getIcAvaliacaoManual() {
		return this.icAvaliacaoManual;
	}

	public void setIcAvaliacaoManual(String icAvaliacaoManual) {
		this.icAvaliacaoManual = icAvaliacaoManual;
	}

	public String getIcBloqueioAlcada() {
		return this.icBloqueioAlcada;
	}

	public void setIcBloqueioAlcada(String icBloqueioAlcada) {
		this.icBloqueioAlcada = icBloqueioAlcada;
	}

	public String getNoProduto() {
		return this.noProduto;
	}

	public void setNoProduto(String noProduto) {
		this.noProduto = noProduto;
	}

	public Long getNuOperacao() {
		return this.nuOperacao;
	}

	public void setNuOperacao(Long nuOperacao) {
		this.nuOperacao = nuOperacao;
	}

	public Long getNuPrazoMxmoEmprestimo() {
		return this.nuPrazoMxmoEmprestimo;
	}

	public void setNuPrazoMxmoEmprestimo(Long nuPrazoMxmoEmprestimo) {
		this.nuPrazoMxmoEmprestimo = nuPrazoMxmoEmprestimo;
	}

	public Long getNuRatingAvaliacao() {
		return this.nuRatingAvaliacao;
	}

	public void setNuRatingAvaliacao(Long nuRatingAvaliacao) {
		this.nuRatingAvaliacao = nuRatingAvaliacao;
	}

	public BigDecimal getVrMaximoEmprestimo() {
		return this.vrMaximoEmprestimo;
	}

	public void setVrMaximoEmprestimo(BigDecimal vrMaximoEmprestimo) {
		this.vrMaximoEmprestimo = vrMaximoEmprestimo;
	}

	public BigDecimal getVrMaximoPrestacao() {
		return this.vrMaximoPrestacao;
	}

	public void setVrMaximoPrestacao(BigDecimal vrMaximoPrestacao) {
		this.vrMaximoPrestacao = vrMaximoPrestacao;
	}

}