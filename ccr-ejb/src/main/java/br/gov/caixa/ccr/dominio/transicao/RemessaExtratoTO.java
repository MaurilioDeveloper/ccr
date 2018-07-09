//package br.gov.caixa.ccr.dominio.transicao;
//
//import java.io.Serializable;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.Table;
//
//import br.gov.caixa.ccr.util.Utilities;
//
//@Entity
//@Table(name="CCRTB009_REMESSA_EXTRATO")
//public class RemessaExtratoTO implements Serializable {
//	
//	private static final long serialVersionUID = 1L;
//	
//	@Id
//	@Column(name="NU_REMESSA_EXTRATO", nullable=false, unique=true, length=5)
//	private int id;
//	
//	@Column(name="NO_REMESSA_EXTRATO", nullable=false, length=50)
//	private String nome;
//	
//	public RemessaExtratoTO() {
//	}
//	
//	public RemessaExtratoTO(int id, String nome) {
//		super();
//		this.id = id;
//		this.nome = nome;
//	}
//
//	public int getId() {
//		return id;
//	}
//
//	public void setId(int id) {
//		this.id = id;
//	}
//	
//	public String getNome() {
//		return Utilities.leftRightTrim(nome);
//	}
//
//	public void setNome(String nome) {
//		this.nome = nome;
//	}
//
//}
