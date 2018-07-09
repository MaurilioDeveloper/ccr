package br.gov.caixa.ccr.template;

import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.xml.datatype.XMLGregorianCalendar;

import br.gov.caixa.ccr.dominio.transicao.CampoUsadoTO;
import br.gov.caixa.ccr.dominio.transicao.DocumentoTO;
import br.gov.caixa.ccr.template.config.FreeMarkerConfig;
import br.gov.caixa.ccr.template.formatters.TemplateFormatter;
import freemarker.cache.StringTemplateLoader;
import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@Stateless
public class TemplatePopulator {

	private static final String REGEX_CAMPOS = "\\##(?<tags>.*?)\\##";
	
	@Inject
	private TemplateFormatter templateFormatter;

	/**
	 * Preenche Template de um documento que eh preenchido com campos
	 * dinamicos(sistemas, ex: CCR, SICLI, etc) e campos digitados na tela
	 * 
	 * @param templateName
	 *            O nome do template
	 * @param templateString
	 *            O conteudo do template(html)
	 * @param objectList
	 *            Lista de objetos usados para preenchimento dos campos
	 *            dinamicos do template
	 * @return
	 * @throws ParseException
	 * @throws TemplateException
	 * @throws IOException
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public String gerarArquivoFromTemplateString(String templateName, String templateString, List<Object> oList)
			throws ParseException, TemplateException, IOException, IllegalArgumentException, IllegalAccessException {

		Configuration configuration = new Configuration(Configuration.VERSION_2_3_23);
		configuration.setDefaultEncoding("UTF-8");
		configuration.setClassForTemplateLoading(getClass(), "/templates/");
		FreeMarkerConfig instance = new FreeMarkerConfig(configuration);
		
		if (!(instance.getConfiguration().getTemplateLoader() instanceof StringTemplateLoader)) {
			StringTemplateLoader tloader = new StringTemplateLoader();
			instance.getConfiguration().setTemplateLoader(tloader);
		}

		((StringTemplateLoader) configuration.getTemplateLoader()).putTemplate(templateName, templateString);
		Template template = configuration.getTemplate(templateName);

		StringWriter stringWriter = new StringWriter();
		Map<String, Object> map = new HashMap<String, Object>();
		for (Object o : oList) {
			
			Map<String, Object> mapObject = transformObjectToMap(o);
			map.put(o.getClass().getSimpleName(), mapObject);
			
			
		}
		template.process(map, stringWriter);

		return stringWriter.toString();
	}

	private Map<String, Object> transformObjectToMap(Object o) throws IllegalArgumentException, IllegalAccessException {
		Field[] declaredFields = o.getClass().getDeclaredFields();
		Map<String, Object> objetoMap = new HashMap<String, Object>();
		for (Field field : declaredFields) {
			field.setAccessible(true);
				
			Object valor = null;
			if (field.get(o) != null) {
				//se for complexo, recursao...
				if (isSimpleType(field)) {
					valor = templateFormatter.formatarValor(o, field);
				} else if (field.getType().equals(List.class)) {
					valor = transformList(o, field);
				} else {
					valor = transformObjectToMap(field.get(o));
				}
			}
			objetoMap.put(field.getName(), valor);
		}
		return objetoMap;
	}

	private Object transformList(Object o, Field field) throws IllegalAccessException {
		Object valor;
		List lista = (List) field.get(o);
		List<Object> listaResult = new ArrayList<Object>();
		for (Object object : lista) {
			Object valorTemp = transformObjectToMap(object);
			listaResult.add(valorTemp);
		}
		valor = listaResult;
		return valor;
	}

	private boolean isSimpleType(Field field) {
        Class<?> type = field.getType();
        if(type.isEnum()) {
            return true;
        } 
        if(type.equals(Integer.TYPE) || type.equals(Integer.class)) {
        	return true;
        } 
        if(type.equals(Byte.TYPE) || type.equals(Byte.class)) {
        	return true;
        }
        if(type.equals(Short.TYPE) || type.equals(Short.class)) {
        	return true;
        }
        if(type.equals(Long.TYPE) || type.equals(Long.class)) {
        	return true;
        } 
        if(type.equals(Double.TYPE) || type.equals(Double.class)) {
        	return true;
        } 
        if(type.equals(Float.TYPE) || type.equals(Float.class)) {
        	return true;
        } 
        if(type.equals(Character.TYPE) || type.equals(Character.class)) {
        	return true;
        } 
        if(type.equals(String.class)) {
        	return true;
        }
        if(type.equals(BigInteger.class)){
        	return true;
        } 
        if(type.equals(BigDecimal.class)){
        	return true;
        } 
        if (type.equals(Date.class)) {
        	return true;
        } 
        if (type.equals(Calendar.class)) {
        	return true;
        } 
        if (type.equals(Boolean.TYPE) || type.equals(Boolean.class)) {
        	return true;
        }
        if (type.equals(Map.class)) {
        	return true;
        }
        if (type.equals(Date.class)) {
        	return true;
        }
        if (type.equals(Calendar.class)) {
        	return true;
        }
        if (type.equals(XMLGregorianCalendar.class)) {
        	return true;
        }
        if (type.equals(BigDecimal.class)) {
        	return true;
        }
        return false;
    }

	/**
	 * Preenche Template de um documento que eh preenchido com campos
	 * dinamicos(sistemas, ex: CCR, SICLI, etc) e campos digitados na tela
	 * 
	 * @param doc
	 *            Template do documento, entidade documento na base de dados
	 * @param objectList
	 *            Lista de objetos usados para preenchimento dos campos
	 *            dinamicos do template
	 * @return
	 * @throws ParseException
	 * @throws TemplateException
	 * @throws IOException
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public String gerarArquivoFromDocumento(DocumentoTO doc, List<Object> objectList)
			throws ParseException, TemplateException, IOException, IllegalArgumentException, IllegalAccessException {

		String templateString = doc.getDeModelo();
		
		// busca tags campos ##CAMPO##
		List<String> camposTemplate = buscarTagsCampos(templateString);
		
		// substitui tags campos ##CAMPO## por tags campo java ${campoJava}
		templateString = substituirTagsCamposPorTagsCamposJava(templateString, camposTemplate, doc.getCampoUsados());
		
		// gera Arquivo atraves do template freemarker, utilizando para preenchimento a lista de objetos objectList
		String arquivo = gerarArquivoFromTemplateString(doc.getNoDocumento(), templateString, objectList);
		return arquivo;
	}

	/**
	 * substitui tags campos ##CAMPO## por tags campo java ${campoJava}
	 * @param templateString
	 * @param camposTemplate
	 * @param campoUsados
	 * @return
	 */
	private String substituirTagsCamposPorTagsCamposJava(String templateString, List<String> camposTemplate, List<CampoUsadoTO> campoUsados) {
		Map<String, String> deParaMap = new HashMap<String, String>();
		
		for (CampoUsadoTO campoUsadoTO : campoUsados) {
			deParaMap.put(campoUsadoTO.getCampo().getNoCampo(), campoUsadoTO.getCampo().getNoCampoJava());
		}
		for (String campo : camposTemplate) {
			String campoJava = deParaMap.get(campo);
			if (campoJava != null && !campoJava.equalsIgnoreCase("")) {
				//${(error.value)!"Default"}
				templateString = templateString.replaceAll("##"+campo+"##", "\\${("+campoJava+")!''}");
			}
		}
		return templateString;
	}

	/**
	 * busca tags campos ##CAMPO##
	 * @param templateString
	 * @return
	 */
	private List<String> buscarTagsCampos(String templateString) {
		Pattern pattern = Pattern.compile(REGEX_CAMPOS, Pattern.CASE_INSENSITIVE);
		Matcher comparator = pattern.matcher(templateString);
		List<String> allMatches = new ArrayList<String>();
		while (comparator.find()) {
			allMatches.add(comparator.group("tags"));
		}
		return allMatches;
	}
}
