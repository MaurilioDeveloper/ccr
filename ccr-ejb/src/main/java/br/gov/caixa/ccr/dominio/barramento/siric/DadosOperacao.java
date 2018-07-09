package br.gov.caixa.ccr.dominio.barramento.siric;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="DADOS_OPERACAO")
@XmlType(propOrder={"agencia", "produto", "financiamento", "prazo", "prestacao", "margem", "convenente"})
public class DadosOperacao implements Serializable {

	private static final long serialVersionUID = -9108351328917518364L;

	private String agencia;
	
	@XmlElement(name = "PRODUTO")
	private String produto = "110";
	
	private String financiamento;	
	private String prazo;	
	private String prestacao;	
	private String margem;	
	private Convenente convenente;

	public DadosOperacao() {
		this.convenente = new Convenente();
	}
	
	@XmlElement(name = "AGENCIA")
	public String getAgencia() {
		return agencia;
	}

	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}

	@XmlElement(name = "VR_FINANCIAMENTO")
	public String getFinanciamento() {
		return financiamento;
	}

	public void setFinanciamento(String financiamento) {
		this.financiamento = financiamento;
	}

	@XmlElement(name = "PRAZO")
	public String getPrazo() {
		return prazo;
	}

	public void setPrazo(String prazo) {
		this.prazo = prazo;
	}

	@XmlElement(name = "VR_PRESTACAO_NECESSARIA")
	public String getPrestacao() {
		return prestacao;
	}

	public void setPrestacao(String prestacao) {
		this.prestacao = prestacao;
	}

	@XmlElement(name = "VR_MARGEM_CONSIGNAVEL")
	public String getMargem() {
		return margem;
	}

	public void setMargem(String margem) {
		this.margem = margem;
	}

	@XmlElement(name = "CONVENENTE")
	public Convenente getConvenente() {
		return convenente;
	}

	public void setConvenente(Convenente convenente) {
		this.convenente = convenente;
	}
	
}
