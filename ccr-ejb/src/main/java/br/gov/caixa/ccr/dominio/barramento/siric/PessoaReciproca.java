package br.gov.caixa.ccr.dominio.barramento.siric;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="PESSOA_RECIPROCA")
@XmlType(propOrder={"tipo", "cnpj"})
public class PessoaReciproca implements Serializable {

	private static final long serialVersionUID = 1187993758164395595L;

	private String tipo;	
	private String cnpj;

	@XmlElement(name = "TP_PESSOA")
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	@XmlElement(name = "CNPJ")
	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
		
}
