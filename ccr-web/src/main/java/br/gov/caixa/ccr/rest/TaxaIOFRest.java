package br.gov.caixa.ccr.rest;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import br.gov.caixa.ccr.dominio.Combo;
import br.gov.caixa.ccr.dominio.Retorno;
import br.gov.caixa.ccr.dominio.TaxaIOF;
import br.gov.caixa.ccr.negocio.IComboBean;
import br.gov.caixa.ccr.negocio.ITaxaIOFBean;
import br.gov.caixa.ccr.util.RetornoListaVO;

@RequestScoped
@Path("/TaxaIOF")
public class TaxaIOFRest extends AbstractSecurityRest {
	
	@Inject 
	private ITaxaIOFBean taxaIOFService;
	
	@Inject
	private IComboBean comboService;
	
	/********************************************************************************
	 * ******************************* REST COMBOS **********************************
	 * ******************************************************************************/
	@POST
	@Path("/Combo/consultar")
	@Produces({ MediaType.APPLICATION_JSON})
	public RetornoListaVO<Combo> carregarCombo(Combo combo) {
		try {
			return new RetornoListaVO<Combo>(comboService.consultar(combo));
		} catch (Exception e) {
			return new RetornoListaVO<Combo>(-1L, e.getMessage(), Retorno.ERRO_EXCECAO);
		}
	}	
	
	@GET
	@Path("/lista")
	@Produces({ MediaType.APPLICATION_JSON})
	public RetornoListaVO<TaxaIOF> listarTaxaIOF() {
		return listarTaxaSeguro(null);	
	}
	
	
	@GET
	@Path("/listaRange")
	@Consumes({MediaType.APPLICATION_JSON})
	public RetornoListaVO<TaxaIOF> listarTaxaIOF(
			@QueryParam("dataIni") String dataIni, 
			@QueryParam("dataFim") String dataFim ) {		
		try {
			RetornoListaVO a = new RetornoListaVO<TaxaIOF>(taxaIOFService.listar(dataIni, dataFim));
			
			
			return a;
		} catch (Exception e) {
			return new RetornoListaVO<TaxaIOF>(-1L, e.getMessage(), Retorno.ERRO_EXCECAO);
		}
	}
	
	@GET
	@Path("/lista/{dataInicioVigencia}")
	@Produces({ MediaType.APPLICATION_JSON})
	public RetornoListaVO<TaxaIOF> listarTaxaSeguro(@PathParam("dataInicioVigencia") String dataInicioVigencia) {
		try {
			return new RetornoListaVO<TaxaIOF>(taxaIOFService.listar(dataInicioVigencia));
		} catch (Exception e) {
			return new RetornoListaVO<TaxaIOF>(-1L, e.getMessage(), Retorno.ERRO_EXCECAO);
		}	
	}
	
	
	@POST
	@Path("/salvar")
	@Consumes({MediaType.APPLICATION_JSON})
	public Retorno salvarTaxaIOF(TaxaIOF taxaIOF) {		
		try {
			return taxaIOFService.salvar(taxaIOF, getDadosUsuarioLogado().getCodigoUsuario());
		} catch (Exception e) {
			return new Retorno(-1L, e.getMessage(), Retorno.ERRO_EXCECAO);
		}
	}
	
	@POST
	@Path("/excluir/{id}")
	public Retorno excluirTaxaIOF(@PathParam("id") int id) {
		try {
			return taxaIOFService.excluir(id);
		} catch (Exception e) {
			return new Retorno(-1L, e.getMessage(), Retorno.ERRO_EXCECAO);
		}
	}
	
}