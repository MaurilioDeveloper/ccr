define(['enumeracoes/eMensagemCCR',
        'enumeracoes/eTipoServicos',
        'util/retorno',       
        'text!visao/wizardConsignado.html',
        'text!visao/contratacao/contratar/contratar/panelDadosCliente.html',
        'text!visao/contratacao/contratar/contratar/panelDadosContrato.html',
        'text!visao/contratacao/contratar/contratar/panelDadosConta.html',
        'controle/contratacao/wizardConsignadoControle',
        'modelo/convenios/convenio',
        'modelo/contratacao/contrato',
        'modelo/cliente/cliente',
        'text!visao/contratacao/detalheConsultaCET.html',
        'controle/contratacao/detalheConsultaCETControle',
        ], 
function(EMensagemCCR, ETipoServicos, Retorno, wizardConsignadoTpl, dadosClienteTpl, dadosContratoTpl, dadosContaTpl, wizardConsignadoControle, Convenio, Contrato, Cliente, detalheConsultaCetTpl, DetalheConsultaCETControle){

	var _this = null;
	var ContratarConsignadoContratar = Backbone.View.extend({

		className: 'contratarConsignadoContratar',
		
		/**
		 * Templates
		 */
		wizardConsignadoTemplate	: _.template(wizardConsignadoTpl),
		dadosClienteTemplate	  	: _.template(dadosClienteTpl),
		dadosContratoTemplate	  	: _.template(dadosContratoTpl),
		dadosContaTemplate		  	: _.template(dadosContaTpl),
		detalheCetTemplate			: _.template(detalheConsultaCetTpl),
		
		/**
		 * Variaveis da classe
		 */
		validator   : new Validators(),
		message     : new Message(),
		comando     : null,
		modelo		: null,
		collection  : null,
		acao		: null,
		contrato	: null,
		contratoDados	: null,
		cliente		    : null,
		numeroContrato  : null,
		contratoModel : null,
		
		/**
		 * Função que faz bind das ações e interações da pagina com as funções
		 * javascript
		 * 
		 */
		events : {
			'click #controleDatasContrato' 	: 'controleDatasContrato',	
			'click #controleTaxasContrato' 	: 'controleTaxasContrato',
			'click #btnContratar' 			: 'contratar',
			'click #btnImprimirContrato' 	: 'abrirImprimir',
			'click a#btnImprimir'			: 'contruirImpressao',
			'click a#btnFecharImpressao'	: 'fecharImpressao',
			'click td.btn-toggle-popupcet'  : 'showDadosConsultaCET',
			'click a.btn-close-popupcet'	: 'closeDadosConsultaCET',
			'click a.btn-print-popupcet'  	: 'printDadosConsultaCET',
			'click a#contratacaoConfirmada' : 'confirmaContratacao',
			'click a#contratacaoNegada'		: 'negaContratacao'
		},
				
		
		/**
		 * Função padrão de incialização do template html
		 * 
		 */
		initialize : function() {
			console.log("contratarConsignadoContratar - initialize");
			
			_this = this;			
			
			_this.validator.addValidator(new DataValidator());			
			_this.validator.withErrorRender(new BootstrapErrorRender());
			_this.ctrlConsultaCET = new DetalheConsultaCETControle();
		},
		
		
		render : function(data, cliente, contrato) {
			_this.contratoSalvo = contrato;
			_this.cliente = cliente;
			_this.contratoDados = data;
			_this.convenioDados = data.convenio;
			var dadosSimulacao;
			console.log("contratarConsignadoContratar - render");
			if(_this.numeroContrato != null) {
				$("#btnAvancar").show();
			}
			if(data.cliente == undefined) {
				data.cliente = {};
				data.cliente.attributes = {};
				data.cliente.attributes.dados = cliente;
				data.idConvenio = data.convenio.id;
				data.cpf = data.cliente.attributes.dados.cpf.documento.numero + data.cliente.attributes.dados.cpf.documento.digitoVerificador;	
				dadosSimulacao = data;
				data.simulacao.attributes = dadosSimulacao;
//				data.simulacao.attributes.simulacao = data.simulacao;
			} else {
//				_this.avaliacoes = data.avaliacoes;
			}
			var produtosCaixa = $.extend(true, [], data.cliente.attributes.dados.produtoCaixa);
			var contasCorrentes = [];
			_this.convenio = new Convenio();
			var agenciaConta;
			var el = _this.$el;
			
			$.each(produtosCaixa, function (index, produtoCaixa) {				
				if (produtoCaixa == null){
					return true;					
				}else {
						
						agenciaConta =  produtoCaixa.contrato.substring(0,4)+"."+
										produtoCaixa.contrato.substring(4,7)+"."+
										produtoCaixa.contrato.substring(7,15)+"-"+
										produtoCaixa.contrato.substring(15);
						
						var obj ={};
						obj.id = index;
						obj.descricao = agenciaConta;
						contasCorrentes.push(obj);									
				}
			});
			
			data.cliente.contasCorrentesLst = $.extend(true, [], contasCorrentes);
			
			_this.$el.html(_this.dadosClienteTemplate({dados: data}));
			_this.$el.find('#divDadosContrato').html(_this.dadosContratoTemplate({dados: data.simulacao.attributes}));
			_this.$el.find('#divDadosConta').html(_this.dadosContaTemplate({dados: data}));

			if(data.avaliacao.bloqueadoAlcada == 1){
				data.dados.avaliacao.bloqueadoAlcada = "1 - Sim";
				$('#divBloqueioAlcada').show();
			}
			
			gCarregaComboContaCorrente(_this.$el, "contasCorrentes", null, data.cliente.contasCorrentesLst);
			rolarPaginaParaCima();
			$('#btnAvancar').hide();
			$('#btnContratar').show();
			return _this;
		},
		

		abrirImprimir: function(e) {
			_this.contratoModel = new Contrato();
			_this.contratoModel.buscarTipoDocumentoImpressaoCCR().done(function sucesso(data) {
				Retorno.trataRetorno(data, 'documento', null, false);
				
				if(data.tipoRetorno == "ERRO_EXCECAO" ){
					Retorno.trataRetorno({codigo: -1, tipo: "ERRO_EXCECAO", mensagem: "Ocorreu um erro ao imprimir!"}, '', jqXHR);
				}
			
				_this.idConvenio = _this.$el.find('#convenio').text();
				
				_this.$el.find('#numContrato').text(_this.contrato.attributes.nuContrato);
				_this.$el.find('#nomeCliente').text(_this.contratoDados.cliente.attributes.nomeCliente.nome);
				_this.$el.find('#divModalImprimir').modal('show');
				loadMaskEl(_this.$el);
				if(data.icModeloTestemunha == "A"){
					_this.impressaoTipoCCBTrue();
				
				}else{
					_this.impressaoTipoCCBFalse();
				}
			})
			.error(function erro(jqXHR) {
				Retorno.trataRetorno({codigo: -1, tipo: "ERRO_EXCECAO", mensagem: "Ocorreu um erro ao imprimir!"}, '', jqXHR);
			});
			_this.validator.withForm('contratarConsignado').cleanErrors();
		},
		
		
		impressaoTipoCCBTrue : function (){
			_this.$el.find('#inputImprimiGerenteResp').removeAttr('validators', 'required');
			_this.$el.find('#inputImprimiCPFGerente').removeAttr('validators', 'required');
			_this.$el.find('#inputImprimiTestemunha1').removeAttr('validators', 'required');
			_this.$el.find('#inputImprimiCPFTestemunha1').removeAttr('validators', 'required');
			_this.$el.find('#inputImprimiTestemunha2').removeAttr('validators', 'required');
			_this.$el.find('#inputImprimiCPFTestemunha2').removeAttr('validators', 'required');
			
			_this.$el.find('#spanImprimiGerenteResp')[0].innerHTML = "";
		    _this.$el.find('#spanImprimiCPFGerenteResp')[0].innerHTML = "";
			_this.$el.find('#spanImprimiTestemunha1')[0].innerHTML = "";
			_this.$el.find('#spanImprimiCPFTestemunha1')[0].innerHTML = "";
			_this.$el.find('#spanImprimiTestemunha2')[0].innerHTML = "";
			_this.$el.find('#spanImprimiCPFTestemunha2')[0].innerHTML = "";
		},
		
		impressaoTipoCCBFalse : function (){
			_this.$el.find('#inputImprimiGerenteResp').attr('validators', 'required');
			_this.$el.find('#inputImprimiCPFGerente').attr('validators', 'required');
			_this.$el.find('#inputImprimiTestemunha1').attr('validators', 'required');
			_this.$el.find('#inputImprimiCPFTestemunha1').attr('validators', 'required');
			_this.$el.find('#inputImprimiTestemunha2').attr('validators', 'required');
			_this.$el.find('#inputImprimiCPFTestemunha2').attr('validators', 'required');
			
			_this.$el.find('#spanImprimiGerenteResp')[0].innerHTML = "*";
		    _this.$el.find('#spanImprimiCPFGerenteResp')[0].innerHTML = "*";
			_this.$el.find('#spanImprimiTestemunha1')[0].innerHTML = "*";
			_this.$el.find('#spanImprimiCPFTestemunha1')[0].innerHTML = "*";
			_this.$el.find('#spanImprimiTestemunha2')[0].innerHTML = "*";
			_this.$el.find('#spanImprimiCPFTestemunha2')[0].innerHTML = "*";
		},
		
		
		contruirImpressao : function(){
			
			_this.validator.withForm('contratarConsignado').cleanErrors();
			
			_this.$el.find('#matriculaCliente').remove('validators','cpf');
			_this.$el.find('#contasCorrentes').remove('validators','cpf');
			
			if(_this.validator.withForm('contratarConsignado').validate()) {
					var cpfClienteFormat = String(_this.$el.find('#cpfCliente').text()).replace(/[.\-\/]/g, '');
					_this.buscaDadosSicli(cpfClienteFormat);	
			}
			
		},

		imprimir : function(dadosCliente){
			var cpfClienteFormat = String(_this.$el.find('#cpfCliente').text()).replace(/[.\-\/]/g, '');
			var cpfTestemunha1Format = String(_this.$el.find('#inputImprimiCPFTestemunha1').val()).replace(/[.\-\/]/g, '');
			var cpfTestemunha2Format = String(_this.$el.find('#inputImprimiCPFTestemunha2').val()).replace(/[.\-\/]/g, '');
			var cpfGerenteFormat = String(_this.$el.find('#inputImprimiCPFGerente').val()).replace(/[.\-\/]/g, '');

			var impressao= {
				numContrato  			:	_this.$el.find('#numContrato').text(),
				cpfCliente 				:	cpfClienteFormat,
				nomeCliente 			:	_this.$el.find('#nomeCliente').text(),
				localImpressaoContrato 	:	_this.$el.find('#inputImprimiLocal').val(),
				nomeGerente 			:	_this.$el.find('#inputImprimiGerenteResp').val(),
				nomeTestemunha1 		:	_this.$el.find('#inputImprimiTestemunha1').val(),
				cpfTestemunha1 			:	cpfTestemunha1Format,
				nomeTestemunha2 		:	_this.$el.find('#inputImprimiTestemunha2').val(),
				cpfTestemunha2 			:	cpfTestemunha2Format,
				sicli                   :   dadosCliente, 
				cpfGerente              :   cpfGerenteFormat,
				
			};
			_this.contrato = new Contrato();
			_this.contrato.imprimirContrato(impressao).done(function sucesso(data) {
				Retorno.trataRetorno(data, 'documento', null, false);
				
				if(data.tipoRetorno == "ERRO_EXCECAO" ){
					_this.$el.find('#divModalImprimir').modal('hide');
					Retorno.trataRetorno({codigo: -1, tipo: "ERRO_EXCECAO", mensagem: "Ocorreu um erro ao imprimir!"}, '', jqXHR);
					loadCCR.stop();
				}

				for (i = 0; i < data.contratosImpressao.length; i++) { 
					newWin = window.open("");
					newWin.document.write(data.contratosImpressao[i]);
					newWin.print();
					newWin.close();
				}
				
				if(data.contratosImpressao.length == 0){
					_this.$el.find('#divModalImprimir').modal('hide');
					rolarPaginaParaCima();
					Retorno.trataRetorno({codigo: 0, tipo: "AVISO", mensagem: "", idMsg: 'MA004'}, 'documento');
				}
				
				Retorno.trataRetorno({codigo: 1, tipo: "ERRO_EXCECAO", mensagem: "Ocorreu um erro ao imprimir!"}, 'manterGrupo', jqXHR);
			})
			.error(function erro(jqXHR) {
				_this.$el.find('#divModalImprimir').modal('hide');
				Retorno.trataRetorno({codigo: -1, tipo: "ERRO_EXCECAO", mensagem: "Ocorreu um erro ao imprimir!"}, '', jqXHR);
				loadCCR.stop();
			});
		},
		
		buscaDadosSicli : function(cpf) {
			_this = this;
			//valida sicli
			_this.modeloCliente = new Cliente();
			
			_this.modeloCliente.consultarDadosSicliPorCPF(cpf).done(function sucesso(data) {		
				console.log(data);
				loadCCR.stop();
				
				if (data.dados.responseArqRef.status.codigo == 0) {
					_this.imprimir(data.dados);	
				}else {
					//Valida o retorno do SICLI, caso erro sobe a msg.
					Retorno.validarRetorno(data);
				}
				
			})
			.fail(function erro(jqXHR) {
				_this.gestor  = "false";
			});
		},
		
		
		fecharImpressao : function(){
			_this.validator.withForm('contratarConsignado').cleanErrors();
			_this.$el.find('#divModalImprimir').modal('hide');
		},
		
		controleDatasContrato : function() {
			console.log("contratarConsignadoContratar - controleDatasContrato");
			
			if (_this.mostrarDatas){
				_this.$el.find("#datasContrato").hide();
				_this.mostrarDatas = false;
			}else{
				_this.$el.find("#datasContrato").show();
				_this.mostrarDatas = true;
			}			
		},
		
		controleTaxasContrato : function() {
			console.log("contratarConsignadoContratar - controleTaxasContrato");
			
			if (_this.mostrarTaxas){
				_this.$el.find("#taxasContrato").hide();
				_this.mostrarTaxas = false;
			}else{
				_this.$el.find("#taxasContrato").show();
				_this.mostrarTaxas = true;
			}			
		},
		
		contratar : function(){
			if (_this.validator.withForm('contratarConsignado').validate()){
				$("#confirmContratacao").modal('show');
			}	
		},
		
		
		confirmaContratacao : function() {
			_this.contrato = new Contrato();
			
			_this.popularContrato();
			
			// Status: Aguardando Autorizações
			var id = 2;
			
			// Tipo: Contração
			var tipo = 2;
			var descricao = "";

//			var contratoSituacao = {
//				nuContrato : _this.contratoSalvo.nuContrato,
//				situacao : {
//					id : id,
//					tipo :tipo,
//					descricao : descricao
//				}
//			}

			
			var situacao = {
				id : id,
				tipo :tipo,
				descricao : descricao
			}
				

			_this.contrato.attributes.situacao = situacao;
//			_this.contrato.attributes.avaliacaoRequest = _this.avaliacoes;
			
//			_this.contrato.atualizarSituacaoContrato(contratoSituacao).done(function sucesso(data) {
			_this.contrato.atualizarContrato(_this.contrato.attributes).done(function sucesso(data) {

				if(data.tipoRetorno == 'ERRO_EXCECAO'){
					Retorno.trataRetorno({codigo: -1, tipo: "AVISO", mensagem: "Ocorreu um erro ao atualizar o contrato!"});
					rolarPaginaParaCima();
				}else{					
					Retorno.trataRetorno({codigo: 0, tipo: "SUCESSO", mensagem: "", idMsg: 'MA001'}, 'contrato');
					rolarPaginaParaCima();
					
					$('#btnImprimirContrato').show();
					$('#btnVoltar').show();
					$('#btnAvancar').show();
					$('#btnSair').show();
					$('#btnContratar').hide();
					$('#contratoCabecalho').show();
					$(".btnConsultar").attr('disabled', true);
					$("#margemConsignado").attr('disabled', true);
					$("#btnAlterarDadosSICLI").attr('disabled', true);
					$("#radioConvenioCont").attr('disabled', true);
					$("#matriculaCliente").attr('disabled', true);
					$("#contasCorrentes").attr('disabled', true);
					$("#btnRecalcular").attr('disabled', true);
					$(".btnRecalcularAvaliar").hide();
					$("#numeroCPF").attr('disabled', true);
					$("#semSeguro").attr('disabled', true);
					
					$("#comSeguro").attr('disabled', true);
					$('#numeroContrato').text(_this.contratoSalvo.nuContrato);
					
					_this.numeroContrato = _this.contratoSalvo.nuContrato;
					
				}
				
				$("#confirmContratacao").modal('hide');
			})
			.error(function erro(jqXHR) {
				if(jqXHR && jqXHR.responseText && jqXHR.responseText != ""){
					Retorno.exibeErroDetalhe(jqXHR.responseText);
				}else{
					Retorno.trataRetorno({codigo: -1, tipo: "ERRO_EXCECAO", mensagem: "Ocorreu um erro ao salvar o contrato!"});
				}
			});
		},
		
		
		stepclicked: function (e) {
			console.log(e);
		},

		negaContratacao : function() {
			$("#confirmContratacao").modal('hide');
		},
		
		showDadosConsultaCET : function(e) {
			console.log("Simular Consignado - showDadosConsultaCET");
			
			_this.ctrlConsultaCET.setDadosCET(_this.contratoDados.simulacao.attributes.simulacao,'simular');				
			_this.dadosCET = _this.ctrlConsultaCET.recuperaDados();
			if(_this.contrato == null) {
				_this.contrato = {};
				_this.dadosCET.nuContrato = _this.contrato.nuContrato;
			}
			if(_this.contratoDados.avaliacoes == null) {
				_this.dadosCET.noProduto = 	_this.contratoDados.avaliacao.produto;
			} else {
				_this.dadosCET.noProduto = _this.contratoDados.avaliacoes[0].produto;	
			}
			_this.$el.find('#divModalCETBodyThree').html(_this.detalheCetTemplate({dadosCET: _this.dadosCET}));
			_this.$el.find('#divModalCET').modal('show'); 
			$('#divModalCETBodyThree').animate({ scrollTop: 0 }, 'slow');
		},
		
		closeDadosConsultaCET : function() {
			console.log("Simular Consignado - closeDadosConsultaCET");
			_this.$el.find('#divModalCET').modal('hide');
		},
		
		printDadosConsultaCET :  function(e) {
			console.log("Simular Consignado - printDadosConsultaCET");
			_this.ctrlConsultaCET.printDadosCET();
		},

		formatarData : function (data){
			var date = data.split("/");
			return new Date(date[2], date[1] - 1, date[0]);
		},
		
		recuperarContrato : function(){
			return _this.numeroContrato;
		},
		
		popularContrato : function(){
					

			_this.contratoDados.cliente.attributes = _this.cliente;
			_this.contrato.attributes.sicliCliente = JSON.stringify(_this.cliente);

			_this.contrato.attributes.nuContratoAplicacao = '000';
			_this.contrato.attributes.icTipoCredito = '1';
			
			_this.contrato.attributes.nuAgenciaConcessora = _this.contratoDados.agencia.idUnidade;
			_this.contrato.attributes.nuNaturalMovimento = _this.contratoDados.agencia.numNatural;
			_this.contrato.attributes.nuUnidadeMovimento = _this.contratoDados.agencia.idUnidade;
			_this.contrato.attributes.nuUnidadeMovimento = _this.contratoDados.agencia.idUnidade;
			_this.contrato.attributes.nuCpf = _this.contratoDados.cpf != null ? String(_this.contratoDados.cpf).replace(/[.\-\/]/g, '') : 0;
			_this.contrato.attributes.nuCocli = _this.contratoDados.cliente.attributes.cocliAtivo.cocliAtivo;
			_this.contrato.attributes.coMatriculaCliente = _this.$el.find('#matriculaCliente').val();
			_this.contrato.attributes.nuContrato = _this.contratoSalvo.nuContrato; 
			
			//Set Canal Atendimento (1 - agencia)
			_this.contrato.attributes.canalAtend = 1;			
			
			var rendas = _this.contratoDados.cliente.attributes.renda;
			
			for(i= 0 ; i < rendas.length ; i++){
			if(rendas[i].cpfCnpjFontePagadora == _this.convenioDados.cnpjFontePagadora){
				_this.contrato.attributes.vrBrutoBeneficio = rendas[i].valorRendaBruta;
				_this.contrato.attributes.vrLiquidoBeneficio =rendas[i].valorRendaLiquida;
				break;
			}
		}
			
			//Dados do Convenio
			var convenioVo = {};
			convenioVo.id = _this.convenioDados.id;
			convenioVo.cnpjConvenente = _this.convenioDados.cnpjConvenente;
			convenioVo.nome = _this.convenioDados.nome;
			convenioVo.numContaCredito = _this.convenioDados.numContaCredito;
			convenioVo.numAgenciaContaCredito = _this.convenioDados.numAgenciaContaCredito;
			
			_this.contrato.attributes.qtEmpregado = _this.convenioDados.qtEmpregado;
			_this.contrato.attributes.ddBeneficio = _this.convenioDados.diaCreditoSal;
            _this.contrato.attributes.nuCnpjFontePagadora = _this.convenioDados.cnpjFontePagadora;
            _this.contrato.attributes.nuAgenciaContaDebito = _this.convenioDados.numAgenciaContaCredito;
            _this.contrato.attributes.nuContaDebito = _this.convenioDados.numContaCredito;
            _this.contrato.attributes.nuDvContaDebito = _this.convenioDados.numDvContaCredito;
            _this.contrato.attributes.nuOperacaoContaDebito = _this.convenioDados.nuOperacaoContaDebito;
            	
			_this.contrato.attributes.convenioTO = convenioVo;
			//Dados Simulacao
			
			var simulacao = _this.contratoDados.simulacao.attributes;
			_this.contrato.attributes.pcTaxaSeguro = simulacao.simulacao.pcTaxaSeguro;
			_this.contrato.attributes.vrContrato = simulacao.simulacao.valorContrato;
			_this.contrato.attributes.vrTotalContrato = simulacao.simulacao.valorTotalContrato;
			_this.contrato.attributes.vrLiquidoContrato = simulacao.simulacao.valorLiquido;
//			_this.contrato.attributes.vrPrestacao = simulacao.simulacao.prestacao.replace(",","."); //.replace(/\./g, "").replace(",",".");
//			_this.contrato.attributes.vrPrestacao =  simulacao.simulacao.prestacao.replace('.', '').replace(',','.');
			
			if(simulacao.simulacao.prestacao.indexOf('.') != -1) {
				if(simulacao.simulacao.prestacao.indexOf(',') != -1) {
					_this.contrato.attributes.vrPrestacao =  simulacao.simulacao.prestacao.replace('.', '').replace(',','.');
				} else {
					_this.contrato.attributes.vrPrestacao =  simulacao.simulacao.prestacao;
				}
			} else {
				_this.contrato.attributes.vrPrestacao =  simulacao.simulacao.prestacao.replace('.', '').replace(',','.');
			}
				
			_this.contrato.attributes.vrIof = simulacao.simulacao.iof;
			_this.contrato.attributes.vrJuroAcerto = simulacao.simulacao.juroAcerto;
			_this.contrato.attributes.vrSeguroPrestamista = simulacao.simulacao.valorSeguro;
			_this.contrato.attributes.qtDiaJuroAcerto = simulacao.camposComuns.quantDiasJurosAcerto
			_this.contrato.attributes.pzContrato = simulacao.prazo;
			_this.contrato.attributes.pcTaxaJuroContrato = simulacao.simulacao.taxaJuros;
			_this.contrato.attributes.pcCetMensal = simulacao.simulacao.cetmensal;
			_this.contrato.attributes.pcCetAnual = simulacao.simulacao.cetanual;
			_this.contrato.attributes.pcCetJuroAcerto = simulacao.simulacao.cetjuroAcerto;
			_this.contrato.attributes.pcCetIof = simulacao.simulacao.cetiof;
			_this.contrato.attributes.pcTaxaJuroAnual = simulacao.simulacao.taxaJuros;
			_this.contrato.attributes.pcTaxaEfetivaAnual = simulacao.simulacao.taxaEfetivaAnual;
			_this.contrato.attributes.pcTaxaEfetivaMensal = simulacao.simulacao.taxaEfetivaMensal;
			_this.contrato.attributes.vrCetAnual = simulacao.simulacao.vrCetAnual;
			_this.contrato.attributes.vrCetMensal = simulacao.simulacao.vrCetMensal;
//			_this.contratoDados.avaliacoes[0].nuAvaliacao = _this.contratoDados.nuAvaliacaoOperacao;
			_this.contrato.attributes.avaliacaoCliente = _this.contratoDados.avaliacaoCliente;
			_this.contrato.attributes.avaliacaoOperacao = _this.contratoDados.avaliacaoOperacao;
			
			if(simulacao.tipoSeguro == 1) {
				_this.contrato.attributes.icSeguroPrestamista = "S";
				_this.contrato.attributes.pcCetSeguro = simulacao.simulacao.cetseguro;
			} else {
				_this.contrato.attributes.icSeguroPrestamista = "N";
			}
		
			_this.contrato.attributes.pcAliquotaBasica = simulacao.camposComuns.iofAliquotaBasica;
			_this.contrato.attributes.pcAliquotaComplementar = simulacao.camposComuns.iofAliquotaComplementar;
			
			
			//Datas
			_this.contrato.attributes.dtVncmoPrimeiraPrestacao = _this.formatarData(simulacao.camposComuns.vencimentoPrimeiraPrestacao);
			
			if(simulacao.dataContratacao != undefined) {
				_this.contrato.attributes.dtContrato = _this.formatarData(simulacao.dataContratacao);
			} else {
				var date = new Date(_this.contratoSalvo.dtContrato);
				date = date.getDay() + "/" + (date.getMonth() + 1) + "/" + date.getFullYear();
				_this.contrato.attributes.dtContrato = _this.formatarData(date);
			}
			_this.contrato.attributes.dtBaseCalculo = _this.formatarData(simulacao.camposComuns.basePrimeiraPrestacao);
			_this.contrato.attributes.dtVencimento = _this.formatarData(simulacao.simulacao.vencimentoContrato);
			
			// Dados Avaliacao
			
			/*var avaliacaoCliente = {};
			avaliacaoCliente.nuAvaliacao = _this.contratoDados.avaliacoes.avaliacaoCliente.codigo;
			avaliacaoCliente.noProduto = _this.contratoDados.avaliacoes.avaliacaoCliente.produto;
			avaliacaoCliente.noResultado = _this.contratoDados.avaliacoes.avaliacaoCliente.resultado;
			avaliacaoCliente.vrMaximoEmprestimo = _this.contratoDados.avaliacoes.avaliacaoCliente.limiteGlobal;
			
			var avaliacaoOperacao = {};
			avaliacaoOperacao.nuAvaliacao = _this.contratoDados.avaliacoes.avaliacaoOperacao.idAvaliacao;
			avaliacaoOperacao.nuPrazoMxmoEmprestimo = _this.contratoDados.avaliacoes.avaliacaoOperacao.prazo;
			avaliacaoOperacao.nuRatingAvaliacao = _this.contratoDados.avaliacoes.avaliacaoOperacao.rating;			
			avaliacaoOperacao.vrMaximoEmprestimo = _this.contratoDados.avaliacoes.avaliacaoOperacao.limiteGlobal;
			avaliacaoOperacao.icBloqueioAlcada = _this.contratoDados.avaliacoes.avaliacaoOperacao.bloqueadoAlcada;		
			avaliacaoOperacao.dtInicoAvaliacao = _this.formatarData(_this.contratoDados.avaliacoes.avaliacaoOperacao.inicioValidade);
			avaliacaoOperacao.dtFimAvaliacao =  _this.formatarData(_this.contratoDados.avaliacoes.avaliacaoOperacao.fimValidade);

			_this.contrato.attributes.avaliacaoCliente = avaliacaoCliente;
			_this.contrato.attributes.avaliacaoOperacao = avaliacaoOperacao;
			_this.contrato.attributes.nuAvaliacao = avaliacaoOperacao;*/
			
			var conta = _this.$el.find('#contasCorrentes').find(':selected').text()			
			_this.contrato.attributes.nuAgenciaContaCredito = conta.substring(0,4);
			_this.contrato.attributes.nuOperacaoContaCredito = conta.substring(5,8);
			_this.contrato.attributes.nuContaCredito = conta.substring(9,17);
			_this.contrato.attributes.nuDvContaCredito = conta.substring(18);
			
			return _this.contrato;
		}
	});

	return ContratarConsignadoContratar;
	
});