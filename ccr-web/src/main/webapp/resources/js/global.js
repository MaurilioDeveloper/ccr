var constsDeclared = false;
if(!constsDeclared) {
	const ID_CANAL = 1; // AGENCIA
	const NU_BANCO_CEF = 104;
	const NU_MODALIDADE = 0;
	const NU_GARANTIA = 1;
	constsDeclared = true;
}

var gDescEstadoCivil = [
    "", "Solteiro(a)", "", "Divorciado(a)", "Separado(a)", "Viúvo(a)", "Com união estável", 
    "Casado(a) com Comunhão Total de Bens", "Casado(a) sem Comunhão de Bens", "Casado(a) com Comunhão Parcial de Bens"
];

var gDescGrauInstrucao = [
	"Não Alfabetizado", "Até 4ª série Incompl. do Ens. Fund.", "Com 4ª série Compl. do Ens. Fund.",
	"Da 5ª a 8ª Série Incompl. do Ens. Fund.", "Ensino Fundamental Completo", "Ensino Médio Incompleto", "Ensino Médio Completo", 
	"Superior Incompleto", "Superior Completo", "Especialização/Pós-Graduação", "Mestrado", "Doutorado"
];

var DateDiff = {

	inDays : function(d1, d2) {
		var t2 = d2.getTime();
		var t1 = d1.getTime();

		return parseInt((t2 - t1) / (24 * 3600 * 1000));
	},

	inWeeks : function(d1, d2) {
		var t2 = d2.getTime();
		var t1 = d1.getTime();

		return parseInt((t2 - t1) / (24 * 3600 * 1000 * 7));
	},

	inMonths : function(d1, d2) {
		var d1Y = d1.getFullYear();
		var d2Y = d2.getFullYear();
		var d1M = d1.getMonth();
		var d2M = d2.getMonth();

		return (d2M + 12 * d2Y) - (d1M + 12 * d1Y);
	},

	inYears : function(d1, d2) {
		return d2.getFullYear() - d1.getFullYear();
	}
};

function isDate(txtDate) {
	var currVal = txtDate;
	if (currVal == '' || currVal == null) {
		return false;
	}

	// Declare Regex
	var rxDatePattern = /^(\d{1,2})(\/|-)(\d{1,2})(\/|-)(\d{4})$/;
	var dtArray = currVal.match(rxDatePattern); // is format OK?

	if (dtArray == null) {
		return false;
	}

	// Checks for mm/dd/yyyy format.
	dtDay = dtArray[1];
	dtMonth = dtArray[3];
	dtYear = dtArray[5];
	// atualizado denovo

	if (dtMonth < 1 || dtMonth > 12) {
		return false;
	} else if (dtDay < 1 || dtDay > 31) {
		return false;
	} else if ((dtMonth == 4 || dtMonth == 6 || dtMonth == 9 || dtMonth == 11) && dtDay == 31) {
		return false;
	} else if (dtMonth == 2) {
		var isleap = (dtYear % 4 == 0 && (dtYear % 100 != 0 || dtYear % 400 == 0));
		if (dtDay > 29 || (dtDay == 29 && !isleap)) {
			return false;
		}
	}
	
	if (dtYear < 1) {
		return false;
	}
	
	return true;
}

function dataInicioMaiorDataFim(dataInicio, dataFim) {
	var arrDI = dataInicio.split("/");
	var arrDF = dataFim.split("/");
	
	var inicioAnoMesDia = new Date(parseInt(arrDI[2]), parseInt(arrDI[1])-1, parseInt(arrDI[0]));
	var fimAnoMesDia = new Date(parseInt(arrDF[2]), parseInt(arrDF[1])-1, parseInt(arrDF[0]));
	
	return (inicioAnoMesDia > fimAnoMesDia);
}

function converterDateSqlToDate(dateSQL) {
	var arrDif = dateSQL.split("-");
	var inicioAnoMesDia = new Date(parseInt(arrDif[2]), parseInt(arrDif[1])-1, parseInt(arrDif[0]));
	return inicioAnoMesDia;
}

function purificaAtributo(valor2) {

	var caracteres = [".", "-", "/", "_", "'"];
	var valor = new String(valor2);
	
	for (var i = 0; i < caracteres.length; i++) {
		while (valor.indexOf(caracteres[i]) >= 0) {
			valor = valor.replace(caracteres[i], "");
		}
	}
		
	return valor;
}

