define(['configuracoes'], 
function(config){
	
	var _this = null;
	var Autorizar = Backbone.Model.extend({
	
		urlRoot: config['urlBaseRest'] + 'autorizar/',

		idAttribute: 'numero',
		
	    defaults: {
	    	
	    },
	
		initialize: function initialize() {
			this.set({
				
			});
		},
		
		autorizar: function(entrada){
			console.log ("Model autorizar -> autorizar");
			
			_this = this;			
			_this.url = _this.urlRoot + "autorizar";
			
			// usar sync pois o save e o fetch adicionam a classe Retorno na model e nem voce e nem eu queremos isso 
			return _this.sync('create', _this, {			    
				contentType: "application/json",
				data: JSON.stringify(entrada)
			});
			
		},
		
		cancelar: function(contrato, justificativa){
			console.log ("Model autorizar -> cancelar");
			
			_this = this;			
			_this.url = _this.urlRoot + "cancelar?" + $.param({contrato: contrato, justificativa: justificativa});
			
			// usar sync pois o save e o fetch adicionam a classe Retorno na model e nem voce e nem eu queremos isso 
			return _this.sync('create', _this, {			    
				contentType: "application/json",
				data: ''
			});
			
		},
		
		buscaAutorizacaoPorContrato: function(idContrato){
			console.log("Collection Autorizacao - buscaAutorizacaoPorContrato");
			_this = this;
			
			
//			var parameters = $.param({idContrato: idContrato});
//			_this.url = _this.urlRoot + "buscaAutorizacaoPorContrato?" + parameters;
			_this.url = _this.urlRoot + "buscaAutorizacaoPorContrato/" + idContrato;
			console.log("Collection Realiza Busca  Autorização por Contrato - url "+ _this.url);
			return this.fetch({
			    type: "GET",
			    contentType: "application/json", 
			    async : false
			});
		},
		

		buscaAutorizacaoPorSituacao: function(situacao, nuContrato, nuSituacaoContrato){
			console.log("Collection Autorizacao - buscaAutorizacaoPorSituacao");
			_this = this;
			
			var parameters = $.param({situacao: situacao, nuContrato: nuContrato, nuSituacaoContrato: nuSituacaoContrato});
			_this.url = _this.urlRoot + "buscaAutorizacaoPorSituacao?" + parameters;
			console.log("Collection Realiza Busca  Autorização por Situação - url "+ _this.url);
			return this.fetch({
			    type: "GET",
			    contentType: "application/json", 
			    async : false
			});
		},
	    
	});
		
	return Autorizar;
	
});