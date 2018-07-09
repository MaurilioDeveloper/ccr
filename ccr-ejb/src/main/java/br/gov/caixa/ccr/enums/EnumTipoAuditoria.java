package br.gov.caixa.ccr.enums;

public enum EnumTipoAuditoria {

	ALTERACAO(1L, "A"),
	EXCLUSAO(2L, "E"),
	INCLUSAO(3L, "I");
	
	private Long codigo;
	private String descricao;
	
	private EnumTipoAuditoria(Long codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	/**
	 * @return the codigo
	 */
	public Long getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo the codigo to set
	 */
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return the descricao
	 */
	public String getDescricao() {
		return descricao;
	}

	/**
	 * @param descricao the descricao to set
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	
	
}
