package br.gov.caixa.ccr.dominio;

import java.io.Serializable;
/**
 * 
 * @author TIVIT
 * @version 1.0.0.0
 * 
 *          Unidade da Caixa Economica Federal objeto de dominio. Usado com
 *          objeto de transicao para consulta CCRVW001_UNIDADE
 * 
 */

public class Unidade extends Retorno  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 233394273691373571L;
	
	private String indicadorUltimaSituacao;
	private String nomeUnidade;
	private Long numCgcUnidade;
	private Long numDvCgc;
	private Long numDvNuUnidade;
	private Long numNatural;
	private Long numTpUnidadeU21;
	private Long idUnidade;
	private String ufL22;
	private String sgUnidade;
	
	public Unidade(Long codigo, String mensagem, String tipo) {
		super.setCodigoRetorno(codigo);
		super.setMensagemRetorno(mensagem);
		super.setTipoRetorno(tipo);
	}

	public Unidade() {
		// TODO Auto-generated constructor stub
	}

	public String getIndicadorUltimaSituacao() {
		return indicadorUltimaSituacao;
	}
	public void setIndicadorUltimaSituacao(String indicadorUltimaSituacao) {
		this.indicadorUltimaSituacao = indicadorUltimaSituacao;
	}
	public String getNomeUnidade() {
		return nomeUnidade;
	}
	public void setNomeUnidade(String nomeUnidade) {
		this.nomeUnidade = nomeUnidade;
	}
	public Long getNumCgcUnidade() {
		return numCgcUnidade;
	}
	public void setNumCgcUnidade(Long numCgcUnidade) {
		this.numCgcUnidade = numCgcUnidade;
	}
	public Long getNumDvCgc() {
		return numDvCgc;
	}
	public void setNumDvCgc(Long numDvCgc) {
		this.numDvCgc = numDvCgc;
	}
	public Long getNumDvNuUnidade() {
		return numDvNuUnidade;
	}
	public void setNumDvNuUnidade(Long numDvNuUnidade) {
		this.numDvNuUnidade = numDvNuUnidade;
	}
	public Long getNumNatural() {
		return numNatural;
	}
	public void setNumNatural(Long numNatural) {
		this.numNatural = numNatural;
	}
	public Long getNumTpUnidadeU21() {
		return numTpUnidadeU21;
	}
	public void setNumTpUnidadeU21(Long numTpUnidadeU21) {
		this.numTpUnidadeU21 = numTpUnidadeU21;
	}
	public Long getIdUnidade() {
		return idUnidade;
	}
	public void setIdUnidade(Long idUnidade) {
		this.idUnidade = idUnidade;
	}
	public String getUfL22() {
		return ufL22;
	}
	public void setUfL22(String ufL22) {
		this.ufL22 = ufL22;
	}
	public String getSgUnidade() {
		return sgUnidade;
	}
	public void setSgUnidade(String sgUnidade) {
		this.sgUnidade = sgUnidade;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	@Override
	public String toString() {
		return "Unidade [indicadorUltimaSituacao=" + indicadorUltimaSituacao + ", nomeUnidade=" + nomeUnidade
				+ ", numCgcUnidade=" + numCgcUnidade + ", numDvCgc=" + numDvCgc + ", numDvNuUnidade=" + numDvNuUnidade
				+ ", numNatural=" + numNatural + ", numTpUnidadeU21=" + numTpUnidadeU21 + ", idUnidade=" + idUnidade
				+ ", ufL22=" + ufL22 + ", sgUnidade=" + sgUnidade + "]";
	}
	
}
