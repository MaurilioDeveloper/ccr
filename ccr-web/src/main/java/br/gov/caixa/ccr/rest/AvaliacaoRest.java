package br.gov.caixa.ccr.rest;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import org.apache.commons.lang3.StringUtils;

import br.gov.caixa.arqrefcore.excecao.BusinessException;
import br.gov.caixa.arqrefcore.excecao.SystemException;
import br.gov.caixa.arqrefcore.jms.Correlation;
import br.gov.caixa.arqrefservices.dominio.Empregado;
import br.gov.caixa.arqrefservices.dominio.barramento.enumerador.ESiglas;
import br.gov.caixa.arqrefservices.dominio.siric.AvaliacaoValiada;
import br.gov.caixa.arqrefservices.dominio.siric.DesbloquearAvaliacaoRiscoRequest;
import br.gov.caixa.arqrefservices.dominio.siric.DesbloquearAvaliacaoRiscoResponse;
import br.gov.caixa.arqrefservices.dominio.siric.solicitacao.Aprovacao;
import br.gov.caixa.arqrefservices.dominio.siric.transicao.DadosSIRICTO;
import br.gov.caixa.arqrefservices.dominio.siric.transicao.MensagemRetornoSIRICTO;
import br.gov.caixa.arqrefservices.negocio.AvaliacaoRiscoCredito;
import br.gov.caixa.arqrefservices.negocio.siric.AvaliacaoRiscoCreditoSiric;
import br.gov.caixa.arqrefservices.negocio.siric.DesbloqueioAlcadaSiric;
import br.gov.caixa.ccr.dominio.AvaliacaoRiscoRequest;
import br.gov.caixa.ccr.dominio.CancelaAvaliacaoRequest;
import br.gov.caixa.ccr.dominio.ConsultaAvaliacaoRequest;
import br.gov.caixa.ccr.dominio.ConsultaAvaliacaoResp;
import br.gov.caixa.ccr.dominio.DesbloqueioTomadorRequest;
import br.gov.caixa.ccr.dominio.SolicitaAvaliacaoOperacaoRequest;
import br.gov.caixa.ccr.dominio.SolicitaAvaliacaoRequest;
import br.gov.caixa.ccr.dominio.Usuario;
import br.gov.caixa.ccr.negocio.IAvaliacaoBean;

@Path("/avaliacao")
public class AvaliacaoRest extends AbstractSecurityRest {
	
	@Inject
	private IAvaliacaoBean avaliacaoBeanService;	
	
	@Inject
	private AvaliacaoRiscoCreditoSiric avaliacaoService;
	
	@Inject
	private AvaliacaoRiscoCredito avRiscoService;
	
	@Context
	private SecurityContext securityContext;
	
	@Inject
	private DesbloqueioAlcadaSiric desbloqueioSiric;
	
	@POST
	@Path("/salvarAvaliacaoRisco")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ConsultaAvaliacaoResp salvarAvaliacaoRisco(AvaliacaoRiscoRequest request) throws SystemException, BusinessException{	
		ConsultaAvaliacaoResp resp = new ConsultaAvaliacaoResp();
		try {
			resp.setIdAvaliacao(avaliacaoBeanService.salvarAvaliacao(request.getAvaliacao(), getDadosUsuarioLogado().getCodigoUsuario()));
		} catch (Exception e) {}
	
		return resp;
	}	

