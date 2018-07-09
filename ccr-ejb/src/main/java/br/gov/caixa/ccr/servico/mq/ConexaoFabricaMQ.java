package br.gov.caixa.ccr.servico.mq;

import javax.ejb.Singleton;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import br.gov.caixa.arqrefcore.barramento.DAOBarramento;
import br.gov.caixa.ccr.dominio.barramento.anotacao.MINUTAMQ;
import br.gov.caixa.ccr.dominio.barramento.anotacao.SIABEMQ;
import br.gov.caixa.ccr.dominio.barramento.anotacao.SICCRMQ;
import br.gov.caixa.ccr.dominio.barramento.anotacao.SIRICMQ;

@Singleton
public class ConexaoFabricaMQ {
	
  @Inject
  private Instance<ConexaoSICCRMQ> conexaoSICCRMQ;
  
  @Inject
  private Instance<ConexaoSIABEMQ> conexaoSIABEMQ;
  
  @Inject 
  private Instance<ConexaoSIRICMQ> conexaoSIRICMQ;
  
  @Inject 
  private Instance<ConexaoMINUTAMQ> conexaoMinutaMQ;
  
  @Produces
  @SICCRMQ
  public DAOBarramento getConexaoSICCRMQ() {
    return (DAOBarramento)this.conexaoSICCRMQ.get();
  }
    
  @Produces
  @SIABEMQ
  public DAOBarramento getConexaoSIABEMQ() {
	  return (DAOBarramento)this.conexaoSIABEMQ.get();
  }
  
  @Produces
  @SIRICMQ
  public DAOBarramento getConexaoSIRICMQ() {
	  return (DAOBarramento)this.conexaoSIRICMQ.get();
  }
  
  @Produces
  @MINUTAMQ
  public DAOBarramento getConexaoMinutaMQ() {
	  return (DAOBarramento)this.conexaoMinutaMQ.get();
  }
  
}
