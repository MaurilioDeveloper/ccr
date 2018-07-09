package br.gov.caixa.ccr.dominio;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

import br.gov.caixa.arqrefservices.dominio.sicli.EstadoCivil;


public class EstadoCivilImpressao  implements Serializable{
	private static final long serialVersionUID = -8386405850539729449L;
	private static final String NAO_INFORMADO = "Não Informado";
	private static final String SOLTEIRO = "Solteiro (a)";
	private static final String CASADO = "Casado (a)";
	private static final String DIVORCIADO = "Divorciado (a)";
	private static final String SEPARADO_JUDICIALMENTE = "Separado (a) Judicialmente";
	private static final String VIUVO = "Viúvo (a)";
	private static final String UNIAO_INSTAVEL = "Com União Estável";
	private static final String CASADO_COMUNHAO_TOTAL = "Casado (a) com comunhão total de bens";
	private static final String CASADO_SEM_COMUNHAO = "Casado (a) sem comunhão de bens";
	private static final String CASAD_COMUNHAO_PARCIAL = "Casado (a) com comunhão parcial de bens";
	
	private String estadoCivil;
	

	public EstadoCivilImpressao(EstadoCivil estadoCivil2) throws IllegalAccessException, InvocationTargetException {
		if(estadoCivil2 != null){
			BeanUtils.copyProperties(this, estadoCivil2);
		}
	}

	public String getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(String estadoCivil) {
		switch (estadoCivil) {
		case "0":
			this.estadoCivil  = NAO_INFORMADO;
			break;
		case "1":
			this.estadoCivil = SOLTEIRO;
			break;
		case "2":
			this.estadoCivil = CASADO;
			break;
		case "3":
			this.estadoCivil = DIVORCIADO;
			break;
		case "4":
			this.estadoCivil = SEPARADO_JUDICIALMENTE;
			break;
		case "5":
			this.estadoCivil = VIUVO;
			break;
		case "6":
			this.estadoCivil = UNIAO_INSTAVEL;
			break;
		case "7":
			this.estadoCivil = CASADO_COMUNHAO_TOTAL;
			break;
		case "8":
			this.estadoCivil = CASADO_SEM_COMUNHAO;
			break;
		case "9":
			this.estadoCivil = CASAD_COMUNHAO_PARCIAL;
			break;
		}
	}

	@Override
	public String toString() {
		return "EstadoCivil [estadoCivil=" + estadoCivil + "]";
	}
}
