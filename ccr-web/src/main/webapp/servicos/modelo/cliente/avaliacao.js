/**
 * @author F620600
 * @version 1.0.0.0
 *  
 */
define(['configuracoes',
        'enumeracoes/eMensageria',
        'modelo/Mensageria',
        'util/retorno'], 
function(config, EMensageria, Mensageria, Retorno){
	
	var _this = null;
	var Avaliacao = Backbone.RelationalModel.extend({

		//url de para chamada do servico rest padrao
		urlRoot: config['urlBaseRest'] + 'avaliacao/',
	
		mensageria : undefined,
		consultaOperacional : false,
		isGerenteLogado : "",
		avaliacao : true,
		
		/***
		 * Criacao de valores padroes
		 */
	    defaults : {
	    	
	    },
	    
	    /**
		 * Função padrão de incialização do modelo
		 * 
		 * @TODO
		 * 	
		 */
		initialize : function () {
			console.log("Avaliacao - initialize");
			//variavel auxiliar para uso em funcoes internas
			_this = this;
			
			//array de validadores
			this.validador = {};
			     	
		},
		
		/**
		 *Função para validar os dados do cliente
		 *
		 *Parametro eh o atributo que deseja ser validado
		 *
		 *@param(atributo)
		 * 
		 */	
		validaAtributo: function (atributo) {
	        return (_this.validador[atributo]) ? _this.validador[atributo](_this.get(atributo)) : {isValid: true};
	    },
	    
	    getMensageria : function(){
			if(_this.mensageria == undefined || _this.mensageria == null){
				_this.mensageria = new Mensageria();

				_this.listenTo(_this.mensageria, 'consultar', _this.recebido); 
				_this.listenTo(_this.mensageria, 'solicitar', _this.recebidoAvaliacao);
				_this.listenTo(_this.mensageria, EMensageria.NAO_RECEBIDO, this.naoRecebido);
				_this.listenTo(_this.mensageria, 'error', _this.erro);
			}
			return this.mensageria;
		},
	    
		consultar: function(){ 	
			
			_this = this;			
			_this.url = _this.urlRoot + "consultar";
			 teste={};
			return _this.fetch({
			    type: "POST",
			    contentType: "application/json",
			    data: JSON.stringify(teste)
			});
		},
		
		salvarAvaliacaoRisco: function(cliente){ 	
			var request = {
					avaliacao : cliente
			},
			
			_this = this;			
			_this.url = _this.urlRoot + "salvarAvaliacaoRisco";
			 teste={};
			return _this.fetch({
			    type: "POST",
			    contentType: "application/json",
			    data: JSON.stringify(request)
			});
			
		},
		
		avaliacaoOperacao: function(cliente){ 	
			
			_this = this;			
			_this.url = _this.urlRoot + "operacao";
			 teste={};
			return _this.fetch({
			    type: "POST",
			    contentType: "application/json",
			    data: JSON.stringify(cliente)
			});
			
		},
		
		
		solicitar: function(){ 
			
			//Define os dados de envio			
			_this.getMensageria().unset('dados');
			var param = $.param({ 'idSimulacao': this.get('idSimulacao') });
			
			//Realiza a requisicao ao modelo de mensageria
			_this.getMensageria().requisicao(_this.urlRoot + 'solicitar', 'solicitar', 5, 1000, true, param);
		},
		
		/**
		 * A ser executada apenas quando o evento do modelo de mensageria 'recebido' for acionado
		 */
		recebido : function(modelo){
			console.log('Avaliacao - recebido - ini', modelo);
			
			var avaliacao = modelo.get('dados');
			var possuiAvaliacaoValida = false;
			
			console.log('dados', avaliacao);
			
			if (avaliacao != null && 'consulta' in avaliacao)
				if (avaliacao.consulta != null && 'avaliacoesValidas' in avaliacao.consulta)
					if (avaliacao.consulta.avaliacoesValidas != null && avaliacao.consulta.avaliacoesValidas.length > 0)
						possuiAvaliacaoValida = true;
				
			if (possuiAvaliacaoValida)	
				_this.trigger("SUCESSO_AVALIACAO", avaliacao.consulta.avaliacoesValidas); //Aciona os listeners do evento
			else
				_this.solicitar();
		},
		
		/**
		 * A ser executada apenas quando o evento do modelo de mensageria 'recebido' for acionado
		 */
		recebidoAvaliacao : function(modelo){
			console.log('Avaliacao - recebido - ini', modelo);
				
			_this.trigger(modelo.eventoSucesso, modelo.get('dados')); //Aciona os listeners do evento			
		},
		
		/**
		 * 
		 */
		naoRecebido : function(modelo){
			console.log('Avaliacao - naoRecebido - ini');
			
			//Aciona os listeners do evento naoRecebido
			_this.trigger("SEM_RETORNO_AVALIACAO", _this);
		},
		
		/**
		 * Tratamento de erros neste modelo.
		 */
		erro : function(modelo, xhr, request){
			console.log('Avaliacao - erro - ini');
			
			//Repassa o erro para quem estiver escutando este modelo
			_this.trigger("ERROR_AVALIACAO", _this, xhr, request);
		},
		
		
		consultarListaAvaliacaoRisco: function(usuario, consultaOperacionalParam) {
			console.log("avaliacao - consultarAvaliacaoListaAvalicao");
			//limpa o formato do cpf
			var consultaAvaliacao = {
					dadosSicli : usuario
			}
			
			//variavel auxiliar para uso em funcoes internas
			var _this = this;
			_this.consultaOperacional = consultaOperacionalParam;

			//seta a url com o parametro de busca
			_this.url  = _this.urlRoot + "consultarListaAvaliacaoRisco";
			_this.fetch({
				type : "POST",
				contentType : "application/json",
				traditional: true,
				data: JSON.stringify(consultaAvaliacao),
				async: false,
				"success" : function(model, response) {
					var correlation = response;
					setTimeout(function(){
						//realiza a busca por 3 com time de 5s (5000)
						_this.agendador(3, 3000,_this.recebeListaConsultaAvaliacaoRisco,correlation);						
					},3000);
					
				},
				"error" : function(model, retorno) { 
					loadCCR.stop();
					
				}
			});
		},
		
		agendador : function (tentativas, atraso, funcao, parametro){
			var _this = this;
			if(tentativas > 0){
				//recebe o resultado da ultima execurcai
				var reexcutar = _this.recebeListaConsultaAvaliacaoRisco(parametro);
				console.log(reexcutar);
				//verifica se deve reexecutar
				if(!reexcutar){
					console.log("reexcutar");
					console.log(new Date());
					tentativas = tentativas - 1;
					//execulta a funcao de agendar com um tempo de atraso
					//esse tempo eh de expera por resposta do barramento
					setTimeout(function(){
						_this.agendador(tentativas, atraso, funcao, parametro);						
					},atraso);
								
				}	
			}else{
				Retorno.trataRetorno({codigo: -1, tipo: "ERROR", mensagem: "", idMsg: 'MA011'}, 'contrato');
				loadCCR.stop();
			}

		},
		
		recebeListaConsultaAvaliacaoRisco: function (correlation){
			console.log("avaliacao - recebeListaConsultaAvaliacaoRisco");
			var _this = this;
			
			console.log(_this.listaAvaliacao);
			
			//executa a busca dos dados na fila de resposta
			var reExecutar = false;
			_this.fetch({
				type: "POST",
				contentType : "application/json",
				async: false,	
				data: JSON.stringify(correlation),
				dataType: "json",
				url:  _this.urlRoot + "recebeListaAvaliacaoRisco",
				"success" : function(model, response) {
					if(response != null){
						_this.listaAvaliacao = response;
						reExecutar = true;
						if(_this.consultaOperacional){
							_this.trigger("showResultadoListaOperacao",true);
							
						}else{
							_this.trigger("showResultadoListaCliente",true);
						}						
					}else{
						reExecutar = false;
					}	
				},
				"error" : function(model, retorno, opcoes) {
					reExecutar = false;
				},
				"always" : function(model, retorno, opcoes) {
					console.log("avaliacao - recebeListaConsultaAvaliacaoRisco - always");	
				}
			});
			return reExecutar;
		},
		
		isGerente: function() {
			var _this = this;
			this.url  = _this.urlRoot + "verificarUsuarioGerencial"	
			var obj = {};
			
	        	
			//realiza a busca
			_this.fetch({
				type : "POST",
				contentType : "application/json",
				data: JSON.stringify(obj),
				traditional: true,			
				async: false,
				"success" : function(model, response) {
					console.log("isGerenteConformidade - success");
					_this.isGerenteLogado = response;
				},
				"error" : function(model, retorno) {
					console.log("AvaliacaoRiscoCredito - isGerenteConformidade - erro");	
					console.log(retorno);
				}
			});
		},
		
		solicitaAvaliacaoRiscoClienteSiric: function(clienteParam) {				
			var solicitaAvaliacao ={
					dadosSicli :  clienteParam,
			}
			
			//variavel auxiliar para uso em funcoes internas
			var _this = this;
			//seta a url com o parametro de busca
			
			_this.url  =  _this.urlRoot + "solicitaAvaliacaoRiscoClienteSiric";
			var correlationID = null;
			//realiza a busca
			_this.fetch({
				type : "POST",
				contentType : "application/json",
				data: JSON.stringify(solicitaAvaliacao),
				traditional: true,			
				async: false,
				"success" : function(model, response) {				
					var correlation = response;
					//_this.i = 0;
					setTimeout(function(){
						//realiza a busca por 3 com time de 5s (5000)
						_this.agendadorRespostaRiscoCreditoCliente(3, 3000,_this.recebeListaConsultaAvaliacaoRisco,correlation);						
					},3000);
				},
				"error" : function(model, retorno) {
					loadCCR.stop();
					Retorno.trataRetorno({codigo: -1, tipo: "ERROR", mensagem: "", idMsg: 'MA011'}, 'contrato');
				}
			});
		},
		
		solicitaAvaliacaoRiscoOperacionaSiric: function(dadosParam, clienteParam, rendaParam) {			
			var solicitaAvaliacaoRequest = {
					renda : rendaParam,
					dadosSicli : clienteParam,
					dadosOperacao: dadosParam.dadosOperacao
			}

			
			var _this = this;
			_this.consultaOperacional = true;
			//seta a url com o parametro de busca
		
			_this.url  = _this.urlRoot + "solicitaAvaliacaoRiscoCreditoSiric";
			var correlationID = null;
			//realiza a busca
			_this.fetch({
				type : "POST",
				contentType : "application/json",
				data: JSON.stringify(solicitaAvaliacaoRequest),
				traditional: true,			
				async: false,
				"success" : function(model, response) {		
					var correlation = response;
					setTimeout(function(){
						_this.agendadorRespostaRiscoCreditoCliente(6, 10000,_this.consultaRespostaAvaliacaoRiscoClienteCredito,correlation);						
					},10000);						
				},
				"error" : function(model, retorno) {
					loadCCR.stop();
					Retorno.trataRetorno({codigo: -1, tipo: "ERROR", mensagem: "", idMsg: 'MA012'}, 'contrato');
				}
			});
		},	
		
		agendadorRespostaRiscoCreditoCliente : function (tentativas, atraso, funcao, parametro){ 
			console.log(new Date());
			console.log("tentativas -- > " + tentativas);
			var _this = this;
			if(tentativas > 0){
				var reexcutar = _this.consultaRespostaAvaliacaoRiscoClienteCredito(parametro);
				console.log(reexcutar);
				//logica invertida
				if(!reexcutar){
					console.log("reexcutar");
					console.log(new Date());
					tentativas = tentativas - 1;
					setTimeout(function(){
						_this.agendadorRespostaRiscoCreditoCliente(tentativas, atraso, funcao, parametro);						
					},atraso);						
				}	
			}else{
				Retorno.trataRetorno({codigo: -1, tipo: "ERROR", mensagem: "", idMsg: 'MA011'}, 'contrato');
				loadCCR.stop();
			}
		},
		
		
		consultaRespostaAvaliacaoRiscoClienteCredito: function(correlationID) {  
			var _this = this;
			var reAgendar = false;
			
			_this.url  =  _this.urlRoot + "recebeAvaliacaoRiscoClienteCredito";
			
			//realiza a busca
			_this.fetch({
				type : "POST",
				contentType : "application/json",
				data: JSON.stringify(correlationID),
				async: false,
				"success" : function(model, response) {
					console.log("success recebeAvaliacaoRiscoClienteCredito");
					if(_this.consultaOperacional){						
						_this.codAvaliacaoRiscoCliente = response != undefined && response.aprovacao != undefined ? response.aprovacao.codigoAvaliacao : "";
						_this.trigger("controleBtnConsultarAvaliacaoOperacaoEnabled",true);
						_this.trigger("showMensagemSolicitacaoAvaliacaoOperacao",true);
						_this.trigger("controleBtnSolicitarAvaliacaoOperacaoDisabled",true);

					}else{
						if(response == null || response.aprovacao == null){
							_this.trigger("showMensagemSolicitacaoAvaliacaoClienteError",true);
						}else{
							_this.trigger("controleBtnSolicitaAvaliacaoClienteDisabled",true);
							_this.trigger("controleBtnConsultarAvaliacaoClienteEnabled",true);
							_this.trigger("showMensagemSolicitacaoAvaliacaoCliente",true);
						}
					}
					
					reAgendar = true;
				},
				"error" : function(model, retorno) {
					Retorno.trataRetorno({codigo: -1, tipo: "ERROR", mensagem: "", idMsg: 'MA013'}, 'contrato');
				}
			});

			return reAgendar;
		},
		
		desbloquearAvaliacaoRisco: function(cpf ,codigoAvaliacao, agencia) {
			var _this = this;
			_this.url  = _this.urlRoot + "desbloquearAvaliacaoRisco";
		
			var obj = {
					cpf: formatadores.retiraFormato(cpf), 
					codigoAvaliacao:codigoAvaliacao,
					agenciaLogado : agencia
			};
			
			_this.fetch({
				type : "POST",
				contentType : "application/json",
				data: JSON.stringify(obj),
				traditional: true,			
				async: false,
				"success" : function(model, response) {
					if(response.codigoRetorno == 0){
						_this.trigger('showSimulacaoResultado',true);
					}else{
						loadCCR.stop();
						Retorno.trataRetorno({codigo: -1, tipo: "ERROR", mensagem: "", idMsg: 'MA010'}, 'contrato');
						
					}
				},
				"error" : function(model, retorno) {
					loadCCR.stop();
					Retorno.trataRetorno({codigo: -1, tipo: "ERROR", mensagem: "", idMsg: 'MA010'}, 'contrato');
				}
			});
		},
		
		/**
		 * solicitarCancelarAvaliacaoRiscoCredito
		 * params: {cpfCliente, codigoAvaliacao, numeroUnidade}
		 */		
		solicitarCancelarAvaliacaoRiscoCredito: function(cpfCliente, codigoAvaliacao, numeroUnidade, fluxoAvaliar) {	 
			var cancelaAvaliacaoRequest = {
					cpfCliente : cpfCliente,
					codigoAvaliacao : codigoAvaliacao,
					numeroUnidade: numeroUnidade
			}
         	var _this = this;
			_this.avaliacao = fluxoAvaliar;
		
			_this.url  = _this.urlRoot + "solicitarCancelarAvaliacaoRiscoCredito";
			var correlationID = null;
			//realiza a busca
			_this.fetch({
				type : "POST",
				contentType : "application/json",
				data: JSON.stringify(cancelaAvaliacaoRequest),
				async: false,
				"success" : function(model, response) {		
					correlationID = response.id;
					setTimeout(function(){
						_this.agendadorCancelaAvaliacaoRisco(6, 10000,_this.receberCancelarAvaliacaoRiscoCredito,correlationID);						
					},10000);						
				},
				"error" : function(model, retorno) {
					loadCCR.stop();
					Retorno.trataRetorno({codigo: -1, tipo: "ERROR", mensagem: "", idMsg: 'MA012'}, 'contrato');
				}
			});
		},	
		
		/**
		 * Agendador para Cancelar Avaliação de Risco
		 */		
		agendadorCancelaAvaliacaoRisco : function (tentativas, atraso, funcao, parametro){
			console.log(new Date());
			console.log("tentativas -- > " + tentativas);
			var _this = this;
			if(tentativas > 0){
				var reexcutar = _this.receberCancelarAvaliacaoRiscoCredito(parametro);
				console.log(reexcutar);
				//logica invertida
				if(!reexcutar){
					console.log("reexcutar");
					console.log(new Date());
					tentativas = tentativas - 1;
					setTimeout(function(){
						_this.agendadorCancelaAvaliacaoRisco(tentativas, atraso, funcao, parametro);						
					},atraso);						
				}	
			}else{
				Retorno.trataRetorno({codigo: -1, tipo: "ERROR", mensagem: "", idMsg: 'MA011'}, 'contrato');
				loadCCR.stop();
			}
		},
		

		/**
		 * receberCancelarAvaliacaoRiscoCredito
		 */
		receberCancelarAvaliacaoRiscoCredito: function(correlationID) { 
			var _this = this;
			var reAgendar = false;
			
			_this.url  =  _this.urlRoot + "receberCancelarAvaliacaoRiscoCredito";
			
			//realiza a busca
			_this.fetch({
				type : "POST",
				contentType : "application/json",
				data: JSON.stringify(correlationID),
				async: false,
				"success" : function(model, response) {
					console.log("success");
					if(response.codigoRetorno === "0"){
						if(_this.avaliacao){
							_this.trigger('solicitaAvaliacaoRiscoOperacaoSiric',true);
							_this.trigger('controleComponentesAvaliacaoOperacaoAposRecalculo',true);
						}else{
							Retorno.trataRetorno({codigo: 0, tipo: "SUCESSO", mensagem: "", idMsg: 'MA001'}, 'contrato');
						}
						
						reAgendar = true;
					}else{
						reAgendar = false;
					}
					
				},
				"error" : function(model, retorno) {
					Retorno.trataRetorno({codigo: -1, tipo: "ERROR", mensagem: "", idMsg: 'MA013'}, 'contrato');
					reAgendar = false;
				}
			});

			return reAgendar;
		},
	});
	
	return Avaliacao;

});