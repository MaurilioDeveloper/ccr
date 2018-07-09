package br.gov.caixa.ccr.dominio.barramento.alteracaocliente;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import br.gov.caixa.ccr.dominio.barramento.MensagemBarramento;
import br.gov.caixa.ccr.dominio.barramento.SibarHeader;


@XmlRootElement(name = "manutcli:SERVICO_ENTRADA")
@XmlSeeAlso({ SibarHeader.class , AtualizacaoCliente.class})
@XmlAccessorType(XmlAccessType.FIELD)
public class MensagemAtualizacaoCliente extends MensagemBarramento {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@XmlAttribute(name = "xmlns:ippo")
	private String xmlns_ippo = "http://caixa.gov.br/ippo/manutencao";

	@XmlAttribute(name = "xmlns:manutcli")
	private String xmlns_manutcli = "http://caixa.gov.br/sibar/manutencaocliente";

	@XmlAttribute(name = "xmlns:sibar_base")
	private String xmlns_sibar_base = "http://caixa.gov.br/sibar";

	@XmlAttribute(name = "xmlns:xsi")
	private String xmlns_xsi = "http://www.w3.org/2001/XMLSchema-instance";

	@XmlAttribute(name = "xsi:schemaLocation")
	private String xsi_schemaLocation = "http://caixa.gov.br/sibar/manutencaocliente ServicoManutencaoCliente.xsd http://caixa.gov.br/ippo/manutencao ../../ippo/manutencao/IPPO_CAMPOS_MANUTENCAO.xsd http://caixa.gov.br/sibar ../MensagensBarramento.xsd http://caixa.gov.br/ippo/manutencao ../../ippo/manutencao/CLIPO300.xsd  ";

	@XmlElement(name = "DADOS")
	private AtualizacaoCliente atualizacao;

	public MensagemAtualizacaoCliente() {

	}

	public MensagemAtualizacaoCliente(AtualizacaoCliente atualizacao) {
		this.atualizacao = atualizacao;
	}

	public AtualizacaoCliente getAtualizacao() {
		return atualizacao;
	}

	public void setAtualizacao(AtualizacaoCliente atualizacao) {
		this.atualizacao = atualizacao;
	}
}
