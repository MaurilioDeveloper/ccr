/**
 * @author F620600
 *  
 * @version 1.0.0.0
 * 
 * 
 */
define(['configuracoes',
        'enumeracoes/eMensageria',
        'modelo/Mensageria'], 
function(config, EMensageria, Mensageria){
	
	var _this = null;
	var Beneficio = Backbone.RelationalModel.extend({
		//url de para chamada do servico rest padrao
		urlRoot: config['urlBaseRest'] + 'beneficio/',
	
		mensageria : undefined,
		
		/***
		 * Criacao de valores padroes
		 */
	    defaults : {
	    	cpf: undefined,
	    	beneficio: undefined
	    },
	    
	    tipos: {
	    	cpf: 'CPF',
	    	beneficio_numero: 'NUMERO', 
	    	beneficio_dv: 'NUMERO'
	    },
	    
	    /**
		 * Função padrão de incialização do modelo
		 * 
		 * @TODO
		 * 	
		 */
		initialize : function () {
			console.log("Beneficio - initialize");
						
			//array de validadores
			this.validador = {};
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
	        return (_this.validador[atributo]) ? _this.validador[atributo](_this.get(atributo)) : {isValid: true};
	    },
	    
	    getMensageria : function(){
			if(_this.mensageria == undefined || _this.mensageria == null){
				_this.mensageria = new Mensageria();

				_this.listenTo(_this.mensageria, EMensageria.SUCESSO, _this.recebido); 
				_this.listenTo(_this.mensageria, EMensageria.NAO_RECEBIDO, _this.naoRecebido);
				_this.listenTo(_this.mensageria, 'error', _this.erro);
			}
			return this.mensageria;
		},
	    
		consultar: function(){		
			_this = this;
			
			//Define os dados de envio
			_this.getMensageria().set('dados', config.preparaAtributos(_this.attributes, _this.tipos));			
			_this.getMensageria().requisicao(_this.urlRoot + 'consultar', EMensageria.SUCESSO, 5, 1000);
		},
		
		/**
		 * A ser executada apenas quando o evento do modelo de mensageria 'recebido' for acionado
		 */
		recebido : function(modelo){
			_this = this;
			console.log('Beneficio - recebido - ini', modelo);
			
			//Aciona os listeners do evento referente a operaÃ§Ã£o que estÃ¡ sendo executada
			_this.trigger(modelo.eventoSucesso, modelo.get('dados'));
		},
		
		/**
		 * 
		 */
		naoRecebido : function(modelo){
			_this = this;
			console.log('Beneficio - naoRecebido - ini');
			
			//Aciona os listeners do evento naoRecebido
			_this.trigger(EMensageria.NAO_RECEBIDO, _this);
		},
		
		/**
		 * Tratamento de erros neste modelo.
		 */
		erro : function(modelo, xhr, request){
			_this = this;
			console.log('Beneficio - erro - ini');
			
			//Repassa o erro para quem estiver escutando este modelo
			_this.trigger('error', _this, xhr, request);
		}
		
		
	});
	
	return Beneficio;

});