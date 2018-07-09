package br.gov.caixa.ccr.negocio.bo.cobol;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.logging.Logger;

import br.gov.caixa.arqrefcore.cobol.conexao.CicsConnection;
import br.gov.caixa.util.jcicsconnect.MensagemCics;

/**
 * Executa as chamadas aos programas COBOL
 * esta sendo usado XML para envio e retorno
 * 
 * @author 
 *
 */
public class CicsConexao extends CicsConnection implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3093407918530924369L; 

	protected Logger log = Logger.getLogger(getClass().getName());
	
	public String execute(String programa, String dadosEnvio, char tipo) {
		log.info("CicsConexao - INICIO execute");
		log.fine("XML Entrada: \n" + dadosEnvio);
		String resultado = "";

		MensagemCics msg = super.execute(programa, String.valueOf(tipo), dadosEnvio);
		
		try {
			resultado = new String(converteISOParaUTF(msg.getDadosRetorno()));
			//adicionando cabecalho
			resultado =  this.verificarRetorno(resultado);
			log.fine("XML Output: \n" + resultado);
		} catch (UnsupportedEncodingException e) {
			log.severe(e.getMessage());
		}
		log.info("CicsConexao - FIM execute");
		return resultado;
	}

	private String converteISOParaUTF(String iso) throws UnsupportedEncodingException {
		log.info("CicsConexao - INICIO execute");
		log.fine(iso);
		log.info("CicsConexao - FIM execute");
		return new String(iso.getBytes("UTF-8"));
	}
	
	private String verificarRetorno(String resultado){
		log.info("CicsConexao - INICIO verificarRetorno");
		if(resultado != null && (resultado.startsWith("<") && !resultado.contains("xml"))){
			log.fine("corrigindo xml de retorno");
			resultado = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" + resultado;
		}
		log.fine(resultado);
		log.info("CicsConexao - FIM verificarRetorno");
		return resultado;
	}

}
