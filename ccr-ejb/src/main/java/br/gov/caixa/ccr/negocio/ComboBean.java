package br.gov.caixa.ccr.negocio;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import br.gov.caixa.ccr.dominio.Combo;
import br.gov.caixa.ccr.dominio.transicao.ComboTO;
import br.gov.caixa.ccr.util.Utilities;


@Stateless
@LocalBean
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ComboBean implements IComboBean {

	@PersistenceContext
	private EntityManager em;
	
	private ArrayList<String> queryNames = new ArrayList<String>();
		
	@Override
	public List<Combo> consultar(Combo combo) throws Exception {		
	
		init();
		
		List<ComboTO> retornoTO = new ArrayList<ComboTO>();
		List<Combo> retorno = new ArrayList<Combo>();		
		
		TypedQuery<ComboTO> query = null;
		
		query = em.createNamedQuery(queryNames.get(Integer.parseInt(combo.getId())), ComboTO.class);
		
		if (combo.getFiltroNumerico() != null && !combo.getFiltroNumerico().isEmpty())
			query.setParameter("pFiltroNumerico", Long.parseLong(combo.getFiltroNumerico()));
		else if (combo.getFiltroTextual() != null && !combo.getFiltroTextual().isEmpty())
			query.setParameter("pFiltroTextual", combo.getFiltroTextual());
		
		retornoTO = (List<ComboTO>) query.getResultList();	

		Utilities.copyListClassFromTo(retorno, Combo.class, retornoTO);
		
		return retorno;
		
	} 
	
	private void init(){
		
		queryNames.add(0, "Combo.listaConvenio");
		queryNames.add(1, "Combo.listaGrupoTaxa");
		queryNames.add(2, "Combo.listaRemessaExtrato");
		queryNames.add(3, "Combo.listaSituacao");
		queryNames.add(4, "Combo.listaCanaisAtendimento");
		queryNames.add(5, "Combo.listaGrupoAverbacao");
		queryNames.add(6, "Combo.listaConvenioGrupoAverbacao");
		queryNames.add(7, "Combo.listaTransacaoLog");
		queryNames.add(8, "Combo.UnidadeFederativa");
		queryNames.add(9, "Combo.listaTipoDocumento");
		queryNames.add(10, "Combo.listaCamposDinamicos");
		queryNames.add(11, "Combo.listaGrupoTaxaCOD");
		queryNames.add(12, "Combo.listaSituacaoArquivo");
		queryNames.add(13, "Combo.listaTransacaoAuditada");
		
	}
	
}
