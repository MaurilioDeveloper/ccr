package br.gov.caixa.ccr.dominio;

import java.io.Serializable;
import java.math.BigInteger;

public class Usuario implements Serializable {

	private static final long serialVersionUID = -7111083308947178542L;

	private String nome;

	private String codigoUsuario;

	private String codigoUsuarioExterno;

	private String codigoFuncaoRH;

	private String codigoPerfilAplicacao;

	private Integer numeroUnidadeLogado;

	private Integer numeroNaturalLogado;

	private Integer numeroNaturalLogado2;

	private Integer numeroUnidadeOrigem;

	private Integer numeroNaturalOrigem;

	private String codigoTerminal;

	private String matriculaEmpregado;

	private String matricula;

	private String codigoAplicacao;

	private Boolean perfilGestor;

	private Boolean usuarioExterno = false;

	private BigInteger cpfUsuario;

	private String cnpj;

	private String cpf;
	
	public Usuario() {

	}

	public Usuario(Integer numeroUnidadeLogado) {
		this.numeroUnidadeLogado = numeroUnidadeLogado;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCodigoUsuario() {
		if (codigoUsuario != null && codigoUsuario.indexOf("@") > 0) {
			int str = codigoUsuario.trim().indexOf("@");
			if (str > 7) {
				return codigoUsuario.trim().substring(0, 8);
			} else {
				return codigoUsuario.trim().substring(0, str);
			}
		}

		return codigoUsuario;
	}

	public void setCodigoUsuario(String codigoUsuario) {
		this.codigoUsuario = codigoUsuario;
	}

	public String getCodigoUsuarioExterno() {
		return codigoUsuarioExterno;
	}

	public void setCodigoUsuarioExterno(String codigoUsuarioExterno) {
		this.codigoUsuarioExterno = codigoUsuarioExterno;
	}

	public String getCodigoFuncaoRH() {
		return codigoFuncaoRH;
	}

	public void setCodigoFuncaoRH(String codigoFuncaoRH) {
		this.codigoFuncaoRH = codigoFuncaoRH;
	}

	public String getCodigoPerfilAplicacao() {
		return codigoPerfilAplicacao;
	}

	public void setCodigoPerfilAplicacao(String codigoPerfilAplicacao) {
		this.codigoPerfilAplicacao = codigoPerfilAplicacao;
	}

	public Integer getNumeroUnidadeLogado() {
		return numeroUnidadeLogado;
	}

	public void setNumeroUnidadeLogado(Integer numeroUnidadeLogado) {
		this.numeroUnidadeLogado = numeroUnidadeLogado;
	}

	public Integer getNumeroNaturalLogado() {
		return numeroNaturalLogado;
	}

	public void setNumeroNaturalLogado(Integer numeroNaturalLogado) {
		this.numeroNaturalLogado = numeroNaturalLogado;
	}

	public Integer getNumeroUnidadeOrigem() {
		return numeroUnidadeOrigem;
	}

	public void setNumeroUnidadeOrigem(Integer numeroUnidadeOrigem) {
		this.numeroUnidadeOrigem = numeroUnidadeOrigem;
	}

	public Integer getNumeroNaturalOrigem() {
		return numeroNaturalOrigem;
	}

	public void setNumeroNaturalOrigem(Integer numeroNaturalOrigem) {
		this.numeroNaturalOrigem = numeroNaturalOrigem;
	}

	public String getCodigoTerminal() {
		return codigoTerminal;
	}

	public void setCodigoTerminal(String codigoTerminal) {
		this.codigoTerminal = codigoTerminal;
	}

	public Integer getNumeroNaturalLogado2() {
		return numeroNaturalLogado2;
	}

	public void setNumeroNaturalLogado2(Integer numeroNaturalLogado2) {
		this.numeroNaturalLogado2 = numeroNaturalLogado2;
	}

	public String getMatriculaEmpregado() {
		return matriculaEmpregado;
	}

	public void setMatriculaEmpregado(String matriculaEmpregado) {
		this.matriculaEmpregado = matriculaEmpregado;
	}

	public Boolean getPerfilGestor() {
		return perfilGestor;
	}

	public void setPerfilGestor(Boolean perfilGestor) {
		this.perfilGestor = perfilGestor;
	}

	public String getCodigoAplicacao() {
		return codigoAplicacao;
	}

	public void setCodigoAplicacao(String codigoAplicacao) {
		this.codigoAplicacao = codigoAplicacao;
	}

	public BigInteger getCpfUsuario() {
		return cpfUsuario;
	}

	public void setCpfUsuario(BigInteger cpfUsuario) {
		this.cpfUsuario = cpfUsuario;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public Boolean getUsuarioExterno() {
		return usuarioExterno;
	}

	public void setUsuarioExterno(Boolean usuarioExterno) {
		this.usuarioExterno = usuarioExterno;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

}