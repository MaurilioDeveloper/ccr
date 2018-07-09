package br.gov.caixa.ccr.negocio;

import java.util.List;

import br.gov.caixa.ccr.dominio.GrupoTaxa;
import br.gov.caixa.ccr.dominio.GrupoTaxaAutoComplete;
import br.gov.caixa.ccr.dominio.Retorno;

public interface IGrupoBean {

	List<GrupoTaxa> listar() throws Exception;
	List<GrupoTaxa> listar(String codigo, String nome) throws Exception;
	Retorno salvar(GrupoTaxa grupo) throws Exception;
	Retorno excluir(Long codigo) throws Exception;
	Long buscarPorCodigo(Integer codigo) throws Exception;
	List<GrupoTaxaAutoComplete> consultarAutocomplete(String desc) throws Exception;
	

	
}
