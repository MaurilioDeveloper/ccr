package br.gov.caixa.ccr.enums;

public enum EnumSituacaoArquivo {

	INICIAL(1L, "Inicial"),
	PROCESSADO(2L, "Processado"),
	PROCESSAMENTOCERRO(3L, "Processamento com erro"),
	NAOPROCESSADO(4L, "Não Processado");
	
	private Long codigo;
	private String descricao;
	
	private EnumSituacaoArquivo(Long codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}


	 public static String valueById(long cod){  
        for (EnumSituacaoArquivo p: EnumSituacaoArquivo.values()) {
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
