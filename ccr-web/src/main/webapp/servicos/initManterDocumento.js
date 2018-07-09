var initManterDocumento = Backbone.View.extend({
	
	initialize : function initialize() {
		
		console.log('Inicio - initManterDocumento');
		
		this.$el.off();
		var that = this;

		$.get( '../ccr-web/servicos/visao/index.html').done(function(dataHTML) {
			that.template = _.template(dataHTML);
			that.render();
		});
		
		console.log('fim - initManterDocumento');
		
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
		DashboardControleCCR.manterDocumento();
	}
	
		
});

var initManterDocumento = new initManterDocumento({ el : $('#container') });


//# sourceURL=consultaPropostaCardView.js