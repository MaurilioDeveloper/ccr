package br.gov.caixa.ccr.dominio;

public class ConsultaAvaliacaoResp extends Retorno {

	private static final long serialVersionUID = -951661454351674582L;	
	
	
	private Long  idAvaliacao;
	private String proposta;
	private String codigo;
	private String tipoPessoa;	
	private String cpf;
	private String produto;
	private String modalidade;
	private String inicioValidade;
	private String fimValidade;
	private String prazo;
	private String limiteGlobal;
	private String limiteCPM;
	private String financiamento;
	private String bloqueadoAlcada;
	private String acaoVenda;
	private String rating;
	private String resultado;
	private String valorMaximoPrestacao;
	
	public ConsultaAvaliacaoResp(String codigo, String cpf, String produto,
			String inicioValidade, String fimValidade, String prazo,
			String limiteGlobal, String limiteCPM, String financiamento, String resultado, String valorMaximoPrestacao) {
		this.codigo = codigo;
		this.cpf = cpf;
		this.produto = produto;
		this.inicioValidade = inicioValidade;
		this.fimValidade = fimValidade;
		this.prazo = prazo;
		this.limiteGlobal = limiteGlobal;
		this.limiteCPM = limiteCPM;
		this.financiamento = financiamento;
		this.resultado = resultado;
		this.bloqueadoAlcada="1";
		this.valorMaximoPrestacao = valorMaximoPrestacao;
		
	}
	public ConsultaAvaliacaoResp(){
		
	}

	public String getProposta() {
		return proposta;
	}

	public void setProposta(String proposta) {
		this.proposta = proposta;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getTipoPessoa() {
		return tipoPessoa;
	}

	public void setTipoPessoa(String tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getProduto() {
		return produto;
	}

	public void setProduto(String produto) {
		this.produto = produto;
	}

	public String getModalidade() {
		return modalidade;
	}

	public void setModalidade(String modalidade) {
		this.modalidade = modalidade;
	}

	public String getInicioValidade() {
		return inicioValidade;
	}

	public void setInicioValidade(String inicioValidade) {
		this.inicioValidade = inicioValidade;
	}

	public String getFimValidade() {
		return fimValidade;
	}

	public void setFimValidade(String fimValidade) {
		this.fimValidade = fimValidade;
	}

	public String getPrazo() {
		return prazo;
	}

	public void setPrazo(String prazo) {
		this.prazo = prazo;
	}

	public String getLimiteGlobal() {
		return limiteGlobal;
	}

	public void setLimiteGlobal(String limiteGlobal) {
		this.limiteGlobal = limiteGlobal;
	}

	public String getLimiteCPM() {
		return limiteCPM;
	}

	public void setLimiteCPM(String limiteCPM) {
		this.limiteCPM = limiteCPM;
	}

	public String getFinanciamento() {
		return financiamento;
	}

	public void setFinanciamento(String financiamento) {
		this.financiamento = financiamento;
	}

	public String getBloqueadoAlcada() {
		return bloqueadoAlcada;
	}

	public void setBloqueadoAlcada(String bloqueadoAlcada) {
		this.bloqueadoAlcada = bloqueadoAlcada;
	}

	public String getAcaoVenda() {
		return acaoVenda;
	}

	public void setAcaoVenda(String acaoVenda) {
		this.acaoVenda = acaoVenda;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

	public String getValorMaximoPrestacao() {
		return valorMaximoPrestacao;
	}

	public void setValorMaximoPrestacao(String valorMaximoPrestacao) {
		this.valorMaximoPrestacao = valorMaximoPrestacao;
	}
	public Long getIdAvaliacao() {
		return idAvaliacao;
	}
	public void setIdAvaliacao(Long idAvaliacao) {
		this.idAvaliacao = idAvaliacao;
	}
	
	
	
}

