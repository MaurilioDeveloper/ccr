package br.gov.caixa.ccr.dominio;

public class Retorno {

	// Os nomes das variáveis está fora do padrão 
	// pois os nomes comuns (mensagem, codigo, tipo) estavam causando incompatibilidade nos nomes ao estender a classe retorno
	
	private String mensagemRetorno = "";
	private Long codigoRetorno = 0L;
	private String tipoRetorno = SUCESSO;
	private String idMsg = ""; // id da msg cadastrada nos enums -> ccr-web/src/main/webapp/servicos/enumeracoes/eMensagemCCR.js
	
	public static final String SUCESSO = "SUCESSO";
	public static final String ERRO_NEGOCIAL = "ERRO_NEGOCIAL";	
	public static final String ERRO_EXCECAO = "ERRO_EXCECAO";

	public Retorno() {

	}

	public Retorno(Long codigo, String mensagem, String tipo) {
		this.codigoRetorno = codigo;
		this.mensagemRetorno = mensagem;
		this.tipoRetorno = tipo;
	}
	
	public Retorno(Long codigo, String mensagem, String tipo, String idMsg) {
		this.codigoRetorno = codigo;
		this.mensagemRetorno = mensagem;
		this.tipoRetorno = tipo;
		this.idMsg = idMsg;
	}

	public String getMensagemRetorno() {
		return mensagemRetorno;
	}

	public void setMensagemRetorno(String mensagem) {
		this.mensagemRetorno = mensagem;
	}

	public Long getCodigoRetorno() {
		return codigoRetorno;
	}

	public void setCodigoRetorno(Long codigo) {
		this.codigoRetorno = codigo;
	}

	public String getTipoRetorno() {
		return tipoRetorno;
	}

	public void setTipoRetorno(String tipo) {
		this.tipoRetorno = tipo;
	}

	public String getIdMsg() {
		return idMsg;
	}

	public void setIdMsg(String idMsg) {
		this.idMsg = idMsg;
	}	
	
	public void setAll(Long codigo, String mensagem, String tipo) {
		this.codigoRetorno = codigo;
		this.mensagemRetorno = mensagem;
		this.tipoRetorno = tipo;
	}
	
	public void setAll(Long codigo, String mensagem, String tipo, String idMsg) {
		this.codigoRetorno = codigo;
		this.mensagemRetorno = mensagem;
		this.tipoRetorno = tipo;
		this.idMsg = idMsg;
	}

}
