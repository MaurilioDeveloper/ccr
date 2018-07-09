package br.gov.caixa.ccr.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.annotation.security.PermitAll;
import javax.enterprise.context.RequestScoped;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import br.gov.caixa.arqrefcore.excecao.BusinessException;
import br.gov.caixa.ccr.dominio.Empregado;
import br.gov.caixa.ccr.dominio.UserProfile;
@PermitAll
@RequestScoped
@Path("/userprofile")
public class UserProfileRest extends AbstractSecurityRest {

	private static Properties propriedade;

	@POST
	@Path("/load")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public UserProfile loadUserProfile() throws BusinessException {
		UserProfile user = new UserProfile();
		String usuario = getDadosUsuarioLogado().getCodigoUsuario();
		
		Empregado empregado =  getEmpregado(usuario);
		empregado.setMatriculaUsuario(usuario);
		if(empregado.getNomePessoa() == null || "null".equals(empregado.getNomePessoa())) {
			empregado.setNomePessoa("CAIXA");
		}
		user.setEmpregado(empregado);
		//user.getEmpregado().setNumeroIP(user.getNumeroIP());
		//user.getPerfilNav(user.getEmpregado(), gruposUsuarioOriginais());
		user.setGrupoUsuarioList(grupoUsuario());
				
		return user;
	}
		
	@POST
	@Path("/loadgroups")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<String> grupoUsuario()  {

		List<String> grupos = new ArrayList<String>();
		
		log.info("<<-------- Inicio da execucao UserProfileRest.grupoUsuario --------->> ");

		try {
			propriedade = new Properties();
			propriedade.load(getClass().getClassLoader().getResourceAsStream("ldap_groups.properties"));
			Set<Object> chaves = propriedade.keySet();
			
			for (Object key : chaves) {
				log.fine(">>Validando permisÃ£o para " + (String) key);
				if(temPerfilUsuario((String) key)){
					log.fine(">>>> Autorizado");
					grupos.add(propriedade.getProperty((String)key));
				}
			}
			
		} catch (IOException e) {
			log.warning("ERRO: Erro ao validar permissÃ£o: " + e.getCause());
			//new ValidationException((Exception) e.getCause());
		}

		log.info("<<------------Termino da execucao UserProfileRest.grupoUsuario ------------>> ");
		return grupos;
	}
	
	@GET
	@Path("/loadgroupsGet")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<String> grupoUsuarioGet()  {

		List<String> grupos = new ArrayList<String>();
		
		log.info("<<-------- Inicio da execucao UserProfileRest.grupoUsuario --------->> ");

		try {
			propriedade = new Properties();
			propriedade.load(getClass().getClassLoader().getResourceAsStream("ldap_groups.properties"));
			Set<Object> chaves = propriedade.keySet();
			
			for (Object key : chaves) {
				log.fine(">>Validando permisÃ£o para " + (String) key);
				if(temPerfilUsuario((String) key)){
					log.fine(">>>> Autorizado");
					grupos.add(propriedade.getProperty((String)key));
				}
			}
			
		} catch (IOException e) {
			log.warning("ERRO: Erro ao validar permissÃ£o: " + e.getCause());
			//new ValidationException((Exception) e.getCause());
		}

		log.info("<<------------Termino da execucao UserProfileRest.grupoUsuario ------------>> ");
		return grupos;
	}
	
	@GET
	@Path("/loginpasswd")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public String loginPasswd(@QueryParam("userName") String userName, 
			@QueryParam("password") String password)  {

		// Obtain a LoginContext, needed for authentication. Tell it 
		// to use the LoginModule implementation specified by the 
		// entry named "JaasSample" in the JAAS login configuration 
		// file and to also use the specified CallbackHandler.
		LoginContext lc = null;
		try {
			CallbackHandler handler = new CcrCallbackHandler(userName, password);

			lc = new LoginContext("jaas-sifec", handler);
			System.out.println("");
		} catch (LoginException le) {
		    System.err.println("Cannot create LoginContext. "
		        + le.getMessage());
		    System.exit(-1);
		} catch (SecurityException se) {
		    System.err.println("Cannot create LoginContext. "
		        + se.getMessage());
		    System.exit(-1);
		} 

		try {
		    // attempt authentication
		    lc.login();

		} catch (LoginException le) {

		    return le.getMessage();

		}

		return "Authentication succeeded!";
	}
}