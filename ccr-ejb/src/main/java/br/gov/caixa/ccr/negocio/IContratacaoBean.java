package br.gov.caixa.ccr.negocio;

import br.gov.caixa.arqrefcore.excecao.BusinessException;
import br.gov.caixa.arqrefcore.excecao.SystemException;
import br.gov.caixa.arqrefcore.jms.Correlation;
import br.gov.caixa.ccr.dominio.Empregado;
import br.gov.caixa.ccr.dominio.barramento.contratacao.DadosContrato;
import br.gov.caixa.ccr.dominio.barramento.contratacao.DadosResultado;

public interface IContratacaoBean {

	Correlation contrataCreditoConsignado(DadosContrato dados, Empregado empregado)
			throws BusinessException, SystemException;
	
	DadosResultado recebeDadosContratacao(Correlation correlation)
			throws BusinessException, SystemException;

}
