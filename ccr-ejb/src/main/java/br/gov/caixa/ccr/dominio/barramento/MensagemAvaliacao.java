package br.gov.caixa.ccr.dominio.barramento;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import br.gov.caixa.ccr.dominio.barramento.siric.DadosAvaliacao;

@XmlRootElement(name="avaliacao_risco_credito:SERVICO_ENTRADA")
public class MensagemAvaliacao extends MensagemBarramento {
  
	private static final long serialVersionUID = -1289042495758899650L;

	@XmlAttribute(name="xmlns:avaliacao_risco_credito")  
	private String xmlns_avaliacaoRiscoCredito = "http://caixa.gov.br/sibar/avaliacao_risco_credito";

	@XmlAttribute(name="xmlns:sibar_base")  
	private String xmlns_sibarBase = "http://caixa.gov.br/sibar";
	
	// pode ser por beneficio ou por cpf
	private DadosAvaliacao dados;
    
	@XmlElement(name="DADOS")  
	public DadosAvaliacao getDados(){
    	return this.dados;
	}
  
	public void setDados(DadosAvaliacao dados){
    	this.dados = dados;  
	}
}

