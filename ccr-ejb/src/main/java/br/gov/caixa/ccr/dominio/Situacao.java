package br.gov.caixa.ccr.dominio;

import br.gov.caixa.ccr.util.Utilities;

public class Situacao {

	private Long id;
	private Long tipo;
	private String descricao;
	
	public Situacao() {}
	
	public Situacao(Long id, Long tipo) {
		this.tipo = tipo;
		this.id = id;
	}
	
	public Situacao(Long id, Long tipo, String descricao) {
		this.tipo = tipo;
		this.id = id;
		this.descricao = descricao;
	}
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTipo() {
		return tipo;
	}

	public void setTipo(Long tipo) {
		this.tipo = tipo;
	}

	public String getDescricao() {
		return Utilities.leftRightTrim(descricao);
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
