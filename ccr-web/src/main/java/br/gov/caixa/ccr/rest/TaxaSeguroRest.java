package br.gov.caixa.ccr.rest;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import br.gov.caixa.ccr.dominio.Retorno;
import br.gov.caixa.ccr.dominio.TaxaSeguroFaixa;
import br.gov.caixa.ccr.negocio.ITaxaSeguroBean;
import br.gov.caixa.ccr.util.RetornoListaVO;

@RequestScoped
@Path("/TaxaSeguro")
public class TaxaSeguroRest extends AbstractSecurityRest {
	
	@Inject
	private ITaxaSeguroBean taxaSeguroService;
	
	/********************************************************************************
	 * **************************** REST TAXA SEGURO ********************************
	 * ******************************************************************************/

	@GET
	@Path("/lista/") 
	@Produces({ MediaType.APPLICATION_JSON})
	public RetornoListaVO<TaxaSeguroFaixa> listarTaxaSeguro(
			@QueryParam("dataInicioVigencia") String dataInicioVigencia, 
			@QueryParam("dataFinalVigencia") String dataFinalVigencia) {
		try {
			return new RetornoListaVO<TaxaSeguroFaixa>(taxaSeguroService.listar(dataInicioVigencia, dataFinalVigencia));
		} catch (Exception e) {
			return new RetornoListaVO<TaxaSeguroFaixa>(-1L, e.getMessage(), Retorno.ERRO_EXCECAO);
		}
	}
	
	@POST
	@Path("/salvar")
	@Consumes({MediaType.APPLICATION_JSON})
	public Retorno salvarTaxaSeguro(TaxaSeguroFaixa taxaSeguroFaixa, @QueryParam("inicioVigenciaChave") String inicioVigenciaChave) {
		try {
			return taxaSeguroService.salvar(taxaSeguroFaixa, inicioVigenciaChave, getDadosUsuarioLogado().getCodigoUsuario());
		} catch (Exception e) {
			return new Retorno(-1L, e.getMessage(), Retorno.ERRO_EXCECAO);
		}
	}
	
	@POST
	@Path("/excluir")
	public Retorno excluirTaxaSeguro(TaxaSeguroFaixa taxaSeguroFaixa) {
		try {
			return taxaSeguroService.excluir(taxaSeguroFaixa);
		} catch (Exception e) {
			return new Retorno(-1L, e.getMessage(), Retorno.ERRO_EXCECAO);
		}
	}
	
	
}