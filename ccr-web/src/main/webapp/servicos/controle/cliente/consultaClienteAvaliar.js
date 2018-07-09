/**
 * @author 
 * 
 * JavaScript que controla as ações das paginas:
 *  
 * @version 1.0.0.0
 * 
 * 
 */
define(['enumeracoes/eMensagemCCR',
        'enumeracoes/eTipoServicos',
        'util/retorno',       
        'text!visao/wizardConsignado.html',
        'text!visao/cliente/dadosClientePF.html',
        'text!visao/contratacao/contratar/simular/consultaDadosClientePF.html',
        'controle/contratacao/wizardConsignadoControle'
        ], 
function(EMensagemCCR, ETipoServicos, Retorno, wizardConsignadoTpl, dadosClientePFTpl, consultaDadosClientePFTpl, wizardConsignadoControle){

	var _this = null;
	var ConsultaClienteAvaliar = Backbone.View.extend({

		className: 'consultaClienteAvaliar',
		
		/**
		 * Templates
		 */
		wizardConsignadoTemplate : _.template(wizardConsignadoTpl),
		consultaDadosClientePFTemplate  : _.template(consultaDadosClientePFTpl),
		dadosClientePFTemplate  : _.template(dadosClientePFTpl),
		
		/**
		 * Variaveis da classe
		 */
		validator   : new Validators(),
		message     : new Message(),
		comando     : null,
		modelo		: null,
		collection  : null,
		acao		: null,
		
		/**
		 * Função que faz bind das ações e interações da pagina com as funções
		 * javascript
		 * 
		 */
		events : {
			'click a#btnConsultar' 		  : 'consultarCliente',
			'click a#btnLimparForm'		  : 'limparFormToda',
			'click a#btnSair'		      : 'sair',
			'click #btnAvancar' 		  : 'avancar',
			'click button#btnVoltar'	  : 'voltar',
			
		},
				
		
		/**
		 * Função padrão de incialização do template html
		 * 
		 */
		initialize : function() {
			console.log("consultaClienteAvaliar - initialize");
			
			_this = this;			
			
			// configura validator
			_this.validator.addValidator(new DataValidator());			
			_this.validator.withErrorRender(new BootstrapErrorRender());
		},
		
		
		render : function() {
			console.log("consultaClienteAvaliar - render");	
			
			//loadCCR.start();
			
			//Inclui o template inicial no el deste controle
			this.$el.html(_this.dadosClientePFTemplate({}));			
			//$('#step2').html(_this.dadosClientePFTemplate({}));
			loadMaskEl(_this.$el);
			listenToDatepickerChange(_this.$el, _this.changed);
			
			return _this;
		},
		
				
		/**
		 * Função utilizada para avancar na etapas.
		 * Acao do botao btnAvancar
		 * 
		 * Parametro passado automaticamente
		 * 
		 * @param evento
		 * 
		 */
		avancar : function(evento) {
			console.log("consultaClienteAvaliar - avancar");			
			//pega o numero da etapa pelo active e o numero pelo data
			var etapaAtual = $('ul.wizard-steps > li.active').data('step');			
			//avanca a etapa
			console.log("consultaClienteAvaliar - carrega etapa: " + etapaAtual);			
			var wizardConsignadoControl = new wizardConsignadoControle();			
			wizardConsignadoControl.carregarEtapa(etapaAtual+1);			
		},
		
		/**
		 * Função utilizada para voltar na etapas.
		 * Acao do botao btnVoltar
		 * 
		 * Parametro passado automaticamente
		 * 
		 * @param evento
		 * 
		 */
		voltar : function(evento) {
			console.log("consultaClienteAvaliar - voltar");
			//pega o numero da etapa pelo active e o numero pelo data
			var etapaAtual = $('ul.wizard-steps > li.active').data('step');
			//volta a etapa
			console.log("consultaClienteAvaliar - carrega etapa: " + etapaAtual);			
			var wizardConsignadoControl = new wizardConsignadoControle();			
			wizardConsignadoControl.carregarEtapa(etapaAtual-1);
			
		},
		
						
	    getCollection: function () {
	    	if (_this.collection == null || this.collection == undefined)
	    		_this.collection = new TaxaIOFCollection();
	    		
	    	return _this.collection;
	    },
	    
	    changed : function (e) {
	    	_this.modelo.set(e.target.name,e.target.value);
	    	_this.getCollection().set(_this.modelo, {remove: false});
		},
		
		sair: function () {
			loadCCR.start();
			console.log("saindo do CCR...");
			window.location = 'index.html';
		},
					
				
		limparForm: function() {
						
		},
		
		limparFormToda: function() {
						
		}		
		
	
	});

	return ConsultaClienteAvaliar;
	
});