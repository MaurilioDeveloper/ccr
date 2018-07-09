package br.gov.caixa.ccr.dominio.barramento.alteracaocliente;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "DADOS")
@XmlSeeAlso({ CamposSicli.class })
@XmlType(propOrder = { "codigo", "camposSicli" })
public class AtualizacaoCliente implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String codigo;

	private CamposSicli camposSicli;

	@XmlElement(name="COCLI")
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	@XmlElement(name="CAMPOS")
	public CamposSicli getCamposSicli() {
		return camposSicli;
	}

	public void setCamposSicli(CamposSicli camposSicli) {
		this.camposSicli = camposSicli;
	}

}
