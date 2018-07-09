package br.gov.caixa.ccr.enums;

public enum FilasMQ {

	FACTORY_SIEMP("java:/jms/SiempConnectionFactory"), 
	FACTORY_SIRIC("java:/jms/SiricConnectionFactory"), 
	FACTORY_SICLI("java:/jms/SicliConnectionFactory"),
	
	SICLI_REQ_MANUTENCAO_CLIENTE("java:/jms/SIBAR.REQ.MANUTENCAO_CLIENTE"),
	SICLI_RSP_MANUTENCAO_CLIENTE("java:/jms/SIBAR.RSP.MANUTENCAO_CLIENTE"),
	
	SICLI_REQ_CONSULTA_CLIENTE("java:/jms/SIBAR.REQ.CONSULTA_CLIENTE"),
	SICLI_RSP_CONSULTA_CLIENTE("java:/jms/SIBAR.RSP.CONSULTA_CLIENTE"),
	
	SIRIC_REQ_AVALIACAO_RISCO("java:/jms/SIBAR.REQ.AVALIACAO_RISCO_CREDITO"), 
	SIRIC_RSP_AVALIACAO_RISCO_PROTOCOLO("java:/jms/SIBAR.RSP.AVALIACAO_RISCO_PROTOCOLO"),
	SIRIC_RSP_AVALIACAO_RISCO_CREDITO("java:/jms/SIEMP.RSP.AVALIACAO_RISCO_CREDITO"),
	
	SIRIC_REQ_CANCELAMENTO_AVALIACAO_RISCO("java:/jms/SIBAR.REQ.CANCELAMENTO_AVALIACAO_RISCO"),
	SIRIC_RSP_CANCELAMENTO_AVALIACAO_RISCO("java:/jms/SIBAR.RSP.CANCELAMENTO_AVALIACAO_RISCO"),
	
	SIAPI_REQ_CREDITO("java:/jms/SIAPI.REQ.CREDITO"),
	SIAPI_RSP_CREDITO("java:/jms/SIAPI.RSP.CREDITO"),
	
	SIRIC_REQ_CONSULTA_AVALIACAO_RISCO("java:/jms/SIBAR.REQ.CONSULTA_AVALIACAO_RISCO"),
	SIRIC_RSP_CONSULTA_AVALIACAO_RISCO("java:/jms/SIBAR.RSP.CONSULTA_AVALIACAO_RISCO"),
	
	
	SIFEC_REQ_PORTAL("java:/jms/SIFEC.REQ.PORTAL"), 
	SIFEC_RSP_PORTAL("java:/jms/SIFEC.RSP.PORTAL");

	private String name;

	private FilasMQ(String s) {
		name = s;
	}

	public boolean equalsName(String otherName) {
		return (otherName == null) ? false : name.equals(otherName);
	}

	public String toString() {
		return name;
	}

	public FilasMQ get(String filas) {
		return FilasMQ.valueOf(filas);
	}
}
