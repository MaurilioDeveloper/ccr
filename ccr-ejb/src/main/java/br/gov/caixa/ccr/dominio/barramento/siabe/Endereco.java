package br.gov.caixa.ccr.dominio.barramento.siabe;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="ENDERECO")
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class Endereco implements Serializable {

	private static final long serialVersionUID = 2036750851247390965L;

	private String logradouro;	
	private String bairro;	
	private String cidade;	
	private String uf;	
	private String cep;

	@XmlElement(name="LOGRADOURO")
	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	@XmlElement(name="BAIRRO")
	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	@XmlElement(name="CIDADE")
	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	@XmlElement(name="UF")
	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	@XmlElement(name="CEP")
	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}
	
}
