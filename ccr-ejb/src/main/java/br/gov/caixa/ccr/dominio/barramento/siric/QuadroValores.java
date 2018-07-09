package br.gov.caixa.ccr.dominio.barramento.siric;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="QUADRO_VALORES")
@XmlType(propOrder={"codigo", "linha"})
public class QuadroValores implements Serializable {

	private static final long serialVersionUID = -2916315826081705426L;
	
	// descobrir o que eh o quadro de valor 700 e o valor da linha 01
	@XmlElement(name = "COD_QUADRO_VALOR")
	private String codigo = "700";
	
	private String linha;

	@XmlElement(name = "LINHA_1")
	public String getLinha() {
		return linha;
	}

	public void setLinha(String linha) {
		this.linha = linha;
	}

}
