/**
 * @author F620600
 * 
 * JavaScript para o log collection
 * 
 * @version 1.0.0.0
 * 
 * 
 */
define(['configuracoes', 
        'modelo/administracao/log'], 
function(config, Log){
	
	var _this = null;	
	var LogColecao = Backbone.Collection.extend({

		//url de para chamada do servico rest padrao
		urlRoot: config['urlBaseRest'] + 'Administracao/log',
		url: '',
		model: Log,
		
		initialize : function () {
			console.log("Collection Log - initialize");
		
		},
		
		listar: function(inicio, fim, transacao){
			_this = this;
			
			fim = (fim == '' ? preparaDataBD(inicio) : preparaDataBD(fim))+ ' 23:59:59';
			inicio = preparaDataBD(inicio) + ' 00:00:00';			
			
			var parameters = $.param({dataInicio: inicio, dataFim: fim, transacao: transacao});
			_this.url = _this.urlRoot + "/lista?" + parameters;
						
			return this.fetch({
			    type: "GET",
			    contentType: "application/json"
			});
			
		},
		
		parse: function(data){
		    return data["listaRetorno"];
		}
		
	});
	
	return LogColecao;

});