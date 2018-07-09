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
        'text!visao/contratacao/historicoEstorno.html'
        ], 
function(EMensagemCCR, ETipoServicos, historicoEstornoTpl){

	var _this = null;
	var HistoricoEstornoControle = Backbone.View.extend({

		className: 'historicoEstornoControle',
		
		/**
		 * Templates
		 */
		historicoEstornoTemplate : _.template(historicoEstornoTpl),
		
		/**
		 * Variaveis da classe
		 */
		validator   : new Validators(),
		message     : new Message(),
		comando     : null,
	
		/**
		 * Função que faz bind das ações e interações da pagina com as funções
		 * javascript
		 * 
		 */
		events : {
			'click a#btnSimular'	     : 'simular',
			'click a#btnLimpar'		     : 'limparForm',
			'click a#btnSimularNovo'     : 'simularNovo',
			
			'blur #valorLiquido'		 : 'verificaCampoInformado',
			'blur #valorPrestacao'	 	 : 'verificaCampoInformado'
		},
				
		
		/**
		 * Função padrão de incialização do template html
		 * 
		 */
		initialize : function() {
			console.log("Manter IOF controle - initialize");
			
			// pra nao ter problema de pegar outro 'this'
			_this = this;
			
			// configura validator
			_this.validator.withErrorRender(new BootstrapErrorRender());
			_this.validator.addValidator(new MaiorQueZeroValidator());
		},
			
		/**
		 * Função padrão para reenderizar os dados html na tela
		 * Nessa função é carregado a mascara para CPF e colocadao o foco
		 * no campo de CPF
		 * Retorna o proprio objeto criado
		 * @returns {anonymous}
		 */
		render : function() {
			console.log("Manter IOF - render");	
			
			//Inclui o template inicial no el deste controle
			_this.$el.html(_this.historicoEstornoTemplate());
			
			//Carrega as mascaras usadas.
			mascarasEl(_this.$el);
			
			// desabilita os botoes
			_this.$el.find('a.disabled').on('click', function(evt) {
				evt.preventDefault();
				return false;
			});
				
			// configura datatable
			_this.$el.find('#gridListaContratos').dataTable({
				'aoColumns' : [ null, null, null ],
				'aaSorting': []
			});
			
			return _this;
		},
		
		verificaCampoInformado: function(evt) {
			var $vrContratoLiq = _this.$el.find('#valorLiquido');
			var $vrPrestacao = _this.$el.find('#valorPrestacao');
			
			var vrContratoLiq = $vrContratoLiq.val().trim() == '' ? 0 : parseFloat($vrContratoLiq.val().replace('.', '').replace(',', '.'));
			var vrPrestacao = $vrPrestacao.val().trim() == '' ? 0 : parseFloat($vrPrestacao.val().replace('.', '').replace(',', '.')); 
			
			if (vrContratoLiq > 0) {
				$vrContratoLiq.prop('disabled', false).attr('validators', 'required,maiorQueZero').closest('.control-group').find('.obrigatorio').html('*');
				$vrPrestacao.prop('disabled', true).attr('validators', '').closest('.control-group').find('.obrigatorio').html('');
				
				return;
			}
			
			if (vrPrestacao > 0) {
				$vrContratoLiq.prop('disabled', true).attr('validators', '').closest('.control-group').find('.obrigatorio').html('');
				$vrPrestacao.prop('disabled', false).attr('validators', 'required,maiorQueZero').closest('.control-group').find('.obrigatorio').html('*');
				
				return;
			}
			
			$vrContratoLiq.prop('disabled', false).attr('validators', 'required,maiorQueZero').closest('.control-group').find('.obrigatorio').html('*');
			$vrPrestacao.prop('disabled', false).attr('validators', 'required,maiorQueZero').closest('.control-group').find('.obrigatorio').html('*');
		},
		
		simular: function () {
			if (_this.validator.withForm('formSimulacaoAberta').validate()){
				_this.limparForm();
				_this.$el.find('#divFormularioSimulacao').hide();
				_this.$el.find('#divResultadoSimulacaoAberta').show();				
			}
		},
		
		limparForm: function() {
			_this.validator.withForm('formSimulacaoAberta').cleanErrors();			
			_this.$el.find('#formSimulacaoAberta')[0].reset();
			
			_this.$el.find('#valorLiquido').prop('disabled', false).attr('validators', 'required,maiorQueZero')
										   .closest('.control-group').find('.obrigatorio').html('*');
			
			_this.$el.find('#valorPrestacao').prop('disabled', false).attr('validators', 'required,maiorQueZero')
											 .closest('.control-group').find('.obrigatorio').html('*');
		},
		
		simularNovo: function () {
			_this.limparForm();
			_this.$el.find('#divResultadoSimulacaoAberta').hide();
			_this.$el.find('#divFormularioSimulacao').show();			
		}
	});

	return HistoricoEstornoControle;
	
});


