package br.gov.caixa.ccr.negocio;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import br.gov.caixa.ccr.dominio.TaxaSeguro;
import br.gov.caixa.ccr.dominio.TaxaSeguroFaixa;
import br.gov.caixa.ccr.dominio.transicao.TaxaSeguroTO;
import br.gov.caixa.ccr.util.DataUtil;
import br.gov.caixa.ccr.util.Utilities;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class TaxaSeguroBean implements ITaxaSeguroBean {

	@PersistenceContext
	private EntityManager em;

	@Override
	public List<TaxaSeguroFaixa> listar(String dateIniStr, String dateFimStr) throws Exception {

		
		TypedQuery<TaxaSeguroTO> query = null;
		
		if((dateIniStr != null && !dateIniStr.equals("")) && 
				(dateFimStr != null && !dateFimStr.equals(""))){
			query = em.createNamedQuery("TaxaSeguro.listarPorInicioFinalVigencia", TaxaSeguroTO.class);	
			Date dateIni = DataUtil.converter(dateIniStr, DataUtil.PADRAO_DATA_BARRAMENTO);
			Date dateFim = DataUtil.converter(dateFimStr, DataUtil.PADRAO_DATA_BARRAMENTO);
			query.setParameter("pInicioVigencia", dateIni);
			query.setParameter("pFimVigencia", dateFim);
		}else if ((dateIniStr != null && !dateIniStr.equals("")) && 
			(dateFimStr != null && dateFimStr.equals("") || dateFimStr == null)) {
			
			query = em.createNamedQuery("TaxaSeguro.listarPorInicioVigencia", TaxaSeguroTO.class);	
			Date dateIni = DataUtil.converter(dateIniStr, DataUtil.PADRAO_DATA_BARRAMENTO);
			query.setParameter("pInicioVigencia", dateIni);
			
		}else if((dateFimStr != null && !dateFimStr.equals("")) && 
				(dateIniStr != null && dateIniStr.equals("") || dateIniStr == null)){
			
			query = em.createNamedQuery("TaxaSeguro.listarPorFinalVigencia", TaxaSeguroTO.class);
			Date dateFim = DataUtil.converter(dateFimStr, DataUtil.PADRAO_DATA_BARRAMENTO);
			query.setParameter("pFimVigencia", dateFim);
		}else{
			query = em.createNamedQuery("TaxaSeguro.listar", TaxaSeguroTO.class);
		}
		
		 
//		TypedQuery<TaxaSeguroTO> query = null;
//		if (dateIniStr != null && !dateIniStr.equals("") ) {
//			query = em.createNamedQuery("TaxaSeguro.listarPorInicioVigencia", TaxaSeguroTO.class);
//			Date date = DataUtil.converter(dateIniStr, DataUtil.PADRAO_DATA_BARRAMENTO);
//			query.setParameter("pInicioVigencia", date);
//		} else {
//			query = em.createNamedQuery("TaxaSeguro.listar", TaxaSeguroTO.class);
//		}
		

		List<TaxaSeguroFaixa> listaTaxaSeguroFaixa = agrupaTaxasPorPrazo(query.getResultList());
		listaTaxaSeguroFaixa = agrupaTaxasPorIdade(listaTaxaSeguroFaixa);

		return listaTaxaSeguroFaixa;
	}

	@Override
	public TaxaSeguro findVigente(int prazo, int idade) throws Exception {

		TaxaSeguro taxaSeguro = new TaxaSeguro();

		// cria a Query
		TypedQuery<TaxaSeguroTO> query = em.createNamedQuery("TaxaSeguro.findVigente", TaxaSeguroTO.class);
		query.setParameter("pPrazo", prazo);
		query.setParameter("pIdade", idade);

		// resultado
		List<TaxaSeguroTO> listaRetorno = query.getResultList();
		if (listaRetorno.isEmpty())
			return null;

		TaxaSeguroTO to = listaRetorno.get(0);
		Utilities.copyAttributesFromTo(taxaSeguro, to);

		return taxaSeguro;
	}

	@Override
	public Retorno salvar(TaxaSeguroFaixa taxaSeguroFaixa, String inicioVigenciaChave, String codigoUsuario)
			throws Exception {
		Retorno retorno = new Retorno();
		
		try {

			Calendar cal = Calendar.getInstance();
			cal.setTime(DataUtil.converter(taxaSeguroFaixa.getInicioVigencia(), DataUtil.PADRAO_DATA_ISO));
			cal.set(Calendar.DATE, cal.get(Calendar.DATE) - 1);

			if (validaSalvarTaxaSeguro(taxaSeguroFaixa, retorno) == false) {
				return retorno;
			}

			// incluir
			if (taxaSeguroFaixa.getId() == 0) {
				
				if (existeVigenciaPeriodo(taxaSeguroFaixa)) {
					retorno.setAll(1L, "", Retorno.ERRO_NEGOCIAL, "MA0052");
					return retorno;
				}
				
				atualizarVigencia(taxaSeguroFaixa, DataUtil.formatar(cal.getTime(), DataUtil.PADRAO_DATA_ISO), codigoUsuario);				
				incluiTaxas(taxaSeguroFaixa, codigoUsuario);
				retorno.setIdMsg("MA005");
			} else {
				if (taxaSeguroFaixa.isUpdatable()) {
					// converte a taxa inicio chave para verificar se eh
					// valida.. se nao for causara uma exception
					DataUtil.converter(inicioVigenciaChave, DataUtil.PADRAO_DATA_ISO);
										
					// VERIFICA SE O PRAZO E IDADE ESTAO NO RANGE
					if (taxaSeguroFaixa.getPrazoMin() < taxaSeguroFaixa.getPrazoMinOld() || taxaSeguroFaixa.getPrazoMax() > taxaSeguroFaixa.getPrazoMaxOld()){						
						//Msg especifica para a regra de prazo
						String montaMsgRet = "Prazo informado não pertence ao intervalo "+taxaSeguroFaixa.getPrazoMinOld()+" à "+taxaSeguroFaixa.getPrazoMaxOld()+".";						
						retorno.setAll(-1L, montaMsgRet , Retorno.ERRO_NEGOCIAL);
						return retorno;
					}else if (taxaSeguroFaixa.getIdadeMin() < taxaSeguroFaixa.getIdadeMinOld() || taxaSeguroFaixa.getIdadeMax() > taxaSeguroFaixa.getIdadeMaxOld()) {
						//Msg especifica para a regra de idade
						String montaMsgRet = "Idade informada fora do intervalo "+taxaSeguroFaixa.getIdadeMinOld()+" à "+taxaSeguroFaixa.getIdadeMaxOld()+".";						
						retorno.setAll(-1L, montaMsgRet , Retorno.ERRO_NEGOCIAL);
						return retorno;
					}					
													
					//deleta registros prazoe idade editados
					if (taxaSeguroFaixa.getPrazoMin() != taxaSeguroFaixa.getPrazoMinOld() || taxaSeguroFaixa.getPrazoMax() != taxaSeguroFaixa.getPrazoMaxOld()
							|| taxaSeguroFaixa.getIdadeMin() != taxaSeguroFaixa.getIdadeMinOld() || taxaSeguroFaixa.getIdadeMax() != taxaSeguroFaixa.getIdadeMaxOld())
						atualizarPrazoIdade(taxaSeguroFaixa);					
					
					// Atualiza a Taxa alterada
					alteraTaxas(taxaSeguroFaixa, inicioVigenciaChave, codigoUsuario);
										
					retorno.setIdMsg("MA005");
				}
			}

		} catch (Exception e) {
			retorno.setAll(1L, e.getMessage(), Retorno.ERRO_EXCECAO, "");
		}

		return retorno;
	}

	private void atualizarPrazoIdade(TaxaSeguroFaixa taxaSeguroFaixa) throws Exception {
		{
			// metodo utilizado no processo de alterar taxa seguro - deleta/altera registros de prazo alterados
			atualizarPrazoIdadeDeleteMenor(taxaSeguroFaixa);
			// metodo utilizado no processo de alterar taxa seguro - deleta/altera registros de prazo alterados
			atualizarPrazoIdadeDeleteMaior(taxaSeguroFaixa);
			//metodo utilizado no processo de alterar taxa seguro - deleta registros de idade alterados
			atualizaIdadeDelete(taxaSeguroFaixa);
		}
	}

	private boolean validaSalvarTaxaSeguro(TaxaSeguroFaixa taxaSeguroFaixa, Retorno retorno) throws Exception {
		if (taxaSeguroFaixa.getPrazoMin() < 1) {
			retorno.setAll(1L, "", Retorno.ERRO_NEGOCIAL, "MA0054");
			return false;
		} else if (taxaSeguroFaixa.getPrazoMin() > taxaSeguroFaixa.getPrazoMax()) {
			retorno.setAll(1L, "", Retorno.ERRO_NEGOCIAL, "MA0053");
			return false;
		} else if (taxaSeguroFaixa.getIdadeMin() < 1) {
			retorno.setAll(1L, "", Retorno.ERRO_NEGOCIAL, "MA0055");
			return false;
		} else if (taxaSeguroFaixa.getIdadeMax() < 1) {
			retorno.setAll(1L, "", Retorno.ERRO_NEGOCIAL, "MA0056");
			return false;
		} else if (taxaSeguroFaixa.getIdadeMin() > taxaSeguroFaixa.getIdadeMax()) {
			retorno.setAll(1L, "", Retorno.ERRO_NEGOCIAL, "ll056");
			return false;
		}
		return true;
	}
	
	// metodo utilizado no processo de alterar taxa seguro - deleta registros de prazo alterados
	private void atualizarPrazoIdadeDeleteMenor(TaxaSeguroFaixa taxaSeguroFaixa) throws Exception {
		
		TypedQuery<TaxaSeguroTO> query;			
		query = em.createNamedQuery("TaxaSeguro.atualizarPrazoIdadeDeleteMenor", TaxaSeguroTO.class);		
		query.setParameter("pInicioVigencia", DataUtil.converter(taxaSeguroFaixa.getInicioVigencia(), DataUtil.PADRAO_DATA_ISO));
		
		//verifica se a idade foi alterada
		if (taxaSeguroFaixa.getIdadeMin() == taxaSeguroFaixa.getIdadeMinOld() && taxaSeguroFaixa.getIdadeMax() == taxaSeguroFaixa.getIdadeMaxOld()) {			
			if (taxaSeguroFaixa.getTaxa() != taxaSeguroFaixa.getTaxaOld()) {
				return;
			}
		}
		
		//deleta os registros que estao abaixo do range informado 
		for (int prazo = taxaSeguroFaixa.getPrazoMinOld(); prazo < taxaSeguroFaixa.getPrazoMin(); prazo++) {
			
			query.setParameter("pPrazo", prazo);
			query.setParameter("pIdade", taxaSeguroFaixa.getIdadeMin());
			query.executeUpdate();			
		}
	}
		
	// metodo utilizado no processo de alterar taxa seguro - deleta registros de prazo alterados
	private void atualizarPrazoIdadeDeleteMaior(TaxaSeguroFaixa taxaSeguroFaixa) throws Exception {
		
		TypedQuery<TaxaSeguroTO> query;			
		query = em.createNamedQuery("TaxaSeguro.atualizarPrazoIdadeDeleteMaior", TaxaSeguroTO.class);		
		query.setParameter("pInicioVigencia", DataUtil.converter(taxaSeguroFaixa.getInicioVigencia(), DataUtil.PADRAO_DATA_ISO));
		
		//verifica se a idade foi alterada
		if (taxaSeguroFaixa.getIdadeMin() == taxaSeguroFaixa.getIdadeMinOld() && taxaSeguroFaixa.getIdadeMax() == taxaSeguroFaixa.getIdadeMaxOld()) {			
			if (taxaSeguroFaixa.getTaxa() != taxaSeguroFaixa.getTaxaOld()) {
				return;
			}
		}
		
		//deleta os registros que estao abaixo do range informado 
		for (int prazo = taxaSeguroFaixa.getPrazoMaxOld(); prazo > taxaSeguroFaixa.getPrazoMax(); prazo--) {
			
			query.setParameter("pPrazo", prazo);
			query.setParameter("pIdade", taxaSeguroFaixa.getIdadeMax());
			query.executeUpdate();			
		}					
		
	}	
	
	//metodo utilizado no processo de alterar taxa seguro - deleta registros de idade alterados
	private void atualizaIdadeDelete(TaxaSeguroFaixa taxaSeguroFaixa) throws Exception {
		
		TypedQuery<TaxaSeguroTO> query;
		
		query = em.createNamedQuery("TaxaSeguro.atualizarIdadeDelete", TaxaSeguroTO.class);
		query.setParameter("pInicioVigencia", DataUtil.converter(taxaSeguroFaixa.getInicioVigencia(), DataUtil.PADRAO_DATA_ISO));
				
		for (int prazo = taxaSeguroFaixa.getPrazoMin(); prazo <= taxaSeguroFaixa.getPrazoMax(); prazo++) {			
			
			for (int idade = taxaSeguroFaixa.getIdadeMinOld(); idade < taxaSeguroFaixa.getIdadeMin(); idade++) {				
				query.setParameter("pPrazo", prazo);
				query.setParameter("pIdade", idade);
				query.executeUpdate();
			}
			
			for (int idade = taxaSeguroFaixa.getIdadeMaxOld(); idade > taxaSeguroFaixa.getIdadeMax(); idade--) {
				query.setParameter("pPrazo", prazo);
				query.setParameter("pIdade", idade);
				query.executeUpdate();				
			}			
		}		
	}

	@Override
	public Retorno excluir(TaxaSeguroFaixa taxaSeguroFaixa) throws Exception {
		Retorno retorno = new Retorno();

		try {
			if (taxaSeguroFaixa.isExcludable()) {
				TypedQuery<TaxaSeguroTO> query;

				// APAGA PELO RANGE DO PRAZO
				query = em.createNamedQuery("TaxaSeguro.excluirRange", TaxaSeguroTO.class);

				query.setParameter("pPrazoMin", taxaSeguroFaixa.getPrazoMin());
				query.setParameter("pPrazoMax", taxaSeguroFaixa.getPrazoMax());
				query.setParameter("pIdadeMin", taxaSeguroFaixa.getIdadeMin());
				query.setParameter("pIdadeMax", taxaSeguroFaixa.getIdadeMax());
				query.setParameter("pInicioVigencia",
						DataUtil.converter(taxaSeguroFaixa.getInicioVigencia(), DataUtil.PADRAO_DATA_ISO));

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
	private List<TaxaSeguroFaixa> agrupaTaxasPorPrazo(List<TaxaSeguroTO> listaRetorno) throws Exception {
		List<TaxaSeguroFaixa> listaTaxaSeguroFaixa = new ArrayList<TaxaSeguroFaixa>();
		List<TaxaSeguro> lista = new ArrayList<TaxaSeguro>();

		Utilities.copyListClassFromTo(lista, TaxaSeguro.class, listaRetorno);
		int count = 0;
		
		for (int i = 0; i < lista.size(); i++) {
			Date inicioVigencia, fimVigencia;
			int prazoMin = 999, prazoMax = 0, idadeMin = 999, idadeMax = 0, aux = 0;
			double taxa;
			Boolean continua = true, updatable = false, excludable = false;
			String usuarioInclusao, usuarioFinalizacao, tsInclusao, tsFinalizacao;
			TaxaSeguro taxaSeguro = lista.get(i);

			// para agrupar estes valores devem ser iguais
			inicioVigencia = taxaSeguro.getInicioVigencia();
			fimVigencia = taxaSeguro.getFimVigencia();
			
			taxa = taxaSeguro.getTaxa();
			updatable = taxaSeguro.isUpdatable();
			excludable = taxaSeguro.isExcludable();
			usuarioInclusao = taxaSeguro.getUsuarioInclusao();
			usuarioFinalizacao = taxaSeguro.getUsuarioFinalizacao();
			tsInclusao = DataUtil.formatar(taxaSeguro.getDataInclusaoTaxa(), DataUtil.PADRAO_DATA_HORA_COMPLETA);
			tsFinalizacao = DataUtil.formatar(taxaSeguro.getDataInclusaoFimVigencia(),
					DataUtil.PADRAO_DATA_HORA_COMPLETA);

			do {
				int prazo = taxaSeguro.getPrazo();
				prazoMin = prazo < prazoMin ? prazo : prazoMin;
				prazoMax = prazo > prazoMax ? prazo : prazoMax;

				int idade = taxaSeguro.getIdade();
				idadeMin = idade < idadeMin ? idade : idadeMin;
				idadeMax = idade > idadeMax ? idade : idadeMax;

				aux = i + 1;
				if (aux >= lista.size()) {
					continua = false;
				} else {
					TaxaSeguro proxTaxaSeguro = lista.get(aux);

					if (taxaSeguro.equals(proxTaxaSeguro) && idade == proxTaxaSeguro.getIdade()) {
						taxaSeguro = lista.get(++i);
					} else {
						continua = false;
					}

				}
			} while (continua);

			TaxaSeguroFaixa taxaSeguroFaixa = new TaxaSeguroFaixa();
			taxaSeguroFaixa.setId(++count);
			taxaSeguroFaixa.setInicioVigencia(DataUtil.formatar(inicioVigencia, DataUtil.PADRAO_DATA_ISO));
			taxaSeguroFaixa.setFimVigencia(DataUtil.formatar(fimVigencia, DataUtil.PADRAO_DATA_ISO));
			
			taxaSeguroFaixa.setPrazoMin(prazoMin);
			taxaSeguroFaixa.setPrazoMax(prazoMax);
			taxaSeguroFaixa.setIdadeMin(idadeMin);
			taxaSeguroFaixa.setIdadeMax(idadeMax);
			taxaSeguroFaixa.setTaxa(taxa);
			taxaSeguroFaixa.setUpdatable(updatable);
			taxaSeguroFaixa.setExcludable(excludable);
			taxaSeguroFaixa.setUsuarioInclusao(usuarioInclusao);
			taxaSeguroFaixa.setUsuarioFinalizacao(usuarioFinalizacao);
			taxaSeguroFaixa.setDataInclusaoTaxa(tsInclusao);
			taxaSeguroFaixa.setDataInclusaoFimVigencia(tsFinalizacao);

			listaTaxaSeguroFaixa.add(taxaSeguroFaixa);
		}

		return listaTaxaSeguroFaixa;
	}

	private List<TaxaSeguroFaixa> agrupaTaxasPorIdade(List<TaxaSeguroFaixa> lista) throws Exception {
		List<TaxaSeguroFaixa> listaTaxaSeguroFaixa = new ArrayList<TaxaSeguroFaixa>();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		
		int count = 0;

		for (int i = 0; i < lista.size(); i++) {
			String inicioVigencia, fimVigencia;
			int prazoMin = 999, prazoMax = 0, idadeMin = 999, idadeMax = 0, aux = 0;
			double taxa;
			Boolean continua = true, updatable = false, excludable = false;
			String usuarioInclusao, usuarioFinalizacao, tsInclusao, tsFinalizacao;
			TaxaSeguroFaixa taxaSeguro = lista.get(i);

			// para agrupar estes valores devem ser iguais
			inicioVigencia = taxaSeguro.getInicioVigencia();
			fimVigencia = taxaSeguro.getFimVigencia();
			taxa = taxaSeguro.getTaxa();
			updatable = taxaSeguro.isUpdatable();
			excludable = taxaSeguro.isExcludable();
			usuarioInclusao = taxaSeguro.getUsuarioInclusao();
			usuarioFinalizacao = taxaSeguro.getUsuarioFinalizacao();
			tsInclusao = taxaSeguro.getDataInclusaoTaxa();
			tsFinalizacao = taxaSeguro.getDataInclusaoFimVigencia();

			do {
				// int prazo = taxaSeguro.getPrazo();
				prazoMin = taxaSeguro.getPrazoMin();
				prazoMax = taxaSeguro.getPrazoMax();

				int idade = taxaSeguro.getIdadeMin();
				idadeMin = idade < idadeMin ? idade : idadeMin;
				idadeMax = idade > idadeMax ? idade : idadeMax;

				aux = i + 1;
				if (aux >= lista.size()) {
					continua = false;
				} else {
					TaxaSeguroFaixa proxTaxaSeguro = lista.get(aux);

					if (taxaSeguro.equals(proxTaxaSeguro) && taxaSeguro.getPrazoMin() == proxTaxaSeguro.getPrazoMin()
							&& taxaSeguro.getPrazoMax() == proxTaxaSeguro.getPrazoMax()) {
						taxaSeguro = lista.get(++i);
					} else {
						continua = false;
					}

				}
			} while (continua);

			TaxaSeguroFaixa taxaSeguroFaixa = new TaxaSeguroFaixa();
			taxaSeguroFaixa.setId(++count);
			taxaSeguroFaixa.setInicioVigencia(inicioVigencia);
			taxaSeguroFaixa.setFimVigencia(fimVigencia);
			
			
			
			if(taxaSeguroFaixa.getInicioVigencia() != null && taxaSeguroFaixa.getInicioVigencia() != ""){
				taxaSeguroFaixa.setDtInicioVigencia(format.parse(taxaSeguroFaixa.getInicioVigencia()));
			}
			
			if(taxaSeguroFaixa.getFimVigencia() != null && taxaSeguroFaixa.getFimVigencia() != ""){
				taxaSeguroFaixa.setDtFimVigencia(format.parse(taxaSeguroFaixa.getFimVigencia()));
			}
			
			taxaSeguroFaixa.setPrazoMin(prazoMin);
			taxaSeguroFaixa.setPrazoMax(prazoMax);
			taxaSeguroFaixa.setIdadeMin(idadeMin);
			taxaSeguroFaixa.setIdadeMax(idadeMax);
			taxaSeguroFaixa.setTaxa(taxa);
			taxaSeguroFaixa.setUpdatable(updatable);
			taxaSeguroFaixa.setExcludable(excludable);
			taxaSeguroFaixa.setUsuarioInclusao(usuarioInclusao);
			taxaSeguroFaixa.setUsuarioFinalizacao(usuarioFinalizacao);
			taxaSeguroFaixa.setDataInclusaoTaxa(tsInclusao);
			taxaSeguroFaixa.setDataInclusaoFimVigencia(tsFinalizacao);

			listaTaxaSeguroFaixa.add(taxaSeguroFaixa);
		}

		return listaTaxaSeguroFaixa;
	}

	private void incluiTaxas(TaxaSeguroFaixa taxaSeguroFaixa, String codigoUsuario) throws Exception {
		Calendar hoje = Calendar.getInstance();
		TypedQuery<TaxaSeguroTO> query = em.createNamedQuery("TaxaSeguro.incluir", TaxaSeguroTO.class);

		// valores iguais para todos os prazos
		query.setParameter("pInicioVigencia",
				DataUtil.converter(taxaSeguroFaixa.getInicioVigencia(), DataUtil.PADRAO_DATA_ISO));
		query.setParameter("pTaxa", taxaSeguroFaixa.getTaxa());

		query.setParameter("pCoUsuarioInclusao", codigoUsuario.toUpperCase());
		query.setParameter("pTsInclusao", hoje.getTime());

		if (taxaSeguroFaixa.getFimVigencia() == null || taxaSeguroFaixa.getFimVigencia().isEmpty()) {
			query.setParameter("pFimVigencia", (Date) null, TemporalType.DATE);
			query.setParameter("pCoUsuarioFinalizacao", null);
			query.setParameter("pTsInclusaoFimVigencia", (Date) null, TemporalType.DATE);
		} else {
			query.setParameter("pFimVigencia",
					DataUtil.converter(taxaSeguroFaixa.getFimVigencia(), DataUtil.PADRAO_DATA_ISO));
			query.setParameter("pCoUsuarioFinalizacao", codigoUsuario.toUpperCase());
			query.setParameter("pTsInclusaoFimVigencia", hoje.getTime());
		}

		// itera prazo por prazo e insere na base
		for (int prazo = taxaSeguroFaixa.getPrazoMin(); prazo <= taxaSeguroFaixa.getPrazoMax(); prazo++) {
			query.setParameter("pPrazo", prazo);

			for (int idade = taxaSeguroFaixa.getIdadeMin(); idade <= taxaSeguroFaixa.getIdadeMax(); idade++) {
				query.setParameter("pIdade", idade);
				query.executeUpdate();
			}
		}
	}

	/* METODOS QUE SAO ACESSADOS INTERNAMENTE */
	private void alteraTaxas(TaxaSeguroFaixa taxaSeguroFaixa, String inicioVigenciaChave, String codigoUsuario)
			throws Exception {
		Calendar cal = Calendar.getInstance();
		TypedQuery<TaxaSeguroTO> query;
		
		query = em.createNamedQuery("TaxaSeguro.atualizar", TaxaSeguroTO.class);		
		query.setParameter("pTaxa", taxaSeguroFaixa.getTaxa());
		query.setParameter("pInicioVigenciaAnterior", DataUtil.converter(inicioVigenciaChave, DataUtil.PADRAO_DATA_ISO));
		query.setParameter("pPrazoMin", taxaSeguroFaixa.getPrazoMin());
		query.setParameter("pPrazoMax", taxaSeguroFaixa.getPrazoMax());
		query.setParameter("pIdadeMin", taxaSeguroFaixa.getIdadeMin());
		query.setParameter("pIdadeMax", taxaSeguroFaixa.getIdadeMax());	
		
		query.executeUpdate();		
	}

	private Boolean existeVigenciaPeriodo(TaxaSeguroFaixa taxaSeguroFaixa) throws Exception {
		TypedQuery<TaxaSeguroTO> query;

		String queryName = taxaSeguroFaixa.getFimVigencia() != null && !taxaSeguroFaixa.getFimVigencia().isEmpty()
				? "TaxaSeguro.verificaVigenciaValidaInc" : "TaxaSeguro.verificaVigenciaValidaIncSemFim";
		query = em.createNamedQuery(queryName, TaxaSeguroTO.class);

		query.setParameter("pPrazoMin", taxaSeguroFaixa.getPrazoMin());
		query.setParameter("pPrazoMax", taxaSeguroFaixa.getPrazoMax());
		query.setParameter("pIdadeMin", taxaSeguroFaixa.getIdadeMin());
		query.setParameter("pIdadeMax", taxaSeguroFaixa.getIdadeMax());
		query.setParameter("pInicioVigencia",
				DataUtil.converter(taxaSeguroFaixa.getInicioVigencia(), DataUtil.PADRAO_DATA_ISO));

		if (taxaSeguroFaixa.getFimVigencia() != null && !taxaSeguroFaixa.getFimVigencia().isEmpty())
			query.setParameter("pFimVigencia",
					dataFimVigenciaComHoras(taxaSeguroFaixa.getFimVigencia()));

		if (query.getResultList().size() > 0)
			return true;

		return false;
	}

	private Boolean existeVigenciaPeriodo(TaxaSeguroFaixa taxaSeguroFaixa, String inicioVigenciaChave)
			throws Exception {
		TypedQuery<TaxaSeguroTO> query;

		String queryName = taxaSeguroFaixa.getFimVigencia() != null && !taxaSeguroFaixa.getFimVigencia().isEmpty()
				? "TaxaSeguro.verificaVigenciaValida" : "TaxaSeguro.verificaVigenciaValidaSemFim";
		query = em.createNamedQuery(queryName, TaxaSeguroTO.class);

		query.setParameter("pInicioVigenciaChave", DataUtil.converter(inicioVigenciaChave, DataUtil.PADRAO_DATA_ISO));
		query.setParameter("pPrazoMin", taxaSeguroFaixa.getPrazoMin());
		query.setParameter("pPrazoMax", taxaSeguroFaixa.getPrazoMax());
		query.setParameter("pIdadeMin", taxaSeguroFaixa.getIdadeMin());
		query.setParameter("pIdadeMax", taxaSeguroFaixa.getIdadeMax());
		query.setParameter("pInicioVigencia",
				DataUtil.converter(taxaSeguroFaixa.getInicioVigencia(), DataUtil.PADRAO_DATA_ISO));

		if (taxaSeguroFaixa.getFimVigencia() != null && !taxaSeguroFaixa.getFimVigencia().isEmpty())
			query.setParameter("pFimVigencia",
					dataFimVigenciaComHoras(taxaSeguroFaixa.getFimVigencia()));

		if (query.getResultList().size() > 0)
			return true;

		return false;
	}

	// So atualiza a vigencia final se esta estiver nula
	private void atualizarVigencia(TaxaSeguroFaixa taxaSeguroFaixa, String fimVigenciaAtualizada, String codigoUsuario)
			throws Exception {
		Calendar cal = Calendar.getInstance();
		TypedQuery<TaxaSeguroTO> query = em.createNamedQuery("TaxaSeguro.atualizarVigencia", TaxaSeguroTO.class);

		query.setParameter("pFimVigencia", dataFimVigenciaComHoras(fimVigenciaAtualizada));
		query.setParameter("pCoUsuarioFinalizacao", codigoUsuario.toUpperCase());
		query.setParameter("pTsInclusaoFimVigencia", cal.getTime());

		for (int prazo = taxaSeguroFaixa.getPrazoMin(); prazo <= taxaSeguroFaixa.getPrazoMax(); prazo++) {
			query.setParameter("pPrazo", prazo);

			for (int idade = taxaSeguroFaixa.getIdadeMin(); idade <= taxaSeguroFaixa.getIdadeMax(); idade++) {
				query.setParameter("pIdade", idade);
				query.executeUpdate();
			}
		}

	}
	
	private Date dataFimVigenciaComHoras(String dataFimVigencia) throws ParseException{
		
		return   DataUtil.converter(dataFimVigencia+" 23:59:59", DataUtil.PADRAO_DATA_HORA_COMPLETA_ISO);
		
	}

}