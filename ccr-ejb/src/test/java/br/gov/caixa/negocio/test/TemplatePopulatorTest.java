
//package br.gov.caixa.negocio.test;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Scanner;
//
//import javax.inject.Inject;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import br.gov.caixa.arqrefservices.dominio.sicli.CamposRetornados;
//import br.gov.caixa.ccr.dominio.transicao.CampoTO;
//import br.gov.caixa.ccr.dominio.transicao.CampoUsadoTO;
//import br.gov.caixa.ccr.dominio.transicao.DocumentoTO;
//import br.gov.caixa.ccr.template.TemplatePopulator;
//import br.gov.caixa.ccr.template.config.FreeMarkerConfig;
//import freemarker.core.ParseException;
//import freemarker.template.TemplateException;
////import br.gov.caixa.negocio.test.WeldJUnit4Runner;
//
////@RunWith(WeldJUnit4Runner.class)
//public class TemplatePopulatorTest {
//	
//	@Inject
//	TemplatePopulator templatePop;
//	
//	@Inject
//	FreeMarkerConfig freeMarkerConfig;
//
//	@SuppressWarnings("resource")
//	@Test
//	public void testTemplate() throws ParseException, TemplateException, IOException {
//		
//		
//		InputStream resourceAsStream = TemplatePopulatorTest.class.getResourceAsStream("/templates/extratoMensalCotista.ftlh");
//		Scanner s = new Scanner(resourceAsStream).useDelimiter("\\A");
//		String result = s.hasNext() ? s.next() : "";
//		
////		Template template = configuration.getTemplate("extratoMensalCotista.ftlh");
//		// objeto entity da base de dados
//		DocumentoTO doc = new DocumentoTO();
//		doc.setDeModelo(result);
//		List<CampoUsadoTO> campoUsados = new ArrayList<CampoUsadoTO>();
//		CampoUsadoTO c = new CampoUsadoTO();
//		CampoTO campo = new CampoTO();
//		campo.setDeOrigemCampo("TEST");
//		campo.setNoCampo("NOME_COTISTA");
//		campo.setNoCampoJava("ExtratoMensalCotista.nomeCotista");
//		c.setCampo(campo);
//		campoUsados.add(c);
//		doc.setCampoUsados(campoUsados);
//		doc.setNoDocumento("bla");
//		//		Arquivo arq = dp.gerarArquivoFromTemplateString("bla", result, oList);
//		List<Object> l = new ArrayList<Object>();
//		//l.add(MockCreator.createMock(ExtratoMensalCotista.class));
//		l.add(new CamposRetornados());
//		
//		templatePop.gerarArquivoFromDocumento(doc, l);
//		
//		System.out.println("");
//	}
//}