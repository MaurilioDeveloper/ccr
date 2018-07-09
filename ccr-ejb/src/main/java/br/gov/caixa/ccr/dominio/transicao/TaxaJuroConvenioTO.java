package br.gov.caixa.ccr.dominio.transicao;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="CCRTB006_TAXA_JURO_CONVENIO")
public class TaxaJuroConvenioTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private static final float EPSILON = 0.000009F;
		
	@Id
	@ManyToOne
	@JoinColumn(name = "NU_CONVENIO")
	private ConvenioTO convenio;
	
	@Id
	@Column(name="PZ_TAXA_CONVENIO", nullable=false, length=5)
	private int prazo;
	
	@Id
	@Column(name="DT_INICIO_VIGENCIA", nullable=false)
	private Date inicioVigencia;
	
	@Id
	@Column(name="IC_UTILIZACAO_TAXA", nullable=false, length=1)
	private short tipo;
		
	@Column(name="PC_TAXA_MINIMA", nullable=false, precision=8, scale=5)
	private Float pcMinimo;
	
	@Column(name="PC_TAXA_MEDIA", nullable=false, precision=8, scale=5)
	private Float pcMedio;
	
	@Column(name="PC_TAXA_MAXIMA", nullable=false, precision=8, scale=5)
	private Float pcMaximo;	
	
	@Column(name="DT_FIM_VIGENCIA")
	private Date fimVigencia;
	
	@Column(name="CO_USUARIO_INCLUSAO")
	private String usuarioInclusao;
	
	@Column(name="TS_INCLUSAO_TAXA")
	private Date dataInclusaoTaxa;
	
	@Column(name="CO_USUARIO_FINALIZACAO")
	private String usuarioFinalizacao;
	
	@Column(name="TS_INCLUSAO_FIM_VIGENCIA_TAXA")
	private Date dataInclusaoFimVigencia;
	
	public ConvenioTO getConvenio() {
		return convenio;
	}

	public void setConvenio(ConvenioTO convenio) {
		this.convenio = convenio;
	}

	public int getPrazo() {
		return prazo;
	}

	public void setPrazo(int prazo) {
		this.prazo = prazo;
	}

	public short getTipo() {
		return tipo;
	}

	public void setTipo(short tipo) {
		this.tipo = tipo;
	}
		
	public Float getPcMinimo() {
		return pcMinimo;
	}

	public void setPcMinimo(Float pcMinimo) {
		this.pcMinimo = pcMinimo;
	}

	public Float getPcMedio() {
		return pcMedio;
	}

	public void setPcMedio(Float pcMedio) {
		this.pcMedio = pcMedio;
	}

	public Float getPcMaximo() {
		return pcMaximo;
	}

	public void setPcMaximo(Float pcMaximo) {
		this.pcMaximo = pcMaximo;
	}

	public Date getInicioVigencia() {
		return inicioVigencia;
	}

	public void setInicioVigencia(Date inicioVigencia) {
		this.inicioVigencia = inicioVigencia;
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

	@Override
	public boolean equals(Object obj) {
		
		if (obj instanceof TaxaJuroConvenioTO) {
			TaxaJuroConvenioTO taxaCompara = (TaxaJuroConvenioTO) obj;
			
			if (this.inicioVigencia.compareTo(taxaCompara.getInicioVigencia()) != 0)			
				return false;
			
			if (this.fimVigencia != null && taxaCompara.getFimVigencia() != null) {			
				if (this.fimVigencia.compareTo(taxaCompara.getFimVigencia()) != 0)
					return false;
			} else { 
				if (!(this.fimVigencia == null && taxaCompara.getFimVigencia() == null))
					return false;					
			}
			
			if (this.tipo != taxaCompara.getTipo())
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
	
}
