/**
 * @author F620600
 * 
 * JavaScript que controla as ações das paginas:
 *  consultaAuditoria.html
 *  
 * @version 1.0.0.0
 * 
 * 
 */
define(['enumeracoes/eMensagemCCR',
        'enumeracoes/eTipoServicos',
        'util/retorno',
        'modelo/auditoria/auditoria',
        'modelo/auditoria/auditoriaColecao',
        'text!visao/auditoria/consultaAuditoria.html',
        'text!visao/auditoria/consultaAuditoriaLista.html',
        'text!visao/auditoria/consultaAuditoriaModal.html'
        ], 
function(EMensagemCCR, ETipoServicos, Retorno, Auditoria, AuditoriaCollection, consultaAuditoriaTpl, auditoriaListaTpl, auditoriaModalTpl){

	var _this = null;
	var ConsultaAuditoriaControle = Backbone.View.extend({

		className: 'ConsultaAuditoriaControle',
		
		/**
		 * Templates
		 */
		consultaAuditoriaTemplate : _.template(consultaAuditoriaTpl),
		auditoriaListaTemplate  : _.template(auditoriaListaTpl),
		auditoriaModalTemplate  : _.template(auditoriaModalTpl),
		
		/**
		 * Variaveis da classe
		 */
		validator   : new Validators(),
		message     : new Message(),
		comando     : null,
		modelo      : null,
		collection  : null,
		listHistorico: [],
		retorno : {},
		aoColumns : [],
		aoColumnDefs : [],
		qtdTotalRegistros : '',
		
		aoColumnsContrato : [
		    {
		    	'sTitle' : 'Data / Hora',
				'mData' : 'dhTransacaoAuditada',
				'bSortable': false
		    },{
		    	'sTitle' : 'Usuário',
				'mData' : 'coUsuarioAuditado',
				'bSortable': false
		    },{
		    	'sTitle' : 'Transação',
				'mData' : 'transacaoAuditada',
				'bSortable': false
		    },{
		    	'sTitle' : 'Tipo',
				'mData' : 'icTipoAcao',
				'bSortable': false
		    },{
		    	'sTitle' : 'Campo',
				'mData' : 'noCampoAuditado',
				'bSortable': false
		    }
		],	
		
		aoColumnDefsContrato : [{
			'aTargets' : [ 0 ],
			'sWidth' : '20%',
			'mRender' : function(data, type, row) {
                var retorno = '';
                
                if(row.dhTransacaoAuditada != null){
                	var date = new Date(row.dhTransacaoAuditada);
                	retorno = formatDate(date, 'dd/MM/yyyy HH:mm:ss');
                }
                return retorno;
           }
			
		},{
			'aTargets' : [ 1 ],
			'sWidth' : '20%'
		},{
			'aTargets' : [ 2 ],
			'sWidth' : '20%',
			
		},{
			'aTargets' : [ 2 ],
			'sWidth' : '20%',
			
		},{
			'aTargets' : [ 4 ],
			'sWidth' : '20%',
		}
		],
		
		aoColumnsConvenio : [
		    {
		    	'sTitle' : 'Data / Hora',
				'mData' :  'dhTransacaoAuditada',
				'bSortable': false
		    },{
		    	'sTitle' : 'Usuário',
				'mData' : 'coUsuarioAuditado',
				'bSortable': false
		    },{
		    	'sTitle' : 'Transação',
				'mData' : 'transacaoAuditada',
				'bSortable': false
		    },{
		    	'sTitle' : 'Tipo',
				'mData' : 'icTipoAcao',
				'bSortable': false
		    },{
		    	'sTitle' : 'Campo',
				'mData' : 'noCampoAuditado',
				'bSortable': false
		    },{
		    	'sTitle' : 'Anterior',
				'mData' : 'deValorAnterior',
				'bSortable': false
		    },{
		    	'sTitle' : 'Atual',
				'mData' : 'deValorAtual',
				'bSortable': false
		    },{
		    	'sTitle' : 'Convenete',
				'mData' : 'nuConvenio',
				'bSortable': false
		    },{
		    	'sTitle' : 'Canal',
				'mData' : 'nuCanal',
				'bSortable': false
		    }
		],	
		
		aoColumnDefsConvenio : [{
			'aTargets' : [ 0 ],
			'sWidth' : '12%',
			'mRender' : function(data, type, row) {
                var retorno = '';
                
                if(row.dhTransacaoAuditada != null){
                	var date = new Date(row.dhTransacaoAuditada);
                	retorno = formatDate(date, 'dd/MM/yyyy HH:mm:ss');
                }
                return retorno;
           }
			
		},{
			'aTargets' : [ 1 ],
			'sWidth' : '12%'
		},{
			'aTargets' : [ 2 ],
			'sWidth' : '12%',
		},{
			'aTargets' : [ 3 ],
			'sWidth' : '12%',
		},{
			'aTargets' : [ 4 ],
			'sWidth' : '12%',
		},{
			'aTargets' : [ 5 ],
			'sWidth' : '12%',
			'mRender' : function(data, type, row) {
					return _this.renderListaValorAnterior(row);
				}
		},{
			'aTargets' : [ 6 ],
			'sWidth' : '12%',
			'mRender' : function(data, type, row) {
				return _this.renderListaValorAtual(row);
			}
		},{
			'aTargets' : [ 7 ],
			'sWidth' : '12%',
			'mRender' : function(data, type, row) {
				var retorno = '';
				if(row.nuConvenio != null){
					retorno = formatadores.formatarCNPJ(row.nuConvenio.cnpjConvenente);
				}
				return retorno;
			},
		},{
			'aTargets' : [ 8 ],
			'sWidth' : '12%',
			'mRender' : function(data, type, row) {
				var retorno = '';
				if(row.nuCanal.id != null){
					retorno = row.nuCanal.id + ' - ' + row.nuCanal.nome;
				}
				return retorno;
			},
		}],
		
		aoColumnsCpf : [
		    {
		    	'sTitle' : 'Data / Hora',
				'mData' : 'dhTransacaoAuditada',
				'bSortable': false
		    },{
		    	'sTitle' : 'Usuário',
				'mData' : 'coUsuarioAuditado',
				'bSortable': false
		    },{
		    	'sTitle' : 'Transação',
				'mData' : 'transacaoAuditada',
				'bSortable': false
		    },{
		    	'sTitle' : 'Tipo',
				'mData' : 'icTipoAcao',
				'bSortable': false
		    },{
		    	'sTitle' : 'Campo',
				'mData' : 'noCampoAuditado',
				'bSortable': false
		    },{
		    	'sTitle' : 'Contrato',
				'mData' : 'nuContrato.nuContrato',
				'bSortable': false
		    }
		],	
		
		aoColumnDefsCpf : [{
			'aTargets' : [ 0 ],
			'sWidth' : '17%',
			'mRender' : function(data, type, row) {
                var retorno = '';
                
                if(row.dhTransacaoAuditada != null){
                	var date = new Date(row.dhTransacaoAuditada);
                	retorno = formatDate(date, 'dd/MM/yyyy HH:mm:ss');
                }
                return retorno;
           },
			
		},{
			'aTargets' : [ 1 ],
			'sWidth' : '17%'
		},{
			'aTargets' : [ 2 ],
			'sWidth' : '17%',
		},{
			'aTargets' : [ 3 ],
			'sWidth' : '17%',
		},{
			'aTargets' : [ 4 ],
			'sWidth' : '17%',
		},{
			'aTargets' : [ 5 ],
			'sWidth' : '17%',
			'mRender' : function(data, type, row) {
				var retorno = '';
				if(row.nuContrato != null){
					retorno = row.nuContrato.nuContrato;
				}
				return retorno;
			},
		}
		],
		
		aoColumnsDefault : [
		    {
		    	'sTitle' : 'Data / Hora',
				'mData' : 'dhTransacaoAuditada',
				'bSortable': false
		    },{
		    	'sTitle' : 'Usuário',
				'mData' : 'coUsuarioAuditado',
				'bSortable': false
		    },{
		    	'sTitle' : 'Transação',
				'mData' : 'transacaoAuditada',
				'bSortable': false
		    },{
		    	'sTitle' : 'Tipo',
				'mData' : 'icTipoAcao',
				'bSortable': false
		    },{
		    	'sTitle' : 'Campo',
				'mData' : 'noCampoAuditado',
				'bSortable': false
		    },{
		    	'sTitle' : 'Anterior',
				'mData' : 'deValorAnterior',
				'bSortable': false
		    },{
		    	'sTitle' : 'Atual',
				'mData' : 'deValorAtual',
				'bSortable': false
		    },{
		    	'sTitle' : 'Convênio',
				'mData' : 'nuConvenio',
				'bSortable': false
		    },{
		    	'sTitle' : 'Canal',
				'mData' : 'nuCanal',
				'bSortable': false
		    }
		],	
		
		aoColumnDefsDefault : [{
			'aTargets' : [ 0 ],
			'sWidth' : '12%',
			'mRender' : function(data, type, row) {
                var retorno = '';
                
                if(row.dhTransacaoAuditada != null){
                	var date = new Date(row.dhTransacaoAuditada);
                	retorno = formatDate(date, 'dd/MM/yyyy HH:mm:ss');
                }
                return retorno;
           },
		},{
			'aTargets' : [ 1 ],
			'sWidth' : '10%'
		},{
			'aTargets' : [ 2 ],
			'sWidth' : '12%',
			
		},{
			'aTargets' : [ 3 ],
			'sWidth' : '10%',
			
		},{
			'aTargets' : [ 4 ],
			'sWidth' : '12%',
		},{
			'aTargets' : [ 5 ],
			'sWidth' : '12%',
			'mRender' : function(data, type, row) {
				return _this.renderListaValorAnterior(row);
			}
		},{
			'aTargets' : [ 6 ],
			'sWidth' : '12%',
			'mRender' : function(data, type, row) {
				return _this.renderListaValorAtual(row);
			}
		},{
			'aTargets' : [ 7 ],
			'sWidth' : '10%',
			'mRender' : function(data, type, row) {
				var retorno = '';
				if(row.nuConvenio != null){
					retorno = row.nuConvenio.id;
				}
				return retorno;
			},
		},{
			'aTargets' : [ 8 ],
			'sWidth' : '18%',
			'mRender' : function(data, type, row) {
				var retorno = '';
				if(row.nuCanal.id != null){
					retorno = row.nuCanal.id + ' - ' + row.nuCanal.nome;
				}
				return retorno;
			},
		}],
		
		
		/**
		 * Função que faz bind das ações e interações da pagina com as funções
		 * javascript
		 * 
		 */
		events : {
			"change input[name='rdoIdentificador']"	:"redefineTelaFiltro",
			'click a#btnConsultar'					: 'consultar',
			'click a#btnLimparForm'					: 'limpar',
			'click a#btnFechar'     				: 'fechar',
			'click .camposLista'     				: 'camposListaAbrirPopup',
			'click #btnVoltarPopup'					: 'fecharPopup'
		},
		
		renderListaValorAtual : function(row){
			var retorno = "";
			if (row.campoId == 7){
				retorno = "<a class='btn-link camposLista' campo="+row.campoId+" atual="+row.deValorAtual+" anterior="+row.deValorAnterior+">SR</a>"
			}else if (row.campoId == 18){
				retorno = "<a class='btn-link camposLista' campo="+row.campoId+" atual="+row.deValorAtual+" anterior="+row.deValorAnterior+">CNPJ</a>"
			} else if (row.campoId == 6){
				retorno = "<a class='btn-link camposLista' campo="+row.campoId+" atual="+row.deValorAtual+" anterior="+row.deValorAnterior+">UF</a>"
			} else if(row.deValorAtual != null){
				retorno = "<label>"+row.deValorAtual+"</label>";
			}
			return retorno;
		},
		
		renderListaValorAnterior : function(row){
			var retorno = "";
			if (row.campoId == 7){
				retorno = "<a class='btn-link camposLista' campo="+row.campoId+" atual="+row.deValorAtual+" anterior="+row.deValorAnterior+">SR</a>"
			}else if (row.campoId == 18){
				retorno = "<a class='btn-link camposLista' campo="+row.campoId+" atual="+row.deValorAtual+" anterior="+row.deValorAnterior+">CNPJ</a>"
			} else if (row.campoId == 6){
				retorno = "<a class='btn-link camposLista' campo="+row.campoId+" atual="+row.deValorAtual+" anterior="+row.deValorAnterior+">UF</a>"
			}else if(row.deValorAnterior != null){
				retorno = "<label>"+row.deValorAnterior+"</label>";
			}
			return retorno;
		},

				
		
		/**
		 * Função padrão de incialização do template html
		 * 
		 */
		initialize : function() {
			console.log("Consulta Auditoria controle - initialize");
			
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
			console.log("Consultar Auditoria - render");	
			
			//Inclui o template inicial no el deste controle
			_this.$el.html(_this.consultaAuditoriaTemplate());
			
			//Carrega as mascaras usadas.
			loadMaskEl(_this.$el);
			listenToDatepickerChange(_this.$el, _this.changed);
			gCarregarTransacao(_this.$el, "select_transacao", null, '5');
			gCarregarTransacao(_this.$el, "select_transacaoPrincipal", null, '5');
			
			_this.$el.find("#radioContrato").prop('checked', true);
			
			return _this;
		},
		
		getCollection: function () {
	    	if (_this.collection == null || this.collection == undefined)
	    		_this.collection = new AuditoriaCollection();
	    		
	    	return _this.collection;
	    },

	    getModelo: function () {
	    	if (_this.modelo == null || this.modelo == undefined)
	    		_this.modelo = new Auditoria();
	    		
	    	return _this.modelo;
	    },
	    

		
		consultar: function () {
			console.log("Auditoria - consultar");	
			
			if (_this.validator.withForm('formFiltroAuditoria').validate()){
				loadCCR.start();
				
				var opIdentificador = _this.$el.find('input[name="rdoIdentificador"]:checked').val();
				var consulta ={};
				
				if(_this.$el.find('#contrato').val() != ""){
					consulta.nuContrato = _this.$el.find('#contrato').val();
				}
				if(_this.$el.find('#cpf').val() != ""){
					var cpf = String(_this.$el.find('#cpf').val()).replace(/[.\-\/]/g, '');
					consulta.cpf = cpf;
				}
				if(_this.$el.find('#cnpj').val() != ""){
					var cnpj = String(_this.$el.find('#cnpj').val()).replace(/[.\-\/]/g, '');
					consulta.cnpj = cnpj;
				}
				if(_this.$el.find('#convenio').val() != ""){
					consulta.convenio = _this.$el.find('#convenio').val();
				}
				if(_this.$el.find('#convenioPrincipal').val() != ""){
					consulta.convenio = _this.$el.find('#convenioPrincipal').val();
				}
				if(_this.$el.find('#dataInicio').val() != ""){
					consulta.dtInicio = _this.$el.find('#dataInicio').val();
				}
				if(_this.$el.find('#dataFim').val() != ""){
					consulta.dtFim = _this.$el.find('#dataFim').val();
				}
				if(_this.$el.find('#usuarioPrincipal').val() != ""){
					consulta.usuario = _this.$el.find('#usuarioPrincipal').val();
				}
				if(_this.$el.find('#usuario').val() != ""){
					consulta.usuario = _this.$el.find('#usuario').val();
				}
				if(_this.$el.find('#select_transacao').val() != ""){
					consulta.transacao = _this.$el.find('#select_transacao').find(':selected').val();
				}
				if(_this.$el.find('#select_transacaoPrincipal').val() != ""){
					consulta.transacao = _this.$el.find('#select_transacaoPrincipal').find(':selected').val();
				}
				
				_this.$el.find('#divLista').html(_this.auditoriaListaTemplate());
				$('#divLista').removeClass('hidden');
				
				if(opIdentificador == 3 || opIdentificador == 5 || opIdentificador == 6){
					_this.aoColumns	   = _this.aoColumnsDefault;
					_this.aoColumnDefs = _this.aoColumnDefsDefault;
					_this.grid		   = '#gridListaDefault';
					$('#listaPorDefault').removeClass('hidden');
				}
				//Apresenta a lista referente a busca por contrato
				if(opIdentificador == 1){
					_this.aoColumns	   = _this.aoColumnsContrato;
					_this.aoColumnDefs = _this.aoColumnDefsContrato;
					_this.grid		   = '#gridListaContrato';
					$('#listaPorContrato').removeClass('hidden');
				}
				//Apresenta a lista referente a busca por cpf
				if(opIdentificador == 2){
					_this.aoColumns	   = _this.aoColumnsCpf;
					_this.aoColumnDefs = _this.aoColumnDefsCpf;
					_this.grid		   = '#gridListaCPF';
					$('#listaPorCPF').removeClass('hidden');
				}
				//Apresenta a lista referente a busca de convenio
				if(opIdentificador == 4){
					_this.aoColumns	   = _this.aoColumnsConvenio;
					_this.aoColumnDefs = _this.aoColumnDefsConvenio;
					_this.grid		   = '#gridListaConvenio';
					$('#listaPorConvenio').removeClass('hidden');
				}
				
				var url = window.location.host;
				
				_this.$el.find(_this.grid).dataTable({
					'bPaginate' : true,
					'bFilter': false,
					'aoColumns' : _this.aoColumns,
					'aoColumnDefs' : _this.aoColumnDefs,
					'oLanguage' : {'sEmptyTable' : 'Nenhum registro encontrado.', 'sLengthMenu': '_MENU_ registros por página', 'sInfoFiltered' : ''},
					'sAjaxSource': 'http://'+url+'/ccr-web/consignado/auditoria/lista',
					'sAjaxDataProp' : 'auditoria',
					'bServerSide' : true,
					'bProcessing' : true,
					'fnServerData' : function (sSource, aoData, fnCallback, oSettings){
						var paramMap = {};
			        	for ( var i = 0; i < aoData.length; i++) {
			        		paramMap[aoData[i].name] = aoData[i].value;
			        	}

			        	//page calculations
			        	var pageSize = paramMap.iDisplayLength;
			        	var start = paramMap.iDisplayStart;
			        	if(start == undefined){
			        		start = 0;
			        	}
			        	
			        	var backToFirstPage = false;
			        	
			        	if(consulta.sEcho > 1 && pageSize > 10) {
			        		start = 0;
			        		backToFirstPage = true;
			        	}
			        	
			        	var pageNum = (start == 0) ? 1 : (start / pageSize) + 1; // pageNum is 1 based
			        	var qtdTotalRegistros = that.qtdTotalRegistrosMF;
			        	
			            consulta.sEcho = pageNum;
			            consulta.iDisplayStart = start;
			        	consulta.iDisplayLength = pageSize;
			        	consulta.qtdTotalRegistros = qtdTotalRegistros;

						$.ajax({
							"dataType" : 'json',
							"type" : "GET",
							"contentType": "application/json",
							"url" :  sSource,
							"data" : consulta,
							"success" : function(data) {
								data.iTotalRecords = data.totalPaginas;
								data.iTotalDisplayRecords = data.totalRegistros;
								data.sEcho = data.paginaAtual;
//								data._iDisplayStart = 10;
								that.qtdTotalRegistrosMF = data.totalRegistros;
								oSettings.iDraw = 1;
								loadCCR.stop();
								
								if(backToFirstPage && pageSize > 10) {
									oSettings._iDisplayStart = 0;
								}
								
								
								fnCallback(data);
							},
					        'error': function (e) {
					        	loadCCR.stop();
					            console.log(e.message);
					            console.log(oSettings);
					            console.log(aoData);
					            console.log(consulta);	
					        }
						});
					},
				});
			}
		},
		
		limparCampos: function(){
			_this.$el.find('#divContrato').find('input').val('');		
			_this.$el.find('#select_transacaoPrincipal').val('');
			_this.$el.find('#divUsuarioPrincipal').find('input').val('');
			_this.$el.find('#divConvenioPrincipal').find('input').val('');
			_this.$el.find('#select_transacao').val('');
			_this.$el.find('#divConvenio').find('input').val('');
			_this.$el.find('#divCPF').find('input').val('');
			_this.$el.find('#divConvenente').find('input').val('');
			_this.$el.find('#divUsuario').find('input').val('');
			_this.$el.find('#dataInicio').val('');
			_this.$el.find('#dataFim').val('');
			
			_this.$el.find("#divTransacaoPrincipal").hide();
			_this.$el.find("#divUsuarioPrincipal").hide();
			_this.$el.find("#divConvenioPrincipal").hide();
			_this.$el.find('#divContrato').hide();
			_this.$el.find("#divConvenio").hide();
			_this.$el.find('#divConvenente').hide();
			_this.$el.find('#divTransacao').hide();
			_this.$el.find('#divCPF').hide();
			_this.$el.find("#divUsuario").hide();
			_this.$el.find("#divPeriodo").hide();
			
			
			_this.$el.find('#divLista').addClass("hidden");
		},
		
		redefineTelaFiltro : function() {
			
			console.log('redefineTelaFiltro - inicio');
			var opIdentificador = _this.$el.find('input[name="rdoIdentificador"]:checked').val();
			_this.limparCampos();
			$("#select_transacao option[value='3']").remove();

			switch (opIdentificador) {
			case '1': // radioContrato
				console.log('radioContrato selecionado...');
				
				// initFiltroContrato
				_this.$el.find("#divContrato").show();
				
				_this.validator.withForm('formFiltroAuditoria').cleanErrors();
				
				break;
			case '2': // radioCPF
				console.log('radioCPF selecionado...');
				
				// initFiltroCPF
				_this.$el.find("#divCPF").show();
				_this.$el.find("#divPeriodo").show();
				_this.$el.find("#divCPF").css('background-color', '#fff');
				
				_this.validator.withForm('formFiltroAuditoria').cleanErrors();
				
				break;				
			case '3': // radioConvenente
				console.log('radioConvenente selecionado...');
				
				// initFiltroConvenente
				_this.$el.find("#divConvenente").show();
				_this.$el.find("#divUsuario").show();
				_this.$el.find("#divUsuario").css('background-color', '#f4f4f4');
				
				_this.$el.find("#divTransacao").show();
				_this.$el.find("#divPeriodo").show();
				
				_this.validator.withForm('formFiltroAuditoria').cleanErrors();
				
				break;
			case '4': // radioConvenio
				console.log('radioConvenio selecionado...');
				
				// initFiltroConvenio
				_this.$el.find("#divConvenioPrincipal").show();
				_this.$el.find("#divConvenioPrincipal").css('background-color', '#fff');
				
				_this.$el.find("#divUsuario").show();
				_this.$el.find("#divUsuario").css('background-color', '#f4f4f4');
				
				_this.$el.find("#divTransacao").show();
				_this.$el.find("#divPeriodo").show();
				
				_this.validator.withForm('formFiltroAuditoria').cleanErrors();
				
				break;				
			case '5': // radioUsuario
				console.log('radioUsuario selecionado...');
				
				// initFiltroUsuario
				_this.$el.find("#divUsuarioPrincipal").show();
				_this.$el.find("#divConvenio").show();
				_this.$el.find("#divConvenio").css('background-color', '#f4f4f4');
				_this.$el.find("#divTransacao").show();
				_this.$el.find("#divPeriodo").show();
				
				_this.validator.withForm('formFiltroAuditoria').cleanErrors();
				
				break;				
			case '6': // radioTransacao
				console.log('radioTransacao selecionado...');
				
				// initFiltroTransacao
				_this.$el.find("#divTransacaoPrincipal").show();
				_this.$el.find("#divTransacaoPrincipal").css('background-color', '#fff');
				
				_this.$el.find("#divConvenio").show();
				_this.$el.find("#divUsuario").show();
				_this.$el.find("#divUsuario").css('background-color', '#fff');
				
				_this.$el.find("#divPeriodo").show();
				
				_this.validator.withForm('formFiltroAuditoria').cleanErrors();
				
				break;				
			default:
				break;
			}	
			console.log('redefineTelaFiltro - fim');
		},

		fechar: function () {
			_this.$el.find('#divDetalhe').modal('hide');
		},
		
		limpar: function () {
			_this.validator.withForm('formFiltroAuditoria').cleanErrors();
			$('#divLista').addClass('hidden');
			_this.$el.find('#divContrato').find('input').val('');		
			_this.$el.find('#select_transacaoPrincipal').val('');
			_this.$el.find('#divUsuarioPrincipal').find('input').val('');
			_this.$el.find('#divConvenioPrincipal').find('input').val('');
			_this.$el.find('#select_transacao').val('');
			_this.$el.find('#divConvenio').find('input').val('');
			_this.$el.find('#divCPF').find('input').val('');
			_this.$el.find('#divConvenente').find('input').val('');
			_this.$el.find('#divUsuario').find('input').val('');
			_this.$el.find('#dataInicio').val('');
			_this.$el.find('#dataFim').val('');
		},
		
		camposListaAbrirPopup : function(e){
			console.log("Consultar Auditoria - Popup Campos Lista");
			
			var anterior = e.target.attributes.anterior.value;
			var anteriorList = anterior.split(",");
			
			var atual = e.target.attributes.atual.value;
			var atualList = atual.split(",");

			var sizeAnterior = anteriorList.length;
			var sizeAtual = atualList.length;
			
			var size = sizeAtual;
			
			if(sizeAnterior >= sizeAtual){
				size = sizeAnterior
			}
			
			var list =[];
			var obj = {};
				
			for(var i = 0 ; size > i; i++){
				var atualParam = "";
				var anteriorParam = "";
				list[i] = {};
				
				if(sizeAtual>=i){
					list[i].atual = atualList[i];
				}else{
					list[i].atual = "";
				}
				if(sizeAnterior>=i){
					list[i].anterior = anteriorList[i]
				}else{
					list[i].anterior = ""
				}		
			}

			
			_this.$el.find('#divModalCamposLista').html(_this.auditoriaModalTemplate({lista:list, campo:e.target.attributes.campo.value}));
			_this.$el.find('#divModalCamposLista').modal('show'); 
		},
		
		fecharPopup : function() {
			_this.$el.find('#divModalCamposLista').modal('hide'); 
		},

	});

	return ConsultaAuditoriaControle;
	
});