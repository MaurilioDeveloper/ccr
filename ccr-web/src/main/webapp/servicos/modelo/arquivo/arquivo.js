/**
 * @author F620600
 * @version 1.0.0.0
 *  
 */
define(['configuracoes',
        'enumeracoes/eMensageria',
        'modelo/contratacao/contrato',
        'modelo/Mensageria'], 
function(config, EMensageria, Contrato, Mensageria){
	
	var _this = null;
	var Arquivo = Backbone.RelationalModel.extend({
		//url de para chamada do servico rest padrao
		urlRoot: config['urlBaseRest'] + 'arquivo/',
	
		mensageria : undefined,
		contrato : undefined,
		
		/***
		 * Criacao de valores padroes
		 */
	    defaults : {
	    	
	    },
	    
	    /**
		 * Função padrão de incialização do modelo
		 * 
		 * @TODO
		 * 	
		 */
		initialize : function () {
			console.log("Consulta Arquivo - initialize");
			//variavel auxiliar para uso em funcoes internas
			_this = this;
			
			//array de validadores
			this.validador = {};
			     	
		},
		
		/**
		 *Função para validar os dados do arquivo
		 *
		 *Parametro eh o atributo que deseja ser validado
		 *
		 *@param(atributo)
		 * 
		 */	
		validaAtributo: function (atributo) {
	        return (_this.validador[atributo]) ? _this.validador[atributo](_this.get(atributo)) : {isValid: true};
	    },
	    
	    getMensageria : function(){
			if(_this.mensageria == undefined || _this.mensageria == null){
				_this.mensageria = new Mensageria();

				_this.listenTo(_this.mensageria, 'consultar', _this.recebido); 
				_this.listenTo(_this.mensageria, EMensageria.NAO_RECEBIDO, this.naoRecebido);
				_this.listenTo(_this.mensageria, 'error', _this.erro);
			}
			return this.mensageria;
		},
	    
		
		consultar: function(consulta){ 	
			
			_this = this;			
			_this.url = _this.urlRoot + "consultar";
			
			return _this.fetch({
			    type: "POST",
			    contentType: "application/json",
			    data: JSON.stringify(consulta)
			});
//			return this.fetch({
//				data: consulta, 
//				type: "GET",
//			    contentType: "application/json"
//			});
			
		},
		
		
		
	});
	
	return Arquivo;

});