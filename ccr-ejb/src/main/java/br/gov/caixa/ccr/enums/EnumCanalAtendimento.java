package br.gov.caixa.ccr.enums;

/**
 * Codigo identificador de canal de atendimento, numerico(2).
 * 01 - Agencia
 * 02 - Intranet
 * ...
 * @author TIVIT
 *
 */

public enum EnumCanalAtendimento {
	
	AGENCIA(01, "Agencia"),
	INTRANET(02, "Intranet");
	
	private Integer codigo;
	private String descricao;
	
	private EnumCanalAtendimento(Integer codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public int getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}	
}
