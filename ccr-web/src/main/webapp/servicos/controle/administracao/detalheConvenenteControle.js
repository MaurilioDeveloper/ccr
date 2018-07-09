/**
 * @author F620600
 * 
 * JavaScript que controla as ações das paginas:
 *  detalheConvenente.html
 * @version 1.0.0.0
 * 
 * 
 */
define(['enumeracoes/eMensagemCCR',
        'enumeracoes/eTipoServicos',
        'util/retorno',
        'modelo/convenios/convenio',
        'modelo/cliente/cliente',
        'text!visao/administracao/detalheConvenente.html'
        ], 
        
function(EMensagemCCR, ETipoServicos, Retorno, Convenio, Cliente, detalheConvenenteTpl){

	var _this = null;
	var DetalheConvenenteControle = Backbone.View.extend({

		className: 'DetalheConvenenteControle',
		
		/**
		 * Templates
		 */
		detalheConvenente  : _.template(detalheConvenenteTpl),
		
		/**
		 * Variaveis da classe
		 */
		validator   : new Validators(),
		message     : new Message(),
		comando     : null,
		convenio     : null,
		modelo		: null,
		collection  : null,
		acao		: null,
		
		/**
		 * Função que faz bind das ações e interações da pagina com as funções
		 * javascript
		 * 
		 */
		events : {
			'click a#btnConsultarTaxaIOF' : 'consultarTaxaIOF',
			'click a#collapse1Link' : 'collapse1',
			'click a#collapse2Link' : 'collapse2',
			'click a#collapse3Link' : 'collapse3',
		},
		
		collapse1 : function() {
			
			if(document.getElementById("collapseOne").style.display == 'none'){
				document.getElementById("collapseOne").style.display = 'block';
			}else{
				document.getElementById("collapseOne").style.display = 'none';
			}
		},
		
		collapse2 : function() {
			if(document.getElementById("collapseTwo").style.display == 'none'){
				document.getElementById("collapseTwo").style.display = 'block';
			}else{
				document.getElementById("collapseTwo").style.display = 'none';
			}
			
		},
		
		collapse3 : function() {
			if(document.getElementById("collapse3").style.display == 'none'){
				document.getElementById("collapse3").style.display = 'block';
			}else{
				document.getElementById("collapse3").style.display = 'none';
			}
			
		},
				
		
		showAbranValues: function (param) {
			alert("teste: "+param);
		},
		/**
		 * Função padrão de incialização do template html
		 * 
		 */
		initialize : function() {
			console.log("detalheConvenente controle - initialize");

			// pra nao ter problema de pegar outro 'this'
			_this = this;		
			_this.acao = _this.options.acao || 'T';
			 
		},
		
		render : function(e) {
			
			console.log("Manter Convenente - render");	
			loadCCR.start();
			_this.modelo = new Convenio();
	 		
			_this.modelo.consultarConvenio(e)
			.done(function sucesso(data) {
				//Inclui o template inicial no el deste controle
				loadCCR.stop();
				
				// trta retorno em caso de erro
				if (data.mensagemRetorno == "MA004") {
					Retorno.trataRetorno({codigo: 1, tipo: "AVISO", mensagem: "", idMsg: 'MA004'}, 'convenio');
					return;
				}
				
				console.log("Manter Convenente - render:"+JSON.stringify(data));				
				_this.convenio = data;				
				
				//implementacao a conta corrente				
				//Agencia
				 var agencia = _this.convenio.numAgenciaContaCredito;
				 
				if(agencia != null && agencia.toString().length<4){
					 var adicionar = 4 - agencia.toString().length;
					 for (var i = 0; i < adicionar; i++) agencia = '0' + agencia;
						_this.convenio.numAgenciaContaCredito = agencia; 
				 }
				 //Operador
				 var operador = _this.convenio.numOperacaoContaCredito;
				 if(operador.toString().length<3){
					 var adicionar = 3 - operador.toString().length;
					 for (var i = 0; i < adicionar; i++) operador = '0' + operador;
						_this.convenio.numOperacaoContaCredito = operador; 
				 }
				 //ContaCorrente sem o digito
				 var conta = _this.convenio.numContaCredito;
				 if(conta.toString().length<8){
					 var adicionar = 8 - conta.toString().length;
					 for (var i = 0; i < adicionar; i++) conta = '0' + conta;
					_this.convenio.numContaCredito = conta; 
				 }
				 
				 _this.convenio.contaCorrente =	_this.convenio.numAgenciaContaCredito+"."+ 
												_this.convenio.numOperacaoContaCredito+"."+
												_this.convenio.numContaCredito+"-"+												
												_this.convenio.numDvContaCredito;
				 
				
				var cnpj = data.cnpjConvenente;
				
				
				

				_this.$el.html(_this.detalheConvenente({convenio: _this.convenio}));
				_this.loadFields(_this.convenio);	

					var abrangenciaObj = document.getElementById("abrangenciaList");
					
					//radioAbrangencia_1 - Nacional
	                //radioAbrangencia_2 - UF
	                //radioAbrangencia_3 - SR
					var abrangenciaList = [];
					
					if(_this.convenio.indAbrangencia == "2"){
						abrangenciaList = _this.convenio.abrangenciaUF;
						
						for(var i = 0; i < abrangenciaList.length; i++) {
						    var opt = document.createElement('option');
						    opt.innerHTML = abrangenciaList[i].sgUF;
						    opt.value = abrangenciaList[i].numConvenio;
						    abrangenciaObj.appendChild(opt);
						}
						document.getElementById("abrangenciaList").style.display = "block";
					}else if(_this.convenio.indAbrangencia == "3"){
						abrangenciaList = _this.convenio.abrangenciaSR;
						
						for(var i = 0; i < abrangenciaList.length; i++) {
						    var opt = document.createElement('option');
						    opt.innerHTML = abrangenciaList[i].unidade;
						    opt.value = abrangenciaList[i].numConvenio;
						    abrangenciaObj.appendChild(opt);
						}
						document.getElementById("abrangenciaList").style.display = "block";
					}else{
						document.getElementById("abrangenciaList").style.display = "none";
					}
					
					var cnpjList = document.getElementById("CNPJList");
					
					convenioCNPJVinculadoList = _this.convenio.convenioCNPJVinculado;
					
					for(var i = 0; i < convenioCNPJVinculadoList.length; i++) {
					    var opt = document.createElement('option');
					    opt.innerHTML = convenioCNPJVinculadoList[i].nuCNPJ;
					    opt.value = convenioCNPJVinculadoList[i].nuCNPJ;
					    cnpjList.appendChild(opt);
					}
				
				
				
				
					//BUSCA DADOS NO SICLI
//				$.when(_this.buscaDadosCliente(cnpj)).then(function(obj){
//					_this.cliente = obj;
//					
//					// validacao diferenciando o mock do sicli com o sicli
//					/*if (_this.cliente.razaoSocial == null) {
//						_this.cliente.razaoSocial = _this.cliente.nomeCliente;
//						_this.cliente.razaoSocial.razaoSocial = _this.cliente.razaoSocial.nome;
//					}*/
//					
//					//_this.convenio.indAbrangencia = "2";
//					_this.$el.html(_this.detalheConvenente({sicli:_this.cliente, convenio: _this.convenio}));
//					_this.loadFields(_this.convenio);	
//					
//					var abrangenciaObj = document.getElementById("abrangenciaList");
//					
//					//radioAbrangencia_1 - Nacional
//	                //radioAbrangencia_2 - UF
//	                //radioAbrangencia_3 - SR
//					var abrangenciaList = [];
//					
//					if(_this.convenio.indAbrangencia == "2"){
//						abrangenciaList = _this.convenio.abrangenciaUF;
//						
//						for(var i = 0; i < abrangenciaList.length; i++) {
//						    var opt = document.createElement('option');
//						    opt.innerHTML = abrangenciaList[i].sgUF;
//						    opt.value = abrangenciaList[i].numConvenio;
//						    abrangenciaObj.appendChild(opt);
//						}
//						document.getElementById("abrangenciaList").style.display = "block";
//					}else if(_this.convenio.indAbrangencia == "3"){
//						abrangenciaList = _this.convenio.abrangenciaSR;
//						
//						for(var i = 0; i < abrangenciaList.length; i++) {
//						    var opt = document.createElement('option');
//						    opt.innerHTML = abrangenciaList[i].unidade;
//						    opt.value = abrangenciaList[i].numConvenio;
//						    abrangenciaObj.appendChild(opt);
//						}
//						document.getElementById("abrangenciaList").style.display = "block";
//					}else{
//						document.getElementById("abrangenciaList").style.display = "none";
//					}
//					
//					var cnpjList = document.getElementById("CNPJList");
//					
//					convenioCNPJVinculadoList = _this.convenio.convenioCNPJVinculado;
//					
//					for(var i = 0; i < convenioCNPJVinculadoList.length; i++) {
//					    var opt = document.createElement('option');
//					    opt.innerHTML = convenioCNPJVinculadoList[i].nuCNPJ;
//					    opt.value = convenioCNPJVinculadoList[i].nuCNPJ;
//					    cnpjList.appendChild(opt);
//					}
//					//_this.$el.find('#divDetalhesConvenente').html(_this.manterConvenenteTemplate({sicli:_this.cliente, convenio:_this.convenio}));
//					//_this.$el.html(_this.detalheConvenente({sicli:_this.cliente, convenio: _this.convenio}));
//				});
				
				
			})
			.fail(function erro(jqXHR) {
				(Alert("Errro"));
			}); 
			return _this;
		},		
		loadFields: function (data) {
			
			//var i = 0;
			for(var i =0; i<data.canais.length;i++){
				var canal = data.canais[i];
				
				var indPermiteContratacao = canal.indPermiteContratacao;
				var indPermiteRenovacao = canal.indPermiteRenovacao;
				var indExigeAutorizacaoContrato =  canal.indAutorizaMargemContrato;
				var indExigeAnuencia = canal.indExigeAnuencia;
				var indAutorizaMargemRenovacao = canal.indAutorizaMargemRenovacao;
				var indSituacaoConvenioCanal = canal.indSituacaoConvenioCanal;
				
				document.getElementById("radioPermiteContratacao_"+data.canais[i].canal.id+"_"+indPermiteContratacao+"").checked = "true";					
				document.getElementById("radioPermiteRenovacao_"+data.canais[i].canal.id+"_"+indPermiteRenovacao+"").checked = "true";	
				document.getElementById("radioExigeAutorizacaoContratacao_"+data.canais[i].canal.id+"_"+indExigeAutorizacaoContrato+"").checked = "true";
				document.getElementById("radioExigeAnuencia_"+data.canais[i].canal.id+"_"+indExigeAnuencia+"").checked = "true";
				document.getElementById("radioExigeAutorizacaoRenovacao_"+data.canais[i].canal.id+"_"+indAutorizaMargemRenovacao+"").checked = "true";
				document.getElementById("radioSituacaoConvenioCanal_"+data.canais[i].canal.id+"_"+indSituacaoConvenioCanal+"").checked = "true";
				
			}
				
			document.getElementById("radioAbrangencia_"+data.indAbrangencia).checked = "true";
			
		},
		sair: function () {
			loadCCR.start();
			console.log("saindo do CCR...");
			window.location = 'index.html';
		},
					
		
		voltar: function () {						
			_this.limparForm();
			_this.comando = null;
			
			_this.$el.find('#divFormulario').modal('hide');
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
			/*cnpj = cnpj.replace(/[_.\-\/]/g, '');*/
			
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
								_this.agendador4(6, 2000,correlationId);						
							},2000);
						}else{
							_this.clienteDC = dataRet.dados;
							//return _this.cliente;
							prm.resolve(_this.clienteDC);						
						}
					}else{						
						if (dataRet.dados.responseArqRef.status.codigo ==0){
							_this.clienteDC = dataRet.dados;
							//return _this.cliente;
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
		
		agendador4 : function (tentativas, atraso, correlationId){
			console.log("Cliente - agendador");
			console.log(new Date());
			console.log("tentativas -- > " + tentativas);
			loadCCR.start();
			var that = this;
			if(tentativas > 0){
				//recebe o resultado da consulta cliente e verifica
				//se havera reexcucao
				
				return _this.modelo.consultarCorrelationId(correlationId).done(function sucesso(dataRet) {
					
					if (dataRet.dados==null){						
					
							console.log("reexcutar");						
							tentativas = tentativas - 1;
							console.log("executando o agendador: "+tentativas);
							_this.chamaAgendador4(tentativas, atraso, correlationId);													
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
						//_this.consultarCNPJ_render(dataRet.dados);	
						return;													
					}
					
				})
				.error(function erro(jqXHR) {
					_this.gestor  = "false";
				});			
				
			}

		},
		
		chamaAgendador4: function (tentativas, atraso, correlationId) {
			setTimeout(function(){
				_this.agendador4(tentativas, atraso, correlationId);						
			},atraso);
		}
	 
	});

	return DetalheConvenenteControle;
	
});