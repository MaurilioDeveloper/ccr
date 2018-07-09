package br.gov.caixa.ccr.dominio.barramento.siabe;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="BENEFICIO")
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class BeneficioDetalhe implements Serializable {

	private static final long serialVersionUID = -7492112899781726647L;

	private String numero;
	private String dv;
	private String dataNascimento;
	private CreditoPgto creditoPgto;
	private String cpf;

	@XmlElement(name="NUMERO")
	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	@XmlElement(name="DV")
	public String getDv() {
		return dv;
	}

	public void setDv(String dv) {
		this.dv = dv;
	}

	@XmlElement(name="DATA_NASCIMENTO")
	public String getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	@XmlElement(name="CREDITO_PAGAMENTO")
	public CreditoPgto getCreditoPgto() {
		return creditoPgto;
	}

	public void setCreditoPgto(CreditoPgto creditoPgto) {
		this.creditoPgto = creditoPgto;
	}

	@XmlElement(name="CPF")
	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
}
