package br.gov.caixa.ccr.dominio;

import java.util.Calendar;
import java.util.Date;

public class TaxaSeguro {

	private static final float EPSILON = 0.000009F;

	private int prazo;
	private int idade;
	private Date inicioVigencia;
	private double taxa;
	private Date fimVigencia;
	private String usuarioInclusao;
	private Date dataInclusaoTaxa;
	private String usuarioFinalizacao;
	private Date dataInclusaoFimVigencia;
	
	public int getPrazo() {
		return prazo;
	}

	public void setPrazo(int prazo) {
		this.prazo = prazo;
	}

	public int getIdade() {
		return idade;
	}

	public void setIdade(int idade) {
		this.idade = idade;
	}

	public Date getInicioVigencia() {
		return inicioVigencia;
	}

	public void setInicioVigencia(Date inicioVigencia) {
		this.inicioVigencia = inicioVigencia;
	}

	public double getTaxa() {
		return taxa;
	}

	public void setTaxa(double taxa) {
		this.taxa = taxa;
	}

	public Date getFimVigencia() {
		return fimVigencia;
	}

	public void setFimVigencia(Date fimVigencia) {
		this.fimVigencia = fimVigencia;
	}

	public String getUsuarioInclusao() {
		return usuarioInclusao;
	}

	public void setUsuarioInclusao(String usuarioInclusao) {
		this.usuarioInclusao = usuarioInclusao;
	}

	public Date getDataInclusaoTaxa() {
		return dataInclusaoTaxa;
	}

	public void setDataInclusaoTaxa(Date dataInclusaoTaxa) {
		this.dataInclusaoTaxa = dataInclusaoTaxa;
	}

	public String getUsuarioFinalizacao() {
		return usuarioFinalizacao;
	}

	public void setUsuarioFinalizacao(String usuarioFinalizacao) {
		this.usuarioFinalizacao = usuarioFinalizacao;
	}

	public Date getDataInclusaoFimVigencia() {
		return dataInclusaoFimVigencia;
	}

	public void setDataInclusaoFimVigencia(Date dataInclusaoFimVigencia) {
		this.dataInclusaoFimVigencia = dataInclusaoFimVigencia;
	}

	public Boolean isUpdatable() {
		
		Date dtNow = new Date();
		
//		Calendar cal = Calendar.getInstance();
//		cal.setTime(dtNow);
//		cal.add(Calendar.DATE, +1);
//		Date calMais1D = cal.getTime();
		
		if(this.fimVigencia == null || 
				(this.fimVigencia != null && this.fimVigencia.equals("")) ){
			return true;
		}else{
			
			if(this.inicioVigencia.after(dtNow)){
				return true;
			}else if(this.inicioVigencia.after(dtNow)){
				return false;
			}else{
				return false;
			}
		}
		//return this.fimVigencia == null;
		
	}

	public Boolean isExcludable() {
		if(this.fimVigencia != null){
			return false;
		}else{
			//return isUpdatable();
			return true;
			
		}
	}

	@Override
	public boolean equals(Object obj) {

		if (obj instanceof TaxaSeguro) {
			TaxaSeguro taxaCompara = (TaxaSeguro) obj;
			
			if (this.inicioVigencia.compareTo(taxaCompara.getInicioVigencia()) != 0)			
				return false;
			
			if (this.fimVigencia != null && taxaCompara.getFimVigencia() != null) {			
				if (this.fimVigencia.compareTo(taxaCompara.getFimVigencia()) != 0)
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
	
	
}
