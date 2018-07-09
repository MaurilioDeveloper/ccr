/**
 * @author F620600
 * 
 * JavaScript para o convenio
 * 
 * @version 1.0.0.0
 * 
 * 
 */
define(['configuracoes'], 
function(config){
	
	var _this = null;
	var ConvenioModelo = Backbone.RelationalModel.extend({
		//url de para chamada do servico rest padrao
		urlRoot: config['urlBaseRest'] + 'Administracao/convenio/',
		url: '',
			
		idAttribute: '_id', 
			
		/***
		 * Criacao de valores padroes
		 */
	    defaults : {
	    	id: 0,
	    	nome: "",
	    	convenente: { 
    		  cnpj: 0,
    		  razaoSocial: "",
    		  logradouro: "",
    		  complemento: "",
    		  numero: "",
    		  bairro:"",
    		  cidade: "",
    		  uf: "",
    		  cep: 0,
    		  complementoCep: 0,
    		  qtEmpregado: 0
    		},
	    	grupo: {
    		  id: "", 
    		  nome: ""
	    	},
	    	remessa: {
    		  id: 0,
    		  nome: ""
    		},
	    	situacao: {
    		  id: 1,
    		  tipo: 1,
    		  descricao:""
    		},
    		canais: [{
    			idConvenio: 0,	
    			canal: {
    				id: "",
    				nome: ""
    			},    		
    			permiteContratacao: "N",	
    			permiteRenovacao: "N",	
    			taxaJuroPadrao: "",	
    			exigeAutorizacao: "N",	
    			minimoExigeAutorizacao: 0,	
    			qtDiaMaxSimulacaoFutura: 0
    		}],    		
	    	agenciaContaCredito: 0,
	    	operacaoContaCredito: 0,
	    	contaCredito:0,
	    	dvContaCredito: 0,
	    	diaCreditoSal: 0,
	    	prazoMaximo: 0,
	    	naturalSRResp: 0,
	    	naturalPVResp: 0,
	    	naturalSRCobranca:0,
	    	naturalPVCobranca: 0,
	    	abrangencia: 1,
	    	prazoEmissao: 0,
	    	permiteContratoCanal: "N",
	    	autorizaMargemContrato: "N",
	    	prazoMinimoAutMargemContr: 0,
	    	permiteRenovacaoCanal: "N",
	    	autorizaMargemRenovacao: "N",
	    	prazoMinimoAutMargemRenov: 0,
	    	permiteMaisContrPorCli: "N",
	    	inibeRemessaSINAD: "N",
	    	tarifaAverbacao: "N",
	    	permiteCarencia: "N",
	    	prazoMaxCarencia: 0,
	    	permiteMoratoria: "N",
	    	prazoMaxMoratoria: 0,
	    	exigeClienteRenovacao:"N",
	    	formaAverbacao: 0,
	    	srresp: 0,
	    	pvresp: 0,
	    	srcobranca: 0,
	    	pvcobranca:0
	    },
	    
	    tipos : {
	    	id: 'NUMERO',
	    	nome: 'CHAR',
	    	convenente_cnpj: 'CNPJ',
    		convenente_razaoSocial: "CHAR",
    		convenente_logradouro: "CHAR",
    		convenente_complemento: "CHAR",
    		convenente_numero: "CHAR",
    		convenente_bairro:"CHAR",
    		convenente_cidade: "CHAR",
    		convenente_uf: "CHAR",
    		convenente_cep: "NUMERO",
    		convenente_complementoCep: "NUMERO",
    		convenente_qtEmpregado: "NUMERO",    		
	    	grupo_id: "NUMERO", 
	    	grupo_nome: "CHAR",
	    	remessa_id: "NUMERO",
	    	remessa_nome: "CHAR",
	    	situacao_id: "NUMERO",
	    	situacao_tipo: "NUMERO",
	    	situacao_descricao: "CHAR",
	    	agenciaContaCredito: "NUMERO",
	    	operacaoContaCredito: "NUMERO",
	    	contaCredito:"NUMERO",
	    	dvContaCredito: "NUMERO",
	    	diaCreditoSal: "NUMERO",
	    	prazoMaximo: "NUMERO",
	    	naturalSRResp: "NUMERO",
	    	naturalPVResp: "NUMERO",
	    	naturalSRCobranca:"NUMERO",
	    	naturalPVCobranca: "NUMERO",
	    	abrangencia: "NUMERO",
	    	prazoEmissao: "NUMERO",
	    	permiteContratoCanal: "CHAR",
	    	autorizaMargemContrato: "CHAR",
	    	prazoMinimoAutMargemContr: "NUMERO",
	    	permiteRenovacaoCanal: "CHAR",
	    	autorizaMargemRenovacao: "CHAR",
	    	prazoMinimoAutMargemRenov: "NUMERO",
	    	permiteMaisContrPorCli: "CHAR",
	    	inibeRemessaSINAD: "CHAR",
	    	tarifaAverbacao: "CHAR",
	    	permiteCarencia: "CHAR",
	    	prazoMaxCarencia: "NUMERO",
	    	permiteMoratoria: "CHAR",
	    	prazoMaxMoratoria: "NUMERO",
	    	exigeClienteRenovacao:"CHAR",
	    	formaAverbacao: "NUMERO",
	    	srresp: "NUMERO",
	    	pvresp: "NUMERO",
	    	srcobranca: "NUMERO",
	    	pvcobranca: "NUMERO"
	    }, 
	    
	    /**
		 * Função padrão de incialização do modelo
		 * 
		 * @TODO
		 * 	Alterar a validacao para Codigo
		 */
		initialize : function () {
			console.log("Alterar IOF - initialize");
			
			//array de validadores
			this.validador = {};
			     	
		},
			    	    
	    consultar: function (tipoConsulta, id, cnpj, nome, situacao, sr, agencia) {
	    	_this = this;

	    	//Para os atributos de tipo primitivo, DEVEM ser setados os valores "default", quando não usados, para não dar erro no REST. 
	    	
			switch (tipoConsulta) {
			case '1': // Convenio
				nome = nome != null ? String(nome).replace(/['"%]/g, '') : null;
				
				if (id != null && !isNaN(id) && id > 0) {
					_this.url = _this.urlRoot + "consultar?tipoConsulta="+tipoConsulta+"&id="+id+"&cnpj=0&situacao=0&sr=0&agencia=0&nome="+nome+"&situacao="+situacao;	    	
				} else {
					if (situacao != null && !isNaN(situacao) && situacao > 0) {
						_this.url = _this.urlRoot + "consultar?tipoConsulta="+tipoConsulta+"&id=0&cnpj=0&sr=0&agencia=0&nome="+nome+"&situacao="+situacao;	    	
					} else {
						_this.url = _this.urlRoot + "consultar?tipoConsulta="+tipoConsulta+"&id=0&cnpj=0&situacao=0&sr=0&agencia=0&nome="+nome;	    	
					}
				}
				break;

			case '2': // Convenente
				cnpj = cnpj != null ? String(cnpj).replace(/[.\-\/]/g, '') : null;
				_this.url = _this.urlRoot + "consultar?tipoConsulta="+tipoConsulta+"&id=0&situacao=0&sr=0&agencia=0&cnpj="+cnpj;
				break;
				
			case '3': // Unidade
				sr = (sr != null && sr != "") ? String(sr).replace(/[.\-\/]/g, '') : 0;
				agencia = (agencia != null && agencia != "") ? String(agencia).replace(/[.\-\/]/g, '') : 0;
				_this.url = _this.urlRoot + "consultar?tipoConsulta="+tipoConsulta+"&sr="+sr+"&agencia="+agencia;	    	
				
				break;
				
			default:
				break;
			}	    	
	    			    		
			return _this.fetch({
			    type: "GET",
			    contentType: "application/json"
			});
		},
		
		salvar: function (convenio) {
	    	_this = this;
	    	
	    	_this.url = _this.urlRoot + "salvar";
	    	config.preparaAtributos(convenio, _this.tipos);
	    			    		
	    	// usar sync pois o save e o fetch adicionam a classe Retorno na model e nem voce e nem eu queremos isso 
			return _this.sync('create', _this, {			    
				contentType: "application/json",
				data: JSON.stringify(convenio)
			});
		}
		
	});
	
	return ConvenioModelo;

});