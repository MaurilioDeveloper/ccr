package br.gov.caixa.ccr.dominio.transicao;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the CCRTB072_CAMPO database table.
 * 
 */
@Entity
@Table(name="CCRTB072_CAMPO")
public class CampoTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CCRTB072_CAMPO_IDCAMPO_GENERATOR", sequenceName="CCRSQ073_CAMPO_USADO")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CCRTB072_CAMPO_IDCAMPO_GENERATOR")
	@Column(name="NU_CAMPO")
	private Long idCampo;

	@Column(name="DE_CAMPO")
	private String deCampo;

	@Column(name="DE_ORIGEM_CAMPO")
	private String deOrigemCampo;

	@Column(name="NO_CAMPO")
	private String noCampo;

	//TODO SKMT - pedir para alterar tamanho do campo para 100
	@Column(name="NO_CAMPO_JAVA")
	private String noCampoJava;

	public CampoTO() {
	}

	public Long getIdCampo() {
		return this.idCampo;
	}

	public void setIdCampo(Long idCampo) {
		this.idCampo = idCampo;
	}

	public String getDeCampo() {
		return this.deCampo;
	}

	public void setDeCampo(String deCampo) {
		this.deCampo = deCampo;
	}

	public String getDeOrigemCampo() {
		return this.deOrigemCampo;
	}

	public void setDeOrigemCampo(String deOrigemCampo) {
		this.deOrigemCampo = deOrigemCampo;
	}

	public String getNoCampo() {
		return this.noCampo;
	}

	public void setNoCampo(String noCampo) {
		this.noCampo = noCampo;
	}

	public String getNoCampoJava() {
		return this.noCampoJava;
	}

	public void setNoCampoJava(String noCampoJava) {
		this.noCampoJava = noCampoJava;
	}

}