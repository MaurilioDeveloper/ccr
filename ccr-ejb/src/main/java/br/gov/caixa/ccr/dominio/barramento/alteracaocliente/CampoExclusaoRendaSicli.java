package br.gov.caixa.ccr.dominio.barramento.alteracaocliente;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "EXCLUSAO_RENDA")
@XmlType(propOrder = { "codigo", "ocorrencia", "branco" })
public class CampoExclusaoRendaSicli implements Serializable {

	private static final long serialVersionUID = 1L;

	private String codigo = "701";

	private String ocorrencia;

	private String branco = "E";

	@XmlElement(name = "CODIGO")
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	@XmlElement(name = "OCORRENCIA")
	public String getOcorrencia() {
		return ocorrencia;
	}

	public void setOcorrencia(String ocorrencia) {
		this.ocorrencia = ocorrencia;
	}

	@XmlElement(name = "BRANCO")
	public String getBranco() {
		return branco;
	}

	public void setBranco(String branco) {
		this.branco = branco;
	}

}
