loadMask();
/*
 * Função responsável pelo carregamento das mascaras.
 */
function loadMask() {
	jQuery(function($){		
		
		$('div.data').datetimepicker({
		    language: "pt-BR",
        	pickTime: false
		}).on('changeDate', function (ev) {			
			$(this).datetimepicker('hide');
		});
		
		$(".cnpj").mask("99.999.999/9999-99");
	    $(".cpf").mask("999.999.999-99");
	    $(".rg").mask("9.999.999");
	    $(".rg-sp").mask("9.999.999 aaa");
	    $(".rg-sp-uf").mask("9.999.999 aaa/aa");
	    $(".telefone").mask("9999-9999");
	    $(".telefone-ddd").mask("(99) 9999-9999");
	    $(".telefone-ddi").mask("+99 (99) 9999-9999");
	    $(".cep").mask("99999-999").off('focus.mask');
	    $(".placa").mask("aaa 9999").off('focus.mask');
	    $(".hora-min").mask("99:99");
	    $(".hora-min-seg").mask("99:99:99");
	    $(".data").mask("99/99/9999");
	    $(".numeroConta").mask("9999999999-**");
	    $(".timeStamp").mask("99/99/9999 99:99");
	    $(".diasEmAtraso").mask("9.999");
	    $('.percent').mask('##9,99');
	    $(".usuarioCaixa").mask("a999999");
	    
		$(".numeroContratoAPX").mask("99.9999.999.9999999-99");
		
		$('.indice').priceFormat({
			prefix: '',
	    	centsSeparator: ',',	    	
	    	thousandsSeparator: '',
	    	centsLimit: 2
		}); 
		
		$('.indice5').priceFormat({
			limit: 8,
			prefix: '',
	    	centsSeparator: ',',	    	
	    	thousandsSeparator: '',
	    	centsLimit: 5
	    	
		}); 

		$('.indice10').priceFormat({
			prefix: '',
	    	centsSeparator: ',',	    	
	    	thousandsSeparator: '',
	    	centsLimit: 10
		}); 

		//Alphanum.js
		
		$(".alpha").alpha(); //Alphanum
		$('.numero').numeric(); //numeric
		$('.numeroreal').numeric({allowMinus : false,}); // Número real
		
		//Price-format.js
		$('.moeda').priceFormat({
			prefix: '',
	    	centsSeparator: ',',	    	
	    	thousandsSeparator: '.'
		}); 
		
		$('.moeda10').priceFormat({
			prefix: '',
	    	centsSeparator: ',',	
	    	 limit: 10,
	    	thousandsSeparator: '.'
		});
		
		$('.moeda5').priceFormat({
			prefix: '',
	    	centsSeparator: ',',	
	    	 limit: 5,
	    	thousandsSeparator: '.'
		});
		
		$('.moedaSemCentavo').priceFormat({
			prefix: '',
	    	centsSeparator: '',	    	
	    	thousandsSeparator: '.',
	    	centsLimit: 0
		}); 
		
		$('.quantidade').priceFormat({
			prefix: '',
	    	centsSeparator: '',	
	    	limit: 10,
	    	centsLimit: 0,
	    	thousandsSeparator: '.'
		}); 
	});
}

function loadMaskEl($el) {
	jQuery(function($){		
		
		$el.find('div.data').datetimepicker({
		    language: "pt-BR",
        	pickTime: false
		}).on('changeDate', function (ev) {			
			$(this).datetimepicker('hide');
		});
		
		$el.find(".cnpj").mask("99.999.999/9999-99");
	    $el.find(".cpf").mask("999.999.999-99");
	    $el.find(".rg").mask("9.999.999");
	    $el.find(".rg-sp").mask("9.999.999 aaa");
	    $el.find(".rg-sp-uf").mask("9.999.999 aaa/aa");
	    $el.find(".telefone").mask("9999-9999");
	    $el.find(".telefone-ddd").mask("(99) 9999-9999");
	    $el.find(".telefone-ddi").mask("+99 (99) 9999-9999");
	    $el.find(".cep").mask("99999-999").off('focus.mask');
	    $el.find(".placa").mask("aaa 9999").off('focus.mask');
	    $el.find(".hora-min").mask("99:99");
	    $el.find(".hora-min-seg").mask("99:99:99");
	    $el.find(".data").mask("99/99/9999");
	    $el.find(".numeroConta").mask("9999999999-**");
	    $el.find(".timeStamp").mask("99/99/9999 99:99");
	    $el.find(".diasEmAtraso").mask("9.999");
	    $el.find('.percent').mask('##9,99');
	    $el.find(".usuarioCaixa").mask("a999999");
	    
	    $el.find(".numeroContratoAPX").mask("99.9999.999.9999999-99");
		
	    $el.find('.indice').priceFormat({
			prefix: '',
	    	centsSeparator: ',',	    	
	    	thousandsSeparator: '',
	    	centsLimit: 2
		}); 
		
	    $el.find('.indice5').priceFormat({
	    	limit: 8,
	    	prefix: '',
	    	centsSeparator: ',',	    	
	    	thousandsSeparator: '',
	    	centsLimit: 5
	    	
		}); 

	    $el.find('.indice10').priceFormat({
			prefix: '',
	    	centsSeparator: ',',	    	
	    	thousandsSeparator: '',
	    	centsLimit: 10
		}); 

		//Alphanum.js
		
		$el.find(".alpha").alpha(); //Alphanum
		$el.find('.numero').numeric(); //numeric
		$el.find('.numeroreal').numeric({allowMinus : false,}); // Número real
		
		//Price-format.js
		$el.find('.moeda').priceFormat({
			prefix: '',
	    	centsSeparator: ',',	    	
	    	thousandsSeparator: '.'
		}); 
		
		$el.find('.moeda10').priceFormat({
			prefix: '',
	    	centsSeparator: ',',	
	    	 limit: 10,
	    	thousandsSeparator: '.'
		});
		
		$el.find('.moeda5').priceFormat({
			prefix: '',
	    	centsSeparator: ',',	
	    	 limit: 5,
	    	thousandsSeparator: '.'
		});
		
		$el.find('.moedaSemCentavo').priceFormat({
			prefix: '',
	    	centsSeparator: '',	    	
	    	thousandsSeparator: '.',
	    	centsLimit: 0
		}); 
		
		$el.find('.quantidade').priceFormat({
			prefix: '',
	    	centsSeparator: '',	
	    	limit: 10,
	    	centsLimit: 0,
	    	thousandsSeparator: '.'
		}); 
	});
}

//# sourceURL=mask-conf-ccr.js