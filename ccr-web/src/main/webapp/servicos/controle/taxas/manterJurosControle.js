/**
 * @author F620600
 * 
 * JavaScript que controla as ações das paginas:
 *  manterListaJuros.html
 *  
 * @version 1.0.0.0 
 * 
 * 
 */
define(['enumeracoes/eMensagemCCR',
        'enumeracoes/eMensageria',
        'modelo/convenios/convenio',
        'util/retorno',
        'modelo/convenios/convenioColecao',
        'modelo/taxas/taxaJuros',
        'modelo/taxas/taxaJurosColecao',
        'text!visao/taxas/manterJuros.html',
        'text!visao/taxas/manterListaJuros.html'
        ], 
function(EMensagemCCR, EMensageria, Convenio, Retorno, ConvenioCol, TaxaJurosModel, TaxaJurosCollection, manterTaxaJurosTpl, manterListaTaxaJurosTpl){
	
	var _this = null;
	var ManterJurosControle = Backbone.View.extend({
		
		className: 'manterJurosControle',
		
		/**
		 * Templates
		 */
		manterJurosTemplate : _.template(manterTaxaJurosTpl),
		filtroTaxaTemplate  : _.template(manterListaTaxaJurosTpl),	
		
		
		/**
		 * Variaveis da classe
		 */
		validator     : new Validators(),
		message       : new Message(),
		comando       : null,
		modelo		  : null,
		
		prazoMinOld : null,
		prazoMaxOld : null,
		tipoConsultaRet : null,
		codigoTaxaRet : null,
		listcodGrupos : null,
		inicioVigenciaChave: '', // variavel auxiliar necessaria na alteracao
		
		/**
		 * Função que faz bind das ações e interações da pagina com as funções
		 * javascript
		 * 
		 */
		events : {
			// consulta cliente controle
			'click #formFiltroTaxa a#btnConsultarTaxaJuros'	: 'consultarTaxaJuros',
			'click #formFiltroTaxa a#btnLimpar'	 	: 'limparForm',			
			'click a#btnAlterarTaxa'    : 'alterarTaxa',
			'click a#btnNovaTaxa'		: 'novaTaxa',
			'click a#btnSalvar'			: 'salvar',
			'click a#btnLimparForm'		: 'limparFiltro',
			'click a#btnCancelar'		: 'cancelar',
			'click a#btnRemoverTaxa'	: 'remover',
			'click a#btnVoltar'			: 'voltar',
			'click a#bntSair'		      : 'sair',			
			'click a#btnConsultarCovenio'		      : 'consultarCovenio',			
			
			'change input#radioGrupoTaxa' : 'mostrarCamposPorTipoPesquisa',
			'change input#radioConvenio' : 'mostrarCamposPorTipoPesquisa',
			'change select#idGrupo' : 'changedSelectsConvenio',
			'change select#idGrupoIncluir': 'changedSelectsIdGrupoIncluir',
			'blur input' 				: 'changed'
		},
				
		consultarCovenio : function(e) {
			
			var convenio = $("#codigoTaxa").val();
			var a  = $('#btnConsultarCovenio').prop("disabled");
			_this.convenioModel = new Convenio();
			$('#convenioMessage').addClass("hide");
			
			$('#inicioVigencia').attr('validators', '');
			$('#prazoMin').attr('validators', '');
			$('#prazoMax').attr('validators', '');
			$('#pcMinimo').attr('validators', '');
			$('#pcMedio').attr('validators', '');
			$('#pcMaximo').attr('validators', '');
			$('#nomeConvenio').attr('validators', '');
			
			if (_this.validator.withForm('divFormulario').validate()){
				
				//loadCCR.start();
				
				_this.convenioModel.consultarConvenio(convenio).done(function sucesso(data) {
					//loadCCR.stop();
					
					$('#inicioVigencia').attr('validators', 'required,data,dataMenorTaxaSeguroIOF');
					$('#prazoMin').attr('validators', 'required');
					$('#prazoMax').attr('validators', 'required');
					$('#pcMinimo').attr('validators', 'required');
					$('#pcMedio').attr('validators', 'required');
					$('#pcMaximo').attr('validators', 'required');
					$('#nomeConvenio').attr('validators', 'required');
					
					//_this.validator.addValidator(new ValidaRetornoConvenio(data);
					
					if(data.tipoRetorno ==  "ERRO_NEGOCIAL"){
						//Retorno.trataRetorno({codigo: 1, tipo: "AVISO", mensagem: "", idMsg: 'MA004'}, 'convenio');
						$('#convenioMessage').removeClass("hide");
						
						//return;
					}else{
						$('#convenioMessage').addClass("hide");
						//sucesso
						$('#nomeConvenio').val(data.nome);
						
						
						
					}
						
					
				})
				.error(function erro(jqXHR) {
					// (Alert("Errro"));
					msgCCR.alerta("Funcionalidade Indísponivel!!!", function () {});
				});
			
			}
			
			
			
			
			
			
		},
		
		changedSelectsIdGrupoIncluir : function(e) {
			var value = e.target.value;
			var vlrTipoPesquisa = value;
			$('#convenioMessage').addClass("hide");
			$('#nomeConvenio').val("");
			$('#codigoTaxa').val("");
			$('#codigoTaxa').attr("disabled", false);
			
			
			var valorCod = _this.getIdListGrupo(vlrTipoPesquisa);
			
			//alert(vlrTipoPesquisa+"/"+valor);
			
			_this.validator.withForm('divFormulario').cleanErrors();
			
			valorCod = $('select[name=idGrupoIncluir]').val();
			
			if(valorCod=="999"){
				this.$el.find('#codigoTaxa').prop('disabled', false).attr('validators', 'required');
				this.$el.find('#btnConsultarCovenio').prop('disabled', false);
				this.$el.find('#nomeConvenio').prop('disabled', true).attr('validators', 'required');
			}else{
				this.$el.find('#codigoTaxa').prop('disabled', true).attr('validators', '');
				this.$el.find('#btnConsultarCovenio').prop('disabled', true);
				this.$el.find('#nomeConvenio').prop('disabled', true).attr('validators', '');
				
				$('#inicioVigencia').attr('validators', 'required,data,dataMenorTaxaSeguroIOF');
				$('#prazoMin').attr('validators', 'required');
				$('#prazoMax').attr('validators', 'required');
				$('#pcMinimo').attr('validators', 'required');
				$('#pcMedio').attr('validators', 'required');
				$('#pcMaximo').attr('validators', 'required');
			}
			
				
			//_this.validator.withForm('formFiltroTaxa').cleanErrors();
			//_this.validator.withForm('cabecalhoFiltroTaxa').cleanErrors();
			/////_this.validator.withForm('divListaTaxaJuros').cleanErrors();
			
			
		},
		
		/**
		 * Função padrão de incialização do template html
		 * 
		 */
		initialize : function() {
			console.log("Consulta Cliente controle - initialize");
			var amanha = new Date();
			amanha.setDate((new Date()).getDate() + 1);
			
			_this = this;
			
			// configura validator
			_this.validator.withErrorRender(new BootstrapErrorRender());
			_this.validator.addValidator(new DataValidator());
			_this.validator.addValidator(new DataValidatorTaxaSeguroIOF(formatDate(amanha, 'dd/MM/yyyy')));
			_this.validator.addValidator(new DataValidatorMenor(formatDate(amanha, 'dd/MM/yyyy')));
		},
			
		/**
		 * Função padrão para reenderizar os dados html na tela
		 * Nessa função é carregado a mascara para CPF e colocadao o foco
		 * no campo de CPF
		 * Retorna o proprio objeto criado
		 * @returns {anonymous}
		 */
		render : function() {
			
			console.log("Filtro Taxa - render");	
			
			loadCCR.start();
			
			//Inclui o template inicial no el deste controle
			_this.$el.html(_this.manterJurosTemplate({TaxaJuro: {}}));
			
			//Alinha os botões do popup à direita, com uma margen de 30px
			_this.$el.find('#divFormulario #divAcoesManterJuros').css('text-align', 'right');
			_this.$el.find('#btnVoltar').css('margin-right', '30px');
			
			//Carrega as mascaras usadas.
			loadMaskEl(_this.$el);
			listenToDatepickerChange(_this.$el, _this.changed);
			
			//disabilita campo convenio
			//_this.$el.find('#codigoConvenio').prop('disabled', true);
			//_this.$el.find('#codigoConvenio').prop('disabled', true).val('');
			
			//Carrega combos GrupoTaxa
			gCarregarGrupoTaxa(_this.$el, 'idGrupo');
			//gCarregarConvenio(_this.$el, 'codigoConvenio');

			var URL_COMBO_GRUPO = '../ccr-web/consignado/Administracao/Combo/consultar/';
			
			$.ajax({
				type : 'POST',
				contentType : 'application/json',
				dataType : "json",
				url : URL_COMBO_GRUPO,
				data : getComboJson(11, 'dominio'),//qual combo carregar
				async : false,
				success : function success(data, textStatus, jqXHR) {
					if (data.codigoRetorno == 0){
						_this.listcodGrupos = data.listaRetorno;
					}else{
						msgCCR.erro('Ocorreu um erro na execu&ccedil;&atilde;o do comando!<br /><br />' + data.mensagemRetorno);
					}
					loadCCR.stop();
				},
				error : function error(jqXHR, textStatus, errorThrown) {
					loadCCR.stop();
					console.log('Carrega combo gCarregarGrupoTaxa', jqXHR);
					msgCCR.erro('Ocorreu um erro na execu&ccedil;&atilde;o do comando!<br /><br />' + jqXHR.responseText);
				}
			});
						
						
			return _this;
		},
		
		getCodListGrupo : function(idGrupo) {
			
			for (i = 0; i < _this.listcodGrupos.length; i++) {
				if(_this.listcodGrupos[i].id == idGrupo){
						return _this.listcodGrupos[i].descricao;
				}
			}//return cod grupoTaxa
		},
		
		getIdListGrupo : function(codGrupo) {
			
			for (i = 0; i < _this.listcodGrupos.length; i++) {
				if(_this.listcodGrupos[i].descricao == codGrupo){
					return _this.listcodGrupos[i].id;
				}
			}//return cod grupoTaxa
		},
		
		consultarTaxaJuros : function() {
			console.log("Filtro Taxa - consultarTaxaJuros");
			
			_this.validator.withForm('formCadastroJuros').cleanErrors();	
			_this.validator.withForm('formFiltroTaxa').cleanErrors();
			_this.validator.withForm('cabecalhoFiltroTaxa').cleanErrors();
			_this.validator.withForm('divListaTaxaJuros').cleanErrors();
			//_this.$el.find('#idGrupo').attr('validators', 'required');
			
			var identificador = _this.$el.find('input[name="identificador"]:checked').val();
			
			if (identificador == "CONVENIO") {
				_this.$el.find('#codigoConvenio').attr('validators', 'required');
			}
						
			var el = _this.$el.find('#codigoConvenioShow');	
			
			if (_this.validator.withForm('formFiltroTaxa').validate()){
				loadCCR.start();
				
				
				var utilizar = _this.$el.find('input[name="utilizar"]:checked').val();
				
				_this.getCollection().tipoConsulta = identificador;
				_this.getCollection().codigoTaxa = _this.$el.find('#idGrupo').val();
				_this.getCollection().utilizarEm = utilizar;
				_this.getCollection().dataInicial= _this.$el.find('#inicioVigenciaFiltro').val();
				_this.getCollection().dataFinal= _this.$el.find('#finalVigenciaFiltro').val();
				_this.getCollection().convenio= _this.$el.find('#codigoConvenio').val();

				
				// lista taxas			
				_this.getCollection().listar()
				.done(function sucesso(data) {
					
					loadCCR.stop();
					_this.$el.find('#divListaTaxaJurosShow').removeClass("hidden");
					
					// verifica Retorno
					Retorno.trataRetorno(data, 'manterJuros', null, false);
					
					if (data.listaRetorno != null) {
						_this.$el.find('#divListaTaxaJuros').html(_this.filtroTaxaTemplate({TaxaJuro: data.listaRetorno}));
					} else {
						_this.$el.find('#divListaTaxaJuros').html(_this.filtroTaxaTemplate({TaxaJuro: []}));
					}					
					
					// configura datatable
					_this.$el.find('#gridListaTaxaJuros').dataTable({
						'aoColumns' : [ null, null, null, null, null, null, null, null, null,{ "bSortable": false } ],
						'aaSorting': [],
						//'oLanguage' : {'sEmptyTable' : 'Nenhum registro encontrado.', 'sLengthMenu': '_MENU_ registros por página'}
						'oLanguage' : {'sEmptyTable' : 'Nenhum registro encontrado.', 'sLengthMenu': '_MENU_ registros por página', 'sInfoFiltered' : '(Filtrado _MAX_ do total de entradas)'},
						//'sInfoFiltered' : '(Filtrado _MAX_ do total de entradas)'
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
						
						$('#gridListaTaxaJuros').find('th').last().hide();
						$('#gridListaTaxaJuros').find('td').last().hide();
					}
					
				})
				.fail(function erro(jqXHR) {
					console.log(jqXHR);
					Retorno.trataRetorno({codigo: -1, tipo: "ERRO_EXCECAO", mensagem: "Ocorreu um erro ao listar as taxas de Juros cadastradas!"});
					loadCCR.stop();
				});
				
				return _this;
			}
								
		},
		
		validaConvenio: function (idConvenio) {
			
			console.log("Filtro Taxa - validaConvenio");	
			
			//Chamada do serviço consultar convenio			
			_this.modelo = new Convenio();			
			loadCCR.start();
			_this.getCollection().listar(1, idConvenio, null, null, null, null, null)
			.done(function (data) 
				{					
					if (data.listaRetorno == null || data.listaRetorno.length == 0)
						loadCCR.stop();
						msgCCR.erro('Convênio não encontrado!<br/>' + data.mensagemRetorno);					
					
				})
				.fail(function (jqXHR) {
					loadCCR.stop();
					Retorno.trataRetorno({codigo: -1, tipo: "ERRO_EXCECAO", mensagem: "Ocorreu um erro ao validar o convenio!"}, 'convenio', jqXHR);
				});
		},
			
		getCollection: function () {
	    	if (_this.collection == null || this.collection == undefined)
	    		_this.collection = new TaxaJurosCollection();
	    		
	    	return _this.collection;
	    },
	    
	    changed : function (e) {
	    	console.log('changed', e);
	    	//_this.modelo.set(e.target.name,e.target.value);
	    	//_this.getCollection().set(_this.modelo, {remove: false});
		},
		
		novaTaxa: function () {						
			
			console.log("Filtro Taxa - novaTaxa");			
			_this.limparFormInclusao();
			
			$('#convenioMessage').addClass("hide");
			
			//new
			_this.modelo = new TaxaJurosModel();
			
			//Seta as propriedades padrões dos campos titulo e data de inicio da vigência
			_this.$el.find('#divTituloForm').html('&nbsp;Incluir Taxa Juros');
			_this.$el.find('#inicioVigencia').prop('disabled', false).val('');
			_this.$el.find('#divFormulario #spanIconDataVigencia').prop('class', 'add-on');
			
			//campos novos
			//habilita/desabilita campos...
			//_this.$el.find('#codigoTaxa').prop('disabled', true);
			_this.$el.find('#codigoTaxa').prop('disabled', true).val('');			
			_this.$el.find('#inicioVigencia').prop('disabled', false);
			_this.$el.find('#prazoMin').prop('disabled', false);
			_this.$el.find('#prazoMax').prop('disabled', false);
			_this.$el.find('#pcMinimo').prop('disabled', false);
			_this.$el.find('#pcMedio').prop('disabled', false);
			_this.$el.find('#pcMaximo').prop('disabled', false);
			_this.$el.find('#utilizarEm').prop('disabled', false);
			_this.$el.find('#idGrupoIncluir').prop('disabled', false);
			
			//Carrega combos GrupoTaxa
			gCarregarGrupoTaxa(_this.$el, 'idGrupoIncluir');			
						
			_this.$el.find('#divFormulario').modal('show');
			_this.comando = 1; // novo			
		},
		
		limparFormInclusao: function() {			
			_this.$el.find('#prazoMin').prop('disabled', false).val('');
			_this.$el.find('#prazoMax').prop('disabled', false).val('');
			_this.$el.find('#pcMinimo').prop('disabled', false).val('');
			_this.$el.find('#pcMedio').prop('disabled', false).val('');
			_this.$el.find('#pcMaximo').prop('disabled', false).val('');	
		},
		
		alterarTaxa: function (evt) {
			
			//_this.limparForm();
			
			var id = _this.$el.find(evt.currentTarget).data('idtaxa');
			_this.modelo = _this.getCollection().get(id);
			var val = _this.modelo.attributes.codigoTaxa;
			
			// muda o título do modal de 'Incluir Taxa Juros' para 'Alterar Taxa Juros'
			_this.$el.find('#divTituloForm').html('&nbsp;Alterar Taxa Juros');
			
			//Carrega combo Grupo Taxa			
			if (_this.getCollection().tipoConsulta == "GRUPO") {
				gCarregarGrupoTaxa(_this.$el, 'idGrupoIncluir', _this.modelo.get('codigoTaxa'));
				//_this.$el.find('#codigoTaxa').prop('disabled', true);
				_this.$el.find('#codigoTaxa').prop('disabled', true).val('');
				//_this.$el.find('#codigoTaxa').prop('disabled', true).val(_this.modelo.get('codigoTaxa'));
			}else {
				
				//pega nome convenio
				 var nomeConvenio = "";
				 var convModel = new Convenio();
				 convModel.consultarConvenio(val).done(function sucesso(data) {
					//$('#nomeConvenio').val(data.nome);
					//nomeConvenio = data.nome;
					// _this.$el.find('#nomeConvenio').prop('disabled').val(data.nome);
					$('#nomeConvenio').val(data.nome);
					
				})
				.error(function erro(jqXHR) {
					// (Alert("Errro"));
					msgCCR.alerta("Funcionalidade Indísponivel!!!", function () {});
				});
				 
				var valorCod = _this.getIdListGrupo(999);
				//gCarregarGrupoTaxa(_this.$el, 'idGrupoIncluir', 999); //Banda Matriz
				gCarregarGrupoTaxa(_this.$el, 'idGrupoIncluir', valorCod); //Banda Matriz
				
				_this.$el.find('#codigoTaxa').prop('disabled', true).val(_this.modelo.get('codigoTaxa'));
			}	
			
			// pega a data de inicio vigencia (chave) para o caso do usuario alterar esta data 
			_this.inicioVigenciaChave = _this.modelo.get('inicioVigencia');
			_this.prazoMinOld = _this.modelo.get('prazoMin');
			_this.prazoMaxOld = _this.modelo.get('prazoMax');
			
			_this.modelo.set({
				inicioVigencia: formatadores.formatarData(_this.modelo.get('inicioVigencia')),
				fimVigencia: formatadores.formatarData(_this.modelo.get('fimVigencia')),
				pcMinimo:formatadores.formatarTaxa(_this.modelo.get('pcMinimo'), 5),
				pcMedio:formatadores.formatarTaxa(_this.modelo.get('pcMedio'), 5),
				pcMaximo:formatadores.formatarTaxa(_this.modelo.get('pcMaximo'), 5)
			});
			
			//manipula dados da tela
			_this.$el.find('#inicioVigencia').prop('disabled', true).val(_this.modelo.get('inicioVigencia')).attr('validators', '');
			_this.$el.find('#divFormulario #spanIconDataVigencia').prop('class', 'hidden');
			
			_this.$el.find('#fimVigencia').val(_this.modelo.get('fimVigencia'));
			_this.$el.find('#prazoMin').prop('disabled', false).val(_this.modelo.get('prazoMin'));
			_this.$el.find('#prazoMax').prop('disabled', false).val(_this.modelo.get('prazoMax'));
			_this.$el.find('#pcMinimo').prop('disabled', false).val(_this.modelo.get('pcMinimo'));
			_this.$el.find('#pcMedio').prop('disabled', false).val( _this.modelo.get('pcMedio'));
			_this.$el.find('#pcMaximo').prop('disabled', false).val(_this.modelo.get('pcMaximo'));			
			_this.$el.find('#idGrupoIncluir').prop('disabled', true);			
			_this.$el.find('#utilizarEm').prop('disabled', true).val(_this.modelo.get('tipoTaxa'));
			_this.$el.find('#nomeConvenio').prop('disabled', true).val(nomeConvenio);
			

			if(_this.modelo.attributes.dataInclusaoFimVigencia == null){
				_this.$el.find('#prazoMin').prop('disabled', false);
				_this.$el.find('#prazoMax').prop('disabled', false);
			}else{
				_this.$el.find('#prazoMin').prop('disabled', true);
				_this.$el.find('#prazoMax').prop('disabled', true);
			}
			
			
			_this.$el.find('#divFormulario').modal('show');
			_this.comando = 2; // alterar
		},
		
		salvar: function () {
			
			console.log("Filtro Taxa - salvar");
			
			var consultaSeguro = {};
			
			if (_this.validator.withForm('formCadastroJuros').validate()){
				
					_this.$el.find('#divFormulario').modal('hide');
					loadCCR.start();	
					
					var tipoConsulta, codigoTaxa, utilizarEm;
					
					// pega a data de inicio vigencia (chave) para o caso do usuario alterar esta data 
					//_this.inicioVigenciaChave = _this.modelo.get('inicioVigencia');
					_this.inicioVigenciaChave = $('#inicioVigencia').val();
					
					//seta o valor da combo utilizarEm - 1 contratacao 2 renovacao
					var utilizarEm = _this.$el.find('input[name="utilizarEmIncluir"]:checked').val();
					
					 	if(utilizarEm=="contratacao") {
							_this.modelo.attributes.tipoTaxa = 1;
						}else{
							_this.modelo.attributes.tipoTaxa = 2;
						}
					
					
					if ($('#codigoTaxa').val() != "") {
						tipoConsulta = "CONVENIO";
						codigoTaxa = $('#codigoTaxa').val();
						
						_this.modelo.attributes.tipoConsulta = tipoConsulta;
						_this.modelo.attributes.codigoTaxa = codigoTaxa;
						
						
						//Metodo que valida se o convenio informado existe //Aguardando caso de Uso Manter Convenio
						//_this.validaConvenio(codigoTaxa);
						
					} else {
						tipoConsulta = "GRUPO";
						codigoTaxa = $('#idGrupoIncluir').val();
						
						_this.modelo.attributes.tipoConsulta = tipoConsulta;
						_this.modelo.attributes.codigoTaxa = codigoTaxa;
						
					}
					
					_this.modelo.attributes.prazoMinOld = _this.prazoMinOld;
					_this.modelo.attributes.prazoMaxOld = _this.prazoMaxOld;
					
					_this.modelo.attributes.inicioVigencia =  $("#inicioVigencia").val();
					_this.modelo.attributes.pcMinimo =  $("#pcMinimo").val();
					_this.modelo.attributes.pcMedio =  $("#pcMedio").val();
					_this.modelo.attributes.pcMaximo =  $("#pcMaximo").val();
					_this.modelo.attributes.prazoMin =  $("#prazoMin").val();
					_this.modelo.attributes.prazoMax =  $("#prazoMax").val();
					
					msgCCR.confirma(EMensagemCCR.manterJuros.MA0046, function() {
						_this.modelo.salvar(_this.inicioVigenciaChave, consultaSeguro)
						.done(function sucesso(data){
							Retorno.trataRetorno(data, 'manterJuros');					
							_this.atualizar();					
						})
						.fail(function erro(jqXHR){
							Retorno.trataRetorno({codigo: -1, tipo: "ERRO_EXCECAO", mensagem: "Ocorreu um erro ao salvar a taxa de Juros!"}, 'manterJuros', jqXHR);					
							_this.atualizar();
						});	
						loadCCR.stop();
					});				
					
					_this.$el.find('#idGrupo').prop('disabled', false).attr('validators', '');
					_this.$el.find('#codigoConvenio').prop('disabled', true).attr('validators', '');
			}
			
		},
		
		limparFiltro: function() {
			_this.$el.find('#divListaTaxaJurosShow').addClass("hidden");
			_this.limparForm();
		},
		
		limparForm: function() {
			
			_this.validator.withForm('formCadastroJuros').cleanErrors();
			
			// habilita todos os campos.
			_this.$el.find('#inicioVigencia').prop('disabled', false).attr('validators', 'required,data,dataMenorTaxaSeguroIOF');
			_this.$el.find('#prazoMin').prop('disabled', false);
			_this.$el.find('#prazoMax').prop('disabled', false);
			_this.$el.find('#pcMinimo').prop('disabled', false);
			_this.$el.find('#pcMedio').prop('disabled', false);
			_this.$el.find('#pcMaximo').prop('disabled', false);
			_this.$el.find('#idGrupo').prop('disabled', false);
			
			_this.$el.find('#formCadastroJuros')[0].reset();
			_this.$el.find('#formFiltroTaxa')[0].reset();
			_this.validator.withForm('formFiltroTaxa').cleanErrors();
			//_this.$el.find('#codigoConvenio').prop('disabled', true);
			this.$('#codigoConvenioShow').hide('fast');
		},
		
		cancelar: function () {						
			_this.limparForm();
			_this.$el.find('#divFormulario').modal('hide');
		},
		
		remover: function (evt) {
			var id = _this.$el.find(evt.currentTarget).data('idtaxa');
			_this.modelo = _this.getCollection().get(id);
			
			msgCCR.confirma(EMensagemCCR.manterJuros.MA003, function() {
				loadCCR.start();
				
				_this.modelo.excluir()
				.done(function sucesso(data){
					loadCCR.stop();
					if (Retorno.trataRetorno(data, 'manterJuros')) {
						_this.modelo.clear().stopListening;
						_this.getCollection().remove(id);								
					}
					
					_this.atualizar();
				})
				.fail(function erro (jqXHR) {					
					Retorno.trataRetorno({codigo: -1, tipo: "ERRO_EXCECAO", mensagem: "Ocorreu um erro ao excluir a taxa de Juros!"}, 'manterJuros', jqXHR);
					_this.atualizar();
				});
			});
		},
		
		hangedSelectsConvenio : function (e) {
			var value = e.target.value;
			
			alert(value);
			
		},
		changedSelectsConvenio : function (e) {
			
			console.log("Manter Juros - changedSelectsConvenio");
			
	    	_this = this;
			var value = e.target.value;
			var selectOption;
			
			selectOption = _this.$el.find('#idGrupo').find(':selected').text();
			
			var cod = _this.getCodListGrupo(value);
			
			
			if (cod != "") {
				// 999 é o id da Banda Matriz na combo idGrupo - tabela CCRTB002_GRUPO_TAXA coluna CCRTB002_GRUPO_TAXA
		    	if (cod == '999'){
		    		_this.$el.find('#codigoConvenio').prop('disabled', false).attr('validators', 'required');
		    		//referente a tela de consultar taxa juros
		    		_this.$el.find('#codigoConvenio').attr('validators', 'required');
		    		//referente a tela de incluir taxa juros
		    		_this.$el.find('#codigoTaxa').prop('disabled', true).attr('validators', 'required');
		    		$('#codigoConvenioShow').show('fast');
		    		_this.$el.find('input[name="identificador"][value=CONVENIO]').prop('checked', true);	
		    		
		    	}else {
		    		_this.$el.find('input[name="identificador"][value=GRUPO]').prop('checked', true);
		    		
		    		$('#codigoConvenioShow').hide('fast');
		    		//referente a tela de consultar taxa juros
		    		_this.$el.find('#codigoConvenio').val('');
		    		_this.$el.find('#codigoConvenio').attr('validators', '');
		    		//referente a tela de incluir taxa juros
		    		_this.$el.find('#codigoTaxa').prop('disabled', true).val('');
		    		_this.$el.find('#codigoTaxa').prop('disabled', true).attr('validators', '');
		    	}
			}else {
				//referente a tela de consultar taxa juros
				_this.$el.find('#codigoConvenio').prop('disabled', true).val('');
	    		_this.$el.find('#codigoConvenio').prop('disabled', true).attr('validators', '');
	    		//referente a tela de incluir taxa juros
	    		_this.$el.find('#codigoTaxa').prop('disabled', true).val('');
	    		_this.$el.find('#codigoTaxa').prop('disabled', true).attr('validators', '');
			}		
		},
		
		sair: function () {
			loadCCR.start();
			console.log("saindo do CCR...");
			window.location = 'index.html';
		},
		
		mostrarCamposPorTipoPesquisa : function mostrarCamposPorTipoPesquisa(evt){
			
			var vlrTipoPesquisa = evt.target.value;
			_this.validator.withForm('formFiltroTaxa').cleanErrors();

			
			
			this.$('#codigoConvenioShow').hide('fast');
			
			if(vlrTipoPesquisa=="CONVENIO"){
				this.$el.find('#codigoConvenioShow').prop('disabled', false).attr('validators', 'required');
				this.$('#codigoConvenioShow').show('fast');
				this.$el.find('#codigoConvenio').attr('disabled', false)
				
				
				//this.$el.find('#idGrupo').prop('disabled', true).val('999');
				var valorCod = _this.getIdListGrupo('999');
				this.$el.find('#idGrupo').prop('disabled', true).val(valorCod);
				_this.$el.find('#idGrupo').attr('validators', '');

			}
			if(vlrTipoPesquisa=="GRUPO"){
				this.$el.find('#codigoConvenioShow').prop('disabled', true).attr('validators', '');
				this.$el.find('#idGrupo').prop('disabled', false).val('');

				
			}
			
		},
		
		atualizar: function(){
			
			_this.consultarTaxaJuros();
			$('ul.nav a#manterTaxaSeguro.links-menu').trigger('click');
		}		
		
	});

	return ManterJurosControle;
	
});