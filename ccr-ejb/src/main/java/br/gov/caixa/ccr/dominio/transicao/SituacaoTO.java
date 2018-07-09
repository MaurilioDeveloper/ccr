package br.gov.caixa.ccr.dominio.transicao;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.gov.caixa.ccr.util.Utilities;

@Entity
@Table(name="CCRTB014_SITUACAO")
public class SituacaoTO implements Serializable {

	private static final long serialVersionUID = -6624163043634730913L;
	
	@Id
	@Column(name="NU_SITUACAO", nullable=false)
	private Long id;
	
	@Id
	@Column(name="NU_TIPO_SITUACAO", nullable=false)
	private Long tipo;
	
	@Column(name="DE_SITUACAO", nullable=false, length=80)
	private String descricao;
	
	public SituacaoTO() {
	}

	public SituacaoTO(Long id) {
		super();
		this.id = id;
	}

	public SituacaoTO(Long id, Long tipo) {
		super();
		this.id = id;
		this.tipo = tipo;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public String getDescricao() {
		return Utilities.leftRightTrim(descricao);
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Long getTipo() {
		return tipo;
	}

	public void setTipo(Long tipo) {
		this.tipo = tipo;
	}

}
