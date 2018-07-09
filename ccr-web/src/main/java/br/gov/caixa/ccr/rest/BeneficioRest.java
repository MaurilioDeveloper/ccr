package br.gov.caixa.ccr.rest;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.gov.caixa.arqrefcore.excecao.BusinessException;
import br.gov.caixa.arqrefcore.excecao.SystemException;
import br.gov.caixa.arqrefcore.jms.Correlation;
import br.gov.caixa.ccr.dominio.Mensageria;
import br.gov.caixa.ccr.dominio.barramento.siabe.Beneficiario;
import br.gov.caixa.ccr.dominio.barramento.siabe.Beneficio;
import br.gov.caixa.ccr.dominio.barramento.siabe.Conta;
import br.gov.caixa.ccr.dominio.barramento.siabe.CreditoPgto;
import br.gov.caixa.ccr.dominio.barramento.siabe.DadosConsultaBeneficio;
import br.gov.caixa.ccr.dominio.barramento.siabe.DadosResultado;
import br.gov.caixa.ccr.servico.IConsultaBeneficiario;

@Path("/beneficio")
public class BeneficioRest extends AbstractSecurityRest {
	
	@Inject
	private IConsultaBeneficiario consultaBeneficioService;	
	
	@POST
	@Path("/consultar")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Mensageria<DadosResultado> solicitarConsulta(Mensageria<DadosConsultaBeneficio> entrada) throws SystemException, BusinessException{
		Mensageria<DadosResultado> resposta = new Mensageria<DadosResultado>();
		DadosResultado camposRetornados = null;
		/*
		//Define o empregado
		Empregado empregado = new Empregado();
		
		//TODO - obter do login no @SecurityContext
		//String usuario = securityContext.getUserPrincipal().getName();
		empregado.setNumeroMatricula(891843); /// USUARIO COM ACESSO
		
		Correlation correlation = consultaBeneficioService.consultaBeneficio(entrada.getDados(), empregado);
		camposRetornados = consultaBeneficioService.recebeDadosBeneficio(correlation);
		
		resposta.setCorrelationId(correlation.getId());
		resposta.setDados(camposRetornados);
		*/
		
		// SIMULA RETORNO já que falta acesso ao SIABE
		camposRetornados = new DadosResultado();
		Beneficiario beneficiario = new Beneficiario();		
		Beneficio beneficio = new Beneficio();
		CreditoPgto credito = new CreditoPgto();
		Conta conta = new Conta();
		
		beneficio.setNumero("1234567890");
		beneficio.setDv("5");
		beneficiario.setBeneficio(beneficio);
		
		credito.setValorBruto("15000.00");
		credito.setValorLiquido("12000.00");
		beneficiario.setCreditoPgto(credito);
		
		conta.setAgencia("2");
		conta.setOperacao("1");
		conta.setNumero("1");
		conta.setDv("9");
		beneficiario.setConta(conta);	
		
		camposRetornados.setBeneficiario(beneficiario);
		
		resposta.setCorrelationId("afkein240389fnae392urkanf324");
		resposta.setDados(camposRetornados);		
		
		return resposta;
	}
	
	@GET
	@Path("/consultar/{correlationId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Mensageria<DadosResultado> respostaConsulta(@PathParam("correlationId") final String correlationId) throws SystemException, BusinessException{
		Mensageria<DadosResultado> resposta = new Mensageria<DadosResultado>();
		DadosResultado camposRetornados = null;
		Correlation correlation = new Correlation(correlationId);
		camposRetornados = consultaBeneficioService.recebeDadosBeneficio(correlation);
		
		resposta.setCorrelationId(correlation.getId());
		resposta.setDados(camposRetornados);
		
		return resposta;
	}
	

}
