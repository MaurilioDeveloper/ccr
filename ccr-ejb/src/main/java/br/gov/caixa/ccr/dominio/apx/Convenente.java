package br.gov.caixa.ccr.dominio.apx;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import br.gov.caixa.arqrefcore.cobol.conversor.anotacao.Tamanho;
import br.gov.caixa.arqrefservices.dominio.econsig.consultarParametros.ConsultarParametrosDadosRET;

/**
 * Classe de dominio
 * @author c110503
 *
 */
@XmlRootElement
@XmlType(propOrder = { "descricaoConvenente", "codigoConvenente","cnpj","listaConvenio","matricula" })
public class Convenente implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2977395024559988788L;
	
	@Tamanho(tamanho = 14)
	private String cnpj;
	
	@Tamanho(tamanho = 05)
	private String codigoConvenente;
	
	@Tamanho(tamanho = 35)
	private String descricaoConvenente;
	
	private List<Convenio> listaConvenio;
	
	// Setado fixo
	private String operacao = "1";
	
	private String matricula;
	
	private String senhaMatricula;
	
	private String codigoServico;
	
	private ConsultarParametrosDadosRET dadosParametros;
	
	Boolean consultarParametros = true;
	
	@XmlAttribute(name = "CNPJ") 
	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	@XmlAttribute(name = "CO-CONVENENTE") 
	public String getCodigoConvenente() {
		return codigoConvenente;
	}

	public void setCodigoConvenente(String codigoConvenente) {
		this.codigoConvenente = codigoConvenente;
	}

	@XmlElementWrapper(name="CONVENIOS")
	@XmlElement(name = "CONVENIO")
	public List<Convenio> getListaConvenio() {
		return listaConvenio;
	}

	public void setListaConvenio(List<Convenio> listaConvenio) {
		this.listaConvenio = listaConvenio;
	}

	@XmlAttribute(name = "nome") 
	public String getDescricaoConvenente() {
		return descricaoConvenente;
	}

	public void setDescricaoConvenente(String descricaoConvenente) {
		this.descricaoConvenente = descricaoConvenente;
	}
	
	@XmlAttribute(name = "CO-OPERACAO")
	public String getOperacao() {
		return operacao;
	}

	public void setOperacao(String operacao) {
		this.operacao = operacao;
	}

	@Override
	public String toString() {
		return "Convenente [cnpj=" + cnpj + ", codigoConvenente="
				+ codigoConvenente + ", descricaoConvenente="
				+ descricaoConvenente + ", operacao=" + operacao
				+ ", listaConvenio=" + listaConvenio + "]";
	}

	@XmlAttribute(name = "CO-MATRICULA")
	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	@XmlTransient
	public String getSenhaMatricula() {
		return senhaMatricula;
	}

	public void setSenhaMatricula(String senhaMatricula) {
		this.senhaMatricula = senhaMatricula;
	}

	@XmlTransient
	public ConsultarParametrosDadosRET getDadosParametros() {
		return dadosParametros;
	}

	public void setDadosParametros(ConsultarParametrosDadosRET dadosParametros) {
		this.dadosParametros = dadosParametros;
	}

	@XmlTransient
	public String getCodigoServico() {
		return codigoServico;
	}

	public void setCodigoServico(String codigoServico) {
		this.codigoServico = codigoServico;
	}
	
	@XmlTransient
	public Boolean isConsultarParametros() {
		return consultarParametros;
	}

	public void setConsultarParametros(Boolean consultarParametros) {
		this.consultarParametros = consultarParametros;
	}

}
