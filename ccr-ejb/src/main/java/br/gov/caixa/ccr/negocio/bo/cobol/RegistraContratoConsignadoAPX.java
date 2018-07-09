package br.gov.caixa.ccr.negocio.bo.cobol;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import javax.ejb.AccessTimeout;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.LocalBean;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;

import br.gov.caixa.arqrefcore.excecao.BusinessException;
import br.gov.caixa.arqrefcore.jaxb.conversor.ConverterXML;
import br.gov.caixa.arqrefservices.dominio.Empregado;
import br.gov.caixa.arqrefservices.dominio.sicli.CamposRetornados;
import br.gov.caixa.arqrefservices.dominio.sicli.CarteiraCliente;
import br.gov.caixa.arqrefservices.dominio.sicli.EnderecoNacional;
import br.gov.caixa.arqrefservices.dominio.sicli.EstadoCivil;
import br.gov.caixa.arqrefservices.dominio.sicli.MeioComunicacao;
import br.gov.caixa.arqrefservices.dominio.siric.Aprovada;
import br.gov.caixa.arqrefservices.dominio.siric.Avaliacao;
import br.gov.caixa.arqrefservices.negocio.EmpregadoService;
import br.gov.caixa.ccr.dominio.apx.Canal;
import br.gov.caixa.ccr.dominio.apx.ClientePessoaFisica;
import br.gov.caixa.ccr.dominio.apx.ContratoConsignado;
import br.gov.caixa.ccr.dominio.apx.ContratoConsignadoTO;
import br.gov.caixa.ccr.dominio.apx.Convenente;
import br.gov.caixa.ccr.dominio.apx.Margem;
import br.gov.caixa.ccr.dominio.apx.SeguroPrestamista;
import br.gov.caixa.ccr.dominio.apx.Usuario;
/*import br.gov.caixa.arqrefcore.jaxb.conversor.ConverterXML;
import br.gov.caixa.ccr.dominio.apx.Canal;
import br.gov.caixa.ccr.dominio.apx.ClientePessoaFisica;
import br.gov.caixa.ccr.dominio.apx.ContratoConsignadoTO;*/
/*import br.gov.caixa.apx.dominio.Canal;
import br.gov.caixa.apx.dominio.enumeracao.ETipoCanal;
import br.gov.caixa.apx.dominio.transicao.ContratoConsignadoTO;
import br.gov.caixa.apx.stubs.StubsAPX;*/
import br.gov.caixa.ccr.dominio.transicao.ContratoTO;
import br.gov.caixa.ccr.dominio.transicao.RetornoAplicacaoTO;
import br.gov.caixa.ccr.enums.EnumSituacaoContrato;
import br.gov.caixa.ccr.negocio.IClienteBean;
import freemarker.template.utility.StringUtil;


/**
 * Classe que Registra no COBOL as informacoes referente a contrato consignado
 * feita pelo CCR
 * 
 * @author
 * 
 */
public class RegistraContratoConsignadoAPX extends AbstratoBO implements IRegistraContratoConsignadoAPX {
	
	private Logger log = Logger.getLogger(getClass().getName());
	
	@PersistenceContext
	private EntityManager em;
	
	@Inject
	private IClienteBean clienteService;
	
	@Inject
	private EmpregadoService empregadoService;
	
