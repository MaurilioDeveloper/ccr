/**
 * @author 
 * 
 * JavaScript que controla as ações das paginas:
 *  
 * @version 1.0.0.0
 * 
 * 
 */
define(['enumeracoes/eMensagemCCR',
        'enumeracoes/eTipoServicos',
        'util/retorno',
        'modelo/cliente/cliente',
        'modelo/convenios/convenioColecao',
        'modelo/taxas/taxaJurosColecao',
        'modelo/contratacao/simulacao',
        'modelo/cliente/avaliacao',
        'text!visao/contratacao/contratar/simular/panelDivsContratacao.html',
        'text!visao/cliente/dadosClientePF.html',
        'text!visao/contratacao/contratar/simular/consultaDadosClientePF.html',
        'text!visao/contratacao/contratar/simular/panelDadosClienteContratacao.html',
        'text!visao/contratacao/contratar/simular/panelListaConvenioContratacao.html',
        'text!visao/contratacao/contratar/simular/panelListaPrazoContratacao.html',
        'text!visao/contratacao/contratar/simular/panelDadosDIContratacao.html',
        'text!visao/contratacao/contratar/simular/panelDadosEnderecoContratacao.html',
        'text!visao/contratacao/contratar/simular/panelDadosMCContratacao.html',
        'text!visao/contratacao/contratar/simular/panelListaRendaContratacao.html',
        'text!visao/contratacao/contratar/simular/panelResultadoSimulacaoContratacao.html',
        'controle/contratacao/detalheConsultaCETControle',
        'text!visao/contratacao/detalheConsultaCET.html',
        ], 
function(EMensagemCCR, ETipoServicos, Retorno, Cliente, ConvenioColecao, TaxaJurosColecao, Simulacao, Avaliacao, panelDivsContratacaoTPL , dadosClientePFTPL, consultaDadosClientePFTpl, panelDadosClienteTpl, panelListaConvenioTpl, panelListaPrazoTpl, panelDadosDITpl, panelDadosEnderecoTpl, panelDadosMCTpl, panelDadosRendaTpl, resultSimulacaoTpl, DetalheConsultaCETControle, detalheConsultaCetTpl){
	var _this = null;
	
	var ConsultaClienteControle = Backbone.View.extend({

		className: 'consultaClienteControle',
		
		/**
		 * Templates
		 */		
		panelDivsContratacaoTemplate 		: 	_.template(panelDivsContratacaoTPL),
		panelListaPrazoTemplate 			: 	_.template(panelListaPrazoTpl),
		panelListaConvenioTemplate 			: 	_.template(panelListaConvenioTpl),
		panelDadosClienteTemplate 			: 	_.template(panelDadosClienteTpl),
		consultaDadosClientePFTemplate 		: 	_.template(consultaDadosClientePFTpl),
		dadosClientePFTemplate 				: 	_.template(dadosClientePFTPL),
		panelDadosDITemplate 				: 	_.template(panelDadosDITpl),
		panelDadosEnderecoTemplate 			: 	_.template(panelDadosEnderecoTpl),		
		panelDadosMCTemplate 				: 	_.template(panelDadosMCTpl),		
		panelDadosRendaTemplate 			: 	_.template(panelDadosRendaTpl),
		resultSimulacaoTemplate 			:	_.template(resultSimulacaoTpl),
		detalheCetTemplate					: 	_.template(detalheConsultaCetTpl),
		
		/**
		 * Variaveis da classe
		 */
		validator   	: new Validators(),
		cnpjValidator 	: new CNPJValidator(),
		message     	: new Message(),
		comando     	: null,
		modelo			: null,
		collection  	: null,
		acao			: null,
		ctrlConsultaCET : new DetalheConsultaCETControle(),
		identificadorSeguro : 1,
		convenios		: null,
		convenio		: null,
		margemConsignado : null,
		/**
		 * Função que faz bind das ações e interações da pagina com as funções
		 * javascript
		 * 
		 */
		events : {
			'click a#btnConsultar' 		  		: 'consultarCliente',
			'click a#btnLimparForm'		  		: 'limparFormToda',
			'click a#btnAlterarDadosSICLI'		: 'alterarDadosSICLI',
			'click a#btnSalvaDadosSICLI'		: 'salvaDadosSICLI',
			'click a#btnSair'		      		: 'sair',
			'click #btnAvancar' 		  		: 'avancar',
			'click button#btnVoltar'	 		: 'voltar',
			
			'click #btnRecalcular'  	  		: 'showModalRecalcular',
			'click #btnRecalcularModal'   		: 'recalcularSimulacao',
			'click #btnVoltarRecalcularModal'	: 'fecharModalRecalcular',
			
			'click td.btn-toggle-popupcet'  	: 'showDadosConsultaCET',
			'click a.btn-close-popupcet'		: 'closeDadosConsultaCET',
			'click a.btn-print-popupcet'  		: 'printDadosConsultaCET',
			
			'click #radioValorLiquido'			: 'selecionarValor',
			'click #radioValorPrestacao'		: 'selecionarValor',
			'click #comSeguro'					: 'comSeguroConfirm',
			'click #semSeguro'					: 'semSeguroConfirm',
			'click #radioConvenioCont'		  	: 'consultarPrazos',
			'click a#controleDetalheSimulacao' 	: 'controleDetalheSimulacao',
			'click a.editarRenda'				: 'editarRenda',
			'click a.salvarRenda'				: 'salvarRenda',
			'click a#aposentadoPorInvalidez'	: 'aposentadoPorInvalidez',
			'click a#naoAposentadoPorInvalidez'	: 'naoAposentadoPorInvalidez',
			
			"change input[name='identificadorSeguro']"	:'redefinirIdentificadorSeguro',
			
			'click div#dadosClienteHeading' 		: 'collapse',
			'click div#dadosDIHeading' 				: 'collapse',
			'click div#enderecoContratacaoHeading' 	: 'collapse',
			'click div#meiosComunicacaoHeading' 	: 'collapse',
			'click div#rendaContratacaoHeading'		: 'collapse',
			'click div#conveniosContratacaoHeading'	: 'collapse',
			'click div#prazoContratacaoHeading'		: 'collapse',
			'click div#simulacaoContratacaoHeading'	: 'collapse',
		},
				
		
		/**
		 * Função padrão de incialização do template html
		 * 
		 */
		initialize : function() {
			console.log("consultaClienteControle - initialize");
						
			_this = this;			
						
			// configura validator
			_this.validator.addValidator(new DataValidator());			
			_this.validator.withErrorRender(new BootstrapErrorRender());
		},
				
		
		render : function(agencia) {
			console.log("consultaClienteControle - render");
						
			
			//Inclui o template inicial no el deste controle
			this.$el.html(_this.consultaDadosClientePFTemplate({agencia: agencia}));
			
			//Carrega as mascaras usadas.
			loadMaskEl(_this.$el);
			listenToDatepickerChange(_this.$el, _this.changed);
			_this.mostrarDetalhe = false;
			
			_this.rendaSalva = [];
			
			return _this;
		},
			
		consultarCliente : function (){
			console.log("ConsultaClienteControle - consultarCliente");
			loadCCR.start();
			
			if(_this.$el.find('#margemConsignado').val()=="0,00"){
				_this.$el.find('#margemConsignado').val('');
			}

			if (_this.validator.withForm('consultaClientePF').validate()){
				

				_this.margemConsignado = _this.$el.find('#margemConsignado').val();		
				_this.cpf = _this.$el.find('#numeroCPF').val();				
				console.log("-----------CPF:"+_this.$el.find('#numeroCPF').val());
				
				_this.cliente = new Cliente();
				_this.cliente.consultarDadosSicliPorCPF(_this.cpf)
				.done(function sucesso(data) {
					loadCCR.start();
					data = data.dados;
					_this.rendaSalva = [];
					if(data.nomePai == null){
						data.nomePai = "";
					}
					if(data.nomeMae == null){
						data.nomeMae = "";
					}
					
					if(data.meioComunicacao != null) {
						$.each(data.meioComunicacao, function( index, value ) {
							var telefoneComunicacao = value.codigoComunicacao; 
							if(telefoneComunicacao.charAt( 0 ) === '0' ) {
								telefoneComunicacao = telefoneComunicacao.slice( 1 );
							}
							data.meioComunicacao[index].codigoComunicacao = telefoneComunicacao;
						});
					}
					
					if(data.responseArqRef.status.codigo != 0) {
						var dataRet = {};
						dataRet.dados = data;
						Retorno.validarRetorno(dataRet);					
						loadCCR.stop();
						return;
					}

					// trata retorno em caso de erro
					if (data.mensagemRetorno == "MA004") {
						_this.$el.find("#panel_dados_cliente").hide();
						Retorno.trataRetorno({codigo: 1, tipo: "AVISO", mensagem: "", idMsg: 'MA0026'}, 'SICLI');
						loadCCR.stop();
						return;
					}
					
					//Add info no contexto para usa-lo depois
					_this.dataNascimento = data.dataNascimento.data;
					
					//Inclui a DIV com os dados do cliente no el desta controle
					_this.$el.find('#retornoConsultaDadosCliente').html(_this.panelDivsContratacaoTemplate({}));
					_this.$el.find('#divDadosClienteContratacao').html(_this.panelDadosClienteTemplate({cliente: data}));
					_this.$el.find('#divDadosDIContratacao').html(_this.panelDadosDITemplate({cliente: data}));
					_this.$el.find('#divDadosEnderecoContratacao').html(_this.panelDadosEnderecoTemplate({cliente: data}));
					_this.$el.find('#divDadosMCContratacao').html(_this.panelDadosMCTemplate({cliente: data}));
					
					//Documenstos de Identificação
					if (data.carteiraTrabalho != null) {
						_this.$el.find('#divCTPS').show();
					} else {
						_this.$el.find('#divCTPS').hide();
					}					
					if (data.cnh != null) {
						_this.$el.find('#divCNH').show();
					} else {
						_this.$el.find('#divCNH').hide();
					}					
					if (data.passaporte != null) {
						_this.$el.find('#divPASS').show();
					} else {
						_this.$el.find('#divPASS').hide();
					}					
					
					var rendaList = [];
					var rendaListTela = [];
					
					var dfds = [];
					for(var i = 0; i < data.renda.length; i++) {
						dfds.push($.Deferred());
					}
						
					
					
					data.renda.forEach(function(renda, i){
//						renda.cpfCnpjFontePagadora = "19388373000100";
//						renda.tipoFontePagadora = 1;
						if (_this.cnpjValidator.validate(renda.cpfCnpjFontePagadora) && renda.tipoRenda == "F" 
							&& (renda.tipoFontePagadora == "1" || renda.tipoFontePagadora == "2" || renda.tipoFontePagadora == "3")){

							var rendaTela = {};
							rendaTela.cpfCnpjFontePagadora = renda.cpfCnpjFontePagadora;
							rendaTela.dataAdimissao = renda.dataAdimissao;
							rendaTela.dataApuracao = renda.dataApuracao;
							rendaTela.codigoComprobatorioRenda = renda.codigoComprobatorioRenda;
							rendaTela.valorRendaBruta = renda.valorRendaBruta;
							rendaTela.anoReferencia = renda.anoReferencia;
							rendaTela.valorRendaLiquida = renda.valorRendaLiquida;
							rendaTela.mesReferencia = renda.mesReferencia;
							rendaTela.ocorrencia = renda.ocorrencia;
							
							loadCCR.start();
							_this.cliente.consultarCNPJ(renda.cpfCnpjFontePagadora)
							.done(function sucesso(data) {
								if(data != undefined && data.dados != undefined && data.dados.razaoSocial != undefined && data.dados.razaoSocial.razaoSocial != undefined)
									rendaTela.razaoSocial = data.dados.razaoSocial.razaoSocial;
								rendaListTela.push(rendaTela);
							}).always(function() {
								dfds[i].resolve();
							});

							rendaList.push(renda);
							
						} else {
							dfds[i].resolve();
						}
						
					});
					
					$.when.apply(undefined, dfds).done(function() {
						console.log(rendaListTela);
						if (rendaList.length != 0){
							// Consultar convenios
							loadCCR.start();
							_this.listarConveniosCliente(data.renda);
							_this.$el.find('#divDadosRendaContratacao').html(_this.panelDadosRendaTemplate({cliente: rendaListTela}));
						}else{
							_this.$el.find("#panel_dados_cliente").hide();
							Retorno.trataRetorno({codigo: 1, tipo: "AVISO", mensagem: "", idMsg: 'MA0097'}, 'convenio');
							loadCCR.stop();
							return;
						}
					})
					
						
				});
				
			}
			loadCCR.stop();
							
		},
		
		
		editarRenda: function(evt) {
			$('.data').mask('99/99/9999').off('focus.mask').off('blur.mask');
			$('.moeda').priceFormat({
				prefix : '',
				centsSeparator : ',',
				thousandsSeparator : '.'
			});
			_this.$el.find("#dataAdmissaoList"+evt.currentTarget.attributes[1].nodeValue).attr('disabled', false);
			_this.$el.find("#mesAnoReferenciaList"+evt.currentTarget.attributes[1].nodeValue).attr('disabled', false);
			_this.$el.find("#valorRendaBrutaList"+evt.currentTarget.attributes[1].nodeValue).attr('disabled', false);		
			_this.$el.find("#valorRendaLiquidaList"+evt.currentTarget.attributes[1].nodeValue).attr('disabled', false);
			_this.$el.find("#codigoComprobatorioRenda"+evt.currentTarget.attributes[1].nodeValue).attr('disabled', false);
			loadMask(_this.$el.find("#rendaContratacaoBody"));
		},
		
		salvarRenda : function(evt) {
			_this.$el.find("#dataAdmissaoList"+evt.currentTarget.attributes[1].nodeValue).attr('disabled', true);
			_this.$el.find("#mesAnoReferenciaList"+evt.currentTarget.attributes[1].nodeValue).attr('disabled', true);
			_this.$el.find("#valorRendaBrutaList"+evt.currentTarget.attributes[1].nodeValue).attr('disabled', true);		
			_this.$el.find("#valorRendaLiquidaList"+evt.currentTarget.attributes[1].nodeValue).attr('disabled', true);
			_this.$el.find("#codigoComprobatorioRenda"+evt.currentTarget.attributes[1].nodeValue).attr('disabled', true);
			
			$.each(_this.cliente.attributes.dados.renda, function( index, value ) {
				var dataAdmissao = _this.$el.find("#dataAdmissaoList"+evt.currentTarget.attributes[1].nodeValue).val();
				var formatDate = dataAdmissao.split('/')
				formatDate = formatDate[2] + "-" + formatDate[1] + "-" + formatDate[0]; 
				_this.cliente.attributes.dados.renda[index].dataAdimissao = formatDate;
				
				var mesAnoRef = _this.$el.find("#mesAnoReferenciaList"+evt.currentTarget.attributes[1].nodeValue).val().split('/')
				_this.cliente.attributes.dados.renda[index].anoReferencia = mesAnoRef[1];
				
				var valorRendaBruta = _this.$el.find("#valorRendaBrutaList"+evt.currentTarget.attributes[1].nodeValue).val();
				valorRendaBruta = valorRendaBruta.replace(/\D/g, '');
//				valorRendaBruta = valorRendaBruta.substring(0, valorRendaBruta.length-2) + "." + valorRendaBruta.substring(valorRendaBruta.length - 2, valorRendaBruta.length);
				
				var valorRendaLiquida = _this.$el.find("#valorRendaLiquidaList"+evt.currentTarget.attributes[1].nodeValue).val();
				valorRendaLiquida = valorRendaLiquida.replace(/\D/g, '');
//				valorRendaLiquida = valorRendaLiquida.substring(0, valorRendaLiquida.length-2) + "." + valorRendaLiquida.substring(valorRendaLiquida.length - 2, valorRendaLiquida.length);
				
				_this.cliente.attributes.dados.renda[index].valorRendaBruta = valorRendaBruta;
				_this.cliente.attributes.dados.renda[index].valorRendaLiquida =  valorRendaLiquida;
				_this.cliente.attributes.dados.renda[index].mesReferencia = mesAnoRef[0];
				_this.cliente.attributes.dados.renda[index].codigoComprobatorioRenda = _this.$el.find("#codigoComprobatorioRenda"+evt.currentTarget.attributes[1].nodeValue).val(); 
			});
			if(_this.rendaSalva == null) {
				_this.rendaSalva = [];
			}
			
			$.each(_this.cliente.attributes.dados.renda, function( index, value ) {
				if(value.ocorrencia === evt.currentTarget.attributes[1].nodeValue) {
					_this.rendaSalva[index] = _this.cliente.attributes.dados.renda[index];
				}
			});

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

		
		listarConveniosCliente : function (listaRenda){
			console.log("ConsultaClienteControle - listarConveniosCliente");
			
			_this.convenioColecao = new ConvenioColecao();
			_this.convenioColecao.listarConvenioPorFontePagadora(listaRenda)
			.done(function sucesso(data) {

				if (data.listaRetorno.length != 0){
					 
					_this.convenios = data.listaRetorno;
					_this.$el.find('#divListaConvenioContratacao').html(_this.panelListaConvenioTemplate({convenios: data.listaRetorno, margem: _this.$el.find('#margemConsignado').val()}));
					
					var convenioAtivo = [];
					var vigentes = [];
					

					for (var i = 0; i < data.listaRetorno.length; i++){
						if(data.listaRetorno[i].situacao.descricao == 'ATIVO'){
							convenioAtivo.push(data.listaRetorno[i]);
						}
						/*if(data.listaRetorno[i].vigente && data.listaRetorno[i].situacao.descricao == 'ATIVO') {
							 vigentes.push(true);
						}*/
					}
					
					if(convenioAtivo.length == 1 ){
						_this.$el.find('input[name="convenio"][value='+convenioAtivo[0].id +']').prop('checked', true);	
						_this.consultarPrazos(convenioAtivo[0].id);
						_this.convenio = convenioAtivo[0];
					}
										
					/*if(vigentes.length == 0){ 
						Retorno.trataRetorno({codigo: 1, tipo: "AVISO", mensagem: "Não possui convênio vigente ou ativo.", }, 'simular');		
					}*/


					
					loadCCR.stop();

				}else {					
					Retorno.trataRetorno({codigo: 1, tipo: "AVISO", mensagem: "Não possui convênio vinculado para fonte pagadora.", }, 'simular');
					loadCCR.stop();
					return;
				}
				
			})
			.error(function erro(jqXHR) {

				msgCCR.alerta("Funcionalidade Indísponivel!!!", function () {});
				loadCCR.stop();
			});
			
		},
		
		consultarPrazos : function (evt){
			console.log("ConsultaClienteControle - consultarPrazos");
			
			var idConvenioSel = null;
			
			if (evt.target != undefined){
				_this.idConvenio = evt.target.value;				
			}else{
				_this.idConvenio = evt;
			}
			console.log(_this.idConvenio);
			
			for(var i = 0; i < _this.convenios.length; i++) {
				if(_this.convenios[i].id == _this.idConvenio ){
					_this.convenio  = _this.convenios[i];
				}
				
			}
			
			_this.taxaJurosColecao = new TaxaJurosColecao();

			_this.taxaJurosColecao.codigoTaxa = _this.idConvenio;
			_this.taxaJurosColecao.canal = "1";

			_this.taxaJurosColecao.listarPrazoConvenio()
			.done(function sucesso(data) {
				loadCCR.stop();
				
				if (data.listaTaxaJuro.length != 0){
					
					_this.$el.find('#divListaPrazoContratacao').html(_this.panelListaPrazoTemplate({taxas: data.listaTaxaJuro}));
					
					_this.faixaJuroPadrao = data.faixaJuroPadrao;
					_this.taxaList = data.listaTaxaJuro;
					_this.taxaJuroSimulacao = data.taxaJuro;
					_this.prazoSimulacao = data.prazo;
					
					_this.simular();
					$("#btnSair").show();
					$("#btnRecalcular").show();
				}else{
					Retorno.trataRetorno({codigo: 1, tipo: "AVISO", mensagem: "", idMsg: 'MA006'}, 'manterJuros');
					return;
				}
			})
			.error(function erro(jqXHR) {

				msgCCR.alerta("Funcionalidade Indísponivel!!!", function () {});
				loadCCR.stop();
			});
			
		},
		
		simular : function (){
			console.log("Simular Main - simular");
			
				_this.simulacao = new Simulacao();

				_this.avaliacao = new Avaliacao();
//				_this.simulacao.attributes.cpfCliente = _this.cpf;
				_this.simulacao.attributes.dataNascimento = formatadores.formatarData(_this.dataNascimento);
				
				//pega a data atual
				var today = new Date();				
				 
				// Dados necessários para efetuar simulação
				_this.simulacao.attributes.idConvenio = _this.idConvenio;
				_this.simulacao.attributes.prazo 		  = _this.$el.find('#prazoRecalculo').val() === "" ? _this.prazoSimulacao : _this.$el.find('#prazoRecalculo').val(); 
				_this.simulacao.attributes.taxaJuros 	  = _this.$el.find('#jurosRecalculo').val() === "" ? _this.taxaJuroSimulacao : _this.$el.find('#jurosRecalculo').val().replace(',','.'); 
				//Dado informado na tela (Verificar se será enviado pelo SISRH)
				_this.simulacao.attributes.valorPrestacao = _this.$el.find('#prestacaoRecalculo').val() === "" ? _this.$el.find('#margemConsignado').val() : _this.$el.find('#prestacaoRecalculo').val();
				_this.simulacao.attributes.valorLiquido   = _this.$el.find("#valorLiquidoRecalculo").val() === "" ? _this.simulacao.attributes.valorLiquido : _this.$el.find("#valorLiquidoRecalculo").val();
				_this.simulacao.attributes.valorContrato  = _this.$el.find("#valorContratoRecalculo").val() === "" ? _this.simulacao.attributes.valorContrato : _this.$el.find("#valorContratoRecalculo").val();
				_this.simulacao.attributes.dataContratacao = formatadores.formatarData(today);
				_this.nomeCliente  = _this.cliente.attributes.nomeCliente.nome;
					
				
				_this.simulacao.simular()
				.done(function sucesso(data) {
					console.log(data);
					$("#btnAvancar").hide();
					// trata retorno em caso de erro
					if (data.mensagemRetorno != 'SUCESSO') {
						loadCCR.stop();
						Retorno.trataRetorno({codigo: 1, tipo: "AVISO", mensagem: "", idMsg: data.mensagemRetorno}, 'simular');
						return;
					}
					_this.$el.find('#divSimulacaoContratacao').html(_this.resultSimulacaoTemplate({result: data, simulacao : _this.simulacao.attributes}));
					_this.simulacao.attributes.tipoSeguro = _this.$el.find('input[name="identificadorSeguro"]:checked').val();

					
					if(data.simulacaoComSeguro.possuiResultado){
						_this.$el.find('.comSeguro').removeClass('hide');
					}else{
						_this.$el.find('.comSeguro').addClass('hide');
						$("#semSeguro").prop("checked", true)
//						$("#btnAvancar").show();
						Retorno.trataRetorno({codigo: 0, tipo: "AVISO", mensagem: "", idMsg: 'MA0094'}, 'simular');
					}
										
					loadCCR.stop();
				})
				.error(function erro(jqXHR) {
					Retorno.trataRetorno({codigo: 1, tipo: "AVISO", mensagem: "Taxa de juro igual a zero!"}, 'manterJuros');
					loadCCR.stop();
				});
		},
		
		alterarDadosSICLI : function() {
			console.log("consultaClienteControle - alterarDadosSICLI");
			
			//loadCCR.start();		
			
			//Altera botao da ação
			_this.$el.find('#btnAlterarDadosSICLI').hide(); // Botao Alterar Dados SICLI
			_this.$el.find('#btnSalvaDadosSICLI').show();// Botao Salvar Dados SICLI
			
			//Campos Editaveis do SICLI:
			
			// DIV Documento de identidade
			_this.$el.find('#divDI_identidade_orgaoEmissor').hide();
			_this.$el.find('#di_identidade_orgaoEmissor').show();			
			_this.$el.find('#divDI_identidade_uf').hide();
			_this.$el.find('#di_identidade_uf').show();			
			_this.$el.find('#divDI_identidade_numero').hide();
			_this.$el.find('#di_identidade_numero').show();			
			_this.$el.find('#divDI_identidade_dataEmissao').hide();
			_this.$el.find('#di_identidade_dataEmissao').show();

			
			//DIV Endereço
			_this.$el.find("#div_enderecoNacional_uf").hide();
			_this.$el.find("#enderecoNacional_uf").show();
			_this.$el.find("#div_enderecoNacional_municipio").hide();
			_this.$el.find("#enderecoNacional_municipio").show();
			_this.$el.find("#div_enderecoNacional_logradouro").hide();
			_this.$el.find("#enderecoNacional_logradouro").show();
			_this.$el.find("#div_enderecoNacional_complemento").hide();
			_this.$el.find("#enderecoNacional_complemento").show();
			_this.$el.find("#div_enderecoNacional_bairro").hide();
			_this.$el.find("#enderecoNacional_bairro").show();
			_this.$el.find("#div_enderecoNacional_numero").hide();
			_this.$el.find("#enderecoNacional_numero").show();
			_this.$el.find("#div_enderecoNacional_cep").hide();
			_this.$el.find("#enderecoNacional_cep").show();
			
			//DIV CTPS
			_this.$el.find('#divDI_CTPS_numero').hide();
			_this.$el.find('#di_CTPS_numero').show();
			_this.$el.find('#divDI_CTPS_uf').hide();
			_this.$el.find('#di_CTPS_uf').show();
			_this.$el.find('#divDI_CTPS_dataEmissao').hide();
			_this.$el.find('#di_CTPS_dataEmissao').show();
			_this.$el.find('#divDI_CTPS_orgaoEmissor').hide();
			_this.$el.find('#di_CTPS_orgaoEmissor').show();
			
			// DIV Meio de Comunicação 
			_this.$el.find(".div_meioComunicacao_telefone").hide();
			_this.$el.find(".meioComunicacao_telefone").show();
			
			$(".telefone").mask("9999-9999");
			
			// DIV Passaporte
			_this.$el.find('#divDI_PASS_numero').hide();
			_this.$el.find('#di_PASS_numero').show();			
			_this.$el.find('#divDI_PASS_paisEmissao').hide();
			_this.$el.find('#di_PASS_paisEmissao').show();			
			_this.$el.find('#divDI_PASS_orgaoEmissor').hide();
			_this.$el.find('#di_PASS_orgaoEmissor').show();			
			_this.$el.find('#divDI_PASS_dataEmissao').hide();
			_this.$el.find('#di_PASS_dataEmissao').show();			
			_this.$el.find('#divDI_PASS_uf').hide();
			_this.$el.find('#di_PASS_uf').show();
			_this.$el.find('#divDI_PASS_validade').hide();
			_this.$el.find('#di_PASS_validade').show();
			// DIV CNH
			_this.$el.find('#divDI_CNH_numero').hide();
			_this.$el.find('#di_CNH_numero').show();			
			_this.$el.find('#divDI_CNH_1Habilitacao').hide();
			_this.$el.find('#di_CNH_1Habilitacao').show();			
			_this.$el.find('#divDI_CNH_orgaoEmissor').hide();
			_this.$el.find('#di_CNH_orgaoEmissor').show();			
			_this.$el.find('#divDI_CNH_dataEmissao').hide();
			_this.$el.find('#di_CNH_dataEmissao').show();			
			_this.$el.find('#divDI_CNH_validade').hide();
			_this.$el.find('#di_CNH_validade').show();
			_this.$el.find('#di_identidade_orgaoEmissor').focus();
			
			//DIV Renda
			_this.$el.find(".div_codigoComprobatorioRenda").hide();
			_this.$el.find(".codigoComprobatorioRenda").show();
			_this.$el.find(".div_dataAdmissao").hide();
			_this.$el.find(".dataAdmissao").show();
			_this.$el.find(".div_mesAnoReferencia").hide();
			_this.$el.find(".mesAnoReferencia").show();
			_this.$el.find(".div_valorRendaBruta").hide();
			_this.$el.find(".valorRendaBruta").show();		
			_this.$el.find(".div_valorRendaLiquida").hide();
			_this.$el.find(".valorRendaLiquida").show();
			
			// Mostra coluna de ações da Lista
			_this.$el.find(".acoes").show();
			//loadCCR.stop();
			loadMaskEl(_this.$el.find("#dadosDIBody"));
			loadMaskEl(_this.$el.find("#enderecoContratacaoBody"));
			loadMaskEl(_this.$el.find("#meiosComunicacaoBody"));
			return _this;
		},
		
		

		dadosAlteradosSICLI : function() {
			console.log("consultaClienteControle - voltarDadosSICLI");
			
			
			//Altera botao da ação
			_this.$el.find('#btnAlterarDadosSICLI').show(); // Botao Alterar Dados SICLI
			_this.$el.find('#btnSalvaDadosSICLI').hide();// Botao Salvar Dados SICLI
			
			//Campos Editaveis do SICLI:
			
			// DIV Documento de identidade
			_this.$el.find('#divDI_identidade_orgaoEmissor').show();
			_this.$el.find('#di_identidade_orgaoEmissor').hide();			
			_this.$el.find('#divDI_identidade_uf').show();
			_this.$el.find('#di_identidade_uf').hide();			
			_this.$el.find('#divDI_identidade_numero').show();
			_this.$el.find('#di_identidade_numero').hide();			
			_this.$el.find('#divDI_identidade_dataEmissao').show();
			_this.$el.find('#di_identidade_dataEmissao').hide();			
			_this.$el.find('#divDI_identidade_dataValidade').show();
			_this.$el.find('#di_identidade_dataValidade').hide();
			//DIV CTPS
			_this.$el.find('#divDI_CTPS_numero').show();
			_this.$el.find('#di_CTPS_numero').hide();
			_this.$el.find('#divDI_CTPS_uf').show();
			_this.$el.find('#di_CTPS_uf').hide();
			_this.$el.find('#divDI_CTPS_dataEmissao').show();
			_this.$el.find('#di_CTPS_dataEmissao').hide();
			_this.$el.find('#divDI_CTPS_orgaoEmissor').show();
			_this.$el.find('#di_CTPS_orgaoEmissor').hide();
			// DIV Passaporte
			_this.$el.find('#divDI_PASS_numero').show();
			_this.$el.find('#di_PASS_numero').hide();			
			_this.$el.find('#divDI_PASS_paisEmissao').show();
			_this.$el.find('#di_PASS_paisEmissao').hide();			
			_this.$el.find('#divDI_PASS_orgaoEmissor').show();
			_this.$el.find('#di_PASS_orgaoEmissor').hide();			
			_this.$el.find('#divDI_PASS_dataEmissao').show();
			_this.$el.find('#di_PASS_dataEmissao').hide();			
			_this.$el.find('#divDI_PASS_uf').show();
			_this.$el.find('#di_PASS_uf').hide();
			_this.$el.find('#divDI_PASS_validade').show();
			_this.$el.find('#di_PASS_validade').hide();
			// DIV CNH
			_this.$el.find('#divDI_CNH_numero').show();
			_this.$el.find('#di_CNH_numero').hide();			
			_this.$el.find('#divDI_CNH_1Habilitacao').show();
			_this.$el.find('#di_CNH_1Habilitacao').hide();			
			_this.$el.find('#divDI_CNH_orgaoEmissor').show();
			_this.$el.find('#di_CNH_orgaoEmissor').hide();			
			_this.$el.find('#divDI_CNH_dataEmissao').show();
			_this.$el.find('#di_CNH_dataEmissao').hide();			
			_this.$el.find('#divDI_CNH_validade').show();
			_this.$el.find('#di_CNH_validade').hide();
			//DIV Endereço
			_this.$el.find("#div_enderecoNacional_uf").show();
			_this.$el.find("#enderecoNacional_uf").hide();
			_this.$el.find("#div_enderecoNacional_municipio").show();
			_this.$el.find("#enderecoNacional_municipio").hide();
			_this.$el.find("#div_enderecoNacional_logradouro").show();
			_this.$el.find("#enderecoNacional_logradouro").hide();
			_this.$el.find("#div_enderecoNacional_complemento").show();
			_this.$el.find("#enderecoNacional_complemento").hide();
			_this.$el.find("#div_enderecoNacional_bairro").show();
			_this.$el.find("#enderecoNacional_bairro").hide();
			_this.$el.find("#div_enderecoNacional_numero").show();
			_this.$el.find("#enderecoNacional_numero").hide();
			_this.$el.find("#div_enderecoNacional_cep").show();
			_this.$el.find("#enderecoNacional_cep").hide();
			
			// Mostra coluna de ações da Lista
			_this.$el.find(".acoes").hide();
			
			return _this;
		},
		
		salvaDadosSICLI : function() {
			console.log("consultaClienteControle - salvaDadosSICLI");
							
			_this.client = new Cliente();
			
			if(_this.cliente.attributes != undefined) {
				_this.cliente = _this.cliente.attributes.dados;
			}
			
			_this.cliente.identidade.descricaoOrgaoEmissor = $('#di_identidade_orgaoEmissor').val(); 
			_this.cliente.identidade.documento.uf = $('#di_identidade_uf').val();
			_this.cliente.identidade.documento.numero = $('#di_identidade_numero').val();
			_this.cliente.identidade.documento.dataEmissao = $('#inputdi_identidade_dataEmissao').val();
		
			if(_this.cliente.carteiraTrabalho != null) {
				_this.cliente.carteiraTrabalho.orgaoEmissor = $('#di_CTPS_orgaoEmissor').val(); 
				_this.cliente.carteiraTrabalho.uf = $('#di_CTPS_uf').val();
				_this.cliente.carteiraTrabalho.subTipoDocumento = $('#di_CTPS_numero').val(); 
				_this.cliente.carteiraTrabalho.dataEmissao = $('#CTPS_dataEmissao').val();
				_this.cliente.carteiraTrabalho.dataValidade = $('#CTPS_dataValidade').val();
			}

			if(_this.cliente.passaporte != null) {
				_this.cliente.passaporte.subTipoDocumento = $('#di_PASS_numero').val();
				_this.cliente.passaporte.paisEmissao = $('#di_PASS_paisEmissao').val();  
				_this.cliente.passaporte.descricaoOrgaoEmissor = $('#di_PASS_orgaoEmissor').val(); 
				_this.cliente.passaporte.dataEmissao = $('#PASS_dataEmissao').val(); 
				_this.cliente.passaporte.uf = $('#di_PASS_uf').val(); 
				_this.cliente.passaporte.validade = $('#PASS_validade').val(); 
			}
			
			if(_this.cliente.cnh != null) {
				_this.cliente.cnh.numero = $('#di_CNH_numero').val();
				_this.cliente.cnh.descicaoTipoOrgaoEmissor = $('#di_CNH_orgaoEmissor').val(); 
				_this.cliente.cnh.dataEmissao = $('#CNH_dataEmissao').val(); 
				_this.cliente.cnh.dataValidade = $('#di_CNH_validade').val();
				_this.cliente.cnh.dataPrimeiraHabilitacao = $('#1Habilitacao').val();
				_this.cliente.cnh.dataValidade = $('#CNH_validade').val();
			}
			
			_this.cliente.enderecoNacional[0].uf = $('#enderecoNacional_uf').val();
			_this.cliente.enderecoNacional[0].nomeMunicipio = $('#enderecoNacional_municipio').val();
			_this.cliente.enderecoNacional[0].logradouro = $('#enderecoNacional_logradouro').val();
			_this.cliente.enderecoNacional[0].complemento = $('#enderecoNacional_complemento').val();
			_this.cliente.enderecoNacional[0].bairro = $('#enderecoNacional_bairro').val();
			_this.cliente.enderecoNacional[0].numero = $.trim($('#enderecoNacional_numero').val());
			_this.cliente.enderecoNacional[0].cep = $('#enderecoNacional_cep').val();
			
			if(_this.cliente.meioComunicacao != null) {
				$.each(_this.cliente.meioComunicacao, function( index, value ) {
					var telefoneComunicacao = $("#meioComunicacao_telefone"+value.ocorrencia).val(); 
					if(telefoneComunicacao.charAt( 0 ) === '0' ) {
						telefoneComunicacao = telefoneComunicacao.slice( 1 );
					}
					_this.cliente.meioComunicacao[index].codigoComunicacao = telefoneComunicacao;
				});
			}

			
			delete _this.cliente.responseArqRef;
			
			_this.dadosAlteradosSICLI();
			
			_this.$el.find('#divDadosClienteContratacao').html(_this.panelDadosClienteTemplate({cliente: _this.cliente}));
			_this.$el.find('#divDadosDIContratacao').html(_this.panelDadosDITemplate({cliente: _this.cliente}));
			_this.$el.find('#divDadosEnderecoContratacao').html(_this.panelDadosEnderecoTemplate({cliente: _this.cliente}));
			_this.$el.find('#divDadosMCContratacao').html(_this.panelDadosMCTemplate({cliente: _this.cliente}));
			
			if (_this.cliente.carteiraTrabalho == null) {
				_this.$el.find('#divCTPS').hide();
			}					
			if (_this.cliente.cnh == null) {
				_this.$el.find('#divCNH').hide();
			}					
			if (_this.cliente.passaporte == null) {
				_this.$el.find('#divPASS').hide();
			}					
			
			if(_this.cliente.identidade != null) {
				 var array = _this.cliente.identidade.documento.dataEmissao.split('/');
				 console.log(array);
				 _this.cliente.identidade.documento.dataEmissao = array[2] + "-" + array[1] + "-" + array[0];
			}
			
			if(_this.cliente.enderecoNacional != null) {
				for(var i=0; i < _this.cliente.enderecoNacional.length; i++) {
					_this.cliente.enderecoNacional[i].cep = _this.cliente.enderecoNacional[i].cep.replace('-', '');
				} 
			} 
			
			if(_this.rendaSalva != null) {
				if(_this.rendaSalva.length == 0) {
					_this.rendaSalva = null;
				}
			}
			_this.cliente.renda = _this.rendaSalva;
			
			// IMPLEMENTAR A CHAMADA DO SERVIÇO DE ALTERAR INFORMAÇÕES DO SICLI
			_this.client.alterarDadosPF(_this.cliente).done(function sucesso(data) {
				if(data.tipoRetorno == "ERRO_EXCECAO"){
					rolarPaginaParaCima();
					Retorno.trataRetorno({codigo: 0, tipo: "AVISO", mensagem: data.mensagemRetorno}, 'contrato');
					loadCCR.stop();
				}else{
					rolarPaginaParaCima();
					Retorno.trataRetorno({codigo: 0, tipo: "SUCESSO", mensagem: "Dados do SICLI alterado com sucesso."}, 'contrato');
					loadCCR.stop();
				}
				
				_this.consultarCliente();
//				_this.dadosAlteradosSICLI();
			}).error(function erro(jqXHR) {
				msgCCR.alerta("Funcionalidade Indísponivel!!!", function () {});
				loadCCR.stop();
			}); 
			
			return _this;
		},
		
		
		preencheFaixaPadrao : function (evt){
			console.log("Simular Main - preencheFaixaPadrao");
			var prazo = evt.target.value;
			
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
					_this.$el.find("#frmTaxaJuros").val(formatadores.formatarTaxa(faixaPadrao, 5));
				}else{
					_this.$el.find("#frmTaxaJuros").val('');
				}
			});
			
		},
		
		comSeguroConfirm : function() {
			console.log("ConsultaClienteControle - comSeguroConfirm");
			$("#divModalConfirmSeguro").modal('show');

		},
		
		semSeguroConfirm : function () {
			_this.$el.find("#aposentadoPorInvalidez").val(false);
			$("#btnAvancar").show();
		},
		
		aposentadoPorInvalidez : function () {
			_this.$el.find("#divModalConfirmSeguro").modal('hide');
			_this.$el.find("#aposentadoPorInvalidez").val(true);
			$("#btnAvancar").show();
		},
		
		naoAposentadoPorInvalidez : function () {	
			_this.$el.find("#divModalConfirmSeguro").modal('hide');
			_this.$el.find("#aposentadoPorInvalidez").val(false);
			$("#btnAvancar").show();
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
		
		
		/**
		 * Função utilizada para avancar na etapas.
		 * Acao do botao btnAvancar
		 * 
		 * Parametro passado automaticamente
		 * 
		 * @param evento
		 * 
		 */
		avancar : function(evento) {
			console.log("consultaClienteControle - avancar");
			
//			if($("#comSeguro").prop('checked') || $("#semSeguro").prop('checked')) {
			
				//pega o numero da etapa pelo active e o numero pelo data
				var etapaAtual = $('ul.wizard-steps > li.active').data('step');
				//avanca a etapa
				console.log("consultaClienteControle - carrega etapa: " + etapaAtual);			
				var wizardConsignadoControl = new wizardConsignadoControle();			
				wizardConsignadoControl.carregarEtapa(etapaAtual+1);
				
			
//			} else {
//				Retorno.trataRetorno({codigo: 1, tipo: "AVISO", mensagem: "", idMsg: 'MA0080'}, 'simular');
//				return;
//			}
			
		},
		
		/**
		 * Função utilizada para voltar na etapas.
		 * Acao do botao btnVoltar
		 * 
		 * Parametro passado automaticamente
		 * 
		 * @param evento
		 * 
		 */
		voltar : function(evento) {
			console.log("consultaClienteControle - voltar");			
			//pega o numero da etapa pelo active e o numero pelo data
			var etapaAtual = $('ul.wizard-steps > li.active').data('step');			
			//volta a etapa
			console.log("consultaClienteControle - carrega etapa: " + etapaAtual);			
			var wizardConsignadoControl = new wizardConsignadoControle();			
			wizardConsignadoControl.carregarEtapa(etapaAtual-1);			
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
			console.log("saindo do CCR...");
			window.location = 'index.html';
		},					
		
		recuperarDados: function(){
			console.log("consultaClienteControle - recuperarDados");
			_this.simulacao.attributes.tipoSeguro = _this.$el.find('input[name="identificadorSeguro"]:checked').val();
			_this.simulacao.attributes.convenio = _this.convenio;
			return _this.simulacao.attributes;
		},
		
		showModalRecalcular: function(){
			console.log("consultaClienteControle - showModalRecalcular");
			$('#divModalRecalcular').modal('show');
			$("#valorLiquidoRecalculo").val('');
			$("#prestacaoRecalculo").val('');
			$("#valorContratoRecalculo").val('');
			$("#prazoRecalculo").val('');
			$("#jurosRecalculo").val('');
			loadMaskEl($('#divModalRecalcular'));
		},
		
		fecharModalRecalcular: function(){
			console.log("consultaClienteControle - fecharModalRecalcular");
			$('#divModalRecalcular').modal('hide');
		},
		
		recalcularSimulacao: function(){
			
			if(_this.$el.find("#prazoRecalculo").val() > _this.convenio.canais[0].przMaximoContratacao) {
				
				// MA0083
				$("#mensagens-error").html('Prazo maior que o permitido');
				$("#mensagens-error").fadeTo(6000, 500).slideUp(500, function(){
				    $("#mensagens-error").slideUp(500);
				});
				
				$("#div_prazo").addClass("error");
			} else if((parseFloat(_this.$el.find("#jurosRecalculo").val().replace(',', '.')) > parseFloat(_this.taxaList[0].pcMaximo) 
					|| parseFloat(_this.$el.find("#jurosRecalculo").val().replace(',', '.')) < parseFloat(_this.taxaList[0].pcMinimo)) 
					&& _this.$el.find("#jurosRecalculo").val() != "") {
				
				// MA0084
				$("#mensagens-error").html('Taxa de juros fora do permitido');
				$("#mensagens-error").fadeTo(6000, 500).slideUp(500, function(){
				    $("#mensagens-error").slideUp(500);
				});
				
				$("#div_taxaJuros").addClass("error");
			} else if(parseFloat(_this.$el.find("#prestacaoRecalculo").val().replace(/\D/g, '')) > parseFloat($("#margemConsignado").val().replace(/\D/g, ''))) {
				
				// 
				$("#mensagens-error").html('Valor da prestação fora da margem');
				$("#mensagens-error").fadeTo(6000, 500).slideUp(500, function(){
				    $("#mensagens-error").slideUp(500);
				});
				
				$("#div_valor_prestacao").addClass("error"); 
			} else {
				
				$("#div_taxaJuros").removeClass("error");
				$("#div_prazo").removeClass("error");
				$("#div_valor_prestacao").removeClass("error"); 
				
				_this.simulacao = new Simulacao();
				_this.simulacao.attributes.valorLiquido = _this.$el.find("#valorLiquidoRecalculo").val();
				formatadores.formatarMoedaParaFloat(mascararMoeda(_this.$el.find("#valorLiquidoRecalculo"), 2));
				_this.simulacao.attributes.valorPrestacao = _this.$el.find("#prestacaoRecalculo").val();
				_this.simulacao.attributes.prazo = _this.$el.find("#prazoRecalculo").val();
				_this.simulacao.attributes.taxaJuros = _this.$el.find("#jurosRecalculo").val();
				
				// Ajustar
				if (_this.simulacao.attributes.valorLiquido === "" &&
					_this.simulacao.attributes.valorPrestacao === "" &&
					_this.simulacao.attributes.prazo === "" && 
					_this.simulacao.attributes.taxaJuros === ""){
					
					// MA0081
					$("#mensagens-error").fadeTo(4000, 500).slideUp(500, function(){
					    $("#mensagens-error").slideUp(500);
					});
				} else {
					$('#divModalRecalcular').modal('hide');
					_this.simular();
					$("#btnAvancar").hide();
					loadCCR.stop();
				}
			}
			
		},
		
		showDadosConsultaCET : function(e) {
			console.log("Simular Consignado - showDadosConsultaCET");
			
			if (e.currentTarget.id == "tdCETComSeguro"){
				_this.ctrlConsultaCET.setDadosCET(_this.simulacao.attributes.simulacaoComSeguro,'simular');				
			}else{
				_this.ctrlConsultaCET.setDadosCET(_this.simulacao.attributes.simulacaoSemSeguro,'simular');				
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
		
		redefinirIdentificadorSeguro: function(){
			_this.identificadorSeguro = _this.$el.find('input[name="identificadorSeguro"]:checked').val(); 
		},
		
		collapse : function(evt){
			collapse(evt);
		},
		
	
	});

	return ConsultaClienteControle;
	
});