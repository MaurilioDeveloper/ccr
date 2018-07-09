var $ccr = $ccr || {};
$ccr = $ccr || {};
$ccr.vr = $ccr.vr || {};
$ccr.fn = $ccr.fn || {};
$ccr.vr.url = $ccr.vr.url || {};
$ccr.ui = $ccr.ui || new UIHelper();
$ccr.validators = $ccr.validators || new Validators();
$ccr.validators.withErrorRender(new BootstrapErrorRender());
$ccr.msgm = $ccr.msgm || {};
$ccr.regex = $ccr.regex || {};





$ccr.fn.limparCamposDocumentoPrincipal = function limparCamposDocumentoPrincipal(){
	$ccr.vr.documentoPrincipal = '';
	$ccr.vr.descricaoTipoDocumento = '';
	$ccr.vr.numeroContrato = '';
	$ccr.vr.codigoProduto = '';
	$ccr.vr.codigoModalidade = '';
	$ccr.vr.numeroDvContrato = '';
	$ccr.vr.cpfCliente = '';
	$ccr.vr.tipoAcaoValidacao = '';
	$ccr.vr.dadosPessoais = null;
};

$ccr.fn.loadTemplate = function loadTemplate(nomeViewHTML, donefunction) {
	return $ccr.fn.getTemplate(nomeViewHTML).done(donefunction);
};

$ccr.fn.getTemplate = function getTemplate(nomeTemplateHTML) {
	return $.get($ccr.fn.urlTemplate(nomeTemplateHTML));
};

$ccr.fn.urlTemplate = function urlTemplate(nomeTemplateHTML) {
	return $ccr.vr.url.templateRoot + '/' + nomeTemplateHTML + '.html';
};

$ccr.fn.loadView = function loadView(nomeViewJS, donefunction) {
	return $ccr.fn.getView(nomeViewJS).done(donefunction);
};

$ccr.fn.getView = function getView(nomeViewJS) {
	return $.get($ccr.fn.urlView(nomeViewJS));
};

$ccr.fn.urlView = function urlView(nomeViewJS) {
	return $ccr.vr.url.viewRoot + '/' + nomeViewJS + '.js';
};


$ccr.fn.agendadorBarramento = function agendadorBarramento(that, tentativas, atraso, fnObterDados, fnSemDados) {	
	if(tentativas > 0){						
		if(fnObterDados(that, tentativas)){							
			tentativas = tentativas - 1;				
			setTimeout( function setTimeoutAgendador(){$simulador.fgts.fn.agendadorBarramento(that, tentativas, atraso, fnObterDados, fnSemDados);} ,atraso);							
		}	
	}else {
		fnSemDados(that);
	}	 

	
};

$ccr.fn.ajax = function ajax(vrUrl, vrData, fnSucess, fnError, isAsync) {
	return $.ajax({
		type : $ccr.vr.POST,
		contentType : $ccr.vr.appTipoJson,
		dataType : $ccr.vr.json,
		url : vrUrl,
		data : JSON.stringify(vrData),
		async : _.isUndefined(isAsync) ? true : isAsync,
		success : fnSucess,
		error : _.isFunction(fnError) ? fnError : $ccr.fn.ajaxTratamentoErroDefault
	});
};

$ccr.fn.ajaxTratamentoErroDefault = function ajaxTratamentoErroDefault(jqXHR, textStatus, errorThrown){
	$ccr.fn.ajaxStatusStop();
	$caixa.errorManager.ajaxError(jqXHR, textStatus, errorThrown);
};

$ccr.fn.estabelecimentoPost = function post(vrUrl, vrData, fnSucess, fnError, isAsync) {
	return $ccr.fn.ajax($ccr.vr.url.estabelecimentoRest + vrUrl, vrData, fnSucess, fnError, isAsync);
};

$ccr.fn.totalizadorPost = function post(vrUrl, vrData, fnSucess, fnError, isAsync) {
	return $ccr.fn.ajax($ccr.vr.url.totalizadorPost + vrUrl, vrData, fnSucess, fnError, isAsync);
};

$ccr.fn.post = function post(vrUrl, vrData, fnSucess, fnError, isAsync) {
	return $ccr.fn.ajax($ccr.vr.url.rest + vrUrl, vrData, fnSucess, fnError, isAsync);
};

$ccr.fn.isAjaxStatusNaoIniciado = function isAjaxStatusNaoIniciado() {
	return $('#ajaxStatus').css('display') == "none";
};

