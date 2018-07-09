package br.gov.caixa.ccr.servico;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.gov.caixa.arqrefcore.barramento.DAOBarramento;
import br.gov.caixa.arqrefcore.excecao.BusinessException;
import br.gov.caixa.arqrefcore.excecao.SystemException;
import br.gov.caixa.arqrefcore.excecao.enumerador.ECategoriaErro;
import br.gov.caixa.arqrefcore.excecao.enumerador.ESeveridadeErro;
import br.gov.caixa.arqrefcore.excecao.mensagem.UtilMensagem;
import br.gov.caixa.arqrefcore.jaxb.conversor.ConverterXML;
import br.gov.caixa.arqrefcore.jms.Correlation;
import br.gov.caixa.arqrefservices.dominio.enumerador.EMensagensArqrefServiceErro;
import br.gov.caixa.arqrefservices.util.MensagemArqrefService;
import br.gov.caixa.ccr.dominio.Empregado;
import br.gov.caixa.ccr.dominio.barramento.MensagemAvaliacao;
import br.gov.caixa.ccr.dominio.barramento.MensagemAvaliacaoSaida;
import br.gov.caixa.ccr.dominio.barramento.MensagemConsultaAvaliacao;
import br.gov.caixa.ccr.dominio.barramento.MensagemConsultaAvaliacaoSaida;
import br.gov.caixa.ccr.dominio.barramento.SibarHeader;
import br.gov.caixa.ccr.dominio.barramento.anotacao.SIRICMQ;
import br.gov.caixa.ccr.dominio.barramento.enumerador.EFilasMQ;
import br.gov.caixa.ccr.dominio.barramento.siric.DadosAvaliacao;
import br.gov.caixa.ccr.dominio.barramento.siric.DadosConsultaAvaliacao;
import br.gov.caixa.ccr.dominio.barramento.siric.DadosResultadoAvaliacao;
import br.gov.caixa.ccr.dominio.barramento.siric.DadosResultadoConsulta;
import br.gov.caixa.ccr.negocio.SimulacaoBean;

public class AvaliacaoCliente implements IAvaliacaoCliente {
	
	@Inject
	@SIRICMQ
	private DAOBarramento conexaoMQ;
	private Logger log = Logger.getLogger(getClass().getName());
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public Correlation consultaAvaliacao(DadosConsultaAvaliacao dados, Empregado empregado) throws BusinessException, SystemException {

		MensagemConsultaAvaliacao msg = new MensagemConsultaAvaliacao();
		SibarHeader sibarHeader = new SibarHeader(empregado, DadosConsultaAvaliacao.OPERACAO);
		
		msg.setSibarHeader(sibarHeader);
		msg.setDados(dados);
		
		String xml = ConverterXML.convertToXml(msg);
		this.log.fine("XML ENTRADA CONSULTA AVALIACAO");
		this.log.fine(xml);
		
		// PUT
		String id = this.conexaoMQ.put(xml, EFilasMQ.SIBAR_REQ_CONSULTA_AVALIACAO_RISCO.toString());
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
			msgArqrefService.setOrigemErro(SimulacaoBean.class.getName());
			msgArqrefService.setSeveridade(ESeveridadeErro.SEVERIDADE_AVISO.getValor());
			msgArqrefService.setSeveridadeDescricao(ESeveridadeErro.SEVERIDADE_AVISO.getDescricao());
			msgArqrefService.setParagrafoErro(UtilMensagem.getMetodoNome());
			
			this.log.info(msgArqrefService.toString());
			
			throw new BusinessException(msgArqrefService);
		}
			
