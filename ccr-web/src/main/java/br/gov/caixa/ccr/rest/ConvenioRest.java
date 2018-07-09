package br.gov.caixa.ccr.rest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

import br.gov.caixa.arqrefcore.excecao.BusinessException;
import br.gov.caixa.arqrefservices.dominio.sicli.Renda;
import br.gov.caixa.ccr.dominio.Auditoria;
import br.gov.caixa.ccr.dominio.Combo;
import br.gov.caixa.ccr.dominio.Convenio;
import br.gov.caixa.ccr.dominio.ConvenioCanal;
import br.gov.caixa.ccr.dominio.Log;
import br.gov.caixa.ccr.dominio.Retorno;
import br.gov.caixa.ccr.enums.EnumCanalAtendimento;
import br.gov.caixa.ccr.enums.EnumTipoAuditoria;
import br.gov.caixa.ccr.negocio.IComboBean;
import br.gov.caixa.ccr.negocio.IConvenioBean;
import br.gov.caixa.ccr.negocio.ILogBean;
import br.gov.caixa.ccr.util.RetornoListaVO;

@RequestScoped
@Path("/convenio")
public class ConvenioRest extends AbstractSecurityRest {
	
	@Inject
	private IConvenioBean convenioService;
	
	@Inject
	private IComboBean comboService;
	
//	@Inject
//	private IParametroOperacaoBean parametroOperacaoService;
		
	@Inject 
	private ILogBean logService;
	
	
	//FEC0109 - ACESSO GESTORES CONSIGNADO - GERAL
	private static String PERFIL_GESTAO_FEC0109 = "FEC0109";
	
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
	
	/********************************************************************************
	 * ***************************** REST CONVENIO **********************************
	 * ******************************************************************************/
	@GET
	@Path("/listar")
	@Produces({ MediaType.APPLICATION_JSON})
	public RetornoListaVO<Convenio> listarConvenio(
			@QueryParam("tipoConsulta") String tipoConsulta, 
			@QueryParam("id") int id, 
			@QueryParam("cnpj") long cnpj, 
			@QueryParam("nome") String nome, 
			@QueryParam("situacao") int situacao, 
			@QueryParam("sr") int sr, 
			@QueryParam("agencia") int agencia) {
		
		try {
			return new RetornoListaVO<Convenio>(convenioService.lista(id, cnpj, nome, situacao, sr, agencia, null));
		} catch (BusinessException e) {
			return new RetornoListaVO<Convenio>(-1L, e.getMessage(), Retorno.ERRO_NEGOCIAL, e.getMessage());
		}catch (Exception e) {
			return new RetornoListaVO<Convenio>(-1L, e.getMessage(), Retorno.ERRO_EXCECAO);
		}
	}
	
	/********************************************************************************
	 * ********************* REST CONVENIO CANAL AUDITORIA **************************
	 * ******************************************************************************/
	
	/**
	 * Metodo que retorna dados para futuramente gravar
	 * uma Auditoria, caso o usuário decida salvar todo
	 * o Convênio.
	 *  
	 * @param nuCanal
	 * @param nuConvenio
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws ParseException
	 */
	@GET
	@Path("/excluir")
	@Produces({ MediaType.APPLICATION_JSON})
	public List<Auditoria> excluirCanal(@QueryParam("idCanal") Long nuCanal, @QueryParam("nuConvenio") Long nuConvenio) {
		return convenioService.funcoesCanalAuditoria(nuCanal, nuConvenio, getDadosUsuarioLogado().getCodigoUsuario(), EnumTipoAuditoria.EXCLUSAO, null);
	}
	
	/**
	 * Metodo que retorna dados para futuramente gravar
	 * uma Auditoria, caso o usuário decida salvar todo
	 * o Convênio. 
	 * 
	 * @param nuCanal
	 * @param nuConvenio
	 * @return
	 */
	@POST
	@Path("/alterarCanal")
	@Produces({ MediaType.APPLICATION_JSON})
	public List<Auditoria> alterarCanal(ConvenioCanal convenioCanal) {
		return convenioService.funcoesCanalAuditoria(convenioCanal.getCanal().getId(), convenioCanal.getIdConvenio(), getDadosUsuarioLogado().getCodigoUsuario(), EnumTipoAuditoria.ALTERACAO, convenioCanal);
	}
	
	/**
	 * Metodo que retorna dados para futuramente gravar
	 * uma Auditoria, caso o usuário decida salvar todo
	 * o Convênio. 
	 * 
	 * @param nuCanal
	 * @param nuConvenio
	 * @return
	 */
	@POST
	@Path("/salvarCanal")
	@Produces({ MediaType.APPLICATION_JSON})
	public List<Auditoria> salvarCanal(ConvenioCanal convenioCanal) {
		return convenioService.funcoesCanalAuditoria(0L, 0L, getDadosUsuarioLogado().getCodigoUsuario(), EnumTipoAuditoria.INCLUSAO, convenioCanal);
	}
	
	/**
	 * Metodo responsável por persistir definitivamente uma auditoria
	 * 
	 * @param auditorias
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws ParseException
	 */
	@POST
	@Path("/auditoriaCanalPersiste")
	@Produces({ MediaType.APPLICATION_JSON})
	public Retorno auditoriaCanaisPersiste(List<Auditoria> auditorias) throws IllegalArgumentException, IllegalAccessException, ParseException {
		try {
			return convenioService.auditoriaCanaisPersiste(auditorias);
		} catch (Exception e) {
			return new Retorno(-1L, e.getMessage(), Retorno.ERRO_NEGOCIAL, e.getMessage());
		}
	}
	
	/********************************************************************************
	 * ******************** FIM REST CONVENIO CANAL AUDITORIA ***********************
	 * ******************************************************************************/
	
