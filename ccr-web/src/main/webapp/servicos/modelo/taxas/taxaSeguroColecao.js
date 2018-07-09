/**
 * @author F620600
 * 
 * JavaScript para o taxa juros
 * 
 * @version 1.0.0.0
 * 
 * 
 */
define(['configuracoes', 
        'modelo/taxas/taxaSeguro'], 
function(config, TaxaSeguro){
	
	var _this = null;	
	var ManterSeguroColecao = Backbone.Collection.extend({

		//url de para chamada do servico rest padrao
		urlRoot: config['urlBaseRest'] + 'TaxaSeguro',
		url: '',
		model: TaxaSeguro,
		
		initialize : function () {
			console.log("Collection Taxa Seguro - initialize");
		},
		
		listar: function(dateIni, dateFim){
			_this = this;
			
			consulta =  { 
					dataInicioVigencia: dateIni,
					dataFinalVigencia: dateFim
    		};
			
			_this.url = _this.urlRoot + "/lista/";
						
			return _this.fetch({
				data: consulta, // consulta é um objeto javascript com os query parameters
			    type: "GET",
			    contentType: "application/json"
			});
			
		},

		parse: function(data){
		    return data["listaRetorno"];
		}
		
	});
	
	return ManterSeguroColecao;

});