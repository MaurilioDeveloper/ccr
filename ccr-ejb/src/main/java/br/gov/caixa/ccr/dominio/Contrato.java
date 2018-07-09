package br.gov.caixa.ccr.dominio;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Transient;

import br.gov.caixa.arqrefservices.dominio.siric.AvaliacaoValiada;

public class Contrato extends Retorno {


	private Long nuContrato;
	private Convenio convenioTO;
	private Situacao situacao;
	private String coTipoSimulacao;
	private String coUsuario;
	private String coMatriculaCliente;
	private Long ddBeneficio;
	private Date dtBaseCalculo;
	private Date dtCompetencia;
	private Date dtContrato;
	private Date dtLiberacaoCredito;
	private Date dtVencimento;
	private Date dtVncmoPrimeiraPrestacao;
	private String icEmpregadoTemporario;
	private String icRecebeSalarioCaixa;
	private String icSeguroPrestamista;
	private Integer icTipoCredito;
	private Long nuAgenciaContaCredito;
	private Long nuAgenciaContaDebito;
	private Avaliacao avaliacaoCliente;
	private Avaliacao avaliacaoOperacao;
	private Avaliacao nuAvaliacao;
	private Long nuBancoContaCredito;
	private Long nuBancoContaDebito;
	private Long nuBeneficio;
	private Long nuCocli;
	private Long nuContaCredito;
	private Long nuContaDebito;
	private Long nuContratoAplicacao;
	private Long nuCpf;
	private Long nuCnpjFontePagadora;
	private Long nuDvContaCredito;
	private Long nuDvContaDebito;
	private Long nuNaturalMovimento;
	private Long nuOperacaoContaCredito;
	private Long nuOperacaoContaDebito;
	private Long nuOrgaoMilitar;
	private Long nuRic;
	private Long nuUnidadeMovimento;
	private BigDecimal pcAliquotaBasica;
	private BigDecimal pcAliquotaComplementar;
	private BigDecimal pcCetAnual;
	private BigDecimal pcCetCapital;
	private BigDecimal pcCetIof;
	private BigDecimal pcCetJuroAcerto;
	private BigDecimal pcCetMensal;
	private BigDecimal pcCetSeguro;
	private BigDecimal pcTaxaJuroAnual;
	private BigDecimal pcTaxaJuroContrato;
	private BigDecimal pcTaxaSeguro;
	private Long pzContrato;
	private Long qtDiaJuroAcerto;
	private Long qtEmpregado;
	private BigDecimal vrBrutoBeneficio;
	private BigDecimal vrCetAnual;
	private BigDecimal vrCetMensal;
	private BigDecimal vrContrato;
	private BigDecimal vrIof;
	private BigDecimal vrJuroAcerto;
	private BigDecimal vrLiquidoBeneficio;
	private BigDecimal vrLiquidoContrato;
	private BigDecimal vrTotalContrato;
	private BigDecimal vrPrestacao;
	private BigDecimal vrSeguroPrestamista;
	private List<SituacaoContrato> historico; 
	private String nomeCliente;
	private Date dtSituacaoContrato;
	private String descSituacao;
	private String justificativa;
	private boolean imprimir;
	private boolean autorizar;
	private boolean avaliar;
	private boolean avaliarOperacao;
	private BigDecimal pcTaxaEfetivaAnual;
	private BigDecimal pcTaxaEfetivaMensal;
	private Long nuAgenciaConcessora;
	private String sicliCliente;
	private Long canalAtend;	
	private boolean reenviarContratoSIAPX;
	
	public Long getCanalAtend() {
		return canalAtend;
	}
	public void setCanalAtend(Long canalAtend) {
		this.canalAtend = canalAtend;
	}
	/**
	 * @return the nuContrato
	 */
	public Long getNuContrato() {
		return nuContrato;
	}
	/**
	 * @param nuContrato the nuContrato to set
	 */
	public void setNuContrato(Long nuContrato) {
		this.nuContrato = nuContrato;
	}
	
	
	/**
	 * @return the convenioTO
	 */
	public Convenio getConvenioTO() {
		return convenioTO;
	}
	/**
	 * @param convenioTO the convenioTO to set
	 */
	
