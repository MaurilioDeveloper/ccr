package br.gov.caixa.ccr.rest;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import br.gov.caixa.ccr.dominio.Retorno;
import br.gov.caixa.ccr.dominio.TaxaJuro;
import br.gov.caixa.ccr.negocio.IEmprestimoBean;
import br.gov.caixa.ccr.negocio.ILogBean;
import br.gov.caixa.ccr.util.RetornoListaVO;

@RequestScoped
@Path("/emprestimo")
public class EmprestimoRest extends AbstractSecurityRest {
	
	@Inject
	private IEmprestimoBean emprestimoService;
	
	@Inject 
	private ILogBean logService;
	
	
	/********************************************************************************
	 * ******************************* REST COMBOS **********************************
	 * ******************************************************************************/
	
	@SuppressWarnings("unchecked")
	@GET
	@Path("/consultarPrazos")
	@Produces({ MediaType.APPLICATION_JSON})
	public Object consultarPrazos(@QueryParam("idConvenio") String idConvenio, @QueryParam("idCanal") String idCanal) {
		try {
			Object o = emprestimoService.consultarPrazos(idConvenio,Long.valueOf( idCanal));
			if (o instanceof Retorno)
				return o;
			return new RetornoListaVO<TaxaJuro>((List<TaxaJuro>) o);
		} catch (Exception e) {
			return new RetornoListaVO<TaxaJuro>(-1L, e.getMessage(), Retorno.ERRO_EXCECAO);
		}
	}
		
	
}