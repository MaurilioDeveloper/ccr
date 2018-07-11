package br.gov.caixa.ccr.negocio;

import java.sql.Timestamp;
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
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.gov.caixa.ccr.dominio.ArquivoContratoErro;
import br.gov.caixa.ccr.dominio.ArquivoContratoRequest;
import br.gov.caixa.ccr.dominio.ArquivoContratoResponse;
import br.gov.caixa.ccr.dominio.transicao.ArquivoContratoTO;
import br.gov.caixa.ccr.dominio.transicao.ArquivoIntegracaoTO;
import br.gov.caixa.ccr.dominio.transicao.ContratoTO;
import br.gov.caixa.ccr.dominio.transicao.ErroArquivoContratoTO;
import br.gov.caixa.ccr.dominio.transicao.ErroIntegracaoTO;
import br.gov.caixa.ccr.dominio.transicao.SituacaoTO;
import br.gov.caixa.ccr.enums.EnumSituacaoArquivo;


@Stateless
@LocalBean
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ArquivoBean implements IArquivoBean {

	@PersistenceContext
	private EntityManager em;	

	@Override
	public List<ArquivoContratoResponse> consultar(ArquivoContratoRequest arquivo) throws Exception {		
		
		CriteriaBuilder qb = em.getCriteriaBuilder();
		CriteriaQuery<ArquivoContratoTO> criteria = qb.createQuery(ArquivoContratoTO.class);
		Root<ArquivoContratoTO> contratoFiltro = criteria.from(ArquivoContratoTO.class); 
		final Path<ArquivoIntegracaoTO> arquivoIntegracao = contratoFiltro.join("arquivoIntegracao",JoinType.INNER);
		final Path<ContratoTO> contrato = contratoFiltro.join("contrato",JoinType.INNER);
		final Path<SituacaoTO> situacaoTO = contratoFiltro.join("arquivoIntegracao").join("situacao");
		Predicate p = qb.conjunction();
		
		if(arquivo.getSituacaoRetorno() > 0) {
			p.getExpressions().add(qb.equal(situacaoTO.get("tipo"), 4));
			p.getExpressions().add(qb.equal(situacaoTO.get("id"), arquivo.getSituacaoRetorno()));
		}
		
		if(arquivo.getNuContrato() != null) {	
			p.getExpressions().add(qb.equal(contrato.get("nuContrato"), arquivo.getNuContrato()));
		}
		
		if(arquivo.getDtInicioRemessa() != null && !("").equals(arquivo.getDtInicioRemessa()) || arquivo.getDtFimRemessa() != null && !("").equals(arquivo.getDtFimRemessa())){
			Timestamp timestampFim = null; 
			Timestamp timestampIni = null; 
			
			if(arquivo.getDtInicioRemessa() != null && !("").equals(arquivo.getDtInicioRemessa())){
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				Date date = sdf.parse(arquivo.getDtInicioRemessa());
				timestampIni = new Timestamp(date.getTime()); 
			}	
			if(arquivo.getDtFimRemessa() != null && !("").equals(arquivo.getDtFimRemessa())){
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
				Date date = sdf.parse(arquivo.getDtFimRemessa() + " 23:59:59");
				timestampFim = new Timestamp(date.getTime()); 
			}
			
			if(timestampIni!=null){
				p.getExpressions().add(qb.greaterThanOrEqualTo(arquivoIntegracao.<Date>get("dtEnvioArquivo"), timestampIni));
			}	
			if(timestampFim!=null){
				p.getExpressions().add(qb.lessThanOrEqualTo(arquivoIntegracao.<Date>get("dtRetornoArquivo"), timestampFim));
			}
			
		}
		
		criteria.where(p);
		
		List<ArquivoContratoTO> listaDados = em.createQuery(criteria).getResultList();
		List<ArquivoContratoResponse> listaRetorno = new ArrayList<ArquivoContratoResponse>();
		int i = 1;	
		for (ArquivoContratoTO arquivoContratoTO : listaDados) {
					
				CriteriaBuilder query = em.getCriteriaBuilder();
				CriteriaQuery<ErroArquivoContratoTO> criteriaQuery = query.createQuery(ErroArquivoContratoTO.class);
				Root<ErroArquivoContratoTO> erroArquivo = criteriaQuery.from(ErroArquivoContratoTO.class);
				
				final Path<ArquivoContratoTO> arquivoContratoErro = erroArquivo.join("arquivoContrato",JoinType.INNER);
				final Path<ErroIntegracaoTO> erroIntegracao = erroArquivo.join("erroIntegracao",JoinType.INNER);
				
				Predicate pErro = query.conjunction();
				
				if(arquivo.getSituacaoRetorno() > 0) {
					pErro.getExpressions().add(query.equal(arquivoContratoErro.get("nuArquivoContrato"), arquivoContratoTO.getNuArquivoContrato()));
				}
				
				criteriaQuery.where(pErro);
			
				List<ErroArquivoContratoTO> erros = em.createQuery(criteriaQuery).getResultList();	

				ArquivoContratoResponse arquivoResponse = new ArquivoContratoResponse();
				arquivoResponse.setIdentificador("Contrato");
				arquivoResponse.setDtRemessa(arquivoContratoTO.getArquivoIntegracao().getDtEnvioArquivo());
				arquivoResponse.setDtRetorno(arquivoContratoTO.getArquivoIntegracao().getDtRetornoArquivo());
				arquivoResponse.setQuantidadeRemessa(arquivoContratoTO.getArquivoIntegracao().getQtdeContratosEnvio());
				if(arquivoContratoTO.getArquivoIntegracao().getQtdeContratosRetorno() != null) {
					arquivoResponse.setQuantidadeRetorno(arquivoContratoTO.getArquivoIntegracao().getQtdeContratosRetorno());
				}
				arquivoResponse.setSequencial(i);
				arquivoResponse.setSituacaoRetorno(EnumSituacaoArquivo.valueById(arquivoContratoTO.getArquivoIntegracao().getSituacao().getId()));
				List<ArquivoContratoErro> listArquivoErro = new ArrayList<ArquivoContratoErro>();
				for (ErroArquivoContratoTO erroArquivoContratoTO : erros) {
					ArquivoContratoErro arquivoErro = new ArquivoContratoErro();
					arquivoErro.setCodErro(erroArquivoContratoTO.getErroIntegracao().getNuErroIntegracao());
					arquivoErro.setDescErro(erroArquivoContratoTO.getErroIntegracao().getDescErroIntegracao());
					arquivoErro.setNuContrato(arquivoContratoTO.getContrato().getNuContrato());
					listArquivoErro.add(arquivoErro);
				} 
				
				arquivoResponse.setArquivoContratoErro(listArquivoErro);	
				/**
				 * Faz a subtração da quantidade de Contratos que foram enviados
				 * com a quantidade de Contratos que foram retornados, com isso,
				 * retorna a quantidade de Contratos com erro.
				 */
				arquivoResponse.setErrosRetorno(listArquivoErro.size());
				listaRetorno.add(arquivoResponse);
				i++;
		}
		
		return listaRetorno; 
				
	}
	
	
	
	
}
