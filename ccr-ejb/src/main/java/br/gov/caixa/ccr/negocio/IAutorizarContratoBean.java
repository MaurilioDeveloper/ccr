package br.gov.caixa.ccr.negocio;

import java.text.ParseException;
import java.util.List;

import br.gov.caixa.arqrefcore.excecao.BusinessException;
import br.gov.caixa.arqrefcore.excecao.SystemException;
import br.gov.caixa.arqrefservices.dominio.Empregado;
import br.gov.caixa.ccr.dominio.AutorizacaoContrato;
import br.gov.caixa.ccr.dominio.AutorizarContrato;
import br.gov.caixa.ccr.enums.EnumTipoAutorizacao;

public interface IAutorizarContratoBean {

	List<AutorizacaoContrato> buscaAutorizacaoPorContrato(Long idContrato)
			throws Exception;

	Object buscaAutorizacaoPorSituacao(String situacao, Long nuContrato, Long nuSituacaoContrato);

	List<AutorizacaoContrato> autorizar(AutorizarContrato dados, EnumTipoAutorizacao tipoUsuario, Empregado empregado)
			throws BusinessException, SystemException, ParseException;	
	
}
