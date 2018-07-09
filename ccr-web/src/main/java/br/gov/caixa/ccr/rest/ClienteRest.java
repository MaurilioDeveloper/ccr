package br.gov.caixa.ccr.rest;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.dozer.DozerBeanMapper;

import br.gov.caixa.arqrefcore.excecao.BusinessException;
import br.gov.caixa.arqrefcore.excecao.SystemException;
import br.gov.caixa.arqrefcore.excecao.mensagem.Message;
import br.gov.caixa.arqrefcore.excecao.mensagem.Messages;
import br.gov.caixa.arqrefcore.jms.Correlation;
import br.gov.caixa.arqrefservices.dominio.Empregado;
import br.gov.caixa.arqrefservices.dominio.sicli.CamposRetornados;
import br.gov.caixa.arqrefservices.dominio.sicli.alteracao.MantemDadosCliente;
/*import br.gov.caixa.arqrefservices.dominio.sicli.alteracao.CamposSicli;
import br.gov.caixa.arqrefservices.dominio.sicli.alteracao.EnderecoNacional;
import br.gov.caixa.arqrefservices.dominio.sicli.alteracao.EnderecoNacionalComplemento;
import br.gov.caixa.arqrefservices.dominio.sicli.alteracao.MantemDadosCliente;
import br.gov.caixa.arqrefservices.dominio.sicli.alteracao.MeioComunicacao;
*/
import br.gov.caixa.arqrefservices.dominio.sicli.transicao.DadosSICLITO;
import br.gov.caixa.arqrefservices.negocio.IClienteNegocio;
import br.gov.caixa.ccr.dominio.CamposRetornadosCCR;
import br.gov.caixa.ccr.dominio.Convenio;
import br.gov.caixa.ccr.dominio.Mensageria;
import br.gov.caixa.ccr.dominio.Retorno;
import br.gov.caixa.ccr.dominio.mensageria.MensagemCCR;
import br.gov.caixa.ccr.dominio.mensageria.Status;
import br.gov.caixa.ccr.negocio.IClienteBean;

@Path("/cliente")
public class ClienteRest extends AbstractSecurityRest {

	@Inject
	private IClienteNegocio clienteService;
	
	@Inject
	private IClienteBean clienteBean;	

	@POST
	@Path("/solicitarConsulta")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Mensageria<CamposRetornados> solicitarConsulta(Mensageria<DadosSICLITO> entrada)
			throws SystemException, BusinessException {
		Mensageria<CamposRetornados> resposta = new Mensageria<CamposRetornados>();
		CamposRetornados camposRetornados = null;

		// Define o empregado
		Empregado empregado = new Empregado();

		String usuario = "c89599"; // securityContext.getUserPrincipal().getName();
		empregado.setNumeroMatricula(Integer.parseInt(usuario.substring(1)));
		entrada.getDados().setEmpregado(empregado);

		Correlation correlation = clienteService.solicitaDadosClientePF(entrada.getDados());
		camposRetornados = clienteService.recebeDadosClientePF(correlation);

		resposta.setCorrelationId(correlation.getId());
		resposta.setDados(camposRetornados);

		return resposta;
	}

