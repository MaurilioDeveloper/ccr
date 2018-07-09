define(['configuracoes'], 
function(config){
	
	var _this = null;
	var Log = Backbone.Model.extend({
	
		urlRoot: config['urlBaseRest'] + 'Administracao/log',
		
	    defaults: {
	    	
	    },
	
		initialize: function initialize() {

		},
		
	});
		
	return Log;
	
});