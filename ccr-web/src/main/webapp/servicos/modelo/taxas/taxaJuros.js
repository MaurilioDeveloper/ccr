/**
 * @author F620600
 * 
 * JavaScript para o taxa juros
 * 
 * @version 1.0.0.0
 * 
 * 
 */
define(['configuracoes'], 
function(config){
	
	var _this = null;
	
	var ManterJurosModelo = Backbone.RelationalModel.extend({
		
		//url de para chamada do servico rest padrao
		urlRoot: config['urlBaseRest'] + 'TaxaJuro',
		url: '',
		
		/***
		 * Criacao de valores padroes
		 */
	    defaults : {
	    	id: undefined,
	    	codigoTaxa: 0, // codigo do grupo ou do convenio
	    	tipoConsulta: "",
	    	inicioVigencia: '',
	    	fimVigencia: '',
	    	prazoMin: 0,
	    	prazoMax: 0,
	    	pcMinimo: 0,
	    	pcMedio: 0,
	    	pcMaximo: 0,	    	
	    	tipoTaxa: 1,// concessao ou renovacao - padrao concessao
	    	prazoMaxOld : 0, //utilizado na alteração da taxa de juros.
			prazoMinOld : 0, //utilizado na alteração da taxa de juros.	    	
	    },
	    
	    /**
		 * Função padrão de incialização do modelo
		 * 
		 * @TODO
		 * 	Alterar a validacao para Codigo
		 */
		initialize : function () {
			console.log("Taxa juros - initialize");
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

		buscar: function(){
			console.log ("Model taxa juro -> buscar");
			
			_this = this;			
			_this.url = _this.urlRoot + "/consulta/" + _this.attributes.id;		
			
			return _this.fetch({
				type: "GET",
			    contentType: "application/json"
			});
		},
		
		salvar: function(inicioVigenciaChave, taxaSeguro){
			console.log ("Model taxa juro -> salvar");
			
			_this = this;			
					
			_this.url = _this.urlRoot + "/salvar?inicioVigenciaChave=" + preparaDataBD(inicioVigenciaChave);
			_this.preparaCampos();
			
			// usar sync pois o save e o fetch adicionam a classe Retorno na model e nem voce e nem eu queremos isso 
			return _this.sync('create', _this, {			    
				contentType: "application/json"
			});
			
		},
		
		excluir: function(){
			console.log ("Model taxa juro -> excluir");
			
			_this = this;
			_this.url = _this.urlRoot + "/excluir";
			
			// no excluir sempre vem formatado do java, soh precisa formatar a data
			_this.attributes.inicioVigencia = preparaDataBD(_this.attributes.inicioVigencia);
			
			// retirado o destroy pq qndo dava erro negocial ele excluia a model da mesma forma			
			return _this.sync('create', _this, {			    
				contentType: "application/json"
			});
		},
		
		preparaCampos: function(){			
			if (_this.attributes == undefined)
				return;
			
			_this.attributes.inicioVigencia = preparaDataBD(_this.attributes.inicioVigencia);			
			_this.attributes.fimVigencia = preparaDataBD(_this.attributes.fimVigencia);
			_this.attributes.pcMinimo = purificaMoeda(_this.attributes.pcMinimo);
			_this.attributes.pcMedio = purificaMoeda(_this.attributes.pcMedio);
			_this.attributes.pcMaximo = purificaMoeda(_this.attributes.pcMaximo);			
		}
		
	});
	
	return ManterJurosModelo;

});