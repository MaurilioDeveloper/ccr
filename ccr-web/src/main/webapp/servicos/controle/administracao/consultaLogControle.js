/**
 * @author F620600
 * 
 * JavaScript que controla as ações das paginas:
 *  consultaLog.html
 *  
 * @version 1.0.0.0
 * 
 * 
 */
define(['enumeracoes/eMensagemCCR',
        'enumeracoes/eTipoServicos',
        'util/retorno',
        'modelo/administracao/log',
        'modelo/administracao/logColecao',
        'text!visao/administracao/consultaLog.html',
        'text!visao/administracao/detalheLog.html'
        ], 
function(EMensagemCCR, ETipoServicos, Retorno, Log, LogCollection, consultaLogTpl, detalheLogTpl){

	var _this = null;
	var ConsultaLogControle = Backbone.View.extend({

		className: 'ConsultaLogControle',
		
		/**
		 * Templates
		 */
		consultaLogTemplate : _.template(consultaLogTpl),
		detalheLogTemplate  : _.template(detalheLogTpl),
		
		/**
		 * Variaveis da classe
		 */
		validator   : new Validators(),
		message     : new Message(),
		comando     : null,
		modelo      : null,
		collection  : null,
	
		/**
		 * Função que faz bind das ações e interações da pagina com as funções
		 * javascript
		 * 
		 */
		events : {
			'click a#btnConsultar'	: 'consultar',
			'click a#btnLimpar'		: 'limparForm',
			'click a#btnDetalhe'    : 'detalhe',
			'click a#btnFechar'     : 'fechar',
			
			'blur input' 			: 'changed',
		},
				
		
		/**
		 * Função padrão de incialização do template html
		 * 
		 */
		initialize : function() {
			console.log("Consulta LOG controle - initialize");
			
			var hoje = new Date();
			
			// pra nao ter problema de pegar outro 'this'
			_this = this;
			
			// configura validator
			_this.validator.withErrorRender(new BootstrapErrorRender());
			
			_this.validator.addValidator(new DataValidator());
			_this.validator.addValidator(new DataValidatorMaior(formatDate(hoje, 'dd/MM/yyyy')));
			_this.validator.withErrorRender(new BootstrapErrorRender());
			
		},
			
		/**
		 * Função padrão para reenderizar os dados html na tela
		 * Nessa função é carregado a mascara para CPF e colocadao o foco
		 * no campo de CPF
		 * Retorna o proprio objeto criado
		 * @returns {anonymous}
		 */
		render : function() {
			console.log("Consultar LOG - render");	
			
			//Inclui o template inicial no el deste controle
			_this.$el.html(_this.consultaLogTemplate());
			
			//Carrega as mascaras usadas.
			loadMaskEl(_this.$el);
			listenToDatepickerChange(_this.$el, _this.changed);
			
			gCarregarTransacaoLog(_this.$el);

			return _this;
		},
		
		getCollection: function () {
	    	if (_this.collection == null || this.collection == undefined)
	    		_this.collection = new LogCollection();
	    		
	    	return _this.collection;
	    },

	    getModelo: function () {
	    	if (_this.modelo == null || this.modelo == undefined)
	    		_this.modelo = new Log();
	    		
	    	return _this.modelo;
	    },
	    
	    changed : function (e) {
	    	console.log('changed', e);
	    	
		},
		
		consultar: function () {
			console.log("LOG - consultar");	
			
			if (_this.validator.withForm('formFiltro').validate()){
				loadCCR.start();
			
				var inicio, fim, transacao;
				inicio = _this.$el.find('#dataInicio').val();
				fim = _this.$el.find('#dataFim').val();
				transacao = _this.$el.find('#transacao').val();
					
				// lista taxas			
				_this.getCollection().listar(inicio, fim, transacao)
				.done(function sucesso(data) {
					console.info(data);
					
					// verifica Retorno
					Retorno.trataRetorno(data, 'consultarLog', null, false);			
					_this.$el.find('#divLista').html(_this.detalheLogTemplate({lista: data.listaRetorno}));
					
					// configura datatable
					_this.$el.find('#gridLista').dataTable({
						'aoColumns' : [ null, null, null, null, null ],
						'aaSorting': []
					});
					
					loadCCR.stop();
				})
				.error(function erro(jqXHR) {
					console.log(jqXHR);
					Retorno.trataRetorno({codigo: -1, tipo: "ERRO_EXCECAO", mensagem: "Ocorreu um erro ao listar os logs!"});
					loadCCR.stop();
				});
			}
		},
		
		detalhe: function (e) {
			var id = $(e.target).data('id') == undefined ? $(e.target).parent().data('id') : $(e.target).data('id');
			_this.modelo = _this.getCollection().get(id);
			
			if (_this.modelo.get('detalhe').indexOf('xml') > -1)
				_this.$el.find('#divInfoDetalhe').html('<pre>' + _this.formatXml(_this.modelo.get('detalhe')) + '</pre>');
			else			
				_this.$el.find('#divInfoDetalhe').html('<pre>' + _this.modelo.get('detalhe') + '</pre>');
				
			_this.$el.find('#divDetalhe').modal('show');
			_this.$el.find('#divDetalhe').css({
			  'width': function () { 
			    return ($(document).width() * .65) + 'px';  
			  },
			  'margin-left': function () { 
			    return -($(this).width() / 2); 
			  }
			});
			
		},
		
		fechar: function () {
			_this.$el.find('#divDetalhe').modal('hide');
		},
		
		limpar: function () {
			_this.validator.withForm('formFiltro').cleanErrors();
			_this.$el.find('#formFiltro')[0].reset();
		},
		
		formatXml: function(xml) {
		    var formatted = '';
		    var reg = /(>)(<)(\/*)/g;
		    xml = _.unescape(xml);
		    xml = xml.replace(reg, '$1\r\n$2$3');
		    var pad = 0;
		    jQuery.each(xml.split('\r\n'), function(index, node) {
		        var indent = 0;
		        if (node.match( /.+<\/\w[^>]*>$/ )) {
		            indent = 0;
		        } else if (node.match( /^<\/\w/ )) {
		            if (pad != 0) {
		                pad -= 1;
		            }
		        } else if (node.match( /^<\w[^>]*[^\/]>.*$/ )) {
		            indent = 1;
		        } else {
		            indent = 0;
		        }

		        var padding = '';
		        for (var i = 0; i < pad; i++) {
		            padding += '#ESPACO#';
		        }

		        formatted += padding + node + '#NEW_LINE#';
		        pad += indent;
		    });

		    formatted = _.escape(formatted);
		    formatted = formatted.replace(/#ESPACO#/g, '&nbsp;&nbsp;&nbsp;');
		    formatted = formatted.replace(/#NEW_LINE#/g, '<br />');
		    
		    return formatted;
		}
		
	});

	return ConsultaLogControle;
	
});