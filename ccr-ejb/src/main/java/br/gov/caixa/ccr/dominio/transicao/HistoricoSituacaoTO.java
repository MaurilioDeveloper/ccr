package br.gov.caixa.ccr.dominio.transicao;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="CCRTB038_HISTORICO_SITUACAO")
public class HistoricoSituacaoTO implements Serializable {
	
	public static final String QUERY_BUSCA_HIST = 
			"SELECT t.DT_SITUACAO_CONTRATO, s.NU_SITUACAO, t.CO_USUARIO, a.DE_JUSTIFICATIVA "
			+ "FROM CCR.CCRTB038_HISTORICO_SITUACAO t join CCR.CCRTB014_SITUACAO s ON t.NU_HISTORICO_SITUACAO = s.NU_SITUACAO "
			+ "LEFT JOIN CCR.CCRTB050_AUTORIZACAO_CONTRATO a ON a.NU_CONTRATO = t.NU_HISTORICO_SITUACAO "
			+ "where t.NU_CONTRATO = ? ORDER BY t.DT_SITUACAO_CONTRATO ASC";
	
	public static final String QUERY_BUSCA_ID = "SELECT MAX(NU_HISTORICO_SITUACAO)+1 FROM CCR.CCRTB038_HISTORICO_SITUACAO";
	
	private static final long serialVersionUID = 1L;
	
	@Id
//	@SequenceGenerator(sequenceName = "CCRTB038_NUSEQSITUACAOCONTRATO", allocationSize = 1, name="CCRTB038_NUSEQSITUACAOCONTRATO_GENERATOR" )
//	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CCRTB038_NUSEQSITUACAOCONTRATO_GENERATOR")
	@Column(name="NU_HISTORICO_SITUACAO")
	private Long id;
	
	@Column(name="NU_CONTRATO")
	private Long contrato;
	
	@Column(name="NU_SITUACAO")
	private Integer situacao;
	
	@Column(name="NU_TIPO_SITUACAO")
	private Integer tipoSituacao;
	
	@Column(name="DT_SITUACAO_CONTRATO")
	@Temporal(TemporalType.TIMESTAMP)
	private Date data;
	
	@Column(name="CO_USUARIO")
	private String usuario;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getContrato() {
		return contrato;
	}

	public void setContrato(Long contrato) {
		this.contrato = contrato;
	}

	public Integer getSituacao() {
		return situacao;
	}

	public void setSituacao(Integer situacao) {
		this.situacao = situacao;
	}

	public Integer getTipoSituacao() {
		return tipoSituacao;
	}

	public void setTipoSituacao(Integer tipoSituacao) {
		this.tipoSituacao = tipoSituacao;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	
}
