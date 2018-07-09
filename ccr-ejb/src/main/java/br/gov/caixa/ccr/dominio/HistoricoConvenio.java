package br.gov.caixa.ccr.dominio;

import java.io.Serializable;
import java.util.Date;

import br.gov.caixa.ccr.util.DataUtil;
import br.gov.caixa.ccr.util.Utilities;

public class HistoricoConvenio extends Retorno implements Serializable {
	
	private static final long serialVersionUID = 2274226853805552648L;

	private int historico;
	private int id;
	private long cnpj;  
	private String nome;
	private int grupo;
	private int remessa;	
	private int tipoSituacao;
	private int situacao;
	private short agenciaContaCredito;
	private short operacaoContaCredito; 
	private int contaCredito;
	private byte dvContaCredito;
	private byte diaCreditoSal;
	private short prazoMaximo;
	private short naturalSRResp;
	private short SRResp;
	private short naturalPVResp;
	private short PVResp;
	private short naturalSRCobranca;
	private short SRCobranca;
	private short naturalPVCobranca;
	private short PVCobranca;
	private byte abrangencia;
	private short prazoEmissao;
	private char permiteContratoCanal;
	private char autorizaMargemContrato;
	private short prazoMinimoAutMargemContr;
	private char permiteRenovacaoCanal;
	private char autorizaMargemRenovacao;
	private short prazoMinimoAutMargemRenov;
	private char permiteMaisContrPorCli;
	private char inibeRemessaSINAD;
	private char tarifaAverbacao;
	private char permiteCarencia;
	private short prazoMaxCarencia;
	private char permiteMoratoria;
	private short prazoMaxMoratoria;
	private char exigeClienteRenovacao;
	private byte formaAverbacao;
	private Date tsHistorico;
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
		return Utilities.leftRightTrim(nome);
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

	public short getPrazoMaximo() {
		return prazoMaximo;
	}

	public void setPrazoMaximo(short prazoMaximo) {
		this.prazoMaximo = prazoMaximo;
	}

	public short getSRResp() {
		return SRResp;
	}

	public void setSRResp(short sRResp) {
		SRResp = sRResp;
	}

	public short getPVResp() {
		return PVResp;
	}

	public void setPVResp(short pVResp) {
		PVResp = pVResp;
	}

	public short getSRCobranca() {
		return SRCobranca;
	}

	public void setSRCobranca(short sRCobranca) {
		SRCobranca = sRCobranca;
	}

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

	public short getPrazoEmissao() {
		return prazoEmissao;
	}

	public void setPrazoEmissao(short prazoEmissao) {
		this.prazoEmissao = prazoEmissao;
	}

	public char getPermiteContratoCanal() {
		return permiteContratoCanal;
	}

	public void setPermiteContratoCanal(char permiteContratoCanal) {
		this.permiteContratoCanal = permiteContratoCanal;
	}

	public char getAutorizaMargemContrato() {
		return autorizaMargemContrato;
	}

	public void setAutorizaMargemContrato(char autorizaMargemContrato) {
		this.autorizaMargemContrato = autorizaMargemContrato;
	}

	public short getPrazoMinimoAutMargemContr() {
		return prazoMinimoAutMargemContr;
	}

	public void setPrazoMinimoAutMargemContr(short prazoMinimoAutMargemContr) {
		this.prazoMinimoAutMargemContr = prazoMinimoAutMargemContr;
	}

	public char getPermiteRenovacaoCanal() {
		return permiteRenovacaoCanal;
	}

	public void setPermiteRenovacaoCanal(char permiteRenovacaoCanal) {
		this.permiteRenovacaoCanal = permiteRenovacaoCanal;
	}

	public char getAutorizaMargemRenovacao() {
		return autorizaMargemRenovacao;
	}

	public void setAutorizaMargemRenovacao(char autorizaMargemRenovacao) {
		this.autorizaMargemRenovacao = autorizaMargemRenovacao;
	}

	public short getPrazoMinimoAutMargemRenov() {
		return prazoMinimoAutMargemRenov;
	}

	public void setPrazoMinimoAutMargemRenov(short prazoMinimoAutMargemRenov) {
		this.prazoMinimoAutMargemRenov = prazoMinimoAutMargemRenov;
	}

	public char getPermiteMaisContrPorCli() {
		return permiteMaisContrPorCli;
	}

	public void setPermiteMaisContrPorCli(char permiteMaisContrPorCli) {
		this.permiteMaisContrPorCli = permiteMaisContrPorCli;
	}

	public char getInibeRemessaSINAD() {
		return inibeRemessaSINAD;
	}

	public void setInibeRemessaSINAD(char inibeRemessaSINAD) {
		this.inibeRemessaSINAD = inibeRemessaSINAD;
	}

	public char getTarifaAverbacao() {
		return tarifaAverbacao;
	}

	public void setTarifaAverbacao(char tarifaAverbacao) {
		this.tarifaAverbacao = tarifaAverbacao;
	}

	public char getPermiteCarencia() {
		return permiteCarencia;
	}

	public void setPermiteCarencia(char permiteCarencia) {
		this.permiteCarencia = permiteCarencia;
	}

	public short getPrazoMaxCarencia() {
		return prazoMaxCarencia;
	}

	public void setPrazoMaxCarencia(short prazoMaxCarencia) {
		this.prazoMaxCarencia = prazoMaxCarencia;
	}

	public char getPermiteMoratoria() {
		return permiteMoratoria;
	}

	public void setPermiteMoratoria(char permiteMoratoria) {
		this.permiteMoratoria = permiteMoratoria;
	}

	public short getPrazoMaxMoratoria() {
		return prazoMaxMoratoria;
	}

	public void setPrazoMaxMoratoria(short prazoMaxMoratoria) {
		this.prazoMaxMoratoria = prazoMaxMoratoria;
	}

	public char getExigeClienteRenovacao() {
		return exigeClienteRenovacao;
	}

	public void setExigeClienteRenovacao(char exigeClienteRenovacao) {
		this.exigeClienteRenovacao = exigeClienteRenovacao;
	}

	public byte getFormaAverbacao() {
		return formaAverbacao;
	}

	public void setFormaAverbacao(byte formaAverbacao) {
		this.formaAverbacao = formaAverbacao;
	}

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

	public short getNaturalSRResp() {
		return naturalSRResp;
	}

	public void setNaturalSRResp(short naturalSRResp) {
		this.naturalSRResp = naturalSRResp;
	}

	public short getNaturalPVResp() {
		return naturalPVResp;
	}

	public void setNaturalPVResp(short naturalPVResp) {
		this.naturalPVResp = naturalPVResp;
	}

	public short getNaturalSRCobranca() {
		return naturalSRCobranca;
	}

	public void setNaturalSRCobranca(short naturalSRCobranca) {
		this.naturalSRCobranca = naturalSRCobranca;
	}

	public short getNaturalPVCobranca() {
		return naturalPVCobranca;
	}

	public void setNaturalPVCobranca(short naturalPVCobranca) {
		this.naturalPVCobranca = naturalPVCobranca;
	}

	public String getTsHistorico() {
		return DataUtil.formatarDataHora(tsHistorico);
	}

	public void setTsHistorico(Date tsHistorico) {
		this.tsHistorico = tsHistorico;
	}

	public String getUsuario() {
		return Utilities.leftRightTrim(usuario);
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
}
