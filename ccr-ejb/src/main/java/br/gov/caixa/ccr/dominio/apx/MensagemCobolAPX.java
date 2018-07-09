package br.gov.caixa.ccr.dominio.apx;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import br.gov.caixa.arqrefcore.cobol.conversor.anotacao.Tamanho;

/**
 * Classe que representa as mensagens do COBOL.
 * Usada pelo APX
 * 
 * @author c110503
 *
 */

@XmlRootElement
@XmlType(propOrder = { "programa", "paragrafo", "categoria", "codigoErro",
		"severidade", "mensagemErro", "mensagemNegocial" })
public class MensagemCobolAPX implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4631560779660100305L;
	
	private String programa;
	
	private String paragrafo;
	
	private String categoria;
	
	private String codigoErro;
	
	private String severidade;
	
	private String mensagemErro;
	
	private String mensagemNegocial;
	
	private String indicativoErro;
	
	@XmlAttribute(name = "PROGRAMA-ME") 
	public String getPrograma() {
		return programa;
	}
	public void setPrograma(String programa) {
		this.programa = programa;
	}
	
	@XmlAttribute(name = "PARAGRAFO-ME") 
	public String getParagrafo() {
		return paragrafo;
	}
	public void setParagrafo(String paragrafo) {
		this.paragrafo = paragrafo;
	}
	
	@XmlAttribute(name = "CATEGORIA-ME") 
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	
	@XmlAttribute(name = "CODIGO-ME") 
	public String getCodigoErro() {
		return codigoErro;
	}
	public void setCodigoErro(String codigoErro) {
		this.codigoErro = codigoErro;
	}
	
	@XmlAttribute(name = "SEVERIDADE-ME") 
	public String getSeveridade() {
		return severidade;
	}
	public void setSeveridade(String severidade) {
		this.severidade = severidade;
	}
	
	@XmlAttribute(name = "MSG-SISTEMA-ME") 
	public String getMensagemErro() {
		return mensagemErro;
	}
	public void setMensagemErro(String mensagemErro) {
		this.mensagemErro = mensagemErro;
	}
	
	@XmlAttribute(name = "MSG-NEGOCIAL-ME")
	public String getMensagemNegocial() {
		return mensagemNegocial;
	}
	public void setMensagemNegocial(String mensagemNegocial) {
		this.mensagemNegocial = mensagemNegocial;
	}
	
	@XmlAttribute(name = "IC-ERRO")
	public String getIndicativoErro() {
		return indicativoErro;
	}
	public void setIndicativoErro(String indicativoErro) {
		this.indicativoErro = indicativoErro;
	}
	
	@Override
	public String toString() {
		return "MensagemCobolAPX [programa=" + programa + ", paragrafo="
				+ paragrafo + ", categoria=" + categoria + ", codigoErro="
				+ codigoErro + ", severidade=" + severidade + ", mensagemErro="
				+ mensagemErro + ", mensagemNegocial=" + mensagemNegocial
				+ ", indicativoErro=" + indicativoErro + "]";
	}
	
	

	

}
