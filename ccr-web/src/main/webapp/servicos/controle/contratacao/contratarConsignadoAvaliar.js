define(['enumeracoes/eMensagemCCR',
        'enumeracoes/eTipoServicos',
        'util/retorno',       
        'modelo/cliente/avaliacao',
        'modelo/contratacao/simulacao',
        'text!visao/contratacao/contratar/avaliar/panelSimulacaoInicial.html',
        'text!visao/contratacao/contratar/avaliar/panelAvaliacaoCliente.html',
        'text!visao/contratacao/contratar/avaliar/panelResultadoSimulacao.html',
        'text!visao/contratacao/contratar/avaliar/panelAvaliacaoOperacao.html',
        'controle/contratacao/wizardConsignadoControle',
        'controle/contratacao/detalheConsultaCETControle',
        'text!visao/contratacao/detalheConsultaCET.html',
        'modelo/contratacao/contrato',
        'modelo/cliente/cliente',
        ], 
function(EMensagemCCR, ETipoServicos, Retorno, Avaliacao, Simulacao, simulacaoInicialTpl, avaliacaoClienteTpl, resultadoSimulacaoTpl, avaliacaoOperacaoTpl, wizardConsignadoControle, DetalheConsultaCETControle, detalheConsultaCetTpl, Contrato, Cliente){

	var _this = null;
	var ContratarConsignadoAvaliar = Backbone.View.extend({

		className: 'contratarConsignadoAutorizar',
		
		/**
		 * Templates
		 */
		simulacaoInicialTemplate  	: _.template(simulacaoInicialTpl),
		avaliacaoClienteTemplate	: _.template(avaliacaoClienteTpl),
		resultadoSimulacaoTemplate	: _.template(resultadoSimulacaoTpl),
		avaliacaoOperacaoTemplate	: _.template(avaliacaoOperacaoTpl),
		detalheCetTemplate			: _.template(detalheConsultaCetTpl),
		
		/**
		 * Variaveis da classe
		 */
		validator   : new Validators(),
		message     : new Message(),
		comando     : null,
		modelo		: null,
		collection  : null,
		acao		: null,
		avaliacao	: new Avaliacao(),
		avaliacoes  : {},
		numeroContrato  : null,
		contratoModelo  : null,
		contrato  	: null,
		simulacaoContrato: null,
		agencia: null,
		avaliacaoClienteValida : null,
		avaliacaoRiscoOptemp: null,
		isGerente : false,
		manter : null,
	
		
		/**
		 * Função que faz bind das ações e interações da pagina com as funções
		 * javascript
		 * 
		 */
		events : {
			'click #btnAvaliacaoCliente'			: 'showAvaliacaoCliente',
			'click #controleDetalheSimulacao' 		: 'controleDetalheSimulacao',
			'click #btnAvaliacaoOperacao'			: 'showAvaliacaoOperacao',	
			'click #btnRecalcular'					: 'showModalRecalcular',
			'click #btnVoltarRecalcularModal'		: 'fecharModalRecalcular',
			'click #btnRecalcularModal'				: 'recalcularSimulacao',
			'click #radioValorLiquido'				: 'selecionarValor',
			'click #radioValorPrestacao'			: 'selecionarValor',
			'click a#bloqueadaAlcadaClose'  		: 'bloqueadaAlcadaClose',
			'click td.btn-toggle-popupcet'  		: 'showDadosConsultaCET',
			'click a.btn-close-popupcet'			: 'closeDadosConsultaCET',
			'click a.btn-print-popupcet'  			: 'printDadosConsultaCET',
			'click #btnSolicitarAvaliacaoCliente' 	: 'solicitarAvaliacaoCliente',
			'click #btnConsultarAvaliacaoCliente' 	: 'consultaAvaliacaoCliente',
			'click #btnConsultarAvaliacaoOperacao' 	: 'consultarAvaliacaoOperacao',
			'click #btnSolicitarAvaliacaoOperacao' 	: 'solicitarAvaliacaoOperacao',
			'click #btnDesbloquar' 	                : 'desbloquerAvaliacao'
		},
				
		finalizar : function() {
			$wizard.wizard('completeProgressBar');
		},
		
		initialize : function() {
			
			console.log("contratarConsignadoAvaliar - initialize");
			
			_this = this;		
			_this.ctrlConsultaCET = new DetalheConsultaCETControle();
			_this.validator.addValidator(new DataValidator());			
			_this.validator.withErrorRender(new BootstrapErrorRender());
		},
		
		
		render : function(data, cliente, manter, operacao, avancar) {
			console.log("contratarConsignadoAvaliar - render");
			if(avancar != null) {
				$("#btnAvancar").hide();
				return _this;
			}
			_this.controleBtnSolicitaAvaliacaoClienteDisabled();
			_this.controleBtnConsultarAvaliacaoClienteDisabled();
			_this.avaliacao = new Avaliacao();
			_this.op = false;
			_this.manter = manter;
//			_this.nuAvaliacaoCliente = null;
//			_this.nuAvaliacaoOperacao = null;
			
			if(data.nuContrato!=null){
				_this.agencia = data.agencia;
				if(operacao) {
					_this.op = operacao;
				}
				_this.consultarContrato(data.nuContrato);
			}
			else{
				_this.numeroContrato = null;
				_this.dadosSimulacao = data;
				_this.cliente = cliente;
				_this.novaSimulacao = data;
				_this.contrato =null;
				if(data.tipoSeguro==1){
					_this.dadosSimulacao.simulacao = data.simulacaoComSeguro;
				}else if(data.tipoSeguro==2){
					_this.dadosSimulacao.simulacao = data.simulacaoSemSeguro;
				}
				
				/**
				 * Verifica se o valor esta vindo com '.' e/ou ','
				 * deixando-o preparado para formatação
				 */
				if(_this.dadosSimulacao.simulacao.prestacao.indexOf('.') != -1) {
					if(_this.dadosSimulacao.simulacao.prestacao.indexOf(',') != -1) {
						_this.dadosSimulacao.simulacao.prestacao =  _this.dadosSimulacao.simulacao.prestacao.replace('.', '').replace(',','.');
					} else {
						_this.dadosSimulacao.simulacao.prestacao =  _this.dadosSimulacao.simulacao.prestacao;
					}
				} else {
					_this.dadosSimulacao.simulacao.prestacao =  _this.dadosSimulacao.simulacao.prestacao.replace('.', '').replace(',','.');
				}
				
				
				
				this.$el.html(_this.simulacaoInicialTemplate({simular: _this.dadosSimulacao}));
				
			}
			
			/**
			 * Responsavel por usar este Objeto no simular();
			 * Assim não altera o objeto de simulacao Inicial.
			 * (Caso o usuario queira simular uma nova).
			 */
			$('#btnAvancar').hide();
			
			loadMaskEl(_this.$el);
			listenToDatepickerChange(_this.$el, _this.changed);
		
			_this.isGerente =  _this.isGerenteLogado();
			return _this;
		},
	
		
		selecionarValor :  function(evt){
			console.log("Simular Main - selecionarValor");
			
			_this.identificadorValor = evt.target.value;

			// apresenta opções de filtro de acordo com identificador
			// selecionado
			switch (_this.identificadorValor){
			case '1': // CPF
				_this.$el.find("#div_valor_liquido").show();
				_this.$el.find("#div_valor_prestacao_avaliar").hide();

				break;
			case '2': // CNPJ
				_this.$el.find("#div_valor_liquido").hide();
				_this.$el.find("#div_valor_prestacao_avaliar").show();

				break;
			}
		},

		
		showSimulacaoResultado: function(){
			_this.dadosSimulacao.idConvenio = _this.dadosSimulacao.convenio.id;
			_this.$el.find('#divResultadoSimulacao').html(_this.resultadoSimulacaoTemplate({simularResultado: _this.dadosSimulacao}));
			_this.abrirPanelOperacao();
			_this.controleBtnDesbloquearAvaliacaoDisabled();
			loadCCR.stop();
		},

		bloqueadaAlcadaClose : function() {
			$("#divModalBloqueadaAlcada").modal("hide");
		},
		
		showModalRecalcular: function(){
			
			console.log("contratarConsignadoAvaliar - showModalRecalcular");
			$('#divModalRecalcularAvaliar').modal('show');
			$("#valorLiquidoRecalculoAvaliar").val('');
			$("#prestacaoRecalculoAvaliar").val('');
			$("#valorContratoRecalculoAvaliar").val('');
			$("#prazoRecalculoAvaliar").val('');
			$("#jurosRecalculoAvaliar").val('');
			loadMaskEl(_this.$el);
		},
		
		controleDetalheSimulacao : function() {
			console.log("contratarConsignadoAvaliar - controleDetalheSimulacao");
			
			if (_this.mostrarDetalhe){
				_this.$el.find("#detalheSimulacao").hide();
				_this.mostrarDetalhe = false;
			}else{
				_this.$el.find("#detalheSimulacao").show();
				_this.mostrarDetalhe = true;
			}			
		},

		fecharModalRecalcular: function(){
			$('#divModalRecalcularAvaliar').modal('hide');
		},
		
		recalcularSimulacao: function(){
			if(_this.$el.find("#prazoRecalculoAvaliar").val() > _this.dadosSimulacao.convenio.canais[0].przMaximoContratacao) {
				
				// MA0083
				$(".alert").html('Prazo maior que o permitido');
				$(".alert").fadeTo(6000, 500).slideUp(500, function(){
				    $(".alert").slideUp(500);
				});
				
				$("#div_prazoAvaliar").addClass("error");
			} else if((parseFloat(_this.$el.find("#jurosRecalculoAvaliar").val().replace(',', '.')) > parseFloat(_this.dadosSimulacao.taxaList[0].pcMaximo) 
					|| parseFloat(_this.$el.find("#jurosRecalculoAvaliar").val().replace(',', '.')) < parseFloat(_this.dadosSimulacao.taxaList[0].pcMinimo)) 
					&& _this.$el.find("#jurosRecalculoAvaliar").val() != "") {
				
				// MA0084
				$(".alert").html('Taxa de juros fora do permitido');
				$(".alert").fadeTo(6000, 500).slideUp(500, function(){
				    $(".alert").slideUp(500);
				});
				
				$("#div_taxaJurosAvaliar").addClass("error");
			} else if(parseFloat(_this.$el.find("#prestacaoRecalculoAvaliar").val().replace(/\D/g, '')) > parseFloat(_this.dadosSimulacao.valorMargem.replace(/\D/g, ''))) {
				$(".alert").html('Valor da prestação fora da margem');
				$(".alert").fadeTo(6000, 500).slideUp(500, function(){
				    $(".alert").slideUp(500);
				});
				
				$("#div_valor_prestacao_avaliar").addClass("error");
			} else {
				
				$("#div_taxaJurosAvaliar").removeClass("error");
				$("#div_prazoAvaliar").removeClass("error");
				$("#div_valor_prestacao_avaliar").removeClass("error");
				
				_this.simulacao = new Simulacao();
				_this.simulacao.attributes.valorLiquido = _this.$el.find("#valorLiquidoRecalculoAvaliar").val();
				formatadores.formatarMoedaParaFloat(mascararMoeda(_this.$el.find("#valorLiquidoRecalculoAvaliar"), 2));
				_this.simulacao.attributes.valorPrestacao = _this.$el.find("#prestacaoRecalculoAvaliar").val();
				_this.simulacao.attributes.prazo = _this.$el.find("#prazoRecalculoAvaliar").val();
				_this.simulacao.attributes.taxaJuros = _this.$el.find("#jurosRecalculoAvaliar").val();
				
				_this.controleBtnSolicitarAvaliacaoOperacaoEnabled(); 
				_this.controleBtnConsultarAvaliacaoOperacaoDisabled();
				_this.controleComponentesAvaliacaoOperacaoAposRecalculo();
				
				// Ajustar
				if (_this.simulacao.attributes.valorLiquido === "" &&
					_this.simulacao.attributes.valorPrestacao === "" &&
					_this.simulacao.attributes.prazo === "" && 
					_this.simulacao.attributes.taxaJuros === ""){
					
					// MA0081
					$(".alert").fadeTo(4000, 500).slideUp(500, function(){
					    $(".alert").slideUp(500);
					});
				} else {
					$('#divModalRecalcularAvaliar').modal('hide');
					_this.simular();
					loadCCR.stop();
				}
			}
		},
		
		controleComponentesAvaliacaoOperacaoAposRecalculo : function(){
			$('#btnAvancar').hide();
			$('#divResultadoAvaliacaoOperacao').addClass('hidden');
		},

		showDadosConsultaCET : function(e) {
			console.log("Simular Consignado - showDadosConsultaCET");
			
			_this.ctrlConsultaCET.setDadosCET(_this.dadosSimulacao.simulacao,'simular');				
			_this.dadosCET = _this.ctrlConsultaCET.recuperaDados();
			_this.dadosCET.noProduto = _this.avaliacaoClienteValida.produto;
			_this.$el.find('#divModalCETBodyTwo').html(_this.detalheCetTemplate({dadosCET: _this.dadosCET}));
			_this.$el.find('#divModalCET').modal('show'); 
			$('#divModalCETBodyTwo').animate({ scrollTop: 0 }, 'slow');
		},
		
		closeDadosConsultaCET : function() {
			console.log("Simular Consignado - closeDadosConsultaCET");
			_this.$el.find('#divModalCET').modal('hide');
		},
		
		printDadosConsultaCET :  function(e) {
			console.log("Simular Consignado - printDadosConsultaCET");
			_this.ctrlConsultaCET.printDadosCET();
		},

		simular : function (){
			console.log("Simular Main - simular");
				_this.novaSimulacao = new Simulacao();
				_this.novaSimulacao.attributes.idConvenio = _this.dadosSimulacao.convenio.id;
				_this.novaSimulacao.attributes.prazo 		  = _this.$el.find('#prazoRecalculoAvaliar').val() === "" ? _this.dadosSimulacao.simulacao.prazo : _this.$el.find('#prazoRecalculoAvaliar').val(); 
				_this.novaSimulacao.attributes.taxaJuros 	  = _this.$el.find('#jurosRecalculoAvaliar').val() === "" ? _this.dadosSimulacao.simulacao.taxaJuros : _this.$el.find('#jurosRecalculoAvaliar').val().replace(',','.'); 
				//Dado informado na tela (Verificar se será enviado pelo SISRH)
				_this.novaSimulacao.attributes.valorPrestacao = _this.$el.find('#prestacaoRecalculoAvaliar').val() === "" ? "" : _this.$el.find('#prestacaoRecalculoAvaliar').val();
				_this.novaSimulacao.attributes.valorLiquido   = _this.$el.find("#valorLiquidoRecalculoAvaliar").val() === "" ? "" : _this.$el.find("#valorLiquidoRecalculoAvaliar").val();
				
				_this.avaliacao = new Avaliacao();
				
				_this.novaSimulacao.attributes.cpfCliente = _this.cliente.cpf.documento.numero +""+ _this.cliente.cpf.documento.digitoVerificador;
				_this.novaSimulacao.attributes.dataNascimento = formatadores.formatarData(_this.dadosSimulacao.dataNascimento);
				
				//pega a data atual
				var today = new Date();
				_this.novaSimulacao.attributes.dataContratacao = formatadores.formatarData(today);//_this.$el.find('#frmDataContratacao').val(); 
				console.log(_this);
				console.log("_this.simulacao.attributes.idConvenio: "+_this.novaSimulacao.attributes.idConvenio);
				console.log("_this.simulacao.attributes.cpfCliente: "+_this.novaSimulacao.attributes.cpfCliente);
				console.log("_this.simulacao.attributes.dataNascimento: "+_this.novaSimulacao.attributes.dataNascimento);				
				console.log("_this.simulacao.attributes.valorLiquido: "+_this.novaSimulacao.attributes.valorLiquido);
				console.log("_this.simulacao.attributes.prazo: "+_this.novaSimulacao.attributes.prazo);
				console.log("_this.simulacao.attributes.dataContratacao: "+_this.novaSimulacao.attributes.dataContratacao);
					
				loadCCR.stop();				
				
				
				_this.novaSimulacao.simular()
				.done(function sucesso(data) {
					loadCCR.stop();
					console.log(data);

					// trata retorno em caso de erro
					if (data.mensagemRetorno != 'SUCESSO') {
						Retorno.trataRetorno({codigo: 1, tipo: "AVISO", mensagem: "", idMsg: data.mensagemRetorno}, 'simular');
						return;
					}
					
					if(_this.dadosSimulacao.tipoSeguro == 2) {
						_this.novaSimulacao.attributes.simulacao = data.simulacaoSemSeguro;
							
						if(data.simulacaoSemSeguro.prestacao == "") {
							_this.novaSimulacao.attributes.valorPrestacao = _this.dadosSimulacao.simulacao.prestacao;
							_this.novaSimulacao.attributes.simulacao.prestacao = _this.dadosSimulacao.simulacao.prestacao;
							_this.novaSimulacao.attributes.valorMargem = _this.dadosSimulacao.valorMargem;
						} else {
							_this.novaSimulacao.attributes.valorPrestacao = data.simulacaoSemSeguro.prestacao;
							_this.novaSimulacao.attributes.valorMargem = _this.dadosSimulacao.valorMargem;
						}
						
					} else {
						_this.novaSimulacao.attributes.simulacao = data.simulacaoComSeguro;
						
						if(data.simulacaoComSeguro.prestacao == "") {
							_this.novaSimulacao.attributes.valorPrestacao = _this.dadosSimulacao.simulacao.prestacao;
							_this.novaSimulacao.attributes.simulacao.prestacao = _this.dadosSimulacao.simulacao.prestacao;
							_this.novaSimulacao.attributes.valorMargem = _this.dadosSimulacao.valorMargem;	
						} else {
							_this.novaSimulacao.attributes.valorPrestacao = data.simulacaoComSeguro.prestacao;
							_this.novaSimulacao.attributes.valorMargem = _this.dadosSimulacao.valorMargem;
						}
						
					}
					_this.simulacao.attributes.tipoSeguro = _this.$el.find('input[name="identificadorSeguro"]:checked').val();
					

					if(_this.novaSimulacao.attributes.simulacao.prestacao.indexOf('.') != -1) {
						if(_this.novaSimulacao.attributes.simulacao.prestacao.indexOf(',') != -1) {
							_this.novaSimulacao.attributes.simulacao.prestacao =  _this.novaSimulacao.attributes.simulacao.prestacao.replace('.', '').replace(',','.');
						} else {
							_this.novaSimulacao.attributes.simulacao.prestacao =  _this.novaSimulacao.attributes.simulacao.prestacao;
						}
					} else {
						_this.novaSimulacao.attributes.simulacao.prestacao =  _this.novaSimulacao.attributes.simulacao.prestacao.replace('.', '').replace(',','.');
					}
					
					_this.$el.find('#divResultadoSimulacao').html(_this.resultadoSimulacaoTemplate({simularResultado: _this.novaSimulacao.attributes}));
					
					
										
				})
				.error(function erro(jqXHR) {

					msgCCR.alerta("Funcionalidade Indísponivel!!!", function () {});
					loadCCR.stop();
				});
		},
		
		recuperarDados : function(){
			return _this.avaliacoes;
		},
		
		formatarData : function (data){
			var date = data.split("/");
			return new Date(date[2], date[1] - 1, date[0]);
		},
		
		solicitarAvaliacaoCliente : function(){
			loadCCR.start();
			delete _this.cliente.responseArqRef; 			 
			_this.avaliacao.solicitaAvaliacaoRiscoClienteSiric(_this.cliente);
 
			this.listenTo(_this.avaliacao,"showMensagemSolicitacaoAvaliacaoCliente", _this.showMensagemSolicitacaoAvaliacaoCliente);
			this.listenTo(_this.avaliacao,"showMensagemSolicitacaoAvaliacaoClienteError", _this.showMensagemSolicitacaoAvaliacaoClienteError);
			this.listenTo(_this.avaliacao,"controleBtnConsultarAvaliacaoClienteEnabled", _this.controleBtnConsultarAvaliacaoClienteEnabled);
			this.listenTo(_this.avaliacao,"controleBtnSolicitaAvaliacaoClienteDisabled", _this.controleBtnSolicitaAvaliacaoClienteDisabled);
		},
		
		consultaAvaliacaoCliente : function(){
			loadCCR.start();
			delete _this.cliente.responseArqRef;
			if(_this.cliente.responseArqRef != undefined) {
				_this.cliente.responseArqRef = undefined;
			}
			this.listenTo(_this.avaliacao,"showResultadoListaCliente",_this.showResultadoListaAvaliacaoCliente);
			_this.avaliacao.consultarListaAvaliacaoRisco(_this.cliente, false );
			return this;
		},
		
		consultarAvaliacaoOperacao : function(evento) {
			loadCCR.start();
			var _this = this;
			delete _this.cliente.responseArqRef;
			this.listenTo(_this.avaliacao,"showResultadoListaOperacao",_this.showResultadoListaAvaliacaoOperacao);
			this.listenTo(_this.avaliacao,"controleBtnConsultarAvaliacaoOperacaoDisabled",_this.controleBtnConsultarAvaliacaoOperacaoDisabled);
			_this.avaliacao.consultarListaAvaliacaoRisco(_this.cliente, true);
			return this;
		},
		
		
		solicitarAvaliacaoOperacao : function (){
			loadCCR.start();
			 var dados = _this.populaDadosOperacao();
			 var rendaSelecionada = null;
			 
			for (var obj in _this.cliente.renda) {
				var er = /^[0-9]+$/;
				if(er.test(obj)){
					if(_this.cliente.renda[obj].cpfCnpjFontePagadora == _this.dadosSimulacao.convenio.cnpjConvenente){
						rendaSelecionada = _this.cliente.renda[obj];
					}
				}
			}
			this.listenTo(_this.avaliacao,"showMensagemSolicitacaoAvaliacaoOperacao",_this.showMensagemSolicitacaoAvaliacaoOperacao);
			this.listenTo(_this.avaliacao,"controleBtnSolicitarAvaliacaoOperacaoDisabled",_this.controleBtnSolicitarAvaliacaoOperacaoDisabled);
			this.listenTo(_this.avaliacao,"controleBtnConsultarAvaliacaoOperacaoEnabled",_this.controleBtnConsultarAvaliacaoOperacaoEnabled);
			_this.avaliacao.solicitaAvaliacaoRiscoOperacionaSiric(dados, _this.cliente, rendaSelecionada); 
		},
		
		
		populaDadosOperacao: function(){
			var _this = this;
			var valorNaoFormatado = null;
			var dados= {
	    			dadosOperacao : {
	    				convenenteSIRIC : {}
	    			}
	    	};
			var dataAdimissao = null;
			
			for (var obj in _this.cliente.renda) {
				var er = /^[0-9]+$/;
				if(er.test(obj)){
					if(_this.cliente.renda[obj].cpfCnpjFontePagadora == _this.dadosSimulacao.convenio.cnpjConvenente){
						dataAdimissao = _this.cliente.renda[obj].dataAdimissao;
					}
				}
			}
			
			if(_this.novaSimulacao == null){
				var prazo = _this.dadosSimulacao.simulacao.prazo;	
				var valorPrestacao = _this.dadosSimulacao.simulacao.prestacao;
				var valorFinanciamento = _this.dadosSimulacao.simulacao.valorContrato;
				var taxa = _this.dadosSimulacao.simulacao.taxaJuros
			}else{
				if(_this.novaSimulacao.attributes == null) {
					var prazo = _this.dadosSimulacao.simulacao.prazo;	
					var valorPrestacao = _this.dadosSimulacao.simulacao.prestacao;
					var valorFinanciamento = _this.dadosSimulacao.simulacao.valorContrato;
					var taxa = _this.dadosSimulacao.simulacao.taxaJuros
				} else {
					var prazo = _this.novaSimulacao.attributes.simulacao.prazo;
					var valorPrestacao =_this.novaSimulacao.attributes.simulacao.prestacao;
					var valorFinanciamento = _this.novaSimulacao.attributes.simulacao.valorContrato;
					var taxa = _this.novaSimulacao.attributes.simulacao.taxaJuros
				}
			}
			
			var valorMargem = _this.dadosSimulacao.valorPrestacao;
			var cnpjConvenente = _this.dadosSimulacao.convenio.cnpjConvenente;
			var cpf = _this.cliente.cpf.documento.numero +""+ _this.cliente.cpf.documento.digitoVerificador;
			
			
			dados.dadosOperacao.produto = "110";
	    	dados.dadosOperacao.prazo = prazo;
	    	dados.dadosOperacao.pcTaxaJuros = taxa;
	    	dados.dadosOperacao.agencia =  _this.dadosSimulacao.agencia.idUnidade;
	    	valorNaoFormatado = valorMargem;
			valorNaoFormatado = formatadores.formatarMoedaSemFormato(valorNaoFormatado);
			valorNaoFormatado = formatadores.formatarMoedaParaFloat(valorNaoFormatado);
	    	dados.dadosOperacao.valorMargemConsignavel = valorNaoFormatado;
	    	
	    	valorNaoFormatado = formatadores.formatarMoedaParaFloat(valorFinanciamento);
	    	dados.dadosOperacao.valorFinanciamento = valorNaoFormatado;
	    	
	    	valorNaoFormatado = formatadores.formatarMoedaParaFloat(valorPrestacao);
	    	dados.dadosOperacao.valorPrestacaoNecessaria = valorNaoFormatado;
	    	dados.dadosOperacao.convenenteSIRIC.cnpj = cnpjConvenente;
			
			return dados;
				
		 },
		
		
		
		showResultadoListaAvaliacaoOperacao : function(){
			var _this = this;
			_this.avaliacaoRiscoOptemp = this.chegaAvaliacaoValida("400000110", _this.avaliacao.listaAvaliacao);

			if(_this.avaliacaoRiscoOptemp != null ){	
				if( _this.validaAvaliacaoOperacao(_this.avaliacaoRiscoOptemp)){
					_this.salvarAvaliacaoRisco(_this.avaliacaoRiscoOptemp);
					_this.controleBtnConsultarAvaliacaoOperacaoDisabled();
					$.get('../ccr-web/servicos/visao/contratacao/contratar/avaliar/panelAvaliacaoOperacao.html')
					.done(function(data) {						
						_this.template = _.template(data);
						$('#divResultadoAvaliacaoOperacao').removeClass('hidden');
						$("#divResultadoAvaliacaoOperacao").html( _this.template({avaliacaoRiscoValida : _this.avaliacaoRiscoOptemp}) );
						$('#btnAvancar').show();
						
					}).always(function() { 
						
					});
				}			
			}else{
				this.controleBtnSolicitaAvaliacaoClienteEnabled();
			}	
			
			loadCCR.stop();
		},
		
		
		validaAvaliacaoOperacao : function(avaliacao){
			var contrato = _this.dadosSimulacao.simulacao;
			var valorCPM = avaliacao.valorLimiteCPM;
			
			var prazoAvaliacao = parseInt(avaliacao.prazo);
			var prazoSimulacao = parseInt(contrato.prazo);
			
			//Verifica se o valor está formatado com o . separando a parte fracionária do valor
			var valorLimiteCPM = parseFloat(formatadores.formatarMoedaSemFormatoParaFloat(valorCPM));
			var valorPrestacao = parseFloat(contrato.prestacao);
			
			//Verifica se o valor está formatado com o . separando a parte fracionária do valor
			var valorFinanciamento = parseFloat(formatadores.formatarMoedaSemFormatoParaFloat(avaliacao.valorFinanciamento));
			var valorContrato = parseFloat(contrato.valorContrato);
			
			console.log("Valores: ", prazoAvaliacao, prazoSimulacao, valorLimiteCPM, valorPrestacao, valorFinanciamento, valorContrato);
			console.log("Validações: ", prazoAvaliacao < prazoSimulacao, valorLimiteCPM < valorPrestacao, valorFinanciamento < valorContrato);
			//Verifica a validade do prazo
			if(prazoAvaliacao < prazoSimulacao){
				Retorno.trataRetorno({codigo: -1, tipo: "AVISO", mensagem: "", idMsg: 'MA005'}, 'contrato');
				return false;
			}
			//Verifica se a capacidade de pagamento e maior que o valor da prestacao
			if(valorLimiteCPM < valorPrestacao){
				Retorno.trataRetorno({codigo: -1, tipo: "AVISO", mensagem: "", idMsg: 'MA006'}, 'contrato');
				return false;
			}	
			//Verifica a validade do valor do financiamento
			if(valorFinanciamento < valorContrato){
				Retorno.trataRetorno({codigo: -1, tipo: "AVISO", mensagem: "", idMsg: 'MA007'}, 'contrato');
				return false;
			}
			
			//Verifica se a avaliação está bloqueada por alçada
			var flagBloquadaAlcada = "0";
			//verifica se a alcada veio de uma consulta de avaliacao ou solicitacao de avaliacao
			if(avaliacao.flagBloquadaAlcada != undefined){
				flagBloquadaAlcada = avaliacao.flagBloquadaAlcada;
			}else if(avaliacao.flagBloqueoAlcada != undefined){
				flagBloquadaAlcada = avaliacao.flagBloqueoAlcada;
			}
			if(flagBloquadaAlcada != "0"){
				//Retorno.trataRetorno({codigo: 0, tipo: "AVISO", mensagem: "", idMsg: 'MA008'}, 'contrato');
				$("#divModalBloqueadaAlcada").modal("show");
			} 

	        return true;
		},
		
		chegaAvaliacaoValida : function(produto, colecaoAvaliacaoRisco){
			var colecaoAvRiscoTmp = [];
			var er = /^[0-9]+$/;
			for (var obj in colecaoAvaliacaoRisco) {
				if(er.test(obj)){
					if(colecaoAvaliacaoRisco[obj].produto == produto){
						var dataAtual = new Date();				
						var dataIniVal = new Date(this.formatDateMMDDAA(colecaoAvaliacaoRisco[obj].dataInicioValidade));
						var dataFimVal = new Date(this.formatDateMMDDAA(colecaoAvaliacaoRisco[obj].dataFimValidade));
						if((dataFimVal >= dataAtual && dataIniVal <= dataAtual)){
							colecaoAvRiscoTmp.push(colecaoAvaliacaoRisco[obj]);
						}
					}
				}	
			}
			return colecaoAvRiscoTmp[0];
		},
		
		showResultadoListaAvaliacaoCliente : function(){
			console.log(_this.avaliacao.listaAvaliacao);
			if(_this.avaliacao.listaAvaliacao != undefined) {
				var avaliacaoRiscoTmp = this.chegaAvaliacaoValida("110", _this.avaliacao.listaAvaliacao);
				if(avaliacaoRiscoTmp != null ){
					_this.avaliacaoClienteValida = avaliacaoRiscoTmp;
					_this.$el.find('#divAvaliacaoCliente').html(_this.avaliacaoClienteTemplate({avaliacao : avaliacaoRiscoTmp}));
					_this.controleBtnDesbloquearAvaliacaoDisabled();
					
					if( _this.validaAvaliacaoCliente(avaliacaoRiscoTmp)){
						_this.controleBtnConsultarAvaliacaoClienteDisabled();
						if(_this.numeroContrato == null){
							_this.popularContrato(_this.dadosSimulacao,_this.cliente, avaliacaoRiscoTmp);
						}
						
						if(avaliacaoRiscoTmp.bloqueioTomador == "1"){
							$('#btnDesbloquar').removeClass("hidden");
							_this.controleBtnDesbloquearAvaliacaoEnabled()
						}else{
							$('#btnDesbloquar').addClass('hidden');
							_this.showSimulacaoResultado();
						}
						
						if(_this.isGerente){		
							$('#btnDesbloquar')["0"].innerHTML ="Desbloquear";
						}else{
							$('#btnDesbloquar')["0"].innerHTML="Solicitar Desbloqueio";
						}
					}
				
				}else{
					this.controleBtnSolicitaAvaliacaoClienteEnabled();
					this.controleBtnConsultarAvaliacaoClienteDisabled();
				}	
			
				
				if(_this.op) {
					 _this.dadosSimulacao.idConvenio = _this.dadosSimulacao.convenio.id; 
					_this.showSimulacaoResultado();
				}
				
				loadCCR.stop();
			}
		},
		
		abrirPanelOperacao : function (){
			$.get('../ccr-web/servicos/visao/contratacao/contratar/avaliar/panelSolicitarAvaliacaoOperacao.html')
			.done(function(data) {
				_this.template = _.template(data);
				$("#divPanelAvaliacaOperacao").html( _this.template());
				
				if(_this.manter != null && _this.contrato.situacao.id == 15){
					_this.controleBtnSolicitarAvaliacaoOperacaoDisabled();
					_this.consultarAvaliacaoOperacao(null);
				}
			}).always(function() { 
			});
		},
			
		isGerenteLogado : function(){
			var isGerente = false;
			_this.avaliacao.isGerente();
			if(_this.avaliacao.isGerenteLogado){
				isGerente =  true;
			}
			return isGerente;
		},
		
			
		validaAvaliacaoCliente : function(avaliacaoRiscoCredito){	
			var situacao = avaliacaoRiscoCredito.situacao;
			if(situacao == 1 || situacao == 5){
				return true
			}
			
			return false;
		},
		
		formatDateMMDDAA : function(date){
			var from = date; 
			var numbers = from.match(/\d+/g); 
			var date = new Date(numbers[1] +"/"+ numbers[0]  +"/"+numbers[2]);
			return date;
		},
		
		
		
		checarAvaliacoesRiscoCliente : function(colecaoAvaliacaoRiscoCredito){
			var colecaoAvRiscoTmp = [];
			for (var obj in colecaoAvaliacaoRiscoCredito) {
				if(colecaoAvaliacaoRiscoCredito[obj].produto == "110"){
					colecaoAvRiscoTmp.push(colecaoAvaliacaoRiscoCredito[obj]);
				}
			}
			
			//colecao que sera retornada
			var colecaoAvRiscoRetorno = [];
			//verifica se existe avaliacoes do produto
			if(colecaoAvRiscoTmp.length > 0 ){
				var x = 0;
				for(; x < colecaoAvRiscoTmp.length ; x++ ){
					var avaliacaoRisco = colecaoAvRiscoTmp[x];
					//Validacao da avaliacao
					var validacao = this.validarAvaliacaoRisco(avaliacaoRisco);
					//Se for valida incluir na lista
					if(validacao.isValid){
						colecaoAvRiscoRetorno.push(avaliacaoRisco);
					}	
				}
			}
			return colecaoAvRiscoRetorno;
		},
		
		showMensagemSolicitacaoAvaliacaoCliente : function(){
			Retorno.trataRetorno({codigo: 0, tipo: "SUCESSO", mensagem: "", idMsg: 'MA004'}, 'contrato');
			loadCCR.stop();
		},

		showMensagemSolicitacaoAvaliacaoClienteError : function(){
			Retorno.trataRetorno({codigo: 0, tipo: "AVISO", mensagem: "", idMsg: 'MA011'}, 'contrato');
			loadCCR.stop();
		},
		
		showMensagemSolicitacaoAvaliacaoOperacao : function(){
			Retorno.trataRetorno({codigo: 0, tipo: "SUCESSO", mensagem: "", idMsg: 'MA004'}, 'contrato');
			loadCCR.stop();
		},
		
		popularContrato : function(data,cliente, avaliacaoRisco){
			
			if(_this.numeroContrato != null){
				return;
			}
			if(_this.contratoModelo==null){
				_this.contratoModelo = new Contrato();
			}
			_this.contratoModelo.attributes.nuContratoAplicacao = '000';
			_this.contratoModelo.attributes.icTipoCredito = '1';
			
			_this.contratoModelo.attributes.sicliCliente = JSON.stringify(_this.cliente);

			_this.contratoModelo.attributes.nuAgenciaConcessora = data.agencia.idUnidade;
			_this.contratoModelo.attributes.nuNaturalMovimento = data.agencia.numNatural;
			_this.contratoModelo.attributes.nuUnidadeMovimento = data.agencia.idUnidade;
			_this.contratoModelo.attributes.nuContrato = null;
			_this.contratoModelo.attributes.nuUnidadeMovimento = data.agencia.idUnidade;
			_this.contratoModelo.attributes.nuCpf = data.cpf != null ? String(data.cpf).replace(/[.\-\/]/g, '') : 0;
			_this.contratoModelo.attributes.nuCocli = cliente.cocliAtivo.cocliAtivo;
			_this.contratoModelo.attributes.coMatriculaCliente = _this.$el.find('#matriculaCliente').val();
			_this.contratoModelo.attributes.canalAtend = 1;			
			
			var rendas = cliente.renda;
			
			for(i= 0 ; i < rendas.length ; i++){
				if(rendas[i].cpfCnpjFontePagadora == data.convenio.cnpjFontePagadora){
					_this.contratoModelo.attributes.vrBrutoBeneficio = rendas[i].valorRendaBruta;
					_this.contratoModelo.attributes.vrLiquidoBeneficio =rendas[i].valorRendaLiquida;
					break;
				}
			}
			
			//Dados do Convenio
			var convenioVo = {};
			convenioVo.id = data.convenio.id;
			convenioVo.cnpjConvenente = data.convenio.cnpjConvenente;
			convenioVo.nome = data.convenio.nome;
			convenioVo.numContaCredito = data.convenio.numContaCredito;
			convenioVo.numAgenciaContaCredito = data.convenio.numAgenciaContaCredito;
			
			_this.contratoModelo.attributes.qtEmpregado = data.convenio.qtEmpregado;
			_this.contratoModelo.attributes.ddBeneficio = data.convenio.diaCreditoSal;
            _this.contratoModelo.attributes.nuCnpjFontePagadora = data.convenio.cnpjFontePagadora;
            _this.contratoModelo.attributes.nuAgenciaContaDebito = data.convenio.numAgenciaContaCredito;
            _this.contratoModelo.attributes.nuContaDebito = data.convenio.numContaCredito;
            _this.contratoModelo.attributes.nuDvContaDebito = data.convenio.numDvContaCredito;
            _this.contratoModelo.attributes.nuOperacaoContaDebito = data.convenio.nuOperacaoContaDebito;
            	
			_this.contratoModelo.attributes.convenioTO = convenioVo;
			//Dados Simulacao
			
			var simulacao = data;
			_this.contratoModelo.attributes.pcTaxaSeguro = simulacao.simulacao.pcTaxaSeguro;
			_this.contratoModelo.attributes.vrContrato = simulacao.simulacao.valorContrato;
			_this.contratoModelo.attributes.vrTotalContrato = simulacao.simulacao.valorTotalContrato;
			_this.contratoModelo.attributes.vrLiquidoContrato = simulacao.simulacao.valorLiquido;
			
			if(simulacao.simulacao.prestacao.indexOf('.') != -1) {
				if(simulacao.simulacao.prestacao.indexOf(',') != -1) {
					_this.contratoModelo.attributes.vrPrestacao =  simulacao.simulacao.prestacao.replace('.', '').replace(',','.');
				} else {
					_this.contratoModelo.attributes.vrPrestacao =  simulacao.simulacao.prestacao;
				}
			} else {
				_this.contratoModelo.attributes.vrPrestacao =  simulacao.simulacao.prestacao.replace('.', '').replace(',','.');
			}
				
			_this.contratoModelo.attributes.vrIof = simulacao.simulacao.iof;
			_this.contratoModelo.attributes.vrJuroAcerto = simulacao.simulacao.juroAcerto;
			_this.contratoModelo.attributes.vrSeguroPrestamista = simulacao.simulacao.valorSeguro;
			_this.contratoModelo.attributes.qtDiaJuroAcerto = simulacao.camposComuns.quantDiasJurosAcerto
			_this.contratoModelo.attributes.pzContrato = simulacao.prazo;
			_this.contratoModelo.attributes.pcTaxaJuroContrato = simulacao.simulacao.taxaJuros;
			_this.contratoModelo.attributes.pcCetMensal = simulacao.simulacao.cetmensal;
			_this.contratoModelo.attributes.pcCetAnual = simulacao.simulacao.cetanual;
			_this.contratoModelo.attributes.pcCetJuroAcerto = simulacao.simulacao.cetjuroAcerto;
			_this.contratoModelo.attributes.pcCetIof = simulacao.simulacao.cetiof;
			_this.contratoModelo.attributes.pcTaxaJuroAnual = simulacao.simulacao.taxaJuros;
			_this.contratoModelo.attributes.pcTaxaEfetivaAnual = simulacao.simulacao.taxaEfetivaAnual;
			_this.contratoModelo.attributes.pcTaxaEfetivaMensal = simulacao.simulacao.taxaEfetivaMensal;
			_this.contratoModelo.attributes.vrCetAnual = simulacao.simulacao.vrCetAnual;
			_this.contratoModelo.attributes.vrCetMensal = simulacao.simulacao.vrCetMensal;
			
			if(simulacao.tipoSeguro == 1) {
				_this.contratoModelo.attributes.icSeguroPrestamista = "S";
				_this.contratoModelo.attributes.pcCetSeguro = simulacao.simulacao.cetseguro;
			} else {
				_this.contratoModelo.attributes.icSeguroPrestamista = "N";
			}
		
			_this.contratoModelo.attributes.pcAliquotaBasica = simulacao.camposComuns.iofAliquotaBasica;
			_this.contratoModelo.attributes.pcAliquotaComplementar = simulacao.camposComuns.iofAliquotaComplementar;
			
			
			//Datas
			_this.contratoModelo.attributes.dtVncmoPrimeiraPrestacao = _this.formatarData(simulacao.camposComuns.vencimentoPrimeiraPrestacao);
			
			if(_this.numeroContrato != null) {
				_this.contratoModelo.attributes.dtContrato = _this.formatarData(_this.contrato.dtContrato);
			} else {
				_this.contratoModelo.attributes.dtContrato = _this.formatarData(simulacao.dataContratacao);
			}
			
			_this.contratoModelo.attributes.dtBaseCalculo = _this.formatarData(simulacao.camposComuns.basePrimeiraPrestacao);
			_this.contratoModelo.attributes.dtVencimento = _this.formatarData(simulacao.simulacao.vencimentoContrato);
			_this.contratoModelo.attributes.situacao={};
			
			if(avaliacaoRisco.bloqueioTomador == "1"){
				_this.contratoModelo.attributes.situacao.id=13;
			}else{
				_this.contratoModelo.attributes.situacao.id=14;
			}

			_this.contratoModelo.attributes.situacao.tipo=2;
			console.log(_this.contratoModelo.attributes);
			_this.contratoModelo.contratar(_this.contratoModelo.attributes).done(function sucesso(data) {	
				//verificar se é gerends 
				if(avaliacaoRisco.bloqueioTomador != "1"){
					_this.salvarAvaliacaoRisco(avaliacaoRisco);
				}
			
				if(data.tipoRetorno == 'ERRO_EXCECAO'){
					Retorno.trataRetorno({codigo: -1, tipo: "AVISO", mensagem: "Ocorreu um erro ao salvar o contrato!"});
					rolarPaginaParaCima();
				}else{
					_this.contrato=data;
					
					_this.numeroContrato = data.nuContrato;	
				}
			})
			.error(function erro(jqXHR) {
				if(jqXHR && jqXHR.responseText && jqXHR.responseText != ""){
					Retorno.exibeErroDetalhe(jqXHR.responseText);
				}else{
					Retorno.trataRetorno({codigo: -1, tipo: "ERRO_EXCECAO", mensagem: "Ocorreu um erro ao salvar a proposta!"});
				}
			});
		},
		
		salvarAvaliacaoRisco : function(avaliacaoRisco){
			_this.avaliacao.salvarAvaliacaoRisco(avaliacaoRisco).done(function success(dados) {
//				if(_this.nuAvaliacaoCliente == null) {
//					_this.nuAvaliacaoCliente = dados.idAvaliacao;
//				} else {
//					_this.nuAvaliacaoOperacao = dados.idAvaliacao;
//				}
				 var situacao = avaliacaoRisco.produto == "110" ? 14 : 15;
				_this.atualizaContratoAvaliacaoRisco(situacao, 2, "", dados.idAvaliacao);
			});
		},
		
		consultarContrato : function(id) {
			if(_this.contratoModelo==null){
				_this.contratoModelo = new Contrato();
			}
			_this.contratoModelo.consultarPorId(id).done(function sucesso(data) {
				_this.contrato = data;
				_this.cliente = new Cliente();
				_this.cliente.consultarDadosSicliPorCPF(_this.contrato.nuCpf)
				.done(function sucesso(dataRef) {
					if(dataRef != undefined && dataRef.dados != undefined && dataRef.dados.nomeCliente != undefined && dataRef.dados.nomeCliente.nome != undefined)
					_this.cliente=dataRef.dados;
					_this.numeroContrato = _this.contrato.nuContrato;
					_this.carregarSimulacaoPorContrato();
				});
			})
			.error(function erro(jqXHR) {
				if(jqXHR && jqXHR.responseText && jqXHR.responseText != ""){
					Retorno.exibeErroDetalhe(jqXHR.responseText);
				}else{
					Retorno.trataRetorno({codigo: -1, tipo: "ERRO_EXCECAO", mensagem: "Ocorreu um erro ao buscar dados da empresa!"});
				}
			});
		
		},
		
		carregarSimulacaoPorContrato : function() {
			_this.simulacaoContrato = new Simulacao();
			
			_this.simulacaoContrato.attributes.idConvenio = _this.contrato.convenioTO.id;
			_this.simulacaoContrato.attributes.prazo 		  = _this.contrato.pzContrato; 
			_this.simulacaoContrato.attributes.taxaJuros 	  = _this.contrato.pcTaxaJuroContrato; 
			//Dado informado na tela (Verificar se será enviado pelo SISRH)
//			_this.simulacaoContrato.attributes.valorPrestacao = _this.contrato.vrPrestacao;
			if(_this.contrato.vrPrestacao.toString().indexOf('.') != -1 && _this.contrato.vrPrestacao.toString().indexOf(',') != -1) {
				_this.simulacaoContrato.attributes.valorPrestacao = _this.contrato.vrPrestacao;	
			} else {
				_this.simulacaoContrato.attributes.valorPrestacao = mascararMoeda(_this.contrato.vrPrestacao);		
			}
			//_this.simulacaoContrato.attributes.valorLiquido   = _this.$el.find("#valorLiquidoRecalculoAvaliar").val() === "" ? "" : _this.$el.find("#valorLiquidoRecalculoAvaliar").val();
									
			_this.simulacaoContrato.attributes.cpfCliente = _this.cliente.cpf.documento.numero +""+ _this.cliente.cpf.documento.digitoVerificador;
			_this.simulacaoContrato.attributes.dataNascimento = formatadores.formatarData(_this.cliente.dataNascimento.data);
			
			//pega a data atual
			var today = new Date();
			_this.simulacaoContrato.attributes.dataContratacao = formatadores.formatarData(today);//_this.$el.find('#frmDataContratacao').val(); 
				
			loadCCR.start();				
			
			_this.simulacaoContrato.simular()
			.done(function sucesso(data) {
				loadCCR.stop();
				console.log(data);
				// trata retorno em caso de erro
				if (data.mensagemRetorno != 'SUCESSO') {
					Retorno.trataRetorno({codigo: 1, tipo: "AVISO", mensagem: "", idMsg: data.mensagemRetorno}, 'simular');
					return;
				}
				_this.dadosSimulacao=data;
				_this.dadosSimulacao.convenio=_this.contrato.convenioTO;
				if(_this.contrato.icSeguroPrestamista=="S"){
					_this.dadosSimulacao.simular=data.simulacaoComSeguro;
					_this.dadosSimulacao.simulacao=data.simulacaoComSeguro;
					_this.dadosSimulacao.prazo=data.simulacaoComSeguro.prazo;
				}else{
					_this.dadosSimulacao.simular=data.simulacaoSemSeguro;
					_this.dadosSimulacao.simulacao=data.simulacaoSemSeguro;
					_this.dadosSimulacao.prazo=data.simulacaoSemSeguro.prazo;
				}
				_this.dadosSimulacao.agencia=_this.agencia;
								
				$('#btnAvancar').hide();
				
				/**
				 * Verifica se o valor esta vindo com '.' e/ou ','
				 * deixando-o preparado para formatação
				 */
				if(_this.dadosSimulacao.simulacao.prestacao.indexOf('.') != -1) {
					if(_this.dadosSimulacao.simulacao.prestacao.indexOf(',') != -1) {
						_this.dadosSimulacao.simulacao.prestacao =  _this.dadosSimulacao.simulacao.prestacao.replace('.', '').replace(',','.');
					} else {
						_this.dadosSimulacao.simulacao.prestacao =  _this.dadosSimulacao.simulacao.prestacao;
					}
				} else {
					_this.dadosSimulacao.simulacao.prestacao =  _this.dadosSimulacao.simulacao.prestacao.replace('.', '').replace(',','.');
				}
				
				_this.$el.html(_this.simulacaoInicialTemplate({simular: _this.dadosSimulacao}));

				if(_this.manter != null) {
					$("#btnConsultarAvaliacaoCliente").attr('disabled', true);
					_this.consultaAvaliacaoCliente(); 
					$('#aDadosCliente').removeClass('complete');
				}

				loadMaskEl(_this.$el);
				listenToDatepickerChange(_this.$el, _this.changed);
								
				return _this;
													
			})
			.error(function erro(jqXHR) {

				msgCCR.alerta("Funcionalidade Indísponivel!!!", function () {});
				loadCCR.stop();
			});
		
		},
		
		desbloquerAvaliacao : function(){
			if(_this.isGerente){
				this.listenTo(_this.avaliacao,"showSimulacaoResultado",_this.showSimulacaoResultado);
				var cpf = _this.cliente.cpf.documento.numero +""+ _this.cliente.cpf.documento.digitoVerificador
				loadCCR.start();
				_this.avaliacao.desbloquearAvaliacaoRisco(cpf ,_this.avaliacaoClienteValida.codigoAvaliacao, _this.dadosSimulacao.agencia.idUnidade);
				_this.salvarAvaliacaoRisco(_this.avaliacaoClienteValida);
			}else{
				Retorno.trataRetorno({codigo: 0, tipo: "AVISO", mensagem: "", idMsg: 'MA009'}, 'contrato');
			}
		},
		
		atualizaContratoAvaliacaoRisco : function(id, tipo, descricao, nrAvaliacao){
			var contratoSituacao = {
					nuContrato : _this.numeroContrato,
					situacao : {
						id : id,
						tipo :tipo,
						descricao : descricao
					},
					idAvaliacao : nrAvaliacao
			}
			_this.contratoModelo.atualizarSituacaoContrato(contratoSituacao).done(function sucesso(data) {
				//loadCCR.stop();
			});
		},
		
		controleBtnDesbloquearAvaliacaoDisabled : function (){
			$('#btnDesbloquar').attr('disabled',true);
		},
		
		controleBtnDesbloquearAvaliacaoEnabled : function (){
			$('#btnDesbloquar').attr('disabled',false);
		},
		
		controleBtnSolicitaAvaliacaoClienteDisabled : function (){
			$('#btnSolicitarAvaliacaoCliente').attr('disabled',true);
		},
		
		controleBtnSolicitaAvaliacaoClienteEnabled : function (){
			Retorno.trataRetorno({codigo: 0, tipo: "AVISO", mensagem: "", idMsg: 'MA003'}, 'contrato');
			$('#btnSolicitarAvaliacaoCliente').attr('disabled',false);
		},
		
		controleBtnSolicitarAvaliacaoOperacaoDisabled : function (){
			$('#btnSolicitarAvaliacaoOperacao').attr('disabled',true);
		},
		
		controleBtnSolicitarAvaliacaoOperacaoEnabled : function (){
			$('#btnSolicitarAvaliacaoOperacao').attr('disabled',false);
		},
		
		controleBtnConsultarAvaliacaoOperacaoDisabled : function (){
			$('#btnConsultarAvaliacaoOperacao').attr('disabled',true);
		},
		
		controleBtnConsultarAvaliacaoOperacaoEnabled : function (){
			$('#btnConsultarAvaliacaoOperacao').attr('disabled',false);
		},
		
		controleBtnConsultarAvaliacaoClienteDisabled : function(){
			$('#btnConsultarAvaliacaoCliente').attr('disabled',true);
		},
		
		controleBtnConsultarAvaliacaoClienteEnabled : function(){
			$('#btnConsultarAvaliacaoCliente').attr('disabled',false);
		},
	});

	return ContratarConsignadoAvaliar;
	
});