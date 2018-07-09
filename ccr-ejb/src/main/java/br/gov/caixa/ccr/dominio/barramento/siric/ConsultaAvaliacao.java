package br.gov.caixa.ccr.dominio.barramento.siric;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="CONSULTA")
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
@XmlType(propOrder={"avaliacoesValidas"})
public class ConsultaAvaliacao implements Serializable {

	private static final long serialVersionUID = -951661454351674582L;	
	
	private List<AvaliacaoValida> avaliacoesValidas;

	public ConsultaAvaliacao() {
		
	}
	
	public ConsultaAvaliacao(String cpf) {
		this.avaliacoesValidas.add(new AvaliacaoValida(cpf));
	}
	
	@XmlElement(name="AVALIACOES_VALIDAS")
	public List<AvaliacaoValida> getAvaliacoesValidas() {
		return avaliacoesValidas;
	}

	public void setAvaliacoesValidas(List<AvaliacaoValida> avaliacoesValidas) {
		this.avaliacoesValidas = avaliacoesValidas;
	}
	
}
