package br.gov.caixa.ccr.util;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.HibernateException;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.usertype.UserType;

public class TrimmedString implements UserType {
	
	public TrimmedString() {
		super();
    }
	
	@Override
	public Object assemble(Serializable cached, Object owner) throws HibernateException {
		return cached;
	}

	@Override
	public Object deepCopy(Object value) throws HibernateException {
		if (value == null) return null;
        return new String((String) value);
	}

	@Override
	public Serializable disassemble(Object value) throws HibernateException {
		return (Serializable) value;
	}

	@Override
	public boolean equals(Object x, Object y) throws HibernateException {
		return (x == y) || (x != null && y != null && (x.equals(y)));
	}

	@Override
	public int hashCode(Object value) throws HibernateException {
		return value.hashCode();
	}

	@Override
	public boolean isMutable() {
		return false;
	}

	public Object nullSafeGet(ResultSet rs, String name) throws HibernateException, SQLException {
		String[] names = {name};
		return this.nullSafeGet(rs, names, new Object());
	}
	
	public Object nullSafeGet(ResultSet rs, String[] names) throws HibernateException, SQLException {
		return this.nullSafeGet(rs, names, new Object());
	}
	
	@Override
	public Object nullSafeGet(ResultSet rs, String[] names, Object owner) throws HibernateException, SQLException {
		return new String("");
				
		/*
		String val = rs.getString(names[0]);
		
		if (null == val)
			return null;
		
        return Utilities.leftRightTrim(val);
       	*/
	}
	
	public Object nullSafeGet(ResultSet rs, String name, SessionImplementor session, Object owner) throws HibernateException, SQLException {
		String[] names = {name};
		return this.nullSafeGet(rs, names, owner);
	}
	
	public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner) throws HibernateException, SQLException {
		return this.nullSafeGet(rs, names, owner);
	}

	@Override
	public void nullSafeSet(PreparedStatement st, Object value, int index) throws HibernateException, SQLException {
		st.setString(index, (String)value);
	}

	@Override
	public Object replace(Object original, Object target, Object owner) throws HibernateException {
		return this.deepCopy(original);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Class returnedClass() {
		return String.class;
	}

	@Override
	public int[] sqlTypes() {
		return new int[] { Types.VARCHAR };
	}
	
}
