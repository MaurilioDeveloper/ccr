package br.gov.caixa.ccr.negocio;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import br.gov.caixa.arqrefcore.excecao.BusinessException;
import br.gov.caixa.ccr.dominio.CET;
import br.gov.caixa.ccr.dominio.Convenio;
import br.gov.caixa.ccr.dominio.Empregado;
import br.gov.caixa.ccr.dominio.PrestacaoPrice;
import br.gov.caixa.ccr.dominio.Simular;
import br.gov.caixa.ccr.dominio.TaxaIOF;
import br.gov.caixa.ccr.dominio.TaxaJuro;
import br.gov.caixa.ccr.dominio.TaxaSeguro;
import br.gov.caixa.ccr.dominio.ValorReajusteSimulacao;
import br.gov.caixa.ccr.dominio.barramento.simulacao.CamposComuns;
import br.gov.caixa.ccr.dominio.barramento.simulacao.DadosResultado;
import br.gov.caixa.ccr.dominio.barramento.simulacao.SimulacaoComSeguro;
import br.gov.caixa.ccr.dominio.barramento.simulacao.SimulacaoSemSeguro;
import br.gov.caixa.ccr.dominio.transicao.ConvenioTO;
import br.gov.caixa.ccr.dominio.transicao.GrupoTaxaTO;
import br.gov.caixa.ccr.util.DataUtil;
import br.gov.caixa.ccr.util.SimulacaoUtil;

@Stateless
@LocalBean
@TransactionManagement(TransactionManagementType.CONTAINER)
public class SimulacaoBean implements ISimulacaoBean {
	@PersistenceContext
	private EntityManager em;

	@Inject
	private ITaxaIOFBean taxaIOFService;
	
	@Inject
	private ITaxaSeguroBean taxaSeguroService;
	
	@Inject
	private ITaxaJuroBean taxaJuroService;
	

	@EJB
	private IConvenioBean convenioBean;

	
	@Override
	public DadosResultado simulaInterno(Simular simulacao, Empregado empregado) throws BusinessException, Exception {

		DadosResultado resultado = new DadosResultado(); 
		CamposComuns camposComuns = new CamposComuns();
		ConvenioTO convenioCompleto = null;
		convenioCompleto = buscaConvenio( Long.valueOf(simulacao.getIdConvenio()));
		
		HashMap<String, Date> hashDatas = SimulacaoUtil.calculaDatas(simulacao, convenioCompleto);

		
		Date dataNascimento = DataUtil.converter(simulacao.getDataNascimento(), DataUtil.PADRAO_DATA);
		Calendar cal = Calendar.getInstance();
		int idade = DataUtil.calcularIdade(dataNascimento);
		
		int prazo = Integer.parseInt(simulacao.getPrazo());

		Date dataSimulacao = SimulacaoUtil.calculaDiaUtil(simulacao.dataContratacaoConvertida());
		
		Date dataBaseConrato=hashDatas.get(SimulacaoUtil.KEY_BASE_CONTRATO);
		int qtDiasJuroAcerto = DataUtil.diferencaEmDias(dataSimulacao,dataBaseConrato );
		
		String codigoConvenio  = convenioCompleto.getId().toString();
		
		TaxaIOF taxaIOF = buscaTaxaIOF();
		
		TaxaSeguro taxaSeguro = buscaTaxaSeguro(idade, prazo);
		
		TaxaJuro taxaJuro =  buscaTaxaJuro(convenioCompleto.getGrupoTaxa(), prazo, codigoConvenio);
		
		
		double valorLiquido = getValorLiquido(simulacao);
		
		double taxaJuros = getTaxaJuros(simulacao, taxaJuro);			
		
		SimulacaoComSeguro simulacaoComSeguro = null;
		if(taxaSeguro!=null){
			simulacaoComSeguro=simulaComSeguro(simulacao, hashDatas, dataNascimento, prazo,
				dataSimulacao, qtDiasJuroAcerto, taxaIOF, taxaSeguro, taxaJuros, valorLiquido);
			simulacaoComSeguro.setPossuiResultado(true);
			camposComuns.setTaxaSeguro(Double.toString((double) taxaSeguro.getTaxa()));
		}else{
			simulacaoComSeguro= new SimulacaoComSeguro();
			simulacaoComSeguro.setPossuiResultado(false);
		}
		
		resultado.setSimulacaoComSeguro(simulacaoComSeguro);		
				
		SimulacaoSemSeguro simulacaoSemSeguro = simulaSemSeguro(simulacao, hashDatas, prazo, dataSimulacao,
				qtDiasJuroAcerto, taxaIOF, taxaJuros, valorLiquido);
		
		
		resultado.setSimulacaoSemSeguro(simulacaoSemSeguro);
		
		camposComuns.setBasePrimeiraPrestacao(DataUtil.formatarData(hashDatas.get(SimulacaoUtil.KEY_BASE_CONTRATO)));
		camposComuns.setData(DataUtil.formatarData(cal.getTime()));
		camposComuns.setHora(DataUtil.formatarHora(cal.getTime()));
		camposComuns.setQuantDiasJurosAcerto(Integer.toString(qtDiasJuroAcerto));
		camposComuns.setVencimentoPrimeiraPrestacao(DataUtil.formatarData(hashDatas.get(SimulacaoUtil.KEY_VCTO_1A_PRESTACAO)));
		camposComuns.setIofAliquotaBasica(Double.toString((double) taxaIOF.getAliquotaBasica()));
		camposComuns.setIofAliquotaComplementar(Double.toString((double) taxaIOF.getAliquotaComplementar()));
		
		resultado.setCamposComuns(camposComuns);
		
		return resultado;
	}

