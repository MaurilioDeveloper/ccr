package br.gov.caixa.ccr.enums;

public enum EnumSituacaoContrato {
	
	SIMULACAO(1L, "Simulação"),
	AGUARDANDO_AUTORIZACOES(2L, "Aguardando Autorizações"),
	APROVADA_GERENCIAL(3L, "Autorização Contratação Aprovada"),
	APROVADA_CONFORMIDADE(4L, "Autorização Confirmação Alçada Aprovada"),
	NEGADA_GERENCIAL(5L, "Autorização Contratação Negada"),
	NEGADA_CONFORMIDADE(6L, "Autorização Confirmação Alçada Negada"),
	AUTORIZADO(7L, "Contrato Autorizado"),
	SIAPX(8L,"Contrato enviado SIAPX"),
	NEGADA(9L, "Contrato Negado"),
	CANCELADO(12L, "Cancelado"),
	AGUARDANDO_DESBLOQUEIO(13L, "Aguardando Desbloqueio Comercial"),
	AGUARDANDO_AVALIACAO_OPERACAO(14L, "Aguardando Avaliação Operação"),
	AGUARDANDO_CONTRATAR(15L, "Aguardando Contratar"),
	AGUARDANDO_AUTORIZACAO_ALCADA(16L, "Aguardando Autorização Alçada");
	
	
	private Long codigo;
	private String descricao;
	
	private EnumSituacaoContrato(Long codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}
	
	 public static String valueById(long cod){  
	        for (EnumSituacaoContrato p: EnumSituacaoContrato.values()) {
	            if (p.getCodigo() == cod) {
	                return p.getDescricao();
	            }
	        }
	        return null;
	    } 

	public Long getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}	
}
