package br.gov.caixa.ccr.dominio.barramento.siric;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="MENSAGEM_RETORNO")
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class MensagemRetorno implements Serializable {

	private static final long serialVersionUID = -6949008839200522487L;

	private String proposta;	
	private Cliente cliente;
	private String produto;
	private String idMensagem;

	@XmlElement(name = "PROPOSTA")
	public String getProposta() {
		return proposta;
	}

	public void setProposta(String proposta) {
		this.proposta = proposta;
	}

	@XmlElement(name = "CLIENTE")
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	@XmlElement(name = "PRODUTO")	
	public String getProduto() {
		return produto;
	}

	public void setProduto(String produto) {
		this.produto = produto;
	}

	@XmlElement(name = "IDENTIFICADOR_MENSAGEM")
	public String getIdMensagem() {
		return idMensagem;
	}

	public void setIdMensagem(String idMensagem) {
		this.idMensagem = idMensagem;
	}
	
}
