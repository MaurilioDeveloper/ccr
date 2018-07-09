package br.gov.caixa.ccr.dominio;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

import br.gov.caixa.arqrefservices.dominio.sicli.ProfissaoSiric;

public class ProfissaoSiricImpressao  implements Serializable{


	private static final long serialVersionUID = -5773401995663232370L;

	private String descricaoProfissao;
	
	private String profissao;
	
	private String dataInicio;
	
	private String dataTermino;
	
	private String profissaoPrincipal;

	public ProfissaoSiricImpressao(ProfissaoSiric profissaoSiric) throws IllegalAccessException, InvocationTargetException {
		if(profissaoSiric != null){
			BeanUtils.copyProperties(this, profissaoSiric);
		}
	}

	public String getDescricaoProfissao() {
		return descricaoProfissao;
	}

	public void setDescricaoProfissao(String descricaoProfissao) {
		this.descricaoProfissao = descricaoProfissao;
	}

	public String getProfissao() {
		return profissao;
	}

	public void setProfissao(String profissao) {
		this.profissao = profissao;
	}

	public String getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(String dataInicio) {
		this.dataInicio = dataInicio;
	}

	public String getDataTermino() {
		return dataTermino;
	}

	public void setDataTermino(String dataTermino) {
		this.dataTermino = dataTermino;
	}

	public String getProfissaoPrincipal() {
		return profissaoPrincipal;
	}

	public void setProfissaoPrincipal(String profissaoPrincipal) {
		this.profissaoPrincipal = profissaoPrincipal;
	}

	@Override
	public String toString() {
		return "ProfissaoSiric [descricaoProfissao=" + descricaoProfissao
				+ ", profissao=" + profissao + ", dataInicio=" + dataInicio
				+ ", dataTermino=" + dataTermino + ", profissaoPrincipal="
				+ profissaoPrincipal + "]";
	}
	
	
	

}
