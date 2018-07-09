package br.gov.caixa.ccr.dominio.barramento;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import br.gov.caixa.ccr.dominio.barramento.simulacao.DadosSimulacaoContrato;

@XmlRootElement(name="nssicon:SERVICO_ENTRADA")
public class MensagemSimulacaoSICCR extends MensagemBarramento {
  
	private static final long serialVersionUID = 1L;
    
	@XmlAttribute(name="xmlns:credito_base")  
	private String xmlns_tns = "http://caixa.gov.br/sifec/biblioteca";
  
	@XmlAttribute(name="xmlns:nssicon")
	private String xmlns_nssicon = "http://caixa.gov.br/siccr/consignado/simulacontratacao";
	
	@XmlAttribute(name="xmlns:sibar_base")  
	private String xmlns_sibar_base = "http://caixa.gov.br/sibar";
  
	@XmlAttribute(name="xmlns:xsi")
	private String xmlns_xsi = "http://www.w3.org/2001/XMLSchema-instance"; 
    
	@XmlAttribute(name="xsi:schemaLocation")
	private String xsi_location = "http://caixa.gov.br/siccr/consignado/simulacontratacao SIMULA_CONTRATACAO.xsd";
		
	private DadosSimulacaoContrato dados;
    
	@XmlElement(name="DADOS")  
	public DadosSimulacaoContrato getDados(){
    	return this.dados;
	}
  
	public void setDados(DadosSimulacaoContrato dados){
    	this.dados = dados;  
	}
}

