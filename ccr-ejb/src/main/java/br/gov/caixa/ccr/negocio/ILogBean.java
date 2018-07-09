package br.gov.caixa.ccr.negocio;

import java.util.List;

import br.gov.caixa.ccr.dominio.Log;

public interface ILogBean {

	List<Log> listar(String dataInicio, String dataFim, Integer transacao) throws Exception;
	
}