	@POST
	@Path("/consultarListaAvaliacaoRisco")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Correlation consultarListaAvaliacaoRisco(ConsultaAvaliacaoRequest consultaAvaliacaoRequest) throws BusinessException, SystemException {
		log.info("AvaliacaoRest - INICIO consultarListaAvaliacaoRisco");
		
		br.gov.caixa.ccr.dominio.Empregado empregadoContexto= getEmpregado();
		Usuario usuarioLogado= getDadosUsuarioLogado();
		Empregado empregado = new Empregado();
		String codigoUsuario = usuarioLogado.getCodigoUsuario();
		if(StringUtils.isEmpty(codigoUsuario)){
			empregado.setMatriculaUsuario( codigoUsuario);
			empregado.setNumeroMatricula( Integer.parseInt(StringUtils.substring(codigoUsuario, 1) ));
		}else{
			empregado.setMatriculaUsuario("");
		}
		
		if(empregadoContexto.getNumeroUnidade()!= null){
			empregado.setNumeroUnidade(empregadoContexto.getNumeroUnidade());
		}else{
			empregado.setNumeroUnidade(0);
		}
		
		String cpf = consultaAvaliacaoRequest.getDadosSicli().getCpf().getDocumento().getNumero()
				+ consultaAvaliacaoRequest.getDadosSicli().getCpf().getDocumento().getDigitoVerificador();
		DadosSIRICTO dadosSIRICTO = new DadosSIRICTO();
		dadosSIRICTO.setEmpregado(empregado);
		dadosSIRICTO.setCpf(StringUtils.leftPad(cpf, 11, "0"));
		
 		Correlation correlation = avRiscoService.consultaListaAvaliacaoRisco(dadosSIRICTO);
		log.fine(correlation.toString());
		log.info("AvaliacaoRiscoRest - FIM consultaListaAvaliacaoRisco");
		return correlation;	

	}
	
	
	@POST
	@Path("/recebeListaAvaliacaoRisco")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<AvaliacaoValiada> recebeListaAvaliacaoRisco(Correlation correlation) throws BusinessException, SystemException {
		log.info("AvaliacaoRest - INICIO recebeListaAvaliacaoRisco");
		log.fine(correlation.toString());
		if (!Boolean.parseBoolean(System.getProperty("simulaSIRIC", "false"))) {
			return mockRetornoListaAvaliacao();
		}
	
		List<AvaliacaoValiada> listAvValida = avRiscoService.recebeListaConsultaAvaliacaoRisco(correlation);
		
		if(listAvValida != null){
			log.fine(listAvValida.toString());
		}else{
			listAvValida = new ArrayList<AvaliacaoValiada>();
		}
		
		if(listAvValida != null){
			for(AvaliacaoValiada item : listAvValida){
				if(item.getSituacao() != null) {
					if(item.getSituacao().equals("1")){
						item.setSituacao("1 - Aceito");
					}else if(item.getSituacao().equals("5")){
						item.setSituacao("5 - Sem Requisito");
					}
				}
			}			
		}
		log.info("AvaliacaoRiscoRest - FIM recebeListaConsultaAvaliacaoRisco");
		return listAvValida;

	}
	
	private List<AvaliacaoValiada> mockRetornoListaAvaliacao(){
        List<AvaliacaoValiada> listAvValida = new ArrayList<AvaliacaoValiada>();
		
		AvaliacaoValiada avaliacaoValiada = new AvaliacaoValiada();
		avaliacaoValiada.setCodigoAvaliacao("11111");
		avaliacaoValiada.setAcaoVenda("a");
		avaliacaoValiada.setCnpj("1522132");
		avaliacaoValiada.setDataFimValidade("28/10/2018");
		avaliacaoValiada.setDataInicioValidade("02/01/2018");
		avaliacaoValiada.setFlagBloquadaAlcada("1");
		avaliacaoValiada.setModalidade("operacao");
		avaliacaoValiada.setPrazo("80");
		avaliacaoValiada.setRating("1");
		avaliacaoValiada.setProduto("200000110");
		avaliacaoValiada.setSituacao("5");
		avaliacaoValiada.setBloqueioTomador("1");
		avaliacaoValiada.setFlagBloquadaAlcada("1");
		avaliacaoValiada.setValorLimiteCPM("999999999");
		avaliacaoValiada.setValorFinanciamento("999999999");
		avaliacaoValiada.setValorLimiteGlobal("10");
		
		AvaliacaoValiada avaliacaoValiada2 = new AvaliacaoValiada();
		avaliacaoValiada2.setCodigoAvaliacao("748860264");
		avaliacaoValiada2.setAcaoVenda("a");
		avaliacaoValiada2.setCnpj("1522132");
		avaliacaoValiada2.setDataFimValidade("14/06/2018");
		avaliacaoValiada2.setDataInicioValidade("15/05/2018");
		avaliacaoValiada2.setFlagBloquadaAlcada("1");
		avaliacaoValiada2.setModalidade("cliente");
		avaliacaoValiada2.setProduto("110");
		avaliacaoValiada2.setPrazo("80");
		avaliacaoValiada2.setRating("C05");
		avaliacaoValiada2.setValorLimiteGlobal("10");
		avaliacaoValiada2.setSituacao("1");
	    avaliacaoValiada2.setBloqueioTomador("1");
		avaliacaoValiada2.setFlagBloquadaAlcada("1");
		avaliacaoValiada2.setValorLimiteCPM("1500");
		avaliacaoValiada2.setValorFinanciamento("1500");
		
		
		listAvValida.add(avaliacaoValiada);
		listAvValida.add(avaliacaoValiada2);
		
		return listAvValida;
	}
	
