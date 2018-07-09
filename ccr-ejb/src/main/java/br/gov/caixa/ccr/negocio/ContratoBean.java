package br.gov.caixa.ccr.negocio;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;

import com.google.gson.Gson;

import br.gov.caixa.arqrefcore.excecao.BusinessException;
import br.gov.caixa.arqrefcore.excecao.SystemException;
import br.gov.caixa.arqrefcore.log.Logging;
import br.gov.caixa.arqrefservices.dominio.Empregado;
import br.gov.caixa.arqrefservices.dominio.sicli.CamposRetornados;
import br.gov.caixa.arqrefservices.dominio.siric.AvaliacaoValiada;
import br.gov.caixa.ccr.dominio.Contrato;
import br.gov.caixa.ccr.dominio.ContratoSituacaoRequest;
import br.gov.caixa.ccr.dominio.Retorno;
import br.gov.caixa.ccr.dominio.SituacaoContrato;
import br.gov.caixa.ccr.dominio.apx.ContratoConsignadoTO;
import br.gov.caixa.ccr.dominio.transicao.AuditoriaTO;
import br.gov.caixa.ccr.dominio.transicao.AvaliacaoRiscoTO;
import br.gov.caixa.ccr.dominio.transicao.CanalAtendimentoTO;
import br.gov.caixa.ccr.dominio.transicao.ContratoTO;
import br.gov.caixa.ccr.dominio.transicao.ConvenioSrTO;
import br.gov.caixa.ccr.dominio.transicao.ConvenioTO;
import br.gov.caixa.ccr.dominio.transicao.HistoricoSituacaoTO;
import br.gov.caixa.ccr.dominio.transicao.SimulacaoTO;
import br.gov.caixa.ccr.dominio.transicao.SituacaoTO;
import br.gov.caixa.ccr.dominio.transicao.UnidadeTO;
import br.gov.caixa.ccr.enums.EnumCampoAuditoria;
import br.gov.caixa.ccr.enums.EnumSituacaoContrato;
import br.gov.caixa.ccr.enums.EnumTipoAuditoria;
import br.gov.caixa.ccr.enums.EnumTransacaoAuditada;
import br.gov.caixa.ccr.negocio.bo.cobol.IRegistraContratoConsignadoAPX;

@Stateless
@LocalBean
@TransactionManagement(TransactionManagementType.CONTAINER)
@Logging
public class ContratoBean implements IContratoBean {

	@PersistenceContext
	private EntityManager em;
	
	@EJB
	private IClienteBean clienteBean;
	
	@Context
	private SecurityContext securityContext;
	
	@Inject
	private IRegistraContratoConsignadoAPX registraContratoConsignadoAPX;
	
	//chamada do mock para detalhar
	@SuppressWarnings("unchecked")
	@Override
	public Contrato consultarContratoPorId(Long id, String userlog) throws Exception {
		
		TypedQuery<ContratoTO> query = null;
		Query queryHist = null;
		
		query = em.createNamedQuery("Contrato.buscaPorId", ContratoTO.class);
		query.setParameter("pNuContrato", id);
				
		ContratoTO contratoTO =  (ContratoTO) query.getSingleResult();
		Contrato contrato = new Contrato();
		contrato.setDescSituacao(EnumSituacaoContrato.valueById(contratoTO.getSituacao().getId()));
		
		DozerBeanMapper mapper = new DozerBeanMapper();
		mapper.map(contratoTO, contrato);

		queryHist = em.createNativeQuery(HistoricoSituacaoTO.QUERY_BUSCA_HIST);
		queryHist.setParameter(1, id);
			
		List<SituacaoContrato> hist = new ArrayList<SituacaoContrato>();
		List<Object[]> histList = queryHist.getResultList();
		
		for (Object[] situacao : histList) {
			SituacaoContrato situacaoContrato = new SituacaoContrato();
			situacaoContrato.setData((Date) situacao[0]);
			BigDecimal bd = (BigDecimal) situacao[1];
			situacaoContrato.setSituacaoDesc(EnumSituacaoContrato.valueById(bd.longValue()));
			situacaoContrato.setUsuario((String) situacao[2]);
			situacaoContrato.setSituacao(bd.intValue());
			situacaoContrato.setDeJustificativa((String) situacao[3]);
			
			
			hist.add(situacaoContrato);		
		}
		
		contrato.setHistorico(hist);	
		return contrato;
	}
	
