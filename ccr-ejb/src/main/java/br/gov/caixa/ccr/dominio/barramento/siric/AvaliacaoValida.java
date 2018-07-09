package br.gov.caixa.ccr.dominio.barramento.siric;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="AVALIACOES_VALIDAS")
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
@XmlType(propOrder={"proposta", "codigo", "tipoPessoa", "cpf", "produto", "modalidade", "inicioValidade",
					"fimValidade", "prazo", "limiteGlobal", "limiteCPM", "financiamento", 
					"bloqueadoAlcada", "acaoVenda", "rating" })
public class AvaliacaoValida implements Serializable {

	private static final long serialVersionUID = -956541454351674582L;	
	
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

	public AvaliacaoValida() {
		
	}
	
	public AvaliacaoValida(String cpf) {
		this.tipoPessoa = "1";
		this.cpf = cpf;
	}
	
	public AvaliacaoValida(String codigo, String cpf, String produto,
			String inicioValidade, String fimValidade, String prazo,
			String limiteGlobal, String limiteCPM, String financiamento) {
		this.codigo = codigo;
		this.cpf = cpf;
		this.produto = produto;
		this.inicioValidade = inicioValidade;
		this.fimValidade = fimValidade;
		this.prazo = prazo;
		this.limiteGlobal = limiteGlobal;
		this.limiteCPM = limiteCPM;
		this.financiamento = financiamento;
	}

	@XmlElement(name="PROPOSTA")
	public String getProposta() {
		return proposta;
	}

	public void setProposta(String proposta) {
		this.proposta = proposta;
	}

	@XmlElement(name="CODIGO_AVALIACAO")
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	@XmlElement(name="TP_PESSOA")
	public String getTipoPessoa() {
		return tipoPessoa;
	}

	public void setTipoPessoa(String tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}

	@XmlElement(name="CPF")
	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	@XmlElement(name="PRODUTO")
	public String getProduto() {
		return produto;
	}

	public void setProduto(String produto) {
		this.produto = produto;
	}

	@XmlElement(name="MODALIDADE")
	public String getModalidade() {
		return modalidade;
	}

	public void setModalidade(String modalidade) {
		this.modalidade = modalidade;
	}

	@XmlElement(name="DT_INICIO_VALIDADE")
	public String getInicioValidade() {
		return inicioValidade;
	}

	public void setInicioValidade(String inicioValidade) {
		this.inicioValidade = inicioValidade;
	}

	@XmlElement(name="DT_FIM_VALIDADE")
	public String getFimValidade() {
		return fimValidade;
	}

	public void setFimValidade(String fimValidade) {
		this.fimValidade = fimValidade;
	}

	@XmlElement(name="PRAZO")
	public String getPrazo() {
		return prazo;
	}

	public void setPrazo(String prazo) {
		this.prazo = prazo;
	}

	@XmlElement(name="VR_LIMITE_GLOBAL")
	public String getLimiteGlobal() {
		return limiteGlobal;
	}

	public void setLimiteGlobal(String limiteGlobal) {
		this.limiteGlobal = limiteGlobal;
	}

	@XmlElement(name="VR_LIMITE_CPM")
	public String getLimiteCPM() {
		return limiteCPM;
	}

	public void setLimiteCPM(String limiteCPM) {
		this.limiteCPM = limiteCPM;
	}

	@XmlElement(name="VR_FINANCIAMENTO")
	public String getFinanciamento() {
		return financiamento;
	}

	public void setFinanciamento(String financiamento) {
		this.financiamento = financiamento;
	}

	@XmlElement(name="FLAG_BLOQ_ALCADA")
	public String getBloqueadoAlcada() {
		return bloqueadoAlcada;
	}

	public void setBloqueadoAlcada(String bloqueadoAlcada) {
		this.bloqueadoAlcada = bloqueadoAlcada;
	}

	@XmlElement(name="ACAO_VENDA")
	public String getAcaoVenda() {
		return acaoVenda;
	}

	public void setAcaoVenda(String acaoVenda) {
		this.acaoVenda = acaoVenda;
	}

	@XmlElement(name="RATING")
	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}
	
}
