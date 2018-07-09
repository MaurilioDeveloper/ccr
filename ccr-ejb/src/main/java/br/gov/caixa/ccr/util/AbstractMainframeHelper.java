package br.gov.caixa.ccr.util;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import br.gov.caixa.arqrefcore.excecao.mensagem.Message;
import br.gov.caixa.arqrefservices.dominio.barramento.CamposRetornados;
import br.gov.caixa.arqrefservices.dominio.barramento.Chave;
import br.gov.caixa.ccr.dominio.barramento.alteracaocliente.MensagemSiemp;

@XmlRootElement
public abstract class AbstractMainframeHelper implements Serializable {

	private static final long serialVersionUID = -7319941652463019386L;

	private MensagemSiemp mensagem;
	private Message message;
	
	//ATRIBUTOS USADOS PELO SICLI
	private Chave chave;
	
	private CamposRetornados camposRetornadosSICLI;
	
	private CamposRetornados camposRetornadosSICLIEmpresa;
	//FIM ATRIBUTOS PELO SICLI

	public AbstractMainframeHelper() {
	}
	
	public AbstractMainframeHelper(MensagemSiemp mensagem) {
		this.mensagem = mensagem;
	}
	
	public Message getMessage() {
		if (message == null) {
			message = new MensagemSiempAdapter(getMensagem());
		}
		
		return message;
	}

	public MensagemSiemp getMensagem() {
		if(this.mensagem == null) {
			mensagem = new MensagemSiemp();
		}
		return mensagem;
	}
	
	public void setMensagem(MensagemSiemp mensagem) {
		this.mensagem = mensagem;
	}
	
	/**
	 * Avalisa se a resposta da comunicação com o main frame contem erro.
	 * 
	 * @return TRUE se contiver erro.
	 * @since 17/06/2013
	 */
	public boolean isContemErro() {
		return mensagem != null && getMensagem().isContemMensagemErro();
	}

	public Chave getChave() {
		return chave;
	}

	public void setChave(Chave chave) {
		this.chave = chave;
	}

	public CamposRetornados getCamposRetornadosSICLI() {
		return camposRetornadosSICLI;
	}

	public void setCamposRetornadosSICLI(CamposRetornados camposRetornadosSICLI) {
		this.camposRetornadosSICLI = camposRetornadosSICLI;
	}

	public CamposRetornados getCamposRetornadosSICLIEmpresa() {
		return camposRetornadosSICLIEmpresa;
	}

	public void setCamposRetornadosSICLIEmpresa(
			CamposRetornados camposRetornadosSICLIEmpresa) {
		this.camposRetornadosSICLIEmpresa = camposRetornadosSICLIEmpresa;
	}

	public void setMessage(Message message) {
		this.message = message;
	}
	
}
