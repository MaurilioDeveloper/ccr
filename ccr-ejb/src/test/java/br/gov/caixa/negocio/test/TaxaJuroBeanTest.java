//package br.gov.caixa.negocio.test;
//
//import static org.junit.Assert.assertEquals;
//import static org.mockito.Mockito.when;
//
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.List;
//
//import javax.persistence.EntityManager;
//import javax.persistence.TypedQuery;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.mockito.runners.MockitoJUnitRunner;
//
//import br.gov.caixa.ccr.dominio.Retorno;
//import br.gov.caixa.ccr.dominio.TaxaJuro;
//import br.gov.caixa.ccr.dominio.transicao.GrupoTaxaTO;
//import br.gov.caixa.ccr.dominio.transicao.TaxaJuroConvenioTO;
//import br.gov.caixa.ccr.dominio.transicao.TaxaJuroGrupoTO;
//import br.gov.caixa.ccr.negocio.TaxaJuroBean;
//
//@RunWith(MockitoJUnitRunner.class)
//public class TaxaJuroBeanTest {
//
//	private static final String ERRO_NEGOCIAL = "ERRO_NEGOCIAL";
//
//	private static final String SUCESSO = "SUCESSO";
//
//	@InjectMocks
//	private TaxaJuroBean taxaJuroBean;
//
//	@Mock
//	private EntityManager em;
//
//	@Mock
//	private TypedQuery<TaxaJuroGrupoTO> query;
//
//	@Mock
//	private TypedQuery<TaxaJuroConvenioTO> queryConvenio;
//
//	@Before
//	public void before() {
//		taxaJuroBean = new TaxaJuroBean();
//		MockitoAnnotations.initMocks(this);
//	}
//
//	private void createMocksDefault() {
//		when(em.createNamedQuery("TaxaJuroGrupo.listarTodas", TaxaJuroGrupoTO.class)).thenReturn(query);
//		when(em.createNamedQuery("TaxaJuroGrupo.listar", TaxaJuroGrupoTO.class)).thenReturn(query);
//		when(em.createNamedQuery("TaxaJuroConvenio.listar", TaxaJuroConvenioTO.class)).thenReturn(queryConvenio);
//		when(em.createNamedQuery("TaxaJuroGrupo.incluir", TaxaJuroGrupoTO.class)).thenReturn(query);
//		when(em.createNamedQuery("TaxaJuroConvenio.incluir", TaxaJuroConvenioTO.class)).thenReturn(queryConvenio);
//		when(em.createNamedQuery("TaxaJuroGrupo.verificaVigenciaValidaInc", TaxaJuroGrupoTO.class)).thenReturn(query);
//		when(em.createNamedQuery("TaxaJuroConvenio.verificaVigenciaValidaInc", TaxaJuroConvenioTO.class))
//				.thenReturn(queryConvenio);
//		when(em.createNamedQuery("TaxaJuroGrupo.atualizarVigencia", TaxaJuroGrupoTO.class)).thenReturn(query);
//		when(em.createNamedQuery("TaxaJuroConvenio.atualizarVigencia", TaxaJuroConvenioTO.class))
//				.thenReturn(queryConvenio);
//		when(em.createNamedQuery("TaxaJuroGrupo.verificaVigenciaValida", TaxaJuroGrupoTO.class)).thenReturn(query);
//		when(em.createNamedQuery("TaxaJuroConvenio.verificaVigenciaValida", TaxaJuroConvenioTO.class))
//				.thenReturn(queryConvenio);
//		when(em.createNamedQuery("TaxaJuroGrupo.verificaVigenciaValidaSemFim", TaxaJuroGrupoTO.class))
//				.thenReturn(query);
//		when(em.createNamedQuery("TaxaJuroConvenio.verificaVigenciaValidaSemFim", TaxaJuroConvenioTO.class))
//				.thenReturn(queryConvenio);
//		when(em.createNamedQuery("TaxaJuroGrupo.verificaVigenciaValidaIncSemFim", TaxaJuroGrupoTO.class))
//				.thenReturn(query);
//		when(em.createNamedQuery("TaxaJuroGrupo.excluirRange", TaxaJuroGrupoTO.class)).thenReturn(query);
//		when(em.createNamedQuery("TaxaJuroConvenio.excluirRange", TaxaJuroConvenioTO.class)).thenReturn(queryConvenio);
//		when(em.createNamedQuery("TaxaJuroGrupo.atualizarFimVigencia", TaxaJuroGrupoTO.class)).thenReturn(query);
//		when(em.createNamedQuery("TaxaJuroConvenio.atualizarFimVigencia", TaxaJuroConvenioTO.class))
//				.thenReturn(queryConvenio);
//		when(em.createNamedQuery("TaxaJuroGrupo.atualizar", TaxaJuroGrupoTO.class)).thenReturn(query);
//		when(em.createNamedQuery("TaxaJuroConvenio.atualizar", TaxaJuroConvenioTO.class)).thenReturn(queryConvenio);
//		when(em.createNamedQuery("TaxaJuroConvenio.verificaVigenciaValidaIncSemFim", TaxaJuroConvenioTO.class))
//				.thenReturn(queryConvenio);
//		when(em.createNamedQuery("TaxaJuroGrupo.atualizarVigenciaDelete", TaxaJuroGrupoTO.class)).thenReturn(query);
//		when(em.createNamedQuery("TaxaJuroConvenio.atualizarVigenciaDelete", TaxaJuroConvenioTO.class)).thenReturn(queryConvenio);
//		when(query.executeUpdate()).thenReturn(1);
//		when(query.getResultList()).thenReturn(new ArrayList<TaxaJuroGrupoTO>());
//	}
//
//	@Test
//	public void testIncluirTaxaJuro() throws Exception {
//		createMocksDefault();
//		// test para inclusao com sucesso
//
//		TaxaJuro taxaJuroGrupo = new TaxaJuro();
//		taxaJuroGrupo.setInicioVigencia("2017-07-21");
//		taxaJuroGrupo.setCodigoTaxa(13L); // grupo taxa NICHO
//		taxaJuroGrupo.setTipoTaxa((short) 1);
//		taxaJuroGrupo.setTipoConsulta(TaxaJuro.GRUPO);
//		taxaJuroGrupo.setFimVigencia("");
//		taxaJuroGrupo.setPrazoMin(1);
//		taxaJuroGrupo.setPrazoMax(3);
//		taxaJuroGrupo.setPcMinimo(Float.valueOf(0.00001f));
//		taxaJuroGrupo.setPcMedio(Float.valueOf(0.00002f));
//		taxaJuroGrupo.setPcMaximo(Float.valueOf(0.00003f));
//		String inicioVigenciaChave = "";
//
//		Retorno retorno = taxaJuroBean.salvar(taxaJuroGrupo, inicioVigenciaChave, "test");
//		assertEquals(Long.valueOf(0), retorno.getCodigoRetorno());
//		assertEquals(SUCESSO, retorno.getTipoRetorno());
//	}
//
//	@Test
//	public void testIncluirTaxaJuroConvenio() throws Exception {
//		createMocksDefault();
//
//		TaxaJuro taxaJuroConvenio = new TaxaJuro();
//		taxaJuroConvenio.setInicioVigencia("2017-07-21");
//		taxaJuroConvenio.setCodigoTaxa(999L); // grupo taxa BANDA MATRIZ
//		taxaJuroConvenio.setTipoTaxa((short) 1);
//		taxaJuroConvenio.setTipoConsulta(TaxaJuro.CONVENIO);
//		taxaJuroConvenio.setFimVigencia("");
//		taxaJuroConvenio.setPrazoMin(1);
//		taxaJuroConvenio.setPrazoMax(3);
//		taxaJuroConvenio.setPcMinimo(Float.valueOf(0.00001f));
//		taxaJuroConvenio.setPcMedio(Float.valueOf(0.00002f));
//		taxaJuroConvenio.setPcMaximo(Float.valueOf(0.00003f));
//		String inicioVigenciaChave = "";
//
//		Retorno retorno = taxaJuroBean.salvar(taxaJuroConvenio, inicioVigenciaChave, "test");
//		assertEquals(Long.valueOf(0), retorno.getCodigoRetorno());
//		assertEquals(SUCESSO, retorno.getTipoRetorno());
//	}
//
//	@Test
//	public void testIncluirTaxaJuroVigente() throws Exception {
//		createMocksDefault();
//		ArrayList<TaxaJuroGrupoTO> lista = new ArrayList<TaxaJuroGrupoTO>();
//		lista.add(new TaxaJuroGrupoTO());
//		when(query.getResultList()).thenReturn(lista);
//
//		TaxaJuro taxaJuro = new TaxaJuro();
//		taxaJuro.setInicioVigencia("2017-07-01");
//		taxaJuro.setCodigoTaxa(13L); // grupo taxa NICHO
//		taxaJuro.setTipoTaxa((short) 1);
//		taxaJuro.setTipoConsulta(TaxaJuro.GRUPO);
//		taxaJuro.setFimVigencia("");
//		taxaJuro.setPrazoMin(1);
//		taxaJuro.setPrazoMax(3);
//		taxaJuro.setPcMinimo(Float.valueOf(0.00001f));
//		taxaJuro.setPcMedio(Float.valueOf(0.00002f));
//		taxaJuro.setPcMaximo(Float.valueOf(0.00003f));
//		String inicioVigenciaChave = "";
//
//		Retorno retorno = taxaJuroBean.salvar(taxaJuro, inicioVigenciaChave, "test");
//		assertEquals(Long.valueOf(1), retorno.getCodigoRetorno());
//		assertEquals(ERRO_NEGOCIAL, retorno.getTipoRetorno());
//		assertEquals("MN003", retorno.getIdMsg());
//	}
//
//	@Test
//	public void testIncluirTaxaJuroConvenioVigente() throws Exception {
//		createMocksDefault();
//		ArrayList<TaxaJuroConvenioTO> lista = new ArrayList<TaxaJuroConvenioTO>();
//		lista.add(new TaxaJuroConvenioTO());
//		when(queryConvenio.getResultList()).thenReturn(lista);
//
//		TaxaJuro taxaJuroConvenio = new TaxaJuro();
//		taxaJuroConvenio.setInicioVigencia("2017-07-21");
//		taxaJuroConvenio.setCodigoTaxa(999L); // grupo taxa BANDA MATRIZ
//		taxaJuroConvenio.setTipoTaxa((short) 1);
//		taxaJuroConvenio.setTipoConsulta(TaxaJuro.CONVENIO);
//		taxaJuroConvenio.setFimVigencia("");
//		taxaJuroConvenio.setPrazoMin(1);
//		taxaJuroConvenio.setPrazoMax(3);
//		taxaJuroConvenio.setPcMinimo(Float.valueOf(0.00001f));
//		taxaJuroConvenio.setPcMedio(Float.valueOf(0.00002f));
//		taxaJuroConvenio.setPcMaximo(Float.valueOf(0.00003f));
//		String inicioVigenciaChave = "";
//
//		Retorno retorno = taxaJuroBean.salvar(taxaJuroConvenio, inicioVigenciaChave, "test");
//		assertEquals(Long.valueOf(1), retorno.getCodigoRetorno());
//		assertEquals(ERRO_NEGOCIAL, retorno.getTipoRetorno());
//		assertEquals("MN003", retorno.getIdMsg());
//	}
//
//	@Test
//	public void testIncluirTaxaJuroPrazoZero() throws Exception {
//		createMocksDefault();
//
//		TaxaJuro taxaJuro = new TaxaJuro();
//		taxaJuro.setInicioVigencia("2017-07-01");
//		taxaJuro.setCodigoTaxa(13L); // grupo taxa NICHO
//		taxaJuro.setTipoTaxa((short) 1);
//		taxaJuro.setTipoConsulta(TaxaJuro.GRUPO);
//		taxaJuro.setFimVigencia("");
//		taxaJuro.setPrazoMin(0);
//		taxaJuro.setPrazoMax(3);
//		taxaJuro.setPcMinimo(Float.valueOf(0.00001f));
//		taxaJuro.setPcMedio(Float.valueOf(0.00002f));
//		taxaJuro.setPcMaximo(Float.valueOf(0.00003f));
//		String inicioVigenciaChave = "";
//
//		Retorno retorno = taxaJuroBean.salvar(taxaJuro, inicioVigenciaChave, "test");
//		assertEquals(Long.valueOf(1), retorno.getCodigoRetorno());
//		assertEquals(ERRO_NEGOCIAL, retorno.getTipoRetorno());
//		assertEquals("MA0054", retorno.getIdMsg());
//	}
//
//	@Test
//	public void testIncluirTaxaJuroPrazoInvalido() throws Exception {
//		createMocksDefault();
//
//		TaxaJuro taxaJuro = new TaxaJuro();
//		taxaJuro.setInicioVigencia("2017-07-01");
//		taxaJuro.setCodigoTaxa(13L); // grupo taxa NICHO
//		taxaJuro.setTipoTaxa((short) 1);
//		taxaJuro.setTipoConsulta(TaxaJuro.GRUPO);
//		taxaJuro.setFimVigencia("");
//		taxaJuro.setPrazoMin(2);
//		taxaJuro.setPrazoMax(1);
//		taxaJuro.setPcMinimo(Float.valueOf(0.00001f));
//		taxaJuro.setPcMedio(Float.valueOf(0.00002f));
//		taxaJuro.setPcMaximo(Float.valueOf(0.00003f));
//		String inicioVigenciaChave = "";
//
//		Retorno retorno = taxaJuroBean.salvar(taxaJuro, inicioVigenciaChave, "test");
//		assertEquals(Long.valueOf(1), retorno.getCodigoRetorno());
//		assertEquals(ERRO_NEGOCIAL, retorno.getTipoRetorno());
//		assertEquals("MA0053", retorno.getIdMsg());
//	}
//
//	@Test
//	public void testAlterarTaxaJuro() throws Exception {
//		createMocksDefault();
//
//		TaxaJuro taxaJuro = new TaxaJuro();
//		taxaJuro.setId(2);
//		taxaJuro.setInicioVigencia("2017-07-01");
//		taxaJuro.setCodigoTaxa(13L); // grupo taxa NICHO
//		taxaJuro.setTipoTaxa((short) 1);
//		taxaJuro.setTipoConsulta(TaxaJuro.GRUPO);
//		taxaJuro.setFimVigencia("");
//		taxaJuro.setPrazoMin(10);
//		taxaJuro.setPrazoMax(13);
//		taxaJuro.setPcMinimo(Float.valueOf(0.00001f));
//		taxaJuro.setPcMedio(Float.valueOf(0.00002f));
//		taxaJuro.setPcMaximo(Float.valueOf(0.00003f));
//		String inicioVigenciaChave = "2017-06-05";
//		taxaJuro.setUpdatable(Boolean.TRUE);
//
//		Retorno retorno = taxaJuroBean.salvar(taxaJuro, inicioVigenciaChave, "test");
//		assertEquals(Long.valueOf(0), retorno.getCodigoRetorno());
//		assertEquals(SUCESSO, retorno.getTipoRetorno());
//	}
//
//	@Test
//	public void testAlterarTaxaJuroConvenio() throws Exception {
//		createMocksDefault();
//
//		TaxaJuro taxaJuroConvenio = new TaxaJuro();
//		taxaJuroConvenio.setInicioVigencia("2017-07-21");
//		taxaJuroConvenio.setId(3);
//		taxaJuroConvenio.setCodigoTaxa(999L); // grupo taxa BANDA MATRIZ
//		taxaJuroConvenio.setTipoTaxa((short) 1);
//		taxaJuroConvenio.setTipoConsulta(TaxaJuro.CONVENIO);
//		taxaJuroConvenio.setFimVigencia("");
//		taxaJuroConvenio.setPrazoMin(1);
//		taxaJuroConvenio.setPrazoMax(3);
//		taxaJuroConvenio.setPcMinimo(Float.valueOf(0.00001f));
//		taxaJuroConvenio.setPcMedio(Float.valueOf(0.00002f));
//		taxaJuroConvenio.setPcMaximo(Float.valueOf(0.00003f));
//		taxaJuroConvenio.setUpdatable(Boolean.TRUE);
//		String inicioVigenciaChave = "2017-06-05";
//
//		Retorno retorno = taxaJuroBean.salvar(taxaJuroConvenio, inicioVigenciaChave, "test");
//		assertEquals(Long.valueOf(0), retorno.getCodigoRetorno());
//		assertEquals(SUCESSO, retorno.getTipoRetorno());
//	}
//
//	@Test
//	public void testExcluirTaxaJuro() throws Exception {
//		createMocksDefault();
//
//		TaxaJuro taxaJuro = new TaxaJuro();
//		taxaJuro.setId(2);
//		taxaJuro.setInicioVigencia("2017-07-01");
//		taxaJuro.setCodigoTaxa(13L); // grupo taxa NICHO
//		taxaJuro.setTipoTaxa((short) 1);
//		taxaJuro.setTipoConsulta(TaxaJuro.GRUPO);
//		taxaJuro.setFimVigencia("");
//		taxaJuro.setPrazoMin(10);
//		taxaJuro.setPrazoMax(13);
//		taxaJuro.setPcMinimo(Float.valueOf(0.00001f));
//		taxaJuro.setPcMedio(Float.valueOf(0.00002f));
//		taxaJuro.setPcMaximo(Float.valueOf(0.00003f));
//		taxaJuro.setExcludable(Boolean.TRUE);
//
//		Retorno retorno = taxaJuroBean.excluir(taxaJuro);
//		assertEquals(Long.valueOf(0), retorno.getCodigoRetorno());
//		assertEquals(SUCESSO, retorno.getTipoRetorno());
//	}
//
//	@Test
//	public void testExcluirTaxaJuroConvenio() throws Exception {
//		createMocksDefault();
//
//		TaxaJuro taxaJuroConvenio = new TaxaJuro();
//		taxaJuroConvenio.setInicioVigencia("2017-07-21");
//		taxaJuroConvenio.setId(3);
//		taxaJuroConvenio.setCodigoTaxa(999L); // grupo taxa BANDA MATRIZ
//		taxaJuroConvenio.setTipoTaxa((short) 1);
//		taxaJuroConvenio.setTipoConsulta(TaxaJuro.CONVENIO);
//		taxaJuroConvenio.setFimVigencia("");
//		taxaJuroConvenio.setPrazoMin(1);
//		taxaJuroConvenio.setPrazoMax(3);
//		taxaJuroConvenio.setPcMinimo(Float.valueOf(0.00001f));
//		taxaJuroConvenio.setPcMedio(Float.valueOf(0.00002f));
//		taxaJuroConvenio.setPcMaximo(Float.valueOf(0.00003f));
//		taxaJuroConvenio.setExcludable(Boolean.TRUE);
//
//		Retorno retorno = taxaJuroBean.excluir(taxaJuroConvenio);
//		assertEquals(Long.valueOf(0), retorno.getCodigoRetorno());
//		assertEquals(SUCESSO, retorno.getTipoRetorno());
//	}
//
//	@Test
//	public void testListarTaxaJuro() throws Exception {
//		createMocksDefault();
//
//		ArrayList<TaxaJuroGrupoTO> result = new ArrayList<>();
//		addTaxaJuroFakes(result);
//		when(query.getResultList()).thenReturn(result);
//
//		List<TaxaJuro> retorno = taxaJuroBean.listar(TaxaJuro.GRUPO, 2,"0","0","0", null);
//		assertEquals(3, retorno.size());
//	}
//
//	/**
//	 * Adciona 5 TaxaJuro na lista. 1 delas com data de fim vigencia. Os prazos
//	 * são: 1 a 3 e 3 a 4.
//	 * 
//	 * @param result
//	 */
//	private void addTaxaJuroFakes(ArrayList<TaxaJuroGrupoTO> result) {
//		TaxaJuroGrupoTO t = new TaxaJuroGrupoTO();
//		GrupoTaxaTO grupo = new GrupoTaxaTO();
//		grupo.setId(13L);
//		grupo.setNome("NICHO SENADO");
//		// data na TO é do tipo java.sql.Date
//		java.sql.Date current = new java.sql.Date(Calendar.getInstance().getTimeInMillis());
//		Calendar c = Calendar.getInstance();
//		java.util.Date dt = new java.util.Date();
//		c.setTime(dt);
//		c.add(Calendar.DATE, 30);
//		java.sql.Date posterior = new java.sql.Date(c.getTimeInMillis());
//
//		t.setGrupo(grupo);
//		t.setInicioVigencia(current);
//		t.setDataInclusaoTaxa(current);
//		t.setPrazo(1);
//		t.setPcMinimo(0.001f);
//		t.setPcMedio(0.002f);
//		t.setPcMaximo(0.003f);
//		t.setUsuarioInclusao("test");
//		result.add(t);
//
//		t = new TaxaJuroGrupoTO();
//		t.setGrupo(grupo);
//		t.setInicioVigencia(current);
//		t.setDataInclusaoTaxa(current);
//		t.setPrazo(2);
//		t.setPcMinimo(0.001f);
//		t.setPcMedio(0.002f);
//		t.setPcMaximo(0.003f);
//		t.setUsuarioInclusao("test");
//		result.add(t);
//
//		t = new TaxaJuroGrupoTO();
//		t.setGrupo(grupo);
//		t.setInicioVigencia(current);
//		t.setDataInclusaoTaxa(current);
//		t.setFimVigencia(current);
//		t.setDataInclusaoFimVigencia(current);
//		t.setPrazo(3);
//		t.setPcMinimo(0.001f);
//		t.setPcMedio(0.002f);
//		t.setPcMaximo(0.003f);
//		t.setUsuarioInclusao("test");
//		result.add(t);
//
//		t = new TaxaJuroGrupoTO();
//		t.setGrupo(grupo);
//		t.setInicioVigencia(posterior);
//		t.setDataInclusaoTaxa(current);
//		t.setPrazo(3);
//		t.setPcMinimo(0.001f);
//		t.setPcMedio(0.002f);
//		t.setPcMaximo(0.003f);
//		t.setUsuarioInclusao("test");
//		result.add(t);
//
//		t = new TaxaJuroGrupoTO();
//		t.setGrupo(grupo);
//		t.setInicioVigencia(posterior);
//		t.setDataInclusaoTaxa(current);
//		t.setPrazo(4);
//		t.setPcMinimo(0.001f);
//		t.setPcMedio(0.002f);
//		t.setPcMaximo(0.003f);
//		t.setUsuarioInclusao("test");
//		result.add(t);
//	}
//}
