/**
 * @author F620600
 * @version 1.0.0.0
 *  
 */
define(['configuracoes',
        'enumeracoes/eMensageria',
        'modelo/Mensageria'], 
function(config, EMensageria, Mensageria){
	
	var _this = null;
	var AgendadorContrato = Backbone.RelationalModel.extend({
		//url de para chamada do servico rest padrao
		urlRoot: config['urlBaseRest'] + 'processoenviocontrato/',
	
		mensageria : undefined,
		
		/***
		 * Criacao de valores padroes
		 */
	    defaults : {
	    	
	    },
	    
	    /**
		 * Função padrão de incialização do modelo
		 * 
		 * 	
		 */
		initialize : function () {
			console.log("Processo Envio Contrato - initialize");
			//variavel auxiliar para uso em funcoes internas
			_this = this;
			
			//array de validadores
			this.validador = {};
			     	
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
		
		
		atualizar: function(dataInicio, dataFim, intervalo) {
			_this = this;			
			
			var parameters = "horaInicio="+dataInicio+"&horaFim="+dataFim+"&intervalo="+intervalo;
			_this.url = _this.urlRoot + "atualizar?" + parameters;
			
			return _this.fetch({
			    type: "POST",
			    contentType: "application/json"
			});
		},
	    
		buscar: function(){ 	
			
			_this = this;			
			_this.url = _this.urlRoot + "consultar";
			
			return _this.fetch({
			    type: "GET",
			    contentType: "application/json",
			    async : true
			});
		}
		
		
		
	});
	
	return AgendadorContrato;

});