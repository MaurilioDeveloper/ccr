package br.gov.caixa.ccr.negocio;

import java.util.List;
import br.gov.caixa.ccr.dominio.Combo;

public interface IComboBean {

	List<Combo> consultar(Combo combo) throws Exception;
	
}
