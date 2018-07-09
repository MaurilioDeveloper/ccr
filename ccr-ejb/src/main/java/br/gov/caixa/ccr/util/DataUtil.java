package br.gov.caixa.ccr.util;

import java.sql.Time;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataUtil {

	public static final int MILISSEGUNDOS_DIA = 86400000;
	public static final int SEGUNDOS_DIA = 86400;

	public static final String PADRAO_DATA = "dd/MM/yyyy";
	public static final String PADRAO_DATA_BARRAMENTO = "ddMMyyyy";
	public static final String PADRAO_DATA_ISO = "yyyy-MM-dd";
	public static final String PADRAO_DATA_HORA_COMPLETA = "dd/MM/yyyy HH:mm:ss";
	public static final String PADRAO_DATA_HORA_COMPLETA_ISO = "yyyy-MM-dd HH:mm:ss";
	public static final String PADRAO_HORA_MINUTO = "HH:mm";
	public static final String PADRAO_HORA_COMPLETA = "HH:mm:ss";
	public static final String PADRAO_ANO = "yyyy";
	public static final String PADRAO_MES = "MM";
	public static final String PADRAO_MES_COMPLETO = "MMMM";
	public static final String PADRAO_DIA = "dd";
	public static final String PADRAO_DIGITOS_HORA = "HH";
	public static final String PADRAO_DIGITOS_MINUTO = "mm";
	public static final String PADRAO_DIGITOS_SEGUNDO = "ss";
	public static final String[] MESES = {"Janeiro", "Fevereiro", "Mar�o", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro",
			"Dezembro"};
	public static final String YYY = "yyyy";
	public static final String DD = "dd";
	public static final String MM = "MM";

	/**
	 * Construtor privado evita inst�ncias indevidas
	 */
	private DataUtil() {
	}

	/**
	 * Retorna uma data como texto de acordo com o padr�o.
	 * 
	 * @param data
	 * @param padrao
	 * @return
	 */
	public static String formatar(Date data, String padrao) {
		if (data == null)
			return null;
		
		DateFormat formatador = new SimpleDateFormat(padrao);
		String dataFormatada = formatador.format(data);
		return dataFormatada;
	}
	
	/**
	 * Retorna uma data como texto no padr�o dd/MM/yyyy HH:mm:ss.
	 * 
	 * @param data
	 * @return
	 */
	public static String formatarDataHora(Date data) {
		if (data == null)
			return null;
		
		String dataFormatada = formatar(data, PADRAO_DATA_HORA_COMPLETA);
		return dataFormatada;
	}

	/**
	 * Retorna uma data como texto no padr�o dd/MM/yyyy.
	 * 
	 * @param data
	 * @return
	 */
	public static String formatarData(Date data) {
		if (data == null)
			return null;
		
		String dataFormatada = formatar(data, PADRAO_DATA);
		return dataFormatada;
	}

	/**
	 * Retorna uma hora como texto no padr�o HH:mm:ss.
	 * 
	 * @param data
	 * @return
	 */
	public static String formatarHora(Date data) {
		String dataFormatada = formatar(data, PADRAO_HORA_COMPLETA);
		return dataFormatada;
	}

	/**
	 * Retorna uma hora como texto no padr�o HH:mm.
	 * 
	 * @param data
	 * @return
	 */
	public static String formatarHoraMinuto(Date data) {
		String dataFormatada = formatar(data, PADRAO_HORA_MINUTO);
		return dataFormatada;
	}

	/**
	 * Retorna uma data convertendo o texto de acordo com o padr�o informado.
	 * 
	 * @param texto
	 * @param padrao
	 * @return
	 * @throws ParseException
	 */
	public static Date converter(String texto, String padrao) throws ParseException {
		if (texto == null)
			return null;
		
		DateFormat formatador = new SimpleDateFormat(padrao);
		Date data = formatador.parse(texto);
		return data;
	}

	/**
	 * Converte o texto no formato 'dd/MM/yyyy HH:mm:ss' para um objeto Date com
	 * informa��es de data e hora.
	 * 
	 * @param texto
	 * @return
	 * @throws ParseException
	 */
	public static Date converterDataHora(String texto) throws ParseException {
		Date data = converter(texto, PADRAO_DATA_HORA_COMPLETA);
		return data;
	}

	/**
	 * Converte o texto no formato 'dd/MM/yyyy' para um objeto Date com
	 * informa��es apenas de data.
	 * 
	 * @param texto
	 * @return
	 * @throws ParseException
	 */
	public static Date converterData(String texto) throws ParseException {
		Date data = converter(texto, PADRAO_DATA);
		return data;
	}

	/**
	 * Converte o texto no formato 'HH:mm:ss' para um objeto Date com
	 * informa��es apenas de hora.
	 * 
	 * @param texto
	 * @return
	 * @throws ParseException
	 */
	public static Date converterHora(String texto) throws ParseException {
		Date data = converter(texto, PADRAO_HORA_COMPLETA);
		return data;
	}
	
	/**
	 * Converte a data  para time no formato 'HH:mm:ss'
	 * apenas de hora.
	 * 
	 * @param data
	 * @return
	 * @throws ParseException
	 */
	public static Time converterTime(Date data) throws ParseException {
		String texto = formataTime(data); 
		
		return Time.valueOf(texto);
	}

	public static String formataTime(Date data) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(data);
		return cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE) + ":" + cal.get(Calendar.SECOND);
	}
	
	/**
	 * Calcula a diferen�a em dias entre duas datas.
	 * 
	 * @param dataInicial
	 * @param dataFinal
	 * @return diferen�a em dias
	 */
	public static int calcularDiferencaDias(Date dataInicial, Date dataFinal) {
		int diferenca = (int) ((dataFinal.getTime() - dataInicial.getTime()) / MILISSEGUNDOS_DIA);
		return diferenca;
	}

	/**
	 * Calcula a diferen�a em dias entre duas datas no formato dd/MM/yyyy.
	 * 
	 * @param dataInicial
	 * @param dataFinal
	 * @return dia
	 */
	public static int calcularDiferencaDias(String dataInicial, String dataFinal) {
		Date data1 = null;
		Date data2 = null;
		try {
			data1 = converterData(dataInicial);
			data2 = converterData(dataFinal);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return calcularDiferencaDias(data1, data2);
	}

	/**
	 * Calcula diferença em meses entre duas datas
	 * @param dataInicial
	 * @param dataFinal
	 * @return
	 */
	public static int calcularDiferencaMeses(Date dataInicial, Date dataFinal) {
		int diferencaEmMeses = 0;
		
		Calendar cinicial = Calendar.getInstance(), cfinal = Calendar.getInstance();
		cinicial.setTime(dataInicial);
		cfinal.setTime(dataFinal);
		
		diferencaEmMeses = (cfinal.get(Calendar.YEAR) - cinicial.get(Calendar.YEAR)) * 12;
		diferencaEmMeses += (cfinal.get(Calendar.MONTH) - cinicial.get(Calendar.MONTH)) + 1;	
		
		return diferencaEmMeses;
	}
	
	/**
	 * Calcular idade pela data de nascimento
	 * @param dataNascimento
	 * @return idade
	 */
	public static int calcularIdade(Date dataNascimento) {
		Calendar cal = Calendar.getInstance();
		return calcularDiferencaMeses(dataNascimento, cal.getTime()) / 12;
	}
	
	public static int calcularIdade(String dataNascimento) throws Exception {
		Calendar cal = Calendar.getInstance();
		Date data = converterData(dataNascimento);
		return calcularDiferencaMeses(data, cal.getTime()) / 12;
	}
	
	/**
	 * Retira a informa��o de hora de uma data.
	 * 
	 * @param data
	 * @return
	 * @throws ParseException
	 * @deprecated Use {@link DataUtil#zerarHora()} em vez disso.
	 */
	@Deprecated
	public static Date retirarHora(Date data) throws ParseException {
		Date dataTemporaria = new Date();

		dataTemporaria = converterData(formatarData(data));
		return dataTemporaria;
	}

	/**
	 * Zera a hora, o minuto e o segundo de uma data passando a ficar 00:00:00.
	 * 
	 * @param data
	 * @return
	 */
	public static Date zerarHora(Date data) {
		Calendar c = Calendar.getInstance();
        c.setTime(data);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
		
		return c.getTime();
	}

	/**
	 * Verifica se uma data est� no formato v�lido.
	 * 
	 * @param data
	 * @return
	 */
	public static boolean isDataValida(String data) {
		try {
			DateFormat formatador = new SimpleDateFormat(PADRAO_DATA);
			formatador.setLenient(false);
			formatador.parse(data);
			return true;
		} catch (ParseException e) {
			return false;
		}
	}

	/**
	 * Retorna primeiro dia do m�s como Date
	 * 
	 * @param ano
	 * @return
	 */
	public static Date getPrimeiroDiaAno(int ano) {
		Calendar calendario = getPrimeiroDiaAnoCalendar(ano);
		Date data = getDate(calendario);

		return data;
	}

	/**
	 * Retorna primeiro dia do m�s como Calendar
	 * 
	 * @param ano
	 * @return
	 */
	public static Calendar getPrimeiroDiaAnoCalendar(int ano) {
		Calendar calendario = Calendar.getInstance();
		calendario.set(ano, Calendar.JANUARY, 1, 0, 0, 0);
		calendario.setTimeInMillis(calendario.getTimeInMillis());

		return calendario;
	}

	/**
	 * Retorna �ltimo dia do m�s como Date
	 * 
	 * @param ano
	 * @return
	 */
	public static Date getUltimoDiaAno(int ano) {
		Calendar calendario = getUltimoDiaAnoCalendar(ano);
		Date data = getDate(calendario);

		return data;
	}

	/**
	 * Retorna �ltimo dia do m�s como Calendar
	 * 
	 * @param ano
	 * @return
	 */
	public static Calendar getUltimoDiaAnoCalendar(int ano) {
		Calendar calendario = Calendar.getInstance();
		calendario.set(ano, Calendar.DECEMBER, 31, 0, 0, 0);
		calendario.setTimeInMillis(calendario.getTimeInMillis());

		return calendario;
	}

	/**
	 * Retorna primeiro dia do m�s como Date
	 * 
	 * @param mes
	 * @param ano
	 * @return
	 */
	public static Date getPrimeiroDiaMes(int mes, int ano) {
		Calendar calendario = getPrimeiroDiaMesCalendar(mes, ano);
		Date data = getDate(calendario);

		return data;
	}

	/**
	 * Retorna primeiro dia do m�s como Calendar
	 * 
	 * @param mes
	 * @param ano
	 * @return
	 */
	public static Calendar getPrimeiroDiaMesCalendar(int mes, int ano) {
		Calendar calendario = Calendar.getInstance();
		calendario.set(ano, mes, 1, 0, 0, 0);
		calendario.setTimeInMillis(calendario.getTimeInMillis());

		return calendario;
	}

	/**
	 * Retorna �ltimo dia do m�s como Date
	 * 
	 * @param mes
	 * @param ano
	 * @return
	 */
	public static Date getUltimoDiaMes(int mes, int ano) {
		Calendar calendario = getUltimoDiaMesCalendar(mes, ano);
		Date data = getDate(calendario);

		return data;
	}

	/**
	 * Retorna �ltimo dia do m�s como Calendar
	 * 
	 * @param mes
	 * @param ano
	 * @return
	 */
	public static Calendar getUltimoDiaMesCalendar(int mes, int ano) {
		Calendar calendario = Calendar.getInstance();
		calendario.set(ano, mes, 1, 0, 0, 0);
		calendario.set(ano, mes, calendario.getActualMaximum(Calendar.DATE));
		calendario.setTimeInMillis(calendario.getTimeInMillis());

		return calendario;
	}

	/**
	 * Converte de Date para Calendar
	 * 
	 * @param data
	 * @return
	 */
	public static Calendar getCalendar(Date data) {
		Calendar calendario = Calendar.getInstance();
		calendario.setTime(data);

		return calendario;
	}

	/**
	 * Converte de Calendar para Date
	 * 
	 * @param calendario
	 * @return
	 */
	public static Date getDate(Calendar calendario) {
		Date data = new Date(calendario.getTimeInMillis());

		return data;
	}

	/**
	 * Cria objeto Date
	 * 
	 * @param dia
	 * @param mes
	 * @param ano
	 * @return
	 */
	public static Date getDate(int dia, int mes, int ano) {
		return getDate(dia, mes, ano, 0, 0, 0);
	}

	/**
	 * Cria objeto Date
	 * 
	 * @param dia
	 * @param mes
	 * @param ano
	 * @param horas
	 * @param minutos
	 * @param segundos
	 * @return
	 */
	public static Date getDate(int dia, int mes, int ano, int horas, int minutos, int segundos) {
		Calendar calendario = getCalendar(dia, mes, ano, horas, minutos, segundos);
		Date data = getDate(calendario);

		return data;
	}

	/**
	 * Cria objeto Calendar
	 * 
	 * @param dia
	 * @param mes
	 * @param ano
	 * @param hora
	 * @param minuto
	 * @param segundo
	 * @return
	 */
	public static Calendar getCalendar(int dia, int mes, int ano, int horas, int minutos, int segundos) {
		Calendar calendario = Calendar.getInstance();
		calendario.set(ano, mes, dia, horas, minutos, segundos);
		calendario.setTimeInMillis(calendario.getTimeInMillis());

		return calendario;
	}

	/**
	 * Cria objeto Calendar
	 * 
	 * @param dia
	 * @param mes
	 * @param ano
	 * @return
	 */
	public static Calendar getCalendar(int dia, int mes, int ano) {
		return getCalendar(dia, mes, ano, 0, 0, 0);
	}

	public static Date adicionarDias(final Date data, final int quantidade) {
		return atualizarCalendario(data, Calendar.DATE, quantidade);
	}

	public static Date adicionarMeses(final Date data, final int quantidade) {
		return atualizarCalendario(data, Calendar.MONTH, quantidade);
	}

	public static Date adicionarAnos(final Date data, final int quantidade) {
		return atualizarCalendario(data, Calendar.YEAR, quantidade);
	}

	public static Date adicionarHoras(final Date data, final int quantidade) {
		return atualizarCalendario(data, Calendar.HOUR_OF_DAY, quantidade);
	}

	public static Date adicionarMinutos(final Date data, final int quantidade) {
		return atualizarCalendario(data, Calendar.MINUTE, quantidade);
	}

	public static Date adicionarSegundos(final Date data, final int quantidade) {
		return atualizarCalendario(data, Calendar.SECOND, quantidade);
	}

	private static Date atualizarCalendario(final Date data, final int campo, final int quantidade) {
		validarDataVazia(data);
		final Calendar calendar = Calendar.getInstance(Locale.getDefault());
		calendar.setTime(data);
		calendar.add(campo, quantidade);
		return calendar.getTime();
	}

	public static int getAno(final Date data) {
		validarDataVazia(data);
		final String anoAtual = new SimpleDateFormat(DataUtil.YYY).format(data);
		return Integer.valueOf(anoAtual).intValue();
	}

	public static int getDia(final Date data) {
		validarDataVazia(data);
		final String valorFormatado = new SimpleDateFormat(DataUtil.DD).format(data);
		return Integer.valueOf(valorFormatado).intValue();
	}

	public static int getMes(final Date data) {
		validarDataVazia(data);
		final String valorFormatado = new SimpleDateFormat(DataUtil.MM).format(data);
		return Integer.valueOf(valorFormatado).intValue();
	}

	public static String getHoraMinutos(final Date data) {
		final SimpleDateFormat format = new SimpleDateFormat(PatternDeDataUtil.gethhmm());
		return format.format(data);
	}

	public static Date criarData(final int dia, final int mes, final int ano) {
		if (dia == 0 || mes == 0 && ano == 0) {
			throw new IllegalArgumentException("Data não pode ser nula.");
		}

		return new GregorianCalendar(ano, mes, dia).getTime();
	}

	public static int calcularDiferencaEmDiasEntreDatas(final Date dataInicial, final Date dataFinal) {
		final GregorianCalendar dataComparacaoInicial = new GregorianCalendar();
		dataComparacaoInicial.setTime(criarData(getDia(dataInicial), getMes(dataInicial) - 1, getAno(dataInicial)));
		dataComparacaoInicial.set(Calendar.HOUR_OF_DAY, 0);
		dataComparacaoInicial.set(Calendar.MINUTE, 0);
		dataComparacaoInicial.set(Calendar.SECOND, 0);
		dataComparacaoInicial.set(Calendar.MILLISECOND, 0);

		final GregorianCalendar dataComparacaoFinal = new GregorianCalendar();
		dataComparacaoFinal.setTime(criarData(getDia(dataFinal), getMes(dataFinal) - 1, getAno(dataFinal)));
		dataComparacaoFinal.set(Calendar.HOUR_OF_DAY, 0);
		dataComparacaoFinal.set(Calendar.MINUTE, 0);
		dataComparacaoFinal.set(Calendar.SECOND, 0);
		dataComparacaoFinal.set(Calendar.MILLISECOND, 0);

		final long periodoInicial = dataComparacaoInicial.getTimeInMillis();
		final long periodoFinal = dataComparacaoFinal.getTimeInMillis();

		return Long.valueOf((periodoFinal - periodoInicial) / (24 * 60 * 60 * 1000)).intValue();
	}

	public static Date adicionarMesEmData(final Date data, final int qtdMes) {
		validarDataVazia(data);
		final Calendar calendario = Calendar.getInstance();
		calendario.setTime(data);
		calendario.add(Calendar.MONTH, qtdMes);
		return calendario.getTime();
	}

	public static Date adicionarDiaEmData(final Date data, final int qtdDias) {
		final Calendar calendario = Calendar.getInstance();
		calendario.setTime(data);
		// calendario.add(Calendar.DATE, qtdDias);
		calendario.add(Calendar.DAY_OF_MONTH, qtdDias);
		return calendario.getTime();
	}

	public static boolean isDataNoIntervalo(final Date inicial, final Date fim, final Date data) {
		if (inicial == null || fim == null || data == null) {
			throw new IllegalArgumentException("Não é possível realizar a verificação do intervalo.");
		}
		return data.compareTo(inicial) >= 0 && data.compareTo(fim) <= 0;
	}

	/**
	 * Verifica se a segunda data passada por parametro é menor que a primeira.
	 * 
	 * @param primeira
	 * @param segunda
	 * @return boolean
	 * @author rodolfo.bueno
	 */
	public static boolean isSegundaDataMenor(final Date primeira, final Date segunda) {
		if (primeira == null || segunda == null) {
			throw new IllegalArgumentException("Não é possível realizar a verificação.");
		}
		return segunda.before(primeira);
	}

	/**
	 * Verifica se a segunda data passada por parametro é menor ou igual a
	 * primeira.
	 * 
	 * @param primeira
	 * @param segunda
	 * @return boolean
	 * @author rodolfo.bueno
	 */
	public static boolean isSegundaDataMenorOuIgual(final Date primeira, final Date segunda) {
		if (primeira == null || segunda == null) {
			throw new IllegalArgumentException("Não é possível realizar a verificação.");
		}
		return segunda.compareTo(primeira) <= 0;
	}

	public static Calendar getCalendarSomenteDiaMesAno(final Date data) {
		final Calendar cal = Calendar.getInstance();
		cal.setTime(data);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal;
	}

	public static Date getDateSomenteDiaMesAno(final Date data) {
		final Calendar cal = getCalendarSomenteDiaMesAno(data);
		return cal.getTime();
	}

	public static int getDiasDoAno(final Date data) {
		int quantideDeDias = 0;

		if (data != null) {
			final Calendar calendar = getNovoCalendar(data);
			quantideDeDias = calendar.getActualMaximum(Calendar.DAY_OF_YEAR);
		}
		return quantideDeDias;
	}

	public static boolean validarData(final String data) {
		final Pattern p = Pattern.compile("^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)"
				+ "(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:("
				+ "?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00"
				+ "))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9" + "]\\d)?\\d{2})$");

		final Matcher m = p.matcher(data);

		return m.find();
	}

	public static Calendar getNovoCalendar() {
		return Calendar.getInstance(getLocaleBrasil());
	}

	public static Calendar getNovoCalendar(final Date data) {
		final Calendar calendar = getNovoCalendar();

		if (data != null) {
			calendar.setTime(data);
		}

		return calendar;
	}

	public static String getMesExtenso(final Date data) {
		final Calendar calendar = Calendar.getInstance();
		if (data == null) {
			throw new IllegalArgumentException("Data não pode ser nula.");
		}
		calendar.setTime(data);
		return calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, getLocaleBrasil());
	}

	public static SimpleDateFormat getNovoSimpleDateFormat(final String pattern, final Locale locale) {
		return new SimpleDateFormat(pattern, locale != null ? locale : getLocaleBrasil());
	}

	public static Locale getLocaleBrasil() {
		return new Locale("pt", "BR");
	}

	/**
	 * Valida se a data informada é nulla e lança uma exceção caso seja.
	 * 
	 * @param data
	 * @author rodolfo.bueno
	 */
	private static void validarDataVazia(final Date data) {
		if (data == null) {
			throw new IllegalArgumentException("Data não pode ser nula.");
		}
	}

	/**
	 * Cria a data atual com a hora zerada.
	 * 
	 * @return Date
	 * @author rodolfo.bueno
	 */
	public static Date criarDataAtualComHoraZerada() {
		final Calendar cal = criarCalendarioDataAtualComHoraZerada();
		return cal.getTime();
	}

	public static Calendar criarCalendarioDataAtualComHoraZerada() {
		final Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal;
	}

	public static int retornaQtdDiasSemFinalSemana(final Date dataInicio, final Date dataFinal) {
		final Calendar calDataInicial = Calendar.getInstance();
		final Calendar calDataFinal = Calendar.getInstance();
		calDataInicial.setTime(dataInicio);
		calDataFinal.setTime(dataFinal);
		int count = 0;

		while (calDataInicial.before(calDataFinal)) {
			if (isFinalDeSemana(calDataInicial)) {
				calDataInicial.add(Calendar.DAY_OF_MONTH, 1);
				continue;
			}
			calDataInicial.add(Calendar.DAY_OF_MONTH, 1);
			count++;
		}

		return count;
	}

	public static Date retornarDataAcrescidaDeDiasDaSemana(final Date data, int qtdeDias) {
		final Calendar cal = Calendar.getInstance();
		cal.setTime(data);
		while (qtdeDias > 0) {
			if (isFinalDeSemana(cal)) {
				cal.add(Calendar.DAY_OF_MONTH, 1);
				if (!isFinalDeSemana(cal))
					qtdeDias--;
				continue;
			}
			cal.add(Calendar.DAY_OF_MONTH, 1);
			qtdeDias--;
		};

		return cal.getTime();
	}

	protected static boolean isFinalDeSemana(final Calendar cal) {
		return cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
	}

	public static Date retornarDataAcrescidaDeDiasUteis(final Date data, int qtdeDias, final List<Date> feriados) {
		final Calendar cal = Calendar.getInstance();
		cal.setTime(data);
		Collections.sort(feriados);

		while (qtdeDias > 1) {

			if (isFinalDeSemana(cal)) {
				cal.add(Calendar.DAY_OF_MONTH, 1);
				if (!isFinalDeSemana(cal)) {
					qtdeDias--;
				}
				continue;
			}
			// verifica se o dia em questão é feriado
			if (feriados != null && feriados.size() > 0) {
				if (isFeriado(feriados, cal)) {
					cal.add(Calendar.DAY_OF_MONTH, 1);
					if (!isFeriado(feriados, cal) && !isFinalDeSemana(cal)) {
						qtdeDias--;
					}
					continue;
				}
			}

			cal.add(Calendar.DAY_OF_MONTH, 1);
			if (!isFeriado(feriados, cal) && !isFinalDeSemana(cal)) {
				qtdeDias--;
			}
		}
		return cal.getTime();
	}

	protected static boolean isFeriado(final List<Date> feriados, final Calendar cal) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM");
		for (Date feriado : feriados) {
			if (sdf.format(cal.getTime()).equals(sdf.format(feriado))) {
				return true;
			}
		}
		return false;
	}

	public static boolean isDiaUtil(final Date data) {
		final Calendar calDataRetorno = Calendar.getInstance();
		calDataRetorno.setTime(data);

		boolean retorno = Boolean.TRUE;

		if (isFinalDeSemana(calDataRetorno)) {
			retorno = Boolean.FALSE;
		}

		return retorno;
	}

	public static Date getProximoDiaUtil(final Date data) {
		final Calendar calDataRetorno = Calendar.getInstance();
		calDataRetorno.setTime(data);

		int qtdeDias = 3;

		while (qtdeDias > 0) {
			if (isFinalDeSemana(calDataRetorno)) {
				calDataRetorno.add(Calendar.DAY_OF_MONTH, 1);
				qtdeDias--;
				continue;
			}

			qtdeDias = -1;
		}
		return calDataRetorno.getTime();
	}

	public static boolean isMesAnoValido(final String mesAno) {

		if (mesAno != null && mesAno.length() != 7) {
			return false;
		}

		try {
			final SimpleDateFormat sdf = new SimpleDateFormat(PatternDeDataUtil.getMMyyyy());
			sdf.setLenient(false);
			sdf.parse(mesAno);
			return true;
		} catch (final ParseException e) {
			return false;
		}
	}

	public static boolean isSegundaDataMesAnoMaior(final String primeiroMesAno, final String segundoMesAno) {
		try {
			final Date data1 = new SimpleDateFormat(PatternDeDataUtil.getMMyyyy()).parse(primeiroMesAno);
			final Date data2 = new SimpleDateFormat(PatternDeDataUtil.getMMyyyy()).parse(segundoMesAno);
			return isSegundaDataMenor(data2, data1);

		} catch (final ParseException e) {
			return false;
		}
	}

	public static String getAnoMesAtual() {
		final String sdf = new SimpleDateFormat(PatternDeDataUtil.getMMyyyy()).format(new Date());
		return sdf;
	}

	public static String getDataFormatada(Date data) {
		return data != null ? new SimpleDateFormat(DataUtil.PADRAO_DATA).format(data) : "";
	}

	public static String getDataPorExtenso(final Date data) {
		final SimpleDateFormat sdf = getNovoSimpleDateFormat(PatternDeDataUtil.getvDiairguladddeMMMMdeyyyy(), getLocaleBrasil());
		return sdf.format(data);
	}

	/**
	 * Calcula a diferença de duas datas em dias <br>
	 * <b>Importante:</b> Quando realiza a diferença em dias entre duas datas,
	 * este método considera as horas restantes e as converte em fração de dias.
	 * 
	 * @param dataInicial
	 * @param dataFinal
	 * @return quantidade de dias existentes entre a dataInicial e dataFinal.
	 * @throws ParseException 
	 */
	public static int diferencaEmDias(Date dataInicial, Date dataFinal) throws ParseException {
		/*double result = 0;
		long diferenca = dataFinal.getTime() - dataInicial.getTime();
		double diferencaEmDias = (diferenca / 1000) / 60 / 60 / 24;
		long horasRestantes = (diferenca / 1000) / 60 / 60 % 24;
		result = diferencaEmDias + (horasRestantes / 24d);
		return result;
		*/
		int result = 0;
		Format formatter = new SimpleDateFormat("dd/MM/yyyy");
        String dataInicio = formatter.format(dataInicial);
        String dataFim = formatter.format(dataFinal);
        
        DateFormat dfa = new SimpleDateFormat ("dd/MM/yyyy");
        Date dtInicial = dfa.parse (dataInicio);
        Date dtFinal = dfa.parse (dataFim);
        //System.out.println ( (dtFinal.getTime() - dtInicial.getTime() + 3600000L) / 86400000L);
        result = (int) ((dtFinal.getTime() - dtInicial.getTime() + 3600000L) / 86400000L);
	        
		return result;		
	}

	public static String converterParaCompleto(String mes) throws ParseException {
		// Instância o SimpleDateFormat com o formato MM (esse formato indica o
		// formato de numeros).
		SimpleDateFormat sdf = new SimpleDateFormat("MM");
		// Faz o parse ("transforma") a String que contêm o mês em um Date.
		Date mesDate = sdf.parse(mes);
		// Altera o pattern do SimpleDateFormat.
		sdf.applyPattern("MMMM");
		// Retorna o nome do Mês.
		return sdf.format(mesDate);
	}

	public static String getDescricaoMes(Integer mes) {
		String[] meses = new String[12];
		// meses = new Array(12);

		meses[0] = "Janeiro";
		meses[1] = "Fevereiro";
		meses[2] = "Março";
		meses[3] = "Abril";
		meses[4] = "Maio";
		meses[5] = "Junho";
		meses[6] = "Julho";
		meses[7] = "Agosto";
		meses[8] = "Setembro";
		meses[9] = "Outubro";
		meses[10] = "Novembro";
		meses[11] = "Dezembro";

		return meses[mes - 1];
	}

}