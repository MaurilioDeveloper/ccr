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
        'util/retorno',
        'modelo/contratacao/contrato',
        'modelo/cliente/cliente',
        'controle/contratacao/detalheConsultaCETControle',
        'text!visao/contratacao/manter/detalheContrato.html',
        'text!visao/contratacao/detalheConsultaCET.html',
        'modelo/contratacao/autorizar',
        ], 
function(EMensagemCCR, Retorno, contratoModelo, Cliente, DetalheConsultaCETControle, detalheContratoTpl,detalheConsultaCetTpl, Autorizar){

	var _this = null;
	var DetalheContratoControle = Backbone.View.extend({

		className: 'DetalheContratoControle',
		
		/**
		 * Templates
		 */
		detalheContratoTemplate : _.template(detalheContratoTpl),
		detalheCetTemplate				: _.template(detalheConsultaCetTpl),
		
		/**
		 * Variaveis da classe
		 */
		validator   : new Validators(),
		message     : new Message(),
		comando     : null,
		modelo		: null,
		collection  : null,
		contrato	: null,
		contratoDados: null,
		dfd : 		null,
	
		/**
		 * Função que faz bind das ações e interações da pagina com as funções
		 * javascript
		 * 
		 */
		events : {
			'click #btnVoltar' 		 			: 'voltar',
			'click #btnVoltarModal'				: 'fecharModal',
			'click tr.btn-toggle-popupcet'  	: 'showDadosConsultaCET',
			'click a.btn-close-popupcet'		: 'closeDadosConsultaCET',
			'click a.btn-print-popupcet'  		: 'printDadosConsultaCET',
			'click div#dadosClienteHeading' 	: 'collapse',
			'click div#dadosContratoHeading' 	: 'collapse',
			'click div#historicoHeading' 		: 'collapse',
			'click div#avaliacaoClienteHeading' : 'collapse',
			'click div#avaliacaoOperacaoHeading': 'collapse',
			'click #contratoNegado'				: 'showDetalhesNegado',
		},

		/**
		 * Função padrão de incialização do template html
		 * 
		 */
		initialize : function() {
			console.log("detalhe contrato controle - initialize");
			_this = this;
			_this.modelo = new contratoModelo();
			_this.ctrlConsultaCET = new DetalheConsultaCETControle();
			_this.validator.withErrorRender(new BootstrapErrorRender());
		},
			

		/**
		 * Função padrão para reenderizar os dados html na tela
		 * Nessa função é carregado a mascara para CPF e colocadao o foco
		 * no campo de CPF
		 * Retorna o proprio objeto criado
		 * @returns {anonymous}
		 */
		render : function(e, agenciaConcessora) {
			console.log("autorizar - render");	
			_this.dfd = $.Deferred();
			_this.contrato = $(e.target).data('contrato') == undefined ? $(e.target).parent().data('contrato') : $(e.target).data('contrato');
			_this.consultarContrato(_this.contrato);
			_this.loadDadosAutorizacao(_this.contrato);
			
			_this.dfd.promise().done(function() {
				_this.$el.html(_this.detalheContratoTemplate({contrato: _this.contratoDados, agencia: agenciaConcessora, autorizacoes: _this.autorizacoes}));
				if(_this.contratoDados.avaliacaoOperacao.icBloqueioAlcada == '1'){
					_this.$('#divBloaqueadaAlcada').show();
				}else{
					_this.$('#divBloaqueadaAlcada').hide();
				}
			})
			
			loadMask();
			return _this;
		},
		
		consultarContrato : function(id) {
			_this.modelo.consultarPorId(id).done(function sucesso(data) {
				_this.contratoDados = data;
			})
			.error(function erro(jqXHR) {
				if(jqXHR && jqXHR.responseText && jqXHR.responseText != ""){
					Retorno.exibeErroDetalhe(jqXHR.responseText);
				}else{
					Retorno.trataRetorno({codigo: -1, tipo: "ERRO_EXCECAO", mensagem: "Ocorreu um erro ao buscar dados da empresa!"});
				}
			});
			
			_this.cliente = new Cliente();
			_this.cliente.consultarDadosSicliPorCPF(_this.contratoDados.nuCpf)
			.done(function sucesso(data) {
				if(data != undefined && data.dados != undefined && data.dados.nomeCliente != undefined && data.dados.nomeCliente.nome != undefined)
				_this.contratoDados.nomeCliente = data.dados.nomeCliente.nome;
				_this.dfd.resolve();
			});
		
		},
		
		loadDadosAutorizacao: function(idContrato) {
			_this.modelo = new Autorizar();
			_this.modelo.buscaAutorizacaoPorContrato(idContrato)
			.done(function sucesso(data) {
				_this.autorizacoes = data;
			})
			.error(function erro(jqXHR) {
				console.log(jqXHR);
			});
		},

		voltar: function () {
			DashboardControleCCR.manterContrato();
		},
		
		collapse : function(evt){
			collapse(evt);
		},
		
		showDadosConsultaCET : function(e) {
			_this.ctrlConsultaCET.setDadosCET(_this.contratoDados,'manter');
			_this.dadosCET = _this.ctrlConsultaCET.recuperaDados();
			_this.$el.find('#divModalCETBody').html(_this.detalheCetTemplate({dadosCET: _this.dadosCET}));
			_this.$el.find('#divModalCET').modal('show');
		
		},
		
		closeDadosConsultaCET : function() {
			_this.$el.find('#divModalCET').modal('hide');
		},
		
		printDadosConsultaCET :  function(e) {
			_this.ctrlConsultaCET.printDadosCET();
		},
		
		/**
		 * Busca na Tabela de Autorizações através do codigoSituacao e Numero Contrato
		 * Com isso, e feito uma verificação para ver se o Contrato é do Tipo Conformidade
		 * para assim, retornar para a Modal de Justificativa, seu respectivo valor.
		 */
		showDetalhesNegado: function(event) {
			_this.modelo = new Autorizar();
			$("#justificativa").attr("disabled", true);
			$(_this.autorizacoes).each(function(i, obj) {
				if(obj.codigoSituacao === "N") {
					_this.modelo.buscaAutorizacaoPorSituacao(obj.codigoSituacao, _this.contratoDados.nuContrato, obj.nuSituacaoContrato)
					.done(function sucesso(data){
						$(data).each(function(index, valor) {
							if(valor.icTipoAutorizacao === "G" || valor.icTipoAutorizacao === "C") {
								/**
								 * Verifica Numero situação contrato para abrir a justificativa
								 * daquela especifica
								 */
								if(event.target.attributes[1].nodeValue==obj.nuSituacaoContrato.toString()){
									_this.$el.find("#justificativa").val(valor.deJustificativa);
									return false;
								}
							}
						});
					});
				}
			});
			_this.$el.find('#divFormulario').modal('show');
		},

		fecharModal: function () {						
			_this.$el.find('#divFormulario').modal('hide');
		},
		
		
	});

	return DetalheContratoControle;
	
});


