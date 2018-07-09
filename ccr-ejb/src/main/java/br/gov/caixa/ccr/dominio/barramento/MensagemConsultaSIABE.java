package br.gov.caixa.ccr.dominio.barramento;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import br.gov.caixa.ccr.dominio.barramento.siabe.DadosConsultaBeneficio;

@XmlRootElement(name="consultabeneficiarioinss:SERVICO_ENTRADA")
public class MensagemConsultaSIABE extends MensagemBarramento {
  
	private static final long serialVersionUID = -1289042495358899080L;

	@XmlAttribute(name="xmlns:consultabeneficiarioinss")  
	private String xmlns_consbeneficiario = "http://caixa.gov.br/sibar/consulta_beneficiario_inss";
	
	@XmlAttribute(name="xmlns:sibar_base")  
	private String xmlns_sibar_base = "http://caixa.gov.br/sibar";
	
	// pode ser por beneficio ou por cpf
	private DadosConsultaBeneficio dados;
    
	@XmlElement(name="DADOS")  
	public DadosConsultaBeneficio getDados(){
    	return this.dados;
	}
  
	public void setDados(DadosConsultaBeneficio dados){
    	this.dados = dados;  
	}
}

