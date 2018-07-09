package br.gov.caixa.ccr.dominio;

import br.gov.caixa.ccr.util.Utilities;

public class Canal { 
	
	private Long id;
	private String nome;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return Utilities.leftRightTrim(nome);
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
}
