package br.gov.caixa.ccr.enums;

public enum EnumTipoAutorizacao {
	GERENCIAL("G"), SEGUNDOGERENTE("C");
	private String codigo;
	
	private EnumTipoAutorizacao(String codigo) {
		this.codigo = codigo;
	}

	public String getCodigo() {
		return codigo;
	}


	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
}
