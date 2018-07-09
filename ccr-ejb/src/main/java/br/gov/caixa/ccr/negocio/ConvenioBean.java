package br.gov.caixa.ccr.negocio;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.dozer.DozerBeanMapper;

import br.gov.caixa.arqrefcore.excecao.BusinessException;
import br.gov.caixa.ccr.dominio.Auditoria;
import br.gov.caixa.ccr.dominio.Canal;
import br.gov.caixa.ccr.dominio.Convenente;
import br.gov.caixa.ccr.dominio.Convenio;
import br.gov.caixa.ccr.dominio.ConvenioCNPJVinculado;
import br.gov.caixa.ccr.dominio.ConvenioCanal;
import br.gov.caixa.ccr.dominio.ConvenioSR;
import br.gov.caixa.ccr.dominio.ConvenioUF;
import br.gov.caixa.ccr.dominio.GrupoAverbacao;
import br.gov.caixa.ccr.dominio.GrupoTaxa;
import br.gov.caixa.ccr.dominio.Retorno;
import br.gov.caixa.ccr.dominio.Situacao;
import br.gov.caixa.ccr.dominio.transicao.AuditoriaTO;
import br.gov.caixa.ccr.dominio.transicao.CanalAtendimentoTO;
import br.gov.caixa.ccr.dominio.transicao.ConvenioCNPJVinculadoTO;
import br.gov.caixa.ccr.dominio.transicao.ConvenioCanalPK;
import br.gov.caixa.ccr.dominio.transicao.ConvenioCanalTO;
import br.gov.caixa.ccr.dominio.transicao.ConvenioSrPK;
import br.gov.caixa.ccr.dominio.transicao.ConvenioSrTO;
import br.gov.caixa.ccr.dominio.transicao.ConvenioTO;
import br.gov.caixa.ccr.dominio.transicao.ConvenioUfPK;
import br.gov.caixa.ccr.dominio.transicao.ConvenioUfTO;
import br.gov.caixa.ccr.dominio.transicao.GrupoAverbacaoTO;
import br.gov.caixa.ccr.dominio.transicao.GrupoTaxaTO;
import br.gov.caixa.ccr.dominio.transicao.SituacaoTO;
import br.gov.caixa.ccr.dominio.transicao.UnidadeTO;
import br.gov.caixa.ccr.enums.EnumCampoAuditoria;
import br.gov.caixa.ccr.enums.EnumTipoAuditoria;
import br.gov.caixa.ccr.enums.EnumTransacaoAuditada;
import br.gov.caixa.ccr.util.Utilities;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ConvenioBean implements IConvenioBean {

	private static String MA0059 = "MA0059";

	@PersistenceContext
	private EntityManager em;

	@EJB
	private IClienteBean clienteBean;

	@EJB
	private IConvenioCanalBean convenioCanalBean;

	@Inject
	private IConvenioValidacao iConvenioValidacao;

	@SuppressWarnings("unchecked")
	@Override
	public List<Convenio> lista(Integer id, Long cnpj, String nome, Integer situacao, Integer sr, Integer agencia,
			List<Long> canais) throws Exception {

		if (agencia != null && agencia > 0) {
			Retorno retorno = iConvenioValidacao.validaAG(Long.valueOf(agencia));
			if (retorno != null) {
				throw new BusinessException(retorno.getIdMsg());
			}
		}

		if (sr != null && sr > 0) {
			Retorno retorno = iConvenioValidacao.validaSR(Long.valueOf(sr));
			if (retorno != null) {
				throw new BusinessException(retorno.getIdMsg());
			}

		}

		TypedQuery<ConvenioTO> query = null;

		// ajuste de consulta - to upper
		if (nome != null && nome != "")
			nome = nome.toUpperCase();

		// CNPJ
		if (cnpj != null && cnpj != 0) {
			// query = em.createNamedQuery("Convenio.consultarPorCNPJ",
			// ConvenioTO.class);
			// query.setParameter("pCNPJ", cnpj);

//			query = em.createNamedQuery("ConvenioCNPJVinculadoTO.consultarPorCNPJ", ConvenioTO.class);
//			query.setParameter(1, String.valueOf(cnpj));
//			query.setParameter(2, String.valueOf(cnpj));
//			query.setParameter(3, String.valueOf(cnpj));
		}

		// ID
		else if ((id != null && id != 0) && (nome == null || (nome != null && nome.isEmpty()))
				&& (situacao == null || (situacao != null && situacao == 0))) {
			query = em.createNamedQuery("Convenio.consultarPorId", ConvenioTO.class);
			query.setParameter("pId", Long.valueOf(id));
			// NOME
		} else if ((nome != null && !nome.isEmpty()) && (situacao == null || (situacao != null && situacao == 0))
				&& (id == null || (id != null && id == 0))) {
			query = em.createNamedQuery("Convenio.consultarPorNome", ConvenioTO.class);
			query.setParameter("pNome", "%" + nome + "%");
			// SITUACAO
		} else if ((situacao != null && situacao != 0) && (nome == null || (nome != null && nome.isEmpty()))
				&& (id == null || (id != null && id == 0))) {
			query = em.createNamedQuery("Convenio.consultarPorSituacao", ConvenioTO.class);
			SituacaoTO situacaoTO = new SituacaoTO();
			situacaoTO.setId(Long.valueOf(situacao));
			situacaoTO.setTipo(Long.valueOf(1));
			query.setParameter("pSituacao", situacaoTO);
		}

		// ID && NOME
		else if ((id != null && id != 0) && ((nome != null && !nome.isEmpty()))
				&& (situacao == null || (situacao != null && situacao == 0))) {
			query = em.createNamedQuery("Convenio.consultarPorNomeAndID", ConvenioTO.class);
			query.setParameter("pNome", "%" + nome + "%");
			query.setParameter("pId", Long.valueOf(id));
		}

		// SITUACAO && NOME
		else if ((id == null || (id != null && id == 0)) && ((nome != null && !nome.isEmpty()))
				&& (situacao != null && situacao != 0)) {
			query = em.createNamedQuery("Convenio.consultarPorSituacaoAndNome", ConvenioTO.class);
			query.setParameter("pNome", "%" + nome + "%");

			SituacaoTO situacaoTO = new SituacaoTO();
			situacaoTO.setId(Long.valueOf(situacao));
			situacaoTO.setTipo(Long.valueOf(1));
			query.setParameter("pSituacao", situacaoTO);
		}

		// ID && SITUACAO
		else if ((id != null && id != 0) && (nome == null || (nome != null && nome.isEmpty()))
				&& (situacao != null && situacao != 0)) {
			query = em.createNamedQuery("Convenio.consultarPorSituaçãoAndID", ConvenioTO.class);
			query.setParameter("pId", Long.valueOf(id));

			SituacaoTO situacaoTO = new SituacaoTO();
			situacaoTO.setId(Long.valueOf(situacao));
			situacaoTO.setTipo(Long.valueOf(1));
			query.setParameter("pSituacao", situacaoTO);
		}

		// ID && SITUACAO && NOME
		else if ((id != null && id != 0) && (nome != null && !nome.isEmpty()) && (situacao != null && situacao != 0)) {
			query = em.createNamedQuery("Convenio.consultarPorSituacaoAndNomeAndID", ConvenioTO.class);
			query.setParameter("pId", Long.valueOf(id));
			query.setParameter("pNome", "%" + nome + "%");

			SituacaoTO situacaoTO = new SituacaoTO();
			situacaoTO.setId(Long.valueOf(situacao));
			situacaoTO.setTipo(Long.valueOf(1));
			query.setParameter("pSituacao", situacaoTO);
		}

		// SR
		else if ((sr != null && sr != 0) && (agencia == null || (agencia != null && agencia == 0))) {
			query = em.createNamedQuery("Convenio.consultarPorSR", ConvenioTO.class);
			query.setParameter("pSR", Long.valueOf(sr));
		}

		// AGENCIA
		else if (agencia != null && agencia != 0 && (sr == null || (sr != null && sr == 0))) {
			query = em.createNamedQuery("Convenio.consultarPorAgencia", ConvenioTO.class);
			query.setParameter("pAgencia", Long.valueOf(agencia));
		}

		// AGENCIA && SR
		else if ((sr != null && sr != 0) && (agencia != null && agencia != 0)) {
			query = em.createNamedQuery("Convenio.consultarPorAgenciaAndSR", ConvenioTO.class);
			query.setParameter("pSR", Long.valueOf(sr));
			query.setParameter("pAgencia", Long.valueOf(agencia));
		}

		else {
			// FE12. Nenhum filtro informado - MA0069
			/**
			 * o sistema verifica que nenhuma informação do filtro foi
			 * informada,
			 */
			//
			// query = em.createNamedQuery("Convenio.consultarAll",
			// ConvenioTO.class);
			throw new BusinessException("MA0069");

		}

		// cast return query
//		List<ConvenioTO> listConvenioTO = (List<ConvenioTO>) query.getResultList();
		List<ConvenioTO> listConvenioTO = new ArrayList<ConvenioTO>();
		ConvenioTO convenio =  em.find(ConvenioTO.class, 2L);
		listConvenioTO.add(convenio);
		List<Convenio> convenioViewListRetorno = new ArrayList<Convenio>();
		for (ConvenioTO convenioTO : listConvenioTO) {
			// Recupera lista de ConvenioCanal
			Convenio convenioView = convert(convenioTO);

			Calendar dtCriacaoMaisPrazo = Calendar.getInstance();
			Calendar dateNow = Calendar.getInstance();

			/*if(convenioTO.getPrzVigenciaConvenio() != null && convenioTO.getDtCriacaoConvenio() != null) {
				dtCriacaoMaisPrazo.setTime(convenioTO.getDtCriacaoConvenio());
				dtCriacaoMaisPrazo.add(Calendar.DAY_OF_MONTH, convenioTO.getPrzVigenciaConvenio());
				dtCriacaoMaisPrazo.set(Calendar.MILLISECOND, 0);
				dtCriacaoMaisPrazo.set(Calendar.HOUR, 0);
				dtCriacaoMaisPrazo.set(Calendar.MINUTE, 0);
				dtCriacaoMaisPrazo.set(Calendar.SECOND, 0);
				
				dateNow.set(Calendar.MILLISECOND, 0);
				dateNow.set(Calendar.HOUR, 0);
				dateNow.set(Calendar.MINUTE, 0);
				dateNow.set(Calendar.SECOND, 0);
			}*/
			
			/**
			 * Verifica data Vigênte do Convênio
			 * Se o compareTo, retornar 0, significa que as datas são iguais
			 * Se o compareTo, retornar -1, significa que a data de agora é maior que a data de 
			 * criação + prazo do convenio
			 * Ou seja, serão datas vigentes
			 * 
			 */
			/*if(dateNow.getTime().compareTo(dtCriacaoMaisPrazo.getTime()) == 0 || dateNow.getTime().compareTo(dtCriacaoMaisPrazo.getTime()) < 0) {
				convenioView.setVigente(true);
			} else {
				convenioView.setVigente(false);
			}*/
			
			

			if (canais != null && !canais.isEmpty()) {

				convenioView.setCanais(convenioCanalBean.lista(convenioTO.getId(), canais));

				if (convenioView.getCanais().isEmpty())
					continue;
			}

			// busca LISTA ConvenioCNPJVinculadoTO.consultarPorConvenio
			TypedQuery<ConvenioCNPJVinculadoTO> queryConvenioCNPJVinculadoTO = null;
			queryConvenioCNPJVinculadoTO = em.createNamedQuery("ConvenioCNPJVinculadoTO.consultarPorConvenio",
					ConvenioCNPJVinculadoTO.class);
			queryConvenioCNPJVinculadoTO.setParameter(1, convenioView.getId());
			List<ConvenioCNPJVinculadoTO> listConvenioCNPJVinculadoTO = (List<ConvenioCNPJVinculadoTO>) queryConvenioCNPJVinculadoTO
					.getResultList();

			List<ConvenioCNPJVinculado> convenioCNPJVinculadoLst = new ArrayList<>();

			for (ConvenioCNPJVinculadoTO convenioVinc : listConvenioCNPJVinculadoTO) {
				ConvenioCNPJVinculado cVinculado = new ConvenioCNPJVinculado();
				cVinculado.setNuCNPJ(convenioVinc.getNuCNPJ());
				cVinculado.setNuConvenio(String.valueOf(convenioVinc.getNuConvenio().getId()));
				convenioCNPJVinculadoLst.add(cVinculado);
			}
			convenioView.setConvenioCNPJVinculado(convenioCNPJVinculadoLst);
			convenioViewListRetorno.add(convenioView);
		}

		// Utilities.copyListClassFromTo(convenioViewListRetorno,
		// Convenio.class, listConvenioTO);

		return convenioViewListRetorno;
	}

	@Override
	public Object consultarSR(Long id) throws Exception {
		Retorno retorno = iConvenioValidacao.validaSR(id);
		Convenio convenio = null;
		if (retorno.getTipoRetorno().equals(Retorno.ERRO_NEGOCIAL)) {// sr
																		// existe
			convenio = new Convenio(retorno.getCodigoRetorno(), retorno.getMensagemRetorno(), retorno.getTipoRetorno(),
					retorno.getIdMsg());
			return convenio;
		} else {// sr n existe
			convenio = new Convenio(id, "", Retorno.SUCESSO, "MA002");
			return convenio;

		}
	}

	@Override
	public Convenio consultar(Long id, String userlog) throws Exception {
		Convenio convenio = new Convenio();
		ConvenioTO convenioTO = new ConvenioTO();

		if (id != 0) {
			convenioTO = em.find(ConvenioTO.class, id);

			if (convenioTO == null) {
				convenio.setAll(-1L, "Nenhum registro encontrado para o filtro informado!", Retorno.ERRO_NEGOCIAL);
				return convenio;
			}
		}

		// cast convenio
		// Utilities.copyAttributesFromTo(convenio, convenioTO);
		convenio = convert(convenioTO);

		TypedQuery<ConvenioCanalTO> query = null;
		query = em.createNamedQuery("ConvenioCanal.consultAll", ConvenioCanalTO.class);

		List<ConvenioCanalTO> listConvenioTO = (List<ConvenioCanalTO>) query.getResultList();

		// canais retorno
		List<ConvenioCanal> canaisLst = new ArrayList<ConvenioCanal>();

		// load convenio/canal
		for (ConvenioCanalTO convenioCanalTO : listConvenioTO) {
			if (convenioCanalTO.getId().getIdConvenio() == convenio.getId().longValue()) {
				ConvenioCanal convenioCanal = new ConvenioCanal();

				convenioCanal.setIndAutorizaMargemRenovacao(convenioCanalTO.getIndAutorizaMargemRenovacao());
				convenioCanal.setIndPermiteContratacao(convenioCanalTO.getIndPermiteContratacao());
				convenioCanal.setIndPermiteRenovacao(convenioCanalTO.getIndPermiteRenovacao());
				convenioCanal.setPrzMaximoRenovacao(convenioCanalTO.getPrzMaximoRenovacao());
				convenioCanal.setPrzMaximoContratacao(convenioCanalTO.getPrzMaximoContratacao());
				convenioCanal.setIndFaixaJuroRenovacao(convenioCanalTO.getIndFaixaJuroRenovacao());
				convenioCanal.setIndExigeAnuencia(convenioCanalTO.getIndExigeAnuencia());
				convenioCanal.setIndFaixaJuroContratacao(convenioCanalTO.getIndFaixaJuroContratacao());
				convenioCanal.setPrzMinimoContratacao(convenioCanalTO.getPrzMinimoContratacao());
				convenioCanal.setPrzMinimoRenovacao(convenioCanalTO.getPrzMinimoRenovacao());
				convenioCanal.setQtdDiaGarantiaCondicao(convenioCanalTO.getQtdDiaGarantiaCondicao());

//				Long idSituacaoConvenioCanal = convenioCanalTO.getSituacao() != null
//						? convenioCanalTO.getSituacao().getId() : null;
//				convenioCanal.setIndSituacaoConvenioCanal(
//						ConvenioCanalTO.SITUACAO_INATIVO.equals(idSituacaoConvenioCanal) ? "N" : "S");

				convenioCanal.setIndExigeAutorizacaoContrato(convenioCanalTO.getIndAutorizaMargemContrato());
				convenioCanal.setIndAutorizaMargemContrato(convenioCanalTO.getIndAutorizaMargemContrato());

				// get dados canal
				TypedQuery<CanalAtendimentoTO> queryCanalAtendimento = null;
				queryCanalAtendimento = em.createNamedQuery("CanalAtendimentoTO.consultarCanal",
						CanalAtendimentoTO.class);
				queryCanalAtendimento.setParameter("pID",
						Long.valueOf(convenioCanalTO.getId().getIdCanalAtendimento()));
				List<CanalAtendimentoTO> lstCanalAtendimentoTO = (List<CanalAtendimentoTO>) queryCanalAtendimento
						.getResultList();

				for (CanalAtendimentoTO canalAtendimentoTO : lstCanalAtendimentoTO) {
					Canal canal = new Canal();
					canal.setId(canalAtendimentoTO.getId());
					canal.setNome(canalAtendimentoTO.getNome());
					convenioCanal.setCanal(canal);
				}

				canaisLst.add(convenioCanal);
			}
		}
		convenio.setCanais(canaisLst);

		/*
		 * //dados cliente - convenente CamposRetornados dadosCliente =
		 * clienteBean.consultar(String.valueOf(convenio.getCnpjConvenente()),
		 * userlog); Convenente convenente = new Convenente();
		 * 
		 * if(dadosCliente != null && dadosCliente.getRazaoSocial() != null &&
		 * dadosCliente.getRazaoSocial().getRazaoSocial() != null){
		 * convenente.setRazaoSocial(dadosCliente.getRazaoSocial().
		 * getRazaoSocial()); }else{ convenente.setRazaoSocial(""); }
		 * 
		 * convenente.setCnpj(convenio.getCnpjConvenente());
		 * dadosCliente.getProdutoCaixa(); for (EnderecoNacional
		 * enderecoNacional : dadosCliente.getEnderecoNacional()) {
		 * convenente.setCidade(enderecoNacional.getNomeMunicipio());
		 * convenente.setLogradouro(enderecoNacional.getLogradouro());
		 * convenente.setUf(enderecoNacional.getUf());
		 * convenente.setNumero(enderecoNacional.getNumero());
		 * if(enderecoNacional.getCep() != null )
		 * convenente.setCep(Integer.valueOf(enderecoNacional.getCep()));
		 * convenente.setComplemento(enderecoNacional.getComplemento()); }
		 */
		Convenente convenente = new Convenente();
		convenio.setConvenente(convenente);

		// radioAbrangencia_1 - Nacional

		if (convenio.getIndAbrangencia() == 2) {
			// radioAbrangencia_2 - UF

			ConvenioUfPK convenioUfPK = new ConvenioUfPK();
			convenioUfPK.setIdConvenio(Long.valueOf(convenio.getId()));

			TypedQuery<ConvenioUfTO> queryConvenioUfTO = em.createNamedQuery("ConvenioUfTO.buscarPorId",
					ConvenioUfTO.class);
			queryConvenioUfTO.setParameter(1, convenio.getId());
			List<ConvenioUfTO> lstConvenioUfTO = (List<ConvenioUfTO>) queryConvenioUfTO.getResultList();

			List<ConvenioUF> lstConvenioUf = new ArrayList<ConvenioUF>();

			for (ConvenioUfTO convenioUfTO : lstConvenioUfTO) {

				if (convenioUfTO.getId().getIdConvenio().equals(convenio.getId())) {
					ConvenioUF convenioUF = new ConvenioUF();

					convenioUF.setCodUsuarioExclusao(convenioUfTO.getCodUsuarioExclusao() != null
							? Long.valueOf(convenioUfTO.getCodUsuarioExclusao()) : null);
					convenioUF.setCodUsuarioInclusao(convenioUfTO.getCodUsuarioInclusao() != null
							? Long.valueOf(convenioUfTO.getCodUsuarioInclusao()) : null);
					convenioUF.setDtExclusaoUF(convenioUfTO.getDataExclusaoUf());
					convenioUF.setDtInclusaoUF(convenioUfTO.getDataInclusaoUf());
					convenioUF.setNumConvenio(convenioUfTO.getId().getIdConvenio().intValue());
					convenioUF.setSgUF(convenioUfTO.getId().getUf());
					lstConvenioUf.add(convenioUF);
				}

			}
			// VinculoUnidadeTO
			convenio.setAbrangenciaUF(lstConvenioUf);

		} else if (convenio.getIndAbrangencia() == 3) {
			// radioAbrangencia_3 - SR

			TypedQuery<ConvenioSrTO> queryConvenioSrTO = em.createNamedQuery("ConvenioSrTO.buscarPorId",
					ConvenioSrTO.class);
			queryConvenioSrTO.setParameter(1, convenio.getId());
			List<ConvenioSrTO> lstConvenioSrTO = (List<ConvenioSrTO>) queryConvenioSrTO.getResultList();

			List<ConvenioSR> lstConvenioSR = new ArrayList<ConvenioSR>();
			for (ConvenioSrTO convenioSrTO : lstConvenioSrTO) {

				if (convenioSrTO.getId().getIdConvenio().equals(convenio.getId())) {
					ConvenioSR convenioSR = new ConvenioSR();

					convenioSR.setDtExclusaoSR(convenioSrTO.getDataExclusaoSr());
					convenioSR.setDtInclusaoSR(convenioSrTO.getDataInclusaoSr());
					convenioSR.setCodUsuarioExclusao(convenioSrTO.getCodUsuarioExclusao() != null
							? Long.valueOf(convenioSrTO.getCodUsuarioExclusao()) : null);
					convenioSR.setCodUsuarioInclusao(convenioSrTO.getCodUsuarioInclusao() != null
							? Long.valueOf(convenioSrTO.getCodUsuarioInclusao()) : null);
					convenioSR.setNumConvenio(convenioSrTO.getId().getIdConvenio().intValue());
					convenioSR.setUnidade(convenioSrTO.getId().getIdUnidade() != null
							? String.valueOf(convenioSrTO.getId().getIdUnidade()) : null);
					convenioSR.setNumConvenio(convenioSrTO.getId().getIdConvenio() != null
							? convenioSrTO.getId().getIdConvenio().intValue() : null);
					convenioSR.setNumNatural(convenioSrTO.getId().getIdNatural() != null
							? convenioSrTO.getId().getIdNatural().intValue() : null);
					lstConvenioSR.add(convenioSR);
				}

			}

			convenio.setAbrangenciaSR(lstConvenioSR);

		}

		// busca LISTA ConvenioCNPJVinculadoTO.consultarPorConvenio
		TypedQuery<ConvenioCNPJVinculadoTO> queryConvenioCNPJVinculadoTO = null;
		queryConvenioCNPJVinculadoTO = em.createNamedQuery("ConvenioCNPJVinculadoTO.consultarPorConvenio",
				ConvenioCNPJVinculadoTO.class);
		queryConvenioCNPJVinculadoTO.setParameter(1, convenio.getId());
		List<ConvenioCNPJVinculadoTO> listConvenioCNPJVinculadoTO = (List<ConvenioCNPJVinculadoTO>) queryConvenioCNPJVinculadoTO
				.getResultList();

		List<ConvenioCNPJVinculado> convenioCNPJVinculadoLst = new ArrayList<>();

		for (ConvenioCNPJVinculadoTO convenioVinc : listConvenioCNPJVinculadoTO) {
			ConvenioCNPJVinculado cVinculado = new ConvenioCNPJVinculado();
			cVinculado.setNuCNPJ(convenioVinc.getNuCNPJ());
			cVinculado.setNuConvenio(String.valueOf(convenioVinc.getNuConvenio().getId()));
			convenioCNPJVinculadoLst.add(cVinculado);
		}

		convenio.setConvenioCNPJVinculado(convenioCNPJVinculadoLst);

		return convenio;
	}

	@Override
	public ConvenioCanal consultarConvenioCanal(Long id, Long canal) throws Exception {

		ConvenioCanal convenioCanal = new ConvenioCanal();

		ConvenioCanalTO convenioCanalTO = new ConvenioCanalTO();
		ConvenioCanalPK convCanalId = new ConvenioCanalPK(id, canal);
		convenioCanalTO.setId(convCanalId);

		ConvenioCanalTO to = em.find(ConvenioCanalTO.class, convCanalId);
		Utilities.copyAttributesFromTo(convenioCanal, to);

		return convenioCanal;
	}

	private ConvenioTO convert(Convenio convenioView) throws Exception {
		ConvenioTO convenioTO = new ConvenioTO();
		Utilities.copyAttributesFromTo(convenioTO, convenioView);
		convenioTO.setCnpjConvenente(convenioView.getConvenente().getCnpj());
		// convenioTO.setGrupoTaxa(new
		// GrupoTaxaTO(iGrupobean.buscarPorCodigo(convenioView.getGrupo().getId().intValue()),
		// convenioView.getGrupo().getNome()));
		convenioTO.setGrupoTaxa(new GrupoTaxaTO(convenioView.getGrupo().getId(), convenioView.getGrupo().getNome()));
		convenioTO.setGrupoAverbacao(new GrupoAverbacaoTO(convenioView.getGrupoAverbacao().getId(),
				convenioView.getGrupoAverbacao().getNome()));
		convenioTO
				.setSituacao(new SituacaoTO(convenioView.getSituacao().getId(), convenioView.getSituacao().getTipo()));

		return convenioTO;
	}

	private static Convenio convert(ConvenioTO convenioTO)
			throws IllegalArgumentException, IllegalAccessException, ParseException {
		Convenio convenioView = new Convenio();
		Utilities.copyAttributesFromTo(convenioView, convenioTO);

		convenioView.setCnpjConvenente(convenioTO.getCnpjConvenente());

		if (convenioTO.getGrupoTaxa() != null)
			convenioView
					.setGrupo(new GrupoTaxa(convenioTO.getGrupoTaxa().getId(), convenioTO.getGrupoTaxa().getNome()));

		if (convenioTO.getGrupoAverbacao() != null) {
			convenioView.setGrupoAverbacao(new GrupoAverbacao(convenioTO.getGrupoAverbacao().getId(),
					convenioTO.getGrupoAverbacao().getNome()));
		} else {
			convenioView.setGrupoAverbacao(new GrupoAverbacao(0L, ""));

		}

		// if(convenioTO.getSituacao() != null)
		convenioView.setSituacao(new Situacao(convenioTO.getSituacao().getId(), convenioTO.getSituacao().getTipo(),
				convenioTO.getSituacao().getDescricao()));

		return convenioView;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Retorno salvar(Convenio convenio, String usuario, boolean isUserInRole) throws Exception {

		EnumTipoAuditoria tipoAuditoria = null;
		Convenio convenioAuditoria = null;
		List<ConvenioCNPJVinculadoTO> listConvenioCNPJVinculadoTOInicial = new ArrayList<ConvenioCNPJVinculadoTO>();
		List<ConvenioSrTO> lstConvenioSrTOInicial = new ArrayList<ConvenioSrTO>();
		List<ConvenioUfTO> lstConvenioUfTOInicial = new ArrayList<ConvenioUfTO>();

		if (convenio.getId() != null && convenio.getId() != 0) {

			TypedQuery<ConvenioUfTO> queryConvenioUfTO = em.createNamedQuery("ConvenioUfTO.buscarPorId",
					ConvenioUfTO.class);
			queryConvenioUfTO.setParameter(1, convenio.getId());
			lstConvenioUfTOInicial = (List<ConvenioUfTO>) queryConvenioUfTO.getResultList();

			TypedQuery<ConvenioSrTO> queryConvenioSrTO = em.createNamedQuery("ConvenioSrTO.buscarPorId",
					ConvenioSrTO.class);
			queryConvenioSrTO.setParameter(1, convenio.getId());
			lstConvenioSrTOInicial = (List<ConvenioSrTO>) queryConvenioSrTO.getResultList();

			TypedQuery<ConvenioCNPJVinculadoTO> queryConvenioCNPJVinculadoTO = null;
			queryConvenioCNPJVinculadoTO = em.createNamedQuery("ConvenioCNPJVinculadoTO.consultarPorConvenio",
					ConvenioCNPJVinculadoTO.class);
			queryConvenioCNPJVinculadoTO.setParameter(1, convenio.getId());
			listConvenioCNPJVinculadoTOInicial = (List<ConvenioCNPJVinculadoTO>) queryConvenioCNPJVinculadoTO
					.getResultList();

			convenioAuditoria = consultar(convenio.getId(), usuario);
			tipoAuditoria = EnumTipoAuditoria.ALTERACAO;
		} else {
			tipoAuditoria = EnumTipoAuditoria.INCLUSAO;
		}

		/* VALIDACAO */
		Retorno retorno = iConvenioValidacao.validar(convenio, usuario, isUserInRole);

		if (retorno != null) {
			throw new BusinessException(retorno.getIdMsg());
		}

		Situacao sitAux = convenio.getSituacao();
		sitAux.setTipo(1l);
		convenio.setSituacao(sitAux);

		/* consulta num natural */
		Long nuNaturalByNumNaturalPvCobranca = getNuNatutalPorUnidade(
				convenio.getNumPvCobranca() != null ? Long.valueOf(convenio.getNumPvCobranca()) : null);
		convenio.setNumNaturalPvCobranca(nuNaturalByNumNaturalPvCobranca);
		if (nuNaturalByNumNaturalPvCobranca == null
				|| (nuNaturalByNumNaturalPvCobranca != null && nuNaturalByNumNaturalPvCobranca < 1)) {
			throw new BusinessException(MA0059);
		}

		Long nuNaturalByNumNaturalPvResponsavel = getNuNatutalPorUnidade(
				convenio.getNumPvResponsavel() != null ? Long.valueOf(convenio.getNumPvResponsavel()) : null);
		convenio.setNumNaturalPvResponsavel(nuNaturalByNumNaturalPvResponsavel);
		if (nuNaturalByNumNaturalPvResponsavel == null
				|| (nuNaturalByNumNaturalPvResponsavel != null && nuNaturalByNumNaturalPvResponsavel < 1)) {
			throw new BusinessException(MA0059);
		}

		Long nuNaturalNumNaturalSprnaCobranca = getNuNatutalPorUnidade(
				convenio.getNumSprnaCobranca() != null ? Long.valueOf(convenio.getNumSprnaCobranca()) : null);
		convenio.setNumNaturalSprnaCobranca(nuNaturalNumNaturalSprnaCobranca);
		if (nuNaturalNumNaturalSprnaCobranca == null
				|| (nuNaturalNumNaturalSprnaCobranca != null && nuNaturalNumNaturalSprnaCobranca < 1)) {
			throw new BusinessException(MA0059);
		}

		Long nuNaturalNumNaturalSprnaResponsavel = getNuNatutalPorUnidade(
				convenio.getNumSprnaResponsavel() != null ? Long.valueOf(convenio.getNumSprnaResponsavel()) : null);
		convenio.setNumNaturalSprnaResponsavel(nuNaturalNumNaturalSprnaResponsavel);
		if (nuNaturalNumNaturalSprnaResponsavel == null
				|| (nuNaturalNumNaturalSprnaResponsavel != null && nuNaturalNumNaturalSprnaResponsavel < 1)) {
			throw new BusinessException(MA0059);
		}

		/* CONVERT */
		ConvenioTO convenioTO = convert(convenio);

		/* GET NEXT ID */
		if (convenioTO.getId() == null || (convenioTO.getId() != null && convenioTO.getId() == 0))
			convenioTO.setId(getNextIdConvenio().longValueExact());

		convenioTO.setCodDvConvenio(Long.valueOf(getDVConvenio(convenioTO.getId().toString())));
//		convenioTO.setDtCriacaoConvenio(new Date());

		// GRAVA CONVENIO
		em.merge(convenioTO);
		em.flush();

		// excluir ConvenioVinculados
		Query query = em.createNamedQuery("ConvenioCNPJVinculadoTO.excluiConveniosVinculados",
				ConvenioCNPJVinculadoTO.class);
		query.setParameter("pConvenio", convenio.getId());
		query.executeUpdate();

		// ConvenioCNPJVinculado -- CONVERTER/GRAVAR em TO
		for (ConvenioCNPJVinculado convenioCNPJVinculado : convenio.getConvenioCNPJVinculado()) {
			ConvenioCNPJVinculadoTO convenioCNPJVinculadoTO = new ConvenioCNPJVinculadoTO();
			convenioCNPJVinculadoTO.setNuCNPJ(convenioCNPJVinculado.getNuCNPJ());
			convenioCNPJVinculadoTO.setNuConvenio(convenioTO);

			// GRAVA ConvenioCNPJVinculado
			em.merge(convenioCNPJVinculadoTO);
			em.flush();

		}

		// excluir SR vinculados
		Query queryExcluirSr = em.createNamedQuery("ConvenioSrTO.excluiSrTO", ConvenioSrTO.class);
		queryExcluirSr.setParameter("pConvenio", convenio.getId());
		queryExcluirSr.executeUpdate();

		for (ConvenioSR convenioSR : convenio.getAbrangenciaSR()) {
			ConvenioSrTO convenioSrTO = new ConvenioSrTO();
			Utilities.copyAttributesFromTo(convenioSR, convenioSrTO);

			ConvenioSrPK convenioSrPK = new ConvenioSrPK();
			convenioSrPK.setIdConvenio(convenioTO.getId());
			convenioSrPK.setIdUnidade(convenioSR.getUnidade() != null ? Long.valueOf(convenioSR.getUnidade()) : null);

			Long nuNaturalByNuUnidade = getNuNatutalPorUnidade(
					convenioSR.getUnidade() != null ? Long.valueOf(convenioSR.getUnidade()) : null);

			/* consulta num natural */
			if (nuNaturalByNuUnidade == null || (nuNaturalByNuUnidade != null && nuNaturalByNuUnidade < 1)) {
				retorno = new Retorno(1L, "", Retorno.ERRO_NEGOCIAL, MA0059);
				throw new BusinessException(MA0059);
			}

			// long natural = 1//teste;
			convenioSrPK.setIdNatural(nuNaturalByNuUnidade);
			convenioSrTO.setId(convenioSrPK);

			em.clear();
			em.persist(convenioSrTO);
			em.flush();
		}

		// excluir UF vinculados
		Query queryExcluirUF = em.createNamedQuery("ConvenioUfTO.excluiUFTO", ConvenioUfTO.class);
		queryExcluirUF.setParameter("pConvenio", convenio.getId());
		queryExcluirUF.executeUpdate();

		for (ConvenioUF convenioUF : convenio.getAbrangenciaUF()) {
			ConvenioUfTO convenioUfTO = new ConvenioUfTO();
			Utilities.copyAttributesFromTo(convenioUF, convenioUfTO);

			ConvenioUfPK convenioUfPK = new ConvenioUfPK();

			convenioUfPK.setIdConvenio(convenioTO.getId().longValue());
			convenioUfPK.setUf(convenioUF.getSgUF());
			convenioUfTO.setId(convenioUfPK);

			em.clear();
			em.persist(convenioUfTO);
			em.flush();
		}

		/**
		 * Esta lista foi criada para adicionar todos aqueles Canais que não
		 * foram excluidos.
		 */
		List<ConvenioCanalTO> listConvenioCanalGravados = new ArrayList<ConvenioCanalTO>();

		/**
		 * Esta lista foi criada para adicionar todos aqueles Canais que foram
		 * excluidos.
		 */
		List<ConvenioCanalTO> listConvenioCanalExcluidos = new ArrayList<ConvenioCanalTO>();

		List<ConvenioCanalTO> listConvenioCanal = new ArrayList<ConvenioCanalTO>();

		if (convenio.getId() > 0) {
			// exclui todos os canais vinculados ao convenio
			Query queryConvVinc = em.createNamedQuery("ConvenioCanalTO.consultarById", ConvenioCanalTO.class);
			queryConvVinc.setParameter("pConvenio", convenio.getId());
			listConvenioCanal = (List<ConvenioCanalTO>) queryConvVinc.getResultList();

		}

		if (convenio.getCanais().size() == 0 && listConvenioCanal.size() > 0) {
			for (ConvenioCanalTO convenioCanalTO : listConvenioCanal) {
				listConvenioCanalExcluidos.add(convenioCanalTO);
			}
		} else {
			// insere informacoes canais convenio
			for (ConvenioCanal convenioCanal : convenio.getCanais()) {

				ConvenioCanalTO convenioCanalTO = new ConvenioCanalTO();
				Utilities.copyAttributesFromTo(convenioCanalTO, convenioCanal);
				ConvenioCanalPK id = new ConvenioCanalPK(convenioCanal.getIdConvenio(),
						convenioCanal.getCanal().getId());
				convenioCanalTO.setId(id);

				String indSituacaoConvenioCanal = convenioCanal.getIndSituacaoConvenioCanal();
				SituacaoTO situacao = new SituacaoTO("S".equalsIgnoreCase(indSituacaoConvenioCanal)
						? ConvenioCanalTO.SITUACAO_ATIVO : ConvenioCanalTO.SITUACAO_INATIVO,
						ConvenioCanalTO.TIPO_SITUACAO_CONVENIO_CANAL);
//				convenioCanalTO.setSituacao(situacao);

				if (listConvenioCanal.size() > 0) {
					for (ConvenioCanalTO convenioCanalBanco : listConvenioCanal) {
						if (convenioCanalTO.getId().getIdCanalAtendimento() != convenioCanalBanco.getId()
								.getIdCanalAtendimento()) {
							listConvenioCanalExcluidos.add(convenioCanalBanco);
						}
					}
					listConvenioCanalGravados.add(convenioCanalTO);
				} else {
					listConvenioCanalGravados.add(convenioCanalTO);
				}

			}
		}
		for (ConvenioCanalTO convenioCanalTO : listConvenioCanalExcluidos) {
			Query queryContrConvCanal = em.createNamedQuery("Contrato.buscaContratoPorConvenioECanal", Object.class);
			queryContrConvCanal.setParameter("nuConvenio", convenio.getId());
			queryContrConvCanal.setParameter("nuCanal", convenioCanalTO.getId().getIdCanalAtendimento());
			List<Object> objectVincCanal = new ArrayList<Object>();
			objectVincCanal = (List<Object>) queryContrConvCanal.getResultList();

			if (objectVincCanal.size() == 0) {
				// exclui todos os canais vinculados ao convenio
				try {
					Query queryExcCanal = em.createNamedQuery("ConvenioCanalTO.excluiCanaisVinculados",
							ConvenioCanalTO.class);
					queryExcCanal.setParameter("pConvenio", convenio.getId());
					queryExcCanal.setParameter("pCanal", convenioCanalTO.getId().getIdCanalAtendimento());
					queryExcCanal.executeUpdate();
				} catch (Exception e) {
					// Este canal não pode ser excluido, pois possui registro
					// vinculado em alguma tabela.
					throw new BusinessException("MA0010");
				}
			} else {
				// Este canal não pode ser excluido, pois possui registro
				// vinculado em alguma tabela.
				throw new BusinessException("MA0010");
			}

		}

		for (ConvenioCanalTO convenioCanalAdd : listConvenioCanalGravados) {
			if (convenioCanalAdd.getId().getIdCanalAtendimento() > 0) {
				ConvenioCanalPK id = new ConvenioCanalPK(convenioTO.getId(),
						convenioCanalAdd.getId().getIdCanalAtendimento());
				convenioCanalAdd.setId(id);

				em.clear();
				em.merge(convenioCanalAdd);
				em.flush();
			}
		}

		// exclui todos os canais vinculados ao convenio
		// Query queryConvVinc =
		// em.createNamedQuery("ConvenioCanalTO.excluiCanaisVinculados",
		// ConvenioCanalTO.class);
		// queryConvVinc.setParameter("pConvenio", convenio.getId());
		// queryConvVinc.executeUpdate();
		//
		// // insere informacoes canais convenio
		// for(ConvenioCanal convenioCanal: convenio.getCanais()) {
		// ConvenioCanalTO convenioCanalTO = new ConvenioCanalTO();
		//
		// Utilities.copyAttributesFromTo(convenioCanalTO, convenioCanal);
		//
		// ConvenioCanalPK id = new ConvenioCanalPK(convenioTO.getId(),
		// convenioCanal.getCanal().getId());
		// convenioCanalTO.setId(id);
		//
		// if (convenioCanal.getCanal().getId() != null &&
		// convenioCanal.getCanal().getId() > 0) {
		// em.clear();
		// em.merge(convenioCanalTO); // O MERGE TAVA DANDO UM ERRO DE FOREIGN
		// KEY SEM MOTIVO
		// em.flush();
		// //em.clear();
		// }
		// }

		// grava auditoria do convenio

		if (tipoAuditoria == EnumTipoAuditoria.INCLUSAO) {
			salvarAuditoria(convenioTO, null, usuario, EnumTipoAuditoria.INCLUSAO, null, null, null);
		} else if (tipoAuditoria == EnumTipoAuditoria.ALTERACAO) {
			salvarAuditoria(convenioTO, convenioAuditoria, usuario, EnumTipoAuditoria.ALTERACAO, lstConvenioUfTOInicial,
					lstConvenioSrTOInicial, listConvenioCNPJVinculadoTOInicial);
		}

		// grava cnpjConvenente.
		if (retorno == null) {
			retorno = new Retorno(convenioTO.getId(), "", Retorno.SUCESSO, "MA002");
		}

		return retorno;
	}

	@SuppressWarnings("unchecked")
	public List<Auditoria> funcoesCanalAuditoria(Long nuCanal, Long nuConvenio, String usuario,
			EnumTipoAuditoria tipoAuditoria, ConvenioCanal convenioCanalNovo) {
		ConvenioCanalTO convenioCanal = new ConvenioCanalTO();
		List<Auditoria> auditorias = new ArrayList<Auditoria>();

		if (!tipoAuditoria.getDescricao().equals(EnumTipoAuditoria.INCLUSAO.getDescricao())) {
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT c.* FROM CCR.CCRTB003_CONVENIO_CANAL c ");
			sql.append(" WHERE c.NU_CONVENIO = :pConvenio ");
			sql.append(" AND c.NU_CANAL_ATENDIMENTO = :pCanal ");

			TypedQuery<ConvenioCanalTO> queryConvCanal = null;
			queryConvCanal = (TypedQuery<ConvenioCanalTO>) em.createNativeQuery(sql.toString());
			queryConvCanal.setParameter("pConvenio", nuConvenio);
			queryConvCanal.setParameter("pCanal", nuCanal);

			Object objeto = queryConvCanal.getSingleResult();
			Object[] objectArray = (Object[]) objeto;

			if (objectArray.length > 0) {
				ConvenioCanalPK convenioCanalPK = new ConvenioCanalPK();
				convenioCanalPK.setIdCanalAtendimento(nuCanal);
				convenioCanalPK.setIdConvenio(nuConvenio);

				/* Cast Object to Entity */
				convenioCanal.setId(convenioCanalPK);
				convenioCanal.setIndAutorizaMargemContrato(String.valueOf(objectArray[2]));
				convenioCanal.setIndAutorizaMargemRenovacao(String.valueOf(objectArray[3]));
				convenioCanal.setIndExigeAnuencia(String.valueOf(objectArray[4]));
				convenioCanal.setIndFaixaJuroContratacao(String.valueOf(objectArray[5]));
				convenioCanal.setIndFaixaJuroRenovacao(String.valueOf(objectArray[6]));
				convenioCanal.setIndPermiteContratacao(String.valueOf(objectArray[7]));
				convenioCanal.setIndPermiteRenovacao(String.valueOf(objectArray[8]));
				convenioCanal.setPrzMaximoContratacao((BigDecimal) objectArray[9]);
				convenioCanal.setPrzMaximoRenovacao(Integer.valueOf(objectArray[10].toString()));
				convenioCanal.setPrzMinimoContratacao(String.valueOf(objectArray[11]));
				convenioCanal.setPrzMinimoRenovacao(String.valueOf(objectArray[12]));
				convenioCanal.setQtdDiaGarantiaCondicao(String.valueOf(objectArray[13]));

				List<AuditoriaTO> listAuditoria = new ArrayList<AuditoriaTO>();
				if (convenioCanalNovo == null) {
					convenioCanalNovo = new ConvenioCanal();
					DozerBeanMapper mapper = new DozerBeanMapper();
					mapper.map(convenioCanalNovo, convenioCanal);
					listAuditoria = genericoAuditoriaCanal(convenioCanal, usuario, tipoAuditoria, convenioCanalNovo,
							nuConvenio);
				} else {
					listAuditoria = genericoAuditoriaCanal(convenioCanal, usuario, tipoAuditoria, convenioCanalNovo,
							nuConvenio);
				}

				for (AuditoriaTO auditoriaTO : listAuditoria) {
					Auditoria auditoria = new Auditoria();
					DozerBeanMapper mapper = new DozerBeanMapper();
					mapper.map(auditoriaTO, auditoria);
					auditorias.add(auditoria);
				}
			}
		} else {
			List<AuditoriaTO> listAuditoria = genericoAuditoriaCanal(null, usuario, tipoAuditoria, convenioCanalNovo,
					nuConvenio);
			for (AuditoriaTO auditoriaTO : listAuditoria) {
				Auditoria auditoria = new Auditoria();
				DozerBeanMapper mapper = new DozerBeanMapper();
				mapper.map(auditoriaTO, auditoria);
				auditorias.add(auditoria);
			}
		}

		return auditorias;
	}

	public Retorno auditoriaCanaisPersiste(List<Auditoria> auditorias)
			throws IllegalArgumentException, IllegalAccessException, ParseException {
		List<AuditoriaTO> listAuditorias = new ArrayList<AuditoriaTO>();

		for (Auditoria auditoria : auditorias) {
			AuditoriaTO auditoriaTO = new AuditoriaTO();
			DozerBeanMapper mapper = new DozerBeanMapper();
			mapper.map(auditoria, auditoriaTO);
			listAuditorias.add(auditoriaTO);
			em.persist(auditoriaTO);
		}

		em.flush();

		return new Retorno(1L, "", Retorno.SUCESSO, "MA002");
	}

	@SuppressWarnings("unchecked")
	public List<AuditoriaTO> genericoAuditoriaCanal(ConvenioCanalTO convenioCanalTO, String usuario,
			EnumTipoAuditoria tipoAuditoria, ConvenioCanal convenioCanalNovo, Long nuConvenio) {

		Date dateAuditoria = new Date();
		List<AuditoriaTO> listAuditorias = new ArrayList<AuditoriaTO>();

		ConvenioTO convenioTO = new ConvenioTO();
		CanalAtendimentoTO canalAtendimentoTO = new CanalAtendimentoTO();
		// Fazer o Dozzer do Convenio pro ConvenioTO
		if (convenioCanalNovo.getConvenio() != null) {
			DozerBeanMapper mapper = new DozerBeanMapper();
			mapper.map(convenioCanalNovo.getConvenio(), convenioTO);
		} else {
			TypedQuery<ConvenioTO> queryConvenio = null;
			queryConvenio = (TypedQuery<ConvenioTO>) em.createNamedQuery("Convenio.consultarPorId", ConvenioTO.class);
			if (convenioCanalNovo.getIdConvenio() != null) {
				queryConvenio.setParameter("pId", convenioCanalNovo.getIdConvenio());
			} else {
				if (nuConvenio != null) {
					queryConvenio.setParameter("pId", nuConvenio);
				} else {
					queryConvenio.setParameter("pId", convenioCanalTO.getId().getIdConvenio());
				}

			}

			convenioTO = queryConvenio.getSingleResult();
		}

		TypedQuery<CanalAtendimentoTO> queryCanalAtendimento = null;
		queryCanalAtendimento = (TypedQuery<CanalAtendimentoTO>) em
				.createNamedQuery("CanalAtendimentoTO.consultarCanal", CanalAtendimentoTO.class);
		if (convenioCanalNovo.getCanal().getId() != null) {
			queryCanalAtendimento.setParameter("pID", convenioCanalNovo.getCanal().getId());
		} else {
			queryCanalAtendimento.setParameter("pID", convenioCanalTO.getId().getIdCanalAtendimento());
		}
		canalAtendimentoTO = queryCanalAtendimento.getSingleResult();

		if (!tipoAuditoria.getDescricao().equals(EnumTipoAuditoria.EXCLUSAO.getDescricao())) {
			/* ALTERAÇÃO ou INCLUSÃO */
			listAuditorias = salvarAuditoriaCanal(convenioCanalTO, convenioCanalNovo, usuario, tipoAuditoria,
					convenioTO, canalAtendimentoTO);
		} else {
			/* EXCLUSÃO */

			/* Nome Canal */
			AuditoriaTO auditoriaNomeCanal = popularAuditoria(dateAuditoria, usuario, null);
			auditoriaNomeCanal.setDeValorAnterior(canalAtendimentoTO.getNome());
			auditoriaNomeCanal.setIcTipoAcao(tipoAuditoria.getDescricao());
			auditoriaNomeCanal.setNoCampoAuditado(EnumCampoAuditoria.NOMECANAL.getDescricao());
			auditoriaNomeCanal.setNuConvenio(convenioTO);
			auditoriaNomeCanal.setNuCanal(canalAtendimentoTO);
			auditoriaNomeCanal.setNuTransacaoAuditada(EnumTransacaoAuditada.CANAL.getCodigo());
			listAuditorias.add(auditoriaNomeCanal);

			/* Qtd de dias de garantia */
			AuditoriaTO auditoriaGarantiaCondicao = popularAuditoria(dateAuditoria, usuario, null);
			auditoriaGarantiaCondicao.setDeValorAnterior(convenioCanalTO.getQtdDiaGarantiaCondicao());
			auditoriaGarantiaCondicao.setIcTipoAcao(tipoAuditoria.getDescricao());
			auditoriaGarantiaCondicao.setNoCampoAuditado(EnumCampoAuditoria.GCONDICOES.getDescricao());
			auditoriaGarantiaCondicao.setNuConvenio(convenioTO);
			auditoriaGarantiaCondicao.setNuCanal(canalAtendimentoTO);
			auditoriaGarantiaCondicao.setNuTransacaoAuditada(EnumTransacaoAuditada.CANAL.getCodigo());
			listAuditorias.add(auditoriaGarantiaCondicao);

			/* Exige anuencia */
			AuditoriaTO auditoriaExigeAnuencia = popularAuditoria(dateAuditoria, usuario, null);
			auditoriaExigeAnuencia.setDeValorAnterior(convenioCanalTO.getIndExigeAnuencia());
			auditoriaExigeAnuencia.setIcTipoAcao(tipoAuditoria.getDescricao());
			auditoriaExigeAnuencia.setNoCampoAuditado(EnumCampoAuditoria.EANUENCIA.getDescricao());
			auditoriaExigeAnuencia.setNuConvenio(convenioTO);
			auditoriaExigeAnuencia.setNuCanal(canalAtendimentoTO);
			auditoriaExigeAnuencia.setNuTransacaoAuditada(EnumTransacaoAuditada.CANAL.getCodigo());
			listAuditorias.add(auditoriaExigeAnuencia);

			/* Permite contratação */
			AuditoriaTO auditoriaPermiteContratacao = popularAuditoria(dateAuditoria, usuario, null);
			auditoriaPermiteContratacao.setDeValorAnterior(convenioCanalTO.getIndPermiteContratacao());
			auditoriaPermiteContratacao.setIcTipoAcao(tipoAuditoria.getDescricao());
			auditoriaPermiteContratacao.setNoCampoAuditado(EnumCampoAuditoria.PCONTRATACAO.getDescricao());
			auditoriaPermiteContratacao.setNuConvenio(convenioTO);
			auditoriaPermiteContratacao.setNuCanal(canalAtendimentoTO);
			auditoriaPermiteContratacao.setNuTransacaoAuditada(EnumTransacaoAuditada.CANAL.getCodigo());
			listAuditorias.add(auditoriaPermiteContratacao);

			/* Exige margem de contratação */
			AuditoriaTO auditoriaMargemContrato = popularAuditoria(dateAuditoria, usuario, null);
			auditoriaMargemContrato.setDeValorAnterior(convenioCanalTO.getIndAutorizaMargemContrato());
			auditoriaMargemContrato.setIcTipoAcao(tipoAuditoria.getDescricao());
			auditoriaMargemContrato.setNoCampoAuditado(EnumCampoAuditoria.EAMCONTRATACAO.getDescricao());
			auditoriaMargemContrato.setNuConvenio(convenioTO);
			auditoriaMargemContrato.setNuCanal(canalAtendimentoTO);
			auditoriaMargemContrato.setNuTransacaoAuditada(EnumTransacaoAuditada.CANAL.getCodigo());
			listAuditorias.add(auditoriaMargemContrato);

			/* Prazo mínimo de contratação */
			AuditoriaTO auditoriaPrzMinContratacao = popularAuditoria(dateAuditoria, usuario, null);
			auditoriaPrzMinContratacao.setDeValorAnterior(convenioCanalTO.getPrzMinimoContratacao());
			auditoriaPrzMinContratacao.setIcTipoAcao(tipoAuditoria.getDescricao());
			auditoriaPrzMinContratacao.setNoCampoAuditado(EnumCampoAuditoria.PMINCONTRATACAO.getDescricao());
			auditoriaPrzMinContratacao.setNuConvenio(convenioTO);
			auditoriaPrzMinContratacao.setNuCanal(canalAtendimentoTO);
			auditoriaPrzMinContratacao.setNuTransacaoAuditada(EnumTransacaoAuditada.CANAL.getCodigo());
			listAuditorias.add(auditoriaPrzMinContratacao);

			/* Prazo maximo de contratação */
			AuditoriaTO auditoriaPrzMaxContratacao = popularAuditoria(dateAuditoria, usuario, null);
			auditoriaPrzMaxContratacao.setDeValorAnterior(String.valueOf(convenioCanalTO.getPrzMaximoContratacao()));
			auditoriaPrzMaxContratacao.setIcTipoAcao(tipoAuditoria.getDescricao());
			auditoriaPrzMaxContratacao.setNoCampoAuditado(EnumCampoAuditoria.PMAXCONTRATACAO.getDescricao());
			auditoriaPrzMaxContratacao.setNuConvenio(convenioTO);
			auditoriaPrzMaxContratacao.setNuCanal(canalAtendimentoTO);
			auditoriaPrzMaxContratacao.setNuTransacaoAuditada(EnumTransacaoAuditada.CANAL.getCodigo());
			listAuditorias.add(auditoriaPrzMaxContratacao);

			/* Faixa juros contratação */
			AuditoriaTO auditoriaSrCobranca = popularAuditoria(dateAuditoria, usuario, null);
			auditoriaSrCobranca.setDeValorAnterior(convenioCanalTO.getIndFaixaJuroContratacao());
			auditoriaSrCobranca.setIcTipoAcao(tipoAuditoria.getDescricao());
			auditoriaSrCobranca.setNoCampoAuditado(EnumCampoAuditoria.FJCONTRATACAO.getDescricao());
			auditoriaSrCobranca.setNuConvenio(convenioTO);
			auditoriaSrCobranca.setNuCanal(canalAtendimentoTO);
			auditoriaSrCobranca.setNuTransacaoAuditada(EnumTransacaoAuditada.CANAL.getCodigo());
			listAuditorias.add(auditoriaSrCobranca);

			/* Permite renovação */
			AuditoriaTO auditoriaPermiteRenovacao = popularAuditoria(dateAuditoria, usuario, null);
			auditoriaPermiteRenovacao.setDeValorAnterior(convenioCanalTO.getIndPermiteRenovacao());
			auditoriaPermiteRenovacao.setIcTipoAcao(tipoAuditoria.getDescricao());
			auditoriaPermiteRenovacao.setNoCampoAuditado(EnumCampoAuditoria.PRENOVACAO.getDescricao());
			auditoriaPermiteRenovacao.setNuConvenio(convenioTO);
			auditoriaPermiteRenovacao.setNuCanal(canalAtendimentoTO);
			auditoriaPermiteRenovacao.setNuTransacaoAuditada(EnumTransacaoAuditada.CANAL.getCodigo());
			listAuditorias.add(auditoriaPermiteRenovacao);

			/* Exige margem renovação */
			AuditoriaTO auditoriaMargemRenovacao = popularAuditoria(dateAuditoria, usuario, null);
			auditoriaMargemRenovacao.setDeValorAnterior(convenioCanalTO.getIndAutorizaMargemRenovacao());
			auditoriaMargemRenovacao.setIcTipoAcao(tipoAuditoria.getDescricao());
			auditoriaMargemRenovacao.setNoCampoAuditado(EnumCampoAuditoria.EAMRENOVACAO.getDescricao());
			auditoriaMargemRenovacao.setNuConvenio(convenioTO);
			auditoriaMargemRenovacao.setNuCanal(canalAtendimentoTO);
			auditoriaMargemRenovacao.setNuTransacaoAuditada(EnumTransacaoAuditada.CANAL.getCodigo());
			listAuditorias.add(auditoriaMargemRenovacao);

			/* Prazo mínimo renovação */
			AuditoriaTO auditoriaPrzMinRenovacao = popularAuditoria(dateAuditoria, usuario, null);
			auditoriaPrzMinRenovacao.setDeValorAnterior(convenioCanalTO.getPrzMinimoRenovacao());
			auditoriaPrzMinRenovacao.setIcTipoAcao(tipoAuditoria.getDescricao());
			auditoriaPrzMinRenovacao.setNoCampoAuditado(EnumCampoAuditoria.PMINRENOVACAO.getDescricao());
			auditoriaPrzMinRenovacao.setNuConvenio(convenioTO);
			auditoriaPrzMinRenovacao.setNuCanal(canalAtendimentoTO);
			auditoriaPrzMinRenovacao.setNuTransacaoAuditada(EnumTransacaoAuditada.CANAL.getCodigo());
			listAuditorias.add(auditoriaPrzMinRenovacao);

			/* Prazo maximo renovação */
			AuditoriaTO auditoriaPrazoMax = popularAuditoria(dateAuditoria, usuario, null);
			auditoriaPrazoMax.setDeValorAnterior(String.valueOf(convenioCanalTO.getPrzMaximoRenovacao()));
			auditoriaPrazoMax.setIcTipoAcao(tipoAuditoria.getDescricao());
			auditoriaPrazoMax.setNoCampoAuditado(EnumCampoAuditoria.PMAXRENOVACAO.getDescricao());
			auditoriaPrazoMax.setNuConvenio(convenioTO);
			auditoriaPrazoMax.setNuCanal(canalAtendimentoTO);
			auditoriaPrazoMax.setNuTransacaoAuditada(EnumTransacaoAuditada.CANAL.getCodigo());
			listAuditorias.add(auditoriaPrazoMax);

			/* Faixa juros renovação */
			AuditoriaTO auditoriaFJRenovacao = popularAuditoria(dateAuditoria, usuario, null);
			auditoriaFJRenovacao.setDeValorAnterior(convenioCanalTO.getIndFaixaJuroRenovacao());
			auditoriaFJRenovacao.setIcTipoAcao(tipoAuditoria.getDescricao());
			auditoriaFJRenovacao.setNoCampoAuditado(EnumCampoAuditoria.FJRENOVACAO.getDescricao());
			auditoriaFJRenovacao.setNuConvenio(convenioTO);
			auditoriaFJRenovacao.setNuCanal(canalAtendimentoTO);
			auditoriaFJRenovacao.setNuTransacaoAuditada(EnumTransacaoAuditada.CANAL.getCodigo());
			listAuditorias.add(auditoriaFJRenovacao);
		}

		return listAuditorias;

	}

	public List<AuditoriaTO> salvarAuditoriaCanal(ConvenioCanalTO convenioCanalTO, ConvenioCanal convenioNovo,
			String usuario, EnumTipoAuditoria tipoAuditoria, ConvenioTO convenioTO,
			CanalAtendimentoTO canalAtendimentoTO) {

		Date dateAuditoria = new Date();
		List<AuditoriaTO> listAuditorias = new ArrayList<AuditoriaTO>();

		if (!EnumTipoAuditoria.ALTERACAO.getDescricao().equals(tipoAuditoria.getDescricao())) {
			AuditoriaTO auditoriaNomeCanal = popularAuditoria(dateAuditoria, usuario, null);
			auditoriaNomeCanal.setNoCampoAuditado(EnumCampoAuditoria.NOMECANAL.getDescricao());
			if (!tipoAuditoria.getDescricao().equals(EnumTipoAuditoria.EXCLUSAO.getDescricao())) {
				auditoriaNomeCanal.setDeValorAtual(canalAtendimentoTO.getNome());
			}
			auditoriaNomeCanal.setIcTipoAcao(tipoAuditoria.getDescricao());
			auditoriaNomeCanal.setNuConvenio(convenioTO);
			auditoriaNomeCanal.setNuCanal(canalAtendimentoTO);
			auditoriaNomeCanal.setNuTransacaoAuditada(EnumTransacaoAuditada.CANAL.getCodigo());
			listAuditorias.add(auditoriaNomeCanal);
		}

		if (!convenioNovo.getQtdDiaGarantiaCondicao().isEmpty() && convenioNovo.getQtdDiaGarantiaCondicao() != null) {

			AuditoriaTO auditoriaGarantiaCondicao = popularAuditoria(dateAuditoria, usuario, null);
			auditoriaGarantiaCondicao.setDeValorAtual(convenioNovo.getQtdDiaGarantiaCondicao().toString());
			auditoriaGarantiaCondicao.setNoCampoAuditado(EnumCampoAuditoria.GCONDICOES.getDescricao());
			auditoriaGarantiaCondicao.setIcTipoAcao(tipoAuditoria.getDescricao());
			auditoriaGarantiaCondicao.setNuConvenio(convenioTO);
			auditoriaGarantiaCondicao.setNuCanal(canalAtendimentoTO);
			auditoriaGarantiaCondicao.setNuTransacaoAuditada(EnumTransacaoAuditada.CANAL.getCodigo());

			if (tipoAuditoria.getDescricao().equals(EnumTipoAuditoria.ALTERACAO.getDescricao())) {
				if (!convenioCanalTO.getQtdDiaGarantiaCondicao().equals(convenioNovo.getQtdDiaGarantiaCondicao())) {
					auditoriaGarantiaCondicao
							.setDeValorAnterior(String.valueOf(convenioCanalTO.getQtdDiaGarantiaCondicao()));
					listAuditorias.add(auditoriaGarantiaCondicao);
				}
			} else {
				listAuditorias.add(auditoriaGarantiaCondicao);
			}

		}

		if (!convenioNovo.getIndExigeAnuencia().isEmpty() && convenioNovo.getIndExigeAnuencia() != null) {

			AuditoriaTO auditoriaExigeAnuencia = popularAuditoria(dateAuditoria, usuario, null);
			auditoriaExigeAnuencia.setDeValorAtual(convenioNovo.getIndExigeAnuencia().toString());

			auditoriaExigeAnuencia.setNoCampoAuditado(EnumCampoAuditoria.EANUENCIA.getDescricao());
			auditoriaExigeAnuencia.setIcTipoAcao(tipoAuditoria.getDescricao());
			auditoriaExigeAnuencia.setNuConvenio(convenioTO);
			auditoriaExigeAnuencia.setNuCanal(canalAtendimentoTO);
			auditoriaExigeAnuencia.setNuTransacaoAuditada(EnumTransacaoAuditada.CANAL.getCodigo());

			if (tipoAuditoria.getDescricao().equals(EnumTipoAuditoria.ALTERACAO.getDescricao())) {
				if (!convenioCanalTO.getIndExigeAnuencia().equals(convenioNovo.getIndExigeAnuencia())) {
					auditoriaExigeAnuencia.setDeValorAnterior(String.valueOf(convenioNovo.getIndExigeAnuencia()));
					listAuditorias.add(auditoriaExigeAnuencia);
				}
			} else {
				listAuditorias.add(auditoriaExigeAnuencia);
			}

		}

		if (!convenioNovo.getIndPermiteContratacao().isEmpty() && convenioNovo.getIndPermiteContratacao() != null) {

			AuditoriaTO auditoriaPermiteContratacao = popularAuditoria(dateAuditoria, usuario, null);
			auditoriaPermiteContratacao.setDeValorAtual(convenioNovo.getIndPermiteContratacao().toString());
			auditoriaPermiteContratacao.setNoCampoAuditado(EnumCampoAuditoria.PCONTRATACAO.getDescricao());
			auditoriaPermiteContratacao.setIcTipoAcao(tipoAuditoria.getDescricao());
			auditoriaPermiteContratacao.setNuConvenio(convenioTO);
			auditoriaPermiteContratacao.setNuCanal(canalAtendimentoTO);
			auditoriaPermiteContratacao.setNuTransacaoAuditada(EnumTransacaoAuditada.CANAL.getCodigo());

			if (tipoAuditoria.getDescricao().equals(EnumTipoAuditoria.ALTERACAO.getDescricao())) {
				if (!convenioCanalTO.getIndPermiteContratacao().equals(convenioNovo.getIndPermiteContratacao())) {
					auditoriaPermiteContratacao
							.setDeValorAnterior(String.valueOf(convenioNovo.getIndPermiteContratacao()));
					listAuditorias.add(auditoriaPermiteContratacao);
				}
			} else {
				listAuditorias.add(auditoriaPermiteContratacao);
			}

		}

		if (!convenioNovo.getIndAutorizaMargemContrato().isEmpty()
				&& convenioNovo.getIndAutorizaMargemContrato() != null) {

			AuditoriaTO auditoriaMargemContrato = popularAuditoria(dateAuditoria, usuario, null);
			auditoriaMargemContrato.setDeValorAtual(convenioNovo.getIndAutorizaMargemContrato());
			auditoriaMargemContrato.setNoCampoAuditado(EnumCampoAuditoria.EAMCONTRATACAO.getDescricao());
			auditoriaMargemContrato.setIcTipoAcao(tipoAuditoria.getDescricao());
			auditoriaMargemContrato.setNuConvenio(convenioTO);
			auditoriaMargemContrato.setNuCanal(canalAtendimentoTO);
			auditoriaMargemContrato.setNuTransacaoAuditada(EnumTransacaoAuditada.CANAL.getCodigo());

			if (tipoAuditoria.getDescricao().equals(EnumTipoAuditoria.ALTERACAO.getDescricao())) {
				if (!convenioCanalTO.getIndAutorizaMargemContrato()
						.equals(convenioNovo.getIndAutorizaMargemContrato())) {
					auditoriaMargemContrato
							.setDeValorAnterior(String.valueOf(convenioCanalTO.getIndAutorizaMargemContrato()));
					listAuditorias.add(auditoriaMargemContrato);
				}
			} else {
				listAuditorias.add(auditoriaMargemContrato);
			}

		}

		if (!convenioNovo.getPrzMinimoContratacao().isEmpty() && convenioNovo.getPrzMinimoContratacao() != null) {

			AuditoriaTO auditoriaPrzMinContratacao = popularAuditoria(dateAuditoria, usuario, null);
			auditoriaPrzMinContratacao.setDeValorAtual(convenioNovo.getPrzMinimoContratacao().toString());
			auditoriaPrzMinContratacao.setNoCampoAuditado(EnumCampoAuditoria.PMINCONTRATACAO.getDescricao());
			auditoriaPrzMinContratacao.setIcTipoAcao(tipoAuditoria.getDescricao());
			auditoriaPrzMinContratacao.setNuConvenio(convenioTO);
			auditoriaPrzMinContratacao.setNuCanal(canalAtendimentoTO);
			auditoriaPrzMinContratacao.setNuTransacaoAuditada(EnumTransacaoAuditada.CANAL.getCodigo());

			if (tipoAuditoria.getDescricao().equals(EnumTipoAuditoria.ALTERACAO.getDescricao())) {
				if (!convenioCanalTO.getPrzMinimoContratacao().equals(convenioNovo.getPrzMinimoContratacao())) {
					auditoriaPrzMinContratacao
							.setDeValorAnterior(String.valueOf(convenioCanalTO.getPrzMinimoContratacao()));
					listAuditorias.add(auditoriaPrzMinContratacao);
				}
			} else {
				listAuditorias.add(auditoriaPrzMinContratacao);
			}
		}

		if (convenioNovo.getPrzMaximoContratacao() != null) {
			AuditoriaTO auditoriaPrzMaxContratacao = popularAuditoria(dateAuditoria, usuario, null);
			auditoriaPrzMaxContratacao.setNoCampoAuditado(EnumCampoAuditoria.PMAXCONTRATACAO.getDescricao());
			auditoriaPrzMaxContratacao.setDeValorAtual(convenioNovo.getPrzMaximoContratacao().toString());
			auditoriaPrzMaxContratacao.setIcTipoAcao(tipoAuditoria.getDescricao());
			auditoriaPrzMaxContratacao.setNuConvenio(convenioTO);
			auditoriaPrzMaxContratacao.setNuCanal(canalAtendimentoTO);
			auditoriaPrzMaxContratacao.setNuTransacaoAuditada(EnumTransacaoAuditada.CANAL.getCodigo());

			if (tipoAuditoria.getDescricao().equals(EnumTipoAuditoria.ALTERACAO.getDescricao())) {
				if (!convenioCanalTO.getPrzMaximoContratacao().equals(convenioNovo.getPrzMaximoContratacao())) {
					auditoriaPrzMaxContratacao
							.setDeValorAnterior(String.valueOf(convenioCanalTO.getPrzMaximoContratacao()));
					listAuditorias.add(auditoriaPrzMaxContratacao);
				}
			} else {
				listAuditorias.add(auditoriaPrzMaxContratacao);
			}

		}

		if (!convenioNovo.getIndFaixaJuroContratacao().isEmpty() && convenioNovo.getIndFaixaJuroContratacao() != null) {
			AuditoriaTO auditoriaSrCobranca = popularAuditoria(dateAuditoria, usuario, null);
			auditoriaSrCobranca.setDeValorAtual(convenioNovo.getIndFaixaJuroContratacao().toString());
			auditoriaSrCobranca.setNoCampoAuditado(EnumCampoAuditoria.FJCONTRATACAO.getDescricao());
			auditoriaSrCobranca.setIcTipoAcao(tipoAuditoria.getDescricao());
			auditoriaSrCobranca.setNuConvenio(convenioTO);
			auditoriaSrCobranca.setNuCanal(canalAtendimentoTO);
			auditoriaSrCobranca.setNuTransacaoAuditada(EnumTransacaoAuditada.CANAL.getCodigo());

			if (tipoAuditoria.getDescricao().equals(EnumTipoAuditoria.ALTERACAO.getDescricao())) {
				if (!convenioCanalTO.getIndFaixaJuroContratacao().equals(convenioNovo.getIndFaixaJuroContratacao())) {
					auditoriaSrCobranca
							.setDeValorAnterior(String.valueOf(convenioCanalTO.getIndFaixaJuroContratacao()));
					listAuditorias.add(auditoriaSrCobranca);
				}
			} else {
				listAuditorias.add(auditoriaSrCobranca);
			}

		}

		if (!convenioNovo.getIndPermiteRenovacao().isEmpty() && convenioNovo.getIndPermiteRenovacao() != null) {
			AuditoriaTO auditoriaPermiteRenovacao = popularAuditoria(dateAuditoria, usuario, null);
			auditoriaPermiteRenovacao.setDeValorAtual(convenioNovo.getIndPermiteRenovacao().toString());
			auditoriaPermiteRenovacao.setNoCampoAuditado(EnumCampoAuditoria.PRENOVACAO.getDescricao());
			auditoriaPermiteRenovacao.setIcTipoAcao(tipoAuditoria.getDescricao());
			auditoriaPermiteRenovacao.setNuConvenio(convenioTO);
			auditoriaPermiteRenovacao.setNuCanal(canalAtendimentoTO);
			auditoriaPermiteRenovacao.setNuTransacaoAuditada(EnumTransacaoAuditada.CANAL.getCodigo());

			if (tipoAuditoria.getDescricao().equals(EnumTipoAuditoria.ALTERACAO.getDescricao())) {
				if (!convenioCanalTO.getIndPermiteRenovacao().equals(convenioNovo.getIndPermiteRenovacao())) {
					auditoriaPermiteRenovacao
							.setDeValorAnterior(String.valueOf(convenioCanalTO.getIndPermiteRenovacao()));
					listAuditorias.add(auditoriaPermiteRenovacao);
				}
			} else {
				listAuditorias.add(auditoriaPermiteRenovacao);
			}

		}

		if (!convenioNovo.getIndAutorizaMargemRenovacao().isEmpty()
				&& convenioNovo.getIndAutorizaMargemRenovacao() != null) {
			AuditoriaTO auditoriaMargemRenovacao = popularAuditoria(dateAuditoria, usuario, null);
			auditoriaMargemRenovacao.setDeValorAtual(convenioNovo.getIndAutorizaMargemRenovacao().toString());
			auditoriaMargemRenovacao.setNoCampoAuditado(EnumCampoAuditoria.EAMRENOVACAO.getDescricao());
			auditoriaMargemRenovacao.setIcTipoAcao(tipoAuditoria.getDescricao());
			auditoriaMargemRenovacao.setNuConvenio(convenioTO);
			auditoriaMargemRenovacao.setNuCanal(canalAtendimentoTO);
			auditoriaMargemRenovacao.setNuTransacaoAuditada(EnumTransacaoAuditada.CANAL.getCodigo());

			if (tipoAuditoria.getDescricao().equals(EnumTipoAuditoria.ALTERACAO.getDescricao())) {
				if (!convenioCanalTO.getIndAutorizaMargemRenovacao()
						.equals(convenioNovo.getIndAutorizaMargemRenovacao())) {
					auditoriaMargemRenovacao
							.setDeValorAnterior(String.valueOf(convenioCanalTO.getIndPermiteRenovacao()));
					listAuditorias.add(auditoriaMargemRenovacao);
				}
			} else {
				listAuditorias.add(auditoriaMargemRenovacao);
			}

		}

		if (!convenioNovo.getPrzMinimoRenovacao().isEmpty() && convenioNovo.getPrzMinimoRenovacao() != null) {
			AuditoriaTO auditoriaPrzMinRenovacao = popularAuditoria(dateAuditoria, usuario, null);
			auditoriaPrzMinRenovacao.setDeValorAtual(convenioNovo.getPrzMinimoRenovacao().toString());
			auditoriaPrzMinRenovacao.setNoCampoAuditado(EnumCampoAuditoria.PMINRENOVACAO.getDescricao());
			auditoriaPrzMinRenovacao.setIcTipoAcao(tipoAuditoria.getDescricao());
			auditoriaPrzMinRenovacao.setNuConvenio(convenioTO);
			auditoriaPrzMinRenovacao.setNuCanal(canalAtendimentoTO);
			auditoriaPrzMinRenovacao.setNuTransacaoAuditada(EnumTransacaoAuditada.CANAL.getCodigo());

			if (tipoAuditoria.getDescricao().equals(EnumTipoAuditoria.ALTERACAO.getDescricao())) {
				if (!convenioCanalTO.getPrzMinimoRenovacao().equals(convenioNovo.getPrzMinimoRenovacao())) {
					auditoriaPrzMinRenovacao
							.setDeValorAnterior(String.valueOf(convenioCanalTO.getPrzMinimoRenovacao()));
					listAuditorias.add(auditoriaPrzMinRenovacao);
				}
			} else {
				listAuditorias.add(auditoriaPrzMinRenovacao);
			}

		}

		if (convenioNovo.getPrzMaximoRenovacao() != null) {
			AuditoriaTO auditoriaPrazoMax = popularAuditoria(dateAuditoria, usuario, null);
			auditoriaPrazoMax.setDeValorAtual(convenioNovo.getPrzMaximoRenovacao().toString());
			auditoriaPrazoMax.setNoCampoAuditado(EnumCampoAuditoria.PMAXRENOVACAO.getDescricao());
			auditoriaPrazoMax.setIcTipoAcao(tipoAuditoria.getDescricao());
			auditoriaPrazoMax.setNuConvenio(convenioTO);
			auditoriaPrazoMax.setNuCanal(canalAtendimentoTO);
			auditoriaPrazoMax.setNuTransacaoAuditada(EnumTransacaoAuditada.CANAL.getCodigo());

			if (tipoAuditoria.getDescricao().equals(EnumTipoAuditoria.ALTERACAO.getDescricao())) {
				if (!convenioCanalTO.getPrzMaximoRenovacao().equals(convenioNovo.getPrzMaximoRenovacao())) {
					auditoriaPrazoMax.setDeValorAnterior(String.valueOf(convenioCanalTO.getPrzMaximoRenovacao()));
					listAuditorias.add(auditoriaPrazoMax);
				}
			} else {
				listAuditorias.add(auditoriaPrazoMax);
			}

		}

		if (!convenioNovo.getIndFaixaJuroRenovacao().isEmpty() && convenioNovo.getIndFaixaJuroRenovacao() != null) {
			AuditoriaTO auditoriaFJRenovacao = popularAuditoria(dateAuditoria, usuario, null);
			auditoriaFJRenovacao.setDeValorAtual(convenioNovo.getIndFaixaJuroRenovacao().toString());
			auditoriaFJRenovacao.setNoCampoAuditado(EnumCampoAuditoria.FJRENOVACAO.getDescricao());
			auditoriaFJRenovacao.setIcTipoAcao(tipoAuditoria.getDescricao());
			auditoriaFJRenovacao.setNuConvenio(convenioTO);
			auditoriaFJRenovacao.setNuCanal(canalAtendimentoTO);
			auditoriaFJRenovacao.setNuTransacaoAuditada(EnumTransacaoAuditada.CANAL.getCodigo());

			if (tipoAuditoria.getDescricao().equals(EnumTipoAuditoria.ALTERACAO.getDescricao())) {
				if (!convenioCanalTO.getIndFaixaJuroRenovacao().equals(convenioNovo.getIndFaixaJuroRenovacao())) {
					auditoriaFJRenovacao.setDeValorAnterior(String.valueOf(convenioCanalTO.getIndFaixaJuroRenovacao()));
					listAuditorias.add(auditoriaFJRenovacao);
				}
			} else {
				listAuditorias.add(auditoriaFJRenovacao);
			}

		}

		return listAuditorias;
	}

	// Geração de registro de auditoria do convenio
	public void salvarAuditoria(ConvenioTO convenioTO, Convenio convenioAnterior, String usuario,
			EnumTipoAuditoria tipoAuditoria, List<ConvenioUfTO> lstConvenioUfTOInicial,
			List<ConvenioSrTO> lstConvenioSrTOInicial,
			List<ConvenioCNPJVinculadoTO> listConvenioCNPJVinculadoTOInicial) {

		// Busca valores após alteração ou inclusão na base
		List<ConvenioCNPJVinculadoTO> listConvenioCNPJVinculadoTOFinal = new ArrayList<ConvenioCNPJVinculadoTO>();
		List<ConvenioSrTO> lstConvenioSrTOFinal = new ArrayList<ConvenioSrTO>();
		List<ConvenioUfTO> lstConvenioUfTOFinal = new ArrayList<ConvenioUfTO>();

		TypedQuery<ConvenioUfTO> queryConvenioUfTO = em.createNamedQuery("ConvenioUfTO.buscarPorId",
				ConvenioUfTO.class);
		queryConvenioUfTO.setParameter(1, convenioTO.getId());
		lstConvenioUfTOFinal = (List<ConvenioUfTO>) queryConvenioUfTO.getResultList();

		TypedQuery<ConvenioSrTO> queryConvenioSrTO = em.createNamedQuery("ConvenioSrTO.buscarPorId",
				ConvenioSrTO.class);
		queryConvenioSrTO.setParameter(1, convenioTO.getId());
		lstConvenioSrTOFinal = (List<ConvenioSrTO>) queryConvenioSrTO.getResultList();

		TypedQuery<ConvenioCNPJVinculadoTO> queryConvenioCNPJVinculadoTO = null;
		queryConvenioCNPJVinculadoTO = em.createNamedQuery("ConvenioCNPJVinculadoTO.consultarPorConvenio",
				ConvenioCNPJVinculadoTO.class);
		queryConvenioCNPJVinculadoTO.setParameter(1, convenioTO.getId());
		listConvenioCNPJVinculadoTOFinal = (List<ConvenioCNPJVinculadoTO>) queryConvenioCNPJVinculadoTO.getResultList();

		String valorAnteriorUF = null;
		String valorAtualUF = null;
		String valorAnterioSR = null;
		String valorAtualSR = null;
		String valorAnterioCNPJ = null;
		String valorAtualCNPJ = null;

		// Criação de string para gravar na base as ufs
		if (lstConvenioUfTOInicial != null && !lstConvenioUfTOInicial.isEmpty()) {
			for (ConvenioUfTO ufAnterior : lstConvenioUfTOInicial) {
				valorAnteriorUF = valorAnteriorUF + ufAnterior.getId().getUf() + ",";
			}
			valorAnteriorUF = valorAnteriorUF.substring(0, valorAnteriorUF.length() - 1);
		}

		if (lstConvenioUfTOFinal != null && !lstConvenioUfTOFinal.isEmpty()) {
			for (ConvenioUfTO ufAtual : lstConvenioUfTOFinal) {
				valorAtualUF = valorAtualUF + ufAtual.getId().getUf() + ",";
			}
			valorAtualUF = valorAtualUF.substring(0, valorAtualUF.length() - 1);
		}

		// Criação de string para gravar na base as srs
		if (lstConvenioSrTOInicial != null && !lstConvenioSrTOInicial.isEmpty()) {
			for (ConvenioSrTO srAnterior : lstConvenioSrTOInicial) {
				valorAnterioSR = valorAnterioSR + srAnterior.getId().getIdUnidade() + ",";
			}
			valorAnterioSR = valorAnterioSR.substring(0, valorAnterioSR.length() - 1);
		}

		if (lstConvenioSrTOFinal != null && !lstConvenioSrTOFinal.isEmpty()) {
			for (ConvenioSrTO srAtual : lstConvenioSrTOFinal) {
				valorAtualSR = valorAtualSR + srAtual.getId().getIdUnidade() + ",";
			}
			valorAtualSR = valorAtualSR.substring(0, valorAtualSR.length() - 1);
		}

		// Criação de string para gravar na base as cnpjs
		if (listConvenioCNPJVinculadoTOInicial != null && !listConvenioCNPJVinculadoTOInicial.isEmpty()) {
			for (ConvenioCNPJVinculadoTO cnpjAnterior : listConvenioCNPJVinculadoTOInicial) {
				valorAnterioCNPJ = valorAnterioCNPJ + cnpjAnterior.getNuCNPJ() + ",";
			}
			valorAnterioCNPJ = valorAnterioCNPJ.substring(0, valorAnterioCNPJ.length() - 1);
		}

		if (listConvenioCNPJVinculadoTOFinal != null && !listConvenioCNPJVinculadoTOFinal.isEmpty()) {
			for (ConvenioCNPJVinculadoTO cnpjAtual : listConvenioCNPJVinculadoTOFinal) {
				valorAtualCNPJ = valorAtualCNPJ + cnpjAtual.getNuCNPJ() + ",";
			}
			valorAtualCNPJ = valorAtualCNPJ.substring(0, valorAtualCNPJ.length() - 1);
		}

		// Cria data para todos os registros da auditoria da ação
		Date dateAuditoria = new Date();

		if (valorAtualUF != null) {
			AuditoriaTO auditoriaNomeConvenio = popularAuditoria(dateAuditoria, usuario, convenioTO);

			if (tipoAuditoria == EnumTipoAuditoria.INCLUSAO) {
				auditoriaNomeConvenio.setIcTipoAcao(EnumTipoAuditoria.INCLUSAO.getDescricao());
				auditoriaNomeConvenio.setNoCampoAuditado(EnumCampoAuditoria.UFABRANGENCIA.getDescricao());
				auditoriaNomeConvenio.setDeValorAtual(valorAtualUF);
				em.persist(auditoriaNomeConvenio);
			} else if (tipoAuditoria == EnumTipoAuditoria.ALTERACAO && !valorAtualUF.equals(valorAnteriorUF)) {
				auditoriaNomeConvenio.setIcTipoAcao(EnumTipoAuditoria.ALTERACAO.getDescricao());
				auditoriaNomeConvenio.setDeValorAnterior(valorAnteriorUF);
				auditoriaNomeConvenio.setNoCampoAuditado(EnumCampoAuditoria.UFABRANGENCIA.getDescricao());
				auditoriaNomeConvenio.setDeValorAtual(valorAtualUF);
				em.persist(auditoriaNomeConvenio);
			}
		}

		if (valorAtualSR != null) {
			AuditoriaTO auditoriaNomeConvenio = popularAuditoria(dateAuditoria, usuario, convenioTO);

			if (tipoAuditoria == EnumTipoAuditoria.INCLUSAO) {
				auditoriaNomeConvenio.setIcTipoAcao(EnumTipoAuditoria.INCLUSAO.getDescricao());
				auditoriaNomeConvenio.setNoCampoAuditado(EnumCampoAuditoria.SRABRANGENCIA.getDescricao());
				auditoriaNomeConvenio.setDeValorAtual(valorAtualSR);
				em.persist(auditoriaNomeConvenio);
			} else if (tipoAuditoria == EnumTipoAuditoria.ALTERACAO && !valorAtualSR.equals(valorAnterioSR)) {
				auditoriaNomeConvenio.setIcTipoAcao(EnumTipoAuditoria.ALTERACAO.getDescricao());
				auditoriaNomeConvenio.setDeValorAnterior(valorAnterioSR);
				auditoriaNomeConvenio.setNoCampoAuditado(EnumCampoAuditoria.SRABRANGENCIA.getDescricao());
				auditoriaNomeConvenio.setDeValorAtual(valorAtualSR);
				em.persist(auditoriaNomeConvenio);
			}
		}

		if (valorAtualCNPJ != null) {
			AuditoriaTO auditoriaNomeConvenio = popularAuditoria(dateAuditoria, usuario, convenioTO);

			if (tipoAuditoria == EnumTipoAuditoria.INCLUSAO) {
				auditoriaNomeConvenio.setIcTipoAcao(EnumTipoAuditoria.INCLUSAO.getDescricao());
				auditoriaNomeConvenio.setNoCampoAuditado(EnumCampoAuditoria.CNPJVINCULADO.getDescricao());
				auditoriaNomeConvenio.setDeValorAtual(valorAtualCNPJ);
				em.persist(auditoriaNomeConvenio);
			} else if (tipoAuditoria == EnumTipoAuditoria.ALTERACAO && !valorAtualCNPJ.equals(valorAnterioCNPJ)) {
				auditoriaNomeConvenio.setIcTipoAcao(EnumTipoAuditoria.ALTERACAO.getDescricao());
				auditoriaNomeConvenio.setDeValorAnterior(valorAnterioCNPJ);
				auditoriaNomeConvenio.setNoCampoAuditado(EnumCampoAuditoria.CNPJVINCULADO.getDescricao());
				auditoriaNomeConvenio.setDeValorAtual(valorAtualCNPJ);
				em.persist(auditoriaNomeConvenio);
			}
		}

		// Verifica se campos estão vazios ou nulos e tipo de auditoria e cria e
		// salva objetos.
		if (!convenioTO.getNome().isEmpty() && convenioTO.getNome() != null) {
			AuditoriaTO auditoriaNomeConvenio = popularAuditoria(dateAuditoria, usuario, convenioTO);

			if (tipoAuditoria == EnumTipoAuditoria.INCLUSAO) {
				auditoriaNomeConvenio.setIcTipoAcao(EnumTipoAuditoria.INCLUSAO.getDescricao());
				auditoriaNomeConvenio.setNoCampoAuditado(EnumCampoAuditoria.CONVENIO.getDescricao());
				auditoriaNomeConvenio.setDeValorAtual(convenioTO.getNome());
				em.persist(auditoriaNomeConvenio);
			} else if (tipoAuditoria == EnumTipoAuditoria.ALTERACAO
					&& !convenioAnterior.getNome().equals(convenioTO.getNome())) {
				auditoriaNomeConvenio.setIcTipoAcao(EnumTipoAuditoria.ALTERACAO.getDescricao());
				auditoriaNomeConvenio.setDeValorAnterior(convenioAnterior.getNome());
				auditoriaNomeConvenio.setNoCampoAuditado(EnumCampoAuditoria.CONVENIO.getDescricao());
				auditoriaNomeConvenio.setDeValorAtual(convenioTO.getNome());
				em.persist(auditoriaNomeConvenio);
			}
		}

		if (convenioTO.getGrupoTaxa().getId() != null) {
			AuditoriaTO auditoriaGrupoTaxa = popularAuditoria(dateAuditoria, usuario, convenioTO);

			if (tipoAuditoria == EnumTipoAuditoria.INCLUSAO) {
				auditoriaGrupoTaxa.setIcTipoAcao(EnumTipoAuditoria.INCLUSAO.getDescricao());
				auditoriaGrupoTaxa.setNoCampoAuditado(EnumCampoAuditoria.GTAXAS.getDescricao());
				auditoriaGrupoTaxa.setDeValorAtual(convenioTO.getGrupoTaxa().getId().toString());
				em.persist(auditoriaGrupoTaxa);
			} else if (tipoAuditoria == EnumTipoAuditoria.ALTERACAO
					&& convenioAnterior.getGrupo().getId() != convenioTO.getGrupoTaxa().getId()) {
				auditoriaGrupoTaxa.setIcTipoAcao(EnumTipoAuditoria.ALTERACAO.getDescricao());
				auditoriaGrupoTaxa.setDeValorAnterior(convenioAnterior.getGrupo().getId().toString());
				auditoriaGrupoTaxa.setNoCampoAuditado(EnumCampoAuditoria.GTAXAS.getDescricao());
				auditoriaGrupoTaxa.setDeValorAtual(convenioTO.getGrupoTaxa().getId().toString());
				em.persist(auditoriaGrupoTaxa);
			}
		}

		if (convenioTO.getGrupoAverbacao().getId() != null) {
			AuditoriaTO auditoriaGrupoAverbacao = popularAuditoria(dateAuditoria, usuario, convenioTO);

			if (tipoAuditoria == EnumTipoAuditoria.INCLUSAO) {
				auditoriaGrupoAverbacao.setIcTipoAcao(EnumTipoAuditoria.INCLUSAO.getDescricao());
				auditoriaGrupoAverbacao.setNoCampoAuditado(EnumCampoAuditoria.GAVERBACAO.getDescricao());
				auditoriaGrupoAverbacao.setDeValorAtual(convenioTO.getGrupoAverbacao().getId().toString());
				em.persist(auditoriaGrupoAverbacao);
			} else if (tipoAuditoria == EnumTipoAuditoria.ALTERACAO
					&& convenioAnterior.getGrupoAverbacao().getId() != convenioTO.getGrupoAverbacao().getId()) {
				auditoriaGrupoAverbacao.setIcTipoAcao(EnumTipoAuditoria.ALTERACAO.getDescricao());
				auditoriaGrupoAverbacao.setDeValorAnterior(convenioAnterior.getGrupoAverbacao().getId().toString());
				auditoriaGrupoAverbacao.setNoCampoAuditado(EnumCampoAuditoria.GAVERBACAO.getDescricao());
				auditoriaGrupoAverbacao.setDeValorAtual(convenioTO.getGrupoAverbacao().getId().toString());
				em.persist(auditoriaGrupoAverbacao);
			}
		}

		if (convenioTO.getSituacao().getId() != null) {
			AuditoriaTO auditoriaSituacaoConvenio = popularAuditoria(dateAuditoria, usuario, convenioTO);

			if (tipoAuditoria == EnumTipoAuditoria.INCLUSAO) {
				auditoriaSituacaoConvenio.setIcTipoAcao(EnumTipoAuditoria.INCLUSAO.getDescricao());
				auditoriaSituacaoConvenio.setNoCampoAuditado(EnumCampoAuditoria.SCONVENIO.getDescricao());
				auditoriaSituacaoConvenio.setDeValorAtual(convenioTO.getSituacao().getId().toString());
				em.persist(auditoriaSituacaoConvenio);
			} else if (tipoAuditoria == EnumTipoAuditoria.ALTERACAO
					&& convenioAnterior.getSituacao().getId() != convenioTO.getSituacao().getId()) {
				auditoriaSituacaoConvenio.setIcTipoAcao(EnumTipoAuditoria.ALTERACAO.getDescricao());
				auditoriaSituacaoConvenio.setDeValorAnterior(convenioAnterior.getSituacao().getId().toString());
				auditoriaSituacaoConvenio.setNoCampoAuditado(EnumCampoAuditoria.SCONVENIO.getDescricao());
				auditoriaSituacaoConvenio.setDeValorAtual(convenioTO.getSituacao().getId().toString());
				em.persist(auditoriaSituacaoConvenio);
			}
		}

		if (!convenioTO.getCodigoOrgao().isEmpty() && convenioTO.getCodigoOrgao() != null) {
			AuditoriaTO auditoriaCodigoOrgao = popularAuditoria(dateAuditoria, usuario, convenioTO);

			if (tipoAuditoria == EnumTipoAuditoria.INCLUSAO) {
				auditoriaCodigoOrgao.setIcTipoAcao(EnumTipoAuditoria.INCLUSAO.getDescricao());
				auditoriaCodigoOrgao.setNoCampoAuditado(EnumCampoAuditoria.CORGAO.getDescricao());
				auditoriaCodigoOrgao.setDeValorAtual(convenioTO.getCodigoOrgao());
				em.persist(auditoriaCodigoOrgao);
			} else if (tipoAuditoria == EnumTipoAuditoria.ALTERACAO
					&& !convenioAnterior.getCodigoOrgao().equals(convenioTO.getCodigoOrgao())) {
				auditoriaCodigoOrgao.setIcTipoAcao(EnumTipoAuditoria.ALTERACAO.getDescricao());
				auditoriaCodigoOrgao.setDeValorAnterior(convenioAnterior.getCodigoOrgao());
				auditoriaCodigoOrgao.setNoCampoAuditado(EnumCampoAuditoria.CORGAO.getDescricao());
				auditoriaCodigoOrgao.setDeValorAtual(convenioTO.getCodigoOrgao());
				em.persist(auditoriaCodigoOrgao);
			}
		}

		if (convenioTO.getNumSprnaResponsavel() != null) {
			AuditoriaTO auditoriaSrResponsavel = popularAuditoria(dateAuditoria, usuario, convenioTO);

			if (tipoAuditoria == EnumTipoAuditoria.INCLUSAO) {
				auditoriaSrResponsavel.setIcTipoAcao(EnumTipoAuditoria.INCLUSAO.getDescricao());
				auditoriaSrResponsavel.setNoCampoAuditado(EnumCampoAuditoria.SRRESPONSAVEL.getDescricao());
				auditoriaSrResponsavel.setDeValorAtual(convenioTO.getNumSprnaResponsavel().toString());
				em.persist(auditoriaSrResponsavel);
			} else if (tipoAuditoria == EnumTipoAuditoria.ALTERACAO
					&& !convenioAnterior.getNumSprnaResponsavel().equals(convenioTO.getNumSprnaResponsavel())) {
				auditoriaSrResponsavel.setIcTipoAcao(EnumTipoAuditoria.ALTERACAO.getDescricao());
				auditoriaSrResponsavel.setDeValorAnterior(convenioAnterior.getNumSprnaResponsavel().toString());
				auditoriaSrResponsavel.setNoCampoAuditado(EnumCampoAuditoria.SRRESPONSAVEL.getDescricao());
				auditoriaSrResponsavel.setDeValorAtual(convenioTO.getNumSprnaResponsavel().toString());
				em.persist(auditoriaSrResponsavel);
			}
		}

		if (convenioTO.getNumPvResponsavel() != null) {
			AuditoriaTO auditoriaAgResponsavel = popularAuditoria(dateAuditoria, usuario, convenioTO);

			if (tipoAuditoria == EnumTipoAuditoria.INCLUSAO) {
				auditoriaAgResponsavel.setIcTipoAcao(EnumTipoAuditoria.INCLUSAO.getDescricao());
				auditoriaAgResponsavel.setNoCampoAuditado(EnumCampoAuditoria.ARESPONSAVEL.getDescricao());
				auditoriaAgResponsavel.setDeValorAtual(convenioTO.getNumPvResponsavel().toString());
				em.persist(auditoriaAgResponsavel);
			} else if (tipoAuditoria == EnumTipoAuditoria.ALTERACAO
					&& !convenioAnterior.getNumPvResponsavel().equals(convenioTO.getNumPvResponsavel())) {
				auditoriaAgResponsavel.setIcTipoAcao(EnumTipoAuditoria.ALTERACAO.getDescricao());
				auditoriaAgResponsavel.setDeValorAnterior(convenioAnterior.getNumPvResponsavel().toString());
				auditoriaAgResponsavel.setNoCampoAuditado(EnumCampoAuditoria.ARESPONSAVEL.getDescricao());
				auditoriaAgResponsavel.setDeValorAtual(convenioTO.getNumPvResponsavel().toString());
				em.persist(auditoriaAgResponsavel);
			}
		}

		if (convenioTO.getNumNaturalSprnaCobranca() != null) {
			AuditoriaTO auditoriaSrCobranca = popularAuditoria(dateAuditoria, usuario, convenioTO);

			if (tipoAuditoria == EnumTipoAuditoria.INCLUSAO) {
				auditoriaSrCobranca.setIcTipoAcao(EnumTipoAuditoria.INCLUSAO.getDescricao());
				auditoriaSrCobranca.setNoCampoAuditado(EnumCampoAuditoria.SRCOBRANCA.getDescricao());
				auditoriaSrCobranca.setDeValorAtual(convenioTO.getNumSprnaCobranca().toString());
				em.persist(auditoriaSrCobranca);
			} else if (tipoAuditoria == EnumTipoAuditoria.ALTERACAO
					&& !convenioAnterior.getNumSprnaCobranca().equals(convenioTO.getNumSprnaCobranca())) {
				auditoriaSrCobranca.setIcTipoAcao(EnumTipoAuditoria.ALTERACAO.getDescricao());
				auditoriaSrCobranca.setDeValorAnterior(convenioAnterior.getNumSprnaCobranca().toString());
				auditoriaSrCobranca.setNoCampoAuditado(EnumCampoAuditoria.SRCOBRANCA.getDescricao());
				auditoriaSrCobranca.setDeValorAtual(convenioTO.getNumSprnaCobranca().toString());
				em.persist(auditoriaSrCobranca);
			}
		}

		if (convenioTO.getNumPvCobranca() != null) {
			AuditoriaTO auditoriaAgCobranca = popularAuditoria(dateAuditoria, usuario, convenioTO);

			if (tipoAuditoria == EnumTipoAuditoria.INCLUSAO) {
				auditoriaAgCobranca.setIcTipoAcao(EnumTipoAuditoria.INCLUSAO.getDescricao());
				auditoriaAgCobranca.setNoCampoAuditado(EnumCampoAuditoria.ACOBRANCA.getDescricao());
				auditoriaAgCobranca.setDeValorAtual(convenioTO.getNumPvCobranca().toString());
				em.persist(auditoriaAgCobranca);
			} else if (tipoAuditoria == EnumTipoAuditoria.ALTERACAO
					&& !convenioTO.getNumPvCobranca().equals(convenioAnterior.getNumPvCobranca())) {
				auditoriaAgCobranca.setIcTipoAcao(EnumTipoAuditoria.ALTERACAO.getDescricao());
				auditoriaAgCobranca.setDeValorAnterior(convenioAnterior.getNumPvCobranca().toString());
				auditoriaAgCobranca.setNoCampoAuditado(EnumCampoAuditoria.ACOBRANCA.getDescricao());
				auditoriaAgCobranca.setDeValorAtual(convenioTO.getNumPvCobranca().toString());
				em.persist(auditoriaAgCobranca);
			}
		}

		if (convenioTO.getDiaCreditoSal() != null) {
			AuditoriaTO auditoriaDiaCredito = popularAuditoria(dateAuditoria, usuario, convenioTO);

			if (tipoAuditoria == EnumTipoAuditoria.INCLUSAO) {
				auditoriaDiaCredito.setIcTipoAcao(EnumTipoAuditoria.INCLUSAO.getDescricao());
				auditoriaDiaCredito.setNoCampoAuditado(EnumCampoAuditoria.DCSALARIO.getDescricao());
				auditoriaDiaCredito.setDeValorAtual(convenioTO.getDiaCreditoSal().toString());
				em.persist(auditoriaDiaCredito);
			} else if (tipoAuditoria == EnumTipoAuditoria.ALTERACAO
					&& convenioAnterior.getDiaCreditoSal() != convenioTO.getDiaCreditoSal()) {
				auditoriaDiaCredito.setIcTipoAcao(EnumTipoAuditoria.ALTERACAO.getDescricao());
				auditoriaDiaCredito.setDeValorAnterior(convenioAnterior.getDiaCreditoSal().toString());
				auditoriaDiaCredito.setNoCampoAuditado(EnumCampoAuditoria.DCSALARIO.getDescricao());
				auditoriaDiaCredito.setDeValorAtual(convenioTO.getDiaCreditoSal().toString());
				em.persist(auditoriaDiaCredito);
			}
		}

		if (convenioTO.getPrzEmissaoExtrato() != null) {
			AuditoriaTO auditoriaPrazoEmissao = popularAuditoria(dateAuditoria, usuario, convenioTO);

			if (tipoAuditoria == EnumTipoAuditoria.INCLUSAO) {
				auditoriaPrazoEmissao.setIcTipoAcao(EnumTipoAuditoria.INCLUSAO.getDescricao());
				auditoriaPrazoEmissao.setNoCampoAuditado(EnumCampoAuditoria.PEEXTRATO.getDescricao());
				auditoriaPrazoEmissao.setDeValorAtual(convenioTO.getPrzEmissaoExtrato().toString());
				em.persist(auditoriaPrazoEmissao);
			} else if (tipoAuditoria == EnumTipoAuditoria.ALTERACAO
					&& convenioAnterior.getPrzEmissaoExtrato() != convenioTO.getPrzEmissaoExtrato()) {
				auditoriaPrazoEmissao.setIcTipoAcao(EnumTipoAuditoria.ALTERACAO.getDescricao());
				auditoriaPrazoEmissao.setDeValorAnterior(convenioAnterior.getPrzEmissaoExtrato().toString());
				auditoriaPrazoEmissao.setNoCampoAuditado(EnumCampoAuditoria.PEEXTRATO.getDescricao());
				auditoriaPrazoEmissao.setDeValorAtual(convenioTO.getPrzEmissaoExtrato().toString());
				em.persist(auditoriaPrazoEmissao);
			}
		}

		if (convenioTO.getQtDiaAguardarAutorizacao() != null) {
			AuditoriaTO auditoriaPrazoMax = popularAuditoria(dateAuditoria, usuario, convenioTO);

			if (tipoAuditoria == EnumTipoAuditoria.INCLUSAO) {
				auditoriaPrazoMax.setIcTipoAcao(EnumTipoAuditoria.INCLUSAO.getDescricao());
				auditoriaPrazoMax.setNoCampoAuditado(EnumCampoAuditoria.PMAUTORIZACAO.getDescricao());
				auditoriaPrazoMax.setDeValorAtual(convenioTO.getQtDiaAguardarAutorizacao().toString());
				em.persist(auditoriaPrazoMax);
			} else if (tipoAuditoria == EnumTipoAuditoria.ALTERACAO
					&& convenioAnterior.getQtDiaAguardarAutorizacao() != convenioTO.getQtDiaAguardarAutorizacao()) {
				auditoriaPrazoMax.setIcTipoAcao(EnumTipoAuditoria.ALTERACAO.getDescricao());
				auditoriaPrazoMax.setDeValorAnterior(convenioAnterior.getQtDiaAguardarAutorizacao().toString());
				auditoriaPrazoMax.setNoCampoAuditado(EnumCampoAuditoria.PMAUTORIZACAO.getDescricao());
				auditoriaPrazoMax.setDeValorAtual(convenioTO.getQtDiaAguardarAutorizacao().toString());
				em.persist(auditoriaPrazoMax);
			}
		}

		if (convenioTO.getQtEmpregado() != null) {
			AuditoriaTO auditoriaQtdEmpregados = popularAuditoria(dateAuditoria, usuario, convenioTO);

			if (tipoAuditoria == EnumTipoAuditoria.INCLUSAO) {
				auditoriaQtdEmpregados.setIcTipoAcao(EnumTipoAuditoria.INCLUSAO.getDescricao());
				auditoriaQtdEmpregados.setNoCampoAuditado(EnumCampoAuditoria.QTDEMPREGADOS.getDescricao());
				auditoriaQtdEmpregados.setDeValorAtual(convenioTO.getQtEmpregado().toString());
				em.persist(auditoriaQtdEmpregados);
			} else if (tipoAuditoria == EnumTipoAuditoria.ALTERACAO
					&& (!convenioAnterior.getQtEmpregado().toString().equals(convenioTO.getQtEmpregado().toString()))) {
				auditoriaQtdEmpregados.setIcTipoAcao(EnumTipoAuditoria.ALTERACAO.getDescricao());
				auditoriaQtdEmpregados.setDeValorAnterior(convenioAnterior.getQtEmpregado().toString());
				auditoriaQtdEmpregados.setNoCampoAuditado(EnumCampoAuditoria.QTDEMPREGADOS.getDescricao());
				auditoriaQtdEmpregados.setDeValorAtual(convenioTO.getQtEmpregado().toString());
				em.persist(auditoriaQtdEmpregados);
			}
		}

		if (convenioTO.getNumContaCredito() != null) {
			AuditoriaTO auditoriaContaCorrente = popularAuditoria(dateAuditoria, usuario, convenioTO);

			if (tipoAuditoria == EnumTipoAuditoria.INCLUSAO) {
				auditoriaContaCorrente.setIcTipoAcao(EnumTipoAuditoria.INCLUSAO.getDescricao());
				auditoriaContaCorrente.setNoCampoAuditado(EnumCampoAuditoria.CONTACORRENTE.getDescricao());
				auditoriaContaCorrente.setDeValorAtual(convenioTO.getNumContaCredito().toString());
				em.persist(auditoriaContaCorrente);
			} else if (tipoAuditoria == EnumTipoAuditoria.ALTERACAO
					&& !convenioAnterior.getNumContaCredito().equals(convenioTO.getNumContaCredito())) {
				auditoriaContaCorrente.setIcTipoAcao(EnumTipoAuditoria.ALTERACAO.getDescricao());
				auditoriaContaCorrente.setDeValorAnterior(convenioAnterior.getNumContaCredito().toString());
				auditoriaContaCorrente.setNoCampoAuditado(EnumCampoAuditoria.CONTACORRENTE.getDescricao());
				auditoriaContaCorrente.setDeValorAtual(convenioTO.getNumContaCredito().toString());
				em.persist(auditoriaContaCorrente);
			}
		}

		if (convenioTO.getIndAbrangencia() != null) {
			AuditoriaTO auditoriAbrangencia = popularAuditoria(dateAuditoria, usuario, convenioTO);

			if (tipoAuditoria == EnumTipoAuditoria.INCLUSAO) {
				auditoriAbrangencia.setIcTipoAcao(EnumTipoAuditoria.INCLUSAO.getDescricao());
				auditoriAbrangencia.setNoCampoAuditado(EnumCampoAuditoria.ABRANGENCIA.getDescricao());
				auditoriAbrangencia.setDeValorAtual(convenioTO.getIndAbrangencia().toString());
				em.persist(auditoriAbrangencia);
			} else if (tipoAuditoria == EnumTipoAuditoria.ALTERACAO
					&& convenioAnterior.getIndAbrangencia() != convenioTO.getIndAbrangencia()) {
				auditoriAbrangencia.setIcTipoAcao(EnumTipoAuditoria.ALTERACAO.getDescricao());
				auditoriAbrangencia.setDeValorAnterior(convenioAnterior.getIndAbrangencia().toString());
				auditoriAbrangencia.setNoCampoAuditado(EnumCampoAuditoria.ABRANGENCIA.getDescricao());
				auditoriAbrangencia.setDeValorAtual(convenioTO.getIndAbrangencia().toString());
				em.persist(auditoriAbrangencia);
			}

		}

		em.flush();
	}

	public AuditoriaTO popularAuditoria(Date dateAuditoria, String usuario, ConvenioTO convenioTO) {
		AuditoriaTO auditoriaTO = new AuditoriaTO();

		auditoriaTO.setDhTransacaoAuditada(dateAuditoria);
		auditoriaTO.setCoUsuarioAuditado(usuario);
		auditoriaTO.setNuTransacaoAuditada(EnumTransacaoAuditada.CONVENIO.getCodigo());
		if (convenioTO != null) {
			auditoriaTO.setNuConvenio(convenioTO);
		}

		return auditoriaTO;
	}

	@Override
	public Retorno alterar(Convenio convenio, String usuario, boolean isUserInRole) throws Exception {
		return null;
	}

	private Long getNuNatutalPorUnidade(Long valor) throws Exception {
		Query query = em.createNativeQuery(UnidadeTO.QUERY_CONSULTA_POR_NUUNIDADE);
		query.setParameter(1, new BigDecimal(valor));
		BigDecimal retorno = (BigDecimal) query.getSingleResult();
		if (retorno != null) {
			return Long.valueOf(retorno.intValue());
		}
		return null;

	}

	private void gravaAuditoria(ConvenioTO convenioTO, String usuario) {
		try {
			// Auditoria<HistoricoConvenioTO> auditoria = new
			// Auditoria<HistoricoConvenioTO>(em, HistoricoConvenioTO.class,
			// usuario);
			// Auditoria<ConvenioAuditoriaTO> auditoria = new
			// Auditoria<ConvenioAuditoriaTO>(em, ConvenioAuditoriaTO.class,
			// usuario);

			// auditoria.gravaAuditoria(convenioTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private BigDecimal getNextIdConvenio() throws Exception {
		Query query = em.createNativeQuery(ConvenioTO.QUERY_NEXT_ID);
		return (BigDecimal) query.getSingleResult();
	}

	private static int getDVConvenio(String num) {

		// String num = "0010";
		int soma = 0;
		int resto = 0;
		int dv = 0;
		String[] numeros = new String[num.length() + 1];
		int multiplicador = 2;
		for (int i = num.length(); i > 0; i--) {

			if (multiplicador > 9) {

				multiplicador = 2;
				numeros[i] = String.valueOf(Integer.valueOf(num.substring(i - 1, i)) * multiplicador);
				multiplicador++;
			} else {
				numeros[i] = String.valueOf(Integer.valueOf(num.substring(i - 1, i)) * multiplicador);
				multiplicador++;
			}
		}

		for (int i = numeros.length; i > 0; i--) {
			if (numeros[i - 1] != null) {
				System.out.println(numeros[i - 1]);
				soma += Integer.valueOf(numeros[i - 1]);
			}
		}

		soma = soma * 10;

		// Dividindo pela base 11
		resto = soma % 11;

		// digito verificador
		// dv = 11 / resto;
		dv = resto;
		if ((dv == 0) || (dv == 10)) {
			dv = 0;
		}
		return dv;
	}

}
