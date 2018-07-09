/**
 * @author F620600
 * 
 * JavaScript para o taxa seguro
 * 
 * @version 1.0.0.0
 * 
 * 
 */
define(['configuracoes'], 
function(config){
	
	var _this = null;
	
	var ManterSeguroModelo = Backbone.RelationalModel.extend({
		//url de para chamada do servico rest padrao
		urlRoot: config['urlBaseRest'] + 'TaxaSeguro',
		url: '',
	
		/***
		 * Criacao de valores padroes
		 */
	    defaults : {
	    	id: undefined,
	    	inicioVigencia: '',
	    	fimVigencia: '',
	    	prazoMin: 0,
	    	prazoMax: 0,
	    	idadeMin: 0,
	    	idadeMax: 0,
	    	taxa: 0,
	    	prazoMinOld : 0, //utilizado na alteração da taxa seguro.
	    	prazoMaxOld : 0, //utilizado na alteração da taxa seguro.			
			idadeMinOld : 0, //utilizado na alteração da taxa seguro.
	    	idadeMaxOld : 0, //utilizado na alteração da taxa seguro.
	    	taxaOld : 0, //utilizado na alteração da taxa seguro.
			
	    },
	    
	    tipos : {
	    	inicioVigencia: 'DATA',
	    	fimVigencia: 'DATA',
	    	prazoMin: 'NUMERO',
	    	prazoMax: 'NUMERO',
	    	idadeMin: 'NUMERO',
	    	idadeMax: 'NUMERO',
	    	taxa: 'TAXA',
	    	prazoMinOld : 'NUMERO',
	    	prazoMaxOld : 'NUMERO',			
			idadeMinOld : 'NUMERO',
	    	idadeMaxOld : 'NUMERO',
	    	taxaOld : 'TAXA',
	    },
	    
	    /**
		 * Função padrão de incialização do modelo
		 * 
		 * @TODO
		 * 	Alterar a validacao para Codigo
		 */
		initialize : function () {
			console.log("Taxa Seguro Modelo - initialize");
			//variavel auxiliar para uso em funcoes internas
			_this = this;
			
			//array de validadores
			_this.validador = {};			     	
		},
		
		/**
		 *Função para validar os dados do cliente
		 *
		 *Parametro eh o atributo que deseja ser validado
		 *
		 *@param(atributo)
		 * 
		 */	
		validaAtributo: function (atributo) {
			_this = this;
	        return (_this.validador[atributo]) ? _this.validador[atributo](_this.get(atributo)) : {isValid: true};
	    },

		salvar: function(inicioVigenciaChave, consultaSeguro){
			console.log ("Model taxa seguro -> salvar");
			
			_this = this;			
			_this.url = _this.urlRoot + "/salvar?inicioVigenciaChave=" + inicioVigenciaChave;
			_this.attributes = config.preparaAtributos(_this.attributes, _this.tipos);		
			
			// usar sync pois o save e o fetch adicionam a classe Retorno na model e nem voce e nem eu queremos isso 
			return _this.sync('create', _this, {			    
				contentType: "application/json"
			});
		},
		
		excluir: function(){
			console.log ("Model taxa juro -> excluir");
			
			_this = this;
			_this.url = _this.urlRoot + "/excluir";
			
			_this.attributes = config.preparaAtributos(_this.attributes, _this.tipos);
			
			// retirado o destroy pq qndo dava erro negocial ele excluia a model da mesma forma			
			return _this.sync('create', _this, {			    
				contentType: "application/json"
			});
		},
		
		
	});
	
	return ManterSeguroModelo;

});