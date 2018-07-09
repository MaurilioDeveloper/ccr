package br.gov.caixa.ccr.dominio.barramento.siric;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="TOMADOR_VALIDA")
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class TomadorValida implements Serializable {

	private static final long serialVersionUID = -7007258448105726026L;

	private String proposta;	
	private String codigoAvaliacao;	
	private String resultado;	
	private String calculoTotal;	
	private String disponivel;	
	private String rating;

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

	@XmlElement(name = "RESULTADO")
	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

	@XmlElement(name = "VALOR_CALCULADO_TOTAL")
	public String getCalculoTotal() {
		return calculoTotal;
	}

	public void setCalculoTotal(String calculoTotal) {
		this.calculoTotal = calculoTotal;
	}

	@XmlElement(name = "VALOR_DISPONIVEL")
	public String getDisponivel() {
		return disponivel;
	}

	public void setDisponivel(String disponivel) {
		this.disponivel = disponivel;
	}

	@XmlElement(name = "RATING")
	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}
	
}
