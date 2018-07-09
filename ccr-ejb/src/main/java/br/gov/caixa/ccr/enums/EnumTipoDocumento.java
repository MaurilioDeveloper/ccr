package br.gov.caixa.ccr.enums;

public enum EnumTipoDocumento {
	
	TIPO_CONTRATO_CCR(1L, "Contrato CCR"),
	TIPO_PRESTAMISTA_CCR(2L, "Contrato Prestamista CCR");
	
	private Long codigo;
	private String descricao;
	
	private EnumTipoDocumento(Long codigo, String descricao) {
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
