/**
 * @author c110503
 * 
 * JavaScript para o obejto Usuario
 * representa o usuario logado no sistema
 * 
 * @version 1.0.0.0
 * 
 * 
 */
var Usuario = Backbone.RelationalModel.extend({

	//url de para chamada do servico rest padrao
	urlRoot: '/',
	/***
	 * Criacao de valores padroes
	 */
    defaults : {   	
    	
    },
    
    /**
	 * Função padrão de incialização do modelo
	 */
	initialize : function () {
		console.log("Usuario - initialize");
				
	}	
	
});
