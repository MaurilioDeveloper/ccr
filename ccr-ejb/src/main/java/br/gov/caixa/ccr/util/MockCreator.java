package br.gov.caixa.ccr.util;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * Classe utilitaria para criacao de objetos MOCK.
 * @author TIVIT
 * @since 31/03/2017
 * */
public class MockCreator implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3521127416758094428L;
	private static Random random = new Random();

	/**
	 * Metodo utilizado para criar mock.
	 * */
	public static <T> List<T> createMockAsList(final Class<T> clazz) {
    	List<T> list = new ArrayList<T>();
    	for (int i = 0; i < 1; i++) {
//    	for (int i = 0; i < random.nextInt(10); i++) {
    		T t = createMock(clazz);
    		if (t != null) {
    			list.add(createMock(clazz));
    		}
    	}
    	return list;
    }
	
	/**
	 * Metodo utilizado para criar mock.
	 * 	Funciona apenas com classes individuais, nao com listas e arrays.
	 * */
    public static <T> T createMock(final Class<T> clazz) {
    	try {
	        T instance = clazz.newInstance();
	        for(Field field: clazz.getDeclaredFields()) {
	            field.setAccessible(true);
	            if(!field.getName().equals("serialVersionUID")){
		            Object value = getRandomValueForField(field);
		            field.set(instance, value);
	            }
	        }
	        return instance;
    	} catch (Exception e) {
    		e.printStackTrace();
    		return null;
    	}
    }
 
    /**
     * Gera Mock conforme tipo dos objetos, caso necessario adicao de algum novo tipo
     * 	basta complementar com novos fluxos de criacao
     * */
    private static Object getRandomValueForField(Field field) throws Exception {
        Class<?> type = field.getType();
        if(type.isEnum()) {
            Object[] enumValues = type.getEnumConstants();
            return enumValues[random.nextInt(enumValues.length)];
        } 
        if(type.equals(Integer.TYPE) || type.equals(Integer.class)) {
            return 1;//Math.abs(random.nextInt());
        } 
        if(type.equals(Byte.TYPE) || type.equals(Byte.class)) {
        	byte b = 1;
            return Byte.valueOf(b);
        }
        if(type.equals(Short.TYPE) || type.equals(Short.class)) {
        	short s = 1;
            return Short.valueOf(s);
        }
        if(type.equals(Long.TYPE) || type.equals(Long.class)) {
            return 1L;//Math.abs(random.nextLong());
        } 
        if(type.equals(Double.TYPE) || type.equals(Double.class)) {
            return 1.5d;//Math.abs(random.nextDouble());
        } 
        if(type.equals(Float.TYPE) || type.equals(Float.class)) {
            return 1.5;//Math.abs(random.nextFloat());
        } 
        if(type.equals(Character.TYPE) || type.equals(Character.class)) {
            return 'S';
        } 
        if(type.equals(String.class)) {
        	return "S";//return UUID.randomUUID().toString().substring(0, 2);
        }
        if(type.equals(BigInteger.class)){
            return BigInteger.valueOf(1);//BigInteger.valueOf(random.nextInt());
        } 
        if (type.equals(Date.class)) {
        	return new Date();
        } 
        if (type.equals(Calendar.class)) {
        	return Calendar.getInstance();
        } 
        if (type.equals(Boolean.TYPE) || type.equals(Boolean.class)) {
        	return Boolean.FALSE;
        }
        if (type.equals(XMLGregorianCalendar.class)) {
        	GregorianCalendar c = new GregorianCalendar();
        	c.setTime(new Date());
        	XMLGregorianCalendar date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
        	return date2;
        }
        if (type.equals(List.class)) {
        	Type generic = field.getGenericType();
        	if (generic instanceof ParameterizedType) {
        		 ParameterizedType pType = (ParameterizedType)generic;
        		 Class clazz = (Class) pType.getActualTypeArguments()[0];
        		 List l = new ArrayList();
        		 for (int i = 0; i < 1; i++) {
//        		 for (int i = 0; i < random.nextInt(10); i++) {
        			 l.add(createMock(clazz));	 
        		 }
        		 return l;
        	}
        	return new ArrayList();
        } 
        if (type.equals(BigDecimal.class)) {
        	return BigDecimal.ONE;
        }
        else {
        	System.out.println(type);
        }
        return createMock(type);
    }
}
