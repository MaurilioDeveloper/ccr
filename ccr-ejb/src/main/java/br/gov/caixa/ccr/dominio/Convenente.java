package br.gov.caixa.ccr.dominio;

import br.gov.caixa.ccr.util.Utilities;

public class Convenente {

	private Long cnpj;
	private String razaoSocial;
	private String logradouro;
	private String complemento;
	private String numero;
	private String bairro;
	private String cidade;
	private String uf;
	private Integer cep;
	private Integer complementoCep;
	private Integer qtEmpregado;
	private Integer nuPessoa;

	public Long getCnpj() {
		return cnpj;
	}

	public void setCnpj(Long cnpj) {
		this.cnpj = cnpj;
	}

	public String getRazaoSocial() {
		return Utilities.leftRightTrim(razaoSocial);
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getLogradouro() {
		return Utilities.leftRightTrim(logradouro);
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getComplemento() {
		return Utilities.leftRightTrim(complemento);
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getNumero() {
		return Utilities.leftRightTrim(numero);
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getBairro() {
		return Utilities.leftRightTrim(bairro);
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return Utilities.leftRightTrim(cidade);
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getUf() {
		return Utilities.leftRightTrim(uf);
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public Integer getCep() {
		return cep;
	}

	public void setCep(Integer cep) {
		this.cep = cep;
	}

	public Integer getComplementoCep() {
		return complementoCep;
	}

	public void setComplementoCep(Integer complementoCep) {
		this.complementoCep = complementoCep;
	}

	public Integer getQtEmpregado() {
		return qtEmpregado;
	}

	public void setQtEmpregado(Integer qtEmpregado) {
		this.qtEmpregado = qtEmpregado;
	}

	public Integer getNuPessoa() {
		return nuPessoa;
	}

	public void setNuPessoa(Integer nuPessoa) {
		this.nuPessoa = nuPessoa;
	}
	
}
