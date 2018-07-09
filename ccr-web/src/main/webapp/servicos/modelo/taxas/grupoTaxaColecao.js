/**
 * @author F620600
 * 
 * JavaScript para o listar IOF
 * 
 * @version 1.0.0.0
 * 
 * 
 */
define(['configuracoes', 
        'modelo/taxas/taxaIOF'], 
function(config, TaxaIOF){
	
	var _this = null;	
	var ManterGrupoTaxaColecao = Backbone.Collection.extend({

		//url de para chamada do servico rest padrao
		urlRoot: config['urlBaseRest'] + 'TaxaIOF',
		url: '',
		model: TaxaIOF,
		
		initialize : function () {
			console.log("Collection Taxa IOF - initialize");
		
		},
		
		listar: function(date){
			_this = this;
			_this.url = _this.urlRoot + "/lista/"+date;
						
			return this.fetch({
			    type: "GET",
			    contentType: "application/json"
			});
			
		},
		
		parse: function(data){
		    return data["listaRetorno"];
		}
		
	});
	
	return ManterGrupoTaxaColecao;

});