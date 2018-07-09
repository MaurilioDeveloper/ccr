package br.gov.caixa.ccr.negocio;

import java.util.List;

import br.gov.caixa.ccr.dominio.ArquivoContratoRequest;
import br.gov.caixa.ccr.dominio.ArquivoContratoResponse;

public interface IArquivoBean {
	
	List<ArquivoContratoResponse> consultar(ArquivoContratoRequest arquivo) throws Exception;
}
