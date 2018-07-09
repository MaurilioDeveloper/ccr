package br.gov.caixa.ccr.dominio.mensageria;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class ResponseArtRef {

	private Long dispositivoId;

	private Status status;

	public Long getDispositivoId() {
		return dispositivoId;
	}

	public void setDispositivoId(Long dispositivoId) {
		this.dispositivoId = dispositivoId;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
