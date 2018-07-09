package br.gov.caixa.ccr.servico;

import br.gov.caixa.arqrefcore.excecao.BusinessException;
import br.gov.caixa.arqrefcore.excecao.SystemException;
import br.gov.caixa.arqrefcore.jms.Correlation;
import br.gov.caixa.ccr.dominio.Empregado;
import br.gov.caixa.ccr.dominio.barramento.siric.DadosConsultaAvaliacao;
import br.gov.caixa.ccr.dominio.barramento.siric.DadosResultadoAvaliacao;
import br.gov.caixa.ccr.dominio.barramento.siric.DadosResultadoConsulta;

public abstract interface IAvaliacaoCliente {

	public abstract Correlation consultaAvaliacao(DadosConsultaAvaliacao dados, Empregado empregado)
			throws BusinessException, SystemException;
	
	public abstract DadosResultadoConsulta recebeDadosConsulta(Correlation correlation)
			throws BusinessException, SystemException;
	
	public abstract Correlation solicitaAvaliacao(int idSimulacao, Empregado empregado)
			throws BusinessException, SystemException;
	
	public abstract DadosResultadoAvaliacao recebeDadosAvaliacao(Correlation correlation)
			throws BusinessException, SystemException;
	
}
