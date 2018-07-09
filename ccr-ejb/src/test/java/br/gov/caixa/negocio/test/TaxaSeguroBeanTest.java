//package br.gov.caixa.negocio.test;
//
//import static org.junit.Assert.assertEquals;
//import static org.mockito.Mockito.when;
//
//import java.util.ArrayList;
//import java.util.Date;
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
//import br.gov.caixa.ccr.dominio.TaxaSeguroFaixa;
//import br.gov.caixa.ccr.dominio.transicao.TaxaSeguroTO;
//import br.gov.caixa.ccr.negocio.TaxaSeguroBean;
//
//@RunWith(MockitoJUnitRunner.class)
//public class TaxaSeguroBeanTest {
//	
//	private static final String ERRO_NEGOCIAL = "ERRO_NEGOCIAL";
//
//	private static final String SUCESSO = "SUCESSO";
//
//	@InjectMocks
//	private TaxaSeguroBean taxaSeguroBean;
//	
//	@Mock
//	private EntityManager em;
//	
//	@Mock
//	private TypedQuery<TaxaSeguroTO> query;
//	
//	@Before
//	public void before() {
//		taxaSeguroBean = new TaxaSeguroBean();
//	    MockitoAnnotations.initMocks(this);
//	}
//	
//	private void createMocksDefault() {
//		when(em.createNamedQuery("TaxaSeguro.incluir", TaxaSeguroTO.class)).thenReturn(query);
//		when(em.createNamedQuery("TaxaSeguro.verificaVigenciaValidaIncSemFim", TaxaSeguroTO.class)).thenReturn(query);
//		when(em.createNamedQuery("TaxaSeguro.verificaVigenciaValidaInc", TaxaSeguroTO.class)).thenReturn(query);
//		when(em.createNamedQuery("TaxaSeguro.atualizarVigencia", TaxaSeguroTO.class)).thenReturn(query);
//		when(em.createNamedQuery("TaxaSeguro.atualizar", TaxaSeguroTO.class)).thenReturn(query);
//		when(em.createNamedQuery("TaxaSeguro.excluirRange", TaxaSeguroTO.class)).thenReturn(query);
//		when(em.createNamedQuery("TaxaSeguro.listar", TaxaSeguroTO.class)).thenReturn(query);
//		when(em.createNamedQuery("TaxaSeguro.listarPorInicioVigencia", TaxaSeguroTO.class)).thenReturn(query);
//	    when(query.executeUpdate()).thenReturn(1);
//	    when(query.getResultList()).thenReturn(new ArrayList<TaxaSeguroTO>());
//	}
//	
//	@Test
//	public void testIncluirTaxaSeguro() throws Exception {
//		createMocksDefault();
//
//	    TaxaSeguroFaixa taxaSeguroFaixa = new TaxaSeguroFaixa();
//	    taxaSeguroFaixa.setInicioVigencia("2017-07-01");
//	    taxaSeguroFaixa.setFimVigencia("");
//	    taxaSeguroFaixa.setIdadeMin(14);
//	    taxaSeguroFaixa.setIdadeMax(20);
//	    taxaSeguroFaixa.setPrazoMin(16);
//	    taxaSeguroFaixa.setPrazoMax(30);
//	    taxaSeguroFaixa.setTaxa(Float.valueOf(0.2f));
//		String inicioVigenciaChave = "";
//		
//		Retorno retorno = taxaSeguroBean.salvar(taxaSeguroFaixa, inicioVigenciaChave, "test");
//	    assertEquals(Long.valueOf(0), retorno.getCodigoRetorno());
//	    assertEquals(SUCESSO, retorno.getTipoRetorno());
//	}
//
//	@Test
//	public void testIncluirTaxaSeguroVigente() throws Exception {
//		createMocksDefault();
//	    ArrayList<TaxaSeguroTO> lista = new ArrayList<TaxaSeguroTO>();
//	    lista.add(new TaxaSeguroTO());
//		when(query.getResultList()).thenReturn(lista);
//
//	    TaxaSeguroFaixa taxaSeguroFaixa = new TaxaSeguroFaixa();
//	    taxaSeguroFaixa.setInicioVigencia("2017-07-01");
//	    taxaSeguroFaixa.setFimVigencia("");
//	    taxaSeguroFaixa.setIdadeMin(1);
//	    taxaSeguroFaixa.setIdadeMax(20);
//	    taxaSeguroFaixa.setPrazoMin(16);
//	    taxaSeguroFaixa.setPrazoMax(30);
//	    taxaSeguroFaixa.setTaxa(Float.valueOf(0.2f));
//		String inicioVigenciaChave = "";
//		
//		Retorno retorno = taxaSeguroBean.salvar(taxaSeguroFaixa, inicioVigenciaChave, "test");
//	    assertEquals(Long.valueOf(1), retorno.getCodigoRetorno());
//	    assertEquals(ERRO_NEGOCIAL, retorno.getTipoRetorno());
//	    assertEquals("MA0052", retorno.getIdMsg());
//	}
//	
//	@Test
//	public void testIncluirTaxaSeguroIdadeZero() throws Exception {
//		createMocksDefault();
//
//	    TaxaSeguroFaixa taxaSeguroFaixa = new TaxaSeguroFaixa();
//	    taxaSeguroFaixa.setInicioVigencia("2017-07-01");
//	    taxaSeguroFaixa.setFimVigencia("");
//	    taxaSeguroFaixa.setIdadeMin(0);
//	    taxaSeguroFaixa.setIdadeMax(20);
//	    taxaSeguroFaixa.setPrazoMin(16);
//	    taxaSeguroFaixa.setPrazoMax(30);
//	    taxaSeguroFaixa.setTaxa(Float.valueOf(0.2f));
//		String inicioVigenciaChave = "";
//		
//		Retorno retorno = taxaSeguroBean.salvar(taxaSeguroFaixa, inicioVigenciaChave, "test");
//	    assertEquals(Long.valueOf(1), retorno.getCodigoRetorno());
//	    assertEquals(ERRO_NEGOCIAL, retorno.getTipoRetorno());
//	    assertEquals("MA0055", retorno.getIdMsg());
//	}
//	
//	@Test
//	public void testIncluirTaxaSeguroIdadeInvalida() throws Exception {
//		createMocksDefault();
//
//	    TaxaSeguroFaixa taxaSeguroFaixa = new TaxaSeguroFaixa();
//	    taxaSeguroFaixa.setInicioVigencia("2017-07-01");
//	    taxaSeguroFaixa.setFimVigencia("");
//	    taxaSeguroFaixa.setIdadeMin(15);
//	    taxaSeguroFaixa.setIdadeMax(11);
//	    taxaSeguroFaixa.setPrazoMin(16);
//	    taxaSeguroFaixa.setPrazoMax(30);
//	    taxaSeguroFaixa.setTaxa(Float.valueOf(0.2f));
//		String inicioVigenciaChave = "";
//		
//		Retorno retorno = taxaSeguroBean.salvar(taxaSeguroFaixa, inicioVigenciaChave, "test");
//	    assertEquals(Long.valueOf(1), retorno.getCodigoRetorno());
//	    assertEquals(ERRO_NEGOCIAL, retorno.getTipoRetorno());
//	    assertEquals("ll056", retorno.getIdMsg());
//	}
//	
//	@Test
//	public void testIncluirTaxaSeguroPrazoZero() throws Exception {
//		createMocksDefault();
//
//	    TaxaSeguroFaixa taxaSeguroFaixa = new TaxaSeguroFaixa();
//	    taxaSeguroFaixa.setInicioVigencia("2017-07-01");
//	    taxaSeguroFaixa.setFimVigencia("");
//	    taxaSeguroFaixa.setIdadeMin(10);
//	    taxaSeguroFaixa.setIdadeMax(20);
//	    taxaSeguroFaixa.setPrazoMin(0);
//	    taxaSeguroFaixa.setPrazoMax(30);
//	    taxaSeguroFaixa.setTaxa(Float.valueOf(0.2f));
//		String inicioVigenciaChave = "";
//		
//		Retorno retorno = taxaSeguroBean.salvar(taxaSeguroFaixa, inicioVigenciaChave, "test");
//	    assertEquals(Long.valueOf(1), retorno.getCodigoRetorno());
//	    assertEquals(ERRO_NEGOCIAL, retorno.getTipoRetorno());
//	    assertEquals("MA0054", retorno.getIdMsg());
//	}
//	
//	@Test
//	public void testIncluirTaxaSeguroPrazoInvalido() throws Exception {
//		createMocksDefault();
//
//	    TaxaSeguroFaixa taxaSeguroFaixa = new TaxaSeguroFaixa();
//	    taxaSeguroFaixa.setInicioVigencia("2017-07-01");
//	    taxaSeguroFaixa.setFimVigencia("");
//	    taxaSeguroFaixa.setIdadeMin(15);
//	    taxaSeguroFaixa.setIdadeMax(11);
//	    taxaSeguroFaixa.setPrazoMin(16);
//	    taxaSeguroFaixa.setPrazoMax(10);
//	    taxaSeguroFaixa.setTaxa(Float.valueOf(0.2f));
//		String inicioVigenciaChave = "";
//		
//		Retorno retorno = taxaSeguroBean.salvar(taxaSeguroFaixa, inicioVigenciaChave, "test");
//	    assertEquals(Long.valueOf(1), retorno.getCodigoRetorno());
//	    assertEquals(ERRO_NEGOCIAL, retorno.getTipoRetorno());
//	    assertEquals("MA0053", retorno.getIdMsg());
//	}
//	
//	@Test
//	public void testAlterarTaxaSeguro() throws Exception {
//		createMocksDefault();
//
//	    TaxaSeguroFaixa taxaSeguroFaixa = new TaxaSeguroFaixa();
//	    taxaSeguroFaixa.setId(5);
//	    taxaSeguroFaixa.setInicioVigencia("2017-11-10 00:00:00");
//	    taxaSeguroFaixa.setFimVigencia("");
//	    taxaSeguroFaixa.setIdadeMin(2);
//	    taxaSeguroFaixa.setIdadeMax(2);
//	    taxaSeguroFaixa.setPrazoMin(6);
//	    taxaSeguroFaixa.setPrazoMax(6);	    
//	    taxaSeguroFaixa.setIdadeMinOld(2);
//	    taxaSeguroFaixa.setIdadeMaxOld(2);
//	    taxaSeguroFaixa.setPrazoMinOld(6);
//	    taxaSeguroFaixa.setPrazoMaxOld(6);	    
//	    
//	    taxaSeguroFaixa.setTaxa(Float.valueOf(0.2f));
//		String inicioVigenciaChave = "2017-11-10 00:00:00";
//		taxaSeguroFaixa.setUpdatable(Boolean.TRUE);
//		
//		Retorno retorno = taxaSeguroBean.salvar(taxaSeguroFaixa, inicioVigenciaChave, "test");
//	    assertEquals(Long.valueOf(0), retorno.getCodigoRetorno());
//	    assertEquals(SUCESSO, retorno.getTipoRetorno());
//	}
//	
//	@Test
//	public void testExcluirTaxaSeguro() throws Exception {
//		createMocksDefault();
//
//	    TaxaSeguroFaixa taxaSeguroFaixa = new TaxaSeguroFaixa();
//	    taxaSeguroFaixa.setId(5);
//	    taxaSeguroFaixa.setInicioVigencia("2017-07-01");
//	    taxaSeguroFaixa.setFimVigencia("");
//	    taxaSeguroFaixa.setIdadeMin(14);
//	    taxaSeguroFaixa.setIdadeMax(20);
//	    taxaSeguroFaixa.setPrazoMin(16);
//	    taxaSeguroFaixa.setPrazoMax(30);
//	    taxaSeguroFaixa.setTaxa(Float.valueOf(0.2f));
//	    taxaSeguroFaixa.setExcludable(Boolean.TRUE);
//		
//		Retorno retorno = taxaSeguroBean.excluir(taxaSeguroFaixa);
//	    assertEquals(Long.valueOf(0), retorno.getCodigoRetorno());
//	    assertEquals(SUCESSO, retorno.getTipoRetorno());
//	}
//	
//	@Test
//	public void testListarTaxaSeguroComData() throws Exception {
//		createMocksDefault();
//
//	    TaxaSeguroFaixa taxaSeguroFaixa = new TaxaSeguroFaixa();
//	    taxaSeguroFaixa.setId(5);
//	    taxaSeguroFaixa.setInicioVigencia("2017-07-01");
//	    taxaSeguroFaixa.setFimVigencia("");
//	    taxaSeguroFaixa.setIdadeMin(14);
//	    taxaSeguroFaixa.setIdadeMax(20);
//	    taxaSeguroFaixa.setPrazoMin(16);
//	    taxaSeguroFaixa.setPrazoMax(30);
//	    taxaSeguroFaixa.setTaxa(Float.valueOf(0.2f));
//	    
//	    ArrayList<TaxaSeguroTO> result = new ArrayList<TaxaSeguroTO>();
//	    addTaxaSeguroFakes(result);
//		when(query.getResultList()).thenReturn(result);
//		
//		List<TaxaSeguroFaixa> retorno = taxaSeguroBean.listar("25052017", "25052017");//ddMMyyyy
//	    assertEquals(2, retorno.size());
//	}
//
//	/**
//	 * @param result
//	 */
//	private void addTaxaSeguroFakes(ArrayList<TaxaSeguroTO> result) {
//		TaxaSeguroTO t = new TaxaSeguroTO();
//	    t.setDataInclusaoFimVigencia(new Date());
//	    t.setDataInclusaoTaxa(new Date());
//	    t.setFimVigencia(new Date());
//	    t.setIdade(20);
//	    t.setInicioVigencia(new Date());
//	    t.setPrazo(12);
//	    t.setTaxa(0.5f);
//	    t.setUsuarioInclusao("test");
//		result.add(t);
//		t = new TaxaSeguroTO();
//	    t.setDataInclusaoFimVigencia(new Date());
//	    t.setDataInclusaoTaxa(new Date());
//	    t.setFimVigencia(new Date());
//	    t.setIdade(25);
//	    t.setInicioVigencia(new Date());
//	    t.setPrazo(14);
//	    t.setTaxa(0.5f);
//	    t.setUsuarioInclusao("test");
//	    result.add(t);
//	}
//	
//	@Test
//	public void testListarTaxaSeguro() throws Exception {
//		createMocksDefault();
//
//	    TaxaSeguroFaixa taxaSeguroFaixa = new TaxaSeguroFaixa();
//	    taxaSeguroFaixa.setId(5);
//	    taxaSeguroFaixa.setInicioVigencia("2017-07-01");
//	    taxaSeguroFaixa.setFimVigencia("");
//	    taxaSeguroFaixa.setIdadeMin(14);
//	    taxaSeguroFaixa.setIdadeMax(20);
//	    taxaSeguroFaixa.setPrazoMin(16);
//	    taxaSeguroFaixa.setPrazoMax(30);
//	    taxaSeguroFaixa.setTaxa(Float.valueOf(0.2f));
//	    
//	    ArrayList<TaxaSeguroTO> result = new ArrayList<TaxaSeguroTO>();
//	    addTaxaSeguroFakes(result);
//		when(query.getResultList()).thenReturn(result);
//		
//		List<TaxaSeguroFaixa> retorno = taxaSeguroBean.listar(null, null);
//	    assertEquals(2, retorno.size());
//	}
//
//}