	@Override
	public Retorno salvar(Contrato contrato, SituacaoContrato situacao) throws Exception {
			ContratoTO contratoTO = new ContratoTO();
			HistoricoSituacaoTO situacaoContratoTO = new HistoricoSituacaoTO();
			AuditoriaTO auditoriaTO = new AuditoriaTO();
			
			DozerBeanMapper mapper = new DozerBeanMapper();
			BigDecimal idsituacao = (BigDecimal) em.createNativeQuery(HistoricoSituacaoTO.QUERY_BUSCA_ID).getSingleResult();
			if(idsituacao == null){
				idsituacao = new BigDecimal(1);
			}
						
			situacaoContratoTO.setId(idsituacao.longValue());
			situacaoContratoTO.setSituacao(situacao.getSituacao());			
			situacaoContratoTO.setTipoSituacao(situacao.getTipoSituacao().intValue());
			situacaoContratoTO.setUsuario(situacao.getUsuario());
			situacaoContratoTO.setData(new Date());
			
			situacao.setId(idsituacao.longValue());
			
			mapper.map(situacao, situacaoContratoTO);
			mapper.map(contrato, contratoTO);
			
			//Set Canal Atendimento
			CanalAtendimentoTO canalAtendimento = new CanalAtendimentoTO();
			canalAtendimento.setId(contrato.getCanalAtend());
			contratoTO.setCanalAtendimento(canalAtendimento);
			
			// Cria primeiramente o Contrato com NU_AVALIACAO = 0 
			AvaliacaoRiscoTO avaliacaoOperacao=em.find(AvaliacaoRiscoTO.class, 0L); 
			contratoTO.setAvaliacaoOperacao(avaliacaoOperacao);
			
			
			em.persist(contratoTO);
			em.flush();
			
			situacaoContratoTO.setContrato(contratoTO.getNuContrato());
			
			em.persist(situacaoContratoTO);
			em.flush();
			
			//Criação de registro de simulação com mesmo id do contrato e json do sicli na coluna DE_AUDITORIA_SIMULACAO
			
			SimulacaoTO simulacaoTO = new SimulacaoTO();
			mapper.map(contrato, simulacaoTO);
			simulacaoTO.setNuSimulacao(contratoTO.getNuContrato());
			simulacaoTO.setAuditoriaSimulacao(contrato.getSicliCliente());
			simulacaoTO.setVrMargem(new BigDecimal(1));
			simulacaoTO.setDtVencimentoPrimeiraPrestacao(new Date());
			simulacaoTO.setDtSimulacao(new Date());

			em.persist(simulacaoTO);
			em.flush();

			// Geração de registro de auditoria na contratação
			auditoriaTO.setDhTransacaoAuditada(new Date());
			auditoriaTO.setCoUsuarioAuditado(contrato.getCoUsuario());
			auditoriaTO.setNuTransacaoAuditada(EnumTransacaoAuditada.CONTRATACAO.getCodigo());
			auditoriaTO.setIcTipoAcao(EnumTipoAuditoria.INCLUSAO.getDescricao());
			auditoriaTO.setNoCampoAuditado(EnumCampoAuditoria.CONTRATO.getDescricao());
			auditoriaTO.setDeValorAtual(contratoTO.getNuContrato().toString());
			auditoriaTO.setNuContrato(contratoTO);
			auditoriaTO.setNuConvenio(contratoTO.getConvenioTO());
			
			em.persist(auditoriaTO);
			em.flush();
			
			contrato.setNuContrato(contratoTO.getNuContrato());

			return contrato;
		}