	@GET
	@Path("/consultar")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Mensageria<CamposRetornadosCCR> consultar(@QueryParam("cnpj") String cnpj) throws Exception {

		Correlation correlation = null;
		Mensageria<CamposRetornadosCCR> resposta = new Mensageria<CamposRetornadosCCR>();
		//cnpj="123";

		try {
			// Serviço utilizado para retornar as correlation id
			if (StringUtils.isNotEmpty(cnpj)) {
				DadosSICLITO dadosSICLITO = new DadosSICLITO();
				String cnpjStr = StringUtils.leftPad(cnpj, 14, '0');
				dadosSICLITO.setCnpj(cnpjStr);
				dadosSICLITO.setCodigoUsuario(getDadosUsuarioLogado().getCodigoUsuario());
				correlation = clienteService.solicitaDadosCliente(dadosSICLITO);
				resposta.setCorrelationId(correlation.getId());
			}

		} catch (SystemException e) {

			// Exceções de Sistema
			MensagemCCR mensagemErro = new MensagemCCR();
			mensagemErro.setMessages(e.getMessages());
			String codigoErro = mensagemErro.getMensagem().getCategoriaErro().substring(0, 1)
					+ mensagemErro.getMensagem().getSeveridade() + mensagemErro.getMensagem().getCodigoErro();

			popularMensagemNegocial(resposta, mensagemErro.getMensagem().getCategoriaErro(),
					mensagemErro.getMensagem().getCodigoErro(),
					mensagemErro.getMensagem().getMensagemNegocial().toString(), codigoErro,
					mensagemErro.getMensagem().getParagrafoErro(), mensagemErro.getMensagem().getSeveridadeDescricao(),
					mensagemErro.getMensagem().getSistema(), "ERRO_EXCECAO");
			return resposta;

		} catch (BusinessException e) {

			// Exceções Negociais
			MensagemCCR mensagemErro = new MensagemCCR();
			mensagemErro.setMessages(e.getMessages());
			String codigoErro = mensagemErro.getMensagem().getCategoriaErro().substring(0, 1)
					+ mensagemErro.getMensagem().getSeveridade() + mensagemErro.getMensagem().getCodigoErro();

			popularMensagemNegocial(resposta, mensagemErro.getMensagem().getCategoriaErro(),
					mensagemErro.getMensagem().getCodigoErro(),
					mensagemErro.getMensagem().getMensagemNegocial().toString(), codigoErro,
					mensagemErro.getMensagem().getParagrafoErro(), mensagemErro.getMensagem().getSeveridadeDescricao(),
					mensagemErro.getMensagem().getSistema(), "ERRO_NEGOCIAL");
			return resposta;
		}

		return resposta;
	}

	@GET
	@Path("/consultarPF")
	@Produces(MediaType.APPLICATION_JSON)
	public Object consultarPF(@QueryParam("cpf") String cpf) throws Exception{

		try {
			CamposRetornados camposRetornados = null;
			camposRetornados = clienteBean.consultarPF(cpf, getDadosUsuarioLogado().getCodigoUsuario());
			return camposRetornados;
			
		} catch (Exception e) {
			return new Convenio(-1L, e.getMessage(), Retorno.ERRO_EXCECAO);
		}
		
		
	}
	
	
	@POST
	@Path("/alterarDadosPF")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Object alterarDadosPF( CamposRetornados camposRetornados) throws Exception{

		try {
			br.gov.caixa.arqrefservices.dominio.sicli.alteracao.Usuario usuarioCompleto = new br.gov.caixa.arqrefservices.dominio.sicli.alteracao.Usuario() ;
			usuarioCompleto.setCodigoUsuario(this.getEmpregado().getMatriculaUsuario());
			MantemDadosCliente mantemDadosCliente = clienteService.atualizaDadosClienteSicli(camposRetornados,usuarioCompleto);
			if(mantemDadosCliente.getMensagem() != null) {
				if(mantemDadosCliente.getMensagem().getNumeroMensagemErro() != 11) {
					return new Retorno(-1L, mantemDadosCliente.getMensagem().getDescricaoMensagemErro(), Retorno.ERRO_EXCECAO);
				}
			}
			return camposRetornados;
		} catch (Exception e) {
			return new Retorno(-1L, e.getMessage(), Retorno.ERRO_EXCECAO);
		}
		
		
	}

