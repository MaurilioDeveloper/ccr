package br.gov.caixa.ccr.dominio.apx;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class GrupoTaxa implements Serializable{

	private static final long serialVersionUID = 7863822970908608675L;

	private List<Taxa> listaTaxasContratacao;
	
	private List<Taxa> listaTaxasRenovacao;
	
	@XmlElementWrapper(name="CONTRATACAO")
	@XmlElement(name="TAXA")
	public List<Taxa> getListaTaxasContratacao() {
		return listaTaxasContratacao;
	}
	public void setListaTaxasContratacao(List<Taxa> listaTaxasContratacao) {
		this.listaTaxasContratacao = listaTaxasContratacao;
	}
	
	@XmlElementWrapper(name="RENOVACAO")
	@XmlElement(name="TAXA")
	public List<Taxa> getListaTaxasRenovacao() {
		return listaTaxasRenovacao;
	}
	public void setListaTaxasRenovacao(List<Taxa> listaTaxasRenovacao) {
		this.listaTaxasRenovacao = listaTaxasRenovacao;
	}

	@Override
	public String toString() {
		return "GrupoTaxa [listaTaxasContratacao=" + listaTaxasContratacao + ", listaTaxasRenovacao=" + listaTaxasRenovacao + "]";
	}
	
}
