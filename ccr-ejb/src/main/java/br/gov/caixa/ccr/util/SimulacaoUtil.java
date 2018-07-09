package br.gov.caixa.ccr.util;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import br.gov.caixa.ccr.dominio.CET;
import br.gov.caixa.ccr.dominio.PrestacaoPrice;
import br.gov.caixa.ccr.dominio.Simular;
import br.gov.caixa.ccr.dominio.ValorReajusteSimulacao;
import br.gov.caixa.ccr.dominio.transicao.ConvenioTO;

public class SimulacaoUtil {

	private static final int QT_DIAS_MES = 30;
	private static final int QT_DIAS_ANO = 365;
	
	public static final String KEY_LIMITE_EMISSAO = "KEY_DATA_LIMITE_EMISSAO_EXTRATO";
	public static final String KEY_VCTO_1A_PRESTACAO = "KEY_DATA_VENCIMENTO_1A_PRESTACAO";
	public static final String KEY_BASE_CONTRATO = "KEY_DATA_BASE_CONTRATO";
	public static final String KEY_VCTO_CONTRATO = "KEY_DATA_VENCIMENTO_CONTRATO";
	
	/**
	 * Calcula as datas da simulação:<br />
	 * Data Limite emissao extrato<br />
	 * Data Vencimento primeira prestacao<br />
	 * Data Base do contrato<br />
	 * Data Vencimento do contrato (última prestação)
	 * @param dataSimulacao
	 * @param prazoEmissao
	 * @param diaCredito
	 * @param prazo
	 * @return HashMap<String, Date>
	 */
	
	public static HashMap<String, Date> calculaDatas(Simular simulacao, ConvenioTO convenioCompleto) {
		Date dataSimulacao = SimulacaoUtil.calculaDiaUtil(simulacao.dataContratacaoConvertida());

		return calculaDatas(dataSimulacao,convenioCompleto.getPrzEmissaoExtrato(),convenioCompleto.getDiaCreditoSal(),Integer.parseInt(simulacao.getPrazo()));
	}
	public static HashMap<String, Date> calculaDatas(Date dataSimulacao, int prazoEmissao, int diaCredito, int prazo) {
		HashMap<String, Date> hashDatas = new HashMap<String, Date>();
		
		/***********************************************  
		 * Calcula data limite emissao extrato         *
		 ***********************************************/
		Date dataLimiteEmissao = DataUtil.adicionarDiaEmData(dataSimulacao, prazoEmissao);
		hashDatas.put(KEY_LIMITE_EMISSAO, dataLimiteEmissao);
		
		/*********************************************** 
		 * Calcula data vencimento primeira prestacao  *
		 ***********************************************/
		Date data1aPrestacao = new Date(0);
		int prazo1aPestacao = 0, countMeses = 0;
		
		while (!(prazo1aPestacao > 29 && data1aPrestacao.compareTo(dataLimiteEmissao) >= 0 )) {
			countMeses++;
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(dataSimulacao);
			
			if (diaCredito > 0 && diaCredito < 29)
				cal.set(Calendar.DATE, diaCredito);
			
			data1aPrestacao = DataUtil.adicionarMeses(cal.getTime(), countMeses);
			prazo1aPestacao = DataUtil.calcularDiferencaDias(dataSimulacao, data1aPrestacao);
		}
		
		hashDatas.put(KEY_VCTO_1A_PRESTACAO, data1aPrestacao);
		
		/************************************************ 
		 * Calcula a data base do contrato              *
		 ************************************************/
		Date dataBase = new Date();
		
		if (countMeses > 0) {
			countMeses--;
		}
		
		Calendar cal = Calendar.getInstance();		
		cal.setTime(dataSimulacao);
		
		if (diaCredito > 0 && diaCredito < 29)
			cal.set(Calendar.DATE, diaCredito);
		
		if (countMeses == 0)
			dataBase = cal.getTime();
		else
			dataBase = DataUtil.adicionarMesEmData(cal.getTime(), countMeses);
		
		hashDatas.put(KEY_BASE_CONTRATO, dataBase);
		
		/************************************************ 
		 * Calcula data vencimento do contrato          *
		 ************************************************/
		Date dataVencimentoContrato = new Date();
		dataVencimentoContrato = DataUtil.adicionarMeses(dataBase, prazo);
		hashDatas.put(KEY_VCTO_CONTRATO, dataVencimentoContrato);
		
		return hashDatas;
	}
	
