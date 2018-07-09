package br.gov.caixa.ccr.dominio.transicao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the CCRTB017_ARQUIVO_INTEGRACAO database table.
 * 
 */
@Entity
@Table(name="CCRTB019_ERRO_ARQUIVO_CONTRATO")
public class ErroArquivoContratoTO {
	
	@Id
	@SequenceGenerator(sequenceName = "CCRSQ019_ERRO_ARQUIVO_CONTRATO", allocationSize = 1, name="CCRTB019_ARQUIVO_NUSEQERRO_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CCRTB019_ARQUIVO_NUSEQERRO_GENERATOR")
	@Column(name="NU_ERRO_ARQUIVO_CONTRATO")
	private Long nuErroArquivoContrato;
	
	@ManyToOne
	@JoinColumn(name="NU_ARQUIVO_CONTRATO", referencedColumnName="NU_ARQUIVO_CONTRATO")
	private ArquivoContratoTO arquivoContrato;

	@OneToOne
	@JoinColumn(name="CO_ERRO_INTEGRACAO", referencedColumnName="CO_ERRO_INTEGRACAO")
	private ErroIntegracaoTO erroIntegracao;

	/**
	 * @return the nuErroArquivoContrato
	 */
	public Long getNuErroArquivoContrato() {
		return nuErroArquivoContrato;
	}

	/**
	 * @param nuErroArquivoContrato the nuErroArquivoContrato to set
	 */
	public void setNuErroArquivoContrato(Long nuErroArquivoContrato) {
		this.nuErroArquivoContrato = nuErroArquivoContrato;
	}

	

	/**
	 * @return the arquivoContrato
	 */
	public ArquivoContratoTO getArquivoContrato() {
		return arquivoContrato;
	}

	/**
	 * @param arquivoContrato the arquivoContrato to set
	 */
	public void setArquivoContrato(ArquivoContratoTO arquivoContrato) {
		this.arquivoContrato = arquivoContrato;
	}

	/**
	 * @return the erroIntegracao
	 */
	public ErroIntegracaoTO getErroIntegracao() {
		return erroIntegracao;
	}

	/**
	 * @param erroIntegracao the erroIntegracao to set
	 */
	public void setErroIntegracao(ErroIntegracaoTO erroIntegracao) {
		this.erroIntegracao = erroIntegracao;
	}

	
	
}