	@GET
	@Path("/consultarCorrelationId")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Mensageria<CamposRetornadosCCR> consultarCorrelationId(
			@QueryParam("correlationId") final String correlationId) throws Exception {
		Mensageria<CamposRetornadosCCR> resposta = new Mensageria<CamposRetornadosCCR>();
		CamposRetornadosCCR camposRetornados = null;
		CamposRetornados camRet = null;
		Correlation correlation = new Correlation(correlationId);

		try {
			camRet = clienteService.recebeDadosCliente(correlation);
			camposRetornados= new CamposRetornadosCCR();
			DozerBeanMapper mapper = new DozerBeanMapper();
			if(camRet != null ){
				mapper.map(camRet, camposRetornados);
				camposRetornados.getResponseArqRef().setStatus(new Status());	
	
				resposta.setDados(camposRetornados);
			}
			resposta.setCorrelationId(correlation.getId());

		} catch (SystemException e) {

			// Exceções de Sistema
			MensagemCCR mensagemErro = new MensagemCCR();
			mensagemErro.setMessages(e.getMessages());
			String codigoErro = mensagemErro.getMensagem().getCategoriaErro().substring(0, 1)
					+ mensagemErro.getMensagem().getSeveridade() + mensagemErro.getMensagem().getCodigoErro();

			popularMensagemNegocial(resposta, mensagemErro.getMensagem().getCategoriaErro(),
					mensagemErro.getMensagem().getCodigoErro(),
					mensagemErro.getMensagem().getMensagemNegocial().toString(), codigoErro,
					mensagemErro.getMensagem().getParagrafoErro(), mensagemErro.getMensagem().getSeveridadeDescricao(),
					mensagemErro.getMensagem().getSistema(), "ERRO_EXCECAO");
			return resposta;

		} catch (BusinessException e) {

			// Exceções Negociais
			MensagemCCR mensagemErro = new MensagemCCR();
			mensagemErro.setMessages(e.getMessages());
			String codigoErro = mensagemErro.getMensagem().getCategoriaErro().substring(0, 1)
					+ mensagemErro.getMensagem().getSeveridade() + mensagemErro.getMensagem().getCodigoErro();

			popularMensagemNegocial(resposta, mensagemErro.getMensagem().getCategoriaErro(),
					mensagemErro.getMensagem().getCodigoErro(),
					mensagemErro.getMensagem().getMensagemNegocial().toString(), codigoErro,
					mensagemErro.getMensagem().getParagrafoErro(), mensagemErro.getMensagem().getSeveridadeDescricao(),
					mensagemErro.getMensagem().getSistema(), "ERRO_NEGOCIAL");
			return resposta;
		}

		// caso sicli nao retorne as tentativas
		if (resposta.getDados()==null ||resposta.getDados().getResponseArqRef() ==null || resposta.getDados().getResponseArqRef().getStatus() == null) {
			popularMensagemNegocial(resposta, "ERRO", null, "SEM RETORNO DO SICLI", "COD", "", "", "", "ERRO_EXCECAO");
		}

		return resposta;
	}

	/**
	 * 
	 * Metodo que solicita dados do cliente na fila de requisicao. Metodo
	 * assincrono. Retorna um corralation id para busca de dados do cliente na
	 * fila de resposta
	 * 
	 * @param tipoConsulta
	 * @param cpf
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 */
	@GET
	@Path("/consultaClientePF")
	@Produces(MediaType.APPLICATION_JSON)
	public Correlation consultaClientePF() throws BusinessException, SystemException {
		DadosSICLITO dadosSICLITO = new DadosSICLITO();

		if (getDadosUsuarioLogado() != null) {
			String usuario = getDadosUsuarioLogado().getCodigoUsuario();
			dadosSICLITO.setCodigoUsuario(usuario);
			dadosSICLITO.getEmpregado().setMatricula(usuario);
			dadosSICLITO.getEmpregado().setNumeroMatricula(Integer.valueOf(usuario.substring(1)));
		}
		Correlation correlation = clienteService.solicitaDadosClientePF(dadosSICLITO);
		return correlation;

	}
	
		
	@GET
	@Path("/consultarCorrelationIdPF")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Mensageria<CamposRetornadosCCR> consultarCorrelationIdPF(
			@QueryParam("correlationId") final String correlationId) throws Exception {
		Mensageria<CamposRetornadosCCR> resposta = new Mensageria<CamposRetornadosCCR>();
		CamposRetornadosCCR camposRetornados = null;
		CamposRetornados camRet = null;
		Correlation correlation = new Correlation(correlationId);
		
		try {
			camRet = clienteService.recebeDadosClientePF(correlation);
			if(camRet != null) {
				DozerBeanMapper mapper = new DozerBeanMapper();
				camposRetornados= new CamposRetornadosCCR();
				mapper.map(camRet, camposRetornados);
				camposRetornados.getResponseArqRef().setStatus(new Status());	
			}
			resposta.setCorrelationId(correlation.getId());
			resposta.setDados(camposRetornados);

		} catch (SystemException e) {

			// Exceções de Sistema
			MensagemCCR mensagemErro = new MensagemCCR();
			mensagemErro.setMessages(e.getMessages());
			String codigoErro = mensagemErro.getMensagem().getCategoriaErro().substring(0, 1)
					+ mensagemErro.getMensagem().getSeveridade() + mensagemErro.getMensagem().getCodigoErro();

			popularMensagemNegocial(resposta, mensagemErro.getMensagem().getCategoriaErro(),
					mensagemErro.getMensagem().getCodigoErro(),
					mensagemErro.getMensagem().getMensagemNegocial().toString(), codigoErro,
					mensagemErro.getMensagem().getParagrafoErro(), mensagemErro.getMensagem().getSeveridadeDescricao(),
					mensagemErro.getMensagem().getSistema(), "ERRO_EXCECAO");
			return resposta;

		} catch (BusinessException e) {

			// Exceções Negociais
			MensagemCCR mensagemErro = new MensagemCCR();
			mensagemErro.setMessages(e.getMessages());
			String codigoErro = mensagemErro.getMensagem().getCategoriaErro().substring(0, 1)
					+ mensagemErro.getMensagem().getSeveridade() + mensagemErro.getMensagem().getCodigoErro();

			popularMensagemNegocial(resposta, mensagemErro.getMensagem().getCategoriaErro(),
					mensagemErro.getMensagem().getCodigoErro(),
					mensagemErro.getMensagem().getMensagemNegocial().toString(), codigoErro,
					mensagemErro.getMensagem().getParagrafoErro(), mensagemErro.getMensagem().getSeveridadeDescricao(),
					mensagemErro.getMensagem().getSistema(), "ERRO_NEGOCIAL");
			return resposta;
		}
		
		// caso sicli nao retorne as tentativas
		if (resposta.getDados()==null ||resposta.getDados().getResponseArqRef() ==null || resposta.getDados().getResponseArqRef().getStatus() == null) {
			popularMensagemNegocial(resposta, "ERRO", null, "SEM RETORNO DO SICLI", "COD", "", "", "", "ERRO_EXCECAO");
		}
	
		return resposta;
	}
	
	

