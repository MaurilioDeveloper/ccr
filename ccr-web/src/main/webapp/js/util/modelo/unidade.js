/**
 * @author TIVIT
 * 
 * JavaScript para o obejto Unidade
 * representa uma unidade da Caixa Economica Federal
 * 
 * @version 1.0.0.0
 * 
 * 
 */
define(['configuracoes',
        'enumeracoes/eMensageria',
        'modelo/Mensageria'], 
function(config, EMensageria, Mensageria){

	var Unidade = Backbone.RelationalModel.extend({
	
		//url de para chamada do servico rest padrao
		urlRoot: config['urlBaseRest'] + 'unidade/',
		url: '',

		/***
		 * Criacao de valores padroes
		 */
	    defaults : {   	
	    	
	    },
	    
	    /**
		 * Função padrão de incialização do modelo
		 */
		initialize : function () {
			console.log("Unidade - initialize");
			//inicializa o objeto verificando se o mesmo nao foi pesquisado
	//		if(!this.has("numeroMatricula")){
	//			this.consultar();
	//		}
					
		},   
		
	
		/**
		 * Funcao que consulta uma unidade de CEF
		 * pelo numero da unidade (inteiro)
		 * @param nuUnidade
		 */
		consultaUnidadePorNumeroUnidade: function(nuUnidade) {
			console.log("Unidade - consultaUnidadePorNumeroUnidade");				
			var nuUnidade = parseInt(nuUnidade);
			//variavel auxiliar utilizada em funcoes internas
			var that = this;
			//seta o urlRoot que sera usado pelo servico rest
			this.url = that.urlRoot + "consultaUnidadePorNumeroUnidade/" + nuUnidade;
			
			this.fetch({
				type : "GET",
				contentType : "application/json",
				async: false,
				"success" : function(model, response) {
					console.log("Usuario - consultar - success");	
					console.log(model);
					var unidade = model.attributes;
				},
				"error" : function(erro) {
					console.log("Usuario - consultar - erro");							
					//convete mensagem texto para formato JSON				
					var msg = erro;	
					console.log(erro);
				}
			});		
		},
			
	});

	return Unidade;
});