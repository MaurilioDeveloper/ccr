package br.gov.caixa.ccr.negocio;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

import br.gov.caixa.ccr.dominio.Retorno;
import br.gov.caixa.ccr.dominio.TaxaJuro;
import br.gov.caixa.ccr.dominio.transicao.TaxaJuroConvenioTO;
import br.gov.caixa.ccr.dominio.transicao.TaxaJuroGrupoTO;
import br.gov.caixa.ccr.util.DataUtil;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class TaxaJuroBean implements ITaxaJuroBean  {

	@PersistenceContext
	private EntityManager em;
			
	// Retorna lista generica pois o retorno poder ser da Classe TaxaJurosGrupo ou TaxaJurosConvenio
	@Override
	public List<TaxaJuro> listar(String tipoConsulta, int codigo,String utilizarEm, String dataInicial,String dataFinal, String convenio) throws Exception {
		
		List<TaxaJuro> listaTaxaJuro = null;
		
		//1-Concess&atilde;o
		//2-Renova&ccedil;&atilde;o
		
		
		String utilizarEmRetorno  = (utilizarEm.equals(TaxaJuro.CONTRATACAO) ? "Contratação": "Renovação");
		if(utilizarEm != null && !utilizarEm.isEmpty()) utilizarEm  = (utilizarEm.equals(TaxaJuro.CONTRATACAO) ? "1": "2"); 
		
		//String utilizarEmRetorno  = (utilizarEm.equals("1") ? "Contratação": "Renovação");
		
		if (tipoConsulta.equals(TaxaJuro.GRUPO)) {
			TypedQuery<TaxaJuroGrupoTO> query = buscaPorGrupo(codigo, utilizarEm, dataInicial, dataFinal);
		    
		    // resultado
			listaTaxaJuro = agrupaTaxas(query.getResultList(), TaxaJuro.GRUPO, utilizarEmRetorno);			
		} else if (tipoConsulta.equals(TaxaJuro.CONVENIO)) {
			// cria a query
			if(convenio!=null && !convenio.isEmpty() ){
				TypedQuery<TaxaJuroConvenioTO> query = buscaPorConvenio(Integer.parseInt(convenio), utilizarEm, dataInicial, dataFinal);

				// resultado
				listaTaxaJuro = agrupaTaxas(query.getResultList(), TaxaJuro.CONVENIO, utilizarEmRetorno);
			}
		}
		
		
		//ordenacao decrescente.
		Collections.sort (listaTaxaJuro, ordenarDescrescente());
		return listaTaxaJuro;
	}


	private Comparator<TaxaJuro> ordenarDescrescente() {
		return new Comparator<TaxaJuro>() {
				@Override
				public int compare(TaxaJuro o1, TaxaJuro o2) {
					final TaxaJuro p1 = (TaxaJuro) o1;
	            	final TaxaJuro p2 = (TaxaJuro) o2;
	                return p1.getDtInicioVigencia().after(p2.getDtInicioVigencia()) ? -1 : (p1.getDtInicioVigencia().before(p2.getDtInicioVigencia()) ? +1 : 0);
	            
				}
	        };
	}

	private TypedQuery<TaxaJuroConvenioTO> buscaPorConvenio(int codigo, String utilizarEm, String dataInicial,
			String dataFinal) {
		
		
		// cria a query
		TypedQuery<TaxaJuroConvenioTO> query = null;
		//query = em.createNamedQuery("TaxaJuroGrupo.listarTodas", TaxaJuroGrupoTO.class);
		String sql="SELECT T.* "
				+ "  FROM CCR.CCRTB006_TAXA_JURO_CONVENIO T "
				+ " WHERE 1=1 ";
		
		if(codigo>0){
			sql+=" and  T.NU_CONVENIO = :pCodigoConvenio ";
		}
		if(!("").equals(utilizarEm)){
			sql+=" and T.IC_UTILIZACAO_TAXA = :pUtilizar ";
		}
		if(!("").equals(dataInicial)){
			sql+=" and DT_INICIO_VIGENCIA  <= to_date(:pDataInicial,'DD/MM/YYYY')  ";
		}
		if(!("").equals(dataFinal)){
			sql+=" and (DT_FIM_VIGENCIA >= to_date(:pDataFinal,'DD/MM/YYYY')  OR DT_FIM_VIGENCIA is null)";
		}

		query = (TypedQuery<TaxaJuroConvenioTO>) em.createNativeQuery(sql,  TaxaJuroConvenioTO.class);
		
		if(codigo>0){
			query.setParameter("pCodigoConvenio", new Long(codigo));
		}
		if(!("").equals(utilizarEm)){
		   	query.setParameter("pUtilizar", utilizarEm);
		}
		if(!("").equals(dataInicial)){
		   	query.setParameter("pDataInicial", dataInicial);
		}
		if(!("").equals(dataFinal)){
		   	query.setParameter("pDataFinal", dataFinal);
		}
		return query;
	}
	
	private TypedQuery<TaxaJuroGrupoTO> buscaPorGrupo(int codigo, String utilizarEm, String dataInicial,
			String dataFinal) {
		// cria a query
		TypedQuery<TaxaJuroGrupoTO> query = null;
		//query = em.createNamedQuery("TaxaJuroGrupo.listarTodas", TaxaJuroGrupoTO.class);
		String sql="SELECT T.* "
				+ " FROM CCR.CCRTB005_TAXA_JURO_GRUPO T "
				+ " LEFT JOIN CCR.CCRTB002_GRUPO_TAXA GT ON T.NU_GRUPO = GT.NU_GRUPO_TAXA"
				+ " WHERE 1=1 ";
		
		if(codigo>0){
			sql+=" and  GT.NU_GRUPO_TAXA = :pCodigoGrupo ";
		}
		if(!("").equals(utilizarEm)){
			sql+=" and T.IC_UTILIZACAO_TAXA = :pUtilizar ";
		}
		if(!("").equals(dataInicial)){
			sql+=" and DT_INICIO_VIGENCIA  <= to_date(:pDataInicial,'DD/MM/YYYY') ";
		}
		if(!("").equals(dataFinal)){
			sql+=" and (DT_FIM_VIGENCIA >= to_date(:pDataFinal,'DD/MM/YYYY') OR DT_FIM_VIGENCIA is null) ";
		}

		query = (TypedQuery<TaxaJuroGrupoTO>) em.createNativeQuery(sql,  TaxaJuroGrupoTO.class);
		
		if(codigo>0){
			query.setParameter("pCodigoGrupo", new Long(codigo));
		}
		if(!("").equals(utilizarEm)){
		   	query.setParameter("pUtilizar", utilizarEm);
		}
		if(!("").equals(dataInicial)){
		   	query.setParameter("pDataInicial", dataInicial);
		}
		if(!("").equals(dataFinal)){
		   	query.setParameter("pDataFinal", dataFinal);
		}
		return query;
	}
	
	@Override
	public TaxaJuro findVigente(int codigoConvenio, int codigoGrupo, int prazo) throws Exception {
		String tipoConsulta = codigoConvenio > 0 ? TaxaJuro.CONVENIO : TaxaJuro.GRUPO;
		
		TaxaJuro taxaJuro = null;
		
		if (tipoConsulta.equals(TaxaJuro.GRUPO)) {
			// cria a query
			TypedQuery<TaxaJuroGrupoTO> query = em.createNamedQuery("TaxaJuroGrupo.findVigente", TaxaJuroGrupoTO.class);
			query.setParameter("pCodigo", codigoGrupo);
			query.setParameter("pPrazo", prazo);
			
			// resultado
			List<TaxaJuroGrupoTO> listaRetorno = query.getResultList();
			
			if (listaRetorno.isEmpty())
				return null;
			
			taxaJuro = copyTaxaEspecificaToGenerica(listaRetorno.get(0), tipoConsulta);
			
		} else if (tipoConsulta.equals(TaxaJuro.CONVENIO)) {
			// cria a query
			TypedQuery<TaxaJuroConvenioTO> query = em.createNamedQuery("TaxaJuroConvenio.findVigente", TaxaJuroConvenioTO.class);
			query.setParameter("pCodigo", codigoConvenio);
			query.setParameter("pPrazo", prazo);
			
			// resultado
			List<TaxaJuroConvenioTO> listaRetorno = query.getResultList();			
			
			if (listaRetorno.isEmpty())
				return null;
			
			taxaJuro = copyTaxaEspecificaToGenerica(listaRetorno.get(0), tipoConsulta);
		}
		
		return taxaJuro;
	}
	
	@Override
	public Retorno salvar(TaxaJuro taxaJuro, String inicioVigenciaChave, String coUsuario)
			throws Exception {
		
		Retorno retorno = new Retorno();
		
		try {

			Calendar cal = Calendar.getInstance();
			cal.setTime(taxaJuro.dateInicioVigencia());
			cal.set(Calendar.DATE, cal.get(Calendar.DATE) - 1);

			if(validarTaxaJuro(taxaJuro, retorno) == false){
				return retorno;
			}
			
			// incluir
			if (taxaJuro.getId() == 0) {
				//verifica se existe um registro vigente
				if (existeVigenciaPeriodo(taxaJuro)){
					retorno.setAll(1L, "", Retorno.ERRO_NEGOCIAL, "MN003");
					return retorno;
				}
				
				atualizarVigencia(taxaJuro, DataUtil.formatar(cal.getTime(), DataUtil.PADRAO_DATA_ISO),	coUsuario);
				incluiTaxas(taxaJuro, coUsuario);
				retorno.setIdMsg("MA005"); //operacao realizada com sucesso
			} else {
				if (taxaJuro.isUpdatable()) {					
					
					// verifica se a vigencia ja existe
					if (existeVigenciaPeriodo(taxaJuro, inicioVigenciaChave)){
						retorno.setAll(1L, "", Retorno.ERRO_NEGOCIAL, "MN004");
						return retorno;
					}
					
					//metodo que faz o update das informacoes quando for uma alteraçao
					alteraTaxas(taxaJuro, inicioVigenciaChave, coUsuario);
					
					//verifica se um dos prazos foram alterados
					if (taxaJuro.getPrazoMax() != taxaJuro.getPrazoMaxOld() || taxaJuro.getPrazoMin() != taxaJuro.getPrazoMinOld() ) {
						atualizarVigenciaDelete(taxaJuro, DataUtil.formatar(cal.getTime(), DataUtil.PADRAO_DATA_ISO),	coUsuario);					
						incluiTaxas(taxaJuro, coUsuario);
					}
															
					retorno.setIdMsg("MA005"); 
				}
			}

		} catch (Exception e) {
			retorno.setAll(1L, e.getMessage(), Retorno.ERRO_EXCECAO, "");
		}

		return retorno;
	}
	
	@Override
	public Retorno excluir(TaxaJuro taxaJuro) throws Exception {
		Retorno retorno = new Retorno();
		
		try {			
			if (taxaJuro.isExcludable()) {				
				TypedQuery<?> query;
				
				// APAGA PELO RANGE DO PRAZO
				if (taxaJuro.tipoConsultaIsGrupo())			 
					query = em.createNamedQuery("TaxaJuroGrupo.excluirRange", TaxaJuroGrupoTO.class);
				else
					query = em.createNamedQuery("TaxaJuroConvenio.excluirRange", TaxaJuroConvenioTO.class);
				
				query.setParameter("pCodigo", taxaJuro.getCodigoTaxa()); // codigo do grupo ou do convenio
				query.setParameter("pPrazoMin", taxaJuro.getPrazoMin());
				query.setParameter("pPrazoMax", taxaJuro.getPrazoMax());				
				query.setParameter("pInicioVigencia", DataUtil.converter(taxaJuro.getInicioVigencia(), DataUtil.PADRAO_DATA_ISO));
				query.setParameter("pTipo", taxaJuro.getTipoTaxa());
				
				query.executeUpdate();
								
				retorno.setIdMsg("MA005");
			} else {
				retorno.setAll(1L, "", Retorno.ERRO_NEGOCIAL, "MN002");
			}
		} catch (Exception e) {
			retorno.setAll(1L, e.getMessage(), Retorno.ERRO_EXCECAO, "");
		}
		
		return retorno;
	}
	
	/* METODOS QUE SAO ACESSADOS INTERNAMENTE */
	
	private boolean validarTaxaJuro(TaxaJuro taxaJuro, Retorno retorno) throws Exception{
		if(taxaJuro.getPrazoMin() < 1){
			retorno.setAll(1L, "", Retorno.ERRO_NEGOCIAL, "MA0054");
			return false;
		} else if(taxaJuro.getPrazoMin() > taxaJuro.getPrazoMax()){
			retorno.setAll(1l, "", Retorno.ERRO_NEGOCIAL, "MA0053");
			return false;
		}
		return true;
	}
	
	private List<TaxaJuro> agrupaTaxas(List<?> lista, String tipoCons, String utilizarEm) throws Exception {
		List<TaxaJuro> listaTaxaJuro = new ArrayList<TaxaJuro>();
		int count = 0;

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		
		for (int i=0; i < lista.size(); i++) {
			Date inicioVigencia, fimVigencia, inclusaoTaxa, inclusaoFimVigencia;
			int prazoMin = 999, prazoMax = 0, aux = 0;
			Long codigo = 0L;
			short tipo;
			double pcMinimo, pcMedio, pcMaximo;
			String usuarioInclusao, usuarioFinalizacao;
			
			Boolean continua = true;
			Object _this = lista.get(i);
						
			// para agrupar estes valores devem ser iguais
			codigo = tipoCons.equals(TaxaJuro.GRUPO) ? ((TaxaJuroGrupoTO) _this).getGrupo().getId() : ((TaxaJuroConvenioTO) _this).getConvenio().getId();
			inicioVigencia = tipoCons.equals(TaxaJuro.GRUPO) ? ((TaxaJuroGrupoTO) _this).getInicioVigencia() : ((TaxaJuroConvenioTO) _this).getInicioVigencia();
			fimVigencia = tipoCons.equals(TaxaJuro.GRUPO) ? ((TaxaJuroGrupoTO) _this).getFimVigencia() : ((TaxaJuroConvenioTO) _this).getFimVigencia();
			pcMinimo = tipoCons.equals(TaxaJuro.GRUPO) ? ((TaxaJuroGrupoTO) _this).getPcMinimo() : ((TaxaJuroConvenioTO) _this).getPcMinimo();
			pcMedio = tipoCons.equals(TaxaJuro.GRUPO) ? ((TaxaJuroGrupoTO) _this).getPcMedio() : ((TaxaJuroConvenioTO) _this).getPcMedio();
			pcMaximo = tipoCons.equals(TaxaJuro.GRUPO) ? ((TaxaJuroGrupoTO) _this).getPcMaximo() : ((TaxaJuroConvenioTO) _this).getPcMaximo();
			tipo = tipoCons.equals(TaxaJuro.GRUPO) ? ((TaxaJuroGrupoTO) _this).getTipo() : ((TaxaJuroConvenioTO) _this).getTipo();
			usuarioInclusao = tipoCons.equals(TaxaJuro.GRUPO) ? ((TaxaJuroGrupoTO) _this).getUsuarioInclusao() : ((TaxaJuroConvenioTO) _this).getUsuarioInclusao();
			inclusaoTaxa = tipoCons.equals(TaxaJuro.GRUPO) ? ((TaxaJuroGrupoTO) _this).getDataInclusaoTaxa() : ((TaxaJuroConvenioTO) _this).getDataInclusaoTaxa();
			usuarioFinalizacao = tipoCons.equals(TaxaJuro.GRUPO) ? ((TaxaJuroGrupoTO) _this).getUsuarioFinalizacao() : ((TaxaJuroConvenioTO) _this).getUsuarioFinalizacao();
			inclusaoFimVigencia = tipoCons.equals(TaxaJuro.GRUPO) ? ((TaxaJuroGrupoTO) _this).getDataInclusaoFimVigencia() : ((TaxaJuroConvenioTO) _this).getDataInclusaoFimVigencia();
			
			do {
				int prazo = tipoCons.equals(TaxaJuro.GRUPO) ? ((TaxaJuroGrupoTO) _this).getPrazo() : ((TaxaJuroConvenioTO) _this).getPrazo(); 
				prazoMin = prazo < prazoMin ? prazo : prazoMin;
				prazoMax= prazo > prazoMax ? prazo : prazoMax;
								
				aux = i + 1;
				if (aux >= lista.size()) {
					continua = false;
				} else {
					Object that = lista.get(aux); // proximo objeto
					
					if (tipoCons.equals(TaxaJuro.GRUPO)) {
						// mesmos valores - forca a utilização do equals da classe
						if (((TaxaJuroGrupoTO) _this).equals(that))
							_this = lista.get(++i);
						else 
							continua = false;
					} else {
						// mesmos valores - forca a utilização do equals da classe
						if (((TaxaJuroConvenioTO) _this).equals(that))
							_this = lista.get(++i);
						else // mesmos valores
							continua = false;
					}
				}
			} while (continua);
			
			TaxaJuro taxaJuro = new TaxaJuro();			
			taxaJuro.setId(++count);
			taxaJuro.setCodigoTaxa(codigo);
			taxaJuro.setTipoConsulta(tipoCons);
			taxaJuro.setDateInicioVigencia(inicioVigencia);
			taxaJuro.setDateFimVigencia(fimVigencia);
			
			if(taxaJuro.getInicioVigencia() != null && taxaJuro.getInicioVigencia() != ""){
				taxaJuro.setDtInicioVigencia(format.parse(taxaJuro.getInicioVigencia()));
			}
			
			if(taxaJuro.getFimVigencia() != null && taxaJuro.getFimVigencia() != ""){
				taxaJuro.setDtFimVigencia(format.parse(taxaJuro.getFimVigencia()));
			}
			
			taxaJuro.setTipoTaxa(tipo);
			taxaJuro.setPrazoMin(prazoMin);
			taxaJuro.setPrazoMax(prazoMax);
			taxaJuro.setPcMinimo(pcMinimo);
			taxaJuro.setPcMedio(pcMedio);
			taxaJuro.setPcMaximo(pcMaximo);
			taxaJuro.setUsuarioInclusao(usuarioInclusao);
			taxaJuro.setDateInclusaoTaxa(inclusaoTaxa);
			taxaJuro.setUsuarioFinalizacao(usuarioFinalizacao);
			taxaJuro.setDateInclusaoFimVigencia(inclusaoFimVigencia);
			taxaJuro.setUtilizarEm(utilizarEm);
			
			listaTaxaJuro.add(taxaJuro);
		}
				
		return listaTaxaJuro;
	}
	
	private void incluiTaxas(TaxaJuro taxaJuro, String coUsuario) throws Exception {
		TypedQuery<?> query;
		Calendar hoje = Calendar.getInstance();
		
		if (taxaJuro.tipoConsultaIsGrupo())
			query = em.createNamedQuery("TaxaJuroGrupo.incluir", TaxaJuroGrupoTO.class);
		else
			query = em.createNamedQuery("TaxaJuroConvenio.incluir", TaxaJuroConvenioTO.class);
			
		// valores iguais para todos os prazos
		query.setParameter("pCodigo", taxaJuro.getCodigoTaxa()); // codigo do grupo ou do convenio
		query.setParameter("pTipo", taxaJuro.getTipoTaxa());
		query.setParameter("pInicioVigencia", DataUtil.converter(taxaJuro.getInicioVigencia(), DataUtil.PADRAO_DATA_ISO));
		query.setParameter("pPcMinimo", taxaJuro.getPcMinimo());
		query.setParameter("pPcMedio", taxaJuro.getPcMedio());
		query.setParameter("pPcMaximo", taxaJuro.getPcMaximo());
		query.setParameter("pCoUsuarioInclusao", coUsuario);
		query.setParameter("pTsInclusao", hoje.getTime());
		
		query.setParameter("pFimVigencia", (Date) null, TemporalType.DATE);
		query.setParameter("pCoUsuarioFinalizacao", null);
		query.setParameter("pTsInclusaoFimVigencia", (Date) null, TemporalType.DATE);
		
		// itera prazo por prazo e insere na base
		for (int prazo=taxaJuro.getPrazoMin(); prazo<=taxaJuro.getPrazoMax(); prazo++){
			query.setParameter("pPrazo", prazo);
			query.executeUpdate();
		}		
	}
	
	private void alteraTaxas(TaxaJuro taxaJuro, String inicioVigenciaChave, String coUsuario) throws Exception {
		//Calendar hoje = Calendar.getInstance();
		TypedQuery<?> query;
		
			// soh nao altera a faixa do prazo
			if (taxaJuro.tipoConsultaIsGrupo())
				query = em.createNamedQuery("TaxaJuroGrupo.atualizar", TaxaJuroGrupoTO.class);
			else
				query = em.createNamedQuery("TaxaJuroConvenio.atualizar", TaxaJuroConvenioTO.class);
			
			query.setParameter("pCodigo", taxaJuro.getCodigoTaxa()); // codigo do grupo ou do convenio
			query.setParameter("pTipo", taxaJuro.getTipoTaxa());
			query.setParameter("pPrazoMin", taxaJuro.getPrazoMin());
			query.setParameter("pPrazoMax", taxaJuro.getPrazoMax());				
			query.setParameter("pPcMinimo", taxaJuro.getPcMinimo());
			query.setParameter("pPcMedio", taxaJuro.getPcMedio());
			query.setParameter("pPcMaximo", taxaJuro.getPcMaximo());			
			query.setParameter("pInicioVigenciaAnterior", DataUtil.converter(inicioVigenciaChave, DataUtil.PADRAO_DATA_ISO));
			
			query.executeUpdate();
	}
	
	private Boolean existeVigenciaPeriodo(TaxaJuro taxaJuro) throws Exception {
		TypedQuery<?> query;
		
		if (taxaJuro.tipoConsultaIsGrupo()) {
			String queryName = "TaxaJuroGrupo.verificaVigenciaValidaIncSemFim";
			query = em.createNamedQuery(queryName, TaxaJuroGrupoTO.class);
		} else {
			String queryName = "TaxaJuroConvenio.verificaVigenciaValidaIncSemFim";
			query = em.createNamedQuery(queryName, TaxaJuroConvenioTO.class);			
		}
		
		query.setParameter("pCodigo", taxaJuro.getCodigoTaxa()); // codigo do grupo ou do convenio
		query.setParameter("pTipo", taxaJuro.getTipoTaxa());
		query.setParameter("pPrazoMin", taxaJuro.getPrazoMin());
		query.setParameter("pPrazoMax", taxaJuro.getPrazoMax());
		query.setParameter("pInicioVigencia", DataUtil.converter(taxaJuro.getInicioVigencia(), DataUtil.PADRAO_DATA_ISO));
		
		if (taxaJuro.possuiFimVigencia())
			//query.setParameter("pFimVigencia", DataUtil.converter(taxaJuro.getFimVigencia(), DataUtil.PADRAO_DATA_ISO));
		
		if(query.getResultList().size() > 0)
			return true;
		
		return false;
	}
	
	private Boolean existeVigenciaPeriodo(TaxaJuro taxaJuro, String inicioVigenciaChave) throws Exception {		
		TypedQuery<?> query;
		
		if (taxaJuro.tipoConsultaIsGrupo()) {
			String queryName = "TaxaJuroGrupo.verificaVigenciaValidaSemFim";
			query = em.createNamedQuery(queryName, TaxaJuroGrupoTO.class);
		} else {
			String queryName = "TaxaJuroConvenio.verificaVigenciaValidaSemFim";
			query = em.createNamedQuery(queryName, TaxaJuroConvenioTO.class);			
		}
		
		query.setParameter("pCodigo", taxaJuro.getCodigoTaxa()); // codigo do grupo ou do convenio
		query.setParameter("pTipo", taxaJuro.getTipoTaxa());
		query.setParameter("pInicioVigenciaChave", DataUtil.converter(inicioVigenciaChave, DataUtil.PADRAO_DATA_ISO));
		query.setParameter("pPrazoMin", taxaJuro.getPrazoMin());
		query.setParameter("pPrazoMax", taxaJuro.getPrazoMax());
		query.setParameter("pInicioVigencia", DataUtil.converter(taxaJuro.getInicioVigencia(), DataUtil.PADRAO_DATA_ISO));
		
		if (taxaJuro.possuiFimVigencia())
			//query.setParameter("pFimVigencia", DataUtil.converter(taxaJuro.getFimVigencia(), DataUtil.PADRAO_DATA_ISO));
				
		if(query.getResultList().size() > 0)
			return true;
		
		return false;
	}
	
	// So atualiza a vigencia final se esta estiver nula
	private void atualizarVigencia(TaxaJuro taxaJuro, String fimVigenciaAtualizada, String coUsuario) throws Exception {
		TypedQuery<?> query;
		Calendar cal = Calendar.getInstance();
		
		if (taxaJuro.tipoConsultaIsGrupo())
			query = em.createNamedQuery("TaxaJuroGrupo.atualizarVigencia", TaxaJuroGrupoTO.class);
		else
			query = em.createNamedQuery("TaxaJuroConvenio.atualizarVigencia", TaxaJuroConvenioTO.class);

		query.setParameter("pCodigo", taxaJuro.getCodigoTaxa()); // codigo do grupo ou do convenio
		query.setParameter("pFimVigencia", dataFimVigenciaComHoras(fimVigenciaAtualizada));
		query.setParameter("pCoUsuarioFinalizacao", coUsuario);
		query.setParameter("pTsInclusaoFimVigencia", (Date) cal.getTime(), TemporalType.DATE);
		query.setParameter("pTipo", taxaJuro.getTipoTaxa());

		for (int prazo=taxaJuro.getPrazoMin(); prazo<=taxaJuro.getPrazoMax(); prazo++){
			query.setParameter("pPrazo", prazo);
			query.executeUpdate();
		}
	}
	
	// So atualiza a vigencia final se esta estiver nula
	private void atualizarVigenciaDelete(TaxaJuro taxaJuro, String fimVigenciaAtualizada, String coUsuario) throws Exception {
		TypedQuery<?> query;
		
		if (taxaJuro.tipoConsultaIsGrupo())
			query = em.createNamedQuery("TaxaJuroGrupo.atualizarVigenciaDelete", TaxaJuroGrupoTO.class);
		else
			query = em.createNamedQuery("TaxaJuroConvenio.atualizarVigenciaDelete", TaxaJuroConvenioTO.class);

		query.setParameter("pCodigo", taxaJuro.getCodigoTaxa()); // codigo do grupo ou do convenio
		query.setParameter("pTipo", taxaJuro.getTipoTaxa());
		
		//deleta os registros editados
		for (int prazo=taxaJuro.getPrazoMinOld(); prazo<=taxaJuro.getPrazoMaxOld(); prazo++){
			query.setParameter("pPrazo", prazo);
			query.executeUpdate();
		}			
	}
	
	private <T> TaxaJuro copyTaxaEspecificaToGenerica(T taxa, String tipo) throws Exception {
		TaxaJuro taxaJuro = new TaxaJuro();
		
		if (tipo.equals(TaxaJuro.GRUPO)) {
			TaxaJuroGrupoTO to = (TaxaJuroGrupoTO) taxa;
			
			taxaJuro.setId(1);
			taxaJuro.setCodigoTaxa(to.getGrupo().getId());
			taxaJuro.setTipoConsulta(tipo);
			taxaJuro.setDateInicioVigencia(to.getInicioVigencia());
			taxaJuro.setDateFimVigencia(to.getFimVigencia());
			taxaJuro.setTipoTaxa(to.getTipo());
			taxaJuro.setPrazoMin(to.getPrazo());
			taxaJuro.setPrazoMax(to.getPrazo());
			taxaJuro.setPcMinimo(to.getPcMinimo());
			taxaJuro.setPcMedio(to.getPcMedio());
			taxaJuro.setPcMaximo(to.getPcMaximo());
			taxaJuro.setUsuarioInclusao(to.getUsuarioInclusao());
			taxaJuro.setDateInclusaoTaxa(to.getDataInclusaoTaxa());
			taxaJuro.setUsuarioFinalizacao(to.getUsuarioFinalizacao());
			taxaJuro.setDateInclusaoFimVigencia(to.getDataInclusaoFimVigencia());			
		} else {
			TaxaJuroConvenioTO to = (TaxaJuroConvenioTO) taxa;
			
			taxaJuro.setId(1);
			taxaJuro.setCodigoTaxa(to.getConvenio().getId());
			taxaJuro.setTipoConsulta(tipo);
			taxaJuro.setDateInicioVigencia(to.getInicioVigencia());
			taxaJuro.setDateFimVigencia(to.getFimVigencia());
			taxaJuro.setTipoTaxa(to.getTipo());
			taxaJuro.setPrazoMin(to.getPrazo());
			taxaJuro.setPrazoMax(to.getPrazo());
			taxaJuro.setPcMinimo(to.getPcMinimo());
			taxaJuro.setPcMedio(to.getPcMedio());
			taxaJuro.setPcMaximo(to.getPcMaximo());
			taxaJuro.setUsuarioInclusao(to.getUsuarioInclusao());
			taxaJuro.setDateInclusaoTaxa(to.getDataInclusaoTaxa());
			taxaJuro.setUsuarioFinalizacao(to.getUsuarioFinalizacao());
			taxaJuro.setDateInclusaoFimVigencia(to.getDataInclusaoFimVigencia());
		}
		
		return taxaJuro;
	}
	
	private Date dataFimVigenciaComHoras(String dataFimVigencia) throws ParseException{
		
		return   DataUtil.converter(dataFimVigencia+" 23:59:59", DataUtil.PADRAO_DATA_HORA_COMPLETA_ISO);
		
	}
}
