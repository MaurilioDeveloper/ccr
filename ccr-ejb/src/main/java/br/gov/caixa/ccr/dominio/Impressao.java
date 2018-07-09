package br.gov.caixa.ccr.dominio;

import java.util.Date;

import br.gov.caixa.ccr.template.formatters.FormatEnum;
import br.gov.caixa.ccr.template.formatters.TemplateFormat;

public class Impressao {
	
	private String localImpressaoContrato;
	private String nomeGerente;
	
	@TemplateFormat(format=FormatEnum.CPF)
	private Long cpfGerente;
	
	private String nomeTestemunha1;
	
	@TemplateFormat(format=FormatEnum.CPF)
	private Long cpfTestemunha1;
	
	private String nomeTestemunha2;
	
	@TemplateFormat(format=FormatEnum.CPF)
	private Long cpfTestemunha2;
	
	private String numContrato;
	
	@TemplateFormat(format=FormatEnum.DATA)
	private Date dataImpressao;
	
	@TemplateFormat(format=FormatEnum.CPF)
	private String cpfCliente;
	
	private String  nomeCliente;
	
	private CamposRetornadosCCR sicli;
	
	public String getLocalImpressaoContrato() {
		return localImpressaoContrato;
	}
	public void setLocalImpressaoContrato(String localImpressaoContrato) {
		this.localImpressaoContrato = localImpressaoContrato;
	}
	public String getNomeGerente() {
		return nomeGerente;
	}
	public void setNomeGerente(String nomeGerente) {
		this.nomeGerente = nomeGerente;
	}
	public Long getCpfGerente() {
		return cpfGerente;
	}
	public void setCpfGerente(Long cpfGerente) {
		this.cpfGerente = cpfGerente;
	}
	public String getNomeTestemunha1() {
		return nomeTestemunha1;
	}
	public void setNomeTestemunha1(String nomeTestemunha1) {
		this.nomeTestemunha1 = nomeTestemunha1;
	}
	public Long getCpfTestemunha1() {
		return cpfTestemunha1;
	}
	public void setCpfTestemunha1(Long cpfTestemunha1) {
		this.cpfTestemunha1 = cpfTestemunha1;
	}
	public String getNomeTestemunha2() {
		return nomeTestemunha2;
	}
	public void setNomeTestemunha2(String nomeTestemunha2) {
		this.nomeTestemunha2 = nomeTestemunha2;
	}
	public Long getCpfTestemunha2() {
		return cpfTestemunha2;
	}
	public void setCpfTestemunha2(Long cpfTestemunha2) {
		this.cpfTestemunha2 = cpfTestemunha2;
	}
	public String getNumContrato() {
		return numContrato;
	}
	public void setNumContrato(String numContrato) {
		this.numContrato = numContrato;
	}
	public String getCpfCliente() {
		return cpfCliente;
	}
	public void setCpfCliente(String cpfCliente) {
		this.cpfCliente = cpfCliente;
	}
	public String getNomeCliente() {
		return nomeCliente;
	}
	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}
	public Date getDataImpressao() {
		return dataImpressao;
	}
	public void setDataImpressao(Date dataImpressao) {
		this.dataImpressao = dataImpressao;
	}
	public CamposRetornadosCCR getSicli() {
		return sicli;
	}
	public void setSicli(CamposRetornadosCCR sicli) {
		this.sicli = sicli;
	}
	
	
		
	
}
