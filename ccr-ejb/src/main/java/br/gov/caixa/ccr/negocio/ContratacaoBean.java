package br.gov.caixa.ccr.negocio;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;

import br.gov.caixa.arqrefcore.barramento.DAOBarramento;
import br.gov.caixa.arqrefcore.excecao.BusinessException;
import br.gov.caixa.arqrefcore.excecao.SystemException;
import br.gov.caixa.arqrefcore.excecao.enumerador.ECategoriaErro;
import br.gov.caixa.arqrefcore.excecao.enumerador.ESeveridadeErro;
import br.gov.caixa.arqrefcore.excecao.mensagem.UtilMensagem;
import br.gov.caixa.arqrefcore.jaxb.conversor.ConverterXML;
import br.gov.caixa.arqrefcore.jms.Correlation;
import br.gov.caixa.ccr.dominio.Empregado;
import br.gov.caixa.arqrefservices.dominio.enumerador.EMensagensArqrefServiceErro;
import br.gov.caixa.arqrefservices.util.MensagemArqrefService;
import br.gov.caixa.ccr.dominio.barramento.MensagemContratacaoSICCR;
import br.gov.caixa.ccr.dominio.barramento.MensagemContratacaoSICCRSaida;
import br.gov.caixa.ccr.dominio.barramento.SibarHeader;
import br.gov.caixa.ccr.dominio.barramento.anotacao.SICCRMQ;
import br.gov.caixa.ccr.dominio.barramento.contratacao.DadosContrato;
import br.gov.caixa.ccr.dominio.barramento.contratacao.DadosResultado;
import br.gov.caixa.ccr.dominio.barramento.enumerador.EFilasMQ;

public class ContratacaoBean implements IContratacaoBean {

	@Inject
	@SICCRMQ
	private DAOBarramento conexaoMQ;
	private Logger log = Logger.getLogger(getClass().getName());
	private final String TIPO_PESQUISA = "SIMULACAO_CONTRATACAO";
	
	@Override
	public Correlation contrataCreditoConsignado(DadosContrato dados, Empregado empregado) throws BusinessException, SystemException {
		
		MensagemContratacaoSICCR msgContratacao = new MensagemContratacaoSICCR();
		SibarHeader sibarHeader = new SibarHeader(empregado, TIPO_PESQUISA);
		
		msgContratacao.setSibarHeader(sibarHeader);
		msgContratacao.setDados(dados);
		
		String xml = ConverterXML.convertToXml(msgContratacao);
		this.log.info("XML ENTRADA CONTRATA CREDITO CONSIGNADO");
		this.log.info(xml);
		
		// PUT
		String id = this.conexaoMQ.put(xml, EFilasMQ.SICCR_REQ_CREDITO.toString());
		Correlation correlation;
		
		if (id != null && !id.isEmpty()) {
			correlation = new Correlation(id);
		} else {
			MensagemArqrefService msgArqrefService = new MensagemArqrefService();
			msgArqrefService.setCategoriaErro(ECategoriaErro.ERRO_MQ_SERIES.getDescricao());
			msgArqrefService.setCodigoErro(Integer.toString(ECategoriaErro.ERRO_MQ_SERIES.getValor()));
			msgArqrefService.setInformacoesAdicionais("");
			  
			List<String> listaMensagemNegocial = new ArrayList<String>();
			listaMensagemNegocial.add(EMensagensArqrefServiceErro.MN_004.getDescricao());
			msgArqrefService.setMensagemNegocial(listaMensagemNegocial);
			msgArqrefService.setOrigemErro(ContratacaoBean.class.getName());
			msgArqrefService.setSeveridade(ESeveridadeErro.SEVERIDADE_AVISO.getValor());
			msgArqrefService.setSeveridadeDescricao(ESeveridadeErro.SEVERIDADE_AVISO.getDescricao());
			msgArqrefService.setParagrafoErro(UtilMensagem.getMetodoNome());
			
			this.log.info(msgArqrefService.toString());
			
			throw new BusinessException(msgArqrefService);
		}
			
		return correlation;
	}

	@Override
	public DadosResultado recebeDadosContratacao(Correlation correlation) throws BusinessException, SystemException {
		
		if ((correlation == null) || (correlation.getId().isEmpty())) {			
	      MensagemArqrefService msgArqrefService = new MensagemArqrefService();
	      msgArqrefService.setCategoriaErro(ECategoriaErro.ERRO_NEGOCIAL.getDescricao());
	      msgArqrefService.setCodigoErro(Integer.toString(ECategoriaErro.ERRO_NEGOCIAL.getValor()));
	      
	      msgArqrefService.setMensagemNegocial(new ArrayList<String>());
	      msgArqrefService.getMensagemNegocial().add(EMensagensArqrefServiceErro.MN_003.getDescricao());
	      msgArqrefService.setInformacoesAdicionais("");
	      msgArqrefService.setOrigemErro(ContratacaoBean.class.getName());
	      msgArqrefService.setSeveridade(ESeveridadeErro.SEVERIDADE_ERRO.getValor());
	      msgArqrefService.setSeveridadeDescricao(ESeveridadeErro.SEVERIDADE_ERRO.getDescricao());
	      msgArqrefService.setParagrafoErro(UtilMensagem.getMetodoNome());
	      
	      this.log.info(msgArqrefService.toString());
	      
	      throw new BusinessException(msgArqrefService);
	    }
		
		String xml = this.conexaoMQ.get(correlation.getId(), EFilasMQ.SICCR_RSP_CREDITO.toString());
		
		if (xml == null)
			return null;
		
		if (!xml.contains("encoding"))
			xml = xml.replace("?>", " encoding=\"ISO-8859-1\"?>");
		
		this.log.info("XML SAIDA CONTRATA CREDITO CONSIGNADO");
		this.log.info(xml);
		
		MensagemContratacaoSICCRSaida msgSaida = (MensagemContratacaoSICCRSaida) ConverterXML.converterXmlSemSanitizacao(xml, MensagemContratacaoSICCRSaida.class);
		if (msgSaida.getCodigoRetorno().equalsIgnoreCase("X5")) {
			MensagemArqrefService msgArqrefService = new MensagemArqrefService();
		      msgArqrefService.setCategoriaErro(ECategoriaErro.ERRO_MQ_SERIES.getDescricao());
		      msgArqrefService.setCodigoErro(Integer.toString(ECategoriaErro.ERRO_MQ_SERIES.getValor()));
		      
		      msgArqrefService.setMensagemErro(new ArrayList<String>());
		      msgArqrefService.getMensagemErro().add(msgSaida.getMensagemRetorno());
		      
		      msgArqrefService.setMensagemNegocial(new ArrayList<String>());
		      msgArqrefService.getMensagemNegocial().add(msgSaida.getDados().getExcecao());
		      msgArqrefService.setInformacoesAdicionais("");
		      msgArqrefService.setOrigemErro(ContratacaoBean.class.getName());
		      msgArqrefService.setSeveridade(ESeveridadeErro.SEVERIDADE_ERRO.getValor());
		      msgArqrefService.setSeveridadeDescricao(ESeveridadeErro.SEVERIDADE_ERRO.getDescricao());
		      msgArqrefService.setParagrafoErro(UtilMensagem.getMetodoNome());
		      msgArqrefService.setSistema(msgSaida.getOrigemRetorno());
		      
		      this.log.info(msgArqrefService.toString());
		      
		      throw new SystemException(msgArqrefService);
		}
		
		// tratar excecao de negocio
		
		return msgSaida.getDados();
	}
	
}