	@Override
	public List<Contrato> listar( Long cpf, Long nuContrato, Long situacaoFiltro, String periodoINI, String periodoFim, Long nuCnpjFontePagadora, Long nuAgenciaContaCredito, Long convenioFiltro, Long sr, String userLog) throws Exception {
		
		CriteriaBuilder qb = em.getCriteriaBuilder();
		CriteriaQuery<ContratoTO> criteria = qb.createQuery(ContratoTO.class);
		Root<ContratoTO> contratoFiltro = criteria.from(ContratoTO.class);
		final Path<SituacaoTO> situacao = contratoFiltro.join("situacao",JoinType.LEFT);
		final Path<ConvenioTO> convenio = contratoFiltro.join("convenioTO",JoinType.LEFT);
		
		Predicate p = qb.conjunction();
		
		if(sr !=null){

			Retorno  retorno = this.validaSR(Long.valueOf(sr));
			if(retorno !=null){
				throw new BusinessException(retorno.getIdMsg());	
			}

			CriteriaBuilder qbSr = em.getCriteriaBuilder();
			CriteriaQuery<ConvenioSrTO> criteriaSr = qb.createQuery(ConvenioSrTO.class);
			Root<ConvenioSrTO>convenioSR  = criteriaSr.from(ConvenioSrTO.class);
			Predicate pSr = qb.conjunction();
						
			pSr.getExpressions().add(qbSr.equal(convenioSR.get("id").get("idUnidade"),sr));
			criteriaSr.where(pSr);
						
			List<ConvenioSrTO> listaSr = em.createQuery(criteriaSr).getResultList();
			List<Long> listaContrato = new ArrayList<Long>();	
			
			for(ConvenioSrTO srResult : listaSr){
				listaContrato.add(srResult.getId().getIdConvenio());	
			}
			
			p.getExpressions().add(contratoFiltro.get("convenioTO").get("id").in(listaContrato));
		}
				
		if (cpf != null) {
			p.getExpressions().add(qb.equal(contratoFiltro.get("nuCpf"), cpf ));
		}
		
		if (nuContrato != null) {
			p.getExpressions().add(qb.equal(contratoFiltro.get("nuContrato"),nuContrato));	
		}
		
		if (situacaoFiltro != null) {
			p.getExpressions().add(qb.equal(situacao.get("id"),situacaoFiltro));
		}
		
		if (nuCnpjFontePagadora != null) {
			p.getExpressions().add(qb.equal(contratoFiltro.get("nuCnpjFontePagadora"),nuCnpjFontePagadora));
		}
		
		if (nuAgenciaContaCredito != null) {
			Retorno retorno = this.validaAG(Long.valueOf(nuAgenciaContaCredito));
			if(retorno!=null){
				throw new BusinessException(retorno.getIdMsg());	

			}
			p.getExpressions().add(qb.equal(contratoFiltro.get("nuAgenciaContaCredito"),nuAgenciaContaCredito));
		}
		
		if (convenioFiltro != null) {
			p.getExpressions().add(qb.equal(convenio.get("id"),convenioFiltro));
		}
		
		if(periodoINI != null && !("").equals(periodoINI) ||periodoFim != null && !("").equals(periodoFim)){
			Timestamp timestampFim = null; 
			Timestamp timestampIni = null; 
			
			if(periodoINI != null && !("").equals(periodoINI)){
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				Date date = sdf.parse(periodoINI);
				timestampIni = new Timestamp(date.getTime()); 
			}	
			if(periodoFim != null && !("").equals(periodoFim)){
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
				Date date = sdf.parse(periodoFim + " 23:59:59");
				timestampFim = new Timestamp(date.getTime()); 
			}
		if(situacaoFiltro != null){
			if(timestampIni!=null){
				p.getExpressions().add(qb.greaterThanOrEqualTo(contratoFiltro.<Date>get("dtSituacaoContrato"), timestampIni));
			}	
			if(timestampFim!=null){
				p.getExpressions().add(qb.lessThanOrEqualTo(contratoFiltro.<Date>get("dtSituacaoContrato"), timestampFim));
			}
		}else{
			if(timestampIni!=null){
				p.getExpressions().add(qb.greaterThanOrEqualTo(contratoFiltro.<Date>get("dtContrato"), timestampIni));
			}	
			if(timestampFim!=null){
				p.getExpressions().add(qb.lessThanOrEqualTo(contratoFiltro.<Date>get("dtContrato"), timestampFim));
			}
		}
		
		}
		criteria.where(p);
		
		List<ContratoTO> listaRetorno = em.createQuery(criteria).getResultList();
		List<Contrato> listaContrato = new ArrayList<Contrato>();

		for(ContratoTO contrato : listaRetorno){			
			Contrato contratoVO = new Contrato();
			contratoVO.setImprimir(true);
			contratoVO.setAutorizar(true);

			DozerBeanMapper mapper = new DozerBeanMapper();
			mapper.addMapping(builderAtividade);
			mapper.map(contrato, contratoVO);
			contratoVO.setDescSituacao(EnumSituacaoContrato.valueById(contrato.getSituacao().getId()));
			
			boolean reenviarContratoSIAPX = EnumSituacaoContrato.AUTORIZADO.getCodigo().equals(contrato.getSituacao().getId()) 
					&& contrato.getNuContratoAplicacao() == 0;
			contratoVO.setReenviarContratoSIAPX(reenviarContratoSIAPX);
			
			/**
			 * Verifica o ID da situação, para habilitar a ação de clique
			 * no botão de Imprimir na tela de listagem de Contratos
			 */
			if(contrato.getSituacao().getId() != 8) {
				contratoVO.setImprimir(false);
			}
			
			/**
			 * Verifica o ID da situação, para habilitar a ação de clique
			 * no botão de Autorizar na tela de listagem de Contratos
			 */
			if(contrato.getSituacao().getId().equals(EnumSituacaoContrato.AGUARDANDO_AUTORIZACOES.getCodigo())|| 
					contrato.getSituacao().getId().equals(EnumSituacaoContrato.APROVADA_GERENCIAL.getCodigo())||
					contrato.getSituacao().getId().equals(EnumSituacaoContrato.APROVADA_CONFORMIDADE.getCodigo())||
					contrato.getSituacao().getId().equals(EnumSituacaoContrato.AGUARDANDO_AUTORIZACAO_ALCADA.getCodigo())){ 
				contratoVO.setAutorizar(true);
			}else{
				contratoVO.setAutorizar(false);
			}
			if(contrato.getSituacao().getId().equals(EnumSituacaoContrato.AGUARDANDO_DESBLOQUEIO.getCodigo()) ){ 
				contratoVO.setAvaliar(true); 
			}else{
				contratoVO.setAvaliar(false);
			}
			
			if(contrato.getSituacao().getId().equals(EnumSituacaoContrato.AGUARDANDO_AVALIACAO_OPERACAO.getCodigo()) ||
					contrato.getSituacao().getId().equals(EnumSituacaoContrato.AGUARDANDO_CONTRATAR.getCodigo())){
				contratoVO.setAvaliarOperacao(true); 
			} else {
				contratoVO.setAvaliarOperacao(false);
			}
			
			
			
			//TODO verificar como ira ficar
			//CamposRetornados dadosCliente = clienteBean.consultarPF(contrato.getNuCpf().toString(), userLog);
			contratoVO.setNomeCliente(""+contrato.getNuCpf());
			
			
			listaContrato.add(contratoVO);
		}
		
		return listaContrato;
	}
	
