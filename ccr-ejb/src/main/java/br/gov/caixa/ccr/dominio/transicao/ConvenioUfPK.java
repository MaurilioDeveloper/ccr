package br.gov.caixa.ccr.dominio.transicao;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the CCRTB045_CONVENIO_UF database table.
 * 
 */
@Embeddable
public class ConvenioUfPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="NU_CONVENIO", insertable=false, updatable=false)
	private Long idConvenio;

	@Column(name="SG_UF")
	private String uf;

	public ConvenioUfPK() {
	}
	public String getUf() {
		return this.uf;
	}
	public void setUf(String uf) {
		this.uf = uf;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ConvenioUfPK)) {
			return false;
		}
		ConvenioUfPK castOther = (ConvenioUfPK)other;
		return 
			(this.idConvenio == castOther.idConvenio)
			&& this.uf.equals(castOther.uf);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.idConvenio ^ (this.idConvenio >>> 32)));
		hash = hash * prime + this.uf.hashCode();
		
		return hash;
	}
	public Long getIdConvenio() {
		return idConvenio;
	}
	public void setIdConvenio(Long idConvenio) {
		this.idConvenio = idConvenio;
	}
	
	
}