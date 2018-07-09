package br.gov.caixa.ccr.dominio.barramento.siric;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="ERROS_VALIDACAO")
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class ErroValidacao implements Serializable {

	private static final long serialVersionUID = 7104305392922199569L;

	private String tipo;	
	private String item;	
	private String erro;

	@XmlElement(name = "TIPO")
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	@XmlElement(name = "ITEM")
	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	@XmlElement(name = "ERRO")
	public String getErro() {
		return erro;
	}

	public void setErro(String erro) {
		this.erro = erro;
	}
	
}
