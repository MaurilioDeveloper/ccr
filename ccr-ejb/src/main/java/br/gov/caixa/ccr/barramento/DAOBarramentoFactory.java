package br.gov.caixa.ccr.barramento;

import javax.ejb.Singleton;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import br.gov.caixa.arqrefcore.barramento.DAOBarramento;
import br.gov.caixa.ccr.qualifier.Siemp;

/**
 * Factory das DAO's para comunicacao com o Barramento.
 * 
 * @author eduardo
 *
 */
@Singleton
public class DAOBarramentoFactory {
	
	@Inject
	private Instance<SiempDaoBarramento> sourceSiempDAOBarramento;
	
	
	@Produces
	@Siemp
	public DAOBarramento getSiempDAOBarramento() {
		return sourceSiempDAOBarramento.get();
	}
	
}
