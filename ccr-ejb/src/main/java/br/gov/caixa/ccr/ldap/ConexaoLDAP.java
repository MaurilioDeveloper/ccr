package br.gov.caixa.ccr.ldap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import br.gov.caixa.arqrefcore.excecao.SystemException;
import br.gov.caixa.arqrefcore.excecao.enumerador.ECategoriaErro;
import br.gov.caixa.arqrefcore.excecao.enumerador.EMensagensErroARQ;
import br.gov.caixa.arqrefcore.excecao.enumerador.ESeveridadeErro;
import br.gov.caixa.arqrefcore.excecao.mensagem.Message;
import br.gov.caixa.arqrefcore.excecao.mensagem.MessageDefault;
import br.gov.caixa.arqrefcore.excecao.mensagem.Messages;
import br.gov.caixa.arqrefcore.excecao.mensagem.UtilMensagem;

/**
 * Clase usada para conectar no LDAP EXTERNO e buscar dados do usuario
 * 
 * @version 1.0.0.1
 * @author c110503
 *
 */
public class ConexaoLDAP {
	
	private Logger log = Logger.getLogger(getClass().getName());

	/**
	 * Metodo que busca as informacoes do usuario interno o filto eh o login do
	 * usuario
	 * 
	 * @param filtro
	 * @return mapAtributos
	 */
	public HashMap<String, String> getAtributosUsuarioInternoPorLogin(String filtro) {
		return getAtributosUsuarioPorLogin(filtro, "SICCR_URL_LDAP_INTERNO", "SICCR_BASE_INTERNO", "INITIAL_CONTEXT_FACTORY");
	}

	/**
	 * Metodo que busca as informacoes do usuario externo o filto eh o login do
	 * usuario
	 * 
	 * @param filtro
	 * @return mapAtributos
	 */
	public HashMap<String, String> getAtributosUsuarioExternoPorLogin(String filtro) {
		return getAtributosUsuarioPorLogin(filtro , "SICCR_URL_LDAP_EXTERNO", "SICCR_BASE_EXTERNO", "INITIAL_CONTEXT_FACTORY");
	}

