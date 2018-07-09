package br.gov.caixa.ccr.dominio.mensageria;

import java.io.Serializable;

public class Status implements Serializable {

	private static final long serialVersionUID = 1L;

	private String tipoRetorno;

	private String codigo;

	private String mensagem;

	private String origemErro;

	private String paragrafoErro;

	private String mensagemNegocial;

	private String categoriaErro;

	private String tipo;

	public Status() {
		this.tipoRetorno = "0";
		this.codigo = "0";
	}

	public Status(String mensagem) {
		this.codigo = "0";
		this.mensagem = mensagem;
	}

	public Status(String tipoRetorno, String codigo, String mensagem, String origemErro, String paragrafoErro,
			String mensagemNegocial, String categoriaErro, String tipo) {
		super();
		this.tipoRetorno = tipoRetorno;
		this.codigo = codigo;
		this.mensagem = mensagem;
		this.origemErro = origemErro;
		this.paragrafoErro = paragrafoErro;
		this.mensagemNegocial = mensagemNegocial;
		this.categoriaErro = categoriaErro;
		this.tipo = tipo;
	}

	/**
	 * @return the codigo
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo
	 *            the codigo to set
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return the mensagem
	 */
	public String getMsg() {
		return mensagem;
	}

	/**
	 * @param mensagem
	 *            the mensagem to set
	 */
	public void setMsg(String mensagem) {
		this.mensagem = mensagem;
	}

	public String getTipoRetorno() {
		return tipoRetorno;
	}

	public void setTipoRetorno(String tipoRetorno) {
		this.tipoRetorno = tipoRetorno;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public String getOrigemErro() {
		return origemErro;
	}

	public void setOrigemErro(String origemErro) {
		this.origemErro = origemErro;
	}

	public String getParagrafoErro() {
		return paragrafoErro;
	}

	public void setParagrafoErro(String paragrafoErro) {
		this.paragrafoErro = paragrafoErro;
	}

	public String getMensagemNegocial() {
		return mensagemNegocial;
	}

	public void setMensagemNegocial(String mensagemNegocial) {
		this.mensagemNegocial = mensagemNegocial;
	}

	public String getCategoriaErro() {
		return categoriaErro;
	}

	public void setCategoriaErro(String categoriaErro) {
		this.categoriaErro = categoriaErro;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}
