/**
 * 
 * JavaScript que controla as ações da consulta de dados do CET:
 *  detalheConsultaCET.html
 *   
 * 
 */
define(['enumeracoes/eMensagemCCR',
        'util/retorno',
        'text!visao/contratacao/detalheConsultaCET.html',
        'text!visao/contratacao/impressaoConsultaCET.html',
        ], 
function(EMensagemCCR, Retorno, detalheConsultaCETTpl, impressaoConsultaCETTpl){

	var _this = null;
	var DetalheCosultaCETControle = Backbone.View.extend({

		className: 'DetalheCosultaCETControle',
		
		/**
		 * Templates
		 */
		detalheConsultaCETTemplate : _.template(detalheConsultaCETTpl),
		impressaoConsultaCETTemplate : _.template(impressaoConsultaCETTpl),
		
		/**
		 * Variaveis da classe
		 */
		validator   : new Validators(),
		message     : new Message(),
		comando     : null,
		dadosCET	: null,
		
		
		/**
		 * Função padrão de incialização do template html
		 * 
		 */
		initialize : function() {
			console.log("detalhe consulta CET controle - initialize");
			_this = this;
			_this.validator.withErrorRender(new BootstrapErrorRender());
		},
		
		render : function(e) {
			
			console.log("detalhe consulta CET controle - popup render");
												
		    var popupTemplate = ['<div class="popover popover-dados-cet" style="max-width:100% !important">',
	        '<div class="arrow"></div>',
	        '<div class="popover-content" style="overflow-y : scroll; height: 400px !important;"  >',
	        '</div>',
	        '</div>'].join('');
		    
		    var contentTemplate = _this.detalheConsultaCETTemplate({dadosCET: _this.dadosCET});
			
		    _this.closePopup();
		    
		    $(e.target).popover({
				content: contentTemplate,
				template: popupTemplate,
				placement: 'top',
				trigger: 'manual',
				html: true,
			}).on('hidden.bs.popover', function(e){
	            $(this).show();
	        })
		},
		
		togglePopup: function (e) {
			console.log("detalhe consulta CET controle - popup toggle");
			$(e.target).popover('toggle');
		},
		
		closePopup : function(){
			console.log("detalhe consulta CET controle - popup fechar");
			 $(".popover").popover("destroy");
		},
		
		printDadosCET : function(){
			console.log('imprimir dados CET');			
			
			var win = window.open('');
			win.document.write(_this.impressaoConsultaCETTemplate({dadosCET: _this.dadosCET}));
			win.print();
			win.close();
		},
		
		setDadosCET : function(data, identificador){
			
			switch(identificador){
				case 'manter':
					_this.setDadosCETManter(data);
				    break;
				case 'simular':
					_this.setDadosCETSimular(data);
				    break;
				case 'contratar':
					_this.setDadosCETContratar(data); 
				    break;
					
			}
		},
		
		setDadosCETManter : function(data){
			
		    _this.dadosCET = {
		    	//Dados e valores do contrato
			    dsProdutoContrato : data.avaliacaoOperacao.noProduto,
			    numContrato : data.nuContrato,
			    txJuros : data.pcTaxaJuroContrato,
			    prazoContrato : data.pzContrato,
			    vlTotalContrato : data.vrTotalContrato,
			    vlLiquido: data.vrLiquidoContrato,
			    vlIof: data.vrIof,
			    vlJurosAcerto: data.vrJuroAcerto,
			    vlSeguroPr: data.vrSeguroPrestamista,
			    
			    //Dados e valores do CET
			    pctCetliquido: data.vrLiquidoContrato / data.vrTotalContrato,
			    pctCetiof: data.pcCetIof,
			    pctCetjuroAcerto: data.pcCetJuroAcerto,
			    pctCetseguro: data.pcCetSeguro,			    
			    pctCetMensal: data.pcCetMensal,
			    pctCetAnual:  data.pcCetAnual,
				    
			};
		},
		
		setDadosCETSimular : function(data){
		    _this.dadosCET = {
		    		
		    	//Dados e valores do contrato
			    dsProdutoContrato : ' - ',
			    numContrato : ' - ',
			    txJuros : data.taxaJuros,
			    prazoContrato : data.prazo,
			    vlTotalContrato : data.valorTotalContrato,
			    vlLiquido: data.valorLiquido,
			    vlIof: data.iof,
			    vlJurosAcerto: data.juroAcerto,
			    vlSeguroPr: data.valorSeguro,
			    
			    //Dados e valores do CET
			    pctCetliquido: data.valorLiquido / data.valorTotalContrato,
			    pctCetiof: data.cetiof,
			    pctCetjuroAcerto: data.cetjuroAcerto,
			    pctCetseguro: data.cetseguro,			    
			    pctCetMensal: data.cetmensal,
			    pctCetAnual:  data.cetanual,
			    
			};
		},
		
		setDadosCETContratar : function(data){
			_this.dadosCET = {
		    	//Dados e valores do contrato
			    dsProdutoContrato : '',
			    numContrato : '',
			    txJuros : '',
			    prazoContrato : '',
			    vlTotalContrato : '',
			    vlLiquido: '',
			    vlIof: '',
			    vlJurosAcerto: '',
			    vlSeguroPr: '',
			    
			    //Dados e valores do CET
			    pctCetliquido: '',
			    pctCetiof: '',
			    pctCetjuroAcerto: '',
			    pctCetseguro: '',			    
			    pctCetMensal: '',
			    pctCetAnual:  '',
			};
		},
		
		recuperaDados: function(){
			return _this.dadosCET;
		},
	});

	return DetalheCosultaCETControle;
	
});


