define(['enumeracoes/eMensagemCCR',
        'enumeracoes/eTipoServicos',
        'util/retorno',
        'modelo/taxas/taxaIOF',
        'modelo/taxas/taxaIOFColecao',
        'text!visao/taxas/manterIOF.html',
        'text!visao/taxas/manterListaTaxaIOF.html'
        ], 
function(EMensagemCCR, ETipoServicos, Retorno, TaxaIOFModel, TaxaIOFCollection, manterIOFTpl, manterListaTaxaIOFTpl){

	var _this = null;
	var ManterIOFControle = Backbone.View.extend({

		className: 'ManterIOFControle',
		
		/**
		 * Templates
		 */
		manterIOFTemplate : _.template(manterIOFTpl),
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
		
		/**
		 * Função que faz bind das ações e interações da pagina com as funções
		 * javascript
		 * 
		 */
		events : {
			'click a#btnConsultarTaxaIOF' : 'consultarTaxaIOF',
			'click a#btnAlterarTaxa'      : 'alterarTaxa',		
			'click a#btnNovaTaxa'		  : 'novaTaxa',
			'click a#btnSalvar'			  : 'salvar',
			'click a#btnLimparForm'		  : 'limparFormToda',
			'click a#btnSair'		      : 'sair',
			'click a#btnVoltar'		      : 'voltar',
			'click a#btnCancelar'		  : 'cancelar',
			'click a#btnRemoverTaxa'	  : 'remover',
			'blur input#inicioVigencia' 		  : 'changed',
			'blur input#aliquotaBasica' 		  : 'changed',
			'blur input#aliquotaComplementar'  	  : 'changed'
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
			_this.acao = _this.options.acao || 'T';
			
			// configura validator
			_this.validator.addValidator(new DataValidator());
			_this.validator.addValidator(new DataValidatorMenor(formatDate(amanha, 'dd/MM/yyyy')));
			_this.validator.addValidator(new DataValidatorTaxaSeguroIOF(formatDate(amanha, 'dd/MM/yyyy')));
			_this.validator.addValidator(new DataValidatorTaxaSeguroIOF_FimVigencia(formatDate(amanha, 'dd/MM/yyyy')));
			_this.validator.withErrorRender(new BootstrapErrorRender());
		},
		
		
		render : function() {
			console.log("Manter Taxa IOF - render");	
			
			//Inclui o template inicial no el deste controle
			_this.$el.html(_this.manterIOFTemplate({TaxaIOF: {}}));

			//Alinha os botões do popup à direita, com uma margen de 30px
			_this.$el.find('#divFormulario #divAcoesManterIOF').css('text-align', 'right');
			_this.$el.find('#btnVoltar').css('margin-right', '30px');
			
			//Carrega as mascaras usadas.
			loadMaskEl(_this.$el);
			listenToDatepickerChange(_this.$el, _this.changed);
			
			//Sobreescreve submit da form
			_this.$el.find('#formFiltroTaxaIOF').submit(function(ev) {
				ev.preventDefault();
				$('#inicioVigenciaFiltro').blur();
				$('#finalVigenciaFiltro').blur();
				_this.consultarTaxaIOF();
			});
			
			return _this;
		},		
		
		consultarTaxaIOF: function () {

			console.log("Manter IOF - consultarTaxaIOF");	
			loadCCR.start();
			var dateIni = $('#inicioVigenciaFiltro').val();
			var dateFim = $('#finalVigenciaFiltro').val();
		 
			// lista taxas			
			_this.getCollection().listar(dateIni, dateFim)
			.done(function sucesso(data) {
				Retorno.trataRetorno(data, 'manterIOF', null, false);
				
				_this.$el.find('#divListaTaxaIOFShow').removeClass("hidden");
				//
				//$.format.date("Thu Jan 18 2018 00:00:00 GMT-0200 (BRST)", "dd/MM/yyyy");
				//$.format.date("Thu Jan 18 2018 00:00:00 GMT-0200 (BRST)", "dd/MM/yyyy");
				
				//Inclui o template inicial no el deste controle
				_this.$el.find('#divListaTaxaIOF').html(_this.manterListaTaxaIOFTemplate({TaxaIOF: data.listaRetorno}));
								
				//ajustes
				// configura datatable
				_this.$el.find('#gridListaTaxaIOF').DataTable({
					'aoColumns' : [ null, null, null, null, null, null, { "bSortable": false }],
					'aaSorting': [],
					'oLanguage' : {'sEmptyTable' : 'Nenhum registro encontrado.', 'sLengthMenu': '_MENU_ registros por página', 'sInfoFiltered' : '(Filtrado _MAX_ do total de entradas)'},
				});
				
				//https://datatables.net/examples/ajax/orthogonal-data.html
//				$(document).ready(function() {
//				    $('#gridListaTaxaIOF').DataTable( {
//				        ajax: data.listaRetorno,
//				        columns: [
//				            { data: "dtInicioVigencia" },
//				            { data: "dtFinalVigencia" },
//				            { data: {
//				                _:    "dtFinalVigencia.display",
//				                sort: "dtFinalVigencia.timestamp"
//				            } },
//				            { data: "dtFinalVigencia" }
//				        ]
//				    } );
//				} );
//				
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
					
					$('#gridListaTaxaIOF').find('th').last().hide();
					$('#gridListaTaxaIOF').find('td').last().hide();
				}
				
				loadCCR.stop();				
			})
			.error(function erro(jqXHR) {
				Retorno.trataRetorno({codigo: -1, tipo: "ERRO_EXCECAO", mensagem: "Ocorreu um erro ao listar as taxas IOF cadastradas!"}, 'manterIOF', jqXHR);
				loadCCR.stop();
			});
		
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
		
		sair: function () {
			loadCCR.start();
			console.log("saindo do CCR...");
			window.location = 'index.html';
		},
					
		novaTaxa: function () {
			//_this.limparForm();
			_this.modelo = new TaxaIOFModel();
			
			//retira a msg de erro do campo inicioVigencia, caso tenha sidfo mostrado na tela
			_this.validator.withForm('formCadastroIOF').cleanError("inicioVigencia");
			_this.validator.withForm('formCadastroIOF').cleanError("aliquotaBasica");
			_this.validator.withForm('formCadastroIOF').cleanError("aliquotaComplementar");

			//Seta as propriedades padrões dos campos titulo e data de inicio da vigência
			_this.$el.find('#divTituloForm').html('&nbsp;Incluir Al&iacute;quota de IOF');
			_this.$el.find('#inicioVigencia').prop('disabled', false);
			_this.$el.find('#aliquotaBasica').prop('disabled', false).val('');
			_this.$el.find('#aliquotaComplementar').prop('disabled', false).val('');
			_this.$el.find('#divFormulario #spanIconDataVigencia').prop('class', 'add-on');
			
			_this.validator.withForm('formCadastroIOF').cleanErrors();			
			_this.$el.find('#formCadastroIOF')[0].reset();

			_this.$el.find('#divFormulario').modal('show');
			
			_this.comando = 1; // novo
		},
		
		alterarTaxa: function (evt) {
			var id = _this.$el.find(evt.currentTarget).data('codigo');
			_this.modelo = _this.getCollection().get(id);
			
			//retira a msg de erro do campo inicioVigencia, caso tenha sidfo mostrado na tela
			_this.validator.withForm('formCadastroIOF').cleanError("inicioVigencia");			
			_this.validator.withForm('formCadastroIOF').cleanError("aliquotaBasica");
			_this.validator.withForm('formCadastroIOF').cleanError("aliquotaComplementar");
			
			//_this.limparForm();
			_this.comando = 2; // alterar
						
			_this.$el.find('#divTituloForm').html('&nbsp;Alterar Al&iacute;quota de IOF');		
			_this.$el.find('#inicioVigencia').prop('disabled', false).attr('validators', 'required,data');
			_this.$el.find('#inicioVigencia').prop('disabled', true).val(formatadores.formatarData(_this.modelo.get('inicioVigencia')));			
			_this.$el.find('#divFormulario #spanIconDataVigencia').prop('class', 'hide');
			_this.$el.find('#aliquotaBasica').val(formatadores.formatarTaxa(_this.modelo.get('aliquotaBasica'), 5));
			_this.$el.find('#aliquotaComplementar').val(formatadores.formatarTaxa(_this.modelo.get('aliquotaComplementar'), 5));
			
			_this.modelo.set({
				aliquotaBasica: formatadores.formatarTaxa(_this.modelo.get('aliquotaBasica'), 5),
				aliquotaComplementar: formatadores.formatarTaxa(_this.modelo.get('aliquotaComplementar'), 5),				
			});
							
			_this.$el.find('#divFormulario').modal('show');
		},
				
		salvar: function () {
			if (_this.validator.withForm('formCadastroIOF').validate()){
				//_this.limparForm();
				_this.$el.find('#divFormulario').modal('hide');
				
				msgCCR.confirma(EMensagemCCR.manterIOF.MA0046, function() {
					
					loadCCR.start();				
					
					_this.modelo.salvar()
					.done(function sucesso(data){
						Retorno.trataRetorno(data, 'manterIOF');
						_this.atualizar();					
					})
					.error(function erro(jqXHR){
						Retorno.trataRetorno({codigo: -1, tipo: "ERRO_EXCECAO", mensagem: "Ocorreu um erro ao salvar a taxa IOF!"}, 'manterIOF', jqXHR);					
						_this.atualizar();					
					});
				});
				
			}
		},
		
		limparForm: function() {
			_this.$el.find('#inicioVigenciaFiltro').prop('disabled', false).attr('validators', 'required,data,dataMenor');
			_this.$el.find('#finalVigenciaFiltro').prop('disabled', false).attr('validators', 'required,data,dataMenor');
			
			_this.validator.withForm('formCadastroIOF').cleanErrors();			
			_this.$el.find('#formCadastroIOF')[0].reset();			
			_this.$el.find('#divListaTaxaIOFShow').addClass("hidden");
			
		},
		
		limparFormToda: function() {
			_this.$el.find('#inicioVigenciaFiltro').prop('disabled', false).attr('validators', 'required,data,dataMenor');
			_this.$el.find('#finalVigenciaFiltro').prop('disabled', false).attr('validators', 'required,data,dataMenor');
			
			_this.validator.withForm('formCadastroIOF').cleanErrors();			
			_this.$el.find('#formCadastroIOF')[0].reset();
			
			_this.validator.withForm('formFiltroTaxaIOF').cleanErrors();			
			_this.$el.find('#formFiltroTaxaIOF')[0].reset();			
			_this.$el.find('#divListaTaxaIOFShow').addClass("hidden");
		},
		
		voltar: function () {						
			//_this.limparForm();
			_this.comando = null;
			
			_this.$el.find('#divFormulario').modal('hide');
		},
		
		remover: function (evt) {
			var id = _this.$el.find(evt.currentTarget).data('codigo');			
			_this.modelo = _this.getCollection().get(id); 
			
			msgCCR.confirma(EMensagemCCR.manterIOF.MA0046, function() {
				loadCCR.start();
				
				_this.modelo.excluir()
				.done(function sucesso(data){
					if (Retorno.trataRetorno(data, 'manterIOF')) {					
						_this.modelo.clear().stopListening;
						_this.getCollection().remove(id);
						_this.atualizar();		
					}
				})
				.error(function erro (jqXHR) {
					Retorno.trataRetorno({codigo: -1, tipo: "ERRO_EXCECAO", mensagem: "Ocorreu um erro ao excluir a taxa IOF!"}, 'manterIOF', jqXHR);
					loadCCR.stop();
					_this.atualizar();
				});	
			});
			
		},
		
		atualizar: function(){
			_this.consultarTaxaIOF();
			$('ul.nav a#manterTaxaIOF.links-menu').trigger('click');
		}
	
	});

	return ManterIOFControle;
	
});