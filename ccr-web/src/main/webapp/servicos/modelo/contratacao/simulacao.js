/**
 * @author F620600
 * 
 * JavaScript para o simular
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
	var SimularModelo = Backbone.RelationalModel.extend({
		
		//url de para chamada do servico rest padrao
		urlRoot: config['urlBaseRest'] + 'simulacao/',
		urlUserProfile: config['urlBaseRest'] + 'userprofile/',
		url: '',
		
		mensageria : undefined,
	
		/***
		 * Criacao de valores padroes
		 */
	    defaults : {
	    	grupo: undefined,	
	    	idConvenio: undefined,
	    	aposentadoInvalidez: undefined,
	    	dataNascimento: undefined,
	    	valorMargem: undefined,
	    	valorLiquido: undefined,
	    	valorPrestacao: undefined,
	    	prazo: undefined,
	    	taxaJuros: undefined,
	    	usuario: undefined,
	    	cpfCliente: undefined,
	    	nomeCliente: undefined,
	    	beneficio: undefined,
	    	agenciaMovimento: undefined,
	    	dataContratacao: undefined
	    },
	    
	    /**
		 * Função padrão de incialização do modelo
		 * 
		 * @TODO
		 * 	Alterar a validacao para Codigo
		 */
		initialize : function () {
			console.log("Simulacao - initialize");
			//variavel auxiliar para uso em funcoes internas
			_this = this;
			
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
	        return (this.validador[atributo]) ? this.validador[atributo](this.get(atributo)) : {isValid: true};
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
	    
	    simular: function () {
	    	_this = this;
	    	console.log('Simular - ini', JSON.stringify(_this.attributes));
	    	
			_this.url = _this.urlRoot + "simular";
			
			return _this.fetch({
			    type: "POST",
			    contentType: "application/json",
				data: JSON.stringify(_this.attributes),
			});	    	
	    },
			    
	    getAgenciaConcessora : function (){
	    	_this = this;
			_this.url = _this.urlUserProfile + "load";

			return _this.fetch({
			    type: "POST",
			    contentType: "application/json",
			});	    	
						
	    },

		/**
		 * A ser executada apenas quando o evento do modelo de mensageria 'recebido' for acionado
		 */
		recebido : function(modelo){
			_this = this;
			console.log('Simular - recebido - ini', modelo);
			
			//Aciona os listeners do evento referente a operaÃ§Ã£o que estÃ¡ sendo executada			
			_this.trigger(modelo.eventoSucesso, modelo.get('dados'));
		},
		
		/**
		 * 
		 */
		naoRecebido : function(modelo){
			_this = this;
			console.log('Simular - naoRecebido - ini');
			
			//Aciona os listeners do evento naoRecebido
			_this.trigger(EMensageria.NAO_RECEBIDO, _this);
		},
		
		/**
		 * Tratamento de erros neste modelo.
		 */
		erro : function(modelo, xhr, request){
			console.log('Simular - erro - ini', JSON.stringify(_this.attributes));
			
			//Repassa o erro para quem estiver escutando este modelo
			_this.trigger('error', _this, xhr, request);
		}
		
	});
	
	return SimularModelo;

});