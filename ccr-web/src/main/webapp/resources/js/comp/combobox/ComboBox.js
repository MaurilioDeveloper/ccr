var ComboBox = AbstractView
		.extend({

			tagName : 'optgroup',

			initialize : function(config) {
				// this.messageContainer = config.messageContainer;
				this.collection = config.collection;
				this.selectedValue = config.selectedValue;
				this.filtro = config.filtro;
				this.initCollection();
				this.bind(config.bindTo);
			},

			filtro : function(expressao) {
				this.expressaoFiltro = expressao;
			},

			initCollection : function() {
				var self = this;
				
				self.collection.fetch({
					reset : true,
					async : false,
					error : function(collection, response) {
						alert(response.responseText);
					},
					success : function(collection, response, options) {
						if (self.filtro) {
							var	items ={};
							if ($.type(self.filtro) === 'object'){
								items=_.where(response,self.filtro);
							} else {
								items=_.filter(response,self.filtro);
							}
							self.collection.reset(items);
						}
					}

				});
			},

			render : function() {
				if (this.template == null) {
					this.template = this
							.loadTemplate('../gec-web/bibliotecas/javascript/componentes/combobox/comboboxTemplate.html');
				}
				this.$el.html(_.template(this.template, {
					collection : this.collection,
					selectedValue : this.selectedValue
				}));

				return this;
			},

			bind : function(id) {
				var obj = $('#' + id);
				var ListaOpcao = this.render().el;
				obj.empty();
				obj.append(ListaOpcao);
			}
		});
