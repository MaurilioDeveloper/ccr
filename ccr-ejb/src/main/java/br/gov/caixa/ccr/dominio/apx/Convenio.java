package br.gov.caixa.ccr.dominio.apx;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Classe de dominio
 * @author c110503
 *
 */
@XmlRootElement
public class Convenio implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3582412591540937501L;
	
	
	private Integer codigoConvenio;	
	
	private String descricaoConvenio;	
	
	private String prazoMaximo;	
	
	private String indicativoAtivo;
	
	private String descricaoIndicativoAtivo;	
	
	private Integer codigoSERPRO;
	
	private Integer tipoPortal;
	
	private List<Taxa> listaTaxas;
	
	private GrupoTaxa grupoTaxa;
	
	private String diaPagamento;
	
	private String prazoEmissaoExtrato;
	
	private String codigoGrupo;

	@XmlAttribute(name="CO-CONVENIO")	
	public Integer getCodigoConvenio() {
		return codigoConvenio;
	}

	public void setCodigoConvenio(Integer codigoConvenio) {
		this.codigoConvenio = codigoConvenio;
	}

	@XmlAttribute(name="NO-CONVENIO")
	public String getDescricaoConvenio() {
		return descricaoConvenio;
	}

	public void setDescricaoConvenio(String descricaoConvenio) {
		this.descricaoConvenio = descricaoConvenio;
	}

	@XmlAttribute(name="PZ-MAX")
	public String getPrazoMaximo() {
		return prazoMaximo;
	}

	public void setPrazoMaximo(String prazoMaximo) {
		this.prazoMaximo = prazoMaximo;
	}

	@XmlAttribute(name="IC-ATIVO")
	public String getIndicativoAtivo() {
		return indicativoAtivo;
	}

	public void setIndicativoAtivo(String indicativoAtivo) {
		this.indicativoAtivo = indicativoAtivo;
	}
	
	@XmlAttribute(name="DE-IC-ATIVO")
	public String getDescricaoIndicativoAtivo() {
		return descricaoIndicativoAtivo;
	}
	public void setDescricaoIndicativoAtivo(String descricaoIndicativoAtivo) {
		this.descricaoIndicativoAtivo = descricaoIndicativoAtivo;
	}

	@XmlAttribute(name="CO-SERPRO")
	public Integer getCodigoSERPRO() {
		return codigoSERPRO;
	}

	public void setCodigoSERPRO(Integer codigoSERPRO) {
		this.codigoSERPRO = codigoSERPRO;
	}
	
	@XmlTransient
	public List<Taxa> getListaTaxas() {
		return listaTaxas;
	}
	public void setListaTaxas(List<Taxa> listaTaxas) {
		this.listaTaxas = listaTaxas;
	}
	
	@XmlElement(name="TAXAS")
	public GrupoTaxa getGrupoTaxa() {
		return grupoTaxa;
	}

	public void setGrupoTaxa(GrupoTaxa grupoTaxa) {
		this.grupoTaxa = grupoTaxa;
	}
	
	@XmlAttribute(name="TP-PORTAL")
	public Integer getTipoPortal() {
		return tipoPortal;
	}
	public void setTipoPortal(Integer tipoPortal) {
		this.tipoPortal = tipoPortal;
	}	

	@XmlAttribute(name="DIA-PAGAMENTO")
	public String getDiaPagamento() {
		return diaPagamento;
	}

	public void setDiaPagamento(String diaPagamento) {
		this.diaPagamento = diaPagamento;
	}

	@XmlAttribute(name="PZ-EMISSAO-EXT")
	public String getPrazoEmissaoExtrato() {
		return prazoEmissaoExtrato;
	}

	public void setPrazoEmissaoExtrato(String prazoEmissaoExtrato) {
		this.prazoEmissaoExtrato = prazoEmissaoExtrato;
	}
		
	@XmlAttribute(name="CO-GRUPO")
	public String getCodigoGrupo() {
		return codigoGrupo;
	}

	public void setCodigoGrupo(String codigoGrupo) {
		this.codigoGrupo = codigoGrupo;
	}

	@Override
	public String toString() {
		return "Convenio [codigoConvenio=" + codigoConvenio
				+ ", descricaoConvenio=" + descricaoConvenio + ", prazoMaximo="
				+ prazoMaximo + ", indicativoAtivo=" + indicativoAtivo
				+ ", descricaoIndicativoAtivo=" + descricaoIndicativoAtivo
				+ ", codigoSERPRO=" + codigoSERPRO + ", tipoPortal="
				+ tipoPortal + ", listaTaxas=" + listaTaxas + ", grupoTaxa=" + grupoTaxa + ", diaPagamento="
				+ diaPagamento + ", prazoEmissaoExtrato=" + prazoEmissaoExtrato
				+ ", codigoGrupo=" + codigoGrupo + "]";
	}

}