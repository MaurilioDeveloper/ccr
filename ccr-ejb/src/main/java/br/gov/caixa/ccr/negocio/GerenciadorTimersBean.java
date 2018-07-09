//package br.gov.caixa.ccr.negocio;
//
//import java.text.DateFormatSymbols;
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Locale;
//import java.util.Map;
//import java.util.logging.Logger;
//
//import javax.annotation.PostConstruct;
//import javax.annotation.Resource;
//import javax.ejb.LocalBean;
//import javax.ejb.ScheduleExpression;
//import javax.ejb.Singleton;
//import javax.ejb.Startup;
//import javax.ejb.Timeout;
//import javax.ejb.Timer;
//import javax.ejb.TimerConfig;
//import javax.ejb.TimerService;
//import javax.inject.Inject;
//
//import br.gov.caixa.ccr.dominio.transicao.ProcessoEnvioContratoTO;
//import br.gov.caixa.ccr.enums.EnumProcessoEnvioContrato;
//import br.gov.caixa.ccr.negocio.bo.cobol.IRegistraContratoConsignadoAPX;
//
//
//@Singleton
//@Startup
//@LocalBean
//public class GerenciadorTimersBean implements GerenciadorTimers {
//
//	
//	private Logger LOG = Logger.getLogger(getClass().getName());
//	
//	private static final SimpleDateFormat SDF_HORA = new SimpleDateFormat( "HH:mm", new DateFormatSymbols( new Locale( "pt", "BR" ) ) );	
//	private static final Map<String, String[]> MAPA_INTERVALOS = new HashMap<String, String[]>( 10 );
//	
//	private Timer timerSIAPX;
//	
//	@Resource
//	private TimerService timerService;
//	
//	@Inject
//	private IProcessoEnvioContratoBean processoEnvioContratoBean;
//	
//	@Inject
//	private IRegistraContratoConsignadoAPX registraContratoConsignadoAPX;
//	
//	@Override
//	@PostConstruct
//	public void inicializaTimers() {
//		LOG.fine("GerenciadorTimersBean - INICIO Criação agendamento");
//		
//		List<ProcessoEnvioContratoTO> listaParametrosProcesso = processoEnvioContratoBean.listar();
//		if(listaParametrosProcesso != null ){
//			for (ProcessoEnvioContratoTO parametroProcessoTO : listaParametrosProcesso) {
//				Date horaInicio = parametroProcessoTO.getHoraInicio();
//				Date horaFim = parametroProcessoTO.getHoraFim();
//				Integer intervalo = parametroProcessoTO.getIntervalo();
//				
//				this.timerSIAPX = this.agendarTimer( this.timerSIAPX, horaInicio, horaFim, intervalo, EnumProcessoEnvioContrato.SIAPX.name() );
//			}
//		}
//		
//		LOG.fine("GerenciadorTimersBean - FIM Criação agendamento");
//	}
//
//	
//	@Override
//	public void agendarTimerSIAPX(final Date horaInicio, final Date horaFim, final Integer intervalo) {
//		this.timerSIAPX = this.agendarTimer( this.timerSIAPX, horaInicio, horaFim, intervalo, EnumProcessoEnvioContrato.SIAPX.name() );
//	}
//	
//	@Timeout
//	public void timeout(final Timer timer) {
//		
//		if( timer.getInfo().equals( EnumProcessoEnvioContrato.SIAPX.name() ) ){
//            if( this.isHorarioPermitido( new Date(), EnumProcessoEnvioContrato.SIAPX.name() ) ){
//                try{
//                	
//                	registraContratoConsignadoAPX.enviarContrato();
//                }catch( Throwable t ){
//                    t.printStackTrace();
//                }
//            }
//        }
//	}
//	
//	
//	private boolean isHorarioPermitido(final Date horaReferencia, final String ident) {
//	    final String horario = GerenciadorTimersBean.SDF_HORA.format( horaReferencia );
//        synchronized( GerenciadorTimersBean.MAPA_INTERVALOS ){
//            synchronized( GerenciadorTimersBean.MAPA_INTERVALOS.get( ident ) ){
//                final String fim = GerenciadorTimersBean.MAPA_INTERVALOS.get( ident )[ 1 ];
//                final String inicio = GerenciadorTimersBean.MAPA_INTERVALOS.get( ident )[ 0 ];
//                return ( horario.compareTo( inicio ) >= 0 ) &&  ( horario.compareTo( fim ) <= 0 );
//            }
//        }
//    }
//	
//	private Timer agendarTimer(final Timer timer, final Date horaInicio, final Date horaFim, final Integer intervalo, final String ident) {
//		if( timer != null ){
//			timer.cancel();
//		}
//		synchronized( GerenciadorTimersBean.MAPA_INTERVALOS ){
//		    if( !GerenciadorTimersBean.MAPA_INTERVALOS.containsKey( ident ) ){
//		        GerenciadorTimersBean.MAPA_INTERVALOS.put( ident, new String[]{ "00:00", "00:00" } );
//		    }
//		    final String[] periodo = GerenciadorTimersBean.MAPA_INTERVALOS.get( ident );
//		    periodo[ 0 ] = GerenciadorTimersBean.SDF_HORA.format( horaInicio );
//		    periodo[ 1 ] = GerenciadorTimersBean.SDF_HORA.format( horaFim );
//        }
//		return this.timerService.createCalendarTimer( this.schedule( horaInicio, horaFim ,intervalo), new TimerConfig( ident, false ) );
//	}
//	
//	
//	private ScheduleExpression schedule(final Date horaInicio, final Date horaFim, final Integer intervalo) {
//	    final Calendar hrInicio = Calendar.getInstance( new Locale( "pt", "BR" ) );
//        hrInicio.setLenient( true );
//        hrInicio.setTime( horaInicio );
//        final Calendar hrFim = Calendar.getInstance( new Locale( "pt", "BR" ) );
//        hrFim.setLenient( true );
//        hrFim.setTime( horaFim );
//        
//        final StringBuilder bHoras = new StringBuilder();
//        final StringBuilder bMinutos = new StringBuilder();
//        final int hInicio = hrInicio.get( Calendar.HOUR_OF_DAY );
//        final int hFim = hrFim.get( Calendar.HOUR_OF_DAY );
//        if( ( hFim - hInicio ) > 0 ){
//            for( int indexH = hInicio; indexH <= hFim; indexH++ ){
//                if( hInicio != indexH ){
//                    bHoras.append( "," );
//                    bMinutos.append( "," );
//                }
//                bHoras.append( indexH );
//                if( hInicio == indexH ){
//                    bMinutos.append( hrInicio.get( Calendar.MINUTE ) + "-59" );
//                }else if( hFim == indexH ){
//                    bMinutos.append( "0-" + hrFim.get( Calendar.MINUTE ) );
//                }else{
//                    bMinutos.append( "0-59" );
//                }
//            }
//        }else{
//            bHoras.append( hInicio );
//            bMinutos.append( hrInicio.get( Calendar.MINUTE ) + "-" + hrFim.get( Calendar.MINUTE ) );
//        }
//	    String strIntervalo = "*/"+String.valueOf(intervalo);
//		final ScheduleExpression expr = new ScheduleExpression();
//		expr.dayOfWeek( "Mon,Tue,Wed,Thu,Fri" ).hour( bHoras.toString() ).minute( strIntervalo /*bMinutos.toString()*/ );
//		//http://docs.oracle.com/javaee/6/api/javax/ejb/ScheduleExpression.html
//		return expr;
//	}
//
//}
