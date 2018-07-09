package br.gov.caixa.ccr.negocio;

import java.text.ParseException;

import br.gov.caixa.ccr.dominio.AuditoriaRetorno;

public interface IAuditoriaBean {

	AuditoriaRetorno consultar(Long nuContrato, Long cpf, Long cnpj, Long convenio, String dtInicio, String dtFim,
			String usuario, Long transacao, Integer length, Long qtdTotalRegistros, Integer pagina) throws ParseException;
	
}
