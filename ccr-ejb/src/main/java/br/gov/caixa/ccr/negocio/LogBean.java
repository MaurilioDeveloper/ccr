package br.gov.caixa.ccr.negocio;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.gov.caixa.ccr.dominio.Log;


@Stateless
@LocalBean
@TransactionManagement(TransactionManagementType.CONTAINER)
public class LogBean implements ILogBean {

	@PersistenceContext
	private EntityManager em;
		
	@Override
	public List<Log> listar(String dataInicio, String dataFim, Integer transacao) throws Exception {		
		// LEGADO
//		List<LogTO> retornoTO = new ArrayList<LogTO>();
		List<Log> retorno = new ArrayList<Log>();		
		
//		TypedQuery<LogTO> query = null;
//		
//		query = em.createNamedQuery("Log.listar", LogTO.class);
//		
//		query.setParameter("pTransacao", transacao);
//		
//		query.setParameter("pDataInicio", DataUtil.converter(dataInicio, DataUtil.PADRAO_DATA_HORA_COMPLETA_ISO), TemporalType.TIMESTAMP);
//		
//		if (dataFim == null || dataFim.isEmpty())
//			query.setParameter("pDataFim", DataUtil.converter(dataInicio, DataUtil.PADRAO_DATA_HORA_COMPLETA_ISO), TemporalType.TIMESTAMP);
//		else
//			query.setParameter("pDataFim", DataUtil.converter(dataFim, DataUtil.PADRAO_DATA_HORA_COMPLETA_ISO), TemporalType.TIMESTAMP);
//		
//		retornoTO = (List<LogTO>) query.getResultList();
//		Utilities.copyListClassFromTo(retorno, Log.class, retornoTO);
		
		return retorno;
		
	} 
	
}
