/**
 * @author F620600
 * 
 * JavaScript que controla as ações das paginas:
 * 
 * 
 * @version 1.0.0.0
 * 
 * 
 */
define(['enumeracoes/eMensagemCCR',
        'enumeracoes/eTipoServicos',
        'modelo/convenios/convenio',
        'modelo/cliente/cliente',
        'modelo/convenios/convenioColecao',
        'util/retorno',
        'text!visao/administracao/manterConvenente.html',
        'text!visao/administracao/detalheConvenente.html',
        'text!visao/administracao/manutencaoConvenente.html',
        'text!visao/administracao/filtroConvenente.html',
        'text!visao/administracao/manterListaConvenente.html',
        'text!visao/administracao/inclusaoConvenente.html'
        ], 
function(EMensagemCCR, ETipoServicos, Convenio, Cliente, ConvenioCollection, Retorno, manterConvenenteTpl, detalheConvenenteTpl, manutencaoConvenenteTpl, filtroConvenenteTpl, manterListaConvenenteTpl, inclusaoConvenenteTpl){

	var _this = null;
	var ManterConvenenteControle = Backbone.View.extend({

		className: 'ManterConvenenteControle',
		
		/**
		 * Templates
		 */
		convenenteTemplate : _.template( _.unescape($(manterConvenenteTpl).filter('#divConvenioTpl').html()) ),
		filtroConvenenteTemplate : _.template(filtroConvenenteTpl),
		listaConvenenteTemplate: _.template( _.unescape($(manterConvenenteTpl).filter('#divlistaConvenioTpl').html()) ),
		detalheConvenenteTemplate: _.template(detalheConvenenteTpl),
		manterConvenenteTemplate: _.template(manutencaoConvenenteTpl),		
		manterListaConvenenteTemplate: _.template(manterListaConvenenteTpl),	
		inclusaoConvenenteTemplate: _.template(inclusaoConvenenteTpl),
		edit : [],
		
		/**
		 * Variaveis da classe
		 */
		validator   : new Validators(),
		message     : new Message(),
		comando     : null,
		modelo		: null,
		convenio	: null,
		cliente		: null,
		collection  : null,
		acao		: null,
		gestor		: null,
		
		/**
		 * Função que faz bind das ações e interações da pagina com as funções
		 * javascript
		 * 
		 */
		events : {
			'click a#bntNovoConvenente'			: 'novoConvenente',
			'click a#bntAlterarConvenente'		: 'alterarConvenente',
			'click a#bntConsultarConvenente'    : 'consultarConvenente',
			'click a#btnDetalhesConvenio'    	: 'detalhesConvenio',
			'click a#bntLimparFormFiltro'    	: 'limpar',
			'click a#btnVoltar'					: 'voltar',
			'click a#btnSair'					: 'sair',
			'click a#btnSalvar'					: 'salvar',
			'click a#btnIncluirCanal'			: 'incluirCanal',
			'click a#btnConsultarCnpj'			: 'consultarCNPJ',
			'click a#btnIncluirUf'				: 'incluirUF',
			'click a#btnExcluirUf'				: 'excluirUF',
			'click a#btnIncluirSr'				: 'incluirSr',
			'click a#btnExcluirSr'				: 'excluirSr',	
			'click a#btnIncluirCNPJ'			: 'incluirCNPJ',
			'click a#btnExcluirCNPJ'			: 'excluirCNPJ',
			'blur #formManutencaoConvenio input' : 'changed',
			'blur #formManutencaoConvenio select': 'changed',
			"change input[name='rdoAbrangencia']":"redefineAbrangencia",
			"change input[name='rdoIdentificador']":"redefineTelaFiltro",
			'change #grupoAverbacao'	: 'changedSelectsGrpAverb',
			"focus input[name='cnpjConvenente']":"cnpjMask",			
			"focus input[name='CNPJText']":"cnpjMask",			
			"focus input[name='codigoOrgao.id']":"valorNumerico",
			"focus input[name='numSprnaResponsavel']":"valorNumerico",
			"focus input[name='numPvResponsavel']":"valorNumerico",
			"focus input[name='numSprnaCobranca']":"valorNumerico",
			"focus input[name='numPvCobranca']":"valorNumerico",
			"focus input[name='diaCreditoSal']":"valorNumerico",
			"focus input[name='qtdeEmpregados']":"defineNumerico",
			"focus input[name='przEmissaoExtrato']":"defineNumerico",
			"focus input[name='srText']":"defineNumerico",
			"focus input[name='qtDiaAguardarAutorizacao']":"valorNumerico",
			"focus input[name='qtdDiaGarantiaCondicao']":"valorNumerico",
			"focus input[name='przMinimoContratacao']":"valorNumerico",
			"focus input[name='przMinimoRenovacao']":"valorNumerico",
			"focus input[name='przMaximoContratacao']":"valorNumerico",
			"focus input[name='przMaximoRenovacao']":"valorNumerico",			
			"change input[name='canais.indPermiteRenovacao']":"checkPermiteRenovacao",
			"change input[name='canais.indPermiteContratacao']":"checkPermiteContratacao",
			'change .radio-indPermiteContratacao': 'alterarRadioPermiteContratacao',
			'change .radio-indPermiteRenovacao': 'alterarRadioPermiterenovacao',
			'click .btn-excluir-canal': 'excluirCanal',
			'click .btn-alterar-canal': 'alterarCanal',
			'click .btn-gravar-canal': 'gravarCanal'
		},
		
		alterarRadioPermiteContratacao : function(ev){
			var index = $(ev.currentTarget).data('index');
			
			if(index.match("_N")){
				var canal = index.replace("_N", "");
				$("#przMinimoContratacao_input_"+canal).val("");
				$("#przMaximoContratacao_input_"+canal).val("");
				
				 _this.$el.find('#przMinimoContratacao_input_'+canal).prop('disabled', true).attr('validators', '');
				 _this.$el.find("#przMaximoContratacao_input_"+canal).prop('disabled', true).attr('validators', '');
				 
				 _this.$el.find("#indFaixaJuroContratacao_input_"+canal).prop('disabled', true).attr('validators', '');
				 $("#radioExigeAutorizacaoContratacao_"+canal+"_S").prop('disabled', true);
				 $("#radioExigeAutorizacaoContratacao_"+canal+"_N").prop('disabled', true);
				 $("#indFaixaJuroContratacao_input_"+canal).val("").change(); 
			}else{	 
				var canal = index.replace("_S", "");
				 _this.$el.find("#przMinimoContratacao_input_"+canal).prop('disabled', false).attr('validators', 'required');
				 _this.$el.find("#przMaximoContratacao_input_"+canal).prop('disabled', false).attr('validators', 'required');
				 _this.$el.find("#indFaixaJuroContratacao_input_"+canal).prop('disabled', false).attr('validators', 'required');
				 $("#radioExigeAutorizacaoContratacao_"+canal+"_S").prop('disabled', false);
				 $("#radioExigeAutorizacaoContratacao_"+canal+"_N").prop('disabled', false); 
			}
		},
		
		
		alterarRadioPermiterenovacao : function(ev){
			var index = $(ev.currentTarget).data('index');
			if(index.match("_N")){
				var canal = index.replace("_N", "");
				$("#przMinimoRenovacao_input_"+canal).val("");
				$("#przMaximoRenovacao_input_"+canal).val("");
				
				 _this.$el.find('#przMinimoRenovacao_input_'+canal).prop('disabled', true).attr('validators', '');
				 _this.$el.find("#przMaximoRenovacao_input_"+canal).prop('disabled', true).attr('validators', '');
				 
				 _this.$el.find("#indFaixaJuroRenovacao_input_"+canal).prop('disabled', true).attr('validators', '');
				 $("#radioExigeAutorizacaoRenovacao_"+canal+"_S").prop('disabled', true);
				 $("#radioExigeAutorizacaoRenovacao_"+canal+"_N").prop('disabled', true);
				 $("#indFaixaJuroRenovacao_input_"+canal).val("").change(); 
			}else{
				var canal = index.replace("_S", "");
				 _this.$el.find("#przMinimoRenovacao_input_"+canal).prop('disabled', false).attr('validators', 'required');
				 _this.$el.find("#przMaximoRenovacao_input_"+canal).prop('disabled', false).attr('validators', 'required');
				 _this.$el.find("#indFaixaJuroRenovacao_input_"+canal).prop('disabled', false).attr('validators', 'required');
				 $("#radioExigeAutorizacaoRenovacao_"+canal+"_S").prop('disabled', false);
				 $("#radioExigeAutorizacaoRenovacao_"+canal+"_N").prop('disabled', false); 
			}
		},
		
		checkPermiteRenovacao : function(){
			_this = this;
			 var value = _this.$el.find('input[name="canais.indPermiteRenovacao"]:checked').val();
			 
			 if(value == 'N'){
				 _this.$el.find('#przMinimoRenovacao').prop('disabled', true).attr('validators', '');
				_this.$el.find('#przMaximoRenovacao').prop('disabled', true).attr('validators', '');
				 _this.$el.find('#indFaixaJuroRenovacao').prop('disabled', true).attr('validators', '');
				 $("#canaisIndAutorizaMargemRenovacao_S").prop('disabled', true);
				 $("#canaisIndAutorizaMargemRenovacao_N").prop('disabled', true);
				 _this.limparCamposRenovacao();
			 }else{
				 _this.$el.find('#przMinimoRenovacao').prop('disabled', false).attr('validators', 'required');
				 _this.$el.find('#przMaximoRenovacao').prop('disabled', false).attr('validators', 'required');
				_this.$el.find('#indFaixaJuroRenovacao').prop('disabled', false).attr('validators', 'required');
				 $("#canaisIndAutorizaMargemRenovacao_S").prop('disabled', false);
				 $("#canaisIndAutorizaMargemRenovacao_N").prop('disabled', false);
			 }
		},
		
		checkPermiteContratacao : function(){
			_this = this;
			 var value = _this.$el.find('input[name="canais.indPermiteContratacao"]:checked').val();
			 
			 if(value == 'N'){
				 _this.$el.find('#przMinimoContratacao').prop('disabled', true).attr('validators', '');
				 _this.$el.find('#przMaximoContratacao').prop('disabled', true).attr('validators', '');
				 _this.$el.find('#indFaixaJuroContratacao').prop('disabled', true).attr('validators', '');
				 $("#canaisIndAutorizaMargemContrato_S").prop('disabled', true);
				 $("#canaisIndAutorizaMargemContrato_N").prop('disabled', true);
				 _this.limparCamposContratacao();
			 }else{
				 _this.$el.find('#przMinimoContratacao').prop('disabled', false).attr('validators', 'required');
				 _this.$el.find('#przMaximoContratacao').prop('disabled', false).attr('validators', 'required');
				 _this.$el.find('#indFaixaJuroContratacao').prop('disabled', false).attr('validators', 'required');
				 $("#canaisIndAutorizaMargemContrato_S").prop('disabled', false);
				 $("#canaisIndAutorizaMargemContrato_N").prop('disabled', false); 
			 }
		},
		
		limparCamposContratacao : function(){
			$("#przMinimoContratacao").val("");
			$("#przMaximoContratacao").val("");
		},
		
		limparCamposRenovacao : function(){
			$("#przMinimoRenovacao").val("");
			$("#przMaximoRenovacao").val("");
		},
		
		defineNumerico : function() {
			
			$("#qtdeEmpregados").numeric();
			$("#przEmissaoExtrato").numeric();
			$("#srText").numeric();
			
					
		},
		
		cnpjMask : function() {
			$(".cnpj").mask("99.999.999/9999-99").off('focus.mask')
			.off('blur.mask');
		},
		
		valorNumerico : function() {
			$('.numero').numeric(); // numeric
		},
		
		/**
		 * Função padrão de incialização do template html
		 * 
		 */
		initialize : function() {
			console.log("Manter Convenente controle - initialize");
			// pra nao ter problema de pegar outro 'this'
			_this = this;
			_this.acao = _this.options.acao || 'T';
			
			// configura validator
			_this.validator.withErrorRender(new BootstrapErrorRender());
			
			// perfil de acesso
			var retorno = "";
			_this.gestor = "";
			_this.modelo = new Convenio();
			_this.modelo.consultarPerfil(retorno).done(function sucesso(data) {
				_this.gestor = $.trim(data.mensagemRetorno);
				// _this.gestor = "false";
			})
			.error(function erro(jqXHR) {
				_this.gestor  = "false";
			});
			
		},
			
		/**
		 * Função padrão para reenderizar os dados html na tela Nessa função é
		 * carregado a mascara para CPF e colocadao o foco no campo de CPF
		 * Retorna o proprio objeto criado
		 * 
		 * @returns {anonymous}
		 */
		render : function() {
			console.log("Manter Convenente - render");	
			
			_this.edit = [];
			
			// Inclui o template inicial no el deste controle
			_this.$el.html(_this.convenenteTemplate());
			
			// configura datatable
			_this.$el.find('#divFiltroConvenenteForm').html(_this.filtroConvenenteTemplate());
			
			// Carrega as mascaras usadas.
			loadMaskEl(_this.$el);
			// loadMask();
			
			// desabilita os botoes
			_this.$el.find('a.disabled').on('click', function(evt) {
				evt.preventDefault();
				return false;
			});
				        
	        var camposFormComposto = [
				{campo: _this.$el.find('#cnpj')},
				{campo: _this.$el.find('#id')},
				{campo: _this.$el.find('#nome')},	            
				{campo: _this.$el.find('#rdoIdentificador')},	            
				{campo: _this.$el.find('#situacao')},	            
				{campo: _this.$el.find('#sr')},	            
				{campo: _this.$el.find('#agencia')}	            
	        ];
	        
	        if (_this.acao == 'C') {
	        	_this.$el.find('#formFiltroConvenente #bntNovoConvenente').remove();
	        	_this.$el.find('#formFiltroConvenente #bntAlterarConvenente').remove();
			}
	        
	        // Carrega a combo de situação
			var el = _this.$el.find('#divFiltroConvenente');
			gCarregarSituacao(el, "situacao", null, '1');	// 1 é
															// NU_TIPO_SITUACAO
															// para todas as
															// situações no BD.
			
			_this.canaisExcluidos  = [];
			_this.canaisInclusao   = [];
			_this.canaisAlteracao  = [];
			_this.preparaTelaFiltro();
	        
			return _this;
		},
		
		preparaTelaFiltro : function() {
			_this.$el.find('input[name="rdoIdentificador"][value="1"]').prop('checked', true);
			_this.initTelaFiltro();
		},
		
		initTelaFiltro : function () {
			_this.$el.find('#divCodigoConvenente').show();
			_this.$el.find('#divNomeConvenente').show();
			_this.$el.find('#divSituacao').show();
			_this.$el.find('#divCNPJConvenente').hide();
			_this.$el.find('#divSR').hide();
			_this.$el.find('#divAgencia').hide();
		},
		
		redefineTelaFiltro : function() {
			console.log('redefineTelaFiltro - inicio');
			var opIdentificador = _this.$el.find('input[name="rdoIdentificador"]:checked').val();

			switch (opIdentificador) {
			case '1': // Convenio
				console.log('Convenio selecionado...');
				_this.validator.withForm('formFiltroConvenente').cleanErrors();
				_this.initTelaFiltro();
				
				// 2
				_this.$el.find('#cnpj').val("");
				
				// 3
				_this.$el.find('#sr').val("");
				_this.$el.find('#agencia').val("");
				
				break;
			case '2': // Convenente
				console.log('Convenente selecionado...');
				_this.validator.withForm('formFiltroConvenente').cleanErrors();
				_this.$el.find('#divCodigoConvenente').hide();
				_this.$el.find('#divNomeConvenente').hide();
				_this.$el.find('#divSituacao').hide();
				_this.$el.find('#divCNPJConvenente').show();
				_this.$el.find('#divSR').hide();
				_this.$el.find('#divAgencia').hide();
				
				// 1
				_this.$el.find('#id').val("");
				_this.$el.find('#nome').val("");
				_this.$el.find('#situacao').val("");
				
				// 3
				_this.$el.find('#sr').val("");
				_this.$el.find('#agencia').val("");
				
				break;				
			case '3': // Unidade
				console.log('Unidade selecionado...');
				_this.validator.withForm('formFiltroConvenente').cleanErrors();
				_this.$el.find('#divCodigoConvenente').hide();
				_this.$el.find('#divNomeConvenente').hide();
				_this.$el.find('#divSituacao').hide();
				_this.$el.find('#divCNPJConvenente').hide();
				_this.$el.find('#divSR').show();
				_this.$el.find('#divAgencia').show();
				
				// 1
				_this.$el.find('#id').val("");
				_this.$el.find('#nome').val("");
				_this.$el.find('#situacao').val("");
				
				// 2
				_this.$el.find('#cnpj').val("");
				
				
				break;				
			default:
				break;
			}	
			console.log('redefineTelaFiltro - fim');
		},
		
	    changed : function (e) {
	    	var name = e.target.name;
	    	
	    	if (name == null || name == "" || name.indexOf("canais") > -1)
	    		return;
	    	
	    	if (name.indexOf('.') > -1) {
	    		var realName = name.split('.');
	    		
	    		if (realName[1] == 'cep') {
	    			var valor = $(e.target).val();
	    			var arrayValor;
	    			
	    			if (valor.indexOf('-') > -1)
	    				arrayValor = valor.split('-');
	    			else
	    				arrayValor = [0, 0];
	    			
	    			_this.convenio[realName[0]][realName[1]] = arrayValor[0];
	    			_this.convenio[realName[0]]['complementoCep'] = arrayValor[1];
	    		} else {
	    			_this.convenio[realName[0]][realName[1]] = $(e.target).val();
	    		}
	    		
	    	} else {
    			_this.convenio[name] = $(e.target).val();
	    	}
		},
		
		detalhesConvenente: function (evt) {	
			
			var index = _this.$el.find(evt.currentTarget).data('index');	
			
			_this.convenio = _this.collection.models[index].attributes;
			
			_this.modelo = new Convenio();
			_this.modelo.consultarConvenio(_this.convenio.id)
			.done(function sucesso(data) {
				
				var i = 0;
				
				$.each(data.canais, function (index, canal) {				
					if (canal == null){
						return true;					
					}else {
						
						
						if (canal.indPermiteContratacao == "N") {
							canal.indPermiteContratacao = "Não"
						}else{
							canal.indPermiteContratacao = "Sim"
						}
						
						
						if (canal.indPermiteRenovacao == "N") {
							canal.indPermiteRenovacao = "Não"
						}else{
							canal.indPermiteRenovacao = "Sim"
						}
						
						if (canal.indExigeAutorizacaoContrato == "N") {
							canal.indExigeAutorizacaoContrato = "Não"
						}else{
							canal.indExigeAutorizacaoContrato = "Sim"
						}
						
						if (canal.indPermiteRenovacao == "N") {
							canal.indPermiteRenovacao = "Não"
						}else{
							canal.indPermiteRenovacao = "Sim"
						}
						
						if (canal.indExigeAutorizacaoContrato == "N") {
							canal.indExigeAutorizacaoContrato = "Não"
						}else{
							canal.indExigeAutorizacaoContrato = "Sim"
						}
						
						if (canal.indTaxaJuroPadrao == "N") {
							canal.indTaxaJuroPadrao = "Não"
						}else{
							canal.indTaxaJuroPadrao = "Sim"
						}
						
						if (canal.qtdeDiaGarantia == null) {
							canal.qtdeDiaGarantia = 0;
						}
						
						if (canal.qtDiaMaximoSimulacaoFutura == null) {
							canal.qtDiaMaximoSimulacaoFutura = 0;
						}
						
						if (canal.qtDiaMaximoSimulacaoFutura == null) {
							canal.qtDiaMaximoSimulacaoFutura = 0;
						}
						
						if (canal.przMinimoAtrzaMrgmRenovacao == null) {
							canal.przMinimoAtrzaMrgmRenovacao = 0;
						}
						
						if (canal.przMaximoRenovacao == null) {
							canal.przMaximoRenovacao = 0;
						}
						
						if (canal.faixaJurosRenovacao == null) {
							canal.faixaJurosRenovacao = 0;
						}
					}
					
					data.canais[i] = canal;
					i++;					
				});				
					
				_this.convenio = data;
				
				_this.$el.find('#divDetalhesConvenente').html(_this.detalheConvenenteTemplate({convenio: _this.convenio}));
				
				// _this.carregarcombos();
				loadMask(_this.$el.find('#divDetalhesConvenente'));
				_this.$el.find('#divFiltroConvenente').hide();
				_this.$el.find('#divListaConvenio').hide();
				_this.$el.find('#divDetalhesConvenente').show();
				
				loadCCR.stop();
				$('#ajaxStatus').modal('hide');
			})
			.error(function erro(jqXHR) {
				// (Alert("Errro"));
				msgCCR.alerta("Funcionalidade Indísponivel!!!", function () {});
			});
					
		},
				
		consultarCNPJ : function (evento) {
			console.log("CCR - consultarCNPJ");
			var el = _this.$el;// .find('#divDetalheConvenente');
			var cnpj = _this.$el.find('#formInclusaoConvenio #cnpjConvenente').val()
			_this.modelo = new Convenio();
			
			// validacoes a parte
			_this.$el.find('#formInclusaoConvenio #cnpjConvenente').attr('validators', 'cnpj');
			//if ($.trim(cnpj) == '' || _this.validator.withInput('cnpj').validate()) {
			//if (_this.validator.withInput('cnpj').validate()) {
			if (_this.validator.withForm('formInclusaoConvenio').validate()){	
					
				loadCCR.start();
				
				//Chamada do SICLI - busca correlationId
				_this.modelo.consultar(cnpj).done(function sucesso(data) {
					console.log(data)
					var correlationId = data.correlationId;
					console.log(correlationId);
					
					//Chamada do SICLI - com o correlationId busca os dados no fila de response
					_this.modelo.consultarCorrelationId(correlationId).done(function sucesso(dataRet) {					
						
						if (dataRet.dados.responseArqRef.status.codigo ==null){
							if (dataRet.dados==null || dataRet.dados.cocliAtivo==null){						
								console.log("Chama o agendador");						
								//Chama o agendador
								setTimeout(function(){
									_this.agendador(6, 2000,correlationId);						
								},2000);
							}else{
								_this.consultarCNPJ_render(dataRet.dados);							
							}	
							
						}else{						
							if (dataRet.dados.responseArqRef.status.codigo ==0){
								_this.consultarCNPJ_render(dataRet.dados);							
							}else{
								Retorno.validarRetorno(dataRet);
								loadCCR.stop();
								return;							
							}
						}					
					})
					.error(function erro(jqXHR) {
						_this.gestor  = "false";
					});			
				
				})
				.error(function erro(jqXHR) {
					_this.gestor  = "false";
				});
			}//if valida CNPJ
			
			
		},
		
		/**
		 * Funcao que agenda as execucoes de busca de cliente
		 * 
		 * @param tentativas
		 * @param atraso
		 * @param funcao
		 * @param parametro
		 */
		agendador : function (tentativas, atraso, correlationId){
			console.log("Cliente - agendador");
			console.log(new Date());
			console.log("tentativas -- > " + tentativas);
			var that = this;
			if(tentativas > 0){
				//recebe o resultado da consulta cliente e verifica
				//se havera reexcucao
				_this.modelo.consultarCorrelationId(correlationId).done(function sucesso(dataRet) {
						if (dataRet.dados==null || dataRet.dados.cocliAtivo==null){			
								console.log("reexcutar");						
								tentativas = tentativas - 1;
								console.log("executando o agendador: "+tentativas);
								_this.chamaAgendador(tentativas, atraso, correlationId);													
						}else{
							_this.consultarCNPJ_render(dataRet.dados);						
						}
						
					})
					.error(function erro(jqXHR) {
						_this.gestor  = "false";
					});
			}else{
				
				_this.modelo.consultarCorrelationId(correlationId).done(function sucesso(dataRet) {
				
								
					if (dataRet.dados==null || dataRet.dados.cocliAtivo==null){			
					
						Retorno.validarRetorno(dataRet);
						loadCCR.stop();
						_this.consultarCNPJ_render(dataRet.dados);	
						return;													
					}
					
				})
				.error(function erro(jqXHR) {
					_this.gestor  = "false";
				});
			}
		},
		
		chamaAgendador: function (tentativas, atraso, correlationId) {
			setTimeout(function(){
				_this.agendador(tentativas, atraso, correlationId);						
			},atraso);
		},
		
		consultarCNPJ_render: function(data){						
			
			_this.$el.find('#formInclusaoConvenio #cnpjConvenente').attr('validators', 'cnpj');
			
			if (_this.validator.withForm('panelDadosConvenente').validate()){
				//loadCCR.start();
			
				var ufs = document.getElementById("ufList");
				var srs = document.getElementById("srList");
				var CNPJs = document.getElementById("CNPJList");
				
				var el = _this.$el;// .find('#divDetalheConvenente');
				var cnpj = _this.$el.find('#formInclusaoConvenio #cnpjConvenente').val();
				_this.modelo = new Convenio();
				cnpj = cnpj.replace(/[_.\-\/]/g, '');
				 
				loadCCR.stop();
				
				_this.cliente = data;
				 
				// recupera dados da tela
				_this.recuperaDadosTela();
				
				// validacao abrangencia
				var opIdentificador = _this.$el.find('input[name="rdoAbrangencia"]:checked').val();
				
				_this.$el.find('#divDetalhesConvenente').html(_this.inclusaoConvenenteTemplate({sicli: _this.cliente, convenio:_this.convenio}));
				
				if(_this.gestor != "true"){
					document.getElementById("divIncluirCanais").style.display = 'none'
					document.getElementById("divCanais").style.display = 'none'
				}else{
					document.getElementById("divIncluirCanais").style.display = 'block'
					document.getElementById("divCanais").style.display = 'block'
				}
			
				
				var produtosCaixa = $.extend(true, [], _this.cliente.produtoCaixa);
				var contasCorrentes = [];
				// var comboContaCorrentesLst = [];
				var agenciaConta;
										
				$.each(produtosCaixa, function (index, produtoCaixa) {				
					if (produtoCaixa == null){
						return true;					
					}else {
							
							agenciaConta =  produtoCaixa.contrato.substring(0,4)+"."+
											produtoCaixa.contrato.substring(4,7)+"."+
											produtoCaixa.contrato.substring(7,15)+"-"+
											produtoCaixa.contrato.substring(15);
							
							var obj ={};
							obj.id = index;
							obj.descricao = agenciaConta;
							contasCorrentes.push(obj);									
					}
				});
				
				_this.cliente.contasCorrentesLst = $.extend(true, [], contasCorrentes);
				
				// carrega combos da tela
				_this.carregarCombosIncluirConvenente(); // combo
															// conta
															// corrente
				gCarregaComboContaCorrente(el, "contasCorrentes", _this.convenio.contaCorrente, _this.cliente.contasCorrentesLst);
				
				// Valida Abrangencia
				_this.$el.find('input[name="rdoAbrangencia"][value='+opIdentificador+']').prop('checked', true);				
				if (opIdentificador == 2) { // UF
					
					// Recarrega lista UFs
					$('#ufList option').remove();					
					
					for (i = 0; i < ufs.length; i++) { 
						
						 var text = ufs.options[i].text;						 
						 var option = new Option(text,text);
						 $('#ufList').append(option);				 
					 }
					
					_this.$el.find('#divAbrangenciaUf').show();
					_this.$el.find('#divAbrangenciaSr').hide();
					
				}else if (opIdentificador == 3){ // SR
					
					// Recarrega lista SRs
					$('#srList option').remove();
					
					for (i = 0; i < srs.length; i++) { 
						
						 var text = srs.options[i].text;						 
						 var option = new Option(text,text);
						 $('#srList').append(option);				 
					 }
					
					_this.$el.find('#divAbrangenciaUf').hide();
					_this.$el.find('#divAbrangenciaSr').show();
					
				}else {
					_this.$el.find('#divAbrangenciaUf').hide();
					_this.$el.find('#divAbrangenciaSr').hide();
				}
				
				
				// Recarrega lista CNPJs
				$('#CNPJList option').remove();
				
				for (i = 0; i < CNPJs.length; i++) { 
					
					 var text = CNPJs.options[i].text;						 
					 var option = new Option(text,text);
					 $('#CNPJList').append(option);				 
				 }
				
				
				// valida codigo orgao
				if (_this.convenio.grupoAverbacao.id == '7'){
		    		_this.$el.find('#codigoOrgao').prop('disabled', false).attr('validators', 'required');
		    	}else {
		    		_this.$el.find('#codigoOrgao').prop('disabled', true).attr('validators', '');
		    	}
				
			}//fecha if
		},
		
		alterarConvenente: function (evt) {	
			
			var index = _this.$el.find(evt.currentTarget).data('index');
			_this.convenio = _this.collection.models[index].attributes;
			_this.cliente = new Cliente();
			_this.cliente = _this.cliente.attributes;
			
			_this.convenio.convenioCNPJVinculado.shift();
						
			_this.modelo = new Convenio();
			_this.modelo.consultarConvenio(_this.convenio.id) 
			.done(function sucesso(data) {
				
				// trta retorno em caso de erro
				if (data.mensagemRetorno == "MA004") {
					Retorno.trataRetorno({codigo: 1, tipo: "AVISO", mensagem: "", idMsg: 'MA004'}, 'convenio');
					loadCCR.stop();
					return;
				}
				
				_this.convenio = data;				
				var cnpj = data.cnpjConvenente;	
				console.log("-------------------------------------------");
				console.log(_this.convenio);
				console.log("-------------------------------------------");
				
				$.when(_this.buscaDadosCliente(cnpj)).then(function(obj){
					_this.cliente = obj;
					_this.$el.find('#divDetalhesConvenente').html(_this.manterConvenenteTemplate({sicli:_this.cliente, convenio:_this.convenio}));
					
					// Carrega combos com selecionados
					_this.carregarCombosAlterarConvenente();
					
					var cnpjList = document.getElementById("CNPJList");
					convenioCNPJVinculadoList = _this.convenio.convenioCNPJVinculado;
					for(var i = 0; i < convenioCNPJVinculadoList.length; i++) {
				    	var opt = document.createElement('option');
				    	opt.innerHTML = convenioCNPJVinculadoList[i].nuCNPJ;
				    	opt.value = convenioCNPJVinculadoList[i].nuCNPJ;
				    	cnpjList.appendChild(opt);
					}
					
					// implementacaodas listas UFs / SRs
					// UFs list
					var abrangenciaObjUF = document.getElementById("ufList");				
					if(_this.convenio.indAbrangencia == "2"){					
						ufList = _this.convenio.abrangenciaUF;					
						for(var i = 0; i < ufList.length; i++) {
						    var opt = document.createElement('option');
						    opt.innerHTML = ufList[i].sgUF;
						    opt.value = ufList[i].sgUF;
						    abrangenciaObjUF.appendChild(opt);
						}
						_this.$el.find('#ufList').prop('disabled', false);
						_this.$el.find('#divAbrangenciaUf').show();
						_this.$el.find('#divAbrangenciaSr').hide();
						_this.adicionarRemoverRequiredUfList(document.getElementById("ufList").length);
						 
					}
					
					// SRs List
					var abrangenciaObjSR = document.getElementById("srList");				
					if(_this.convenio.indAbrangencia == "3"){					
						srList = _this.convenio.abrangenciaSR;					
						for(var i = 0; i < srList.length; i++) {
						    var opt = document.createElement('option');
						    opt.innerHTML = srList[i].unidade;
						    opt.value = srList[i].unidade;
						    abrangenciaObjSR.appendChild(opt);
						}
						_this.$el.find('#srList').prop('disabled', false);
						_this.$el.find('#divAbrangenciaUf').hide();
						_this.$el.find('#divAbrangenciaSr').show();
						_this.adicionarRemoverRequiredSRList(document.getElementById("srList").length);
					}				
					_this.$el.find('input[name="rdoAbrangencia"][value="'+_this.convenio.indAbrangencia+'"]').prop('checked', true);
					
					// valida codigo orgao
					if (_this.convenio.grupoAverbacao.id == 7) {
						_this.$el.find('#codigoOrgao').prop('disabled', false);
					}else {
						_this.$el.find('#codigoOrgao').prop('disabled', true);
					}
					
					// implementacao a conta corrente
					// Agencia
					 var agencia = _this.convenio.numAgenciaContaCredito.toString();
					if(agencia.length<4){
						 var adicionar = 4 - agencia.length;
						 for (var i = 0; i < adicionar; i++) agencia = '0' + agencia;
							_this.convenio.numAgenciaContaCredito = agencia; 
					 }
					 // Operador
					 var operador = _this.convenio.numOperacaoContaCredito.toString();
					 if(operador.length<3){
						 var adicionar = 3 - operador.length;
						 for (var i = 0; i < adicionar; i++) operador = '0' + operador;
							_this.convenio.numOperacaoContaCredito = operador; 
					 }
					 // ContaCorrente sem o digito
					 var conta = _this.convenio.numContaCredito.toString();
					 if(conta.length<8){
						 var adicionar = 8 - conta.length;
						 for (var i = 0; i < adicionar; i++) conta = '0' + conta;
						_this.convenio.numContaCredito = conta; 
					 }
					 
					 _this.convenio.contaCorrente =	_this.convenio.numAgenciaContaCredito+"."+ 
													_this.convenio.numOperacaoContaCredito+"."+
													_this.convenio.numContaCredito+"-"+												
													_this.convenio.numDvContaCredito;
					 	 
					 	var produtosCaixa = $.extend(true, [], _this.cliente.produtoCaixa);
						var contasCorrentesList = [];
						var agenciaConta;
												
						$.each(produtosCaixa, function (index, produtoCaixa) {				
							if (produtoCaixa == null){
								return true;					
							}else {
									agenciaConta =  produtoCaixa.contrato.substring(0,4)+"."+
													produtoCaixa.contrato.substring(4,7)+"."+
													produtoCaixa.contrato.substring(7,15)+"-"+
													produtoCaixa.contrato.substring(15);
									
									var obj ={};
									obj.id = index;
									obj.descricao = agenciaConta;
									contasCorrentesList.push(obj);
							}
						});
						
						_this.cliente.contasCorrentesLst = $.extend(true, [], contasCorrentesList);

						var contasCorrentes = [];
						
						//Verifica se a conta corrente salva na base do ccr esta contida na lista de conta correntes que retornam do SICLI 
					 	for (var int = 0; int < _this.cliente.contasCorrentesLst.length; int++) {
							
							if (_this.cliente.contasCorrentesLst[int].descricao === _this.convenio.contaCorrente) {
								
								var obj ={};
								obj.id = _this.cliente.contasCorrentesLst[int].id;
								obj.descricao = _this.convenio.contaCorrente;
								contasCorrentes.push(obj);							
								
							}						
						}
					 	
					 	// validacao para a combo de conta corrente
						var contaCorrenteCombo;
						if(contasCorrentes.length != 0) {
							contaCorrenteCombo = contasCorrentes[0].id;
						}else {
							contaCorrenteCombo = "";
						}
						
						// var i = 0;
						for(var i =0; i<_this.convenio.canais.length;i++){
							var canal = _this.convenio.canais[i];
							
							var indPermiteContratacao = canal.indPermiteContratacao;
							var indPermiteRenovacao = canal.indPermiteRenovacao;
							var indExigeAutorizacaoContrato =  canal.indAutorizaMargemContrato;
							var indExigeAnuencia = canal.indExigeAnuencia;
							var indAutorizaMargemRenovacao = canal.indAutorizaMargemRenovacao;
							var indSituacaoConvenioCanal = canal.indSituacaoConvenioCanal;
		 
							document.getElementById("radioIndPermiteContratacao_"+data.canais[i].canal.id+"_"+indPermiteContratacao+"").checked = "true";					
							document.getElementById("radioIndPermiteRenovacao_"+data.canais[i].canal.id+"_"+indPermiteRenovacao+"").checked = "true";	
							document.getElementById("radioExigeAutorizacaoContratacao_"+data.canais[i].canal.id+"_"+indExigeAutorizacaoContrato+"").checked = "true";
							document.getElementById("radioExigeAnuencia_"+data.canais[i].canal.id+"_"+indExigeAnuencia+"").checked = "true";
							document.getElementById("radioExigeAutorizacaoRenovacao_"+data.canais[i].canal.id+"_"+indAutorizaMargemRenovacao+"").checked = "true";
							document.getElementById("radioSituacaoConvenioCanal_"+data.canais[i].canal.id+"_"+indSituacaoConvenioCanal+"").checked = "true";
							
							if(_this.gestor != "true"){
								// disabilitar campos
								_this.$el.find('a[name="btnAlterarCanal_'+data.canais[i].canal.id+'"]').hide();
								_this.$el.find('a[name="btnExcluirCanal_'+data.canais[i].canal.id+'"]').hide();
								_this.$el.find('a[name="btnGravarCanal_'+data.canais[i].canal.id+'"]').hide();
								
								document.getElementById("radioIndPermiteContratacao_"+data.canais[i].canal.id+"_S").disabled = "true";					
								document.getElementById("radioIndPermiteRenovacao_"+data.canais[i].canal.id+"_S").disabled = "true";	
								document.getElementById("radioExigeAutorizacaoContratacao_"+data.canais[i].canal.id+"_S").disabled = "true";
								document.getElementById("radioExigeAnuencia_"+data.canais[i].canal.id+"_S").disabled = "true";
								document.getElementById("radioExigeAutorizacaoRenovacao_"+data.canais[i].canal.id+"_S").disabled = "true";
								document.getElementById("radioSituacaoConvenioCanal_"+data.canais[i].canal.id+"_S").disabled = "true";
								
								document.getElementById("radioIndPermiteContratacao_"+data.canais[i].canal.id+"_N").disabled = "true";					
								document.getElementById("radioIndPermiteRenovacao_"+data.canais[i].canal.id+"_N").disabled = "true";	
								document.getElementById("radioExigeAutorizacaoContratacao_"+data.canais[i].canal.id+"_N").disabled = "true";
								document.getElementById("radioExigeAnuencia_"+data.canais[i].canal.id+"_N").disabled = "true";
								document.getElementById("radioExigeAutorizacaoRenovacao_"+data.canais[i].canal.id+"_N").disabled = "true";
								document.getElementById("radioSituacaoConvenioCanal_"+data.canais[i].canal.id+"_N").disabled = "true";
							}
							
						}
						
						if(_this.gestor != "true"){
							// disabilitar campos se nao for gestor
							document.getElementById("situacaoIncluir").disabled = true;
							document.getElementById("btnIncluirCanal").style.display = 'none'
							document.getElementById("divIncluirCanais").style.display = 'none'
						}else{
							document.getElementById("situacaoIncluir").disabled = false;
							document.getElementById("btnIncluirCanal").style.display = 'block'
							document.getElementById("divIncluirCanais").style.display = 'block'
								
						}
						gCarregaComboContaCorrenteAlteraConvenio(_this.$el, "contasCorrentes", contaCorrenteCombo, _this.cliente.contasCorrentesLst);

									
					_this.$el.find('#divFiltroConvenente').hide();
					_this.$el.find('#divListaConvenio').hide();
					_this.$el.find('#divDetalhesConvenente').show();
					
					loadCCR.stop();
					$('#ajaxStatus').modal('hide');
				
				});
				
	
			})
			.error(function erro(jqXHR) {
				// (Alert("Errro"));
				msgCCR.alerta("Funcionalidade Indísponivel!!!", function () {});
			});
		
		},	
		
		novoConvenente: function (evt) {
			_this.validator.withForm('formFiltroConvenente').cleanErrors();
			
			// var index = _this.$el.find(evt.currentTarget).data('index');
			_this.cliente = new Cliente();
			_this.cliente = _this.cliente.attributes;
			_this.convenio = new Convenio();
			_this.convenio = _this.convenio.attributes;
			
			//limpa dados
			_this.convenio.cnpjConvenente = "";
			_this.convenio.nome = "";
			
			// Para iniciar a tela com as listas vazias
			_this.convenio.canais.shift();
			_this.convenio.abrangenciaUF.shift();
			_this.convenio.abrangenciaSR.shift();
			_this.convenio.convenioCNPJVinculado.shift();
			
			// Carrega as mascaras usadas.
			// loadMaskEl(_this.$el);
			listenToDatepickerChange(_this.$el, _this.changed);
			
			_this.limpar();
			
			_this.$el.find('#divDetalhesConvenente').html(_this.inclusaoConvenenteTemplate({sicli:_this.cliente, convenio:_this.convenio}));

			// carregas as combos usadas na inclusao
			_this.carregarCombosIncluirConvenenteNovo();			
			
			// tela inclusao
			_this.$el.find('#divFiltroConvenente').hide();
			_this.$el.find('#divListaConvenio').hide();
			_this.$el.find('#divDetalhesConvenente').show();
			_this.$el.find('#divAbrangenciaUf').hide();
			_this.$el.find('#divAbrangenciaSr').hide();			
			_this.$el.find('input[name="rdoAbrangencia"][value="1"]').prop('checked', true);
			_this.$el.find('#codigoOrgao').prop('disabled', true).val('');
			_this.$el.find('#contasCorrentes').prop('disabled', true);
			
			if(_this.gestor != "true"){
				document.getElementById("divIncluirCanais").style.display = 'none'
				document.getElementById("divCanais").style.display = 'none'
			}else{
				document.getElementById("divIncluirCanais").style.display = 'block'
				document.getElementById("divCanais").style.display = 'block'
			}
		
		},	
		
		carregarCombos: function (){
			var el = _this.$el;// .find('#divDetalheConvenente');
			gCarregarGrupoTaxa(el, "grupo", _this.convenio.grupo.id);
			gCarregarUF(el, "uf", _this.convenio.convenente.uf);			
			gCarregarSituacao(el, "situacao", _this.convenio.situacao.id, _this.convenio.situacao.tipo);
			gCarregarTaxaPadrao(el, "taxaJuroPadrao", "B");
			// gCarregarFormaAverbacao(el, "formaAverbacao",
			// _this.convenio.formaAverbacao); // VAI DAR ERRO
			gCarregarCanal(el, "idCanal");
		},
		
		carregarCombosIncluirConvenenteNovo: function (){
			
			var el = _this.$el.find('#divIncluirConvenente');		
			gCarregarGrupoTaxaConvenio(el, "grupo");
			gCarregarUFConvenio(el, "uf", _this.convenio.convenente.uf);
			gCarregarSituacaoConvenio(el, "situacaoIncluir", "", _this.convenio.situacao.tipo);
			gCarregarTaxaPadrao(el, "indFaixaJuroContratacao", "");
			gCarregarTaxaPadrao(el, "indFaixaJuroRenovacao", "");
			// gCarregarFormaAverbacao(el, "formaAverbacao",
			// _this.convenio.formaAverbacao);
			gCarregarGrupoAverbacaoConvenio(el, "grupoAverbacao");
			gCarregarCanalConvenio(el, "idCanal");
		},
		
		carregarCombosIncluirConvenente: function (){
			
			var el = _this.$el.find('#divIncluirConvenente');		
			gCarregarGrupoTaxaConvenio(el, "grupo", _this.convenio.grupo.id);
			gCarregarUFConvenio(el, "uf", _this.convenio.convenente.uf);
			gCarregarSituacaoConvenio(el, "situacaoIncluir", _this.convenio.situacao.id, _this.convenio.situacao.tipo);
			gCarregarTaxaPadrao(el, "indFaixaJuroContratacao", "");
			gCarregarTaxaPadrao(el, "indFaixaJuroRenovacao", "");
			// gCarregarFormaAverbacao(el, "formaAverbacao",
			// _this.convenio.formaAverbacao);
			gCarregarGrupoAverbacaoConvenio(el, "grupoAverbacao", _this.convenio.grupoAverbacao.id);
			gCarregarCanalConvenio(el, "idCanal");
		},
		
		
		carregarCombosAlterarConvenente: function (){
			
			var el = _this.$el.find('#divAlterarConvenente');		
			gCarregarGrupoTaxaConvenio(el, "grupo", _this.convenio.grupo.id);
			gCarregarUFConvenio(el, "uf", _this.convenio.convenente.uf);
			gCarregarTaxaPadrao(el, "indFaixaJuroContratacao", "");
			gCarregarTaxaPadrao(el, "indFaixaJuroRenovacao", "");
			gCarregarGrupoAverbacaoConvenio(el, "grupoAverbacao", _this.convenio.grupoAverbacao.id);
			gCarregarCanalConvenio(el, "idCanal");			
			gCarregarSituacaoConvenioAlterar(el, "situacaoIncluir", _this.convenio.situacao.id, _this.convenio.situacao.tipo);
		},
		
		
		consultarConvenente: function () {
			_this.consultarConvenio();
		},
		
		pad: function(str, length) {		
			  const resto = length - String(str).length;
			  return '0'.repeat(resto > 0 ? resto : '0') + str;
		},
		
		consultarConvenio : function() {

			if (_this.validator.withForm('formFiltroConvenente').validate()){
				var id = _this.$el.find('#id').val(),
			    	cnpj = _this.$el.find('#cnpj').val(),
			    	nome = _this.$el.find('#nome').val(),
			    	situacao = _this.$el.find('#situacao').val(),
			    	sr = _this.$el.find('#sr').val(),
			    	agencia = _this.$el.find('#agencia').val(),
			    	tipoConsulta = _this.$el.find('input[name="rdoIdentificador"]:checked').val();
				
				// validacoes a parte
				_this.$el.find('#cnpj').attr('validators', 'cnpj');
				if ($.trim(cnpj) == '' || _this.validator.withInput('cnpj').validate()) {					
					loadCCR.start();
					
					_this.getCollection().listar(tipoConsulta, id, cnpj, nome, situacao, sr, agencia)
					.done(function (data) {
						if (Retorno.validarRetorno(data)){							
							return;
						}

						if(data.idMsg === "MA0069") {
							Retorno.trataRetorno({codigo: -1, tipo: "", mensagem: "Nenhum filtro informado"}, 'convenio');
							loadCCR.stop();
							return;
						}
						
						_this.$el.find('#divListaConvenioShow').removeClass("hidden");
						
						// Inclui o template inicial no el deste controle
						_this.$el.find('#divListaConvenio').html(_this.manterListaConvenenteTemplate({Convenio: data.listaRetorno}));
										
						// configura datatable
						_this.$el.find('#gridListaConvenio').dataTable({
							'aoColumns' : [ null, null, null, null, null, null, { "bSortable": false }],
							'aaSorting': [],
							'oLanguage' : {'sEmptyTable' : 'Nenhum registro encontrado.', 'sLengthMenu': '_MENU_ registros por página', 'sInfoFiltered' : '(Filtrado _MAX_ do total de entradas)'},
							  
						});
  						
						loadCCR.stop();
					})
					.fail(function (jqXHR) {
						loadCCR.stop();
						Retorno.trataRetorno({codigo: -1, tipo: "ERRO_EXCECAO", mensagem: "Ocorreu um erro ao consultar o convenio!"}, 'convenio', jqXHR);
					});
					
					// desabilita os botoes
					_this.$el.find('a.disabled').on('click', function(evt) {
						evt.preventDefault();
						return false;
					});
					
					// volta validador
					_this.$el.find('#cnpj').attr('validators', 'formComposto');
				}
			}
			return _this;	// Buscar a lista do de contas correntes SICLI e inserir em
	
		},
		
	    getCollection: function () {
	    	if (_this.collection == null || this.collection == undefined)
	    		_this.collection = new ConvenioCollection();
	    		
	    	return _this.collection;
	    },		
		
		consultar: function (tipo) {
			if (_this.validator.withForm('formFiltroConvenente').validate()){
				var id = _this.$el.find('#id').val(),
			    	cnpj = _this.$el.find('#cnpj').val(),
			    	nome = _this.$el.find('#nome').val(),
			    	situacao = _this.$el.find('#situacao').val(),
			    	sr = _this.$el.find('#sr').val(),
			    	agencia = _this.$el.find('#agencia').val(),
			    	tipoConsulta = _this.$el.find('input[name="rdoIdentificador"]:checked').val();
				
				// validacoes a parte
				_this.$el.find('#cnpj').attr('validators', 'cnpj');
				if ($.trim(cnpj) == '' || _this.validator.withInput('cnpj').validate()) {					
					loadCCR.start();
					
					_this.getCollection().listar(tipoConsulta, id, cnpj, nome, situacao, sr, agencia)
					.done(function (data) {
						
						if (data == null) {
							Retorno.trataRetorno({codigo: 0, tipo: "AVISO", mensagem: "", idMsg: 'MA001'}, 'convenio');
							loadCCR.stop();
							return;
						}						
						
						if (!Retorno.trataRetorno(data, 'convenio', undefined, false)) {
							loadCCR.stop();
							return;
						}
						
						_this.convenio = data;
						_this.$el.find('#divListaConvenioShow').removeClass("hidden");
						
						if (tipo == 'C') {
							_this.$el.find('#divlistaConvenioTpl').html(_this.manterListaConvenenteTemplate({convenio: _this.convenio}));
							
						} else {
							_this.$el.find('#divDetalhesConvenente').html(_this.manterConvenenteTemplate({convenio: _this.convenio}));
							_this.carregarCombos();
							loadMask(_this.$el.find('#divDetalhesConvenente'));
						}							
						
						_this.$el.find('#divFiltroConvenente').hide();
						_this.$el.find('#divDetalhesConvenente').show();
						
						// chama tratamento compatibilide accordion
				        _this.compatibilidadeAccordion();
				        
				        // inicia aberto
				        _this.$el.find('#collapseTwo').show();
				        _this.$el.find('#collapseThree').show();
				        						
						loadCCR.stop();
					})
					.fail(function (jqXHR) {
						loadCCR.stop();
						Retorno.trataRetorno({codigo: -1, tipo: "ERRO_EXCECAO", mensagem: "Ocorreu um erro ao consultar o convenio!"}, 'convenio', jqXHR);
					});
					
					// desabilita os botoes
					_this.$el.find('a.disabled').on('click', function(evt) {
						evt.preventDefault();
						return false;
					});
					
					// volta validador
					_this.$el.find('#cnpj').attr('validators', 'formComposto');
				}
			}		
		},
				
		salvar : function () {
			if(_this.edit.length == 0){
				// Abrangencia
				var abrangencia = _this.$el.find('input[name="rdoAbrangencia"]:checked').val();
				
				if (_this.convenio.abrangenciaUF != null) {
					_this.convenio.abrangenciaUF.shift();
				}
				if (_this.convenio.abrangenciaSR != null) {
					_this.convenio.abrangenciaSR.shift();
				}
							
				_this.$el.find('#formInclusaoConvenio #cnpjConvenente').attr('validators', 'cnpj');
				
				if (_this.validator.withForm('panelDadosConvenente').validate()){	// validate
																					// do
																					// cnpj
					if($('#CNPJList').length > 0 && $('#CNPJText').val() == ""){
						_this.$el.find('#CNPJText').attr('validators', '');
						_this.$el.find('#CNPJList').removeClass("cnpj");
					}
					
					if (_this.validator.withForm('panelDadosConvenio').validate()){	// validate
																					// campos
																					// div
																					// convenio
						$('#situacaoIncluir').css('border-color', '#cccccc');
						$('#przEmissaoExtrato').css('border-color', '#cccccc');
					
						msgCCR.confirma(EMensagemCCR.manterIOF.MA0046, function() {
						
							loadCCR.start();
							
							_this.cliente = new Cliente();
							_this.cliente = _this.cliente.attributes;
							
							_this.recuperaDadosTela();				

							var canais = $.extend(true, [], _this.convenio.canais);
							var canaisFinal = [];
							
							$.each(canais, function (index, canal) {
								if (canal == null)
									return true; // continue
								
								canaisFinal.push(this);
							});
			
							if(_this.convenio.id > 0) {
								// alterar
								_this.convenio.canais = canaisFinal;
							}else{
								_this.convenio.canais = $.extend(true, [], canaisFinal);
								
							}
							_this.modelo = new Convenio();
							
							_this.convenio.cnpjConvenente = _this.convenio.cnpjConvenente != null ? String(_this.convenio.cnpjConvenente).replace(/[.\-\/]/g, '') : 0;
							
							// seta valores da lista de UFs para o objeto
							var ufs = document.getElementById("ufList");
							var srs = document.getElementById("srList");
							
							abrangenciaUFs = $.extend(true, [], "");			
							for (i = 0; i < ufs.length; i++) { 
								
								 var text = ufs.options[i].text;				 
								 abrangenciaUF = { 
									 sgUF: ufs.options[i].text
								 }				 
								 abrangenciaUFs.push(abrangenciaUF);				 
							}
							_this.convenio.abrangenciaUF = $.extend(true, [], abrangenciaUFs);
							
							// seta valores da lista de SRs para o objeto
							abrangenciaSRs = $.extend(true, [], "");			
							for (i = 0; i < srs.length; i++) { 
								 
								 var text = srs.options[i].text;				 
								 abrangenciaSR = { 
									unidade: srs.options[i].text
								 }				 
								 abrangenciaSRs.push(abrangenciaSR);				 
							}
							_this.convenio.abrangenciaSR = $.extend(true, [], abrangenciaSRs);
							
							
							var CNPJList = document.getElementById("CNPJList");
							// seta valores da lista de CNPJs para o objeto
							CNPJs = $.extend(true, [], {});			
							for (i = 0; i < (CNPJList.length); i++) { 
								
								var text = CNPJList.options[i].text.replace(/[_.\-\/]/g, '');			 
								if (text != 0) {
									CNPJ = { 
											nuConvenio: text,
											nuCNPJ: text
											 
										 }				 
										 CNPJs.push(CNPJ);
								}	// Buscar a lista do de contas correntes SICLI e inserir em			 
							}
							
							/**********************************************************************************
							 * Aqui serão realmente persistido as auditorias de exclusão e alteração
							 * ********************************************************************************/
							_this.modelo = new Convenio();
							if(_this.canaisExcluidos.length > 0) {
								for(var i=0; i < _this.canaisExcluidos.length; i++) {
									_this.modelo.auditoriaCanalPersiste(_this.canaisExcluidos[i])
									.done(function (data) {
										console.log(data);
									});
								}
							}
							
							if(_this.canaisAlteracao.length > 0) {
								for(var i=0; i < _this.canaisAlteracao.length; i++) {
									_this.modelo.auditoriaCanalPersiste(_this.canaisAlteracao[i])
									.done(function (data) {
										console.log(data);
									});
								}
							}
							/**********************************************************************************
							 * Fim persistência exclusão e alteração de auditorias para canal
							 * ********************************************************************************/
							
							_this.convenio.convenioCNPJVinculado = $.extend(true, [], CNPJs);

							//metodo que valida o CNPJ no SICLI
							_this.validarCNPJ(_this.convenio.cnpjConvenente);						
													
							_this.modelo.salvar(_this.convenio)
							.done(function (data) {
								

								if (data == null) {
									Retorno.trataRetorno({codigo: 0, tipo: "AVISO", mensagem: "", idMsg: 'MA001'}, 'convenio');
									loadCCR.stop();
									return;
								}						
								
								if(data.idMsg == "MA0010"){
									Retorno.trataRetorno({codigo: 0, tipo: "AVISO", mensagem: "", idMsg: 'MA0010'}, 'convenio');
									loadCCR.stop();
									return;
								}
								
								if(data.idMsg == "MA0072"){
									$('#situacaoIncluir').css('border-color', 'red');
									$('#przEmissaoExtrato').css('border-color', '#cccccc');
								}
								
								
								if(data.idMsg == "MA0073"){
									$('#przEmissaoExtrato').css('border-color', 'red');
									$('#situacaoIncluir').css('border-color', '#cccccc');
								}
								
								_this.convenio = _this.modelo.defaults;
								
								if(data.tipoRetorno == "SUCESSO"){
									
									/**********************************************************************************
									 * Aqui serão realmente persistido a auditoria de inclusão
									 * ********************************************************************************/
									if(_this.canaisInclusao.length > 0) {
										for(var i=0; i < _this.canaisInclusao.length; i++) {
											for(var j=0; j < _this.canaisInclusao[i].length; j++) {
												_this.canaisInclusao[i][j].nuConvenio.id = _this.convenio.id;
											}
											_this.modelo.auditoriaCanalPersiste(_this.canaisInclusao[i])
											.done(function (data) {
												console.log(data);
											});
										}
									}
									/**********************************************************************************
									 * Fim persistência inclusão de auditoria para canal
									 * ********************************************************************************/
									
									var codRetorno = data.codigoRetorno;
									data.codigoRetorno = 0;
									
									if (Retorno.trataRetorno(data, 'convenio', undefined)) {
										loadCCR.stop();
										//_this.voltar();
									}
									DashboardControleCCR.detalharConvenenteControle(codRetorno);
								}else {
									Retorno.trataRetorno(data, 'convenio', undefined);
								}
								
								loadCCR.stop();
								
							})
							.fail(function (jqXHR) {
								loadCCR.stop();
								Retorno.trataRetorno({codigo: -1, tipo: "ERRO_EXCECAO", mensagem: "Ocorreu um erro ao salvar o convenio!"}, 'convenio', jqXHR);
							});
						});
					}
				}	
			}else{
				for (i = 0; i < _this.edit.length; i++) {   
				    $("#btnGravarCanal_"+_this.edit[i]).attr("style", "border-color: #b94a48; border-width: 1px;");	
					$("#labelCanalObrigatorio_"+_this.edit[i]).removeAttr("style", "display:none");
					$("#labelCanalObrigatorio_"+_this.edit[i]).attr("style", "color: #b94a48;font-size: 12px;");
				    
				}
			}
		},
		
		recuperaDadosTela: function () {
			// nomeConvenio
			_this.convenio.nome = $('#nomeConvenio').val();		
			
			// var cnpjConvenente = $('#cnpjConvenente').val();
			_this.convenio.convenente.cnpj = $('#cnpjConvenente').val();
			_this.convenio.cnpjConvenente = $('#cnpjConvenente').val();			
			
			// seta grupo
			_this.convenio.grupo.id = $('#grupo').val();
			_this.convenio.grupo.nome = _this.$el.find('#grupo').find(':selected').text();
			
			// seta grupoAverbacao
			_this.convenio.grupoAverbacao.id = $('#grupoAverbacao').val();
			_this.convenio.grupoAverbacao.nome = _this.$el.find('#grupoAverbacao').find(':selected').text();
			
			// seta situacao
			_this.convenio.situacao.id = $('#situacaoIncluir').val();
			_this.convenio.situacao.descricao = _this.$el.find('#situacaoIncluir').find(':selected').text();
			
			// Abrangencia
			_this.convenio.indAbrangencia = _this.$el.find('input[name="rdoAbrangencia"]:checked').val();
			 			
			// Conta corrente contasCorrentes
			var agenciaConta = _this.$el.find('#contasCorrentes').find(':selected').text()			
			_this.convenio.numAgenciaContaCredito = agenciaConta.substring(0,4);
			_this.convenio.numOperacaoContaCredito = agenciaConta.substring(5,8);
			_this.convenio.numContaCredito = agenciaConta.substring(9,17);
			_this.convenio.numDvContaCredito = agenciaConta.substring(18);
			
			// Outros campos
			_this.convenio.numPvResponsavel = $('#numPvResponsavel').val();
			_this.convenio.codigoOrgao = $('#codigoOrgao').val();
			_this.convenio.numSprnaResponsavel = $('#numSprnaResponsavel').val();				
			_this.convenio.numSprnaCobranca = $('#numSprnaCobranca').val();				
			_this.convenio.numPvCobranca = $('#numPvCobranca').val();
			_this.convenio.diaCreditoSal = $('#diaCreditoSal').val();				
			_this.convenio.przEmissaoExtrato = $('#przEmissaoExtrato').val();
			_this.convenio.przMaximoRenovacao = $('#przMaximoRenovacao').val();
//			_this.convenio.przVigenciaConvenio = $('#przVigenciaConvenio').val();
			_this.convenio.qtEmpregado = $('#qtdeEmpregados').val();
			_this.convenio.qtDiaAguardarAutorizacao = $('#qtDiaAguardarAutorizacao').val();
			
		},
		
		limpar: function () {
			// 1
			_this.$el.find('#id').val("");
			_this.$el.find('#nome').val("");
			_this.$el.find('#situacao').val("");
			
			// 2
			_this.$el.find('#cnpj').val("");

			// 3
			_this.$el.find('#sr').val("");
			_this.$el.find('#agencia').val("");
			
			_this.$el.find('#divListaConvenioShow').addClass("hidden");
			
		},		
		
		incluirCanal: function () {
			$('#idCanal').css('border-color', '#cccccc');
			
			_this.modelo =  new Convenio();
			
			var permiteContratacao =  _this.$el.find('input[name="canais.indPermiteContratacao"]:checked').val();
			var permiteRenovacao = _this.$el.find('input[name="canais.indPermiteRenovacao"]:checked').val();
			if((permiteContratacao != "N") || (permiteRenovacao != "N")){
				if (_this.validator.withForm('divIncluirCanais').validate()){	
					// recupera dados da tela
					_this.recuperaDadosTela();
					
					var ufs = document.getElementById("ufList");
					var srs = document.getElementById("srList");
					var CNPJs = document.getElementById("CNPJList");
					
					var index = _this.convenio.canais.length, canais = [], canal = {};
					canais = $.extend(true, [], _this.convenio.canais);
					
					if (_this.$el.find('#cnpjConvenente').val() == "") {
						msgCCR.alerta("CNPJ do Convenente obrigat&oacute;rio!", function () {});
						return;
					}else if (_this.$el.find('#idCanal').val() == "") {
						$('#idCanal').css('border-color', 'red');
						msgCCR.alerta("O canal &eacute; obrigat&oacute;rio!", function () {});
						return;
					} else {
						var isCanalCarregado = false;
						$.map(_this.convenio.canais, function (value, key) { 
							if (value == null)
								return true; // continue
							
							if (value.canal.id == _this.$el.find('#idCanal').val()) { 
								isCanalCarregado = true;
								return false; // break
							} 
						});
						
						if (isCanalCarregado){
							msgCCR.alerta("Canal j&aacute; informado! Selecione outro canal.", function () {});
							return;
						}
					}
					
					// recupera dados da tela e insere um novo canal
					canal = {
						canal: { id: _this.$el.find('#idCanal').val(), nome: _this.$el.find('#idCanal').find(':selected').text() },
						
						indPermiteContratacao: _this.$el.find('input[name="canais.indPermiteContratacao"]:checked').val(), // Permite // Contratação
						indPermiteRenovacao: _this.$el.find('input[name="canais.indPermiteRenovacao"]:checked').val(), // Permite // Renovação
						qtdDiaGarantiaCondicao: _this.$el.find('#qtdDiaGarantiaCondicao').val(), // Garantia // Condições
						
						indAutorizaMargemContrato: _this.$el.find('input[name="canais.indAutorizaMargemContrato"]:checked').val(), // Exige// Autorização// Margem// Contrataçao
						indAutorizaMargemRenovacao: _this.$el.find('input[name="canais.indAutorizaMargemRenovacao"]:checked').val(), // Exige// Autorização// Margem// Renovaçao
						indExigeAutorizacaoContrato: _this.$el.find('input[name="canais.indExigeAutorizacaoContrato"]:checked').val(), // Exige// Anuência
						indSituacaoConvenioCanal: _this.$el.find('input[name="canais.indSituacaoConvenioCanal"]:checked').val(), // Ativo //Inativo
						
						przMinimoContratacao: _this.$el.find('#przMinimoContratacao').val(), // Contratacao// Prazo// Mínimo
						przMinimoRenovacao: _this.$el.find('#przMinimoRenovacao').val(), // Renovacao// Prazo// Mínimo
						
						przMaximoContratacao: _this.$el.find('#przMaximoContratacao').val(), // Contratacao// Prazo// Maximo
						przMaximoRenovacao: _this.$el.find('#przMaximoRenovacao').val(), // Renovacao// Prazo// Maximo
						indFaixaJuroContratacao: _this.$el.find('#indFaixaJuroContratacao').find(':selected').text() == "Selecione" ? "" : _this.$el.find('#indFaixaJuroContratacao').find(':selected').text(), // Faixas// Juros// Padrão
						indFaixaJuroRenovacao: _this.$el.find('#indFaixaJuroRenovacao').find(':selected').text() =="Selecione" ? "" : _this.$el.find('#indFaixaJuroRenovacao').find(':selected').text(), // Faixas// Juros// Padrão
						indExigeAnuencia: "N"				
					};
					
					/**
					 * Esta chamada para inclusão do Canal é responsável por
					 * criar auditoria de inclusão de um Canal e deixa-la preparada,
					 * para na hora de salvar o convênio ele realmente persistir as 
					 * auditorias criadas. Tentar executar tudo direto no Salvar seria 
					 * muito complexo, então, para simplificar foi feito desta maneira
					 * ao acionar o botão de incluir canal.
					 */
					canal.idConvenio = _this.convenio.id;
					if(canal.idConvenio == 0) {
						canal.convenio = _this.convenio;
						canal.convenio.convenente.cnpj = canal.convenio.convenente.cnpj.replace(/\D/g, '');
						canal.convenio.cnpjConvenente = canal.convenio.cnpjConvenente.replace(/\D/g, '');
						console.log(canal.convenio);
					}
					_this.modelo.salvarCanal(canal)
					.done(function (data) {
						console.log(data);
						_this.canaisInclusao.push(data);
						_this.canaisInclusao = $.extend(true, _this.canaisInclusao, _this.canaisInclusao);
					});
					
					canais.push(canal);
					_this.convenio.canais = $.extend(true, [], canais);
					
					// Recupera valores canais
					var opIdentificador = _this.$el.find('input[name="rdoAbrangencia"]:checked').val();
					var idcanal = _this.$el.find('#idCanal').val();			
					var validaPermiteContrataca = _this.$el.find('input[name="canais.indPermiteContratacao"]:checked').val();			
					var validaPermiteRenovacao = _this.$el.find('input[name="canais.indPermiteRenovacao"]:checked').val();			
					var validaAutorizaMargemContrato = _this.$el.find('input[name="canais.indAutorizaMargemContrato"]:checked').val();
					var validaAutorizaMargemRenovacao = _this.$el.find('input[name="canais.indAutorizaMargemRenovacao"]:checked').val();
					var validaExigeAutorizacaoContrato = _this.$el.find('input[name="canais.indExigeAutorizacaoContrato"]:checked').val();	
					
					_this.$el.find('#divDetalhesConvenente').html(_this.inclusaoConvenenteTemplate({sicli:_this.cliente, convenio:_this.convenio}));		 		
					
					if(_this.gestor != "true"){
						document.getElementById("divIncluirCanais").style.display = 'none'
						document.getElementById("divCanais").style.display = 'none'
					}else{
						document.getElementById("divIncluirCanais").style.display = 'block'
						document.getElementById("divCanais").style.display = 'block'
					}			
					
					// carrega combos da tela
					_this.carregarCombosIncluirConvenente();
					
					// Combo conta corrente
					// implementacao da conta corrente
					// Agencia
					 var agencia = _this.convenio.numAgenciaContaCredito.toString();
					if(agencia.length<4){
						 var adicionar = 4 - agencia.length;
						 for (var i = 0; i < adicionar; i++) agencia = '0' + agencia;
							_this.convenio.numAgenciaContaCredito = agencia; 
					 }
					 // Operador
					 var operador = _this.convenio.numOperacaoContaCredito.toString();
					 if(operador.length<3){
						 var adicionar = 3 - operador.length;
						 for (var i = 0; i < adicionar; i++) operador = '0' + operador;
							_this.convenio.numOperacaoContaCredito = operador; 
					 }
					 // ContaCorrente sem o digito
					 var conta = _this.convenio.numContaCredito.toString();
					 if(conta.length<8){
						 var adicionar = 8 - conta.length;
						 for (var i = 0; i < adicionar; i++) conta = '0' + conta;
						_this.convenio.numContaCredito = conta; 
					 }
					 
					 _this.convenio.contaCorrente =	_this.convenio.numAgenciaContaCredito+"."+ 
													_this.convenio.numOperacaoContaCredito+"."+
													_this.convenio.numContaCredito+"-"+												
													_this.convenio.numDvContaCredito;
					
					
					for (var int = 0; int < _this.cliente.contasCorrentesLst.length; int++) {
						
						if (_this.cliente.contasCorrentesLst[int].descricao == _this.convenio.contaCorrente) {
							
							var contasCorrentes = [];
							var obj ={};
							obj.id = _this.cliente.contasCorrentesLst[int].id;
							obj.descricao = _this.convenio.contaCorrente;
							contasCorrentes.push(obj);
						}						
					}
					
					// validacao para a combo de conta corrente
					var contaCorrenteCombo;
					if (contasCorrentes != null) {
						contaCorrenteCombo = contasCorrentes[0].id;
					}else {
						contaCorrenteCombo = "";
					}
					
					gCarregaComboContaCorrenteAlteraConvenio(_this.$el, "contasCorrentes", contaCorrenteCombo , _this.cliente.contasCorrentesLst);
					
					if (_this.convenio.grupoAverbacao.id == '7'){
				    		_this.$el.find('#codigoOrgao').prop('disabled', false).attr('validators', 'required');
			    	}else {
			    		_this.$el.find('#codigoOrgao').prop('disabled', true).attr('validators', '');
			    	}
					
					for(var i =0; i<_this.convenio.canais.length;i++){
						var canal = _this.convenio.canais[i];
						
						document.getElementById("radioIndPermiteContratacao_"+_this.convenio.canais[i].canal.id+"_"+_this.convenio.canais[i].indPermiteContratacao+"").checked = "true";
						document.getElementById("radioIndPermiteContratacao_"+_this.convenio.canais[i].canal.id+"_"+_this.convenio.canais[i].indPermiteContratacao+"").disabled = "true";
										
						document.getElementById("radioIndPermiteRenovacao_"+_this.convenio.canais[i].canal.id+"_"+_this.convenio.canais[i].indPermiteRenovacao+"").checked = "true";
						document.getElementById("radioIndPermiteRenovacao_"+_this.convenio.canais[i].canal.id+"_"+_this.convenio.canais[i].indPermiteRenovacao+"").disabled = "true";
						
						document.getElementById("radioExigeAutorizacaoContratacao_"+_this.convenio.canais[i].canal.id+"_"+_this.convenio.canais[i].indAutorizaMargemContrato+"").checked = "true";
						document.getElementById("radioExigeAutorizacaoContratacao_"+_this.convenio.canais[i].canal.id+"_"+_this.convenio.canais[i].indAutorizaMargemContrato+"").disabled = "true";
						
						document.getElementById("radioExigeAutorizacaoRenovacao_"+_this.convenio.canais[i].canal.id+"_"+_this.convenio.canais[i].indAutorizaMargemRenovacao+"").checked = "true";
						document.getElementById("radioExigeAutorizacaoRenovacao_"+_this.convenio.canais[i].canal.id+"_"+_this.convenio.canais[i].indAutorizaMargemRenovacao+"").disabled = "true";
						
						document.getElementById("radioExigeAnuencia_"+_this.convenio.canais[i].canal.id+"_"+_this.convenio.canais[i].indExigeAutorizacaoContrato+"").checked = "true";
						document.getElementById("radioExigeAnuencia_"+_this.convenio.canais[i].canal.id+"_"+_this.convenio.canais[i].indExigeAutorizacaoContrato+"").disabled = "true";
						
						document.getElementById("radioSituacaoConvenioCanal_"+_this.convenio.canais[i].canal.id+"_"+_this.convenio.canais[i].indSituacaoConvenioCanal+"").checked = "true";
						document.getElementById("radioSituacaoConvenioCanal_"+_this.convenio.canais[i].canal.id+"_"+_this.convenio.canais[i].indSituacaoConvenioCanal+"").disabled = "true";
					}
									
					_this.$el.find('input[name="rdoAbrangencia"][value='+opIdentificador+']').prop('checked', true);			
					if (opIdentificador == 2) { // UF
						// Recarrega lista UFs
						$('#ufList option').remove();					
						for (i = 0; i < ufs.length; i++) { 
							 var text = ufs.options[i].text;						 
							 var option = new Option(text,text);
							 $('#ufList').append(option);				 
						 }
						
						_this.$el.find('#divAbrangenciaUf').show();
						_this.$el.find('#ufList').prop('disabled', false).attr('validators', '');
						_this.$el.find('#divAbrangenciaSr').hide();
						_this.$el.find('#srList').prop('disabled', true).attr('validators', '');
					}else if (opIdentificador == 3){ // SR
						// Recarrega lista SRs
						$('#srList option').remove();
						
						for (i = 0; i < srs.length; i++) { 
							
							 var text = srs.options[i].text;						 
							 var option = new Option(text,text);
							 $('#srList').append(option);				 
						 }					
						
						_this.$el.find('#divAbrangenciaUf').hide();
						_this.$el.find('#ufList').prop('disabled', true).attr('validators', '');
						_this.$el.find('#divAbrangenciaSr').show();
						_this.$el.find('#srList').prop('disabled', false).attr('validators', '');					
						
					}else {
						_this.$el.find('#divAbrangenciaUf').hide();
						_this.$el.find('#ufList').prop('disabled', true).attr('validators', '');
						_this.$el.find('#divAbrangenciaSr').hide();
						_this.$el.find('#srList').prop('disabled', true).attr('validators', '');
					}

					// Recarrega lista CNPJs
					$('#CNPJList option').remove();
					
					for (i = 0; i < CNPJs.length; i++) { 
						
						 var text = CNPJs.options[i].text;						 
						 var option = new Option(text,text);
						 $('#CNPJList').append(option);				 
					 }
				}else{
					$('#idCanal').css('border-color', 'red');
				}
			}else{
				Retorno.trataRetorno({codigo: 1, tipo: "ERRO_NEGOCIAL", mensagem: "", idMsg: 'MA0101'}, 'convenio');
			}
		},
		
		excluirCanal: function (evt) {
			
			// recupera dados da tela
			_this.recuperaDadosTela();
			_this.modelo = new Convenio();
			_this.convenioCanal = [];
			_this.convenioCanal.idCanal = _this.$el.find(evt.currentTarget).data('index');
			_this.convenioCanal.nuConvenio = _this.convenio.id; 
			
			/**
			 * Esta chamada para exclusão do Canal é responsável por
			 * criar auditoria de exclusão de um Canal e deixa-la preparada,
			 * para na hora de salvar o convênio ele realmente persistir as 
			 * auditorias criadas. Tentar executar tudo direto no Salvar seria 
			 * muito complexo, então, para simplificar foi feito desta maneira
			 * ao acionar o botão de excluir.
			 */
			_this.modelo.excluir(_this.convenioCanal)
			.done(function (data) {
				_this.canaisExcluidos.push(data);
				_this.canaisExcluidos = $.extend(true, _this.canaisExcluidos, _this.canaisExcluidos);
			});
			
			
			var ufs = document.getElementById("ufList");
			var srs = document.getElementById("srList");
			var CNPJs = document.getElementById("CNPJList");
			
			var canalId = _this.$el.find(evt.currentTarget).data('index');			
			var canais = $.extend(true, [], _this.convenio.canais);
			var canaisFinal = [];
			canaisFinal = canais;
			
			$.each(canais, function (index, canal) {				
				if (canal == null){
					return true;					
				}else {
					if ( canal.canal.id == canalId) {
						delete canaisFinal[index];
						Retorno.trataRetorno({codigo: 0, tipo: "SUCESSO", mensagem: "", idMsg: 'MA006'}, 'convenio');
					}
				}
			});

			_this.convenio.canais = $.extend(true, [], canaisFinal);
			
			// Recupera valores canais
			var opIdentificador = _this.$el.find('input[name="rdoAbrangencia"]:checked').val();
			var idcanal = _this.$el.find('#idCanal').val();			
			var validaPermiteContrataca = _this.$el.find('input[name="canais.indPermiteContratacao"]:checked').val();			
			var validaPermiteRenovacao = _this.$el.find('input[name="canais.indPermiteRenovacao"]:checked').val();			
			var validaAutorizaMargemContrato = _this.$el.find('input[name="canais.indAutorizaMargemContrato"]:checked').val();
			var validaAutorizaMargemRenovacao = _this.$el.find('input[name="canais.indAutorizaMargemRenovacao"]:checked').val();
			var validaExigeAutorizacaoContrato = _this.$el.find('input[name="canais.indExigeAutorizacaoContrato"]:checked').val();	
			
			_this.$el.find('#divDetalhesConvenente').html(_this.inclusaoConvenenteTemplate({sicli:_this.cliente, convenio:_this.convenio}));			
			
			// carrega combos da tela
			_this.carregarCombosIncluirConvenente();
			
			// Combo conta corrente
			// implementacao da conta corrente
			for (var int = 0; int < _this.cliente.contasCorrentesLst.length; int++) {
				
				if (_this.cliente.contasCorrentesLst[int].descricao == _this.convenio.contaCorrente) {
					
					var contasCorrentes = [];
					var obj ={};
					obj.id = _this.cliente.contasCorrentesLst[int].id;
					obj.descricao = _this.convenio.contaCorrente;
					contasCorrentes.push(obj);
				}						
			}
			
			// validacao para a combo de conta corrente
			var contaCorrenteCombo;
			if (contasCorrentes != null) {
				contaCorrenteCombo = contasCorrentes[0].id;
			}else {
				contaCorrenteCombo = "";
			}
						
			gCarregaComboContaCorrente(_this.$el, "contasCorrentes", contaCorrenteCombo, _this.cliente.contasCorrentesLst);
			
			if (_this.convenio.grupoAverbacao.id == '7'){
	    		_this.$el.find('#codigoOrgao').prop('disabled', false).attr('validators', 'required');
	    	}else {
	    		_this.$el.find('#codigoOrgao').prop('disabled', true).attr('validators', '');
	    	}
			
			// Seta valores canais
			for(var i =0; i<_this.convenio.canais.length;i++){
				var canal = _this.convenio.canais[i]; 
				
				if (canal == null){
					continue;					
				}
				
				document.getElementById("radioIndPermiteContratacao_"+_this.convenio.canais[i].canal.id+"_"+_this.convenio.canais[i].indPermiteContratacao+"").checked = "true";
				document.getElementById("radioIndPermiteContratacao_"+_this.convenio.canais[i].canal.id+"_"+_this.convenio.canais[i].indPermiteContratacao+"").disabled = "true";
								
				document.getElementById("radioIndPermiteRenovacao_"+_this.convenio.canais[i].canal.id+"_"+_this.convenio.canais[i].indPermiteRenovacao+"").checked = "true";
				document.getElementById("radioIndPermiteRenovacao_"+_this.convenio.canais[i].canal.id+"_"+_this.convenio.canais[i].indPermiteRenovacao+"").disabled = "true";
				
				document.getElementById("radioExigeAutorizacaoContratacao_"+_this.convenio.canais[i].canal.id+"_"+_this.convenio.canais[i].indAutorizaMargemContrato+"").checked = "true";
				document.getElementById("radioExigeAutorizacaoContratacao_"+_this.convenio.canais[i].canal.id+"_"+_this.convenio.canais[i].indAutorizaMargemContrato+"").disabled = "true";
				
				document.getElementById("radioExigeAutorizacaoRenovacao_"+_this.convenio.canais[i].canal.id+"_"+_this.convenio.canais[i].indAutorizaMargemRenovacao+"").checked = "true";
				document.getElementById("radioExigeAutorizacaoRenovacao_"+_this.convenio.canais[i].canal.id+"_"+_this.convenio.canais[i].indAutorizaMargemRenovacao+"").disabled = "true";
				
				document.getElementById("radioExigeAnuencia_"+_this.convenio.canais[i].canal.id+"_"+_this.convenio.canais[i].indExigeAutorizacaoContrato+"").checked = "true";
				document.getElementById("radioExigeAnuencia_"+_this.convenio.canais[i].canal.id+"_"+_this.convenio.canais[i].indExigeAutorizacaoContrato+"").disabled = "true";
			}
			
			_this.$el.find('input[name="rdoAbrangencia"][value='+opIdentificador+']').prop('checked', true);			
			if (opIdentificador == 2) { // UF
				
				// Recarrega lista UFs
				$('#ufList option').remove();
				
				for (i = 0; i < ufs.length; i++) { 
					
					 var text = ufs.options[i].text;						 
					 var option = new Option(text,text);
					 $('#ufList').append(option);				 
				}
				
				_this.$el.find('#divAbrangenciaUf').show();
				_this.$el.find('#ufList').prop('disabled', false).attr('validators', '');
				_this.$el.find('#divAbrangenciaSr').hide();
				_this.$el.find('#srList').prop('disabled', true).attr('validators', '');
				
			}else if (opIdentificador == 3){ // SR
				
				// Recarrega lista SRs
				$('#srList option').remove();
				
				for (i = 0; i < srs.length; i++) { 
					
					 var text = srs.options[i].text;						 
					 var option = new Option(text,text);
					 $('#srList').append(option);				 
				}
				
				_this.$el.find('#divAbrangenciaUf').hide();
				_this.$el.find('#ufList').prop('disabled', true).attr('validators', '');
				_this.$el.find('#divAbrangenciaSr').show();
				_this.$el.find('#srList').prop('disabled', false).attr('validators', '');
				
			}else {
				_this.$el.find('#divAbrangenciaUf').hide();
				_this.$el.find('#ufList').prop('disabled', true).attr('validators', '');
				_this.$el.find('#divAbrangenciaSr').hide();
				_this.$el.find('#srList').prop('disabled', true).attr('validators', '');
			}
			
			
			// Recarrega lista CNPJs
			$('#CNPJList option').remove();
			
			for (i = 0; i < CNPJs.length; i++) { 
				
				 var text = CNPJs.options[i].text;						 
				 var option = new Option(text,text);
				 $('#CNPJList').append(option);				 
			 }
			
			if(_this.gestor != "true"){
				document.getElementById("divIncluirCanais").style.display = 'none'
				document.getElementById("divCanais").style.display = 'none'
			}else{
				document.getElementById("divIncluirCanais").style.display = 'block'
				document.getElementById("divCanais").style.display = 'block'
			}
			
			
		},
		
		redefineAbrangencia : function() {
			console.log('redefineAbrangencia - inicio');			
			var opIdentificador = _this.$el.find('input[name="rdoAbrangencia"]:checked').val();
		
			switch (opIdentificador) {
			case '1': // Nacional - radioAbrangenciaNacional
				console.log('Nacional selecionado...');
				_this.$el.find('#divAbrangenciaUf').hide();
				_this.$el.find('#divAbrangenciaSr').hide();
				break;
			case '2': // UF - divAbrangenciaUf - radioAbrangenciaUf
				console.log('UF selecionado...');				
				// _this.validator.withForm('formFiltroConvenente').cleanErrors();
				_this.$el.find('#divAbrangenciaUf').show();
				_this.$el.find('#ufList').prop('disabled', false).attr('validators', 'required');
				_this.$el.find('#divAbrangenciaSr').hide();
				_this.$el.find('#srList').prop('disabled', true).attr('validators', '');
				break;				
			case '3': // SR - divAbrangenciaSr - radioAbrangenciaSr
				console.log('SR selecionado...');				
				// _this.validator.withForm('formFiltroConvenente').cleanErrors();
				_this.$el.find('#divAbrangenciaUf').hide();
				_this.$el.find('#ufList').prop('disabled', true).attr('validators', '');
				_this.$el.find('#divAbrangenciaSr').show();
				_this.$el.find('#srList').prop('disabled', false).attr('validators', 'required');
				break;				
			default:
				break;
			}	
			console.log('redefineAbrangencia - fim');
		},
		
		adicionarRemoverRequiredCNPJList: function (num) {
			console.log(num);
			if (num == 0) {
				_this.$el.find('#CNPJList').attr('validators', '');
			} else {
				_this.$el.find('#CNPJList').attr('validators', '');
			}
		},
		
		adicionarRemoverRequiredUfList: function (num) {
			console.log(num);
			if (num == 0) {
				_this.$el.find('#ufList').attr('validators', 'required');
			} else {
				_this.$el.find('#ufList').attr('validators', '');
			}
		},
		
		adicionarRemoverRequiredSRList: function (num) {
			console.log(num);
			if (num == 0) {
				_this.$el.find('#srList').attr('validators', 'required');
			} else {
				_this.$el.find('#srList').attr('validators', '');
			}
		},
		
		incluirUF: function (e) {
			_this = this;
			 var option = new Option(_this.$el.find('#uf').find(':selected').text(),_this.$el.find('#uf').find(':selected').text());
			 var uflist = document.getElementById("ufList");
			 var validaUFInserida = 0;
			 var selecionado = _this.$el.find('#uf').find(':selected').text();
			 
			 if (uflist.length == 0) {
				 if (selecionado != "Selecione") {
					 $('#ufList').append(option);
				}				 
			 }else {
				 for (i = 0; i < uflist.length; i++) { 
						
					 var text = uflist.options[i].text;
					 
					 if (_this.$el.find('#uf').find(':selected').text() == text) {
						validaUFInserida = 1;
					 }				 
				 }
			 }
			 			 
			 if (validaUFInserida == 0) {
				 
				 if (selecionado != "Selecione") {
					 $('#ufList').append(option);
				 }
				 
			 }else {
				 msgCCR.alerta("UF j&aacute foi informado!", function () {});
			 }
			 _this.adicionarRemoverRequiredUfList(document.getElementById("ufList").length);
			 
		},
		
		excluirUF: function (e) {
			var uf = $("#ufList").val();
			$.each(uf, function (item, value) {
				$("#ufList option[value="+value+"]").remove();
			});
			_this.adicionarRemoverRequiredUfList(document.getElementById("ufList").length);
		},
		
		incluirSr: function (e) {
			
			_this = this;
			 var option1 = parseInt($('#srText').val());
			 var option2 = parseInt($('#srText').val());
			 var option = new Option(option1,option2);
			 var srlist = document.getElementById("srList");
			 var srValue = option1;
			 var validaSRInserida = 0;
			 
			 _this.modelo.consultarSR(srValue).done(function sucesso(data) {
					// trta retorno em caso de erro
					if (data.idMsg == "MA0059") {
						Retorno.trataRetorno({codigo: 1, tipo: "AVISO", mensagem: "", idMsg: 'MA0059'}, 'convenio');
						return;
					}else{
						 if (srlist.length == 0) {
							 $('#srList').append(option);
						 }else {
							 for (i = 0; i < srlist.length; i++) { 
									
								 var text = srlist.options[i].text;
								 if (option1 == text) {
									validaSRInserida = 1;
								 }				 
							 }
						 }
						 			 
						 if (validaSRInserida == 0) {
							 $('#srList').append(option);
						 }else {
							 msgCCR.alerta("SR j&aacute foi informado!", function () {});
						 }
						 
						 $('#srText').val("");
						 _this.adicionarRemoverRequiredSRList(document.getElementById("srList").length);
						
					}
				})
				.error(function erro(jqXHR) {
					_this.gestor  = "false";
				});
		},		
		
		excluirSr: function (e) {
			var sr = $("#srList").val();
			$.each(sr, function (item, value) {
				$("#srList option[value="+value+"]").remove();
			});
			_this.adicionarRemoverRequiredSRList(document.getElementById("srList").length);
		},
		
		changedSelectsGrpAverb : function (e) {
			
			console.log("Manter Convenio - changedSelectsGrpAverb");
			
			_this.convenio.situacao.id = $('#situacaoIncluir').val();
			_this.convenio.situacao.descricao = _this.$el.find('#situacaoIncluir').find(':selected').text();
			
			_this = this;
			var value = e.target.value;
			var selectOption;
			
			selectOption = _this.$el.find('#grupoAverbacao').find(':selected').text();
			
			if (value != "") {
				// 7 é o id da MPOG na combo grupoAverbacao - tabela
				// CCRTB011_GRUPO_AVERBACAO coluna NU_GRUPO_AVERBACAO
		    	if (value == '7'){
		    		_this.$el.find('#codigoOrgao').prop('disabled', false);
		    	}else {
		    		_this.$el.find('#codigoOrgao').prop('disabled', true).val('').attr('validators', '');
		    	}
			}else {
				_this.$el.find('#codigoOrgao').prop('disabled', true).val('').attr('validators', '');
			}		
		},

		compatibilidadeAccordion: function () {
			
			_this.$el.find('#headingOne').click(function() {
	        	_this.$el.find('#collapseOne').toggle();
	        	
	        	if (_this.$el.find('#collapseOne').css('display') == 'none') {
	        		_this.$el.find('#collapseOne').hide();	        		
	        	} else {
	        		_this.$el.find('#collapseOne').show();
	        	}
	        	
	        }).css({cursor: 'pointer'}); 
	        
	        _this.$el.find('#headingTwo').click(function() {
	        	_this.$el.find('#collapseTwo').toggle();
	        	
	        	if (_this.$el.find('#collapseTwo').css('display') == 'none') {
	        		_this.$el.find('#collapseTwo').hide();
	        	} else {
	        		_this.$el.find('#collapseTwo').show();
	        	}
	        	
	        }).css({cursor: 'pointer'});
	        
	        _this.$el.find('#headingThree').click(function() {
	        	_this.$el.find('#collapseThree').toggle();
	        	
	        	if (_this.$el.find('#collapseThree').css('display') == 'none') {
	        		_this.$el.find('#collapseThree').hide();
	        	} else {
	        		_this.$el.find('#collapseThree').show();        		
	        	}
	        	
	        }).css({cursor: 'pointer'});
	        			
		},
		
		consultaCC: function(cnpj){
			
			var prm = $.Deferred();
			
			_this.modeloCC = new Convenio();
			_this.modeloCC.consultar(cnpj)
			.done(function sucesso(data) {
				
					_this.clienteCC = new Cliente();
					_this.clienteCC = data;
					
					var produtosCaixa = $.extend(true, [], _this.clienteCC.produtoCaixa);
					var contasCorrentes = [];
					var agenciaConta;
											
					$.each(produtosCaixa, function (index, produtoCaixa) {				
						if (produtoCaixa == null){
							return true;					
						}else {
								agenciaConta =  produtoCaixa.contrato.substring(0,4)+"."+
												produtoCaixa.contrato.substring(4,7)+"."+
												produtoCaixa.contrato.substring(7,15)+"-"+
												produtoCaixa.contrato.substring(15);
								var obj ={};
								obj.id = index;
								obj.descricao = agenciaConta;
								contasCorrentes.push(obj);									
						}
					});
					
					_this.clienteCC.contasCorrentesLst = $.extend(true, [], contasCorrentes);
					prm.resolve(_this.clienteCC.contasCorrentesLst);
				
				})
				.error(function erro(jqXHR) {
					prm.reject();
				});	
			
			return prm.promise();
		},
		
		validacaoRadioPermiteContratacao :  function(canal){
			 _this.$el.find('#przMinimoContratacao_input_'+canal).prop('disabled', true).attr('validators', '');
			 _this.$el.find("#przMaximoContratacao_input_"+canal).prop('disabled', true).attr('validators', '');
			 _this.$el.find("#indFaixaJuroContratacao_input_"+canal).prop('disabled', true).attr('validators', '');
			 $("#radioExigeAutorizacaoContratacao_"+canal+"_S").prop('disabled', true);
			 $("#radioExigeAutorizacaoContratacao_"+canal+"_N").prop('disabled', true);
			
		},
		
		validacaoRadioPermiteRenovacao : function(canal){
			 _this.$el.find('#przMinimoRenovacao_input_'+canal).prop('disabled', true).attr('validators', '');
			 _this.$el.find("#przMaximoRenovacao_input_"+canal).prop('disabled', true).attr('validators', '');
			 _this.$el.find("#indFaixaJuroRenovacao_input_"+canal).prop('disabled', true).attr('validators', '');
			 $("#radioExigeAutorizacaoRenovacao_"+canal+"_S").prop('disabled', true);
			 $("#radioExigeAutorizacaoRenovacao_"+canal+"_N").prop('disabled', true);
		},
		
		alterarCanal: function (evt) {			
			_this.recuperaDadosTela();			
			
			var canalId = _this.$el.find(evt.currentTarget).data('index');	
			if( _this.$el.find("input[name='radioIndPermiteContratacao_"+canalId+"']:checked").val() == "N"){
				_this.validacaoRadioPermiteContratacao(canalId);
			}
			
			if( _this.$el.find("input[name='radioIndPermiteRenovacao_"+canalId+"']:checked").val() == "N"){
				_this.validacaoRadioPermiteRenovacao(canalId);
			}
				
			_this.edit.push(canalId);
			var canais = $.extend(true, [], _this.convenio.canais);
			var canaisFinal = [];
			canaisFinal = canais;
			
			$("#przMinimoContratacao_input_"+canalId).attr("style", "display:block");
			$("#przMinimoContratacao_span_"+canalId).attr("style", "display:none");

			$("#przMinimoRenovacao_input_"+canalId).attr("style", "display:block");
			$("#przMinimoRenovacao_span_"+canalId).attr("style", "display:none");

			$("#przMinimoRenovacao_input_"+canalId).attr("style", "display:block");
			$("#przMinimoRenovacao_span_"+canalId).attr("style", "display:none");

			$("#przMaximoContratacao_input_"+canalId).attr("style", "display:block");
			$("#przMaximoContratacao_span_"+canalId).attr("style", "display:none");
	
			$("#przMaximoRenovacao_input_"+canalId).attr("style", "display:block");
			$("#przMaximoRenovacao_span_"+canalId).attr("style", "display:none");

			if($("#indFaixaJuroContratacao_input_"+canalId+" option").size() > 0){
				$("#indFaixaJuroContratacao_input_"+canalId+" option").remove();
			}
			
			if($("#indFaixaJuroRenovacao_input_"+canalId+" option").size() > 0){
				$("#indFaixaJuroRenovacao_input_"+canalId+" option").remove();
			}

			arraySelect = [" ","A", "B", "C"];
			var lstAuxContr = [];
			var valueAux =document.getElementById("indFaixaJuroContratacao_span_"+canalId+"").innerHTML.trim()
			$.each(arraySelect, function (index, array) {
				if (array == null)
					return true; // continue
					var opt = document.createElement('option');
				if(this[0] == valueAux){
				    opt.innerHTML = document.getElementById("indFaixaJuroContratacao_span_"+canalId+"").innerHTML.trim();
				    opt.value = document.getElementById("indFaixaJuroContratacao_span_"+canalId+"").innerHTML.trim();
				    opt.setAttribute("selected","true");
				}else{
				    opt.innerHTML = this[0];
				    opt.value = this[0];
				}

				$("#indFaixaJuroContratacao_input_"+canalId).append(opt);
			});
			
			
			$("#indFaixaJuroContratacao_input_"+canalId).attr("style", "display:block");
			$("#indFaixaJuroContratacao_input_"+canalId).attr("style", "height: 24px; width:95px");
			$("#indFaixaJuroContratacao_span_"+canalId).attr("style", "display:none");			
			
			arraySelect = [" ","A", "B", "C"];
			lstAuxContr = [];
			valueAux =document.getElementById("indFaixaJuroRenovacao_span_"+canalId+"").innerHTML.trim();																	
			$.each(arraySelect, function (index, array) {
				if (array == null)
					return true; // continue
					var opt = document.createElement('option');
				if(this[0] == valueAux){
				    opt.innerHTML = document.getElementById("indFaixaJuroRenovacao_span_"+canalId+"").innerHTML.trim();
				    opt.value = document.getElementById("indFaixaJuroRenovacao_span_"+canalId+"").innerHTML.trim();
				    opt.setAttribute("selected","true");
				}else{
				    opt.innerHTML = this[0];
				    opt.value = this[0];
				}
			
				$("#indFaixaJuroRenovacao_input_"+canalId).append(opt);
			});
			
			 $("#indFaixaJuroRenovacao_input_"+canalId).attr("style", "display:block");
			 $("#indFaixaJuroRenovacao_input_"+canalId).attr("style", "height: 24px; width:95px");
			 $("#indFaixaJuroRenovacao_span_"+canalId).attr("style", "display:none");
			 $("#qtdDiaGarantiaCondicao_input_"+canalId).attr("style", "display:block");
			 $("#qtdDiaGarantiaCondicao_span_"+canalId).attr("style", "display:none");
			 $("#radioIndPermiteContratacao_"+canalId+"_S").prop('disabled', false);
			 $("#radioIndPermiteContratacao_"+canalId+"_N").prop('disabled', false);
			 $("#radioExigeAutorizacaoContratacao_"+canalId+"_S").prop('disabled', false);
			 $("#radioExigeAutorizacaoContratacao_"+canalId+"_N").prop('disabled', false);
			 $("#radioIndPermiteRenovacao_"+canalId+"_S").prop('disabled', false);
			 $("#radioIndPermiteRenovacao_"+canalId+"_N").prop('disabled', false);
			 $("#radioExigeAutorizacaoRenovacao_"+canalId+"_S").prop('disabled', false);
			 $("#radioExigeAutorizacaoRenovacao_"+canalId+"_N").prop('disabled', false);
			 $("#radioExigeAnuencia_"+canalId+"_S").prop('disabled', false);
			 $("#radioExigeAnuencia_"+canalId+"_N").prop('disabled', false);
			 $("#indPermiteRenovacao_"+canalId).prop('disabled', false);
			 $("#exigeAutorizacaoContratacao_"+canalId).prop('disabled', false);
			 $("#exigeAutorizacaoRenovacao_"+canalId).prop('disabled', false);
			 $("#exigeAnuencia_"+canalId).prop('disabled', false);
			 $("#radioSituacaoConvenioCanal_"+canalId+"_S").prop('disabled', false);
			 $("#radioSituacaoConvenioCanal_"+canalId+"_N").prop('disabled', false);
			 
			 $("#btnAlterarCanal_"+canalId).attr("style", "display:none");
			 $("#btnGravarCanal_"+canalId).removeAttr("style", "display:none;");	
		},
		
		gravarCanal: function (ev) {
			var canalId = $(ev.currentTarget).data('index'); 	
			
			var permiteContratacao =  _this.$el.find("input[name='radioIndPermiteContratacao_"+canalId+"']:checked").val();
			var permiteRenovacao = _this.$el.find("input[name='radioIndPermiteRenovacao_"+canalId+"']:checked").val();
			
			if((permiteContratacao != "N") || (permiteRenovacao != "N")){
				if (_this.validator.withForm("divCanais_"+canalId).validate()){	
					
				_this.recuperaDadosTela();
					var itemPosRemovidos = []; 
					for (i = 0; i < _this.edit.length; i++) {   
					   if( _this.edit[i] != canalId){
						   itemPosRemovidos.push[_this.edit[i]];
					   }
					}
					
					_this.edit = itemPosRemovidos;
					
					var canais = $.extend(true, [], _this.convenio.canais);
					var canaisFinal = [];
					canaisFinal = canais;
					
					var i = 0;
					$.each(canaisFinal, function (index, canal) {				
						if (canal == null){
							return true;					
						}else {
							
							if(canal.canal.id == canalId){
								
								canal.przMinimoContratacao = document.getElementById("przMinimoContratacao_input_"+canalId+"").value;
		  						canal.przMinimoRenovacao = document.getElementById("przMinimoRenovacao_input_"+canalId+"").value;
		  						canal.przMinimoRenovacao = document.getElementById("przMinimoRenovacao_input_"+canalId+"").value;
		  						canal.przMaximoContratacao = document.getElementById("przMaximoContratacao_input_"+canalId+"").value;
		  						canal.przMaximoRenovacao = document.getElementById("przMaximoRenovacao_input_"+canalId+"").value;
		  						canal.qtdDiaGarantiaCondicao = document.getElementById("qtdDiaGarantiaCondicao_input_"+canalId+"").value;
		  						canal.indFaixaJuroContratacao = document.getElementById("indFaixaJuroContratacao_input_"+canalId+"").value;
		  						canal.indFaixaJuroRenovacao = document.getElementById("indFaixaJuroRenovacao_input_"+canalId+"").value;

		  						canal.indPermiteContratacao = $("input[name=radioIndPermiteContratacao_"+canalId+"]:checked").val();
		  						canal.indPermiteRenovacao = $("input[name=radioIndPermiteRenovacao_"+canalId+"]:checked").val();
		  						canal.indAutorizaMargemContrato = $("input[name=radioExigeAutorizacaoContratacao_"+canalId+"]:checked").val();
		  						canal.indAutorizaMargemRenovacao = $("input[name=radioExigeAutorizacaoRenovacao_"+canalId+"]:checked").val();
		  						canal.indExigeAnuencia = $("input[name=radioExigeAnuencia_"+canalId+"]:checked").val();
		  						canal.indExigeAutorizacaoContrato = $("input[name=radioExigeAutorizacaoContratacao_"+canalId+"]:checked").val();
		  						canal.indSituacaoConvenioCanal = $("input[name=radioSituacaoConvenioCanal_"+canalId+"]:checked").val();
		  					
		  						canal.idConvenio = _this.convenio.id;
		  						
		  						/**
		  						 * Esta chamada para alteração do Canal é responsável por
		  						 * criar auditoria de alteração de um Canal e deixa-la preparada,
		  						 * para na hora de salvar o convênio ele realmente persistir as 
		  						 * auditorias criadas. Tentar executar tudo direto no Salvar seria 
		  						 * muito complexo, então, para simplificar foi feito desta maneira
		  						 * ao acionar o botão de gravar alteração.
		  						 */
		  						_this.modelo.alterarCanal(canal)
		  						.done(function (data) {
		  							console.log(data);
		  							_this.canaisAlteracao.push(data);
		  							_this.canaisAlteracao = $.extend(true, _this.canaisAlteracao, _this.canaisAlteracao);
		  						});
							}
						}
						canaisFinal[i] = canal;
						i++;					
					});			
					_this.convenio.canais = canaisFinal;			
					_this.desabilitaCampos(canalId);
				}	
			}else{
				Retorno.trataRetorno({codigo: 1, tipo: "ERRO_NEGOCIAL", mensagem: "", idMsg: 'MA0101'}, 'convenio');
			}
		},
		
		
		desabilitaCampos : function(canalId){
			document.getElementById("przMinimoContratacao_span_"+canalId+"").innerHTML =  document.getElementById("przMinimoContratacao_input_"+canalId+"").value;
			document.getElementById("przMinimoContratacao_input_"+canalId+"").style.display = "none";
			document.getElementById("przMinimoContratacao_span_"+canalId+"").style.display = "block";
			
			document.getElementById("przMinimoRenovacao_span_"+canalId+"").innerHTML =  document.getElementById("przMinimoRenovacao_input_"+canalId+"").value;
			document.getElementById("przMinimoRenovacao_input_"+canalId+"").style.display = "none";
			document.getElementById("przMinimoRenovacao_span_"+canalId+"").style.display = "block";
			
			document.getElementById("przMinimoRenovacao_span_"+canalId+"").innerHTML =  document.getElementById("przMinimoRenovacao_input_"+canalId+"").value;			
			document.getElementById("przMinimoRenovacao_input_"+canalId+"").style.display = "none";
			document.getElementById("przMinimoRenovacao_span_"+canalId+"").style.display = "block";
			
			document.getElementById("przMaximoContratacao_span_"+canalId+"").innerHTML =  document.getElementById("przMaximoContratacao_input_"+canalId+"").value;			
			document.getElementById("przMaximoContratacao_input_"+canalId+"").style.display = "none";
			document.getElementById("przMaximoContratacao_span_"+canalId+"").style.display = "block";
			
			document.getElementById("przMaximoRenovacao_span_"+canalId+"").innerHTML =  document.getElementById("przMaximoRenovacao_input_"+canalId+"").value;			
			document.getElementById("przMaximoRenovacao_input_"+canalId+"").style.display = "none";
			document.getElementById("przMaximoRenovacao_span_"+canalId+"").style.display = "block";
			
			document.getElementById("indFaixaJuroContratacao_span_"+canalId+"").innerHTML =  document.getElementById("indFaixaJuroContratacao_input_"+canalId+"").value;			
			document.getElementById("indFaixaJuroContratacao_input_"+canalId+"").style.display = "none";
			document.getElementById("indFaixaJuroContratacao_span_"+canalId+"").style.display = "block";
			
			document.getElementById("indFaixaJuroRenovacao_span_"+canalId+"").innerHTML =  document.getElementById("indFaixaJuroRenovacao_input_"+canalId+"").value;
			document.getElementById("indFaixaJuroRenovacao_input_"+canalId+"").style.display = "none";
			document.getElementById("indFaixaJuroRenovacao_span_"+canalId+"").style.display = "block";
			
			document.getElementById("qtdDiaGarantiaCondicao_span_"+canalId+"").innerHTML =  document.getElementById("qtdDiaGarantiaCondicao_input_"+canalId+"").value;
			document.getElementById("qtdDiaGarantiaCondicao_input_"+canalId+"").style.display = "none";
			document.getElementById("qtdDiaGarantiaCondicao_span_"+canalId+"").style.display = "block";
			
			_this.$el.find('input[name="indPermiteContratacao'+canalId+'"]').prop('disabled', true);		
			_this.$el.find('input[name="indPermiteRenovacao'+canalId+'"]').prop('disabled', true);		
			_this.$el.find('input[name="exigeAutorizacaoContratacao'+canalId+'"]').prop('disabled', true);		
			_this.$el.find('input[name="exigeAutorizacaoRenovacao'+canalId+'"]').prop('disabled', true);		
			_this.$el.find('input[name="exigeAnuencia'+canalId+'"]').prop('disabled', true);		
			
			$("#radioExigeAutorizacaoContratacao_"+canalId+"_S").prop('disabled', true);
			$("#radioExigeAutorizacaoContratacao_"+canalId+"_N").prop('disabled', true);
						 
			$("#radioExigeAutorizacaoRenovacao_"+canalId+"_S").prop('disabled', true);
			$("#radioExigeAutorizacaoRenovacao_"+canalId+"_N").prop('disabled', true);
			
			$("#radioIndPermiteContratacao_"+canalId+"_S").prop('disabled', true);
			$("#radioIndPermiteContratacao_"+canalId+"_N").prop('disabled', true);
			
			$("#radioIndPermiteRenovacao_"+canalId+"_S").prop('disabled', true);
			$("#radioIndPermiteRenovacao_"+canalId+"_N").prop('disabled', true);
			
			$("#radioExigeAnuencia_"+canalId+"_S").prop('disabled', true);
			$("#radioExigeAnuencia_"+canalId+"_N").prop('disabled', true);	
			
			$("#radioSituacaoConvenioCanal_"+canalId+"_S").prop('disabled', true);
			$("#radioSituacaoConvenioCanal_"+canalId+"_N").prop('disabled', true);	
			
			
			 $("#btnAlterarCanal_"+canalId).removeAttr("style", "display:none");
			 $("#btnGravarCanal_"+canalId).attr("style", "display:none;");	
		},
		
		voltar: function () {			
			DashboardControleCCR.voltarCovenioIniDashboardControleCCR(0);
		},
		
		sair: function () {
			loadCCR.start();
			console.log("saindo do CCR...");
			window.location = 'index.html';
		},
		
		
		detalhesConvenio: function (evt) {	
			loadCCR.start();
			var index = _this.$el.find(evt.currentTarget).data('index');
			_this.convenio = _this.collection.models[index].attributes;
			_this.cliente = new Cliente();
			_this.cliente = _this.cliente.attributes;
			
			_this.modelo = new Convenio();
			_this.modelo.consultarConvenio(_this.convenio.id) 
			.done(function sucesso(data) {
				loadCCR.stop();
				// trta retorno em caso de erro
				if (data.tipoRetorno == "ERRO_EXCECAO") {
					Retorno.trataRetorno({codigo: 1, tipo: "AVISO", mensagem: "", idMsg: 'MA004'}, 'convenio');
					return;
				}else{
					DashboardControleCCR.detalharConvenenteControle(_this.convenio.id);
				}
			})
			.error(function erro(jqXHR) {
				msgCCR.alerta("Funcionalidade Indísponivel!!!", function () {});
			});
		},
		
		/**
		 * Função que valida o CNPJ no SICLI
		 * 
		 *  
		 * @param (cnpj)
		 * 
		 */
		validarCNPJ : function (cnpj) {
			console.log("CCR - consultarCNPJ");
			
			_this.modelo = new Convenio();
			cnpj = cnpj.replace(/[_.\-\/]/g, '')
			
			loadCCR.start();
			
			//Chamada do SICLI - busca correlationId
			_this.modelo.consultar(cnpj).done(function sucesso(data) {
				console.log(data)
				var correlationId = data.correlationId;
				console.log(correlationId);
				
				//Chamada do SICLI - com o correlationId busca os dados no fila de response
				_this.modelo.consultarCorrelationId(correlationId).done(function sucesso(dataRet) {	
					
					if (dataRet.dados.responseArqRef.status.codigo ==null){
						if (dataRet.dados==null || dataRet.dados.cocliAtivo==null){						
							console.log("Chama o agendador");						
							//Chama o agendador
							setTimeout(function(){
								_this.agendador2(6, 2000,correlationId);						
							},2000);
						}else{
							return dataRet.dados;
						}
					}else{						
						if (dataRet.dados.responseArqRef.status.codigo ==0){
							return dataRet.dados;							
						}else{
							Retorno.validarRetorno(dataRet);
							loadCCR.stop();
							return;							
						}
					}
					
				})
				.error(function erro(jqXHR) {
					_this.gestor  = "false";
				});			
			
			})
			.error(function erro(jqXHR) {
				_this.gestor  = "false";
			});			
	        	
		},
		/**
		 * Funcao que agenda as execucoes de busca de cliente
		 * 
		 * @param tentativas
		 * @param atraso
		 * @param funcao
		 * @param parametro
		 */
		agendador2 : function (tentativas, atraso, correlationId){
			console.log("Cliente - agendador");
			console.log(new Date());
			console.log("tentativas -- > " + tentativas);
			loadCCR.start();
			var that = this;
			if(tentativas > 0){
				//recebe o resultado da consulta cliente e verifica
				//se havera reexcucao
				
				_this.modelo.consultarCorrelationId(correlationId).done(function sucesso(dataRet) {					
					if (dataRet.dados==null || dataRet.dados.cocliAtivo==null){						
					
							console.log("reexcutar");						
							tentativas = tentativas - 1;
							console.log("executando o agendador: "+tentativas);
							_this.chamaAgendador2(tentativas, atraso, correlationId);													
					}else{
						return true;						
					}
					})
					.error(function erro(jqXHR) {
						_this.gestor  = "false";
					});
			}else{
				_this.modelo.consultarCorrelationId(correlationId).done(function sucesso(dataRet) {							
					if (dataRet.dados==null || dataRet.dados.cocliAtivo==null){			
					
						Retorno.validarRetorno(dataRet);
						loadCCR.stop();
						return;													
					}
					
				})
				.error(function erro(jqXHR) {
					_this.gestor  = "false";
				});				
			}
		},
		
		chamaAgendador2: function (tentativas, atraso, correlationId) {
			setTimeout(function(){
				_this.agendador2(tentativas, atraso, correlationId);						
			},atraso);
		},
		
		formatarCNPJ : function(valor) {
			if(this.verificaValor(valor)){
				return "";
			}
			var formatador = "00.000.000/0000-00";
			return this.formatadorGenerico(valor, formatador);
		},
		verificaValor : function(valor){		
			if(valor == null || valor === "" ){
				return true;
			}		
		},
		formatadorGenerico : function (valor, Mascara) {
			var boleanoMascara;
			var exp = /\-|\.|\/|\(|\)| /g;
			var mascaraSemMascara = Mascara.replace(exp, '');
			campoSoNumeros = new String(valor);

			// Criar metodo fora para o tratamento
			if (mascaraSemMascara.length !== campoSoNumeros.length) {
				var caract = mascaraSemMascara.length - campoSoNumeros.length;
				var valorCompleto = mascaraSemMascara.substring(0, caract) +''+ campoSoNumeros;
				campoSoNumeros = valorCompleto;
			}

			var posicaoCampo = 0;
			var NovoValorCampo = '';
			var TamanhoMascara = campoSoNumeros.length;

			for ( var i = 0; i <= TamanhoMascara; i++) {

				boleanoMascara = ((Mascara.charAt(i) == '-')
						|| (Mascara.charAt(i) == '.') || (Mascara.charAt(i) == '/'));
				boleanoMascara = boleanoMascara
						|| ((Mascara.charAt(i) == '(') || (Mascara.charAt(i) == ')') || (Mascara.charAt(i) == ' '));

				if (boleanoMascara) {
					NovoValorCampo += Mascara.charAt(i);
					TamanhoMascara++;
				} else {
					NovoValorCampo += campoSoNumeros.charAt(posicaoCampo);
					posicaoCampo++;
				}
			}

			return NovoValorCampo;
		},
		
		/**
		 * Função que busca dados no SICLI
		 * 
		 *  
		 * @param (cnpj)
		 * 
		 */
		buscaDadosCliente : function (cnpj) {
			console.log("CCR - consultarCNPJ");
			
			var prm = $.Deferred();
			
			_this.modeloDC = new Convenio();
			_this.clienteDC = new Cliente();
			
			//Chamada do SICLI - busca correlationId
			_this.modeloDC.consultar(cnpj).done(function sucesso(data) {
				console.log(data)
				var correlationId = data.correlationId;
				console.log(correlationId);
				
				//Chamada do SICLI - com o correlationId busca os dados no fila de response
				_this.modeloDC.consultarCorrelationId(correlationId).done(function sucesso(dataRet) {
					if (dataRet.dados.responseArqRef.status.codigo ==null){
						if (dataRet.dados==null || dataRet.dados.cocliAtivo==null){				
							console.log("Chama o agendador");						
							//Chama o agendador
							setTimeout(function(){
								_this.agendador3(6, 2000,correlationId);						
							},2000);
						}else{
							_this.clienteDC = dataRet.dados;
							prm.resolve(_this.clienteDC);						
						}
					}else{						
						if (dataRet.dados.responseArqRef.status.codigo ==0){
							_this.clienteDC = dataRet.dados;
							prm.resolve(_this.clienteDC);						
						}else{
							Retorno.validarRetorno(dataRet);
							loadCCR.stop();
							return;							
						}
					}					
				})
				.error(function erro(jqXHR) {
					prm.reject();
				});			
			
			})
			.error(function erro(jqXHR) {
				prm.reject();
			});
			
			return prm.promise();	        	
		},
		
		agendador3 : function (tentativas, atraso, correlationId){
			console.log("Cliente - agendador");
			console.log(new Date());
			console.log("tentativas -- > " + tentativas);
			loadCCR.start();
			var that = this;
			if(tentativas > 0){
				//recebe o resultado da consulta cliente e verifica
				//se havera reexcucao
				return _this.modelo.consultarCorrelationId(correlationId).done(function sucesso(dataRet) {

					if (dataRet.dados==null || dataRet.dados.cocliAtivo==null){							
							console.log("reexcutar");						
							tentativas = tentativas - 1;
							console.log("executando o agendador: "+tentativas);
							_this.chamaAgendador3(tentativas, atraso, correlationId);													
					}else{
						_this.cliente = dataRet.dados;
						return _this.cliente;						
					}
					})
					.error(function erro(jqXHR) {
						_this.gestor  = "false";
					});
			}else{
				
				_this.modelo.consultarCorrelationId(correlationId).done(function sucesso(dataRet) {		
					if (dataRet.dados==null || dataRet.dados.cocliAtivo==null){			
						Retorno.validarRetorno(dataRet);
						loadCCR.stop();
						return;													
					}
					
				})
				.error(function erro(jqXHR) {
					_this.gestor  = "false";
				});
			}
		},
		
		chamaAgendador3: function (tentativas, atraso, correlationId) {
			setTimeout(function(){
				_this.agendador3(tentativas, atraso, correlationId);						
			},atraso);
		},
		
		incluirCNPJ : function() {
			
			if (_this.validator.withInput('CNPJText').validate()){
			_this = this;
				
			var option = new Option($('#CNPJText').val(),$('#CNPJText').val());
			var CNPJList = document.getElementById("CNPJList");
			var validaCNPJInserida = 0;
			//valida sicli
			_this.modelo = new Convenio();
			_this.modeloCliente = new Cliente();
			cnpj = $('#CNPJText').val().replace(/[_.\-\/]/g, '');
			
				_this.modeloCliente.consultarCNPJ(cnpj).done(function sucesso(data) {			
					console.log(data);
					loadCCR.stop();
					
					if (data.dados.responseArqRef.status.codigo == 0) {				
						
						var cnpjConv = $('#cnpjConvenente').val().replace(/[_.\-\/]/g, '');
						
						if(cnpj ==  cnpjConv){
							Retorno.trataRetorno({codigo: 1, tipo: "ERRO_NEGOCIAL", mensagem: "", idMsg: 'MA0099'}, 'convenio');
						}else{
							 if (CNPJList.length == 0) {
								 $('#CNPJList').append(option);
								 //$('#CNPJList').append(option.value.replace(/[_.\-\/]/g, ''));
							 }else {
								 for (i = 0; i < CNPJList.length; i++) { 
										
									 var text = CNPJList.options[i].text;
									 if ($('#CNPJText').val().replace(/[_.\-\/]/g, '') == text.replace(/[_.\-\/]/g, '')) {
										 validaCNPJInserida = 1;
									 }				 
								 }
							 }
							 			 
							 if (validaCNPJInserida == 0) {
								 $('#CNPJList').append(option);
							 }else {
								 msgCCR.alerta("CNPJ j&aacute foi informado!", function () {});
							 }
							 
							 $('#CNPJText').val("");
							 _this.adicionarRemoverRequiredCNPJList(document.getElementById("CNPJList").length);
						}
						
					}else {
							//Valida o retorno do SICLI, caso erro sobe a msg.
							Retorno.validarRetorno(data);
					}
					
				})
				.fail(function erro(jqXHR) {
					_this.gestor  = "false";
				});
			}
		},
		
		excluirCNPJ : function() {
			
			var sr = $("#CNPJList").val().toString();
			$("#CNPJList option[value='"+sr+"']").remove();
			_this.adicionarRemoverRequiredSRList(document.getElementById("CNPJList").length);
		},

	});

	return ManterConvenenteControle;	
});