	@POST
	@Path("/solicitaAvaliacaoRiscoClienteSiric")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Correlation solicitaAvaliacaoRiscoClienteSiric(SolicitaAvaliacaoRequest solicitaAvaliacaoRequest) throws BusinessException, SystemException {
		br.gov.caixa.ccr.dominio.Empregado empregadoContexto= getEmpregado();
		Usuario usuarioLogado= getDadosUsuarioLogado();
		Empregado empregado = new Empregado();
		if(StringUtils.isEmpty(usuarioLogado.getCodigoUsuario())){
			empregado.setMatriculaUsuario( usuarioLogado.getCodigoUsuario());
		}else{
			empregado.setMatriculaUsuario("");
		}
		
		if(empregadoContexto.getNumeroUnidade()!= null){
			empregado.setNumeroUnidade(empregadoContexto.getNumeroUnidade());
		}else{
			empregado.setNumeroUnidade(0);
		}
		
		Correlation correlation = avaliacaoService.solicitaAvaliacaoRiscoCliente(solicitaAvaliacaoRequest.getDadosSicli(),empregado, null, ESiglas.SICCR.toString());
		return correlation;

	}
	
	@POST
	@Path("/solicitaAvaliacaoRiscoCreditoSiric")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Correlation solicitaAvaliacaoRiscoCreditoSiric(SolicitaAvaliacaoOperacaoRequest solicitaAvaliacaoRequest) throws BusinessException, SystemException {
		br.gov.caixa.ccr.dominio.Empregado empregadoContexto= getEmpregado();
		Usuario usuarioLogado= getDadosUsuarioLogado();
		Empregado empregado = new Empregado();
		if(StringUtils.isEmpty(usuarioLogado.getCodigoUsuario())){
			empregado.setMatriculaUsuario( usuarioLogado.getCodigoUsuario());
		}else{
			empregado.setMatriculaUsuario("");
		}
		
		if(empregadoContexto.getNumeroUnidade()!= null){
			empregado.setNumeroUnidade(empregadoContexto.getNumeroUnidade());
		}else{
			empregado.setNumeroUnidade(0);
		}
		
		Correlation correlation = avaliacaoService.solicitaAvaliacaoRiscoCredito(solicitaAvaliacaoRequest.getDadosOperacao(), solicitaAvaliacaoRequest.getDadosSicli(), empregado, solicitaAvaliacaoRequest.getRenda());
		return correlation;

	}
	
	@POST
	@Path("/recebeAvaliacaoRiscoClienteCredito")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public br.gov.caixa.arqrefservices.dominio.siric.solicitacao.Avaliacao recebeAvaliacaoRiscoClienteCredito(Correlation correlation) throws BusinessException, SystemException {
		log.info("INICIO recebeAvaliacaoRiscoClienteCredito");
		if (Boolean.parseBoolean(System.getProperty("simulaSIRIC", "false"))) {
			Aprovacao aprovacao = new Aprovacao();
			aprovacao.setAcaoVenda("sadva");
			aprovacao.setCodigoAvaliacao("111");
			aprovacao.setMotivo("1");
			aprovacao.setDataInicioVigencia("19/07/1987");
			aprovacao.setDataFimVigencia("20/07/1987");
			aprovacao.setPrazo("22");
			aprovacao.setModalidade("Teste do Luzi");
			aprovacao.setProduto("Produto do Luzi");
			aprovacao.setProposta("1215");
			aprovacao.setRating("Rating");
			aprovacao.setValorFinanciamento("25");
			aprovacao.setValorLimiteGlobal("26");
			aprovacao.setFlagBloqueioAlcada("");
			
			br.gov.caixa.arqrefservices.dominio.siric.solicitacao.Avaliacao avaliacao =new br.gov.caixa.arqrefservices.dominio.siric.solicitacao.Avaliacao();
			avaliacao.setAprovacao(aprovacao);
			avaliacao.setCodigoRetornoMensagem("1212121");
			
			return avaliacao;
		}

		//Consulta PROTOCOLO
		br.gov.caixa.arqrefservices.dominio.siric.solicitacao.Avaliacao avaliacao = avaliacaoService.retornoDadosAvaliacaoRisco(correlation.getId());		
		log.info("Retorno avaliacaoProtocolo" + avaliacao );
		
		br.gov.caixa.arqrefservices.dominio.siric.Avaliacao avaliacaoCliente = new br.gov.caixa.arqrefservices.dominio.siric.Avaliacao();
		if(avaliacao != null) {
			//Retorno da avaliação cliente
			avaliacaoCliente = avRiscoService.recebeAvaliacaoRisco(correlation, ESiglas.SICCR.toString());
			log.info("Retorno avaliacao" + avaliacaoCliente);
		}
		
		log.info("FIM recebeAvaliacaoRiscoClienteCredito");
		return avaliacao;

	}
	
