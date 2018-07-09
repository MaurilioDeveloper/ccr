package br.gov.caixa.ccr.dominio.transicao;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import br.gov.caixa.ccr.util.Utilities;

@Entity
@Table(name="CCRTB004_CANAL_ATENDIMENTO")
public class CanalTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="NU_CANAL_ATENDIMENTO", nullable=false, unique=true, length=5)
	private int id;
	
	@Column(name="NO_CANAL_ATENDIMENTO", nullable=false, length=50)
	private String nome;

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
