package br.gov.caixa.ccr.dominio.apx;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import br.gov.caixa.arqrefservices.dominio.Empregado;
import br.gov.caixa.arqrefservices.dominio.siric.Avaliacao;

/**
 * Classe de Transicao que trata o envio e retorno 
 * do COBOL para Consulta/Contratacao de Consignacao.
 * 
 * Programa COBOL: APXPO031/APXPO011
 * 
 * @author c110503
 *
 */
@XmlRootElement(name="CONSULTA-CONTRATOS-RENOVACAO-ENTRADA")
public class ContratoConsignadoTO extends AbstratoTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1131384620893983235L;

	private Usuario empregado;
	
	private ClientePessoaFisica clientePessoaFisica;
	
	private Avaliacao avaliacaoRisco;
	
	private ContratoConsignado contratoConsignado;
	
	private ContratoConsignado contratoSeguroConsignado;
	
	private Margem margem;
	
	private List<ContratoConsignado> listaContratoConsignado;
	
	private String indicativoRetornoCancelamento;
	
	private String codigoAvaliacao;
	
	private String codigoConceito;
	
	private String urlComprovanteConsignado;
		
	private Boolean exigeIof;
	
	@XmlElement(name = "USUARIO")
	public Usuario getEmpregado() {
		return empregado;
	}
	public void setEmpregado(Usuario empregado) {
		this.empregado = empregado;
	}

	@XmlElement(name = "CLIENTE")
	public ClientePessoaFisica getClientePessoaFisica() {
		return clientePessoaFisica;
	}

	public void setClientePessoaFisica(ClientePessoaFisica clientePessoaFisica) {
		this.clientePessoaFisica = clientePessoaFisica;
	}	

	@XmlElement(name = "AVALIACAO-RISCO")
	public Avaliacao getAvaliacaoRisco() {
		return avaliacaoRisco;
	}
	public void setAvaliacaoRisco(Avaliacao avaliacaoRisco) {
		this.avaliacaoRisco = avaliacaoRisco;
	}
	@XmlElement(name = "CONTRATO")
	public ContratoConsignado getContratoConsignado() {
		return contratoConsignado;
	}

	public void setContratoConsignado(ContratoConsignado contratoConsignado) {
		this.contratoConsignado = contratoConsignado;
	}
	
	@XmlElement(name = "CODIGO_CONCEITO")
	public String getCodigoConceito() {
		return codigoConceito;
	}
	
	public void setCodigoConceito(String codigoConceito) {
		this.codigoConceito = codigoConceito;
	}
	
	
	@XmlElement(name = "CODIGO_AVALIACAO")
	public String getCodigoAvaliacao() {
		return codigoAvaliacao;
	}
	
	public void setCodigoAvaliacao(String codigoAvaliacao) {
		this.codigoAvaliacao = codigoAvaliacao;
	}
	
	@XmlElement(name = "CONTRATO-SEGURO")
	public ContratoConsignado getContratoSeguroConsignado() {
		return contratoSeguroConsignado;
	}
	public void setContratoSeguroConsignado(
			ContratoConsignado contratoSeguroConsignado) {
		this.contratoSeguroConsignado = contratoSeguroConsignado;
	}
	
	@XmlElement(name = "MARGEM")
	public Margem getMargem() {
		return margem;
	}
	public void setMargem(Margem margem) {
		this.margem = margem;
	}
	
	@XmlElementWrapper(name="CONTRATOS")
	@XmlElement(name = "CONTRATO")
	public List<ContratoConsignado> getListaContratoConsignado() {
		if(listaContratoConsignado != null){
			listaContratoConsignado = this.limparListaContratoConsignado(listaContratoConsignado);
		}
		return listaContratoConsignado;
	}
	
	@XmlAttribute(name = "IC-RETORNO-OK")
	public String getIndicativoRetornoCancelamento() {
		return indicativoRetornoCancelamento;
	}
	public void setIndicativoRetornoCancelamento(
			String indicativoRetornoCancelamento) {
		this.indicativoRetornoCancelamento = indicativoRetornoCancelamento;
	}
	
	
	public void setListaContratoConsignado(
			List<ContratoConsignado> listaContratoConsignado) {
		this.listaContratoConsignado = listaContratoConsignado;
	}
	
	private List<ContratoConsignado> limparListaContratoConsignado(List<ContratoConsignado>  listaContratoConsignado){
		List<ContratoConsignado>  listaContratoConsignadoTmp = new ArrayList<ContratoConsignado>();
		for(ContratoConsignado contrato : listaContratoConsignado){
			System.out.println(!contrato.getCodigoContrato().isEmpty());
			System.out.println(contrato.getCodigoContrato().trim().isEmpty());
			if(contrato.getCodigoContrato() != null && !contrato.getCodigoContrato().trim().isEmpty()){
				listaContratoConsignadoTmp.add(contrato);
			}
			
		}
		return listaContratoConsignadoTmp;
	}

	@Override
	public String toString() {
		return "ContratoConsignadoTO [empregado=" + empregado
				+ ", clientePessoaFisica=" + clientePessoaFisica
				+ ", avaliacaoRisco=" + avaliacaoRisco
				+ ", contratoConsignado=" + contratoConsignado
				+ ", contratoSeguroConsignado=" + contratoSeguroConsignado
				+ ", margem=" + margem + ", codigoAvaliacao="
				+ ", codigoAvaliacao=" + codigoAvaliacao + ", listaContratoConsignado="
				+ listaContratoConsignado + ", indicativoRetornoCancelamento="
				+ indicativoRetornoCancelamento + "]";
	}
	public String getUrlComprovanteConsignado() {
		return urlComprovanteConsignado;
	}
	public void setUrlComprovanteConsignado(String urlComprovanteConsignado) {
		this.urlComprovanteConsignado = urlComprovanteConsignado;
	}
	
	public Boolean getExigeIof() {
		return exigeIof;
	}
	
	public void setExigeIof(Boolean exigeIof) {
		this.exigeIof = exigeIof;
	}
	
	
}
