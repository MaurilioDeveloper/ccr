package br.gov.caixa.ccr.dominio.barramento.alteracaocliente;

import br.gov.caixa.ccr.util.AbstractMainframeHelper;

public class AtualizarCliente extends AbstractMainframeHelper {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private MensagemAtualizacaoCliente mensagemAtualizacao;

	public AtualizarCliente(AtualizacaoCliente atualizacao) {
		mensagemAtualizacao = new MensagemAtualizacaoCliente(atualizacao);
	}

	public MensagemAtualizacaoCliente getMensagemAtualizacao() {
		return mensagemAtualizacao;
	}

	public void setMensagemAtualizacao(MensagemAtualizacaoCliente mensagemAtualizacao) {
		this.mensagemAtualizacao = mensagemAtualizacao;
	}


}
