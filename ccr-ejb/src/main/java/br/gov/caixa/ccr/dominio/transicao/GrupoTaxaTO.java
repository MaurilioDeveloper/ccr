package br.gov.caixa.ccr.dominio.transicao;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;


/**
 * The persistent class for the CCRTB002_GRUPO_TAXA database table.
 * 
 */
@Entity
@Table(name="CCRTB002_GRUPO_TAXA")
public class GrupoTaxaTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="NU_GRUPO_TAXA")
	private Long id;

	@Column(name="NO_GRUPO_TAXA")
	private String nome;
	
	//@Transient
	//TODO SKMT fazer UNIQUE INDEX
	@Column(name="CO_GRUPO_TAXA",unique=true)
	private Integer codigo;
	
	//@Transient
	@Column(name="CO_USUARIO_ALTERACAO")
	private String usuario;
	
	//@Transient
	@Column(name="TS_ALTERACAO")
	private Date data;

	
	public GrupoTaxaTO() {
	}

	public GrupoTaxaTO(Long id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return this.nome;
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
	
	

}