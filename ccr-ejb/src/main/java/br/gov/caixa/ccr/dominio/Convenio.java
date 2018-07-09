package br.gov.caixa.ccr.dominio;

import java.util.Date;
import java.util.List;

public class Convenio extends Retorno {
	
	public Convenio() {
		super.setCodigoRetorno(0L);
		this.setConvenente(new Convenente());
		this.setGrupo(new GrupoTaxa());
		this.setRemessa(new RemessaExtrato());
		this.setSituacao(new Situacao());
	}
	
	public Convenio(Long codigo, String mensagem, String tipo) {
		super.setCodigoRetorno(codigo);
		super.setMensagemRetorno(mensagem);
		super.setTipoRetorno(tipo);
	}
	
	public Convenio(Long codigo, String mensagem, String tipo, String idMsg) {
		super.setCodigoRetorno(codigo);
		super.setMensagemRetorno(mensagem);
		super.setTipoRetorno(tipo);
		super.setIdMsg(idMsg);
	}

	private String mensagemRetorno;  
	
	private Long id;
	
	private Boolean updatable;
	
	private Convenente convenente;
	
	private GrupoTaxa grupo;
	
	private RemessaExtrato remessa;
	
	private Situacao situacao;
	
	private GrupoAverbacao grupoAverbacao;
	
	private OrigemRecurso origemRecurso;
	
	List<ConvenioCanal> canais;
	
	List<ConvenioUF> abrangenciaUF;
	
	List<ConvenioSR> abrangenciaSR;
	
	private Long codDvConvenio;

	private Integer diaCreditoSal;

	private Date dtMigracao;

	private Integer indAbrangencia;

	private String indAceitaCanal;

	private String indCarencia;

	private String indExigeClienteRenovacao;

	private String indInibeRemessaInadimplente;

	private String indMoratoria;

	private String indPermiteContratoCliente;

	private String indTarifaAverbacao;

	private String nome;

	private Long numAgenciaContaCredito;

	private Long numContaCredito;

	private Long numDvContaCredito;

	/**/
	private Long numNaturalPvCobranca;
	
	private Long numNaturalPvResponsavel;
	
	private Long numNaturalSprnaCobranca;
	
	private Long numNaturalSprnaResponsavel;
	
	/**/
	
	private Long numOperacaoContaCredito;

	private Long numPvCobranca;

	private Long numPvResponsavel;

	private Long numSprnaCobranca;

	private Long numSprnaResponsavel;

	private Integer przEmissaoExtrato;

	private Integer przMaximoCarencia;

	private Integer przMaximoEmprestimo;

	private Integer przMaximoMoratoria;

	private Integer przMaximoRenovacao;
	
	private Integer przVigenciaConvenio;

	private Long cnpjConvenente;
	
	private Integer qtEmpregado;
	
	private String codigoOrgao;
	
	private Integer qtDiaAguardarAutorizacao;
	
	private String contaCorrente;

	List<ConvenioCNPJVinculado> convenioCNPJVinculado;
	
	private String cnpjFontePagadora;
	
	private String dtCriacaoConvenio;
	
	private Boolean vigente;

	
	public String getContaCorrente() {
		return contaCorrente;
	}

