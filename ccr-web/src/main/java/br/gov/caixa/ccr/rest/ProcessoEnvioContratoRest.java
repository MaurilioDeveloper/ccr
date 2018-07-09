package br.gov.caixa.ccr.rest;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import br.gov.caixa.ccr.dominio.ProcessoEnvioContrato;
import br.gov.caixa.ccr.dominio.transicao.ProcessoEnvioContratoTO;
import br.gov.caixa.ccr.negocio.GerenciadorTimers;
import br.gov.caixa.ccr.negocio.IProcessoEnvioContratoBean;

@RequestScoped
@Path("/processoenviocontrato")
public class ProcessoEnvioContratoRest extends AbstractSecurityRest{

	@Inject
	private IProcessoEnvioContratoBean processoEnvioContratoBean;
	
	@Inject
	private GerenciadorTimers gerenciadorTimers;
	
	@POST
	@Path("/atualizar")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ProcessoEnvioContrato atualizarProcessoEnvioContrato(@QueryParam("horaInicio") String horaInicio, @QueryParam("horaFim") String horaFim, @QueryParam("intervalo") String intervalo) throws ParseException{
		SimpleDateFormat SDF_HORA = new SimpleDateFormat( "HH:mm", new DateFormatSymbols( new Locale( "pt", "BR" ) ) );
		
		Date inicio = SDF_HORA.parse(horaInicio);
		Date fim = SDF_HORA.parse(horaFim);
		
		ProcessoEnvioContrato vo = new ProcessoEnvioContrato();
		ProcessoEnvioContratoTO entity = processoEnvioContratoBean.atualizar(inicio, fim, new Integer(intervalo));
		
		vo.setId(entity.getId());
		vo.setProcesso(entity.getProcesso().toString());
		vo.setHoraFim(entity.getHoraFim());
		vo.setHoraInicio(entity.getHoraInicio());
		vo.setIntervalo(entity.getIntervalo());
		
		gerenciadorTimers.agendarTimerSIAPX(inicio, fim, vo.getIntervalo());
		
		return vo;
	}
	
	@GET
	@Path("/consultar")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ProcessoEnvioContrato buscarProcessoEnvioContrato(){
		ProcessoEnvioContrato vo = new ProcessoEnvioContrato();
		ProcessoEnvioContratoTO entity = processoEnvioContratoBean.buscar();
		
		vo.setId(entity.getId());
		vo.setProcesso(entity.getProcesso().toString());
		vo.setHoraFim(entity.getHoraFim());
		vo.setHoraInicio(entity.getHoraInicio());
		vo.setIntervalo(entity.getIntervalo());
		
		return vo;
	}
	
}
