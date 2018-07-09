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
        'util/retorno',
        'util/modelo/unidade',
        'modelo/contratacao/contratoColecao',
        'modelo/contratacao/contrato',
        'modelo/cliente/cliente',
        'text!visao/contratacao/manter/manterContrato.html',
        'text!visao/contratacao/manter/consultaContrato.html',
        'text!visao/contratacao/manter/consultaContratoLista.html',
        ], 
        
function(EMensagemCCR, ETipoServicos, Retorno, Unidade, ContratoColecao, Contrato, Cliente, autorizarContratoTpl, consultaContratoTpl, consutaContratoListaTpl){

	var _this = null;
	var ManterContratoControle = Backbone.View.extend({

		className: 'ManterContratoControle',
		
		/**
		 * Templates
		 */
		autorizarContratoTemplate : _.template(autorizarContratoTpl),
		consultaContratoTemplate : _.template(consultaContratoTpl),
		consultaContratoListaTemplate : _.template(consutaContratoListaTpl),
		modeloCliente : null,

		
		/**
		 * Variaveis da classe
		 */
		validator   : new Validators(),
		message     : new Message(),
		comando     : null,
		modelo		: null,
		collection  : null,
		acao		: null,
		modeloContrato : new Contrato(),
		dadosContrato : null,
		agenciaConcessora : null,
		unidade		: new Unidade(),
	
		/**
		 * Função que faz bind das ações e interações da pagina com as funções
		 * javascript
		 * 
		 */
		events : {
			"change input[name='rdoIdentificador']"	:"redefineTelaFiltro",
			"change #select_situacao"			  	: 'validarErroProcessamento',
			'click a#bntConsultarContrato' 			: 'consultarContrato',
			'click a#btnDetalharContrato' 			: 'detalhar',
			'click a#btnImprimir'					: 'contruirImpressao',
			'click a#bntLimpar'						: 'limparCampos',
			'click a#btnAutorizarContrato' 			: 'autorizar',
			'click a#btnAvaliarOperacaoContrato' 	: 'avaliarOperacao',
			'click a#btnAvaliarAutorizarContrato' 	: 'avaliar',
			'click a#btnCancelarContrato'  			: 'cancelar',
			'click a#btnVoltar' 		   			: 'voltar',
			'click a#btnSair'						: 'sair',
			'click a#btnConfirmarCancelamento' 		: 'confirmarCancelamento',
			'click a#btnVoltarCancelar' 	   		: 'voltarCancelar',
			'click a#btnImprimirContrato' 			: 'abrirImprimir',
			'click a#btnFecharImpressao'			: 'fecharImpressao',
		    'click a#bntNovoContrato'				: 'novoContrato',
			"focus input[name='cnpjConvenente']"	:"cnpjMask",			
			"focus input[name='CNPJText']"			:"cnpjMask",
			'click a#btnReenviarContrato' 	: 'reenviarContrato',
		},
		
		/**
		 * Função padrão de incialização do template html
		 * 
		 */
		initialize : function() {
			console.log("autorizar controle - initialize");
			
			// pra nao ter problema de pegar outro 'this'
			_this = this;
			_this.acao = _this.options.acao || 'T';
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
			console.log("autorizar - render");	
			// desabilita os botoes
			_this.$el.find('a.disabled').on('click', function(evt) {
				evt.preventDefault();
				return false;
			});	
			_this.$el.html(_this.autorizarContratoTemplate());
			_this.$el.find('#divFiltroContratoForm').html(_this.consultaContratoTemplate());
//			_this.consultarContrato();
//			_this.$el.find('input[name="rdoIdentificador"][value="1"]').prop('checked', true);
			_this.loadProfile();

			loadMaskEl(_this.$el);
			_this.$el.find('#divListaContratos').addClass('hidden');
			return _this;
			
		},
		
		loadProfile : function(id) {
			_this.modelo = new Contrato();
			_this.unidade.consultaUnidadePorNumeroUnidade(usuario.attributes.empregado.numeroUnidade);
			_this.agenciaConcessora = _this.unidade.attributes;
			
			_this.modelo.loadProfile().done(function sucesso(data) {
				_this.agenciaConcessora = _this.agenciaConcessora.idUnidade + " - "
				+ _this.agenciaConcessora.nomeUnidade;
				$("#agencia").text(_this.agenciaConcessora);
				_this.preparaTelaFiltro();
			})
			.error(function erro(jqXHR) {
				$("#agencia").text('');
			});
		
		},
		
		preparaTelaFiltro : function() {
			loadCCR.start();
			_this.$el.find('input[name="rdoIdentificador"][value="1"]').prop('checked', true);
			_this.initTelaFiltro();
			loadCCR.stop();
		},
		
		initTelaFiltro : function () {
			
			//mostrar cpf - situacao
			_this.$el.find('#divCampoCPF').show();
			_this.$el.find('#divCampoSituacao').show();
			
			document.getElementById("divCampoCPF").style.backgroundColor="white";
			document.getElementById("divCampoSituacao").style.backgroundColor="#f4f4f4";
			
//			var el = _this.$el.find('#divFiltroContratoForm');
			gCarregarSituacao(_this.$el, "select_situacao", null, '2');
															// NU_TIPO_SITUACAO
															// para todas as
															// situações no BD.

			
			/**
			 * Remove do Select de situação as que nunca aconteceram: 
			 * Autorização Contratação Aprovada
			 * Autorização Confirmação Alçada Aprovada
			 * Autorização Contratação Negada
             * Autorização Confirmação Alçada Negada
			 */ 
			$("#select_situacao option[value='3']").remove();
			$("#select_situacao option[value='4']").remove();
			$("#select_situacao option[value='5']").remove();
			$("#select_situacao option[value='6']").remove();
			
			//esconder
			_this.$el.find('#divCNPJConvenente').hide();		
			_this.$el.find('#divCampoContrato').hide();
			_this.$el.find('#divCampoPeriodo').hide();
			_this.$el.find('#divCampoSR').hide();
			_this.$el.find('#divCampoAgencia').hide();
			_this.$el.find('#divCampoCNPJ').hide();
			_this.$el.find('#divCampoConvenio').hide();
			_this.$el.find('#divFiltroErroProcessamento').hide();			
		},
		
		redefineTelaFiltro : function() {
			
			console.log('redefineTelaFiltro - inicio');
			var opIdentificador = _this.$el.find('input[name="rdoIdentificador"]:checked').val();
			_this.limparCampos();
			_this.$el.find('#obrigatorioPeriodo').hide();
			_this.$el.find('#divFiltroErroProcessamento').hide();

			switch (opIdentificador) {
			case '1': // radioCliente
				console.log('radioCliente selecionado...');
				_this.validator.withForm('formFiltroContrato').cleanErrors();
				_this.initTelaFiltro();
				
				break;
			case '2': // radioContrato
				console.log('radioContrato selecionado...');
				_this.validator.withForm('formFiltroContrato').cleanErrors();
				
				//mostrar 
				_this.$el.find('#divCampoContrato').show();
				document.getElementById("divCampoContrato").style.backgroundColor="white";
				
				//esconder
				_this.$el.find('#divCampoCPF').hide();
				_this.$el.find('#divCampoSituacao').hide();
				_this.$el.find('#divCNPJConvenente').hide();
				_this.$el.find('#divCampoPeriodo').hide();
				_this.$el.find('#divCampoSR').hide();
				_this.$el.find('#divCampoAgencia').hide();
				_this.$el.find('#divCampoCNPJ').hide();
				_this.$el.find('#divCampoConvenio').hide();
				_this.$el.find('#lblFiltrar').hide();
				
				
				
				break;				
			case '3': // radioSR
				console.log('radioSR selecionado...');
				_this.validator.withForm('formFiltroContrato').cleanErrors();
				
				//mostrar 
				_this.$el.find('#divCampoSR').show();
				_this.$el.find('#divCampoSituacao').show();
				_this.$el.find('#divCampoPeriodo').show();
				_this.$el.find('#lblFiltrar').show();
				
				
				
				document.getElementById("divCampoSR").style.backgroundColor="white";
				document.getElementById("divCampoSituacao").style.backgroundColor="#f4f4f4";
				document.getElementById("divCampoPeriodo").style.backgroundColor="white";
				
				
				//esconder
				_this.$el.find('#divCampoContrato').hide();
				_this.$el.find('#divCampoCPF').hide();
				_this.$el.find('#divCNPJConvenente').hide();
				_this.$el.find('#divCampoAgencia').hide();
				_this.$el.find('#divCampoCNPJ').hide();
				_this.$el.find('#divCampoConvenio').hide();
				
				
				break;
			case '4': // radioAgencia
				console.log('radioAgencia selecionado...');
				_this.validator.withForm('formFiltroContrato').cleanErrors();
				
				
				//mostrar 
				_this.$el.find('#divCampoAgencia').show();
				_this.$el.find('#divCampoSituacao').show();
				_this.$el.find('#divCampoPeriodo').show();
				_this.$el.find('#lblFiltrar').show();
				
				document.getElementById("divCampoAgencia").style.backgroundColor="white";
				document.getElementById("divCampoSituacao").style.backgroundColor="#f4f4f4";
				document.getElementById("divCampoPeriodo").style.backgroundColor="white";
				
				//esconder
				_this.$el.find('#divCampoSR').hide();
				_this.$el.find('#divCampoContrato').hide();
				_this.$el.find('#divCampoCPF').hide();
				_this.$el.find('#divCNPJConvenente').hide();
				_this.$el.find('#divCampoCNPJ').hide();
				_this.$el.find('#divCampoConvenio').hide();
				
				
				
				break;				
			case '5': // radioData
				console.log('radioData selecionado...');
				_this.validator.withForm('formFiltroContrato').cleanErrors();
				_this.$el.find('#obrigatorioPeriodo').show();
				_this.$el.find('#inicioVigenciaFiltro').attr('validators', 'required');
				_this.$el.find('#finalVigenciaFiltro').attr('validators', 'required');

				document.getElementById("divCampoSituacao").style.backgroundColor="white";
				document.getElementById("divCampoPeriodo").style.backgroundColor="#f4f4f4";
				
				
				//mostrar 
				_this.$el.find('#divCampoSituacao').show();
				_this.$el.find('#divCampoPeriodo').show();
				_this.$el.find('#lblFiltrar').show();
				
				//esconder
				_this.$el.find('#divCampoAgencia').hide();
				_this.$el.find('#divCampoSR').hide();
				_this.$el.find('#divCampoContrato').hide();
				_this.$el.find('#divCampoCPF').hide();
				_this.$el.find('#divCNPJConvenente').hide();
				_this.$el.find('#divCampoCNPJ').hide();
				_this.$el.find('#divCampoConvenio').hide();
				
				
				break;				
			case '6': // radioConvenente
				console.log('radioConvenente selecionado...');
				_this.validator.withForm('formFiltroContrato').cleanErrors();
				
				
				document.getElementById("divCampoCNPJ").style.backgroundColor="white";
				document.getElementById("divCampoSituacao").style.backgroundColor="#f4f4f4";
				document.getElementById("divCampoPeriodo").style.backgroundColor="white";
				
				
				//mostrar 
				_this.$el.find('#divCampoCNPJ').show();
				_this.$el.find('#divCampoSituacao').show();
				_this.$el.find('#divCampoPeriodo').show();
				_this.$el.find('#lblFiltrar').show();
				
				//esconder
				_this.$el.find('#divCampoAgencia').hide();
				_this.$el.find('#divCampoSR').hide();
				_this.$el.find('#divCampoContrato').hide();
				_this.$el.find('#divCampoCPF').hide();
				_this.$el.find('#divCNPJConvenente').hide();
				_this.$el.find('#divCampoConvenio').hide();
				
				
				break;				
			case '7': // radioConvenio
				console.log('radioConvenio selecionado...');
				_this.validator.withForm('formFiltroContrato').cleanErrors();
				
				//mostrar 
				_this.$el.find('#divCampoConvenio').show();
				_this.$el.find('#divCampoSituacao').show();
				_this.$el.find('#divCampoPeriodo').show();
				_this.$el.find('#lblFiltrar').show();
				
				
				document.getElementById("divCampoConvenio").style.backgroundColor="white";
				document.getElementById("divCampoSituacao").style.backgroundColor="#f4f4f4";
				document.getElementById("divCampoPeriodo").style.backgroundColor="white";
				
				
				
				//esconder
				_this.$el.find('#divCampoCNPJ').hide();
				_this.$el.find('#divCampoAgencia').hide();
				_this.$el.find('#divCampoSR').hide();
				_this.$el.find('#divCampoContrato').hide();
				_this.$el.find('#divCampoCPF').hide();
				_this.$el.find('#divCNPJConvenente').hide();
				
				
				break;				
			default:
				break;
			}	
			console.log('redefineTelaFiltro - fim');
		},
		
		validarErroProcessamento: function(){
			var situacao = _this.$el.find('#select_situacao').val();
			if(situacao == 8){
				_this.$el.find('#divFiltroErroProcessamento').show();
			}else{
				_this.$el.find('#divFiltroErroProcessamento').hide();
			}
		},
				
		getCollection: function () {
	    	if (this.collection == null || this.collection == undefined)
	    		this.collection = new ContratoColecao();
	    		
	    	return this.collection;
	    },
	    
	    cnpjMask : function() {
			$(".cnpj").mask("99.999.999/9999-99").off('focus.mask')
			.off('blur.mask');
			
		},
	    
	    changed : function (e) {
	    	this.modelo.set(e.target.name,e.target.value);
	    	this.getCollection().set(this.modelo, {remove: false});
		},
		
		consultarContrato : function(e) {
			console.log("autorizar controle - initialize");
			loadCCR.start();
			var opIdentificador = _this.$el.find('input[name="rdoIdentificador"]:checked').val();
			switch (opIdentificador) {
			case '1': // radioCliente
				if(!_this.validator.withInput('cpf_input').validate()){
					loadCCR.stop();
					return;
				}
				break;
			case '2': // radioContrato		
				if(!_this.validator.withInput('input_contrato').validate()){
					loadCCR.stop();
					return;
				}		
				break;
			case '3': // radioSr				
				if(!_this.validator.withInput('sr_input').validate()){
					loadCCR.stop();
					return;
				}
				var validacao = _this.validaSr();
				if(!validacao){
					_this.$el.find('#divListaContratos').addClass("hidden");
					loadCCR.stop();
					return;
				}else{
					$('#messageContainer').hide();
					break;
				}

				
			case '4': // radioAgencia
				if(!_this.validator.withInput('agencia_input').validate()){
					loadCCR.stop();
					return;
				}	
				var validacao = _this.validaAg();
				if(!validacao){
					_this.$el.find('#divListaContratos').addClass("hidden");
					loadCCR.stop();
					return;
				}else{
					$('#messageContainer').hide();
					break;
				}
			case '5': // radioData
				if(!_this.validator.withInput('inicioVigenciaFiltro').validate() || !_this.validator.withInput('finalVigenciaFiltro').validate()){
					loadCCR.stop();
					return;
				}
				break;
			case '6': // radioConvenente
				if(!_this.validator.withInput('CNPJ_input').validate()){
					loadCCR.stop();
					return;
				}	
				break;
			case '7': // radioConvenio
				if(!_this.validator.withInput('convenio_input').validate()){
					loadCCR.stop();
					return;
				}
				break;
			default:
				break;
			}	
			
			var listaAux = [];
			var consulta ={};
			if(_this.$el.find('#CNPJ_input').val() !="")
				var cnpj = String(_this.$el.find('#CNPJ_input').val()).replace(/[.\-\/]/g, '');
				consulta.cnpj = cnpj;
			if(_this.$el.find('#cpf_input').val() !="")
				var cpf = String(_this.$el.find('#cpf_input').val()).replace(/[.\-\/]/g, '');
				consulta.cpf = cpf;
			if(_this.$el.find('#select_situacao').val() !="" && _this.$el.find('#select_situacao').val()!=null)
				consulta.situacao 	= _this.$el.find('#select_situacao').val();
			if(_this.$el.find('#input_contrato').val() !="")
				consulta.contrato 	= _this.$el.find('#input_contrato').val();
			if(_this.$el.find('#sr_input').val() !="")
				consulta.sr 		= _this.$el.find('#sr_input').val();
			if(_this.$el.find('#agencia_input').val() !="")
				consulta.agencia 	= _this.$el.find('#agencia_input').val();
			if(_this.$el.find('#inicioVigenciaFiltro').val() !="")
				consulta.periodoINI = _this.$el.find('#inicioVigenciaFiltro').val();
			if(_this.$el.find('#finalVigenciaFiltro').val() !="")
				consulta.periodoFIM = _this.$el.find('#finalVigenciaFiltro').val();
			if(_this.$el.find('#convenio_input').val() !="")
				consulta.convenio 	= _this.$el.find('#convenio_input').val();		
			
			var perfil;
			
			_this.modelo = new Contrato();
			_this.modelo.validarPerfil()
			.done(function sucesso(data) {
				perfil = data;
				console.log("---------------- Perfil GESTOR "+perfil+" ----------------");
			})
			.error(function erro(jqXHR) {
				Retorno.trataRetorno({codigo: -1, tipo: "ERRO_EXCECAO", mensagem: "Ocorreu um erro ao autorizar o contrato!"}, 'autorizar', jqXHR);
				loadCCR.stop();
			});
			
			this.getCollection().listar(consulta)
			.done(function sucesso(data) {
				if(data.listaRetorno.length == 0){
					 listaAux = [];		
					_this.$el.find('#divListaContratos').html(_this.consultaContratoListaTemplate({lista: listaAux, perfil: true }));
					_this.$el.find('#divListaContratos').show();
				}else{
					$.each( data.listaRetorno, function( key, value ) {
						var avaliarAutorizar = false;
						if((value.autorizar && perfil) || value.avaliar) {
							avaliarAutorizar = true;
						}
						
						data.listaRetorno[key].avaliarAutorizar = avaliarAutorizar;
					});
					_this.$el.find('#divListaContratos').html(_this.consultaContratoListaTemplate({lista: data.listaRetorno, perfil: perfil }));
					_this.$el.find('#divListaContratos').show();
				}
					
				if (typeof e == 'undefined'){
					_this.preparaTelaFiltro();	
				}

				_this.$el.find('#gridListaContratos').dataTable({
					'aoColumns' : [ null, null, null, null, null, null, null, null, null, null ],
					'aaSorting': [],
					'oLanguage' : {'sEmptyTable' : 'Nenhum registro encontrado.', 'sLengthMenu': '_MENU_ registros por página', 'sInfoFiltered' : '(Filtrado _MAX_ do total de entradas)'},
				});		
				_this.$el.find('#divListaContratos').removeClass("hidden");
				listenToDatepickerChange(_this.$el, _this.changed);
				loadCCR.stop();				
			})
			.error(function erro(jqXHR) {
				Retorno.trataRetorno({codigo: -1, tipo: "ERRO_EXCECAO", mensagem: "Ocorreu um erro ao listar os contratos para autoriza&ccedil;&atilde;o!"}, 'autorizar', jqXHR);
				loadCCR.stop();
			});
		},
		
		validaSr: function (e){
			var sr = _this.$el.find('#sr_input').val();
			var retorno = true;
			_this.modelo.validaSr(sr)
			.done(function sucesso(data) {
				if(data.idMsg=='MA0059'){
					Retorno.trataRetorno({codigo: 1, tipo: "AVISO", mensagem: "A unidade informada não é um SR."});
					loadCCR.stop();	
					retorno = false;
				}
			})
			.error(function erro(jqXHR) {
				Retorno.trataRetorno({codigo: 1, tipo: "AVISO", mensagem: "Não foi possível consultar a Sr."}, '', jqXHR);
				retorno = false;
			});
			return retorno;
		},
		
		validaAg: function (e){
			var ag = _this.$el.find('#agencia_input').val();
			var retorno = true;
			_this.modelo.validaAg(ag)
			.done(function sucesso(data) {
				if(data.idMsg=='MA0060'){
					Retorno.trataRetorno({codigo: 1, tipo: "AVISO", mensagem: "Unidade informada não é Agência."});
					loadCCR.stop();	
					retorno = false;
				}
			})
			.error(function erro(jqXHR) {
				Retorno.trataRetorno({codigo: 1, tipo: "AVISO", mensagem: "Não foi possível consultar a Agência."}, '', jqXHR);
				retorno = false;
			});
			return retorno;
		},
		
		detalhar: function (e) {
			DashboardControleCCR.detalharContrato(e, _this.agenciaConcessora);
		},
		
		sair: function () {
			loadCCR.start();
			console.log("saindo do CCR...");
			window.location = 'index.html';
		},
		
		autorizar: function (e) {
			var etapa = 'autorizar';
			DashboardControleCCR.contratarConsignado(e, etapa);
		},
		
		reenviarContrato : function(e){
			var idContrato = _this.$el.find(e.currentTarget).data('nucontrato');
			loadCCR.start();
			_this.modelo.reenviarContratoSIAPX(idContrato).done(function sucesso(data) {
				
				if(data.codigoRetorno === -1){
					Retorno.trataRetorno({codigo: 1, tipo: "AVISO", mensagem: data.mensagemRetorno});
				}else{
					_this.$el.find(e.currentTarget).addClass("disabled");
					_this.$el.find(e.currentTarget).attr("id", "");
					Retorno.trataRetorno({codigo: 0, tipo: "SUCESSO", mensagem: "Reenviado contrato pro SIAPX"});					
				}
				loadCCR.stop();
			})
			.error(function erro(jqXHR) {
				Retorno.trataRetorno({codigo: 1, tipo: "AVISO", mensagem: "Não foi possível reenviar contrato ao SIAPX."});
				loadCCR.stop();
				retorno = false;
			});
		},
		
		avaliarOperacao: function (e) {
			var etapa = 'avaliarOperacao';
			DashboardControleCCR.contratarConsignado(e, etapa);
		},
		
		avaliar: function (e) {
			var etapa = 'avaliar';
			DashboardControleCCR.contratarConsignado(e, etapa);
		},
		
		cancelar: function () {
			this.$el.find('#divFormularioCancelamento').modal('show');
		},
		
		voltar: function () {
			_this.$el.find('#divListaContratos').show('fast');
			_this.$el.find('#divDetalharContrato').html('').hide('fast');
		},
		
		confirmarCancelamento: function () {
			if (_this.acao == 'C')
				return;
			
			var justificativa = _this.$el.find('#justificativa').val() == '' ? 0 : _this.$el.find('#justificativa').val();
			
			this.$el.find('#divFormularioCancelamento').modal('hide');			
			loadCCR.start();
			
			this.modelo.cancelar(_this.modelo.get('numero'), justificativa)
			.done(function sucesso(data) {
				Retorno.trataRetorno(data, 'autorizar', null, true);
				$('ul.nav a#autorizarContrato.links-menu').trigger('click');				
			})
			.error(function erro(jqXHR) {
				Retorno.trataRetorno({codigo: -1, tipo: "ERRO_EXCECAO", mensagem: "Ocorreu um erro ao cancelar o contrato!"}, 'autorizar', jqXHR);
				loadCCR.stop();
			});
		},
		
		voltarCancelar: function () {
			this.$el.find('#divFormularioCancelamento').modal('hide');
		},
		
		abrirImprimir: function (e) {
			_this.contrato = new Contrato();
			_this.contrato.buscarTipoDocumentoImpressaoCCR().done(function sucesso(data) {
				Retorno.trataRetorno(data, 'documento', null, false);
				
				if(data.tipoRetorno == "ERRO_EXCECAO" ){
					Retorno.trataRetorno({codigo: -1, tipo: "ERRO_EXCECAO", mensagem: "Ocorreu um erro ao imprimir!"}, '', jqXHR);
				}
				
				var id = $(e.target).data('contrato') == undefined ? $(e.target).parent().data('contrato') : $(e.target).data('contrato');
				_this.consultarContratoPorId(id);
				_this.buscarDadosSicli(_this.dadosContrato.nuCpf);
				_this.$el.find('#numContrato').text(_this.dadosContrato.nuContrato);
				_this.$el.find('#dataLiberacao').text(formatTimeStamp(_this.dadosContrato.dtLiberacaoCredito,"dd/MM/yyyy"));
				_this.$el.find('#cpfCliente').text(formatadores.formatarCPF(_this.dadosContrato.nuCpf));
				
				_this.$el.find('#divImprimir').modal('show');
	
				if(data.icModeloTestemunha == "A"){
					_this.impressaoTipoCCBTrue();
				
				}else{
					_this.impressaoTipoCCBFalse();
				}
			})
			.error(function erro(jqXHR) {
				Retorno.trataRetorno({codigo: -1, tipo: "ERRO_EXCECAO", mensagem: "Ocorreu um erro ao imprimir!"}, '', jqXHR);
			});
			_this.validator.withForm('impressao').cleanErrors();
		},
		
		buscarDadosSicli : function(cpf) {
			_this = this;
			//valida sicli
			_this.modeloCliente = new Cliente();
			
			_this.modeloCliente.consultarDadosSicliPorCPF(cpf).done(function sucesso(data) {		
				console.log(data);
				_this.clienteSICLI = data.dados;
				if(_this.clienteSICLI.nomeCliente.nome != null){
					_this.$el.find('#nomeCliente').text(_this.clienteSICLI.nomeCliente.nome);
				}
				loadCCR.stop();
			})
			.fail(function erro(jqXHR) {
				_this.gestor  = "false";
			});
		},
		
		impressaoTipoCCBTrue : function (){
			_this.$el.find('#inputImprimiGerenteResp').removeAttr('validators', 'required');
			_this.$el.find('#inputImprimiCPFGerenteResp').removeAttr('validators', 'required');
			_this.$el.find('#inputImprimiTestemunha1').removeAttr('validators', 'required');
			_this.$el.find('#inputImprimiCPFTestemunha1').removeAttr('validators', 'required');
			_this.$el.find('#inputImprimiTestemunha2').removeAttr('validators', 'required');
			_this.$el.find('#inputImprimiCPFTestemunha2').removeAttr('validators', 'required');
			
			_this.$el.find('#spanImprimiGerenteResp')[0].innerHTML = "";
		    _this.$el.find('#spanImprimiCPFGerenteResp')[0].innerHTML = "";
			_this.$el.find('#spanImprimiTestemunha1')[0].innerHTML = "";
			_this.$el.find('#spanImprimiCPFTestemunha1')[0].innerHTML = "";
			_this.$el.find('#spanImprimiTestemunha2')[0].innerHTML = "";
			_this.$el.find('#spanImprimiCPFTestemunha2')[0].innerHTML = "";
		},
		
		impressaoTipoCCBFalse : function (){
			_this.$el.find('#inputImprimiGerenteResp').attr('validators', 'required');
			_this.$el.find('#inputImprimiCPFGerenteResp').attr('validators', 'required');
			_this.$el.find('#inputImprimiTestemunha1').attr('validators', 'required');
			_this.$el.find('#inputImprimiCPFTestemunha1').attr('validators', 'required');
			_this.$el.find('#inputImprimiTestemunha2').attr('validators', 'required');
			_this.$el.find('#inputImprimiCPFTestemunha2').attr('validators', 'required');
			
			_this.$el.find('#spanImprimiGerenteResp')[0].innerHTML = "*";
		    _this.$el.find('#spanImprimiCPFGerenteResp')[0].innerHTML = "*";
			_this.$el.find('#spanImprimiTestemunha1')[0].innerHTML = "*";
			_this.$el.find('#spanImprimiCPFTestemunha1')[0].innerHTML = "*";
			_this.$el.find('#spanImprimiTestemunha2')[0].innerHTML = "*";
			_this.$el.find('#spanImprimiCPFTestemunha2')[0].innerHTML = "*";
		},
		
		consultarContratoPorId : function(id) {
			_this.modeloContrato.consultarPorId(id).done(function sucesso(data) {
				_this.dadosContrato = data;
				
			})
			.error(function erro(jqXHR) {
				if(jqXHR && jqXHR.responseText && jqXHR.responseText != ""){
					Retorno.exibeErroDetalhe(jqXHR.responseText);
				}else{
					Retorno.trataRetorno({codigo: -1, tipo: "ERRO_EXCECAO", mensagem: "Ocorreu um erro ao buscar dados da empresa!"});
				}
			});
		
		},
		
		contruirImpressao : function(){
			if (_this.validator.withForm('impressao').validate()){
				var cpfClienteFormat = String(_this.$el.find('#cpfCliente').text()).replace(/[.\-\/]/g, '');
				_this.buscaDadosSicli(cpfClienteFormat);	
			}
		},
		
		imprimir : function (dadosCliente){
			var cpfClienteFormat = String(_this.$el.find('#cpfCliente').text()).replace(/[.\-\/]/g, '');
			var cpfTestemunha1Format = String(_this.$el.find('#inputImprimiCPFTestemunha1').val()).replace(/[.\-\/]/g, '');
			var cpfTestemunha2Format = String(_this.$el.find('#inputImprimiCPFTestemunha2').val()).replace(/[.\-\/]/g, '');
			var cpfGerenteFormat = String(_this.$el.find('#inputImprimiCPFGerenteResp').val()).replace(/[.\-\/]/g, '');
			
			var impressao= {
				numContrato  			:	_this.$el.find('#numContrato').text(),
				cpfCliente 				:	cpfClienteFormat,
				nomeCliente 			:	_this.$el.find('#nomeCliente').text(),
				localImpressaoContrato 	:	_this.$el.find('#inputImprimiLocal').val(),
				nomeGerente 			:	_this.$el.find('#inputImprimiGerenteResp').val(),
				nomeTestemunha1 		:	_this.$el.find('#inputImprimiTestemunha1').val(),
				cpfTestemunha1 			:	cpfTestemunha1Format,
				nomeTestemunha2 		:	_this.$el.find('#inputImprimiTestemunha2').val(),
				cpfTestemunha2 			:	cpfTestemunha2Format,
				sicli                   :   dadosCliente, 
				cpfGerente              :   cpfGerenteFormat
				
			};
			
			_this.modeloContrato.imprimirContrato(impressao).done(function sucesso(data) {
				Retorno.trataRetorno(data, 'documento', null, false);
				
				for (i = 0; i < data.contratosImpressao.length; i++) { 
					newWin = window.open("");
					newWin.document.write(data.contratosImpressao[i]);

					newWin.print();					newWin.close();
				}
				
				if(data.contratosImpressao.length == 0){
					Retorno.trataRetorno({codigo: 0, tipo: "AVISO", mensagem: "", idMsg: 'MA004'}, 'documento');
					_this.$el.find('#divImprimir').modal('hide');
				}
			})
			.error(function erro(jqXHR) {
				Retorno.trataRetorno({codigo: -1, tipo: "ERRO_EXCECAO", mensagem: "Ocorreu ao imprimir!"}, '', jqXHR);
			});
		},
		
		
		fecharImpressao : function(){
			_this.validator.withForm('impressao').cleanErrors();
			_this.$el.find('#divImprimir').modal('hide');
		},
		
		
		limparCampos: function(){
			_this.$el.find('#divCNPJConvenente').find('input').val('');		
			_this.$el.find('#divCampoContrato').find('input').val('');
			_this.$el.find('#divCampoPeriodo').find('input').val('');
			_this.$el.find('#divCampoSR').find('input').val('');
			_this.$el.find('#divCampoAgencia').find('input').val('');
			_this.$el.find('#divCampoCNPJ').find('input').val('');
			_this.$el.find('#divCampoConvenio').find('input').val('');
			_this.$el.find('#divCampoCPF').find('input').val('');
			_this.$el.find('#select_situacao').val('');
			
			_this.$el.find('#divListaContratos').addClass("hidden");
		},
		
		novoContrato: function(e){
			DashboardControleCCR.contratarConsignado(e,'1');
		},
		
		
		buscaDadosSicli : function(cpf) {
			_this = this;
			//valida sicli
			_this.modeloCliente = new Cliente();
			
			_this.modeloCliente.consultarDadosSicliPorCPF(cpf).done(function sucesso(data) {		
				console.log(data);
				loadCCR.stop();
				
				if (data.dados.responseArqRef.status.codigo == 0) {
					_this.imprimir(data.dados);	
				}else {
					//Valida o retorno do SICLI, caso erro sobe a msg.
					Retorno.validarRetorno(data);
				}
				
			})
			.fail(function erro(jqXHR) {
				_this.gestor  = "false";
			});
		},
	});

	return ManterContratoControle;
	
});