	private static BeanMappingBuilder builderAtividade = new BeanMappingBuilder() {
		protected void configure() {
			
			mapping(ContratoTO.class, ContratoTO.class)
	        	.exclude("convenioTO.convenioCanais");
		}
	};
	
	public Retorno validaSR(Long valor){
				
		Retorno retorno = null;
		TypedQuery<UnidadeTO> queryUnidadeTO = null;
		queryUnidadeTO = em.createNamedQuery("UnidadeTO.verificaSRPorNuUnidade", UnidadeTO.class);
		queryUnidadeTO.setParameter(1, valor);
		List<UnidadeTO> listConvenioTO = (List<UnidadeTO> ) queryUnidadeTO.getResultList();
		if(listConvenioTO.size() < 1){
			return new Retorno(1L, "", Retorno.ERRO_NEGOCIAL, "MA0059");
		}
		return retorno;
		
	}
	
	
	public Retorno validaAG(Long valor){
		
		Retorno retorno = null;
		TypedQuery<UnidadeTO> queryUnidadeTO = null;
		queryUnidadeTO = em.createNamedQuery("UnidadeTO.verificaAgPorNuUnidade", UnidadeTO.class);
		queryUnidadeTO.setParameter(1, valor);
		List<UnidadeTO> listConvenioTO = (List<UnidadeTO> ) queryUnidadeTO.getResultList();
		if(listConvenioTO.size() < 1){
			return new Retorno(1L, "", Retorno.ERRO_NEGOCIAL, "MA0060");
		}
		return retorno;
		
	}


