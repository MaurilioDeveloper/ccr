loadMask();
/*
 * Função responsável pelo carregamento das mascaras.
 */
function loadMask() {
	jQuery(function($){
		
		//Maskedinput.js
		
		$(".cnpj").mask("99.999.999/9999-99").off('focus.mask').off('blur.mask');
		$(".cpf").mask("999.999.999-99").off('focus.mask').off('blur.mask');
		$(".rg").mask("9.999.999").off('focus.mask').off('blur.mask');
		$(".rg-sp").mask("9.999.999 aaa").off('focus.mask').off('blur.mask');
		$(".rg-sp-uf").mask("9.999.999 aaa/aa").off('focus.mask').off('blur.mask');
		$(".telefone").mask("9999-9999").off('focus.mask').off('blur.mask');
		$(".telefone-ddd").mask("(99) 9999-9999").off('focus.mask').off('blur.mask');
		$(".telefone-ddi").mask("+99 (99) 9999-9999").off('focus.mask').off('blur.mask');
		$(".cep").mask("99999-999").off('focus.mask').off('blur.mask');
		$(".placa").mask("aaa 9999").off('focus.mask').off('blur.mask');
		$(".hora-min").mask("99:99");
		$(".hora-min-seg").mask("99:99:99");
		$(".data").mask("99/99/9999").off('focus.mask').off('blur.mask');
		$(".numeroConta").mask("9999999999-**").off('focus.mask').off('blur.mask');
		$(".timeStamp").mask("99/99/9999 99:99").off('focus.mask').off('blur.mask');
		
		$(".numeroContrato").mask("99.9999.999.9999999-99").off('focus.mask').off('blur.mask');
		$(".matricula").mask("a999999").off('focus.mask').off('blur.mask');
		
		//Alphanum.js
		
		$(".alpha").alpha(); //Alphanum
		$('.numero').numeric(); //numeric
		$('.inteiro').numeric({
			allowPlus           : false, // Allow the + sign
			allowMinus          : false,  // Allow the - sign
			allowThouSep        : false,  // Allow the thousands separator, default is the comma eg 12,000
			allowDecSep         : false,  // Allow the decimal separator, default is the fullstop eg 3.141
			allowLeadingSpaces  : false,
			maxDigits           : '',    // The max number of digits or '' for no max
			maxDecimalPlaces    : '',    // The max number of decimal places or '' for no max
			maxPreDecimalPlaces : ''     // The max number digits before the decimal point or '' for no max
		});
		
		//Price-format.js
		
		$('.moeda').priceFormat({
			prefix: '',
	    	centsSeparator: ',',	    	
	    	thousandsSeparator: '.'
		}); 
	});
}