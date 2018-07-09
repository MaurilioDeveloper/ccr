package br.gov.caixa.ccr.dominio;

public class ParametroOperacao extends Retorno {
	
	private Integer numero;
	private String descricao;
	private Integer grupoTaxaPadrao;
	private String indicadorTaxaPadrao;
	private Integer idadeMinima;
	private Integer idadeMaxima;
	private String exigeAutorizacao;
	private Integer valorMinimoExigencia;
	private Integer maximoDiasSimulacaoFutura;

	public ParametroOperacao() {
		
	}
	
	public ParametroOperacao(Long codigo, String mensagem, String tipo) {
		super.setAll(codigo, mensagem, tipo);
	}
	
	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getGrupoTaxaPadrao() {
		return grupoTaxaPadrao;
	}

	public void setGrupoTaxaPadrao(Integer grupoTaxaPadrao) {
		this.grupoTaxaPadrao = grupoTaxaPadrao;
	}

	public String getIndicadorTaxaPadrao() {
		return indicadorTaxaPadrao;
	}

	public void setIndicadorTaxaPadrao(String indicadorTaxaPadrao) {
		this.indicadorTaxaPadrao = indicadorTaxaPadrao;
	}

	public Integer getIdadeMinima() {
		return idadeMinima;
	}

	public void setIdadeMinima(Integer idadeMinima) {
		this.idadeMinima = idadeMinima;
	}

	public Integer getIdadeMaxima() {
		return idadeMaxima;
	}

	public void setIdadeMaxima(Integer idadeMaxima) {
		this.idadeMaxima = idadeMaxima;
	}

	public String getExigeAutorizacao() {
		return exigeAutorizacao;
	}

	public void setExigeAutorizacao(String exigeAutorizacao) {
		this.exigeAutorizacao = exigeAutorizacao;
	}

	public Integer getValorMinimoExigencia() {
		return valorMinimoExigencia;
	}

	public void setValorMinimoExigencia(Integer valorMinimoExigencia) {
		this.valorMinimoExigencia = valorMinimoExigencia;
	}

	public Integer getMaximoDiasSimulacaoFutura() {
		return maximoDiasSimulacaoFutura;
	}

	public void setMaximoDiasSimulacaoFutura(Integer maximoDiasSimulacaoFutura) {
		this.maximoDiasSimulacaoFutura = maximoDiasSimulacaoFutura;
	}
	
}
