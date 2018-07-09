package br.gov.caixa.ccr.dominio.barramento.siric;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="DADOS")
@XmlType(propOrder={"consulta"})
public class DadosConsultaAvaliacao implements Serializable {

	private static final long serialVersionUID = -951661454351609482L;	
	public static final String OPERACAO = "CONSULTAAVALIACAORISCOCREDITO";
	
	private ConsultaAvaliacao consulta;

	public DadosConsultaAvaliacao() {
		
	}
	
	public DadosConsultaAvaliacao(String cpf) {
		this.consulta = new ConsultaAvaliacao(cpf);
	}
	
	@XmlElement(name="CONSULTA")
	public ConsultaAvaliacao getConsulta() {
		return consulta;
	}

	public void setConsulta(ConsultaAvaliacao consulta) {
		this.consulta = consulta;
	}
		
}
