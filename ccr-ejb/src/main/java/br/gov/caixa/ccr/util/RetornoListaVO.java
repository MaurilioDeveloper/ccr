package br.gov.caixa.ccr.util;

import java.util.ArrayList;
import java.util.List;

import br.gov.caixa.ccr.dominio.Retorno;

public class RetornoListaVO<E extends Retorno> {

	private Long codigoRetorno = 0L;
	private String mensagemRetorno = "";
	private String tipoRetorno = Retorno.SUCESSO;
	private String idMsg = "";
	private List<E> listaRetorno;

	public RetornoListaVO(List<E> listaRetorno) {
		this.listaRetorno = listaRetorno;
	}

	public RetornoListaVO(Long codigo, String mensagem) {
		this.codigoRetorno = codigo;
		this.mensagemRetorno = mensagem;
		this.listaRetorno = new ArrayList<E>();
	}

	public RetornoListaVO(Long codigo, String mensagem, String tipo) {
		this.codigoRetorno = codigo;
		this.mensagemRetorno = mensagem;
		this.tipoRetorno = tipo;
		this.listaRetorno = new ArrayList<E>();
	}

	public RetornoListaVO(Long codigo, String mensagem, String tipo, String idMsg) {
		this.codigoRetorno = codigo;
		this.mensagemRetorno = mensagem;
		this.tipoRetorno = tipo;
		this.idMsg = idMsg;
		this.listaRetorno = new ArrayList<E>();
	}

	public Long getCodigoRetorno() {
		return codigoRetorno;
	}

	public String getMensagemRetorno() {
		return mensagemRetorno;
	}

	public List<E> getListaRetorno() {
		return listaRetorno;
	}

	public String getTipoRetorno() {
		return tipoRetorno;
	}

	public String getIdMsg() {
		return idMsg;
	}
}
