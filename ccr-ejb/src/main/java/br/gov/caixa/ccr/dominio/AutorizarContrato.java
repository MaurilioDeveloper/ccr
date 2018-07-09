package br.gov.caixa.ccr.dominio;

import br.gov.caixa.ccr.enums.EnumAutorizar;

public class AutorizarContrato {
	
	private String idContrato;	
	private EnumAutorizar autorizar;
	private String justificativa;
	
	
	public String getIdContrato() {
		return idContrato;
	}
	public void setIdContrato(String idContrato) {
		this.idContrato = idContrato;
	}
	public EnumAutorizar getAutorizar() {
		return autorizar;
	}
	public void setAutorizar(EnumAutorizar autorizar) {
		this.autorizar = autorizar;
	}
	public String getJustificativa() {
		return justificativa;
	}
	public void setJustificativa(String justificativa) {
		this.justificativa = justificativa;
	}
	
	
}