	/***
	 * 
	 * @param contratoConsignadoTO
	 * @return
	 * @throws BusinessException 
	 */
	@Override
	public ContratoConsignadoTO contratarConsignado(ContratoTO contratoTo, CamposRetornados camposRetornados, Empregado empregado) throws BusinessException {
		
		
		log.info("ContratoConsignadoBO - INICIO contratarConsignado");
		log.info(contratoTo.toString());
		
		ContratoConsignadoTO contratoConsignadoTo = this.populaContratoConsignadoTO(contratoTo, camposRetornados, empregado);
		
		String xmlEnviar = ConverterXML.convertToXml(contratoConsignadoTo);
		log.info("XML Enviar");
		log.info(xmlEnviar);
		
		
		String xmlRetorno = cicsConexao.execute("APXPOX33", xmlEnviar, '1');
		log.info("XML Retorno");
		log.info(xmlRetorno);			
		
		ContratoConsignadoTO contratoConsignadoRetornoTO = ConverterXML.convertFromXml(xmlRetorno, ContratoConsignadoTO.class);
		log.info(contratoConsignadoRetornoTO.toString());
		/*if(contratoConsignadoRetornoTO.getMensagem().getMensagemNegocial().trim() != "") {
			log.severe("RegistraContratoConsignadoAPX - Erro agendador envio Contrato SIAPX");
			log.severe("Erro: " + contratoConsignadoRetornoTO.getMensagem().getMensagemNegocial());

			RetornoAplicacaoTO retornoAplicacaoTO = new RetornoAplicacaoTO();
			retornoAplicacaoTO.setContrato(contratoTo);
			retornoAplicacaoTO.setDeRetornoAplicacao(contratoConsignadoRetornoTO.getMensagem().getMensagemNegocial());
			retornoAplicacaoTO.setDtEnvioAplicacao(new Date());
			
			em.persist(retornoAplicacaoTO);
			em.flush();
		} else {
			log.fine("RegistraContratoConsignadoAPX - Sucesso ao enviar contrato SIAPX");
			
			TypedQuery<ContratoTO> queryContrato = em.createNamedQuery("Contrato.atualizaStatusSiapx", ContratoTO.class);
			queryContrato.setParameter("nuContratoAplicacao", contratoConsignadoRetornoTO.getContratoConsignado().getCodigoContrato());
			queryContrato.setParameter("numSituacao", EnumSituacaoContrato.SIAPX.getCodigo());
			queryContrato.setParameter("numTipoSituacao", contratoTo.getSituacao().getTipo());
			queryContrato.setParameter("contratoId", contratoTo.getNuContrato());
			queryContrato.executeUpdate();
			
			log.fine("RegistraContratoConsignadoAPX - Contrato Atualizado");
		}*/
		log.info("ContratoConsignadoBO - FIM contratarConsignado");
		return contratoConsignadoRetornoTO;
	}
		
	
//	private boolean isTesteMockSimulacaoAPX(){
//		String testeMock = System.getProperty("TESTE_MOCK_CONTRATACAO_APX");
//		return testeMock!=null && testeMock.equals("true");
//	}
	
	@Override
	public void enviarContrato() {
		log.info("RegistraContratoConsignadoAPX  - INICIO enviarContrato - Agendador envio Contrato");
		
		/**
		 ************************************************************************
		 * BUSCA TODOS OS CONTRATOS QUE POSSUEM NU_CONTRATO_APLICACAO IGUAL A 0,
		 * OU SEJA, TODOS OS CONTRATOS QUE NÃO RETORNARAM COM SUCESSO DO SIAPX
		 * E TODOS QUE POSSUEM A NU_SITUACAO IGUAL A 7 (Contrato Autorizado).
		 ************************************************************************
		 */
		TypedQuery<ContratoTO> query = null;
		
		query = em.createNamedQuery("Contrato.buscaEnvioSiapx", ContratoTO.class);
		query.setParameter("pNuContratoAplicacao", 0L);
		query.setParameter("pNuSituacao", EnumSituacaoContrato.AUTORIZADO.getCodigo());
				
		List<ContratoTO> listContratos =  (List<ContratoTO>) query.getResultList();
		
		for (ContratoTO contratoTO : listContratos) {
			
			/**
			 ***********************************************************
			 * BUSCA AVALIAÇÃO CLIENTE COMERCIAL E AVALIAÇÃO OPERAÇÃO
			 *********************************************************** 
			 */
//			AvaliacaoRiscoTO avaliacaoOperacao = em.find(AvaliacaoRiscoTO.class, contratoTO.getAvaliacaoCliente().getNuAvaliacao());
//			AvaliacaoRiscoTO avaliacaoCliente  = em.find(AvaliacaoRiscoTO.class, contratoTO.getAvaliacaoOperacao().getNuAvaliacao());
		
			try {
				CamposRetornados camposRetornados = clienteService.consultarPF(String.valueOf(contratoTO.getNuCpf()), "c891975");
				Empregado  empregado = empregadoService.getEmpregado("c891975");
				this.contratarConsignado(contratoTO, camposRetornados, empregado);
			} catch (Exception e) {
				log.severe("RegistraContratoConsignadoAPX  - Erro consulta SICLI");
			}
			
		}
		
		log.info("RegistraContratoConsignadoAPX  - FIM enviarContrato - Agendador envio Contrato");
	}
	
