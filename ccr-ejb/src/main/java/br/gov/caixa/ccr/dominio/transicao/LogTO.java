//package br.gov.caixa.ccr.dominio.transicao;
//
//import java.io.Serializable;
//import java.util.Date;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.Table;
//
//@Entity
//@Table(name="CCRTB016_OCORRENCIA_INTERFACE")
//public class LogTO implements Serializable {
//	
//	private static final long serialVersionUID = 1L;
//	
//	@Id
//	@Column(name="NU_OCORRENCIA", nullable=false, unique=true, length=10)
//	private Long id;
//	
//	@Column(name="NU_INTERFACE")
//	private Integer transacao;
//
//	@Column(name="TS_OCORRENCIA")
//	private Date data;	
//
//	@Column(name="IC_ACAO")
//	private String acao;
//
//	@Column(name="IC_OCORRENCIA")
//	private String ocorrencia;
//
//	@Column(name="DE_RESUMO_OCORRENCIA")
//	private String resumo;
//
//	@Column(name="DE_OCORRENCIA")
//	private String detalhe;
//
//	public Long getId() {
//		return id;
//	}
//
//	public void setId(Long id) {
//		this.id = id;
//	}
//	
//	public Integer getTransacao() {
//		return transacao;
//	}
//
//	public void setTransacao(Integer transacao) {
//		this.transacao = transacao;
//	}
//
//	public Date getData() {
//		return data;
//	}
//
//	public void setData(Date data) {
//		this.data = data;
//	}
//
//	public String getAcao() {
//		return acao;
//	}
//
//	public void setAcao(String acao) {
//		this.acao = acao;
//	}
//
//	public String getOcorrencia() {
//		return ocorrencia;
//	}
//
//	public void setOcorrencia(String ocorrencia) {
//		this.ocorrencia = ocorrencia;
//	}
//
//	public String getResumo() {
//		return resumo;
//	}
//
//	public void setResumo(String resumo) {
//		this.resumo = resumo;
//	}
//
//	public String getDetalhe() {
//		return detalhe;
//	}
//
//	public void setDetalhe(String detalhe) {
//		this.detalhe = detalhe;
//	}
//	
//}
