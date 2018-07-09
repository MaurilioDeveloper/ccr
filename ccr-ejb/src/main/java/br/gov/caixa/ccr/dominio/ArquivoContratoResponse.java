package br.gov.caixa.ccr.dominio;

import java.util.Date;
import java.util.List;

public class ArquivoContratoResponse extends Retorno {

	private long sequencial;
	private String identificador;
	private Date dtRemessa;
	private long quantidadeRemessa;
	private Date dtRetorno;
	private long quantidadeRetorno;
	private long errosRetorno;
	private String situacaoRetorno;
	private List<ArquivoContratoErro> arquivoContratoErro;
	
	/**
	 * @return the sequencial
	 */
	public long getSequencial() {
		return sequencial;
	}
	/**
	 * @param sequencial the sequencial to set
	 */
	public void setSequencial(long sequencial) {
		this.sequencial = sequencial;
	}
	/**
	 * @return the identificador
	 */
	public String getIdentificador() {
		return identificador;
	}
	/**
	 * @param identificador the identificador to set
	 */
	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}
	
	public long getQuantidadeRemessa() {
		return quantidadeRemessa;
	}
	/**
	 * @param quantidadeRemessa the quantidadeRemessa to set
	 */
	public void setQuantidadeRemessa(long quantidadeRemessa) {
		this.quantidadeRemessa = quantidadeRemessa;
	}
	/**
	 * @return the quantidadeRetorno
	 */
	public long getQuantidadeRetorno() {
		return quantidadeRetorno;
	}
	/**
	 * @param quantidadeRetorno the quantidadeRetorno to set
	 */
	public void setQuantidadeRetorno(long quantidadeRetorno) {
		this.quantidadeRetorno = quantidadeRetorno;
	}
	/**
	 * @return the errosRetorno
	 */
	public long getErrosRetorno() {
		return errosRetorno;
	}
	/**
	 * @param errosRetorno the errosRetorno to set
	 */
	public void setErrosRetorno(long errosRetorno) {
		this.errosRetorno = errosRetorno;
	}
	/**
	 * @return the situacaoRetorno
	 */
	public String getSituacaoRetorno() {
		return situacaoRetorno;
	}
	/**
	 * @param situacaoRetorno the situacaoRetorno to set
	 */
	public void setSituacaoRetorno(String situacaoRetorno) {
		this.situacaoRetorno = situacaoRetorno;
	}
	/**
	 * @return the arquivoContratoErro
	 */
	public List<ArquivoContratoErro> getArquivoContratoErro() {
		return arquivoContratoErro;
	}
	/**
	 * @param arquivoContratoErro the arquivoContratoErro to set
	 */
	public void setArquivoContratoErro(List<ArquivoContratoErro> arquivoContratoErro) {
		this.arquivoContratoErro = arquivoContratoErro;
	}
	/**
	 * @return the dtRemessa
	 */
	public Date getDtRemessa() {
		return dtRemessa;
	}
	/**
	 * @param dtRemessa the dtRemessa to set
	 */
	public void setDtRemessa(Date dtRemessa) {
		this.dtRemessa = dtRemessa;
	}
	/**
	 * @return the dtRetorno
	 */
	public Date getDtRetorno() {
		return dtRetorno;
	}
	/**
	 * @param dtRetorno the dtRetorno to set
	 */
	public void setDtRetorno(Date dtRetorno) {
		this.dtRetorno = dtRetorno;
	}
	
	
	
}
