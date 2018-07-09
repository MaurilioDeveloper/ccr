package br.gov.caixa.ccr.rest;

import java.util.Date;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;

import br.gov.caixa.arqrefcore.excecao.BusinessException;
import br.gov.caixa.arqrefcore.excecao.SystemException;
import br.gov.caixa.arqrefservices.dominio.Empregado;
import br.gov.caixa.arqrefservices.negocio.EmpregadoService;
import br.gov.caixa.ccr.dominio.Contrato;
import br.gov.caixa.ccr.dominio.ContratoSituacaoRequest;
import br.gov.caixa.ccr.dominio.Documento;
import br.gov.caixa.ccr.dominio.Impressao;
import br.gov.caixa.ccr.dominio.Retorno;
import br.gov.caixa.ccr.dominio.Situacao;
import br.gov.caixa.ccr.dominio.SituacaoContrato;
import br.gov.caixa.ccr.dominio.apx.ContratoConsignadoTO;
import br.gov.caixa.ccr.enums.EnumSituacaoContrato;
import br.gov.caixa.ccr.enums.EnumTipoContrato;
import br.gov.caixa.ccr.negocio.IAutorizaBean;
import br.gov.caixa.ccr.negocio.IContratoBean;
import br.gov.caixa.ccr.negocio.IDocumentoBean;
import br.gov.caixa.ccr.negocio.IUnidadeBean;
import br.gov.caixa.ccr.util.RetornoListaVO;

@Path("/contratacao")
public class ContratacaoRest extends AbstractSecurityRest {

	@Inject
	private IContratoBean contratoService;

	@Inject
	private IAutorizaBean autorizacaoService;
	
	@Inject
	private IDocumentoBean documento;
	
	@Inject
	private IUnidadeBean unidadeService;
	
	@Inject
	private EmpregadoService empregadoService;	
	
	@POST
	@Path("/contratar")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Retorno contratar(Contrato contrato) throws SystemException, BusinessException {
		
		try {
			if(contrato.getSituacao()==null){
				Situacao situacao=new Situacao();
				situacao.setTipo(EnumTipoContrato.CONTRATO.getCodigo());
				situacao.setId(EnumSituacaoContrato.AGUARDANDO_AUTORIZACOES.getCodigo());
				situacao.setDescricao(EnumSituacaoContrato.AGUARDANDO_AUTORIZACOES.getDescricao());
				contrato.setSituacao(situacao);
			}
			contrato.setDtLiberacaoCredito(new Date());
			contrato.setDtSituacaoContrato(new Date());
			
			String usuario = getDadosUsuarioLogado().getCodigoUsuario();
			contrato.setCoUsuario(usuario);
			
			SituacaoContrato situacaoContrato = new SituacaoContrato();
			situacaoContrato.setData(new Date());
			situacaoContrato.setSituacao(contrato.getSituacao().getId().intValue());
			situacaoContrato.setTipoSituacao(contrato.getSituacao().getTipo().intValue());
			situacaoContrato.setUsuario(usuario);
			
			
			return contratoService.salvar(contrato, situacaoContrato);
		} catch (BusinessException e) {
			return new Retorno(-1L, e.getMessage(), Retorno.ERRO_NEGOCIAL, e.getMessage());
		} catch (Exception e) {
			return new Retorno(-1L, e.getMessage(), Retorno.ERRO_EXCECAO);
		}
	}

	@GET
	@Path("/consultarCpfPorContrato")
	@Produces(MediaType.APPLICATION_JSON)
	public Long consultarCpfPorContrato(@QueryParam("idContrato") final String idContrato)
			throws SystemException, BusinessException {
		return contratoService.consultarCpfPorContrato(Long.valueOf(idContrato));
	}

	@GET
	@Path("/validarPerfil")
	@Produces(MediaType.APPLICATION_JSON)
	public Boolean validarPerfil() {
		/**
		 * FEC0109: Perfil do tipo Gerencial
		 * FEC0100: Perfil do tipo Escriturario
		 */
		Boolean validacaoGestores =  temPerfilUsuario("FEC0109") || temPerfilUsuario("FEC0102");
//		Boolean validacaoEscriturario = temPerfilUsuario("FEC0112");
//		Boolean validacao = validacaoGestores && !validacaoEscriturario;

		return validacaoGestores;
	}
	
	@GET
	@Path("/validarPerfilAutorizar")
	@Produces(MediaType.APPLICATION_JSON)
	public Boolean validarPerfilAutorizar() {
		/**
		 * FEC0100
		 * Perfil do tipo Escriturario
		 */
		Boolean validacao = (temPerfilUsuario("FEC0100"));
		return validacao;
	}

	/**
	 * AUTORIZAR
	 **/
	@GET
	@Path("/listaContrato")
	@Produces(MediaType.APPLICATION_JSON)
	public RetornoListaVO<Contrato> consultaListaAutorizacao(@QueryParam("situacao") Long situacao,
			@QueryParam("cpf") Long cpf, @QueryParam("contrato") Long contrato,
			@QueryParam("cnpj") Long nuCnpjFontePagadora, @QueryParam("periodoINI") String periodoINI,
			@QueryParam("periodoFIM") String periodoFIM, @QueryParam("agencia") Long agencia, @QueryParam("sr") Long sr,
			@QueryParam("convenio") Long convenio) throws SystemException, BusinessException {

		try {
			String userLog = getDadosUsuarioLogado().getCodigoUsuario();
			RetornoListaVO<Contrato> retorno = new RetornoListaVO<Contrato>(contratoService.listar(cpf, contrato,
					situacao, periodoINI, periodoFIM, nuCnpjFontePagadora, agencia, convenio, sr, userLog));
			return retorno;
		} catch (Exception e) {
			return new RetornoListaVO<Contrato>(-1L, e.getMessage(), Retorno.ERRO_EXCECAO);
		}

	}

