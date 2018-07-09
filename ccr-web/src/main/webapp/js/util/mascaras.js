
/**
 * @author c110503
 * 
 * Scritp criado para tratamento das mascaras dos sistema. Tem dependencia das
 * seguintes JS: JQUERY.MASKEDINPUT Version: 1.3.1 JQUERY.PRICE-FORMAT
 * JQUERY.ALPHANUM
 * 
 *@TODO
 *	Carregar as mascaras sob demanda. 
 *	Parametrizar o carregamento das funcoes de mascara
 * 
 */

function mascaras() {
	jQuery(function($) {

		/**
		 * Função utilizada para formatar CNPJ
		 * 
		 * Depende de JQUERY.MASKEDINPUT
		 */
		$(".cnpj").mask("99.999.999/9999-99").off('focus.mask')
				.off('blur.mask');

		/**
		 * Função utilizada para formatar CPF
		 * 
		 * Depende de JQUERY.MASKEDINPUT
		 */
		$(".cpf").mask("999.999.999-99").off('focus.mask').off('blur.mask');
		/**
		 * Função utilizada para formatar RG
		 * 
		 * Depende de JQUERY.MASKEDINPUT
		 */
		$(".rg").mask("9.999.999").off('focus.mask').off('blur.mask');
		/**
		 * Função utilizada para formatar RG_SP
		 * 
		 * Depende de JQUERY.MASKEDINPUT
		 */
		$(".rg-sp").mask("9.999.999 aaa").off('focus.mask').off('blur.mask');
		/**
		 * Função utilizada para formatar RG_SP_UF
		 * 
		 * Depende de JQUERY.MASKEDINPUT
		 */
		$(".rg-sp-uf").mask("9.999.999 aaa/aa").off('focus.mask').off(
				'blur.mask');
		/**
		 * Função utilizada para formatar Telefone
		 * 
		 * Depende de JQUERY.MASKEDINPUT
		 */
		$(".telefone").mask("9999-9999").off('focus.mask').off('blur.mask');
		/**
		 * Função utilizada para formatar DDDTelefone
		 * 
		 * Depende de JQUERY.MASKEDINPUT
		 */
		$(".telefone-ddd").mask("(99) 9999-9999").off('focus.mask').off(
				'blur.mask');
		/**
		 * Função utilizada para formatar TelefoneCompleto
		 * 
		 * Depende de JQUERY.MASKEDINPUT
		 */
		$(".telefone-ddi").mask("+99 (99) 9999-9999").off('focus.mask').off(
				'blur.mask');
		/**
		 * Função utilizada para formatar CEP
		 * 
		 * Depende de JQUERY.MASKEDINPUT
		 */
		$(".cep").mask("99999-999").off('focus.mask').off('blur.mask');
		/**
		 * Função utilizada para formatar PlacaVeiculo
		 * 
		 * Depende de JQUERY.MASKEDINPUT
		 */
		$(".placa").mask("aaa 9999").off('focus.mask').off('blur.mask');
		/**
		 * Função utilizada para formatar HoraMinuto
		 * 
		 * Depende de JQUERY.MASKEDINPUT
		 */
		$(".hora-min").mask("99:99");
		/**
		 * Função utilizada para formatar HoraCompleto
		 * 
		 * Depende de JQUERY.MASKEDINPUT
		 */
		$(".hora-min-seg").mask("99:99:99");
		/**
		 * Função utilizada para formatar Data
		 * 
		 * Depende de JQUERY.MASKEDINPUT
		 */
		$(".data").mask("99/99/9999").off('focus.mask').off('blur.mask');
		/**
		 * Função utilizada para formatar NumeroConta
		 * 
		 * Depende de JQUERY.MASKEDINPUT
		 */
		$(".numeroConta").mask("9999999999-**").off('focus.mask').off(
				'blur.mask');
		/**
		 * Função utilizada para formatar NumeroContaCompleto
		 * 
		 * Depende de JQUERY.MASKEDINPUT
		 */
		$(".numeroContaCompleto").mask("9999.999.99999999-9").off('focus.mask')
				.off('blur.mask');
		/**
		 * Função utilizada para formatar DataHora
		 * 
		 * Depende de JQUERY.MASKEDINPUT
		 */
		$(".timeStamp").mask("99/99/9999 99:99").off('focus.mask').off(
				'blur.mask');
		/**
		 * Função utilizada para formatar NumeroContrato
		 * 
		 * Depende de JQUERY.MASKEDINPUT
		 */
		$(".numeroContrato").mask("99.9999.999.9999999-99").off('focus.mask')
				.off('blur.mask');
		/**
		 * Função utilizada para formatar Alfanumerico
		 * 
		 * Depende de JQUERY.MASKEDINPUT
		 */
		// Alphanum.js
		$(".alpha").alpha(); // Alphanum
		/**
		 * Função utilizada para formatar Numero
		 * 
		 * Depende de JQUERY.MASKEDINPUT
		 */
		$('.numero').numeric(); // numeric
		/**
		 * Função utilizada para formatar Monetario
		 * 
		 * Depende de JQUERY.MASKEDINPUT
		 */
		$('.moeda').priceFormat({
			prefix : '',
			centsSeparator : ',',
			thousandsSeparator : '.'
		});
		
		/**
		 * Função utilizada para formatar Percentual
		 * 
		 * Depende de JQUERY.MASKEDINPUT
		 */
		$(".percentual").mask("9,99", {placeholder : "0"}).off('focus.mask').off('blur.mask');
		
		/**
		 * Função utilizada para formatar Percentual
		 * 
		 * Depende de JQUERY.MASKEDINPUT
		 */
		$(".taxaJuro").mask("99,99999", {placeholder : "0"}).off('focus.mask').off('blur.mask');
		
		/**
		 * Função utilizada para formatar a data
		 * 
		 * Depende de JQUERY
		 */
		 $('.datetimepicker').datetimepicker({
		        language: 'pt-BR',
		        pickTime: false
		    });
	});
}