	public void setConvenioTO(Convenio convenioTO) {
		this.convenioTO = convenioTO;
	}
	/**
	 * @return the justificativa
	 */
	public String getJustificativa() {
		return justificativa;
	}
	/**
	 * @param justificativa the justificativa to set
	 */
	public void setJustificativa(String justificativa) {
		this.justificativa = justificativa;
	}
	/**
	 * @return the situacao
	 */
	public Situacao getSituacao() {
		return situacao;
	}
	/**
	 * @param situacao the situacao to set
	 */
	public void setSituacao(Situacao situacao) {
		this.situacao = situacao;
	}
	/**
	 * @return the coTipoSimulacao
	 */
	public String getCoTipoSimulacao() {
		return coTipoSimulacao;
	}
	/**
	 * @param coTipoSimulacao the coTipoSimulacao to set
	 */
	public void setCoTipoSimulacao(String coTipoSimulacao) {
		this.coTipoSimulacao = coTipoSimulacao;
	}
	/**
	 * @return the coUsuario
	 */
	public String getCoUsuario() {
		return coUsuario;
	}
	/**
	 * @param coUsuario the coUsuario to set
	 */
	public void setCoUsuario(String coUsuario) {
		this.coUsuario = coUsuario;
	}
	/**
	 * @return the ddBeneficio
	 */
	public Long getDdBeneficio() {
		return ddBeneficio;
	}
	/**
	 * @param ddBeneficio the ddBeneficio to set
	 */
	public void setDdBeneficio(Long ddBeneficio) {
		this.ddBeneficio = ddBeneficio;
	}
	/**
	 * @return the dtBaseCalculo
	 */
	public Date getDtBaseCalculo() {
		return dtBaseCalculo;
	}
	/**
	 * @param dtBaseCalculo the dtBaseCalculo to set
	 */
	public void setDtBaseCalculo(Date dtBaseCalculo) {
		this.dtBaseCalculo = dtBaseCalculo;
	}
	/**
	 * @return the dtCompetencia
	 */
	public Date getDtCompetencia() {
		return dtCompetencia;
	}
	/**
	 * @param dtCompetencia the dtCompetencia to set
	 */
	public void setDtCompetencia(Date dtCompetencia) {
		this.dtCompetencia = dtCompetencia;
	}
	/**
	 * @return the dtContrato
	 */
	public Date getDtContrato() {
		return dtContrato;
	}
	/**
	 * @param dtContrato the dtContrato to set
	 */
	public void setDtContrato(Date dtContrato) {
		this.dtContrato = dtContrato;
	}
	/**
	 * @return the dtLiberacaoCredito
	 */
	public Date getDtLiberacaoCredito() {
		return dtLiberacaoCredito;
	}
	/**
	 * @param dtLiberacaoCredito the dtLiberacaoCredito to set
	 */
	public void setDtLiberacaoCredito(Date dtLiberacaoCredito) {
		this.dtLiberacaoCredito = dtLiberacaoCredito;
	}
	/**
	 * @return the dtVencimento
	 */
	public Date getDtVencimento() {
		return dtVencimento;
	}
	/**
	 * @param dtVencimento the dtVencimento to set
	 */
	public void setDtVencimento(Date dtVencimento) {
		this.dtVencimento = dtVencimento;
	}
	/**
	 * @return the dtVncmoPrimeiraPrestacao
	 */
	public Date getDtVncmoPrimeiraPrestacao() {
		return dtVncmoPrimeiraPrestacao;
	}
	/**
	 * @param dtVncmoPrimeiraPrestacao the dtVncmoPrimeiraPrestacao to set
	 */
	public void setDtVncmoPrimeiraPrestacao(Date dtVncmoPrimeiraPrestacao) {
		this.dtVncmoPrimeiraPrestacao = dtVncmoPrimeiraPrestacao;
	}
	/**
	 * @return the icEmpregadoTemporario
	 */
	public String getIcEmpregadoTemporario() {
		return icEmpregadoTemporario;
	}
	/**
	 * @param icEmpregadoTemporario the icEmpregadoTemporario to set
	 */
	public void setIcEmpregadoTemporario(String icEmpregadoTemporario) {
		this.icEmpregadoTemporario = icEmpregadoTemporario;
	}
	/**
	 * @return the icRecebeSalarioCaixa
	 */
	public String getIcRecebeSalarioCaixa() {
		return icRecebeSalarioCaixa;
	}
	/**
	 * @param icRecebeSalarioCaixa the icRecebeSalarioCaixa to set
	 */
	public void setIcRecebeSalarioCaixa(String icRecebeSalarioCaixa) {
		this.icRecebeSalarioCaixa = icRecebeSalarioCaixa;
	}
	/**
	 * @return the icSeguroPrestamista
	 */
	public String getIcSeguroPrestamista() {
		return icSeguroPrestamista;
	}
	/**
	 * @param icSeguroPrestamista the icSeguroPrestamista to set
	 */
	public void setIcSeguroPrestamista(String icSeguroPrestamista) {
		this.icSeguroPrestamista = icSeguroPrestamista;
	}
	/**
	 * @return the icTipoCredito
	 */
	public Integer getIcTipoCredito() {
		return icTipoCredito;
	}
	/**
	 * @param icTipoCredito the icTipoCredito to set
	 */
	public void setIcTipoCredito(Integer icTipoCredito) {
		this.icTipoCredito = icTipoCredito;
	}
	/**
	 * @return the nuAgenciaContaCredito
	 */
	public Long getNuAgenciaContaCredito() {
		return nuAgenciaContaCredito;
	}
	/**
	 * @param nuAgenciaContaCredito the nuAgenciaContaCredito to set
	 */
	public void setNuAgenciaContaCredito(Long nuAgenciaContaCredito) {
		this.nuAgenciaContaCredito = nuAgenciaContaCredito;
	}
	/**
	 * @return the nuAgenciaContaDebito
	 */
	public Long getNuAgenciaContaDebito() {
		return nuAgenciaContaDebito;
	}
	/**
	 * @param nuAgenciaContaDebito the nuAgenciaContaDebito to set
	 */
	public void setNuAgenciaContaDebito(Long nuAgenciaContaDebito) {
		this.nuAgenciaContaDebito = nuAgenciaContaDebito;
	}

