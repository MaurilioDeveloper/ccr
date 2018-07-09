package br.gov.caixa.ccr.dominio;

public class PrestacaoPrice {

	private double amortizacao;	
	private double juros;

	public PrestacaoPrice() {
		
	}
	
	public PrestacaoPrice(double amortizacao, double juros) {
		this.amortizacao = amortizacao;
		this.juros = juros;
	}
	
	public double getAmortizacao() {
		return amortizacao;
	}

	public void setAmortizacao(double amortizacao) {
		this.amortizacao = amortizacao;
	}

	public double getJuros() {
		return juros;
	}

	public void setJuros(double juros) {
		this.juros = juros;
	}
	
	public double getPrestacao(){
		return this.amortizacao + this.juros;
	}
	
}