$ccr.fn.ajaxStatusStop = function fnAjaxStatusStop() {
	$('#ajaxStatus').fadeOut('fast', function onRemoveModalFadeOut() {
		$('#ajaxStatus').css('z-index', 1050).css('display','none');
		var excluiBackdrop = true;
		
		$('.modal').each(function(index, obj) {
			if($(obj).css('display') == 'block') {
				excluiBackdrop = false;
			} 
		});
		
		if(excluiBackdrop) {
			$(".modal-backdrop").fadeOut(300, function() {
				$('.modal-backdrop').remove();
			});
		} else {
			$('.modal-backdrop').css('z-index', 1040);
		}
	});
};

$ccr.fn.mensagemConfirmacao = function MensagemConfirmacao(corpoMensagem, msgBtnCancelar, msgBtnConfirmar, callback, callbackCancelar) {
	var id = 'MDLconfirmacao_' + (Math.random()).toFixed(3) * 1000;
	
	var MsgConfirmModal = $('<div class="modal hide fade" id="' + id + '">'
			+ '<div class="modal-header">'
			+ '<a class="close" data-dismiss="modal" >&times;</a>'
			+ '<h3>Mensagem de Confirma&ccedil;&atilde;o</h3>' + '</div>'
			+ '<div class="modal-body">' + '<p>' + corpoMensagem + '</p>'
			+ '</div>' + '<div class="modal-footer">'
			+ '<a href="#" id="btnConfirmar" class="btn btn-primary btn-ok">' + msgBtnConfirmar
			+ '</a>' 
			+ '<a href="#" id="btnCancelar" class="btn btn-cancelar" data-dismiss="modal">'
			+ msgBtnCancelar + '</a>'
			+ '</div>' + '</div>');
	MsgConfirmModal.modal('show');
	
	MsgConfirmModal.find('#btnConfirmar').click(function(event) {
		event.preventDefault();
		MsgConfirmModal.modal('hide');
		//Ao fechar retira o html gerado evitando lixo no DOM
		eval('$("#'+ id + '").remove();');
		callback();
	});
	
	MsgConfirmModal.find('#btnCancelar').click(function(event) {
		event.preventDefault();
		MsgConfirmModal.modal('hide');
		eval('$("#'+ id + '").remove();');
		if(callbackCancelar != undefined){
			callbackCancelar();
		}
	});
};

$ccr.fn.exibirMensagem = function exibirMensagem(mensagem) {
	$caixa.message.processMessage($caixa.message.infoMessage(mensagem));
},

$ccr.fn.exibirMensagemWarn = function exibirMensagem(mensagem) {
	$caixa.message.processMessage($caixa.message.warnMessage(mensagem));
},

$ccr.fn.fecharMensagem = function fnFecharMensagem() {
	$caixa.message.processMessage($caixa.message.close());
};

$ccr.fn.loadMask = function () {
	loadMask();
	
	jQuery(function($){
		$('.mesAno').mask('99/9999').off('focus.mask').off('blur.mask');
		$('.numeroContrato').mask('9.999.999.999.999.999').off('focus.mask').off('blur.mask');
		$('.alfaNumerico').alphanum();
		$('.dataHoraCompleta').mask('99/99/9999 99:99:99').off('focus.mask').off('blur.mask');
		$('.luhn').mask("99999999-9").off('focus.mask').off('blur.mask');
		$(".numeroContaSemAsteriscos").mask("99999999-9").off('focus.mask').off('blur.mask');
	});
};

$ccr.fn.modalShow = function modalShow(idDivModal) {
	$('.modal').css({position: 'fixed', top: '3%',right: '3%', left: '3%',width: 'auto',margin: '0'});
	$('.modal-body').css({ 'max-height': '350px', 'padding': '15px', 'overflow-y': 'auto', '-webkit-overflow-scrolling': 'touch'});
	$('#'+idDivModal).modal('show');
};

$ccr.fn.modalMessageContainerIdShown = function modalContainerIdShown(idModal, modalContainerId) {
	return $('#'+idModal).on("shown", function() {
		$caixa.message.containerId = modalContainerId;
	});
};

$ccr.fn.modalMessageContainerIdHidden = function modalContainerIdHidden(idModal, modalContainerId) {
	return $('#'+idModal).on("hidden", function() {
		$ccr.fn.fecharMensagem();
		$caixa.message.containerId = modalContainerId;
	});	
};

