define(['configuracoes'], 
function(config){
	
	var _this = null;
	var ComboLocal = Backbone.Model.extend({
	
		urlRoot: config['urlBaseRest'] + 'Administracao/parametroOperacao/',

	    defaults: {
	    	numero: 0,
	    	descricao: '',
	    	grupoTaxaPadrao: undefined,
	    	indicadorTaxaPadrao: undefined,
	    	idadeMinima: 0,
	    	idadeMaxima: 0,
	    	exigeAutorizacao: '',
	    	valorMinimoExigencia: 0,
	    	maximoDiasSimulacaoFutura: 0
	    },
	
		initialize: function initialize() {
			this.set({
				numero: this.defaults.numero,
		    	descricao: this.defaults.descricao,
		    	grupoTaxaPadrao: this.defaults.grupoTaxaPadrao,
		    	indicadorTaxaPadrao: this.defaults.indicadorTaxaPadrao,
		    	idadeMinima: this.defaults.idadeMinima,
		    	idadeMaxima: this.defaults.idadeMaxima,
		    	exigeAutorizacao: this.defaults.exigeAutorizacao,
		    	valorMinimoExigencia: this.defaults.valorMinimoExigencia,
		    	maximoDiasSimulacaoFutura: this.defaults.maximoDiasSimulacaoFutura
			});
		},
		
		consultar: function () {
	    	_this = this;
	    	_this.url = _this.urlRoot + "consultar";
	    			    		
			return _this.fetch({
			    type: "GET",
			    contentType: "application/json"
			});
		}
	    
	});
		
	return ComboLocal;
	
});