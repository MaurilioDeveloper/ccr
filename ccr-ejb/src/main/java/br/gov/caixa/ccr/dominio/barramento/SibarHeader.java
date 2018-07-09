package br.gov.caixa.ccr.dominio.barramento;

import br.gov.caixa.arqrefcore.util.Dater;
import br.gov.caixa.ccr.dominio.Empregado;

import java.io.Serializable;
import java.util.Properties;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="sibar_base:HEADER")
@XmlType(propOrder={"versao", "autenticacao", "usuarioServico", "usuario", "operacao", "indice", 
			 		"sistemaOrigem", "unidade", "identificadorOrigem", "dataHora", "idProcesso"})
public class SibarHeader implements Serializable {
  private static final long serialVersionUID = 1L;
  
  private String versao;
  
  @XmlElement(name="AUTENTICACAO")
  private String autenticacao = "MA==";
  
  private String usuarioServico;
  private String usuario;
  private String operacao;
  
  @XmlElement(name="INDICE")
  private String indice = "0";
  
  private String sistemaOrigem;
  private String unidade;
  private String identificadorOrigem;
  private String dataHora;
  private String idProcesso;
  
  public SibarHeader (){
	  
  }
  
  public SibarHeader (Empregado empregado, String tipoPesquisa) {
	  this.criarSibarHeader(empregado, tipoPesquisa, null);
  }
  
  public SibarHeader (Empregado empregado, String tipoPesquisa, String usuarioServico) {
	  this.criarSibarHeader(empregado, tipoPesquisa, usuarioServico);
  }
  
  @XmlElement(name="VERSAO")
  public String getVersao()
  {
    return this.versao;
  }
  
  public void setVersao(String versao)
  {
    this.versao = versao;
  }
  
  @XmlElement(name="USUARIO_SERVICO")
  public String getUsuarioServico()
  {
    return this.usuarioServico;
  }
  
  public void setUsuarioServico(String usuarioServico)
  {
    this.usuarioServico = usuarioServico;
  }
  
  @XmlElement(name="OPERACAO")
  public String getOperacao()
  {
    return this.operacao;
  }
  
  public void setOperacao(String operacao)
  {
    this.operacao = operacao;
  }
  
  @XmlElement(name="SISTEMA_ORIGEM")
  public String getSistemaOrigem()
  {
    return this.sistemaOrigem;
  }
  
  public void setSistemaOrigem(String sistemaOrigem)
  {
    this.sistemaOrigem = sistemaOrigem;
  }
  
  @XmlElement(name="UNIDADE")
  public String getUnidade()
  {
    return this.unidade;
  }
  
  public void setUnidade(String unidade)
  {
    this.unidade = unidade;
  }
  
  @XmlElement(name="IDENTIFICADOR_ORIGEM")
  public String getIdentificadorOrigem()
  {
    return this.identificadorOrigem;
  }
  
  public void setIdentificadorOrigem(String identificadorOrigem)
  {
    this.identificadorOrigem = identificadorOrigem;
  }
  
  @XmlElement(name="DATA_HORA")
  public String getDataHora()
  {
    return this.dataHora;
  }
  
  public void setDataHora(String dataHora)
  {
    this.dataHora = dataHora;
  }
  
  @XmlElement(name="ID_PROCESSO")
  public String getIdProcesso()
  {
    return this.idProcesso;
  }
  
  public void setIdProcesso(String idProcesso)
  {
    this.idProcesso = idProcesso;
  }
  
  @XmlElement(name="USUARIO")
  public String getUsuario()
  {
    return this.usuario;
  }
  
  public void setUsuario(String usuario)
  {
    this.usuario = usuario;
  }
  
  public void setAutenticacao(String autenticacao)
  {
    this.autenticacao = autenticacao;
  }
  
  private void criarSibarHeader(Empregado empregado, String tipoPesquisa, String usuarioServico) {
	  if (usuarioServico == null || usuarioServico.trim().equals("")) {
		  Properties propriedade = new Properties();
		  
		  try {
			  propriedade.load(getClass().getClassLoader().getResourceAsStream("barramento.properties"));
			  usuarioServico = propriedade.getProperty("USUARIO_SERVICO");
		  } catch (Exception e) {
			  usuarioServico = "";
		  }
	  }
	  
	  String numeroMatriculaTmp = empregado.getNumeroMatricula().toString();
		if (numeroMatriculaTmp.length() < 6) {
		  for (int x = numeroMatriculaTmp.length(); x < 6; x++) {
		    numeroMatriculaTmp = "0" + numeroMatriculaTmp;
		  }
		}
		
		if (String.valueOf(empregado.getNumeroUnidade()).equals("null"));
			empregado.setNumeroUnidade(0);
		
		setVersao("1.0");
		setUsuarioServico(usuarioServico);
		setUsuario("C" + numeroMatriculaTmp);
		setOperacao(tipoPesquisa);
		setSistemaOrigem("SICCR");
		setUnidade(String.valueOf(empregado.getNumeroUnidade()));
		
		
		Dater dater = new Dater();
		setDataHora(dater.withToday().asString("yyyyMMddHHmmss"));    
		setIdProcesso(tipoPesquisa);
  }
}
