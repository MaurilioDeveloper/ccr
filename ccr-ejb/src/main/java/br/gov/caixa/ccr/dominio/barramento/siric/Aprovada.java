package br.gov.caixa.ccr.dominio.barramento.siric;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="APROVADA")
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class Aprovada implements Serializable {

	private static final long serialVersionUID = 2787481275784096707L;

	private String proposta;	
	private String codigoAvaliacao;	
	private String limiteGlobal;	
	private String valorCPM;	
	private String financiamento;	
	private String rating;	
	private String bloqueado;	
	private String acaoVenda;

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

	@XmlElement(name = "VR_LIMITE_GLOBAL")
	public String getLimiteGlobal() {
		return limiteGlobal;
	}

	public void setLimiteGlobal(String limiteGlobal) {
		this.limiteGlobal = limiteGlobal;
	}

	@XmlElement(name = "VR_CPM")
	public String getValorCPM() {
		return valorCPM;
	}

	public void setValorCPM(String valorCPM) {
		this.valorCPM = valorCPM;
	}

	@XmlElement(name = "VR_FINANCIAMENTO")
	public String getFinanciamento() {
		return financiamento;
	}

	public void setFinanciamento(String financiamento) {
		this.financiamento = financiamento;
	}

	@XmlElement(name = "RATING")
	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	@XmlElement(name = "FLAG_BLOQ_ALCADA")
	public String getBloqueado() {
		return bloqueado;
	}

	public void setBloqueado(String bloqueado) {
		this.bloqueado = bloqueado;
	}

	@XmlElement(name = "ACAO_VENDA")
	public String getAcaoVenda() {
		return acaoVenda;
	}

	public void setAcaoVenda(String acaoVenda) {
		this.acaoVenda = acaoVenda;
	} 
	
}
