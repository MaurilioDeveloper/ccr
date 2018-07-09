
define([ 'text!visao/contratacao/gerenciadorLimites.html'
	,'modelo/limite/limite'], 
        
function(GerenciadorTpl, Limite){

	var _this = null;
	var GerenciadorLimitesControle = Backbone.View.extend({

		className: 'GerenciadorLimitesControle',
		
		/**
		 * Templates
		 */
		gerenciadorLimitesTemplate : _.template(GerenciadorTpl),

		
		/**
		 * Variaveis da classe
		 */
		validator   : new Validators(),
		message     : new Message(),
		comando     : null,
		modelo		: null,
		collection  : null,
		acao		: null,

	
		/**
		 * Função que faz bind das ações e interações da pagina com as funções
		 * javascript
		 * 
		 */
		events : {
			'click #btnEnviar' 			: 'enviar',
		},
		
		/**
		 * Função padrão de incialização do template html
		 * 
		 */
		initialize : function() {
			console.log("gerenciadroLimites controle - initialize");
			
			_this = this;
			_this.validator.withErrorRender(new BootstrapErrorRender());
		},
			

		render : function() {
			console.log("gerenciadroLimites - render");	
			loadCCR.start();
			_this.$el.html(_this.gerenciadorLimitesTemplate());

			loadCCR.stop();
			return _this;
			
		},
		
		enviar	: function() {
			
			var envio = {};
			envio.tipoPessoa 	= _this.$el.find('#tpPessoa').val();
			envio.cpfCnpj  	= _this.$el.find('#cpfCnpj').val();
			envio.operacao		= _this.$el.find('#acao').val();
			envio.usuario	= _this.$el.find('#usuario').val();
			envio.vlCPM	= _this.$el.find('#vlCpmUtil').val();
			envio.descProduto= _this.$el.find('#dscProduto').val();
			envio.urlServidor= _this.$el.find('#urlServidor').val();

			_this.getCollection().consultar(envio)
			.done(function sucesso(data) {
				_this.$el.find('#tpPessoaRetorno').val(data.tipoPessoa);
				_this.$el.find('#cpfCnpjRetorno').val(data.cpfCnpj);
				_this.$el.find('#acaoRetorno').val(data.operacao);
				_this.$el.find('#vlCpmUtilRetorno').val(data.vlCPM);
				_this.$el.find('#coRetorno').val(data.codRetorno);
				_this.$el.find('#dscMensagemRetorno').val(data.descMensagem);
				_this.$el.find('#nuAvaliacaoRetorno').val(data.nuAvaliacao);
				_this.$el.find('#dtAvaliacaoRetorno').val(data.dataAvaliacao);
				_this.$el.find('#dtVlldeAvaliacaoRetorno').val(data.dataValidadeAvaliacao);
				_this.$el.find('#conceitoRetorno').val(data.conceito);
				_this.$el.find('#situacaoCliRetorno').val(data.situacaoCliente);
			})
			.error(function erro(jqXHR) {
				console.log(jqXHR);
				Retorno.trataRetorno({codigo: -1, tipo: "ERRO_EXCECAO", mensagem: "Ocorreu um erro ao listar as auditorias!"});
				loadCCR.stop();
			});
			
		},
			
		getCollection: function () {
	    	if (this.collection == null || this.collection == undefined)
	    		this.collection = new Limite();
	    		
	    	return this.collection;
	    },
	    
	});

	return GerenciadorLimitesControle;
	
});


