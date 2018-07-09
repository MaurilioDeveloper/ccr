package br.gov.caixa.ccr.enums;

public enum EnumSituacaoAutorizacao {

	GERENCIALNEGADA(1L, "Autorização Gerencial Negada"), 
	GERENCIALAPROVADA(2L, "Autorização Gerencial Aprovada"), 
	CONFORMIDADEAPROVADA(3L, "Autorização Conformidade Aprovada"), 
	CONFORMIDADENEGADA(4L, "Autorização Conformidade Negada");

	private Long codigo;
	private String descricao;

	private EnumSituacaoAutorizacao(Long codigo, String descricao) {
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

	public static String valueById(long cod) {
		for (EnumSituacaoAutorizacao p : EnumSituacaoAutorizacao.values()) {
			if (p.getCodigo() == cod) {
				return p.getDescricao();
			}
		}
		return null;
	}

}
