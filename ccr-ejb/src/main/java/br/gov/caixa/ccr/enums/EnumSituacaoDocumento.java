package br.gov.caixa.ccr.enums;

public enum EnumSituacaoDocumento {
	
	ATIVO(1L, "A"),
	INATIVO(2L, "I");
	
	private Long codigo;
	private String valor;

	private EnumSituacaoDocumento(Long codigo, String valor) {
		this.codigo = codigo;
		this.valor = valor;
	}
	
	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	
}
