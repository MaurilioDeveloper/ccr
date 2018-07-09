package br.gov.caixa.ccr.dominio;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import br.gov.caixa.ccr.util.DataUtil;

public class TaxaIOF extends Retorno {

	private int id;
	private double aliquotaBasica;
	private double aliquotaComplementar;
	private String inicioVigencia;
	private String fimVigencia;
	private Boolean updatable;
	private Boolean excludable;
	private String usuarioInclusao;
	private String dataInclusaoTaxa;
	private String usuarioFinalizacao;
	private String dataInclusaoFimVigencia;
	private Date dtInicioVigencia;
	private Date dtFinalVigencia;
	
	public int getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public double getAliquotaBasica() {
		return aliquotaBasica;
	}

	public void setAliquotaBasica(double aliquotaBasica) {
		this.aliquotaBasica = aliquotaBasica;
	}

	public double getAliquotaComplementar() {
		return aliquotaComplementar;
	}

	public void setAliquotaComplementar(double aliquotaComplementar) {
		this.aliquotaComplementar = aliquotaComplementar;
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
	
	public Boolean isUpdatable() {
		/*Calendar cal = Calendar.getInstance();
		Date inicioVigencia = new Date();
		//hoje
		//se fim vigencia em branco ---autorizado..
		
		//novo
		//se o inicio vigencia for maior que hoje
		try {
			inicioVigencia = DataUtil.converterData(this.inicioVigencia);
		} catch (ParseException e) {
			return this.updatable;
		}
		
		//return (this.fimVigencia == null && inicioVigencia.after(cal.getTime()));
		return (inicioVigencia.after(cal.getTime()));*/
		
		
		
		// Regra alterada para que seja possivel alterar uma taxa vigente
		Date fimVigencia = new Date();
		Calendar calendar = Calendar.getInstance();
	    int year = calendar.get(Calendar.YEAR);
	    int month = calendar.get(Calendar.MONTH);
	    int day = calendar.get(Calendar.DATE);
	    calendar.set(year, month, day, 00, 00, 00);
		
		try {
			if (this.fimVigencia != null) {
				fimVigencia = DataUtil.converterData(this.fimVigencia);
			}else {
				return true;
			}
			
		} catch (ParseException e) {
			return this.updatable;
		}
		
		SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
		String fimVigenciaStr = fmt.format(fimVigencia);
		String dataAgora = fmt.format(calendar.getTime());
		
		if(fimVigenciaStr.equals(dataAgora) || fimVigencia.compareTo(calendar.getTime()) > 0) {
			 System.out.println("fimVigencia is equal to calendar.getTime() OR fimVigencia is after calendar.getTime()");
			 return true;
		} 
		
		return false;				
	}

	public void setUpdatable(Boolean updatable) {
		this.updatable = updatable;
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

	public void isExcludable() {
		if(this.fimVigencia == null){ 
			this.excludable = true;
		}else{ 
			this.excludable = false;
			
		}
		
	}

	public Boolean getExcludable() {
		return excludable;
	}

	public void setExcludable(Boolean excludable) {
		this.excludable = excludable;
	}

	public Date getDtInicioVigencia() {
		return dtInicioVigencia;
	}

	public void setDtInicioVigencia(Date dtInicioVigencia) {
		this.dtInicioVigencia = dtInicioVigencia;
	}

	public Date getDtFinalVigencia() {
		return dtFinalVigencia;
	}

	public void setDtFinalVigencia(Date dtFinalVigencia) {
		this.dtFinalVigencia = dtFinalVigencia;
	}
	
	
	
	
}