	public void popularMensagemNegocial(Mensageria<CamposRetornadosCCR> response, String tipoRetorno, String codigo,
			String mensagem, String origemErro, String paragrafoErro, String mensagemNegocial, String categoriaErro,
			String tipo) {

		if (response.getDados() == null) {
			response.setDados(new CamposRetornadosCCR());
		}
		response.getDados().getResponseArqRef().setStatus(new Status(tipoRetorno, codigo, mensagem, origemErro,
				paragrafoErro, mensagemNegocial, categoriaErro, tipo));
	}

	
	@GET
	@Path("/consultarCorrelationClientePorCPF")
	@Produces(MediaType.APPLICATION_JSON)
	public Mensageria<CamposRetornadosCCR> consultarCorrelationClientePorCPF(@QueryParam("cpf") String cpf) throws Exception {

		Correlation correlation = null;
		Mensageria<CamposRetornadosCCR> resposta = new Mensageria<CamposRetornadosCCR>();
		try {
				DadosSICLITO dadosSICLITO = new DadosSICLITO();		
				String cpfStr = StringUtils.leftPad(cpf, 11, '0');
				dadosSICLITO.setCpf(cpfStr);
				dadosSICLITO.setCodigoUsuario(getDadosUsuarioLogado().getCodigoUsuario());
				correlation = clienteService.solicitaDadosCliente(dadosSICLITO);
				resposta.setCorrelationId(correlation.getId());
		} catch (SystemException e) {
			// Exceções de Sistema
			MensagemCCR mensagemErro = new MensagemCCR();
			mensagemErro.setMessages(e.getMessages());
			String codigoErro = mensagemErro.getMensagem().getCategoriaErro().substring(0, 1)
					+ mensagemErro.getMensagem().getSeveridade() + mensagemErro.getMensagem().getCodigoErro();

			popularMensagemNegocial(resposta, mensagemErro.getMensagem().getCategoriaErro(),
					mensagemErro.getMensagem().getCodigoErro(),
					mensagemErro.getMensagem().getMensagemNegocial().toString(), codigoErro,
					mensagemErro.getMensagem().getParagrafoErro(), mensagemErro.getMensagem().getSeveridadeDescricao(),
					mensagemErro.getMensagem().getSistema(), "ERRO_EXCECAO");
			return resposta;

		} catch (BusinessException e) {

			// Exceções Negociais
			MensagemCCR mensagemErro = new MensagemCCR();
			mensagemErro.setMessages(e.getMessages());
			String codigoErro = mensagemErro.getMensagem().getCategoriaErro().substring(0, 1)
					+ mensagemErro.getMensagem().getSeveridade() + mensagemErro.getMensagem().getCodigoErro();

			popularMensagemNegocial(resposta, mensagemErro.getMensagem().getCategoriaErro(),
					mensagemErro.getMensagem().getCodigoErro(),
					mensagemErro.getMensagem().getMensagemNegocial().toString(), codigoErro,
					mensagemErro.getMensagem().getParagrafoErro(), mensagemErro.getMensagem().getSeveridadeDescricao(),
					mensagemErro.getMensagem().getSistema(), "ERRO_NEGOCIAL");
			return resposta;
		} 


		return resposta;
	} 
	
	
}
