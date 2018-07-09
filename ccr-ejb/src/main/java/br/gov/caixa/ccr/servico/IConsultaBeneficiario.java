package br.gov.caixa.ccr.servico;

import br.gov.caixa.arqrefcore.excecao.BusinessException;
import br.gov.caixa.arqrefcore.excecao.SystemException;
import br.gov.caixa.arqrefcore.jms.Correlation;
import br.gov.caixa.ccr.dominio.Empregado;
import br.gov.caixa.ccr.dominio.barramento.siabe.DadosConsultaBeneficio;
import br.gov.caixa.ccr.dominio.barramento.siabe.DadosResultado;

public abstract interface IConsultaBeneficiario {

	public abstract Correlation consultaBeneficio(DadosConsultaBeneficio dados, Empregado empregado)
			throws BusinessException, SystemException;
	
	public abstract DadosResultado recebeDadosBeneficio(Correlation correlation)
			throws BusinessException, SystemException;
	
}
