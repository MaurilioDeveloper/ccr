package br.gov.caixa.ccr.dominio.barramento.siric;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="DADOS")
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class DadosResultadoConsulta implements Serializable {

	private static final long serialVersionUID = 7953576004795934959L;
	
	private ConsultaAvaliacao consulta;
	private String excecao;

	@XmlElement(name="CONSULTA")
	public ConsultaAvaliacao getConsulta() {
		return consulta;
	}

	public void setConsulta(ConsultaAvaliacao consulta) {
		this.consulta = consulta;
	}
	
	@XmlElement(name = "EXCECAO")
	public String getExcecao() {
		return excecao;
	}

	public void setExcecao(String excecao) {
		this.excecao = excecao;
	}
	
}
