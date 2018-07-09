package br.gov.caixa.ccr.negocio;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.dozer.DozerBeanMapper;

import com.google.gson.Gson;

import br.gov.caixa.arqrefcore.excecao.BusinessException;
import br.gov.caixa.arqrefcore.excecao.SystemException;
import br.gov.caixa.arqrefservices.dominio.Empregado;
import br.gov.caixa.arqrefservices.dominio.sicli.CamposRetornados;
import br.gov.caixa.ccr.dominio.AutorizacaoContrato;
import br.gov.caixa.ccr.dominio.AutorizarContrato;
import br.gov.caixa.ccr.dominio.transicao.AuditoriaTO;
import br.gov.caixa.ccr.dominio.transicao.AutorizacaoTO;
import br.gov.caixa.ccr.dominio.transicao.ContratoTO;
import br.gov.caixa.ccr.dominio.transicao.HistoricoSituacaoTO;
import br.gov.caixa.ccr.dominio.transicao.SimulacaoTO;
import br.gov.caixa.ccr.enums.EnumAutorizar;
import br.gov.caixa.ccr.enums.EnumCampoAuditoria;
import br.gov.caixa.ccr.enums.EnumSituacaoContrato;
import br.gov.caixa.ccr.enums.EnumTipoAuditoria;
import br.gov.caixa.ccr.enums.EnumTipoAutorizacao;
import br.gov.caixa.ccr.enums.EnumTipoContrato;
import br.gov.caixa.ccr.enums.EnumTransacaoAuditada;
import br.gov.caixa.ccr.negocio.bo.cobol.IRegistraContratoConsignadoAPX;

@Stateless
@LocalBean
@TransactionManagement(TransactionManagementType.CONTAINER)
public class AutorizarContratoBean implements IAutorizarContratoBean {

	@PersistenceContext
	private EntityManager em;
	
	@Inject
	private IRegistraContratoConsignadoAPX registraContratoConsignadoAPX;
	
	@Inject
	private IClienteBean clienteService;

	@TransactionAttribute(TransactionAttributeType.REQUIRED)

