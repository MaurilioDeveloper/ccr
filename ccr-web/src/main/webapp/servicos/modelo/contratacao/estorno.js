/**
 * @author F620600
 * 
 * JavaScript para o alterar IOF
 * 
 * @version 1.0.0.0
 * 
 * 
 */
define([], 
function(){
	
	var _this = null;	
	var ManterIOFModelo = Backbone.RelationalModel.extend({

		//url de para chamada do servico rest padrao
		urlRoot: '/ccr-web/consignado/tabelas/taxaIOF',
		url: '',
		
		defaults : {
	    	acao: 'I', // I - incluir, A - alterar
	    	nuTaxaIOF: 0,
	    	pcAliquotaBasica: 0.0,
	    	pcAliquotaComp: 0.0,
	    	dtInicioVigencia: new Date(),
	    	dtFimVigencia: new Date()
	    },
	    
		initialize : function () {
			console.log("Model Taxa IOF - initialize");

			//variavel auxiliar para uso em funcoes internas
			_this = this;						     	
		},
		
		validate: function(attributes, options) {
			console.log("call -> TaxaIOFModel -> validate");
			
			if (attributes == undefined)
				return true;
			
			var errors = [];
					
			if (!attributes.nuTaxaIOF && attributes.acao == 'A') {
				errors.push({name: 'nuTaxaIOF', message: 'Codigo da taxa IOF nao informado.'});
			}
			
			if (!attributes.pcAliquotaBasica && !attributes.pcAliquotaBasica > 0) {
				errors.push({name: 'pcAliquotaBasica', message: 'Percentual Aliquota Basica nao inforamdo.'});
			}
			
			if (!attributes.pcAliquotaComp && !attributes.pcAliquotaComp > 0) {
				errors.push({name: 'pcAliquotaComp', message: 'Percentual Aliquota Complementar nao inforamdo.'});
			}
			
			if (!attributes.dtInicioVigencia) {
				errors.push({name: 'dtInicioVigencia', message: 'Data de Inicio da Vigencia nao informada'});
			}
			
			return errors.length > 0 ? errors : false;
		},

		listar: function(){
			_this.url = _this.urlRoot + "/lista";
		
			console.log("Model taxa IOF -> lista");
			
			return _this.fetch({
				type: "POST",
			    contentType: "application/json"    			
			});
		},
		
		buscar: function(){
			_this.url = _this.urlRoot + "/consulta";
		
			console.log ("Model taxa IOF -> buscar");
			console.log(_this.attributes.nuTaxaIOF);
			
			return _this.fetch({
				type: "GET",
			    contentType: "application/json",
    			data: $.param({ "nuTaxaIOF": _this.attributes.nuTaxaIOF})
			});
		},
		
		salvar: function(){
			_this.url = _this.urlRoot + "/salvar";
			console.log ("Model taxa IOF -> salvar");
	    	return _this.save(null);	
		}
		
	});
	
	return ManterIOFModelo;

});