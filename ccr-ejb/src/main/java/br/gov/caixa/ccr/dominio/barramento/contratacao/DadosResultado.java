package br.gov.caixa.ccr.dominio.barramento.contratacao;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="DADOS")
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class DadosResultado implements Serializable {

	private static final long serialVersionUID = 2183353433837547599L;
	
	private String excecao;
	private String canal;    
	private String servico;  
	private String numeroSimulacao;
	private String matricula; 
	private String cpfCliente; 
	private String pv;
	private String numeroContrato; 

	@XmlElement(name="EXCECAO")
	public String getExcecao() {
		return excecao;
	}

	public void setExcecao(String excecao) {
		this.excecao = excecao;
	}

	@XmlElement(name="EXCECAO")
	public String getCanal() {
		return canal;
	}

	public void setCanal(String canal) {
		this.canal = canal;
	}

	@XmlElement(name="EXCECAO")
	public String getServico() {
		return servico;
	}

	public void setServico(String servico) {
		this.servico = servico;
	}

	@XmlElement(name="EXCECAO")
	public String getNumeroSimulacao() {
		return numeroSimulacao;
	}

	public void setNumeroSimulacao(String numeroSimulacao) {
		this.numeroSimulacao = numeroSimulacao;
	}

	@XmlElement(name="EXCECAO")
	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	@XmlElement(name="EXCECAO")
	public String getCpfCliente() {
		return cpfCliente;
	}

	public void setCpfCliente(String cpfCliente) {
		this.cpfCliente = cpfCliente;
	}

	@XmlElement(name="EXCECAO")
	public String getPv() {
		return pv;
	}

	public void setPv(String pv) {
		this.pv = pv;
	}

	@XmlElement(name="EXCECAO")
	public String getNumeroContrato() {
		return numeroContrato;
	}

	public void setNumeroContrato(String numeroContrato) {
		this.numeroContrato = numeroContrato;
	}
	
}
