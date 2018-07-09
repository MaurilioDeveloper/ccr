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
 * The persistent class for the CCRTB047_CONVENIO_SR database table.
 * 
 */
@Entity
@Table(name="CCRTB047_CONVENIO_SR")
@NamedNativeQueries({
	@NamedNativeQuery(name="ConvenioSrTO.buscarPorId", query="SELECT * FROM CCR.CCRTB047_CONVENIO_SR"
			+ " WHERE NU_CONVENIO = ? ", resultClass=ConvenioSrTO.class),
	@NamedNativeQuery(name = "ConvenioSrTO.excluiSrTO", query = "DELETE FROM CCR.CCRTB047_CONVENIO_SR WHERE NU_CONVENIO = :pConvenio", resultClass = ConvenioSrTO.class)

})
public class ConvenioSrTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5644143704710877214L;

	@EmbeddedId
	private ConvenioSrPK id;

	@Column(name="CO_USUARIO_EXCLUSAO")
	private String codUsuarioExclusao;

	@Column(name="CO_USUARIO_INCLUSAO")
	private String codUsuarioInclusao;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="TS_EXCLUSAO_SR")
	private Date dataExclusaoSr;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="TS_INCLUSAO_SR")
	private Date dataInclusaoSr;

	public ConvenioSrTO() {
	}

	public ConvenioSrPK getId() {
		return this.id;
	}

	public void setId(ConvenioSrPK id) {
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

	public Date getDataExclusaoSr() {
		return this.dataExclusaoSr;
	}

	public void setDataExclusaoSr(Date dataExclusaoSr) {
		this.dataExclusaoSr = dataExclusaoSr;
	}

	public Date getDataInclusaoSr() {
		return this.dataInclusaoSr;
	}

	public void setDataInclusaoSr(Date dataInclusaoSr) {
		this.dataInclusaoSr = dataInclusaoSr;
	}

}