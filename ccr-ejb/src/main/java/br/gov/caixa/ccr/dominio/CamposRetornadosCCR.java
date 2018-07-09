package br.gov.caixa.ccr.dominio;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import br.gov.caixa.arqrefservices.dominio.sicli.CamposRetornados;
import br.gov.caixa.ccr.dominio.mensageria.ResponseArtRef;

public class CamposRetornadosCCR extends CamposRetornados {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ResponseArtRef responseArqRef = new ResponseArtRef();

	public ResponseArtRef getResponseArqRef() {
		return responseArqRef;
	}

	public void setResponseArqRef(ResponseArtRef responseArqRef) {
		this.responseArqRef = responseArqRef;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
