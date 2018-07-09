package br.gov.caixa.ccr.rest;

import java.util.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.gov.caixa.ccr.dominio.Retorno;
import br.gov.caixa.ccr.dominio.Unidade;
import br.gov.caixa.ccr.negocio.IUnidadeBean;

@Path("/unidade")
public class UnidadeRest extends AbstractSecurityRest {

	protected Logger log = Logger.getLogger(getClass().getSimpleName());	

	@Inject
	IUnidadeBean unidadeService;
	
	@GET
	@Path("/consultaUnidadePorNumeroUnidade/{nuUnidade}")	
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Unidade consultarUnidadePorNumeroUnidade(@PathParam("nuUnidade") Long nuUnidade) {
		try {
			log.info("UnidadeRest - INICIO getUnidadePorNumeroUnidade");
			Unidade unidade = unidadeService.consultaAgenciaPorNuUnidade(nuUnidade);
			log.info("UnidadeRest - FIM getUnidadePorNumeroUnidade");
			return unidade;
			
		}catch (Exception e) {
			return new Unidade(-1L, e.getMessage(), Retorno.ERRO_EXCECAO);
		}
	}

}
