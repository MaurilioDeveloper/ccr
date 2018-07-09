package br.gov.caixa.ccr.util;

public class GeradorDVConvenio {
	
	public int getDVConvenio(String num) {  
		
		//String num = "0010"; 
		int soma = 0;
		int resto = 0;
		int dv = 0;
	    String[] numeros = new String[num.length()+1];
	    int multiplicador = 2;
	    for (int i = num.length(); i > 0; i--) {  	
	    	
	    	if(multiplicador > 9){
	    		
		    	multiplicador = 2;
	    		numeros[i] = String.valueOf(Integer.valueOf(num.substring(i-1,i))*multiplicador);
	    		multiplicador++;
	    	}else{
	    		numeros[i] = String.valueOf(Integer.valueOf(num.substring(i-1,i))*multiplicador);
	    		multiplicador++;
	    	}
	    } 
	    
	    for(int i = numeros.length; i > 0 ; i--){
	    	if(numeros[i-1] != null){
	    		System.out.println(numeros[i-1]);
	    		soma += Integer.valueOf(numeros[i-1]);
	    	}
	    }
	       
	    soma = soma*10;	    
	    
	    //Dividindo pela base 11
	    resto = soma%11;
	    
	    //digito verificador
	    //dv = 11 / resto;
    	dv = resto;
    	
    	return dv;
	}
	
	public static void main(String[] args) {  
		
		String num = "0010"; 
		int soma = 0;
		int resto = 0;
		int dv = 0;
		String[] numeros = new String[num.length()+1];
		int multiplicador = 2;
		for (int i = num.length(); i > 0; i--) {  	
			
			if(multiplicador > 9){
				
				multiplicador = 2;
				System.out.println(num.substring(i-1,i));
				numeros[i] = String.valueOf(Integer.valueOf(num.substring(i-1,i))*multiplicador);
				multiplicador++;
			}else{
				numeros[i] = String.valueOf(Integer.valueOf(num.substring(i-1,i))*multiplicador);
				System.out.println(num.substring(i-1,i));
				System.out.println("*");
				System.out.println(multiplicador);
				System.out.println("------------");
				System.out.println(numeros[i]);
				System.out.println("---------------------------");
				multiplicador++;
			}
		} 
		
		System.out.println("----------------------------------");
		
		for(int i = numeros.length; i > 0 ; i--){
			if(numeros[i-1] != null){
				System.out.println(numeros[i-1]);
				soma += Integer.valueOf(numeros[i-1]);
			}
		}
		System.out.println("----------------------------------");
		System.out.println("Soma: "+soma);
		System.out.println("----------------------------------");
		
		//TODO: verificar se tem q multiplicar por 10	    
		soma = soma*10;	    
		
		//Dividindo pela base 11
		resto = soma%11;
		
		System.out.println("resto da soma/11 =  "+resto);
		System.out.println("----------------------------------");
		
		//digito verificador
		//dv = 11 / resto;
		dv = resto;
		
		System.out.println("----------------------------------");
		System.out.println("DIgito Verificador:"+dv);
		System.out.println("----------------------------------");
		
	}
}