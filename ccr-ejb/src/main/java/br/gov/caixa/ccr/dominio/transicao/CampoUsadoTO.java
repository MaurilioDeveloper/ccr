package br.gov.caixa.ccr.dominio.transicao;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the CCRTB073_CAMPO_MODELO_CONTRATO database table.
 * 
 */
@Entity
@Table(name="CCRTB073_CAMPO_MODELO_CONTRATO")
public class CampoUsadoTO implements Serializable {
	private static final long serialVersionUID = 1L;

	//bi-directional many-to-one association to DocumentoTO
	@Id
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="NU_MODELO")
	private DocumentoTO documento;

	//bi-directional many-to-one association to CampoTO
	@Id
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="NU_CAMPO")
	private CampoTO campo;

	public CampoUsadoTO() {
	}

	public CampoTO getCampo() {
		return campo;
	}

	public void setCampo(CampoTO campo) {
		this.campo = campo;
	}

	public DocumentoTO getDocumento() {
		return documento;
	}

	public void setDocumento(DocumentoTO documento) {
		this.documento = documento;
	}

}