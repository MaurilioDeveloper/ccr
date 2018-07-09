/**
 * @author 
 * 
 * JavaScript que controla as ações das paginas:
 *  wizardConsignado.html
 *  
 * @version 1.0.0.0
 * 
 * 
 */
define(['enumeracoes/eMensagemCCR',
        'enumeracoes/eTipoServicos',
        'util/retorno',
        'util/modelo/unidade',
        'controle/cliente/consultaClienteControle',
        'controle/cliente/consultaClienteAvaliar',
        'controle/contratacao/contratarConsignadoContratar',
        'controle/contratacao/contratarConsignadoAutorizar',
        'controle/contratacao/contratarConsignadoAvaliar',
        'controle/contratacao/contratarConsignadoAutorizar',
        'text!visao/wizardConsignado.html',
        'text!visao/contratacao/contratar/simular/consultaDadosClientePF.html',
        'modelo/contratacao/contrato',
        ], 
function(EMensagemCCR, ETipoServicos, Retorno, Unidade, consultaClienteControl, consultaClienteAvaliarControl, contratarConsignadoContratarControl, contratarConsignadoAutorizarControl, contratarConsignadoAvaliarControl, consignadoAutorizarControl, wizardConsignadoTpl, consultaDadosClientePFTpl, Contrato){

	var _this = null;
	var WizardConsignadoControle = Backbone.View.extend({

		className: 'WizardConsignadoControle',
		
		/**
		 * Templates
		 */
		wizardConsignadoTemplate : _.template(wizardConsignadoTpl),
		consultaDadosClientePFTemplate  : _.template(consultaDadosClientePFTpl),
		
		/**
		 * Variaveis da classe
		 */
		validator   : new Validators(),
		message     : new Message(),
		unidade		: new Unidade(),
		comando     : null,
		modelo		: null,
		collection  : null,
		acao		: null,
		step1		: new consultaClienteControl(),
		step2		: new contratarConsignadoAvaliarControl(),
		step3		: new contratarConsignadoContratarControl(),
		step4		: new contratarConsignadoAutorizarControl(),	
		dadosAutorizacao : null,
		contrato	: null,
		cliente		: null,
		newContracao : true,
		avaliar : false,
		avaliarOperacao : false,
		
		/**
		 * Função que faz bind das ações e interações da pagina com as funções
		 * javascript
		 * 
		 */
		events : {
			'click a#btnLimparForm'		: 'limparForm',
			'click a#bntSair'		    : 'sair',
			'click #btnAvancar' 		: 'avancar',
			'click a#btnCancelar'		: 'cancelar',
			'click button#btnVoltar'	: 'voltar',
			'click .complete'			: 'stepClickGoTo',
		},
				
		finalizar : function() {
			//Etapa vigente
			this.$el.find('#aDadosComplementares').addClass('complete');
			$wizard.wizard('completeProgressBar');
		},
		
		
		/**
		 * Função padrão de incialização do template html
		 * 
		 */
		initialize : function() {
			console.log("Wizard Consignado Controle - initialize");
			
			_this = this;			
			
			// recuperar agencia concessora
			_this.unidade.consultaUnidadePorNumeroUnidade(usuario.attributes.empregado.numeroUnidade);
			_this.agenciaConcessora = _this.unidade.attributes;
			
			// configura validator
			_this.validator.addValidator(new DataValidator());			
		},
			
		/**
		 * Função padrão para reenderizar os dados html na tela		
		 * Retorna o proprio objeto criado
		 * @returns {anonymous}
		 */
		render : function(e, etapa) {
			console.log("Wizard Consignado Controle - render");	

			//Inclui o template inicial no el deste controle
			this.$el.html(_this.wizardConsignadoTemplate({}));	

			//seta o wizard
			$wizard = this.$el.find('#fuelux-wizard');			 
						
			//funcoes para tratamento das acoes do wizard
			$wizard.wizard().on('finished', function(e) {
				$('.wizard-steps').find('li:last').removeClass('active');
				$('.wizard-steps').find('li:last').addClass('complete');			
				$wizard.wizard('completeProgressBar');
			}).on('changed', function(e) {
				$('#btnAvancar').show();
			});
			
			_this.cliente = null;
			
			if(etapa == 'avaliar'){
				var dados = $(e.target).data();
				
				if(dados.contrato == undefined) {
					dados = $(e.target).parent().data();
				}
				
				_this.contratoModelo = new Contrato();
				_this.contratoModelo.consultarPorId(dados.contrato).done(function sucesso(data) {
					_this.contratoBusca = {};
					_this.contratoBusca.situacao = data.situacao;
					_this.contratoModelo.validarPerfil()
					.done(function sucesso(dataPerfil) {
						_this.perfil = dataPerfil;
					})
					.error(function erro(jqXHR) {
						Retorno.trataRetorno({codigo: -1, tipo: "ERRO_EXCECAO", mensagem: "Ocorreu um erro ao autorizar o contrato!"}, 'autorizar', jqXHR);
						loadCCR.stop();
					});				
					
					if(_this.contratoBusca.situacao.id == 2 || _this.contratoBusca.situacao.id == 16) {
						
						_this.autorizar=true;
						
						//Carrega a quarta etapa do Wizard	
						$wizard.wizard('next');
						$wizard.wizard('updateProgressBar');
						$wizard.wizard('next');
						$wizard.wizard('updateProgressBar');
						$wizard.wizard('next');
						$wizard.wizard('updateProgressBar');
						_this.carregarEtapa(4,0,e, false);
					} else if (_this.contratoBusca.situacao.id == 13) {
						_this.avaliar=true;
						_this.carregarEtapa(2,0,e,false);
					} else if(_this.contratoBusca.situacao.id == 14) {
						_this.avaliarOperacao=true;
						_this.operacao = true;
						_this.carregarEtapa(2,0,e,false);
					}
				})
				.error(function erro(jqXHR) {
					if(jqXHR && jqXHR.responseText && jqXHR.responseText != ""){
						Retorno.exibeErroDetalhe(jqXHR.responseText);
					}else{
						Retorno.trataRetorno({codigo: -1, tipo: "ERRO_EXCECAO", mensagem: "Ocorreu um erro ao buscar dados da empresa!"});
					}
				});

				
//				this.carregarEtapa(2,0,e, false);
			}
			else if(etapa == 'autorizar'){
				
				_this.autorizar=true;
				
				//Carrega a quarta etapa do Wizard	
				$wizard.wizard('next');
				$wizard.wizard('updateProgressBar');
				$wizard.wizard('next');
				$wizard.wizard('updateProgressBar');
				$wizard.wizard('next');
				$wizard.wizard('updateProgressBar');
				_this.contrato = new Contrato();
				_this.contrato.validarPerfil()
				.done(function sucesso(data) {
					_this.perfil = data;
				})
				.error(function erro(jqXHR) {
					Retorno.trataRetorno({codigo: -1, tipo: "ERRO_EXCECAO", mensagem: "Ocorreu um erro ao autorizar o contrato!"}, 'autorizar', jqXHR);
					loadCCR.stop();
				});				
				this.carregarEtapa(4,0,e, false);

			} else if (etapa == 'avaliarOperacao') {
				_this.avaliarOperacao = true;
				_this.operacao = true;
				//Carrega a quarta etapa do Wizard
				_this.contrato = new Contrato();
				_this.contrato.validarPerfil()
				.done(function sucesso(data) {
					_this.perfil = data;
				})
				.error(function erro(jqXHR) {
					Retorno.trataRetorno({codigo: -1, tipo: "ERRO_EXCECAO", mensagem: "Ocorreu um erro ao autorizar o contrato!"}, 'autorizar', jqXHR);
					loadCCR.stop();
				});
				this.carregarEtapa(2,0,e, false);
			} else{
				//Carrega a primeira etapa do Wizard		
				this.carregarEtapa(1,undefined,undefined,false);
			}
						
			loadCCR.stop();
			return this;
		},
		
		stepClickGoTo: function gotoStep(e) {
			
			if(_this.autorizar){					
				_this.$el.find('#aDadosCliente').removeClass('active');
				_this.$el.find('#aSimulacao').removeClass('active');
				_this.$el.find('#aAvaliacaoRisco').removeClass('active');
				
				_this.$el.find('#aDadosComplementares').addClass('active');
				_this.$el.find('#btnVoltar').hide();
				
				-this.carregarAutorizarPeloManter(e);
				

//			}
//			else if(_this.avaliar || _this.avaliarOperacao) {
//				_this.$el.find('#aDadosCliente').removeClass('active');
//				_this.carregarAvaliarPeloManter(e);
			}else{		
			
				var step = $('ul.wizard-steps > li.active').data('step');
				var nextIndex = step-1;
				
				if(step == 1) {
					if(_this.contratarConsignadoAvaliarControl != null) {
						// Fluxo vindo pelo manter, e clicando pra voltar em Simulação
//						_this.carregarSimulacaoClick(e);
					}
				}
				
				if(step == 3) {
					$("#btnContratar").hide();
					$("#btnImprimirContrato").show();
				}
				
				if (this.isStepClickValid(e) && this.navigate(nextIndex)) {
					this.checkComplete();
					this.updateStepInactive();
					this.actualView = nextIndex;
					this.updateStepActive();
					this.renderActualView();
				}	
			}	
		},
		
		
		carregarSimulacaoClick : function(e) {
			_this.cliente = _this.contratarConsignadoAvaliarControl.cliente;
			this.carregarEtapa(1,0,e, false);
		},
		
		carregarAutorizarPeloManter : function (e){
			
			_this.$el.find('#aDadosCliente').removeClass('active');
			_this.$el.find('#aSimulacao').removeClass('active');
			_this.$el.find('#aAvaliacaoRisco').removeClass('active');
			
			_this.$el.find('#aDadosComplementares').addClass('active');
			_this.$el.find('#btnVoltar').hide();
			
			this.carregarEtapa(5,null, e,false);
		},
		
		carregarAvaliarPeloManter : function (e){
			_this.$('#step1').removeClass('active');
			$wizard.wizard('next');
			$wizard.wizard('updateProgressBar');
			_this.$el.find('#btnVoltar').hide();
			
			this.carregarEtapa(2,null, e,false);
		},
		
		
		consultar : function() {			
			
			console.log("WizardConsginadoControle - consultar");
			
			alert("WizardConsginadoControle - consultar");
			
		},
		
		
		/**
		 * Funçao de utilizada para carregar as etapas do Wizard
		 * 
		 * @param etapa
		 * 	
		 */
		carregarEtapa : function(etapa, op, e, voltar) {
			console.log("WizardConsginadoControle - carregarEtapa: " + etapa);
			
			$('#btnContratar').hide();
			$('#btnImprimirContrato').hide();
			$('#btnRecalcular').hide();
			
			_this.$el.find("#btnVoltar").hide();
			_this.$el.find("#btnAvancar").hide();
			
			if(etapa == 1){
				if (op == 0) {
					$wizard.wizard('next');
					$wizard.wizard('updateProgressBar');
				}else if (op == 1){
					$wizard.wizard('previous');			
					_this.newContracao = false;
				}
				$('#btnRecalcular').show();
				
				this.$('#step1').addClass('active');
				if(!voltar) {
					if(_this.cliente != null) {
						$wizard.wizard('previous');	
					}
					this.consultaClienteControl = _this.step1.setElement(this.$('#step1')).render(_this.agenciaConcessora, _this.cliente);
				}
				rolarPaginaParaCima();			
			}
			if(etapa == 2){
				$("#btnVoltar").show();
				var cliente=null;
				var manter = null;
				var operacao = false;
				var avancar = null;
				if(this.consultaClienteControl != undefined) {
					var simulacao = this.consultaClienteControl.recuperarDados();
//					var digitoVerificador = this.consultaClienteControl.cliente.attributes.dados.cpf.documento.digitoVerificador;
//					var numeroCpf = this.consultaClienteControl.cliente.attributes.dados.cpf.documento.numero;
					simulacao.cpf = this.consultaClienteControl.cpf;  
					simulacao.taxaList = this.consultaClienteControl.taxaList;
					simulacao.valorMargem = this.consultaClienteControl.margemConsignado;
					if(this.consultaClienteControl.cliente.attributes == undefined) {
						cliente = this.consultaClienteControl.cliente;
					} else {	
						cliente = this.consultaClienteControl.cliente.attributes.dados;
					}
					

					if(_this.contratarConsignadoAvaliarControl != null) {
						if(_this.contratarConsignadoAvaliarControl.numeroContrato != null) {
							simulacao.nuContrato = _this.contratarConsignadoAvaliarControl.numeroContrato;
							manter = "manter";
							avancar = "avancar";
						} 
						
						if(_this.contratarConsignadoAvaliarControl == false) {
							manter = "manter";
							avancar = "avancar";
						}
					}
					
					_this.cliente = cliente;
				}else {
					var simulacao = $(e.target).data();
					_this.contrato = $(e.target).data('contrato') == undefined ? $(e.target).parent().data('contrato') : $(e.target).data('contrato');
					simulacao.agencia = _this.agenciaConcessora;
					simulacao.nuContrato = _this.contrato;
					if(simulacao.nuContrato == null) {
						simulacao.nuContrato = _this.contratarConsignadoAvaliarControl.contratoModelo.attributes.nuContrato;
					}
					manter = "manter";
					_this.$el.find('#aDadosCliente').addClass('concluido');
					$('#aDadosCliente').removeClass('complete');
					$("#btnVoltar").hide();
					operacao = _this.operacao;
				}
				
				$("#btnConsultar").removeAttr('id');
				
				
				var contrato = null;
			
				if(!_this.newContracao){
					contrato = this.step3.recuperarContrato();
				}

				if(contrato != null){
					$("#btnVoltar").hide();
				}
				
				simulacao.agencia = _this.agenciaConcessora;
				if (op == 0) {
					$wizard.wizard('next');
					$wizard.wizard('updateProgressBar');
				}else {
					$wizard.wizard('previous');
				}
				this.$('#step2').addClass('active');
				if(!voltar) {
					this.$('#step1').removeClass('active');
					_this.$el.find('#aDadosCliente').removeClass('active');
					_this.$el.find('#aAvaliacaoRisco').removeClass('active');
					_this.$el.find('#aSimulacao').addClass('active');
					this.contratarConsignadoAvaliarControl = _this.step2.setElement(this.$('#step2')).render(simulacao, cliente, manter, operacao, avancar);
				} 
				rolarPaginaParaCima();
				
			}
			if(etapa == 3){
				var simulacao = null;
				var avaliacao = null;
				var contratarPeloManter = false;
				if(this.consultaClienteControl == undefined) {
					simulacao = _this.contratarConsignadoAvaliarControl.dadosSimulacao; 
					avaliacao = _this.contratarConsignadoAvaliarControl.avaliacaoRiscoOptemp;
					simulacao.avaliacao = avaliacao;
					_this.cliente = _this.contratarConsignadoAvaliarControl.cliente;
					contratarPeloManter = true;
				} else {
					simulacao = _this.consultaClienteControl;
					avaliacao = this.contratarConsignadoAvaliarControl.recuperarDados();
					simulacao.agencia = _this.agenciaConcessora;
					simulacao.avaliacoes = this.contratarConsignadoAvaliarControl.avaliacao.listaAvaliacao;
//					simulacao.avaliacaoCliente = this.contratarConsignadoAvaliarControl.avaliacaoClienteValida;
//					simulacao.avaliacaoOperacao = this.contratarConsignadoAvaliarControl.avaliacaoRiscoOptemp;
					if(this.contratarConsignadoAvaliarControl.novaSimulacao.valorContrato != null || this.contratarConsignadoAvaliarControl.novaSimulacao.attributes != null) {
						simulacao.simulacao = this.contratarConsignadoAvaliarControl.novaSimulacao; 
					}
				}
//				simulacao.nuAvaliacaoOperacao = _this.contratarConsignadoAvaliarControl.nuAvaliacaoOperacao;
				var contrato = this.contratarConsignadoAvaliarControl.contrato;
				if (op == 0) {
					$wizard.wizard('next');
					$wizard.wizard('updateProgressBar');
				}else {
					$wizard.wizard('previous');
				}
				this.$('#step3').addClass('active');
				$('#btnAvancar').hide();
				$("#btnVoltar").show();
				
//				if(_this.contratoBusca.situacao.id == 2) {
//					
//				}
				
				if(contrato != null){
					$("#btnImprimirContrato").show();
					$('#btnAvancar').show();	
				}
				if(!voltar) {
					this.contratarConsignadoContratar = _this.step3.setElement(this.$('#step3')).render(simulacao, _this.cliente, contrato).el;
				}
				rolarPaginaParaCima();
			}
			if(etapa == 4){
				$("#btnVoltar").show();	
				if(this.consultaClienteControl != undefined) {
					var simulacao = this.consultaClienteControl;
					simulacao.agencia = _this.agenciaConcessora;
					_this.contrato = _this.step3.popularContrato;
					simulacao.nuContrato = _this.step3.recuperarContrato();
					_this.contrato = _this.step3.popularContrato();
					simulacao.avaliacaoOperacao = _this.contratarConsignadoAvaliarControl.avaliacaoRiscoOptemp;
					simulacao.cpf = _this.consultaClienteControl.cpf;
				}else {
					var simulacao = {};
					if(e != undefined) {
						simulacao = $(e.target).data();
						_this.contrato = $(e.target).data('contrato') == undefined ? $(e.target).parent().data('contrato') : $(e.target).data('contrato');
					} else  {
						simulacao = _this.contratarConsignadoAvaliarControl;
					}
					simulacao.agencia = _this.agenciaConcessora;
					simulacao.nuContrato = _this.contrato; 
				}
				if (op == 0) {
					$wizard.wizard('next');
					$wizard.wizard('updateProgressBar');
				}else {
					$wizard.wizard('previous');
				}
				
				 _this.$el.find('#btnAvancar').hide();			
				 $("#btnContratar").show();
				 
				 this.$('#step4').addClass('active');
				 _this.dadosAutorizacao = simulacao;
				 this.contratarConsignadoAutorizar = _this.step4.setElement(this.$('#step4')).render(simulacao);
				 if(!_this.perfil) {
					$("#bntAutorizar").attr('disabled', true);
					$("#bntNegar").attr('disabled', true);
				 }
				 
				_this.$el.find('#btnSair').show();
				rolarPaginaParaCima();
				
			}
			
			//chamada do step 4 ai clicar nos botões desatiados do wizard.
			if(etapa == 5){
				 _this.$el.find('#btnAvancar').hide();			
				
				this.$('#step1').removeClass('active');
				this.$('#step2').removeClass('active');
				this.$('#step3').removeClass('active');
				this.$('#step4').addClass('active');
				this.contratarConsignadoAutorizar = _this.step4.setElement(this.$('#step4')).render(_this.dadosAutorizacao);
				rolarPaginaParaCima();
			}	
			
			
		},
	    
		/**
		 * Função utilizada para avancar nas etapas.
		 * Acao do botao btnAvancar
		 * 
		 * Parametro passado automaticamente
		 * 
		 * @param evento
		 * 
		 */
		avancar : function() {
			console.log("WizardConsginadoControle - avancar");
			console.log($("#aposentadoPorInvalidez").val());
			if(!$("#aposentadoPorInvalidez").val()) {
				//pega o numero da etapa pelo active e o numero pelo data
				var etapaAtual = $('ul.wizard-steps > li.active').data('step');
				//avanca a etapa
				var proximaEtapa = etapaAtual + 1;
				console.log(_this);
				var disable = $("#numeroCPF").is(":disabled");
				//chama a funcao para carregar a proxima etapa
				if(disable) {
					this.carregarEtapa(proximaEtapa, 0, undefined, true);
				} else {
					this.carregarEtapa(proximaEtapa, 0, undefined, false);
				}
				
				
			} else {
				Retorno.trataRetorno({codigo: 1, tipo: "AVISO", mensagem: "", idMsg: 'MA0080'}, 'simular');
				return;
			}
				
		},
		
		voltar : function(evento) {
			
			console.log("WizardConsginadoControle - voltar");
			//pega o numero da etapa pelo active e o numero pelo data
			var etapaAtual = $('ul.wizard-steps > li.active').data('step');
			//avanca a etapa
			var proximaEtapa = etapaAtual - 1;
			//chama a funcao para carregar a proxima etapa
			var voltar=true;
			this.carregarEtapa(proximaEtapa, 1, evento, voltar);
			
		},
		
		sair: function () {
			console.log("saindo do CCR...");
			window.location = 'index.html';
		},			
		
		cancelar: function () {		
			
			_this.limparForm();
			_this.comando = null;
		},
		

		
	});

	return WizardConsignadoControle;
	
});