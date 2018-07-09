package br.gov.caixa.ccr.dominio.barramento.siric;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="RELACIONAMENTOS")
@XmlType(propOrder={"pessoa", "pergunta", "dataInicio", "dataFim"})
public class Relacionamento implements Serializable {

	private static final long serialVersionUID = -6988093427380390313L;

	private PessoaReciproca pessoa;

	public Relacionamento() {
		this.pessoa = new PessoaReciproca();
	}
	
	@XmlElement(name = "CODIGO_PERGUNTA")
	private String pergunta = "259";
	
	private String dataInicio;	
	private String dataFim;

	@XmlElement(name = "PESSOA_RECIPROCA")
	public PessoaReciproca getPessoa() {
		return pessoa;
	}

	public void setPessoa(PessoaReciproca pessoa) {
		this.pessoa = pessoa;
	}

	@XmlElement(name = "DATA_INICIO")
	public String getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(String dataInicio) {
		this.dataInicio = dataInicio;
	}

	@XmlElement(name = "DATA_FIM")
	public String getDataFim() {
		return dataFim;
	}

	public void setDataFim(String dataFim) {
		this.dataFim = dataFim;
	}
	
}
