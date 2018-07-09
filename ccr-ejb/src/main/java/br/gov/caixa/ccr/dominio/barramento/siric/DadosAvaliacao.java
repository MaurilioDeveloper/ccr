package br.gov.caixa.ccr.dominio.barramento.siric;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="DADOS")
@XmlType(propOrder={"operacao", "quadro", "relacionamento", "atualizacaoViaSicli"})
public class DadosAvaliacao implements Serializable {

	private static final long serialVersionUID = -956614543513129482L;	
	public static final String OPERACAO = "AVALIACAORISCOCREDITO";
	
	private DadosOperacao operacao;	
	private QuadroValores quadro;	
	private Relacionamento relacionamento;	
	private AtualizacaoViaSicli atualizacaoViaSicli;

	public DadosAvaliacao() {
		this.operacao = new DadosOperacao();
		this.quadro = new QuadroValores();
		this.relacionamento = new Relacionamento();
		this.atualizacaoViaSicli = new AtualizacaoViaSicli();
	}
	
	@XmlElement(name="DADOS_OPERACAO")
	public DadosOperacao getOperacao() {
		return operacao;
	}

	public void setOperacao(DadosOperacao operacao) {
		this.operacao = operacao;
	}

	@XmlElement(name="QUADRO_VALORES")
	public QuadroValores getQuadro() {
		return quadro;
	}

	public void setQuadro(QuadroValores quadro) {
		this.quadro = quadro;
	}

	@XmlElement(name="RELACIONAMENTOS")
	public Relacionamento getRelacionamento() {
		return relacionamento;
	}

	public void setRelacionamento(Relacionamento relacionamento) {
		this.relacionamento = relacionamento;
	}

	@XmlElement(name="AVALIACAO_COM_ATUALIZACAO_VIA_SICLI")
	public AtualizacaoViaSicli getAtualizacaoViaSicli() {
		return atualizacaoViaSicli;
	}

	public void setAtualizacaoViaSicli(AtualizacaoViaSicli atualizacaoViaSicli) {
		this.atualizacaoViaSicli = atualizacaoViaSicli;
	}
		
}
