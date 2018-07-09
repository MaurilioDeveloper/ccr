package br.gov.caixa.ccr.dominio.transicao;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the CCRTBH01_CONVENIO database table.
 * 
 */
@Embeddable
public class Ccrtbh01ConvenioPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="NU_HISTORICO")
	private long id;

	@Column(name="NU_CONVENIO", insertable=false, updatable=false)
	private long nuConvenio;

	public Ccrtbh01ConvenioPK() {
	}
	public long getId() {
		return this.id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getNuConvenio() {
		return this.nuConvenio;
	}
	public void setNuConvenio(long nuConvenio) {
		this.nuConvenio = nuConvenio;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof Ccrtbh01ConvenioPK)) {
			return false;
		}
		Ccrtbh01ConvenioPK castOther = (Ccrtbh01ConvenioPK)other;
		return 
			(this.id == castOther.id)
			&& (this.nuConvenio == castOther.nuConvenio);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.id ^ (this.id >>> 32)));
		hash = hash * prime + ((int) (this.nuConvenio ^ (this.nuConvenio >>> 32)));
		
		return hash;
	}
}