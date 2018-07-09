package br.gov.caixa.ccr.dominio.barramento.alteracaocliente;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import br.gov.caixa.arqrefservices.dominio.barramento.MensagemBarramento;

@XmlRootElement(name="DADOS")
@XmlSeeAlso({ ManutencaoIntegral.class, ManutencaoErro.class, ControleNegocial.class })
public class RetornoDadosAlteracaoSicli extends MensagemBarramento{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5269773180348442626L;
	
	private ManutencaoIntegral manutencaoIntegral;
	
	private List<ManutencaoErro> manutencaoErroList;
	
	private ControleNegocial controleNegocial;
	
	private String excecao;
	
	
	@XmlElementWrapper(name="MANUTENCAO_ERRO")
	@XmlElement(name="ERRO")
	public List<ManutencaoErro> getManutencaoErroList() {
		return this.manutencaoErroList;
	}

	@XmlElement(name="MANUTENCAO_INTEGRAL")
	public ManutencaoIntegral getManutencaoIntegral() {
		return this.manutencaoIntegral;
	}

	@XmlElement(name="EXCECAO")
	public String getExcecao() {
		return this.excecao;
	}

	public void setManutencaoIntegral(ManutencaoIntegral manutencaoIntegral) {
		this.manutencaoIntegral = manutencaoIntegral;
	}

	public void setManutencaoErroList(List<ManutencaoErro> manutencaoErroList) {
		this.manutencaoErroList = manutencaoErroList;
	}

	public void setExcecao(String excecao) {
		this.excecao = excecao;
	}
	
	@XmlElement(name="CONTROLE_NEGOCIAL")
	public ControleNegocial getControleNegocial() {
		return controleNegocial;
	}

	public void setControleNegocial(ControleNegocial controleNegocial) {
		this.controleNegocial = controleNegocial;
	}	
}
