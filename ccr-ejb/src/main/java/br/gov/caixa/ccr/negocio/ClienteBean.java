package br.gov.caixa.ccr.negocio;

import java.util.Calendar;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

import br.gov.caixa.arqrefcore.barramento.DAOBarramento;
import br.gov.caixa.arqrefcore.excecao.BusinessException;
import br.gov.caixa.arqrefcore.excecao.SystemException;
import br.gov.caixa.arqrefcore.excecao.mensagem.MessageDefault;
import br.gov.caixa.arqrefcore.jaxb.conversor.ConverterXML;
import br.gov.caixa.arqrefcore.jms.Correlation;
import br.gov.caixa.arqrefcore.util.Stringer;
import br.gov.caixa.arqrefservices.dominio.Empregado;
import br.gov.caixa.arqrefservices.dominio.barramento.MensagemConsultaDadosSicliSaida;
import br.gov.caixa.arqrefservices.dominio.sicli.CamposRetornados;
import br.gov.caixa.arqrefservices.dominio.sicli.Dados;
import br.gov.caixa.arqrefservices.dominio.sicli.MeioComunicacao;
import br.gov.caixa.arqrefservices.dominio.sicli.Renda;
import br.gov.caixa.arqrefservices.dominio.sicli.transicao.DadosSICLITO;
import br.gov.caixa.arqrefservices.negocio.IClienteNegocio;
import br.gov.caixa.ccr.dominio.Usuario;
import br.gov.caixa.ccr.dominio.barramento.alteracaocliente.AtualizacaoCliente;
import br.gov.caixa.ccr.dominio.barramento.alteracaocliente.AtualizarCliente;
import br.gov.caixa.ccr.dominio.barramento.alteracaocliente.CamposSicli;
import br.gov.caixa.ccr.enums.FilasMQ;
import br.gov.caixa.ccr.qualifier.Siemp;


