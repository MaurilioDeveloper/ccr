package br.gov.caixa.ccr.util;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Properties;

public class ValoresUtil {

	private static final Locale LOCALE = new Locale("pt", "BR");
	private static DecimalFormatSymbols SIMBOLO_DECIMAL_BR = new DecimalFormatSymbols(LOCALE);  
	
	public static final String MASCARA_CEP = "00000-000";
	public static final String MASCARA_CNPJ = "00.000.000/0000-00";
	public static final String MASCARA_CPF = "000.000.000-00";

	public static final DecimalFormat DINHEIRO_REAL = new DecimalFormat("¤ ###,###,##0.00", SIMBOLO_DECIMAL_BR);
	public static final DecimalFormat DECIMAL = new DecimalFormat("###,###,##0.00", SIMBOLO_DECIMAL_BR);
	public static final DecimalFormat INTEIRO = new DecimalFormat("###,###,##0", SIMBOLO_DECIMAL_BR);
	
	public static String formatarValorComMascaraInformada(final String valorParaFormatar, final String mascara) {
		String value = valorParaFormatar;
		final String patern = "[^\\d#]";
		String result = "";
		int notMaskLenght = 0;

		if (value != null && !"".equals(value)) {

			if (value.replaceAll(patern, "") != null) {
				notMaskLenght = mascara.replaceAll(patern, "").length();

				if (value.length() > notMaskLenght) {
					value = value.substring(0, notMaskLenght);
				}

				for (int i = mascara.length() - 1, j = value.length() - 1; i >= 0; i--) {
					final char cMasc = mascara.charAt(i);
					final char cNum = j >= 0 ? value.charAt(j) : '\u0000';

					switch (cMasc) {
					case '#': {
						result = cNum == '\u0000' ? result : cNum + result;
						j--;
						break;
					}
					case '0': {
						result = j >= 0 ? cNum + result : '0' + result;
						j--;
						break;
					}
					default: {
						result = cMasc + result;
						break;
					}
					}
				}
			} else if (!"".equals(mascara)) {
				result = "";
			}
		}
		return result;
	}

	public static String mascaraDinheiro(double valor) {
		return DINHEIRO_REAL.format(valor);		
	}
	
	public static String mascaraDinheiro(double valor, DecimalFormat moeda) {
		return moeda.format(valor);
	}

	public static String mascaraMilhar(double valor) {
		NumberFormat f = NumberFormat.getNumberInstance(LOCALE);
		return f.format(valor);
	}

	public static boolean isNumeroValido(Long valor) {
		return valor != null && valor > 0;
	}

	public static boolean isNumeroInvalido(Long valor) {
		return !isNumeroValido(valor);
	}

	public static boolean isStringValida(String texto) {
		return texto != null && texto.length() > 0;
	}

	public static boolean isStringInvalida(String texto) {
		return !isStringValida(texto);
	}
	
	public static String getPropValue(String propName, @SuppressWarnings("rawtypes") Class classCalling) {
		String propValue = "";
		Properties properties = new Properties();
		
		try {
			InputStream inputStream = classCalling.getClassLoader().getResourceAsStream("global.properties");
			
			if (inputStream != null)
				properties.load(inputStream);				
				
			if (properties.containsKey(propName))
				propValue = properties.getProperty(propName);
			else // throw excpetion for key not found
				new Throwable("Property key not found in global.properties.");
				
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.getMessage();
		}
		
		return propValue;
	} 
	
	public static String valorPorExtenso(double vlr) {
		return valorPorExtenso(vlr,true);
	}
	