	@POST
	@Path("/listarConvenioPorFontePagadora")
	@Produces({ MediaType.APPLICATION_JSON})
	public RetornoListaVO<Convenio> listarConvenioPorFontePagadora(List<Renda> listaRenda) {
		
		try {
			List<Convenio> listaConvenios = new ArrayList<Convenio>();
			
			for (Renda renda : listaRenda) {
				if(!renda.getCpfCnpjFontePagadora().isEmpty() && (renda.getTipoFontePagadora().equals("1") || renda.getTipoFontePagadora().equals("2") || renda.getTipoFontePagadora().equals("3"))){
				Long cnpj = Long.parseLong(renda.getCpfCnpjFontePagadora());
				/* Recuperar lista de canais disponíveis para contratação:
				 * A principio só deve filtrar por Canal: AGENCIA, futuramente
				 * deve ser incluido outros canais. */
				
				List<Long> idcanais = new ArrayList<Long>();	
				idcanais.add((long)EnumCanalAtendimento.AGENCIA.getCodigo());
				List<Convenio> convenioResult=convenioService.lista(0, cnpj, "", 0, 0, 0, idcanais);	
				Boolean duplicado;
				
				if(convenioResult != null && !convenioResult.isEmpty()){
					for(Convenio convenio: convenioResult){
						duplicado = false;
						for(Convenio convenio2 : listaConvenios){
							if(convenio.getId().equals(convenio2.getId())){
								duplicado = true;
							}
						}
						if(!duplicado){
							convenio.setCnpjFontePagadora(renda.getCpfCnpjFontePagadora());
							listaConvenios.add(convenio);
						}
					}					
				}
			}
		}
			
			return new RetornoListaVO<Convenio>(listaConvenios);
		} catch (BusinessException e) {
			return new RetornoListaVO<Convenio>(-1L, e.getMessage(), Retorno.ERRO_NEGOCIAL, e.getMessage());
		}catch (Exception e) {
			return new RetornoListaVO<Convenio>(-1L, e.getMessage(), Retorno.ERRO_EXCECAO);
		}
	}

	/********************************************************************************
	 * ***************************** REST CONVENIO **********************************
	 * ******************************************************************************/
	@GET
	@Path("/consultarConveniosParaSimulacao")
	@Produces({ MediaType.APPLICATION_JSON})
	public RetornoListaVO<Convenio> consultarConveniosParaSimulacao(
			@QueryParam("cnpj") long cnpj, 
			@QueryParam("coConvenio") Integer id, 
			@QueryParam("noConvenio") String nome, 
			@QueryParam("canais") List<Long> canais) {
		
		try {
			return new RetornoListaVO<Convenio>(convenioService.lista(id, cnpj, nome, 0, 0, 0, canais));

		} catch (BusinessException e) {
			return new RetornoListaVO<Convenio>(-1L, e.getMessage(), Retorno.ERRO_NEGOCIAL, e.getMessage());
		}catch (Exception e) {
			return new RetornoListaVO<Convenio>(-1L, e.getMessage(), Retorno.ERRO_EXCECAO);
		}
	}

	@GET
	@Path("/consultar")
	@Produces({ MediaType.APPLICATION_JSON})
	public Convenio consultarConvenio(@QueryParam("id") Long id) {
		try {
			return convenioService.consultar(id, getDadosUsuarioLogado().getCodigoUsuario());
		} catch (Exception e) {
			return new Convenio(-1L, e.getMessage(), Retorno.ERRO_EXCECAO);
		}
	}
	
	@GET
	@Path("/consultarSR/{id}")
	@Produces({ MediaType.APPLICATION_JSON})
	public Convenio consultarSR(@PathParam("id") Long id) {
		try {
			return (Convenio)convenioService.consultarSR(id);
		} catch (Exception e) {
			return new Convenio(-1L, e.getMessage(), Retorno.ERRO_EXCECAO);
		}
	}
		
	@POST
	@Path("/salvar")
	@Consumes({MediaType.APPLICATION_JSON})
	public Retorno salvarConvenio(Convenio convenio) {		
		
		try {
			return convenioService.salvar(convenio, getDadosUsuarioLogado().getCodigoUsuario(), temPerfilUsuario(PERFIL_GESTAO_FEC0109));
		} catch (BusinessException e) {
			return new Retorno(-1L, e.getMessage(), Retorno.ERRO_NEGOCIAL, e.getMessage());
		} catch (Exception e) {
			return new Retorno(-1L, e.getMessage(), Retorno.ERRO_EXCECAO);
		}
	}
	
	
	 
	@POST
	@Path("/perfil")
	@Consumes({MediaType.APPLICATION_JSON})
	public Retorno alterarConvenio() {		
		try {
			//getDadosUsuarioLogado().getCodigoUsuario()
			boolean perfil = temPerfilUsuario(PERFIL_GESTAO_FEC0109);
			Retorno retorno =  new Retorno(0L, ""+perfil, Retorno.SUCESSO, "MA002");
			return retorno;
			
			
		}catch (Exception e) {
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
	
	public static void main(String args[]) {
		
		
		Calendar c = Calendar.getInstance();
		c.set(Calendar.MILLISECOND, 0);
		c.set(Calendar.HOUR, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		
		Date date = new Date();
		Calendar dateNow = Calendar.getInstance();
		dateNow.setTime(date);
		dateNow.set(Calendar.MILLISECOND, 0);
		dateNow.set(Calendar.HOUR, 0);
		dateNow.set(Calendar.MINUTE, 0);
		dateNow.set(Calendar.SECOND, 0);
		if(dateNow.getTime().compareTo(c.getTime()) == 0 || dateNow.getTime().compareTo(c.getTime()) > 0) {
			System.out.println("Datas  são iguais");
		}
	}
	
}
