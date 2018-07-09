//package br.gov.caixa.negocio.test;
//
//import static org.junit.Assert.assertEquals;
//import static org.mockito.Mockito.when;
//
//import java.util.ArrayList;
//import java.util.Calendar;
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
//import br.gov.caixa.ccr.dominio.TaxaIOF;
//import br.gov.caixa.ccr.dominio.transicao.TaxaIOFTO;
//import br.gov.caixa.ccr.negocio.TaxaIOFBean;
//import br.gov.caixa.ccr.negocio.TaxaSeguroBean;
//
//@RunWith(MockitoJUnitRunner.class)
//public class TaxaIofBeanTest {
//	
//	private static final String ERRO_NEGOCIAL = "ERRO_NEGOCIAL";
//
//	private static final String SUCESSO = "SUCESSO";
//
//	@InjectMocks
//	private TaxaSeguroBean taxaSeguroBean;
//	
//	@InjectMocks
//	private TaxaIOFBean taxaIOFBean;
//	
//	@Mock
//	private EntityManager em;
//	
//	@Mock
//	private TypedQuery<TaxaIOFTO> query;
//	
//	@Before
//	public void before() {
//		taxaSeguroBean = new TaxaSeguroBean();
//	    MockitoAnnotations.initMocks(this);
//	}
//	
//	private void createMocksDefault() {
//		when(em.createNamedQuery("TaxaIOF.incluir", TaxaIOFTO.class)).thenReturn(query);
//		when(em.createNamedQuery("TaxaIOF.verificaVigenciaValidaIncSemFim", TaxaIOFTO.class)).thenReturn(query);
//		when(em.createNamedQuery("TaxaIOF.fecharVigenciaAberta", TaxaIOFTO.class)).thenReturn(query);
//		when(em.createNamedQuery("TaxaIOF.atualizarVigenciaFechada", TaxaIOFTO.class)).thenReturn(query);
//		when(em.createNamedQuery("TaxaIOF.excluirRange", TaxaIOFTO.class)).thenReturn(query);
//		when(em.createNamedQuery("TaxaIOF.listar", TaxaIOFTO.class)).thenReturn(query);
//		when(em.createNamedQuery("TaxaIOF.atualizar", TaxaIOFTO.class)).thenReturn(query);
//		when(em.createNamedQuery("TaxaIOF.listarPorInicioVigencia", TaxaIOFTO.class)).thenReturn(query);
//		
//	    when(query.executeUpdate()).thenReturn(1);
//	    when(query.getResultList()).thenReturn(new ArrayList<TaxaIOFTO>());
//	    
//	    TaxaIOFTO taxa = new TaxaIOFTO();
//		Calendar cal = Calendar.getInstance();
//		cal.set(Calendar.YEAR, 2299);
//		taxa.setInicioVigencia(cal.getTime());
//		when(em.find(TaxaIOFTO.class, 5)).thenReturn(taxa);
//		
////		when(em.remove(Mockito.anyObject())).thenReturn(taxa);
//		
//		
//	}
//	
//	@Test
//	public void testIncluirTaxaIOF() throws Exception {
//		createMocksDefault();
//
//		TaxaIOF taxaIOF = new TaxaIOF();
//		taxaIOF.setInicioVigencia("2017-07-01");
//		taxaIOF.setAliquotaBasica(0.1f);
//		taxaIOF.setAliquotaComplementar(0.2f);
//		Retorno retorno = taxaIOFBean.salvar(taxaIOF, "test");
//	    assertEquals(Long.valueOf(0), retorno.getCodigoRetorno());
//	    assertEquals(SUCESSO, retorno.getTipoRetorno());
//	}
//
//	@Test
//	public void testIncluirTaxaIOFVigente() throws Exception {
//		createMocksDefault();
//		ArrayList<TaxaIOFTO> lista = new ArrayList<TaxaIOFTO>();
//	    lista.add(new TaxaIOFTO());
//		when(query.getResultList()).thenReturn(lista);
//		
//		TaxaIOF taxaIOF = new TaxaIOF();
//		taxaIOF.setInicioVigencia("2017-07-01");
//		taxaIOF.setAliquotaBasica(0.1f);
//		taxaIOF.setAliquotaComplementar(0.2f);
//		Retorno retorno = taxaIOFBean.salvar(taxaIOF, "test");
//	    assertEquals(Long.valueOf(1), retorno.getCodigoRetorno());
//	    assertEquals(ERRO_NEGOCIAL, retorno.getTipoRetorno());
//	    assertEquals("MA0052", retorno.getIdMsg());
//	}
//	
//	@Test
//	public void testAlterarTaxaIOF() throws Exception {
//		createMocksDefault();
//		
//		TaxaIOF taxaIOF = new TaxaIOF();
//		taxaIOF.setId(5);
//		taxaIOF.setInicioVigencia("2017-07-01");
//		taxaIOF.setAliquotaBasica(0.1f);
//		taxaIOF.setAliquotaComplementar(0.2f);
//		taxaIOF.setUpdatable(Boolean.TRUE);
//		Retorno retorno = taxaIOFBean.salvar(taxaIOF, "test");
//	    assertEquals(Long.valueOf(0), retorno.getCodigoRetorno());
//	    assertEquals(SUCESSO, retorno.getTipoRetorno());
//	}
//	
//	
//	@Test
//	public void testExcluirTaxaIOF() throws Exception {
//		createMocksDefault();
//
//		Retorno retorno = taxaIOFBean.excluir(5);
//	    assertEquals(Long.valueOf(0), retorno.getCodigoRetorno());
//	    assertEquals(SUCESSO, retorno.getTipoRetorno());
//	}
//
//}
