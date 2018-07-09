package br.gov.caixa.ccr.dominio.transicao;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the CCRTB045_CONVENIO_UF database table.
 * 
 */
@Entity
@Table(name="CCRTB045_CONVENIO_UF")
@NamedNativeQueries({
	@NamedNativeQuery(name="ConvenioUfTO.buscarPorId", query="SELECT * FROM CCR.CCRTB045_CONVENIO_UF WHERE NU_CONVENIO = ? ", resultClass=ConvenioUfTO.class),
	@NamedNativeQuery(name = "ConvenioUfTO.excluiUFTO", query = "DELETE FROM CCR.CCRTB045_CONVENIO_UF WHERE NU_CONVENIO = :pConvenio", resultClass = ConvenioUfTO.class)
})
public class ConvenioUfTO implements Serializable {

	/**
	 
	 
	 	@Id
	@Column(name="NU_CONVENIO", insertable=false, updatable=false)
	private Long idConvenio;

	@Id
	@Column(name="SG_UF")
	private String uf;


	 * 
	 * 
	 */
	private static final long serialVersionUID = -2491074417684347835L;

	@EmbeddedId
	private ConvenioUfPK id;

	@Column(name="CO_USUARIO_EXCLUSAO")
	private String codUsuarioExclusao;

	@Column(name="CO_USUARIO_INCLUSAO")
	private String codUsuarioInclusao;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="TS_EXCLUSAO_UF")
	private Date dataExclusaoUf;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="TS_INCLUSAO_UF")
	private Date dataInclusaoUf;

	public ConvenioUfTO() {
	}

	public ConvenioUfPK getId() {
		return this.id;
	}

	public void setId(ConvenioUfPK id) {
		this.id = id;
	}

	public String getCodUsuarioExclusao() {
		return this.codUsuarioExclusao;
	}

	public void setCodUsuarioExclusao(String codUsuarioExclusao) {
		this.codUsuarioExclusao = codUsuarioExclusao;
	}

	public String getCodUsuarioInclusao() {
		return this.codUsuarioInclusao;
	}

	public void setCodUsuarioInclusao(String codUsuarioInclusao) {
		this.codUsuarioInclusao = codUsuarioInclusao;
	}

	public Date getDataExclusaoUf() {
		return this.dataExclusaoUf;
	}

	public void setDataExclusaoUf(Date dataExclusaoUf) {
		this.dataExclusaoUf = dataExclusaoUf;
	}

	public Date getDataInclusaoUf() {
		return this.dataInclusaoUf;
	}

	public void setDataInclusaoUf(Date dataInclusaoUf) {
		this.dataInclusaoUf = dataInclusaoUf;
	}

}