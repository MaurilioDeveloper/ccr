package br.gov.caixa.ccr.rest;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import br.gov.caixa.arqrefcore.excecao.BusinessException;
import br.gov.caixa.ccr.dominio.Documento;
import br.gov.caixa.ccr.dominio.DocumentoAutoComplete;
import br.gov.caixa.ccr.dominio.Retorno;
import br.gov.caixa.ccr.negocio.IDocumentoBean;
import br.gov.caixa.ccr.util.RetornoListaVO;

@RequestScoped
@Path("/Documento")
public class DocumentoRest extends AbstractSecurityRest {
	
	@Inject 
	private IDocumentoBean documentoService;
	
	@POST
	@Path("/consulta")
	@Consumes({MediaType.APPLICATION_JSON})
	public RetornoListaVO<Documento> consultaDocumento(Documento documento) {		
			
		try {
			return new RetornoListaVO<Documento>(documentoService.listaDocumento(documento));
		} catch (Exception e) {
			return new RetornoListaVO<Documento>(-1L, e.getMessage(), Retorno.ERRO_EXCECAO);
		}	
	}
	
	
	@GET
	@Path("/consultaNomeModelo")
	@Produces({ MediaType.APPLICATION_JSON})
	public RetornoListaVO<DocumentoAutoComplete> consultaNomeModelo(@QueryParam("desc") String desc) {
		try {

			List<DocumentoAutoComplete> documentos = documentoService.consultarAutocomplete(desc);
            return new RetornoListaVO<DocumentoAutoComplete>(documentos);
		}catch (Exception e) {
			return new RetornoListaVO<DocumentoAutoComplete>(-1L, e.getMessage(), Retorno.ERRO_EXCECAO);
		} 
	}	
	
	
	@POST
	@Path("/salvarDocumento")
	@Consumes({MediaType.APPLICATION_JSON})
	public Retorno salvarDocumento(Documento documento) {		
		
		try {
			return documentoService.salvar(documento, getDadosUsuarioLogado().getCodigoUsuario());
		} catch (Exception e) {
			return new Retorno(-1L, e.getMessage(), Retorno.ERRO_EXCECAO);
		}
	}
	
	
	@POST
	@Path("/removeModeloDocumento")
	@Consumes({MediaType.APPLICATION_JSON})
	public Retorno removeModeloDocumento(Documento documento) {		
		
		try {
			
			return documentoService.excluir(documento, getDadosUsuarioLogado().getCodigoUsuario());
		} catch (BusinessException e) {
			return new Retorno(-1L, e.getMessage(), Retorno.ERRO_NEGOCIAL, e.getMessage());
		} catch (Exception e) {
			return new Retorno(-1L, e.getMessage(), Retorno.ERRO_EXCECAO);
		}
	}
	
	@POST
	@Path("/visualizacaoImpressao")
	@Consumes({MediaType.APPLICATION_JSON})
	public Retorno visualizacaoImpressao(Documento documento) {	
		Documento retorno = new Documento();
		try {
			String html = documentoService.imprimeDocumento(documento, documento.getDadosSicli());
			retorno.setTemplateHtml(new StringBuilder().append(html));
	
			return retorno;
		}
		catch (Exception e) {
			return new Documento(-1L, e.getMessage(), Retorno.ERRO_EXCECAO);
		}
	}
	
	@POST
	@Path("/consultaRetorno")
	@Consumes({MediaType.APPLICATION_JSON})
	public RetornoListaVO<Documento> consultaRetorno(Documento documento) {		
			
		try {
			return new RetornoListaVO<Documento>(documentoService.listaDocumentoRetorno(documento));
		} catch (Exception e) {
			return new RetornoListaVO<Documento>(-1L, e.getMessage(), Retorno.ERRO_EXCECAO);
		}	
	}
}