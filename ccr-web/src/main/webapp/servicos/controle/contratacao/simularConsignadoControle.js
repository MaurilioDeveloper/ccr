/**
 * @author F620600
 * 
 * JavaScript que controla as ações das paginas: alterarIOF.html
 * 
 * @version 1.0.0.0
 * 
 * 
 */
define(['enumeracoes/eMensagemCCR',
        'enumeracoes/eTipoServicos',
        'enumeracoes/eMensageria',
        'util/retorno',
        'util/modelo/unidade',
        'modelo/cliente/cliente',
        'modelo/convenios/convenioColecao',
        'modelo/taxas/taxaJurosColecao',
        'modelo/contratacao/simulacao',
        'controle/contratacao/detalheConsultaCETControle',
        'text!visao/contratacao/simular/simularConsignado.html',
        'text!visao/contratacao/simular/panelDadosCliente.html',
        'text!visao/contratacao/simular/panelListaConvenio.html',
        'text!visao/contratacao/simular/panelListaPrazo.html',
        'text!visao/contratacao/simular/panelResultadoSimulacao.html',
        'text!visao/contratacao/detalheConsultaCET.html',
        ], 
function(EMensagemCCR, ETipoServicos, EMensageria, Retorno, Unidade, Cliente, ConvenioColecao, TaxaJurosColecao, Simulacao, DetalheConsultaCETControle, simularConsignadoTpl, dadosClienteTpl, listaConvenioTpl, listaPrazoTpl, resultSimulacaoTpl, detalheConsultaCetTpl){

	var _this = null;
	var SimularMainControle = Backbone.View.extend({

		className: 'SimularMainControle',
		
		/**
		 * Templates
		 */
		simularConsignadoTemplate       : _.template(simularConsignadoTpl),
		dadosClienteTemplate			: _.template(dadosClienteTpl), 
		listaConvenioTemplate			: _.template(listaConvenioTpl),
		listaPrazoTemplate				: _.template(listaPrazoTpl),
		resultSimulacaoTemplate			: _.template(resultSimulacaoTpl),
		detalheCetTemplate				: _.template(detalheConsultaCetTpl),
		
		/**
		 * Variaveis da classe
		 */
		validator   		: new Validators(),
		cnpjValidator 		: new CNPJValidator(),
		message     		: new Message(),
		unidade				: new Unidade(),
		simulacao			: null,
		ctrlCliente 		: null,
		comando     		: null,
		modelo				: null,
		modeloAvaliacao		: null,
		parametroOperacao 	: null,
		convenios			: null,
		simulacaoSel		: null,
		sicli				: null,
		avaliacao			: null,
		simulacaoComSeguro	: '',
		matricula			: '',
		cpf					: null,
		cnpjConvenio		: null,
		idConvenio			: null,
		dataNascimento		: null,
		identificador		: "1",
		identificadorValor	: "1",
		mostrarDetalhe		: false,
		faixaJuroPadrao		: null,
		taxaList			: [],
		agenciaConcessora	: "",
		dadosSimulacao		: null,
				
		/**
		 * Função que faz bind das ações e interações da pagina com as funções
		 * javascript
		 * 
		 */
		events : {			
			'click #btnConsultarCpf'									: 'consultarCliente',
			'click #btnConsultarConvenio'								: 'consultarConvenios',
			'click #radioCpf'											: 'selecionarIdentificador',
			'click #radioCnpj'											: 'selecionarIdentificador',
			'click #radioCodigoConvenio'								: 'selecionarIdentificador',
			'click #radioNomeConvenio'									: 'selecionarIdentificador',
			'click #radioValorLiquido'									: 'selecionarValor',
			'click #radioValorPrestacao'								: 'selecionarValor',

			'click #btnSair'											: 'sair',			
			'click #btnSair2'											: 'sair',			
			'click #radioConvenio'										: 'consultarPrazos',	
			'click #btnSimular'											: 'simular',
			
			'blur #frmPrazo'											: 'preencheFaixaPadrao',
			'click a#controleDetalheSimulacao' 							: 'controleDetalheSimulacao',
			'click td.btn-toggle-popupcet'  							: 'showDadosConsultaCET',
			'click a.btn-close-popupcet'								: 'closeDadosConsultaCET',
			'click a.btn-print-popupcet'  								: 'printDadosConsultaCET',

		},
				
		
		/**
		 * Função padrão de incialização do template html
		 * 
		 */
		initialize : function() {
			console.log("Simular Main controle - initialize");
			
			// pra nao ter problema de pegar outro 'this'
			_this = this;

			// recuperar agencia concessora
			_this.unidade.consultaUnidadePorNumeroUnidade(usuario.attributes.empregado.numeroUnidade);
			
			// configura validator
			_this.validator.withErrorRender(new BootstrapErrorRender());
			_this.validator.addValidator(new MaiorQueZeroValidator());
			
			_this.convenioColecao = new ConvenioColecao();
			_this.ctrlConsultaCET = new DetalheConsultaCETControle();
			
		},
			
		/**
		 * Função padrão para reenderizar os dados html na tela Nessa função é
		 * carregado a mascara para CPF e colocadao o foco no campo de CPF
		 * Retorna o proprio objeto criado
		 * 
		 * @returns {anonymous}
		 */
		render : function() {
			_this = this;
			
			console.log("Simular Main - render");	
			
			// Inclui o template inicial no el deste controle
			_this.$el.html(_this.simularConsignadoTemplate());
			loadMaskEl(_this.$el.find('#div_form_filtro_simulacao'));
			listenToDatepickerChange(_this.$el, _this.changed);
			
			// Create the widget.  Route searches to a custom handler
		    _this.$('#nomeConvenio')
		         .autocomplete({ source: $.proxy( _this.findConvenios, _this), minLength: 3 });

			return _this;
		},
		
		findConvenios: function ( request, response ) {
			console.log("Simular Main - findConvenios");	

			$.when( _this.convenioColecao.consultarConveniosParaSimulacao(0, 0, request.term, [1]) )
		         .then(function ( data ) { response( _.map(data.listaRetorno, function ( d ) { return { value: d.nome, label: d.nome }; }) ); });
		 
	   },
		   
		selecionarIdentificador :  function(evt){
			console.log("Simular Main - selecionarIdentificador");
			_this.validator.withForm('formSimular').cleanErrors();

			_this.limparConsulta();
			
			_this.$el.find("#panel_dados_cliente").hide();
			_this.$el.find("#panel_lista_convenio").hide();
			_this.$el.find("#panel_lista_prazo").hide();
			_this.$el.find("#panel_form_simulacao").hide();
			_this.$el.find("#panel_result_simulacao").hide();
			_this.$el.find("#formActionSimular").hide();
			_this.$el.find("#formActionSair").show();

			_this.identificador = evt.target.value;

			// apresenta opções de filtro de acordo com identificador
			// selecionado
			switch (_this.identificador){
			case '1': // CPF

				_this.$el.find("#div_cpf").show();
				_this.$el.find("#div_cnpj").hide();
				_this.$el.find("#div_codigo_convenio").hide();
				_this.$el.find("#div_nome_convenio").hide();

				break;
			case '2': // CNPJ

				_this.$el.find("#div_cpf").hide();
				_this.$el.find("#div_cnpj").show();
				_this.$el.find("#div_codigo_convenio").hide();
				_this.$el.find("#div_nome_convenio").hide();

				break;
			case "3": // Codigo convenio

				_this.$el.find("#div_cpf").hide();
				_this.$el.find("#div_cnpj").hide();
				_this.$el.find("#div_codigo_convenio").show();
				_this.$el.find("#div_nome_convenio").hide();

				break;
			case "4":

				_this.$el.find("#div_cpf").hide();
				_this.$el.find("#div_cnpj").hide();
				_this.$el.find("#div_codigo_convenio").hide();
				_this.$el.find("#div_nome_convenio").show();

				break;
			}
		},
		
		
		selecionarValor :  function(evt){
			console.log("Simular Main - selecionarValor");
			
			_this.identificadorValor = evt.target.value;

			// apresenta opções de filtro de acordo com identificador
			// selecionado
			switch (_this.identificadorValor){
			case '1': // CPF

				_this.$el.find("#div_valor_liquido").show();
				_this.$el.find("#div_valor_prestacao").hide();

				break;
			case '2': // CNPJ

				_this.$el.find("#div_valor_liquido").hide();
				_this.$el.find("#div_valor_prestacao").show();

				break;
			}
		},
		
		consultarCliente : function (){
			console.log("Simular Consignado - consultarCliente");
			loadCCR.start();				

			_this.validator.withForm('formFiltro').cleanErrors();

			if (_this.validator.withForm('formFiltro').validate()){

				_this.cpf = _this.$el.find('#numeroCpf').val();
				_this.cliente = new Cliente();
				_this.cliente.consultarDadosSicliPorCPF(_this.cpf)
				.done(function sucesso(data) {
					loadCCR.stop();
					var dataRetorno =data;
					data=data.dados;
					// trata retorno em caso de erro
					if (data.mensagemRetorno != undefined) {
						_this.$el.find("#panel_dados_cliente").hide();
						Retorno.trataRetorno({codigo: 1, tipo: "AVISO", mensagem: "", idMsg: data.mensagemRetorno}, 'SICLI');
						return;
					}
					
					if(data.responseArqRef.status.codigo != 0) {
						var dataRet = {};
						dataRet.dados = data;
						Retorno.validarRetorno(dataRet);					
						loadCCR.stop();
						return;
					}

					
					_this.dataNascimento = data.dataNascimento.data;
					var rendaList = [];
					data.renda.forEach(function(renda){
						if (_this.cnpjValidator.validate(renda.cpfCnpjFontePagadora) && renda.tipoRenda == "F" 
							&& (renda.tipoFontePagadora == "1" || renda.tipoFontePagadora == "2" || renda.tipoFontePagadora == "3")){
							
							rendaList.push(renda);
						}
					});
					
					if (rendaList.length != 0){
						// Inclui o template inicial no el deste controle
						_this.$el.find("#panel_dados_cliente").show();
						_this.$el.find('#panel_dados_cliente').html(_this.dadosClienteTemplate({cliente: data}));
						
						// Consultar convenios
						_this.listarConveniosCliente(rendaList);
					}else{
						_this.$el.find("#panel_dados_cliente").hide();
						Retorno.trataRetorno({codigo: 1, tipo: "AVISO", mensagem: "", idMsg: 'MA0097'}, 'convenio');
						return;
					}
					
				})
				.error(function erro(jqXHR) {
					Retorno.trataRetorno({codigo: -1, tipo: "ERRO_EXCECAO", mensagem: "Ocorreu um erro ao consultar o cliente!"}, 'simular', jqXHR);
					loadCCR.stop();
				});
				
			}else{
				loadCCR.stop();
			}

			
		},
		
		listarConveniosCliente : function (listaRenda){
			console.log("Simular Consignado - listarConveniosCliente");
			loadCCR.start();				

			_this.convenioColecao = new ConvenioColecao();
			_this.convenioColecao.listarConvenioPorFontePagadora(listaRenda)
			.done(function sucesso(data) {
				loadCCR.stop();

				if (data.listaRetorno.length != 0){
					_this.$el.find("#panel_lista_convenio").show();
					_this.$el.find('#panel_lista_convenio').html(_this.listaConvenioTemplate({convenios: data.listaRetorno}));
					
					if (data.listaRetorno.length == 1 
							&& data.listaRetorno[0].situacao.descricao == 'ATIVO'){
						idConvenioSel = _this.$el.find("#radioConvenio").get(0).value;
						_this.$el.find('input[value="'+ idConvenioSel +'"]').prop("checked", true);
						_this.consultarPrazos(idConvenioSel);
					}

				}else {
					
					Retorno.trataRetorno({codigo: 1, tipo: "AVISO", mensagem: "", idMsg: 'MA004'}, 'simular');
					return;
				}
				
			})
			.error(function erro(jqXHR) {

				Retorno.trataRetorno({codigo: -1, tipo: "ERRO_EXCECAO", mensagem: "Ocorreu um erro ao listar o convênio!"}, 'simular', jqXHR);
				loadCCR.stop();
			});
			
		},

		consultarConvenios : function (){
			console.log("Simular Consignado - consultar");
			loadCCR.start();				
			
			_this.$el.find("#panel_dados_cliente").hide();
			_this.$el.find("#panel_lista_convenio").hide();
			_this.$el.find("#panel_lista_prazo").hide();
			_this.$el.find("#panel_form_simulacao").hide();
			_this.$el.find("#panel_result_simulacao").hide();
			_this.$el.find("#formActionSimular").hide();
			_this.$el.find("#formActionSair").show();
			_this.validator.withForm('formFiltro').cleanErrors();

			if (_this.validator.withForm('formFiltro').validate()){
				_this.convenioColecao = new ConvenioColecao();
				_this.cnpj = _this.$el.find('#numeroCnpj').val();
				_this.coConvenio = _this.$el.find('#codigoConvenio').val();
				_this.noConvenio = _this.$el.find('#nomeConvenio').val();
				
				// Adicionar canais para consulta:
				// A princípio filtrar apenas por agencia, futuramente deve
				// ser adicionado outros canais.
				var canais = [1]; // 1 - Agencia
			
				_this.convenioColecao.consultarConveniosParaSimulacao(_this.cnpj, _this.coConvenio, _this.noConvenio, canais)
				.done(function sucesso(data) {
					loadCCR.stop();
					
					if (data.listaRetorno.length != 0){
						_this.$el.find("#panel_lista_convenio").show();
						_this.$el.find('#panel_lista_convenio').html(_this.listaConvenioTemplate({convenios: data.listaRetorno}));
						
						if (data.listaRetorno.length == 1 
								&& data.listaRetorno[0].situacao.descricao == 'ATIVO'){
							idConvenioSel = _this.$el.find("#radioConvenio").get(0).value;
							_this.$el.find('input[value="'+ idConvenioSel +'"]').prop("checked", true);
							_this.consultarPrazos(idConvenioSel);
						}
					}else{
	
						Retorno.trataRetorno({codigo: 1, tipo: "AVISO", mensagem: "", idMsg: 'MA004'}, 'simular');
						return;
					}
				})
				.error(function erro(jqXHR) {
	
					Retorno.trataRetorno({codigo: -1, tipo: "ERRO_EXCECAO", mensagem: "Ocorreu um erro ao simular ao listar os convênios!"}, 'simular', jqXHR);
	
					loadCCR.stop();
				});
			}else{
				loadCCR.stop();
			}
			
		},

		consultarPrazos : function (evt){
			console.log("Simular Consignado - consultarPrazos");
			_this.$el.find("#panel_result_simulacao").hide();

			
			var idConvenioSel = null;
			
			if (evt.target != undefined){
				_this.idConvenio = evt.target.value;				
			}else{
				_this.idConvenio = evt;
			}
			console.log(_this.idConvenio);
			loadCCR.start();				

			_this.taxaJurosColecao = new TaxaJurosColecao();

			_this.taxaJurosColecao.tipoConsulta = "CONVENIO";
			_this.taxaJurosColecao.codigoTaxa = _this.idConvenio;
			_this.taxaJurosColecao.canal = "1";

			_this.taxaJurosColecao.listarPrazoConvenio()
			.done(function sucesso(data) {
				loadCCR.stop();
				
				if (data.listaTaxaJuro.length != 0){
					_this.$el.find("#panel_lista_prazo").show();
					_this.$el.find('#panel_lista_prazo').html(_this.listaPrazoTemplate({taxas: data.listaTaxaJuro}));				
					
					_this.faixaJuroPadrao =  data.faixaJuroPadrao;
					_this.taxaList = data.listaTaxaJuro;
					
					_this.montaFormSimular();

				}else{
					_this.$el.find("#panel_lista_prazo").hide();
					_this.$el.find("#panel_form_simulacao").hide();
					_this.$el.find("#formActionSimular").hide();
					_this.$el.find("#formActionSair").show();
					
					Retorno.trataRetorno({codigo: 1, tipo: "AVISO", mensagem: "", idMsg: 'MA006'}, 'manterJuros');
					return;
				}
			})
			.error(function erro(jqXHR) {

				Retorno.trataRetorno({codigo: -1, tipo: "ERRO_EXCECAO", mensagem: "Ocorreu um erro ao consultar os prazos!"}, 'simular', jqXHR);
				loadCCR.stop();
			});
			
		},

		montaFormSimular :	function (){

			// Apresenta formulário para simulação
			_this.$el.find("#panel_form_simulacao").show();
			
			if (_this.identificador != "1"){
				_this.$el.find("#div_data_nascimento").show();
				$("#div_data_nascimento").addClass('control-group');
				
			}else{
				_this.$el.find("#div_data_nascimento").hide();
				$("#div_data_nascimento").removeClass('control-group');
				
				_this.$el.find("#frmCpfCliente").val(_this.cpf);
			}
			
			_this.$el.find("#frmDataContratacao").val($.datepicker.formatDate('dd/mm/yy', new Date()));
			_this.$el.find("#formActionSimular").show();
			_this.$el.find("#formActionSair").hide();
			
			_this.$el.find('#frmValorLiquido').val('');
			_this.$el.find('#frmValorPrestacao').val('');
			_this.$el.find('#frmPrazo').val('')
			_this.$el.find("#frmDataNascimento").val('');

			loadMaskEl(_this.$el.find('#panel_form_simulacao'));

		},
		
		sair : function (){
			console.log("Simular Main - sair");
			location.reload();
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
		
		limparConsulta : function () {
			
			_this.$el.find("#numeroCpf").val('');
			_this.$el.find("#numeroCnpj").val('');
			_this.$el.find("#codigoConvenio").val('');
			_this.$el.find("#nomeConvenio").val('');
			
			_this.$el.find("#formSimular")[0].reset();
		},	
		
		simular : function (){
			console.log("Simular Main - simular");

			
			_this.validator.withForm('formSimular').cleanErrors();
			
			if (_this.identificadorValor == "1") {
				if(_this.$el.find('#frmValorLiquido').val()=="0,00"){
					_this.$el.find('#frmValorLiquido').val('');
				} 
			}else {
				if(_this.$el.find('#frmValorPrestacao').val()=="0,00"){
					_this.$el.find('#frmValorPrestacao').val('');
				} 
			}

			if (_this.validator.withForm('formSimular').validate()){
				
				loadCCR.start();				
				
				if ((_this.$el.find('#frmValorLiquido').val() == "0,00" || _this.$el.find('#frmValorLiquido').val() == "") 
					&& (_this.$el.find('#frmValorPrestacao').val() == "0,00" || _this.$el.find('#frmValorPrestacao').val() == "")){

					loadCCR.stop();				

					$('#frmValorLiquido').parent().closest('div.control-group').addClass('error');
					$('#frmValorPrestacao').parent().closest('div.control-group').addClass('error');

					Retorno.trataRetorno({codigo: 1, tipo: "AVISO", mensagem: "", idMsg: 'MA0018'}, 'simular');
					return;
				}
				
				_this.simulacao = new Simulacao();
				if (_this.identificador == "1") {
					_this.simulacao.attributes.cpfCliente = _this.cpf;
					_this.simulacao.attributes.dataNascimento = formatadores.formatarData(_this.dataNascimento);
				}else {
					_this.simulacao.attributes.dataNascimento = _this.$el.find('#frmDataNascimento').val();
				}
				
				if (_this.identificadorValor == "1") {
					_this.simulacao.attributes.valorLiquido = _this.$el.find('#frmValorLiquido').val(); 
					_this.simulacao.attributes.valorPrestacao=null;
				}else {
					_this.simulacao.attributes.valorPrestacao = _this.$el.find('#frmValorPrestacao').val(); 
					_this.simulacao.attributes.valorLiquido=null;
				}
				_this.simulacao.attributes.idConvenio = _this.idConvenio; 
				_this.simulacao.attributes.valorPrestacao = _this.$el.find('#frmValorPrestacao').val(); 
				_this.simulacao.attributes.prazo = _this.$el.find('#frmPrazo').val(); 
				_this.simulacao.attributes.taxaJuros = _this.$el.find('#frmTaxaJuros').val().replace(",","."); 
				_this.simulacao.attributes.dataContratacao = _this.$el.find('#frmDataContratacao').val(); 
			

				loadCCR.stop();				
				
				
				
				_this.simulacao.simular()
				.done(function sucesso(data) {
					loadCCR.stop();
					console.log(data);
					
					// trata retorno em caso de erro
					if (data.mensagemRetorno != "SUCESSO") {
						_this.$el.find("#panel_result_simulacao").hide();
						Retorno.trataRetorno({codigo: 1, tipo: "AVISO", mensagem: "", idMsg: data.mensagemRetorno}, 'simular');
						return;
					}
					
					_this.dadosSimulacao = data;
					
					_this.$el.find("#panel_result_simulacao").show();
					
					_this.$el.find('#panel_result_simulacao').html(_this.resultSimulacaoTemplate({result: data, simulacao : _this.simulacao.attributes}));

					_this.$el.find("#formActionSair").show();
					_this.$el.find("#btnSair").hide();
					
					if(_this.dadosSimulacao.simulacaoComSeguro.possuiResultado){
						_this.$el.find('.comSeguro').removeClass('hide');
					}else{
						_this.$el.find('.comSeguro').addClass('hide');
						Retorno.trataRetorno({codigo: 0, tipo: "AVISO", mensagem: "", idMsg: 'MA0094'}, 'simular');
					}
				})
				.error(function erro(jqXHR) {

					Retorno.trataRetorno({codigo: -1, tipo: "ERRO_EXCECAO", mensagem: "Ocorreu um erro ao simular o crédito consignado!"}, 'simular', jqXHR);
					loadCCR.stop();
				});
			}

		},
		
		preencheFaixaPadrao : function (evt){
			console.log("Simular Main - preencheFaixaPadrao");
			var prazo = evt.target.value;
			var faixaInserir=0;
			_this.taxaList.forEach(function(taxa){
				var faixaPadrao = "0";
				
				switch (_this.faixaJuroPadrao){
				case 'A': 
					faixaPadrao = taxa.pcMinimo
					break;
				case 'B': 
					faixaPadrao = taxa.pcMedio
					break;
				case 'C': 
					faixaPadrao = taxa.pcMaximo
					break;
				}
				if (prazo >= taxa.prazoMin && prazo <= taxa.prazoMax){
					faixaInserir=faixaPadrao;
				}
			});
			
			_this.$el.find("#frmTaxaJuros").val(formatadores.formatarTaxa(faixaInserir, 5));
			
		},
		
		controleDetalheSimulacao : function() {
			console.log("Simular Main - controleDetalheSimulacao");
			
			if (_this.mostrarDetalhe){
				_this.$el.find("#detalheSimulacao").hide();
				_this.mostrarDetalhe = false;
			}else{
				_this.$el.find("#detalheSimulacao").show();
				_this.mostrarDetalhe = true;
			}			
		},

		
		showDadosConsultaCET : function(e) {
			console.log("Simular Consignado - showDadosConsultaCET");
			
			if (e.currentTarget.id == "tdCETComSeguro"){
				_this.ctrlConsultaCET.setDadosCET(_this.dadosSimulacao.simulacaoComSeguro,'simular');				
			}else{
				_this.ctrlConsultaCET.setDadosCET(_this.dadosSimulacao.simulacaoSemSeguro,'simular');
			}

			_this.dadosCET = _this.ctrlConsultaCET.recuperaDados();
			_this.$el.find('#divModalCETBody').html(_this.detalheCetTemplate({dadosCET: _this.dadosCET}));
			_this.$el.find('#divModalCET').modal('show');
			$('#divModalCETBody').animate({ scrollTop: 0 }, 'slow');
		},
		
		closeDadosConsultaCET : function() {
			console.log("Simular Consignado - closeDadosConsultaCET");
			_this.$el.find('#divModalCET').modal('hide');
		},
		
		printDadosConsultaCET :  function(e) {
			console.log("Simular Consignado - printDadosConsultaCET");
			_this.ctrlConsultaCET.printDadosCET();
		},
		
	});

	return SimularMainControle;
	
});