function purificaMoeda(valor2) {

	valor = new String(valor2);

	while (valor.indexOf(".") > 0) {
		valor = valor.replace(".", "");
	}

	while (valor.indexOf(",") > 0) {
		valor = valor.replace(",", ".");
	}

	return valor;
}

function obterDiaAtual() {
	var dataAtual = new Date();
	return dataAtual.getDate();
}

function obterMesAtual() {
	var dataAtual = new Date();
	return dataAtual.getMonth() + 1;
}

function obterAnoAtual() {
	var dataAtual = new Date();
	return dataAtual.getFullYear();
}

function limparFormulario(idFormulario) {
	$(idFormulario).each(function() {
		this.reset();
	});
}

function adicionaMascaraDataHoraBanco(valor) {
	return  valor.substring(8, 10) + "/" + valor.substring(5, 7) + "/" + valor.substring(0, 4) + ' ' 
			+ valor.substring(11);
};

function adicionaMascaraDataBanco(valor) {

	return  valor.substring(8, 10) + "/" + valor.substring(5, 7) + "/" + valor.substring(0, 4) ; 
};




function adicionaMascaraHoraBanco(valor) {
	return  valor.substring(11);
};


function adicionaMascaraCPF(valor) {
	return valor.substring(0, 3) + '.' + valor.substring(3, 6) + '.'
			+ valor.substring(6, 9) + '-' + valor.substring(9, 11) + "";
};

function preencheZeros(valor, tamanho) {
	var sValor = new String(valor); 
    var contador = sValor.length;  
    if (sValor.length != tamanho) {  
        do {  
            sValor = "0" + sValor;  
            contador += 1;  
              
        } while (contador < tamanho)  
    }  
    return sValor;
}  

function mascararData(milisegundos) {
	return $.datepicker.formatDate("dd/mm/yy", new Date(milisegundos));
};

function mascararHora(milisegundos){
	var data = new Date(milisegundos);
	
	var horas = "";
	if( data.getHours() < 10){
		horas = "0" +  data.getHours();
	} else {
		horas = data.getHours();
	}
	
	var minutos = "";
	if( data.getMinutes() < 10){
		minutos = "0" +   data.getMinutes();
	} else {
		minutos = data.getMinutes();
	}
	
	var segundos = "";
	if( data.getSeconds() < 10){
		segundos = "0" +   data.getSeconds();
	} else {
		segundos = data.getSeconds();
	}
	
	return horas + ':' + minutos + ':' + segundos; 
}

function getDataHoraAtualFormatada() {
	var data = new Date();
	var dataFormatada = mascararData(data);
	var horaFormatada = mascararHora(data); 
	return dataFormatada + ' às ' + horaFormatada;
};

function mascararDataHora(milisegundos){
	var data = new Date(milisegundos);
	var dataFormatada = mascararData(data);
	var horaFormatada = mascararHora(data); 
	
	return dataFormatada + ' ' + horaFormatada;
};

/**
 * Habilita e desabilita campos
 * @param string seletor e boolean true,false
 * @return campo tratado
 * @since 02/07/2014
 *
 */
function desabilitarCampo(seletor,boolean) {
	return $(seletor).prop('disabled', boolean);
}

/**
 * Função que formata um valor com a mascara de moeda no formato 999.999.999,99.
 * 
 * @param valor
 *            a ser formatado
 * @returns {String} valor mascarado
 * @see http://forum.imasters.com.br/topic/244057-mscara-moeda-com-expresso-regular/
 * @since 23/07/2013
 */
function mascararMoeda(valor) {
	var valorStr = new String(parseFloat(valor).toFixed(2));
	var str1 = valorStr.substring(0,1);
	
	//console.log("call -> mascararMoeda");
	//console.log(str1);
	
	valorStr = valorStr.replace(/\D/g, "");
	valorStr = valorStr.replace(/[0-9]{19}/, "inválido");
	valorStr = valorStr.replace(/(\d{1})(\d{17})$/, "$1.$2");
	valorStr = valorStr.replace(/(\d{1})(\d{14})$/, "$1.$2");
	valorStr = valorStr.replace(/(\d{1})(\d{11})$/, "$1.$2");
	valorStr = valorStr.replace(/(\d{1})(\d{8})$/, "$1.$2");
	valorStr = valorStr.replace(/(\d{1})(\d{5})$/, "$1.$2");
	valorStr = valorStr.replace(/(\d{1})(\d{1,2})$/, "$1,$2");
	
	if (str1 == "-"){
		console.log("call -> mascararMoeda -> Entrou");
		valorStr = "-" + valorStr;

	}
	//console.log("call -> mascararMoeda 2");
	//console.log(valorStr);

	return valorStr;
};

