package br.gov.caixa.ccr.dao;

import br.gov.caixa.arqrefcore.excecao.BusinessException;
import br.gov.caixa.ccr.dominio.Usuario;
import br.gov.caixa.ccr.dominio.barramento.alteracaocliente.AtualizarCliente;

public interface AtualizarClienteDAO {
	AtualizarCliente atualizarCliente(AtualizarCliente atualizarCliente, Usuario usuario) throws BusinessException;
}
