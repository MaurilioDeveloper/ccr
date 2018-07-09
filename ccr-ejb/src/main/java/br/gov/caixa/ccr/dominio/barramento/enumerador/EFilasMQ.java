package br.gov.caixa.ccr.dominio.barramento.enumerador;

public enum EFilasMQ {
	
  FACTORY_SICCR("java:/jms/SiccrConnectionFactory"),  
  FACTORY_SIABE("java:/jms/SiabeConnectionFactory"), 
  FACTORY_SIRIC("java:/jms/SiricConnectionFactory"),
  FACTORY_MINUTA("java:/jms/MinutaConnectionFactory"),
 
  SICCR_REQ_CREDITO("java:/jms/SICCR.REQ.CREDITO"),  
  SICCR_RSP_CREDITO("java:/jms/SICCR.RSP.CREDITO"),
  
  SIABE_REQ_CONSULTA_BENEFICIARIO("java:/jms/SIBAR.REQ.CONSULTA_BENEFICIARIO_INSS"),
  SIABE_RSP_CONSULTA_BENEFICIARIO("java:/jms/SIBAR.RSP.CONSULTA_BENEFICIARIO_INSS"),
  
  SIBAR_REQ_CONSULTA_AVALIACAO_RISCO("java:/jms/SIBAR.REQ.CONSULTA_AVALIACAO_RISCO"),
  SIBAR_RSP_CONSULTA_AVALIACAO_RISCO("java:/jms/SIBAR.RSP.CONSULTA_AVALIACAO_RISCO"),  
  
  SIBAR_REQ_AVALIACAO_RISCO_CREDITO("java:/jms/SIBAR.REQ.AVALIACAO_RISCO_CREDITO"),
  SICCR_RSP_AVALIACAO_RISCO_CREDITO("java:/jms/SIAPX.RSP.AVALIACAO_RISCO_CREDITO"),
    
  SIFEC_REQ_RECUPERAR_DADOS_MINUTA("java:/jms/SIFEC.REQ.RECUPERAR_DADOS_MINUTA"),
  SIFEC_RSP_RECUPERAR_DADOS_MINUTA("java:/jms/SIFEC.RSP.RECUPERAR_DADOS_MINUTA"),
  
  SIFEC_REQ_GERAR_MINUTA("java:/jms/SIFEC.REQ.GERAR_MINUTA"),
  SIFEC_RSP_GERAR_MINUTA("java:/jms/SIFEC.RSP.GERAR_MINUTA");
    
  private String name;
  
  private EFilasMQ(String s) {
    this.name = s;
  }
  
  public boolean equalsName(String otherName) {
    return otherName == null ? false : this.name.equals(otherName);
  }
  
  public String toString() {
    return this.name;
  }
  
  public EFilasMQ get(String filas) {
    return valueOf(filas);
  }
}

