package br.gov.caixa.ccr.dominio.transicao;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="CCRTBH01_CONVENIO")
public class HistoricoConvenioTO implements Serializable {
	
	private static final long serialVersionUID = 2576227853805552648L;

	@Id
	@Column(name="NU_HISTORICO", nullable=false)
	private int historico;
	
	@Id
	@Column(name="NU_CONVENIO", nullable=false)
	private int id;
	
//	@Column(name="NU_CNPJ_CONVENIO", nullable=false, length=14)
	@Column(name="NU_CNPJ_CONVENENTE", nullable=false, length=14)
	private long cnpj;
	
	@Column(name="NO_CONVENIO", nullable=false, length=50)
	private String nome;

//	@Column(name="NU_GRUPO")
	@Column(name="NU_GRUPO_TAXA")
	private int grupo;
	
	@Column(name = "NU_REMESSA_EXTRATO", nullable=false)
	private int remessa;	

	@Column(name = "NU_TIPO_SITUACAO", nullable=false)
	private int tipoSituacao;
	
	@Column(name = "NU_SITUACAO", nullable=false)
	private int situacao;
	
	@Column(name="NU_AGENCIA_CONTA_CREDITO", length=4)
	private short agenciaContaCredito;

	@Column(name="NU_OPERACAO_CONTA_CREDITO", length=3)
	private short operacaoContaCredito; 

	@Column(name="NU_CONTA_CREDITO", length=8)
	private int contaCredito;

	@Column(name="NU_DV_CONTA_CREDITO", length=1)
	private byte dvContaCredito;
	
	@Column(name="DD_CREDITO_SALARIO", length=2)
	private byte diaCreditoSal;
	
//	@Column(name="PZ_MAXIMO_EMPRESTIMO", nullable=false, length=3) 
//	private short prazoMaximo;

//	@Column(name="NU_NATURAL_SR_RESPONSAVEL", nullable=false, length=4)
//	private short naturalSRResp;
	
//	@Column(name="NU_SR_RESPONSAVEL", nullable=false, length=4)
//	private short SRResp;

	@Column(name="NU_NATURAL_PV_RESPONSAVEL", nullable=false, length=4)
	private short naturalPVResp;
	
	@Column(name="NU_PV_RESPONSAVEL", nullable=false, length=4)
	private short PVResp;

//	@Column(name="NU_NATURAL_SR_COBRANCA", nullable=false, length=4)
//	private short naturalSRCobranca;
	
//	@Column(name="NU_SR_COBRANCA", nullable=false, length=4)
//	private short SRCobranca;

	@Column(name="NU_NATURAL_PV_COBRANCA", nullable=false, length=4)
	private short naturalPVCobranca;
	
	@Column(name="NU_PV_COBRANCA", nullable=false, length=4)
	private short PVCobranca;

	@Column(name="IC_ABRANGENCIA", nullable=false, length=1)
	private byte abrangencia;

//	@Column(name="PZ_EMISSAO", length=3)
//	private short prazoEmissao;

//	@Column(name="IC_PERMITE_CONTRATO_CANAL", nullable=false, length=1)
//	private char permiteContratoCanal;

//	@Column(name="IC_AUTORIZA_MARGEM_CONTRATO", nullable=false, length=1)
//	private char autorizaMargemContrato;

//	@Column(name="PZ_MINIMO_ATRZA_MRGM_CONTRATO", length=3)
//	private short prazoMinimoAutMargemContr;

//	@Column(name="IC_PERMITE_RENOVACAO_CANAL", nullable=false, length=1)
//	private char permiteRenovacaoCanal;

//	@Column(name="IC_AUTORIZA_MARGEM_RENOVACAO", nullable=false, length=1)
//	private char autorizaMargemRenovacao;

//	@Column(name="PZ_MINIMO_ATRZA_MRGM_RENOVACAO", length=3)
//	private short prazoMinimoAutMargemRenov;

//	@Column(name="IC_PERMITE_CONTRATO_CLIENTE", nullable=false, length=1)
//	private char permiteMaisContrPorCli;

//	@Column(name="IC_INIBE_REMESSA_INADIMPLENTE", nullable=false, length=1)
//	private char inibeRemessaSINAD;

//	@Column(name="IC_TARIFA_AVERBACAO", nullable=false, length=1)
//	private char tarifaAverbacao;

//	@Column(name="IC_CARENCIA", nullable=false, length=1)
//	private char permiteCarencia;

//	@Column(name="PZ_MAXIMO_CARENCIA", length=3)
//	private short prazoMaxCarencia;

//	@Column(name="IC_MORATORIA", nullable=false, length=1)
//	private char permiteMoratoria;

//	@Column(name="PZ_MAXIMO_MORATORIA", length=3)
//	private short prazoMaxMoratoria;

//	@Column(name="IC_EXIGE_CLIENTE_RENOVACAO", nullable=false, length=1)
//	private char exigeClienteRenovacao;

//	@Column(name="IC_FORMA_AVERBACAO", nullable=false, length=1)
//	private byte formaAverbacao;
	
	@Column(name="TS_HISTORICO")
	private Date tsHistorico;
	
	@Column(name="CO_USUARIO")
	private String usuario;
	
	public int getHistorico() {
		return historico;
	}

