// Filename: router.js
define([
], function(){
  var AppRouter = Backbone.Router.extend({
    routes: {
      // Define some URL routes
    	
      'consignado/consultarMargem': 'consultarMargem',
      'consignado/simularConsignado': 'simularConsignado',
//      'consignado/simularConsignadoInternet': 'simularConsignadoInternet',
      'consignado/contratarConsignado': 'contratarConsignado',
      'consignado/cancelarConsignado': 'cancelarConsignado',
      'consignado/renovarConsignado': 'renovarConsignado',
    	  
    },
    
    /**
     * Rota para a consulta de margem
     */
    consultarMargem: function(){
    	console.log('router - consultaMargem - ini');
    	//carraga o load da pagina
		carregar.iniciar();
		//Carrega o controlador e renderiza
    	requireAPX(['controle/consignado/MargemConsignadoControle'], 
			function(MargemControle){
				var margemControle = new MargemControle();
				$('#container').html(margemControle.render().el);
				
				//Inclusão das máscaras
				mascaras();
				
				//finaliza o load a pagina
				carregar.finalizar(); 
			},
			//Se ocorrer erro está função será executada
			function(error){
				console.error(error);
				//finaliza o load a pagina
				carregar.finalizar(); 
			}
		);
		
		console.log('router - consultaMargem - end');
    },
    
    /**
     * Rota para a simulação
     */
    simularConsignado: function(){
    	console.log('router - simularConsignado - ini');
    	
    	//carrega o load da pagina
		carregar.iniciar();
		
		//Carrega o controlador e renderiza
    	requireAPX(['controle/consignado/SimularConsignadoControle'], 
			function(SimularConsignadoControle){
    		console.log('Iniciando Controle da simulação');
				var simularConsignadoControle = new SimularConsignadoControle({
					//Apresentação com seguro e operação
					seguro : true, operacao: true
				});
				$('#container').html(simularConsignadoControle.render().el);
				
				//Inclusão das máscaras
				mascaras();
				
				//finaliza o load a pagina
				carregar.finalizar(); 
				console.log('Finalizando Controle da simulação');
			},
			//Se ocorrer erro está função será executada
			function(error){
				console.error(error);
				//finaliza o load a pagina
				carregar.finalizar(); 
			}
    	
		);
		
		console.log('router - simularConsignado - end');
    },
    
    /**
     * Rota para a contratação
     */
    contratarConsignado: function(){
    	console.log('router - contratarConsignado - ini');
		
		//Carrega o controlador e renderiza
    	requireAPX(['controle/consignado/WizardConsignadoControle'], 
			function(WizardConsignadoControle){
				var wizardConsignadoControle = new WizardConsignadoControle();
				$('#container').html(wizardConsignadoControle.render().el);
			},
			//Se ocorrer erro está função será executada
			function(error){
				console.error(error);
				//finaliza o load a pagina
				carregar.finalizar(); 
			}
		);
		
		console.log('router - contratarConsignado - end');
    },
    
    /**
     * Rota para o cancelamento de contratação
     */
    cancelarConsignado: function(){
    	console.log('router - cancelarConsignado - ini');
		
		/*//Carrega o controlador e renderiza
    	requireAPX(['controle/consignado/WizardConsignadoControle'], 
			function(WizardConsignadoControle){
				var wizardConsignadoControle = new WizardConsignadoControle();
				$('#container').html(wizardConsignadoControle.render().el);
			},
			//Se ocorrer erro está função será executada
			function(error){
				console.error(error);
				//finaliza o load a pagina
				carregar.finalizar(); 
			}
		);*/
    	
    	//Carregando o controlador do convenio
		$.ajax({
			url: "../apx-web/servicos/consignado/controle/cancelarConsignadoControle.js",
			async: false,
			context: document.body
		}).done(function(data) {
			
		});
		this.cancelarConsignadoControle = new CancelarConsignadoControle({
			el : $("#container")
		});		

		
		console.log('router - cancelarConsignado - end');
    },
    /**
     * Rota para a renovação
     */
    renovarConsignado: function(){
    	console.log('router - renovarConsignado - ini');
		
		//Carrega o controlador e renderiza
    	requireAPX(['controle/consignado/WizardRenovarConsignadoControle'], 
			function(WizardRenovarConsignadoControle){
				var wizardRenovarConsignadoControle = new WizardRenovarConsignadoControle();
				$('#container').html(wizardRenovarConsignadoControle.render().el);
			},
			//Se ocorrer erro está função será executada
			function(error){
				console.error(error);
				//finaliza o load a pagina
				carregar.finalizar(); 
			}
		);
		
		console.log('router - renovarConsignado - end');
    }
    //TODO - implementar os demais routers
  });
  
  return AppRouter;
});