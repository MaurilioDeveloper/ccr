/**
 * @author 
 * 
 * JavaScript que controla as ações das paginas:
 *  manterDocumentoMain.html
 *  
 * @version 1.0.0.0
 * 
 * 
 */
define(['enumeracoes/eMensagemCCR',
        'enumeracoes/eTipoServicos',
        'util/retorno',
        'modelo/documento/documento',
        'text!visao/documento/manterDocumentoMain.html', 
        'text!visao/documento/manterDocumentoListar.html',
        'text!visao/documento/incluirAlterarDocumento.html',
        'text!visao/documento/detalheDocumento.html',
        'text!visao/documento/templateDocumento.html',
        'modelo/contratacao/contrato',
        'modelo/cliente/cliente',
        'layout/ckeditor/ckeditor'
        ], 
function(EMensagemCCR, ETipoServicos, Retorno, manterDocumentoModel, manterDocumentoTpl, manterDocumentoListaTpl, inclusaoAltDocumentoTpl, detalheDocumentoTpl, templateDoc, Contrato, Cliente, editorHtml){

	var _this = null;
	var manterDocumentoControle = Backbone.View.extend({

		className: 'manterDocumentoControle',	
		
		/**
		 * Templates
		 */
		mainTemplate  					: _.template(manterDocumentoTpl),
		manterDocumentoListaTemplate  	: _.template(manterDocumentoListaTpl),
		inclusaoAltDocumentoTemplate 	: _.template(inclusaoAltDocumentoTpl),
		detalheDocumentoTemplate 		: _.template(detalheDocumentoTpl),
		templateParaTeste               : _.template(templateDoc),
		
		/**
		 * Variaveis da classe
		 */
		validator   : new Validators(),
		message     : new Message(),
		comando     : null,
		modelo		: null,
		collection  : null,		
		documento   : null,
		tipoDocSelecionado : null,
		
		contrato : new Contrato,
		cliente : new Cliente,
		
		/**
		 * Função que faz bind das ações e interações da pagina com as funções
		 * javascript
		 * 
		 */
		events : {
			'click a#btnConsultarDocumento' 					: 'consultarDocumento'		,
			'click a#btnDetalhesDocumento'  					: 'detalheDocumentoView'	,
			'click a#btnAlterarDocumento'  						: 'alterarDocumentoView'	,
			'click a#btnRemoverDocumento'  						: 'removerDocumento'		,
			'click a#btnNovoDocumento'      					: 'novoDocumentoView'		,
			'focus #inputNomeModeloDocumento'					: 'getAutocomplete'			,
		    'keydown #inputNomeModeloDocumento'					: 'invokefetch'				,
		    'click a#btnVisualizaDocumento' 					: 'visualizaDocumento'		,
		    'click a#btnSalvaDocumento' 						: 'salvaDocumento'			,
		    'click a#btnVoltarDocumento' 						: 'voltar'					,
		    'change #selectCamposDinamDocumento'   				: 'incluirCampoDinamico'	,
		    'click a#btnVoltarVisualizaContato'					: 'voltarModalVisContrato'	,
		    'click a#btnImprimirVisualizaContato'				: 'impressaoContratoView'	,
		    'click a#btnLimparForm'								: 'limparFiltro'			,
		    'click a#btnSair' 									: 'sair'					,
		    'focus #inputVisualizaNumeroContrato'               : 'valorNumerico'			,
		},
				
		
		/**
		 * Função padrão de incialização do template html
		 * 
		 */
		initialize : function() {
			console.log("Manter Documento controle - initialize");
			var amanha = new Date();
			amanha.setDate((new Date()).getDate() + 1);
			
			// pra nao ter problema de pegar outro 'this'
			_this = this;
			
			// configura validator
			_this.validator.addValidator(new DataValidator());
			_this.validator.addValidator(new DataValidatorTaxaSeguroIOF(formatDate(amanha, 'dd/MM/yyyy')));
			_this.validator.withErrorRender(new BootstrapErrorRender());
			_this.collectionFetched = false;
			_this.$el.html(_this.mainTemplate());
			
			_this.carregarComboTipoDocumento();
			
			

		},
		
		render : function() {
			console.log("Gerenciar Documento - render");	
		
			loadMaskEl(_this.$el);
			
			// Create the widget.  Route searches to a custom handler
			 _this.$('#inputNomeModeloDocumento').autocomplete({ source: $.proxy( _this.findConvenios, _this), minLength: 3 });
			
			return _this;
		},
		
		valorNumerico : function() {
			$('.numero').numeric(); // numeric
		},
		
		findConvenios: function ( request, response ) {
			   console.log("Simular Main - findConvenios");    

			   $.when( _this.getCollection().getAutoCompleteNomeModelo(request.term) )
			                 .then(function ( data ) { response( _.map(data.listaRetorno, function ( d ) { return { value: d.nomeModeloDocumento, label: d.nomeModeloDocumento }; }) ); });
			         
		},
		
		consultarDocumento : function(){
			
			console.log("Gerenciar Documento - consultarDocumento");	
			
			loadCCR.start();
			
			var documento = {};
			documento.tipoDocumento = _this.$el.find('#selectTipoDocumento').val();
			documento.nomeModeloDocumento =_this.$el.find('#inputNomeModeloDocumento').val();
			documento.inicioVigencia =_this.$el.find('#inicioVigenciaFiltro').val();
			documento.fimVigencia = _this.$el.find('#finalVigenciaFiltro').val();
			
			
			if(_this.validaCamposFiltroPesquisa(documento.tipoDocumento,documento.nomeModeloDocumento,
					documento.inicioVigencia, documento.fimVigencia)){
				// lista contratos			
				_this.getCollection().buscar(documento )
				.done(function sucesso(data) {
					Retorno.trataRetorno(data, 'documento', null, false);
					//Inclui o template inicial no el deste controle
					_this.$el.find('#divListaDocumento').removeClass('hide');
					_this.$el.find('#divListaDocumento').html(_this.manterDocumentoListaTemplate({documento: data.listaRetorno}));
					
									
					// configura datatable
					_this.$el.find('#gridDocumentoListar').dataTable({
						'aoColumns' : [ null, null, null, null, null, null, null,  { "bSortable": false } ],
						'aaSorting': [],
						'oLanguage' : {'sEmptyTable' : 'Nenhum registro encontrado.', 'sLengthMenu': '_MENU_ registros por página', 'sInfoFiltered' : '(Filtrado _MAX_ do total de entradas)'},
					});
					
					//Carrega as mascaras usadas.
					
					// desabilita os botoes
					_this.$el.find('a.disabled').on('click', function(evt) {
						evt.preventDefault();
						return false;
					});

					listenToDatepickerChange(_this.$el, _this.changed);
								
					loadCCR.stop();				
				})
				.error(function erro(jqXHR) {
					Retorno.trataRetorno({codigo: -1, tipo: "ERRO_EXCECAO", mensagem: "Ocorreu um erro ao listar as os modelos cadastrados!"}, '', jqXHR);
					loadCCR.stop();
				});
				return _this;
			}
		},
		
		carregarComboCamposDinamicos: function (){
			var el = _this.$el;
			gCarregarCampoDinamico(el, "selectCamposDinamDocumento", "");
		},
		
		
		carregarComboTipoDocumentoIncluAlt : function(){
			var el = _this.$el;
			console.log(el);
			gCarregarCampoTipoDocumento(el, "selectTipoDocumentoIncAlt", _this.tipoDocSelecionado);
			
		},
		
		
		carregarComboTipoDocumento : function(){
			var el = _this.$el;
			console.log(el);
			gCarregarCampoTipoDocumento(el, "selectTipoDocumento", "");
			
		},
		
		getCollection: function () {
		   	if (_this.collection == null || this.collection == undefined)
		   		_this.collection = new manterDocumentoModel();
		    		
		   	return _this.collection;
		},
	
		alterarDocumentoView: function (evt) {
			var index = _this.$el.find(evt.currentTarget).data('index');	
			_this.documento = _this.collection.attributes.listaRetorno[index];
			_this.tipoDocSelecionado = _this.documento.tipoDocumento;
			_this.$el.find('#divIncAltDocumento').html(_this.inclusaoAltDocumentoTemplate({documento : _this.documento}));
			_this.controleViewEditar();
			
			
			
			CKEDITOR.instances.editorHtml.setData( _this.documento.templateHtml)
			
		},
		
		getItemSelecionado : function(){
			var itens = _this.$el.find('#selectTipoDocumento');
			
			for (i = 0; i < itens.children().length; i++) { 
				var valor = itens[0].options[i].text;
					var item = valor.substring(valor.indexOf('-')+1);
				    if(item.trim() == _this.tipoDocSelecionado.trim() ){
				    	_this.tipoDocSelecionado = itens[0].options[i].value;
				    }
			    
			}
			
		},
		
		removerDocumento: function (evt) {
			msgCCR.confirma(EMensagemCCR.manterIOF.MA0046, function() {
				var index = _this.$el.find(evt.currentTarget).data('index');	
				_this.documento = _this.collection.attributes.listaRetorno[index];
				_this.getCollection().removeModeloDocumento(_this.documento).done(function sucesso(data) {
					_this.consultarDocumento();		
					Retorno.trataRetorno({codigo: 0, tipo: "SUCESSO", mensagem: "", idMsg: 'MA002'}, 'documento');
				
				})
				.error(function erro(jqXHR) {
					Retorno.trataRetorno({codigo: -1, tipo: "ERRO_EXCECAO", mensagem: "Ocorreu um erro excluir o modelo!"}, '', jqXHR);
				});
			});
		},
		
		detalheDocumentoView : function(evt){
			var index = _this.$el.find(evt.currentTarget).data('index');	
			_this.documento = _this.collection.attributes.listaRetorno[index];
			_this.$el.find('#divDetailDocumento').html(_this.detalheDocumentoTemplate({documento : _this.documento}));
			_this.controleViewDetalhe();
			
			CKEDITOR.instances.editorHtml.setData( _this.documento.templateHtml)
		},
		
		novoDocumentoView : function(){
			_this.documento = {};
			_this.tipoDocSelecionado = null;
			_this.$el.find('#divIncAltDocumento').html(_this.inclusaoAltDocumentoTemplate({documento :_this.documento}));
			_this.controleViewIncluir();		
			_this.carregarComboCamposDinamicos();
		},
		
		visualizaDocumento : function(){
			if(CKEDITOR.instances.editorHtml.getData() == ""){
				Retorno.trataRetorno({codigo: 0, tipo: "AVISO", mensagem: "", idMsg: 'MA003'}, 'documento');
				return ;
			}else{
				_this.$el.find('#divModalVisualizarDoc').removeClass('hide');
				_this.$el.find('#divModalVisualizarDoc').modal('show');
			}
		
		},
		
		salvaDocumento : function(){
			
			_this.validator.withForm('divIncAltDocumentoForm').cleanErrors();

			if (_this.validator.withForm('formIncAltDocumento').validate()){
				if(CKEDITOR.instances.editorHtml.getData() == ""){
					Retorno.trataRetorno({codigo: 0, tipo: "AVISO", mensagem: "", idMsg: 'MA003'}, 'documento');
					return ;
				}
				
				msgCCR.confirma(EMensagemCCR.manterIOF.MA0046, function() {
					
				var inicioVigenciaTemp = null;
				if( _this.documento.inicioVigencia != null){
					inicioVigenciaTemp = _this.documento.inicioVigencia;
				}else{
					inicioVigenciaTemp = _this.$el.find('#inputInicioVigenciaIncAlt').val();
				}
				
				
				loadCCR.start();
				
				var icModeloCCB = _this.$el.find('#checkCCBIncAlt').is(":checked") ? 'A': 'I';
				
				var  documento ={
						inicioVigencia: inicioVigenciaTemp,
						tipoDocumento: _this.$el.find('#selectTipoDocumentoIncAlt').val(),
						nomeModeloDocumento: _this.$el.find('#inputNomeModeloDocumentoIncAlt').val(),
						templateHtml: CKEDITOR.instances.editorHtml.getData(),
						camposDinamicos: _this.$el.find('#selectCamposDinamDocumento').val(),
						id : _this.documento.id,
						icModeloTestemunha : icModeloCCB
					}
				
				
					
					_this.getCollection().salvarNovoDocumento(documento).done(function sucesso(data) {
						if (data.tipoRetorno == "ERRO_EXCECAO") {
							Retorno.trataRetorno({codigo: -1, tipo: "ERRO_EXCECAO", mensagem: "Ocorreu um erro ao salvar o Modelo!"}, 'documento');
							return;
						}else if(data.tipoRetorno == "ERRO_NEGOCIAL"){
							Retorno.trataRetorno(data, 'documento');		
						}
						else{
							_this.voltar();
							_this.consultarDocumentoRetorno(documento);
							Retorno.trataRetorno(data, 'documento');						
						}
						
						loadCCR.stop();
					})
					
					.fail(function (jqXHR) {
							loadCCR.stop();
							Retorno.trataRetorno({codigo: -1, tipo: "ERRO_EXCECAO", mensagem: "Ocorreu um erro ao salvar o Modelo Documento!"}, 'documento', jqXHR);
						})
					.error(function erro(jqXHR) {
						msgCCR.alerta("Funcionalidade Indísponivel!!!", function () {});
						loadCCR.stop();
					});
				});
			}else{
				loadCCR.stop();
			}
			
		},
		
		
		consultarDocumentoRetorno : function(documento){
			console.log("Gerenciar Documento - consultarDocumentoRetorno");	
			
			loadCCR.start();
				
			_this.getCollection().consultaRetorno(documento)
			.done(function sucesso(data) {
				Retorno.trataRetorno(data, 'documento', null, false);
				//Inclui o template inicial no el deste controle
				_this.$el.find('#divListaDocumento').removeClass('hide');
				_this.$el.find('#divListaDocumento').html(_this.manterDocumentoListaTemplate({documento: data.listaRetorno}));
				
								
				// configura datatable
				_this.$el.find('#gridDocumentoListar').dataTable({
					'aoColumns' : [ null, null, null, null, null, null, null, { "bSortable": false } ],
					'aaSorting': [],
					'oLanguage' : {'sEmptyTable' : 'Nenhum registro encontrado.', 'sLengthMenu': '_MENU_ registros por página', 'sInfoFiltered' : '(Filtrado _MAX_ do total de entradas)'},
				});
				
				//Carrega as mascaras usadas.
				
				// desabilita os botoes
				_this.$el.find('a.disabled').on('click', function(evt) {
					evt.preventDefault();
					return false;
				});

				listenToDatepickerChange(_this.$el, _this.changed);
							
				loadCCR.stop();				
			})
			.error(function erro(jqXHR) {
				Retorno.trataRetorno({codigo: -1, tipo: "ERRO_EXCECAO", mensagem: "Ocorreu um erro ao listar as os modelos cadastrados!"}, '', jqXHR);
				loadCCR.stop();
			});
			return _this;
		
		},
		
		voltar : function(){
			_this.controleViewManter();
			
		},
		
		validaCamposFiltroPesquisa : function (tipoDoc,nomeModelo, inicioVirgencia, finalVirgecia){
			if(tipoDoc == "" && nomeModelo =="" && inicioVirgencia== "" && finalVirgecia==""){
				loadCCR.stop();
				Retorno.trataRetorno({codigo: 0, tipo: "AVISO", mensagem: "", idMsg: 'MA001'}, 'documento');
				return false;
			}		
			return true;
		},
		
		controleViewIncluir : function(){
			_this.$el.find('#tituloPagDoc').text("Incluir Modelo Documento");
			_this.$el.find('#liTituloDoc').text("Incluir Modelo Documento");
			_this.$el.find('#divInputInicioVigenciaIncAlt').removeClass('hide');
			_this.$el.find('#divInputInputTipoDocumentoIncAlt').removeClass('hide');
			_this.$el.find('#divDetailDocumento').html("");
			_this.$el.find('#btnSalvaDocumento').removeClass('hide');
			loadMaskEl(_this.$el.find('#divIncAltDocumento'));
			_this.carregarComboTipoDocumentoIncluAlt();
			
			_this.controleViewConteudoIncEditDetail();
		},
		
		controleViewManter : function(){
			_this.$el.find('#tituloPagDoc').text("Cadastro Modelo Documento");
			_this.$el.find('#liTituloDoc').text("Manter Documento");
			_this.$el.find('#divFiltroDocumento').removeClass('hide');
			_this.$el.find('#divIncAltDocumentoForm').addClass('hide');
			
			//limpa o editor ao retonar para a pagina principal
			CKEDITOR.instances.editorHtml.setData("");
		},
		
		controleViewEditar : function(){
			_this.$el.find('#tituloPagDoc').text("Alterar Modelo Documento");
			_this.$el.find('#liTituloDoc').text("Alterar Modelo Documento");
			
			_this.$el.find('#divInputInicioVigenciaIncAlt').addClass('hide');
			_this.$el.find('#divInputInputTipoDocumentoIncAlt').addClass('hide');
			
			_this.$el.find('#divDetailDocumento').html("");
			_this.$el.find('#btnSalvaDocumento').removeClass('hide');
			_this.carregarComboCamposDinamicos();
			_this.controleViewConteudoIncEditDetail();
			
			_this.getItemSelecionado();
			_this.carregarComboTipoDocumentoIncluAlt();
			_this.$el.find('#selectTipoDocumentoIncAlt').prop('disabled', true);
			_this.$el.find('#checkCCBIncAlt').prop('disabled', true);
		},	
		
		controleViewConteudoIncEditDetail : function(){
			_this.$el.find('#divFiltroDocumento').addClass('hide');
			_this.$el.find('#divIncAltDocumentoForm').removeClass('hide');
			
		},
		
		controleViewDetalhe : function(){
			_this.$el.find('#tituloPagDoc').text("Detalhar Modelo Documento");
			_this.$el.find('#liTituloDoc').text("Detalhar Modelo Documento");
			_this.$el.find('#divIncAltDocumento').html("");
			_this.$el.find('#btnSalvaDocumento').addClass('hide');
			_this.controleViewConteudoIncEditDetail();
			_this.$el.find('#checkCCBIncAlt').prop('disabled', true);
		
		},	
		
		incluirCampoDinamico : function(e){
			var value = e.target.getValue();
			if(value != ""){
				var element = CKEDITOR.dom.element.createFromHtml( "<span>##"+value+"##</span>" );
				CKEDITOR.instances.editorHtml.insertElement(element);
			}
		},
		
		voltarModalVisContrato: function () {						
			_this.$el.find('#divModalVisualizarDoc').modal('hide');
		},
		
		impressaoContratoView : function(){		
			if (_this.validator.withForm('formVisualizaContrato').validate()){
				_this.consultarCpfPorContrato(_this.$el.find('#inputVisualizaNumeroContrato').val());
			}
		},
		
		gerarImpressaoTeste: function(dadosSicliParam){
			var documento = {
					nrContrato : _this.$el.find('#inputVisualizaNumeroContrato').val(),
					templateHtml : CKEDITOR.instances.editorHtml.getData(),
					dadosSicli : dadosSicliParam 
			}
			
			_this.getCollection().visualizacaoImpressao(documento )
			.done(function sucesso(data) {
				Retorno.trataRetorno(data, 'documento', null, false);
				
				var divToPrint ="<html><head><style>body {-webkit-print-color-adjust: exact; color-adjust: exact; background-image: url(\"/ccr-web/layout/img/marcaDagua.png\");}"
					+"</style></head><body>"+data.templateHtml+"</body></html>";
									
				newWin = window.open("");
				newWin.document.write(divToPrint);
				
				newWin.print();
				newWin.close();
				
			})
			.error(function erro(jqXHR) {
				Retorno.trataRetorno({codigo: -1, tipo: "ERRO_EXCECAO", mensagem: "Ocorreu ao imprimir!"}, '', jqXHR);
				loadCCR.stop();
			});
		},
		
		limparFiltro: function () {
			_this.$el.find('#selectTipoDocumento').val("");
			_this.$el.find('#inputNomeModeloDocumento').val("");
			_this.$el.find('#inicioVigenciaFiltro').val("");
			_this.$el.find('#inicioVigenciaFiltro').val("");
			_this.$el.find('#finalVigenciaFiltro').val("");
			_this.$el.find('#divListaDocumento').addClass('hide');
		},	
		
		
		sair: function () {
			loadCCR.start();
			console.log("saindo do CCR...");
			window.location = 'index.html';
		},
		
		consultarCpfPorContrato : function(idContrato) {
			_this.contrato.consultarCpfPorContrato(idContrato).done(function sucesso(data) {
				//consulta dados Sicli
				_this.buscaDadosSicli(data);
			})
			.error(function erro(jqXHR) {
				
				if(jqXHR && jqXHR.responseText && jqXHR.responseText != ""){
					Retorno.trataRetorno({codigo: -1, tipo: "ERRO_NEGOCIAL", mensagem: "Ocorreu ao imprimir!"}, '', jqXHR);
				}else{
					Retorno.trataRetorno({codigo: -1, tipo: "ERRO_NEGOCIAL", mensagem: "Ocorreu ao imprimir!"});
				}
			});
		},
		
		buscaDadosSicli : function(cpf) {
			_this = this;
			//valida sicli
			_this.modeloCliente = new Cliente();
			_this.modeloCliente.consultarDadosSicliPorCPF(cpf).done(function sucesso(data) {		
				console.log(data);
				loadCCR.stop();
				
				if (data.dados.responseArqRef.status.codigo == 0) {
					_this.gerarImpressaoTeste(data.dados);
					
				}else {
					_this.$el.find('#divModalVisualizarDoc').addClass('hide');
					_this.$el.find('#divModalVisualizarDoc').modal('hide');
					Retorno.validarRetorno(data);
					
				}
			})
			.fail(function erro(jqXHR) {
				_this.gestor  = "false";
			});
		},
	});

	return manterDocumentoControle;
	
});


