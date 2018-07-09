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
        'enumeracoes/eMensageria',
        'util/retorno',
        'controle/cliente/consultaClienteControle',
        'modelo/contratacao/simulacao',
        'modelo/administracao/convenente',        
        'modelo/administracao/parametroOperacao',
        'modelo/cliente/avaliacao',
        'modelo/cliente/cliente',
        'modelo/contratacao/contrato',
        'text!visao/contratacao/simularMain.html',
        'text!visao/contratacao/dadosSimulacao.html',
        'text!visao/contratacao/dadosContrato.html',
        'text!visao/administracao/filtroGrupoAverbacao.html',
        'text!visao/cliente/resultadoAvaliacao.html',
        ], 
function(EMensagemCCR, ETipoServicos, EMensageria, Retorno, ConsultaClienteControle,  
		Simular, Convenio, ParametroOperacao, Avaliacao, Cliente, Contrato, simularMainTpl, dadosSimulacaoTpl, 
		dadosContratoTpl, filtroGrupoAverbacaoTpl, resultadoAvaliacaoTpl){

	var _this = null;
	var SimularMainControle = Backbone.View.extend({

		className: 'SimularMainControle',
		
		/**
		 * Templates
		 */
		simularMainTemplate         : _.template(simularMainTpl),
		dadosSimulacaoTemplate      : _.template( _.unescape($(dadosSimulacaoTpl).filter('#divLayoutSimulacao').html()) ),
		dadosNovaSimulacaoTemplate  : _.template( _.unescape($(dadosSimulacaoTpl).filter('#divLayoutNovaSimulacao').html()) ),
		simulacaoResultadoTemplate  : _.template( _.unescape($(dadosSimulacaoTpl).filter('#divLayoutResultado').html()) ),		
		dadosContratoTemplate       : _.template(dadosContratoTpl),
		filtroGrupoAverbacaoTemplate: _.template(filtroGrupoAverbacaoTpl),
		resultadoAvaliacaoTemplate  : _.template(resultadoAvaliacaoTpl),
		
		/**
		 * Variaveis da classe
		 */
		validator   : new Validators(),
		message     : new Message(),
		ctrlCliente : null,
		comando     : null,
		modelo		: null,
		modeloAvaliacao: null,
		
		parametroOperacao : null,
		convenio: null,
		simulacao: null,
		simulacaoSel: null,
		sicli: null,
		avaliacao: null,
		simulacaoComSeguro: '',
		matricula: '',
				
		/**
		 * Função que faz bind das ações e interações da pagina com as funções
		 * javascript
		 * 
		 */
		events : {			
			'click #formComSeguroPrest #btnContratarComSeg'	: 'contratar',
			'click #formSemSeguroPrest #btnContratarSemSeg'	: 'contratar',
			
			'click #formSimular #btnSimular'				: 'simular',
			'click #formSimular #btnLimpar'					: 'limparSimulacao',
			
			'click #btnReniciarProcesso'					: 'atualizar',
			'click #btnNovaSimulacao'						: 'novaSimulacao',
			
			'click #formNovaSimulacao #btnRecalcular'		: 'simular',
			'click #formNovaSimulacao #btnCancelar'			: 'fecharModalNovaSimulacao',
			
			'click #btnContratar'					 		: 'confirmaContrato',
			'click #btnNovaAvaliacao'						: 'novaAvaliacao',
			'click #btnConsultarNovo'						: 'consultarNovoCliente',
						
			'click #divComSeguroPrest'						: 'selecionarResultado',
			'click #divSemSeguroPrest'						: 'selecionarResultado',
			'click #divDetalharResultComSeg'				: 'mostrarDetalhe',
			'click #divDetalharResultSemSeg'				: 'mostrarDetalhe',
			
			'mouseenter #divDetalharResultComSeg'			: 'evidenciaDetalhe',
			'mouseleave #divDetalharResultComSeg'			: 'evidenciaDetalhe',			
			'mouseenter #divDetalharResultSemSeg'			: 'evidenciaDetalhe',
			'mouseleave #divDetalharResultSemSeg'			: 'evidenciaDetalhe',
			
			'blur #valorPrestacao'		 					: 'verificaCampoInformado',
			'blur #valorLiquido'	 						: 'verificaCampoInformado',
				
			'blur input'									: 'changed',
			'change select'									: 'changedSelects',
			
		},
				
		
		/**
		 * Função padrão de incialização do template html
		 * 
		 */
		initialize : function() {
			console.log("Simular Main controle - initialize");
			
			// pra nao ter problema de pegar outro 'this'
			_this = this;
			
			// configura validator
			_this.validator.withErrorRender(new BootstrapErrorRender());
			_this.validator.addValidator(new MaiorQueZeroValidator());
			_this.validator.addValidator(new DataValidator());
			_this.validator.addValidator(new DataValidatorMenor(formatDate(new Date(), 'dd/MM/yyyy')));
			
		},
			
		/**
		 * Função padrão para reenderizar os dados html na tela
		 * Nessa função é carregado a mascara para CPF e colocadao o foco
		 * no campo de CPF
		 * Retorna o proprio objeto criado
		 * @returns {anonymous}
		 */
		render : function() {
			_this = this;
			
			console.log("Simular Main - render");	
			
			//Inclui o template inicial no el deste controle
			_this.$el.html(_this.simularMainTemplate());
						
			_this.$el.find('#divSimulacao_DadosSimulacao').html(_this.dadosSimulacaoTemplate());
			_this.$el.find('form#formSimular').prepend(_this.filtroGrupoAverbacaoTemplate());
			
			//seta obrigatoriedade dos filtro de averbacao
			_this.$el.find('#convenio').prop('disabled', false).attr('validators', 'required').closest('.control-group').find('.obrigatorio').html('*');
			_this.$el.find('#grupo').prop('disabled', false).attr('validators', 'required').closest('.control-group').find('.obrigatorio').html('*');
			
			//Carrega as mascaras usadas.
			loadMaskEl(_this.$el.find('#divSimulacao_DadosSimulacao'));
			
			// desabilita os botoes
			_this.$el.find('a.disabled').on('click', function(evt) {
				evt.preventDefault();
				return false;
			});
			
			_this.$el.find('#wizardSimulacao').wizardCCR();
			
			_this.$el.find('#divComSeguroPrest').css({cursor: 'pointer'});
			_this.$el.find('#divSemSeguroPrest').css({cursor: 'pointer'});			
			_this.$el.find('#divDetalharResultComSeg').css({cursor: 'pointer'});
			_this.$el.find('#divDetalharResultSemSeg').css({cursor: 'pointer'});
						
			listenToDatepickerChange(_this.$el, _this.changed);
			_this.carregarCombos();
			_this.loadInfoParametrosOperacao();
			
			return _this;
		},
		
		getModelo : function () {
	    	if(_this.modelo == null || _this.modelo == undefined){
	    		_this.modelo = new Simular();
	    		_this.listenTo(_this.modelo, EMensageria.SUCESSO, _this.renderSimulacao);
	    		_this.listenTo(_this.modelo, 'error', _this.tratarErro);
	    		_this.listenTo(_this.modelo, EMensageria.NAO_RECEBIDO, _this.semRetorno);
	    	}
	    	
	    	return _this.modelo;
		},

		getModeloAvaliacao: function () {
			if(_this.modeloAvaliacao == null || _this.modeloAvaliacao == undefined){
	    		_this.modeloAvaliacao = new Avaliacao();
	    		_this.listenTo(_this.modeloAvaliacao, "SUCESSO_AVALIACAO", _this.renderAvaliacao);
	    		_this.listenTo(_this.modeloAvaliacao, "ERROR_AVALIACAO", _this.tratarErroAvaliacao);
	    		_this.listenTo(_this.modeloAvaliacao, "SEM_RETORNO_AVALIACAO", _this.semRetornoAvaliacao);
	    	}
	    	
	    	return _this.modeloAvaliacao;
		},
		
		changed : function (e) {
			var name = e.target.name, value = e.target.value;
			console.log(name, value);
			_this.getModelo().set(name, value);	    	
		},
		
		changedSelects : function (e) {
	    	_this = this;
			var name = e.target.name, value = e.target.value;			
	    	
	    	if (name == 'convenio')
	    		_this.loadInfoConvenio(value);	    	
		},
		
		verificaCampoInformado: function(e) {
			var form = $(e.target).closest('form');
			
			var $vrContratoLiq = form.find('#valorLiquido');
			var $vrPrestacao = form.find('#valorPrestacao');
			
			var vrContratoLiq = $vrContratoLiq.val().trim() == '' ? 0 : parseFloat($vrContratoLiq.val().replace('.', '').replace(',', '.'));
			var vrPrestacao = $vrPrestacao.val().trim() == '' ? 0 : parseFloat($vrPrestacao.val().replace('.', '').replace(',', '.')); 
			
			if (vrContratoLiq > 0) {
				$vrContratoLiq.prop('disabled', false).attr('validators', 'required,maiorQueZero').closest('.control-group').find('.obrigatorio').html('*');
				$vrPrestacao.prop('disabled', true).attr('validators', '').closest('.control-group').find('.obrigatorio').html('');
				
				return;
			}
			
			if (vrPrestacao > 0) {
				$vrContratoLiq.prop('disabled', true).attr('validators', '').closest('.control-group').find('.obrigatorio').html('');
				$vrPrestacao.prop('disabled', false).attr('validators', 'required,maiorQueZero').closest('.control-group').find('.obrigatorio').html('*');
				
				return;
			}
			
			$vrContratoLiq.prop('disabled', false).attr('validators', 'required,maiorQueZero').closest('.control-group').find('.obrigatorio').html('*');
			$vrPrestacao.prop('disabled', false).attr('validators', 'required,maiorQueZero').closest('.control-group').find('.obrigatorio').html('*');
		},
		
		
		proximaAba: function () {
			_this.$el.find('#wizardSimulacao').wizardCCR('next');
		},
		
		anteriorAba: function () {
			_this.$el.find('#wizardSimulacao').wizardCCR('previous');
		},
		
		simular: function (e) {
			_this = this;
			
			var isNovaSimulacao = $(e.target).data('nova-simulacao') == 'S' ? true : false;
			var form = isNovaSimulacao ? 'formNovaSimulacao' : 'formSimular';
			
			if (_this.validator.withForm(form).validate()) {
				
				if (_this.parametroOperacao == null || _this.parametroOperacao.get('numero') == 0) {
					msgCCR.alerta("Parametros da Opera&ccedil;&atilde;o n&atilde;o encontrados, aguarde enquanto o sistema realiza nova busca", function () { _this.loadInfoParametrosOperacao(); });				
					return;
				}
				
				if (_this.convenio == null || _this.convenio.get('id') == 0) {
					msgCCR.alerta("Dados do Conv&ecirc;nio n&atilde;o encontrados, aguarde enquanto o sistema realiza nova busca", function () { _this.loadInfoConvenio(_this.$el.find('#convenio').val()); });				
					return;
				}
				
				if (!_this.validaRegrasCampos())
					return;
				
				if (isNovaSimulacao)
					_this.fecharModalNovaSimulacao();
				
				loadCCR.start();
				
				_this.getModelo().set('cnpjConvenio', _this.convenio.get('convenente').cnpj);
				_this.getModelo().simular();				
			}
				
		},
		
		renderSimulacao: function (dados) {
			console.log('renderSimulacao', dados);
			
			if (_this.$el.find('#divSimulacao').hasClass('modal')) { 
				_this.$el.find('#divSimulacao').modal('hide').removeClass('modal fade');
				_this.$el.find('#divSimulacao fieldset').remove();
			} else {
				_this.$el.find('#divSimulacao').hide();
			}
			
			// mostra o resultado da simulacao
			_this.$el.find('#divSimulacao_DadosSimulacao').find('#divResultadoSimulacao').remove(); // limpa o html
			_this.$el.find('#divSimulacao_DadosSimulacao').closest('fieldset').find('legend span').html('Resultado Simula&ccedil;&atilde;o');
			_this.$el.find('#divSimulacao_DadosSimulacao').closest('fieldset').find('#btnReniciarProcesso').show();
			_this.$el.find('#divSimulacao_DadosSimulacao').closest('fieldset').find('#btnNovaSimulacao').show();
			_this.$el.find('#divSimulacao_DadosSimulacao').append( _this.simulacaoResultadoTemplate({ resultado: dados, modelo: _this.modelo }));			
			
			_this.simulacao = $.extend(true, {}, dados);
			_this.limparSimulacao();
			loadCCR.stop();
			
		},
		
		semRetorno: function (modelo) {
			Retorno.trataRetorno({tipo: 'AVISO', idMsg: 'MA001'}, 'simular');
			loadCCR.stop();
		},
		
		tratarErro: function (modelo, xhr, request) {
			if (xhr.responseText.indexOf("[REGRA_NEGOCIO]") > -1) {
				var obj = Retorno.getObjetoErroArqRef(JSON.parse(xhr.responseText));
				var msg = obj.mensagemErro.slice(obj.mensagemErro.indexOf("[REGRA_NEGOCIO]") + 15);
				Retorno.trataRetorno({tipo: "AVISO", mensagem: msg}, 'simular');
			} else {				
				Retorno.trataRetorno(JSON.parse(xhr.responseText), 'simular');
			}
			
			loadCCR.stop();
		},
		
		limparSimulacao: function () {
			_this.$el.find('#formSimular')[0].reset();			
			_this.validator.withForm('formSimular').cleanErrors();
			
			_this.$el.find('#txtVrLiquido').prop('disabled', false).attr('validators', 'required,maiorQueZero')
			   							   .closest('.control-group').find('.obrigatorio').html('*');

			_this.$el.find('#txtVrPrestacao').prop('disabled', false).attr('validators', 'required,maiorQueZero')
							 				 .closest('.control-group').find('.obrigatorio').html('*');
		},
		
		selecionarResultado: function (evt) {			
			var id = _this.$el.find(evt.currentTarget).data('resultado');
			var selecionado = _this.$el.find(evt.currentTarget).data('selecionado');
			
			if (!selecionado) {
				if (id == '1') {
					_this.$el.find('#divSemSeguroPrest').data({selecionado: false});
					_this.$el.find('#divSemSeguroPrest').parent().removeClass('panel-primary').addClass('panel-default');
					
					_this.$el.find('#divComSeguroPrest').data({selecionado: true});
					_this.$el.find('#divComSeguroPrest').parent().removeClass('panel-default').addClass('panel-primary');
				} else {
					_this.$el.find('#divComSeguroPrest').data({selecionado: false});
					_this.$el.find('#divComSeguroPrest').parent().removeClass('panel-primary').addClass('panel-default');
					
					_this.$el.find('#divSemSeguroPrest').data({selecionado: true});
					_this.$el.find('#divSemSeguroPrest').parent().removeClass('panel-default').addClass('panel-primary');
				}	
			}
		},
		
		mostrarDetalhe: function (evt) {
			_this.$el.find('#divDetalheResultComSeg').toggle();				
			_this.$el.find('#divDetalheResultSemSeg').toggle();		
		},
		
		evidenciaDetalhe: function (evt) {
			_this.$el.find(evt.currentTarget).parent().toggleClass('evidenciar');
		},
		
		novaSimulacao: function () {
			_this.$el.prepend(_this.dadosNovaSimulacaoTemplate());
			loadMaskEl(_this.$el.find('#divNovaSimulacao'));
			listenToDatepickerChange(_this.$el.find('#divNovaSimulacao'), _this.changed);
			
			_this.$el.find('#divNovaSimulacao').modal({
				backdrop: 'static'
			});
						
			_this.habilitaPopovers();
			
			// carrega campos
			var valorPrestacao = _this.modelo.get('valorPrestacao') == undefined || _this.modelo.get('valorPrestacao') == null ? '' : _this.modelo.get('valorPrestacao');
				valorLiquido = _this.modelo.get('valorLiquido') == undefined || _this.modelo.get('valorLiquido') == null ? '' : _this.modelo.get('valorLiquido');
			
			_this.$el.find('#divNovaSimulacao input#valorPrestacao').val(mascararNumeroInteiro(valorPrestacao));
			_this.$el.find('#divNovaSimulacao input#dataNascimento').val(formatadores.formatarData(_this.modelo.get('dataNascimento'))).prop('disabled', true);
			_this.$el.find('#divNovaSimulacao input#dataContratacao').val(formatadores.formatarData(_this.modelo.get('dataContratacao')));
			_this.$el.find('#divNovaSimulacao input#valorLiquido').val(mascararNumeroInteiro(valorLiquido)).trigger('blur');
			_this.$el.find('#divNovaSimulacao input#prazo').val(_this.simulacao.simulacaoComSeguro.prazo);
			_this.$el.find('#divNovaSimulacao input#taxaJuros').val(mascararTaxa(_this.simulacao.simulacaoComSeguro.taxaJuros, 5));
			
		},
		
		habilitaPopovers: function () {
			
			_this.$el.find('#aDetalheTaxa').popover({
				content: 'Deve ser informada uma taxa de juros na faixa de idade e prazo permitida para o conv&ecirc;nio selecionado.',
				html: true
			}).on('hidden.bs.popover', function(){
	            $(this).show();
	        });
		
		},
		
		contratar: function (e) {
			_this = this;
			
			if ($(e.target).data('com-seguro') == 'S') {
				_this.simulacaoComSeguro = 'S';
				_this.simulacaoSel = $.extend(true, {}, _this.simulacao.simulacaoComSeguro);
			} else {
				_this.simulacaoComSeguro = 'N';
				_this.simulacaoSel = $.extend(true, {}, _this.simulacao.simulacaoSemSeguro);
			}
			
			var modeloCliente = new Cliente();			
			_this.ctrlCliente = new ConsultaClienteControle({consultaBeneficio: false, modelo: modeloCliente});
			_this.$el.find('#divSimulacao_DadosCliente').html(_this.ctrlCliente.render().el);
			
			_this.listenTo(modeloCliente, "retornoSucessoCliente", _this.validaRetornoCliente);						
			_this.$el.find('#btnContratar').closest('div.form-actions').hide();
			
			_this.proximaAba();
		},
		
		consultarNovoCliente: function () {
			_this.$el.find('#btnContratar').closest('div.form-actions').hide();
			_this.$el.find('#divSimulacao_DadosAvaliacao').parent().hide();
			_this.ctrlCliente.consultarNovoCliente();
		},
		
		fecharModalNovaSimulacao: function() {
			_this.$el.find('#divNovaSimulacao').modal('hide');
		},
		
		atualizar: function () {
			$('ul.nav a#simularCreditoConsignado.links-menu').trigger('click');
		},
		
		carregarCombos: function () {
			_this = this;
			var el = _this.$el;
			var grupoAverbacaoCaixa = '1';
						
			gCarregarGrupoAverbacao(el, 'grupo', grupoAverbacaoCaixa);
			gCarregarConvenioGrupoAverbacao(el, 'convenio', grupoAverbacaoCaixa);
		},
		
		loadInfoParametrosOperacao: function () {
			_this = this;
			loadCCR.start();
			
			_this.parametroOperacao = new ParametroOperacao();
			_this.parametroOperacao.consultar()
			.done(function sucesso(data){
				Retorno.trataRetorno(data, 'parametroOperacao', undefined, false);
				loadCCR.stop();
			})
			.error(function erro(jqXHR){
				Retorno.trataRetorno({codigo: -1, tipo: "ERRO_EXCECAO", mensagem: "Ocorreu um erro ao consultar os parametros da opera&ccedil;&atilde;o!"}, 'parametroOperacao', jqXHR);
				loadCCR.stop();
			});
			
		},
		
		loadInfoConvenio: function (id) {
			_this = this;
			_this.convenio = new Convenio();
			
			if (convenio == '')
				return;
			
			loadCCR.start();
			_this.convenio.consultar(id)
			.done(function sucesso(data){
				Retorno.trataRetorno(data, 'convenio', undefined, false);
				loadCCR.stop();
			})
			.error(function erro(jqXHR){
				Retorno.trataRetorno({codigo: -1, tipo: "ERRO_EXCECAO", mensagem: "Ocorreu um erro ao consultar os parametros da conv&ecirc;nio!"}, 'convenio', jqXHR);
				loadCCR.stop();
			});
		},
		
		validaRegrasCampos: function () {
			_this = this;
			var convenioCanal = undefined;
			var form = _this.$el.find('#formSimular');
			
			// verifica se o convenio possui este canal cadastrado
			convenioCanal = $.grep(_this.convenio.get('canais'), function(e){ return e.canal.id == ID_CANAL; });
			if (convenioCanal.length < 1) {
				Retorno.trataRetorno({tipo: "AVISO", idMsg: 'MN001'}, 'simular');
				return false;
			}
			
			if (convenioCanal[0].permiteContratacao != 'S') {
				Retorno.trataRetorno({tipo: "AVISO", idMsg: 'MN002'}, 'simular');
				return false;
			}
			
			var dataNascimento = _this.getModelo().get("dataNascimento");
			var idade = diffDataFormatada(dataNascimento, formatDate(new Date(), 'dd/MM/yyyy'), 'YY');
			if (idade < _this.parametroOperacao.get('idadeMinima') || idade > _this.parametroOperacao.get('idadeMaxima')) {
				renderErrorAlone(form, 'dataNascimento', EMensagemCCR.simular.MN003);
				return false;
			}
			
			var dataContratacao = _this.getModelo().get("dataContratacao");
			var diffDias = diffDataFormatada(formatDate(new Date(), 'dd/MM/yyyy'), dataContratacao, 'DD');
			if (diffDias > convenioCanal[0].qtDiaMaxSimulacaoFutura) {
				renderErrorAlone(form, 'dataContratacao', EMensagemCCR.simular.MN004);
				return false;
			}
			
			if (_this.getModelo().get('prazo') != '' && _this.getModelo().get('prazo') > 0) {
				if (_this.getModelo().get('prazo') > _this.convenio.get('prazoMaximo')) {
					renderErrorAlone(form, 'prazo', EMensagemCCR.simular.MN005);
					return false;
				}				
			}
			
			return true;
			
		},
		
		validaRetornoCliente: function (dadosCliente) {
			console.info('ini validaRetornoCliente');			
			_this = this;
			
			_this.matricula = dadosCliente.matricula;
			_this.sicli = $.extend(true, {}, dadosCliente.SICLI);
			
			
			// =======================================================================================
			// verifica se recebeu ao menos uma conta do cliente para credito!
			//if ()
			
			_this.consultarAvaliacao(dadosCliente.SICLI.cpf.documento.numero + dadosCliente.SICLI.cpf.documento.digitoVerificador);
		},
		
		novaAvaliacao: function () {
			loadCCR.start();
			_this.consultarAvaliacao(_this.sicli.cpf.documento.numero + _this.sicli.cpf.documento.digitoVerificador);			
		},
		
		consultarAvaliacao: function (cpf) {
			_this.getModeloAvaliacao().set({'cpf': cpf, idSimulacao: _this.simulacaoSel.id});
			_this.getModeloAvaliacao().consultar();
		},
		
		renderAvaliacao: function (dados) {
			console.log('SimularMainControle - renderAvaliacao', dados);
			_this = this;
			
			_this.avaliacao = dados[0];
			// dados da avaliacao
			_this.$el.find('#divSimulacao_DadosAvaliacao').parent().show();
			_this.$el.find('#divSimulacao_DadosAvaliacao').html(_this.resultadoAvaliacaoTemplate({avaliacao: _this.avaliacao}));
			
			_this.$el.find('#btnContratar').parent().show();
			_this.$el.find('#btnContratar').show();
			_this.$el.find('#btnNovaAvaliacao').show();
			
			loadCCR.stop();
		},
		
		tratarErroAvaliacao: function(modelo, response, jqXHR) {
			console.log('SimularMainControle - tratarErroAvaliacao - ini');
			
			loadCCR.stop();
			_this.$el.find('#btnNovaAvaliacao').parent().show();
			_this.$el.find('#btnNovaAvaliacao').show();
			_this.$el.find('#btnContratar').hide();
			Retorno.trataRetorno(JSON.parse(response.responseText), 'SIRIC');
		},
		
		semRetornoAvaliacao: function () {
			console.log('SimularMainControle - tratarErroAvaliacao - ini');
			
			loadCCR.stop();
			_this.$el.find('#btnNovaAvaliacao').parent().show();
			_this.$el.find('#btnNovaAvaliacao').show();
			_this.$el.find('#btnContratar').hide();
			Retorno.trataRetorno({tipo: 'AVISO', idMsg: 'MA001'}, 'SIRIC');			
		},
		
		confirmaContrato: function () {
			
			var contrato = {};
			var precisaAutorizacao = 'S';
			
			for (var i=0; i < _this.sicli.meioComunicacao.length; i++) {
				if (_this.sicli.meioComunicacao[i].meioComunicao == null)
					continue;
					
				if ((_this.sicli.meioComunicacao[i].meioComunicao == '11') || 
				   (_this.sicli.meioComunicacao[i].meioComunicao == '12') ||
				   (_this.sicli.meioComunicacao[i].meioComunicao == '13') ||
				   (_this.sicli.meioComunicacao[i].meioComunicao == '14')) {
					   dddTelefone = _this.sicli.meioComunicacao[i].prefixoDiscagem;
					   telefone = _this.sicli.meioComunicacao[i].codigoComunicacao;
					   break;
				}
			}
			
			var conta = "";
			for (var i=0; i < _this.sicli.produtoCaixa.length; i++) {
				var produto = _this.sicli.produtoCaixa[i];
				if (produto.dataFimAtividade == null) {
					conta = produto.contrato;
					break;
				}
			}
			
			var convenioCanal = $.grep(_this.convenio.get('canais'), function(e){ return e.canal.id == ID_CANAL; });
			if (convenioCanal[0].exigeAutorizacao == 'S') {
				if (parseFloat(_this.simulacaoSel.valorContrato) > parseFloat(convenioCanal[0].minimoExigeAutorizacao))
					precisaAutorizacao = 'S';
				else
					precisaAutorizacao = 'N';
			} else {
				precisaAutorizacao = 'N';
			}
			
			// cria um objeto do contrato
			contrato = {
				numero: 0,
				tipoSituacao: 2, 
				situacao: 1,
				convenio: _this.convenio.get("id"),
				modalidade: NU_MODALIDADE,
				garantia: NU_GARANTIA,
				cpf: _this.sicli.cpf.documento.numero + _this.sicli.cpf.documento.digitoVerificador,
				cocli: _this.sicli.cocliAtivo.cocliAtivo,
				nome: _this.sicli.nomeCliente.nome,
				dataNascimento: formatadores.formatarData(_this.sicli.dataNascimento.data), 
				sexo: _this.sicli.sexo.sexo,
				estadoCivil: _this.sicli.estadoCivil.estadoCivil, 
				carteira: _this.sicli.carteiraCliente[0].carteiraPrincipal,
				ric: 0,
				rg: _this.sicli.identidade.documento.numero,
				orgaoEmissor: _this.sicli.identidade.siglaOrgaoEmissor, 
				ufEmissor: _this.sicli.identidade.documento.uf,
				dataEmissao: formatadores.formatarData(_this.sicli.identidade.documento.dataEmissao),
				beneficio: 0,
				logradouro: _this.sicli.enderecoNacional[0].logradouro,
				numeroEndereco: _this.sicli.enderecoNacional[0].numero,
				complemento: _this.sicli.enderecoNacional[0].complemento,
				bairro: _this.sicli.enderecoNacional[0].bairro,
				municipio: _this.sicli.enderecoNacional[0].nomeMunicipio,
				uf: _this.sicli.enderecoNacional[0].uf,
				cep: _this.sicli.enderecoNacional[0].cep.slice(1, 5),
				cepComplemento: _this.sicli.enderecoNacional[0].cep.slice(5, 8), 
				ddd: dddTelefone,
				telefone: telefone,
				profissao: _this.sicli.profissaoSiric.profissao,
				nacionalidade: 1,
				contratoApx: 0,
				valorLiquido: formatadores.formatarMoedaParaFloat(mascararMoeda(_this.simulacaoSel.valorLiquido, 2)),
				valorPrestacao: _this.simulacaoSel.prestacao,
				prazo: _this.simulacaoSel.prazo,
				valor: _this.simulacaoSel.valorContrato,
				dataLiberacao: formatDate(new Date(), 'dd/MM/yyyy'), 
				valorIOF: formatadores.formatarMoedaParaFloat(mascararMoeda(_this.simulacaoSel.iof, 2)),
				taxaJuro: formatadores.formatarMoedaParaFloat(mascararTaxa(_this.simulacaoSel.taxaJuros, 5)),
				cetMensal: _this.simulacaoSel.cetmensal,
				cetAnual: _this.simulacaoSel.cetanual,
				dataBase: _this.simulacao.camposComuns.basePrimeiraPrestacao,
				vencimentoPrimeiraPrestacao: _this.simulacao.camposComuns.vencimentoPrimeiraPrestacao, 
				quantidadeDiasAcerto: _this.simulacao.camposComuns.quantDiasJurosAcerto,
				valorJuroAcerto: _this.simulacaoSel.juroAcerto,
				comSeguro: _this.simulacaoComSeguro,
				valorSeguro: _this.simulacaoComSeguro == 'S' ? _this.simulacaoSel.valorSeguro : 0,
				taxaSeguro: formatadores.formatarMoedaParaFloat(mascararTaxa(_this.simulacao.camposComuns.taxaSeguro, 5)),
				banco: NU_BANCO_CEF,
				agencia: conta.slice(0,4),
				operacao: conta.slice(4,7),
				conta: conta.slice(7,15),
				digitoVerificador: conta.slice(15,16), 
				tipoCredito: 2,
				data: formatDate(new Date(), 'dd/MM/yyyy'),
				aliquotaBasicaIOF: formatadores.formatarMoedaParaFloat(mascararTaxa(_this.simulacao.camposComuns.iofAliquotaBasica, 5)),  
				aliquotaComplementar: formatadores.formatarMoedaParaFloat(mascararTaxa(_this.simulacao.camposComuns.iofAliquotaComplementar, 5)), 
				avaliacao: _this.avaliacao.codigo,
				naturalPVMovimento: _this.convenio.get("naturalPVResp"), 
				pvMovimento: _this.convenio.get("pvresp"),
				matricula: _this.matricula.slice(1)
			};
			
			var modelContrato = new Contrato();
			
			loadCCR.start();
			modelContrato.cadastrar(contrato, precisaAutorizacao)
			.done(function sucesso(data){
				var retorno = Retorno.trataRetorno(data, 'contrato', undefined, true);
				
				if (retorno) {
					_this.$el.find('#divSimulacao_Contrato').html(_this.dadosContratoTemplate({contrato: data}));
					_this.proximaAba();										
					$("html, body").animate({ scrollTop: 0 }, "slow");
				}
					
				loadCCR.stop();
			})
			.error(function erro(jqXHR){
				Retorno.trataRetorno({codigo: -1, tipo: "ERRO_EXCECAO", mensagem: "Ocorreu um erro ao salvar o contrato!"}, 'contrato', jqXHR);
				loadCCR.stop();
			});
		}
		
	});

	return SimularMainControle;
	
});