	/*@Override
	public Contrato buscaContratoPorConvenio(Long nuConvenio) throws Exception {
		TypedQuery<ContratoTO> queryContratoTO = null;
		queryContratoTO  = em.createNamedQuery("Contrato.buscaContratoPorConvenio", ContratoTO.class);
		queryContratoTO.setParameter("nuConvenio", nuConvenio);
		Contrato contrato = new Contrato();
		ContratoTO contratoTO = (ContratoTO) queryContratoTO.getSingleResult();
		
		DozerBeanMapper mapper = new DozerBeanMapper();
		mapper.map(contratoTO, contrato);
		
		return contrato;
		
	}*/

	@Override
	public Long consultarCpfPorContrato(Long idContrato) throws SystemException, BusinessException {
		if (idContrato != null) {
			ContratoTO contrato = em.find(ContratoTO.class, idContrato);
			if (contrato != null) {
				return contrato.getNuCpf();
			}
		}
		return null;
	}	
	
	
	@Override
	public ContratoTO consultarContratoPorNr(Long nuContrato) throws SystemException, BusinessException {
		ContratoTO contratoTO = em.find(ContratoTO.class, nuContrato);
		return contratoTO;
	}

	@Override
	public Retorno atualizaSituacaoContrato(ContratoSituacaoRequest contrato) throws Exception {
		Query queryContratoUpdate = em.createNamedQuery("Contrato.atualizaStatus", ContratoTO.class);
		queryContratoUpdate.setParameter("contratoId", contrato.getNuContrato());
		queryContratoUpdate.setParameter("numTipoSituacao", contrato.getSituacao().getTipo());
		queryContratoUpdate.setParameter("numSituacao",  contrato.getSituacao().getId() );
		queryContratoUpdate.executeUpdate();
		
		if(contrato.getIdAvaliacao() != null){
			salvaNrAvaliacaoRiscoInContrato(contrato.getIdAvaliacao(), contrato.getSituacao().getId(), contrato.getNuContrato());
		}
		
		return new Retorno(contrato.getNuContrato(), "", Retorno.SUCESSO, "MA001");
	}	
	
	
	@Override
	public Retorno atualizaContrato(Contrato contrato) throws Exception {
		ContratoTO contratoTO = new ContratoTO();

		TypedQuery<ContratoTO> query = null;
		
		query = em.createNamedQuery("Contrato.buscaPorId", ContratoTO.class);
		query.setParameter("pNuContrato", contrato.getNuContrato());
				
		ContratoTO contratoBusca =  (ContratoTO) query.getSingleResult();
		
		DozerBeanMapper mapper = new DozerBeanMapper();
		mapper.map(contrato, contratoTO);
		
		long nuAvaliacaoCliente = contratoBusca.getAvaliacaoCliente().getNuAvaliacao();
		long nuAvaliacaoOperacao = contratoBusca.getAvaliacaoOperacao().getNuAvaliacao();
		SituacaoTO situacao = new SituacaoTO();
		situacao.setId(2L);
		situacao.setTipo(2L);
		contratoTO.setSituacao(situacao);
		AvaliacaoRiscoTO avaliacaoOperacao=em.find(AvaliacaoRiscoTO.class, nuAvaliacaoOperacao);
		AvaliacaoRiscoTO avaliacaoCliente=em.find(AvaliacaoRiscoTO.class, nuAvaliacaoCliente);
		contratoTO.setAvaliacaoOperacao(avaliacaoOperacao);
		contratoTO.setAvaliacaoCliente(avaliacaoCliente);
		
		contratoTO.setDtLiberacaoCredito(new Date());
		
		em.merge(contratoTO);
		em.flush();
		
//		Query queryContratoAvOpeUpdate = em.createNamedQuery("Contrato.atualizaNrAvaliacaClienteOperacao", ContratoTO.class);
//		queryContratoAvOpeUpdate.setParameter("numAvaliacaoCliente", nuAvaliacaoCliente);
//		queryContratoAvOpeUpdate.setParameter("numAvaliacaoOperacao", nuAvaliacaoOperacao);
//		queryContratoAvOpeUpdate.setParameter("contratoId", contratoTO.getNuContrato());
//		queryContratoAvOpeUpdate.executeUpdate();
		
		
		return new Retorno(contrato.getNuContrato(), "", Retorno.SUCESSO, "MA001");
	}	
	
	
	