@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ClienteBean implements IClienteBean {

	@PersistenceContext
	private EntityManager em;
	
	@Inject
	private IClienteNegocio clienteService;
	
/*	@Inject
	private AtualizarClienteDAO atualizarClienteDAO;*/
	
	@Inject
	@Siemp
	public DAOBarramento siempDAO;
	
	@Inject
	public Stringer stringer;
	
	@Override
	public CamposRetornados consultar(String cnpj, String userLog) throws Exception {	
		CamposRetornados campos = null;
		if (StringUtils.isNotEmpty(cnpj)) {
			DadosSICLITO dadosSICLITO = new DadosSICLITO();
			String cnpjStr = StringUtils.leftPad(cnpj, 14, '0');
			dadosSICLITO.setCnpj(cnpjStr);
			dadosSICLITO.setCodigoUsuario(userLog);
			try {
				Correlation correlation = clienteService.solicitaDadosCliente(dadosSICLITO);
				
				campos = clienteService.recebeDadosCliente(correlation);
				if(campos == null){
					throw new BusinessException("MA005");
				}
			} catch (SystemException | BusinessException e) {
				throw new BusinessException("MA0026");
			}
				
		}
		return campos;
	}
	
	private CamposRetornados recuperarXmlConsultaCliente(Correlation correlation, String cnpj) throws BusinessException {
		String saida = siempDAO.getWithTimeout(correlation.getId(), FilasMQ.SICLI_RSP_CONSULTA_CLIENTE.toString(), 5000L);				
		saida = encodingXml(saida);		
		saida = verificarEncondingISO88591(saida);
		if (saida == null) {
			MessageDefault msg = new MessageDefault();
			msg.setCodigoErro("0");
//			String cpfCnpj = stringer.with(chaveEntrada.getCnpj()).isEmpty() ? chaveEntrada.getCpf() : chaveEntrada.getCnpj();
			msg.setInformacoesAdicionais("Não houve retorno para consulta do Cliente: "+ cnpj);
			msg.setOrigemErro("MQ");
			msg.setCategoriaErro("3");
			msg.setSeveridade(1);
			msg.setParagrafoErro("Classe: SiempNegocio - Metodo: consultaDadosClienteSicli - Mensagem Retorno Barramento - NullPointerException");
			msg.setSistema("SICLI");
			throw new SystemException(msg);
		}
		
		MensagemConsultaDadosSicliSaida msgSaida = ConverterXML.converterXmlSemSanitizacao(saida, MensagemConsultaDadosSicliSaida.class);
		
		// VERIFICA O COD_RETORNO E LANCHA A EXCECAO
		if (msgSaida.getCodigoRetorno().equalsIgnoreCase("X5")){
			MessageDefault msg = new MessageDefault();
			msg.setCategoriaErro("3");
			msg.setSeveridade(1);
			msg.setCodigoErro(msgSaida.getCodigoRetorno());
			msg.setInformacoesAdicionais(msgSaida.getDados().getExcecao() +" - "+msgSaida.getMensagemRetorno());
			msg.setOrigemErro(msgSaida.getMensagemRetorno());
			msg.setSistema(msgSaida.getOrigemRetorno());

//			throw new SystemException(msg);
			//TODO SKMT verificar
			throw new BusinessException(msgSaida.getMensagemRetorno());
//			throw new BusinessException("MA004");
		}
		
		Dados dados = msgSaida.getDados();
		if(dados.getRetronoClienteDetalhado() == null
				|| dados.getRetronoClienteDetalhado().getCamposRetornados() == null){
			throw new BusinessException("MA004");
		}
		return dados.getRetronoClienteDetalhado().getCamposRetornados();
	}
	
	private String verificarEncondingISO88591(String saida) {
		if (saida != null && !saida.contains("encoding")) {
			saida = saida.replace("?>", " encoding=\"ISO-8859-1\"?>");
		}
		return saida;
	}
	
	private String encodingXml(String saida) {
		return StringEscapeUtils.unescapeXml(saida).replace("&", "&amp;").replace("'", "&apos;");
	}

	@Override
    public CamposRetornados consultarPF(String cpf, String userLog) throws Exception {

          CamposRetornados campos = null;

          if (StringUtils.isNotEmpty(cpf)) {
        	  		
                 DadosSICLITO dadosSICLITO = new DadosSICLITO();

                 String cpfStr = StringUtils.leftPad(cpf, 11, '0');

                 dadosSICLITO.setCpf(cpfStr);
                 dadosSICLITO.setTipoPesquisa("CPF");
                 dadosSICLITO.setCodigoUsuario(userLog);

               //Define o empregado
                 Empregado empregado = new Empregado();
                 String usuario = userLog;
                 empregado.setNumeroMatricula( Integer.parseInt(usuario.substring(1)) );                  
                 dadosSICLITO.setEmpregado(empregado);        
                 try {

                    Correlation correlation = clienteService.solicitaDadosClientePF(dadosSICLITO);
                    campos = clienteService.recebeDadosCliente(correlation);
                    
                    ///TEST SKMT NAO COMITAR
                    /*AtualizacaoCliente ac = new AtualizacaoCliente();
                    CamposSicli camposSicli = new CamposSicli(); 
					ac.setCamposSicli(camposSicli);
					List<EnderecoNacional> enderecoNacionalList = campos.getEnderecoNacional();
					for (EnderecoNacional enderecoNacional : enderecoNacionalList) {
						
						EnderecoNacional endereco = new EnderecoNacional(); 
						Utilities.copyAttributesFromTo(ac.getCamposSicli(), campos);
						camposSicli.setEndereco(endereco);
					}
                    
                    Usuario u = new Usuario();
                    u.setCodigoUsuario(usuario);
                    atualizarDadosCliente(ac, u);*/

                    if(campos == null){
                    	throw new BusinessException("MA005");
                    }

                 } catch (SystemException | BusinessException e) {

                	 throw new BusinessException("MA004");
                 }

          }

          return campos;
    }
	
	@Override
	public String atualizarDadosCliente(AtualizacaoCliente atualizacaoCliente, Usuario usuario) throws BusinessException {

		AtualizarCliente atualizarCliente = new AtualizarCliente(atualizacaoCliente);
		CamposSicli camposSicli = atualizarCliente.getMensagemAtualizacao().getAtualizacao().getCamposSicli();

		if(camposSicli.getEndereco() != null) {
			if (camposSicli.getEndereco().getComplemento() == null) {
				camposSicli.getEndereco().setComplemento("");
			}

			if (camposSicli.getEndereco().getEnderecoNacionalComplemento() != null) {
				camposSicli.getEndereco().getEnderecoNacionalComplemento().get(0).setAnoReferencia(Integer.toString(Calendar.getInstance().get(Calendar.YEAR)));
				camposSicli.getEndereco().getEnderecoNacionalComplemento().get(0).setMesReferencia(Integer.toString(Calendar.getInstance().get(Calendar.MONTH) + 1));
				camposSicli.getEndereco().getEnderecoNacionalComplemento().get(0).setContrato(null);
				camposSicli.getEndereco().getEnderecoNacionalComplemento().get(0).setFiller(null);
				camposSicli.getEndereco().getEnderecoNacionalComplemento().get(0).setFlagUnico(null);
				camposSicli.getEndereco().setFlagPropaganda(null);
				camposSicli.getEndereco().setFlagCorrepondencia(null);
			}
		}

		if(camposSicli.getRenda() != null) {
			for (Renda renda : camposSicli.getRenda()) {
				renda.setDescricaoCodigoOcupacao(null);
				renda.setDataApuracao(null);

				if (renda.getCocliFontePagadora() == null || renda.getCocliFontePagadora().equals("")
						|| renda.getCocliFontePagadora().equals("0")) {
					renda.setCocliFontePagadora(null);
					renda.setCpfCnpjFontePagadora(null);
					renda.setTipoFontePagadora(null);
				}

				if (renda.getCpfCnpjFontePagadora() != null) {
					renda.setCpfCnpjFontePagadora(StringUtils.rightPad(renda.getCpfCnpjFontePagadora(), 14, '0'));
				}

				if (renda.getCaracteristicaRenda().equals("2")) {
					renda.setTipoRenda("F");
					renda.setValorRendaBruta("0");
					renda.setValorRendaLiquida("0");
					renda.setFlagCompracaoRenda("N");
				} else {
					String flagCompravanteRenda = renda.getFlagCompracaoRenda();
					renda.setFlagCompracaoRenda("S");
					if (flagCompravanteRenda == null || flagCompravanteRenda.equals("")
							|| flagCompravanteRenda.equalsIgnoreCase("N")) {
						renda.setFlagCompracaoRenda("N");
					}
				}

			}
		}

		if (camposSicli.getMeiosComunicacao() != null) {
			for(MeioComunicacao meioComunicacao : camposSicli.getMeiosComunicacao()) {
				meioComunicacao.setFinalidade(null);
			}
		}

		//atualizarClienteDAO.atualizarCliente(atualizarCliente, usuario);
		return atualizarCliente.getMensagemAtualizacao().getAtualizacao().getCodigo();

	}
	
}
