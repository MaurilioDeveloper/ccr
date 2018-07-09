package br.gov.caixa.ccr.dominio;

public class ArquivoContratoErro {

	private long nuContrato;
	private long codErro;
	private String descErro;
	
	public long getNuContrato() {
		return nuContrato;
	}
	
	public void setNuContrato(long nuContrato) {
		this.nuContrato = nuContrato;
	}
	
	public long getCodErro() {
		return codErro;
	}
	
	public void setCodErro(long codErro) {
		this.codErro = codErro;
	}
	
	public String getDescErro() {
		return descErro;
	}
	
	public void setDescErro(String descErro) {
		this.descErro = descErro;
	}
	
	
	
}
