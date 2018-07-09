package br.gov.caixa.ccr.negocio;

import javax.ejb.Local;

import br.gov.caixa.ccr.dominio.ConsultaPrazo;


@Local
public interface IEmprestimoBean {

	ConsultaPrazo consultarPrazos(String idConvenio, Long canal) throws Exception;
}
