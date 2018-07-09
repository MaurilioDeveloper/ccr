package br.gov.caixa.ccr.dominio.transicao;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;

/**
 * The primary key class for the CCRTB047_CONVENIO_SR database table.
 * 
 */
@Embeddable
public class ConvenioSrPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5611194263288939466L;

	@Column(name="NU_CONVENIO", insertable=false, updatable=false)
	private Long idConvenio;

	@Column(name="NU_UNIDADE")
	private Long idUnidade;

	@Column(name="NU_NATURAL")
	private Long idNatural;

	public ConvenioSrPK() {
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ConvenioSrPK)) {
			return false;
		}
		ConvenioSrPK castOther = (ConvenioSrPK)other;
		return 
			(this.idConvenio == castOther.idConvenio)
			&& (this.idUnidade == castOther.idUnidade)
			&& (this.idNatural == castOther.idNatural);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.idConvenio ^ (this.idConvenio >>> 32)));
		hash = hash * prime + ((int) (this.idUnidade ^ (this.idUnidade >>> 32)));
		hash = hash * prime + ((int) (this.idNatural ^ (this.idNatural >>> 32)));
		
		return hash;
	}

	public Long getIdConvenio() {
		return idConvenio;
	}

	public void setIdConvenio(Long idConvenio) {
		this.idConvenio = idConvenio;
	}

	public Long getIdUnidade() {
		return idUnidade;
	}

	public void setIdUnidade(Long idUnidade) {
		this.idUnidade = idUnidade;
	}

	public Long getIdNatural() {
		return idNatural;
	}

	public void setIdNatural(Long idNatural) {
		this.idNatural = idNatural;
	}

	
	
}