package br.gov.caixa.ccr.negocio;

import java.util.List;

import br.gov.caixa.arqrefcore.excecao.BusinessException;
import br.gov.caixa.arqrefcore.excecao.SystemException;
import br.gov.caixa.arqrefservices.dominio.Empregado;
import br.gov.caixa.ccr.dominio.Contrato;
import br.gov.caixa.ccr.dominio.ContratoSituacaoRequest;
import br.gov.caixa.ccr.dominio.Retorno;
import br.gov.caixa.ccr.dominio.SituacaoContrato;
import br.gov.caixa.ccr.dominio.apx.ContratoConsignadoTO;
import br.gov.caixa.ccr.dominio.transicao.ContratoTO;

public interface IContratoBean {

	Contrato consultarContratoPorId(Long id, String userlog) throws Exception;
	
	List<Contrato> listar(Long cpf, Long contrato, Long situacao, String periodoINI, String periodoFIM, Long nuCnpjFontePagadora, Long nuAgenciaContaCredito, Long convenio, Long sr,  String userLog) throws Exception;

	Retorno salvar(Contrato contrato, SituacaoContrato situacao) throws Exception;
	
	Retorno atualizaSituacaoContrato(ContratoSituacaoRequest contrato) throws Exception;
	
	Retorno atualizaContrato(Contrato contrato) throws Exception;
	
	Long consultarCpfPorContrato(Long idContrato) throws SystemException, BusinessException;

	ContratoTO consultarContratoPorNr(Long idContrato) throws SystemException, BusinessException;
	
	ContratoConsignadoTO reenviarContratoSIAPX(Long numeroContrato, Empregado empregado) throws Exception;
	
}
