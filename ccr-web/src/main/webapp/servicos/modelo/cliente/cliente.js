/**
 * @author F620600
 * @version 1.0.0.0
 *  
 */
define(['configuracoes',
        'enumeracoes/eMensageria',
        'modelo/convenios/convenio',
        'modelo/Mensageria', 
        'util/retorno'], 
function(config, EMensageria, Convenio, Mensageria, Retorno){
	
	var _this = null;
	var Cliente = Backbone.RelationalModel.extend({
		//url de para chamada do servico rest padrao
		urlRoot: config['urlBaseRest'] + 'cliente/',
	
		mensageria : undefined,
		convenio : undefined,
		
		/***
		 * Criacao de valores padroes
		 */
	    defaults : {
	    	nomeCliente:{
	    		nome:"",
	    		cpf: 0
	    	},
	    	razaoSocial:{
	    		razaoSocial:""
	    	},
	    	enderecoNacional: [{
	    		nomeMunicipio: "",	   		
	    		logradouro: "",	
	    		uf: "",	
    			numero: "",	
    			cep: "",	
    			complemento: ""	
    		}], 
    		contasCorrentesLst: [],
	    },
	    
	    tipos : {
	    	nome: 'CHAR',
	    	cpf: 'CPF',
	    },
	    
	    /**
		 * Função padrão de incialização do modelo
		 * 
		 * @TODO
		 * 	
		 */
		initialize : function () {
			console.log("Consulta cliente - initialize");
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
				_this.listenTo(_this.mensageria, EMensageria.NAO_RECEBIDO, this.naoRecebido);
				_this.listenTo(_this.mensageria, 'error', _this.erro);
			}
			return this.mensageria;
		},
	    
		consultar: function(){
			
			//Define os dados de envio
			_this.getMensageria().set('dados', {
					tipoPesquisa : 'CPF',
					cpf: this.get('cpf')				
				}
			);
			
			//Realiza a requisicao ao modelo de mensageria
			_this.getMensageria().requisicao(_this.urlRoot + 'consultar', 'consultar', 5, 1000);
		},
		
		/**
		 * Alteração de dados SICLI
		 */
		alterarDadosPF : function(modelo) {
			console.log('Cliente - alterarDadosPF - ini', modelo);
			
			_this = this;			
			_this.url = _this.urlRoot + "alterarDadosPF";
			
			// usar sync pois o save e o fetch adicionam a classe Retorno na model e nem voce e nem eu queremos isso 
			return this.fetch({
			    type: "POST",
			    contentType: "application/json",
			    data:JSON.stringify(modelo),
			    async : false
			});
			
		},
		
		/**
		 * A ser executada apenas quando o evento do modelo de mensageria 'recebido' for acionado
		 */
		recebido : function(modelo){
			console.log('Cliente - recebido - ini', modelo);
			
			//Aciona os listeners do evento referente a operaÃ§Ã£o que estÃ¡ sendo executada
			_this.trigger(modelo.eventoSucesso, modelo.get('dados'));
		},
		
		/**
		 * 
		 */
		naoRecebido : function(modelo){
			console.log('Cliente - naoRecebido - ini');
			
			//Aciona os listeners do evento naoRecebido
			_this.trigger(EMensageria.NAO_RECEBIDO, _this);
		},
		
		/**
		 * Tratamento de erros neste modelo.
		 */
		erro : function(modelo, xhr, request){
			console.log('Cliente - erro - ini');
			
			//Repassa o erro para quem estiver escutando este modelo
			_this.trigger('error', _this, xhr, request);
		},
		
		consultarPF: function (cpf) {
	    	_this = this;
	    	_this.url = _this.urlRoot + "consultarPF?cpf="+ cpf;
	    	config.preparaAtributos(cpf, _this.tipos);

	    	return _this.fetch({
			    type: "GET",
			    contentType: "application/json"
			});
		},
		
		consultarCNPJ : function (cnpj) {
			
			console.log("Cliente.js - Agendador - cnpj: "+cnpj);
			
			_this.modelo = new Convenio();
			var prm = $.Deferred();
		
			//Chamada do SICLI - busca correlationId
			_this.modelo.consultar(cnpj).done(function sucesso(data) {
				console.log(data)
				var correlationId = data.correlationId;
				console.log(correlationId);
				
				//Chamada do SICLI - com o correlationId busca os dados no fila de response
				_this.modelo.consultarCorrelationId(correlationId).done(function sucesso(dataRet) {	
					
					if (dataRet.dados.responseArqRef.status.codigo ==null){
						if (dataRet.dados==null || dataRet.dados.cocliAtivo==null){						
							console.log("Chama o agendador - Cliente.js");						
							//Chama o agendador
							setTimeout(function(){
								
								_this.agendador(6, 2000,correlationId);
								prm.resolve(dataRet);
							},2000);
						}else{
							//return dataRet.dados;
							prm.resolve(dataRet);
							
						}
					}else{						
						if (dataRet.dados.responseArqRef.status.codigo ==0){
							//return dataRet.dados;
							prm.resolve(dataRet);
						}else{
							//Retorno.validarRetorno(dataRet);
							//loadCCR.stop();
							//return;
							prm.resolve(dataRet);
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
			
			return prm.promise();
		
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
			_this.modelo = new Convenio();
			console.log("Cliente - agendador - Cliente.js");
			console.log(new Date());
			console.log("tentativas -- > " + tentativas);
			loadCCR.start();
			var that = this;
			if(tentativas > 0){
				//recebe o resultado da consulta cliente e verifica
				//se havera reexcucao
				
				_this.modelo.consultarCorrelationId(correlationId).done(function sucesso(dataRet) {					
					
					/*if (tentativas==1){							
						Retorno.validarRetorno(dataRet);
							loadCCR.stop();
							return;										
					}*/
					
					if (dataRet.dados==null || dataRet.dados.cocliAtivo==null){						
					
							console.log("reexcutar");						
							tentativas = tentativas - 1;
							console.log("executando o agendador: "+tentativas);
							_this.chamaAgendador(tentativas, atraso, correlationId);
//							Retorno.trataRetorno({codigo: 0, tipo: "", mensagem: "Não houve retorno do cadastro do cliente. Tente novamente."}, 'contratar');
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
					
						//Retorno.validarRetorno(dataRet);
						loadCCR.stop();
						//_this.consultarCNPJ_render(dataRet.dados);
						Retorno.trataRetorno({codigo: 0, tipo: "", mensagem: "Não houve retorno do cadastro do cliente. Tente novamente."}, 'contratar');
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
		
		consultarDadosSicliPorCPF : function (cpf) {			
			console.log("Cliente.js - consultarDadosSicliPorCPF: "+cpf);
		
			var prm = $.Deferred();
			var cpfFormatado=config.formata(cpf, 'CPF');
			//Chamada do SICLI - busca correlationId
			_this.consultarCorrelationIDDadosClientePF(cpf).done(function sucesso(data) {
				console.log(data)
				var correlationId = data.correlationId;
				console.log(correlationId);
				
				//Chamada do SICLI - com o correlationId busca os dados no fila de response
				_this.consultarDadorClientePorCorrelationId(correlationId).done(function sucesso(dataRet) {	
					
					if (dataRet.dados.responseArqRef.status.codigo ==null){
						if (dataRet.dados==null || dataRet.dados.cocliAtivo==null){						
							console.log("Chama o agendador - Cliente.js");						
							setTimeout(function(){
								_this.agendador(6, 2000,correlationId);						
							},2000);
						}else{
							prm.resolve(dataRet);
						}
					}else{						
						if (dataRet.dados.responseArqRef.status.codigo ==0){
							prm.resolve(dataRet);
						}else{
							prm.resolve(dataRet);
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
			
			return prm.promise();
		
		},
		
		consultarCorrelationIDDadosClientePF: function (cpfCliente) {
	    	_this = this;
	    	var parameters = $.param({cpf : cpfCliente});
	    	_this.url = _this.urlRoot +  "consultarCorrelationClientePorCPF?" + parameters;

	    	return _this.fetch({
			    type: "GET",
			    contentType: "application/json"
			});
	    		    	
		},
		
		consultarDadorClientePorCorrelationId: function (correlationId) {
	    	_this = this;
	    	console.log('correlation para chamada : '+ correlationId)
	    	_this.url = _this.urlRoot + "consultarCorrelationIdPF?correlationId="+correlationId;
	    
	    	return _this.fetch({
			    type: "GET",
			    contentType: "application/json"
			}); 	
	    	
	    	
		},
		
	});
	
	return Cliente;

});