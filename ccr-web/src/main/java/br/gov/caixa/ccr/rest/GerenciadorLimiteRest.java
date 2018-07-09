package br.gov.caixa.ccr.rest;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.gov.caixa.arqrefcore.excecao.BusinessException;
import br.gov.caixa.arqrefcore.excecao.SystemException;
import br.gov.caixa.arqrefservices.dominio.limite.GerenciadorLimitesRequest;
import br.gov.caixa.arqrefservices.dominio.limite.GerenciadorLimitesResponse;
import br.gov.caixa.arqrefservices.negocio.IGerenciadorLimite;

@RequestScoped
@Path("/gerenciadorLimite")
public class GerenciadorLimiteRest extends AbstractSecurityRest {
	
	@Inject
	private IGerenciadorLimite gerenciadorLimite;	
	
	
	
	@POST
	@Path("/manter")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public GerenciadorLimitesResponse manter(GerenciadorLimitesRequest request) throws SystemException, BusinessException{
		
		return gerenciadorLimite.manterLimites(request);
		
	}


	
	





}
