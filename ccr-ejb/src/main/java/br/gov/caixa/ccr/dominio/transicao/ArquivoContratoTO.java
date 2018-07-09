package br.gov.caixa.ccr.dominio.transicao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * The persistent class for the CCRTB018_ARQUIVO_CONTRATO database table.
 * 
 */
@Entity
@Table(name="CCRTB018_ARQUIVO_CONTRATO")
public class ArquivoContratoTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="NU_ARQUIVO_CONTRATO")
	private Long nuArquivoContrato;
	
	
	@ManyToOne
	@JoinColumn(name="NU_ARQUIVO_INTEGRACAO", insertable=false, updatable=false)
	private ArquivoIntegracaoTO arquivoIntegracao;
	
	@ManyToOne
	@JoinColumn(name="NU_CONTRATO", insertable=false, updatable=false)
	private ContratoTO contrato;
	
	@OneToMany(mappedBy="arquivoContrato")
	private List<ErroArquivoContratoTO> errosArquivoContrato;
	

	public ArquivoContratoTO() {
	}
	


	/**
	 * @return the arquivoIntegracao
	 */
	public ArquivoIntegracaoTO getArquivoIntegracao() {
		return arquivoIntegracao;
	}

	/**
	 * @param arquivoIntegracao the arquivoIntegracao to set
	 */
	public void setArquivoIntegracao(ArquivoIntegracaoTO arquivoIntegracao) {
		this.arquivoIntegracao = arquivoIntegracao;
	}

	/**
	 * @return the contrato
	 */
	public ContratoTO getContrato() {
		return contrato;
	}

	/**
	 * @param contrato the contrato to set
	 */
	public void setContrato(ContratoTO contrato) {
		this.contrato = contrato;
	}



	/**
	 * @return the nuArquivoContrato
	 */
	public Long getNuArquivoContrato() {
		return nuArquivoContrato;
	}



	/**
	 * @param nuArquivoContrato the nuArquivoContrato to set
	 */
	public void setNuArquivoContrato(Long nuArquivoContrato) {
		this.nuArquivoContrato = nuArquivoContrato;
	}



	/**
	 * @return the errosArquivoContrato
	 */
	public List<ErroArquivoContratoTO> getErrosArquivoContrato() {
		return errosArquivoContrato;
	}



	/**
	 * @param errosArquivoContrato the errosArquivoContrato to set
	 */
	public void setErrosArquivoContrato(List<ErroArquivoContratoTO> errosArquivoContrato) {
		this.errosArquivoContrato = errosArquivoContrato;
	}

	
	

}