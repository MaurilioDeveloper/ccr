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
		urlRoot: config['urlBaseRest'] + 'convenio',
		urlClient: config['urlBaseRest'] + 'cliente',
		url: '',
			
		idAttribute: '_id', 
			
		/***
		 * Criacao de valores padroes
		 */
	    defaults : {//CCRTB001_CONVENIO
	    	id: 0,//NU_CONVENIO
	    	nome: "",//NO_CONVENIO
	    	codDvConvenio: 0, //CO_DV_CONVENIO
	    	convenente: { 
    		  cnpj: 0,	//Convenente - cnpjConvenente -	NU_CNPJ_CONVENENTE
    		  razaoSocial: "",
    		  logradouro: "",
    		  complemento: "",
    		  numero: "",
    		  bairro:"",
    		  cidade: "",
    		  uf: "",
    		  cep: 0,
    		  complementoCep: 0,
    		  qtEmpregado: 0,
    		  nuPessoa: 1
    		},
	    	grupo: { //CCRTB002_GRUPO_TAXA		
    		  id: "", //NU_GRUPO_TAXA
    		  nome: ""
	    	},
	    	grupoAverbacao: {	//CCRTB011_GRUPO_AVERBACAO 
			    id: 0,//NU_GRUPO_AVERBACAO
			    nome: ""			    
			 },
	    	remessa: { //CCRTB009_REMESSA_EXTRATO
    		  id: 1,//NU_REMESSA_EXTRATO
    		  nome: "DEBITO MANUAL"
    		},
	    	situacao: {//CCRTB014_SITUACAO 
    		  id: 0,//NU_SITUACAO
    		  tipo: 1,//NU_TIPO_SITUACAO
    		  descricao: "ATIVO"
    		},
    		origemRecurso: {//CCRTB031_ORIGEM_RECURSO		- ver o que é esse campo
      		  id: 1, //NU_ORIGEM_RECURSO
      		  nome: "CAIXA"
  	    	},
  	    	abrangenciaUF: [{//CCRTB045_CONVENIO_UF
  	    		numConvenio: 0,    	//private Integer numConvenio			
  	    		sgUF: "",			//private String sgUF
  	    		dtInclusaoUF: "", 	//private Date dtInclusaoUF
  	    		dtExclusaoUF: "",	//private Date dtExclusaoUF
  	    		codUsuarioInclusao: 0,	//private Long codUsuarioInclusao
  	    		codUsuarioExclusao: 0  	//private Long codUsuarioExclusao
  	    	}],
  	    	abrangenciaSR: [{//CCRTB047_CONVENIO_SR
  	    		numConvenio: 0,    	//private Integer numConvenio		
  	    		unidade: "",		//private String unidade
  	    		numNatural: 0,		//private Integer numNatural
  	    		dtInclusaoSR: "", 	//private Date dtInclusaoSR
  	    		dtExclusaoSR: "",	//private Date dtExclusaoSR
  	    		codUsuarioInclusao: 0,	//private Long codUsuarioInclusao
  	    		codUsuarioExclusao: 0	//private Long codUsuarioExclusao
  	    	}],
  	    	convenioCNPJVinculado: [{//CCRSQ049_CONVENIO_CNPJ_VINCULADO
  	    		nuConvenio: 0,    	//private Convenio nuConvenio		
  	    		nuCNPJ: 0		//private String nuCNPJ
  	    	}],
    		canais: [{//CCRTB003_CONVENIO_CANAL
    			idConvenio: 0,	
    			canal: {//CCRTB004_CANAL_ATENDIMENTO
    				id: 0,  //NU_CANAL_ATENDIMENTO - CCRTB003_CONVENIO_CANAL
    				nome: ""  // NO_CANAL_ATENDIMENTO - CCRTB004_CANAL_ATENDIMENTO
    			},
    			indExigeAnuencia: "N",
    			indPermiteContratacao: "N", 		// Permite Contratação //IC_PERMITE_CONTRATACAO
    			indPermiteRenovacao: "N",			//Permite Renovação //IC_PERMITE_RENOVACAO
    			qtdDiaGarantiaCondicao: 0,			// Garantia Condições ------ QT_DIA_GARANTIA_CONDICAO - qtdDiaGarantiaCondicao
    			indAutorizaMargemContrato: "N", 	// Exige Autorização Margem //IC_AUTORIZA_MARGEM_CONTRATO
    		   	indAutorizaMargemRenovacao:"N", 	//Exige Autorização Margem //IC_AUTORIZA_MARGEM_RENOVACAO
    			indExigeAutorizacaoContrato: "N", 	//Exige Anuência //IC_EXIGE_AUTORIZACAO_CONTRATO
    			przMinimoContratacao: 0, 			// Contratacao Prazo Mínimo ------ PZ_MINIMO_CONTRATACAO - przMinimoContratacao
    			przMinimoRenovacao: 0,				// Renovacao Prazo Mínimo -------PZ_MINIMO_RENOVACAO - przMinimoRenovacao;
    			przMaximoContratacao:0,				// Contratacao Prazo Maximo ------PZ_MAXIMO_CONTRATACAO - przMaximoContratacao
    			przMaximoRenovacao: 0, 				// Renovacao Prazo Maximo //PZ_MAXIMO_RENOVACAO - przMaximoRenovacao
    			indFaixaJuroRenovacao: "", 			//Faixas Juros Padrão renovacao
    			indFaixaJuroContratacao:  "",		//Faixas Juros Padrão contratacao
    			indSituacaoConvenioCanal: "N"	//Situação do Convenio Canal
    			 			
    		}],
    		numAgenciaContaCredito: 0, //numAgenciaContaCredito //NU_AGENCIA_CONTA_CREDITO
    		numOperacaoContaCredito: 0, //numOperacaoContaCredito //NU_OPERACAO_CONTA_CREDITO 
    		numContaCredito: 0, //numContaCredito //NU_CONTA_CREDITO
    		contaCorrente: "",
    		numDvContaCredito: 0, //numDvContaCredito //NU_DV_CONTA_CREDITO
    		cnpjConvenente: 0, // cpf recuperado do cicli //NU_CNPJ_CONVENENTE
    		codigoOrgao: 0,	//ver o mapeamento desse campo
    		numSprnaResponsavel: 0,  //SR Responsável  //NU_SPRNA_RESPONSAVEL
    		numPvResponsavel: 0,  //Agência Responsável //NU_PV_RESPONSAVEL //ORA-02291: restrição de integridade (CCR.FK_CCRTB001_ICOTBU24_2) violada - chave mãe não localizada
    		numSprnaCobranca: 0,  //SR Cobrança //NU_SPRNA_COBRANCA
    		numPvCobranca: 0,  //Agência Cobrança //NU_PV_COBRANCA  //ORA-02291: restrição de integridade (CCR.FK_CCRTB001_ICOTBU24_1) violada - chave mãe não localizada 		
    		diaCreditoSal: 0, //Dia Credito Salário //DD_CREDITO_SALARIO
  		    przEmissaoExtrato: 0,   //Prazo Emissão //PZ_EMISSAO_EXTRATO
  		    przMaximoRenovacao: 0, //Prazo Máximo Autorização //PZ_MAXIMO_RENOVACAO //ver o mapeamento desse campo //przMaximoRenovacao
  		    qtEmpregado: 0,	//ver o mapeamento desse campo
  		    numNaturalPvCobranca: 1,//NU_NATURAL_PV_COBRANCA //ORA-02291: restrição de integridade (CCR.FK_CCRTB001_ICOTBU24_1) violada - chave mãe não localizada
  		    numNaturalPvResponsavel: 1, //NU_NATURAL_PV_RESPONSAVEL // ORA-02291: restrição de integridade (CCR.FK_CCRTB001_ICOTBU24_2) violada - chave mãe não localizada
  		    przMaximoEmprestimo: 0, //PZ_MAXIMO_EMPRESTIMO
  		    indAbrangencia: 0, //IC_ABRANGENCIA
  		    indTarifaAverbacao: "N", //IC_TARIFA_AVERBACAO
  		    indCarencia: "N", //IC_CARENCIA
  		    indMoratoria: "N", //IC_MORATORIA
  		    indExigeClienteRenovacao: "N", //IC_EXIGE_CLIENTE_RENOVACAO
  		    indAceitaCanal: "N", //IC_ACEITA_CANAL
  		    indPermiteContratoCliente: "N", //IC_PERMITE_CONTRATO_CLIENTE
  		    indInibeRemessaInadimplente: "N",
  		    qtDiaAguardarAutorizacao: 0 //IC_INIBE_REMESSA_INADIMPLENTE
	    },
	    
	    tipos : {
	    	id: 'NUMERO',
	    	nome: 'CHAR',
	    	codDvConvenio: 'NUMERO',
	    	indExigeAnuencia: "CHAR",
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
	    	numAgenciaContaCredito: "NUMERO",
	    	numOperacaoContaCredito: "NUMERO",
	    	numContaCredito:"NUMERO",
	    	dvContaCredito: "NUMERO",
	    	diaCreditoSal: "NUMERO",
	    	codigoOrgao: "NUMERO",
	    	qtEmpregado: "NUMERO",
	    	cnpjConvenente: "CHAR",
	    	numSprnaResponsavel: "NUMERO",
	    	numPvResponsavel: "NUMERO",
	    	numSprnaCobranca: "NUMERO",
	    	numPvCobranca: "NUMERO",
	    	przEmissaoExtrato: "NUMERO",   
  		    przMaximoRenovacao: "NUMERO",
  		    grupoAverbacao_id: "NUMERO",  
  			grupoAverbacao_nome: "CHAR",
  			numNaturalPvCobranca: "NUMERO",
  		    numNaturalPvResponsavel: "NUMERO",
  		    przMaximoEmprestimo: "NUMERO",
  		    indAbrangencia: "NUMERO",
  		    indTarifaAverbacao: "CHAR",
  		    indCarencia: "CHAR",
  		    indMoratoria: "CHAR",
  		    indExigeClienteRenovacao: "CHAR",
  		    indAceitaCanal: "CHAR",
  		    indPermiteContratoCliente: "CHAR",
  		    indInibeRemessaInadimplente: "CHAR",
  		    indFaixaJuroContratacao: "CHAR", //Faixas Juros Padrão
  		    indFaixaJuroRenovacao:  "CHAR",//Faixas Juros Padrão
  		    przMaximoRenovacao: "NUMERO",
			contaCorrente: "CHAR",
			qtDiaAguardarAutorizacao: "NUMERO"
	    }, 
	    
	    /**
		 * Função padrão de incialização do modelo
		 * 
		 * @TODO
		 * 	Alterar a validacao para Codigo
		 */
		initialize : function () {
			console.log("Convenio - initialize");
			
			//array de validadores
			this.validador = {};
			     	
		},
		
		salvar: function (convenio) {
	    	_this = this;
	    	
	    	_this.url = _this.urlRoot + "/salvar";
	    	config.preparaAtributos(convenio, _this.tipos);
	    	
	    	// usar sync pois o save e o fetch adicionam a classe Retorno na model e nem voce e nem eu queremos isso 
			return _this.sync('create', _this, {			    
				contentType: "application/json",
				data: JSON.stringify(convenio)
			});
		},
		
		excluir: function(canal) {
			_this = this;
	    	
	    	_this.url = _this.urlRoot + "/excluir?idCanal="+canal.idCanal+"&nuConvenio="+canal.nuConvenio;
	    	
			return this.fetch({
				type: "GET",
			    contentType: "application/json"
			});
		},
		
		salvarCanal: function(canal) {
			_this = this;
	    	
	    	_this.url = _this.urlRoot + "/salvarCanal";
	    	
			return this.fetch({
				type: "POST",
			    contentType: "application/json",
			    data: JSON.stringify(canal)
			});
		},
		
		alterarCanal: function(canal) {
			_this = this;
	    	
	    	_this.url = _this.urlRoot + "/alterarCanal";
	    	
			return this.fetch({
				type: "POST",
			    contentType: "application/json",
			    data: JSON.stringify(canal) 
			});
		},
		
		
		auditoriaCanalPersiste : function(canaisPersisteAuditoria) {
			
			_this = this;
	    	
			/**
			 * Auditoria de Canais excluidos
			 */
	    	_this.url = _this.urlRoot + "/auditoriaCanalPersiste";
	    	
			return this.fetch({
				type: "POST",
			    contentType: "application/json",
			    data: JSON.stringify(canaisPersisteAuditoria)
			});
		},
		
		consultar: function (cnpj) {
	    	_this = this;
	    	_this.url = _this.urlClient + "/consultar?cnpj="+cnpj;
	    	config.preparaAtributos(cnpj, _this.tipos);

	    	return _this.fetch({
			    type: "GET",
			    contentType: "application/json"
			});
	    		    	
		},
		
		consultarCorrelationId: function (correlationId) {
	    	_this = this;
	    	console.log('correlation para chamada : '+ correlationId)
	    	_this.url = _this.urlClient + "/consultarCorrelationId?correlationId="+correlationId;
	    	config.preparaAtributos(correlationId, _this.tipos);

	    	return _this.fetch({
			    type: "GET",
			    contentType: "application/json"
			}); 	
	    	
	    	
		},
		
		consultarSR: function (sr) {
			_this = this;
			_this.url = _this.urlRoot + "/consultarSR/"+sr;
			config.preparaAtributos(sr, _this.tipos);
			
			return _this.fetch({
				type: "GET",
				contentType: "application/json"
			});
		},
		
		consultarConvenio: function (id) {
	    	_this = this;
	    	_this.url = _this.urlRoot + "/consultar?id="+id;
	    	config.preparaAtributos(id, _this.tipos);

	    	return _this.fetch({
			    type: "GET",
			    contentType: "application/json"
			});
		},
		
		
		consultarPerfil: function (retorno) {
	    	_this = this;
	    	_this.url = _this.urlRoot + "/perfil";
	    	config.preparaAtributos("", _this.tipos);
	    	return _this.sync('create', _this, {			    
				contentType: "application/json",
				data: JSON.stringify(retorno)
			});
		},
		
		label: function () {
	        return this.get("name");
	    }, 
		
		
	});
	
	return ConvenioModelo;

});