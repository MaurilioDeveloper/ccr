package br.gov.caixa.ccr.dominio.transicao;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


/**
 * The persistent class for the CCRTB050_AUTORIZACAO database table.
 * 
 */
@Entity
@Table(name="CCRTB050_AUTORIZACAO_CONTRATO")
public class AutorizacaoTO implements Serializable {
	private static final long serialVersionUID = 1L;

//	public static final String QUERY_BUSCA_ID = "SELECT MAX(NU_SEQ_AUTORIZACAO)+1 FROM CCR.CCRTB050_AUTORIZACAO";
	
	@Id
	@SequenceGenerator(sequenceName = "CCRSQ050_AUTORIZACAO_CONTRATO", allocationSize = 1, name="CCRTB050_AUTORIZACAO_NUSEQAUTORIZACAO_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CCRTB050_AUTORIZACAO_NUSEQAUTORIZACAO_GENERATOR")
	@Column(name="NU_SEQ_AUTORIZACAO")
	private Long nuSeqAutorizacao;

	//codigo do usuario que realizou a autorizacao
	@NotNull
	@Column(name="CO_USUARIO_AUTORIZACAO")
	private String coAutorizacao;

	@Column(name="DE_JUSTIFICATIVA")
	private String deJustificativa;

	@NotNull
	@Column(name="IC_SITUACAO_AUTORIZACAO")
	private String icSituacaoAutorizacao;

	@NotNull
	@Column(name="IC_TIPO_AUTORIZACAO" )
	private String icTipoAutorizacao;

	//TODO alterar para o objeto do contrato
	@Column(name="NU_CONTRATO")
	private Long nuContrato;

	@Column(name="TS_AUTORIZACAO")
	private Timestamp tsAutorizacao;
	
	@OneToOne
	@JoinColumn(name="NU_SITUACAO_CONTRATO")
	private HistoricoSituacaoTO situacaoContrato; 

	public AutorizacaoTO() {
	}

	public long getNuSeqAutorizacao() {
		return this.nuSeqAutorizacao;
	}

	public void setNuSeqAutorizacao(long nuSeqAutorizacao) {
		this.nuSeqAutorizacao = nuSeqAutorizacao;
	}

	public String getCoAutorizacao() {
		return this.coAutorizacao;
	}

	public void setCoAutorizacao(String coAutorizacao) {
		this.coAutorizacao = coAutorizacao;
	}

	public String getDeJustificativa() {
		return this.deJustificativa;
	}

	public void setDeJustificativa(String deJustificativa) {
		this.deJustificativa = deJustificativa;
	}

	public String getIcSituacaoAutorizacao() {
		return this.icSituacaoAutorizacao;
	}

	public void setIcSituacaoAutorizacao(String icSituacaoAutorizacao) {
		this.icSituacaoAutorizacao = icSituacaoAutorizacao;
	}

	public String getIcTipoAutorizacao() {
		return this.icTipoAutorizacao;
	}

	public void setIcTipoAutorizacao(String icTipoAutorizacao) {
		this.icTipoAutorizacao = icTipoAutorizacao;
	}

	public Long getNuContrato() {
		return this.nuContrato;
	}

	public void setNuContrato(Long nuContrato) {
		this.nuContrato = nuContrato;
	}

	public Timestamp getTsAutorizacao() {
		return this.tsAutorizacao;
	}

	public void setTsAutorizacao(Timestamp tsAutorizacao) {
		this.tsAutorizacao = tsAutorizacao;
	}

	public HistoricoSituacaoTO getSituacaoContrato() {
		return situacaoContrato;
	}

	public void setSituacaoContrato(HistoricoSituacaoTO situacaoContrato) {
		this.situacaoContrato = situacaoContrato;
	}

	

}