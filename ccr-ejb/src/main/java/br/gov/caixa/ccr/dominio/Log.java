package br.gov.caixa.ccr.dominio;

import java.util.Date;

import br.gov.caixa.ccr.util.DataUtil;
import br.gov.caixa.ccr.util.Utilities;

public class Log extends Retorno {
	
	private Long id;	
	private Integer transacao;	
	private Date data;	
	private String acao;	
	private String ocorrencia;
	private String resumo;	
	private String detalhe;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Integer getTransacao() {
		return transacao;
	}

	public void setTransacao(Integer transacao) {
		this.transacao = transacao;
	}

	public String getData() {
		return DataUtil.formatarDataHora(data);
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getAcao() {
		return acao;
	}

	public void setAcao(String acao) {
		this.acao = acao;
	}

	public String getOcorrencia() {
		return ocorrencia;
	}

	public void setOcorrencia(String ocorrencia) {
		this.ocorrencia = ocorrencia;
	}

	public String getResumo() {
		return Utilities.leftRightTrim(resumo);
	}

	public void setResumo(String resumo) {
		this.resumo = resumo;
	}

	public String getDetalhe() {
		return Utilities.leftRightTrim(detalhe);
	}

	public void setDetalhe(String detalhe) {
		this.detalhe = detalhe;
	}
	
}
