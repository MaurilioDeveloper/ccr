package br.gov.caixa.ccr.dominio.apx;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import br.gov.caixa.arqrefcore.cobol.conversor.anotacao.Tamanho;

/**
 * Classe de dominio
 * @author c110503
 *
 */
@XmlRootElement
public class Taxa implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2605834211902940001L;

	/**
	 * 
	 */
	
	private Integer prazoInicio;
	
	
	private Integer prazoFim;
	
	private List<Faixa> listaFaixas;
	

	@XmlAttribute(name = "PZ-INI")
	public Integer getPrazoInicio() {
		return prazoInicio;
	}

	public void setPrazoInicio(Integer prazoInicio) {
		this.prazoInicio = prazoInicio;
	}

	@XmlAttribute(name = "PZ-FIM")
	public Integer getPrazoFim() {
		return prazoFim;
	}

	public void setPrazoFim(Integer prazoFim) {
		this.prazoFim = prazoFim;
	}	

	@XmlElementWrapper(name="FAIXAS")
	@XmlElement(name="FAIXA")
	public List<Faixa> getListaFaixas() {
		return listaFaixas;
	}

	public void setListaFaixas(List<Faixa> listaFaixas) {
		this.listaFaixas = listaFaixas;
	}

	@Override
	public String toString() {
		return "Taxa [prazoInicio=" + prazoInicio + ", prazoFim=" + prazoFim
				+ ", listaFaixas=" + listaFaixas + "]";
	}

	

	
}
