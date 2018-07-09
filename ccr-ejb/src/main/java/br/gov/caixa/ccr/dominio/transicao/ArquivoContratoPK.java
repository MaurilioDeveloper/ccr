//package br.gov.caixa.ccr.dominio.transicao;
//
//import java.io.Serializable;
//
//import javax.persistence.Column;
//import javax.persistence.Embeddable;
//
///**
// * The primary key class for the CCRTB003_CONVENIO_CANAL database table.
// * 
// */
//@Embeddable
//public class ArquivoContratoPK implements Serializable {
//	//default serial version id, required for serializable classes.
//	private static final long serialVersionUID = 1L;
//
//	@Column(name="NU_ARQUIVO_INTEGRACAO")
//	private Long nuArquivoIntegracao;
//
//	@Column(name="NU_CONTRATO")
//	private Long nuContrato;
//
//	public ArquivoContratoPK() {
//	}
//
//	public ArquivoContratoPK(Long nuArquivoIntegracao, Long nuContrato) {
//		super();
//		this.nuArquivoIntegracao = nuArquivoIntegracao;
//		this.nuContrato = nuContrato;
//	}
//
//	/**
//	 * @return the nuArquivoIntegracao
//	 */
//	public Long getNuArquivoIntegracao() {
//		return nuArquivoIntegracao;
//	}
//
//	/**
//	 * @param nuArquivoIntegracao the nuArquivoIntegracao to set
//	 */
//	public void setNuArquivoIntegracao(Long nuArquivoIntegracao) {
//		this.nuArquivoIntegracao = nuArquivoIntegracao;
//	}
//
//	/**
//	 * @return the nuContrato
//	 */
//	public Long getNuContrato() {
//		return nuContrato;
//	}
//
//	/**
//	 * @param nuContrato the nuContrato to set
//	 */
//	public void setNuContrato(Long nuContrato) {
//		this.nuContrato = nuContrato;
//	}
//
//		
//}