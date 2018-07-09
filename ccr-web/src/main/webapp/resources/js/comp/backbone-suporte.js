var AbstractView = Backbone.View.extend({
	/**
	 * Carrega o template
	 */
	loadTemplate : function(path) {
		if (this.templates == null) {
			this.templates = [];
		}
		
		if (this.templates[path] == null) {
			var self = this;

			$.ajax({
				url : path,
				async : false
			}).done(function(html) {
				self.templates[path] = html;
			}).fail(
				function(response) {
					console.log('Erro ao carregar o template: '
							+ response.responseText);
				}
			);

			return this.templates[path];
		}

		return this.templates[path];
	},
	
	close : function() {
		this.undelegateEvents();
		this.$el.empty();
		this.unbind();
		$(this).remove();
	}	

	
});

var AbstractEditView = AbstractView.extend({

	/**
	 * Processa erros de validacao (invalid) de uma model. Renderiza os erros
	 * para o padrao abaixo:
	 * 
	 * <div class="control-group codigo"> <label>Código:</label> <input
	 * type='text' id='inputCodigo' value='<%= codigo %>'/> <span
	 * class="help-inline"></span> </div>
	 * 
	 * @param model
	 * @param errors
	 */
	processInvalid : function(model, errors) {
		_.each(errors, function(error) {
			var controlGroup = $('.' + error.name);
			controlGroup.addClass('error');
			controlGroup.find('.help-inline').text(error.message);
		}, this);
	},

	/**
	 * Apaga os erros de validacao.
	 */
	clearInvalid : function() {
		$('.control-group').removeClass('error');
		$('.help-inline').text('');
	}


});
