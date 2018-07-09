package br.gov.caixa.ccr.dominio;

import java.util.Date;

public class TaxaSeguroFaixa extends Retorno {
	
	private static final float EPSILON = 0.000009F;

	private int id;
	private int prazoMin;	
	private int prazoMax;	
	private int idadeMin;	
	private int idadeMax;	
	private String inicioVigencia;	
	private String fimVigencia;	
	private Date dtInicioVigencia;	
	private Date dtFimVigencia;	
	private double taxa;
	private Boolean updatable;	
	private Boolean excludable;
	private String usuarioInclusao;
	private String dataInclusaoTaxa;
	private String usuarioFinalizacao;
	private String dataInclusaoFimVigencia;
		
	private int prazoMinOld;
	private int prazoMaxOld;	
	private int idadeMinOld;	
	private int idadeMaxOld;
	private double taxaOld;
		
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPrazoMin() {
		return prazoMin;
	}

	public void setPrazoMin(int prazoMin) {
		this.prazoMin = prazoMin;
	}

	public int getPrazoMax() {
		return prazoMax;
	}

	public void setPrazoMax(int prazoMax) {
		this.prazoMax = prazoMax;
	}

	public int getIdadeMin() {
		return idadeMin;
	}

	public void setIdadeMin(int idadeMin) {
		this.idadeMin = idadeMin;
	}

	public int getIdadeMax() {
		return idadeMax;
	}

	public void setIdadeMax(int idadeMax) {
		this.idadeMax = idadeMax;
	}

	public String getInicioVigencia() {
		return inicioVigencia;
	}

	public void setInicioVigencia(String inicioVigencia) {
		this.inicioVigencia = inicioVigencia;
	}

	public String getFimVigencia() {
		return fimVigencia;
	}

	public void setFimVigencia(String fimVigencia) {
		this.fimVigencia = fimVigencia;
	}

	public double getTaxa() {
		return taxa;
	}

	public void setTaxa(double taxa) {
		this.taxa = taxa;
	}

	public Boolean isUpdatable() {
		return updatable;
	}

	public void setUpdatable(Boolean updatable) {
		this.updatable = updatable;
	}

	public Boolean isExcludable() {
		return excludable;
	}

	public void setExcludable(Boolean excludable) {
		this.excludable = excludable;
	}

	public String getUsuarioInclusao() {
		return usuarioInclusao;
	}

	public void setUsuarioInclusao(String usuarioInclusao) {
		this.usuarioInclusao = usuarioInclusao;
	}

	public String getDataInclusaoTaxa() {
		return dataInclusaoTaxa;
	}

	public void setDataInclusaoTaxa(String dataInclusaoTaxa) {
		this.dataInclusaoTaxa = dataInclusaoTaxa;
	}

	public String getUsuarioFinalizacao() {
		return usuarioFinalizacao;
	}

	public void setUsuarioFinalizacao(String usuarioFinalizacao) {
		this.usuarioFinalizacao = usuarioFinalizacao;
	}

	public String getDataInclusaoFimVigencia() {
		return dataInclusaoFimVigencia;
	}

	public void setDataInclusaoFimVigencia(String dataInclusaoFimVigencia) {
		this.dataInclusaoFimVigencia = dataInclusaoFimVigencia;
	}
	
	public int getPrazoMinOld() {
		return prazoMinOld;
	}

	public void setPrazoMinOld(int prazoMinOld) {
		this.prazoMinOld = prazoMinOld;
	}

	public int getPrazoMaxOld() {
		return prazoMaxOld;
	}

	public void setPrazoMaxOld(int prazoMaxOld) {
		this.prazoMaxOld = prazoMaxOld;
	}

	public int getIdadeMinOld() {
		return idadeMinOld;
	}

	public void setIdadeMinOld(int idadeMinOld) {
		this.idadeMinOld = idadeMinOld;
	}

	public int getIdadeMaxOld() {
		return idadeMaxOld;
	}

	public void setIdadeMaxOld(int idadeMaxOld) {
		this.idadeMaxOld = idadeMaxOld;
	}
	
	public double getTaxaOld() {
		return taxaOld;
	}

	public void setTaxaOld(double taxaOld) {
		this.taxaOld = taxaOld;
	}
	
	@Override
	public boolean equals(Object obj) {

		if (obj instanceof TaxaSeguroFaixa) {
			TaxaSeguroFaixa taxaCompara = (TaxaSeguroFaixa) obj;
			
			if (!this.inicioVigencia.equals(taxaCompara.getInicioVigencia()))			
				return false;
			
			if (this.fimVigencia != null && taxaCompara.getFimVigencia() != null) {			
				if (!this.fimVigencia.equals(taxaCompara.getFimVigencia()))
					return false;
			} else { 
				if (this.fimVigencia != taxaCompara.getFimVigencia())
					return false;					
			}
			
			if (Math.abs(this.taxa - taxaCompara.getTaxa()) > EPSILON)
				return false;			
			
			return true;
		} else {
			return super.equals(obj);
		}
		
	}

	public Date getDtInicioVigencia() {
		return dtInicioVigencia;
	}

	public void setDtInicioVigencia(Date dtInicioVigencia) {
		this.dtInicioVigencia = dtInicioVigencia;
	}

	public Date getDtFimVigencia() {
		return dtFimVigencia;
	}

	public void setDtFimVigencia(Date dtFimVigencia) {
		this.dtFimVigencia = dtFimVigencia;
	}
	
	
}
