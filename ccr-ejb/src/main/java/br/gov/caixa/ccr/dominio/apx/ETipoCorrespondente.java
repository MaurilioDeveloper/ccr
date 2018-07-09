package br.gov.caixa.ccr.dominio.apx;

/**
 * 
 * @author c110503
 *
 */
public enum ETipoCorrespondente {
	
	
	LOTERICO (1),
	BANCARIO (2);
	
	// Definicao das constantes
	
	private final Integer valor;
	

	// método que define as constantes
	private ETipoCorrespondente(Integer valor) {
		this.valor = valor;
		
	}

	// GET e SET

	public Integer getValor() {
		return valor;
	}	

}
