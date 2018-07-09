package br.gov.caixa.ccr.dominio;

public class ContratoCompleto extends Retorno {
	
	private Long numero;	
	private Situacao situacao;	
	private int convenio;	
	private Modalidade modalidade;	
	private Garantia garantia;	
	private Long cpf;	
	private Long cocli;	
	private String nome;	
	private String dataNascimento;	
	private String sexo;	
	private int estadoCivil;
	private String carteira;	
	private Long ric;	
	private String rg;	
	private String orgaoEmissor;	
	private String ufEmissor;	
	private String dataEmissao;	
	private int beneficio;	
	private String logradouro;	
	private Long numeroEndereco;	
	private String complemento;	
	private String bairro;	
	private String municipio;	
	private String uf;	
	private int cep;	
	private int cepComplemento;	
	private short ddd;	
	private Long telefone;	
	private Long profissao;	
	private int nacionalidade;	
	private Long contratoApx;	
	private Double valorLiquido;	
	private Double valorPrestacao;	
	private int prazo;	
	private Double valor;	
	private String dataLiberacao;	
	private Double valorIOF;	
	private Double taxaJuro;	
	private Double cetMensal;	
	private Double cetAnual;	
	private String dataBase;	
	private String vencimentoPrimeiraPrestacao;	
	private int quantidadeDiasAcerto;	
	private Double valorJuroAcerto;	
	private String comSeguro;	
	private String valorSeguro;	
	private Double taxaSeguro;	
	private int banco;	
	private int agencia;	
	private int operacao;	
	private Long conta;	
	private short digitoVerificador;
	private String tipoCredito;
	private String data;	
	private Double aliquotaBasicaIOF;	
	private Double aliquotaComplementar;	
	private Long avaliacao;

	public Long getNumero() {
		return numero;
	}

	public void setNumero(Long numero) {
		this.numero = numero;
	}

	public Situacao getSituacao() {
		return situacao;
	}

	public void setSituacao(Situacao situacao) {
		this.situacao = situacao;
	}

	public int getConvenio() {
		return convenio;
	}

	public void setConvenio(int convenio) {
		this.convenio = convenio;
	}

	public Modalidade getModalidade() {
		return modalidade;
	}

	public void setModalidade(Modalidade modalidade) {
		this.modalidade = modalidade;
	}

	public Garantia getGarantia() {
		return garantia;
	}

	public void setGarantia(Garantia garantia) {
		this.garantia = garantia;
	}

	public Long getCpf() {
		return cpf;
	}

	public void setCpf(Long cpf) {
		this.cpf = cpf;
	}

	public Long getCocli() {
		return cocli;
	}

