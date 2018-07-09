package br.gov.caixa.ccr.negocio;

import java.util.List;

import br.gov.caixa.ccr.dominio.ConvenioCanal;

public interface IConvenioCanalBean {

	List<ConvenioCanal> lista(Long idConvenio, List<Long> canais) throws Exception;

}
