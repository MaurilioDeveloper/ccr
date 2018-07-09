package br.gov.caixa.ccr.enums;

public enum EnumTransacaoAuditada {
	
	CONVENIO(1L, "Manter Convênio"),
	CANAL(2L, "Manter Canal"),
	CONTRATACAO(3L, "Manter Contração");
	
	private Long codigo;
	private String descricao;
	
	private EnumTransacaoAuditada(Long codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}
	
	 public static String valueById(long cod){  
	        for (EnumTransacaoAuditada p: EnumTransacaoAuditada.values()) {
	            if (p.getCodigo() == cod) {
	                return p.getDescricao();
	            }
	        }
	        return null;
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
