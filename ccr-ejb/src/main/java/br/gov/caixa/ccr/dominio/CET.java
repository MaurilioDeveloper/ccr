package br.gov.caixa.ccr.dominio;

public class CET {

	private double txMensal;	
	private double txAnual;

	public CET() {
		
	}
	
	public CET(double txMensal, double txAnual) {
		this.txMensal = txMensal;
		this.txAnual = txAnual;
	}

	public double getTxMensal() {
		return txMensal;
	}

	public void setTxMensal(double txMensal) {
		this.txMensal = txMensal;
	}

	public double getTxAnual() {
		return txAnual;
	}

	public void setTxAnual(double txAnual) {
		this.txAnual = txAnual;
	}
	
}
