var initGerenciadorLimites = Backbone.View.extend({
	
	initialize : function initialize() {
		
		this.$el.off();
		var that = this;

		$.get( '../ccr-web/servicos/visao/index.html').done(function(dataHTML) {
			that.template = _.template(dataHTML);
			that.render();
		});
		
	},
	
	render : function render(){
		this.preRender();
		this.posRender();		
		return this;
	},
	
	preRender : function preRender() {
		this.$el.html(this.template());
	},
	
	posRender : function posRender(){
		DashboardControleCCR.gerenciadorLimites();
	}
	
		
});

var initGerenciadorLimites = new initGerenciadorLimites({ el : $('#container') });


//# sourceURL=consultaPropostaCardView.js