package br.gov.caixa.ccr.util;


public final class PatternDeDataUtil {

	/**
	 * Construtor.
	 */
	private PatternDeDataUtil() {
		// Construtor.
	}

	/**
	 * Retorna o pattern dd/MM/yyyy.
	 * 
	 * @return pattern dd/MM/yyyy
	 */
	public static String getddMMyyyy() {
		return "dd/MM/yyyy";
	}
	
	/**
	 * Retorna o pattern dd/MM.
	 * 
	 * @return pattern dd/MM
	 */
	public static String getddMM() {
		return "dd/MM";
	}

	/**
	 * Retorna o pattern dd/MM/yyyy hh:mm.
	 * 
	 * @return pattern dd/MM/yyyy hh:mm
	 */
	public static String getddMMyyyyEspacohhmm() {
		return "dd/MM/yyyy hh:mm";
	}

	/**
	 * Retorna o pattern dd/MM/yyyy hh:mm:ss.
	 * 
	 * @return pattern dd/MM/yyyy hh:mm:ss
	 */
	public static String getddMMyyyyEspacohhmmss() {
		return "dd/MM/yyyy hh:mm:ss";
	}

	/**
	 * Retorna o pattern dd/MM/yyyy HH:mm.
	 * 
	 * @return pattern dd/MM/yyyy HH:mm
	 */
	public static String getddMMyyyyEspacoHHmm() {
		return "dd/MM/yyyy HH:mm";
	}

	/**
	 * Retorna o pattern dd/MM/yyyy HH:mm:ss.
	 * 
	 * @return pattern dd/MM/yyyy HH:mm:ss
	 */
	public static String getddMMyyyyEspacoHHmmss() {
		return "dd/MM/yyyy HH:mm:ss";
	}

	/**
	 * Retorna o pattern dd-MM-yyyy.
	 * 
	 * @return pattern dd-MM-yyyy
	 */
	public static String getddHifenMMHifenyyyy() {
		return "dd-MM-yyyy";
	}

	/**
	 * Retorna o pattern MMM/yyyy.
	 * 
	 * @return pattern MMM/yyyy
	 */
	public static String getMMMyyyy() {
		return "MMM/yyyy";
	}

	/**
	 * Retorna o pattern MM/yyyy.
	 * 
	 * @return pattern MM/yyyy
	 */
	public static String getMMyyyy() {
		return "MM/yyyy";
	}

	/**
	 * Retorna o pattern hh:mm.
	 * 
	 * @return pattern hh:mm.
	 */
	public static String gethhmm() {
		return "hh:mm";
	}

	/**
	 * Retorna o pattern hh:mm:ss.
	 * 
	 * @return pattern hh:mm:ss.
	 */
	public static String gethhmmss() {
		return "hh:mm:ss";
	}

	/**
	 * Retorna o pattern HH:mm.
	 * 
	 * @return pattern HH:mm.
	 */
	public static String getHHmm() {
		return "HH:mm";
	}

	/**
	 * Retorna o pattern HH:mm:ss.
	 * 
	 * @return pattern HH:mm:ss.
	 */
	public static String getHHmmss() {
		return "HH:mm:ss";
	}

	/**
	 * Retorna o pattern de data por extenso.
	 * 
	 * @return pattern de data por extenso.
	 */
	public static String getEEEEvirguladddeMMMMdeyyyy() {
		return "EEEE, dd 'de' MMMM 'de' yyyy";
	}
	
	/**
	 * Retorna o pattern de data por extenso.
	 * 
	 * @return pattern de data por extenso.
	 */
	public static String getvDiairguladddeMMMMdeyyyy() {
		return "d 'de' MMMM 'de' yyyy";
	}

	/**
	 * Retorna o pattern do formato IBM (yyyyMMdd).
	 * 
	 * @return pattern do formato IBM
	 */
	public static String getPadraoIBM() {
		return "yyyyMMdd";
	}

	/**
	 * Retorna o pattern do padr�o DB2 (yyyy-MM-dd-HH.mm.ss.SSSSSS).
	 * 
	 * @return pattern do padr�o DB2.
	 */
	public static String getPadraoDB2() {
		return "yyyy-MM-dd-HH.mm.ss.SSSSSS";
	}

	/**
	 * Retorna o pattern do padr�o DB2 (yyyy-MM-dd), somente data.
	 * 
	 * @return pattern do padr�o DB2 somente data.
	 */
	public static String getyyyyMMddSeparadoPorTraco() {
		return "yyyy-MM-dd";
	}

	/**
	 * Retorna o pattern do padr�o DB2 (dd.MM.yyyy), somente data.
	 * 
	 * @return pattern do padr�o DB2 somente data.
	 */
	public static String getddMMyyyySeparadoPorPonto() {
		return "dd.MM.yyyy";
	}

	/**
	 * Retorna o pattern do dia da semana.
	 * 
	 * @return pattern do dia da semana.
	 */
	public static String getEEEE() {
		return "EEEE";
	}
}
