package br.gov.caixa.ccr.negocio;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import br.gov.caixa.arqrefcore.excecao.BusinessException;
import br.gov.caixa.ccr.dominio.ConvenioCanal;
import br.gov.caixa.ccr.dominio.transicao.ConvenioCanalTO;
import br.gov.caixa.ccr.util.Utilities;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ConvenioCanalBean implements IConvenioCanalBean {

	private static String MA0059 = "MA0059";
	
	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<ConvenioCanal> lista(Long idConvenio, List<Long> canais) throws Exception {
		
		TypedQuery<ConvenioCanalTO> query = null;

		//NU_CONVENIO E NU_CANAL_ATENDIMENTO
		if((idConvenio!=null && idConvenio!=0) && (canais!=null && !canais.isEmpty())){

			query = em.createNamedQuery("ConvenioCanal.consultarByConvenioAndCanais", ConvenioCanalTO.class);
			query.setParameter("pNuConvenio", idConvenio);
			query.setParameter("pCanais", canais);			
		}
		else{
			//FE12. Nenhum filtro informado - MA0069
			/**
			 	o sistema verifica que nenhuma informação do filtro foi informada,
		 	*/
			throw new BusinessException(MA0059);					
		}

		List<ConvenioCanalTO> listConvenioCanalTO = (List<ConvenioCanalTO> ) query.getResultList();

		List<ConvenioCanal> convenioCanalListRetorno = new ArrayList<ConvenioCanal>();

		for (ConvenioCanalTO to : listConvenioCanalTO) {
			ConvenioCanal convenioCanal = new ConvenioCanal();
			Utilities.copyAttributesFromTo(convenioCanal, to);
			convenioCanalListRetorno.add(convenioCanal);
		}
		return convenioCanalListRetorno;
	}
}
