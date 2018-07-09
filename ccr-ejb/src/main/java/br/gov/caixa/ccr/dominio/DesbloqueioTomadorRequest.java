package br.gov.caixa.ccr.dominio;

import java.io.Serializable;

public class DesbloqueioTomadorRequest  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String usuarioGerencial; 
	private String usuarioLogado;
	private String agenciaLogado;
	private String cpf;
	private String codigoAvaliacao;
	
	public String getUsuarioGerencial() {
		return usuarioGerencial;
	}
	public void setUsuarioGerencial(String usuarioGerencial) {
		this.usuarioGerencial = usuarioGerencial;
	}
	public String getUsuarioLogado() {
		return usuarioLogado;
	}
	public void setUsuarioLogado(String usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}
	public String getAgenciaLogado() {
		return agenciaLogado;
	}
	public void setAgenciaLogado(String agenciaLogado) {
		this.agenciaLogado = agenciaLogado;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getCodigoAvaliacao() {
		return codigoAvaliacao;
	}
	public void setCodigoAvaliacao(String codigoAvaliacao) {
		this.codigoAvaliacao = codigoAvaliacao;
	}
	
	
}