	private double getValorLiquido(Simular simulacao) throws ParseException {
		
		if(simulacao.getValorLiquido() == null || simulacao.getValorLiquido().isEmpty()){ 
			return 0d; 
		}
		else{
			return converteStringParaDouble(simulacao.getValorLiquido());
		}
	}

	private double getTaxaJuros(Simular simulacao, TaxaJuro taxaJuro) throws ParseException {
		simulacao.getTaxaJuros();
		return Double.valueOf( simulacao.getTaxaJuros());
		/*double taxaJuros;
		if (simulacao.getTaxaJuros() == null || simulacao.getTaxaJuros().trim().isEmpty())
			taxaJuros = (double) taxaJuro.getPcMedio();
		else
			taxaJuros = converteStringParaDouble(simulacao.getTaxaJuros());
		return taxaJuros;*/
	}
	
	private  double converteStringParaDouble(String valor) throws ParseException{
	    NumberFormat nf = NumberFormat.getNumberInstance(new Locale("pt", "BR"));
	    double number = nf.parse(valor).doubleValue();	
	    return number;
	}

	private SimulacaoSemSeguro simulaSemSeguro(Simular simulacao, HashMap<String, Date> hashDatas, int prazo,
			Date dataSimulacao, int qtDiasJuroAcerto, TaxaIOF taxaIOF, double taxaJuros, double valorLiquido)
			throws Exception {
		double valorJuroAcerto;
		double valorIOF;
		double valorSeguro;
		double valorBase;
		double valorTotalContrato = 0d;
		ValorReajusteSimulacao valoresReajustados;
		PrestacaoPrice prestacaoPrice;
		CET cet;
		String valorPrestacaoRetorno;
		if (simulacao.simulacaoPorValorLiquido()) {
			
			valorJuroAcerto = SimulacaoUtil.calculaValorJuroAcerto(taxaJuros, valorLiquido, qtDiasJuroAcerto); // 1a vez
			valorIOF = SimulacaoUtil.calculaValorIOF(prazo, valorLiquido, dataSimulacao, hashDatas.get(SimulacaoUtil.KEY_VCTO_1A_PRESTACAO), taxaJuros, (double) taxaIOF.getAliquotaBasica(), (double) taxaIOF.getAliquotaComplementar());
			valorSeguro = 0d;
			valoresReajustados = SimulacaoUtil.calculaFatorAjuste(valorLiquido, valorJuroAcerto, valorIOF, 0d, valorSeguro);
			
			valorIOF = valoresReajustados.getValorIOFCalculado();
			valorSeguro = 0d;			
			valorBase = valoresReajustados.getValorTotal();
			valorJuroAcerto = SimulacaoUtil.calculaValorJuroAcerto(taxaJuros, valorBase, qtDiasJuroAcerto); // 2a vez
			valorBase = valorBase + valorJuroAcerto;
			
			prestacaoPrice = SimulacaoUtil.calculaValorPrestacaoPrice(prazo, valorBase, taxaJuros);
			valorPrestacaoRetorno=Double.toString(prestacaoPrice.getPrestacao());
			cet = SimulacaoUtil.calculaValorCET(valorLiquido, prestacaoPrice.getPrestacao(), taxaJuros, prazo, dataSimulacao, hashDatas.get(SimulacaoUtil.KEY_VCTO_1A_PRESTACAO));
			valorTotalContrato=valoresReajustados.getValorTotal()+valorJuroAcerto;
			
		} else {
			double valorInvolucao = 0d;
			
			valorInvolucao = SimulacaoUtil.calculaValorInvolucao(taxaJuros, converteStringParaDouble(simulacao.getValorPrestacao()), prazo);
			valorBase = valorInvolucao;
			
			valorSeguro = 0;
			valorIOF = SimulacaoUtil.calculaValorIOF(prazo, valorBase, dataSimulacao, hashDatas.get(SimulacaoUtil.KEY_VCTO_1A_PRESTACAO), taxaJuros, (double) taxaIOF.getAliquotaBasica(), (double) taxaIOF.getAliquotaComplementar());
			valorJuroAcerto = SimulacaoUtil.calculaValorJuroAcerto(taxaJuros, valorBase, qtDiasJuroAcerto); // 1a vez
			
			valorBase = valorBase - valorJuroAcerto;
			valorJuroAcerto = SimulacaoUtil.calculaValorJuroAcerto(taxaJuros, valorBase, qtDiasJuroAcerto); // 2a vez
			
			valorBase = valorInvolucao;
			valorLiquido = valorInvolucao - valorSeguro - valorIOF - valorJuroAcerto;			
			
			cet = SimulacaoUtil.calculaValorCET(valorLiquido, converteStringParaDouble(simulacao.getValorPrestacao()), taxaJuros, prazo, dataSimulacao, hashDatas.get(SimulacaoUtil.KEY_VCTO_1A_PRESTACAO));
			valorTotalContrato=valorBase;
			valorPrestacaoRetorno=simulacao.getValorPrestacao();
		}
		
		SimulacaoSemSeguro simulacaoSemSeguro = new SimulacaoSemSeguro();
		simulacaoSemSeguro.setVencimentoContrato(DataUtil.formatarData(hashDatas.get(SimulacaoUtil.KEY_VCTO_CONTRATO)));
		simulacaoSemSeguro.setPrazo(simulacao.getPrazo()); 
		simulacaoSemSeguro.setTaxaJuros(Double.toString(taxaJuros));
		simulacaoSemSeguro.setPrestacao(valorPrestacaoRetorno);
		simulacaoSemSeguro.setValorLiquido(Double.toString(valorLiquido));
		simulacaoSemSeguro.setJuroAcerto(Double.toString(valorJuroAcerto));
		simulacaoSemSeguro.setIOF(Double.toString(valorIOF));
		simulacaoSemSeguro.setValorContrato(Double.toString(valorLiquido));
		simulacaoSemSeguro.setCETJuroAcerto(Double.toString(SimulacaoUtil.roundedValue(valorJuroAcerto * 100 / valorBase)));
		simulacaoSemSeguro.setCETIOF(Double.toString(SimulacaoUtil.roundedValue(valorIOF * 100 / valorBase)));
		simulacaoSemSeguro.setCETValorContrato(Double.toString(SimulacaoUtil.roundedValue(valorLiquido * 100 / valorBase)));
		simulacaoSemSeguro.setCETMensal(Double.toString(cet.getTxMensal()));
		simulacaoSemSeguro.setCETAnual(Double.toString(cet.getTxAnual()));
		simulacaoSemSeguro.setPcTaxaSeguro(Double.toString(taxaJuros));
		simulacaoSemSeguro.setVrCetAnual(Double.toString(cet.getTxAnual()*valorBase/100));
		simulacaoSemSeguro.setVrCetMensal(Double.toString(cet.getTxMensal()*valorBase/100));
		simulacaoSemSeguro.setValorTotalContrato(Double.toString(valorTotalContrato));
		double taxaEfetivaMensal= calculaTaxaEfetivaMensal(taxaJuros,prazo);
		double taxaEfetivaAnual= calculaTaxaEfetivaMensal(taxaJuros,12);

		simulacaoSemSeguro.setTaxaEfetivaMensal(Double.toString( taxaEfetivaMensal));
		simulacaoSemSeguro.setTaxaEfetivaAnual(Double.toString(taxaEfetivaAnual));
		return simulacaoSemSeguro;
	}

