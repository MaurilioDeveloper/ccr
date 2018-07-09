package br.gov.caixa.ccr.util;

import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.EntityManager;

public class Auditoria<T> {
	
	private EntityManager em;
	private Class<T> entityClass;
	private String coUsuario;
	
	public Auditoria(EntityManager em, Class<T> entityClass, String coUsuario) {
		this.em = em;
		this.coUsuario = coUsuario;
		this.entityClass = entityClass;
	}

	public void gravaAuditoria(Object clazzToAudit) {
		
		try {
			T clazzAudit = entityClass.newInstance();
			Utilities.copyAttributesFromTo(clazzAudit, clazzToAudit);
			
			Integer sequencial = getMaxSequencial(clazzAudit);
			sequencial = sequencial == null ? 1 : sequencial + 1;
			
			Field historico = entityClass.getDeclaredField("historico");
			historico.setAccessible(true);
			historico.set(clazzAudit, (int) sequencial);
			
			Field tsHistorico = entityClass.getDeclaredField("tsHistorico");
			tsHistorico.setAccessible(true);
			tsHistorico.set(clazzAudit, (Date) (Calendar.getInstance()).getTime());
			
			Field usuario = entityClass.getDeclaredField("usuario");
			usuario.setAccessible(true);
			usuario.set(clazzAudit, (String) coUsuario);
			
			em.persist(clazzAudit);
			em.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
		
	private Integer getMaxSequencial(T clazzAudit) throws Exception {
		Field id = entityClass.getDeclaredField("id");
		id.setAccessible(true);
		
		StringBuilder sb = new StringBuilder("select max(T.historico) from " + entityClass.getSimpleName() + " T ");
		sb.append("where T.id = "+ Integer.toString(id.getInt(clazzAudit)));
		String sql = sb.toString();
		
		return (Integer) em.createQuery(sql).getSingleResult();		
	}
	
}
