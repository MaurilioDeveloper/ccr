package br.gov.caixa.ccr.dominio.transicao;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;

import br.gov.caixa.ccr.dominio.Unidade;



/**
 * The persistent class for the CCRVW001_UNIDADE database table.
 * Tipos Unidades:
 * 	8 - Agencia
 *  42 - SRs
 * 
 */
@Table(name="CCRVW001_UNIDADE")
@Entity
@NamedNativeQueries({
	@NamedNativeQuery(name="UnidadeTO.verificaSRPorNuUnidade", query="SELECT * FROM CCR.CCRVW001_UNIDADE where  NU_UNIDADE = ? AND  NU_TP_UNIDADE_U21 = 42 AND IC_ULTIMA_SITUACAO = 'AT' ", resultClass=UnidadeTO.class),
	@NamedNativeQuery(name="UnidadeTO.verificaAgPorNuUnidade", query="SELECT * FROM CCR.CCRVW001_UNIDADE where  NU_UNIDADE = ? AND  NU_TP_UNIDADE_U21 = 8 AND IC_ULTIMA_SITUACAO = 'AT' ", resultClass=UnidadeTO.class),
	@NamedNativeQuery(name="UnidadeTO.consultaPorNuUnidade", query="SELECT * FROM CCR.CCRVW001_UNIDADE where  NU_UNIDADE = ? ", resultClass=UnidadeTO.class)
})
public class UnidadeTO implements Serializable {

	/**
	 * 
	 */
	
	public static final String QUERY_CONSULTA_POR_NUUNIDADE = "SELECT NU_NATURAL FROM CCR.CCRVW001_UNIDADE where  NU_UNIDADE = ?";
	
	private static final long serialVersionUID = -5855765065424090074L;

	@Column(name="IC_ULTIMA_SITUACAO")
	private String indicadorUltimaSituacao;

	@Column(name="NO_UNIDADE")
	private String nomeUnidade;

	@Column(name="NU_CGC_UNIDADE")
	private Long numCgcUnidade;

	@Column(name="NU_DV_CGC")
	private Long numDvCgc;

	@Column(name="NU_DV_NU_UNIDADE")
	private Long numDvNuUnidade;

	@Column(name="NU_NATURAL")
	private Long numNatural;

	@Column(name="NU_TP_UNIDADE_U21")
	private Long numTpUnidadeU21;

	@Column(name="NU_UNIDADE")
	@Id
	private Long idUnidade;

	@Column(name="SG_UF_L22")
	private String ufL22;

	@Column(name="SG_UNIDADE")
	private String unidade;

	public UnidadeTO() {
	}

	public String getIndicadorUltimaSituacao() {
		return this.indicadorUltimaSituacao;
	}

	public void setIndicadorUltimaSituacao(String indicadorUltimaSituacao) {
		this.indicadorUltimaSituacao = indicadorUltimaSituacao;
	}

	public String getNomeUnidade() {
		return this.nomeUnidade;
	}

	public void setNomeUnidade(String nomeUnidade) {
		this.nomeUnidade = nomeUnidade;
	}

	public Long getNumCgcUnidade() {
		return this.numCgcUnidade;
	}

	public void setNumCgcUnidade(Long numCgcUnidade) {
		this.numCgcUnidade = numCgcUnidade;
	}

	public Long getNumDvCgc() {
		return this.numDvCgc;
	}

	public void setNumDvCgc(Long numDvCgc) {
		this.numDvCgc = numDvCgc;
	}

	public Long getNumDvNuUnidade() {
		return this.numDvNuUnidade;
	}

	public void setNumDvNuUnidade(Long numDvNuUnidade) {
		this.numDvNuUnidade = numDvNuUnidade;
	}

	public Long getNumNatural() {
		return this.numNatural;
	}

	public void setNumNatural(Long numNatural) {
		this.numNatural = numNatural;
	}

	public Long getNumTpUnidadeU21() {
		return this.numTpUnidadeU21;
	}

	public void setNumTpUnidadeU21(Long numTpUnidadeU21) {
		this.numTpUnidadeU21 = numTpUnidadeU21;
	}

	public String getUfL22() {
		return this.ufL22;
	}

	public void setUfL22(String ufL22) {
		this.ufL22 = ufL22;
	}

	public String getUnidade() {
		return this.unidade;
	}

	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}

	public Long getIdUnidade() {
		return idUnidade;
	}

	public void setIdUnidade(Long idUnidade) {
		this.idUnidade = idUnidade;
	}

	/**
	 * Metodo que retorna a unidade convertida
	 * 
	 * @return
	 */
	public Unidade convert() {
	
		Unidade unidadeVo = new Unidade();
		
		unidadeVo.setIdUnidade(idUnidade);
		unidadeVo.setIndicadorUltimaSituacao(indicadorUltimaSituacao);
		unidadeVo.setNomeUnidade(nomeUnidade);
		unidadeVo.setNumCgcUnidade(numCgcUnidade);
		unidadeVo.setNumDvCgc(numDvCgc);
		unidadeVo.setNumDvNuUnidade(numDvNuUnidade);
		unidadeVo.setNumNatural(numNatural);
		unidadeVo.setNumTpUnidadeU21(numTpUnidadeU21);
		unidadeVo.setUfL22(ufL22);
		unidadeVo.setSgUnidade(unidade);
		
		return unidadeVo;
	}
}