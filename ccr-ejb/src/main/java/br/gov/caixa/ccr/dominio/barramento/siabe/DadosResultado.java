package br.gov.caixa.ccr.dominio.barramento.siabe;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="DADOS")
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class DadosResultado implements Serializable {

	private static final long serialVersionUID = 7953570404795934959L;
	
	private Beneficios beneficios;
	private Beneficiario beneficiario;
	private String excecao;
	
	@XmlElement(name="BENEFICIARIO")
	public Beneficiario getBeneficiario() {
		return beneficiario;
	}

	public void setBeneficiario(Beneficiario beneficiario) {
		this.beneficiario = beneficiario;
	}
	
	@XmlElement(name="BENEFICIOS")
	public Beneficios getBeneficios() {
		return beneficios;
	}

	public void setBeneficios(Beneficios beneficios) {
		this.beneficios = beneficios;
	}

	
	@XmlElement(name="EXCECAO")
	public String getExcecao() {
		return excecao;
	}

	public void setExcecao(String excecao) {
		this.excecao = excecao;
	}	
	
}
