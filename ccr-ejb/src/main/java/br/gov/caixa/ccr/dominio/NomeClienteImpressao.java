package br.gov.caixa.ccr.dominio;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

import br.gov.caixa.arqrefservices.dominio.sicli.NomeCliente;


public class NomeClienteImpressao implements Serializable{

	private static final long serialVersionUID = 1014608955723488396L;
	private String nome;

	public NomeClienteImpressao(NomeCliente nomeCliente) throws IllegalAccessException, InvocationTargetException {
		if(nomeCliente != null){
			BeanUtils.copyProperties(this, nomeCliente);
		}
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public String toString() {
		return "NomeCliente [nome=" + nome + "]";
	}
	
	

}
