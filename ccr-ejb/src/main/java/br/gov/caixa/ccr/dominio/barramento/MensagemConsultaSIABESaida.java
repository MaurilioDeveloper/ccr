package br.gov.caixa.ccr.dominio.barramento;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import br.gov.caixa.ccr.dominio.barramento.siabe.DadosResultado;

@XmlRootElement(name="consignado:SERVICO_SAIDA")
@XmlSeeAlso({SibarHeader.class, DadosResultado.class})
public class MensagemConsultaSIABESaida extends MensagemBarramento {

	private static final long serialVersionUID = -3462312214159162454L;
	
	private DadosResultado dados;

	@XmlElement(name="DADOS")
	public DadosResultado getDados() {
		return dados;
	}

	public void setDados(DadosResultado dados) {
		this.dados = dados;
	}
	
}
