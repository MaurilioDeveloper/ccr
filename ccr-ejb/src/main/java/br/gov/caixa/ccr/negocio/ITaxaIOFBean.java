package br.gov.caixa.ccr.negocio;

import java.util.List;

import br.gov.caixa.ccr.dominio.Retorno;
import br.gov.caixa.ccr.dominio.TaxaIOF;

public interface ITaxaIOFBean {

	TaxaIOF findVigente() throws Exception;
	List<TaxaIOF> listar(String dataInicioVigencia) throws Exception;
	List<TaxaIOF> listar(String dataInicioVigencia, String dataFinalVigencia) throws Exception;
	Retorno salvar(TaxaIOF taxaIOF, String codigoUsuario) throws Exception;
	Retorno excluir(Integer id) throws Exception;
	
}