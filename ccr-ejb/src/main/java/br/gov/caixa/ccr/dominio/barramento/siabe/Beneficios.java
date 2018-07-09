package br.gov.caixa.ccr.dominio.barramento.siabe;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="BENEFICIOS")
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class Beneficios implements Serializable {

	private static final long serialVersionUID = 3938609979934863523L;
	
	private BeneficioDetalhe[] beneficioDetalhe;

	@XmlElement(name="BENEFICIO")
	public BeneficioDetalhe[] getBeneficioDetalhe() {
		return beneficioDetalhe;
	}

	public void setBeneficioDetalhe(BeneficioDetalhe[] beneficioDetalhe) {
		this.beneficioDetalhe = beneficioDetalhe;
	}

}
