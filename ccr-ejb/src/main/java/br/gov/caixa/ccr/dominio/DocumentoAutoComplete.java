package br.gov.caixa.ccr.dominio;

public class DocumentoAutoComplete extends Retorno {

	private String nomeModeloDocumento;

	public DocumentoAutoComplete() {}

	public String getNomeModeloDocumento() {
		return nomeModeloDocumento;
	}

	public void setNomeModeloDocumento(String nomeModeloDocumento) {
		this.nomeModeloDocumento = nomeModeloDocumento;
	}	
}
