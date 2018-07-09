package br.gov.caixa.ccr.negocio;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.gov.caixa.arqrefcore.excecao.BusinessException;
import br.gov.caixa.arqrefcore.log.Logging;
import br.gov.caixa.ccr.dominio.GrupoTaxa;
import br.gov.caixa.ccr.dominio.GrupoTaxaAutoComplete;
import br.gov.caixa.ccr.dominio.Retorno;
import br.gov.caixa.ccr.dominio.transicao.GrupoTaxaTO;
import br.gov.caixa.ccr.util.Utilities;

@Stateless
@LocalBean
@TransactionManagement(TransactionManagementType.CONTAINER)
@Logging
public class GrupoBean implements IGrupoBean {

	@PersistenceContext
	private EntityManager em;
	private Retorno retorno;	

	@Override
	public List<GrupoTaxa> listar() throws Exception {
		// cria a query
		TypedQuery<GrupoTaxaTO> query = em.createNamedQuery("GrupoTaxa.listar", GrupoTaxaTO.class);
		
		// resultado
		List<GrupoTaxaTO> listaRetorno = query.getResultList();
		List<GrupoTaxa> listaGrupoTaxa = new ArrayList<GrupoTaxa>();
		
		Utilities.copyListClassFromTo(listaGrupoTaxa, GrupoTaxa.class, listaRetorno);
		
		return listaGrupoTaxa;
	}
	
	@Override
	public List<GrupoTaxaAutoComplete> consultarAutocomplete(String desc) throws Exception {
		TypedQuery<GrupoTaxaTO> query = null;
		query = em.createNamedQuery("GrupoTaxa.listarGrupoTaxaNomeAutocomplete", GrupoTaxaTO.class);
		query.setParameter("nomeGrupoTaxa",desc+"%");
		
		
		List<GrupoTaxaTO> listaRetorno = query.getResultList();
		List<GrupoTaxaAutoComplete> grupoLst = new ArrayList<>();
		
		for (GrupoTaxaTO grupoTaxaTO : listaRetorno) {
			GrupoTaxaAutoComplete grupoTaxa = new GrupoTaxaAutoComplete();
			grupoTaxa.setNomeGrupoTaxa(grupoTaxaTO.getNome());
			grupoLst.add(grupoTaxa);
		}

		return grupoLst;
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Retorno salvar(GrupoTaxa grupo) throws Exception {
		TypedQuery<GrupoTaxaTO> query = null;
	
		retorno = new Retorno();
		
		//validaarse ja existe
		if(grupo.getTipoOper().equals("incluir")){
			
			if(grupo.getCodigo() !=null){
				List<GrupoTaxa> lista = listar(String.valueOf(grupo.getCodigo()), "");
				
				if(lista.size() > 0){
					throw new BusinessException("MA0093");	
				}
			}
			
			
		}; 
		
		
		//List<GrupoTaxa> listar(Integer codigo, String nome) throws Exception {
		
		if (grupo.getId() == null || grupo.getId() == 0) {
			// cria a Query
			query = em.createNamedQuery("GrupoTaxa.incluir", GrupoTaxaTO.class);
			
			// passa parametros
			query.setParameter("pNomeGrupo", grupo.getNome());
			query.setParameter("pCodGrupo", grupo.getCodigo());
			query.setParameter("pUsuario", grupo.getUsuario());
			query.setParameter("pTsAlteracao", grupo.getData());
			
			retorno.setIdMsg("MA001");
		} else {
			// cria a Query
			query = em.createNamedQuery("GrupoTaxa.alterar", GrupoTaxaTO.class);
						// passa parametros
			query.setParameter("pNomeGrupo", grupo.getNome());
			query.setParameter("pId", grupo.getId());
			query.setParameter("pCodGrupo", grupo.getCodigo());
			//query.setParameter("pUsuario", grupo.getUsuario());
			//query.setParameter("pTsAlteracao", grupo.getData());
			retorno.setIdMsg("MA001");
			
			/*
			// pega a referencia da classe que esta no banco
			grupoRef = em.find(GrupoTaxa.class, grupo.getCodigo());
			
			if (grupo.isUpdatable()) {			
				// cria a Query
				query = em.createNamedQuery("TaxaIOF.atualizar", TaxaIOF.class);
				
				// passa parametros
				query.setParameter("pID", taxaIOF.getId());
				query.setParameter("pAliquotaBasica", taxaIOF.getAliquotaBasica());
				query.setParameter("pAliquotaComplementar", taxaIOF.getAliquotaComplementar());
				query.setParameter("pInicioVigencia", taxaIOF.getInicioVigencia());
				
				retorno.setIdMsg("MA002");
			} else {
				retorno.setAll(2L, "", Retorno.ERRO_NEGOCIAL, "MN001");
				return retorno;
			}
			*/
		}
		try{
			query.executeUpdate();
		}catch (Exception e) {
			retorno.setAll(1L, e.getMessage(), Retorno.ERRO_EXCECAO, "");
		}
		return retorno;
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Retorno excluir(Long codigo) throws Exception {
		GrupoTaxaTO grupoRef = em.find(GrupoTaxaTO.class, codigo);
		retorno = new Retorno();
		retorno.setAll(0L, "", Retorno.SUCESSO, "MA004");

		try {
			em.remove(grupoRef);				
			retorno.setIdMsg("MA004");
		} catch (Exception e) {				
			if (e.getMessage().indexOf("") > -1) {
				retorno.setAll(2L, "", Retorno.ERRO_NEGOCIAL, "MN002");
			} else {
				retorno.setAll(1L, e.getMessage(), Retorno.ERRO_EXCECAO, "");
			}
			
		}
		
		return retorno;
	}

	@Override
	public List<GrupoTaxa> listar(String codigo, String nome) throws Exception {
		
		List<GrupoTaxa> listaGrupoTaxa = new ArrayList<GrupoTaxa>();
 
		CriteriaBuilder qb = em.getCriteriaBuilder();
		CriteriaQuery<GrupoTaxaTO> criteria = qb.createQuery(GrupoTaxaTO.class);
		Root<GrupoTaxaTO> contratoFiltro = criteria.from(GrupoTaxaTO.class);
		Predicate p = qb.conjunction();
		
		if(codigo != null && !codigo.equals("")) 		
			p.getExpressions().add(qb.equal(contratoFiltro.get("codigo"), codigo));
		
		if(nome != null && !nome.equals(""))
			p.getExpressions().add(qb.like(contratoFiltro.<String>get("nome"), "%"+nome+"%"));
		 	//cb.like(path, "a%");
			
		
		
		 criteria.where(p);
		 List<GrupoTaxaTO> listaRetorno = em.createQuery(criteria).getResultList();
		 Utilities.copyListClassFromTo(listaGrupoTaxa, GrupoTaxa.class, listaRetorno);
		  
		 return listaGrupoTaxa;
	}

	@Override
	public Long buscarPorCodigo(Integer id) throws Exception {
		TypedQuery<GrupoTaxaTO> query = null;
		query = em.createNamedQuery("GrupoTaxa.consultarPorCodigo", GrupoTaxaTO.class);
		
		// passa parametros
		query.setParameter("pcodigo", id);
		GrupoTaxaTO resultado= query.getSingleResult();
		return resultado.getId();
	}
	
}
