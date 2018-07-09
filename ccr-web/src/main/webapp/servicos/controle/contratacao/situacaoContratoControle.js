/**
 * @author F620600
 * 
 * JavaScript que controla as ações das paginas:
 *  alterarIOF.html
 *  
 * @version 1.0.0.0
 * 
 * 
 */
define(['enumeracoes/eMensagemCCR',
        'enumeracoes/eTipoServicos',
        'util/retorno',
        'text!visao/contratacao/situacaoContratoMain.html',
        'text!visao/contratacao/consultaContrato.html',
        'text!visao/contratacao/detalheSituacaoContrato.html'
        ], 
function(EMensagemCCR, ETipoServicos, Retorno, situacaoContratoMainTpl, consultaContratoTpl, detalheSituacaoContratoTpl){

	var _this = null;
	var situacaoContratoControle = Backbone.View.extend({

		className: 'situacaoContratoControle',
		
		/**
		 * Templates
		 */
		mainTemplate  : _.template(situacaoContratoMainTpl),
		filtroContratoTemplate : _.template( _.unescape($(consultaContratoTpl).filter('#divFiltroContratos_02').html()) ),
		listaContratoTemplate  : _.template( _.unescape($(consultaContratoTpl).filter('#divListaContratos_02').html()) ),
		detalheContratoTemplate  : _.template(detalheSituacaoContratoTpl),
		
		/**
		 * Variaveis da classe
		 */
		validator   : new Validators(),
		message     : new Message(),
		comando     : null,
		modelo		: null,
		collection  : null,
		
		/**
		 * Função que faz bind das ações e interações da pagina com as funções
		 * javascript
		 * 
		 */
		events : {
			'click a#btnConsultar'    : 'consultarContrato',		
			'click a#btnDetalhar'	  : 'detalharContrato',
			'click a#btnVoltar'		  : 'atualizar',
		},
				
		
		/**
		 * Função padrão de incialização do template html
		 * 
		 */
		initialize : function() {
			console.log("Manter IOF controle - initialize");
			var amanha = new Date();
			amanha.setDate((new Date()).getDate() + 1);
			
			// pra nao ter problema de pegar outro 'this'
			_this = this;
			
			// configura validator
			_this.validator.addValidator(new DataValidator());
			_this.validator.addValidator(new DataValidatorMenor(formatDate(amanha, 'dd/MM/yyyy')));
			_this.validator.withErrorRender(new BootstrapErrorRender());
			
			_this.$el.html(_this.mainTemplate());
		},
		
		render : function() {
			console.log("Manter IOF - render");	
			loadCCR.start();
			/*
			// lista taxas			
			_this.getCollection().listar()
			.done(function sucesso(data) {
				
				//Inclui o template inicial no el deste controle
				_this.$el.html(_this.manterIOFTemplate({TaxaIOF: data}));
								
				// configura datatable
				_this.$el.find('#gridListaTaxaIOF').dataTable({
					'aoColumns' : [ null, null, null, null, null ],
					'aaSorting': []
				});
				
				//Carrega as mascaras usadas.
				mascarasEl(_this.$el);
				
				// desabilita os botoes
				_this.$el.find('a.disabled').on('click', function(evt) {
					evt.preventDefault();
					return false;
				});
				
				_this.$el.find('#divInputDataVigencia').datetimepicker({
					language : 'pt-BR',
					pickTime : false
				});
			
				loadCCR.stop();
			})
			.error(function erro(jqXHR) {
				console.log(jqXHR);
				Retorno.trataRetorno({codigo: -1, tipo: "ERRO_EXCECAO", mensagem: "Ocorreu um erro ao listar as taxas IOF cadastradas!"});
				loadCCR.stop();
			});
			*/			
			
			_this.$el.find('#divFiltro').html(_this.filtroContratoTemplate());
			loadCCR.stop();
			
			return _this;
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
		
		consultarContrato: function () {
			_this.$el.find('#divFiltro').hide();
			
			_this.$el.find('#divTabs').show();
			_this.$el.find('#divTabsContent').show();
			_this.$el.find('#divTabsContent #lista').html(_this.listaContratoTemplate());
			
			_this.$el.find('#gridListaContratos').dataTable({
				'aoColumns' : [ null, null, null, null, null, null, null, null ],
				'aaSorting': []
			});
			
		},
		
		detalharContrato: function () {
			
			var $ul = _this.$el.find('#divTabs ul');
			
			_this.$el.find('#divTabsContent #00001').html(_this.detalheContratoTemplate());
			$ul.find('li a[href="#00001"]').closest('li').show();
			$ul.find('li a[href="#00001"]').trigger('click');			
		},
		
		limparForm: function() {
			_this.validator.withForm('formFiltroContrato').cleanErrors();			
			_this.$el.find('#formFiltroContrato')[0].reset();
		},
		
		cancelar: function () {						
			_this.limparForm();
			_this.comando = null;
			
			_this.$el.find('#divFormulario').modal('hide');
		},
				
		atualizar: function(){
			$('#menuCCR #consultarSituacaoContrato').trigger('click');
		}
	
	});

	return situacaoContratoControle;
	
});