	private SimulacaoComSeguro simulaComSeguro(Simular simulacao, HashMap<String, Date> hashDatas,
			Date dataNascimento, int prazo, Date dataSimulacao, int qtDiasJuroAcerto, TaxaIOF taxaIOF,
			TaxaSeguro taxaSeguro, double taxaJuros, double valorLiquido) throws Exception {
		double valorJuroAcerto;
		double valorIOF;
		double valorSeguro;
		double valorBase;
		ValorReajusteSimulacao valoresReajustados;
		PrestacaoPrice prestacaoPrice;
		CET cet;
		String valorPrestacaoRetorno;
		if (simulacao.simulacaoPorValorLiquido()) {
			
			valorJuroAcerto = SimulacaoUtil.calculaValorJuroAcerto(taxaJuros, valorLiquido, qtDiasJuroAcerto); // 1a vez
			valorIOF = SimulacaoUtil.calculaValorIOF(prazo, valorLiquido, dataSimulacao, hashDatas.get(SimulacaoUtil.KEY_VCTO_1A_PRESTACAO), taxaJuros, (double) taxaIOF.getAliquotaBasica(), (double) taxaIOF.getAliquotaComplementar());
			valorSeguro = SimulacaoUtil.calculaValorSeguroPrestamista(dataNascimento, hashDatas.get(SimulacaoUtil.KEY_VCTO_CONTRATO), (double) taxaSeguro.getTaxa(), valorLiquido);
			valoresReajustados = SimulacaoUtil.calculaFatorAjuste(valorLiquido, valorJuroAcerto, valorIOF, 0d, valorSeguro);
			
			valorIOF = valoresReajustados.getValorIOFCalculado();
			valorSeguro = valoresReajustados.getValorSeguro();			
			valorBase = valoresReajustados.getValorTotal();
			valorJuroAcerto = SimulacaoUtil.calculaValorJuroAcerto(taxaJuros, valorBase, qtDiasJuroAcerto); // 2a vez
			valorBase = valorBase + valorJuroAcerto;
			
			prestacaoPrice = SimulacaoUtil.calculaValorPrestacaoPrice(prazo, valorBase, taxaJuros);
			cet = SimulacaoUtil.calculaValorCET(valorLiquido, prestacaoPrice.getPrestacao(), taxaJuros, prazo, dataSimulacao, hashDatas.get(SimulacaoUtil.KEY_VCTO_1A_PRESTACAO));
			
			simulacao.setValorPrestacao(Double.toString(prestacaoPrice.getPrestacao()));
			valorPrestacaoRetorno=Double.toString(prestacaoPrice.getPrestacao());
		} else {
			double valorInvolucao = 0d;
			
			valorInvolucao = SimulacaoUtil.calculaValorInvolucao(taxaJuros, converteStringParaDouble(simulacao.getValorPrestacao()), prazo);
			valorBase = valorInvolucao;
			
			valorSeguro = SimulacaoUtil.calculaValorSeguroPrestamista(dataNascimento, hashDatas.get(SimulacaoUtil.KEY_VCTO_CONTRATO), (double) taxaSeguro.getTaxa(), valorBase);
			valorIOF = SimulacaoUtil.calculaValorIOF(prazo, valorBase, dataSimulacao, hashDatas.get(SimulacaoUtil.KEY_VCTO_1A_PRESTACAO), taxaJuros, (double) taxaIOF.getAliquotaBasica(), (double) taxaIOF.getAliquotaComplementar());
			valorJuroAcerto = SimulacaoUtil.calculaValorJuroAcerto(taxaJuros, valorBase, qtDiasJuroAcerto); // 1a vez
			
			valorBase = valorBase - valorJuroAcerto;
			valorJuroAcerto = SimulacaoUtil.calculaValorJuroAcerto(taxaJuros, valorBase, qtDiasJuroAcerto); // 2a vez
			
			valorBase = valorInvolucao;
			valorLiquido = valorInvolucao - valorSeguro - valorIOF - valorJuroAcerto;			
			
			cet = SimulacaoUtil.calculaValorCET(valorBase, converteStringParaDouble(simulacao.getValorPrestacao()), taxaJuros, prazo, dataSimulacao, hashDatas.get(SimulacaoUtil.KEY_VCTO_1A_PRESTACAO));
			valorPrestacaoRetorno=simulacao.getValorPrestacao();
		}
		
		SimulacaoComSeguro simulacaoComSeguro = new SimulacaoComSeguro();
		simulacaoComSeguro.setVencimentoContrato(DataUtil.formatarData(hashDatas.get(SimulacaoUtil.KEY_VCTO_CONTRATO)));
		simulacaoComSeguro.setPrazo(simulacao.getPrazo()); 
		simulacaoComSeguro.setTaxaJuros(Double.toString(taxaJuros));
		simulacaoComSeguro.setPrestacao(valorPrestacaoRetorno);
		simulacaoComSeguro.setValorLiquido(Double.toString(valorLiquido));
		simulacaoComSeguro.setJuroAcerto(Double.toString(valorJuroAcerto));
		simulacaoComSeguro.setIOF(Double.toString(valorIOF));
		simulacaoComSeguro.setValorSeguro(Double.toString(valorSeguro));
		simulacaoComSeguro.setValorContrato(Double.toString(valorLiquido));
		simulacaoComSeguro.setCETJuroAcerto(Double.toString(SimulacaoUtil.roundedValue(valorJuroAcerto * 100 / valorBase)));
		simulacaoComSeguro.setCETIOF(Double.toString(SimulacaoUtil.roundedValue(valorIOF * 100 / valorBase)));
		simulacaoComSeguro.setCETSeguro(Double.toString(SimulacaoUtil.roundedValue(valorSeguro * 100 / valorBase)));
		simulacaoComSeguro.setCETValorContrato(Double.toString(SimulacaoUtil.roundedValue(valorLiquido * 100 / valorBase)));
		simulacaoComSeguro.setCETMensal(Double.toString(cet.getTxMensal()));
		simulacaoComSeguro.setCETAnual(Double.toString(cet.getTxAnual()));
		simulacaoComSeguro.setValorTotalContrato(Double.toString(valorBase));
		simulacaoComSeguro.setPcTaxaSeguro(Double.toString(taxaJuros));
		simulacaoComSeguro.setVrCetAnual(Double.toString(cet.getTxAnual()*valorBase/100));
		simulacaoComSeguro.setVrCetMensal(Double.toString(cet.getTxMensal()*valorBase/100));
		double taxaEfetivaMensal= calculaTaxaEfetivaMensal(taxaJuros,prazo);
		double taxaEfetivaAnual= calculaTaxaEfetivaMensal(taxaJuros,12);
		simulacaoComSeguro.setTaxaEfetivaMensal(Double.toString( taxaEfetivaMensal));
		simulacaoComSeguro.setTaxaEfetivaAnual(Double.toString(taxaEfetivaAnual));
		return simulacaoComSeguro;
	}
//RN048 -  Taxa de Juros por Convenente ou Grupo do ConvênioAjuda
//As taxas de juros são cadastradas por Convenente e por Grupo de Convênio. 
//Para simulação do contrato, o sistema deve verificar primeiro se existe taxa de juros cadastrada para o grupo de convênios, 
	//caso não exista taxa cadastrada, deve ser concultada a taxa cadastrada para o convênio.
	private TaxaJuro buscaTaxaJuro(GrupoTaxaTO grupoTaxa , int prazo, String codigoConvenio) throws Exception {
		Convenio convenio=convenioBean.consultar(Long.parseLong(codigoConvenio),null);
		
		long codigoGrupo=convenio.getGrupo().getId();
		
		TaxaJuro taxaJuro = taxaJuroService.findVigente(0, (int) codigoGrupo, prazo);
		if (taxaJuro != null){
			return taxaJuro;
		}else{
			taxaJuro = taxaJuroService.findVigente(Integer.parseInt(codigoConvenio), (int)codigoGrupo, prazo);
		}
		if (taxaJuro == null)
			throw new BusinessException("MA0015");
		return taxaJuro;
	}

