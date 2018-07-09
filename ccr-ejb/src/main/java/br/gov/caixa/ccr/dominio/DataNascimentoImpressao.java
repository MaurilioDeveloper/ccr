package br.gov.caixa.ccr.dominio;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

import br.gov.caixa.arqrefservices.dominio.sicli.DataNascimento;
import br.gov.caixa.ccr.template.formatters.FormatEnum;
import br.gov.caixa.ccr.template.formatters.TemplateFormat;

public class DataNascimentoImpressao implements Serializable{

	private static final long serialVersionUID = 4008971721897080151L;
	
	@TemplateFormat(format=FormatEnum.DATA)
	private String data;

	public DataNascimentoImpressao(DataNascimento dataNascimento) throws IllegalAccessException, InvocationTargetException {
		if(dataNascimento != null){
			BeanUtils.copyProperties(this, dataNascimento);
		}
		
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}


	public String toString() {
		return "DataNascimento [data=" + data + "]";
	}
	
	

}
