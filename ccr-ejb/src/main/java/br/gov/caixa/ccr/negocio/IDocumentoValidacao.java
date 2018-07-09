package br.gov.caixa.ccr.negocio;

import javax.ejb.Local;

import br.gov.caixa.ccr.dominio.Documento;
import br.gov.caixa.ccr.dominio.Retorno;


@Local
public interface IDocumentoValidacao {
	Retorno validar(Documento documento, String usuario) throws Exception;
}
