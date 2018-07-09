package br.gov.caixa.ccr.dominio.transicao;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the CCRTB003_CONVENIO_CANAL database table.
 * 
 */
@Embeddable
public class ConvenioCanalPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="NU_CONVENIO", insertable=false, updatable=false)
	private Long idConvenio;

	@Column(name="NU_CANAL_ATENDIMENTO", insertable=false, updatable=false)
	private Long idCanalAtendimento;

	public ConvenioCanalPK() {
	}

	public ConvenioCanalPK(Long idConvenio, Long idCanalAtendimento) {
		super();
		this.idConvenio = idConvenio;
		this.idCanalAtendimento = idCanalAtendimento;
	}

	public Long getIdConvenio() {
		return idConvenio;
	}

	public void setIdConvenio(Long idConvenio) {
		this.idConvenio = idConvenio;
	}

	public long getIdCanalAtendimento() {
		return idCanalAtendimento;
	}

	public void setIdCanalAtendimento(long idCanalAtendimento) {
		this.idCanalAtendimento = idCanalAtendimento;
	}
	
	
}