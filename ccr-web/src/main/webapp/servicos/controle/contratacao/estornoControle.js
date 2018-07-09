/**
 * @author F620600
 * 
 * JavaScript que controla as ações das paginas:
 *  alterarIOF.html
 *  
 * @version 1.0.0.0
 * 
 * 
 */
define(['enumeracoes/eMensagemCCR',
        'enumeracoes/eTipoServicos',
        'modelo/contratacao/estorno',
        'text!visao/contratacao/estorno.html',
        'text!visao/contratacao/consultaContrato.html'
        ], 
function(EMensagemCCR, ETipoServicos, EstornoModel, estornoTpl, consultaContratoTpl){

	var _this = null;
	var EstornoControle = Backbone.View.extend({

		className: 'EstornarControle',
		
		/**
		 * Templates
		 */
		estornoTemplate 	  : _.template(estornoTpl),
		filtroContratoTemplate: _.template($(consultaContratoTpl).filter('#divFiltroContratos_01').html()),
		listaContratoTemplate : _.template($(consultaContratoTpl).filter('#divListaContratos_01').html()),
		
		/**
		 * Variaveis da classe
		 */
		validator   : new Validators(),
		message     : new Message(),
		estornoModel: null,
		
		/**
		 * Função que faz bind das ações e interações da pagina com as funções
		 * javascript
		 * 
		 */
		events : {
			'click a#btnConsultar'      : 'consultarContratos',
			'click a#btnContinuar'      : 'estornarContrato',
		},
				
		
		/**
		 * Função padrão de incialização do template html
		 * 
		 */
		initialize : function() {
			console.log("Estorno controle - initialize");
			var amanha = new Date();
			amanha.setDate((new Date()).getDate() + 1);
			
			// pra nao ter problema de pegar outro 'this'
			_this = this;
			
			// configura validator
			_this.validator.addValidator(new DataValidator());
			_this.validator.addValidator(new DataValidatorMenor(formatDate(amanha, 'dd/MM/yyyy')));
			_this.validator.withErrorRender(new BootstrapErrorRender());
			
			// inicia a model
			_this.estornoModel = new EstornoModel();
		},
			
		/**
		 * Função padrão para reenderizar os dados html na tela
		 * Nessa função é carregado a mascara para CPF e colocadao o foco
		 * no campo de CPF
		 * Retorna o proprio objeto criado
		 * @returns {anonymous}
		 */
		render : function() {
			console.log("Estorno - render");	
			
			_this.$el.html(_this.estornoTemplate());
			_this.$el.find('#filtroContrEstorno').html(_this.filtroContratoTemplate());
			
			
			//Carrega as mascaras usadas.
			mascarasEl(_this.$el);
			
			// desabilita os botoes
			_this.$el.find('a.disabled').on('click', function(evt) {
				evt.preventDefault();
				return false;
			});
			
			return _this;
		},
		
		consultarContratos: function () {			
			carregar.iniciar();
			
			// lista contratos			
			//_this.estornoModel.listar()
			//.done(function sucesso(data) {
				//Inclui o template inicial no el deste controle
				_this.$el.find('#listaContrEstorno').html(_this.listaContratoTemplate(/*{listaContratos: data}*/));
				_this.$el.find('#btnContinuar').html('Estornar Contrato');
				
				// configura datatable
				var dataTable = _this.$el.find('#gridListaContratos_01').dataTable({
					'aoColumns' : [ null, null, null, null, null ],
					'aaSorting': [[0, 'desc']]
				});
				
				_this.$el.find('#gridListaContratos_01 tbody').on( 'click', 'tr', function () {
			        if ($(this).hasClass('selected')) {
			            $(this).removeClass('selected');
			        } else {
			        	dataTable.$('tr.selected').removeClass('selected');
			            $(this).addClass('selected');
			        }
			    });
							
				
				carregar.finalizar();
			/*})
			.error(function erro(e) {
				console.log(e);
				_this.message.singleMessage(_this.message.errorMessage("Ocorreu um erro ao listar as taxas ja cadastradas!", false));
				carregar.finalizar();
			});*/
			
		},
				
		estornarContrato: function () {
			_this.$el.find('#divJustEstorno').modal('show');
		},
		
		limparForm: function() {
			_this.validator.withForm('formFiltroEstorno').cleanErrors();			
			_this.$el.find('#formFiltroEstorno')[0].reset();
		},
		
		cancelar: function () {						
			_this.limparForm();
			_this.comando = null;
			
			_this.$el.find('#divFormulario').modal('hide');
		},
		
	
	});

	return EstornoControle;
	
});


