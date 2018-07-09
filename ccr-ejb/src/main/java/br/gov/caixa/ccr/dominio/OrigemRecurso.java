package br.gov.caixa.ccr.dominio;

public class OrigemRecurso {
	
	private Long id;
	private String nome;

	public OrigemRecurso() {}
	
	public OrigemRecurso(Long id, String nome) {
		this.nome = nome;
		this.id = id;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
