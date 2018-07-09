package br.gov.caixa.ccr.dominio.transicao;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="CCRTB007_TAXA_SEGURO")
public class TaxaSeguroTO implements Serializable {
	
	private static final long serialVersionUID = -2021905723887730399L;

	@Id
	@Column(name="PZ_TAXA_SEGURO", nullable=false)
	private int prazo;
	
	@Id
	@Column(name="NU_IDADE", nullable=false)
	private int idade;
	
	@Id
	@Column(name="DT_INICIO_VIGENCIA", nullable=false)
	private Date inicioVigencia;
	
	@Column(name="PC_TAXA_SEGURO", nullable=false, precision=8, scale=5)
	private double taxa;
		
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

	public void setTaxa(Float taxa) {
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
	
}
