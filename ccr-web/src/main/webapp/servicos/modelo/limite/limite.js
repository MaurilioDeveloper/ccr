define(['configuracoes'], 
function(config){
	
	var _this = null;
	var Limite = Backbone.Model.extend({
	
		urlRoot: config['urlBaseRest'] + 'gerenciadorLimite',
		
	    defaults: {
	    	
	    },
	
		initialize: function initialize() {

		},
		consultar: function(consulta){ 	
			
			_this = this;			
			_this.url = _this.urlRoot + "/manter";
			
			return _this.fetch({
			    type: "POST",
			    contentType: "application/json",
			    data: JSON.stringify(consulta)
			});
//			return this.fetch({
//				data: consulta, 
//				type: "GET",
//			    contentType: "application/json"
//			});
			
		},
		
	});
		
	return Limite;
	
});