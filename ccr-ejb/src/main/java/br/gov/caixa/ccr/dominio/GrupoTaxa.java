package br.gov.caixa.ccr.dominio;

import java.util.Date;

import br.gov.caixa.ccr.util.Utilities;

public class GrupoTaxa extends Retorno {

	private Long id;
	private String nome;
	private Integer codigo;
	private String tipoOper;
	private String usuario;
	private Date data;
	
	
	public GrupoTaxa() {}
	
	public GrupoTaxa(Long id, String nome) {
		this.nome = nome;
		this.id = id;
	}

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

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getTipoOper() {
		return tipoOper;
	}

	public void setTipoOper(String tipoOper) {
		this.tipoOper = tipoOper;
	}
	

	
}