	public AvaliacaoRiscoTO avaliacaoOperacao(AvaliacaoValiada avaliacaoValiada, AvaliacaoRiscoTO avaliacaoOperacao) throws ParseException {
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String dateFimString = avaliacaoValiada.getDataFimValidade(); 
        Date dateFim  = formatter.parse(dateFimString);
       //DT_FIM_VALIDADE_AVALIACAO
        avaliacaoOperacao.setDtFimAvaliacao(dateFim);
        
		//DT_GERACAO
        avaliacaoOperacao.setDtGeracao(new Date()); 
		
		String dateIniString = avaliacaoValiada.getDataInicioValidade();
	    Date dateInicio  = formatter.parse(dateIniString);
	    //DT_INICIO_VALIDADE_AVALIACAO
	    avaliacaoOperacao.setDtInicoAvaliacao(dateInicio);
	    avaliacaoOperacao.setIcAvaliacaoManual(null);
		
		//IC_BLOQUEIO_ALCADA
		String bloq = avaliacaoValiada.getFlagBloquadaAlcada() == "0"  ? "N" : "S";
		avaliacaoOperacao.setIcBloqueioAlcada(bloq); 
		
		//NO_PRODUTO_SIRIC
		avaliacaoOperacao.setNoProduto("Consignado");
		//DE_RESULTADO_AVALIACAO
		avaliacaoOperacao.setNoResultado("ACEITO");
		//NU_OPERACAO_PRODUTO
		avaliacaoOperacao.setNuOperacao(Long.parseLong(avaliacaoValiada.getCodigoAvaliacao()));
		//PZ_MAXIMO_EMPRESTIMO
		avaliacaoOperacao.setNuPrazoMxmoEmprestimo(Long.parseLong(avaliacaoValiada.getPrazo()));   
		//IN_RATING_AVALIACAO
		avaliacaoOperacao.setNuRatingAvaliacao(Long.parseLong(avaliacaoValiada.getRating()));  
		String strValorFinciamento = avaliacaoValiada.getValorFinanciamento().replaceAll(",","");
		BigDecimal valorFinanciamento=new BigDecimal(strValorFinciamento);
		//VR_MAXIMO_EMPRESTIMO
		avaliacaoOperacao.setVrMaximoEmprestimo(valorFinanciamento);
		
		String strValorPrestaco = avaliacaoValiada.getValorLimiteCPM().replaceAll(",",""); 
		BigDecimal valorPrestacao=new BigDecimal(strValorPrestaco);
		//VR_MAXIMO_PRESTACAO
		avaliacaoOperacao.setVrMaximoPrestacao(valorPrestacao);
		
		return avaliacaoOperacao;
	}
	
	
	public AvaliacaoRiscoTO avaliacaoCliente(AvaliacaoValiada avaliacaoValiada, AvaliacaoRiscoTO avaliacaoCliente) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	    String dateFimString = avaliacaoValiada.getDataFimValidade(); 
	    Date dateFim  = formatter.parse(dateFimString);
	   //DT_FIM_VALIDADE_AVALIACAO
	    avaliacaoCliente.setDtFimAvaliacao(dateFim);
	    
		//DT_GERACAO
	    avaliacaoCliente.setDtGeracao(new Date()); 
		
