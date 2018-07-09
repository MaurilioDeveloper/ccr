package br.gov.caixa.ccr.dominio.barramento;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import br.gov.caixa.ccr.dominio.barramento.siric.DadosResultadoAvaliacao;

@XmlRootElement(name="avaliacao_risco_credito:SERVICO_SAIDA")
@XmlSeeAlso({SibarHeader.class, DadosResultadoAvaliacao.class})
public class MensagemAvaliacaoSaida extends MensagemBarramento {

	private static final long serialVersionUID = -3464313214159162454L;
	
	private DadosResultadoAvaliacao dados;

	@XmlElement(name="DADOS")
	public DadosResultadoAvaliacao getDados() {
		return dados;
	}

	public void setDados(DadosResultadoAvaliacao dados) {
		this.dados = dados;
	}
	
}