	private ContratoConsignadoTO populaContratoConsignadoTO(ContratoTO contratoTo, CamposRetornados camposRetornados, Empregado empregado){
		Format formatter = new SimpleDateFormat("ddMMyyy");
		ContratoConsignadoTO contratoConsignadoTO = new ContratoConsignadoTO();
		// criancao do canal
		Canal canal = new Canal();
		canal.setCodigoServico("06");
		canal.setCodigoCanal("11");
		canal.setNomeCanal("SIFEC");
		contratoConsignadoTO.setCanal(canal);
		ClientePessoaFisica clientePessoaFisica = new ClientePessoaFisica();
		clientePessoaFisica.setNomeCliente(camposRetornados.getNomeCliente());
		clientePessoaFisica.setSexo(camposRetornados.getSexo());
		clientePessoaFisica.setNumeroCPF(String.valueOf(contratoTo.getNuCpf()));
		clientePessoaFisica.setDataNascimento(camposRetornados.getDataNascimento());
		String dataNascimento[] = camposRetornados.getDataNascimento().getData().split("-");
		clientePessoaFisica.setDataNascimentoAPX(dataNascimento[2] + dataNascimento[1] + dataNascimento[0]);
		clientePessoaFisica.setMatricula(contratoTo.getCoMatriculaCliente());
//		clientePessoaFisica.setNumeroIdentidade(camposRetornados.getIdentidade().getDocumento().getNumero() +""+ camposRetornados.getIdentidade().getDocumento().getDigitoVerificador());
		clientePessoaFisica.setOrgaoEmissorIdentidade(camposRetornados.getIdentidade().getDocumento().getOrgaoEmissor());
		clientePessoaFisica.setDataEmissaoIdentidade(camposRetornados.getIdentidade().getDocumento().getDataEmissao().replace("-", ""));
		clientePessoaFisica.setValorBrutoBeneficio(String.valueOf(contratoTo.getVrBrutoBeneficio()));
		clientePessoaFisica.setValorLiquidoBeneficio(String.valueOf(contratoTo.getVrLiquidoBeneficio()));
		clientePessoaFisica.setNumeroBeneficio(String.valueOf(contratoTo.getNuBeneficio()));
		clientePessoaFisica.setCodigoAgencia(StringUtil.leftPad(String.valueOf(empregado.getNumeroUnidade()), 4, "0"));
		clientePessoaFisica.setUfIdentidade(camposRetornados.getIdentidade().getDocumento().getUf());
		clientePessoaFisica.setIdentidade(camposRetornados.getIdentidade());
		clientePessoaFisica.setNumeroIdentidade(camposRetornados.getIdentidade().getDocumento().getNumero());
//		clientePessoaFisica.setDataEmissaoIdentidade(dataEmissaoIdentidade);
		// Dados não está sendo retornado do SICLI e diz ser obrigatorio
//		clientePessoaFisica.setNaturazaProfissao(camposRetornados.getProfissaoSiric().getProfissao());
		clientePessoaFisica.setNaturazaProfissao("1");
		
		EstadoCivil estadoCivil = new EstadoCivil();
		estadoCivil.setEstadoCivil(camposRetornados.getEstadoCivil().getEstadoCivil());
		clientePessoaFisica.setEstadoCivil(estadoCivil);
		
        List<CarteiraCliente> listCarteira = new ArrayList<CarteiraCliente>();
	    CarteiraCliente carteiraCliente = new CarteiraCliente();
	    carteiraCliente.setCarteiraPrincipal(camposRetornados.getCarteiraCliente().get(0).getCarteiraPrincipal());
	    listCarteira.add(carteiraCliente);
	    clientePessoaFisica.setCarteiraCliente(listCarteira);
	    
	    List<MeioComunicacao> listMeioComunicao = new ArrayList<MeioComunicacao>();
	    MeioComunicacao meioComunicacao = new MeioComunicacao();
	    meioComunicacao.setPrefixoDiscagem(camposRetornados.getMeioComunicacao().get(0).getPrefixoDiscagem());
	    meioComunicacao.setCodigoComunicacao(camposRetornados.getMeioComunicacao().get(0).getCodigoComunicacao());
	    listMeioComunicao.add(meioComunicacao);
	    clientePessoaFisica.setMeioComunicacao(listMeioComunicao);
		

	    List<EnderecoNacional> listEndereco = new ArrayList<EnderecoNacional>();
	    EnderecoNacional enderecoNacional = new EnderecoNacional();
	    enderecoNacional.setLogradouro(camposRetornados.getEnderecoNacional().get(0).getLogradouro());
	    enderecoNacional.setNumero(camposRetornados.getEnderecoNacional().get(0).getNumero().trim());
	    enderecoNacional.setComplemento(camposRetornados.getEnderecoNacional().get(0).getComplemento());
	    enderecoNacional.setNomeMunicipio(camposRetornados.getEnderecoNacional().get(0).getNomeMunicipio());
	    enderecoNacional.setCep(camposRetornados.getEnderecoNacional().get(0).getCep());
	    enderecoNacional.setUf(camposRetornados.getEnderecoNacional().get(0).getUf());
	    listEndereco.add(enderecoNacional);
	    
		
		clientePessoaFisica.setEnderecoNacional(listEndereco);
		
		contratoConsignadoTO.setClientePessoaFisica(clientePessoaFisica);
		
		
		contratoConsignadoTO.setCodigoAvaliacao(String.valueOf(contratoTo.getAvaliacaoOperacao().getNuOperacao()));

		// Campo solicitado no COBOL
		contratoConsignadoTO.setCodigoConceito("c31");
		
		Avaliacao  avaliacaoRisco = new Avaliacao();
		Aprovada avaliacaoAprovada = new Aprovada();
		avaliacaoAprovada.setCodigoAvaliacao(String.valueOf(contratoTo.getAvaliacaoOperacao().getNuOperacao()));
		
		avaliacaoAprovada.setDataInicioVigencia(formatter.format(contratoTo.getAvaliacaoOperacao().getDtInicoAvaliacao()));
		avaliacaoAprovada.setDataFimVigencia(formatter.format(contratoTo.getAvaliacaoOperacao().getDtFimAvaliacao()));
		avaliacaoAprovada.setPrazo(String.valueOf(contratoTo.getAvaliacaoOperacao().getNuPrazoMxmoEmprestimo()));
		avaliacaoAprovada.setValorCPM(String.valueOf(contratoTo.getAvaliacaoOperacao().getVrMaximoPrestacao()));
		avaliacaoAprovada.setValorFinanciamento(String.valueOf(contratoTo.getAvaliacaoOperacao().getVrMaximoEmprestimo()));
		avaliacaoAprovada.setRating(String.valueOf(contratoTo.getAvaliacaoOperacao().getNuRatingAvaliacao()));
		avaliacaoRisco.setAprovada(avaliacaoAprovada);
		
		contratoConsignadoTO.setAvaliacaoRisco(avaliacaoRisco);
		 
		Usuario usuario = new Usuario();
		usuario.setCodigoAgencia("0796");
		usuario.setCodigoUsuario(empregado.getMatricula());
		contratoConsignadoTO.setEmpregado(usuario);
		
//		Empregado emp = new Empregado();
//		emp.setMatricula(empregado.getMatricula());
//		emp.setNumeroUnidadeAlocacao(empregado.getNumeroUnidade());
//		contratoConsignadoTO.setEmpregado(emp);
		
		ContratoConsignado contratoConsignado = new ContratoConsignado();
		contratoConsignado.setDataPrevisaoLiberacao(formatter.format(contratoTo.getDtLiberacaoCredito()));
		contratoConsignado.setDataBaseCalcPrimeiraPrestacao(formatter.format(contratoTo.getDtBaseCalculo()));
		contratoConsignado.setDataVenciamentoPrimeitaPrestacao(formatter.format(contratoTo.getDtVncmoPrimeiraPrestacao()));
		
		// COMPETENCIA MENOR QUE DATA LIBERACAO (TODO) e data corrente maior que data liberacao 
		contratoConsignado.setDataLiberacao(formatter.format(contratoTo.getDtLiberacaoCredito()));
		contratoConsignado.setDataVencimento(formatter.format(contratoTo.getDtVencimento()));
		contratoConsignado.setPrazoContrato(String.valueOf(contratoTo.getPzContrato()));
		contratoConsignado.setValorContrato(String.valueOf(contratoTo.getVrContrato()));
		/*contratoConsignado.setCetValorContrato(String.valueOf(contratoTo.getVrContrato()));*/
		contratoConsignado.setValorIof(String.valueOf(contratoTo.getVrIof()));
		contratoConsignado.setCetIof(String.valueOf(contratoTo.getPcCetIof()));
		contratoConsignado.setValorJurosAcerto(String.valueOf(contratoTo.getVrJuroAcerto()));
		contratoConsignado.setCetJurosAcerto(String.valueOf(contratoTo.getPcCetJuroAcerto()));
		contratoConsignado.setValorBasePrimeiraPrestacao(String.valueOf(contratoTo.getVrPrestacao()));	
		contratoConsignado.setValorLiquido(String.valueOf(contratoTo.getVrLiquidoContrato()));
		contratoConsignado.setValorPrestacao(String.valueOf(contratoTo.getVrPrestacao()));
		contratoConsignado.setCetMensal(String.valueOf(contratoTo.getPcCetMensal()));
		contratoConsignado.setCetAnual(String.valueOf(contratoTo.getPcCetAnual()));
		
		Convenente convenente = new Convenente();
		convenente.setCodigoConvenente(StringUtil.leftPad(String.valueOf(contratoTo.getConvenioTO().getId()), 5, "0"));
//		convenente.setCnpj(String.valueOf(contratoTo.getConvenioTO().getCnpjConvenente()));
		contratoConsignado.setConvenente(convenente);
		// (TODO) Valor Taxa Juros está sendo limitado como 2.0 de minima e 2.0 de maxima. 
		contratoConsignado.setTaxaJuros(String.valueOf(contratoTo.getPcTaxaJuroContrato()));
//		contratoConsignado.setTaxaJuros("2.0");
		
		contratoConsignado.setIndicativoSeguroPrestamista(contratoTo.getIcSeguroPrestamista());
		
		// (TODO) Valor Tipo Simulação está esperando P ou V, fazendo regra com seguro prestamista ou sem seguro. 
		contratoConsignado.setIndicativoTipoSimulacao("V");
		contratoConsignado.setContaDebito(String.valueOf(contratoTo.getNuContaDebito()));
		contratoConsignado.setContaCredito(String.valueOf(StringUtils.leftPad(String.valueOf(contratoTo.getNuAgenciaContaCredito()), 4, "0") 
											+ "" + StringUtils.leftPad(String.valueOf(contratoTo.getNuOperacaoContaCredito()), 3, "0") 
											+ "" + StringUtils.leftPad(String.valueOf(contratoTo.getNuContaCredito()), 8, "0") 
											+ "" + contratoTo.getNuDvContaCredito()));
		
		SeguroPrestamista seguroPrestamista = new SeguroPrestamista();
		
		if(contratoTo.getIcSeguroPrestamista().equals("S")) {
			
			seguroPrestamista.setValor(String.valueOf(contratoTo.getVrSeguroPrestamista()));
			seguroPrestamista.setCet(String.valueOf(contratoTo.getPcCetSeguro()));
			if(contratoTo.getCoMatriculaCliente().indexOf("c") != -1) {
				seguroPrestamista.setMatriculaIndicador(contratoTo.getCoMatriculaCliente().substring(1));
			} else {
				seguroPrestamista.setMatriculaIndicador(contratoTo.getCoMatriculaCliente());
			}
			contratoConsignado.setSeguroPrestamista(seguroPrestamista);
		} else {
			seguroPrestamista.setMatriculaIndicador("0");
			contratoConsignado.setSeguroPrestamista(seguroPrestamista);
		}
		
		
		
		
//		List<Convenio> listConv = new ArrayList<Convenio>();
//		Convenio convenio = new Convenio();
//		convenio.setCodigoConvenio(contratoTo.getConvenioTO().getId().intValue());   //validar
//		listConv.add(convenio);
//		Convenente convenente = new Convenente();
//		convenente.setListaConvenio(listConv);
//		contratoConsignado.setConvenente(convenente);
//		contratoConsignadoTO.setUsuario(usuario);
		contratoConsignadoTO.setContratoConsignado(contratoConsignado);
		
		Margem margem = new Margem();
//		margem.setCodigoOrgao("");
//		margem.setCodigoOrgao("");
//		margem.setDadosInstituidor("");
//		margem.setVinculo("");
//		margem.setDataPrimeiroDesconto("");
		contratoConsignadoTO.setMargem(margem);
		
		
		
		
		return contratoConsignadoTO;
	}	

}
