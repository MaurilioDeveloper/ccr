package br.gov.caixa.ccr.enums;

public enum EnumTipoAcao {
	
	INSERCAO("I", "Inserção"),
	ALTERACAO("A", "Alteração"),
	EXCLUSAO("E", "Exclusão");
	
	private String codigo;
	private String descricao;
	
	private EnumTipoAcao(String codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}
	
	 public static String valueById(String cod){  
	        for (EnumTipoAcao p: EnumTipoAcao.values()) {
	            if (p.getCodigo().equals(cod)) {
	                return p.getDescricao();
	            }
	        }
	        return null;
	 	}

	/**
	 * @return the codigo
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo the codigo to set
	 */
	public void setCodigo(String codigo) {
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
