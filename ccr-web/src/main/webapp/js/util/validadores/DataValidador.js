/**
 * @autor 
 * Verifica se a data informada eh valida
 */
var DataValidator = Class.create(Validator, {
	
	/**
	 * Construtor.
	 * 
	 */
	initialize: function() {
		this.id = "data";
		this.message = "Data inválida";
	},
	
	validate: function(data) {
		
		// o required ja valida se o campo foi preenchido...
		if (data == '')
			return true;
				
		data = formatadores.formatarData(data);
		
		//Verifica se a data nao contem 8 caracteres e adiciona as / - convencional os padroes ddMMyyyy		
		if(data != null && data.length === 8){			
			data = data.substr(0,2)+"/"+data.substr(2,2)+"/"+data.substr(4,4);			
		}		

		var dataTemp = data;
		
		//verifica se a data foi passada corretamente
		if (dataTemp == '')
			return false;
		
		//Declaracao de Regex
		var rxDataPadaro = /^(\d{1,2})(\/|-)(\d{1,2})(\/|-)(\d{4})$/;
		
		// verifica se o formato da data esta correto
		var dataArray = dataTemp.match(rxDataPadaro); 

		if (dataArray == null)
			return false;

		//parte no formato mm/dd/yyyy 
		dataDia = dataArray[1];
		dataMes = dataArray[3];
		dataAno = dataArray[5];

		//verifica o mes
		if (dataMes < 1 || dataMes > 12)
			return false;
		
		//verifica o dia
		else if (dataDia < 1 || dataDia > 31)
			return false;
		
		//verifica meses com 31 dias
		else if ((dataMes == 4 || dataMes == 6 || dataMes == 9 || dataMes == 11)
				&& dataDia == 31)
			return false;
		
		//verificacao de fevereiro e ano bissexto
		else if (dataMes == 2) {
			var isBissexto = (dataAno % 4 == 0 && (dataAno % 100 != 0 || dataAno % 400 == 0));
			if (dataDia > 29 || (dataDia == 29 && !isBissexto))
				return false;
		}
		
		return true;
	}
});

/**
 * @autor f620600
 * Verifica se a data informada eh maior que a dataCompara informada
 * na consutrucao da classe
 */
var DataValidatorMaior = Class.create(Validator, {
	
	dataCompara: null,
	
	/**
	 * Construtor.
	 * 
	 */
	initialize: function(dataCompara) {
		this.id = "dataMaior";
		this.message = "Data não pode ser maior que " + dataCompara;
		this.dataCompara = dataCompara;
	},
	
	validate: function(data) {
		// o required ja valida se o campo foi preenchido...
		if (data == '')
			return true;
		
		if (this.dataCompara == null)
			return true;
		
		console.log("data1-->" + data);
		console.log("data2-->" + this.dataCompara);
		
		//Declaracao de Regex
		var rxDataPadaro = /^(\d{1,2})(\/|-)(\d{1,2})(\/|-)(\d{4})$/;
		
		// verifica se o formato da data esta correto
		var data1Array = data.match(rxDataPadaro);
		
		//data nao esta no padrao
		if (data1Array == null)
			return false;
		
		//parte no formato mm/dd/yyyy 
		data1Dia = data1Array[1];
		data1Mes = data1Array[3];
		data1Ano = data1Array[5];
				
		var data2Array = this.dataCompara.match(rxDataPadaro);
		
		//data nao esta no padrao
		if (data2Array == null)
			return true;
		
		//parte no formato mm/dd/yyyy 
		data2Dia = data2Array[1];
		data2Mes = data2Array[3];
		data2Ano = data2Array[5];

		//verifica ano
		if(data1Ano > data2Ano)
			return false;
		
		//verifica mes e ano
		if((data1Mes > data2Mes) && (data1Ano >= data2Ano))	
			return false;
		
		//verifica dia,mes e ano
		if((data1Dia > data2Dia) && (data1Mes >= data2Mes) && (data1Ano >= data2Ano))	
			return false;
		
		return true;
	}
});

/**
 * @autor f620600
 * Verifica se a data informada eh menor que a dataCompara informada
 * na consutrucao da classe
 */
var DataValidatorMenor = Class.create(Validator, {
	
	dataCompara: null,
	
	/**
	 * Construtor.
	 * 
	 **/
	initialize: function(dataCompara) {
		
		this.id = "dataMenor";
		this.message = "Data não pode ser menor que " + dataCompara;
		this.dataCompara = dataCompara;
	},
	
	validate: function(data) {
		
		// o required ja valida se o campo foi preenchido...
		if (data == '')
			return true;
		
		if (this.dataCompara == null)
			return true;
		
		console.log("data1-->" + data);
		console.log("data2-->" + this.dataCompara);
		
		//Declaracao de Regex
		var rxDataPadaro = /^(\d{1,2})(\/|-)(\d{1,2})(\/|-)(\d{4})$/;
		
		// verifica se o formato da data esta correto
		var data1Array = data.match(rxDataPadaro);
		
		//data nao esta no padrao
		if (data1Array == null)
			return false;
		
		//parte no formato mm/dd/yyyy 
		data1Dia = data1Array[1];
		data1Mes = data1Array[3];
		data1Ano = data1Array[5];
				
		var data2Array = this.dataCompara.match(rxDataPadaro);
		
		//data nao esta no padrao
		if (data2Array == null)
			return true;
		
		//parte no formato mm/dd/yyyy 
		data2Dia = data2Array[1];
		data2Mes = data2Array[3];
		data2Ano = data2Array[5];
		
		//verifica ano
		if(data2Ano > data1Ano)
			return false;
		
		//verifica mes e ano
		if((data2Mes > data1Mes) && (data2Ano >= data1Ano))	
			return false;
		
		//verifica dia,mes e ano
		if((data2Dia > data1Dia) && (data2Mes >= data1Mes) && (data2Ano >= data1Ano))	
			return false;
		
		return true;
	}
});

