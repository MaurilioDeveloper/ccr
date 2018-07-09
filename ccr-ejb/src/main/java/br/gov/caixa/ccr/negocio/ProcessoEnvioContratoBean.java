//package br.gov.caixa.ccr.negocio;
//
//import java.util.Date;
//import java.util.List;
//
//import javax.ejb.LocalBean;
//import javax.ejb.Stateless;
//import javax.ejb.TransactionManagement;
//import javax.ejb.TransactionManagementType;
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import javax.persistence.Query;
//import javax.persistence.TypedQuery;
//
//import br.gov.caixa.arqrefcore.log.Logging;
//import br.gov.caixa.ccr.dominio.transicao.ProcessoEnvioContratoTO;
//import br.gov.caixa.ccr.enums.EnumProcessoEnvioContrato;
//
//@Stateless
//@LocalBean
//@TransactionManagement(TransactionManagementType.CONTAINER)
//@Logging
//public class ProcessoEnvioContratoBean implements IProcessoEnvioContratoBean {
//
//	@PersistenceContext
//	private EntityManager em;
//
//	@Override
//	public List<ProcessoEnvioContratoTO> listar() {
//		
//		Query namedQuery = em.createNamedQuery("ProcessoEnvioContrato.findAll");
//		List<ProcessoEnvioContratoTO> resultList = namedQuery.getResultList();
//		
//		return resultList;
//	}
//	
//	@Override
//	public ProcessoEnvioContratoTO buscar() {
//		TypedQuery<ProcessoEnvioContratoTO> query = null;
//		query = em.createNamedQuery("ProcessoEnvioContrato.buscar", ProcessoEnvioContratoTO.class);
//		query.setParameter(1, EnumProcessoEnvioContrato.SIAPX);
//		ProcessoEnvioContratoTO processoEnvioContratoTO = (ProcessoEnvioContratoTO) query.getSingleResult();
//		
//		return processoEnvioContratoTO;
//	}
//	
//	@Override
//	public ProcessoEnvioContratoTO atualizar(Date horaInicio, Date horaFim, Integer intervalo){
//		ProcessoEnvioContratoTO entity = this.buscar();
//		entity.setHoraFim(horaFim);
//		entity.setHoraInicio(horaInicio);
//		entity.setIntervalo(intervalo);
//		entity = em.merge(entity);
//		em.flush();
//		return entity;
//	}
//	
//	
//}