function mascararNumeroInteiro(valor) {
	var valorStr = new String(valor == null ? 0 : valor);
	var len = valorStr.length;
	
	for(var i = 1; len - (i * 4) >= 0; i++ ) {
		var pos = len - (i * 4);
		valorStr = valorStr.substring(0, pos + 1) + '.' + valorStr.substring(pos + 1, valorStr.length); 
		len = valorStr.length;
	}
	
	return valorStr;
}

function mascararNumeroInteiroComValoresNegativos(valor) {
	var valorStr = new String(valor);
	var len = valorStr.length;
	var valorNegativo = valorStr.charAt(0) == '-' ? true : false;
	
	if (valorNegativo) {
		valorStr = valorStr.replace("-", "");
		len = valorStr.length;
	}
	
	for(var i = 1; len - (i * 4) >= 0; i++ ) {
		var pos = len - (i * 4);
		valorStr = valorStr.substring(0, pos + 1) + '.' + valorStr.substring(pos + 1, valorStr.length); 
		len = valorStr.length;
	}
	
	return valorNegativo ? '-'+valorStr : valorStr;
}

function mascararMoedaComValoresNegativos(valor) {
	var valorStr = new String(parseFloat(valor).toFixed(2));
	var valorNegativo = valorStr.charAt(0) == '-' ? true : false;
	
	var teste = Math.abs(parseFloat(valor));
	teste = (Math.round(teste.toFixed(2)*100))/100;
	
	valorStr = valorStr.replace(/\D/g, "");
	valorStr = valorStr.replace(/[0-9]{19}/, "inválido");
	valorStr = valorStr.replace(/(\d{1})(\d{17})$/, "$1.$2");
	valorStr = valorStr.replace(/(\d{1})(\d{14})$/, "$1.$2");
	valorStr = valorStr.replace(/(\d{1})(\d{11})$/, "$1.$2");
	valorStr = valorStr.replace(/(\d{1})(\d{8})$/, "$1.$2");
	valorStr = valorStr.replace(/(\d{1})(\d{5})$/, "$1.$2");
	valorStr = valorStr.replace(/(\d{1})(\d{1,2})$/, "$1,$2");
	
	return valorNegativo ? '-'+valorStr : valorStr;
};

function pad (str, max) {
  str = str.toString();
  return str.length < max ? pad("0" + str, max) : str;
}

function mascararDataHoraMinutos(milisegundos){
	var data = new Date(milisegundos);
	var dataFormatada = mascararData(data);
	var horaFormatada = mascararHora(data); 
	return dataFormatada + ' ' + horaFormatada + ' ' + getAmPm(data);
}

function getAmPm(data){
	return data.getHours() > 12 ? "PM" : "AM";
}

function dataInicialMaiorDataFinal(dtInicial, dtFinal) {
	if(dtInicial != null && dtInicial != '' && dtFinal != null && dtFinal != ''){
		var inicioAnoMesDia = formataDataAnoMesDia(dtInicial).replace(/-/g, "");
		var fimAnoMesDia = formataDataAnoMesDia(dtFinal).replace(/-/g, "");
		return (inicioAnoMesDia > fimAnoMesDia);		
	}else{
		return false;
	}
}

function formataDataAnoMesDia(valor) {
	var data = valor.split("/");
	return data[2] + "-" + pad(data[1],2) + "-" + pad(data[0],2);
}

function preparaDataBD(data) {
	if (data == null)
		return "";
	
	if (data.indexOf('/') == -1 && data.indexOf('-') == -1)
		return "";
	
	if (data.replace(/[\/_-]/g, '') == "")
		return "";
	
	if (data.indexOf('/') > -1)
		data = formataDataAnoMesDia(data);
	
	return data;
}

