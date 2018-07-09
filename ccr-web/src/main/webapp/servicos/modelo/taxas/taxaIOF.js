/**
 * @author F620600
 * 
 * JavaScript para o Manter IOF
 * 
 * @version 1.0.0.0
 * 
 * 
 */
define(['configuracoes'], 
function(config){
	
	var _this = null;	
	var ManterIOFModelo = Backbone.RelationalModel.extend({

		//url de para chamada do servico rest padrao
		urlRoot: config['urlBaseRest'] + 'TaxaIOF',		
		url: '',
		
		defaults : {
			id: undefined,
	    	aliquotaBasica: 0.0,
	    	aliquotaComplementar: 0.0,
	    	inicioVigencia: '',
	    	fimVigencia: ''
	    },
	    
		initialize : function () {
			console.log("Model Taxa IOF - initialize");				     	
		},
		
		validate: function(attributes, options) {
			console.log("call -> TaxaIOFModel -> validate");
			_this = this;
			
			if (attributes == undefined)
				return true;
			
			var errors = [];
			
			if (attributes.codigo > 0)
				if (!attributes.codigo) {
					errors.push({name: 'codigo', message: 'Codigo da taxa IOF nao informado.'});
				}
			
			if (!attributes.aliquotaBasica && attributes.aliquotaBasica > 0) {
				errors.push({name: 'aliquotaBasica', message: 'Percentual Aliquota Basica nao inforamdo.'});
			}
			
			if (!attributes.aliquotaComplementar && attributes.aliquotaComplementar > 0) {
				errors.push({name: 'aliquotaComplementar', message: 'Percentual Aliquota Complementar nao inforamdo.'});
			}
			
			if (!attributes.inicioVigencia) {
				errors.push({name: 'inicioVigencia', message: 'Data de Inicio da Vigencia nao informada'});
			}

			return errors.length > 0 ? errors : false;
		},

		buscar: function(){
			console.log ("Model taxa IOF -> buscar");
			
			_this = this;			
			_this.url = _this.urlRoot + "/consulta/" + _this.attributes.id;		
			
			return _this.fetch({
				type: "GET",
			    contentType: "application/json"
			});
		},
		
		salvar: function(){
			console.log ("Model taxa IOF -> salvar");
			
			_this = this;			
			_this.url = _this.urlRoot + "/salvar";
			_this.preparaCampos();
			
			// usar sync pois o save e o fetch adicionam a classe Retorno na model e nem voce e nem eu queremos isso 
			return _this.sync('create', _this, {			    
				contentType: "application/json"
			});
			
		},
		
		excluir: function(){
			console.log ("Model taxa IOF -> excluir");
			
			_this = this;
			_this.url = _this.urlRoot + "/excluir/" + _this.get("id");		
			
			// retirado o destroy pq qndo dava erro negocial ele excluia a model da mesma forma			
			return _this.sync('create', _this, {			    
				contentType: "application/json"
			});
		},
		
		preparaCampos: function(){	
			if (_this.attributes == undefined)
				return;

			//Recupera somente a data, ignorando a hora.
			if (_this.attributes.inicioVigencia.indexOf(' ') > 0) {
				_this.attributes.inicioVigencia = _this.attributes.inicioVigencia.substring(0, _this.attributes.inicioVigencia.indexOf(' ')) ;
			}
						
			
			_this.attributes.inicioVigencia = preparaDataBD(_this.attributes.inicioVigencia);			
			_this.attributes.aliquotaBasica = purificaMoeda(_this.attributes.aliquotaBasica);
			_this.attributes.aliquotaComplementar = purificaMoeda(_this.attributes.aliquotaComplementar);
		}
		
	});
	
	return ManterIOFModelo;

});