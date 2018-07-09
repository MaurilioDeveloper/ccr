package br.gov.caixa.ccr.negocio;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

import br.gov.caixa.ccr.dominio.Documento;
import br.gov.caixa.ccr.dominio.Retorno;


@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class DocumentoValidacao implements IDocumentoValidacao {
 
	@PersistenceContext
	private EntityManager em;
	
    @Context
	private SecurityContext securityContext;
	 
	@Override
	public Retorno validar(Documento doc, String usuario) throws Exception {	
		Retorno retorno = null;	
		return retorno;
	}
}
