/**
 * @author F620600
 * 
 * JavaScript para o alterar IOF
 *  
 * @version 1.0.0.0
 * 
 * 
 */
define(['configuracoes', 
        'modelo/contratacao/autorizar'], 
function(config, Autorizar){
	
	var _this = null;	
	var AutorizarColecao = Backbone.Collection.extend({

		//url de para chamada do servico rest padrao
		urlRoot: config['urlBaseRest'] + 'contratacao/autorizar',
		url: '',
		model: Autorizar,
		
		initialize : function () {
			console.log("Collection Autorizar - initialize");
		
		},
		
		listar: function(consulta){
			_this = this;
			_this.url = _this.urlRoot + "/lista";		
			
			return this.fetch({
				data: consulta, // consulta é um objeto javascript com os query parameters
				type: "GET",
			    contentType: "application/json"
			});
			
		},
		 
		parse: function(data){
		    return data["listaRetorno"];
		}
		
	});
	
	return AutorizarColecao;

});