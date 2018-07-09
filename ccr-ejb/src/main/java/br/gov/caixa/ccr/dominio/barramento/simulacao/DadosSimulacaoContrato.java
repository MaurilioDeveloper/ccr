package br.gov.caixa.ccr.dominio.barramento.simulacao;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="DADOS")
@XmlType(propOrder={"servico", "canal", "aposentadoInvalidez", "dataNascimento", "valorMargem", "valorLiquido", "valorPrestacao", 
					"prazo", "taxaJuros", "usuario", "cpfCliente", "beneficio", "agenciaMovimento", "convenio", "grupo"})
public class DadosSimulacaoContrato implements Serializable {

	private static final long serialVersionUID = 7147225298294325192L;
	
	@XmlAttribute(name="xsi:type")  
	private String xsi_type = "nssicon:DADOS_ENTRADA_TYPE";
	
	@XmlElement(name="COD_SERVICO")
	private final String servico = "01";
	
	@XmlElement(name="COD_CANAL")
	private final String canal = "11";
	
	private String convenio;
	private String grupo;
	private String aposentadoInvalidez;
	private String dataNascimento;
	private String valorMargem;
	private String valorLiquido;
	private String valorPrestacao;
	private String prazo;
	private String taxaJuros;
	private String usuario;
	private String cpfCliente;
	private String beneficio;
	private String agenciaMovimento;
	
	@XmlElement(name="COD_CONVENIO")
	public String getConvenio() {
		return convenio;
	}

	public void setConvenio(String convenio) {
		this.convenio = convenio;
	}

	@XmlElement(name="COD_GRUPO")
	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	@XmlElement(name="IND_APOSENTADO_INVALIDEZ")
	public String getAposentadoInvalidez() {
		return aposentadoInvalidez;
	}
	
	public void setAposentadoInvalidez(String aposentadoInvalidez) {
		this.aposentadoInvalidez = aposentadoInvalidez;
	}

	@XmlElement(name="DATA_NASCIMENTO")
	public String getDataNascimento() {
		return dataNascimento;
	}
	
	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	@XmlElement(name="VALOR_MARGEM")
	public String getValorMargem() {
		return valorMargem;
	}
	
	public void setValorMargem(String valorMargem) {
		this.valorMargem = valorMargem;
	}

	@XmlElement(name="VAL_LIQUIDO")
	public String getValorLiquido() {
		return valorLiquido;
	}
	
	public void setValorLiquido(String valorLiquido) {
		this.valorLiquido = valorLiquido;
	}

	@XmlElement(name="VAL_PRESTACAO")
	public String getValorPrestacao() {
		return valorPrestacao;
	}
	
	public void setValorPrestacao(String valorPrestacao) {
		this.valorPrestacao = valorPrestacao;
	}

	@XmlElement(name="PRAZO")
	public String getPrazo() {
		return prazo;
	}
	
	public void setPrazo(String prazo) {
		this.prazo = prazo;
	}

	@XmlElement(name="TAXA_JUROS")
	public String getTaxaJuros() {
		return taxaJuros;
	}
	
	public void setTaxaJuros(String taxaJuros) {
		this.taxaJuros = taxaJuros;
	}

	@XmlElement(name="MATRICULA_USUARIO")
	public String getUsuario() {
		return usuario;
	}
	
	public void setUsuario(String usuario) {
		this.usuario = usuario.toUpperCase().charAt(0) == 'C' ? usuario : "C" + usuario;
	}

	@XmlElement(name="CPF_CLIENTE")
	public String getCpfCliente() {
		return cpfCliente;
	}
	
	public void setCpfCliente(String cpfCliente) {
		this.cpfCliente = cpfCliente;
	}

	@XmlElement(name="NUMERO_BENEFICIO")
	public String getBeneficio() {
		return beneficio;
	}
	
	public void setBeneficio(String beneficio) {
		this.beneficio = beneficio;
	}

	@XmlElement(name="AGENCIA_MOVIMENTO")
	public String getAgenciaMovimento() {
		return agenciaMovimento;
	}
	
	public void setAgenciaMovimento(String agenciaMovimento) {
		this.agenciaMovimento = agenciaMovimento;
	}
	
}
