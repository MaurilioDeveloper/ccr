package br.gov.caixa.ccr.dominio.barramento.alteracaocliente;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import br.gov.caixa.arqrefservices.dominio.sicli.CarteiraCliente;
import br.gov.caixa.arqrefservices.dominio.sicli.CarteiraGRC;
import br.gov.caixa.arqrefservices.dominio.sicli.CocliAtivo;

@XmlRootElement(name="MANUTENCAL_INTEGRAL")
@XmlSeeAlso({ CocliAtivo.class, CarteiraCliente.class, CarteiraGRC.class })
public class ManutencaoIntegral implements Serializable {

	private static final long serialVersionUID = -6968726138860220681L;

	private CocliAtivo cocliAtivo;
	
	private CarteiraCliente carteiraCliente;
	
	private List<CarteiraGRC> listaCarteiraGRC;

	
	@XmlElement(name="COCLI_ATIVO")
	public CocliAtivo getCocliAtivo() {
		return this.cocliAtivo;
	}

	@XmlElement(name="CARTEIRA_CLIENTE")
	public CarteiraCliente getCarteiraCliente() {
		return this.carteiraCliente;
	}

	@XmlElement(name="CARTEIRA_GRC")
	public List<CarteiraGRC> getListaCarteiraGRC() {
		return this.listaCarteiraGRC;
	}

	public void setCocliAtivo(CocliAtivo cocliAtivo) {
		this.cocliAtivo = cocliAtivo;
	}

	public void setCarteiraCliente(CarteiraCliente carteiraCliente) {
		this.carteiraCliente = carteiraCliente;
	}

	public void setListaCarteiraGRC(List<CarteiraGRC> listaCarteiraGRC) {
		this.listaCarteiraGRC = listaCarteiraGRC;
	}
	
	
	
	
}
