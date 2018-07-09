package br.gov.caixa.ccr.dominio.transicao;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the CCRTB070_TIPO_MODELO database table.
 * 
 */
@Entity
@Table(name="CCRTB070_TIPO_MODELO")
public class TipoDocumentoTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CCRTB070_TIPO_MODELO_IDTIPODOCUMENTO_GENERATOR", sequenceName="CCRSQ012_NU_SEQ_TIPO_DOC")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CCRTB070_TIPO_MODELO_IDTIPODOCUMENTO_GENERATOR")
	@Column(name="NU_TIPO_MODELO")
	private Long idTipoDocumento;

	@Column(name="NO_TIPO_MODELO")
	private String noTipoDocumento;


	public TipoDocumentoTO() {
	}
	
	public TipoDocumentoTO(Long id) {
		super();
		this.idTipoDocumento = id;
	}

	public Long getIdTipoDocumento() {
		return this.idTipoDocumento;
	}

	public void setIdTipoDocumento(Long idTipoDocumento) {
		this.idTipoDocumento = idTipoDocumento;
	}

	public String getNoTipoDocumento() {
		return this.noTipoDocumento;
	}

	public void setNoTipoDocumento(String noTipoDocumento) {
		this.noTipoDocumento = noTipoDocumento;
	}




}