	public void setCocli(Long cocli) {
		this.cocli = cocli;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public int getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(int estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public String getCarteira() {
		return carteira;
	}

	public void setCarteira(String carteira) {
		this.carteira = carteira;
	}

	public Long getRic() {
		return ric;
	}

	public void setRic(Long ric) {
		this.ric = ric;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getOrgaoEmissor() {
		return orgaoEmissor;
	}

	public void setOrgaoEmissor(String orgaoEmissor) {
		this.orgaoEmissor = orgaoEmissor;
	}

	public String getUfEmissor() {
		return ufEmissor;
	}

	public void setUfEmissor(String ufEmissor) {
		this.ufEmissor = ufEmissor;
	}

	public String getDataEmissao() {
		return dataEmissao;
	}

	public void setDataEmissao(String dataEmissao) {
		this.dataEmissao = dataEmissao;
	}

	public int getBeneficio() {
		return beneficio;
	}

	public void setBeneficio(int beneficio) {
		this.beneficio = beneficio;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public Long getNumeroEndereco() {
		return numeroEndereco;
	}

	public void setNumeroEndereco(Long numeroEndereco) {
		this.numeroEndereco = numeroEndereco;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public int getCep() {
		return cep;
	}

	public void setCep(int cep) {
		this.cep = cep;
	}

	public int getCepComplemento() {
		return cepComplemento;
	}

	public void setCepComplemento(int cepComplemento) {
		this.cepComplemento = cepComplemento;
	}

	public short getDdd() {
		return ddd;
	}

	public void setDdd(short ddd) {
		this.ddd = ddd;
	}

	public Long getTelefone() {
		return telefone;
	}

	public void setTelefone(Long telefone) {
		this.telefone = telefone;
	}

	public Long getProfissao() {
		return profissao;
	}

	public void setProfissao(Long profissao) {
		this.profissao = profissao;
	}

	public int getNacionalidade() {
		return nacionalidade;
	}

	public void setNacionalidade(int nacionalidade) {
		this.nacionalidade = nacionalidade;
	}

	public Long getContratoApx() {
		return contratoApx;
	}

	public void setContratoApx(Long contratoApx) {
		this.contratoApx = contratoApx;
	}

	public Double getValorLiquido() {
		return valorLiquido;
	}

	public void setValorLiquido(Double valorLiquido) {
		this.valorLiquido = valorLiquido;
	}

	public Double getValorPrestacao() {
		return valorPrestacao;
	}

	public void setValorPrestacao(Double valorPrestacao) {
		this.valorPrestacao = valorPrestacao;
	}

	public int getPrazo() {
		return prazo;
	}

	public void setPrazo(int prazo) {
		this.prazo = prazo;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public String getDataLiberacao() {
		return dataLiberacao;
	}

	public void setDataLiberacao(String dataLiberacao) {
		this.dataLiberacao = dataLiberacao;
	}

	public Double getValorIOF() {
		return valorIOF;
	}

	public void setValorIOF(Double valorIOF) {
		this.valorIOF = valorIOF;
	}

	public Double getTaxaJuro() {
		return taxaJuro;
	}

	public void setTaxaJuro(Double taxaJuro) {
		this.taxaJuro = taxaJuro;
	}

	public Double getCetMensal() {
		return cetMensal;
	}

	public void setCetMensal(Double cetMensal) {
		this.cetMensal = cetMensal;
	}

	public Double getCetAnual() {
		return cetAnual;
	}

	public void setCetAnual(Double cetAnual) {
		this.cetAnual = cetAnual;
	}

	public String getDataBase() {
		return dataBase;
	}

	public void setDataBase(String dataBase) {
		this.dataBase = dataBase;
	}

	public String getVencimentoPrimeiraPrestacao() {
		return vencimentoPrimeiraPrestacao;
	}

	public void setVencimentoPrimeiraPrestacao(String vencimentoPrimeiraPrestacao) {
		this.vencimentoPrimeiraPrestacao = vencimentoPrimeiraPrestacao;
	}

	public int getQuantidadeDiasAcerto() {
		return quantidadeDiasAcerto;
	}

	public void setQuantidadeDiasAcerto(int quantidadeDiasAcerto) {
		this.quantidadeDiasAcerto = quantidadeDiasAcerto;
	}

	public Double getValorJuroAcerto() {
		return valorJuroAcerto;
	}

	public void setValorJuroAcerto(Double valorJuroAcerto) {
		this.valorJuroAcerto = valorJuroAcerto;
	}

	public String getComSeguro() {
		return comSeguro;
	}

	public void setComSeguro(String comSeguro) {
		this.comSeguro = comSeguro;
	}

	public String getValorSeguro() {
		return valorSeguro;
	}

	public void setValorSeguro(String valorSeguro) {
		this.valorSeguro = valorSeguro;
	}

	public Double getTaxaSeguro() {
		return taxaSeguro;
	}

	public void setTaxaSeguro(Double taxaSeguro) {
		this.taxaSeguro = taxaSeguro;
	}

	public int getBanco() {
		return banco;
	}

	public void setBanco(int banco) {
		this.banco = banco;
	}

	public int getAgencia() {
		return agencia;
	}

	public void setAgencia(int agencia) {
		this.agencia = agencia;
	}

	public int getOperacao() {
		return operacao;
	}

	public void setOperacao(int operacao) {
		this.operacao = operacao;
	}

	public Long getConta() {
		return conta;
	}

	public void setConta(Long conta) {
		this.conta = conta;
	}

	public short getDigitoVerificador() {
		return digitoVerificador;
	}

	public void setDigitoVerificador(short digitoVerificador) {
		this.digitoVerificador = digitoVerificador;
	}

	public String getTipoCredito() {
		return tipoCredito;
	}

	public void setTipoCredito(String tipoCredito) {
		this.tipoCredito = tipoCredito;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Double getAliquotaBasicaIOF() {
		return aliquotaBasicaIOF;
	}

	public void setAliquotaBasicaIOF(Double aliquotaBasicaIOF) {
		this.aliquotaBasicaIOF = aliquotaBasicaIOF;
	}

	public Double getAliquotaComplementar() {
		return aliquotaComplementar;
	}

	public void setAliquotaComplementar(Double aliquotaComplementar) {
		this.aliquotaComplementar = aliquotaComplementar;
	}

	public Long getAvaliacao() {
		return avaliacao;
	}

	public void setAvaliacao(Long avaliacao) {
		this.avaliacao = avaliacao;
	}
		
}
