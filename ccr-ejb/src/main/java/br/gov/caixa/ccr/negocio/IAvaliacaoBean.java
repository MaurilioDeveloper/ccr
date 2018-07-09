package br.gov.caixa.ccr.negocio;

import br.gov.caixa.arqrefservices.dominio.siric.AvaliacaoValiada;

public interface IAvaliacaoBean {

	Long salvarAvaliacao(AvaliacaoValiada avaliacao, String userlog) throws Exception;

	
}
