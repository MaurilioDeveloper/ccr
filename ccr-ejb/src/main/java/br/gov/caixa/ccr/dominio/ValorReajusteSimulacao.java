package br.gov.caixa.ccr.dominio;

public class ValorReajusteSimulacao {

	private double percentualFator;	
	private double valorIOFCalculado;	
	private double valorIOFComplementar;	
	private double valorSeguro;	
	private double valorTotal;

	public ValorReajusteSimulacao () {
		
	}
	
	public ValorReajusteSimulacao(double percentualFator, double valorIOFCalculado, 
								  double valorIOFComplementar, double valorSeguro, double valorTotal) {		
		this.percentualFator = percentualFator;
		this.valorIOFCalculado = valorIOFCalculado;
		this.valorIOFComplementar = valorIOFComplementar;
		this.valorSeguro = valorSeguro;
		this.valorTotal = valorTotal;
	}

	public double getPercentualFator() {
		return percentualFator;
	}

	public void setPercentualFator(double percentualFator) {
		this.percentualFator = percentualFator;
	}

	public double getValorIOFCalculado() {
		return valorIOFCalculado;
	}

	public void setValorIOFCalculado(double valorIOFCalculado) {
		this.valorIOFCalculado = valorIOFCalculado;
	}

	public double getValorIOFComplementar() {
		return valorIOFComplementar;
	}

	public void setValorIOFComplementar(double valorIOFComplementar) {
		this.valorIOFComplementar = valorIOFComplementar;
	}

	public double getValorSeguro() {
		return valorSeguro;
	}

	public void setValorSeguro(double valorSeguro) {
		this.valorSeguro = valorSeguro;
	}

	public double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(double valorTotal) {
		this.valorTotal = valorTotal;
	}
	
}
