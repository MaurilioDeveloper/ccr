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
		urlRoot: config['urlBaseRest'] + 'Administracao/auditoria',
		url: '',
		model: Auditoria,
		
		initialize : function () {
			console.log("Collection Auditoria - initialize");
		
		},
		
		listar: function(nuContrato, cpf, cnpj, convenio, dtInicio, dtFim, usuario, transacao){
			_this = this;	
			
			
			var parameters = $.param({nuContrato: nuContrato, cpf: cpf, cnpj: cnpj, 
					convenio: convenio, dtInicio: dtInicio, dtFim: dtFim, usuario: usuario, transacao:transacao });
			
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
	
	return AuditoriaColecao;

});