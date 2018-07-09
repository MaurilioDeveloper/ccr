define(['configuracoes', 
        'modelo/contratacao/autorizar'], 
function(config){
	
	var _this = null;	
	var ContratoColecao = Backbone.Collection.extend({

		//url de para chamada do servico rest padrao
		urlRoot: config['urlBaseRest'] + 'contratacao',
		url: '',
		
		initialize : function () {
			console.log("Collection Autorizar - initialize");
		
		},
		
		listar: function(consulta){
			_this = this;
			_this.url = _this.urlRoot + "/listaContrato";		
			
			return this.fetch({
				data: consulta, 
				type: "GET",
			    contentType: "application/json"
			});
			
		},
		 
		parse: function(data){
		    return data["listaRetorno"];
		}
		
	});
	
	return ContratoColecao;

});