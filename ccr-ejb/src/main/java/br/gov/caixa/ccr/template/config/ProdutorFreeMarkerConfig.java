package br.gov.caixa.ccr.template.config;

import javax.enterprise.inject.Produces;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;

public class ProdutorFreeMarkerConfig {
	
	@Produces
	public FreeMarkerConfig getInstance() {
		Configuration configuration = new Configuration(Configuration.VERSION_2_3_23);
		configuration.setDefaultEncoding("UTF-8");
		configuration.setClassForTemplateLoading(getClass(), "/templates/");
		FreeMarkerConfig instance = new FreeMarkerConfig(configuration);
		
		if (!(instance.getConfiguration().getTemplateLoader() instanceof StringTemplateLoader)) {
			StringTemplateLoader tloader = new StringTemplateLoader();
			instance.getConfiguration().setTemplateLoader(tloader);
		}
		return instance;
	}
}
