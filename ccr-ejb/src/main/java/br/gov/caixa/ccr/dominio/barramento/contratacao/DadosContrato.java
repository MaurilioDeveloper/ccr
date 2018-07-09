package br.gov.caixa.ccr.dominio.barramento.contratacao;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
//import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="DADOS")
/*@XmlType(propOrder={"servico", "canal", "aposentadoInvalidez", "dataNascimento", "valorMargem", "valorLiquido", 
					"valorPrestacao", "prazo", "taxaJuros", "usuario", "cpfCliente", "beneficio", "agenciaMovimento"})*/
public class DadosContrato implements Serializable {

	private static final long serialVersionUID = -8771547922070465619L;

	@XmlAttribute(name="xsi:type")  
	private String xsi_type = "nssicon:DADOS_ENTRADA_TYPE";
	
	@XmlElement(name="COD_SERVICO")
	private final String servico = "01";
	
	@XmlElement(name="COD_CANAL")
	private final String canal = "11";
	
	private String idSimulacao;
	private String matricula;
	private String cpfCliente;
	private String cpfAvalista;
	private String pv;
	private String tipoSituacao;
	private String situacao;
	private String modalidade;
	private String garantia;
	private String cocli;
	private String nome;
	private String dataNascimento;
	private String sexo;
	private String estadoCivil;
	private String carteira;
	private String ric;
	private String identidade;
	private String orgaoEmissor;
	private String ufEmissor;
	private String dataEmissao;
	private String beneficio;
	private String logradouro;
	private String numero;
	private String complemento;
	private String bairro;
	private String municipio;
	private String uf;
	private String cep;
	private String cepComplemento;
	private String dddTelefone;
	private String telefone;
	private String profissao;
	private String nacionalidade;
	private String contratoAPI;
	private String dataBaseCalc;
	private String banco;
	private String agencia;
	private String operacao;
	private String conta;
	private String dv;
	private String tipoCredito;
	
	@XmlElement(name="NUMERO_SIMULACAO")
	public String getIdSimulacao() {
		return idSimulacao;
	}
	
	public void setIdSimulacao(String idSimulacao) {
		this.idSimulacao = idSimulacao;
	}
	
	@XmlElement(name="NUMERO_SIMULACAO")
	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	
	@XmlElement(name="NUMERO_SIMULACAO")
	public String getCpfCliente() {
		return cpfCliente;
	}

	public void setCpfCliente(String cpfCliente) {
		this.cpfCliente = cpfCliente;
	}
	
	@XmlElement(name="NUMERO_SIMULACAO")
	public String getCpfAvalista() {
		return cpfAvalista;
	}

	public void setCpfAvalista(String cpfAvalista) {
		this.cpfAvalista = cpfAvalista;
	}
	
	@XmlElement(name="NUMERO_SIMULACAO")
	public String getPv() {
		return pv;
	}

	public void setPv(String pv) {
		this.pv = pv;
	}
	
	@XmlElement(name="NUMERO_SIMULACAO")
	public String getTipoSituacao() {
		return tipoSituacao;
	}

	public void setTipoSituacao(String tipoSituacao) {
		this.tipoSituacao = tipoSituacao;
	}
	
	@XmlElement(name="NUMERO_SIMULACAO")
	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
	
	@XmlElement(name="NUMERO_SIMULACAO")
	public String getModalidade() {
		return modalidade;
	}

	public void setModalidade(String modalidade) {
		this.modalidade = modalidade;
	}
	
	@XmlElement(name="NUMERO_SIMULACAO")
	public String getGarantia() {
		return garantia;
	}

	public void setGarantia(String garantia) {
		this.garantia = garantia;
	}
	
	@XmlElement(name="NUMERO_SIMULACAO")
	public String getCocli() {
		return cocli;
	}

	public void setCocli(String cocli) {
		this.cocli = cocli;
	}
	
	@XmlElement(name="NUMERO_SIMULACAO")
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@XmlElement(name="NUMERO_SIMULACAO")
	public String getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	
	@XmlElement(name="NUMERO_SIMULACAO")
	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	
	@XmlElement(name="NUMERO_SIMULACAO")
	public String getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(String estadoCivil) {
		this.estadoCivil = estadoCivil;
	}
	
	@XmlElement(name="NUMERO_SIMULACAO")
	public String getCarteira() {
		return carteira;
	}

	public void setCarteira(String carteira) {
		this.carteira = carteira;
	}
	
