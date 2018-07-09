package br.gov.caixa.ccr.rest;

import java.util.Date;
import java.util.List;

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

import br.gov.caixa.ccr.dominio.DocumentoAutoComplete;
import br.gov.caixa.ccr.dominio.GrupoTaxa;
import br.gov.caixa.ccr.dominio.GrupoTaxaAutoComplete;
import br.gov.caixa.ccr.dominio.Retorno;
import br.gov.caixa.ccr.negocio.IGrupoBean;
import br.gov.caixa.ccr.util.RetornoListaVO;

@RequestScoped
@Path("/GrupoTaxa")
public class GrupoTaxaRest extends AbstractSecurityRest {
	
	@Inject 
	private IGrupoBean grupoTaxaService;
	
	
	/********************************************************************************
	 * ******************************* REST COMBOS **********************************
	 * ******************************************************************************/
	
	
	@GET
	@Path("/consulta")
	@Produces({ MediaType.APPLICATION_JSON})
	public RetornoListaVO<GrupoTaxa> consultaGrupoTaxa(
			@QueryParam("codigo") String codigo, 
			@QueryParam("nome") String nome) {
		try {
			return new RetornoListaVO<GrupoTaxa>(grupoTaxaService.listar(codigo,nome));
		} catch (Exception e) {
			return new RetornoListaVO<GrupoTaxa>(-1L, e.getMessage(), Retorno.ERRO_EXCECAO);
		}	
	}
	
	@GET
	@Path("/consultaGrupoTaxa")
	@Produces({ MediaType.APPLICATION_JSON})
	public RetornoListaVO<GrupoTaxaAutoComplete> consultaGrupoTaxa(@QueryParam("desc") String desc) {
		try {

			List<GrupoTaxaAutoComplete> grupoTaxas = grupoTaxaService.consultarAutocomplete(desc);
            return new RetornoListaVO<GrupoTaxaAutoComplete>(grupoTaxas);
		}catch (Exception e) {
			return new RetornoListaVO<GrupoTaxaAutoComplete>(-1L, e.getMessage(), Retorno.ERRO_EXCECAO);
		} 
	}
	
	
	@POST
	@Path("/salvar")
	@Consumes({MediaType.APPLICATION_JSON})
	public Retorno salvarGrupoTaxa(GrupoTaxa grupoTaxa) {		
		try {
			grupoTaxa.setUsuario(getDadosUsuarioLogado().getCodigoUsuario());
			grupoTaxa.setData(new Date());
			return grupoTaxaService.salvar(grupoTaxa);
		} catch (Exception e) {
			return new Retorno(-1L, e.getMessage(), Retorno.ERRO_EXCECAO);
		}
	}
	
	@POST
	@Path("/excluir/{id}")
	public Retorno excluirGrupoTaxa(@PathParam("id") Long id) {
		try {
			return grupoTaxaService.excluir(id);
		} catch (Exception e) {
			return new Retorno(-1L, e.getMessage(), Retorno.ERRO_EXCECAO);
		}
	}
	
}