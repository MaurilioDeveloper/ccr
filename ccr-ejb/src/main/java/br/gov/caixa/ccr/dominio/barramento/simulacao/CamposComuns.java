package br.gov.caixa.ccr.dominio.barramento.simulacao;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="CAMPOS_COMUNS")
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class CamposComuns implements Serializable {

	private static final long serialVersionUID = -8828163504055281941L;
	
	private String hora;
	private String data;
	private String basePrimeiraPrestacao;
	private String vencimentoPrimeiraPrestacao;
	private String quantDiasJurosAcerto;
	private String taxaSeguro;
	private String iofAliquotaBasica;
	private String iofAliquotaComplementar;
	
	@XmlElement(name="HORA_SIMULACAO")
	public String getHora() {
		return hora;
	}
	
	public void setHora(String hora) {
		this.hora = hora;
	}
	
	@XmlElement(name="DTA_SIMULACAO")
	public String getData() {
		return data;
	}
	
	public void setData(String data) {
		this.data = data;
	}
	
	@XmlElement(name="DTA_BASE_CALC_1A_PREST")
	public String getBasePrimeiraPrestacao() {
		return basePrimeiraPrestacao;
	}
	
	public void setBasePrimeiraPrestacao(String basePrimeiraPrestacao) {
		this.basePrimeiraPrestacao = basePrimeiraPrestacao;
	}
	
	@XmlElement(name="DTA_VENC_PRIM_PREST")
	public String getVencimentoPrimeiraPrestacao() {
		return vencimentoPrimeiraPrestacao;
	}
	
	public void setVencimentoPrimeiraPrestacao(String vencimentoPrimeiraPrestacao) {
		this.vencimentoPrimeiraPrestacao = vencimentoPrimeiraPrestacao;
	}
	
	@XmlElement(name="QTD_DIAS_JURO_ACERTO")
	public String getQuantDiasJurosAcerto() {
		return quantDiasJurosAcerto;
	}
	
	public void setQuantDiasJurosAcerto(String quantDiasJurosAcerto) {
		this.quantDiasJurosAcerto = quantDiasJurosAcerto;
	}

	public String getTaxaSeguro() {
		return taxaSeguro;
	}

	public void setTaxaSeguro(String taxaSeguro) {
		this.taxaSeguro = taxaSeguro;
	}

	public String getIofAliquotaBasica() {
		return iofAliquotaBasica;
	}

	public void setIofAliquotaBasica(String iofAliquotaBasica) {
		this.iofAliquotaBasica = iofAliquotaBasica;
	}

	public String getIofAliquotaComplementar() {
		return iofAliquotaComplementar;
	}

	public void setIofAliquotaComplementar(String iofAliquotaComplementar) {
		this.iofAliquotaComplementar = iofAliquotaComplementar;
	}
		
}
