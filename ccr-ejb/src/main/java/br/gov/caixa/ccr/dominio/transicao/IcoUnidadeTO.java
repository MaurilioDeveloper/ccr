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
//@Table(name="CCRVW002_ICO_UNIDADE")
//public class IcoUnidadeTO implements Serializable {
//	
//	private static final long serialVersionUID = 1L;
//	
//	@Id
//	@Column(name="NU_UNIDADE", nullable=false, unique=true, length=5)
//	private int unidade;
//	
//	@Column(name="NU_NATURAL")
//	private int natural;
//	
//	@Column(name="NO_UNIDADE")
//	private String nome;
//
//	@Column(name="SG_UF")
//	private String uf;
//
//	public int getUnidade() {
//		return unidade;
//	}
//
//	public void setUnidade(int unidade) {
//		this.unidade = unidade;
//	}
//
//	public int getNatural() {
//		return natural;
//	}
//
//	public void setNatural(int natural) {
//		this.natural = natural;
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
//	public String getUf() {
//		return uf;
//	}
//
//	public void setUf(String uf) {
//		this.uf = uf;
//	}
//	
//}
