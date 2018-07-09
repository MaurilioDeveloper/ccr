package br.gov.caixa.ccr.rest;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.gov.caixa.arqrefcore.excecao.BusinessException;
import br.gov.caixa.arqrefcore.excecao.SystemException;
import br.gov.caixa.arqrefcore.jms.Correlation;
import br.gov.caixa.ccr.dominio.Empregado;
import br.gov.caixa.ccr.dominio.Mensageria;
import br.gov.caixa.ccr.dominio.Retorno;
import br.gov.caixa.ccr.dominio.Simular;
import br.gov.caixa.ccr.dominio.barramento.simulacao.DadosResultado;
import br.gov.caixa.ccr.negocio.ISimulacaoBean;
import br.gov.caixa.ccr.util.MockCreator;

@Path("/simulacao")
public class SimulacaoRest extends AbstractSecurityRest {
	
	@Inject
	private ISimulacaoBean simulacaoService;	
	
	
	@POST
	@Path("/simular")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public DadosResultado simular(Simular entrada) throws Exception {
		DadosResultado camposRetornados = null;

		Empregado empregado = new Empregado();
		
		String usuario = getDadosUsuarioLogado().getCodigoUsuario();
		empregado.setNumeroMatricula(Integer.parseInt(usuario.substring(1)));
		try{
			camposRetornados = simulacaoService.simulaInterno(entrada, empregado);
			camposRetornados.setMensagemRetorno(Retorno.SUCESSO);
		}catch (BusinessException e) {
			return new DadosResultado(-1L, e.getMessage(), Retorno.ERRO_NEGOCIAL, e.getMessage());
		}
		
		return camposRetornados;
	}
	
	

}
