package br.gov.caixa.ccr.negocio;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.gov.caixa.arqrefcore.log.Logging;
import br.gov.caixa.ccr.dominio.Retorno;

@Stateless
@LocalBean
@TransactionManagement(TransactionManagementType.CONTAINER)
@Logging
public class AutorizaBean implements IAutorizaBean {

	@PersistenceContext
	private EntityManager em;
	
	private static final int SITUACAO_AUTORIZADO = 2;
	private static final int SITUACAO_CANCELADO = 5;
	
	private Retorno retorno;

		
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Retorno autorizar(Long contrato, String usuario) throws Exception {
		retorno = new Retorno();		
		retorno.setMensagemRetorno("Contrato autorizado com sucesso!");		
		this.atualizarSituacao(SITUACAO_AUTORIZADO, contrato, usuario);
				
		return retorno;
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Retorno cancelar(Long contrato, String usuario) throws Exception {
		retorno = new Retorno();		
		retorno.setMensagemRetorno("Contrato cancelado com sucesso!");		
		this.atualizarSituacao(SITUACAO_CANCELADO, contrato, usuario);
		
		return retorno;
	}
	
	private void atualizarSituacao(int situacao, Long contrato, String usuario) throws Exception {
		// LEGADO
		// cria a Query
//		TypedQuery<ContratoTO> query = em.createNamedQuery("Autoriza.atualizaSituacao", ContratoTO.class);
//		
//		// passa parametros
//		query.setParameter("pSituacao", situacao);
//		query.setParameter("pNuContrato", contrato);
//		query.executeUpdate();
//		
//		// insere no historico de situacao
//		Calendar cal = Calendar.getInstance();
//		TypedQuery<HistoricoSituacaoContratoTO> queryHist = em.createNamedQuery("HistoricoSituacaoContrato.inclui", HistoricoSituacaoContratoTO.class);
//		
//		queryHist.setParameter("pNuContrato", contrato);
//		queryHist.setParameter("pSituacao", situacao);
//		queryHist.setParameter("pData", DataUtil.formatar(cal.getTime(), DataUtil.PADRAO_DATA_ISO)); 
//		queryHist.setParameter("pHora", DataUtil.formatar(cal.getTime(), DataUtil.PADRAO_HORA_COMPLETA)); 
//		queryHist.setParameter("pCoUsuario", usuario);		
//		queryHist.executeUpdate();
	}
	
}