	@Override
	public List<AutorizacaoContrato> autorizar(AutorizarContrato dados, EnumTipoAutorizacao tipoUsuario,
			Empregado empregado) throws BusinessException, SystemException, ParseException {
		if (tipoUsuario == null)
			if (dados.getAutorizar() == null) {
				throw new BusinessException("MA0001");
			}
		if (dados.getAutorizar().equals(EnumAutorizar.REJEITAR)
				&& (dados.getJustificativa() == null || dados.getJustificativa().isEmpty())) {
			throw new BusinessException("MA0001");
		}
		
		
		/**
		 *************************************************************
		 * 	BUSCA CONTRATO PARA VERIFICAR SE SUA SITUAÇÃO ESTÁ NEGADA
		 * 	CASO ESTEJA, O CONTRATO FOI ENCERRADO, E NÃO PODERÁ MAIS 
		 * 	SER NEGADO OU APROVADO.
		 *************************************************************
		 */
		TypedQuery<ContratoTO> query = null;
		
		query = em.createNamedQuery("Contrato.buscaPorId", ContratoTO.class);
		query.setParameter("pNuContrato", Long.parseLong(dados.getIdContrato()));
				
		ContratoTO contratoTO =  (ContratoTO) query.getSingleResult();
		
		
		TypedQuery<SimulacaoTO> querySimulacao = null;
		querySimulacao = em.createNamedQuery("Simular.buscaPorIdContrato", SimulacaoTO.class);
		querySimulacao.setParameter("pNuContrato", contratoTO.getNuContrato());
				
		SimulacaoTO simulacaoTO =  (SimulacaoTO) querySimulacao.getSingleResult();
		
		if(contratoTO.getSituacao().getDescricao().equalsIgnoreCase(EnumSituacaoContrato.NEGADA.getDescricao())){
			// Este Contrato já está NEGADO e não pode mais realizar nenhum autorização.
			throw new BusinessException("MA0010");
		}
		
		/**
		 *************************************************************
		 *	FIM BUSCA CONTRATO E VERIFICAÇÃO DE SITUAÇÃO.
		 *************************************************************
		 */
		
		AutorizacaoTO autorizacaoTO = new AutorizacaoTO();
		List<AutorizacaoContrato> listAutorizacaoContrato = new ArrayList<AutorizacaoContrato>();
		
		
		
		/**
		 * @query Responsavel por realizar a busca de autorizações por tipo aprovação (GERENCIAL)
		 * e matricula.
		 */
		TypedQuery<Object> queryGerencial = em.createNamedQuery("Autoriza.buscarPorTipoAprovacaoMatricula",
				Object.class);

		queryGerencial.setParameter("icTipoAutorizacao", EnumTipoAutorizacao.GERENCIAL.getCodigo());
		queryGerencial.setParameter("nuContrato", Long.parseLong(dados.getIdContrato()));
		List<Object> objectGerencial = (List<Object>) queryGerencial.getResultList();
		List<AutorizacaoTO> autorizacaoGerencial = new ArrayList<AutorizacaoTO>();
		
		DozerBeanMapper mapper = new DozerBeanMapper();
		
		// passa todos os dados do objeto para autorizacao
		mapper.map(objectGerencial, autorizacaoGerencial);
		
		
		boolean fluxoAutorizar = false;
		String tipoGerente = "";
		if(autorizacaoGerencial.size() == 0) {
			fluxoAutorizar = true;
		} else {
			if(!autorizacaoGerencial.get(0).getIcSituacaoAutorizacao().equals(dados.getAutorizar())) {
				fluxoAutorizar = true;
			} else {
				fluxoAutorizar = false;
			}
		}
		

		/**
		 * @query Responsavel por realizar a busca de autorizações por tipo aprovação (GERENCIAL => Segundo Gerente)
		 * e matricula.
		 */
		TypedQuery<Object> querySegundoGerente = em.createNamedQuery("Autoriza.buscarPorTipoAprovacaoMatricula",
				Object.class);

		querySegundoGerente.setParameter("icTipoAutorizacao", EnumTipoAutorizacao.SEGUNDOGERENTE.getCodigo());
		querySegundoGerente.setParameter("nuContrato", Long.parseLong(dados.getIdContrato()));
		List<Object> objectSegundoGerente = (List<Object>) querySegundoGerente.getResultList();
		List<AutorizacaoTO> autorizacaoSegundoGerente = new ArrayList<AutorizacaoTO>();
		
		// passa todos os dados do objeto para autorizacao
		mapper.map(objectSegundoGerente, autorizacaoSegundoGerente);
		
		if(autorizacaoSegundoGerente.size() == 0) {
			fluxoAutorizar = true;
		} else {
			if(!autorizacaoSegundoGerente.get(0).getIcSituacaoAutorizacao().equals(dados.getAutorizar())) {
				fluxoAutorizar = true;
			} else {
				fluxoAutorizar = false;
			}
		}
		
		if(autorizacaoGerencial.size() == 0 && autorizacaoSegundoGerente.size() == 0) {
			tipoGerente = EnumTipoAutorizacao.GERENCIAL.getCodigo();
		} else if(!autorizacaoGerencial.get(0).getCoAutorizacao().equals(empregado.getMatricula()) && autorizacaoGerencial.get(0).getIcTipoAutorizacao().equals(EnumTipoAutorizacao.SEGUNDOGERENTE.getCodigo())) {
			tipoGerente = EnumTipoAutorizacao.GERENCIAL.getCodigo();	
		} else if(autorizacaoGerencial.get(0).getCoAutorizacao().trim().equalsIgnoreCase(empregado.getMatricula()) && autorizacaoGerencial.get(0).getIcTipoAutorizacao().equals(EnumTipoAutorizacao.GERENCIAL.getCodigo())) {
			tipoGerente = EnumTipoAutorizacao.GERENCIAL.getCodigo();
		} else {
			tipoGerente = EnumTipoAutorizacao.SEGUNDOGERENTE.getCodigo();
		}
		
		if(fluxoAutorizar) {
			autorizacaoTO = salvaAutorizacao(dados, empregado.getMatricula(), tipoUsuario, tipoGerente);
			
			HistoricoSituacaoTO status = buscaStatusAutorizacaoParaAtualizar(dados, autorizacaoTO);
			atualizaContrato(dados, status, empregado.getMatricula(), autorizacaoTO, tipoUsuario);

			AutorizacaoContrato autorizacaoContrato = new AutorizacaoContrato();
			
			if(autorizacaoTO.getSituacaoContrato().getSituacao().toString().equals(EnumSituacaoContrato.NEGADA.getCodigo().toString())) { 
				if(autorizacaoTO.getIcTipoAutorizacao().equals(EnumTipoAutorizacao.SEGUNDOGERENTE.getCodigo())) {
					autorizacaoContrato.setDescSituacao(EnumSituacaoContrato.NEGADA_CONFORMIDADE.getDescricao());
				} else {
					autorizacaoContrato.setDescSituacao(EnumSituacaoContrato.NEGADA_GERENCIAL.getDescricao());
				}
			} else if(autorizacaoTO.getSituacaoContrato().getSituacao().toString().equals(EnumSituacaoContrato.AUTORIZADO.getCodigo().toString())) {
				autorizacaoContrato.setDescSituacao(EnumSituacaoContrato.APROVADA_CONFORMIDADE.getDescricao());
				//chama metodo que chama o APX		
				Gson gson = new Gson();
				CamposRetornados campos = null;
				campos = gson.fromJson(simulacaoTO.getAuditoriaSimulacao(), CamposRetornados.class);      
				
				try {
					if(campos == null) {
						//Busca dados no SICLI
						campos = clienteService.consultarPF(String.valueOf(contratoTO.getNuCpf()), empregado.getMatricula());
					}
					registraContratoConsignadoAPX.contratarConsignado(contratoTO, campos, empregado);
				} catch (Exception e) {
					throw new BusinessException(e.getMessage());
				}
			} else if(autorizacaoTO.getSituacaoContrato().getSituacao().toString().equals(EnumSituacaoContrato.AGUARDANDO_AUTORIZACAO_ALCADA.getCodigo().toString())){
				autorizacaoContrato.setDescSituacao(EnumSituacaoContrato.APROVADA_GERENCIAL.getDescricao());
			} else {
				autorizacaoContrato.setDescSituacao(EnumSituacaoContrato.valueById(autorizacaoTO.getSituacaoContrato().getSituacao()));
			}
			autorizacaoContrato.setDtSituacao(autorizacaoTO.getTsAutorizacao());
			autorizacaoContrato.setNuSituacaoContrato(autorizacaoTO.getSituacaoContrato().getId().longValue());
			autorizacaoContrato.setUsuario(autorizacaoTO.getCoAutorizacao());
			autorizacaoContrato.setTipoAutorizacao(autorizacaoTO.getIcTipoAutorizacao());
			autorizacaoContrato.setCodigoSituacao(autorizacaoTO.getIcSituacaoAutorizacao());
			listAutorizacaoContrato.add(autorizacaoContrato);

		}
		
		
		
		
		return listAutorizacaoContrato;
	}

