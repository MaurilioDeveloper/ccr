package br.gov.caixa.ccr.negocio.bo.cobol;

import javax.ejb.Local;

import br.gov.caixa.arqrefservices.dominio.Empregado;
import br.gov.caixa.arqrefservices.dominio.sicli.CamposRetornados;
import br.gov.caixa.ccr.dominio.apx.ContratoConsignadoTO;
import br.gov.caixa.ccr.dominio.transicao.ContratoTO;

@Local
public interface IRegistraContratoConsignadoAPX {

	ContratoConsignadoTO contratarConsignado(ContratoTO contratoTo, CamposRetornados camposRetornados, Empregado empregado) throws Exception;
	void enviarContrato();
}
