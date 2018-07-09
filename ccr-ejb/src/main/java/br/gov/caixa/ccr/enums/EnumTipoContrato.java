package br.gov.caixa.ccr.enums;

public enum EnumTipoContrato {
	
	CONVENIO(1L, "CONVENIO"),
	CONTRATO(2L, "CONTRATO");
	
	private Long codigo;
	private String descricao;
	
	private EnumTipoContrato(Long codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	
	
}