	@XmlElement(name="NUMERO_SIMULACAO")
	public String getRic() {
		return ric;
	}

	public void setRic(String ric) {
		this.ric = ric;
	}
	
	@XmlElement(name="NUMERO_SIMULACAO")
	public String getIdentidade() {
		return identidade;
	}

	public void setIdentidade(String identidade) {
		this.identidade = identidade;
	}
	
	@XmlElement(name="NUMERO_SIMULACAO")
	public String getOrgaoEmissor() {
		return orgaoEmissor;
	}

	public void setOrgaoEmissor(String orgaoEmissor) {
		this.orgaoEmissor = orgaoEmissor;
	}
	
	@XmlElement(name="NUMERO_SIMULACAO")
	public String getUfEmissor() {
		return ufEmissor;
	}

	public void setUfEmissor(String ufEmissor) {
		this.ufEmissor = ufEmissor;
	}
	
	@XmlElement(name="NUMERO_SIMULACAO")
	public String getDataEmissao() {
		return dataEmissao;
	}

	public void setDataEmissao(String dataEmissao) {
		this.dataEmissao = dataEmissao;
	}
	
	@XmlElement(name="NUMERO_SIMULACAO")
	public String getBeneficio() {
		return beneficio;
	}

	public void setBeneficio(String beneficio) {
		this.beneficio = beneficio;
	}
	
	@XmlElement(name="NUMERO_SIMULACAO")
	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}
	
	@XmlElement(name="NUMERO_SIMULACAO")
	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}
	
	@XmlElement(name="NUMERO_SIMULACAO")
	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
	
	@XmlElement(name="NUMERO_SIMULACAO")
	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	
	@XmlElement(name="NUMERO_SIMULACAO")
	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}
	
	@XmlElement(name="NUMERO_SIMULACAO")
	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}
	
	@XmlElement(name="NUMERO_SIMULACAO")
	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}
	
	@XmlElement(name="NUMERO_SIMULACAO")
	public String getCepComplemento() {
		return cepComplemento;
	}

	public void setCepComplemento(String cepComplemento) {
		this.cepComplemento = cepComplemento;
	}
	
	@XmlElement(name="NUMERO_SIMULACAO")
	public String getDddTelefone() {
		return dddTelefone;
	}

	public void setDddTelefone(String dddTelefone) {
		this.dddTelefone = dddTelefone;
	}
	
	@XmlElement(name="NUMERO_SIMULACAO")
	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
	@XmlElement(name="NUMERO_SIMULACAO")
	public String getProfissao() {
		return profissao;
	}

	public void setProfissao(String profissao) {
		this.profissao = profissao;
	}
	
	@XmlElement(name="NUMERO_SIMULACAO")
	public String getNacionalidade() {
		return nacionalidade;
	}

	public void setNacionalidade(String nacionalidade) {
		this.nacionalidade = nacionalidade;
	}
	
	@XmlElement(name="NUMERO_SIMULACAO")
	public String getContratoAPI() {
		return contratoAPI;
	}

	public void setContratoAPI(String contratoAPI) {
		this.contratoAPI = contratoAPI;
	}
	
	@XmlElement(name="NUMERO_SIMULACAO")
	public String getDataBaseCalc() {
		return dataBaseCalc;
	}

	public void setDataBaseCalc(String dataBaseCalc) {
		this.dataBaseCalc = dataBaseCalc;
	}
	
	@XmlElement(name="NUMERO_SIMULACAO")
	public String getBanco() {
		return banco;
	}

	public void setBanco(String banco) {
		this.banco = banco;
	}
	
	@XmlElement(name="NUMERO_SIMULACAO")
	public String getAgencia() {
		return agencia;
	}

	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}
	
	@XmlElement(name="NUMERO_SIMULACAO")
	public String getOperacao() {
		return operacao;
	}

	public void setOperacao(String operacao) {
		this.operacao = operacao;
	}
	
	@XmlElement(name="NUMERO_SIMULACAO")
	public String getConta() {
		return conta;
	}

	public void setConta(String conta) {
		this.conta = conta;
	}
	
	@XmlElement(name="NUMERO_SIMULACAO")
	public String getDv() {
		return dv;
	}

	public void setDv(String dv) {
		this.dv = dv;
	}
	
	@XmlElement(name="NUMERO_SIMULACAO")
	public String getTipoCredito() {
		return tipoCredito;
	}

	public void setTipoCredito(String tipoCredito) {
		this.tipoCredito = tipoCredito;
	}	
}
