/**
 * @author F620600
 * 
 * JavaScript que controla as ações das paginas:
 *  manterGrupo.html
 *  manterListaTaxaIOF.html
 *  
 * @version 1.0.0.0
 * 
 * 
 */
define(['enumeracoes/eMensagemCCR',
        'enumeracoes/eTipoServicos',
        'util/retorno',
        'modelo/taxas/grupoTaxa',
        'text!visao/taxas/manterGrupoTaxa.html',
        'text!visao/taxas/manterListaGrupoTaxa.html'
        ], 
function(EMensagemCCR, ETipoServicos, Retorno, GrupoTaxaModel,  manterGrupoTpl, manterListaTaxaIOFTpl){

	var _this = null;
	var ManterGrupoTaxaControle = Backbone.View.extend({

		className: 'ManterGrupoTaxaControle',
		
		/**
		 * Templates
		 */
		manterGrupoTemplate : _.template(manterGrupoTpl),
		manterListaTaxaIOFTemplate  : _.template(manterListaTaxaIOFTpl),
		
		/**
		 * Variaveis da classe
		 */
		validator   : new Validators(),
		message     : new Message(),
		comando     : null,
		modelo		: null,
		collection  : null,
		acao		: null,
		id			: null,
		codRet		: null,
		nomeRet		: null,
		
		/**
		 * Função que faz bind das ações e interações da pagina com as funções
		 * javascript
		 * 
		 */
		events : {
			'click a#btnConsultarGrupoTaxa' 	: 'consultarGrupoTaxa',
			'click a#btnNovoGrupoTaxa'			: 'novoGrupoTaxa',
			'click a#btnLimparForm'				: 'limparForm',
			'focus #inputGrupoTaxa'				: 'getAutocomplete',
			'keydown #inputGrupoTaxa'			: 'invokefetch',
			'click a#btnSair'		   			: 'sair',
			'click a#btnRemoverGrupoTaxa'  		: 'remover',
			'click a#btnAlterarGrupoTaxa'  		: 'alterar'	,
			'click a#btnSalvar'			  		: 'salvar',
			'click a#btnVoltar'		      		: 'voltar'

			
		},
				
		
		/**
		 * Função padrão de incialização do template html
		 * 
		 */
		initialize : function() {
			console.log("Manter IOF controle - initialize");
			// pra nao ter problema de pegar outro 'this'
			_this = this;			
			_this.validator.withErrorRender(new BootstrapErrorRender());
		},
		
		
		render : function() {
			console.log("Manter - render");	
			
			
			//Inclui o template inicial no el deste controle
			_this.$el.html(_this.manterGrupoTemplate({TaxaIOF: {}}));

			//Alinha os botões do popup à direita, com uma margen de 30px
			//_this.$el.find('#divFormulario #divAcoesmanterGrupo').css('text-align', 'right');
			_this.$el.find('#btnVoltar').css('margin-right', '30px');
			
			//Carrega as mascaras usadas.
			loadMaskEl(_this.$el);
			listenToDatepickerChange(_this.$el, _this.changed);
			
			_this.$('#nomeGrupoTaxa').autocomplete({ source: $.proxy( _this.findGrupoTaxas, _this), minLength: 3 });
			//Sobreescreve submit da form
			
			return _this;
		},		
		
		consultarGrupoTaxa: function (retorno) {

			console.log("Manter - consultarGrupoTaxa");
			
			
			_this.validator.withForm('formFiltroTaxaIOF').cleanErrors();
			
			codigo = _this.$el.find('#codigo').val();
			nome = _this.$el.find('#nomeGrupoTaxa').val();
			consultar = false;
			if(retorno=="true"){
				codigo= _this.codRet;
				nome = _this.nomeRet;
				consultar = true;
			}else if(codigo == "" && nome == ""){
				_this.validator.withForm('formFiltroTaxaIOF').validate();
			}else{
				consultar = true;				
			}
			
			if (consultar){
				//_this.$el.find('#listaGrupoTaxa').removeClass("hidden");
				loadCCR.start();
//				var codigo=0;
//				var nome="teste";
				
				
				// lista taxas			
				_this.getCollection().buscar(codigo,nome)
				.done(function sucesso(data) {
					
					_this.$el.find('#divListaGrupoTaxaShow').removeClass("hidden");
					Retorno.trataRetorno(data, 'grupoTaxa', null, false);
					
					//Inclui o template inicial no el deste controle
					_this.$el.find('#divListaGrupoTaxa').html(_this.manterListaTaxaIOFTemplate({grupoTaxa: data.listaRetorno}));
									
					// configura datatable
					_this.$el.find('#gridListaGrupoTaxa').dataTable({
						'aoColumns' : [ null, null, null, null,  { "bSortable": false } ],
						'aaSorting': [],
						//'oLanguage' : {'sEmptyTable' : 'Nenhum registro encontrado.', 'sLengthMenu': '_MENU_ registros por página'} //Sobrescreve o sEmptyTable do jquery.dataTable.js do fec-web.
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
					Retorno.trataRetorno({codigo: -1, tipo: "ERRO_EXCECAO", mensagem: "Ocorreu um erro ao listar !"}, 'manterGrupo', jqXHR);
					loadCCR.stop();
				});
			
				return _this;
				
			}
			
			
					
		},
				
	    getCollection: function () {
	    	if (_this.collection == null || this.collection == undefined)
	    		_this.collection = new GrupoTaxaModel();
	    		
	    	return _this.collection;
	    },
	    
	    findGrupoTaxas : function(request, response) {
	    	console.log("Manter Grupo Taxa - findGrupoTaxas");
	    	
			$.when( _this.getCollection().getAutoCompleteNomeGrupoTaxa(request.term) )
            .then(function ( data ) { response( _.map(data.listaRetorno, function ( d ) { return { value: d.nomeModeloDocumento, label: d.nomeGrupoTaxa }; }) ); });
		},
	    
		sair: function () {
			loadCCR.start();
			console.log("saindo do CCR...");
			window.location = 'index.html';
		},
					
		
		novoGrupoTaxa: function () {
			
			
			_this.id=null;
			//Seta as propriedades padrões dos campos titulo e data de inicio da vigência
			_this.$el.find('#divTituloForm').html('&nbsp;Incluir Grupo Taxa');
			
			_this.$el.find('#tipoOper').val("incluir");
			_this.$el.find('#divInputNovoCodigo').val("");
			_this.$el.find('#divInputNovoNome').val("");
			_this.$el.find('#divInputNovoCodigo').prop('disabled', false);
			_this.validator.withForm('divFormulario').cleanErrors();
			
			_this.$el.find('#divFormulario').modal('show');
			
		},		
		
		limparForm: function() {
			_this.$el.find('#codigo').val("");
			_this.$el.find('#nomeGrupoTaxa').val("");
			_this.$el.find('#divListaGrupoTaxaShow').addClass("hidden");
			
			
			/*_this.$el.find('#inicioVigenciaFiltro').prop('disabled', false).attr('validators', 'required,data,dataMenor');
			
			_this.validator.withForm('formCadastroIOF').cleanErrors();			
			_this.$el.find('#formCadastroIOF')[0].reset();			*/
		},
		
		limparFormToda: function() {
			/*_this.$el.find('#inicioVigenciaFiltro').prop('disabled', false).attr('validators', 'required,data,dataMenor');
			
			_this.validator.withForm('formCadastroIOF').cleanErrors();			
			_this.$el.find('#formCadastroIOF')[0].reset();
			
			_this.validator.withForm('formFiltroTaxaIOF').cleanErrors();			
			_this.$el.find('#formFiltroTaxaIOF')[0].reset();	*/		
		},
		
		voltar: function () {						
			_this.validator.withForm('formCadastroGrupoTaxa').cleanErrors();

			_this.$el.find('#divFormulario').modal('hide');
		},
		salvar: function () {
			var codigo = _this.$el.find('#divInputNovoCodigo').val();
			
			if(parseInt(codigo) < 0){
				_this.$el.find('#divInputNovoCodigo').val("");
				_this.validator.withForm('formCadastroGrupoTaxa').validate();
			}else{
				
				if (_this.validator.withForm('formCadastroGrupoTaxa').validate()){
					_this.$el.find('#divFormulario').modal('hide');
					
					msgCCR.confirma(EMensagemCCR.manterGrupo.MA0046, function() {
						
						loadCCR.start();
						var codigo = _this.$el.find('#divInputNovoCodigo').val().replace(/[_.\-\/]/g, '');
						var nome = _this.$el.find('#divInputNovoNome').val();
						var tipoOper = _this.$el.find('#tipoOper').val();
						
						var grupoTaxa={};
						grupoTaxa.codigo=codigo;
						grupoTaxa.nome=nome;
						grupoTaxa.tipoOper = tipoOper;
						
						_this.codRet=codigo;
						_this.nomeRet=nome;		
						
						grupoTaxa.id=_this.id;
						_this.getCollection().salvar(grupoTaxa)
						.done(function sucesso(data){
							loadCCR.stop();
							
							if (data.mensagemRetorno == "MA0093") {
								Retorno.trataRetorno({codigo: -1, tipo: "ERRO_NEGOCIAL", mensagem: "", idMsg: 'MA0093'}, 'manterGrupo');
								//msgCCR.alerta("Convenente não existe no SICLI.!", function () {});
								return;
							}else{
								Retorno.trataRetorno(data, 'manterGrupo');
								_this.consultarGrupoTaxa("true");
							}
							
							
						})
						.error(function erro(jqXHR){
							Retorno.trataRetorno({codigo: -1, tipo: "ERRO_EXCECAO", mensagem: "Ocorreu um erro ao salvar a Grupo Taxa!"}, 'manterGrupo', jqXHR);					
							loadCCR.stop();			
						});
					});
				}
			}
			
			
			
				
		},
		
		alterar: function (evt) {
			var id = _this.$el.find(evt.currentTarget).data('id');
			var nome = _this.$el.find(evt.currentTarget).data('nome');
			var codigo = _this.$el.find(evt.currentTarget).data('codigo');
			_this.codRet=codigo;
			
			_this.id=id;
			_this.$el.find('#divInputNovoCodigo').val(codigo);
			_this.$el.find('#divInputNovoCodigo').prop('disabled', true);
			
			_this.$el.find('#divInputNovoNome').val(nome);
			_this.$el.find('#tipoOper').val("alterar");
			_this.$el.find('#divTituloForm').html('&nbsp;Alterar Grupo Taxa');
			_this.$el.find('#divFormulario').modal('show');

			
		},
		
		remover: function (evt) {
			var id = _this.$el.find(evt.currentTarget).data('codigo');			
			_this.codRet = _this.$el.find('#codigo').val();
			_this.nomeRet = _this.$el.find('#nomeGrupoTaxa').val();
			
			
			msgCCR.confirma(EMensagemCCR.manterGrupo.MA0046, function() {
				loadCCR.start();
				
				_this.getCollection().excluir(id)
				.done(function sucesso(data){
					loadCCR.stop();
						Retorno.trataRetorno(data, 'manterGrupo');
						_this.consultarGrupoTaxa("true");
								
						
				})
				.error(function erro (jqXHR) {
					Retorno.trataRetorno({codigo: 1, tipo: "ERRO_EXCECAO", mensagem: "Ocorreu um erro ao excluir!"}, 'manterGrupo', jqXHR);
					loadCCR.stop();
				});	
			});
			
		},
		
		atualizar: function(){
			_/*this.consultarTaxaIOF();
			$('ul.nav a#manterTaxaIOF.links-menu').trigger('click');*/
		}
	
	});

	return ManterGrupoTaxaControle;
	
});