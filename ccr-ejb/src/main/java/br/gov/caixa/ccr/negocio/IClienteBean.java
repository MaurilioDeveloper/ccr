package br.gov.caixa.ccr.negocio;

import javax.ejb.Local;

import br.gov.caixa.arqrefcore.excecao.BusinessException;
import br.gov.caixa.arqrefservices.dominio.sicli.CamposRetornados;
import br.gov.caixa.ccr.dominio.Usuario;
import br.gov.caixa.ccr.dominio.barramento.alteracaocliente.AtualizacaoCliente;

@Local
public interface IClienteBean {

	CamposRetornados consultar(String cnpj, String userLog) throws Exception;

	CamposRetornados consultarPF(String cpf, String userLog) throws Exception;

	String atualizarDadosCliente(AtualizacaoCliente atualizacaoCliente, Usuario usuario) throws BusinessException;
}
