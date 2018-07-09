package br.gov.caixa.ccr.dominio.transicao;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;

@Entity
@Table(name="CCRTB008_TAXA_IOF")
@NamedNativeQueries({
	@NamedNativeQuery(name="TaxaIOF.listarDtIni", query="SELECT * FROM CCR.CCRTB008_TAXA_IOF WHERE DT_INICIO_VIGENCIA >= ?", resultClass=TaxaIOFTO.class),
	@NamedNativeQuery(name="TaxaIOF.listarDtFim", query="SELECT * FROM CCR.CCRTB008_TAXA_IOF WHERE (DT_FIM_VIGENCIA  >= ? OR DT_FIM_VIGENCIA IS NULL)", resultClass=TaxaIOFTO.class),
	@NamedNativeQuery(name="TaxaIOF.listarDtIniDtFim", query="SELECT * FROM CCR.CCRTB008_TAXA_IOF WHERE DT_INICIO_VIGENCIA >= ? AND (DT_FIM_VIGENCIA  >= ? OR DT_FIM_VIGENCIA IS NULL)", resultClass=TaxaIOFTO.class)
})
public class TaxaIOFTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="NU_TAXA_IOF", nullable=false, unique=true, length=5)
	private int id;
	
	@Column(name="PC_ALIQUOTA_BASICA", precision=8, scale=5)
	private double aliquotaBasica;
	
	@Column(name="PC_ALIQUOTA_COMPLEMENTAR", precision=8, scale=5)
	private double aliquotaComplementar;
	
	@Column(name="DT_INICIO_VIGENCIA")
	private Date inicioVigencia;
	
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

}
