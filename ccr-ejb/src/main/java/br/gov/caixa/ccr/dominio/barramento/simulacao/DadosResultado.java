package br.gov.caixa.ccr.dominio.barramento.simulacao;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import br.gov.caixa.ccr.dominio.Retorno;

@XmlRootElement(name="DADOS")
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class DadosResultado extends Retorno  {

	private static final long serialVersionUID = -7677138785996733299L;
	
	private CamposComuns camposComuns;	
	private SimulacaoComSeguro simulacaoComSeguro;	
	private SimulacaoSemSeguro simulacaoSemSeguro;
	private String excecao;
	
	public DadosResultado(){
		
	}
	
	public DadosResultado(Long codigo, String mensagem, String tipo, String idMsg) {
		super.setCodigoRetorno(codigo);
		super.setMensagemRetorno(mensagem);
		super.setTipoRetorno(tipo);
		super.setIdMsg(idMsg);
	}
	

	@XmlElement(name="CAMPOS_COMUNS")
	public CamposComuns getCamposComuns() {
		return camposComuns;
	}

	public void setCamposComuns(CamposComuns camposComuns) {
		this.camposComuns = camposComuns;
	}

	@XmlElement(name="SIMULACAO_COM_SEGURO")
	public SimulacaoComSeguro getSimulacaoComSeguro() {
		return simulacaoComSeguro;
	}

	public void setSimulacaoComSeguro(SimulacaoComSeguro simulacaoComSeguro) {
		this.simulacaoComSeguro = simulacaoComSeguro;
	}

	@XmlElement(name="SIMULACAO_SEM_SEGURO")
	public SimulacaoSemSeguro getSimulacaoSemSeguro() {
		return simulacaoSemSeguro;
	}

	public void setSimulacaoSemSeguro(SimulacaoSemSeguro simulacaoSemSeguro) {
		this.simulacaoSemSeguro = simulacaoSemSeguro;
	}

	@XmlElement(name="EXCECAO")
	public String getExcecao() {
		return excecao;
	}

	public void setExcecao(String excecao) {
		this.excecao = excecao;
	}
	
	
	
}
