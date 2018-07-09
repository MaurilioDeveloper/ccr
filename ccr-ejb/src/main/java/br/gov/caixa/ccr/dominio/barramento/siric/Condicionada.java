package br.gov.caixa.ccr.dominio.barramento.siric;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="CONDICIONADA")
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class Condicionada extends CondicionadaOuReprovada {

	private static final long serialVersionUID = 6363419403079830127L;	
	
}
