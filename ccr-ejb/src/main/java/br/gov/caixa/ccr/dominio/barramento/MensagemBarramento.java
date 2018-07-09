package br.gov.caixa.ccr.dominio.barramento;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(propOrder={"sibarHeader", "codigoRetorno", "origemRetorno", "mensagemRetorno"})
public abstract class MensagemBarramento implements Serializable {
  
  private static final long serialVersionUID = 1L;
  
  protected SibarHeader sibarHeader;
  protected String codigoRetorno;
  protected String origemRetorno;
  protected String mensagemRetorno;
  
  @XmlElement(name="sibar_base:HEADER")
  public SibarHeader getSibarHeader()
  {
    if (this.sibarHeader == null) {
      this.sibarHeader = new SibarHeader();
    }
    return this.sibarHeader;
  }
  
  public void setSibarHeader(SibarHeader sibarHeader)
  {
    this.sibarHeader = sibarHeader;
  }
  
  @XmlElement(name="COD_RETORNO")
  public String getCodigoRetorno()
  {
    return this.codigoRetorno;
  }
  
  @XmlElement(name="ORIGEM_RETORNO")
  public String getOrigemRetorno()
  {
    return this.origemRetorno;
  }
  
  @XmlElement(name="MSG_RETORNO")
  public String getMensagemRetorno()
  {
    return this.mensagemRetorno;
  }
  
  public void setCodigoRetorno(String codigoRetorno)
  {
    this.codigoRetorno = codigoRetorno;
  }
  
  public void setOrigemRetorno(String origemRetorno)
  {
    this.origemRetorno = origemRetorno;
  }
  
  public void setMensagemRetorno(String mensagemRetorno)
  {
    this.mensagemRetorno = mensagemRetorno;
  }
}
