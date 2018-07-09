/**
 * @author F620600
 * 
 * JavaScript para o listar taxa juros
 * 
 * @version 1.0.0.0
 * 
 * 
 */
define(['configuracoes', 
        'modelo/taxas/taxaJuros'], 
function(config, TaxaJuros){
	
	var _this = null;	
	var ManterJuroColecao = Backbone.Collection.extend({

		//url de para chamada do servico rest padrao
		urlRoot: config['urlBaseRest'] + 'TaxaJuro',
		urlEmprest: config['urlBaseRest'] + 'emprestimo',
		url: '',
		model: TaxaJuros,
		tipoConsulta: "", 
		codigoTaxa: "0",
		utilizarEm: "0",
		dataInicial: "0",
		dataFinal: "0",
		convenio: "0",
			
		initialize : function () {
			console.log("Collection Taxa Juros - initialize");

			//variavel auxiliar para uso em funcoes internas
			_this = this;			
		},
		
		listar: function(){
			_this = this;
			
			if (this.codigoTaxa === "") {
                this.codigoTaxa = "-1";
            }
			_this.url = _this.urlRoot + "/lista";
			
			var consulta ={};
			consulta.tipoConsulta = this.tipoConsulta;
			consulta.codigo = this.codigoTaxa;
			consulta.utilizarEm = this.utilizarEm;
			consulta.dataInicial = this.dataInicial;
			consulta.dataFinal = this.dataFinal;
			consulta.tipoConsulta = this.tipoConsulta;
			consulta.convenio = this.convenio;
						
			return _this.fetch({
			    type: "GET",
			    data: consulta, // consulta é um objeto javascript com os query parameters
			    contentType: "application/json"
			});
			
		},
		
		listarPrazoConvenio: function(){
			_this = this;
			var idCanal = "1";
			_this.url = _this.urlEmprest + "/consultarPrazos?idConvenio="+this.codigoTaxa+"&idCanal="+ this.canal;
						
			return _this.fetch({
			    type: "GET",
			    contentType: "application/json"
			});
			
		},
		
		parse: function(data){
		    return data["listaRetorno"];
		}		
		
	});
	
	return ManterJuroColecao;

});