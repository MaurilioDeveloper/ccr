package br.gov.caixa.ccr.negocio;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

import br.gov.caixa.arqrefcore.log.Logging;
import br.gov.caixa.arqrefservices.dominio.siric.AvaliacaoValiada;
import br.gov.caixa.ccr.dominio.transicao.AvaliacaoRiscoTO;


@Stateless
@LocalBean
@TransactionManagement(TransactionManagementType.CONTAINER)
@Logging
public class AvaliacaoBean implements IAvaliacaoBean {
	private static final String NAO_ACEITO = "Não aceito";
	private static final String ACEITO = "Aceito";
	private static final String NAO_AVALIADO = " Não avaliado";
	private static final String QV_COM_ERRO = "QV com Erro";
	private static final String CONDICIONADO= "Condicionado";
	private static final String SEM_REQUISITO = "Sem Requisito";
	private static final String PRODUTO = "Consignado";
	

	@PersistenceContext
	private EntityManager em;
	
	@Context
	private SecurityContext securityContext;

	@Override
	public Long salvarAvaliacao(AvaliacaoValiada avaliacaoRequest, String userlog) throws Exception {	
		AvaliacaoRiscoTO avaliacao= new AvaliacaoRiscoTO();

		//NU_AVALIACAO_RISCO
//		0 = Não aceito
//	    1 = Aceito
//		2 = Não avaliado
//		3 = QV com Erro
//		4 = Condicionado
//		5 = Sem Requisito 
		avaliacao.setCoResultado(avaliacaoRequest.getSituacao());  
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String dateFimString = avaliacaoRequest.getDataFimValidade(); 
        Date dateFim  = formatter.parse(dateFimString);
       //DT_FIM_VALIDADE_AVALIACAO
		avaliacao.setDtFimAvaliacao(dateFim);
		//DT_GERACAO
		avaliacao.setDtGeracao(new Date()); 
		
		String dateIniString = avaliacaoRequest.getDataInicioValidade();
	    Date dateInicio  = formatter.parse(dateIniString);
	    //DT_INICIO_VALIDADE_AVALIACAO
		avaliacao.setDtInicoAvaliacao(dateInicio);
		avaliacao.setIcAvaliacaoManual(null);
		
		//IC_BLOQUEIO_ALCADA
		if(isNumber(avaliacaoRequest.getFlagBloquadaAlcada())){
			String bloq = avaliacaoRequest.getFlagBloquadaAlcada() == "0"  ? "N" : "S";
			avaliacao.setIcBloqueioAlcada(bloq); 
		}else{
			avaliacao.setIcBloqueioAlcada(avaliacaoRequest.getFlagBloquadaAlcada()); 
		}
		
		//NO_PRODUTO_SIRIC
		avaliacao.setNoProduto(PRODUTO);
		//DE_RESULTADO_AVALIACAO
		avaliacao.setNoResultado(this.nomeResultado(Integer.valueOf(avaliacaoRequest.getSituacao())));
		//NU_OPERACAO_PRODUTO
		avaliacao.setNuOperacao(Long.parseLong(avaliacaoRequest.getCodigoAvaliacao()));
		//PZ_MAXIMO_EMPRESTIMO
		avaliacao.setNuPrazoMxmoEmprestimo(Long.parseLong(avaliacaoRequest.getPrazo()));   
		//IN_RATING_AVALIACAO
		avaliacao.setNuRatingAvaliacao(Long.parseLong(avaliacaoRequest.getRating()));  
		String strValorFinciamento = avaliacaoRequest.getValorFinanciamento().replaceAll(",","");
		BigDecimal valorFinanciamento=new BigDecimal(strValorFinciamento);
		//VR_MAXIMO_EMPRESTIMO
		avaliacao.setVrMaximoEmprestimo(valorFinanciamento);
		
		String strValorPrestaco = avaliacaoRequest.getValorLimiteCPM().replaceAll(",",""); 
		BigDecimal valorPrestacao=new BigDecimal(strValorPrestaco);
		//VR_MAXIMO_PRESTACAO
		avaliacao.setVrMaximoPrestacao(valorPrestacao); 
		
		em.persist(avaliacao);
		
		return avaliacao.getNuAvaliacao();
	}
	
	private String nomeResultado(final Integer numero){
		switch (numero) {
		case 1:
			return NAO_ACEITO;
		case 2:
			return ACEITO;
		case 3:
			return NAO_AVALIADO;
		case 4:
			return QV_COM_ERRO;
		case 5:
			return CONDICIONADO;
		default :
			return SEM_REQUISITO;
		}
	}

	private boolean isNumber(String s) {
	    char ch = s.charAt(0);
	    return (ch >= 48 && ch <= 57);
	}
	
	
}
