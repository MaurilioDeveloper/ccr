/**
 * @author 
 * 
 * JavaScript para o Manter Documento
 * 
 * @version 1.0.0.0
 * 
 * 
 */
define(['configuracoes'], 
function(config){
	
	var _this = null;	
	var ManterDocumentoModelo = Backbone.RelationalModel.extend({

		//url de para chamada do servico rest padrao
		urlRoot: config['urlBaseRest'] + 'Documento',		
		urlClient: config['urlBaseRest'] + 'cliente',
		url: '',
		idAttribute: '_id', 
		
		defaults : {
			id : 0,
			inicioVigencia : '',
			fimVigencia	: '',
			tipoDocumento : '',
			nomeModeloDocumento : '',
			nomeUsuario : '',
			data : '',
			templateHtml : '',
	    },
	    
		initialize : function () {
			console.log("Model Manter Documento - initialize");				     	
		},	
		
		buscar: function(documento ){
			console.log ("Model Manter Documento -> buscar");
				
			_this = this;
			_this.url = _this.urlRoot + "/consulta";
			
			return this.fetch({
			    type: "POST",
			    contentType: "application/json",
			    data:JSON.stringify(documento),
			    async : false
			});
		},
		
		getAutoCompleteNomeModelo : function(desc){
			console.log ("Model Manter Documento -> getAutoCompleteNomeModelo");
			_this = this;
			_this.url = _this.urlRoot + "/consultaNomeModelo?desc="+desc;
			
			return _this.fetch({
				type: "GET",
			    contentType: "application/json"
			});
		}, 
		
		
		removeModeloDocumento : function(documento){
			_this = this;
	    	_this.url = _this.urlRoot + "/removeModeloDocumento";
	    
			return _this.sync('create', _this, {			    
				contentType: "application/json",
				data: JSON.stringify(documento)
			});
		},
		
		salvarNovoDocumento : function(documento){
			_this = this;
	    	
	    	_this.url = _this.urlRoot + "/salvarDocumento";
	    	
	    	
	    	// usar sync pois o save e o fetch adicionam a classe Retorno na model e nem voce e nem eu queremos isso 
			return _this.sync('create', _this, {			    
				contentType: "application/json",
				data: JSON.stringify(documento)
			});
		},
		
		visualizacaoImpressao: function(documento ){
			console.log ("Model Manter Documento -> consultaImpressaoTeste");
				
			_this = this;
			_this.url = _this.urlRoot + "/visualizacaoImpressao";
			
			return this.fetch({
			    type: "POST",
			    contentType: "application/json",
			    data:JSON.stringify(documento),
			    async : false
			});
		},
		
		consultaRetorno: function(documento ){
			console.log ("Model Manter Documento -> buscarRetorno");
				
			_this = this;
			_this.url = _this.urlRoot + "/consultaRetorno";
			
			return this.fetch({
			    type: "POST",
			    contentType: "application/json",
			    data:JSON.stringify(documento),
			    async : false
			});
		},
		
	});
	
	return ManterDocumentoModelo;

});