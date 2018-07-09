package br.gov.caixa.ccr.negocio;

import java.util.List;

import javax.ejb.Local;

import br.gov.caixa.arqrefservices.dominio.sicli.CamposRetornados;
import br.gov.caixa.ccr.dominio.Documento;
import br.gov.caixa.ccr.dominio.DocumentoAutoComplete;
import br.gov.caixa.ccr.dominio.Impressao;
import br.gov.caixa.ccr.dominio.Retorno;


@Local
public interface IDocumentoBean {

	List<Documento> listaDocumento(Documento documento ) throws Exception;
	Retorno salvar(Documento documento, String usuario) throws Exception;
	Retorno excluir(Documento documento, String usuario) throws Exception;
	String imprimeDocumento(Documento documento, CamposRetornados sicli)
			throws Exception;
	List<DocumentoAutoComplete> consultarAutocomplete(String desc) throws Exception;
	String imprimeDocumento(Long idDocumento, Long idContrato, CamposRetornados sicli)
			throws Exception;
	List<String> imprimeDocumentoContrato(Impressao impressao) throws Exception;
	List<Documento> listaDocumentoRetorno(Documento documento) throws Exception;
	String buscarTipoDocumentoImpressaoCCR() throws Exception;
}


