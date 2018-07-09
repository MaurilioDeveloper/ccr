//package br.gov.caixa.negocio.test;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.ejb.EJB;
//
//import org.jboss.arquillian.junit.Arquillian;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//
//import br.gov.caixa.ccr.dominio.Convenio;
//import br.gov.caixa.ccr.dominio.ConvenioCanal;
//import br.gov.caixa.ccr.dominio.transicao.ConvenioTO;
//import br.gov.caixa.ccr.negocio.ConvenioBean;
//import br.gov.caixa.ccr.negocio.IConvenioBean;
//import br.gov.caixa.ccr.util.MockCreator;
//
//@RunWith(Arquillian.class)
//public class ConvenioBeanIT {
//	
//	@EJB
//	private IConvenioBean convenioBean;
//	
//	////@Test
//	public void testListarConvenioNoFilter2() throws Exception {
//		Integer id = null; 
//		Long cnpj= null; 
//		String nome = null; 
//		Integer situacao  = null; 
//		Integer sr = null; 
//		Integer agencia = null;
//		
//		System.out.println(convenioBean.lista(id, cnpj, nome, situacao, sr, agencia,null));
//	}
//	
//	//@Test
//	public void testListarConvenioNome2() throws Exception {
//		Integer id = null; 
//		Long cnpj= null; 
//		String nome = "TESTE CONVENIO"; 
//		Integer situacao  = null; 
//		Integer sr = null; 
//		Integer agencia = null;
//		
//		//System.out.println(convenioBean.lista(id, cnpj, nome, situacao, sr, agencia));
//	}
//
//	////@Test
//	public void testIncluirConvenio() throws Exception {
//		Convenio convenio = MockCreator.createMock(Convenio.class);
//		List<ConvenioCanal> canais = convenio.getCanais();
//		for (ConvenioCanal convenioCanal : canais) {
//			convenioCanal.setIndTaxaJuroPadrao("A");
//		}
//		
//		List<ConvenioCanal> canList = new ArrayList<>(); 
//		canList.add(canais.get(0));
//		convenio.setCanais(canList);
//		Gson gson = new GsonBuilder().setPrettyPrinting().create();
//		String json = gson.toJson(convenio);
//		System.out.println(json);
//		
//		System.out.println(convenioBean.salvar(convenio, "c891843", true));
//	}
//}
