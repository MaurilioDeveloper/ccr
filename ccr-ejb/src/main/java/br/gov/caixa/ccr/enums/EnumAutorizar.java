package br.gov.caixa.ccr.enums;

public enum EnumAutorizar {
	REJEITAR("N"), AUTORIZAR("A");
	
	private String codigo;
	
	private EnumAutorizar(String codigo) {
		this.codigo = codigo;
	}

	public String getCodigo() {
		return codigo;
	}


	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

}
