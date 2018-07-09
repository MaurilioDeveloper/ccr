package br.gov.caixa.ccr.dominio.barramento.alteracaocliente;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import br.gov.caixa.arqrefservices.dominio.barramento.MensagemBarramento;
import br.gov.caixa.arqrefservices.dominio.barramento.SibarHeader;

@XmlRootElement(name = "manutcli:SERVICO_SAIDA")
@XmlSeeAlso({ SibarHeader.class , EnvioDadosAlteracaoSicli.class})
public class MensagemSicliRetorno extends	MensagemBarramento {
	
	private static final long serialVersionUID = 8848664318809386477L;
	
	private RetornoDadosAlteracaoSicli dados;

	@XmlElement(name = "DADOS")
	public RetornoDadosAlteracaoSicli getDados() {
		return dados;
	}

	public void setDados(RetornoDadosAlteracaoSicli dados) {
		this.dados = dados;
	}
}
