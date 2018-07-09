package br.gov.caixa.ccr.dominio.mensageria;

import br.gov.caixa.arqrefcore.excecao.mensagem.Message;
import br.gov.caixa.arqrefcore.excecao.mensagem.MessageDefault;
import br.gov.caixa.arqrefcore.excecao.mensagem.Messages;
import br.gov.caixa.arqrefservices.util.MensagemArqrefService;

public class MensagemCCR extends MessageDefault {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6703932718785842085L;

	private Messages messages = new Messages();

	private Message mensagem = new MensagemArqrefService();

	@Override
	public String getSistema() {
		return "CCR";
	}

	public Messages getMessages() {
		return messages;
	}

	public void setMessages(Messages messages) {
		this.messages = messages;
		this.mensagem = this.getMessages().getMessages().get(0);
	}

	public Message getMensagem() {
		return mensagem;
	}

	public void setMensagem(MensagemArqrefService mensagem) {
		this.mensagem = mensagem;
	}

	@Override
	public String toString() {
		return "MensagemTutorial [getOrigemErro()=" + getOrigemErro() + ", getParagrafoErro()=" + getParagrafoErro()
				+ ", getCategoriaErro()=" + getCategoriaErro() + ", getCodigoErro()=" + getCodigoErro()
				+ ", getMensagemNegocial()=" + getMensagemNegocial() + ", getMensagemErro()=" + getMensagemErro()
				+ ", getSeveridade()=" + getSeveridade() + ", getInformacoesAdicionais()=" + getInformacoesAdicionais()
				+ ", getSeveridadeDescricao()=" + getSeveridadeDescricao() + "]";
	}

}
