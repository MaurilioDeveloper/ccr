//package br.gov.caixa.ccr.dominio.transicao;
//
//import java.io.Serializable;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.Table;
//
//@Entity
//@Table(name="CCRTB010_PARAMETRO_OPERACAO")
//public class ParametroOperacaoTO implements Serializable {
//	
//	private static final long serialVersionUID = 1L;
//	
//	@Id
//	@Column(name="NU_OPERACAO", nullable=false, unique=true, length=5)
//	private Integer numero;
//	
//	@Column(name="DE_OPERACAO", nullable=false, length=50)
//	private String descricao;
//
//	@Column(name="NU_GRUPO_TAXA_PADRAO", length=5)
//	private Integer grupoTaxaPadrao;
//	
//	@Column(name="IC_TAXA_JURO_PADRAO", nullable=false, length=1)
//	private String indicadorTaxaPadrao;
//
//	@Column(name="NU_IDADE_MINIMA_PRESTAMISTA")
//	private Integer idadeMinima;
//	
//	@Column(name="NU_IDADE_MAXIMA_PRESTAMISTA")
//	private Integer idadeMaxima;
//		
//	@Column(name="IC_EXIGE_AUTORIZACAO_CONTRATO")
//	private String exigeAutorizacao;
//	
//	@Column(name="VR_MINIMO_EXIGE_AUTORIZACAO")
//	private Integer valorMinimoExigencia;
//	
//	@Column(name="QT_DIA_MAXIMO_SIMULACAO_FUTURA")
//	private Integer maximoDiasSimulacaoFutura;
//
//	public Integer getNumero() {
//		return numero;
//	}
//
//	public void setNumero(Integer numero) {
//		this.numero = numero;
//	}
//
//	public String getDescricao() {
//		return descricao;
//	}
//
//	public void setDescricao(String descricao) {
//		this.descricao = descricao;
//	}
//
//	public Integer getGrupoTaxaPadrao() {
//		return grupoTaxaPadrao;
//	}
//
//	public void setGrupoTaxaPadrao(Integer grupoTaxaPadrao) {
//		this.grupoTaxaPadrao = grupoTaxaPadrao;
//	}
//
//	public String getIndicadorTaxaPadrao() {
//		return indicadorTaxaPadrao;
//	}
//
//	public void setIndicadorTaxaPadrao(String indicadorTaxaPadrao) {
//		this.indicadorTaxaPadrao = indicadorTaxaPadrao;
//	}
//
//	public Integer getIdadeMinima() {
//		return idadeMinima;
//	}
//
//	public void setIdadeMinima(Integer idadeMinima) {
//		this.idadeMinima = idadeMinima;
//	}
//
//	public Integer getIdadeMaxima() {
//		return idadeMaxima;
//	}
//
//	public void setIdadeMaxima(Integer idadeMaxima) {
//		this.idadeMaxima = idadeMaxima;
//	}
//
//	public String getExigeAutorizacao() {
//		return exigeAutorizacao;
//	}
//
//	public void setExigeAutorizacao(String exigeAutorizacao) {
//		this.exigeAutorizacao = exigeAutorizacao;
//	}
//
//	public Integer getValorMinimoExigencia() {
//		return valorMinimoExigencia;
//	}
//
//	public void setValorMinimoExigencia(Integer valorMinimoExigencia) {
//		this.valorMinimoExigencia = valorMinimoExigencia;
//	}
//
//	public Integer getMaximoDiasSimulacaoFutura() {
//		return maximoDiasSimulacaoFutura;
//	}
//
//	public void setMaximoDiasSimulacaoFutura(Integer maximoDiasSimulacaoFutura) {
//		this.maximoDiasSimulacaoFutura = maximoDiasSimulacaoFutura;
//	}
//	
//}
