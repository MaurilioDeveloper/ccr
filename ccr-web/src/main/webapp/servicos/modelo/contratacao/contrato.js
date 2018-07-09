define(['configuracoes'], 
function(config){
	
	var _this = null;
	var Contrato = Backbone.Model.extend({
	
		urlRoot: config['urlBaseRest'] + 'contratacao/',
		urlRootUser: config['urlBaseRest'] + 'userprofile/',
		
	    defaults: {
	    	
	    },
	
		initialize: function initialize() {
			
		},
		
		cadastrar: function(contrato, precisaAutorizacao){
			console.log ("Model contrato -> cadastrar interno");
			
			_this = this;			
			_this.url = _this.urlRoot + "cadastrar?" + $.param({precisaAutorizacao: precisaAutorizacao});
			 
			return _this.sync('create', _this, {
				contentType: "application/json",
				data: JSON.stringify(contrato)
			});
			
		},
		
		consultarPorId: function(id){
			console.log("Collection Contrato - ConsultarPorId");
			_this = this;
			
			var parameters = $.param({id : id});
			_this.url = _this.urlRoot + "consultarPorId?" + parameters;
			console.log("Collection Solicitar Cadastro Empresa - url " + _this.url);
			
			return this.fetch({
			    type: "GET",
			    contentType: "application/json",
			    async : false
			});
		},
		
		/*buscaContratoPorConvenio: function(nuConvenio) {
			console.log("Collection Contrato - buscaContratoPorConvenio");
			_this = this;
			
			var parameters = $.param({idConvenio : nuConvenio});
			_this.url = _this.urlRoot + "buscaContratoPorConvenio?" + parameters;
			console.log("Collection buscaContratoPorConvenio - url " + _this.url);
			
			return this.fetch({
			    type: "GET",
			    contentType: "application/json",
			    async : false
			});
		},*/
		
		
		validarPerfil: function(id){
			console.log("Contrato - validarPerfil");
			_this = this;
		
			_this.url = _this.urlRoot + "validarPerfil";
			console.log("Contrato - validarPerfil " + _this.url);
			
			return this.fetch({
			    type: "GET",
			    contentType: "application/json",
			    async : false
			});
		},
		
		validarPerfilAutorizar: function() {
			console.log("Contrato - validarPerfilAutorizar");
			_this = this;
		
			_this.url = _this.urlRoot + "validarPerfilAutorizar";
			console.log("Contrato - validarPerfilAutorizar " + _this.url);
			
			return this.fetch({
			    type: "GET",
			    contentType: "application/json",
			    async : false
			});
		},
				
		loadProfile: function(){
			
			_this = this;			
			_this.url = _this.urlRootUser + "load";
			 
			return _this.fetch({
			    type: "POST",
			    contentType: "application/json",
			});	    
			
		},
		
		contratar: function(contrato){
			console.log ("Model contrato - contratar");
			
			_this = this;			
			_this.url = _this.urlRoot + "contratar";
			 
			return _this.sync('create', _this, {
				contentType: "application/json",
				data: JSON.stringify(contrato)
			});
		}, 
		
		consultarCpfPorContrato: function(id){	
			console.log("Contrato - consultarCpfPorContrato ");
			_this = this;		
			var parameters = $.param({idContrato : id});
			_this.url = _this.urlRoot + "consultarCpfPorContrato?" + parameters;
			
			
			return this.fetch({
			    type: "GET",
			    contentType: "application/json",
			    async : false
			});	
		},
		
		imprimirContrato : function (impressao){
			console.log ("Model contrato - imprimirContrato");
			
			_this = this;			
			_this.url = _this.urlRoot + "imprimirContrato";
			 
			return _this.sync('create', _this, {
				contentType: "application/json",
				data: JSON.stringify(impressao)
			});
		},
		
		buscarTipoDocumentoImpressaoCCR : function (){
			console.log ("Model contrato - buscarTipoDocumentoImpressaoCCR");
			
			_this = this;			
			_this.url = _this.urlRoot + "buscarTipoDocumentoImpressaoCCR";
			 
			return _this.sync('create', _this, {
				contentType: "application/json"
			});
		},
		
		
		validaSr: function(sr){
			console.log("Contrato - validaSr");
			_this = this;
		
			var parameters = $.param({sr : sr});
			_this.url = _this.urlRoot + "validarSr?" + parameters;
			console.log("Collection validaSr - url " + _this.url);
			
			return this.fetch({
			    type: "GET",
			    contentType: "application/json",
			    async : false
			});
		},
		
		validaAg: function(ag){
			console.log("Contrato - validaAg");
			_this = this;
		
			_this.url = _this.urlRoot + "validarAg?ag=" + ag;
			console.log("Collection validaSr - url " + _this.url);
			
			return this.fetch({
			    type: "GET",
			    contentType: "application/json",
			    async : false
			});
		},
		
		atualizarSituacaoContrato : function(contratoSituacao){
			_this = this;			
			_this.url = _this.urlRoot + "atualizaStatusContrato";
			 teste={};
			return _this.fetch({
			    type: "POST",
			    contentType: "application/json",
			    data: JSON.stringify(contratoSituacao)
			});
		},
	    
		
		
		atualizarContrato : function(contrato){
			
			console.log ("Atualizar Contrato - atualizaContrato");
			
			_this = this;			
			_this.url = _this.urlRoot + "atualizaContrato";
			 
			return _this.sync('create', _this, {
				contentType: "application/json",
				data: JSON.stringify(contrato)
			});
		}, 

		
		reenviarContratoSIAPX : function(idContrato){
			_this = this;
			var parameters = $.param({numeroContrato : idContrato});
			_this.url = _this.urlRoot + "reenviarContratoSIAPX?" + parameters;
			
			return this.fetch({
			    type: "GET",
			    contentType: "application/json"
			});
		},
		
	});
		
	return Contrato;
	
});