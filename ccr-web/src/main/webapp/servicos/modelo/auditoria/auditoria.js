define(['configuracoes'], 
function(config){
	
	var _this = null;
	var Auditoria = Backbone.Model.extend({
	
		urlRoot: config['urlBaseRest'] + 'Administracao/auditoria',
		
	    defaults: {
	    	
	    },
	
		initialize: function initialize() {

		},
		
	});
		
	return Auditoria;
	
});