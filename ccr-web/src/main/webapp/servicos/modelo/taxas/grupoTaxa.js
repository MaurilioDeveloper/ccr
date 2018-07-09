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
	var ManterGrupoTaxaModelo = Backbone.RelationalModel.extend({

		//url de para chamada do servico rest padrao
		urlRoot: config['urlBaseRest'] + 'GrupoTaxa',		
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
		
		

		buscar: function(codigo,nome){
			console.log ("Model taxa IOF -> buscar");
			
			_this = this;
			//_this.url = _this.urlRoot + "/consulta?codigo="+codigo+"&nome="+nome;
			_this.url = _this.urlRoot + "/consulta";
			
			var consulta ={};
			consulta.codigo = codigo;
			consulta.nome = nome;

			return _this.fetch({
				data: consulta, 				
				type: "GET",
			    contentType: "application/json"
			});
		},
		
		getAutoCompleteNomeGrupoTaxa : function(desc) {
			console.log ("Model Manter Grupo Taxa -> getAutoCompleteNomeGrupoTaxa");
			_this = this;
			_this.url = _this.urlRoot + "/consultaGrupoTaxa?desc="+desc;
			
			return _this.fetch({
				type: "GET",
			    contentType: "application/json"
			});
		},
		
		salvar: function(grupoTaxa){
			console.log ("Model taxa IOF -> salvar");
			
			_this = this;			
			_this.url = _this.urlRoot + "/salvar";
			
			// usar sync pois o save e o fetch adicionam a classe Retorno na model e nem voce e nem eu queremos isso 
			return this.fetch({
			    type: "POST",
			    contentType: "application/json",
			    data:JSON.stringify(grupoTaxa),
			    async : false
			});
			
		},
		
		excluir: function(id){
			console.log ("Model taxa IOF -> excluir");
			
			_this = this;
			_this.url = _this.urlRoot + "/excluir/" +id;		
			
			// retirado o destroy pq qndo dava erro negocial ele excluia a model da mesma forma			
			return _this.sync('create', _this, {			    
				contentType: "application/json"
			});
		}
		
		
	});
	
	return ManterGrupoTaxaModelo;

});