$ccr.fn.optionsPreenchidoInvalidos = function (names) {
	var result = false;
	if(names.length == 0) {
		 return false;
	}
	
	for ( var int = 0; int < names.length; int++) {
		if( $('[name=' + names[int] + ']:checked').val() == undefined) {
			result = true;
			$('[name=' + names[int] + ']').closest('.control-group').addClass('error');			
			$('<span style="margin-top: 6px;" id="'+ names[int] +'_BootstrapErrorRender" class="help-inline">Campo obrigatório</span>').appendTo($('[name=' + names[int] + ']').closest('.controls'));
		}	
	}	
	return result;	
};

$ccr.fn.isCampoInvalido = function isCampoInvalido(idCampo) {
	return !$ccr.validators.withInput(idCampo).validate();
};

$ccr.fn.isCamposInvalidos = function isCamposInvalidos(listaIdCampos) {
	var isContemCampoInvalido = false;
	
	$(listaIdCampos).each(function(pos, idCampo) {
		if ($ccr.fn.isCampoInvalido (idCampo)) {
			isContemCampoInvalido = true;
		}
	});
	
	return isContemCampoInvalido;
};

$ccr.fn.focusPrimeiroCampoInvalido = function focusPrimeiroCampoInvalido(listaIdCampos) {
	var focus = false;
	_.each(listaIdCampos, function(campo){
		if(_.isEqual($('#' + campo).val(), "") && !focus){
			$('html, body').animate({scrollTop: $('#'+campo).offset().top - 60}, 500);
			$('#' + campo).focus();
			focus = true;
		}
	});
};

$ccr.fn.limparValidadores = function limparValidadores(formId) {
	return $ccr.validators.withForm(formId).cleanErrors();
};

$ccr.fn.exportarTableToXsl = function exportarTableToXsl(table, name) {
	var uri = 'data:application/vnd.ms-excel;base64,';
	var template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><meta http-equiv="content-type" content="text/html; charset=utf-8" /><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--></head><body><table>{table}</table></body></html>';

	var base64 = function(s) {
		return window.btoa(unescape(encodeURIComponent(s)));
	};
	var format = function(s, c) {
		return s.replace(/{(\w+)}/g, function(m, p) {
			return c[p];
		});
	};
	
	if (!table.nodeType){
		table = document.getElementById(table);
	}
	
	var ctx = {
			worksheet : name || 'Planilha1',
			table : table.innerHTML
	};
	
	window.location.href = uri + base64(format(template, ctx));	
};

$ccr.fn.configurarChosen = function configurarChosen() {
    var config = {
      '.chosen-select-deselect'  : {allow_single_deselect: true},
      '.chosen-select-no-single' : {disable_search_threshold: 10},
      '.chosen-select-width'     : {width: '95%'},
      '.chosen-select'           : {placeholder_text_single : ' '}
    };
    
    for (var selector in config) {
      $(selector).chosen(config[selector]);
    }
    
	$.each($("div[id$=chosen]"), function(i, el){
		if($(el).css('width') == "0px")
			$(el).attr("style", 'width: 250px;');
		
	});
	
	//Define a margem com componentes span.
	$('[class*=span] .chosen-select').closest('div').css('margin-left','0px');
};



/**
 * Configura uma <option> com o valor e label informado.
 * 
 * @param value
 *            valor do option
 * @param label
 *            label do option
 * @returns {String}
 * @since 13/08/2013
 */
$ccr.fn.setOption = function fnSetOption(value, label) {
	return '<option value="' + ((value === null || value === undefined) ? '' : value) + '">' + label + '</option>';
};