	@POST
	@Path("/verificarUsuarioGerencial")
	@Consumes({MediaType.APPLICATION_JSON})
	public Boolean verificarUsuarioGerencial() {		
		try {
			log.info("AvaliacaoRest - INICIO verificarUsuarioGerencial");				 
			 if(securityContext.isUserInRole("FEC0109")){ 
				 return Boolean.TRUE;
			
			 }else{
				 return Boolean.FALSE;
			 }
		} catch (Exception e) {
			log.info("AvaliacaoRest - ERRO: "+e);
		}
		return null;
	}
	
	@POST
	@Path("/desbloquearAvaliacaoRisco")
	@Consumes({MediaType.APPLICATION_JSON})
	public DesbloquearAvaliacaoRiscoResponse desbloquearAvaliacaoRisco(DesbloqueioTomadorRequest req) {	
		br.gov.caixa.arqrefservices.dominio.sicli.alteracao.Usuario usuario = new br.gov.caixa.arqrefservices.dominio.sicli.alteracao.Usuario();
		usuario.setCodigoUsuario(getDadosUsuarioLogado().getCodigoUsuario());
		usuario.setNumeroUnidadeLogado(Integer.parseInt(req.getAgenciaLogado()));
		br.gov.caixa.arqrefservices.dominio.siric.DesbloqueioAlcada desbloqueioAlcada= new br.gov.caixa.arqrefservices.dominio.siric.DesbloqueioAlcada();
		desbloqueioAlcada.setCodigoAvaliacao(req.getCodigoAvaliacao());
		desbloqueioAlcada.setCpf(req.getCpf());
		desbloqueioAlcada.setTipoDesbloqueio("TOMADOR");
		desbloqueioAlcada.setUsuario(getDadosUsuarioLogado().getCodigoUsuario());
		DesbloquearAvaliacaoRiscoRequest request= new DesbloquearAvaliacaoRiscoRequest(desbloqueioAlcada, usuario);
		DesbloquearAvaliacaoRiscoResponse response = new DesbloquearAvaliacaoRiscoResponse();
		log.info("AvaliacaoRiscoRest - FIM desbloquearAvaliacaoRisco");
		
		if (Boolean.parseBoolean(System.getProperty("simulaSIRIC", "false"))) {
			response.setCodigoRetorno("0"); //MOCK
		}else{
			response = desbloqueioSiric.desbloquear(request);
		}
		
	    return response;
	}
	
	
	@POST
	@Path("/solicitarCancelarAvaliacaoRiscoCredito")
	@Consumes({MediaType.APPLICATION_JSON})
	public Correlation solicitarCancelarAvaliacaoRiscoCredito(CancelaAvaliacaoRequest request) throws BusinessException, SystemException {
		
		Correlation correlation = avRiscoService.solicitarCancelarAvaliacaoRiscoCredito(request.getCpfCliente(), getDadosUsuarioLogado().getCodigoUsuario().substring(1), request.getCodigoAvaliacao(),request.getNumeroUnidade());

		log.fine(correlation.toString());
		log.info("AvaliacaoRiscoRest - FIM solicitatCancelarAvaliacaoRiscoCredito");
		
		return correlation;

	}
	
	/**
	 * Metodo que solicita o cancelamento da 
	 * avaliacao de risco de credito
	 * Metodo assincrono. 
	 * 
	 * Retorna o correlatioID da solicitancao
	 * 
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 */
	@POST
	@Path("/receberCancelarAvaliacaoRiscoCredito")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public MensagemRetornoSIRICTO receberCancelarAvaliacaoRiscoCredito(Correlation correlation)
			throws BusinessException, SystemException {
		log.info("AvaliacaoRiscoRest - INICIO receberCancelarAvaliacaoRiscoCredito");
		log.fine(correlation.toString());
		MensagemRetornoSIRICTO msgRetornoTO = new MensagemRetornoSIRICTO();
		if (Boolean.parseBoolean(System.getProperty("simulaSIRIC", "false"))) {
			// 0: Sucesso | 1: Erro
				msgRetornoTO.setCodigoRetorno("0");
				return msgRetornoTO;
		}
        
		msgRetornoTO = avRiscoService.receberCancelarAvaliacaoRiscoCredito(correlation);
		
		if(msgRetornoTO != null){
			log.fine(msgRetornoTO.toString());
		}
		
		log.info("AvaliacaoRiscoRest - FIM receberCancelarAvaliacaoRiscoCredito");
		
		return msgRetornoTO;

	}

}
