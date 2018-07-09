package br.gov.caixa.ccr.negocio;

import br.gov.caixa.ccr.dominio.Retorno;
import br.gov.caixa.ccr.dominio.Unidade;

public interface IUnidadeBean {

	Unidade consultaAgenciaPorNuUnidade(Long nuUnidade) throws Exception;
	Retorno validaSR(Long valor) throws Exception;
	Retorno validaAG(Long valor) throws Exception;

}
