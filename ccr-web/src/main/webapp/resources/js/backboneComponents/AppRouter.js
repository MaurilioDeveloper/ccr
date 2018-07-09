/* ****************************************************************
 * Componente de Bundle
 ****************************************************************** */

var AppRouter = Backbone.View.extend({
	events : {},

	initialize : function() {
		console.log("NAVIGATOR");

		this.dashboardView = new DashboardControle();
		this.render();
	},

	render : function() {
		$('#container').html(this.dashboardView.el);
		return this;
	},
});
