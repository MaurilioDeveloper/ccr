package br.gov.caixa.ccr.negocio;

import javax.ejb.Local;

import br.gov.caixa.ccr.dominio.Convenio;
import br.gov.caixa.ccr.dominio.Retorno;


@Local
public interface IConvenioValidacao {

	Retorno validar(Convenio convenio, String usuario, boolean isUserInRole) throws Exception;
	Retorno validaSR(Long valor) throws Exception;
	Retorno validaAG(Long valor) throws Exception;
	
	
}
