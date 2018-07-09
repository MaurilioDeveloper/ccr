/**
 * @author F620600
 * 
 * JavaScript para o alterar IOF
 * 
 * @version 1.0.0.0
 * 
 * 
 */
define([], 
function(){
	
	var simulacaoAberta = Backbone.RelationalModel.extend({
		//url de para chamada do servico rest padrao
		urlRoot: '/',
	
	    defaults : {
	    	
	    },
	    
		initialize : function () {
			console.log("Alterar IOF - initialize");
			//variavel auxiliar para uso em funcoes internas
			var that = this;
			
			//array de validadores
			this.validador = {};
			     	
		},
		
		validaAtributo: function (atributo) {
			console.log("Margem - validaAtributo");
			console.log(atributo);
			console.log(this);
	        return (this.validador[atributo]) ? this.validador[atributo](this.get(atributo)) : {isValid: true};
	    },
	    
		
	});
	
	return simulacaoAberta;

});