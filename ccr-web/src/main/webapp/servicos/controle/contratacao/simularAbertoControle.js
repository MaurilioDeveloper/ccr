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
        'enumeracoes/eMensageria',
        'util/retorno',
        'modelo/contratacao/simulacao',
        'text!visao/contratacao/simularAberto.html'
        ], 
function(EMensagemCCR, ETipoServicos, EMensageria, Retorno, Simulacao, simulacaoAbertaTpl){

	var _this = null;
	var SimularAbertoControle = Backbone.View.extend({

		className: 'SimularAbertoControle',
		
		/**
		 * Templates
		 */
		simulacaoAbertaTemplate : _.template( _.unescape($(simulacaoAbertaTpl).filter('#divTemplateForm').html()) ),
		simulacaoResultTemplate : _.template( _.unescape($(simulacaoAbertaTpl).filter('#divTemplateResultado').html()) ),
		
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
			'click a#btnSimular'	     	 : 'simular',
			'click a#btnLimpar'		     	 : 'limparForm',
			'click a#btnSimularNovo'     	 : 'simularNovo',			
			'click #divDetalharResultComSeg' : 'mostrarDetalhe',
			'click #divDetalharResultSemSeg' : 'mostrarDetalhe',
			
			'blur #formSimulacaoAberta input': 'changed',			
			'blur #valorLiquido'		 	 : 'verificaCampoInformado',
			'blur #valorPrestacao'	 	 	 : 'verificaCampoInformado'			
		},
				
		
		/**
		 * Função padrão de incialização do template html
		 * 
		 */
		initialize : function() {
			console.log("Simulacao Aberta controle - initialize");
			
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
			console.log("Simulacao Aberta - render");	
			
			//Inclui o template inicial no el deste controle
			_this.$el.html(_this.simulacaoAbertaTemplate());
			
			//Carrega as mascaras usadas.
			//loadMask();
			loadMaskEl(_this.$el);
			
			// desabilita os botoes
			_this.$el.find('a.disabled').on('click', function(evt) {
				evt.preventDefault();
				return false;
			});
			
			_this.$el.find('.data').on('changeDate', function (e) {
				_this.changed( { target: { name: $('input', e.target).attr('name'), value: $('input', e.target).val() } });
			});
			
			return _this;
		},
		
		changed : function (e) {
	    	_this.getModelo().set(e.target.name,e.target.value);
		},
		
		getModelo : function(){
	    	if(_this.modelo == null || _this.modelo == undefined){
	    		_this.modelo = new Simular();
	    		_this.listenTo(_this.modelo, EMensageria.SUCESSO, _this.renderSimulacao);
	    		_this.listenTo(_this.modelo, 'error', _this.tratarErro);
	    		_this.listenTo(_this.modelo, EMensageria.NAO_RECEBIDO, _this.semRetorno);
	    	}
	    	
	    	return _this.modelo;
	    },
	    changed : function (e) {
	    	_this.getModelo().set(e.target.name,e.target.value);
		},
		
		getModelo : function(){
	    	if(_this.modelo == null || _this.modelo == undefined){
	    		_this.modelo = new Simulacao();
	    		_this.listenTo(_this.modelo, EMensageria.SUCESSO, _this.renderSimulacao);
	    		_this.listenTo(_this.modelo, 'error', _this.tratarErro);
	    		_this.listenTo(_this.modelo, EMensageria.NAO_RECEBIDO, _this.semRetorno);
	    	}
	    	
	    	return _this.modelo;
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
				
				loadCCR.start();
				_this.getModelo().simular();								
			}
		},
		
		renderSimulacao: function (dados) {
			console.log('renderSimulacao', dados);
			
			// mostra o resultado da simulacao
			_this.$el.find('#divResultadoSimulacaoAberta').html( _this.simulacaoResultTemplate({ resultado: dados, modelo: _this.getModelo() }));			

			_this.$el.find('#divFormularioSimulacao').hide();
			_this.$el.find('#divResultadoSimulacaoAberta').show();
			
			loadCCR.stop();
		},
		
		semRetorno: function (modelo) {
			console.log('semRetorno', modelo);
			loadCCR.stop();
		},
		
		tratarErro: function (modelo, xhr, request) {
			console.log('tratarErro', modelo, xhr);
			
			if (xhr.responseText.indexOf("[REGRA_NEGOCIO]") > -1) {
				var obj = Retorno.getObjetoErroArqRef(JSON.parse(xhr.responseText));
				var msg = obj.mensagemErro.slice(obj.mensagemErro.indexOf("[REGRA_NEGOCIO]") + 15);
				Retorno.trataRetorno({tipo: "AVISO", mensagem: msg}, 'simular');
			} else {
				Retorno.trataRetorno(JSON.parse(xhr.responseText), 'simular');
			}
			
			loadCCR.stop();
		},

		mostrarDetalhe: function (evt) {
			_this.$el.find('#divDetalheResultComSeg').toggle();				
			_this.$el.find('#divDetalheResultSemSeg').toggle();		
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
			_this.modelo = null;
		}
	});

	return SimularAbertoControle;
	
});


