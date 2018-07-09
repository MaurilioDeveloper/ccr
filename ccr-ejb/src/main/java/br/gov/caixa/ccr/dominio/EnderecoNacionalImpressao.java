package br.gov.caixa.ccr.dominio;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

import br.gov.caixa.arqrefservices.dominio.sicli.EnderecoNacional;
import br.gov.caixa.ccr.template.formatters.FormatEnum;
import br.gov.caixa.ccr.template.formatters.TemplateFormat;


public class EnderecoNacionalImpressao implements Serializable {

	private static final long serialVersionUID = 4205907339598025484L;

	private String ocorencia;

	private String produto;

	private String contrato;

	private String tipoLogradouro;

	private String logradouro;

	private String numero;

	private String complemento;

	private String bairro;

	private String municipio;

	private String nomeMunicipio;

	@TemplateFormat(format=FormatEnum.CEP)
	private String cep;

	private String uf;

	private String flagFinalidade;

	private String flagCorrepondencia;

	private String flagPropaganda;


	public EnderecoNacionalImpressao(EnderecoNacional enderecoNacional) throws IllegalAccessException, InvocationTargetException {
		if(enderecoNacional != null){
			BeanUtils.copyProperties(this, enderecoNacional);
		}
	}

	public String getOcorencia() {
		return ocorencia;
	}

	public void setOcorencia(String ocorencia) {
		this.ocorencia = ocorencia;
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

	
	public String getTipoLogradouro() {
		return tipoLogradouro;
	}

	public void setTipoLogradouro(String tipoLogradouro) {
		this.tipoLogradouro = tipoLogradouro;
	}

	
	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}


	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	
	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}


	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	
	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	
	public String getNomeMunicipio() {
		return nomeMunicipio;
	}

	public void setNomeMunicipio(String nomeMunicipio) {
		this.nomeMunicipio = nomeMunicipio;
	}


	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}


	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}


	public String getFlagFinalidade() {
		return flagFinalidade;
	}

	public void setFlagFinalidade(String flagFinalidade) {
		this.flagFinalidade = flagFinalidade;
	}


	public String getFlagCorrepondencia() {
		return flagCorrepondencia;
	}

	public void setFlagCorrepondencia(String flagCorrepondencia) {
		this.flagCorrepondencia = flagCorrepondencia;
	}

	public String getFlagPropaganda() {
		return flagPropaganda;
	}

	public void setFlagPropaganda(String flagPropaganda) {
		this.flagPropaganda = flagPropaganda;
	}


	@Override
	public String toString() {
		return "EnderecoNacional [ocorencia=" + ocorencia + ", produto=" + produto + ", tipoLogradouro="
				+ tipoLogradouro + ", logradouro=" + logradouro + ", numero=" + numero + ", complemento=" + complemento
				+ ", bairro=" + bairro + ", municipio=" + municipio + ", nomeMunicipio=" + nomeMunicipio + ", cep="
				+ cep + ", uf=" + uf + ", flagFinalidade=" + flagFinalidade + ", flagCorrepondencia="
				+ flagCorrepondencia + ", flagPropaganda=" + flagPropaganda + "]";
	}

}