	/**
	 * Retorna o proximo dia util da data informada<br />
	 * Considera apenas Sabado e domingo, não verifica feriados *por enquanto
	 * @param dataSimulacao
	 * @return data util
	 */
	public static Date calculaDiaUtil(Date dataSimulacao) {
		Calendar cal = Calendar.getInstance();
		
		if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
			int proxDiaUtil = cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY ? 1 : 2;
			cal.set(Calendar.DATE, cal.get(Calendar.DATE) + proxDiaUtil);
		} 
		
		return cal.getTime();
	}
	
	/**
	 * Calcula o valor da prestação pela price <br />
	 * Equivalente a subrotina KCRSB001
	 * @param prazo - prazo do contrato
	 * @param valorContrato - montante total do contrato
	 * @param taxaJuro - percentual taxa de juros
	 * @return valorPrestacao - soma dos valores parcelaAmortizacao e parcelaJuros
	 * @throws Exception
	 **/
	public static PrestacaoPrice calculaValorPrestacaoPrice(int prazo, double valorContrato, double taxaJuro) throws Exception {
		
		if (prazo == 0)
			throw new Exception("[REGRA_NEGOCIO]Prazo informado igual a zero!");
		
		if (valorContrato == 0)
			throw new Exception("[REGRA_NEGOCIO]Valor do contrato (montante) igual a zero!");
		
		if (taxaJuro == 0)
			throw new Exception("[REGRA_NEGOCIO]Taxa de juros igual a zero!");
		
		double i = 0d, v1 = 0d, v2 = 0d, v3 = 0d, montante = 0d;
		double prestacao = 0d, parcelaAmortizacao = 0d, parcelaJuros = 0d;
		
		i = taxaJuro / 100;
		v1 = roundedValue( Math.pow((1 + i), -prazo) );
		v2 = roundedValue( 1 - v1 );
		v3 = roundedValue( i / v2 );
		
		montante = valorContrato;
		prestacao = roundedValue( v3 * montante );
		parcelaJuros = roundedValue( montante * i );
		
		if (prazo == 1)
			parcelaAmortizacao = montante;
		else
			parcelaAmortizacao = prestacao - parcelaJuros;
				
		return new PrestacaoPrice(parcelaAmortizacao, parcelaJuros);
	}

	/**
	 * Calcula o valor do IOF básico e adicional<br />
	 * Equivalente a subrotina KCRSB004
	 * @param prazo - prazo do contrato
	 * @param valorBase - valor base do contrato
	 * @param dataLiberacao - data da liberação do contrato
	 * @param dataPrimeiraPrestacao - data da primeira prestação
	 * @param taxaJuro - taxa de juros do contrato
	 * @param taxaIOFbasico - percentual da taxa iof basica
	 * @param taxaIOFAdicional - percentual da taxa iof adicional
	 * @return valorIOF - valor calculado o IOF, soma dos valores IOF básico e adicional
	 * @throws Exception
	 */
	public static double calculaValorIOF(int prazo, double valorBase, Date dataLiberacao, Date dataPrimeiraPrestacao, 
				            double taxaJuro, double taxaIOFbasico, double taxaIOFAdicional) throws Exception {
		
		if (prazo == 0)
			throw new Exception("[REGRA_NEGOCIO]Prazo informado igual a zero!");
		
		if (valorBase == 0)
			throw new Exception("[REGRA_NEGOCIO]Valor base do contrato igual a zero!");
		
		if (taxaJuro == 0)
			throw new Exception("[REGRA_NEGOCIO]Taxa de juros igual a zero!");
		
		if (taxaIOFbasico == 0)
			throw new Exception("[REGRA_NEGOCIO]Taxa IOF básico igual a zero!");
		
		if (taxaIOFAdicional == 0)
			throw new Exception("[REGRA_NEGOCIO]Taxa IOF adicional igual a zero!");
		
		double valorIOFBasico = 0d, valorIOFAdicional = 0d, valorMensal=0d;;
		int quantidadeDias = 0, prazoCalculo = prazo;
				
		valorIOFAdicional = valorBase * (taxaIOFAdicional / 100);		
		
		for (int prestacao = 1; prestacao <= prazo; prestacao++) {
			if (quantidadeDias < QT_DIAS_ANO) {
				if (prestacao == 1) {					
					quantidadeDias = (int) DataUtil.diferencaEmDias(dataLiberacao, dataPrimeiraPrestacao);					
				} else {
					Date dataBaseCalculo = DataUtil.adicionarMesEmData(dataPrimeiraPrestacao, prestacao - 1);
					quantidadeDias = (int) DataUtil.diferencaEmDias(dataLiberacao, dataBaseCalculo);
				}
				
				if (quantidadeDias > QT_DIAS_ANO) {
						quantidadeDias = QT_DIAS_ANO; 
					}				
				
			}
			
			PrestacaoPrice prestacaoPrice = calculaValorPrestacaoPrice(prazoCalculo, valorBase, taxaJuro);				
			valorMensal = ( prestacaoPrice.getAmortizacao()   * quantidadeDias * (taxaIOFbasico / 100) );			
			valorIOFBasico +=valorMensal;
			valorBase = valorBase - prestacaoPrice.getAmortizacao();			
			prazoCalculo--;			
		}		
		
		return valorIOFBasico + valorIOFAdicional;
		
	}
	
	/**
	 * Calcula o fator de ajuste da contratação (quando é contratação pelo valor líquido
	 * e aplicar nos valores de (IOF, IOF Complementar e seguro)<br />
	 * Equivalente a subrotina KCRSB005
	 * @param valorBase -- valor base do contrato
	 * @param valorJuroAcerto -- valor do juro de acerto
	 * @param valorIOF -- valor do IOF
	 * @param valorIOFComplementar -- valor complementar do IOF (apenas na renovação) 
	 * @param valorSeguro -- valor seguro
	 * @return ValorReajusteSimulacao
	 * @throws Exception
	 */
	public static ValorReajusteSimulacao calculaFatorAjuste(double valorBase, double valorJuroAcerto, double valorIOF, 
										 double valorIOFComplementar, double valorSeguro) throws Exception {
	
		if (valorBase == 0)
			throw new Exception("[REGRA_NEGOCIO]Valor base igual a zero!");
		
		double percentualFator = 0d, valorTotal = 0d;
		ValorReajusteSimulacao reajuste = new ValorReajusteSimulacao();
		
		percentualFator = (1 / ( 1 - ((valorJuroAcerto + valorIOF + valorIOFComplementar + valorSeguro) / valorBase) ));
		reajuste.setPercentualFator(percentualFator);
		
		reajuste.setValorIOFCalculado( valorIOF * percentualFator );
		reajuste.setValorIOFComplementar( valorIOFComplementar * percentualFator );
		reajuste.setValorSeguro( valorSeguro * percentualFator );
		
		valorTotal = valorBase + reajuste.getValorIOFCalculado() + reajuste.getValorIOFComplementar() + reajuste.getValorSeguro();
		reajuste.setValorTotal(valorTotal);
		
		return reajuste;
		
	}
	
	/**
	 * Calcula o valor da involução a partir do valor da prestacao, taxa de juros e prazo
	 * Valor da involução é o valor líquido do contrato<br />
	 * Equivalente a subrotina KCRSB006 
	 * @param taxaJuro
	 * @param valorPrestacaoo
	 * @param prazo
	 * @return
	 * @throws Exception
	 */
	public static double calculaValorInvolucao(double taxaJuro, double valorPrestacao, int prazo) throws Exception {
		
		if (taxaJuro == 0)
			throw new Exception("[REGRA_NEGOCIO]Taxa de juro igual a zero!");
		
		if (valorPrestacao == 0)
			throw new Exception("[REGRA_NEGOCIO]Valor de prestação igual a zero!");
		
		if (prazo == 0)
			throw new Exception("[REGRA_NEGOCIO]Prazo igual a zero!");
		
		double valorInvolucao = 0d;
		double juro1 = 0d, juro2 = 0d, juro3 = 0d;
		
		juro1 =  Math.pow(1 + (taxaJuro / 100), prazo) - 1 ;
		juro2 =  Math.pow(1 + (taxaJuro / 100), prazo) * (taxaJuro / 100);
		juro3 =  juro1 / juro2 ;
		
		valorInvolucao = valorPrestacao * juro3;
		return valorInvolucao;
	}
	
	/**
	 * Calcula o valor de juro de acerto a partir do valor base, quantidade de dias e taxa de juro<br />
	 * Equivalente a subrotina KCRSB007
	 * @param taxaJuro
	 * @param valorBase
	 * @param quantidadeDias
	 * @return valorJuroAcerto
	 * @throws Exception
	 */
	public static double calculaValorJuroAcerto(double taxaJuro, double valorBase, int quantidadeDias) throws Exception {
		
		if (taxaJuro == 0)
			throw new Exception("[REGRA_NEGOCIO]Taxa de juro igual a zero!");
		
		if (valorBase == 0)
			throw new Exception("[REGRA_NEGOCIO]Valor de prestação igual a zero!");
		
		if (quantidadeDias == 0)
			return 0d;
		
		double valorJuroAcerto = 0d, coeficienteJuros = 0d;
		coeficienteJuros = (taxaJuro / (double)(QT_DIAS_MES * 100)) * quantidadeDias;
		valorJuroAcerto = ( valorBase * coeficienteJuros );
		
		return valorJuroAcerto;
	}	
	
	/**
	 * Calcula o valor do seguro prestamista<br />
	 * Equivalente a subrotina KCRSB008
	 * @param dataNascimento
	 * @param dataVencimento
	 * @param taxaSeguro
	 * @param valorContrato
	 * @return valorSeguro
	 * @throws Exception
	 */
	public static double calculaValorSeguroPrestamista(Date dataNascimento, Date dataVencimento, double taxaSeguro, double valorContrato) throws Exception {
		
		if (taxaSeguro == 0)
			throw new Exception("[REGRA_NEGOCIO]Taxa de seguro igual a zero!");
		
		if (valorContrato == 0)
			throw new Exception("[REGRA_NEGOCIO]Valor do Contrato igual a zero!");
		
		double valorSeguro = 0d;
		int quantidadeMeses = 0;
		
		quantidadeMeses = DataUtil.calcularDiferencaMeses(dataNascimento, dataVencimento);
		if (quantidadeMeses > 960)
			valorSeguro = 0;
		else
			valorSeguro = roundedValue( valorContrato * taxaSeguro / 100 );
		
		if(Double.compare(valorSeguro, 0.01)<0  ){
			valorSeguro=0;
		}
		return valorSeguro;
	}
	
	/**
	 * Calcula CET mensal e anual<br />
	 * Equivalente a subrotina KCRSB009
	 * @param valorContrato
	 * @param valorPrestacao
	 * @param taxaJuro
	 * @param prazo
	 * @param dataLiberacao
	 * @param dataVcto1aPrestacao
	 * @return CET
	 * @throws Exception
	 */
	public static CET calculaValorCET(double valorContrato, double valorPrestacao, double taxaJuro, int prazo, 
									  Date dataLiberacao, Date dataVcto1aPrestacao) throws Exception {
		
		if (valorContrato == 0)
			throw new Exception("[REGRA_NEGOCIO]Valor do contrato igual a zero!");
		
		if (valorPrestacao == 0)
			throw new Exception("[REGRA_NEGOCIO]Valor da prestação igual a zero!");
		
		if (taxaJuro == 0)
			throw new Exception("[REGRA_NEGOCIO]Taxa de juro igual a zero!");
		
		if (prazo == 0)
			throw new Exception("[REGRA_NEGOCIO]Prazo igual a zero!");
		
		double taxaMensal = 0d, taxaAnual = 0d;
		int quantidadeDias = 0;
		Date dataPrestacao = dataVcto1aPrestacao; 
		
		taxaMensal = taxaJuro;
		taxaAnual = roundedValue( Math.pow(1 + (taxaMensal / 100), 12) - 1 );
		
		if (prazo == 1) {
			quantidadeDias = DataUtil.calcularDiferencaDias(dataLiberacao, dataPrestacao);
			taxaAnual = roundedValue( (Math.pow(valorPrestacao/valorContrato, (double) QT_DIAS_ANO/quantidadeDias) - 1) * 100 );
			taxaMensal = roundedValue( (Math.pow(valorPrestacao/valorContrato, (double) QT_DIAS_MES/quantidadeDias) - 1) * 100 );
		} else {
			int count = 0, prazoCalc = prazo;
			double valorDescapitalizacaoPrest = 0d, valorDescapitalizacaoContr = 0d;
			Date dataProximaPrestacao;
			
			while (count < prazo) {
				count++;
				
				if (count == 1) {
					quantidadeDias = DataUtil.calcularDiferencaDias(dataLiberacao, dataPrestacao);
				} else {
					prazoCalc = count - 1;
					dataProximaPrestacao = DataUtil.adicionarMesEmData(dataPrestacao, prazoCalc);
					quantidadeDias = DataUtil.calcularDiferencaDias(dataLiberacao, dataProximaPrestacao);
				}
					
				valorDescapitalizacaoPrest = roundedValue( valorPrestacao / Math.pow(1 + taxaAnual, (double) quantidadeDias / QT_DIAS_ANO) );
				valorDescapitalizacaoContr = roundedValue(valorDescapitalizacaoContr + valorDescapitalizacaoPrest);
			}
			
			taxaAnual = roundedValue( taxaAnual * ((double) valorDescapitalizacaoContr / valorContrato) * 100 );
			taxaMensal = roundedValue( (Math.pow(1 + (taxaAnual / 100), (double) QT_DIAS_MES / QT_DIAS_ANO) - 1) * 100 );
		}
		
		return new CET(taxaMensal, taxaAnual);
	}
	
	public static double roundedValue(double value) {
		return value;
		//return BigDecimal.valueOf(value).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	public static double roundedUp(double value) {
		return BigDecimal.valueOf(value).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
}
