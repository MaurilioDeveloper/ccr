package br.gov.caixa.ccr.dominio.barramento.alteracaocliente;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

import br.gov.caixa.arqrefservices.dominio.sicli.EnderecoNacional;
import br.gov.caixa.arqrefservices.dominio.sicli.MeioComunicacao;
import br.gov.caixa.arqrefservices.dominio.sicli.Renda;

@XmlRootElement(name="CAMPOS")
@XmlSeeAlso({ MeioComunicacao.class, EnderecoNacional.class, Renda.class })
@XmlType(propOrder={"meiosComunicacao", "endereco", "renda","rendaAExcluir"})
public class CamposSicli implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<MeioComunicacao> meiosComunicacao;

	private EnderecoNacional endereco;

	private List<Renda> renda;

	private List<RendaAExcluir> rendaAExcluir;

	@XmlElement(name = "MEIO_COMUNICACAO")
	public List<MeioComunicacao> getMeiosComunicacao() {
		return meiosComunicacao;
	}

	@XmlElement(name = "ENDERECO_NACIONAL")
	public EnderecoNacional getEndereco() {
		return endereco;
	}

	@XmlElement(name = "RENDA")
	public List<Renda> getRenda() {
		return renda;
	}

	public void setEndereco(EnderecoNacional enderecoNacional) {
		endereco = enderecoNacional;
	}

	public void setRenda(List<Renda> rendaCliente) {
		renda = rendaCliente;
	}

	public void setMeiosComunicacao(List<MeioComunicacao> meioComunicacao) {
		meiosComunicacao = meioComunicacao;
	}

	@XmlElement(name = "EXCLUSAO_RENDA")
	public List<RendaAExcluir> getRendaAExcluir() {
		return rendaAExcluir;
	}

	public void setRendaAExcluir(List<RendaAExcluir> rendaAExcluir) {
		this.rendaAExcluir = rendaAExcluir;
	}

}
