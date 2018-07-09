var initContratarConsignado = Backbone.View.extend({
	
	initialize : function initialize() {
		
		console.log('Inicio - initContratarConsignado');
		
		this.$el.off();
		var that = this;

		$.get( '../ccr-web/servicos/visao/index.html').done(function(dataHTML) {
			that.template = _.template(dataHTML);
			that.render();
		});
		console.log('fim - initContratarConsignado');
		
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
		DashboardControleCCR.contratarConsignado();		
		
	}
	
		
});

var initContratarConsignado = new initContratarConsignado({ el : $('#container') });


//# sourceURL=consultaPropostaCardView.js