		return correlation;
	}
	
	@Override
	public DadosResultadoConsulta recebeDadosConsulta(Correlation correlation) throws BusinessException, SystemException {

		if ((correlation == null) || (correlation.getId().isEmpty())) {			
		      MensagemArqrefService msgArqrefService = new MensagemArqrefService();
		      msgArqrefService.setCategoriaErro(ECategoriaErro.ERRO_NEGOCIAL.getDescricao());
		      msgArqrefService.setCodigoErro(Integer.toString(ECategoriaErro.ERRO_NEGOCIAL.getValor()));
		      
		      msgArqrefService.setMensagemNegocial(new ArrayList<String>());
		      msgArqrefService.getMensagemNegocial().add(EMensagensArqrefServiceErro.MN_003.getDescricao());
		      msgArqrefService.setInformacoesAdicionais("");
		      msgArqrefService.setOrigemErro(SimulacaoBean.class.getName());
		      msgArqrefService.setSeveridade(ESeveridadeErro.SEVERIDADE_ERRO.getValor());
		      msgArqrefService.setSeveridadeDescricao(ESeveridadeErro.SEVERIDADE_ERRO.getDescricao());
		      msgArqrefService.setParagrafoErro(UtilMensagem.getMetodoNome());
		      
		      this.log.info(msgArqrefService.toString());
		      
		      throw new BusinessException(msgArqrefService);
	    }
		
		String xml = this.conexaoMQ.get(correlation.getId(), EFilasMQ.SIBAR_RSP_CONSULTA_AVALIACAO_RISCO.toString());
		
		if (xml == null)
			return null;
		
		if (!xml.contains("encoding"))
			xml = xml.replace("?>", " encoding=\"ISO-8859-1\"?>");
		
		this.log.info("XML SAIDA CONSULTA AVALIACAO CREDITO");
		this.log.info(xml);
		
		MensagemConsultaAvaliacaoSaida msgSaida = (MensagemConsultaAvaliacaoSaida) ConverterXML.converterXmlSemSanitizacao(xml, MensagemConsultaAvaliacaoSaida.class);
		if (msgSaida.getCodigoRetorno().equalsIgnoreCase("X5")) {
			MensagemArqrefService msgArqrefService = new MensagemArqrefService();
		      msgArqrefService.setCategoriaErro(ECategoriaErro.ERRO_MQ_SERIES.getDescricao());
		      msgArqrefService.setCodigoErro(Integer.toString(ECategoriaErro.ERRO_MQ_SERIES.getValor()));
		      
		      msgArqrefService.setMensagemErro(new ArrayList<String>());
		      msgArqrefService.getMensagemErro().add(msgSaida.getMensagemRetorno());
		      
		      msgArqrefService.setMensagemNegocial(new ArrayList<String>());
		      
		      if (msgSaida.getDados() != null && msgSaida.getDados().getExcecao() != null)
		    	  msgArqrefService.getMensagemNegocial().add(msgSaida.getDados().getExcecao());
		      
		      msgArqrefService.setInformacoesAdicionais("");
		      msgArqrefService.setOrigemErro(SimulacaoBean.class.getName());
		      msgArqrefService.setSeveridade(ESeveridadeErro.SEVERIDADE_ERRO.getValor());
		      msgArqrefService.setSeveridadeDescricao(ESeveridadeErro.SEVERIDADE_ERRO.getDescricao());
		      msgArqrefService.setParagrafoErro(UtilMensagem.getMetodoNome());
		      msgArqrefService.setSistema(msgSaida.getOrigemRetorno());
		      
		      this.log.info(msgArqrefService.toString());
		      
		      throw new SystemException(msgArqrefService);
		} else if(msgSaida.getCodigoRetorno().equalsIgnoreCase("00") && msgSaida.getDados() == null){
			return new DadosResultadoConsulta(); 
		} else if(msgSaida.getCodigoRetorno().equalsIgnoreCase("14") && msgSaida.getDados() == null){
			return new DadosResultadoConsulta(); 
		}
				
		return msgSaida.getDados();
		
	}
	
	@Override
	public Correlation solicitaAvaliacao(int idSimulacao, Empregado empregado) throws BusinessException, SystemException {

		MensagemAvaliacao msg = new MensagemAvaliacao();
		SibarHeader sibarHeader = new SibarHeader(empregado, DadosAvaliacao.OPERACAO);
		msg.setSibarHeader(sibarHeader);
		
		DadosAvaliacao dados = new DadosAvaliacao();
		buscaInformacoesSimulacao(idSimulacao, dados, empregado);
		msg.setDados(dados);
		
		String xml = ConverterXML.convertToXml(msg);
		this.log.fine("XML ENTRADA SOLICITA AVALIACAO");
		this.log.fine(xml);
		
		// PUT
		String id = this.conexaoMQ.put(xml, EFilasMQ.SIBAR_REQ_AVALIACAO_RISCO_CREDITO.toString());
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
			msgArqrefService.setOrigemErro(SimulacaoBean.class.getName());
			msgArqrefService.setSeveridade(ESeveridadeErro.SEVERIDADE_AVISO.getValor());
			msgArqrefService.setSeveridadeDescricao(ESeveridadeErro.SEVERIDADE_AVISO.getDescricao());
			msgArqrefService.setParagrafoErro(UtilMensagem.getMetodoNome());
			
			this.log.info(msgArqrefService.toString());
			
			throw new BusinessException(msgArqrefService);
		}
			
		return correlation;
		
	}
	
	@Override
	public DadosResultadoAvaliacao recebeDadosAvaliacao(Correlation correlation) throws BusinessException, SystemException {

		if ((correlation == null) || (correlation.getId().isEmpty())) {			
		      MensagemArqrefService msgArqrefService = new MensagemArqrefService();
		      msgArqrefService.setCategoriaErro(ECategoriaErro.ERRO_NEGOCIAL.getDescricao());
		      msgArqrefService.setCodigoErro(Integer.toString(ECategoriaErro.ERRO_NEGOCIAL.getValor()));
		      
		      msgArqrefService.setMensagemNegocial(new ArrayList<String>());
		      msgArqrefService.getMensagemNegocial().add(EMensagensArqrefServiceErro.MN_003.getDescricao());
		      msgArqrefService.setInformacoesAdicionais("");
		      msgArqrefService.setOrigemErro(SimulacaoBean.class.getName());
		      msgArqrefService.setSeveridade(ESeveridadeErro.SEVERIDADE_ERRO.getValor());
		      msgArqrefService.setSeveridadeDescricao(ESeveridadeErro.SEVERIDADE_ERRO.getDescricao());
		      msgArqrefService.setParagrafoErro(UtilMensagem.getMetodoNome());
		      
		      this.log.info(msgArqrefService.toString());
		      
		      throw new BusinessException(msgArqrefService);
	    }
		
		String xml = this.conexaoMQ.get(correlation.getId(), EFilasMQ.SICCR_RSP_AVALIACAO_RISCO_CREDITO.toString());
		
		if (xml == null)
			return null;
		
		if (!xml.contains("encoding"))
			xml = xml.replace("?>", " encoding=\"ISO-8859-1\"?>");
		
		this.log.fine("XML SAIDA SOLICITA AVALIACAO CREDITO");
		this.log.fine(xml);
		
		MensagemAvaliacaoSaida msgSaida = (MensagemAvaliacaoSaida) ConverterXML.converterXmlSemSanitizacao(xml, MensagemAvaliacaoSaida.class);
		if (msgSaida.getCodigoRetorno().equalsIgnoreCase("X5")) {
			MensagemArqrefService msgArqrefService = new MensagemArqrefService();
		      msgArqrefService.setCategoriaErro(ECategoriaErro.ERRO_MQ_SERIES.getDescricao());
		      msgArqrefService.setCodigoErro(Integer.toString(ECategoriaErro.ERRO_MQ_SERIES.getValor()));
		      
		      msgArqrefService.setMensagemErro(new ArrayList<String>());
		      msgArqrefService.getMensagemErro().add(msgSaida.getMensagemRetorno());
		      
		      msgArqrefService.setMensagemNegocial(new ArrayList<String>());
		      msgArqrefService.getMensagemNegocial().add(msgSaida.getDados().getExcecao());
		      msgArqrefService.setInformacoesAdicionais("");
		      msgArqrefService.setOrigemErro(SimulacaoBean.class.getName());
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
	
	private void buscaInformacoesSimulacao(int id, DadosAvaliacao dados, Empregado empregado) throws BusinessException {
		// busca os dados da simulacao pelo id...
//		SimulacaoTO simulacao =  em.find(SimulacaoTO.class, id);
		// LEGADO
//		if (simulacao == null) {
//			MensagemArqrefService msgArqrefService = new MensagemArqrefService();
//			msgArqrefService.setCategoriaErro(ECategoriaErro.ERRO_NEGOCIAL.getDescricao());
//			msgArqrefService.setCodigoErro(Integer.toString(ECategoriaErro.ERRO_NEGOCIAL.getValor()));
//			msgArqrefService.setInformacoesAdicionais("");
//			  
//			List<String> listaMensagemNegocial = new ArrayList<String>();
//			listaMensagemNegocial.add("Simulação do cliente não encontrada na base!");
//			msgArqrefService.setMensagemNegocial(listaMensagemNegocial);
//			msgArqrefService.setOrigemErro(SimulacaoBean.class.getName());
//			msgArqrefService.setSeveridade(ESeveridadeErro.SEVERIDADE_AVISO.getValor());
//			msgArqrefService.setSeveridadeDescricao(ESeveridadeErro.SEVERIDADE_AVISO.getDescricao());
//			msgArqrefService.setParagrafoErro(UtilMensagem.getMetodoNome());
//						
//			throw new BusinessException(msgArqrefService);
//		}
		// LEGADO
//		ConvenioTO convenio = em.find(ConvenioTO.class, simulacao.getConvenio());
//		if (convenio == null) {
//			MensagemArqrefService msgArqrefService = new MensagemArqrefService();
//			msgArqrefService.setCategoriaErro(ECategoriaErro.ERRO_NEGOCIAL.getDescricao());
//			msgArqrefService.setCodigoErro(Integer.toString(ECategoriaErro.ERRO_NEGOCIAL.getValor()));
//			msgArqrefService.setInformacoesAdicionais("");
//			  
//			List<String> listaMensagemNegocial = new ArrayList<String>();
//			listaMensagemNegocial.add("Convênio vinculado a simulação não encontrado na base!");
//			msgArqrefService.setMensagemNegocial(listaMensagemNegocial);
//			msgArqrefService.setOrigemErro(SimulacaoBean.class.getName());
//			msgArqrefService.setSeveridade(ESeveridadeErro.SEVERIDADE_AVISO.getValor());
//			msgArqrefService.setSeveridadeDescricao(ESeveridadeErro.SEVERIDADE_AVISO.getDescricao());
//			msgArqrefService.setParagrafoErro(UtilMensagem.getMetodoNome());
//						
//			throw new BusinessException(msgArqrefService);
//		}
//		
//		DadosOperacao operacao = new DadosOperacao();
//		operacao.setFinanciamento(Double.toString(simulacao.getContrato()));
//		
//		if (empregado.getNumeroUnidadeAlocacao() == null)
//			operacao.setAgencia(Long.toString(convenio.getNumPvResponsavel()));
//		else			
//			operacao.setAgencia(Integer.toString(empregado.getNumeroUnidadeAlocacao()));
//		
//		operacao.getConvenente().setCnpj(Long.toString(convenio.getCnpjConvenente()));
//		operacao.setPrazo(Integer.toString(simulacao.getPrazo()));
//		operacao.setPrestacao(Double.toString(simulacao.getPrestacao()));
//		operacao.setMargem(Double.toString(simulacao.getPrestacao()));
//		dados.setOperacao(operacao);
//		
//		dados.getRelacionamento().getPessoa().setCnpj(Long.toString(convenio.getCnpjConvenente()));
//		dados.getRelacionamento().getPessoa().setTipo("2");
//		
//		dados.getQuadro().setLinha(Double.toString(simulacao.getPrestacao()).replaceAll("[.]", ""));		
//		dados.getAtualizacaoViaSicli().setCpf(Utilities.padRight(simulacao.getCpf(), 11, '0'));		
		
	}
	
}
