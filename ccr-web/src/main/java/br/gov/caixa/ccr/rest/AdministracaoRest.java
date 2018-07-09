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

import br.gov.caixa.ccr.dominio.Combo;
import br.gov.caixa.ccr.dominio.Log;
import br.gov.caixa.ccr.dominio.Retorno;
import br.gov.caixa.ccr.dominio.TaxaJuro;
import br.gov.caixa.ccr.negocio.IAuditoriaBean;
import br.gov.caixa.ccr.negocio.IComboBean;
import br.gov.caixa.ccr.negocio.ILogBean;
import br.gov.caixa.ccr.negocio.ITaxaJuroBean;
import br.gov.caixa.ccr.util.RetornoListaVO;

@RequestScoped
@Path("/Administracao")
public class AdministracaoRest extends AbstractSecurityRest {
	
	@Inject
	private ITaxaJuroBean taxaJuroSevice;
	
	@Inject
	private IComboBean comboService;
	
//	@Inject
//	private IParametroOperacaoBean parametroOperacaoService;
		
	@Inject 
	private ILogBean logService;
	
	@Inject
	private IAuditoriaBean auditoriaService;
	
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
	
	
	
	@POST
	@Path("/taxaJuro/salvar")
	@Consumes({MediaType.APPLICATION_JSON})
	public Retorno salvarTaxaJuro(TaxaJuro taxaJuro, @QueryParam("inicioVigenciaChave") String inicioVigenciaChave) {
		try {
			return taxaJuroSevice.salvar(taxaJuro, inicioVigenciaChave, getDadosUsuarioLogado().getCodigoUsuario());
		} catch (Exception e) {
			return new Retorno(-1L, e.getMessage(), Retorno.ERRO_EXCECAO);
		}
	}
	
	@POST
	@Path("/taxaJuro/excluir")
	public Retorno excluirTaxaJuro(TaxaJuro taxaJuro) {
		try {
			return taxaJuroSevice.excluir(taxaJuro);
		} catch (Exception e) {
			return new Retorno(-1L, e.getMessage(), Retorno.ERRO_EXCECAO);
		}
	}
	
	/********************************************************************************
	 * ************************* REST PARAMETRO OPERACAO ****************************
	 * ******************************************************************************/
	// LEGADO
//	@GET
//	@Path("/parametroOperacao/consultar")
//	@Produces({ MediaType.APPLICATION_JSON})
//	public ParametroOperacao carregarParametrosOperacao() {
//		try {
//			return parametroOperacaoService.consultar();
//		} catch (Exception e) {
//			return new ParametroOperacao(-1L, e.getMessage(), Retorno.ERRO_EXCECAO);
//		}
//	}
	
	/********************************************************************************
	 * ********************************* REST LOG ***********************************
	 * ******************************************************************************/
	@GET
	@Path("/log/lista")
	@Produces({ MediaType.APPLICATION_JSON})
	public RetornoListaVO<Log> listaLOG(@QueryParam("dataInicio") String dataInicio, @QueryParam("dataFim") String dataFim, @QueryParam("transacao") Integer transacao) {
		try {
			return new RetornoListaVO<Log>(logService.listar(dataInicio, dataFim, transacao));
		} catch (Exception e) {
			return new RetornoListaVO<Log>(-1L, e.getMessage(), Retorno.ERRO_EXCECAO);
		}
	}
	
	
}