/* ****************************************************************
 * Componente de Datas
 ****************************************************************** */

//Namespaces
var $caixa = $caixa || {};
$caixa.data = $caixa.data || {};

/*
 * Formata datas do formato yyyy-mm-dd para o formato dd/mm/yyyy
 * */
$caixa.data.formataData =
function (strData){
	var dia = strData.substring(8);
	var mes = strData.substring(5,7);
	var ano = strData.substring(0,4);
	return dia+'/'+mes+'/'+ano;
}

/*
 * Formata datas do formato ddmmyyyy para o formato dd/mm/yyyy
 * */
$caixa.data.converteNumeroData = function (strData){
	if (strData!=null) {
		console.log("strData:" + strData);
		var dia = strData.substr(0,2);
		var mes = strData.substr(2,2);
		var ano = strData.substr(4,4);
		return dia+'/'+mes+'/'+ano;
	} else {
		return "";
	}
}

Date.isLeapYear = function (year) { 
    return (((year % 4 === 0) && (year % 100 !== 0)) || (year % 400 === 0)); 
};

Date.getDaysInMonth = function (year, month) {
    return [31, (Date.isLeapYear(year) ? 29 : 28), 31, 30, 31, 30, 31, 31, 30, 31, 30, 31][month];
};

Date.prototype.isLeapYear = function () { 
    return Date.isLeapYear(this.getFullYear()); 
};

Date.prototype.getDaysInMonth = function () { 
    return Date.getDaysInMonth(this.getFullYear(), this.getMonth());
};

Date.prototype.addMonths = function (value) {
    var n = this.getDate();
    this.setDate(1);
    this.setMonth(this.getMonth() + value);
    this.setDate(Math.min(n, this.getDaysInMonth()));
    return this;
};

var diffDataFormatada = function (d1, d2, tipo) {
	var arrayD1 = d1.split("/");
	var arrayD2 = d2.split("/");
	
	var date1 = new Date(arrayD1[2], arrayD1[1]-1, arrayD1[0]);
	var date2 = new Date(arrayD2[2], arrayD2[1]-1, arrayD2[0]);
	
	if (tipo == 'DD')
		return dayDiff(date1, date2);
	else if (tipo == 'MM')
		return monthDiff(date1, date2);
	else if (tipo == 'YY')
		return yearDiff(date1, date2);
	
}

var dayDiff = function(d1, d2) {
	if( d2 < d1 ) { 
		var dTmp = d2;
		d2 = d1;
		d1 = dTmp;
	}
	
	var days = parseInt((d2 - d1) / 86400000); // milisegundos no dia
		
	return days;
}

var monthDiff = function(d1, d2) {
	if( d2 < d1 ) { 
		var dTmp = d2;
		d2 = d1;
		d1 = dTmp;
	}
	
	var months = (d2.getFullYear() - d1.getFullYear()) * 12;
	months -= d1.getMonth() + 1;
	months += d2.getMonth();
	
	if( d1.getDate() <= d2.getDate() ) months += 1;
	
	return months;
}

var yearDiff = function(d1, d2) {
	if( d2 < d1 ) { 
		var dTmp = d2;
		d2 = d1;
		d1 = dTmp;
	}
	
	var years = (d2.getUTCFullYear() - d1.getUTCFullYear());
	
	if( d2.getMonth() < d1.getMonth() ) 
		years--;
	else if (d1.getMonth() == d2.getMonth())
		if (d2.getDate() < d1.getDate())
			years--;	
	
	return years;
}