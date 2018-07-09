package br.gov.caixa.ccr.dominio;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

import br.gov.caixa.arqrefservices.dominio.sicli.Documento;
import br.gov.caixa.ccr.template.formatters.FormatEnum;
import br.gov.caixa.ccr.template.formatters.TemplateFormat;

public class DocumentoImpressao implements Serializable{

	
	private static final long serialVersionUID = -6735649292665155041L;

	private String numero;
	
	private String digitoVerificador;
	
	private String serie;
	
	private String uf;
	
	
	@TemplateFormat(format=FormatEnum.DATA)
	private String dataEmissao;
	
	private String orgaoEmissor;

	
	public DocumentoImpressao(Documento documento) throws IllegalAccessException, InvocationTargetException {
		if(documento != null){
			BeanUtils.copyProperties(this, documento);
		}
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}


	public String getDigitoVerificador() {
		return digitoVerificador;
	}

	public void setDigitoVerificador(String digitoVerificador) {
		this.digitoVerificador = digitoVerificador;
	}	


	public String getSerie() {
		return serie;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}


	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}


	public String getDataEmissao() {
		return dataEmissao;
	}

	public void setDataEmissao(String dataEmissao) {
		this.dataEmissao = dataEmissao;
	}	
	

	public String getOrgaoEmissor() {
		return orgaoEmissor;
	}

	public void setOrgaoEmissor(String orgaoEmissor) {
		this.orgaoEmissor = orgaoEmissor;
	}

	@Override
	public String toString() {
		return "Documento [numero=" + numero + ", digitoVerificador="
				+ digitoVerificador + ", serie=" + serie + ", uf=" + uf
				+ ", dataEmissao=" + dataEmissao + "]";
	}
}
	
	