	// consulta contrato na base pelo id
	@GET
	@Path("/consultarPorId")
	@Produces(MediaType.APPLICATION_JSON)
	public Retorno consultaListaAutorizacao(@QueryParam("id") Long id) {
		try {
			return contratoService.consultarContratoPorId(id, getDadosUsuarioLogado().getCodigoUsuario());
		} catch (Exception e) {
			return new Retorno(-1L, e.getMessage(), Retorno.ERRO_EXCECAO);
		}

	}

	// consulta contrato na base pelo nuConvenio
	/*@GET
	@Path("/buscaContratoPorConvenio")
	@Produces(MediaType.APPLICATION_JSON)
	public Retorno buscaContratoPorConvenio(@QueryParam("idConvenio") Long idConvenio) {
		try {
			return contratoService.buscaContratoPorConvenio(idConvenio);
		} catch (Exception e) {
			return new Retorno(-1L, e.getMessage(), Retorno.ERRO_EXCECAO);
		}

	}*/

	@POST
	@Path("/autorizar/efetivar")
	@Consumes({ MediaType.APPLICATION_JSON })
	public Retorno autorizar(@QueryParam("contrato") Long numeroContrato) {
		try {
			return autorizacaoService.autorizar(numeroContrato, getDadosUsuarioLogado().getCodigoUsuario());
		} catch (Exception e) {
			return new Retorno(-1L, e.getMessage(), Retorno.ERRO_EXCECAO);
		}
	}

	@POST
	@Path("/autorizar/cancelar")
	@Consumes({ MediaType.APPLICATION_JSON })
	public Retorno cancelar(@QueryParam("contrato") Long numeroContrato, @QueryParam("contrato") int justificativa) {
		try {
			return autorizacaoService.cancelar(numeroContrato, getDadosUsuarioLogado().getCodigoUsuario());
		} catch (Exception e) {
			return new Retorno(-1L, e.getMessage(), Retorno.ERRO_EXCECAO);
		}
	}

	@GET
	@Path("/validarSr")
	@Consumes({ MediaType.APPLICATION_JSON })
	public Retorno validarSr(@QueryParam("sr") Long sr) {
		try {
			return unidadeService.validaSR(sr);
		} catch (Exception e) {
			return new Retorno(-1L, e.getMessage(), Retorno.ERRO_EXCECAO);
		}
	}
	
	@GET
	@Path("/validarAg")
	@Consumes({ MediaType.APPLICATION_JSON })
	public Retorno validarAg(@QueryParam("ag") Long ag) {
		try {
			return unidadeService.validaAG(ag);
		} catch (Exception e) {
			return new Retorno(-1L, e.getMessage(), Retorno.ERRO_EXCECAO);
		}
	}
	
	@POST
	@Path("/imprimirContrato")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Retorno imprimirContrato(Impressao impressao) throws SystemException, BusinessException {
		try {
			Documento retorno = new Documento();			
			retorno.setContratosImpressao(documento.imprimeDocumentoContrato(impressao));
			return retorno;
		} catch (Exception e) {
			return new Retorno(-1L, e.getMessage(), Retorno.ERRO_EXCECAO);
		}
	}
	
	@POST
	@Path("/buscarTipoDocumentoImpressaoCCR")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Retorno buscarTipoDocumentoImpressaoCCR() throws SystemException, BusinessException {
		try {
			Documento retorno = new Documento();			
			retorno.setIcModeloTestemunha(documento.buscarTipoDocumentoImpressaoCCR());
			return retorno;
		} catch (Exception e) {
			return new Retorno(-1L, e.getMessage(), Retorno.ERRO_EXCECAO);
		}
	}
	
	@POST
	@Path("/atualizaStatusContrato")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Retorno atualizaStatusContrato(ContratoSituacaoRequest contratoSituacao) throws SystemException, BusinessException {
		try {
			return contratoService.atualizaSituacaoContrato(contratoSituacao);
		} catch (Exception e) {
			return new Retorno(-1L, e.getMessage(), Retorno.ERRO_EXCECAO);
		}
	}
	
	
	@POST
	@Path("/atualizaContrato")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Retorno atualizaContrato(Contrato contrato) throws SystemException, BusinessException {
		try {
			return contratoService.atualizaContrato(contrato);
		} catch (Exception e) {
			return new Retorno(-1L, e.getMessage(), Retorno.ERRO_EXCECAO);
		}
	}
	
	@GET
	@Path("/reenviarContratoSIAPX")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Retorno reenviarContratoSIAPX(@QueryParam("numeroContrato") Long numeroContrato) throws Exception {
		Retorno retorno = new Retorno();
		Empregado  empregado = empregadoService.getEmpregado(getDadosUsuarioLogado().getCodigoUsuario());
		
		ContratoConsignadoTO contratoConsignadoTO = contratoService.reenviarContratoSIAPX(numeroContrato, empregado);
		String mensagemNegocial = contratoConsignadoTO.getMensagem().getMensagemNegocial();
		if(StringUtils.isNotBlank(mensagemNegocial)){
			retorno = new Retorno(-1L, mensagemNegocial, Retorno.ERRO_EXCECAO);			
		}
		return retorno;
	}

}