/**
 * Aplica as máscaras em um el
 * @param el
 */
function mascarasEl(el) {
		/**
		 * Função utilizada para formatar CNPJ
		 * 
		 * Depende de JQUERY.MASKEDINPUT
		 */
		el.find(".cnpj").mask("99.999.999/9999-99");

		/**
		 * Função utilizada para formatar CPF
		 * 
		 * Depende de JQUERY.MASKEDINPUT
		 */
		el.find(".cpf").mask("999.999.999-99");
		/**
		 * Função utilizada para formatar RG
		 * 
		 * Depende de JQUERY.MASKEDINPUT
		 */
		el.find(".rg").mask("9.999.999");
		/**
		 * Função utilizada para formatar RG_SP
		 * 
		 * Depende de JQUERY.MASKEDINPUT
		 */
		el.find(".rg-sp").mask("9.999.999 aaa");
		/**
		 * Função utilizada para formatar RG_SP_UF
		 * 
		 * Depende de JQUERY.MASKEDINPUT
		 */
		el.find(".rg-sp-uf").mask("9.999.999 aaa/aa");
		/**
		 * Função utilizada para formatar Telefone
		 * 
		 * Depende de JQUERY.MASKEDINPUT
		 */
		el.find(".telefone").mask("9999-9999");
		/**
		 * Função utilizada para formatar DDDTelefone
		 * 
		 * Depende de JQUERY.MASKEDINPUT
		 */
		el.find(".telefone-ddd").mask("(99) 9999-9999");
		/**
		 * Função utilizada para formatar TelefoneCompleto
		 * 
		 * Depende de JQUERY.MASKEDINPUT
		 */
		el.find(".telefone-ddi").mask("+99 (99) 9999-9999");
		/**
		 * Função utilizada para formatar CEP
		 * 
		 * Depende de JQUERY.MASKEDINPUT
		 */
		el.find(".cep").mask("99999-999");
		/**
		 * Função utilizada para formatar PlacaVeiculo
		 * 
		 * Depende de JQUERY.MASKEDINPUT
		 */
		el.find(".placa").mask("aaa 9999");
		/**
		 * Função utilizada para formatar HoraMinuto
		 * 
		 * Depende de JQUERY.MASKEDINPUT
		 */
		el.find(".hora-min").mask("99:99");
		/**
		 * Função utilizada para formatar HoraCompleto
		 * 
		 * Depende de JQUERY.MASKEDINPUT
		 */
		el.find(".hora-min-seg").mask("99:99:99");
		/**
		 * Função utilizada para formatar Data
		 * 
		 * Depende de JQUERY.MASKEDINPUT
		 */
		el.find(".data").mask("99/99/9999");
		/**
		 * Função utilizada para formatar NumeroConta
		 * 
		 * Depende de JQUERY.MASKEDINPUT
		 */
		el.find(".numeroConta").mask("9999999999-**");
		/**
		 * Função utilizada para formatar NumeroContaCompleto
		 * 
		 * Depende de JQUERY.MASKEDINPUT
		 */
		el.find(".numeroContaCompleto").mask("9999.999.99999999-9");
		/**
		 * Função utilizada para formatar DataHora
		 * 
		 * Depende de JQUERY.MASKEDINPUT
		 */
		el.find(".timeStamp").mask("99/99/9999 99:99");
		/**
		 * Função utilizada para formatar NumeroContrato
		 * 
		 * Depende de JQUERY.MASKEDINPUT
		 */
		el.find(".numeroContrato").mask("99.9999.999.9999999-99");
		/**
		 * Função utilizada para formatar Alfanumerico
		 * 
		 * Depende de JQUERY.MASKEDINPUT
		 */
		// Alphanum.js
		el.find(".alpha").alpha(); // Alphanum
		/**
		 * Função utilizada para formatar Numero
		 * 
		 * Depende de JQUERY.MASKEDINPUT
		 */
		el.find('.numero').numeric(); // numeric
		/**
		 * Função utilizada para formatar Monetario
		 * 
		 * Depende de JQUERY.MASKEDINPUT
		 */
		el.find('.moeda').priceFormat({
			prefix : '',
			centsSeparator : ',',
			thousandsSeparator : '.'
		});
		
		/**
		 * Função utilizada para formatar Percentual
		 * 
		 * Depende de JQUERY.MASKEDINPUT
		 */
		el.find(".percentual").mask("999,99");
		
		/**
		 * Função utilizada para formatar Percentual
		 * 
		 * Depende de JQUERY.MASKEDINPUT
		 */
		el.find(".taxaJuro").mask("99,9?9999");
		
		/**
		 * Função utilizada para formatar o beneficio - 
		 * 
		 * Depende de JQUERY.MASKEDINPUT
		 */
		el.find(".beneficio").mask("9999999999-9");
		
		/**
		 * Função utilizada para formatar a data
		 * 
		 * Depende de JQUERY
		 */
		 el.find('.datetimepicker').datetimepicker({
		        language: 'pt-BR',
		        pickTime: false
	     });
}
