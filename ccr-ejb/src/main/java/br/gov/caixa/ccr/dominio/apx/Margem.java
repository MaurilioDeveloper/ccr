package br.gov.caixa.ccr.dominio.apx;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Classe de dominio
 * @author c110503
 *
 */
@XmlRootElement
public class Margem implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2281209782384629158L;

	private String codigoOrgao;
	
	private String CNPJ;
	
	private String matriculaServidor;
	
	private String identificacaoUnica;
	
	private String dadosInstituidor;
	
	private String valorMargemConsignado;	
	
	private String valorMargemUtilizada;
	
	private String valorMargemDisponivel;
	
	private String valorMargemFixa;
	
	private String valorMargemVariavel;
	
	private String valorMargemDisponivelFixa;
	
	private String dataPrimeiroDesconto;
	
	private String codigoUPAG;
	
	private String ufPAG;
	
	private String bloqueioJudicial;
	
	private String reservaMargem = "";
	
	private String vinculo;
	
	private String nomeServidor;
	
	private String bancoServidor;
	
	private String idadeServidor;
	
	private String ccsServidor;
	
	private String situacaoServidor;
	
	private String classeServidor;
	
	private String numeroContrato;
	
	private String valorBruto;
	
	private String convenio;
	
	private String origem;
	
	private String matricula;
	
	private String folhaPagamento;
	
	private String dataCorte;
	
	private String margemDisponivel;
	
	private String valorliquido;
	
	private String taxaJuros;
	
	private String valorParcela;
	
	private String qtdeParcela;
	
	private String dataLiberacaoCredito;
	
	private String valorIof;
	
	private String cetMensal;
	
	private String valorBasePrimeiraPrestacao;
	
	private String codigoConvenente;
	
	private Date periodoAtual;
	
	private String senhaServidor;
	
	private Integer carencia = 0;
	
	public String getConvenio() {
		return convenio;
	}

	public void setConvenio(String convenio) {
		this.convenio = convenio;
	}

	public String getOrigem() {
		return origem;
	}

	public void setOrigem(String origem) {
		this.origem = origem;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getFolhaPagamento() {
		return folhaPagamento;
	}

	public void setFolhaPagamento(String folhaPagamento) {
		this.folhaPagamento = folhaPagamento;
	}

	public String getDataCorte() {
		return dataCorte;
	}

	public void setDataCorte(String dataCorte) {
		this.dataCorte = dataCorte;
	}

	public String getMargemDisponivel() {
		return margemDisponivel;
	}

	public void setMargemDisponivel(String margemDisponivel) {
		this.margemDisponivel = margemDisponivel;
	}

	@XmlAttribute(name = "CD-ORGAO")
	public String getCodigoOrgao() {
		return codigoOrgao;
	}

	public void setCodigoOrgao(String codigoOrgao) {
		this.codigoOrgao = codigoOrgao;
	}

	public String getCNPJ() {
		return CNPJ;
	}

	public void setCNPJ(String cNPJ) {
		CNPJ = cNPJ;
	}

	public String getMatriculaServidor() {
		return matriculaServidor;
	}

	public void setMatriculaServidor(String matriculaServidor) {
		this.matriculaServidor = matriculaServidor;
	}

	public String getIdentificacaoUnica() {
		return identificacaoUnica;
	}

	public void setIdentificacaoUnica(String identificacaoUnica) {
		this.identificacaoUnica = identificacaoUnica;
	}

	@XmlAttribute(name = "ORG-MAT-INST")
	public String getDadosInstituidor() {
		return dadosInstituidor;
	}

	public void setDadosInstituidor(String dadosInstituidor) {
		this.dadosInstituidor = dadosInstituidor;
	}	

	public String getValorMargemConsignado() {
		return valorMargemConsignado;
	}

	public void setValorMargemConsignado(String valorMargemConsignado) {
		this.valorMargemConsignado = valorMargemConsignado;
	}

	public String getValorMargemUtilizada() {
		return valorMargemUtilizada;
	}

	public void setValorMargemUtilizada(String valorMargemUtilizada) {
		this.valorMargemUtilizada = valorMargemUtilizada;
	}

	public String getValorMargemDisponivel() {
		return valorMargemDisponivel;
	}

	public void setValorMargemDisponivel(String valorMargemDisponivel) {
		this.valorMargemDisponivel = valorMargemDisponivel;
	}

	public String getValorMargemFixa() {
		return valorMargemFixa;
	}

	public void setValorMargemFixa(String valorMargemFixa) {
		this.valorMargemFixa = valorMargemFixa;
	}

	public String getValorMargemVariavel() {
		return valorMargemVariavel;
	}

	public void setValorMargemVariavel(String valorMargemVariavel) {
		this.valorMargemVariavel = valorMargemVariavel;
	}

	public String getValorMargemDisponivelFixa() {
		return valorMargemDisponivelFixa;
	}

	public void setValorMargemDisponivelFixa(String valorMargemDisponivelFixa) {
		this.valorMargemDisponivelFixa = valorMargemDisponivelFixa;
	}

	@XmlAttribute(name="DT-COMPETENCIA")
	public String getDataPrimeiroDesconto() {
		return dataPrimeiroDesconto;
	}

	public void setDataPrimeiroDesconto(String dataPrimeiroDesconto) {
		this.dataPrimeiroDesconto = dataPrimeiroDesconto;
	}

	public String getCodigoUPAG() {
		return codigoUPAG;
	}

	public void setCodigoUPAG(String codigoUPAG) {
		this.codigoUPAG = codigoUPAG;
	}

	public String getUfPAG() {
		return ufPAG;
	}

	public void setUfPAG(String ufPAG) {
		this.ufPAG = ufPAG;
	}

	public String getBloqueioJudicial() {
		return bloqueioJudicial;
	}

	public void setBloqueioJudicial(String bloqueioJudicial) {
		this.bloqueioJudicial = bloqueioJudicial;
	}
	
	public String getReservaMargem() {
		return reservaMargem;
	}
	public void setReservaMargem(String reservaMargem) {
		this.reservaMargem = reservaMargem;
	}	
	
	@XmlAttribute(name="NU-VINCULO")
	public String getVinculo() {
		return vinculo;
	}

	public void setVinculo(String vinculo) {
		this.vinculo = vinculo;
	}
	
	public String getNomeServidor() {
		return nomeServidor;
	}

	public void setNomeServidor(String nomeServidor) {
		this.nomeServidor = nomeServidor;
	}

	public String getBancoServidor() {
		return bancoServidor;
	}

	public void setBancoServidor(String bancoServidor) {
		this.bancoServidor = bancoServidor;
	}

	public String getIdadeServidor() {
		return idadeServidor;
	}

	public void setIdadeServidor(String idadeServidor) {
		this.idadeServidor = idadeServidor;
	}

	public String getCcsServidor() {
		return ccsServidor;
	}

	public void setCcsServidor(String ccsServidor) {
		this.ccsServidor = ccsServidor;
	}

	public String getSituacaoServidor() {
		return situacaoServidor;
	}

	public void setSituacaoServidor(String situacaoServidor) {
		this.situacaoServidor = situacaoServidor;
	}

	public String getClasseServidor() {
		return classeServidor;
	}

	public void setClasseServidor(String classeServidor) {
		this.classeServidor = classeServidor;
	}

	public String getNumeroContrato() {
		return numeroContrato;
	}

	public void setNumeroContrato(String numeroContrato) {
		this.numeroContrato = numeroContrato;
	}

	public String getValorBruto() {
		return valorBruto;
	}

	public void setValorBruto(String valorBruto) {
		this.valorBruto = valorBruto;
	}

	@Override
	public String toString() {
		return "Margem [codigoOrgao=" + codigoOrgao + ", CNPJ=" + CNPJ
				+ ", matriculaServidor=" + matriculaServidor
				+ ", identificacaoUnica=" + identificacaoUnica
				+ ", dadosInstituidor=" + dadosInstituidor
				+ ", valorMargemConsignado=" + valorMargemConsignado
				+ ", valorMargemUtilizada=" + valorMargemUtilizada
				+ ", valorMargemDisponivel=" + valorMargemDisponivel
				+ ", valorMargemFixa=" + valorMargemFixa
				+ ", valorMargemVariavel=" + valorMargemVariavel
				+ ", valorMargemDisponivelFixa=" + valorMargemDisponivelFixa
				+ ", dataPrimeiroDesconto=" + dataPrimeiroDesconto
				+ ", codigoUPAG=" + codigoUPAG + ", ufPAG=" + ufPAG
				+ ", bloqueioJudicial=" + bloqueioJudicial + ", reservaMargem="
				+ reservaMargem + ", vinculo=" + vinculo + ", nomeServidor="
				+ nomeServidor + ", bancoServidor=" + bancoServidor
				+ ", idadeServidor=" + idadeServidor + ", ccsServidor="
				+ ccsServidor + ", situacaoServidor=" + situacaoServidor
				+ ", classeServidor=" + classeServidor + ", numeroContrato="
				+ numeroContrato + ", valorBruto=" + valorBruto + "]";
	}

	public String getValorliquido() {
		return valorliquido;
	}

	public void setValorliquido(String valorliquido) {
		this.valorliquido = valorliquido;
	}

	public String getTaxaJuros() {
		return taxaJuros;
	}

	public void setTaxaJuros(String taxaJuros) {
		this.taxaJuros = taxaJuros;
	}

	public String getValorParcela() {
		return valorParcela;
	}

	public void setValorParcela(String valorParcela) {
		this.valorParcela = valorParcela;
	}

	public String getQtdeParcela() {
		return qtdeParcela;
	}

	public void setQtdeParcela(String qtdeParcela) {
		this.qtdeParcela = qtdeParcela;
	}

	public String getDataLiberacaoCredito() {
		return dataLiberacaoCredito;
	}

	public void setDataLiberacaoCredito(String dataLiberacaoCredito) {
		this.dataLiberacaoCredito = dataLiberacaoCredito;
	}

	public String getValorIof() {
		return valorIof;
	}

	public void setValorIof(String valorIof) {
		this.valorIof = valorIof;
	}

	public String getCetMensal() {
		return cetMensal;
	}

	public void setCetMensal(String cetMensal) {
		this.cetMensal = cetMensal;
	}

	public String getValorBasePrimeiraPrestacao() {
		return valorBasePrimeiraPrestacao;
	}

	public void setValorBasePrimeiraPrestacao(String valorBasePrimeiraPrestacao) {
		this.valorBasePrimeiraPrestacao = valorBasePrimeiraPrestacao;
	}

	public String getCodigoConvenente() {
		return codigoConvenente;
	}

	public void setCodigoConvenente(String codigoConvenente) {
		this.codigoConvenente = codigoConvenente;
	}

	public Date getPeriodoAtual() {
		return periodoAtual;
	}

	public void setPeriodoAtual(Date periodoAtual) {
		this.periodoAtual = periodoAtual;
	}

	public String getSenhaServidor() {
		return senhaServidor;
	}

	public void setSenhaServidor(String senhaServidor) {
		this.senhaServidor = senhaServidor;
	}

	public Integer getCarencia() {
		return carencia;
	}

	public void setCarencia(Integer carencia) {
		this.carencia = carencia;
	}
	
}