	public static String valorPorExtenso(double vlr, Boolean real) {
	    if (vlr == 0)
	       return("zero");

	    long inteiro = (long)Math.abs(vlr); // parte inteira do valor
	    double resto = vlr - inteiro;       // parte fracionária do valor

	    String vlrS = String.valueOf(inteiro);
	    if (vlrS.length() > 15)
	       return("Erro: valor superior a 999 trilhões.");

	    String s = "", saux, vlrP;
	    String centavos = String.valueOf((int)Math.round(resto * 100));

	    String[] unidade = {"", "um", "dois", "três", "quatro", "cinco",
	             "seis", "sete", "oito", "nove", "dez", "onze",
	             "doze", "treze", "quatorze", "quinze", "dezesseis",
	             "dezessete", "dezoito", "dezenove"};
	    String[] centena = {"", "cento", "duzentos", "trezentos",
	             "quatrocentos", "quinhentos", "seiscentos",
	             "setecentos", "oitocentos", "novecentos"};
	    String[] dezena = {"", "", "vinte", "trinta", "quarenta", "cinquenta",
	             "sessenta", "setenta", "oitenta", "noventa"};
	    String[] qualificaS = {"", "mil", "milhão", "bilhão", "trilhão"};
	    String[] qualificaP = {"", "mil", "milhões", "bilhões", "trilhões"};

	// definindo o extenso da parte inteira do valor
	    int n, unid, dez, cent, tam, i = 0;
	    boolean umReal = false, tem = false;
	    while (!vlrS.equals("0")) {
	      tam = vlrS.length();
	// retira do valor a 1a. parte, 2a. parte, por exemplo, para 123456789:
	// 1a. parte = 789 (centena)
	// 2a. parte = 456 (mil)
	// 3a. parte = 123 (milhões)
	      if (tam > 3) {
	         vlrP = vlrS.substring(tam-3, tam);
	         vlrS = vlrS.substring(0, tam-3);
	      }
	      else { // última parte do valor
	        vlrP = vlrS;
	        vlrS = "0";
	      }
	      if (!vlrP.equals("000")) {
	         saux = "";
	         if (vlrP.equals("100"))
	            saux = "cem";
	         else {
	           n = Integer.parseInt(vlrP, 10);  // para n = 371, tem-se:
	           cent = n / 100;                  // cent = 3 (centena trezentos)
	           dez = (n % 100) / 10;            // dez  = 7 (dezena setenta)
	           unid = (n % 100) % 10;           // unid = 1 (unidade um)
	           if (cent != 0)
	              saux = centena[cent];
	           if ((n % 100) <= 19) {
	              if (saux.length() != 0)
	                 saux = saux + " e " + unidade[n % 100];
	              else saux = unidade[n % 100];
	           }
	           else {
	              if (saux.length() != 0)
	                 saux = saux + " e " + dezena[dez];
	              else saux = dezena[dez];
	              if (unid != 0) {
	                 if (saux.length() != 0)
	                    saux = saux + " e " + unidade[unid];
	                 else saux = unidade[unid];
	              }
	           }
	         }
	         if (vlrP.equals("1") || vlrP.equals("001")) {
	            if (i == 0) // 1a. parte do valor (um real)
	               umReal = true;
	            else saux = saux + " " + qualificaS[i];
	         }
	         else if (i != 0)
	                 saux = saux + " " + qualificaP[i];
	         if (s.length() != 0)
	            s = saux + ", " + s;
	         else s = saux;
	      }
	      if (((i == 0) || (i == 1)) && s.length() != 0)
	         tem = true; // tem centena ou mil no valor
	      i = i + 1; // próximo qualificador: 1- mil, 2- milhão, 3- bilhão, ...
	    }

	    if (real)
		    if (s.length() != 0) {
		       if (umReal)
		          s = s + " real";
		       else if (tem)
		               s = s + " reais";
		            else s = s + " de reais";
		    }

	// definindo o extenso dos centavos do valor
	    if (!centavos.equals("0")) { // valor com centavos
	       if (s.length() != 0) // se não é valor somente com centavos
	          s = s + " e ";
	       if (centavos.equals("1")){
	    	   if (real)
	    		   s = s + "um centavo";
	    	   else
	    		   s = s + "um";
	       }
	       else {
	         n = Integer.parseInt(centavos, 10);
	         if (n <= 19)
	            s = s + unidade[n];
	         else {             // para n = 37, tem-se:
	           unid = n % 10;   // unid = 37 % 10 = 7 (unidade sete)
	           dez = n / 10;    // dez  = 37 / 10 = 3 (dezena trinta)
	           s = s + dezena[dez];
	           if (unid != 0)
	              s = s + " e " + unidade[unid];
	         }
	    	 
	         if (real)
	        	 s = s + " centavos";
	       }
	    }
	    return(s);
	  }	
}
