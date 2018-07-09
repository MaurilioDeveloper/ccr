package br.gov.caixa.ccr.dominio.barramento.siabe;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="BENEFICIARIO")
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class Beneficiario implements Serializable {

	private static final long serialVersionUID = 2085679335971144978L;

	private String nome;
	private String cpf;
	private String dataNascimento;
	private String sexo;
	private String estadoCivil;
	private Endereco endereco;
	private Telefone telefone;
	private Beneficio beneficio;
	private CreditoPgto creditoPgto;
	private Conta conta;
	
	@XmlElement(name="NOME")
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}

	@XmlElement(name="CPF")
	public String getCpf() {
		return cpf;
	}
	
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	@XmlElement(name="DATA_NASCIMENTO")
	public String getDataNascimento() {
		return dataNascimento;
	}
	
	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	@XmlElement(name="SEXO")
	public String getSexo() {
		return sexo;
	}
	
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	@XmlElement(name="ESTADO_CIVIL")
	public String getEstadoCivil() {
		return estadoCivil;
	}
	
	public void setEstadoCivil(String estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	@XmlElement(name="ENDERECO")
	public Endereco getEndereco() {
		return endereco;
	}
	
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	@XmlElement(name="TELEFONE")
	public Telefone getTelefone() {
		return telefone;
	}
	
	public void setTelefone(Telefone telefone) {
		this.telefone = telefone;
	}

	@XmlElement(name="BENEFICIO")
	public Beneficio getBeneficio() {
		return beneficio;
	}
	
	public void setBeneficio(Beneficio beneficio) {
		this.beneficio = beneficio;
	}

	@XmlElement(name="CREDITO_PAGAMENTO")
	public CreditoPgto getCreditoPgto() {
		return creditoPgto;
	}
	
	public void setCreditoPgto(CreditoPgto creditoPgto) {
		this.creditoPgto = creditoPgto;
	}

	@XmlElement(name="CONTA")
	public Conta getConta() {
		return conta;
	}
	
	public void setConta(Conta conta) {
		this.conta = conta;
	}
	
}
