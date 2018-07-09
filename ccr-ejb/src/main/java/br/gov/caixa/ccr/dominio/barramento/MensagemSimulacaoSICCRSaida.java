package br.gov.caixa.ccr.dominio.barramento;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import br.gov.caixa.ccr.dominio.barramento.simulacao.DadosResultado;

@XmlRootElement(name="consultabeneficiarioinss:SERVICO_SAIDA")
@XmlSeeAlso({SibarHeader.class, DadosResultado.class})
public class MensagemSimulacaoSICCRSaida extends MensagemBarramento {

	private static final long serialVersionUID = 2601055068509100118L;
	
	private DadosResultado dados;

	@XmlElement(name="DADOS")
	public DadosResultado getDados() {
		return dados;
	}

	public void setDados(DadosResultado dados) {
		this.dados = dados;
	}
	
}