		String dateIniString = avaliacaoValiada.getDataInicioValidade();
	    Date dateInicio  = formatter.parse(dateIniString);
	    //DT_INICIO_VALIDADE_AVALIACAO
	    avaliacaoCliente.setDtInicoAvaliacao(dateInicio);
	    avaliacaoCliente.setIcAvaliacaoManual(null);
		
		//IC_BLOQUEIO_ALCADA
		String bloq = avaliacaoValiada.getFlagBloquadaAlcada() == "0"  ? "N" : "S";
		avaliacaoCliente.setIcBloqueioAlcada(bloq); 
		
		//NO_PRODUTO_SIRIC
		avaliacaoCliente.setNoProduto("Consignado");
		//DE_RESULTADO_AVALIACAO
		avaliacaoCliente.setNoResultado("ACEITO");
		//NU_OPERACAO_PRODUTO
		avaliacaoCliente.setNuOperacao(Long.parseLong(avaliacaoValiada.getCodigoAvaliacao()));
		//PZ_MAXIMO_EMPRESTIMO
		avaliacaoCliente.setNuPrazoMxmoEmprestimo(Long.parseLong(avaliacaoValiada.getPrazo()));   
		//IN_RATING_AVALIACAO
		avaliacaoCliente.setNuRatingAvaliacao(Long.parseLong(avaliacaoValiada.getRating()));  
		String strValorFinciamento = avaliacaoValiada.getValorFinanciamento().replaceAll(",","");
		BigDecimal valorFinanciamento=new BigDecimal(strValorFinciamento);
		//VR_MAXIMO_EMPRESTIMO
		avaliacaoCliente.setVrMaximoEmprestimo(valorFinanciamento);
		
		String strValorPrestaco = avaliacaoValiada.getValorLimiteCPM().replaceAll(",",""); 
		BigDecimal valorPrestacao=new BigDecimal(strValorPrestaco);
		//VR_MAXIMO_PRESTACAO
		avaliacaoCliente.setVrMaximoPrestacao(valorPrestacao); 

		return avaliacaoCliente;
	}
	
	
	public void salvaNrAvaliacaoRiscoInContrato(Long idAvaliacaoRisco, Long nSituacao, Long nrContrato){
		if(nSituacao == EnumSituacaoContrato.AGUARDANDO_AVALIACAO_OPERACAO.getCodigo()){
			Query queryContratoAvCliUpdate = em.createNamedQuery("Contrato.atualizaNrAvaliacaCliente", ContratoTO.class);
			queryContratoAvCliUpdate.setParameter("numAvaliacaoCliente",idAvaliacaoRisco);
			queryContratoAvCliUpdate.setParameter("contratoId", nrContrato);
			queryContratoAvCliUpdate.executeUpdate();
		}else if(nSituacao == EnumSituacaoContrato.AGUARDANDO_CONTRATAR.getCodigo()) {
			Query queryContratoAvOpeUpdate = em.createNamedQuery("Contrato.atualizaNrAvaliacaOperacao", ContratoTO.class);
			queryContratoAvOpeUpdate.setParameter("numAvaliacaoOperacao", idAvaliacaoRisco);
			queryContratoAvOpeUpdate.setParameter("contratoId", nrContrato);
			queryContratoAvOpeUpdate.executeUpdate();
		}else{
			
		}
	}
	
	public ContratoConsignadoTO reenviarContratoSIAPX(Long numeroContrato, Empregado empregado) throws Exception{
		
		TypedQuery<ContratoTO> query = null;
		
		query = em.createNamedQuery("Contrato.buscaPorId", ContratoTO.class);
		query.setParameter("pNuContrato", numeroContrato);
		ContratoTO contrato =  (ContratoTO) query.getSingleResult();
		
		CamposRetornados camposRetornados = clienteBean.consultarPF(String.valueOf(contrato.getNuCpf()), empregado.getMatriculaUsuario());
		
		ContratoConsignadoTO contratarConsignado = registraContratoConsignadoAPX.contratarConsignado(contrato, camposRetornados, empregado);
		return contratarConsignado;
	}
}
