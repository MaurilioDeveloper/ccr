package br.gov.caixa.ccr.negocio;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

import br.gov.caixa.arqrefcore.log.Logging;
import br.gov.caixa.ccr.dominio.Retorno;
import br.gov.caixa.ccr.dominio.TaxaIOF;
import br.gov.caixa.ccr.dominio.transicao.TaxaIOFTO;
import br.gov.caixa.ccr.util.DataUtil;
import br.gov.caixa.ccr.util.Utilities;

@Stateless
@LocalBean
@TransactionManagement(TransactionManagementType.CONTAINER)
@Logging
public class TaxaIOFBean implements ITaxaIOFBean {

	@PersistenceContext
	private EntityManager em;
	private Retorno retorno;	
	TypedQuery<TaxaIOFTO> query = null;
	
	@Override
	public List<TaxaIOF> listar(String dataInicioVigencia, String dataFinalVigencia) throws Exception {
		 
		
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		
		if((dataInicioVigencia != null && !dataInicioVigencia.equals("")) && (dataFinalVigencia != null && !dataFinalVigencia.equals(""))){
			Date dateIni = DataUtil.converter(dataInicioVigencia, DataUtil.PADRAO_DATA);
			Date dateFin = DataUtil.converter(dataFinalVigencia, DataUtil.PADRAO_DATA);
			query = em.createNamedQuery("TaxaIOF.listarDtIniDtFim", TaxaIOFTO.class);
			query.setParameter(1, dateIni);
			query.setParameter(2, dateFin);
			
		}else if((dataInicioVigencia != null && !dataInicioVigencia.equals(""))  && (dataFinalVigencia == null || (dataFinalVigencia != null && dataFinalVigencia.equals("")))){
			Date dateIni = DataUtil.converter(dataInicioVigencia, DataUtil.PADRAO_DATA);
			query = em.createNamedQuery("TaxaIOF.listarDtIni", TaxaIOFTO.class);
			query.setParameter(1, dateIni);
			
		}else if((dataInicioVigencia == null || (dataInicioVigencia!=null && dataInicioVigencia.equals(""))) && (dataFinalVigencia != null && !dataFinalVigencia.equals(""))){
			Date dateFin = DataUtil.converter(dataFinalVigencia, DataUtil.PADRAO_DATA);
			query = em.createNamedQuery("TaxaIOF.listarDtFim", TaxaIOFTO.class);
			query.setParameter(1, dateFin);
		}else {
			query = em.createNamedQuery("TaxaIOF.listar", TaxaIOFTO.class);
		}
		
		// resultado
		List<TaxaIOFTO> listaRetorno = query.getResultList();
		List<TaxaIOF> listaTaxaIOF = new ArrayList<TaxaIOF>();
		
		Utilities.copyListClassFromTo(listaTaxaIOF, TaxaIOF.class, listaRetorno);
		
		List<TaxaIOF> retornoListaTaxaIOF = new ArrayList<TaxaIOF>();
		
		
		for (TaxaIOF taxaIOF : listaTaxaIOF) {
			
			taxaIOF.isExcludable();
			if(taxaIOF.getInicioVigencia() != null && taxaIOF.getInicioVigencia() != ""){
				taxaIOF.setDtInicioVigencia(format.parse(taxaIOF.getInicioVigencia()));
			}
			
			if(taxaIOF.getFimVigencia() != null && taxaIOF.getFimVigencia() != ""){
				taxaIOF.setDtFinalVigencia(format.parse(taxaIOF.getFimVigencia()));
			}
						
			retornoListaTaxaIOF.add(taxaIOF);
		}
		
		return retornoListaTaxaIOF;
	}
	
