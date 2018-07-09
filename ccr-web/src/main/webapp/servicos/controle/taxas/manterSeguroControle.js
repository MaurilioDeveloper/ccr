/**
 * @author F620600
 * 
 * JavaScript que controla as ações das paginas:
 *  ManterSeguro.html
 *  
 * @version 1.0.0.0
 * 
 * 
 */
define(['enumeracoes/eMensagemCCR',
        'enumeracoes/eTipoServicos',
        'util/retorno',
        'modelo/taxas/taxaSeguro',
        'modelo/taxas/taxaSeguroColecao',
        'text!visao/taxas/manterSeguro.html',
        'text!visao/taxas/manterListaSeguro.html'
        ], 
function(EMensagemCCR, ETipoServicos, Retorno, TaxaSeguro, TaxaSeguroColecao, manterSeguroTpl, manterListaSeguroTpl){

	var _this = null;
	var ManterSeguroControle = Backbone.View.extend({

		className: 'ManterSeguroControle',
		
		/**
		 * Templates
		 */
		manterSeguroTemplate : _.template(manterSeguroTpl),
		manterListaSeguroTemplate  : _.template(manterListaSeguroTpl),
		
		/**
		 * Variaveis da classe
		 */
		validator   : new Validators(),
		message     : new Message(),
		comando     : null,
		modelo		: null,
		collection  : null,
		acao		: null,
		prazoMinOld : null,
		prazoMaxOld : null,
		idadeMinOld : null,
		idadeMaxOld : null,
		taxaOld : null,
	
		inicioVigenciaChave: '', // variavel auxiliar necessaria na alteracao
		
		/**
		 * Função que faz bind das ações e interações da pagina com as funções
		 * javascript
		 * 
		 */
		events : {
			'click a#bntConsultarTaxaSeguro' : 'consultarTaxaSeguro',
			'click a#btnAlterarTaxa'    : 'alterarTaxa',		
			'click a#btnNovaTaxa'		: 'novaTaxa',
			'click a#btnSalvar'			: 'salvar',
			'click a#btnLimparForm'		: 'limparForm',
			'click a#bntSair'		      : 'sair',
			'click a#btnVoltar'		      : 'voltar',
			'click a#btnCancelar'		: 'cancelar',
			'click a#btnRemoverTaxa'	: 'remover',
				
			'change input#inicioVigencia' : 'changed',
			'change input#fimVigencia'    : 'changed',
			'change input#prazoMin' 	  : 'changed',
			'change input#prazoMax' 	  : 'changed',
			'change input#idadeMin' 	  : 'changed',
			'change input#idadeMax' 	  : 'changed',
			'change input#taxa'		 	  : 'changed'
		},
				
		/**
		 * Função padrão de incialização do template html
		 * 
		 */
		initialize : function() {
			console.log("Manter Taxa Seguro controle - initialize");
			var amanha = new Date();
			amanha.setDate((new Date()).getDate() + 1);
			
			// pra nao ter problema de pegar outro 'this'
			_this = this;
			_this.acao = _this.options.acao || 'T';
			
			// configura validator
			_this.validator.addValidator(new DataValidator());
			_this.validator.addValidator(new DataValidatorMenor(formatDate(amanha, 'dd/MM/yyyy')));
			_this.validator.addValidator(new DataValidatorTaxaSeguroIOF(formatDate(amanha, 'dd/MM/yyyy')));
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
			console.log("Manter Taxa Seguro - render");	
			
			
			//Inclui o template inicial no el deste controle
			_this.$el.html(_this.manterSeguroTemplate({TaxaSeguro: {}}));
			
			//Alinha os botões do popup à direita, com uma margen de 30px
			_this.$el.find('#divFormulario #divAcoesManterSeguro').css('text-align', 'right');
			_this.$el.find('#btnCancelar').css('margin-right', '30px');
			
			//Carrega as mascaras usadas.
			loadMaskEl(_this.$el);
			listenToDatepickerChange(_this.$el, _this.changed);
			
			//Sobreescreve submit da form
			_this.$el.find('#formFiltroTaxaSeguro').submit(function(ev) {
				ev.preventDefault();
				$('#inicioVigenciaFiltro').blur();
				_this.consultarTaxaSeguro();
			});
			

			return _this;
		},
		
	    getCollection: function () {
	    	if (_this.collection == null || this.collection == undefined)
	    		_this.collection = new TaxaSeguroColecao();
	    		
	    	return _this.collection;
	    },

	    changed : function (e) {
	    	_this.modelo;
	    	_this.modelo.set(e.target.name,e.target.value);
	    	_this.getCollection().set(_this.modelo, {remove: false});
		},

		consultarTaxaSeguro : function() {
			console.log("Manter Taxa Seguro - consultarTaxaSeguro");

			loadCCR.start();
			var dateIni = $('#inicioVigenciaFiltro').val();
			var dateFim = $('#finalVigenciaFiltro').val();
			
			if (dateIni !== null && dateIni !== "") {
				dateIni = dateIni.replace("/", "");
				dateIni = dateIni.replace("/", "");
			}
			
			if (dateFim !== null && dateFim !== "") {
				dateFim = dateFim.replace("/", "");
				dateFim = dateFim.replace("/", "");
			}
			
			// lista taxas
			_this.getCollection().listar(dateIni, dateFim)
				.done(function sucesso(data) {
					_this.$el.find('#divListaTaxaSeguroShow').removeClass("hidden");
					//Inclui o template inicial no el deste controle
					_this.$el.find('#divListaTaxaSeguro').html(_this.manterListaSeguroTemplate({TaxaSeguro: data.listaRetorno}));

					// configura datatable
					_this.$el.find('#gridListaTaxaSeguro').dataTable({
						'aoColumns' : [ /*{sType: "date-br"}, {sType: "date-br"}*/ null, null, null, null, null, null, null, { "bSortable": false } ],
						//'aaSorting': [[0, 'desc'], [2, 'desc'], [9, 'desc']],
						'aaSorting': [],
						//'oLanguage' : {'sEmptyTable' : 'Nenhum registro encontrado.', 'sLengthMenu': '_MENU_ registros por página'}
						'oLanguage' : {'sEmptyTable' : 'Nenhum registro encontrado.', 'sLengthMenu': '_MENU_ registros por página', 'sInfoFiltered' : '(Filtrado _MAX_ do total de entradas)'},
					});

					//Carrega as mascaras usadas.
					loadMask();

					// desabilita os botoes
					_this.$el.find('a.disabled').on('click', function(evt) {
						evt.preventDefault();
						return false;
					});

					listenToDatepickerChange(_this.$el, _this.changed);

					if (_this.acao == 'C') {
						$('#btnNovaTaxa').remove();
						$('#btnAlterarTaxa').remove();
						$('#btnRemoverTaxa').remove();

						$('#gridListaTaxaSeguro').find('th').last().hide();
						$('#gridListaTaxaSeguro').find('td').last().hide();
					}

					loadCCR.stop();
				})
				.error(function erro(jqXHR) {
					console.log(jqXHR);
					Retorno.trataRetorno({codigo: -1, tipo: "ERRO_EXCECAO", mensagem: "Ocorreu um erro ao listar as taxas de Juros cadastradas!"});
					loadCCR.stop();
				});

			return _this;
		},

		sair: function () {
			loadCCR.start();
			console.log("saindo do CCR...");
			window.location = 'index.html';
		},

		novaTaxa: function () {
			_this.limparForm();
			
			_this.modelo = new TaxaSeguro();
			_this.inicioVigenciaChave = '';
			
			//Seta as propriedades padrões dos campos titulo e data de inicio da vigência
			_this.$el.find('#divTituloForm').html('&nbsp;Incluir Taxa Seguro');
			_this.$el.find('#inicioVigencia').prop('disabled', false).val('');
			_this.$el.find('#divFormulario #spanIconDataVigencia').prop('class', 'add-on');
			
			_this.$el.find('#divFormulario').modal('show');
			_this.comando = 1; // novo
		},
		
		alterarTaxa: function (evt) {
			_this.limparForm();
			
			var id = _this.$el.find(evt.currentTarget).data('idtaxa');
			_this.modelo = _this.getCollection().get(id);
			
			var desabilita = false;
			
			// pega a data de inicio vigencia (chave) para o caso do usuario alterar esta data 
			_this.inicioVigenciaChave = _this.modelo.get('inicioVigencia');
			
			
			// muda o título do modal de 'Incluir Taxa Seguro' para 'Alterar Taxa Seguro'
			_this.$el.find('#divTituloForm').html('&nbsp;Alterar Taxa Seguro');
			
			_this.$el.find('#inicioVigencia').prop('disabled', true).val(formatadores.formatarData(_this.modelo.get('inicioVigencia'))).attr('validators', '');
			_this.$el.find('#divFormulario #spanIconDataVigencia').prop('class', 'hide');
			_this.$el.find('#fimVigencia').val(formatadores.formatarData(_this.modelo.get('fimVigencia')));
			
			if((_this.modelo.get('fimVigencia') != null)){
				_this.$el.find('#prazoMin').prop('disabled', true).val(_this.modelo.get('prazoMin'));
				_this.$el.find('#prazoMax').prop('disabled', true).val(_this.modelo.get('prazoMax'));
				_this.$el.find('#idadeMin').prop('disabled', true).val(_this.modelo.get('idadeMin'));
				_this.$el.find('#idadeMax').prop('disabled', true).val(_this.modelo.get('idadeMax'));	
			}else{
				_this.$el.find('#prazoMin').val(_this.modelo.get('prazoMin'));
				_this.$el.find('#prazoMax').val(_this.modelo.get('prazoMax'));
				_this.$el.find('#idadeMin').prop('disabled', false).val(_this.modelo.get('idadeMin'));
				_this.$el.find('#idadeMax').prop('disabled', false).val(_this.modelo.get('idadeMax'));	
			}
			
			_this.$el.find('#taxa').val(formatadores.formatarTaxa(_this.modelo.get('taxa'), 5));
			//_this.modelo.get('taxa')
			_this.modelo.set({taxa: formatadores.formatarTaxa(_this.modelo.get('taxa'), 5)});
			
			//guarda os valores para o caso de uma alteracao
			_this.prazoMinOld = _this.modelo.get('prazoMin');
			_this.prazoMaxOld = _this.modelo.get('prazoMax');
			_this.idadeMinOld = _this.modelo.get('idadeMin');
			_this.idadeMaxOld = _this.modelo.get('idadeMax');
			_this.taxaOld = formatadores.formatarTaxa(_this.modelo.get('taxa'), 5);
						
			_this.$el.find('#divFormulario').modal('show');
			_this.comando = 2; // alterar
		},
		
		salvar: function (evt) {			
			if (_this.validator.withForm('formCadastroSeguro').validate()){
				
//				if(_this.inicioVigenciaChave != null){
//					_this.inicioVigenciaChave = this.$el.find('#inicioVigencia').val();
//					_this.inicioVigenciaChave = _this.inicioVigenciaChave.substr(6, 4) +"-"+ _this.inicioVigenciaChave.substr(3, 2) +"-"+ _this.inicioVigenciaChave.substr(0, 2);
//				}
				_this.limparForm();
				_this.$el.find('#divFormulario').modal('hide');			 

				//seta valores quando for edicao
				_this.modelo.set('prazoMinOld',_this.prazoMinOld);
				_this.modelo.set('prazoMaxOld',_this.prazoMaxOld);
				_this.modelo.set('idadeMinOld',_this.idadeMinOld);
				_this.modelo.set('idadeMaxOld',_this.idadeMaxOld);
				_this.modelo.set('taxaOld',_this.taxaOld);
				
				
				
				msgCCR.confirma(EMensagemCCR.manterSeguro.MA0046, function() {
					loadCCR.start();	
					_this.modelo.salvar(_this.inicioVigenciaChave)
					.done(function sucesso(data){
						loadCCR.stop();
						Retorno.trataRetorno(data, 'manterSeguro');					
						_this.atualizar();					
					})
					.fail(function erro(jqXHR){
						loadCCR.stop();
						Retorno.trataRetorno({codigo: -1, tipo: "ERRO_EXCECAO", mensagem: "Ocorreu um erro ao salvar a taxa de Seguro!"}, 'manterSeguro', jqXHR);
						_this.atualizar();
					});
				});
			}
		},
		
		limparForm: function() {
			
			_this.$el.find('#divListaTaxaSeguroShow').addClass("hidden");
			
			_this.validator.withForm('formCadastroSeguro').cleanErrors();			
			_this.$el.find('#formCadastroSeguro')[0].reset();

			_this.$el.find('#inicioVigenciaFiltro').prop('disabled', false).attr('validators', 'required,data,dataMenor');

			_this.$el.find('#inicioVigencia').prop('disabled', false).attr('validators', 'required,data,dataMenorTaxaSeguroIOF');
			_this.$el.find('#prazoMin').prop('disabled', false);
			_this.$el.find('#prazoMax').prop('disabled', false);
			_this.$el.find('#idadeMin').prop('disabled', false);
			_this.$el.find('#idadeMax').prop('disabled', false);
			_this.$el.find('#taxa').prop('disabled', false);
			
			_this.$el.find('#formFiltroTaxaSeguro')[0].reset();
			
		},
		
		cancelar: function () {						
			_this.limparForm();
			_this.comando = null;
			
			_this.$el.find('#divFormulario').modal('hide');
		},
		

		remover: function (evt) {
			var id = _this.$el.find(evt.currentTarget).data('idtaxa');
			_this.modelo = _this.getCollection().get(id);
			
			msgCCR.confirma(EMensagemCCR.manterSeguro.MA0046, function() {
				loadCCR.start();
				
				_this.modelo.excluir()
				.done(function sucesso(data){
					loadCCR.stop();
					
					if (Retorno.trataRetorno(data, 'manterSeguro')) {
						_this.modelo.clear().stopListening;
						_this.getCollection().remove(id);								
					}
					
					_this.atualizar();
				})
				.fail(function erro (jqXHR) {					
					loadCCR.stop();
					Retorno.trataRetorno({codigo: -1, tipo: "ERRO_EXCECAO", mensagem: "Ocorreu um erro ao excluir a taxa de Seguro!"}, 'manterSeguro', jqXHR);
					_this.atualizar();
				});
			});			
		},

		atualizar: function(){
			
			_this.consultarTaxaSeguro();
			$('ul.nav a#manterTaxaSeguro.links-menu').trigger('click');
		}
		
	});

	return ManterSeguroControle;
	
});


