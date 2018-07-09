/**
 * @author TIVIT
 * 
 * JavaScript para o convenio
 * 
 * @version 1.0.0.0
 * 
 * 
 */
define(['configuracoes', 
        'modelo/convenios/convenio'], 
function(config, Convenio){
	
	var _this = null;	
	var ManterConvenioColecao = Backbone.Collection.extend({

		//url de para chamada do servico rest padrao
		urlRoot: config['urlBaseRest'] + 'convenio',
		url: '',
		model: Convenio,
		tipoConsulta: "", 
			
		initialize : function () {
			console.log("Collection Convenio - initialize");
			//variavel auxiliar para uso em funcoes internas
			//vand _this = this;			
		},
		
		listar: function(tipoConsulta, id, cnpj, nome, situacao, sr, agencia) {
			_this = this;
			
	    	//Para os atributos de tipo primitivo, DEVEM ser setados os valores "default", quando não usados, para não dar erro no REST. 
	    	
			switch (tipoConsulta) {
			case '1': // Convenio
				nome = nome != null ? String(nome).replace(/['"%]/g, '') : null;
				
				if (id != null && !isNaN(id) && id > 0) {
					if (situacao != null && !isNaN(situacao) && situacao == "") {
						_this.url = _this.urlRoot + "/listar?tipoConsulta="+tipoConsulta+"&id="+id+"&cnpj=0&situacao=0&sr=0&agencia=0&nome="+nome;
						
					}else{
						_this.url = _this.urlRoot + "/listar?tipoConsulta="+tipoConsulta+"&id="+id+"&cnpj=0&situacao="+situacao+"&sr=0&agencia=0&nome="+nome;
					}
						    	
				} else {
					if (situacao != null && !isNaN(situacao) && situacao > 0) {
						_this.url = _this.urlRoot + "/listar?tipoConsulta="+tipoConsulta+"&id=0&cnpj=0&sr=0&agencia=0&nome="+nome+"&situacao="+situacao;	    	
					} else {
						_this.url = _this.urlRoot + "/listar?tipoConsulta="+tipoConsulta+"&id=0&cnpj=0&situacao=0&sr=0&agencia=0&nome="+nome;	    	
					}
				}
				break;

			case '2': // Convenente
				cnpj = (cnpj != null && cnpj != "")  ? String(cnpj).replace(/[.\-\/]/g, '') : 0;
				_this.url = _this.urlRoot + "/listar?tipoConsulta="+tipoConsulta+"&id=0&situacao=0&sr=0&agencia=0&cnpj="+cnpj;
				break;
				
			case '3': // Unidade
				sr = (sr != null && sr != "") ? String(sr).replace(/[.\-\/]/g, '') : 0;
				agencia = (agencia != null && agencia != "") ? String(agencia).replace(/[.\-\/]/g, '') : 0;
				_this.url = _this.urlRoot + "/listar?tipoConsulta="+tipoConsulta+"&sr="+sr+"&agencia="+agencia;	    	
				
				break;
				
			default:
				break;
			}	    	
			return _this.fetch({
			    type: "GET",
			    contentType: "application/json"
			});
			
		},		
		listarConvenioPorFontePagadora : function (listaRenda){
			_this = this;

			_this.url = _this.urlRoot + "/listarConvenioPorFontePagadora";
			return _this.fetch({
			    type: "POST",
			    contentType: "application/json",
				data: JSON.stringify(listaRenda),
			});
		},
		consultarConveniosParaSimulacao : function (cnpj, coConvenio, noConvenio, canais){
			_this = this;

			cnpj = (cnpj != null && cnpj != "")  ? String(cnpj).replace(/[.\-\/]/g, '') : 0;
			coConvenio = (coConvenio != null && coConvenio != "") ? coConvenio : 0;
			noConvenio = (noConvenio != null) ? noConvenio : "";
			
			_this.url = _this.urlRoot + "/consultarConveniosParaSimulacao?cnpj="+cnpj
													+ "&coConvenio="+coConvenio
													+ "&noConvenio="+noConvenio
													+ "&canais="+canais;

			return _this.fetch({
			    type: "GET",
			    contentType: "application/json",
			});
		},

		/*vand comentei para testar o método criado acima. este trecho de validação abaixo terá q ficar no controller listar: function(){
			//vand
			_this = this;
			
			if (_this.validator.withForm('formFiltroConvenente').validate()){
				var id = _this.$el.find('#id').val(),
			    	cnpj = _this.$el.find('#cnpj').val(),
			    	nome = _this.$el.find('#nome').val(),
			    	situacao = _this.$el.find('#situacao').val(),
			    	sr = _this.$el.find('#sr').val(),
			    	agencia = _this.$el.find('#agencia').val(),
			    	tipoConsulta = _this.$el.find('input[name="rdoIdentificador"]:checked').val();
				
				// validacoes a parte
				_this.$el.find('#cnpj').attr('validators', 'cnpj');
				if ($.trim(cnpj) == '' || _this.validator.withInput('cnpj').validate()) {					
					loadCCR.start();
					
					_this.modelo = new Convenio();					
					_this.modelo.consultar(tipoConsulta, id, cnpj, nome, situacao, sr, agencia)
					.done(function (data) {
						
						if (data == null) {
							Retorno.trataRetorno({codigo: 0, tipo: "AVISO", mensagem: "", idMsg: 'MA001'}, 'convenio');
							loadCCR.stop();
							return;
						}						
						
						if (!Retorno.trataRetorno(data, 'convenio', undefined, false)) {
							loadCCR.stop();
							return;
						}
						
						_this.convenio = data;
						
						if (tipo == 'C') {
//							_this.$el.find('#divDetalheConvenente').html(_this.detalheConvenenteTemplate({convenio: _this.convenio}));
							//vand teste			
							_this.$el.find('#divlistaConvenioTpl').html(_this.manterListaConvenenteTemplate({convenio: _this.convenio}));
							
						} else {
							_this.$el.find('#divDetalheConvenente').html(_this.manterConvenenteTemplate({convenio: _this.convenio}));
							_this.carregarCombos();
							loadMask(_this.$el.find('#divDetalheConvenente'));
						}							
						
						_this.$el.find('#divFiltroConvenente').hide();
						_this.$el.find('#divDetalheConvenente').show();
						
						// habilita popovers
						_this.habilitaPopovers();	        
				        
				        // chama tratamento compatibilide accordion
				        _this.compatibilidadeAccordion();
				        
				        // inicia aberto
				        _this.$el.find('#collapseTwo').show();
				        _this.$el.find('#collapseThree').show();
				        						
						loadCCR.stop();
					})
					.fail(function (jqXHR) {
						loadCCR.stop();
						Retorno.trataRetorno({codigo: -1, tipo: "ERRO_EXCECAO", mensagem: "Ocorreu um erro ao consultar o convenio!"}, 'convenio', jqXHR);
					});
					
					// volta validador
					_this.$el.find('#cnpj').attr('validators', 'formComposto');
				}
			}		
		},*/
		
		parse: function(data){
		    return data["listaRetorno"];
		}		
		
	});
	
	return ManterConvenioColecao;

});