package br.gov.caixa.ccr.negocio;

import java.util.List;

import br.gov.caixa.ccr.dominio.Retorno;
import br.gov.caixa.ccr.dominio.TaxaSeguro;
import br.gov.caixa.ccr.dominio.TaxaSeguroFaixa;

public interface ITaxaSeguroBean {

	List<TaxaSeguroFaixa> listar(String dateIni, String dateFim) throws Exception;
	TaxaSeguro findVigente(int prazo, int idade) throws Exception;
	Retorno salvar(TaxaSeguroFaixa taxaSeguroFaixa, String inicioVigenciaChave, String codigoUsuario) throws Exception;
	Retorno excluir(TaxaSeguroFaixa taxaSeguroFaixa) throws Exception;
	
}
