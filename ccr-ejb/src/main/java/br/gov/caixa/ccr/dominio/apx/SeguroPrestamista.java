package br.gov.caixa.ccr.dominio.apx;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Classe de dominio
 * Classe que extende a Classe ContratoConsignado
 * Refere as possibilidades de seguro prestamista
 * @author c110503
 * 
 * 
 * 
 *
 */
@XmlRootElement
public class SeguroPrestamista implements Serializable{	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1825995964236718280L;

	private String valor;
	
	private String cet;
	
	private String matriculaIndicador;

	@XmlAttribute(name="VR-SEGURO")
	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	@XmlAttribute(name="PR-CET-SEGURO")
	public String getCet() {
		return cet;
	}

	public void setCet(String cet) {
		this.cet = cet;
	}
	
	@XmlAttribute(name="CO-MAT-INDICADOR")
	public String getMatriculaIndicador() {
		return matriculaIndicador;
	}

	public void setMatriculaIndicador(String matriculaIndicador) {
		this.matriculaIndicador = matriculaIndicador;
	}

	@Override
	public String toString() {
		return "SeguroPrestamista [valor="
				+ valor + ", vet="
				+ cet + ", matriculaIndicador="
				+ matriculaIndicador + "]";
	}
	
}
