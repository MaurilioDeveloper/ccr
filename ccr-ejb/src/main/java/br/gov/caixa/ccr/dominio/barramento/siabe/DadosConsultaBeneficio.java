package br.gov.caixa.ccr.dominio.barramento.siabe;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="DADOS")
@XmlType(propOrder={"cpf", "beneficio"})
public class DadosConsultaBeneficio implements Serializable {

	private static final long serialVersionUID = -6611739565351994235L;
	
	public static final String OPERACAO_CPF = "CONSULTA_BENEFICIO_CPF";
	public static final String OPERACAO_BENEFICIO = "CONSULTA_BENEFICIARIO";

	private String cpf;
	private Beneficio beneficio;
	
	@XmlElement(name="CPF")
	public String getCpf() {
		return cpf;
	}
	
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	@XmlElement(name="BENEFICIO")
	public Beneficio getBeneficio() {
		return beneficio;
	}
	
	public void setBeneficio(Beneficio beneficio) {
		this.beneficio = beneficio;
	}
		
}
