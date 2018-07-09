package br.gov.caixa.ccr.negocio.bo.cobol;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Singleton;

//import br.gov.caixa.apx.dominio.enumeracao.EMensagensAPX;
//import br.gov.caixa.apx.util.MensagemAPX;
import br.gov.caixa.arqrefcore.cobol.conexao.Cics;
import br.gov.caixa.arqrefcore.excecao.SystemException;
import br.gov.caixa.arqrefcore.excecao.enumerador.ECategoriaErro;
import br.gov.caixa.arqrefcore.excecao.enumerador.ESeveridadeErro;
import br.gov.caixa.arqrefcore.excecao.mensagem.UtilMensagem;
import br.gov.caixa.seguranca.LoginException;

/**
 * Classe que faz as chamadas aos programas COBOL.
 * 
 * @author c110503
 *
 */
@Singleton
public class FabricaConexaoCobol {

	//log
	private Logger log = Logger.getLogger(getClass().getName());
	
	@Inject
	private Instance<CicsConexao> cicsConexao;
	//classe de conexao
//	private CicsConnection connection;
	
	
	/**
	 * Metodo que conecta ao COBOL para execucao dos programas.
	 * @TODO
	 * 		VERIFICAR A QUESTAO DO ISLOGGED (connection.isLogged())
	 * @return connection
	 */
	@Produces
	@Cics
	public CicsConexao getConnection() {
		log.info("ConnectionFactory - INICIO getConnection");
		CicsConexao connection = cicsConexao.get();
		log.info("LOGADO COBOL --> " +connection.isLogged());
		try {
			if (!connection.isLogged()) {				
				// Inicializacao dos valores de usuario e senha.
				/*connectSis.inicializarValores();*/
				String usuario = System.getProperty("SIAPX_USUARIO");
				String senha = System.getProperty("SIAPX_PASSWORD");
				//verifica se a propriedade USUARIO foi setada no servidor
				if(usuario == null || usuario.isEmpty()){
					//lanca excecao
					/*MensagemAPX msgAPX = this.getMensagemErro();
					log.info(msgAPX.toString());
					throw new SystemException(msgAPX);*/
				}
				//verifica se a propriedade SENHA foi setada no servidor
				if(senha == null || senha.isEmpty()){
					//lanca excecao
					/*MensagemAPX msgAPX = this.getMensagemErro();
					log.info(msgAPX.toString());
					throw new SystemException(msgAPX);*/
				}
				//TRANSACAO SFECAPXD PXFC
				connection.setUsername(usuario);
				connection.setPassword(senha);
				connection.setSystem("SIAPX");
				connection.setPrefix("PX");
				connection.setTransId("PXFC");
				
			}
			
			connection.login();
			log.info("ConnectionFactory - FIM getConnection");
			return connection;
			/*} catch (SegurancaException e) {
			connection = null;
			throw new RuntimeException(e);*/
		} catch (LoginException e) {
			log.info("LoginException");
			log.info(e.toString());
			connection = null;
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Metodo para retorna mensagem de erro quando 
	 * os parametros de configuracao de usuario e/ou senha
	 * nao foram criados no servidor.
	 * 
	 * @return MensagemTutorial
	 */
	/*private MensagemAPX getMensagemErro(){
		log.info("ConnectionFactory - INCIO getMensagemErro");
		MensagemAPX msgAPX = new MensagemAPX();
		msgAPX.setCategoriaErro(ECategoriaErro.ERRO_CONFIGURACAO_AMBIENTE.getDescricao());
		msgAPX.setCodigoErro(""+ECategoriaErro.ERRO_CONFIGURACAO_AMBIENTE.getValor());
		msgAPX.setSeveridade(ESeveridadeErro.SEVERIDADE_ERRO.getValor());
		msgAPX.setSeveridadeDescricao(ESeveridadeErro.SEVERIDADE_ERRO.getDescricao());
		msgAPX.setInformacoesAdicionais("");
		msgAPX.setMensagemErro(new ArrayList<String>());
		// Lista de Mensagens negocial
		List<String> listaMensagemNegocial = new ArrayList<String>();
		// Parametro vindo de um enunerador
		listaMensagemNegocial.add(EMensagensAPX.ME_001.getDescricao());
		msgAPX.setMensagemNegocial(listaMensagemNegocial);
		msgAPX.setOrigemErro(FabricaConexaoCobol.class.getName());
		msgAPX.setParagrafoErro(UtilMensagem.getMetodoNome());
		log.info(msgAPX.toString());
		log.info("ConnectionFactory - FIM getMensagemErro");
		return msgAPX;
	}*/
}
