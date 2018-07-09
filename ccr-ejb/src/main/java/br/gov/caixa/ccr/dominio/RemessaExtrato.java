package br.gov.caixa.ccr.dominio;

import br.gov.caixa.ccr.util.Utilities;

public class RemessaExtrato {

	private int id;
	private String nome;
	
	public RemessaExtrato() {}
	
	public RemessaExtrato(int id, String nome) {
		this.id = id;
		this.nome = nome;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return Utilities.leftRightTrim(nome);
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
