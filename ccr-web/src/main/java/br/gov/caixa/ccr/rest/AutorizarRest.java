package br.gov.caixa.ccr.rest;

import java.util.ArrayList;
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

import br.gov.caixa.arqrefcore.excecao.BusinessException;
import br.gov.caixa.arqrefcore.excecao.SystemException;
import br.gov.caixa.arqrefservices.dominio.Empregado;
import br.gov.caixa.arqrefservices.negocio.EmpregadoService;
import br.gov.caixa.ccr.dominio.AutorizacaoContrato;
import br.gov.caixa.ccr.dominio.AutorizarContrato;
import br.gov.caixa.ccr.dominio.Retorno;
import br.gov.caixa.ccr.enums.EnumTipoAutorizacao;
import br.gov.caixa.ccr.negocio.IAutorizarContratoBean;

@RequestScoped
@Path("/autorizar")
public class AutorizarRest extends AbstractSecurityRest {
	
	@Inject
	private IAutorizarContratoBean autorizarService;
	@Inject
	private EmpregadoService empregadoService;
	
	String[] gestor = {"FEC0109"};
	
	String[] gerencial = {"FEC0102"};
	
	
	@POST
	@Path("/autorizar")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<AutorizacaoContrato> solicitarConsulta(AutorizarContrato entrada) throws SystemException, BusinessException{
		Empregado  empregado = empregadoService.getEmpregado(getDadosUsuarioLogado().getCodigoUsuario());

		EnumTipoAutorizacao tipoUsuario = null;
		//TODO Arrumar perfil gestor
		if(verificaGerencial() || verificaGestor()){
			tipoUsuario = EnumTipoAutorizacao.GERENCIAL;
		}
		Retorno retorno= new Retorno();
		List<AutorizacaoContrato> autorizacaoContrato = new ArrayList<AutorizacaoContrato>();
		try{
			if(tipoUsuario.equals(EnumTipoAutorizacao.GERENCIAL)) {
				autorizacaoContrato=autorizarService.autorizar(entrada, tipoUsuario, empregado) ;
			} else {
				retorno.setAll(1L, "MA004", Retorno.ERRO_EXCECAO, "");
			}
		}catch (Exception e) {
			retorno.setAll(1L, e.getMessage(), Retorno.ERRO_EXCECAO, "");
		}
		return autorizacaoContrato;
	}
	
	@GET
	@Path("/buscaAutorizacaoPorSituacao")
	@Produces(MediaType.APPLICATION_JSON)
	public Object buscaAutorizacaoPorSituacao(@QueryParam("situacao") String situacao, @QueryParam("nuContrato") Long nuContrato, @QueryParam("nuSituacaoContrato") Long nuSituacaoContrato) throws SystemException, BusinessException {
		try {
			return autorizarService.buscaAutorizacaoPorSituacao(situacao, nuContrato, nuSituacaoContrato);
		} catch (Exception e) {
			return new Retorno(-1L, e.getMessage(), Retorno.ERRO_EXCECAO);
		}
	}
	
	@GET
	@Path("/buscaAutorizacaoPorContrato/{idContrato}")
	@Produces(MediaType.APPLICATION_JSON)
	public Object buscaAutorizacaoPorContrato(@PathParam("idContrato") Long idContrato) throws SystemException, BusinessException
	{   		  
		try {
			return autorizarService.buscaAutorizacaoPorContrato(idContrato);
		} catch (Exception e) {
			return new Retorno(-1L, e.getMessage(), Retorno.ERRO_EXCECAO);
		}
		
	}
	
	private boolean verificaGerencial(){
		for(String role: gerencial){
			if(temPerfilUsuario(role)){
				return true;
			}
		}
		return false;
		
	}
	
	private boolean verificaGestor(){
		for(String role: gestor){
			if(temPerfilUsuario(role)){
				return true;
			}
		}
		return false;
		
	}
		
	
}