	public void setHistorico(int historico) {
		this.historico = historico;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getCnpj() {
		return cnpj;
	}

	public void setCnpj(long cnpj) {
		this.cnpj = cnpj;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getGrupo() {
		return grupo;
	}

	public void setGrupo(int grupo) {
		this.grupo = grupo;
	}

	public int getRemessa() {
		return remessa;
	}

	public void setRemessa(int remessa) {
		this.remessa = remessa;
	}

	public int getTipoSituacao() {
		return tipoSituacao;
	}

	public void setTipoSituacao(int tipoSituacao) {
		this.tipoSituacao = tipoSituacao;
	}

	public int getSituacao() {
		return situacao;
	}

	public void setSituacao(int situacao) {
		this.situacao = situacao;
	}

//	public short getSRResp() {
//		return SRResp;
//	}
//
//	public void setSRResp(short sRResp) {
//		SRResp = sRResp;
//	}

	public short getPVResp() {
		return PVResp;
	}

	public void setPVResp(short pVResp) {
		PVResp = pVResp;
	}

//	public short getSRCobranca() {
//		return SRCobranca;
//	}
//
//	public void setSRCobranca(short sRCobranca) {
//		SRCobranca = sRCobranca;
//	}

	public short getPVCobranca() {
		return PVCobranca;
	}

	public void setPVCobranca(short pVCobranca) {
		PVCobranca = pVCobranca;
	}

	public byte getAbrangencia() {
		return abrangencia;
	}

	public void setAbrangencia(byte abrangencia) {
		this.abrangencia = abrangencia;
	}

//	public short getPrazoEmissao() {
//		return prazoEmissao;
//	}
//
//	public void setPrazoEmissao(short prazoEmissao) {
//		this.prazoEmissao = prazoEmissao;
//	}

//	public char getPermiteContratoCanal() {
//		return permiteContratoCanal;
//	}
//
//	public void setPermiteContratoCanal(char permiteContratoCanal) {
//		this.permiteContratoCanal = permiteContratoCanal;
//	}

//	public char getAutorizaMargemContrato() {
//		return autorizaMargemContrato;
//	}
//
//	public void setAutorizaMargemContrato(char autorizaMargemContrato) {
//		this.autorizaMargemContrato = autorizaMargemContrato;
//	}

//	public short getPrazoMinimoAutMargemContr() {
//		return prazoMinimoAutMargemContr;
//	}
//
//	public void setPrazoMinimoAutMargemContr(short prazoMinimoAutMargemContr) {
//		this.prazoMinimoAutMargemContr = prazoMinimoAutMargemContr;
//	}

//	public char getPermiteRenovacaoCanal() {
//		return permiteRenovacaoCanal;
//	}
//
//	public void setPermiteRenovacaoCanal(char permiteRenovacaoCanal) {
//		this.permiteRenovacaoCanal = permiteRenovacaoCanal;
//	}

//	public char getAutorizaMargemRenovacao() {
//		return autorizaMargemRenovacao;
//	}
//
//	public void setAutorizaMargemRenovacao(char autorizaMargemRenovacao) {
//		this.autorizaMargemRenovacao = autorizaMargemRenovacao;
//	}

//	public short getPrazoMinimoAutMargemRenov() {
//		return prazoMinimoAutMargemRenov;
//	}
//
//	public void setPrazoMinimoAutMargemRenov(short prazoMinimoAutMargemRenov) {
//		this.prazoMinimoAutMargemRenov = prazoMinimoAutMargemRenov;
//	}

//	public byte getFormaAverbacao() {
//		return formaAverbacao;
//	}
//
//	public void setFormaAverbacao(byte formaAverbacao) {
//		this.formaAverbacao = formaAverbacao;
//	}

	public short getAgenciaContaCredito() {
		return agenciaContaCredito;
	}

	public void setAgenciaContaCredito(short agenciaContaCredito) {
		this.agenciaContaCredito = agenciaContaCredito;
	}

	public short getOperacaoContaCredito() {
		return operacaoContaCredito;
	}

	public void setOperacaoContaCredito(short operacaoContaCredito) {
		this.operacaoContaCredito = operacaoContaCredito;
	}

	public int getContaCredito() {
		return contaCredito;
	}

	public void setContaCredito(int contaCredito) {
		this.contaCredito = contaCredito;
	}

	public byte getDvContaCredito() {
		return dvContaCredito;
	}

	public void setDvContaCredito(byte dvContaCredito) {
		this.dvContaCredito = dvContaCredito;
	}

	public byte getDiaCreditoSal() {
		return diaCreditoSal;
	}

	public void setDiaCreditoSal(byte diaCreditoSal) {
		this.diaCreditoSal = diaCreditoSal;
	}

//	public short getNaturalSRResp() {
//		return naturalSRResp;
//	}
//
//	public void setNaturalSRResp(short naturalSRResp) {
//		this.naturalSRResp = naturalSRResp;
//	}

	public short getNaturalPVResp() {
		return naturalPVResp;
	}

	public void setNaturalPVResp(short naturalPVResp) {
		this.naturalPVResp = naturalPVResp;
	}

//	public short getNaturalSRCobranca() {
//		return naturalSRCobranca;
//	}
//
//	public void setNaturalSRCobranca(short naturalSRCobranca) {
//		this.naturalSRCobranca = naturalSRCobranca;
//	}

	public short getNaturalPVCobranca() {
		return naturalPVCobranca;
	}

	public void setNaturalPVCobranca(short naturalPVCobranca) {
		this.naturalPVCobranca = naturalPVCobranca;
	}

	public Date getTsHistorico() {
		return tsHistorico;
	}

	public void setTsHistorico(Date tsHistorico) {
		this.tsHistorico = tsHistorico;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	
}
