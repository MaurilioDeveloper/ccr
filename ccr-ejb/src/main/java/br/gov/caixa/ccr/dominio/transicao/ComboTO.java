package br.gov.caixa.ccr.dominio.transicao;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="COMBO_GENERICO")
public class ComboTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="ID", nullable=false, unique=true)
	private String id;
	
	@Column(name="DESCRICAO", nullable=false)
	private String descricao;
	
	@Column(name="CODIGO")
	private String codigo;

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

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	
	
	
}
