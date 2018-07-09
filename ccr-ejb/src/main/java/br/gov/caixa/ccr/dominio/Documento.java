package br.gov.caixa.ccr.dominio;

import java.util.Date;
import java.util.List;

public class Documento extends Retorno {

	private Long id;
	private String inicioVigencia;
	private String fimVigencia;
	private String tipoDocumento;
	private String nomeModeloDocumento;
	private String nomeUsuario;
	private String data;
	private StringBuilder templateHtml;
	private String camposDinamicos;
	private Long nrContrato;
	private boolean deletable;
	private boolean updatable;
	private CamposRetornadosCCR dadosSicli;
	private List<String> contratosImpressao;
	private Long nrTipoDocumento;
	private Date dtInicioVigencia;
	private Date dtFimVigencia;
	private Date dtInclusao;
	private String icModeloTestemunha;
	
	
	public Documento(Long id, 
			String inicioVigencia, 
			String fimVigencia,
			String tipoDocumento, 
			String nomeModeloDocumento, 
			String nomeUsuario, String data, 
			StringBuilder templateHtml, 
			String camposDinamicos,
			boolean updatable,
			boolean deletable,
			Long nrTipoDocumento,
			Date dtInicioVigencia, 
			Date dtFimVigencia,
			Date dtInclusao,
			String icModeloTestemunha) {
		this.id = id;
		this.inicioVigencia = inicioVigencia;
		this.fimVigencia = fimVigencia;
		this.tipoDocumento = tipoDocumento;
		this.nomeModeloDocumento = nomeModeloDocumento;
		this.nomeUsuario = nomeUsuario;
		this.data = data;
		this.templateHtml = templateHtml;
		this.camposDinamicos = camposDinamicos;
		this.updatable = updatable;
		this.deletable = deletable;
		this.nrTipoDocumento = nrTipoDocumento;
		this.dtInicioVigencia = dtInicioVigencia;
		this.dtFimVigencia = dtFimVigencia;
		this.dtInclusao = dtInclusao;
		this.icModeloTestemunha = icModeloTestemunha;
	}
	
	public Documento() {
		
	}
	
	
	public CamposRetornadosCCR getDadosSicli() {
		return dadosSicli;
	}

	public void setDadosSicli(CamposRetornadosCCR dadosSicli) {
		this.dadosSicli = dadosSicli;
	}

	public Documento(Long codigo, String mensagem, String tipo) {
		super.setCodigoRetorno(codigo);
		super.setMensagemRetorno(mensagem);
		super.setTipoRetorno(tipo);
	}
	
	public Documento(Long codigo, String mensagem, String tipo, String idMsg) {
		super.setCodigoRetorno(codigo);
		super.setMensagemRetorno(mensagem);
		super.setTipoRetorno(tipo);
		super.setIdMsg(idMsg);
	}
	

	public StringBuilder getTemplateHtml() {
		return templateHtml;
	}

	public void setTemplateHtml(StringBuilder templateHtml) {
		this.templateHtml = templateHtml;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getInicioVigencia() {
		return inicioVigencia;
	}

	public void setInicioVigencia(String inicioVigencia) {
		this.inicioVigencia = inicioVigencia;
	}

	public String getFimVigencia() {
		return fimVigencia;
	}

	public void setFimVigencia(String fimVigencia) {
		this.fimVigencia = fimVigencia;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getNomeModeloDocumento() {
		return nomeModeloDocumento;
	}

	public void setNomeModeloDocumento(String nomeModeloDocumento) {
		this.nomeModeloDocumento = nomeModeloDocumento;
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getCamposDinamicos() {
		return camposDinamicos;
	}

	public void setCamposDinamicos(String camposDinamicos) {
		this.camposDinamicos = camposDinamicos;
	}

	public Long getNrContrato() {
		return nrContrato;
	}

	public void setNrContrato(Long nrContrato) {
		this.nrContrato = nrContrato;
	}

	public boolean isDeletable() {
		return deletable;
	}

	public void setDeletable(boolean deletable) {
		this.deletable = deletable;
	}

	public boolean isUpdatable() {
		return updatable;
	}

	public void setUpdatable(boolean updatable) {
		this.updatable = updatable;
	}

	public List<String> getContratosImpressao() {
		return contratosImpressao;
	}

	public void setContratosImpressao(List<String> contratosImpressao) {
		this.contratosImpressao = contratosImpressao;
	}

	public Long getNrTipoDocumento() {
		return nrTipoDocumento;
	}

	public void setNrTipoDocumento(Long nrTipoDocumento) {
		this.nrTipoDocumento = nrTipoDocumento;
	}

	public Date getDtInicioVigencia() {
		return dtInicioVigencia;
	}

	public void setDtInicioVigencia(Date dtInicioVigencia) {
		this.dtInicioVigencia = dtInicioVigencia;
	}

	public Date getDtFimVigencia() {
		return dtFimVigencia;
	}

	public void setDtFimVigencia(Date dtFimVigencia) {
		this.dtFimVigencia = dtFimVigencia;
	}

	public Date getDtInclusao() {
		return dtInclusao;
	}

	public void setDtInclusao(Date dtInclusao) {
		this.dtInclusao = dtInclusao;
	}

	public String getIcModeloTestemunha() {
		return icModeloTestemunha;
	}

	public void setIcModeloTestemunha(String icModeloTestemunha) {
		this.icModeloTestemunha = icModeloTestemunha;
	}

	
}