function preparaDataSIBAR(data) {
	if (data == null)
		return "";
	
	if (data.indexOf('/') == -1 && data.indexOf('-') == -1)
		return "";
	
	if (data.replace(/[\/_-]/g, '') == "")
		return "";
	
	data = data.replace(/[\/_-]/g, '');
	
	if (data.length != 8) {
		return "";
	}	
		
	return data;
}

function verificaNome(nome, idCampo) {
	var testeParametro = false;
	var tamanhoParametro = nome.length;
	var primeiroEspaco = "sim";
	for ( var i = 0; i < tamanhoParametro; i++) {
		if (nome.charAt(i) != " ") {
			if (nome.charAt(i + 1) == " " && nome.charAt(i + 2) != " ") {
				testeParametro = true;
			}
		} else if ((nome.charAt(i) == " " && i != 0) && (primeiroEspaco == "sim")) {
			primeiroEspaco = "nao";
			if ((nome.charAt(i + 1) != " " && nome.charAt(i + 1) != "") && (nome.charAt(i + 2) != " " && nome.charAt(i + 2) != "")) {
				testeParametro = true;
			} else {
				testeParametro = false;
			}
		}
		// verifica espaços duplos
		if (nome.charAt(i) == " " && nome.charAt(i + 1) == " ") {
			return false;
		}
	}

	if (nome.charAt(0) == " " || nome.charAt(1) == " ") {
		testeParametro = false;
	}

	if (!isNaN(parseInt(nome.charAt(0)))) {
		testeParametro = false;
	}

	if (testeParametro == false && tamanhoParametro != 0) {
		$('#' + idCampo).focus();
		return false;
		
	} else {
		return true;
	}
}

function validarSemestreAno(semestre,ano){
	var anoHoje = obterAnoAtual();
	var mesHoje = obterMesAtual();
	var semestreAnoAtual = 0;
	var semestreAno = semestre+ano;
		

	if (mesHoje == 11) {
		semestreAnoAtual = anoHoje + 1;
		semestreAnoAtual = semestreAnoAtual + 1;
	}
	else if( mesHoje == 0 || mesHoje == 1 || mesHoje == 2 || mesHoje == 3 || mesHoje == 4) {
		semestreAnoAtual = anoHoje + 1;
	}
	else if ( mesHoje == 5 || mesHoje == 6 || mesHoje == 7 || mesHoje == 8 || mesHoje == 9 || mesHoje == 10 ) {
		semestreAnoAtual = anoHoje + 2 ;
	}
	
	if(semestreAno > semestreAnoAtual){
		return false;
	}
	
	return true;
}

function validaData(id, dataInformada, model){
	if(purificaAtributo(dataInformada) != '' && !isDate(dataInformada)){
		$('#' + id).val('');
		$('#' + id).focus();
		model.set(id,'');
	}
}

/**
 * Mascara umm valor para CNPJ
 * 
 * @param valor
 * @returns valor sem mascara
 * @since 13/06/2014
 */