	public void setContaCorrente(String contaCorrente) {
		this.contaCorrente = contaCorrente;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCodDvConvenio() {
		return this.codDvConvenio;
	}

	public void setCodDvConvenio(Long codDvConvenio) {
		this.codDvConvenio = codDvConvenio;
	}

	public Integer getDiaCreditoSal() {
		return this.diaCreditoSal;
	}

	public void setDiaCreditoSal(Integer diaCreditoSal) {
		this.diaCreditoSal = diaCreditoSal;
	}

	public Date getDtMigracao() {
		return this.dtMigracao;
	}

	public void setDtMigracao(Date dtMigracao) {
		this.dtMigracao = dtMigracao;
	}

	public Integer getIndAbrangencia() {
		return this.indAbrangencia;
	}

	public void setIndAbrangencia(Integer indAbrangencia) {
		this.indAbrangencia = indAbrangencia;
	}

	public String getIndAceitaCanal() {
		return this.indAceitaCanal;
	}

	public void setIndAceitaCanal(String indAceitaCanal) {
		this.indAceitaCanal = indAceitaCanal;
	}

	public String getIndCarencia() {
		return this.indCarencia;
	}

	public void setIndCarencia(String indCarencia) {
		this.indCarencia = indCarencia;
	}

	public String getIndExigeClienteRenovacao() {
		return this.indExigeClienteRenovacao;
	}

	public void setIndExigeClienteRenovacao(String indExigeClienteRenovacao) {
		this.indExigeClienteRenovacao = indExigeClienteRenovacao;
	}

	public String getIndInibeRemessaInadimplente() {
		return this.indInibeRemessaInadimplente;
	}

	public void setIndInibeRemessaInadimplente(String indInibeRemessaInadimplente) {
		this.indInibeRemessaInadimplente = indInibeRemessaInadimplente;
	}

	public String getIndMoratoria() {
		return this.indMoratoria;
	}

	public void setIndMoratoria(String indMoratoria) {
		this.indMoratoria = indMoratoria;
	}

	public String getIndPermiteContratoCliente() {
		return this.indPermiteContratoCliente;
	}

	public void setIndPermiteContratoCliente(String indPermiteContratoCliente) {
		this.indPermiteContratoCliente = indPermiteContratoCliente;
	}

	public String getIndTarifaAverbacao() {
		return this.indTarifaAverbacao;
	}

	public void setIndTarifaAverbacao(String indTarifaAverbacao) {
		this.indTarifaAverbacao = indTarifaAverbacao;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Long getNumAgenciaContaCredito() {
		return this.numAgenciaContaCredito;
	}

	public void setNumAgenciaContaCredito(Long numAgenciaContaCredito) {
		this.numAgenciaContaCredito = numAgenciaContaCredito;
	}

	public Long getNumContaCredito() {
		return this.numContaCredito;
	}

	public void setNumContaCredito(Long numContaCredito) {
		this.numContaCredito = numContaCredito;
	}

	public Long getNumDvContaCredito() {
		return this.numDvContaCredito;
	}

	public void setNumDvContaCredito(Long numDvContaCredito) {
		this.numDvContaCredito = numDvContaCredito;
	}

	public Long getNumNaturalPvCobranca() {
		return this.numNaturalPvCobranca;
	}

	public void setNumNaturalPvCobranca(Long numNaturalPvCobranca) {
		this.numNaturalPvCobranca = numNaturalPvCobranca;
	}

	public Long getNumNaturalPvResponsavel() {
		return this.numNaturalPvResponsavel;
	}

	public void setNumNaturalPvResponsavel(Long numNaturalPvResponsavel) {
		this.numNaturalPvResponsavel = numNaturalPvResponsavel;
	}

	public Long getNumNaturalSprnaCobranca() {
		return this.numNaturalSprnaCobranca;
	}

	public void setNumNaturalSprnaCobranca(Long numNaturalSprnaCobranca) {
		this.numNaturalSprnaCobranca = numNaturalSprnaCobranca;
	}

	public Long getNumNaturalSprnaResponsavel() {
		return this.numNaturalSprnaResponsavel;
	}

	public void setNumNaturalSprnaResponsavel(Long numNaturalSprnaResponsavel) {
		this.numNaturalSprnaResponsavel = numNaturalSprnaResponsavel;
	}

	public Long getNumOperacaoContaCredito() {
		return this.numOperacaoContaCredito;
	}

	public void setNumOperacaoContaCredito(Long numOperacaoContaCredito) {
		this.numOperacaoContaCredito = numOperacaoContaCredito;
	}

	public Long getNumPvCobranca() {
		return this.numPvCobranca;
	}

	public void setNumPvCobranca(Long numPvCobranca) {
		this.numPvCobranca = numPvCobranca;
	}

	public Long getNumPvResponsavel() {
		return this.numPvResponsavel;
	}

	public void setNumPvResponsavel(Long numPvResponsavel) {
		this.numPvResponsavel = numPvResponsavel;
	}

	public Long getNumSprnaCobranca() {
		return this.numSprnaCobranca;
	}

	public void setNumSprnaCobranca(Long numSprnaCobranca) {
		this.numSprnaCobranca = numSprnaCobranca;
	}

	public Long getNumSprnaResponsavel() {
		return this.numSprnaResponsavel;
	}

	public void setNumSprnaResponsavel(Long numSprnaResponsavel) {
		this.numSprnaResponsavel = numSprnaResponsavel;
	}

	public Integer getPrzEmissaoExtrato() {
		return this.przEmissaoExtrato;
	}

	public void setPrzEmissaoExtrato(Integer przEmissaoExtrato) {
		this.przEmissaoExtrato = przEmissaoExtrato;
	}

	public Integer getPrzMaximoCarencia() {
		return this.przMaximoCarencia;
	}

	public void setPrzMaximoCarencia(Integer przMaximoCarencia) {
		this.przMaximoCarencia = przMaximoCarencia;
	}

	public Integer getPrzMaximoEmprestimo() {
		return this.przMaximoEmprestimo;
	}

	public void setPrzMaximoEmprestimo(Integer przMaximoEmprestimo) {
		this.przMaximoEmprestimo = przMaximoEmprestimo;
	}

	public Integer getPrzMaximoMoratoria() {
		return this.przMaximoMoratoria;
	}

	public void setPrzMaximoMoratoria(Integer przMaximoMoratoria) {
		this.przMaximoMoratoria = przMaximoMoratoria;
	}

	public Integer getPrzMaximoRenovacao() {
		return this.przMaximoRenovacao;
	}

	public void setPrzMaximoRenovacao(Integer przMaximoRenovacao) {
		this.przMaximoRenovacao = przMaximoRenovacao;
	}

	public Long getCnpjConvenente() {
		return cnpjConvenente;
	}

	public void setCnpjConvenente(Long cnpjConvenente) {
		this.cnpjConvenente = cnpjConvenente;
	}

	public GrupoTaxa getGrupo() {
		return grupo;
	}

	public void setGrupo(GrupoTaxa grupo) {
		this.grupo = grupo;
	}

	public RemessaExtrato getRemessa() {
		return remessa;
	}

	public void setRemessa(RemessaExtrato remessa) {
		this.remessa = remessa;
	}

	public Situacao getSituacao() {
		return situacao;
	}

	public void setSituacao(Situacao situacao) {
		this.situacao = situacao;
	}

	public Convenente getConvenente() {
		return convenente;
	}

	public void setConvenente(Convenente convenente) {
		this.convenente = convenente;
	}

	public List<ConvenioCanal> getCanais() {
		return canais;
	}

	public void setCanais(List<ConvenioCanal> canais) {
		this.canais = canais;
	}

	public GrupoAverbacao getGrupoAverbacao() {
		return grupoAverbacao;
	}

	public void setGrupoAverbacao(GrupoAverbacao grupoAverbacao) {
		this.grupoAverbacao = grupoAverbacao;
	}

	public Boolean getUpdatable() {
		return updatable;
	}

	public void setUpdatable(Boolean updatable) {
		this.updatable = updatable;
	}

	public OrigemRecurso getOrigemRecurso() {
		return origemRecurso;
	}

	public void setOrigemRecurso(OrigemRecurso origemRecurso) {
		this.origemRecurso = origemRecurso;
	}

	public List<ConvenioUF> getAbrangenciaUF() {
		return abrangenciaUF;
	}

	public void setAbrangenciaUF(List<ConvenioUF> abrangenciaUF) {
		this.abrangenciaUF = abrangenciaUF;
	}

	public List<ConvenioSR> getAbrangenciaSR() {
		return abrangenciaSR;
	}

	public void setAbrangenciaSR(List<ConvenioSR> abrangenciaSR) {
		this.abrangenciaSR = abrangenciaSR;
	}

	public String getCodigoOrgao() {
		return codigoOrgao;
	}

	public void setCodigoOrgao(String codigoOrgao) {
		this.codigoOrgao = codigoOrgao;
	}

	public Integer getQtDiaAguardarAutorizacao() {
		return qtDiaAguardarAutorizacao;
	}

	public void setQtDiaAguardarAutorizacao(Integer qtDiaAguardarAutorizacao) {
		this.qtDiaAguardarAutorizacao = qtDiaAguardarAutorizacao;
	}

	public Integer getQtEmpregado() {
		return qtEmpregado;
	}

	public void setQtEmpregado(Integer qtEmpregado) {
		this.qtEmpregado = qtEmpregado;
	}

	public List<ConvenioCNPJVinculado> getConvenioCNPJVinculado() {
		return convenioCNPJVinculado;
	}

	public void setConvenioCNPJVinculado(List<ConvenioCNPJVinculado> convenioCNPJVinculado) {
		this.convenioCNPJVinculado = convenioCNPJVinculado;
	}

	public String getMensagemRetorno() {
		return mensagemRetorno;
	}

	public void setMensagemRetorno(String mensagemRetorno) {
		this.mensagemRetorno = mensagemRetorno;
	}

	public String getCnpjFontePagadora() {
		return cnpjFontePagadora;
	}

	public void setCnpjFontePagadora(String cnpjFontePagadora) {
		this.cnpjFontePagadora = cnpjFontePagadora;
	}

	public Integer getPrzVigenciaConvenio() {
		return przVigenciaConvenio;
	}

	public void setPrzVigenciaConvenio(Integer przVigenciaConvenio) {
		this.przVigenciaConvenio = przVigenciaConvenio;
	}

	public String getDtCriacaoConvenio() {
		return dtCriacaoConvenio;
	}

	public void setDtCriacaoConvenio(String dtCriacaoConvenio) {
		this.dtCriacaoConvenio = dtCriacaoConvenio;
	}

	public Boolean getVigente() {
		return vigente;
	}

	public void setVigente(Boolean vigente) {
		this.vigente = vigente;
	}
	
	
	
}
