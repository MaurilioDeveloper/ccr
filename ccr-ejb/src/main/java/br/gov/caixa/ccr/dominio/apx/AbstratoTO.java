package br.gov.caixa.ccr.dominio.apx;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;

/**
 * Classe abstrada de suporte as classes de Transicao (TO)
 * Usado pelo APX
 * @author c110503
 *
 */
public abstract class AbstratoTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1831606987609935853L;
	

	
	//classe da canal e comum a varios programas que utilizam o barramento
	private Canal canal;
	//classe de mensagem padrao do cobol
	private MensagemCobolAPX mensagem;

	@XmlElement(name = "CANAL")
	public Canal getCanal() {
		return canal;
	}

	public void setCanal(Canal canal) {
		this.canal = canal;
	}
	
	@XmlElement(name = "MENSAGEM")
	public MensagemCobolAPX getMensagem() {		
		return mensagem;
	}

	public void setMensagem(MensagemCobolAPX mensagem) {
		this.mensagem = mensagem;
	}

	@Override
	public String toString() {
		return "AbstratoTO [canal=" + canal + ", mensagemErro=" + mensagem
				+ "]";
	}

	


	
	
	
	

}
