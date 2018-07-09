package br.gov.caixa.ccr.rest;

import java.io.IOException;

import javax.annotation.security.PermitAll;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import br.gov.caixa.arqrefcore.versao.Version;
import br.gov.caixa.arqrefcore.versao.VersionService;

@RequestScoped
@Path("/global")
public class GlobalRest extends AbstractSecurityRest {

	@Inject
	private VersionService versionService;
	
	@PermitAll
	@POST
	@Path("/version")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Version version() {
		try {
			return versionService
					.with(getClass().getClassLoader())
					.with("global.properties")
					.ignoreSnapshot()
						.load()
							.get();
		} catch (IOException e) {
			return null;
		}
	}
	
	@GET
	@Path("/logout")
	public void logout(@Context HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		
		if (session != null) {
			session.invalidate();
		}
	}
}