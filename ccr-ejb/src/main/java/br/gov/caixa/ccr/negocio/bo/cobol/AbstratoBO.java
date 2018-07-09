package br.gov.caixa.ccr.negocio.bo.cobol;

import java.util.logging.Logger;

import javax.inject.Inject;

import br.gov.caixa.ccr.negocio.bo.cobol.CicsConexao;
import br.gov.caixa.arqrefcore.cobol.conexao.Cics;
import br.gov.caixa.arqrefcore.cobol.conexao.MainframeManager;

/**
 * Classe abstrata de apoio as classes BO's
 *  
 * @author c110503
 *
 */
public abstract class AbstratoBO {

	protected Logger log = Logger.getLogger(getClass().getSimpleName());
	
	@Inject
	@Cics
	protected CicsConexao cicsConexao;
	
	/***
	 * Metodo para trocar o encoding recebido pelo COBOL
	 * IBM-1140 para UTF-8
	 */
	protected String converteEncodingCobolJava(String xml){
		log.info("AbstratoBO - INCIO - converteEncodingCobolJava");
		//verifica se o xml contem o encoding do COBOL
		if(xml.contains("IBM-1140")){
			log.fine("Encoding IBM-1140");
			log.info("AbstratoBO - FIM - converteEncodingCobolJava");
			return xml.replace("IBM-1140", "UTF-8");			
		}else{
			log.info("AbstratoBO - FIM - converteEncodingCobolJava");
			return xml;
		}
		
		
	}
}
