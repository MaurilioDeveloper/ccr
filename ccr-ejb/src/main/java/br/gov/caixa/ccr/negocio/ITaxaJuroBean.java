package br.gov.caixa.ccr.negocio;

import java.util.List;

import br.gov.caixa.ccr.dominio.Retorno;
import br.gov.caixa.ccr.dominio.TaxaJuro;

public interface ITaxaJuroBean {

	List<TaxaJuro> listar(String tipoConsulta, int codigo,String utilizarEm, String dataInicial,String dataFinal, String convenio) throws Exception;
	TaxaJuro findVigente(int codigoConvenio, int codigoGrupo, int prazo) throws Exception;
	Retorno salvar(TaxaJuro taxaJuro, String inicioVigenciaChave, String coUsuario) throws Exception;
	Retorno excluir(TaxaJuro taxaJuro) throws Exception;
	
}
