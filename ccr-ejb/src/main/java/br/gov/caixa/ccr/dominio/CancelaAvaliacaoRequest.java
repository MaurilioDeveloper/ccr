package br.gov.caixa.ccr.dominio;

import java.io.Serializable;

public class CancelaAvaliacaoRequest implements Serializable {
	private static final long serialVersionUID = 8843133547175200186L;
	private String cpfCliente;
	private String codigoAvaliacao;
	private String numeroUnidade;
	public String getCpfCliente() {
		return cpfCliente;
	}
	public void setCpfCliente(String cpfCliente) {
		this.cpfCliente = cpfCliente;
	}

	public String getCodigoAvaliacao() {
		return codigoAvaliacao;
	}
	public void setCodigoAvaliacao(String codigoAvaliacao) {
		this.codigoAvaliacao = codigoAvaliacao;
	}
	public String getNumeroUnidade() {
		return numeroUnidade;
	}
	public void setNumeroUnidade(String numeroUnidade) {
		this.numeroUnidade = numeroUnidade;
	}
}

