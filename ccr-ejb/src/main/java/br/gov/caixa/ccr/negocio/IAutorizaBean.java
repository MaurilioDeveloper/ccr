package br.gov.caixa.ccr.negocio;

import br.gov.caixa.ccr.dominio.Retorno;

public interface IAutorizaBean {

	Retorno autorizar(Long contrato, String usuario) throws Exception;
	Retorno cancelar(Long contrato, String usuario) throws Exception;

}
