package br.gov.caixa.ccr.dominio;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.gov.caixa.ccr.util.DataUtil;

public class TaxaJuro extends Retorno {

	public final static String GRUPO = "GRUPO";
	public final static String CONVENIO = "CONVENIO";
	public final static String CONTRATACAO = "contratacao";//"1";
	public final static String RENOVACAO = "renovacao";//"2";

	private static final float EPSILON = 0.000009F;
	
	private int id;	
	private Long codigoTaxa;	
	private String tipoConsulta;	
	private String inicioVigencia;	
	private String fimVigencia;	
	private Date dtInicioVigencia;	
	private Date dtFimVigencia;	
	private int prazoMin;	
	private int prazoMax;	
	private double pcMinimo;	
	private double pcMedio;	
	private double pcMaximo;	
	private short tipoTaxa;	
	private Boolean updatable;
	private Boolean excludable;
	private String usuarioInclusao;
	private String dataInclusaoTaxa;
	private String usuarioFinalizacao;
	private String dataInclusaoFimVigencia;
	private String utilizarEm;
	
	private int prazoMinOld;
	private int prazoMaxOld;
	
		
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Long getCodigoTaxa() {
		return codigoTaxa;
	}

	public void setCodigoTaxa(Long codigo) {
		this.codigoTaxa = codigo;
	}

	public String getTipoConsulta() {
		return tipoConsulta;
	}

	public void setTipoConsulta(String tipoConsulta) {
		this.tipoConsulta = tipoConsulta;
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

	public double getPcMinimo() {
		return pcMinimo;
	}

	public void setPcMinimo(double pcMinimo) {
		this.pcMinimo = pcMinimo;
	}

	public double getPcMedio() {
		return pcMedio;
	}

	public void setPcMedio(double pcMedio) {
		this.pcMedio = pcMedio;
	}

	public double getPcMaximo() {
		return pcMaximo;
	}

	public void setPcMaximo(double pcMaximo) {
		this.pcMaximo = pcMaximo;
	}

	public short getTipoTaxa() {
		return tipoTaxa;
	}

	public void setTipoTaxa(short tipo) {
		this.tipoTaxa = tipo;
	}

	public void setUpdatable(Boolean updatable) {
		this.updatable = updatable;
	}

	public void setExcludable(Boolean excludable) {
		this.excludable = excludable;
	}
	
	public Boolean isUpdatable() {
		
		//return this.dataInclusaoFimVigencia == null ? true : false;		
		if(this.dtFimVigencia != null){
			
			try {
				Date dtFimVig = this.dtFimVigencia;
				if(dtFimVig != null && dtFimVig.after(new Date())){
					return true;
				}
			} catch (Exception e) {
				return false;
			}
		}else if(this.dataInclusaoFimVigencia == null){
			return true;
		} 
		
		return false;
	}

	public Boolean isExcludable() {		
		
		return this.dataInclusaoFimVigencia == null ? true : false;
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

	public void setDateInicioVigencia(Date inicioVigencia) throws Exception {
		this.inicioVigencia = DataUtil.formatarData(inicioVigencia);
	}
	
	public Date dateInicioVigencia() throws Exception {
		if (this.inicioVigencia.indexOf('/') > 0)
			return DataUtil.converterData(this.inicioVigencia);
		else			
			return DataUtil.converter(this.inicioVigencia, DataUtil.PADRAO_DATA_ISO);
	}
	
	public void setDateFimVigencia(Date fimVigencia) throws Exception {
		this.fimVigencia = DataUtil.formatarData(fimVigencia);
	}
	
	public Date dateFimVigencia() throws Exception {
		if (this.fimVigencia.indexOf('/') > 0)
			return DataUtil.converterData(this.fimVigencia);
		else			
			return DataUtil.converter(this.fimVigencia, DataUtil.PADRAO_DATA_ISO);
	}
	
	public Boolean possuiFimVigencia() {
		return this.fimVigencia != null && this.fimVigencia.trim() != "";
	}
	
	public Boolean tipoConsultaIsGrupo(){
		return this.tipoConsulta.equals(TaxaJuro.GRUPO);
	}
	
	public void setDateInclusaoTaxa(Date inclusaoTaxa) {
		this.dataInclusaoTaxa = DataUtil.formatarDataHora(inclusaoTaxa);
	}	
	
	public void setDateInclusaoFimVigencia(Date inclusaoFimVigencia) {
		this.dataInclusaoFimVigencia = DataUtil.formatarDataHora(inclusaoFimVigencia);//"dd/MM/yyyy HH:mm:ss";
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
	
	@Override
	public boolean equals(Object obj) {
		Date inicioVigencia, fimVigencia;
		Date inicioVigenciaCompara, fimVigenciaCompara;
		
		try {
			inicioVigencia = this.dateInicioVigencia();
			fimVigencia = this.dateFimVigencia();
		} catch (Exception e) {
			return super.equals(obj);
		}
		
		if (obj instanceof TaxaJuro) {
			TaxaJuro taxaCompara = (TaxaJuro) obj;
			
			try {
				inicioVigenciaCompara = taxaCompara.dateInicioVigencia();
				fimVigenciaCompara = taxaCompara.dateFimVigencia();
			} catch (Exception e) {
				return super.equals(obj);
			}
			
			if (inicioVigencia != inicioVigenciaCompara)			
				return false;
			
			if (this.fimVigencia != null && fimVigenciaCompara != null) {			
				if (fimVigencia.compareTo(fimVigenciaCompara) != 0)
					return false;
			} else { 
				if (fimVigencia != fimVigenciaCompara)
					return false;					
			}
			
			if (this.tipoTaxa != taxaCompara.getTipoTaxa())
				return false;
			
			if (Math.abs(this.pcMinimo - taxaCompara.getPcMinimo()) > EPSILON)
				return false;
			
			if (Math.abs(this.pcMedio - taxaCompara.getPcMedio()) > EPSILON)
				return false;
			
			if (Math.abs(this.pcMaximo - taxaCompara.getPcMaximo()) > EPSILON)
				return false;
			
			return true;
		} else {
			return super.equals(obj);
		}
	}

	public String getUtilizarEm() {
		return utilizarEm;
	}

	public void setUtilizarEm(String utilizarEm) {
		this.utilizarEm = utilizarEm;
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
