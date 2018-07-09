define(['enumeracoes/eMensagemCCR',
        'enumeracoes/eTipoServicos',
        'util/retorno',
        'text!visao/arquivo/filtroArquivo.html',
        'text!visao/arquivo/consultaArquivoLista.html',
        'text!visao/arquivo/modalErrosContrato.html',
        'modelo/arquivo/arquivo',
        'modelo/contratacao/contrato',
        ], 
function(EMensagemCCR, ETipoServicos, Retorno, consultaArquivoTpl, consultaArquivoListaTpl, modalErrosContratoTpl, Arquivo, Contrato){

	var _this = null;
	var ConsultaArquivoControle = Backbone.View.extend({

		className: 'consultarArquivo',
		
		/**
		 * Templates
		 */
		consultaArquivoTemplate 	 : _.template(consultaArquivoTpl),
		consultaArquivoListaTemplate : _.template(consultaArquivoListaTpl),
		modalErrosContratoTemplate   : _.template(modalErrosContratoTpl),
		
		
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
			'click a#btnConsultarArquivo' 	 : 'consultarArquivo',
			'click a#btnVoltaModalContrato'	 : 'fecharModal',
			'click td.modalErros'			 : 'modalContratoErros',
			'click a#btnVoltar' 		   	 : 'voltar',
			'click a#btnSair'				 : 'sair',
			'click a#btnLimparForm'			 : 'limparCampos',
		},
				
		
		/**
		 * Função padrão de inicialização do template html
		 * 
		 */
		initialize : function() {
			console.log("ConsultaArquivo - initialize");
			
			_this = this;			
			
			_this.validator.addValidator(new DataValidator());			
			_this.validator.withErrorRender(new BootstrapErrorRender());
		},
		
		
		render : function(data) {
			console.log("arquivo - render");
			loadCCR.start();
			
			_this.$el.html(_this.consultaArquivoTemplate());
			loadMaskEl(_this.$el);
			
			_this.$el.find('#divListaArquivos').addClass('hidden');
			gCarregarSituacaoArquivo(_this.$el, "select_situacao", null, '4');
			
			loadCCR.stop();
			
			return _this;
		},
		
		getCollection: function () {
	    	if (this.collection == null || this.collection == undefined)
	    		this.collection = new Arquivo();
	    		
	    	return this.collection;
	    },
		
	    consultarArquivo : function(e) {
			console.log("autorizar controle - initialize");
			loadCCR.start();
			var opIdentificador = _this.$el.find('input[name="rdoIdentificador"]:checked').val();
			
			var listaAux = [];
			var consulta ={};
			
			if(_this.$el.find('#select_situacao').val() !="" && _this.$el.find('#select_situacao').val()!=null)
				consulta.situacaoRetorno = _this.$el.find('#select_situacao').val();
			if(_this.$el.find('#input_contrato').val() !="")
				consulta.nuContrato = _this.$el.find('#input_contrato').val();
			if(_this.$el.find('#dataInicio').val() !="")
				consulta.dtInicioRemessa = _this.$el.find('#dataInicio').val();
			if(_this.$el.find('#dataFim').val() !="")
				consulta.dtFimRemessa = _this.$el.find('#dataFim').val();
			if(_this.$el.find('#identificador').val() !="")
				consulta.identificador = _this.$el.find('#identificador').val();
			
			
			if((_this.$el.find('#select_situacao').val() == null || _this.$el.find('#select_situacao').val() == 0) && _this.$el.find('#input_contrato').val() == "" 
				&& _this.$el.find('#dataInicio').val() == "" && _this.$el.find('#dataFim').val() == "") {
				Retorno.trataRetorno({codigo: -1, tipo: "", mensagem: "", idMsg: 'MA0069'}, 'arquivo');
				loadCCR.stop();
				_this.$el.find('#divListaArquivos').hide();
				return
			}
		
			if(!_this.validator.withInput('dataInicio').validate() || !_this.validator.withInput('dataFim').validate()){
				loadCCR.stop();
				return
			}
			
			this.getCollection().consultar(consulta)
			.done(function sucesso(data) {
				if(data.length == 0){
					 _this.listaAux = [];
					 _this.$el.find('#divListaArquivos').html(_this.consultaArquivoListaTemplate({lista: data}));
					 Retorno.trataRetorno({codigo: -1, tipo: "", mensagem: "", idMsg: 'MA004'}, 'arquivo');
//					 _this.$el.find('#divListaArquivos').hide();
				}else{
					_this.listaAux = data;
					_this.$el.find('#divListaArquivos').html(_this.consultaArquivoListaTemplate({lista: data}));
					_this.$el.find('#divListaArquivos').show();
				}
				
	
				_this.$el.find('#gridListaArquivos').dataTable({
					'aoColumns' : [ null, null, null, null, null, null, null, null ],
					'aaSorting': [],
					'oLanguage' : {'sEmptyTable' : 'Nenhum registro encontrado.', 'sLengthMenu': '_MENU_ registros por página', 'sInfoFiltered' : '(Filtrado _MAX_ do total de entradas)'},
				});		
				_this.$el.find('#divListaArquivos').removeClass("hidden");
//				listenToDatepickerChange(_this.$el, _this.changed);
				loadCCR.stop();
			})
			.error(function erro(jqXHR) {
				Retorno.trataRetorno({codigo: -1, tipo: "ERRO_EXCECAO", mensagem: "Ocorreu um erro ao listar os arquivos!"}, 'arquivo', jqXHR);
				loadCCR.stop();
			});
			
		},
		
		modalContratoErros : function(evt) {
			if(_this.listaAux[Number(evt.currentTarget.attributes[1].nodeValue) - 1] == null) {
				listaContratoErros = _this.listaAux[Number(evt.currentTarget.attributes[0].nodeValue) - 1].arquivoContratoErro;
			} else {
				listaContratoErros = _this.listaAux[Number(evt.currentTarget.attributes[1].nodeValue) - 1].arquivoContratoErro;
			}
	    	
	    	console.log(listaContratoErros);
	    	_this.$el.find('#divContratoErros').html(_this.modalErrosContratoTemplate({contratoErros: listaContratoErros}));
	    	_this.$el.find("#divModalContratoErros").modal('show');
	    },
		
	    fecharModal : function() {
	    	_this.$el.find("#divModalContratoErros").modal('hide');
	    },
		
		limparCampos: function(){
			_this.$el.find('#dataInicio').val('');		
			_this.$el.find('#dataFim').val('');
			_this.$el.find('#select_situacao').val('');
			_this.$el.find('#input_contrato').val('');
			
			_this.$el.find('#divListaArquivos').addClass("hidden");
		},
		
		sair: function () {
			loadCCR.start();
			console.log("saindo do CCR...");
			window.location = 'index.html';
		},
		
		
		
		
	});

	return ConsultaArquivoControle;
	
});