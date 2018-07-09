package br.gov.caixa.ccr.dominio;

import java.util.List;

public class UserProfile {
	
	private Empregado empregado;
	private List<String> grupoUsuarioList;

	public Empregado getEmpregado() {
		return empregado;
	}

	public void setEmpregado(Empregado empregado) {
		this.empregado = empregado;
	}

	public List<String> getGrupoUsuarioList() {
		return grupoUsuarioList;
	}

	public void setGrupoUsuarioList(List<String> grupoUsuarioList) {
		this.grupoUsuarioList = grupoUsuarioList;
	}

}