$ccr.fn.removeMascara = function removeMascara(valor) {
	if(!valor) 
		return '';
	
	valor += '';
	
	return valor.replace(/\_/g, '').replace(/\./g, '').replace(/\,/g, '').replace(/\\/g, '').replace(/\-/g, '').replace(/\//g, '').replace(/\(/g, '').replace(/\)/g, '')
			.replace(/\:/g, '').replace(/\s/g, '');
};

$ccr.fn.apresentarMensagem = function apresentarMensagem(data) {	
	if (data.contemErro) {
		$caixa.message.multipleMessage(data.message);		
	}
};

$ccr.fn.disabled = function disabled(id, boolean) {
	$('#'+id).prop('disabled', boolean);
};

$ccr.fn.getEtapaAtiva = function getEtapaAtiva(){
	return $(".wizard-steps").find(".active");
};

$ccr.fn.isEtapaCompleta = function isEtapaCompleta(){
	return $ccr.fn.getEtapaAtiva().is('.complete');
};

$ccr.fn.verificarNumeroEtapaCompleta = function verificarNumeroEtapaCompleta(etapa){
	return $("#crwizard_step_" + etapa).is('.complete');
};

$ccr.fn.moedaToFloat = function moedaToFloat(vlr) {
	if(!vlr){
		return 0;
	}
	
	return vlr.replace($ccr.regex.sinalMais, '').replace($ccr.regex.virgula, '.').replace($ccr.regex.naoNumericos,'')
			.replace($ccr.regex.porcentagemSinal,'');
};

/**
 * Transforma uma data formato AAAA-MM-DD em uma data no formato DD/MM/AAAA
 * 
 * @since 09/09/2014
 */
$ccr.fn.formataDataDiaMesAno = function formataDataDiaMesAno(data) {
	var dataSplit = data.split("-");
	return dataSplit[2] + "/" + dataSplit[1] + "/" + dataSplit[0];
};


$ccr.fn.formataDataAnoMesDia = function formataDataAnoMesDia(valor) {
	var data = valor.split("/");
	return data[2] + data[1] + data[0];
};

$ccr.fn.limparValidacoes = function limparValidacoes(listaIds) {
	_.each(listaIds, function(elementoHTML, index){
		$ccr.validators.cleanError(elementoHTML);
	});
};

$ccr.fn.retiraMascaraNumero = function (valor) {
	return valor.replace(/[^\d]+/g, '');
};

$ccr.fn.dateToDate = function dateToDate(dt) {
	if ($.isNumeric(dt)) {
		return new Date(dt);
	}
	
	var regex = new RegExp(/^(0?[1-9]|[12][0-9]|3[01])[\/\-](0?[1-9]|1[012])[\/\-]\d{4}$/);
	
	if (regex.test(dt)) {
		return new Date($.datepicker.parseDate('dd/mm/yy', dt));
	}
	
	if ($ccr.fn.isDataValida(dt)){
		return dt;
	}
	
	return '';
};

$ccr.fn.isDataValida = function isDataValida(value) {
	if(!value)
		return false;

	var rxFormatoData = /^(\d{1,2})(\/|-)(\d{1,2})(\/|-)(\d{4})$/; 
	var dtArray = value.match(rxFormatoData);

	if (_.isEmpty(dtArray))
		return false;

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
 * Limita os 25 primeiros caracteres
 * 
 * @param str, qtdCaracter
 *
 */
$ccr.fn.limitaCaracteres = function limitaCaracteres(str, qtdCaracter){
	var novaString = '';		
	var stringRest = '';
	
	if((str.length) > qtdCaracter){
		stringRest = '...';
	}
	
	novaString = str.substring(0,qtdCaracter);
	return novaString+stringRest;
};


$ccr.fn.mascararData = function mascararData(milisegundos) {
	var dtRetorno = '';
	
	if(_.isString(milisegundos)) {
		if(milisegundos.indexOf($ccr.vr.strTraco) > -1){
			dtRetorno = $ccr.fn.convertStringYYMMDDToDate(milisegundos);
		} else if(milisegundos.indexOf($ccr.vr.strPonto) > -1){
			dtRetorno = milisegundos.replace($ccr.vr.regexPonto, $ccr.vr.strBarraInvertida$ccr.vr.strBarraInvertida);
		} else if(milisegundos.length === 8){
			dtRetorno = milisegundos.substring(0,2) + $ccr.vr.strBarraInvertida + milisegundos.substring(2,4) + $ccr.vr.strBarraInvertida 
					+ milisegundos.substring(4,8);
		}
	} else if($.isNumeric(milisegundos)){
		dtRetorno = $.datepicker.formatDate($ccr.vr.dataRegexFormat, new Date(milisegundos + 0300));
	}
	
	return dtRetorno;
};

$ccr.fn.convertStringYYMMDDToDate = function convertStringYYMMDDToDate(date) {
	var spltDate = date.substring(0,10).split($ccr.vr.strTraco);
	var nDate = spltDate[2] + $ccr.vr.strBarraInvertida + (spltDate[1].length < 2 ?  "0" + (spltDate[1]) : (spltDate[1] ))  
				+ $ccr.vr.strBarraInvertida + spltDate[0];
	
	return nDate;
};


/**
 * Habilita todos os elementos de um formulario informado.
 * 
 * @param formID
 *            elemento HTML
 * @param boolean
 *            TRUE - desabilida
 */
$ccr.fn.habilitarFormulario = function habilitarFormulario(idForm) {
	$('#'+idForm+' *').attr("disabled",false);
	$('#'+idForm+' button').attr("disabled",false);	
	$('#'+idForm+' a').css('display', 'block');
	$('#'+idForm+' div .date span').removeAttr("style");
};

/**
 * Desabilita todos os elementos de um formulario informado.
 * 
 * @param formID
 *            elemento HTML
 */
$ccr.fn.desabilitarFormulario = function desabilitarFormulario(idForm) {
	 $('#'+idForm+' *').attr("disabled",true);
	 $('#'+idForm+' a').css('display', 'none');
	 $('#'+idForm+' div .date span').attr("style", "display:none");
};


$ccr.fn.mascararValor = function mascararValor(valor, mascara) {
	var boleanoMascara;
	var exp = /\-|\.|\,|\/|\(|\)| /g;
	var mascaraSemMascara = mascara.replace(exp, '');
	var campoSoNumeros = new String(valor);

	if (mascaraSemMascara.length !== campoSoNumeros.length) {
		var caract = mascaraSemMascara.length - campoSoNumeros.length;
		var valorCompleto = mascaraSemMascara.substring(0, caract) + '' + campoSoNumeros;
		campoSoNumeros = valorCompleto;
	}

	var posicaoCampo = 0;
	var novoValorCampo = '';
	var tamanhoMascara = campoSoNumeros.length;

	for ( var i = 0; i <= tamanhoMascara; i++) {
		boleanoMascara = ((mascara.charAt(i) == '-') || (mascara.charAt(i) == '.') || (mascara.charAt(i) == ',') || (mascara.charAt(i) == '/'));
		boleanoMascara = boleanoMascara || ((mascara.charAt(i) == '(') || (mascara.charAt(i) == ')') || (mascara.charAt(i) == ' '));

		if (boleanoMascara) {
			novoValorCampo += mascara.charAt(i);
			tamanhoMascara++;
		} else {
			novoValorCampo += campoSoNumeros.charAt(posicaoCampo);
			posicaoCampo++;
		}
	}

	return novoValorCampo;
};

/**
 * Função que formata um valor com a quantidade de casas decimais informada
 * 
 * @param valor
 *            a ser formatado
 * @param decimais
 *            qtd de casas decimais
 * @returns {String} valor mascarado
 * @since 10/10/2013
 */
$ccr.fn.mascararTaxa = function mascararTaxa(valor, decimais) {
	var valorStr = new String(parseFloat(valor).toFixed(decimais));
	valorStr = valorStr.replace("\.", "\,");
	return valorStr;
}

$ccr.fn.mascararCPF = function mascararCPF(valor){
	if($.isNumeric(valor)){
		return $ccr.fn.mascararValor(valor, '000.000.000-00');
	}
	
	return '';
};

$ccr.fn.mascararCNPJ = function mascararCNPJ(valor){
	if($.isNumeric(valor)){
		valor = $ccr.fn.preencheZeroEsquerda(valor,14);
		return $ccr.fn.mascararValor(valor, '00.000.000/0000-00');
	}
	return '';
};

$ccr.fn.mascararCEP = function mascararCEP(valor){
	if($.isNumeric(valor)){
		return $ccr.fn.mascararValor(valor, '99999-999');
	}
	
	return '';
};

$ccr.fn.mascararMoeda = function mascararMoeda(valor) {
	if($.isNumeric(valor)){
		var valorStr = new String(parseFloat(valor).toFixed(2));
		valorStr = valorStr.replace(/\D/g, "");
		valorStr = valorStr.replace(/[0-9]{19}/, "inválido");
		valorStr = valorStr.replace(/(\d{1})(\d{17})$/, "$1.$2");
		valorStr = valorStr.replace(/(\d{1})(\d{14})$/, "$1.$2");
		valorStr = valorStr.replace(/(\d{1})(\d{11})$/, "$1.$2");
		valorStr = valorStr.replace(/(\d{1})(\d{8})$/, "$1.$2");
		valorStr = valorStr.replace(/(\d{1})(\d{5})$/, "$1.$2");
		valorStr = valorStr.replace(/(\d{1})(\d{1,2})$/, "$1,$2");
		
		return valorStr;
	}
	
	return '';
};

$ccr.fn.validarData = function validarData(vlrData){
	//First check for the pattern
		if(!/^\d{2}\/\d{2}\/\d{4}$/.test(vlrData))
			return false;
		
	// Parse the date parts to integers
		var parts = vlrData.split("/");
		var day = parseInt(parts[0], 10);
		var month = parseInt(parts[1], 10);
		var year = parseInt(parts[2], 10);
		
	// Check the ranges of month and year
		if(year < 1000 || year > 3000 || month == 0 || month > 12)
			return false;
		
		var monthLength = [ 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 ];
		
	// Adjust for leap years
		if(year % 400 == 0 || (year % 100 != 0 && year % 4 == 0))
			monthLength[1] = 29;
		
	// Check the range of the day
		return day > 0 && day <= monthLength[month - 1];
};

$ccr.fn.validarDataHoraCompleta = function validarDataHoraCompleta(dtHoraCompleta){
	var dtHoraOK   = true;
	var apenasData = dtHoraCompleta.substring(0, 10);
	
	if(!$ccr.fn.validarData(apenasData)){
		dtHoraOK =  false;
	} else {
		var horaCompleta = dtHoraCompleta.substring(11, 19);
		
		if (!/^([0-1]?[0-9]|2[0-4]):([0-5][0-9])(:[0-5][0-9])?$/.test(horaCompleta)){
			dtHoraOK = false;
		}
	}
	
	return dtHoraOK;
};

$ccr.fn.diffDiasEntreDatas = function (dtInicio, dtFim){
	var umDia = 24 * 60 * 60 * 1000;
	
	return Math.round(Math.abs((dtInicio.getTime() - dtFim.getTime()) / umDia));
};

/**
 * Função que formata um valor com a mascara de moeda no formato 999.999.999,99.
 * 
 * @param valor
 *            a ser formatado
 * @returns {String} valor mascarado
 * @see http://forum.imasters.com.br/topic/244057-mscara-moeda-com-expresso-regular/
 * @since 23/07/2013
 */
$ccr.fn.mascaraMoeda = function (valor) {
	var valorStr = new String(parseFloat(valor).toFixed(2));
	valorStr = valorStr.replace(/\D/g, "");
	valorStr = valorStr.replace(/[0-9]{19}/, "inválido");
	valorStr = valorStr.replace(/(\d{1})(\d{17})$/, "$1.$2");
	valorStr = valorStr.replace(/(\d{1})(\d{14})$/, "$1.$2");
	valorStr = valorStr.replace(/(\d{1})(\d{11})$/, "$1.$2");
	valorStr = valorStr.replace(/(\d{1})(\d{8})$/, "$1.$2");
	valorStr = valorStr.replace(/(\d{1})(\d{5})$/, "$1.$2");
	valorStr = valorStr.replace(/(\d{1})(\d{1,2})$/, "$1,$2");
	return valorStr;
};

/**
 * Remover mensagem de validação do container
 * 
 * @param container
 */
$ccr.fn.removerMensagemValidacao = function removerMensagemValidacao(
		container) {
	$('.help-inline', container).remove();
	$('.error', container).removeClass('error');
};

/**
 * Preenche com zeros a esquerda
 * 
 * @param valor,tamanho
 */
$ccr.fn.preencheZeroEsquerda = function (valor,tamanho){
	if(valor != undefined ){
		valor = valor.toString();
		var qtd = valor.length;
		if(qtd < tamanho){
			var limite = tamanho-qtd;
			for(var i=0;i<limite;i++){
				valor = '0'+valor;
			}
		}
	}	
	return valor;
};


/**
 * Remove zeros a esquerda
 * 
 * @param valor,tamanho
 */
$ccr.fn.removeZeroAEsquerda = function removeZeroAEsquerda(valorString) {
	return  valorString.replace(/^0+/, '');
};

$.extend( $.fn.dataTableExt.oSort, {
	"ordenarDataHora-pre": function ( a ) {
	if (a == '') {
	return 0;
	}
	var dia = a.substring(0,2);
	var mes = a.substring(3,5);
	var ano = a.substring(6,10);
	var hora = a.substring(11,13);
	var minuto = a.substring(14,16);

	return (ano + mes + dia + hora + minuto) * 1;
	},

	"ordenarDataHora-asc": function ( a, b ) {
	return ((a < b) ? -1 : ((a > b) ? 1 : 0));
	},

	"ordenarDataHora-desc": function ( a, b ) {
	return ((a < b) ? 1 : ((a > b) ? -1 : 0));
	},
});

function loadMaskTel() {
    jQuery(function($){   
    	$('.telefone-novo-digito').mask("9999-9999?9");
    });
};

$ccr.fn.mascararTelefone = function mascararTelefone(valor) {
		
		var regra = /^[0-9]+$/;
		
		if (valor.match(regra)){
				
			var qtdeCaracter = valor.length;		
			
			if(qtdeCaracter >= 10){
				
				var ddd = valor.substr(0,2);
				var tel = valor.substr(2, qtdeCaracter);
				var telefoneFormatado;
				
				telefoneFormatado = "("+ddd+ ")"+tel;
				
				return telefoneFormatado;
			}
			
			if((qtdeCaracter == 8 ) ||(qtdeCaracter == 9)){
				
				var separador = "-";
				var formatarTelefone = valor.substr(0,4);
				var mascararTelefone = formatarTelefone+separador+valor.substr(4, qtdeCaracter);
				
				return mascararTelefone;			
			}
		}else{
			return valor;
		}
};

$ccr.fn.mascararPrefixoDiscagem = function mascararPrefixoDiscagem(valor) {
	
	var regra = /^[0-9]+$/;
	
	if (valor.match(regra)){
		
		var retorno = "("+valor+")";
		return retorno;
	}else{
		return valor;
	}
	
};

$ccr.fn.getDtNascimentoCliente = function getDtNascimentoCliente(that) {
	var dadosCliente = that.wizard.views[0].model.get('dados').retronoClienteDetalhado.camposRetornados;
	var dataNasc = dadosCliente.dataNascimento.data.replace(/-/g,"/");
	return new Date(Date.parse(dataNasc));
};

$ccr.fn.pTrim = function pTrim(str) {
	var carNULL = /\u0000/g;
	var carSOH = /\u0001/g;
	if(str == undefined)
		return '';
	
	return $.trim(str.replace(carNULL, "").replace(carSOH,""));
}

/**
 * Função que formata um valor com a mascara informada.
 * 
 * @param valor
 *            a ser formatado
 * @param Mascara
 *            formato da mascara
 * @returns {String} valor mascarado
 */
$ccr.fn.mascararValor = function mascararValor(valor, Mascara) {
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

$ccr.fn.pararLoadingAoCarregar = function pararLoadingAoCarregar() {
	$('#resultadoTotalizador').html('');
}

$ccr.fn.iniciarLoadingAoCarregar = function () {
	$('#resultadoTotalizador').html('<img src="layout/img/loading.gif">');
}

jQuery.extend(jQuery.fn.dataTableExt.oSort, {
    "value-br-pre": function (a) {
    	var index = a.indexOf('<div');
    	var valor = a.substring(0, index);
        valor = valor.replace('.', '').replace(',', '');
        return parseInt(valor, 10);
    },

    "value-br-asc": function (a, b) {
        return ((a < b) ? -1 : ((a > b) ? 1 : 0));
    },

    "value-br-desc": function (a, b) {
        return ((a < b) ? 1 : ((a > b) ? -1 : 0));
    }
}); 

jQuery.extend(jQuery.fn.dataTableExt.oSort, {
    "value-br-pre": function (a) {
    	var index = a.indexOf('<div');
    	var valor = a.substring(0, index);
        valor = valor.replace('.', '').replace(',', '');
        return parseInt(valor, 10);
    },

    "value-br-asc": function (a, b) {
        return ((a < b) ? -1 : ((a > b) ? 1 : 0));
    },

    "value-br-desc": function (a, b) {
        return ((a < b) ? 1 : ((a > b) ? -1 : 0));
    }
}); 

jQuery.extend(jQuery.fn.dataTableExt.oSort, {
    "value-br-pre": function (a) {
    	var index = a.indexOf('<div');
    	var valor = a.substring(0, index);
        valor = valor.replace('.', '').replace(',', '');
        return parseInt(valor, 10);
    },

    "value-br-asc": function (a, b) {
        return ((a < b) ? -1 : ((a > b) ? 1 : 0));
    },

    "value-br-desc": function (a, b) {
        return ((a < b) ? 1 : ((a > b) ? -1 : 0));
    }
}); 