	public HashMap<String, String> getAtributosUsuarioPorLogin(String filtro, String ldapURL, String base, String initialContextFactory) {
		log.info("<<------------Inicio da execucao ConexaoLDAP.getAtributosUsuarioPorLogin ------------>> ");
		try {
			Hashtable<String, String> env = new Hashtable<String, String>();

			// Cria variaveis para conexao e busca no LDAP
			String LDAP_URL = "";
			String BASE = "";
			String INITIAL_CONTEXT_FACTORY = "";
			
			// Cria variavel para lancar mensagens de erro
			Messages listaMessagesErro = new Messages();
			
			// cria a lista de mensagens de erro
			listaMessagesErro.setMessages(new ArrayList<Message>());
			
			// verifica variavel LDAP_URL e instancia
			if (System.getProperty(ldapURL) != null) {
				LDAP_URL = System.getProperty(ldapURL);
			} else {
				// cria mensagem de excecao por nao ter a variavel configurada
				MessageDefault msg = new MessageDefault();
				msg.setCategoriaErro(ECategoriaErro.ERRO_LDAP.getDescricao());
				msg.setCodigoErro(Integer.toString(ECategoriaErro.ERRO_LDAP.getValor()));
				msg.setInformacoesAdicionais(EMensagensErroARQ.ME_001.getDescricao());
				msg.setOrigemErro(UtilMensagem.getMetodoNome());
				msg.setSeveridade(ESeveridadeErro.SEVERIDADE_ERRO.getValor());
				msg.setSistema(ECategoriaErro.ERRO_LDAP.getDescricao());
				
				// adiciona a lista de mensagens de erro
				listaMessagesErro.add(msg);

			}
			
			// verifica variavel BASE e instancia
			if (System.getProperty(base) != null) {
				BASE = System.getProperty(base);
			} else {
				// cria mensagem de excecao por nao ter a variavel configurada
				MessageDefault msg = new MessageDefault();
				msg.setCategoriaErro(ECategoriaErro.ERRO_LDAP.getDescricao());
				msg.setCodigoErro(Integer.toString(ECategoriaErro.ERRO_LDAP.getValor()));
				msg.setInformacoesAdicionais(EMensagensErroARQ.ME_002.getDescricao());
				msg.setOrigemErro(UtilMensagem.getMetodoNome());
				msg.setSeveridade(ESeveridadeErro.SEVERIDADE_ERRO.getValor());
				msg.setSistema(ECategoriaErro.ERRO_LDAP.getDescricao());
				
				// adiciona a lista de mensagens de erro
				listaMessagesErro.add(msg);
			}
			
			// verifica variavel INITIAL_CONTEXT_FACTORY e instancia
			if (System.getProperty(initialContextFactory) != null) {
				INITIAL_CONTEXT_FACTORY = System.getProperty(initialContextFactory);
			} else {
				// cria mensagem de excecao por nao ter a variavel configurada
				MessageDefault msg = new MessageDefault();
				msg.setCategoriaErro(ECategoriaErro.ERRO_LDAP.getDescricao());
				msg.setCodigoErro(Integer.toString(ECategoriaErro.ERRO_LDAP.getValor()));
				msg.setInformacoesAdicionais(EMensagensErroARQ.ME_003.getDescricao());
				msg.setOrigemErro(UtilMensagem.getMetodoNome());
				msg.setSeveridade(ESeveridadeErro.SEVERIDADE_ERRO.getValor());
				msg.setSistema(ECategoriaErro.ERRO_LDAP.getDescricao());
				
				// adiciona a lista de mensagens de erro
				listaMessagesErro.add(msg);
			}

			// verifica se alguma das condicoes a cima nao foi satisfeita
			if (listaMessagesErro.getMessages().size() > 0) {
				log.warning("<<------------ ERRO: Erro execucao ConexaoLDAP.getAtributosUsuarioPorLogin ------------>> ");
				throw new SystemException(listaMessagesErro);
			}
			
			log.fine("<<--------- LDAP: FABRICA DE CONEXAO: " + INITIAL_CONTEXT_FACTORY);
			log.fine("<<--------- LDAP: URL DE BUSCA : " + LDAP_URL);
			log.fine("<<--------- LDAP: BASE: " + BASE);

			// seta o contexto de fabrcia
			env.put(Context.INITIAL_CONTEXT_FACTORY, INITIAL_CONTEXT_FACTORY);
			
			// seta a url de busca
			env.put(Context.PROVIDER_URL, LDAP_URL);
			
			// cria e instancia a variavel de diretorio
			DirContext dctx = new InitialDirContext(env);			
			
			log.fine("<<--------- LDAP: BASE: PROPRIEDADE ");
			
			// cria variavel de busca
			SearchControls sc = new SearchControls();

			// seta o escopo da busca
			sc.setSearchScope(SearchControls.SUBTREE_SCOPE);

			// seta o tipo de filtro para busca
			String filter = "(&(objectClass=person)(uid=" + filtro + "))";

			// cria o mapa de atributos para retorno
			HashMap<String, String> mapAtributos = new HashMap<String, String>();

			// cria a iteracao a partir da busca no diretorio
			NamingEnumeration<?> results = dctx.search(BASE, filter, sc);

			while (results.hasMore()) {
				// obtem o objeto da busca na iteracao
				SearchResult sr = (SearchResult) results.next();
				// obtem os atributos do resultado
				Attributes attrs = sr.getAttributes();
				// verifica se os atributos nao sao nulos
				if (attrs != null) {
					// cria iteracao no atributos
					NamingEnumeration<String> ldapAttrsId = attrs.getIDs();
					while (ldapAttrsId.hasMoreElements()) {
						// obtem o atributo da iteracao
						Attribute ldapAttr = attrs.get(ldapAttrsId.nextElement());
						// seta no mapa a origem do atributo e o seu valor
						mapAtributos.put(ldapAttr.getID(), ldapAttr.get().toString());
					}
				}
			}
			
			// fecha a conecao com a arvore de diretorios
			dctx.close();
			
			// retorn o mapa com as chave/valores
			log.info("<<------------Termino da execucao ConexaoLDAP.getAtributosUsuarioPorLogin ------------>> ");
			
			return mapAtributos;
		} catch (NamingException e) {
			log.warning("<<------------ ERRO: Erro execucao ConexaoLDAP.getAtributosUsuarioPorLogin ------------>> ");
			MessageDefault msg = new MessageDefault();
			msg.setSeveridade(ESeveridadeErro.SEVERIDADE_ERRO.getValor());
			msg.getMensagemErro().add(e.toString());
			msg.setSistema("ARQREFCORE");
			msg.setCodigoErro(e.getExplanation());
			msg.setCategoriaErro(ECategoriaErro.ERRO_JAVA.getDescricao());
			msg.setOrigemErro(e.getClass().getName());
			msg.setParagrafoErro(UtilMensagem.getMetodoNome());
			throw new SystemException(msg);
		}
		
		
	}
}