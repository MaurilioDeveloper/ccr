package br.gov.caixa.ccr.dominio;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

import javax.xml.bind.annotation.XmlElement;

import org.apache.commons.beanutils.BeanUtils;

import br.gov.caixa.arqrefservices.dominio.sicli.Identidade;

public class IdentidadeImpressao  implements Serializable{

	
	
	private static final long serialVersionUID = 7069930560707898365L;

	private String ocorrencia;
	
	private String descricaoOrgaoEmissor;
	
	private String siglaOrgaoEmissor;
	
	private DocumentoImpressao documento;
	
	private String flagDocumentoIdentificacaoPrincipal;
	
	private String dataFimValidade;
	
	private String tipoDocumento;
	
	private String subTipoDocumento;


	public IdentidadeImpressao(Identidade identidade) throws IllegalAccessException, InvocationTargetException {
		if(identidade != null){
			this.documento = new DocumentoImpressao(identidade.getDocumento());
			this.ocorrencia = identidade.getOcorrencia();
			this.descricaoOrgaoEmissor = identidade.getDescricaoOrgaoEmissor();
			this.siglaOrgaoEmissor = identidade.getSiglaOrgaoEmissor();
			this.flagDocumentoIdentificacaoPrincipal = identidade.getFlagDocumentoIdentificacaoPrincipal();
			this.dataFimValidade = identidade.getDataFimValidade();
			this.tipoDocumento= identidade.getTipoDocumento();
			this.subTipoDocumento = identidade.getTipoDocumento();
		}
	}

	public String getOcorrencia() {
		return ocorrencia;
	}

	public void setOcorrencia(String ocorrencia) {
		this.ocorrencia = ocorrencia;
	}


	public String getDescricaoOrgaoEmissor() {
		return descricaoOrgaoEmissor;
	}

	public void setDescricaoOrgaoEmissor(String descricaoOrgaoEmissor) {
		this.descricaoOrgaoEmissor = descricaoOrgaoEmissor;
	}

	@XmlElement(name="SIGLA_ORG_EMISSOR")
	public String getSiglaOrgaoEmissor() {
		return siglaOrgaoEmissor;
	}

	public void setSiglaOrgaoEmissor(String siglaOrgaoEmissor) {
		this.siglaOrgaoEmissor = siglaOrgaoEmissor;
	}



	
	public String getFlagDocumentoIdentificacaoPrincipal() {
		return flagDocumentoIdentificacaoPrincipal;
	}

	public void setFlagDocumentoIdentificacaoPrincipal(
			String flagDocumentoIdentificacaoPrincipal) {
		this.flagDocumentoIdentificacaoPrincipal = flagDocumentoIdentificacaoPrincipal;
	}


	public String getDataFimValidade() {
		return dataFimValidade;
	}

	public void setDataFimValidade(String dataFimValidade) {
		this.dataFimValidade = dataFimValidade;
	}


	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getSubTipoDocumento() {
		return subTipoDocumento;
	}

	public void setSubTipoDocumento(String subTipoDocumento) {
		this.subTipoDocumento = subTipoDocumento;
	}
	
	

	public DocumentoImpressao getDocumento() {
		return documento;
	}

	public void setDocumento(DocumentoImpressao documento) {
		this.documento = documento;
	}

	@Override
	public String toString() {
		return "Identidade [ocorrencia=" + ocorrencia
				+ ", descricaoOrgaoEmissor=" + descricaoOrgaoEmissor
				+ ", siglaOrgaoEmissor=" + siglaOrgaoEmissor + ", documento="
				+ documento + ", flagDocumentoIdentificacaoPrincipal="
				+ flagDocumentoIdentificacaoPrincipal + ", dataFimValidade="
				+ dataFimValidade + ", tipoDocumento=" + tipoDocumento
				+ ", subTipoDocumento=" + subTipoDocumento + "]";
	}
	
	
	
	
	
	
	
	

}
