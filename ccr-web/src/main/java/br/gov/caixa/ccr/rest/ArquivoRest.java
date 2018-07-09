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
import br.gov.caixa.ccr.dominio.ArquivoContratoRequest;
import br.gov.caixa.ccr.dominio.Retorno;
import br.gov.caixa.ccr.negocio.IArquivoBean;

@RequestScoped
@Path("/arquivo")
public class ArquivoRest extends AbstractSecurityRest {
	
	@Inject
	private IArquivoBean arquivoService;
	
	/**
	 * 
	 * @param identificador
	 * @param dtInicioRemessa
	 * @param dtFimRemessa
	 * @param situacaoRetorno
	 * @param nuContrato
	 * @return  Listagem de Arquivos
	 * @throws SystemException
	 * @throws BusinessException
	 */
	@POST
	@Path("/consultar")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Object consultar(ArquivoContratoRequest arquivoContratoRequest
			) throws SystemException, BusinessException {
		try {
			return arquivoService.consultar(arquivoContratoRequest);
		} catch (Exception e) {
			return new Retorno(-1L, e.getMessage(), Retorno.ERRO_EXCECAO);
		}
	}
		
	
}