/**
 * @autor 299991
 * Verifica se a data inicio de vigencia informada no Form de Taxa Seguro e Aliquota IOF eh menor que a data atual.
 */
var DataValidatorTaxaSeguroIOF = Class.create(Validator, {
	
	dataTaxaSeguroIOF: null,
	
	/**
	 * Construtor.
	 * 
	 **/
	initialize: function(dataTaxaSeguroIOF) {
		this.id = "dataMenorTaxaSeguroIOF";
		this.message = "Data Início de Vigência não pode ser menor ou igual à data atual";
		this.dataTaxaSeguroIOF = dataTaxaSeguroIOF;
	},
	
	validate: function(data) {
		
		console.log(data);
		
		// o required ja valida se o campo foi preenchido...
		if (data == '')
			return true;
		
		if (this.dataTaxaSeguroIOF == null)
			return true;
		
		//Declaracao de Regex
		var rxDataPadaro = /^(\d{1,2})(\/|-)(\d{1,2})(\/|-)(\d{4})$/;
		
		// verifica se o formato da data esta correto
		var data1Array = data.match(rxDataPadaro);
		
		//data nao esta no padrao
		if (data1Array == null)
			return false;
		
		//parte no formato mm/dd/yyyy 
		data1Dia = data1Array[1];
		data1Mes = data1Array[3];
		data1Ano = data1Array[5];
		
		var data2Array = this.dataTaxaSeguroIOF.match(rxDataPadaro);
		
		//data nao esta no padrao
		if (data2Array == null)
			return true;
		
		//parte no formato mm/dd/yyyy 
		data2Dia = data2Array[1];
		data2Mes = data2Array[3];
		data2Ano = data2Array[5];
		
		//verifica ano
		if(data2Ano > data1Ano)
			return false;
		
		//verifica mes e ano
		if((data2Mes > data1Mes) && (data2Ano >= data1Ano))	
			return false;
		
		//verifica dia,mes e ano
		if((data2Dia > data1Dia) && (data2Mes >= data1Mes) && (data2Ano >= data1Ano))	
			return false;
		
		return true;
	}
});

/**
 * @autor 299991
 * Verifica se a data inicio de vigencia informada no Form de Taxa Seguro e Aliquota IOF eh menor que a data atual.
 */
var DataValidatorTaxaSeguroIOF_FimVigencia = Class.create(Validator, {
	
	dataTaxaSeguroIOF: null,
	
	/**
	 * Construtor.
	 * 
	 **/
	initialize: function(dataTaxaSeguroIOF) {
		this.id = "dataMenorTaxaSeguroIOF";
		this.message = "Data Fim de Vigência não pode ser menor ou igual à data atual";
		this.dataTaxaSeguroIOF = dataTaxaSeguroIOF;
	},
	
	validate: function(data) {
		
		console.log(data);
		
		// o required ja valida se o campo foi preenchido...
		if (data == '')
			return true;
		
		if (this.dataTaxaSeguroIOF == null)
			return true;
		
		//Declaracao de Regex
		var rxDataPadaro = /^(\d{1,2})(\/|-)(\d{1,2})(\/|-)(\d{4})$/;
		
		// verifica se o formato da data esta correto
		var data1Array = data.match(rxDataPadaro);
		
		//data nao esta no padrao
		if (data1Array == null)
			return false;
		
		//parte no formato mm/dd/yyyy 
		data1Dia = data1Array[1];
		data1Mes = data1Array[3];
		data1Ano = data1Array[5];
		
		var data2Array = this.dataTaxaSeguroIOF.match(rxDataPadaro);
		
		//data nao esta no padrao
		if (data2Array == null)
			return true;
		
		//parte no formato mm/dd/yyyy 
		data2Dia = data2Array[1];
		data2Mes = data2Array[3];
		data2Ano = data2Array[5];
		
		//verifica ano
		if(data2Ano > data1Ano)
			return false;
		
		//verifica mes e ano
		if((data2Mes > data1Mes) && (data2Ano >= data1Ano))	
			return false;
		
		//verifica dia,mes e ano
		if((data2Dia > data1Dia) && (data2Mes >= data1Mes) && (data2Ano >= data1Ano))	
			return false;
		
		return true;
	}
});


