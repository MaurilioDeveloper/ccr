var DashboardControleCCR = {

	dashContainer: $('#container'),
	
	voltarCovenioIniDashboardControleCCR: function(e) {
		console.log('Inicio - enviarLiberacao');
		var that = this;
		
		// Carrega o controlador e renderiza
		requireCCR([ 'controle/administracao/manterConvenenteControle' ], 
			function(manterConvenenteControle) {
			
			var liberacaoControl = new manterConvenenteControle();
			that.dashContainer.html(liberacaoControl.render().el);
		},
		// Se ocorrer erro está função será executada
		function(error) {
			console.error(error);
		});
		//return true;
	},
	
	renderDashboardControleCCR: function(e) {
		console.log('Inicio - enviarLiberacao');
		var that = this;
		
		// Carrega o controlador e renderiza
		requireCCR([ 'controle/administracao/detalheConvenenteControle' ], 
				function(detalheConvenenteControle) {
			
			var liberacaoControl = new deaaatalheConvenenteControle();
			that.dashContainer.html(liberacaoControl.render(e).el);
		},
		// Se ocorrer erro está função será executada
		function(error) {
			console.error(error);
		});
		//return true;
	},
	/**
	 * Rota para enviar liberação
	 */
	detalharConvenenteControle : function(e) {
		console.log('Inicio - enviarLiberacao');
		var that = this;
		
		// Carrega o controlador e renderiza
		requireCCR([ 'controle/administracao/detalheConvenenteControle' ], 
			function(detalheConvenenteControle) {
			
			var liberacaoControl = new detalheConvenenteControle();
			that.dashContainer.html(liberacaoControl.render(e).el);
		},
		// Se ocorrer erro está função será executada
		function(error) {
			console.error(error);
		});

		console.log('Fim - enviarLiberacao');
	},
	    
		
	/**
	 * Rota para manter taxa IOF
	 */
	manterTaxaIOF: function(e){
    	console.log('Inicio - manterTaxaIOF');
    	var that = this;
    	
		//Carrega o controlador e renderiza
		requireCCR(['controle/taxas/manterIOFControle'], 
			function(manterIOFControle){
				var controlIOF = new manterIOFControle({acao: 'T'});
				that.dashContainer.html(controlIOF.render().el);
			},
			//Se ocorrer erro está função será executada
			function(error){
				console.error(error);					  
			}
		);
		
		console.log('Fim - manterTaxaIOF');
    },
    
    manterGrupoTaxa: function(e){
    	console.log('Inicio - manterGrupoTaxa');
    	var that = this;
    	
		//Carrega o controlador e renderiza
		requireCCR(['controle/taxas/manterGrupoTaxaControle'], 
			function(manterGrupoTaxaControle){
				var controlGrupoTaxa = new manterGrupoTaxaControle({acao: 'T'});
				that.dashContainer.html(controlGrupoTaxa.render().el);
			},
			//Se ocorrer erro está função será executada
			function(error){
				console.error(error);					  
			}
		);
		
		console.log('Fim - manterTaxaIOF');
    },
    
    
    /**
     * Rota para manter taxa de juros
     */
    manterTaxaJuros: function(e){
    	console.log('Inicio - manterTaxaJuros');
    	var that = this;
    	
		//Carrega o controlador e renderiza
		requireCCR(['controle/taxas/manterJurosControle'], 
			function(filtroTaxaControle){
				var controlJuros = new filtroTaxaControle({acao: 'T'});
				that.dashContainer.html(controlJuros.render().el);
			},
			//Se ocorrer erro está função será executada
			function(error){
				console.error(error);
			}
		);
		
		console.log('Fim - manterTaxaJuros');
    },
    
    /**
     * Rota para manter taxa seguro
     */
    manterTaxaSeguro: function(e){
    	console.log('Inicio - manterTaxaSeguro');
    	var that = this;
    	
		//Carrega o controlador e renderiza
		requireCCR(['controle/taxas/manterSeguroControle'], 
			function(manterSeguroControle){
				var controlSeguro = new manterSeguroControle({acao: 'T'});
				that.dashContainer.html(controlSeguro.render().el);
			},
			//Se ocorrer erro está função será executada
			function(error){
				console.error(error);
			}
		);
		
		console.log('Fim - manterTaxaSeguro');
    },
    
    /**
     * Rota para manter convenente
     */
    manterConvenente: function(e) {
    	console.log('Inicio - manterConvenente');
    	var that = this;
    	
		//Carrega o controlador e renderiza
		requireCCR(['controle/administracao/manterConvenenteControle'], 
			function(manterConvenente){
				var controlConvenente = new manterConvenente({acao: 'T'});
				that.dashContainer.html(controlConvenente.render().el);
			},
			//Se ocorrer erro está função será executada
			function(error){
				console.error(error);
			}
		);
		
		console.log('Fim - manterConvenente');
    },
    
    /**
     * Rota para simular consignado
     */
    simularCreditoConsignado: function() {
    	console.log('Inicio - simularConsignado');
    	var that = this;
		
		//Carrega o controlador e renderiza
		requireCCR(['controle/contratacao/simularConsignadoControle'], 
			function(manterConvenente){
				var controlConvenente = new manterConvenente();
				that.dashContainer.html(controlConvenente.render().el);
			},
			//Se ocorrer erro está função será executada
			function(error){
				console.error(error);
			}
		);
		
		console.log('Fim - simularConsignado');
    },
    
    /**
     * Rota para simular aberto
     */
    simularAberto: function () {
    	console.log('Inicio - simularAberto');
    	var that = this;
		
		//Carrega o controlador e renderiza
		requireCCR(['controle/contratacao/simularAbertoControle'], 
			function(simularAberto){
				var controlSimular = new simularAberto();
				that.dashContainer.html(controlSimular.render().el);
			},
			//Se ocorrer erro está função será executada
			function(error){
				console.error(error);
			}
		);
		
		console.log('Fim - simularAberto');
    }, 
    
    /**
     * Rota para estornar contrato
     */
    estornoContrato: function () {
    	console.log('Inicio - estornarContrato');
    	var that = this;
    	
		//Carrega o controlador e renderiza
		requireCCR(['controle/contratacao/estornoControle'], 
			function(estornarContrato){
				var controlEstorno = new estornarContrato();
				that.dashContainer.html(controlEstorno.render().el);
			},
			//Se ocorrer erro está função será executada
			function(error){
				console.error(error);
			}
		);
		
		console.log('Fim - simularAberto');
    },
    
    consultarContrato: function () {
    	console.log('Inicio - estornarContrato');
    	var that = this;
    	
		//Carrega o controlador e renderiza
		requireCCR(['controle/contratacao/situacaoContratoControle'], 
			function(consultarContrato){
				var controlConsultarContr = new consultarContrato();
				that.dashContainer.html(controlConsultarContr.render().el);
			},
			//Se ocorrer erro está função será executada
			function(error){
				console.error(error);
			}
		);
		
		console.log('Fim - simularAberto');
    },
    
    manterContrato: function () {
		console.log('Inicio - manterContrato');
		var that = this;
		
		//Carrega o controlador e renderiza
		requireCCR(['controle/contratacao/manterContratoControle'], 
			function(ManterContratoControle){
				var manterContratoControle = new ManterContratoControle();
				that.dashContainer.html(manterContratoControle.render().el);
			},
			//Se ocorrer erro está função será executada
			function(error){
				console.error(error);
			}
		);
		
		console.log('Fim - manterContrato');
    },
    
    consultarArquivo: function () {
		console.log('Inicio - consultarArquivoControle');
		var that = this;
		
		//Carrega o controlador e renderiza
		requireCCR(['controle/arquivo/consultaArquivoControle'], 
			function(ConsultaArquivoControle){
				var consultaArquivoControle = new ConsultaArquivoControle();
				that.dashContainer.html(consultaArquivoControle.render().el);
			},
			//Se ocorrer erro está função será executada
			function(error){
				console.error(error);
			}
		);
		
		console.log('Fim - consultarArquivoControle');
    },
    
    
    gerenciadorProcesso : function() {
    	console.log('Inicio - agendadorEnvioContrato');
		var that = this;
		
		//Carrega o controlador e renderiza
		requireCCR(['controle/agendadorContrato/agendadorContratoControle'], 
			function(AgendadorContratoControle){
				var agendadorContratoControle = new AgendadorContratoControle();
				that.dashContainer.html(agendadorContratoControle.render().el);
			},
			//Se ocorrer erro está função será executada
			function(error){
				console.error(error);
			}
		);
		
		console.log('Fim - agendadorEnvioContrato');
    },
    
    
    historicoEstorno: function () {
    	console.log('Inicio - historicoEstorno');
    	var that = this;
    	
		//Carrega o controlador e renderiza
		requireCCR(['controle/contratacao/historicoEstornoControle'], 
			function(HistoricoEstornoControle){
				var historicoEstornoControle = new HistoricoEstornoControle();
				that.dashContainer.html(historicoEstornoControle.render().el);
			},
			//Se ocorrer erro está função será executada
			function(error){
				console.error(error);
			}
		);
		
		console.log('Fim - historicoEstorno');
    },
    
    consultarAuditoria: function () {
    	console.log('Inicio - consultarAuditoria');
    	var that = this;
    	
		//Carrega o controlador e renderiza
		requireCCR(['controle/auditoria/consultaAuditoriaControle'], 
			function(ConsultaAuditoriaControle){
				var consultaAuditoriaControle = new ConsultaAuditoriaControle();
				that.dashContainer.html(consultaAuditoriaControle.render().el);
			},
			//Se ocorrer erro está função será executada
			function(error){
				console.error(error);
			}
		);
		
		console.log('Fim - consultarAuditoria');
    },
    
    consultarLog: function () {
    	console.log('Inicio - consultarLog');
    	var that = this;
    	
		//Carrega o controlador e renderiza
		requireCCR(['controle/administracao/consultaLogControle'], 
			function(ConsultaLogControle){
				var consultaLogControle = new ConsultaLogControle();
				that.dashContainer.html(consultaLogControle.render().el);
			},
			//Se ocorrer erro está função será executada
			function(error){
				console.error(error);
			}
		);
		
		console.log('Fim - consultarLog');
    },
    
    
    /**
     * Rota para o Wizard do Contratar Consignado - Init
     */
    contratarConsignado: function(e, etapa){
    	console.log('Inicio - contratarConsignado');
    	var that = this;
    	
    	//Carrega o controlador e renderiza
		requireCCR(['controle/contratacao/wizardConsignadoControle'], 
    			function(wizardConsignado){
    				var controlWizardConsignado = new wizardConsignado({});
    				that.dashContainer.html(controlWizardConsignado.render(e,etapa).el );
    				
    			},
    			//Se ocorrer erro está função será executada
    			function(error){
    				console.error(error);
    			}
    		);
		console.log('Fim - contratarConsignado');
    },
    
    /**
     * Rota para o Wizard do Contratar Consignado - Etapa 01 - Simular
     */
    contratarConsignadoConsultaCliente: function(e){
    	console.log('Inicio - contratarConsignadoConsultaCliente');
    	var that = this;
    	
    	//Carrega o controlador e renderiza
		requireCCR(['controle/cliente/consultaClienteControle'], 
    			function(consultaCliente){
    				var controlConsultaCliente = new consultaCliente({});
    				that.dashContainer.html(controlConsultaCliente.render().el);
    			},
    			//Se ocorrer erro está função será executada
    			function(error){
    				console.error(error);
    			}
    		);  
		
		console.log('Fim - contratarConsignadoConsultaCliente');
    },
    
    /**
     * Rota para o Wizard do Contratar Consignado - Etapa 02 - Avaliar
     */
    contratarConsignadoAvaliar: function(e){
    	console.log('Inicio - contratarConsignadoAvaliar');
    	var that = this;
    	
    	//Carrega o controlador e renderiza
		requireCCR(['controle/cliente/consultaClienteAvaliar'], 
    			function(clienteAvaliar){
    				var controlClienteAvaliar = new clienteAvaliar({});
    				that.dashContainer.html(controlClienteAvaliar.render().el);
    			},
    			//Se ocorrer erro está função será executada
    			function(error){
    				console.error(error);
    			}
    		);  
		
		console.log('Fim - contratarConsignadoAvaliar');
    },
    
    /**
     * Rota para o Wizard do Contratar Consignado - Etapa 03 - Contratar
     */
    contratarConsignadoContratar: function(e){
    	console.log('Inicio - contratarConsignadoContratar');
    	var that = this;
    	
    	//Carrega o controlador e renderiza
		requireCCR(['controle/contratacao/contratarConsignadoContratar'], 
    			function(consignadoContratar){
    				var controlConsignadoContratar = new consignadoContratar({});
    				that.dashContainer.html(controlConsignadoContratar.render().el);
    			},
    			//Se ocorrer erro está função será executada
    			function(error){
    				console.error(error);
    			}
    		);  
		
		console.log('Fim - contratarConsignadoContratar');
    },
    
    /**
     * Rota para o Wizard do Contratar Consignado - Etapa 04 - Autorizar
     */
    contratarConsignadoAutorizar: function(e){
    	console.log('Inicio - contratarConsignadoAutorizar');
    	var that = this;
    	
    	//Carrega o controlador e renderiza
		requireCCR(['controle/contratacao/contratarConsignadoAutorizar'], 
    			function(consignadoAutorizar){
    				var controlConsignadoAutorizar = new consignadoAutorizar({});
    				that.dashContainer.html(controlConsignadoAutorizar.render().el);
    			},
    			//Se ocorrer erro está função será executada
    			function(error){
    				console.error(error);
    			}
    		);  
		
		console.log('Fim - contratarConsignadoAutorizar');
    },
    
    /**
     * Rota para Manter Documento
     */
    manterDocumento: function(e) {
    	console.log('Inicio - manterDocumento');
    	var that = this;
		//Carrega o controlador e renderiza
		requireCCR(['controle/documento/manterDocumentoControle'], 
			function(manterDocumento){
				var controlDocumento = new manterDocumento({acao: 'T'});
				that.dashContainer.html(controlDocumento.render().el);
			},
			//Se ocorrer erro está função será executada
			function(error){
				console.error(error);
			}
		);
		
		console.log('Fim - manterDocumento');
    },
    
    /**
     * Rota para Detalhar Contrato
     */
    detalharContrato: function(e, agenciaConcessora) {
    	console.log('Detalhe - manterContrato');
    	var that = this;
		//Carrega o controlador e renderiza
		requireCCR(['controle/contratacao/detalheContratoControle'], 
			function(detalheContrato){
				var detalheContrato = new detalheContrato();
				that.dashContainer.html(detalheContrato.render(e, agenciaConcessora).el);
			},
			//Se ocorrer erro está função será executada
			function(error){
				console.error(error);
			}
		);
		
		console.log('Fim - detalhe manterDocumento');
    },
    
    gerenciadorLimites: function(e) {
    	console.log('Detalhe - manterContrato');
    	var that = this;
		//Carrega o controlador e renderiza
		requireCCR(['controle/contratacao/gerenciadorLimitesControle'], 
			function(gerenciadorLimites){
				var gerenciadorLimites = new gerenciadorLimites();
				that.dashContainer.html(gerenciadorLimites.render().el);
			},
			//Se ocorrer erro está função será executada
			function(error){
				console.error(error);
			}
		);
		
		console.log('Fim - detalhe manterDocumento');
    },

};