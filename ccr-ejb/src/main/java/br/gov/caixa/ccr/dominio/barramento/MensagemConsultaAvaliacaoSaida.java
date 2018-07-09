package br.gov.caixa.ccr.dominio.barramento;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import br.gov.caixa.ccr.dominio.barramento.siric.DadosResultadoConsulta;

@XmlRootElement(name="consulta_avaliacao_risco:SERVICO_SAIDA")
@XmlSeeAlso({SibarHeader.class, DadosResultadoConsulta.class})
public class MensagemConsultaAvaliacaoSaida extends MensagemBarramento {

	private static final long serialVersionUID = -3464312214159162454L;
	
	private DadosResultadoConsulta dados;

	@XmlElement(name="DADOS")
	public DadosResultadoConsulta getDados() {
		return dados;
	}

	public void setDados(DadosResultadoConsulta dados) {
		this.dados = dados;
	}

}
