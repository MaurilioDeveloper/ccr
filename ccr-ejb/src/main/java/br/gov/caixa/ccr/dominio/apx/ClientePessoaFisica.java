package br.gov.caixa.ccr.dominio.apx;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import br.gov.caixa.arqrefservices.dominio.sicli.CamposRetornados;

/**
 * Classe para tratar cliente no APX Extende a classe baisca do SICLI A
 * direfernca esta no local da CPF. No SICLI eh usado um objeto
 * 
 * @author c110503
 * 
 *         Usando
 * @XmlType(name="clienteAPX") para evitar conflito com a extensao
 * 
 * @TODO Retirar codigo da agencia. Verificar a criacao de classe.
 * 
 */
@XmlRootElement
@XmlType(name = "clienteAPX")
public class ClientePessoaFisica extends CamposRetornados implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -783303285542664402L;

	// variavel para valores de convenete INSS
	private String valorBrutoBeneficio;
	// variavel para valores de convenete INSS
	private String valorLiquidoBeneficio;
	// variavel para valores de convenete INSS
	private String numeroBeneficio;

	private String numeroCPF;

	private String indicativoMatricula;

	// DEVE SER REMOVIDO
	private String codigoAgencia;

	// matricula do cliente no orgao
	private String matricula;

	// alteracao dos campos de identificacao
	// para faciltar o uso no SIAPX
	private String numeroIdentidade;
	private String ufIdentidade;
	private String orgaoEmissorIdentidade;
	private String dataEmissaoIdentidade;

	private String naturazaProfissao;

	private String indicativoEmpregadoTemporario;

	private String dataNascimentoAPX;

	private String indicativoRecebeSalarioCEF;

	@XmlAttribute(name = "CO-MAT-CLIENTE")
	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	@XmlAttribute(name = "NU-IDENTIDADE")
	public String getNumeroIdentidade() {
		return numeroIdentidade;
	}

	public void setNumeroIdentidade(String numeroIdentidade) {
		this.numeroIdentidade = numeroIdentidade;
	}

	@XmlAttribute(name = "UF-IDENTIDADE")
	public String getUfIdentidade() {
		return ufIdentidade;
	}

	public void setUfIdentidade(String ufIdentidade) {
		this.ufIdentidade = ufIdentidade;
	}

	@XmlAttribute(name = "CO-ORG-EMISSOR-IDENTIDADE")
	public String getOrgaoEmissorIdentidade() {
		return orgaoEmissorIdentidade;
	}

	public void setOrgaoEmissorIdentidade(String orgaoEmissorIdentidade) {
		this.orgaoEmissorIdentidade = orgaoEmissorIdentidade;
	}

	@XmlAttribute(name = "DT-EXP-IDENTIDADE")
	public String getDataEmissaoIdentidade() {
		return dataEmissaoIdentidade;
	}

	public void setDataEmissaoIdentidade(String dataEmissaoIdentidade) {
		this.dataEmissaoIdentidade = dataEmissaoIdentidade;
	}

	@XmlAttribute(name = "CO-NAT-PROFISSAO")
	public String getNaturazaProfissao() {
		return naturazaProfissao;
	}

	public void setNaturazaProfissao(String naturazaProfissao) {
		this.naturazaProfissao = naturazaProfissao;
	}

	@XmlAttribute(name = "IC-EMPREGADO-TEMP")
	public String getIndicativoEmpregadoTemporario() {
		return indicativoEmpregadoTemporario;
	}

	public void setIndicativoEmpregadoTemporario(
			String indicativoEmpregadoTemporario) {
		this.indicativoEmpregadoTemporario = indicativoEmpregadoTemporario;
	}

	@XmlAttribute(name = "VR-BRUTO-BENEFICIO")
	public String getValorBrutoBeneficio() {
		return valorBrutoBeneficio;
	}

	public void setValorBrutoBeneficio(String valorBrutoBeneficio) {
		this.valorBrutoBeneficio = valorBrutoBeneficio;
	}

	@XmlAttribute(name = "VR-LIQUIDO-BENEFICIO")
	public String getValorLiquidoBeneficio() {
		return valorLiquidoBeneficio;
	}

	public void setValorLiquidoBeneficio(String valorLiquidoBeneficio) {
		this.valorLiquidoBeneficio = valorLiquidoBeneficio;
	}

	@XmlAttribute(name = "CO-AGENCIA")
	public String getCodigoAgencia() {
		return codigoAgencia;
	}

	public void setCodigoAgencia(String codigoAgencia) {
		this.codigoAgencia = codigoAgencia;
	}

	public String getNumeroBeneficio() {
		return numeroBeneficio;
	}

	public void setNumeroBeneficio(String numeroBeneficio) {
		this.numeroBeneficio = numeroBeneficio;
	}

	@XmlAttribute(name = "IC-MATRICULA")
	public String getIndicativoMatricula() {
		return indicativoMatricula;
	}

	public void setIndicativoMatricula(String indicativoMatricula) {
		this.indicativoMatricula = indicativoMatricula;
	}

	@XmlAttribute(name = "DT-NASCIMENTO")
	public String getDataNascimentoAPX() {
		return dataNascimentoAPX;
	}

	public void setDataNascimentoAPX(String dataNascimentoAPX) {
		this.dataNascimentoAPX = dataNascimentoAPX;
	}

	@XmlAttribute(name = "NU-CPF")
	public String getNumeroCPF() {
		return numeroCPF;
	}

	public void setNumeroCPF(String numeroCPF) {
		this.numeroCPF = numeroCPF;
	}

	@XmlAttribute(name = "IC-REC-SALARIO-CAIXA")
	public String getIndicativoRecebeSalarioCEF() {
		return indicativoRecebeSalarioCEF;
	}

	public void setIndicativoRecebeSalarioCEF(String indicativoRecebeSalarioCEF) {
		this.indicativoRecebeSalarioCEF = indicativoRecebeSalarioCEF;
	}

	@Override
	public String toString() {
		return "ClientePessoaFisica [valorBrutoBeneficio="
				+ valorBrutoBeneficio + ", valorLiquidoBeneficio="
				+ valorLiquidoBeneficio + ", numeroBeneficio="
				+ numeroBeneficio + ", numeroCPF=" + numeroCPF
				+ ", indicativoMatricula=" + indicativoMatricula
				+ ", codigoAgencia=" + codigoAgencia + ", matricula="
				+ matricula + ", numeroIdentidade=" + numeroIdentidade
				+ ", ufIdentidade=" + ufIdentidade
				+ ", orgaoEmissorIdentidade=" + orgaoEmissorIdentidade
				+ ", dataEmissaoIdentidade=" + dataEmissaoIdentidade
				+ ", naturazaProfissao=" + naturazaProfissao
				+ ", indicativoEmpregadoTemporario="
				+ indicativoEmpregadoTemporario + ", dataNascimentoAPX="
				+ dataNascimentoAPX + ", indicativoRecebeSalarioCEF="
				+ indicativoRecebeSalarioCEF + "]";
	}

}
