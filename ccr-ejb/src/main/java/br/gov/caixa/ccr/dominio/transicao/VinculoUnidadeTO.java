package br.gov.caixa.ccr.dominio.transicao;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the CCRVW003_VINCULO_UNIDADE database table.
 * 
 */




@Table(name="CCRVW003_VINCULO_UNIDADE")
@Entity
@NamedNativeQueries({
	//@NamedNativeQuery(name="VinculoUnidadeTO.unidadeViculada", query="SELECT * FROM CCR.CCRVW003_VINCULO_UNIDADE WHERE NU_UNDE_VNCLRA_U24 = 2639 AND  NU_UNDE_VNCLA_U24 = 2", resultClass=UnidadeTO.class)
	@NamedNativeQuery(name="VinculoUnidadeTO.unidadeViculada", query="SELECT * FROM CCR.CCRVW003_VINCULO_UNIDADE WHERE NU_UNDE_VNCLRA_U24 = ? AND  NU_UNDE_VNCLA_U24 = ? AND NU_TP_VINCULO_U22 = 1", resultClass=VinculoUnidadeTO.class)
})
public class VinculoUnidadeTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -841114513767848378L;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_FIM")
	private Date dataFim;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_INICIO")
	private Date dataInicio;

	@Column(name="NU_NTRL_VNCLA_U24")
	@Id
	private Long idNtrlVnclaU24;

	@Column(name="NU_NTRL_VNCLRA_U24")
	@Id
	private Long idNtrlVnclraU24;

	@Column(name="NU_TP_VINCULO_U22")
	@Id
	private Long idTpVinculoU22;

	@Column(name="NU_UNDE_VNCLA_U24")
	@Id
	private Long idUndeVnclaU24;

	@Column(name="NU_UNDE_VNCLRA_U24")
	@Id
	private Long idUndeVnclraU24;

	public VinculoUnidadeTO() {
	}

	public Date getDataFim() {
		return this.dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public Date getDataInicio() {
		return this.dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Long getIdNtrlVnclaU24() {
		return idNtrlVnclaU24;
	}

	public void setIdNtrlVnclaU24(Long idNtrlVnclaU24) {
		this.idNtrlVnclaU24 = idNtrlVnclaU24;
	}

	public Long getIdNtrlVnclraU24() {
		return idNtrlVnclraU24;
	}

	public void setIdNtrlVnclraU24(Long idNtrlVnclraU24) {
		this.idNtrlVnclraU24 = idNtrlVnclraU24;
	}

	public Long getIdTpVinculoU22() {
		return idTpVinculoU22;
	}

	public void setIdTpVinculoU22(Long idTpVinculoU22) {
		this.idTpVinculoU22 = idTpVinculoU22;
	}

	public Long getIdUndeVnclaU24() {
		return idUndeVnclaU24;
	}

	public void setIdUndeVnclaU24(Long idUndeVnclaU24) {
		this.idUndeVnclaU24 = idUndeVnclaU24;
	}

	public Long getIdUndeVnclraU24() {
		return idUndeVnclraU24;
	}

	public void setIdUndeVnclraU24(Long idUndeVnclraU24) {
		this.idUndeVnclraU24 = idUndeVnclraU24;
	}

}