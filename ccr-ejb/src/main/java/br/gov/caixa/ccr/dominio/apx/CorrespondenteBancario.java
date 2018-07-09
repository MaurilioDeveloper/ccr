package br.gov.caixa.ccr.dominio.apx;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Classe de dominio
 * @author c110503
 *
 */
@XmlRootElement
public class CorrespondenteBancario implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5649584731914245774L;
	
	private String codigoCorrespondente;
	
	private String cnpj;
	
	private String razaoSocial;
	
	private String tipo;

	@XmlAttribute(name="CODIGO")
	public String getCodigoCorrespondente() {
		return codigoCorrespondente;
	}

	public void setCodigoCorrespondente(String codigoCorrespondente) {
		this.codigoCorrespondente = codigoCorrespondente;
	}

	@XmlAttribute(name="CNPJ")
	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	@XmlAttribute(name="RAZAO-SOCIAL")
	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	@XmlAttribute(name="TIPO")
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	@Override
	public String toString() {
		return "CorrespondenteBancario [codigoCorrespondente="
				+ codigoCorrespondente + ", CNPJ=" + cnpj + ", razaoSocial="
				+ razaoSocial + ", tipo=" + tipo
				+ "]";
	}
	
	

}
