window.SimpleMessage = {
	
	urlTemplateInfo: '../gec-web/bibliotecas/javascript/componentes/message/info.html',
	urlTemplateSuccess: '../gec-web/bibliotecas/javascript/componentes/message/success.html',
	urlTemplateWarn: '../gec-web/bibliotecas/javascript/componentes/message/warn.html',
	urlTemplateError: '../gec-web/bibliotecas/javascript/componentes/message/error.html',
	urlTemplateFatal: '../gec-web/bibliotecas/javascript/componentes/message/fatal.html',
	
	MessageSeverity: {

			INFO: 		'INFO',
			SUCCESS: 	'SUCCESS',
			WARN: 		'WARN',
			ERROR: 		'ERROR',
			FATAL: 		'FATAL'
			
	},
	
	Message: Backbone.Model.extend({

		defaults: function() {
			return {
				name: 'Message - Componente de Mensagem',
				version: '1.0',
				code: 0,
				text: '',
				severity: window.SimpleMessage.MessageSeverity.INFO
			};
		}
	}),

	MessageView: AbstractView.extend({

		tagName: 'div',

		initialize: function(model, templates) {
			this.model = model;
			this.templateInfo = this.loadTemplate(window.SimpleMessage.urlTemplateInfo);
			this.templateSuccess = this.loadTemplate(window.SimpleMessage.urlTemplateSuccess);
			this.templateWarn = this.loadTemplate(window.SimpleMessage.urlTemplateWarn);
			this.templateError = this.loadTemplate(window.SimpleMessage.urlTemplateError);
			this.templateFatal = this.loadTemplate(window.SimpleMessage.urlTemplateFatal);
		},

		withData: function(data) {
			this.data = data;
		},

		render: function() {
			this.template = null;

			if (this.data == null) {
				if (this.model.get('severity') == window.SimpleMessage.MessageSeverity.INFO) {
					this.template = this.templateInfo;
				} else if (this.model.get('severity') == window.SimpleMessage.MessageSeverity.SUCCESS) {
					this.template = this.templateSuccess;
				} else if (this.model.get('severity') == window.SimpleMessage.MessageSeverity.WARN) {
					this.template = this.templateWarn;
				} else if (this.model.get('severity') == window.SimpleMessage.MessageSeverity.ERROR) {
					this.template = this.templateError;
				} else if (this.model.get('severity') == window.SimpleMessage.MessageSeverity.FATAL) {
					this.template = this.templateFatal;
				} 

				this.$el.html(_.template(this.template, this.model.toJSON()));
			} else {
				if (this.data.severity == window.SimpleMessage.MessageSeverity.INFO) {
					this.template = this.templateInfo;
				} else if (this.data.severity == window.SimpleMessage.MessageSeverity.SUCCESS) {
					this.template = this.templateSuccess;
				} else if (this.data.severity == window.SimpleMessage.MessageSeverity.WARN) {
					this.template = this.templateWarn;
				} else if (this.data.severity == window.SimpleMessage.MessageSeverity.ERROR) {
					this.template = this.templateError;
				} else if (this.data.severity == window.SimpleMessage.MessageSeverity.FATAL) {
					this.template = this.templateFatal;
				} 

				this.$el.html(_.template(this.template, data));
			}

			return this;
		}
	}),
	
	MessageHelper: {

		into: function(messageContainer) {
			this.messageContainer = messageContainer;
		
			return this;
		},

		clear: function() {
			$('#'+this.messageContainer).html('');
		},
		
		info: function(text) {
			var message = new window.SimpleMessage.Message();
			message.set('text', text);
			message.set('severity', window.SimpleMessage.MessageSeverity.INFO);
		
			var view = new window.SimpleMessage.MessageView(message);
			$('#'+this.messageContainer).html(view.render().el);
		},

		success: function(text) {
			var message = new window.SimpleMessage.Message();
			message.set('text', text);
			message.set('severity', window.SimpleMessage.MessageSeverity.SUCCESS);
		
			var view = new window.SimpleMessage.MessageView(message);
			$('#'+this.messageContainer).html(view.render().el);
		},

		warn: function(text) {
			var message = new window.SimpleMessage.Message();
			message.set('text', text);
			message.set('severity', window.SimpleMessage.MessageSeverity.WARN);
		
			var view = new MessageView(message);
			$('#'+this.messageContainer).html(view.render().el);
		},

		error: function(code, text) {
			var message = new window.SimpleMessage.Message();
			message.set('code', code);
			message.set('text', text);
			message.set('severity', window.SimpleMessage.MessageSeverity.ERROR);
		
			var view = new window.SimpleMessage.MessageView(message);
			$('#'+this.messageContainer).html(view.render().el);
		}
	}
};

