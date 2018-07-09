package br.gov.caixa.ccr.dominio.barramento.siabe;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="CREDITO_PAGAMENTO")
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class CreditoPgto implements Serializable {

	private static final long serialVersionUID = 7276156411928626875L;
	
	private String valorBruto;
	private String valorLiquido;

	@XmlElement(name="VALOR_BRUTO")
	public String getValorBruto() {
		return valorBruto;
	}

	public void setValorBruto(String valorBruto) {
		this.valorBruto = valorBruto;
	}

	@XmlElement(name="VALOR_LIQUIDO")
	public String getValorLiquido() {
		return valorLiquido;
	}

	public void setValorLiquido(String valorLiquido) {
		this.valorLiquido = valorLiquido;
	}
	
}
