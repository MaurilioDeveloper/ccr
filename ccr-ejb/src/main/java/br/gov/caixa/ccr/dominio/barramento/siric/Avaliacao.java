package br.gov.caixa.ccr.dominio.barramento.siric;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="AVALIACAO")
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class Avaliacao implements Serializable {

	private static final long serialVersionUID = 1956177309378989573L;

	private Aprovada aprovada;	
	private Condicionada condicionada;	
	private Reprovada reprovada;	
	private MensagemRetorno mensagem;	
	private TomadorValida tomadorValida;

	@XmlElement(name = "APROVADA")
	public Aprovada getAprovada() {
		return aprovada;
	}

	public void setAprovada(Aprovada aprovada) {
		this.aprovada = aprovada;
	}

	@XmlElement(name = "CONDICIONADA")
	public Condicionada getCondicionada() {
		return condicionada;
	}

	public void setCondicionada(Condicionada condicionada) {
		this.condicionada = condicionada;
	}

	@XmlElement(name = "REPROVADA")
	public Reprovada getReprovada() {
		return reprovada;
	}

	public void setReprovada(Reprovada reprovada) {
		this.reprovada = reprovada;
	}

	@XmlElement(name = "MENSAGEM_RETORNO")
	public MensagemRetorno getMensagem() {
		return mensagem;
	}

	public void setMensagem(MensagemRetorno mensagem) {
		this.mensagem = mensagem;
	}

	@XmlElement(name = "TOMADOR_VALIDA")
	public TomadorValida getTomadorValida() {
		return tomadorValida;
	}

	public void setTomadorValida(TomadorValida tomadorValida) {
		this.tomadorValida = tomadorValida;
	}
		
}