	private TaxaIOF buscaTaxaIOF() throws Exception {
		TaxaIOF taxaIOF = taxaIOFService.findVigente();		
		if (taxaIOF == null)
			throw new BusinessException("MA0014");
		return taxaIOF;
	}

	private TaxaSeguro buscaTaxaSeguro(int idade, int prazo) throws Exception {
		TaxaSeguro taxaSeguro = taxaSeguroService.findVigente(prazo, idade);
		return taxaSeguro;
	}

	private ConvenioTO buscaConvenio(Long idConvenio) throws Exception {
		ConvenioTO convenioCompleto = null;
		
		TypedQuery<ConvenioTO> query = em.createNamedQuery("Convenio.consultarPorId", ConvenioTO.class);
		query.setParameter("pId", idConvenio);
		
		List<ConvenioTO> resultado = query.getResultList();
		
		if (!resultado.isEmpty())
			convenioCompleto = resultado.get(0);
		
		if (convenioCompleto == null) {
			throw new BusinessException("[REGRA_NEGOCIO]Nenhum Convenio Selecionado!");
		}
		return convenioCompleto;
	}
	private double calculaTaxaEfetivaMensal(double taxaJuros, int prazo){
		double resultado=(Math.pow(1+(taxaJuros/prazo),prazo))-1;
		return resultado;
	}
	
	private double calculaTaxaEfetivaAnual(double taxaEfetivaMensal){
		return taxaEfetivaMensal*12;
		
	}
	
}