	/**
	 * @return the avaliacaoCliente
	 */
	public Avaliacao getAvaliacaoCliente() {
		return avaliacaoCliente;
	}
	/**
	 * @param avaliacaoCliente the avaliacaoCliente to set
	 */
	public void setAvaliacaoCliente(Avaliacao avaliacaoCliente) {
		this.avaliacaoCliente = avaliacaoCliente;
	}
	/**
	 * @return the avaliacaoOperacao
	 */
	public Avaliacao getAvaliacaoOperacao() {
		return avaliacaoOperacao;
	}
	/**
	 * @param avaliacaoOperacao the avaliacaoOperacao to set
	 */
	public void setAvaliacaoOperacao(Avaliacao avaliacaoOperacao) {
		this.avaliacaoOperacao = avaliacaoOperacao;
	}
	/**
	 * @return the nuBancoContaCredito
	 */
	public Long getNuBancoContaCredito() {
		return nuBancoContaCredito;
	}
	/**
	 * @param nuBancoContaCredito the nuBancoContaCredito to set
	 */
	public void setNuBancoContaCredito(Long nuBancoContaCredito) {
		this.nuBancoContaCredito = nuBancoContaCredito;
	}
	/**
	 * @return the nuBancoContaDebito
	 */
	public Long getNuBancoContaDebito() {
		return nuBancoContaDebito;
	}
	/**
	 * @param nuBancoContaDebito the nuBancoContaDebito to set
	 */
	public void setNuBancoContaDebito(Long nuBancoContaDebito) {
		this.nuBancoContaDebito = nuBancoContaDebito;
	}
	/**
	 * @return the nuBeneficio
	 */
	public Long getNuBeneficio() {
		return nuBeneficio;
	}
	/**
	 * @param nuBeneficio the nuBeneficio to set
	 */
	public void setNuBeneficio(Long nuBeneficio) {
		this.nuBeneficio = nuBeneficio;
	}
	/**
	 * @return the nuCocli
	 */
	public Long getNuCocli() {
		return nuCocli;
	}
	/**
	 * @param nuCocli the nuCocli to set
	 */
	public void setNuCocli(Long nuCocli) {
		this.nuCocli = nuCocli;
	}
	/**
	 * @return the nuContaCredito
	 */
	public Long getNuContaCredito() {
		return nuContaCredito;
	}
	/**
	 * @param nuContaCredito the nuContaCredito to set
	 */
	public void setNuContaCredito(Long nuContaCredito) {
		this.nuContaCredito = nuContaCredito;
	}
	/**
	 * @return the nuContaDebito
	 */
	public Long getNuContaDebito() {
		return nuContaDebito;
	}
	/**
	 * @param nuContaDebito the nuContaDebito to set
	 */
	public void setNuContaDebito(Long nuContaDebito) {
		this.nuContaDebito = nuContaDebito;
	}
	/**
	 * @return the nuContratoAplicacao
	 */
	public Long getNuContratoAplicacao() {
		return nuContratoAplicacao;
	}
	/**
	 * @param nuContratoAplicacao the nuContratoAplicacao to set
	 */
	public void setNuContratoAplicacao(Long nuContratoAplicacao) {
		this.nuContratoAplicacao = nuContratoAplicacao;
	}
	/**
	 * @return the nuCpf
	 */
	public Long getNuCpf() {
		return nuCpf;
	}
	/**
	 * @param nuCpf the nuCpf to set
	 */
	public void setNuCpf(Long nuCpf) {
		this.nuCpf = nuCpf;
	}
	/**
	 * @return the nuCnpjFontePagadora
	 */
	public Long getNuCnpjFontePagadora() {
		return nuCnpjFontePagadora;
	}
	/**
	 * @param nuCnpjFontePagadora the nuCnpjFontePagadora to set
	 */
	public void setNuCnpjFontePagadora(Long nuCnpjFontePagadora) {
		this.nuCnpjFontePagadora = nuCnpjFontePagadora;
	}
	/**
	 * @return the nuDvContaCredito
	 */
	public Long getNuDvContaCredito() {
		return nuDvContaCredito;
	}
	/**
	 * @param nuDvContaCredito the nuDvContaCredito to set
	 */
	public void setNuDvContaCredito(Long nuDvContaCredito) {
		this.nuDvContaCredito = nuDvContaCredito;
	}
	/**
	 * @return the nuDvContaDebito
	 */
	public Long getNuDvContaDebito() {
		return nuDvContaDebito;
	}
	/**
	 * @param nuDvContaDebito the nuDvContaDebito to set
	 */
	public void setNuDvContaDebito(Long nuDvContaDebito) {
		this.nuDvContaDebito = nuDvContaDebito;
	}
	/**
	 * @return the nuNaturalMovimento
	 */
	public Long getNuNaturalMovimento() {
		return nuNaturalMovimento;
	}
	/**
	 * @param nuNaturalMovimento the nuNaturalMovimento to set
	 */
	public void setNuNaturalMovimento(Long nuNaturalMovimento) {
		this.nuNaturalMovimento = nuNaturalMovimento;
	}
	/**
	 * @return the nuOperacaoContaCredito
	 */
	public Long getNuOperacaoContaCredito() {
		return nuOperacaoContaCredito;
	}
	/**
	 * @param nuOperacaoContaCredito the nuOperacaoContaCredito to set
	 */
	public void setNuOperacaoContaCredito(Long nuOperacaoContaCredito) {
		this.nuOperacaoContaCredito = nuOperacaoContaCredito;
	}
	/**
	 * @return the nuOperacaoContaDebito
	 */
	public Long getNuOperacaoContaDebito() {
		return nuOperacaoContaDebito;
	}
	/**
	 * @param nuOperacaoContaDebito the nuOperacaoContaDebito to set
	 */
	public void setNuOperacaoContaDebito(Long nuOperacaoContaDebito) {
		this.nuOperacaoContaDebito = nuOperacaoContaDebito;
	}
	/**
	 * @return the nuOrgaoMilitar
	 */
	public Long getNuOrgaoMilitar() {
		return nuOrgaoMilitar;
	}
	/**
	 * @param nuOrgaoMilitar the nuOrgaoMilitar to set
	 */
	public void setNuOrgaoMilitar(Long nuOrgaoMilitar) {
		this.nuOrgaoMilitar = nuOrgaoMilitar;
	}
	/**
	 * @return the nuRic
	 */
	public Long getNuRic() {
		return nuRic;
	}
	/**
	 * @param nuRic the nuRic to set
	 */
	public void setNuRic(Long nuRic) {
		this.nuRic = nuRic;
	}
	/**
	 * @return the nuUnidadeMovimento
	 */
	public Long getNuUnidadeMovimento() {
		return nuUnidadeMovimento;
	}
	/**
	 * @param nuUnidadeMovimento the nuUnidadeMovimento to set
	 */
	public void setNuUnidadeMovimento(Long nuUnidadeMovimento) {
		this.nuUnidadeMovimento = nuUnidadeMovimento;
	}
	/**
	 * @return the pcAliquotaBasica
	 */
	public BigDecimal getPcAliquotaBasica() {
		return pcAliquotaBasica;
	}
	/**
	 * @param pcAliquotaBasica the pcAliquotaBasica to set
	 */
	public void setPcAliquotaBasica(BigDecimal pcAliquotaBasica) {
		this.pcAliquotaBasica = pcAliquotaBasica;
	}
	/**
	 * @return the pcAliquotaComplementar
	 */
	public BigDecimal getPcAliquotaComplementar() {
		return pcAliquotaComplementar;
	}
	/**
	 * @param pcAliquotaComplementar the pcAliquotaComplementar to set
	 */
	public void setPcAliquotaComplementar(BigDecimal pcAliquotaComplementar) {
		this.pcAliquotaComplementar = pcAliquotaComplementar;
	}
	/**
	 * @return the pcCetAnual
	 */
	public BigDecimal getPcCetAnual() {
		return pcCetAnual;
	}
	/**
	 * @param pcCetAnual the pcCetAnual to set
	 */
	public void setPcCetAnual(BigDecimal pcCetAnual) {
		this.pcCetAnual = pcCetAnual;
	}
	/**
	 * @return the pcCetCapital
	 */
	public BigDecimal getPcCetCapital() {
		return pcCetCapital;
	}
	/**
	 * @param pcCetCapital the pcCetCapital to set
	 */
	public void setPcCetCapital(BigDecimal pcCetCapital) {
		this.pcCetCapital = pcCetCapital;
	}
	/**
	 * @return the pcCetIof
	 */
	public BigDecimal getPcCetIof() {
		return pcCetIof;
	}
	/**
	 * @param pcCetIof the pcCetIof to set
	 */
	public void setPcCetIof(BigDecimal pcCetIof) {
		this.pcCetIof = pcCetIof;
	}
	/**
	 * @return the pcCetJuroAcerto
	 */
	public BigDecimal getPcCetJuroAcerto() {
		return pcCetJuroAcerto;
	}
	/**
	 * @param pcCetJuroAcerto the pcCetJuroAcerto to set
	 */
	public void setPcCetJuroAcerto(BigDecimal pcCetJuroAcerto) {
		this.pcCetJuroAcerto = pcCetJuroAcerto;
	}
	/**
	 * @return the pcCetMensal
	 */
	public BigDecimal getPcCetMensal() {
		return pcCetMensal;
	}
	/**
	 * @param pcCetMensal the pcCetMensal to set
	 */
	public void setPcCetMensal(BigDecimal pcCetMensal) {
		this.pcCetMensal = pcCetMensal;
	}
	/**
	 * @return the pcCetSeguro
	 */
	public BigDecimal getPcCetSeguro() {
		return pcCetSeguro;
	}
	/**
	 * @param pcCetSeguro the pcCetSeguro to set
	 */
	public void setPcCetSeguro(BigDecimal pcCetSeguro) {
		this.pcCetSeguro = pcCetSeguro;
	}
	/**
	 * @return the pcTaxaJuroAnual
	 */
	public BigDecimal getPcTaxaJuroAnual() {
		return pcTaxaJuroAnual;
	}
	/**
	 * @param pcTaxaJuroAnual the pcTaxaJuroAnual to set
	 */
	public void setPcTaxaJuroAnual(BigDecimal pcTaxaJuroAnual) {
		this.pcTaxaJuroAnual = pcTaxaJuroAnual;
	}
	/**
	 * @return the pcTaxaJuroContrato
	 */
	public BigDecimal getPcTaxaJuroContrato() {
		return pcTaxaJuroContrato;
	}
	/**
	 * @param pcTaxaJuroContrato the pcTaxaJuroContrato to set
	 */
	public void setPcTaxaJuroContrato(BigDecimal pcTaxaJuroContrato) {
		this.pcTaxaJuroContrato = pcTaxaJuroContrato;
	}
	/**
	 * @return the pcTaxaSeguro
	 */
	public BigDecimal getPcTaxaSeguro() {
		return pcTaxaSeguro;
	}
	/**
	 * @param pcTaxaSeguro the pcTaxaSeguro to set
	 */
	public void setPcTaxaSeguro(BigDecimal pcTaxaSeguro) {
		this.pcTaxaSeguro = pcTaxaSeguro;
	}
	/**
	 * @return the pzContrato
	 */
	public Long getPzContrato() {
		return pzContrato;
	}
	/**
	 * @param pzContrato the pzContrato to set
	 */
	public void setPzContrato(Long pzContrato) {
		this.pzContrato = pzContrato;
	}
	/**
	 * @return the qtDiaJuroAcerto
	 */
	public Long getQtDiaJuroAcerto() {
		return qtDiaJuroAcerto;
	}
	/**
	 * @param qtDiaJuroAcerto the qtDiaJuroAcerto to set
	 */
	public void setQtDiaJuroAcerto(Long qtDiaJuroAcerto) {
		this.qtDiaJuroAcerto = qtDiaJuroAcerto;
	}
	/**
	 * @return the qtEmpregado
	 */
	public Long getQtEmpregado() {
		return qtEmpregado;
	}
	/**
	 * @param qtEmpregado the qtEmpregado to set
	 */
	public void setQtEmpregado(Long qtEmpregado) {
		this.qtEmpregado = qtEmpregado;
	}
	/**
	 * @return the vrBrutoBeneficio
	 */
	public BigDecimal getVrBrutoBeneficio() {
		return vrBrutoBeneficio;
	}
	/**
	 * @param vrBrutoBeneficio the vrBrutoBeneficio to set
	 */
	public void setVrBrutoBeneficio(BigDecimal vrBrutoBeneficio) {
		this.vrBrutoBeneficio = vrBrutoBeneficio;
	}
	/**
	 * @return the vrCetAnual
	 */
	public BigDecimal getVrCetAnual() {
		return vrCetAnual;
	}
	/**
	 * @param vrCetAnual the vrCetAnual to set
	 */
	public void setVrCetAnual(BigDecimal vrCetAnual) {
		this.vrCetAnual = vrCetAnual;
	}
	/**
	 * @return the vrCetMensal
	 */
	public BigDecimal getVrCetMensal() {
		return vrCetMensal;
	}
	/**
	 * @param vrCetMensal the vrCetMensal to set
	 */
	public void setVrCetMensal(BigDecimal vrCetMensal) {
		this.vrCetMensal = vrCetMensal;
	}
	/**
	 * @return the vrContrato
	 */
	public BigDecimal getVrContrato() {
		return vrContrato;
	}
	/**
	 * @param vrContrato the vrContrato to set
	 */
	public void setVrContrato(BigDecimal vrContrato) {
		this.vrContrato = vrContrato;
	}
	/**
	 * @return the vrIof
	 */
	public BigDecimal getVrIof() {
		return vrIof;
	}
	/**
	 * @param vrIof the vrIof to set
	 */
	public void setVrIof(BigDecimal vrIof) {
		this.vrIof = vrIof;
	}
	/**
	 * @return the vrJuroAcerto
	 */
	public BigDecimal getVrJuroAcerto() {
		return vrJuroAcerto;
	}
	/**
	 * @param vrJuroAcerto the vrJuroAcerto to set
	 */
	public void setVrJuroAcerto(BigDecimal vrJuroAcerto) {
		this.vrJuroAcerto = vrJuroAcerto;
	}
	/**
	 * @return the vrLiquidoBeneficio
	 */
	public BigDecimal getVrLiquidoBeneficio() {
		return vrLiquidoBeneficio;
	}
	/**
	 * @param vrLiquidoBeneficio the vrLiquidoBeneficio to set
	 */
	public void setVrLiquidoBeneficio(BigDecimal vrLiquidoBeneficio) {
		this.vrLiquidoBeneficio = vrLiquidoBeneficio;
	}
	/**
	 * @return the vrLiquidoContrato
	 */
	public BigDecimal getVrLiquidoContrato() {
		return vrLiquidoContrato;
	}
	/**
	 * @param vrLiquidoContrato the vrLiquidoContrato to set
	 */
	public void setVrLiquidoContrato(BigDecimal vrLiquidoContrato) {
		this.vrLiquidoContrato = vrLiquidoContrato;
	}
	/**
	 * @return the vrTotalContrato
	 */
	public BigDecimal getVrTotalContrato() {
		return vrTotalContrato;
	}
	/**
	 * @param vrTotalContrato the vrTotalContrato to set
	 */
	public void setVrTotalContrato(BigDecimal vrTotalContrato) {
		this.vrTotalContrato = vrTotalContrato;
	}
	/**
	 * @return the vrPrestacao
	 */
	public BigDecimal getVrPrestacao() {
		return vrPrestacao;
	}
	/**
	 * @param vrPrestacao the vrPrestacao to set
	 */
	public void setVrPrestacao(BigDecimal vrPrestacao) {
		this.vrPrestacao = vrPrestacao;
	}
	/**
	 * @return the vrSeguroPrestamista
	 */
	public BigDecimal getVrSeguroPrestamista() {
		return vrSeguroPrestamista;
	}
	/**
	 * @param vrSeguroPrestamista the vrSeguroPrestamista to set
	 */
	public void setVrSeguroPrestamista(BigDecimal vrSeguroPrestamista) {
		this.vrSeguroPrestamista = vrSeguroPrestamista;
	}
	/**
	 * @return the historico
	 */
	public List<SituacaoContrato> getHistorico() {
		return historico;
	}
	/**
	 * @param historico the historico to set
	 */
	public void setHistorico(List<SituacaoContrato> historico) {
		this.historico = historico;
	}
	/**
	 * @return the nomeCliente
	 */
	public String getNomeCliente() {
		return nomeCliente;
	}
	/**
	 * @param nomeCliente the nomeCliente to set
	 */
	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}
	/**
	 * @return the coMatriculaCliente
	 */
	public String getCoMatriculaCliente() {
		return coMatriculaCliente;
	}
	/**
	 * @param coMatriculaCliente the coMatriculaCliente to set
	 */
	public void setCoMatriculaCliente(String coMatriculaCliente) {
		this.coMatriculaCliente = coMatriculaCliente;
	}
	/**
	 * @return the dtSituacaoContrato
	 */
	public Date getDtSituacaoContrato() {
		return dtSituacaoContrato;
	}
	/**
	 * @param dtSituacaoContrato the dtSituacaoContrato to set
	 */
	public void setDtSituacaoContrato(Date dtSituacaoContrato) {
		this.dtSituacaoContrato = dtSituacaoContrato;
	}
	/**
	 * @return the descSituacao
	 */
	public String getDescSituacao() {
		return descSituacao;
	}
	/**
	 * @param descSituacao the descSituacao to set
	 */
	public void setDescSituacao(String descSituacao) {
		this.descSituacao = descSituacao;
	}
	/**
	 * @param the imprimir 
	 */
	public boolean isImprimir() {
		return imprimir;
	}
	/**
	 * @param imprimir the imprimir to set
	 */
	public void setImprimir(boolean imprimir) {
		this.imprimir = imprimir;
	}
	/**
	 * @param the autorizar
	 */
	public boolean isAutorizar() {
		return autorizar;
	}
	/**
	 * @param autorizar the autorizar to set
	 */
	public void setAutorizar(boolean autorizar) {
		this.autorizar = autorizar;
	}
	/**
	 * @return the nuAvaliacao
	 */
	public Avaliacao getNuAvaliacao() {
		return nuAvaliacao;
	}
	/**
	 * @param nuAvaliacao the nuAvaliacao to set
	 */
	public void setNuAvaliacao(Avaliacao nuAvaliacao) {
		this.nuAvaliacao = nuAvaliacao;
	}
	/**
	 * @return the pcTaxaEfetivaAnual
	 */
	public BigDecimal getPcTaxaEfetivaAnual() {
		return pcTaxaEfetivaAnual;
	}
	/**
	 * @param pcTaxaEfetivaAnual the pcTaxaEfetivaAnual to set
	 */
	public void setPcTaxaEfetivaAnual(BigDecimal pcTaxaEfetivaAnual) {
		this.pcTaxaEfetivaAnual = pcTaxaEfetivaAnual;
	}
	/**
	 * @return the pcTaxaEfetivaMensal
	 */
	public BigDecimal getPcTaxaEfetivaMensal() {
		return pcTaxaEfetivaMensal;
	}
	/**
	 * @param pcTaxaEfetivaMensal the pcTaxaEfetivaMensal to set
	 */
	public void setPcTaxaEfetivaMensal(BigDecimal pcTaxaEfetivaMensal) {
		this.pcTaxaEfetivaMensal = pcTaxaEfetivaMensal;
	}
	/**
	 * @return the nuAgenciaConcessora
	 */
	public Long getNuAgenciaConcessora() {
		return nuAgenciaConcessora;
	}
	/**
	 * @param nuAgenciaConcessora the nuAgenciaConcessora to set
	 */
	public void setNuAgenciaConcessora(Long nuAgenciaConcessora) {
		this.nuAgenciaConcessora = nuAgenciaConcessora;
	}
	/**
	 * @return the sicliCliente
	 */
	public String getSicliCliente() {
		return sicliCliente;
	}
	/**
	 * @param sicliCliente the sicliCliente to set
	 */
	public void setSicliCliente(String sicliCliente) {
		this.sicliCliente = sicliCliente;
	}
	
	public boolean isAvaliar() {
		return avaliar;
	}
	
	public void setAvaliar(boolean avaliar) {
		this.avaliar = avaliar;
	}
	/**
	 * @return the avaliarOperacao
	 */
	public boolean isAvaliarOperacao() {
		return avaliarOperacao;
	}
	/**
	 * @param avaliarOperacao the avaliarOperacao to set
	 */
	public void setAvaliarOperacao(boolean avaliarOperacao) {
		this.avaliarOperacao = avaliarOperacao;
	}
	
	public boolean isReenviarContratoSIAPX() {
		return reenviarContratoSIAPX;
	}
	public void setReenviarContratoSIAPX(boolean reenviarContratoSIAPX) {
		this.reenviarContratoSIAPX = reenviarContratoSIAPX;
	}
	
	
	
}
