package br.gov.caixa.ccr.dominio.barramento.alteracaocliente;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import br.gov.caixa.arqrefcore.cobol.conversor.anotacao.Tamanho;
import br.gov.caixa.arqrefcore.excecao.BusinessException;
import br.gov.caixa.arqrefcore.excecao.mensagem.Message;

@XmlRootElement
public class MensagemSiemp implements Serializable {

	private static final long serialVersionUID = -2140220681616933354L;

	@Tamanho(tamanho = 4)
	private Integer numeroMensagemCICS;

	@Tamanho(tamanho = 78)
	private String descricaoMensagemCICS;

	@Tamanho(tamanho = 4)
	private Integer numeroGrupoMensagem;

	@Tamanho(tamanho = 4)
	private Integer numeroTipoMensagem;

	@Tamanho(tamanho = 4)
	private Integer numeroMensagemErro;

	@Tamanho(tamanho = 250)
	private String descricaoMensagemErro;

	@Tamanho(tamanho = 250)
	private String descricaoDaAcao;

	@Tamanho(tamanho = 250)
	private String nomeMensagem;

	@Tamanho(tamanho = 1)
	private String indicativoExibicao;

	@Tamanho(tamanho = 50)
	private String nomeProcesso;

	@Tamanho(tamanho = 8)
	private String nomePrograma;

	@Tamanho(tamanho = 8)
	private String nomeTabela;

	@Tamanho(tamanho = 70)
	private String reasonCode;
	
	@Tamanho(tamanho = 3)
	private String codigoSQL;

	@Tamanho(tamanho = 4)
	private String sqlCode = "0";
	
	@Tamanho(tamanho = 26)
	private String controleDaAtualizacao;
	
	@Tamanho(tamanho = 50)
	private String nomeDaMensagemDeErro;
	
	public static MensagemSiemp getMensagemSiemp(BusinessException message) {
		Message m = message.getMessages().getMessages().get(0);
		MensagemSiemp msg = new MensagemSiemp();
		msg.setNumeroMensagemErro(Integer.valueOf(m.getCategoriaErro()));
		msg.setDescricaoMensagemErro(m.getParagrafoErro());
		msg.setDescricaoDaAcao(m.getInformacoesAdicionais());
		return msg; 
	}
	public Integer getNumeroGrupoMensagem() {
		return numeroGrupoMensagem;
	}

	public void setNumeroGrupoMensagem(Integer numeroGrupoMensagem) {
		this.numeroGrupoMensagem = numeroGrupoMensagem;
	}

	public Integer getNumeroTipoMensagem() {
		return numeroTipoMensagem;
	}

	public void setNumeroTipoMensagem(Integer numeroTipoMensagem) {
		this.numeroTipoMensagem = numeroTipoMensagem;
	}

	public String getDescricaoMensagemErro() {
		if (descricaoMensagemErro == null
				|| descricaoMensagemErro.trim().isEmpty())
			return descricaoMensagemCICS;
		return descricaoMensagemErro;
	}

	public void setDescricaoMensagemErro(String descricaoMensagemErro) {
		this.descricaoMensagemErro = descricaoMensagemErro;
	}

	public String getDescricaoDaAcao() {
		return descricaoDaAcao;
	}

	public void setDescricaoDaAcao(String descricaoDaAcao) {
		this.descricaoDaAcao = descricaoDaAcao;
	}

	public String getNomePrograma() {
		return nomePrograma;
	}

	public void setNomePrograma(String nomePrograma) {
		this.nomePrograma = nomePrograma;
	}

	public String getNomeTabela() {
		return nomeTabela;
	}

	public void setNomeTabela(String nomeTabela) {
		this.nomeTabela = nomeTabela;
	}

	public String getReasonCode() {
		return reasonCode;
	}

	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}

	/**
	 * Avalisa se a resposta da comunicação com o main frame contem erro.
	 * 
	 * @return TRUE se contiver erro.
	 * @since 17/06/2013
	 */
	public boolean isContemMensagemErro() {
		return !getDescricaoMensagemErro().trim().isEmpty();
	}

	public Integer getNumeroMensagemCICS() {
		return numeroMensagemCICS;
	}

	public void setNumeroMensagemCICS(Integer numeroMensagemCICS) {
		this.numeroMensagemCICS = numeroMensagemCICS;
	}

	public String getDescricaoMensagemCICS() {
		if (descricaoMensagemCICS == null) {
			this.descricaoMensagemCICS = "";
		}
		return descricaoMensagemCICS;
	}

	public void setDescricaoMensagemCICS(String descricaoMensagemCICS) {
		this.descricaoMensagemCICS = descricaoMensagemCICS;
	}

	public Integer getNumeroMensagemErro() {
		if (numeroMensagemErro == null)
			return numeroMensagemCICS;
		return numeroMensagemErro;
	}

	public void setNumeroMensagemErro(Integer numeroMensagemErro) {
		this.numeroMensagemErro = numeroMensagemErro;
	}

	public String getNomeProcesso() {
		return nomeProcesso;
	}

	public void setNomeProcesso(String nomeProcesso) {
		this.nomeProcesso = nomeProcesso;
	}

	public String getSqlCode() {
		return sqlCode;
	}

	public void setSqlCode(String sqlCode) {
		this.sqlCode = sqlCode;
	}

	public String getIndicativoExibicao() {
		return indicativoExibicao;
	}

	public void setIndicativoExibicao(String indicativoExibicao) {
		this.indicativoExibicao = indicativoExibicao;
	}

	public String getNomeMensagem() {
		return nomeMensagem;
	}

	public void setNomeMensagem(String nomeMensagem) {
		this.nomeMensagem = nomeMensagem;
	}
	public String getControleDaAtualizacao() {
		return controleDaAtualizacao;
	}
	public void setControleDaAtualizacao(String controleDaAtualizacao) {
		this.controleDaAtualizacao = controleDaAtualizacao;
	}
	public String getNomeDaMensagemDeErro() {
		return nomeDaMensagemDeErro;
	}
	public void setNomeDaMensagemDeErro(String nomeDaMensagemDeErro) {
		this.nomeDaMensagemDeErro = nomeDaMensagemDeErro;
	}
	public String getCodigoSQL() {
		return codigoSQL;
	}
	public void setCodigoSQL(String codigoSQL) {
		this.codigoSQL = codigoSQL;
	}
	
}
