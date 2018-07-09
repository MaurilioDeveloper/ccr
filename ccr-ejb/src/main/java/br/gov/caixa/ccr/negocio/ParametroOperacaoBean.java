//package br.gov.caixa.ccr.negocio;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.ejb.LocalBean;
//import javax.ejb.Stateless;
//import javax.ejb.TransactionManagement;
//import javax.ejb.TransactionManagementType;
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import javax.persistence.TypedQuery;
//
//import br.gov.caixa.ccr.dominio.ParametroOperacao;
//import br.gov.caixa.ccr.dominio.Retorno;
//import br.gov.caixa.ccr.dominio.transicao.ParametroOperacaoTO;
//import br.gov.caixa.ccr.util.Utilities;
//
//
//@Stateless
//@LocalBean
//@TransactionManagement(TransactionManagementType.CONTAINER)
//public class ParametroOperacaoBean implements IParametroOperacaoBean {
//
//	@PersistenceContext
//	private EntityManager em;
//			
//	@Override
//	public ParametroOperacao consultar() throws Exception {		
//		
//		List<ParametroOperacaoTO> retornoTO = new ArrayList<ParametroOperacaoTO>();
//		TypedQuery<ParametroOperacaoTO> query = em.createNamedQuery("ParametroOperacao.buscar", ParametroOperacaoTO.class);
//		query.setParameter("pNumeroOperacao", 110);		
//		
//		retornoTO = (List<ParametroOperacaoTO>) query.getResultList();	
//		
//		if (retornoTO.isEmpty()) 
//			return new ParametroOperacao(-1L, "Operacão 110 - Consignado não cadastrada, favor entrar em contato com o administrador do sistema.", Retorno.ERRO_EXCECAO);			
//		
//		ParametroOperacao parametro = new ParametroOperacao();
//		ParametroOperacaoTO to = retornoTO.get(0);
//		Utilities.copyAttributesFromTo(parametro, to);		
//		
//		return parametro;		
//	} 
//	
//}
