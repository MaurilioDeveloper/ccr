//package br.gov.caixa.ccr.dominio.transicao;
//
//import java.io.Serializable;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.Table;
//
//@Entity
//@Table(name="CCRTB030_SITUACAO_CONVENIO")
//public class SituacaoConvenioTO implements Serializable {
//	
//	private static final long serialVersionUID = 1L;
//	
//	@Id
//	@Column(name="NU_SITUACAO_CONVENIO", nullable=false, unique=true, length=5)
//	private int codigo;
//	
//	@Column(name="NO_SITUACAO_CONVENIO", nullable=false, length=50)
//	private String nome;
//
//	public int getCodigo() {
//		return codigo;
//	}
//
//	public void setCodigo(int codigo) {
//		this.codigo = codigo;
//	}
//
//	public String getNome() {
//		return nome;
//	}
//
//	public void setNome(String nome) {
//		this.nome = nome;
//	}
//
//}