	@Override
	public List<TaxaIOF> listar(String dataInicioVigencia) throws Exception {
		// cria a query
		TypedQuery<TaxaIOFTO> query = null;
		if (dataInicioVigencia != null) {
			query = em.createNamedQuery("TaxaIOF.listarPorInicioVigencia", TaxaIOFTO.class);
			Date date = DataUtil.converter(dataInicioVigencia, DataUtil.PADRAO_DATA_BARRAMENTO);
			query.setParameter("pInicioVigencia", date);
		} else {
			query = em.createNamedQuery("TaxaIOF.listar", TaxaIOFTO.class);
		}
		
		// resultado
		List<TaxaIOFTO> listaRetorno = query.getResultList();
		List<TaxaIOF> listaTaxaIOF = new ArrayList<TaxaIOF>();
		
		Utilities.copyListClassFromTo(listaTaxaIOF, TaxaIOF.class, listaRetorno);
		
		return listaTaxaIOF;
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Retorno salvar(TaxaIOF taxaIOF, String codigoUsuario) throws Exception {
		TypedQuery<TaxaIOFTO> query;
		TaxaIOFTO taxaIOFRef = null;
		Date dataInicioVigenciaAntiga = null;
		Calendar hoje = Calendar.getInstance();
		
		retorno = new Retorno();
		
		
		if (taxaIOF.getId() == 0) {
			//Valida somente na inclusão
			if (existeVigenciaPeriodo(taxaIOF)){
				retorno.setAll(1L, "", Retorno.ERRO_NEGOCIAL, "MA0052");
				return retorno;
			}
			
			// cria a Query
			query = em.createNamedQuery("TaxaIOF.incluir", TaxaIOFTO.class);
			
			// passa parametros
			query.setParameter("pAliquotaBasica", taxaIOF.getAliquotaBasica());
			query.setParameter("pAliquotaComplementar", taxaIOF.getAliquotaComplementar());
			query.setParameter("pInicioVigencia", DataUtil.converter(taxaIOF.getInicioVigencia(), DataUtil.PADRAO_DATA_ISO));
			query.setParameter("pCoUsuarioInclusao", codigoUsuario.toUpperCase());
			query.setParameter("pTsInclusao", hoje.getTime());
			
			retorno.setIdMsg("MA003");
		} else {
			// pega a referencia da classe que esta no banco
			taxaIOFRef = em.find(TaxaIOFTO.class, taxaIOF.getId());
			
			TaxaIOF taxaRef = new TaxaIOF(); 
			
			Utilities.copyAttributesFromTo(taxaRef, taxaIOFRef);
			dataInicioVigenciaAntiga = taxaIOFRef.getInicioVigencia();
			
			if (taxaRef.isUpdatable()) {			
				// cria a Query
				query = em.createNamedQuery("TaxaIOF.atualizar", TaxaIOFTO.class);
				
				// passa parametros
				query.setParameter("pID", taxaIOF.getId());
				query.setParameter("pAliquotaBasica", taxaIOF.getAliquotaBasica());
				query.setParameter("pAliquotaComplementar", taxaIOF.getAliquotaComplementar());
				query.setParameter("pInicioVigencia", DataUtil.converter(taxaIOF.getInicioVigencia(),DataUtil.PADRAO_DATA_ISO));
				//query.setParameter("pCoUsuarioInclusao", codigoUsuario.toUpperCase());
				//query.setParameter("pTsInclusao", hoje.getTime());
				
				retorno.setIdMsg("MA003");
			} else {
				retorno.setAll(2L, "", Retorno.ERRO_NEGOCIAL, "MN001");
				return retorno;
			}
		}
		
		try {
			// se for inclusao, atualiza a taxa vigente anterior com o final da vigencia
			// se for alteracao, o fim da vigencia da taxa anterior deve ser SEMPRE um dia antes do inicio da taxa vigente
			if (taxaIOF.getId() == 0)
				fecharVigenciaAberta(DataUtil.converter(taxaIOF.getInicioVigencia(), DataUtil.PADRAO_DATA_ISO), codigoUsuario);
			else
				atualizarVigenciaFechada(dataInicioVigenciaAntiga, taxaIOFRef.getInicioVigencia(), codigoUsuario);
			
			query.executeUpdate();
		} catch (Exception e) {
			retorno.setAll(1L, e.getMessage(), Retorno.ERRO_EXCECAO, "");
		}

		return retorno;
	}
	
	private boolean existeVigenciaPeriodo(TaxaIOF taxaIOF) throws ParseException {
		TypedQuery<TaxaIOFTO> queryEVP;
		
		String queryName = "TaxaIOF.verificaVigenciaValidaIncSemFim";
		queryEVP = em.createNamedQuery(queryName, TaxaIOFTO.class);
		
		queryEVP.setParameter("pInicioVigencia", DataUtil.converter(taxaIOF.getInicioVigencia(), DataUtil.PADRAO_DATA_ISO));
		
		if(queryEVP.getResultList().size() > 0)
			return true;
				
		return false;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Retorno excluir(Integer id) throws Exception {
		TaxaIOFTO taxaIOFRef = em.find(TaxaIOFTO.class, id);
		TaxaIOF taxaRef = new TaxaIOF();		
		Utilities.copyAttributesFromTo(taxaRef, taxaIOFRef);
		
		retorno = new Retorno();
		
		if (taxaRef.isUpdatable()) {
			try {
				Date inicioVigencia = taxaIOFRef.getInicioVigencia();
				em.remove(taxaIOFRef);
				
				// retira o final da vigencia da taxa anterior
				reabrirVigencia(inicioVigencia);
				
				retorno.setIdMsg("MA003");
			} catch (Exception e) {				
				retorno.setAll(1L, e.getMessage(), Retorno.ERRO_EXCECAO, "");
			}
		} else {
			retorno.setAll(2L, "", Retorno.ERRO_NEGOCIAL, "MN002");
		}
		
		return retorno;
	}
	
	@Override
	public TaxaIOF findVigente() throws Exception {
		
		TaxaIOF taxaIOF = new TaxaIOF();
		
		// cria a Query
		TypedQuery<TaxaIOFTO> queryFV = em.createNamedQuery("TaxaIOF.findVigente", TaxaIOFTO.class);
		
		// resultado
		List<TaxaIOFTO> listaRetorno = queryFV.getResultList();
		if (listaRetorno.isEmpty())
			return null;
		
		TaxaIOFTO to = listaRetorno.get(0);		
		Utilities.copyAttributesFromTo(taxaIOF, to);
		
		return taxaIOF;
	}

	/* METODOS QUE SAO ACESSADOS INTERNAMENTE */	
	private void fecharVigenciaAberta(Date dataInicioVigencia, String codigoUsuario) throws Exception{
		TypedQuery<TaxaIOFTO> queryFVA;
		Calendar cal = Calendar.getInstance();
		String dataFimVigencia;
		
		cal.setTime(dataInicioVigencia);
		cal.set(Calendar.DATE, cal.get(Calendar.DATE) - 1);
		cal.set(Calendar.HOUR, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		dataFimVigencia = DataUtil.formatar(cal.getTime(), DataUtil.PADRAO_DATA_HORA_COMPLETA_ISO);
		
		queryFVA = em.createNamedQuery("TaxaIOF.fecharVigenciaAberta", TaxaIOFTO.class);
		queryFVA.setParameter("pFimVigencia", DataUtil.converter(dataFimVigencia, DataUtil.PADRAO_DATA_HORA_COMPLETA_ISO));
		queryFVA.setParameter("pCoUsuarioFinalizacao", codigoUsuario.toUpperCase());
		queryFVA.setParameter("pTsInclusaoFimVigencia", Calendar.getInstance().getTime());
		
		queryFVA.executeUpdate();
	}
	
	private void atualizarVigenciaFechada(Date dataInicioVigenciaAntiga, Date dataInicioVigenciaNova, String codigoUsuario) throws Exception {
		TypedQuery<TaxaIOFTO> queryAVF;
		Calendar cal = Calendar.getInstance();		
		String dataFimVigenciaAntiga, dataFimVigenciaNova;
		
		cal.setTime(dataInicioVigenciaAntiga);
		cal.set(Calendar.DATE, cal.get(Calendar.DATE) - 1);
		cal.set(Calendar.HOUR, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		dataFimVigenciaAntiga = DataUtil.formatar(cal.getTime(), DataUtil.PADRAO_DATA_HORA_COMPLETA_ISO);
		
		cal.setTime(dataInicioVigenciaNova);
		cal.set(Calendar.DATE, cal.get(Calendar.DATE) - 1);
		cal.set(Calendar.HOUR, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		dataFimVigenciaNova = DataUtil.formatar(cal.getTime(), DataUtil.PADRAO_DATA_HORA_COMPLETA_ISO);
		
		// se eh a mesma data nao precisa atualizar no banco
		if (dataFimVigenciaAntiga.equals(dataFimVigenciaNova))
			return;
		
		queryAVF = em.createNamedQuery("TaxaIOF.atualizarVigenciaFechada", TaxaIOFTO.class);
		queryAVF.setParameter("pFimVigenciaAnterior", dataFimVigenciaAntiga);
		queryAVF.setParameter("pFimVigenciaAtual", dataFimVigenciaNova);
		queryAVF.setParameter("pCoUsuarioFinalizacao", codigoUsuario.toUpperCase());
		queryAVF.setParameter("pTsInclusaoFimVigencia", cal.getTime());
		
		queryAVF.executeUpdate();		
	}
	
	private void reabrirVigencia(Date dataInicioVigencia) throws Exception {
		TypedQuery<TaxaIOFTO> queryRV;
		Calendar cal = Calendar.getInstance();
		String dataFimVigencia;
		
		cal.setTime(dataInicioVigencia);
		cal.set(Calendar.DATE, cal.get(Calendar.DATE) - 1);
		cal.set(Calendar.HOUR, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		dataFimVigencia = DataUtil.formatar(cal.getTime(), DataUtil.PADRAO_DATA_HORA_COMPLETA_ISO);
		
		queryRV = em.createNamedQuery("TaxaIOF.atualizarVigenciaFechada", TaxaIOFTO.class);
		queryRV.setParameter("pFimVigenciaAnterior", DataUtil.converter(dataFimVigencia,DataUtil.PADRAO_DATA_HORA_COMPLETA_ISO));
		
		queryRV.executeUpdate();
	}
	
}
