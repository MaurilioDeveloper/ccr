package br.gov.caixa.ccr.servico.mq;

import br.gov.caixa.arqrefcore.barramento.AbstractDAOBarramento;
import br.gov.caixa.ccr.dominio.barramento.enumerador.EFilasMQ;

public class ConexaoMINUTAMQ extends AbstractDAOBarramento {
	protected String factory() {
		return EFilasMQ.FACTORY_MINUTA.toString();
	}
}

