package br.gov.caixa.ccr.dominio.barramento.siric;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="AVALIACAO_COM_ATUALIZACAO_VIA_SICLI")
@XmlType(propOrder={"tipo", "cpf"})
public class AtualizacaoViaSicli implements Serializable {

	private static final long serialVersionUID = 3768241598875274164L;

	@XmlElement(name = "TP_PESSOA")
	private String tipo = "1";	
	private String cpf;

	@XmlElement(name = "CPF")	
	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
}
