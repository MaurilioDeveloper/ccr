package br.gov.caixa.ccr.dominio;

public class Combo extends Retorno {
	
	private String id;	
	private String descricao;
	private int dominio;
	private String filtroNumerico;	
	private String filtroTextual;
	private String codigo;

	public Combo() {
		this.setAll(0L, "", Retorno.SUCESSO);
	}
	
	public Combo(Long codigo, String mensagem, String tipo) {
		this.setAll(codigo, mensagem, tipo);
	}

	public Combo(Long codigo, String mensagem, String tipo, String idMsg) {
		this.setAll(codigo, mensagem, tipo, idMsg);
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public int getDominio() {
		return dominio;
	}

	public void setDominio(int dominio) {
		this.dominio = dominio;
	}

	public String getFiltroNumerico() {
		return filtroNumerico;
	}

	public void setFiltroNumerico(String filtroNumerico) {
		this.filtroNumerico = filtroNumerico;
	}

	public String getFiltroTextual() {
		return filtroTextual;
	}

	public void setFiltroTextual(String filtroTextual) {
		this.filtroTextual = filtroTextual;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	
	
}