function removeMascara(valor) {
	return valor.replace(/\./g, "").replace(/\,/g, "").replace(/\\/g, "").replace(/\-/g, "")
			.replace(/\//g, "").replace(/\(/g, '').replace(/\)/g, '').replace(/\_/g, '').replace(/ /g, '');
};

/**
 * Mascara umm valor para CNPJ
 * 
 * @param valor
 * @returns valor mascarado CNPJ
 * @since 13/06/2014
 */
function mascararCnpj(valor) {
	return mascararValor(valor, "00.000.000/0000-00");
};

function mascararCEP(valor) {
	return mascararValor(valor, "00.000-000");
};

/**
 * Mascara umm valor para CPF
 * 
 * @param valor
 * @returns valor mascarado CPF
 * @since 01/01/2014
 */
function mascararCpf(valor) {
	return mascararValor(valor, "000.000.000-00");
};

/**
 * Exporta tabelas para arquivos xls
 * 
 * @param table = 1 seletor ou 1 array de seletores
 * @returns return arquivo
 * @since 09/07/2014
 */

function exportToExcel(table, name) {
	var uri = 'data:application/vnd.ms-excel;base64,', 
			template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><meta http-equiv="content-type" content="text/html; charset=utf-8" /><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--></head><body><table>{table}</table></body></html>';

	var base64 = function(s) {
		return window.btoa(unescape(encodeURIComponent(s)));
	};
	
	var format = function(s, c) {
		return s.replace(/{(\w+)}/g, function(m, p) {
			return c[p];
		});
	};
	
	if(typeof table == "string"){
		if (!table.nodeType)
			table = document.getElementById(table);
		
		var ctx = {
				worksheet : name != "" || name != undefined ? name : 'Planilha1' ,
				table : table.innerHTML
		};
		window.location.href = uri + base64(format(template, ctx));	
	}else{
		
		var contexto = "";
		$(table).each(function(i,value) {
			contexto += '<p>' + $(value).html() + '</p>';
		});
		
		var ctx = {
				worksheet : name != '' || name != undefined ? name : 'Planilha1' ,
				table : contexto
		};
		window.location.href = uri + base64(format(template, ctx));	
	}
};

/**
 * Converte moeda para float com 2 casas fixas.
 * 
 * @param vlr
 * @returns float
 * @since 17/06/2014
 */
function moedaToFloat(vlr) {
	if (vlr == '' || vlr == undefined || vlr == null) {
		return 0;
	}
	return vlr.replace(/\.+/g, "").replace(/\,/, '.').replace(/\s/g,'').replace(/\%/g,'');
}

/**
 * Método para totalizar valores entre elementos td
 * 
 * @param strSeletorColuna = 'seuSeletor',
 * @param posicaoColuna = posicao da td
 * @returns valor total das colunas
 * @since 18/06/2014
 */
function calcularTotalTd(strSeletorColuna, posicaoColuna) {
	var valorTotalColunas = 0;
	strSeletorColuna = strSeletorColuna.concat(":nth-child(" + posicaoColuna + ")");
	strSeletorColuna = $(strSeletorColuna);
	l = strSeletorColuna.length;
	if (l > 1) {

		for ( var i = 0; i < l; i++) {
			strSeletorColuna[i] = $(strSeletorColuna[i]).text();
			strSeletorColuna[i] = strSeletorColuna[i].replace(".", "").replace(",", "");
			valorTotalColunas += parseFloat((strSeletorColuna[i]));
		}
		
		return mascararMoeda(valorTotalColunas / 100);
	}
	
	console.log(strSeletorColuna);
	return strSeletorColuna.text();

};

function mascararValor(valor, Mascara) {
	var boleanoMascara;
	var exp = /\-|\.|\,|\/|\(|\)| /g;
	var mascaraSemMascara = Mascara.replace(exp, "");
	campoSoNumeros = new String(valor);

	// Criar metodo fora para o tratamento
	if (mascaraSemMascara.length !== campoSoNumeros.length) {
		var caract = mascaraSemMascara.length - campoSoNumeros.length;
		var valorCompleto = mascaraSemMascara.substring(0, caract) + ""
				+ campoSoNumeros;
		campoSoNumeros = valorCompleto;
	}

	var posicaoCampo = 0;
	var NovoValorCampo = "";
	var TamanhoMascara = campoSoNumeros.length;

	for ( var i = 0; i <= TamanhoMascara; i++) {

		boleanoMascara = ((Mascara.charAt(i) == "-") || (Mascara.charAt(i) == ".") 
				|| (Mascara.charAt(i) == ",") || (Mascara.charAt(i) == "/"));
		boleanoMascara = boleanoMascara
				|| ((Mascara.charAt(i) == "(") || (Mascara.charAt(i) == ")") || (Mascara
						.charAt(i) == " "));

		if (boleanoMascara) {
			NovoValorCampo += Mascara.charAt(i);
			TamanhoMascara++;
		} else {
			NovoValorCampo += campoSoNumeros.charAt(posicaoCampo);
			posicaoCampo++;
		}
	}

	return NovoValorCampo;
}

/**
 * Função que formata um valor com a quantidade de casas decimais informada
 * 
 * @param valor
 *            a ser formatado
 * @param decimais
 *            qtd de casas decimais
 * @returns {String} valor mascarado
 * @since 20/06/2014
 */
function mascararTaxa(valor, decimais) {
	var valorStr = new String(parseFloat(valor).toFixed(decimais));
	
	if(valorStr.length > 17)
		return "inválido";
	
	arrValorStr = valorStr.split(".");
	arrValorStr[0] = arrValorStr[0].replace(/(\d{1})(\d{12})$/, "$1.$2");
	arrValorStr[0] = arrValorStr[0].replace(/(\d{1})(\d{9})$/, "$1.$2");
	arrValorStr[0] = arrValorStr[0].replace(/(\d{1})(\d{6})$/, "$1.$2");
	arrValorStr[0] = arrValorStr[0].replace(/(\d{1})(\d{3})$/, "$1.$2");
	valorStr = arrValorStr[0]+","+arrValorStr[1];
	
	return valorStr;
}

/**
 * Função converte uma data em string para
 * um tipo Data();
 * @param string
 * @returns {Date}
 * @since 27/06/2014
 */

function stringToDate(dt) { 
		var expReg = /^(0?[1-9]|[12][0-9]|3[01])[\/\-](0?[1-9]|1[012])[\/\-]\d{4}$/;

		var regex = new RegExp(expReg);

		if ((typeof dt === "int")) {
			return new Date(dt);
		} else if (regex.test(dt)) {
			return new Date($.datepicker.parseDate('dd/mm/yy', dt));
		}
		
		if (isDataValida(dt))
			return dt;
		
		return '';
}

/**
 * Função válida uma data
 * @param valor
 * @returns boolean
 * @since 10/07/2014
 */
function isDataValida(value) {
	if(value == '' || value == undefined)
		return false;

	// formando array
	var rxFormatoData = /^(\d{1,2})(\/|-)(\d{1,2})(\/|-)(\d{4})$/; 
	var dtArray = value.match(rxFormatoData);

	if (dtArray == null)
		return false;

	// formato dd/MM/yyyy
	dataDia = dtArray[1];
	dataMes= dtArray[3];
	dataAno = dtArray[5];
	
	if (dataMes < 1 || dataMes > 12)
		return false;
	else if (dataDia < 1 || dataDia> 31)
		return false;
	else if ((dataMes==4 || dataMes==6 || dataMes==9 || dataMes==11) && dataDia ==31)
		return false;
	else if (dataMes == 2) {
		var isAnoBissexto = (dataAno % 4 == 0 && (dataAno % 100 != 0 || dataAno % 400 == 0));
		
		if (dataDia > 29 || (dataDia == 29 && !isAnoBissexto))
			return false;
	}

	return true;
};

/**
 * Função cria uma data atual tipo string 'DD/MM/YYYY'
 * @returns {String}
 * @since 7/0/2014
 */
function getDataAtualString() {
	var dia = obterDiaAtual() < 10 ? '0' + obterDiaAtual() : obterDiaAtual();
	var mes = obterMesAtual() < 10 ? '0' + obterMesAtual() : obterMesAtual(); 
	return dia + '/' + mes + '/' + obterAnoAtual();
} 

/**
 * Função remove autoCompleto do campo
 * @param id - Id do componente
 * @param tipo - Tipo do componente, (cpf, cnpj...)
 * @since 14/07/2014
 */
function removeAutoComplete(id, tipo) {
    var mask = "";

    switch (tipo) {
	    case 'cpf':
	            mask = $('#' + id).mask("999.999.999-99");
	            break;
	
	    case 'cnpj':
	            mask = $('#' + id).mask("99.999.999/9999-99");
	            break;
	
	    case 'numeroContrato':
	            mask = $('#' + id).mask("99.9999.999.9999999-99");
	
	    case 'outro':
	            mask = $('#' + id);
	}
    return mask.attr("autocomplete", "off");
};


function rolarPaginaParaCima() {	
	$('html, body').animate({ 
		scrollTop: $('html, body').offset().top - 80 
	}, 500);
};

function validaDataMenor1901Maior2079(data) {
	var ano = data.substring(data.length-4, data.length);

	if (((ano < 1901) || (ano > 2079)) && (ano.length != 0)) {
		return true;
	}

	return false;
};

function descricaoMes(mes) {
	meses = new Array(12);

	meses[0] = "Janeiro";
	meses[1] = "Fevereiro";
	meses[2] = "Março";
	meses[3] = "Abril";
	meses[4] = "Maio";
	meses[5] = "Junho";
	meses[6] = "Julho";
	meses[7] = "Agosto";
	meses[8] = "Setembro";
	meses[9] = "Outubro";
	meses[10] = "Novembro";
	meses[11] = "Dezembro";

	return meses[mes-1];
};

function calcularDiferencaDeDiasEntreDuasDatas(dtInicial, dtFinal){
	if (dtInicial != null && dtInicial != '' && dtFinal != null && dtFinal != '') {
		var diaInicial = dtInicial.substring(0,2);
		var mesInicial = dtInicial.substring(3,5);
		var anoInicial = dtInicial.substring(6,10);
		
		var diaFinal = dtFinal.substring(0,2);
		var mesFinal = dtFinal.substring(3,5);
		var anoFinal = dtFinal.substring(6,10);
		
		var data1 = new Date(anoInicial, (mesInicial-1), diaInicial);
		var data2 = new Date(anoFinal, (mesFinal-1), diaFinal);
	    
	    var diferenca1 = data2.getTime() - data1.getTime();
	    var diferenca2 = Math.floor(diferenca1 / (1000 * 60 * 60 * 24));
	    return parseInt(diferenca2) + parseInt(1);
	}
};

function listenToDatepickerChange($el, callback) {
	$el.find('div.data').on('changeDate', function (ev) {			
		var e = {target: { name: $('input', $(ev.currentTarget)).prop('name'), value: $('input', $(ev.currentTarget)).val() } }; 
		callback(e);
	});
}

//Objetos de uso global a aplicacao
var $caixa = $caixa || {};
$caixa.navigator = $caixa.navigator || new Navigator();
$caixa.ajaxStatus = $caixa.ajaxStatus || new AjaxStatus();
$caixa.message = $caixa.message || new Message();

//bundle config
$caixa.bundle = $caixa.bundle || new Bundle('./resources/bundle/', 'Messages');
$caixa.bundle.selectLang('pt');	

$caixa.errorManager = $caixa.errorManager || new ErrorManager($caixa.bundle, $caixa.message, 'ME_');
$caixa.userProfile = new UserProfile('consignado/userprofile/load',$caixa.errorManager);
$caixa.userProfileGroups = new UserProfile('consignado/userprofile/loadgroups',$caixa.errorManager);
$caixa.brokerConfig = $caixa.brokerConfig || new BrokerConfig($caixa.ajaxStatus, $caixa.message, $caixa.errorManager, $caixa.bundle, 3,  'MW_001', 50);

//Global Services
$caixa.globalServices = new GlobalServices('consignado/global', $caixa.errorManager);

//carrega o objeto global de alerta e confirma
var msgCCR = {}; 
requireCCR(['util/mensagem'], function (mensagem) { msgCCR = mensagem; });

var loadCCR = {
	start: function () { 
		try {
			if (!($('#ajaxStatus').data('modal') || {isShown: false}).isShown)
				$('#ajaxStatus').modal('show');
		} catch (e) {
			console.log('start loadCCR', e);
		} 
	},
	
	stop : function () {
		try {
			if (($('#ajaxStatus').data('modal') || {isShown: false}).isShown)
				$('#ajaxStatus').modal('hide');
		} catch (e) {
			console.log('stop loadCCR', e);
		}
	}
};

// CONFIGURA O SORT DO PLUGIN DATATABLE COM DATA NO FORMATO BR
jQuery.extend(jQuery.fn.dataTableExt.oSort, {
    "date-br-pre": function (a) {
        var ukDatea = a.split('/');
        return (ukDatea[2] + ukDatea[1] + ukDatea[0]) * 1;
    },

    "date-br-asc": function (a, b) {
        return ((a < b) ? -1 : ((a > b) ? 1 : 0));
    },

    "date-br-desc": function (a, b) {
        return ((a < b) ? 1 : ((a > b) ? -1 : 0));
    }
});

function collapse (evt) {
	if(evt != undefined){
		var idHeading = $(evt.currentTarget).attr('id'); 	
		var idBody = $(evt.currentTarget.parentElement).find('.panel-body').attr('id');		
		
		this.accordion(idHeading, idBody);
	}
};


function accordion(idDivHead, idDivBody ){
	
	if(idDivHead != undefined  && idDivBody != undefined){
		if($("#"+idDivBody).css('display') === 'none'){
			$("#"+idDivBody).show(500);
		}else{
			$("#"+idDivBody).hide(500);					 
		}		
	}
};

//# sourceURL=global.js