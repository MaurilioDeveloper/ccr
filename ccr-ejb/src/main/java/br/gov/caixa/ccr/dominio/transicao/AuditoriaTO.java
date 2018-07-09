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
@Table(name="CCRTB100_AUDITORIA_TRANSACAO")
public class AuditoriaTO {
	
	@Id	
	@SequenceGenerator(sequenceName = "CCRSQ100_AUDITORIA_TRANSACAO", allocationSize = 1, name="CCRTB100_AUDITORIA_TRANSACAO_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CCRTB100_AUDITORIA_TRANSACAO_GENERATOR")
	@Column(name="NU_AUDITORIA_TRANSACAO", length=19)
	private Long nuAuditoriaTransacao;

	@Column(name="NU_TRANSACAO_AUDITADA", length=22)
	private Long nuTransacaoAuditada;
	
	@Column(name="IC_TIPO_ACAO", length=1)
	private String icTipoAcao;
	
	@Column(name="NO_CAMPO_AUDITADO", length=50)
	private String noCampoAuditado;
	
	@Column(name="DE_VALOR_ANTERIOR", length=255)
	private String deValorAnterior;
	
	@Column(name="DE_VALOR_ATUAL", length=255)
	private String deValorAtual;
	
	@Column(name="DH_TRANSACAO_AUDITADA", length=11)
	private Date dhTransacaoAuditada;
	
	@Column(name="CO_USUARIO_AUDITADO", length=8)
	private String coUsuarioAuditado;
	
	@OneToOne
	@JoinColumn(name="NU_CONVENIO")
	private ConvenioTO nuConvenio;
	
	@OneToOne
	@JoinColumn(name="NU_CONTRATO")
	private ContratoTO nuContrato;
	
	@OneToOne
	@JoinColumn(name="NU_CANAL_ATENDIMENTO")
	private CanalAtendimentoTO nuCanal;
	
	

	/**
	 * @return the nuAuditoriaTransacao
	 */
	public Long getNuAuditoriaTransacao() {
		return nuAuditoriaTransacao;
	}

	/**
	 * @param nuAuditoriaTransacao the nuAuditoriaTransacao to set
	 */
	public void setNuAuditoriaTransacao(Long nuAuditoriaTransacao) {
		this.nuAuditoriaTransacao = nuAuditoriaTransacao;
	}

	/**
	 * @return the nuTransacaoAuditada
	 */
	public Long getNuTransacaoAuditada() {
		return nuTransacaoAuditada;
	}

	/**
	 * @param nuTransacaoAuditada the nuTransacaoAuditada to set
	 */
	public void setNuTransacaoAuditada(Long nuTransacaoAuditada) {
		this.nuTransacaoAuditada = nuTransacaoAuditada;
	}

	/**
	 * @return the icTipoAcao
	 */
	public String getIcTipoAcao() {
		return icTipoAcao;
	}

	/**
	 * @param icTipoAcao the icTipoAcao to set
	 */
	public void setIcTipoAcao(String icTipoAcao) {
		this.icTipoAcao = icTipoAcao;
	}

	/**
	 * @return the noCampoAuditado
	 */
	public String getNoCampoAuditado() {
		return noCampoAuditado;
	}

	/**
	 * @param noCampoAuditado the noCampoAuditado to set
	 */
	public void setNoCampoAuditado(String noCampoAuditado) {
		this.noCampoAuditado = noCampoAuditado;
	}

	/**
	 * @return the deValorAnterior
	 */
	public String getDeValorAnterior() {
		return deValorAnterior;
	}

	/**
	 * @param deValorAnterior the deValorAnterior to set
	 */
	public void setDeValorAnterior(String deValorAnterior) {
		this.deValorAnterior = deValorAnterior;
	}

	/**
	 * @return the deValorAtual
	 */
	public String getDeValorAtual() {
		return deValorAtual;
	}

	/**
	 * @param deValorAtual the deValorAtual to set
	 */
	public void setDeValorAtual(String deValorAtual) {
		this.deValorAtual = deValorAtual;
	}

	/**
	 * @return the dhTransacaoAuditada
	 */
	public Date getDhTransacaoAuditada() {
		return dhTransacaoAuditada;
	}

	/**
	 * @param dhTransacaoAuditada the dhTransacaoAuditada to set
	 */
	public void setDhTransacaoAuditada(Date dhTransacaoAuditada) {
		this.dhTransacaoAuditada = dhTransacaoAuditada;
	}

	/**
	 * @return the coUsuarioAuditado
	 */
	public String getCoUsuarioAuditado() {
		return coUsuarioAuditado;
	}

	/**
	 * @param coUsuarioAuditado the coUsuarioAuditado to set
	 */
	public void setCoUsuarioAuditado(String coUsuarioAuditado) {
		this.coUsuarioAuditado = coUsuarioAuditado;
	}

	/**
	 * @return the nuConvenio
	 */
	public ConvenioTO getNuConvenio() {
		return nuConvenio;
	}

	/**
	 * @param nuConvenio the nuConvenio to set
	 */
	public void setNuConvenio(ConvenioTO nuConvenio) {
		this.nuConvenio = nuConvenio;
	}

	/**
	 * @return the nuContrato
	 */
	public ContratoTO getNuContrato() {
		return nuContrato;
	}

	/**
	 * @param nuContrato the nuContrato to set
	 */
	public void setNuContrato(ContratoTO nuContrato) {
		this.nuContrato = nuContrato;
	}

	/**
	 * @return the nuCanal
	 */
	public CanalAtendimentoTO getNuCanal() {
		return nuCanal;
	}

	/**
	 * @param nuCanal the nuCanal to set
	 */
	public void setNuCanal(CanalAtendimentoTO nuCanal) {
		this.nuCanal = nuCanal;
	}
}
