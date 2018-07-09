package br.gov.caixa.ccr.dominio.barramento.siric;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;

public class CondicionadaOuReprovada implements Serializable {

	private static final long serialVersionUID = 8644479136147654280L;

	private String proposta;	
	private String codigoAvaliacao;	
	private Cliente cliente;	
	private String produto;	
	private String motivo;	
	private String financimanentoPossivel;	
	private String prazoPossivel;	
	private String prestacaoPossivel;

	@XmlElement(name = "PROPOSTA")
	public String getProposta() {
		return proposta;
	}

	public void setProposta(String proposta) {
		this.proposta = proposta;
	}

	@XmlElement(name = "CODIGO_AVALIACAO")
	public String getCodigoAvaliacao() {
		return codigoAvaliacao;
	}

	public void setCodigoAvaliacao(String codigoAvaliacao) {
		this.codigoAvaliacao = codigoAvaliacao;
	}

	@XmlElement(name = "CLIENTE")
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	@XmlElement(name = "PRODUTO")
	public String getProduto() {
		return produto;
	}

	public void setProduto(String produto) {
		this.produto = produto;
	}

	@XmlElement(name = "MOTIVO")
	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	@XmlElement(name = "VR_FINANCIAMENTO_POSSIVEL")
	public String getFinancimanentoPossivel() {
		return financimanentoPossivel;
	}

	public void setFinancimanentoPossivel(String financimanentoPossivel) {
		this.financimanentoPossivel = financimanentoPossivel;
	}

	@XmlElement(name = "PRAZO_POSSIVEL")
	public String getPrazoPossivel() {
		return prazoPossivel;
	}

	public void setPrazoPossivel(String prazoPossivel) {
		this.prazoPossivel = prazoPossivel;
	}

	@XmlElement(name = "VR_PRESTACAO_POSSIVEL")
	public String getPrestacaoPossivel() {
		return prestacaoPossivel;
	}

	public void setPrestacaoPossivel(String prestacaoPossivel) {
		this.prestacaoPossivel = prestacaoPossivel;
	}
	
}
