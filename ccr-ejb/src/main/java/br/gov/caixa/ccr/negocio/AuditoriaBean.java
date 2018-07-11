package br.gov.caixa.ccr.negocio;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.dozer.DozerBeanMapper;

import br.gov.caixa.ccr.dominio.Auditoria;
import br.gov.caixa.ccr.dominio.AuditoriaRetorno;
import br.gov.caixa.ccr.dominio.transicao.AuditoriaTO;
import br.gov.caixa.ccr.dominio.transicao.CanalAtendimentoTO;
import br.gov.caixa.ccr.enums.EnumCampoAuditoria;
import br.gov.caixa.ccr.enums.EnumTipoAcao;
import br.gov.caixa.ccr.enums.EnumTransacaoAuditada;


@Stateless
@LocalBean
@TransactionManagement(TransactionManagementType.CONTAINER)
public class AuditoriaBean implements IAuditoriaBean {

	@PersistenceContext
	private EntityManager em;

	@Override
	public AuditoriaRetorno consultar(Long nuContrato, Long cpf, Long cnpj, Long convenio, String dtInicio, String dtFim,
			String usuario, Long transacao, Integer length, Long qtdTotalRegistros, Integer start) throws ParseException {


		CriteriaBuilder qb = em.getCriteriaBuilder();
		CriteriaQuery<Object> criteria = qb.createQuery();
		Root<AuditoriaTO> auditoriaFiltro = criteria.from(AuditoriaTO.class);
		
		Predicate p = qb.conjunction();
		
		
		if(nuContrato != null){
			p.getExpressions().add(qb.equal(auditoriaFiltro.get("nuContrato"), nuContrato ));
		}
		
		if(cpf !=null){
			p.getExpressions().add(qb.equal(auditoriaFiltro.get("nuContrato").get("nuCpf"), cpf ));
		}
		
		if(cnpj !=null){
			p.getExpressions().add(qb.equal(auditoriaFiltro.get("nuConvenio").get("cnpjConvenente"), cnpj ));
		}
		
		if(convenio != null){
			p.getExpressions().add(qb.equal(auditoriaFiltro.get("nuConvenio"), convenio));		
		}
		
		if(transacao != null){
			p.getExpressions().add(qb.equal(auditoriaFiltro.get("nuTransacaoAuditada"), transacao));
		}
		
		if(usuario != null){
			p.getExpressions().add(qb.equal(auditoriaFiltro.get("coUsuarioAuditado"), StringUtils.rightPad(usuario.toUpperCase(), 8)));
		}
		
		if(dtInicio != null && !("").equals(dtInicio) ||dtFim != null && !("").equals(dtFim)){
			Timestamp timestampIni = null; 
			Timestamp timestampFim = null; 
			
			if(dtInicio != null && !("").equals(dtInicio)){
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				Date date = sdf.parse(dtInicio);
				timestampIni = new Timestamp(date.getTime()); 
			}	
			if(dtFim != null && !("").equals(dtFim)){
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
				Date date = sdf.parse(dtFim + " 23:59:59");
				timestampFim = new Timestamp(date.getTime()); 
			}

			if(timestampIni!=null){
				p.getExpressions().add(qb.greaterThanOrEqualTo(auditoriaFiltro.<Date>get("dhTransacaoAuditada"), timestampIni));
			}	
			if(timestampFim!=null){
				p.getExpressions().add(qb.lessThanOrEqualTo(auditoriaFiltro.<Date>get("dhTransacaoAuditada"), timestampFim));

			}
		}
		// multiselect por realizar o count de registros
		criteria.multiselect(qb.construct(Long.class, qb.count(auditoriaFiltro)));
		criteria.orderBy(qb.desc(auditoriaFiltro.get("dhTransacaoAuditada")));
		criteria.orderBy(qb.asc(auditoriaFiltro.get("nuAuditoriaTransacao")));
		criteria.where(p);
			
		// Busca a quantidade de registros com os filtros acima
		Long count =  (Long) em.createQuery(criteria).getSingleResult();
		int qtdRegistros = count.intValue();
		
		// select para buscar todos os dados conforme os filtro acima
		criteria.select(auditoriaFiltro);
		
		List<Object> listaRetorno = em.createQuery(criteria).setFirstResult(start).setMaxResults(length).getResultList();
		List<Auditoria> listaAuditoria = new ArrayList<Auditoria>();
		
		for(Object object : listaRetorno){
			Auditoria auditoria = new Auditoria();
			DozerBeanMapper mapper = new DozerBeanMapper();
			
			// passa todos os dados do objeto para auditoria
			mapper.map(object, auditoria);
			
			Auditoria auditoriaVO = new Auditoria();
			if(auditoria.getDeValorAnterior() != null) {
				if(auditoria.getDeValorAnterior().indexOf("null") != -1) {
					auditoria.setDeValorAnterior(auditoria.getDeValorAnterior().replace("null", ""));
				}
			}
			if(auditoria.getDeValorAtual() != null) {
				if(auditoria.getDeValorAtual().indexOf("null") != -1) {
					auditoria.setDeValorAtual(auditoria.getDeValorAtual().replace("null", ""));
				}
			}
			mapper.map(auditoria, auditoriaVO);	
			if(auditoria.getNuCanal()==null){
				CanalAtendimentoTO canal = new CanalAtendimentoTO();
				auditoriaVO.setNuCanal(canal);
			}
			auditoriaVO.setTransacaoAuditada(EnumTransacaoAuditada.valueById(auditoria.getNuTransacaoAuditada()));
			auditoriaVO.setCampoId(EnumCampoAuditoria.valueByDesc(auditoria.getNoCampoAuditado()));
			auditoriaVO.setIcTipoAcao(EnumTipoAcao.valueById(auditoria.getIcTipoAcao()));
			
			listaAuditoria.add(auditoriaVO);
		}	
		
		AuditoriaRetorno retorno = new AuditoriaRetorno();
		retorno.setAuditoria(listaAuditoria);
		retorno.setTotalRegistros((long) qtdRegistros);
		
		return retorno;
	}
	
	//public static void teste() {
		
	//}
}
