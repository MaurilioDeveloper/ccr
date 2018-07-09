package br.gov.caixa.ccr.template;

public class Arquivo {

	private String nome;
	private byte[] dados;

	public Arquivo(String nome, byte[] dados) {
		super();
		this.nome = nome;
		this.dados = dados;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public byte[] getDados() {
		return dados;
	}

	public void setDados(byte[] dados) {
		this.dados = dados;
	}
}