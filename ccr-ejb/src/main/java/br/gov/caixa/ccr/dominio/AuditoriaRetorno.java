package br.gov.caixa.ccr.dominio;

import java.util.List;

public class AuditoriaRetorno extends Retorno {
	
	
	private List<Auditoria> auditoria;
	private Long totalRegistros;
	private Integer totalPaginas;
	private Integer paginaAtual;
	private Integer registrosPorPagina;
	/**
	 * @return the registrosPorPagina
	 */
	public Integer getRegistrosPorPagina() {
		return registrosPorPagina;
	}
	/**
	 * @param registrosPorPagina the registrosPorPagina to set
	 */
	public void setRegistrosPorPagina(Integer registrosPorPagina) {
		this.registrosPorPagina = registrosPorPagina;
	}
	/**
	 * @return the auditoria
	 */
	public List<Auditoria> getAuditoria() {
		return auditoria;
	}
	/**
	 * @param auditoria the auditoria to set
	 */
	public void setAuditoria(List<Auditoria> auditoria) {
		this.auditoria = auditoria;
	}
	/**
	 * @return the totalRegistros
	 */
	public Long getTotalRegistros() {
		return totalRegistros;
	}
	/**
	 * @param totalRegistros the totalRegistros to set
	 */
	public void setTotalRegistros(Long totalRegistros) {
		this.totalRegistros = totalRegistros;
	}
	/**
	 * @return the totalPaginas
	 */
	public Integer getTotalPaginas() {
		return totalPaginas;
	}
	/**
	 * @param totalPaginas the totalPaginas to set
	 */
	public void setTotalPaginas(Integer totalPaginas) {
		this.totalPaginas = totalPaginas;
	}
	/**
	 * @return the paginaAtual
	 */
	public Integer getPaginaAtual() {
		return paginaAtual;
	}
	/**
	 * @param paginaAtual the paginaAtual to set
	 */
	public void setPaginaAtual(Integer paginaAtual) {
		this.paginaAtual = paginaAtual;
	}
}
