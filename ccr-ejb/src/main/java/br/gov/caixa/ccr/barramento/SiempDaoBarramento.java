package br.gov.caixa.ccr.barramento;

import br.gov.caixa.arqrefcore.barramento.AbstractDAOBarramento;
import br.gov.caixa.ccr.enums.FilasMQ;

/**
 * DAOBarramento para o SIEMP.
 * 
 * @author sidney
 *
 */
public class SiempDaoBarramento extends AbstractDAOBarramento {

	@Override
	protected String factory() {
		return FilasMQ.FACTORY_SIEMP.toString();
	}

	
}
