package br.gov.caixa.ccr.enums;

public enum EnumCampoAuditoria {

	CONVENIO(1L, "NOME CONVÊNIO"),
	GTAXAS(2L, "GRUPO TAXAS"),
	GAVERBACAO(3L, "GRUPO AVERBAÇÃO"),
	SCONVENIO(4L, "SITUAÇÃO CONVÊNIO"),
	ABRANGENCIA(5L, "ABRANGÊNCIA"),
	UFABRANGENCIA(6L, "UF ABRANGÊNCIA"),
	SRABRANGENCIA(7L, "SR ABRANGÊNCIA"),
	CORGAO(8L, "CÓDIGO ÓRGÃO"),
	SRRESPONSAVEL(9L, "SR RESPONSÁVEL"),
	ARESPONSAVEL(10L, "AGÊNCIA RESPONSÁVEL"),
	SRCOBRANCA(11L, "SR COBRANÇA"),
	ACOBRANCA(12L, "AGÊNCIA COBRANÇA"),
	DCSALARIO(13L, "DIA CRÉDITO SALÁRIO"),
	PEEXTRATO(14L, "PRAZO EMISSÃO EXTRATO"),
	PMAUTORIZACAO(15L, "PRAZO MÁXIMO AUTORIZAÇÃO"),
	QTDEMPREGADOS(16L, "QUANTIDADE EMPREGADOS"),
	CONTACORRENTE(17L, "CONTA CORRENTE"),
	CNPJVINCULADO(18L, "CNPJ VINCULADO"),
	NOMECANAL(19L, "NOME CANAL"),
	GCONDICOES(20L, "GARANTIA CONDIÇÕES"),
	EANUENCIA(21L, "EXIGE ANUÊNCIA"),
	PCONTRATACAO(22L, "PERMITE CONTRATAÇÃO"),
	EAMCONTRATACAO(23L, "EXIGE AUTORIZAÇÃO MARGEM CONTRATAÇÃO"),
	PMINCONTRATACAO(24L, "PRAZO MÍNIMO CONTRATAÇÃO"),
	PMAXCONTRATACAO(25L, "PRAZO MÁXIMO CONTRATAÇÃO"),
	FJCONTRATACAO(26L, "FAIXA JUROS CONTRATAÇÃO"),
	PRENOVACAO(27L, "PERMITE RENOVAÇÃO"),
	EAMRENOVACAO(28L, "EXIGE AUTORIZAÇÃO MARGEM RENOVAÇÃO"),
	PMINRENOVACAO(29L, "PRAZO MÍNIMO RENOVAÇÃO"),
	PMAXRENOVACAO(30L, "PRAZO MÁXIMO RENOVAÇÃO"),
	FJRENOVACAO(31L, "FAIXA JUROS RENOVAÇÃO"),
	CONTRATO(32L, "CONTRATO"),
	ICONTRATO(33L, "IMPRIMI CONTRATO"),
	AGAPROVADA(34L, "AUTORIZAÇÃO GERENCIAL APROVADA"),
	AGNEGADA(35L, "AUTORIZAÇÃO GERENCIAL NEGADA"),
	ACAPROVADA(36L, "AUTORIZAÇÃO CONFORMIDADE APROVADA"),
	ACNEGADA(37L, "AUTORIZAÇÃO CONFORMIDADE NEGADA");
	
	
	private Long codigo;
	private String descricao;
	
	
	private EnumCampoAuditoria(Long codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}
	
	 public static Long valueByDesc(String desc){  
	        for (EnumCampoAuditoria p: EnumCampoAuditoria.values()) {
	            if (p.getDescricao().equals(desc)) {
	                return p.getCodigo();
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
