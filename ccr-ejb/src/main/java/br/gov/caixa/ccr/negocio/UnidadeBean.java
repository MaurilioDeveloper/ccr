package br.gov.caixa.ccr.negocio;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import br.gov.caixa.ccr.dominio.Retorno;
import br.gov.caixa.ccr.dominio.Unidade;
import br.gov.caixa.ccr.dominio.transicao.UnidadeTO;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class UnidadeBean implements IUnidadeBean {

	@PersistenceContext
	private EntityManager em;

	@Override
	public Unidade consultaAgenciaPorNuUnidade(Long nuUnidade) throws Exception {
		
		Unidade unidade = null;
		TypedQuery<UnidadeTO> queryUnidadeTO = null;
		queryUnidadeTO = em.createNamedQuery("UnidadeTO.consultaPorNuUnidade", UnidadeTO.class);
		queryUnidadeTO.setParameter(1, nuUnidade);
		UnidadeTO unidadeTO = (UnidadeTO) queryUnidadeTO.getSingleResult();
		
		if(unidadeTO != null){
			unidade = unidadeTO.convert();
		}

		return unidade;
	}
	
	public Retorno validaSR(Long valor){
		
		Retorno retorno = new Retorno();
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
		
		Retorno retorno = new Retorno();
		TypedQuery<UnidadeTO> queryUnidadeTO = null;
		queryUnidadeTO = em.createNamedQuery("UnidadeTO.verificaAgPorNuUnidade", UnidadeTO.class);
		queryUnidadeTO.setParameter(1, valor);
		List<UnidadeTO> listConvenioTO = (List<UnidadeTO> ) queryUnidadeTO.getResultList();
		if(listConvenioTO.size() < 1){
			return new Retorno(1L, "", Retorno.ERRO_NEGOCIAL, "MA0060");
		}
		return retorno;
		
	}
	
}
