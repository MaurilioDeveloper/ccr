define(['enumeracoes/eMensagemCCR',
        'enumeracoes/eTipoServicos',
        'util/retorno',
        'text!visao/agendadorContrato/agendadorEnvioContrato.html',
        'modelo/agendadorContrato/agendadorContrato'
        ], 
function(EMensagemCCR, ETipoServicos, Retorno, agendadorContratoTpl, AgendadorContrato){

	var _this = null;
	var AgendadorContratoControle = Backbone.View.extend({

		className: 'agendadorEnvioContrato',
		
		/**
		 * Templates
		 */
		agendadorContratoTemplate 	 : _.template(agendadorContratoTpl),
		
		
		/**
		 * Variaveis da classe
		 */
		validator   : new Validators(),
		message     : new Message(),
		comando     : null,
		modelo		: null,
		collection  : null,
		acao		: null,
		contrato	: null,
		
		/**
		 * Função que faz bind das ações e interações da pagina com as funções
		 * javascript
		 * 
		 */
		events : {
			'click #btnSalvar' 		   	 : 'agendar',
			'click #btnSair'				 : 'sair',
			'click #btnLimparForm'			 : 'limparCampos',
		},
				
		
		/**
		 * Função padrão de inicialização do template html
		 * 
		 */
		initialize : function() {
			console.log("AgendadorContratoControle - initialize");
			
			_this = this;			

			_this.validator.addValidator(new DataValidator());			
			_this.validator.withErrorRender(new BootstrapErrorRender());
		},
		
		
		render : function(data) {
			console.log("AgendadorContratoControle - render");
			loadCCR.start();
			this.getCollection().buscar().done(function sucesso(data) {
				_this.dadosProcesso = data;
				_this.$el.html(_this.agendadorContratoTemplate({dadosProcesso: _this.dadosProcesso}));
				loadMaskEl(_this.$el);
				loadCCR.stop();
			}).error(function erro(jqXHR) {
				Retorno.trataRetorno({codigo: -1, tipo: "ERRO_EXCECAO", mensagem: "", idMsg: 'MA003'}, 'agendador', jqXHR);
				loadCCR.stop();
			});					
			
			return _this;
		},
		
		agendar : function() {
//			var dataProcesso = _this.$el.find('#dataProcesso').val();
			if (_this.validator.withForm('formAgendadorContrato').validate()){
				var horaInicio = _this.$el.find('#horaInicio').val();
				var horaFim = _this.$el.find('#horaFim').val();
							var intervalo = _this.$el.find('#intervaloSIAPX').val();
				
				horaInvalida = _this.verificaHora(horaInicio);
				if(horaInvalida){
					$(".horaSolicitar").addClass(" control-group error");
					console.log("Hora Início inválida");
					Retorno.trataRetorno({codigo: -1, tipo: "", mensagem: "Hora Início inválida!", idMsg: ''}, 'agendador');
					return;
				}
				horaInvalida = _this.verificaHora(horaFim);
				if(horaInvalida){
					$(".horaSolicitar").addClass(" control-group error");
					console.log("Hora Fim inválida");
					Retorno.trataRetorno({codigo: -1, tipo: "", mensagem: "Hora Fim inválida!", idMsg: ''}, 'agendador');
					return;
				}
				horaInvalida = _this.comparaHora(horaInicio, horaFim);
				if(horaInvalida) {
					$(".horaSolicitar").addClass(" control-group error");
					console.log("Hora Inicio maior que hora fim");
					return;
				}
				_this.validator.withForm('formFiltroAuditoria').cleanErrors();
				loadCCR.start();
				this.getCollection().atualizar(horaInicio, horaFim, intervalo).done(function(){ 
					loadCCR.stop();
					Retorno.trataRetorno({codigo: 0, tipo: "SUCESSO", mensagem: "", idMsg: 'MA002'}, 'agendador');
				}).error(function() {
					loadCCR.stop();
					Retorno.trataRetorno({codigo: -1, tipo: "ERRO_EXCECAO", mensagem: "", idMsg: 'MA003'}, 'agendador');
				});
			} else {
				$("#horaFim_BootstrapErrorRender").hide();
				$("#horaInicio_BootstrapErrorRender").hide();
				Retorno.trataRetorno({codigo: -1, tipo: "", mensagem: "Favor informar hora início e hora fim!", idMsg: ''}, 'agendador');
			}
		},
		
		getCollection: function () {
	    	if (this.collection == null || this.collection == undefined)
	    		this.collection = new AgendadorContrato();
	    		
	    	return this.collection;
	    },
		
		limparCampos: function(){
			_this.$el.find('#dataProcesso').val('');		
			_this.$el.find('#horaInicio').val('');
			_this.$el.find('#horaFim').val('');
		},
		
		sair: function () {
			loadCCR.start();
			console.log("saindo do CCR...");
			window.location = 'index.html';
		},
		
		verificaHora: function(campo){
		   hrs = (campo.substring(0,2));
		   min = (campo.substring(3,5));
		   horaInvalida = false;
		   if ((hrs < 00 ) || (hrs > 23) || ( min < 00) ||( min > 59)){
			   horaInvalida = true;
		   }
		   if (campo == "") {
			   horaInvalida = true;
		   }
		   return horaInvalida;
		},
		
		comparaHora: function(horaInicio, horaFim) {
			horaInvalida = false;
			horaCompletaInicio = horaInicio.replace(':', ''); 
			horaCompletaFim = horaFim.replace(':', '');
			
			if(horaCompletaInicio > horaCompletaFim) {
				horaInvalida = true;
				Retorno.trataRetorno({codigo: -1, tipo: "", mensagem: "Hora fim não pode ser maior que hora início"}, 'agendador');
			}
			
			return horaInvalida;
		}
		
		
	});

	return AgendadorContratoControle;
	
});