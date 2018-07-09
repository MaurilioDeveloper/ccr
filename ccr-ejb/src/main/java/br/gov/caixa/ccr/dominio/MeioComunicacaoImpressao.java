package br.gov.caixa.ccr.dominio;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

import br.gov.caixa.arqrefservices.dominio.sicli.MeioComunicacao;


public class MeioComunicacaoImpressao implements Serializable {

	private static final long serialVersionUID = 7800792923497852841L;

	private String ocorrencia;

	private String produto;

	private String contrato;

	private String meioComunicao;

	private String prefixoDiscagem;

	private String codigoComunicacao;

	private String nomeContato;

	private String finalidade;

	private String brancos;

	private String flagComunicacao;

	private String flagPropagando;

	private String flagComprovacao;

	private String dataApuracao;
	
	public MeioComunicacaoImpressao(MeioComunicacao meioComunicacao) throws IllegalAccessException, InvocationTargetException {
		if(meioComunicacao != null){
			BeanUtils.copyProperties(this, meioComunicacao);
		}
	}

	public String getOcorrencia() {
		return ocorrencia;
	}

	public void setOcorrencia(String ocorrencia) {
		this.ocorrencia = ocorrencia;
	}

	public String getProduto() {
		return produto;
	}

	public void setProduto(String produto) {
		this.produto = produto;
	}


	public String getContrato() {
		return contrato;
	}

	public void setContrato(String contrato) {
		this.contrato = contrato;
	}


	public String getMeioComunicao() {
		return meioComunicao;
	}

	public void setMeioComunicao(String meioComunicao) {
		this.meioComunicao = meioComunicao;
	}

	
	public String getPrefixoDiscagem() {
		return prefixoDiscagem;
	}

	public void setPrefixoDiscagem(String prefixoDiscagem) {
		this.prefixoDiscagem = prefixoDiscagem;
	}


	public String getCodigoComunicacao() {
		return codigoComunicacao;
	}

	public void setCodigoComunicacao(String codigoComunicacao) {
		this.codigoComunicacao = codigoComunicacao;
	}

	
	public String getNomeContato() {
		return nomeContato;
	}

	public void setNomeContato(String nomeContato) {
		this.nomeContato = nomeContato;
	}

	
	public String getFinalidade() {
		return finalidade;
	}

	public void setFinalidade(String finalidade) {
		this.finalidade = finalidade;
	}

	public String getBrancos() {
		return brancos;
	}

	public void setBrancos(String brancos) {
		this.brancos = brancos;
	}

	public String getFlagComunicacao() {
		return flagComunicacao;
	}

	public void setFlagComunicacao(String flagComunicacao) {
		this.flagComunicacao = flagComunicacao;
	}

	public String getFlagPropagando() {
		return flagPropagando;
	}

	public void setFlagPropagando(String flagPropagando) {
		this.flagPropagando = flagPropagando;
	}
 
	public String getFlagComprovacao() {
		return flagComprovacao;
	}

	public void setFlagComprovacao(String flagComprovacao) {
		this.flagComprovacao = flagComprovacao;
	}

	public String getDataApuracao() {
		return dataApuracao;
	}

	public void setDataApuracao(String dataApuracao) {
		this.dataApuracao = dataApuracao;
	}

	@Override
	public String toString() {
		return "MeioComunicacao [ocorrencia=" + ocorrencia + ", produto=" + produto + ", contrato=" + contrato
				+ ", meioComunicao=" + meioComunicao + ", prefixoDiscagem=" + prefixoDiscagem + ", codigoComunicacao="
				+ codigoComunicacao + ", nomeContato=" + nomeContato + ", finalidade=" + finalidade + ",brancos="
				+ brancos + ", flagComunicacao=" + flagComunicacao + ", flagPropagando=" + flagPropagando
				+ ", flagComprovacao=" + flagComprovacao + ", dataApuracao=" + dataApuracao + "]";
	}

}
