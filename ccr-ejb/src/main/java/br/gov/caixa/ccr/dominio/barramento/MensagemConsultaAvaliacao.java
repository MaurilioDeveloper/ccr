package br.gov.caixa.ccr.dominio.barramento;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import br.gov.caixa.ccr.dominio.barramento.siric.DadosConsultaAvaliacao;

@XmlRootElement(name="consulta_avaliacao_risco:SERVICO_ENTRADA")
public class MensagemConsultaAvaliacao extends MensagemBarramento {
  
	private static final long serialVersionUID = -1289042495758899080L;

	@XmlAttribute(name="xmlns:consulta_avaliacao_risco")  
	private String xmlns_consultaAvaliacaoRisco = "http://caixa.gov.br/sibar/consulta_avaliacao_risco";
	
	@XmlAttribute(name="xmlns:sibar_base")  
	private String xmlns_sibarBase = "http://caixa.gov.br/sibar";
	
	// pode ser por beneficio ou por cpf
	private DadosConsultaAvaliacao dados;
    
	@XmlElement(name="DADOS")  
	public DadosConsultaAvaliacao getDados(){
    	return this.dados;
	}
  
	public void setDados(DadosConsultaAvaliacao dados){
    	this.dados = dados;  
	}
}

