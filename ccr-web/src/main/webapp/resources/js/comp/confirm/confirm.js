var ConfirmView = AbstractView.extend({

	tagName: 'div',

	events: {
		'click #confirm_BtnOk': 'ok',
		'click .confirmClose': 'cancel'
	},

	withQuestion: function(question) {
		this.question = question;

		return this;
	},

	ok: function() {
		this.trigger('ok');
	},

	cancel: function() {
		this.trigger('cancel');
	},

	render: function() {
		this.$el.html(
			_.template(
				this.loadTemplate('../gec-web/bibliotecas/javascript/componentes/confirm/confirmTemplate.html'), 
				{ 'question': this.question }
			)
		);

		return this;
	}
});

