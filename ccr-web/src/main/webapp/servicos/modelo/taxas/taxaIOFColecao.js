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
	var ManterIOFColecao = Backbone.Collection.extend({

		//url de para chamada do servico rest padrao
		urlRoot: config['urlBaseRest'] + 'TaxaIOF',
		url: '',
		model: TaxaIOF,
		defaults : {
			id: 0,
			aliquotaBasica: 0, 
			aliquotaComplementar: 0, 
			inicioVigencia: "", 
			fimVigencia: "", 
			updatable: "",
			excludable: "",
			usuarioInclusao: "",
			dataInclusaoTaxa: "",
			usuarioFinalizacao: "",
			dataInclusaoFimVigencia: ""
		},
		tipos : {
			
			id: 'NUMERO',
			aliquotaBasica: "NUMERO", 
			aliquotaComplementar: 'NUMERO', 
			inicioVigencia: 'CHAR', 
			fimVigencia: 'CHAR', 
			updatable: 'CHAR',
			usuarioInclusao: 'CHAR',
			dataInclusaoTaxa: 'CHAR',
			usuarioFinalizacao: 'CHAR',
			dataInclusaoFimVigencia: 'CHAR'
		},
		
		initialize : function () {
			console.log("Collection Taxa IOF - initialize");
		
		},
		
		listar: function(dataIni, dataFim){
			_this = this;
			_this.url = _this.urlRoot + "/listaRange/?dataIni="+dataIni+"&dataFim="+dataFim;
						
			return this.fetch({
			    type: "GET",
			    contentType: "application/json"
			});
			
		},
		
		parse: function(data){
		    return data["listaRetorno"];
		}
		
	});
	
	return ManterIOFColecao;

});