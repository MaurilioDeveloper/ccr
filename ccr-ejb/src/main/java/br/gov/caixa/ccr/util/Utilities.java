package br.gov.caixa.ccr.util;

import java.lang.reflect.Field;
import java.sql.Date;
import java.text.ParseException;
import java.util.List;

import br.gov.caixa.ccr.dominio.Retorno;

public abstract class Utilities {
    
	/**
	 * Entra com o primeiro parametro que seja do tipo Retorno, <br>
	 * Entra com o segundo parametro que possua os mesmo atributos que o primeiro parametro.
	 *  
	 * @param destino
	 * @param origem
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static Object copyAttributesFromTo(Object destino, Object origem) throws IllegalArgumentException, IllegalAccessException, ParseException {  
		if (destino == null || origem == null) return null;
		Field[] fieldsFromFirstClass = destino.getClass().getDeclaredFields();
		
		if (destino instanceof Retorno) {
        	try {
        		Field[] fieldsA = destino.getClass().getDeclaredFields();
        		fieldsFromFirstClass = new Field[fieldsA.length + 1];
        		
        		fieldsFromFirstClass[0] = Retorno.class.getDeclaredField("codigoRetorno");
        		
        		for (int i = 0; i < fieldsA.length; i++) {
        			fieldsFromFirstClass[i+1] = fieldsA[i]; 
				}
			} catch (NoSuchFieldException e) {}
		}
          
        Field[] fieldsFromSecondClass = origem.getClass().getDeclaredFields();   
  
        for (Field currentFieldFromTheFirstClass : fieldsFromFirstClass) {
        	if (currentFieldFromTheFirstClass.getName().equals("serialVersionUID"))
        		continue;
        	
            for (Field currentFieldFromTheSecondClass : fieldsFromSecondClass) {  
                String nameOfTheFirstField = currentFieldFromTheFirstClass.getName();  
                String nameOfTheSecondField = currentFieldFromTheSecondClass.getName();  
                
                if (nameOfTheFirstField.equals(nameOfTheSecondField)) {  
                    currentFieldFromTheFirstClass.setAccessible(true);  
                    currentFieldFromTheSecondClass.setAccessible(true);  
  
                    // permite que a TO receba date do banco e retorne string pro js
                    if ((currentFieldFromTheSecondClass.getType().equals(Date.class) || currentFieldFromTheSecondClass.getType().equals(java.util.Date.class)) 
                    	&& currentFieldFromTheFirstClass.getType().equals(String.class)) {
                    	currentFieldFromTheFirstClass.set(destino, DataUtil.formatarDataHora((java.util.Date) currentFieldFromTheSecondClass.get(origem)));
                    } else if (currentFieldFromTheFirstClass.getType().equals(java.util.Date.class) && currentFieldFromTheSecondClass.getType().equals(String.class)) {
                    	currentFieldFromTheFirstClass.set(destino, DataUtil.converterData((String) currentFieldFromTheSecondClass.get(origem)));
                    } else if(currentFieldFromTheSecondClass.getType().toString().indexOf("br.gov.caixa.ccr") > -1) {
                    	Utilities.copyAttributesFromTo(currentFieldFromTheFirstClass.get(destino), currentFieldFromTheSecondClass.get(origem));
                    } else {
                    	currentFieldFromTheFirstClass.set(destino, currentFieldFromTheSecondClass.get(origem));
                    }
                }  
            }  
        }  
        
        return destino;  
    }
	
	/**
	 * Entra com o primeiro parametro que seja uma lista de uma classe de dominio, <br>
	 * Entra com o segundo parametro que seja a lista da classe equivalente de transicao.
	 *  
	 * @param listaDestino A lista {@code List<T>} da classe de destino 
	 * @param classDestino A classe de destino
	 * @param listaOrigem A lista {@code List<T>} da classe de origem
	 * @return void
	 * @throws InstantiationException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static <T, S> void copyListClassFromTo(List<T> listaDestino, Class<T> classDestino, List<S> listaOrigem) 
		throws InstantiationException, IllegalArgumentException, IllegalAccessException, ParseException {
		
		for (S origem : listaOrigem){
			T destino = classDestino.newInstance(); 
			
			copyAttributesFromTo(destino, origem);
			listaDestino.add(destino);
		}
	}
	
	public static String preencherZeros(Integer valor, Integer tamanho) {
		StringBuffer sValor = new StringBuffer();
		sValor.append(valor);
	    int contador = sValor.length();  
	    if (contador < tamanho) {  
	        do {  
	            sValor.insert(0, "0");  
	            contador += 1;  
	              
	        } while (contador < tamanho);  
	    }  
	    return sValor.toString();
	}

	public static Integer obterNumero(String texto) {
		return texto == null || texto.trim().equals("") ? null : Integer.valueOf(texto);
	}
	
	public static String padRight(String s, int n, char valor) {
		// String.format("%-10s", "bar").replace(' ', '*');
		if (valor != ' ')
			return String.format("%1$-" + n + "s", s).replace(' ', valor);
		else
			return String.format("%1$-" + n + "s", s);
	}

	public static String padLeft(String s, int n, char valor) {
		// String.format("%10s", "foo").replace(' ', '*');
		// return String.format("%1$" + n + "s", s);
		if (valor != ' ')
			return String.format("%1$" + n + "s", s).replace(' ', valor);
		else
			return String.format("%1$" + n + "s", s);
	}
	
	public static String leftTrim(String texto) {
		if (texto == null)
			return "";
		
		return texto.replaceAll("^\\s+", "");
	}
	
	public static String rightTrim(String texto) {
		if (texto == null)
			return "";
		
		return texto.replaceAll("\\s+$", "");
	}
	
	public static String leftRightTrim(String texto) {
		return Utilities.leftTrim(Utilities.rightTrim(texto));
	}
	
	public static Integer getNumeroMatricula(String matricula) {
		return Integer.valueOf(matricula.substring(1));
	}
	
}
