/**
 * @author F620600
 * 
 * JavaScript para o auditoria collection
 * 
 * @version 1.0.0.0
 * 
 * 
 */
define(['configuracoes', 
        'modelo/administracao/auditoria'], 
function(config, Auditoria){
	
	var _this = null;	
	var AuditoriaColecao = Backbone.Collection.extend({

		//url de para chamada do servico rest padrao
		urlRoot: config['urlBaseRest'] + 'auditoria',
		url: '',
		model: Auditoria,
		
		initialize : function () {
			console.log("Collection Auditoria - initialize");
		
		},
		
		listar: function(consulta){
			_this = this;
			_this.url = _this.urlRoot + "/lista";
						
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
	
	return AuditoriaColecao;

});