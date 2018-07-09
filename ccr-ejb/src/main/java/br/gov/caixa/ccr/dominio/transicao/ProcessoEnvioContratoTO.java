//package br.gov.caixa.ccr.dominio.transicao;
//
//import java.util.Date;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.EnumType;
//import javax.persistence.Enumerated;
//import javax.persistence.Id;
//import javax.persistence.NamedQueries;
//import javax.persistence.NamedQuery;
//import javax.persistence.Table;
//import javax.persistence.Temporal;
//import javax.persistence.TemporalType;
//
//import br.gov.caixa.ccr.enums.EnumProcessoEnvioContrato;
//
//@Entity
//@Table(name="CCRTB102_PRCSO_ENVIO_CONTRATO")
//@NamedQueries( value={ 
//		@NamedQuery(name="ProcessoEnvioContrato.findAll", query="select processo from ProcessoEnvioContratoTO processo"),
//		@NamedQuery(name="ProcessoEnvioContrato.buscar", query="select p from ProcessoEnvioContratoTO p where p.processo = ?")
//})
//public class ProcessoEnvioContratoTO {
//
//	@Id	
//	@Column(name = "NU_PROCESSO_ENVIO_CONTRATO", nullable = false)
//	private Long id;
//	
//	@Enumerated(EnumType.STRING)
//	@Column(name = "NO_PROCESSO_ENVIO_CONTRATO", nullable = false)
//	private EnumProcessoEnvioContrato processo;
//	
//	@Temporal(TemporalType.TIME)
//	@Column(name = "DH_INICIO_PROCESSO")
//	private Date horaInicio;
//	
//	@Temporal(TemporalType.TIME)
//	@Column(name = "DH_FIM_PROCESSO")
//	private Date horaFim;
//	
//	@Column(name = "NU_INTERVALO")
//	private Integer intervalo;
//
//	public Long getId() {
//		return id;
//	}
//
//	public void setId(Long id) {
//		this.id = id;
//	}
//
//	public EnumProcessoEnvioContrato getProcesso() {
//		return processo;
//	}
//
//	public void setProcesso(EnumProcessoEnvioContrato processo) {
//		this.processo = processo;
//	}
//
//	public Date getHoraInicio() {
//		return horaInicio;
//	}
//
//	public void setHoraInicio(Date horaInicio) {
//		this.horaInicio = horaInicio;
//	}
//
//	public Date getHoraFim() {
//		return horaFim;
//	}
//
//	public void setHoraFim(Date horaFim) {
//		this.horaFim = horaFim;
//	}
//
//	public Integer getIntervalo() {
//		return intervalo;
//	}
//
//	public void setIntervalo(Integer intervalo) {
//		this.intervalo = intervalo;
//	}
//	
//	
//}
