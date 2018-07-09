package br.gov.caixa.ccr.dominio.transicao;

import java.io.Serializable;
//git.sirius.tivit.com.br/caixa/siccr.git
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import br.gov.caixa.ccr.template.formatters.FormatEnum;
import br.gov.caixa.ccr.template.formatters.TemplateFormat;


/**
 * The persistent class for the CCRTB017_ARQUIVO_INTEGRACAO database table.
 * 
 */
@Entity
@Table(name="CCRTB017_ARQUIVO_INTEGRACAO")
public class ArquivoIntegracaoTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(sequenceName = "CCRSQ017_ARQUIVO_INTEGRACAO", allocationSize = 1, name="CCRTB017_ARQUIVO_INTEGRACAO_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CCRTB017_ARQUIVO_INTEGRACAO_GENERATOR")
	@Column(name="NU_ARQUIVO_INTEGRACAO")
	private Long nuArquivoIntegracao;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@TemplateFormat(format=FormatEnum.DATA_HORA)
	@Column(name="TS_ENVIO_ARQUIVO")
	private Date dtEnvioArquivo;
	
	@NotNull
	@Column(name="QT_CONTRATO_ENVIO")
	private Long qtdeContratosEnvio;

	@Temporal(TemporalType.TIMESTAMP)
	@TemplateFormat(format=FormatEnum.DATA_HORA)
	@Column(name="TS_RETORNO_ARQUIVO")
	private Date dtRetornoArquivo;

	@Column(name="QT_CONTRATO_RETORNO")
	private Long qtdeContratosRetorno;
	
	//bi-directional many-to-one association to Situacao
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="NU_SITUACAO_ARQUIVO", referencedColumnName="NU_SITUACAO"),
		@JoinColumn(name="NU_TIPO_SITUACAO", referencedColumnName="NU_TIPO_SITUACAO")
		})
	private SituacaoTO situacao;
	
	@OneToMany(mappedBy = "arquivoIntegracao")
	private List<ArquivoContratoTO> arquivosContratos;
	
	public ArquivoIntegracaoTO() {
		
	}

	/**
	 * @return the nuArquivoIntegracao
	 */
	public Long getNuArquivoIntegracao() {
		return nuArquivoIntegracao;
	}

	/**
	 * @param nuArquivoIntegracao the nuArquivoIntegracao to set
	 */
	public void setNuArquivoIntegracao(Long nuArquivoIntegracao) {
		this.nuArquivoIntegracao = nuArquivoIntegracao;
	}

	/**
	 * @return the dtEnvioArquivo
	 */
	public Date getDtEnvioArquivo() {
		return dtEnvioArquivo;
	}

	/**
	 * @param dtEnvioArquivo the dtEnvioArquivo to set
	 */
	public void setDtEnvioArquivo(Date dtEnvioArquivo) {
		this.dtEnvioArquivo = dtEnvioArquivo;
	}

	/**
	 * @return the qtdeContratosEnvio
	 */
	public Long getQtdeContratosEnvio() {
		return qtdeContratosEnvio;
	}

	/**
	 * @param qtdeContratosEnvio the qtdeContratosEnvio to set
	 */
	public void setQtdeContratosEnvio(Long qtdeContratosEnvio) {
		this.qtdeContratosEnvio = qtdeContratosEnvio;
	}

	/**
	 * @return the dtRetornoArquivo
	 */
	public Date getDtRetornoArquivo() {
		return dtRetornoArquivo;
	}

	/**
	 * @param dtRetornoArquivo the dtRetornoArquivo to set
	 */
	public void setDtRetornoArquivo(Date dtRetornoArquivo) {
		this.dtRetornoArquivo = dtRetornoArquivo;
	}

	/**
	 * @return the qtdeContratosRetorno
	 */
	public Long getQtdeContratosRetorno() {
		return qtdeContratosRetorno;
	}

	/**
	 * @param qtdeContratosRetorno the qtdeContratosRetorno to set
	 */
	public void setQtdeContratosRetorno(Long qtdeContratosRetorno) {
		this.qtdeContratosRetorno = qtdeContratosRetorno;
	}


	/**
	 * @return the situacao
	 */
	public SituacaoTO getSituacao() {
		return situacao;
	}

	/**
	 * @param situacao the situacao to set
	 */
	public void setSituacao(SituacaoTO situacao) {
		this.situacao = situacao;
	}

	/**
	 * @return the arquivosContratos
	 */
	public List<ArquivoContratoTO> getArquivosContratos() {
		return arquivosContratos;
	}

	/**
	 * @param arquivosContratos the arquivosContratos to set
	 */
	public void setArquivosContratos(List<ArquivoContratoTO> arquivosContratos) {
		this.arquivosContratos = arquivosContratos;
	}



}