	private AutorizacaoTO salvaAutorizacao(AutorizarContrato dados, String matricula, EnumTipoAutorizacao tipoUsuario, String tipoGerente) {
		AutorizacaoTO autorizacaoTO = new AutorizacaoTO();
//		BigDecimal idAutorizacao = (BigDecimal) em.createNativeQuery(AutorizacaoTO.QUERY_BUSCA_ID).getSingleResult();
		
		autorizacaoTO.setDeJustificativa(dados.getJustificativa());
		autorizacaoTO.setCoAutorizacao(matricula);
		autorizacaoTO.setIcSituacaoAutorizacao(dados.getAutorizar().getCodigo());
		autorizacaoTO.setIcTipoAutorizacao(tipoGerente);
		autorizacaoTO.setNuContrato(Long.parseLong(dados.getIdContrato()));
		em.persist(autorizacaoTO);
		em.flush();
		return autorizacaoTO;
	}

	@SuppressWarnings("unused")
	private HistoricoSituacaoTO buscaStatusAutorizacaoParaAtualizar(AutorizarContrato dados, AutorizacaoTO autorizacaoTO) {
		TypedQuery<Object> queryGerencial = em.createNamedQuery("Autoriza.buscarPorTipoAprovacaoMatricula",
				Object.class);

		queryGerencial.setParameter("icTipoAutorizacao", EnumTipoAutorizacao.GERENCIAL.getCodigo());
		queryGerencial.setParameter("nuContrato", Long.parseLong(dados.getIdContrato()));
		queryGerencial.setMaxResults(1);
		Object objectGerencial = (Object) queryGerencial.getSingleResult();
		
		AutorizacaoTO autorizacaoGerencial = new AutorizacaoTO();
		
		DozerBeanMapper mapper = new DozerBeanMapper();
		
		// passa todos os dados do objeto para autorizacao
		mapper.map(objectGerencial, autorizacaoGerencial);
		
		AutorizacaoTO autorizacaoSegundoGerente = new AutorizacaoTO();

		if(autorizacaoTO.getIcTipoAutorizacao().equals(EnumTipoAutorizacao.SEGUNDOGERENTE.getCodigo())) {
			TypedQuery<Object> querySegundoGerente = em.createNamedQuery("Autoriza.buscarPorTipoAprovacaoMatricula",
					Object.class);
			querySegundoGerente.setParameter("icTipoAutorizacao", EnumTipoAutorizacao.SEGUNDOGERENTE.getCodigo());
			querySegundoGerente.setParameter("nuContrato", Long.parseLong(dados.getIdContrato()));
			querySegundoGerente.setMaxResults(1);
			Object objectSegundoGerente = (Object) querySegundoGerente.getSingleResult();

			// passa todos os dados do objeto para autorizacao
			mapper.map(objectSegundoGerente, autorizacaoSegundoGerente);
			
		}

		HistoricoSituacaoTO situacaoContratoTO = new HistoricoSituacaoTO();
		situacaoContratoTO.setTipoSituacao(EnumTipoContrato.CONTRATO.getCodigo().intValue());

		if(dados.getAutorizar().equals(EnumAutorizar.REJEITAR)) {
			situacaoContratoTO.setSituacao(EnumSituacaoContrato.NEGADA.getCodigo().intValue());
		} else if (autorizacaoGerencial != null && autorizacaoSegundoGerente.getNuContrato() != null) { // caso
																				// já
																				// possua
																				// preenchido
																				// as
																				// duas
																				// autorizacao
			if (autorizacaoGerencial.getIcSituacaoAutorizacao().equals(EnumAutorizar.AUTORIZAR.getCodigo())
					&& autorizacaoSegundoGerente.getIcSituacaoAutorizacao().equals(EnumAutorizar.AUTORIZAR.getCodigo())) {
				situacaoContratoTO.setSituacao(EnumSituacaoContrato.AUTORIZADO.getCodigo().intValue());

			} else if (dados.getJustificativa() != "" && dados.getJustificativa() != null) {
				situacaoContratoTO.setSituacao(EnumSituacaoContrato.NEGADA.getCodigo().intValue());
			} else {
				situacaoContratoTO.setSituacao(EnumSituacaoContrato.AUTORIZADO.getCodigo().intValue());
			}

		} else if(autorizacaoGerencial != null && autorizacaoSegundoGerente.getNuContrato() == null) {
			situacaoContratoTO.setSituacao(EnumSituacaoContrato.AGUARDANDO_AUTORIZACAO_ALCADA.getCodigo().intValue());
		} else if(autorizacaoGerencial == null && autorizacaoSegundoGerente.getNuContrato() != null) {
			situacaoContratoTO.setSituacao(EnumSituacaoContrato.APROVADA_CONFORMIDADE.getCodigo().intValue());
		} else { // caso nao tenha sido preenchido nenhuma aprovacao
			situacaoContratoTO.setSituacao(EnumSituacaoContrato.AGUARDANDO_AUTORIZACOES.getCodigo().intValue());
		}
		return situacaoContratoTO;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AutorizacaoContrato> buscaAutorizacaoPorContrato(Long idContrato) {

		/**
		 * @queryGerencial Responsavel por executar a query buscando dados
		 *                 através do {idContrato, TipoAutorizacao = GERENCIAL)
		 */
		Query queryGerencial = em.createNamedQuery("Autoriza.buscarPorTipoAprovacaoContrato");

		queryGerencial.setParameter("icTipoAutorizacao", EnumTipoAutorizacao.GERENCIAL.getCodigo());
		queryGerencial.setParameter("idContrato", idContrato);
		List<AutorizacaoTO> autorizacaoGerencialTO = queryGerencial.getResultList();
		List<AutorizacaoContrato> listAutorizacaoGerencial = new ArrayList<AutorizacaoContrato>();

		/**
		 * Adiciona dados do retorno da Query ao Objeto AutorizacaoContrato,
		 * pelo qual, será retornado para tela junto ao Autorizacao Conformidade
		 */
		for (AutorizacaoTO autorizacaoTO : autorizacaoGerencialTO) {
			AutorizacaoContrato autorizacaoContrato = new AutorizacaoContrato();
			
			
			if(autorizacaoTO.getSituacaoContrato().getSituacao().toString().equals(EnumSituacaoContrato.NEGADA.getCodigo().toString())) { 
				if(autorizacaoTO.getIcTipoAutorizacao().equals(EnumTipoAutorizacao.SEGUNDOGERENTE.getCodigo())) {
					autorizacaoContrato.setDescSituacao(EnumSituacaoContrato.NEGADA_CONFORMIDADE.getDescricao());
				} else {
					autorizacaoContrato.setDescSituacao(EnumSituacaoContrato.NEGADA_GERENCIAL.getDescricao());
				}
			} else if(autorizacaoTO.getSituacaoContrato().getSituacao().toString().equals(EnumSituacaoContrato.AUTORIZADO.getCodigo().toString())) {
				autorizacaoContrato.setDescSituacao(EnumSituacaoContrato.APROVADA_CONFORMIDADE.getDescricao());
			} else if(autorizacaoTO.getSituacaoContrato().getSituacao().toString().equals(EnumSituacaoContrato.AGUARDANDO_AUTORIZACAO_ALCADA.getCodigo().toString())){
				autorizacaoContrato.setDescSituacao(EnumSituacaoContrato.APROVADA_GERENCIAL.getDescricao());
			} else {
				autorizacaoContrato.setDescSituacao(EnumSituacaoContrato.valueById(autorizacaoTO.getSituacaoContrato().getSituacao()));
			}

			autorizacaoContrato.setDtSituacao(autorizacaoTO.getTsAutorizacao());
			autorizacaoContrato.setUsuario(autorizacaoTO.getCoAutorizacao());
			autorizacaoContrato.setTipoAutorizacao(autorizacaoTO.getIcTipoAutorizacao());
			autorizacaoContrato.setCodigoSituacao(autorizacaoTO.getIcSituacaoAutorizacao());
			autorizacaoContrato.setNuSituacaoContrato(autorizacaoTO.getSituacaoContrato().getId());
			listAutorizacaoGerencial.add(autorizacaoContrato);
		}

		/**
		 * @queryGerencial Responsavel por executar a query buscando dados
		 *                 através do {idContrato, TipoAutorizacao =
		 *                 CONFORMIDADE)
		 */
		Query querySegundoGerente = em.createNamedQuery("Autoriza.buscarPorTipoAprovacaoContrato");

		querySegundoGerente.setParameter("icTipoAutorizacao", EnumTipoAutorizacao.SEGUNDOGERENTE.getCodigo());
		querySegundoGerente.setParameter("idContrato", idContrato);
		List<AutorizacaoTO> autorizacaoConformidadeTO = querySegundoGerente.getResultList();
		List<AutorizacaoContrato> listAutorizacaoSGerente = new ArrayList<AutorizacaoContrato>();

		/**
		 * Adiciona dados do retorno da Query ao Objeto AutorizacaoContrato,
		 * pelo qual, será retornado para tela
		 */
		for (AutorizacaoTO autorizacaoSegundoGerente : autorizacaoConformidadeTO) {
			AutorizacaoContrato autorizacaoContratoConform = new AutorizacaoContrato();
			autorizacaoContratoConform
					.setDescSituacao(EnumSituacaoContrato.valueById(autorizacaoSegundoGerente.getSituacaoContrato().getSituacao()));
			autorizacaoContratoConform.setDtSituacao(autorizacaoSegundoGerente.getTsAutorizacao());
			autorizacaoContratoConform.setUsuario(autorizacaoSegundoGerente.getCoAutorizacao());
			autorizacaoContratoConform.setTipoAutorizacao(autorizacaoSegundoGerente.getIcTipoAutorizacao());
			autorizacaoContratoConform.setCodigoSituacao(autorizacaoSegundoGerente.getIcSituacaoAutorizacao());
			autorizacaoContratoConform.setNuSituacaoContrato(autorizacaoSegundoGerente.getSituacaoContrato().getId());
			listAutorizacaoSGerente.add(autorizacaoContratoConform);
		}

		/**
		 * @List listConformidadeGerencial Responsavel por realizar a junção das
		 *       duas listas a cima e retornadas no resultado das query's
		 */
		@SuppressWarnings("rawtypes")
		List listConformidadeGerencial = new ArrayList(listAutorizacaoGerencial);
		listConformidadeGerencial.addAll(listAutorizacaoSGerente);

		return listConformidadeGerencial;
	}
	
	@SuppressWarnings("unchecked")
	public List<AutorizacaoTO> buscaAutorizacaoPorSituacao(String situacao, Long nuContrato, Long nuSituacaoContrato) {
		Query query = em.createNamedQuery("Autoriza.buscaAutorizacaoPorSituacao");

		query.setParameter("icSituacaoAutorizacao", situacao);
		query.setParameter("nuContrato", nuContrato);
		query.setParameter("nuSituacaoContrato", nuSituacaoContrato);
		List<AutorizacaoTO> listAutorizacaoTO = query.getResultList();
		
		return listAutorizacaoTO;
	}

	private void atualizaContrato(AutorizarContrato dadosContrato, HistoricoSituacaoTO status, String matricula,
			AutorizacaoTO autorizacaoTO, EnumTipoAutorizacao tipoUsuario) throws ParseException {

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		String dateFormtt = df.format(date);
		Date newDate = df.parse(dateFormtt);
		Timestamp timeStamp = new Timestamp(newDate.getTime());
		BigDecimal idsituacao = (BigDecimal) em.createNativeQuery(HistoricoSituacaoTO.QUERY_BUSCA_ID).getSingleResult();
		
		HistoricoSituacaoTO situacaoContratoTO = new HistoricoSituacaoTO();
		situacaoContratoTO.setId(idsituacao.longValue());
		situacaoContratoTO.setSituacao(status.getSituacao().intValue());
		situacaoContratoTO.setContrato(Long.parseLong(dadosContrato.getIdContrato()));
		situacaoContratoTO.setTipoSituacao(status.getTipoSituacao().intValue());
		situacaoContratoTO.setUsuario(matricula);
		situacaoContratoTO.setData(newDate);
		em.persist(situacaoContratoTO);

		TypedQuery<ContratoTO> queryContrato = em.createNamedQuery("Contrato.atualizaStatus", ContratoTO.class);
		queryContrato.setParameter("numSituacao", status.getSituacao().intValue());
		queryContrato.setParameter("numTipoSituacao", status.getTipoSituacao().intValue());
		queryContrato.setParameter("contratoId", Long.parseLong(dadosContrato.getIdContrato()));
		queryContrato.executeUpdate();

		/**
		 * Busca autorização conforme o tipo de Autorização que foi inserido no
		 * metodo @method salvaAutorizacao()
		 */
		StringBuilder sql = new StringBuilder();
		sql.append(" 		SELECT a FROM br.gov.caixa.ccr.dominio.transicao.AutorizacaoTO a ");
		sql.append("		WHERE a.nuSeqAutorizacao = :nuSeqAutorizacao					 ");
		Query query = em.createQuery(sql.toString());
		query.setParameter("nuSeqAutorizacao", autorizacaoTO.getNuSeqAutorizacao());

		AutorizacaoTO autorizacao = (AutorizacaoTO) query.getSingleResult();
		autorizacao.setSituacaoContrato(situacaoContratoTO);
		autorizacao.setTsAutorizacao(timeStamp);
		em.merge(autorizacao);

		TypedQuery<ContratoTO> qrContrato = em.createNamedQuery("Contrato.buscaPorId", ContratoTO.class);
		qrContrato.setParameter("pNuContrato", Long.parseLong(dadosContrato.getIdContrato()));
		ContratoTO contratoTO = (ContratoTO) qrContrato.getSingleResult(); 
		
		AuditoriaTO auditoriaTO = new AuditoriaTO();
		auditoriaTO.setDhTransacaoAuditada(new Date());
		auditoriaTO.setCoUsuarioAuditado(matricula);
		auditoriaTO.setNuContrato(contratoTO);
		auditoriaTO.setNuTransacaoAuditada(EnumTransacaoAuditada.CONTRATACAO.getCodigo());
		auditoriaTO.setIcTipoAcao(EnumTipoAuditoria.INCLUSAO.getDescricao());
		
		if(dadosContrato.getAutorizar().equals(EnumAutorizar.AUTORIZAR)) {
			auditoriaTO.setNoCampoAuditado(EnumCampoAuditoria.AGAPROVADA.getDescricao());
		} else {
			auditoriaTO.setNoCampoAuditado(EnumCampoAuditoria.AGNEGADA.getDescricao());
		}
			
		if(dadosContrato.getAutorizar().equals(EnumAutorizar.AUTORIZAR)) {
			auditoriaTO.setDeValorAtual(EnumSituacaoContrato.AUTORIZADO.getDescricao());
		} else {
			auditoriaTO.setDeValorAtual(EnumSituacaoContrato.NEGADA.getDescricao());
		}
		
		
					
		em.persist(auditoriaTO);
		em.flush();
		
		
	}

}
