package br.gov.caixa.ccr.negocio;

import br.gov.caixa.arqrefcore.excecao.BusinessException;
import br.gov.caixa.ccr.dominio.Empregado;
import br.gov.caixa.ccr.dominio.Simular;
import br.gov.caixa.ccr.dominio.barramento.simulacao.DadosResultado;

public interface ISimulacaoBean {
	DadosResultado simulaInterno(Simular dados, Empregado empregado) throws BusinessException, Exception;
	
}
