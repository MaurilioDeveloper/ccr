package br.gov.caixa.ccr.dominio.apx;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Classe de dominio
 * @author c110503
 *
 */
@XmlRootElement
public class Usuario implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3582412591540937501L;
	
	private String codigoUsuario; 
	
	private String codigoAgencia;	
	
	@XmlAttribute(name="CO-USUARIO")	
	public String getCodigoUsuario() {
		return codigoUsuario;
	}

	public void setCodigoUsuario(String codigoUsuario) {
		this.codigoUsuario = codigoUsuario;
	}
	
	@XmlAttribute(name="CO-AGENCIA")	
	public String getCodigoAgencia() {
		return codigoAgencia;
	}

	public void setCodigoAgencia(String codigoAgencia) {
		this.codigoAgencia = codigoAgencia;
	}
	
	@Override
	public String toString() {
		return "Usuario [codigoUsuario =" + codigoUsuario 
				 + ", codigoAgencia=" + codigoAgencia;
	}

	

}