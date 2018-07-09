package br.gov.caixa.ccr.dominio.transicao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the CCRTB071_MODELO_CONTRATO database table.
 * 
 */
@Entity
@Table(name="CCRTB071_MODELO_CONTRATO")
@NamedNativeQueries({
	@NamedNativeQuery(name = "DocumentoTO.excluirDocumento", query = "DELETE FROM CCR.CCRTB071_MODELO_CONTRATO WHERE NU_MODELO = :pDocumento", resultClass = DocumentoTO.class),
	@NamedNativeQuery(name = "DocumentoTO.excluirDocumentoCamposUsados", query = "DELETE FROM CCR.CCRTB073_CAMPO_MODELO_CONTRATO WHERE NU_MODELO = :pDocumento", resultClass = DocumentoTO.class),
	@NamedNativeQuery(name = "DocumentoTO.listarDocumentos", query = "SELECT * FROM CCR.CCRTB071_MODELO_CONTRATO ", resultClass = DocumentoTO.class),
	@NamedNativeQuery(name = "DocumentoTO.listarDocumentosDataIniVig", query = "SELECT * FROM CCR.CCRTB071_MODELO_CONTRATO WHERE DT_INICIO_VIGENCIA = :dtIniVigencia ", resultClass = DocumentoTO.class),
	@NamedNativeQuery(name = "DocumentoTO.listarDocumentosTipoDoc", query = "SELECT * FROM CCR.CCRTB071_MODELO_CONTRATO WHERE NU_TIPO_MODELO = :tpDoc ", resultClass = DocumentoTO.class),
	@NamedNativeQuery(name = "DocumentoTO.listarDocumentosNomeModelDoc", query = "SELECT * FROM CCR.CCRTB071_MODELO_CONTRATO WHERE NO_MODELO = :nomeDoc ", resultClass = DocumentoTO.class),
	@NamedNativeQuery(name = "DocumentoTO.listarDocumentosNomeModelAutocomplete", query = "SELECT * FROM CCR.CCRTB071_MODELO_CONTRATO WHERE UPPER(NO_MODELO) like UPPER(:nomeDoc) ", resultClass = DocumentoTO.class),
})

public class DocumentoTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="numeroModeloSequence", sequenceName="CCRSQ071_MODELO_CONTRATO", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="numeroModeloSequence")
	@Column(name="NU_MODELO")
	private Long idDocumento;

	@Column(name="CO_USUARIO")
	private String coUsuario;

	@Lob
	@Column(name="DE_MODELO_HTML_CONTRATO")
	private String deModelo;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DT_ATUALIZACAO")
	private Date dtAtualizacao;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_FIM_VIGENCIA")
	private Date dtFimVigencia;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DT_INCLUSAO")
	private Date dtInclusao;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_INICIO_VIGENCIA")
	private Date dtInicioVigencia;

	@Column(name="IC_STATUS")
	private String icStatus;

	@Column(name="NO_MODELO")
	private String noDocumento;

	@Column(name="NU_VERSAO")
	private Integer nuVersao;
	
	@Column(name="IC_MODELO_TESTEMUNHA")
	private String icModeloTestemunha;

	//bi-directional many-to-one association to TipoDocumentoTO
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="NU_TIPO_MODELO")
	private TipoDocumentoTO tipoDocumento;

	//bi-directional many-to-one association to CampoUsadoTO
	@OneToMany(mappedBy="documento", cascade = CascadeType.PERSIST)
	private List<CampoUsadoTO> campoUsados;

	public DocumentoTO() {
	}

	public Long getIdDocumento() {
		return this.idDocumento;
	}

	public void setIdDocumento(Long idDocumento) {
		this.idDocumento = idDocumento;
	}

	public String getCoUsuario() {
		return this.coUsuario;
	}

	public void setCoUsuario(String coUsuario) {
		this.coUsuario = coUsuario;
	}

	public String getDeModelo() {
		return this.deModelo;
	}

	public void setDeModelo(String deModelo) {
		this.deModelo = deModelo;
	}

	public Date getDtAtualizacao() {
		return this.dtAtualizacao;
	}

	public void setDtAtualizacao(Date dtAtualizacao) {
		this.dtAtualizacao = dtAtualizacao;
	}

	public Date getDtFimVigencia() {
		return this.dtFimVigencia;
	}

	public void setDtFimVigencia(Date dtFimVigencia) {
		this.dtFimVigencia = dtFimVigencia;
	}

	public Date getDtInclusao() {
		return this.dtInclusao;
	}

	public void setDtInclusao(Date dtInclusao) {
		this.dtInclusao = dtInclusao;
	}

	public Date getDtInicioVigencia() {
		return this.dtInicioVigencia;
	}

	public void setDtInicioVigencia(Date dtInicioVigencia) {
		this.dtInicioVigencia = dtInicioVigencia;
	}

	public String getIcStatus() {
		return this.icStatus;
	}

	public void setIcStatus(String icStatus) {
		this.icStatus = icStatus;
	}
	
	public String getIcModeloTestemunha() {
		return icModeloTestemunha;
	}

	public void setIcModeloTestemunha(String icModeloTestemunha) {
		this.icModeloTestemunha = icModeloTestemunha;
	}

	public String getNoDocumento() {
		return this.noDocumento;
	}

	public void setNoDocumento(String noDocumento) {
		this.noDocumento = noDocumento;
	}

	public Integer getNuVersao() {
		return this.nuVersao;
	}

	public void setNuVersao(Integer nuVersao) {
		this.nuVersao = nuVersao;
	}

	public TipoDocumentoTO getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(TipoDocumentoTO tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public List<CampoUsadoTO> getCampoUsados() {
		if(campoUsados == null){
			campoUsados = new ArrayList<CampoUsadoTO>();
		}
		return this.campoUsados;
	}

	public void setCampoUsados(List<CampoUsadoTO> campoUsados) {
		this.campoUsados = campoUsados;
	}

	public CampoUsadoTO addCampoUsado(CampoUsadoTO campoUsado) {
		getCampoUsados().add(campoUsado);
		campoUsado.setDocumento(this);

		return campoUsado;
	}

	public CampoUsadoTO removeCampoUsado(CampoUsadoTO campoUsado) {
		getCampoUsados().remove(campoUsado);
		campoUsado.setDocumento(null);

		return campoUsado;
	}

}