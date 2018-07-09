/**
 * @author F620600
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
        'text!visao/wizardConsignado.html',
        'text!visao/contratacao/contratar/autorizar/panelDadosContratacao.html',
        'text!visao/contratacao/contratar/autorizar/panelAutorizacaoGerencial.html',
        'text!visao/contratacao/contratar/autorizar/panelAutorizacaoConformidade.html',
        'controle/contratacao/wizardConsignadoControle',
        'modelo/contratacao/contrato',
        'modelo/contratacao/autorizar',
        'modelo/contratacao/contrato',
        'modelo/cliente/cliente',
        'modelo/cliente/avaliacao'
        ], 
function(EMensagemCCR, ETipoServicos, Retorno, wizardConsignadoTpl, dadosContratacaoTpl, autorizacaoGerencialTpl, autorizacaoConformidadeTpl, wizardConsignadoControle, contratoModelo, Autorizar, Contrato, Cliente, Avaliacao){

	var _this = null;
	var ContratarConsignadoAutorizar = Backbone.View.extend({

		className: 'contratarConsignadoAutorizar',
		
		/**
		 * Templates
		 */
		wizardConsignadoTemplate : _.template(wizardConsignadoTpl),
		dadosContratacaoTemplate  : _.template(dadosContratacaoTpl),
		autorizacaoGerencialTemplate  : _.template(autorizacaoGerencialTpl),
		autorizacaoConformidadeTemplate  : _.template(autorizacaoConformidadeTpl),
		
		/**
		 * Variaveis da classe
		 */
		validator   : new Validators(),
		message     : new Message(),
		comando     : null,
		modelo		: null,
		collection  : null,
		acao		: null,
		contrato    : null,
		dfd : 		null,
		avaliacaoOperacao : null,
		
		/**
		 * Função que faz bind das ações e interações da pagina com as funções
		 * javascript
		 * 
		 */
		events : {
			'click a#btnLimparForm'		  		: 'limparFormToda',
			'click a#btnSair'		      		: 'sair',
			'click button#btnVoltar'	 		: 'voltar',
			'click .btnAutorizar'	  	  		: 'autorizar',
			'click #btnAutorizarContrato'		: 'confirmaAutorizacao',
			'click #btnFecharAutorizarContrato' : 'fechaAutorizacaoContrato',
			'click .btnNegar'	  	  	  		: 'negar',
			'click #headPanelG'			  		: 'controleExibicaoPanelG',
			'click #headPanelC'			  		: 'controleExibicaoPanelC',
			'click #btnJustificativa'     		: 'justificar',
			'click #contratoNegadoGerencial'	: 'showDetalhesNegadoGerencial',
			'click #contratoNegadoConformidade'	: 'showDetalhesNegadoConformidade',
			'click #btnVoltarModal'				: 'fecharModalJustificativa'
			
		},
				
		/**
		 * Função padrão de incialização do template html
		 * 
		 */
		finalizar : function() {
			$wizard.wizard('completeProgressBar');
		},
		
		initialize : function() {
			
			console.log("contratarConsignadoAutorizar - initialize");
			
			_this = this;			
			_this.mostrarPanelG = true;
			_this.mostrarPanelC = true;
			_this.contratoModelo = new contratoModelo();
			_this.contratar = new Contrato();
			// configura validator
			_this.validator.addValidator(new DataValidator());			
			_this.validator.withErrorRender(new BootstrapErrorRender());
		},
		
		
		render : function(e) {
			console.log("contratarConsignadoAutorizar - render");
			loadCCR.start();
			_this.dfd = $.Deferred();
			_this.contratoDados = {};
			_this.autorizacoesGerencial = [];
			_this.autorizacoesConformidade = [];
			if(e != undefined){
				_this.contrato = e.nuContrato;
				if(e.contrato != null ) {
					if(e.contrato.nuCpf != null) {
						_this.cpfCliente = e.contrato.nuCpf; 
					}
				}
				if(e.avaliacaoOperacao != undefined)  {
					_this.avaliacaoOperacao = e.avaliacaoOperacao.codigoAvaliacao;
				}
				_this.consultarContrato(_this.contrato, e);
			}
			
//			_this.dfd.promise().done(function() {
				
				
			// Carrega panelDadosContratacao.html
			// Carrega panelAutorizacaoGerencial.html
			
			//			})
		},
		
		consultarContrato : function(id,e) {
			if(id == null) {
				id = e.contrato.nuContrato;
			}
			_this.contratoModelo.consultarPorId(id).done(function sucesso(data) {
				_this.contratoDados = data;
				_this.avaliacaoOperacao = _this.contratoDados.avaliacaoOperacao;
				
				_this.cliente = new Cliente();
				if(_this.cpfCliente != null) {
					_this.contratoDados.nuCpf = _this.cpfCliente; 
				}
				_this.cliente.consultarDadosSicliPorCPF(_this.contratoDados.nuCpf)
				.done(function sucesso(dataRef) {
					if(dataRef != undefined && dataRef.dados != undefined && dataRef.dados.nomeCliente != undefined && dataRef.dados.nomeCliente.nome != undefined)
					_this.cliente=dataRef.dados;
					_this.clienteDados = _this.cliente;
					_this.agencia = e.agencia;
					_this.contratoDados.nuCpf = _this.cliente.cpf.documento.numero + _this.cliente.cpf.documento.digitoVerificador;
					if(_this.contratoDados.coUsuario == null) {
						_this.contratoDados.coMatriculaCliente = _this.contratoModelo.attributes.coMatriculaCliente;
					} else {
						_this.contratoDados.coMatriculaCliente = _this.contratoDados.coUsuario;
					}
					_this.contratoDados.nomeCliente = _this.cliente.nomeCliente.nome; 
					_this.contratoDados.contaCorrente = data.nuAgenciaContaCredito + "." + data.nuOperacaoContaCredito + "." + data.nuContaCredito + "-" + data.nuDvContaCredito;
				
					/**
					 * @method loadDadosAutorizacao Responsavel por trazer uma lista
					 *         de Autorizações através do {idContrato}.
					 */
					
					_this.$el.html(_this.dadosContratacaoTemplate({contrato: _this.contratoDados}));
					_this.loadDadosAutorizacao(id);
					
					var perfil;
					
					_this.modeloContrato = new Contrato();
					_this.modeloContrato.validarPerfil()
					.done(function sucesso(data) {
						perfil = data;
						
						//Console perfil Gestor - 
						console.log("-----------------------------");
						console.log("Perfil gestor: "+data);
						console.log("-----------------------------");
						
						
					})
					.error(function erro(jqXHR) {
						Retorno.trataRetorno({codigo: -1, tipo: "ERRO_EXCECAO", mensagem: "Ocorreu um erro ao buscar perfil"}, 'autorizar', jqXHR);
						loadCCR.stop();
					});
					
					_this.$el.find('#divPanelAutorizacaoG').html(_this.autorizacaoGerencialTemplate({contrato: _this.contratoDados, autorizacoes: _this.autorizacoesGerencial, perfil: perfil}));
					// Carrega panelAutorizacaoConformidade.html
					_this.$el.find('#divPanelAutorizacaoC').html(_this.autorizacaoConformidadeTemplate({contrato: _this.contratoDados, autorizacoes: _this.autorizacoesConformidade, perfil: perfil}));
					
					
					if(!perfil) {
						console.log("Perfil gestor NOK....");
						$('.btnAutorizar').addClass('disabled');
						_this.$el.find('.btnAutorizar').removeClass('btnAutorizar');
						$('.btnNegar').addClass('disabled');
						_this.$el.find('.btnNegar').removeClass('btnNegar');
						$("#autorizacoesPrimeiroG").hide();
						$("#autorizacoesSegundoG").hide();
					} else  {
						console.log("Perfil gestor OK....");
						$('.btnAutorizar').removeClass('disabled');
						_this.$el.find('.btnAutorizar').addClass('btnAutorizar');
						$('.btnNegar').removeClass('disabled');
						_this.$el.find('.btnNegar').addClass('btnNegar');
					}
					
					if(_this.autorizacoesGerencial.length > 0) {
						$("#gridAutorizacoes").show();
						$("#semAutorizacaoGerencial").hide();
						$("#autorizacoesPrimeiroG").hide();
						$("#autorizacoesSegundoG").show();
					} else {
						$("#gridAutorizacoes").hide();
						$("#semAutorizacaoGerencial").show();
						$("#autorizacoesPrimeiroG").show();
						$("#autorizacoesSegundoG").hide();
					}
					
					if(_this.autorizacoesConformidade.length > 0) {
						$("#semAutorizacaoConformidade").hide();
						$("#gridAutorizacaoConformidade").show();
					} else {
						$("#semAutorizacaoConformidade").show();
						$("#gridAutorizacaoConformidade").hide();
					}

					/**
					 * Desabilita Botões Autorizar e Negar, caso 
					 * já possua Contrato Autorizado Confirmade e Gerencial
					 * ou Negado Confirmidade e Gerencial
					 */
					if(_this.autorizacoesConformidade.length >= 1 && _this.autorizacoesGerencial.length >= 1) {
						if((_this.autorizacoesGerencial[0].codigoSituacao === "N" && _this.autorizacoesConformidade[0].codigoSituacao === "N") || (_this.autorizacoesGerencial[0].codigoSituacao === "A" && _this.autorizacoesConformidade[0].codigoSituacao == "A")) {
							_this.$el.find(".btnAutorizar").attr("disabled", true);
							_this.$el.find(".btnNegar").attr("disabled", true);
							_this.$el.find('.btnAutorizar').removeClass('btnAutorizar');
							_this.$el.find('.btnNegar').removeClass('btnNegar');
							return false;
						}	
					}
					
					var usuarioLogado = "C"+usuario.attributes.empregado.numeroMatricula;
					
					if(_this.contratoDados.situacao.id == 12 || _this.contratoDados.situacao.id == 8 
					 || _this.contratoDados.situacao.id == 9 || _this.contratoDados.situacao.id == 7) {
						_this.$el.find(".btnAutorizar").attr("disabled", true);
						_this.$el.find(".btnNegar").attr("disabled", true);
						_this.$el.find('.btnAutorizar').removeClass('btnAutorizar');
						_this.$el.find('.btnNegar').removeClass('btnNegar');
					} else if(_this.autorizacoesGerencial.length >= 1 && _this.autorizacoesGerencial[0].codigoSituacao === "N" 
						  && usuarioLogado == _this.autorizacoesGerencial[0].usuario.trim()){
						_this.$el.find(".btnNegar").attr("disabled", true);
						_this.$el.find('.btnNegar').removeClass('btnNegar');
					} else if(_this.autorizacoesGerencial.length >= 1 && _this.autorizacoesGerencial[0].codigoSituacao === "A"
							  && usuarioLogado == _this.autorizacoesGerencial[0].usuario.trim()){
						_this.$el.find(".btnAutorizar").attr("disabled", true);
						_this.$el.find('.btnAutorizar').removeClass('btnAutorizar');
						$("#autorizacoesPrimeiroG").show();
						$("#autorizacoesSegundoG").hide();
					}
					
					loadCCR.stop();
					
					return _this;

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
	
		loadDadosAutorizacao: function(idContrato) {
			_this.modelo = new Autorizar();
			_this.modelo.buscaAutorizacaoPorContrato(idContrato)
			.done(function sucesso(data) {
				_this.autorizacoes = data;
				$.each(data, function(i, obj) {
					if(obj.tipoAutorizacao === "G"){
						_this.autorizacoesGerencial[i] = obj;
					}else {
						_this.autorizacoesConformidade.push(obj);
					}
					
				});
				_this.autorizacoesConformidade = _this.autorizacoesConformidade.filter(value => Object.keys(value).length !== 0);
			})
			.error(function erro(jqXHR) {
				console.log(jqXHR);
			});
		},

		controleExibicaoPanelG : function() {
			console.log("panelAutorizacaoGerencial - controleExibicaoPanelG");
			
			if (_this.mostrarPanelG){
				_this.$el.find("#panelG").hide();
				_this.mostrarPanelG = false;
			}else{
				_this.$el.find("#panelG").show();
				_this.mostrarPanelG = true;
			}			
		},
		
		controleExibicaoPanelC : function() {
			console.log("panelAutorizacaoConformidade - controleExibicaoPanelC");
			
			if (_this.mostrarPanelC){
				_this.$el.find("#panelC").hide();
				_this.mostrarPanelC = false;
			}else{
				_this.$el.find("#panelC").show();
				_this.mostrarPanelC = true;
			}			
		},
		
		autorizar: function() {
			console.log("autorizar - autorizarContrato");
			
			_this.modelo = new Autorizar();
			_this.entrada = {};
			_this.entrada.idContrato = _this.contratoDados.nuContrato;
			_this.entrada.autorizar = 1;
			_this.entrada.justificativa = "";
			this.$el.find('#divFormularioConfirmacao').modal('show');
			console.log('Autorizar');
		},
		
		confirmaAutorizacao : function() {
			_this.autorizarNegarContrato(_this.entrada,_this.modelo);
			this.$el.find('#divFormularioConfirmacao').modal('hide');
		},
		
		fechaAutorizacaoContrato : function() {
			this.$el.find('#divFormularioConfirmacao').modal('hide');
		},
		
		negar: function() {
			console.log("autorizar - negaContrato");
			_this.$el.find("#justificativa").attr("disabled", false);
			_this.$el.find("#justificativa").val("");
			_this.$el.find("#btnJustificativa").show();
			this.$el.find('#divFormularioJustificativa').modal('show');
		},
		
		
		justificar: function() {
			_this.contratoDados.justificativa = _this.$el.find("#justificativa").val();
//			this.$el.find('#divFormularioJustificativa').modal('hide');
			_this.modelo = new Autorizar();
			_this.entrada = {};
			_this.entrada.idContrato = _this.contratoDados.nuContrato;
			_this.entrada.autorizar = 0;
			_this.entrada.justificativa = _this.contratoDados.justificativa;
			if(_this.contratoDados.justificativa === "") {
//				Retorno.trataRetorno({codigo: 0, tipo: "ERRO_EXCECAO", mensagem: "", idMsg: 'MA001'}, 'autorizar');
//				rolarPaginaParaCima();
				$("#mensagem-error").fadeTo(6000, 500).slideUp(500, function(){
				    $("#mensagem-error").slideUp(500);
				});
				$("#justificativaContrato").addClass("error");
			} else {
				_this.autorizarNegarContrato(_this.entrada,_this.modelo);
				console.log('Negar');
				$("#justificativaContrato").removeClass("error");
				this.$el.find('#divFormularioJustificativa').modal('hide');
			}
		},
	
		/**
		 * Busca na Tabela de Autorizações através do codigoSituacao e Numero Contrato
		 * Com isso, e feito uma verificação para ver se o Contrato é do Tipo Conformidade
		 * para assim, retornar para a Modal de Justificativa, seu respectivo valor.
		 */
		showDetalhesNegadoConformidade: function(event) {
			_this.modelo = new Autorizar();
			$("#justificativa").attr("disabled", true);
			$(_this.autorizacoesConformidade).each(function(i, obj) {
				if(obj.codigoSituacao === "N") {
					_this.modelo.buscaAutorizacaoPorSituacao(obj.codigoSituacao, _this.contratoDados.nuContrato, obj.nuSituacaoContrato)
					.done(function sucesso(data){
						$(data).each(function(index, valor) {
							if(valor.icTipoAutorizacao === "C") {
								/**
								 * Verifica Numero situação contrato para abrir a justificativa
								 * daquela especifica
								 */
								if(event.target.attributes[1].nodeValue==obj.nuSituacaoContrato.toString()){
									_this.$el.find("#justificativa").val(valor.deJustificativa);
									return false;
								}
							}
						});
					});
				}
			});
			_this.$el.find('#divFormularioJustificativa').modal('show');
			_this.$el.find("#btnJustificativa").hide();
			_this.$el.find('#divFormularioJustificativa').modal('show');
			_this.$el.find("#btnJustificativa").hide();
		},
		
		/**
		 * Busca na Tabela de Autorizações através do codigoSituacao e Numero Contrato
		 * Com isso, e feito uma verificação para ver se o Contrato é do Tipo Gerencial
		 * para assim, retornar para a Modal de Justificativa, seu respectivo valor.
		 */
		showDetalhesNegadoGerencial: function(event) {
			_this.modelo = new Autorizar();
			$("#justificativa").attr("disabled", true);
			$(_this.autorizacoesGerencial).each(function(i, obj) {
				if(obj.codigoSituacao === "N") {
					_this.modelo.buscaAutorizacaoPorSituacao(obj.codigoSituacao, _this.contratoDados.nuContrato, obj.nuSituacaoContrato)
					.done(function sucesso(data){
						$(data).each(function(index, valor) {
							if(valor.icTipoAutorizacao === "G") {
								/**
								 * Verifica Numero situação contrato para abrir a justificativa
								 * daquela especifica
								 */
								if(event.target.attributes[1].nodeValue==obj.nuSituacaoContrato.toString()){
									_this.$el.find("#justificativa").val(valor.deJustificativa);
									return false;
								}
							}
						});
					});
				}
			});
			_this.$el.find('#divFormularioJustificativa').modal('show');
			_this.$el.find("#btnJustificativa").hide();
		},
		
		fecharModalJustificativa: function() {
			_this.$el.find("#divFormularioJustificativa").modal('hide');
		},

		/**
		 * Metodo chamado tanto na requisição de Autorização quanto na Negação
		 * de um Contrato.
		 */
		autorizarNegarContrato(entrada, modelo) {
//			_this.modelo = new Autorizar();
			modelo.autorizar(entrada).done(function sucesso(data) {
				console.log(data);
				
				if(data.idMsg === "MA0010") {
					Retorno.trataRetorno({codigo: 0, tipo: "AVISO", mensagem: "", idMsg: 'MA0010'}, 'autorizar');
				}
				/**
				 * Realiza o append na(s) tabela(s) de Autorização em tempo de
				 * execução. Habilitando e desabilitando os botões, conforme as
				 * autorizações
				 */
				var counter = 0;
				$(data).each(function(index, obj) {
					_this.avaliacaoModelo = new Avaliacao();
					if(obj.codigoSituacao === "N") {
						//{cpfCliente, codigoAvaliacao, numeroUnidade}
						var cpfCliente = _this.clienteDados.cpf.documento.numero +""+_this.clienteDados.cpf.documento.digitoVerificador;
						var numeroUnidade = _this.agencia.idUnidade;
						var codigoAvaliacao =_this.avaliacaoOperacao;
						_this.avaliacaoModelo.solicitarCancelarAvaliacaoRiscoCredito(cpfCliente, codigoAvaliacao, numeroUnidade, false);
					}
					
					if(obj.tipoAutorizacao === "G") {
						$("#gridAutorizacoes").show();
						$("#semAutorizacaoGerencial").hide();
						counter++;
						if(obj.codigoSituacao === "N") {
							counter = 2;
							$("#semAutorizacaoConformidade").hide();
							$("#gridAutorizacaoConformidade").show();
							_this.$el.find("#autorizacoesGerencial").append("<tr>" +
																				"<td>"+ formatTimeStamp(obj.dtSituacao, 'dd/MM/yyyy HH:mm') +"</td>" +
																				"<td>"+ obj.usuario +"</td>" +
																				"<td id='contratoNegadoGerencial' sc='"+obj.nuSituacaoContrato+"' class='btn-link'>"+ obj.descSituacao +"</td>" +
																			"</tr>");
						} else {
							_this.$el.find(".btnAutorizar").attr('disabled', true);
							_this.$el.find('.btnAutorizar').removeClass('btnAutorizar');
							_this.$el.find("#autorizacoesGerencial").append("<tr>" +
									"<td>"+ formatTimeStamp(obj.dtSituacao, 'dd/MM/yyyy HH:mm') +"</td>" +
									"<td>"+ obj.usuario +"</td>" +
									"<td class='retorno'>"+ obj.descSituacao +"</td>" +
								"</tr>");
							
						}
						_this.gerencial = {};
						_this.gerencial.dtSituacao = obj.dtSituacao;
						_this.gerencial.usuario = obj.usuario;
						_this.gerencial.descSituacao = obj.descSituacao;
						_this.gerencial.codigoSituacao = obj.codigoSituacao;
						_this.gerencial.nuSituacaoContrato = obj.nuSituacaoContrato;
						_this.autorizacoesGerencial.push(_this.gerencial); 
					}else {
						$("#semAutorizacaoConformidade").hide();
						$("#gridAutorizacaoConformidade").show();
						counter++
						if(obj.codigoSituacao === "N") {
						counter = 2;
						_this.$el.find("#autorizacoes").append("<tr>" +
								"<td>"+ formatTimeStamp(obj.dtSituacao, 'dd/MM/yyyy HH:mm') +"</td>" +
								"<td>"+ obj.usuario +"</td>" +
								"<td id='contratoNegadoConformidade' sc='"+obj.nuSituacaoContrato+"' class='btn-link'>"+ obj.descSituacao +"</td>" +
							"</tr>");
						} else {
							_this.$el.find("#autorizacoes").append("<tr>" +
									"<td>"+ formatTimeStamp(obj.dtSituacao, 'dd/MM/yyyy HH:mm') +"</td>" +
									"<td>"+ obj.usuario +"</td>" +
									"<td class='retorno'>"+ obj.descSituacao +"</td>" +
								"</tr>");
						}
						_this.conformidade = {};
						_this.conformidade.dtSituacao = obj.dtSituacao;
						_this.conformidade.usuario = obj.usuario;
						_this.conformidade.descSituacao = obj.descSituacao;
						_this.conformidade.codigoSituacao = obj.codigoSituacao;
						_this.conformidade.nuSituacaoContrato = obj.nuSituacaoContrato;
						_this.autorizacoesConformidade.push(_this.conformidade);
					}
				});	
				if(counter==2) {
					_this.$el.find(".btnAutorizar").attr('disabled', true);
					_this.$el.find(".btnNegar").attr('disabled', true);
					_this.$el.find('.btnAutorizar').removeClass('btnAutorizar');
					_this.$el.find('.btnNegar').removeClass('btnNegar');
				}
				
				if(_this.autorizacoesConformidade.length >= 1 && _this.autorizacoesGerencial.length >= 1) {
					if((_this.autorizacoesGerencial[0].codigoSituacao === "N" && _this.autorizacoesConformidade[_this.autorizacoesConformidade.length-1].codigoSituacao === "N") || (_this.autorizacoesGerencial[0].codigoSituacao === "A" && _this.autorizacoesConformidade[_this.autorizacoesConformidade.length-1].codigoSituacao == "A")) {
						_this.$el.find(".btnAutorizar").attr("disabled", true);
						_this.$el.find(".btnNegar").attr("disabled", true);
						_this.$el.find('.btnAutorizar').removeClass('btnAutorizar');
						_this.$el.find('.btnNegar').removeClass('btnNegar');
					}	
				}
				Retorno.trataRetorno({codigo: 0, tipo: "SUCESSO", mensagem: "", idMsg: 'MA0003'}, 'autorizar');
				rolarPaginaParaCima();
			})
			.error(function erro(jqXHR) {
				console.log(jqXHR);
			});
		},
		
		voltar : function(evento) {
			console.log("contratarConsignadoAutorizar - voltar");
			// pega o numero da etapa pelo active e o numero pelo data
			var etapaAtual = $('ul.wizard-steps > li.active').data('step');
			// volta a etapa
			console.log("contratarConsignadoAutorizar - carrega etapa: " + etapaAtual);			
			var wizardConsignadoControl = new wizardConsignadoControle();			
			wizardConsignadoControl.carregarEtapa(etapaAtual-1);
		},
		
						
	    getCollection: function () {
	    	if (_this.collection == null || this.collection == undefined)
	    		_this.collection = new TaxaIOFCollection();
	    		
	    	return _this.collection;
	    },
	    
		sair: function () {
			loadCCR.start();
			console.log("saindo do CCR...");
			window.location = 'index.html';
		},
		
	
	});

	return ContratarConsignadoAutorizar;
	
});