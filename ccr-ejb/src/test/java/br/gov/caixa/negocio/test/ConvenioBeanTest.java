//package br.gov.caixa.negocio.test;
//
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
//import br.gov.caixa.ccr.dominio.transicao.ConvenioTO;
//import br.gov.caixa.ccr.dominio.transicao.TaxaIOFTO;
//import br.gov.caixa.ccr.negocio.ConvenioBean;
//
//@RunWith(MockitoJUnitRunner.class)
//public class ConvenioBeanTest {
//	
//	private static final String ERRO_NEGOCIAL = "ERRO_NEGOCIAL";
//
//	private static final String SUCESSO = "SUCESSO";
//
//	@InjectMocks
//	private ConvenioBean convenioBean;
//	
//	@Mock
//	private EntityManager em;
//	
//	@Mock
//	private TypedQuery<ConvenioTO> query;
//	
//	@Before
//	public void before() {
//		convenioBean = new ConvenioBean();
//	    MockitoAnnotations.initMocks(this);
//	}
//	
//	private void createMocksDefault() {
//		when(em.createNamedQuery("TaxaIOF.incluir", ConvenioTO.class)).thenReturn(query);
//		
//	    when(query.executeUpdate()).thenReturn(1);
//	    when(query.getResultList()).thenReturn(new ArrayList<ConvenioTO>());
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
//	//TODO BROETO montar junits mockados
//	
//	//sucesso
//	@Test
//	public void testIncluirConvenio() throws Exception {
//		createMocksDefault();
//		// TODO
//	}
//	
//	//FE2
//	//FE3
//	//FE4
//	//FE5
//	//FE6
//	//FE7
//	//FE8
//	//FE9
//	//FE10
//	//FE11
//	//FE12
//	//FE13
//	//FE14
//	//FE15
//	//FE16
//}
