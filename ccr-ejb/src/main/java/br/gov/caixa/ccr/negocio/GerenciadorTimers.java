package br.gov.caixa.ccr.negocio;

import java.util.Date;

import javax.ejb.Local;

@Local
public interface GerenciadorTimers {

	void inicializaTimers();
	
	void agendarTimerSIAPX(final Date horaInicio, final Date horaFim, final Integer intervalo);
	
}
