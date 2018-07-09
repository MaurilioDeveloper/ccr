package br.gov.caixa.ccr.dominio;

public class Mensageria<T> {

	private String correlationId;	
	private T dados;

	public String getCorrelationId() {
		return correlationId;
	}

	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}

	public T getDados() {
		return dados;
	}

	public void setDados(T dados) {
		this.dados = dados;
	}
	
}
