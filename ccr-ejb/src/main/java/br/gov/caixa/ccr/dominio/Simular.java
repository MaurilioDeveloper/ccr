package br.gov.caixa.ccr.dominio;

import java.util.Calendar;
import java.util.Date;

import br.gov.caixa.ccr.util.DataUtil;

public class Simular {
	
	private String grupo;	
	private String idConvenio;	
	private String aposentadoInvalidez;	
	private String dataNascimento;	
	private String valorMargem;	
	private String valorLiquido;	
	private String valorPrestacao;	
	private String prazo;	
	private String taxaJuros;	
	private String usuario;	
	private String cpfCliente;	
	private String beneficio;	
	private String agenciaMovimento;	
	private String dataContratacao;

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}


	public String getIdConvenio() {
		return idConvenio;
	}

	public void setIdConvenio(String idConvenio) {
		this.idConvenio = idConvenio;
	}

	public String getAposentadoInvalidez() {
		return aposentadoInvalidez;
	}

	public void setAposentadoInvalidez(String aposentadoInvalidez) {
		this.aposentadoInvalidez = aposentadoInvalidez;
	}

	public String getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getValorMargem() {
		return valorMargem;
	}

	public void setValorMargem(String valorMargem) {
		this.valorMargem = valorMargem;
	}

	public String getValorLiquido() {
		return valorLiquido;
	}

	public void setValorLiquido(String valorLiquido) {
		this.valorLiquido = valorLiquido;
	}

	public String getValorPrestacao() {
		return valorPrestacao;
	}

	public void setValorPrestacao(String valorPrestacao) {
		this.valorPrestacao = valorPrestacao;
	}

	public String getPrazo() {
		return prazo;
	}

	public void setPrazo(String prazo) {
		this.prazo = prazo;
	}

	public String getTaxaJuros() {
		return taxaJuros;
	}

	public void setTaxaJuros(String taxaJuros) {
		this.taxaJuros = taxaJuros;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getCpfCliente() {
		return cpfCliente;
	}

	public void setCpfCliente(String cpfCliente) {
		this.cpfCliente = cpfCliente;
	}

	public String getBeneficio() {
		return beneficio;
	}

	public void setBeneficio(String beneficio) {
		this.beneficio = beneficio;
	}

	public String getAgenciaMovimento() {
		return agenciaMovimento;
	}

	public void setAgenciaMovimento(String agenciaMovimento) {
		this.agenciaMovimento = agenciaMovimento;
	}

	public String getDataContratacao() {
		return dataContratacao;
	}

	public void setDataContratacao(String dataContratacao) {
		this.dataContratacao = dataContratacao;
	}

	public Boolean convenioFoiInformado() {
		try {
			return !(this.idConvenio == null || this.idConvenio.trim().equals("")) && (Long.parseLong(this.idConvenio) > 0);
		} catch (Exception e) {
			return false;
		}
	}

	public Date dataContratacaoConvertida() {
		Calendar cal = Calendar.getInstance();
		
		try {
			if (DataUtil.converter(this.dataContratacao, DataUtil.PADRAO_DATA_BARRAMENTO).before(cal.getTime()))
				return cal.getTime(); 
			else
				return DataUtil.converterData(this.dataContratacao);
		} catch (Exception e) {
			return cal.getTime();
		}
	}
	
	public Boolean simulacaoPorValorLiquido() {
		if (this.valorLiquido == null || this.valorLiquido.isEmpty() )
			return false;
		else
			return true;
			
	}
	
}
