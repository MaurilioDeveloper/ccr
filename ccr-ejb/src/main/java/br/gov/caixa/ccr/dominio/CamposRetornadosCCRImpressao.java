package br.gov.caixa.ccr.dominio;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import br.gov.caixa.arqrefservices.dominio.sicli.CamposRetornados;
import br.gov.caixa.ccr.dominio.mensageria.ResponseArtRef;

public class CamposRetornadosCCRImpressao extends CamposRetornadosImpressao {

	public CamposRetornadosCCRImpressao(CamposRetornados sicli)
			throws IllegalAccessException, InvocationTargetException {
		super(sicli);
	}

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
