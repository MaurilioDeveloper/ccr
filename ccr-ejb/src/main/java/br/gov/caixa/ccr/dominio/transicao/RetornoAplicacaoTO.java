package br.gov.caixa.ccr.dominio.transicao;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="CCRTB016_RETORNO_APLICACAO")
public class RetornoAplicacaoTO {
	
	@Id	
	@SequenceGenerator(sequenceName = "CCRSQ016_RETORNO_APLICACAO", allocationSize = 1, name="CCRTB016_RETORNO_APLICACAO_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CCRTB016_RETORNO_APLICACAO_GENERATOR")
	@Column(name="NU_RETORNO_APLICACAO", length=22)
	private Long nuRetornoAplicacao;

	@Column(name="DH_ENVIO_APLICACAO")
	private Date dtEnvioAplicacao;
	
	@Column(name="DE_RETORNO_APLICACAO", length=255)
	private String deRetornoAplicacao;
	
	@OneToOne
	@JoinColumn(name="NU_CONTRATO")
	private ContratoTO contrato;

	/**
	 * @return the nuRetornoAplicacao
	 */
	public Long getNuRetornoAplicacao() {
		return nuRetornoAplicacao;
	}

	/**
	 * @param nuRetornoAplicacao the nuRetornoAplicacao to set
	 */
	public void setNuRetornoAplicacao(Long nuRetornoAplicacao) {
		this.nuRetornoAplicacao = nuRetornoAplicacao;
	}

	/**
	 * @return the dtEnvioAplicacao
	 */
	public Date getDtEnvioAplicacao() {
		return dtEnvioAplicacao;
	}

	/**
	 * @param dtEnvioAplicacao the dtEnvioAplicacao to set
	 */
	public void setDtEnvioAplicacao(Date dtEnvioAplicacao) {
		this.dtEnvioAplicacao = dtEnvioAplicacao;
	}

	/**
	 * @return the deRetornoAplicacao
	 */
	public String getDeRetornoAplicacao() {
		return deRetornoAplicacao;
	}

	/**
	 * @param deRetornoAplicacao the deRetornoAplicacao to set
	 */
	public void setDeRetornoAplicacao(String deRetornoAplicacao) {
		this.deRetornoAplicacao = deRetornoAplicacao;
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


}