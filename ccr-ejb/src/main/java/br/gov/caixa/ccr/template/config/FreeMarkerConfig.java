package br.gov.caixa.ccr.template.config;

import freemarker.template.Configuration;

public class FreeMarkerConfig {
	
	public static final String CHARSET = "ISO-8859-1";

	public FreeMarkerConfig(Configuration configuration) {
		super();
		this.configuration = configuration;
	}

	private Configuration configuration;
	
	public Configuration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}
}
