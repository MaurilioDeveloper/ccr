$cred.fes.util.DataUtil = {

	ehPeriodoMaiorDoQueUmAno : function ehPeriodoMaiorDoQueUmAno(dataInicio, dataFim) {
		dataInicio = parseFloat(dataInicio.substr(6,4) + dataInicio.substr(3,2) + dataInicio.substr(0,2));
		dataFim = parseFloat(dataFim.substr(6,4) + dataFim.substr(3,2) + dataFim.substr(0,2));
		
		return dataFim - dataInicio > 10000;
	},

	ehDataInicialSuperiorDataFinal : function ehDataInicialSuperiorDataFinal(dataInicio, dataFim) {
		dataInicio = parseFloat(dataInicio.substr(6,4) + dataInicio.substr(3,2) + dataInicio.substr(0,2));
		dataFim = parseFloat(dataFim.substr(6,4) + dataFim.substr(3,2) + dataFim.substr(0,2));
		
		return dataFim - dataInicio < 0;
	},
	
	verificarData : function verificarData(form, idCampo, dataProibidaSiapi) {
		$cred.fes.util.message.close();
		
		var valorCampo = form[idCampo].value;

		if (valorCampo == "" || valorCampo == '__/__/____') {
			return false;
		}

		var valorCampoNumero = valorCampo.split("/").join("");

		if (isNaN(valorCampoNumero) || valorCampoNumero < 0 || isNaN(valorCampoNumero.substring(0, 1))) {
			$cred.fes.util.message.singleMessage({global : true, severity : 'ERROR', summary : 'Data com caracteres inválidos!'});
			form[idCampo].value = "";
			form[idCampo].focus();
			return false;
		}

		if (valorCampo == "" || valorCampo.length < 10) {
			$cred.fes.util.message.singleMessage({global : true, severity : 'ERROR', summary : 'Tamanho de Data Incorreto. Digite a data no formato DD/MM/AAAA.'});
			form[idCampo].value = "";
			form[idCampo].focus();
			return false;
		}
		
		var ehDataValida = this.verificarDataEsdruxula(valorCampo.substr(0, 2), valorCampo.substr(3, 2), valorCampo.substr(6, 4));
		if (!ehDataValida) {
			$cred.fes.util.message.singleMessage({global : true, severity : 'ERROR', summary : 'Data Inválida.'});
			form[idCampo].value = "";
			form[idCampo].focus();
			return false;
		} else {
			var wDtRef = new Date(valorCampo.substr(6, 4), valorCampo.substr(3, 2) - 1, valorCampo.substr(0, 2));
			
			if (Date.parse(wDtRef) >= new Date()) {

				$cred.fes.util.message.singleMessage({global : true, severity : 'ERROR', summary : 'Data não pode ser maior que a Data Atual. Redigite-a!'});
				
				form[idCampo].value = "";
				form[idCampo].focus();
				return false;
			} else {

				var dataProibidaSiapiAux = "";
				
				if (dataProibidaSiapi != "") {
					dataProibidaSiapiAux = dataProibidaSiapi;
					var wDtProibidaSIAPI = new Date(dataProibidaSiapiAux.substr(6, 4), dataProibidaSiapiAux.substr(3, 2) - 1, dataProibidaSiapiAux.substr(0, 2));
				}
				
				if (dataProibidaSiapiAux != ""
						&& Date.parse(wDtRef) > Date.parse(wDtProibidaSIAPI)) {
					$cred.fes.util.message.singleMessage({global : true, severity : 'ERROR', summary : 'Data não pode ser maior que ' + dataProibidaSiapi + '. Redigite-a'});
					form[idCampo].value = "";
					form[idCampo].focus();
					return false;
				}
			}
			return true;
		}
	},

	verificarDataEsdruxula : function verificarDataEsdruxula(pDia, pMes, pAno) {
		var v_dia, v_mes, v_ano;

		v_dia = pDia;
		v_mes = pMes;
		v_ano = pAno;

		if (isNaN(v_dia)) {
			return false;
		}
		if (isNaN(v_mes)) {
			return false;
		}
		if (isNaN(v_ano)) {
			return (false);
		}
		if ((v_dia == "00") || (v_mes == "00")) {
			return false;
		}
		if (((v_ano < 1901) || (v_ano > 2079)) && (v_ano.length != 0)) {
			return false;
		}
		if (v_dia > 31) {
			return false;
		}
		if (v_mes > 12) {
			return false;
		}
		if (v_dia == "31") {
			if ((v_mes == "04") || (v_mes == "06") || (v_mes == "09") || (v_mes == "11")) {
				return false;
			}
		}
		if (v_mes == "02") {
			if (!(v_ano % 4)) {
				if (v_dia > 29) {
					return false;
				}
			} else if (v_dia > 28) {
				return false;
			}
		}

		// o -if- abaixo testa se algum campo foi preenchido e outro deixado
		// em branco deixando a data incompleta
		if (((v_dia != "") || (v_mes != "") || (v_ano != ""))
				&& ((v_dia == "") || (v_mes == "") || (v_ano == ""))) {
			return false;
		}

		return true;
	},
	
	DT_COBOL : function DT_COBOL(pDT_CONVERSAO, pOPER) {
		var wAUX = '';
		var wSeparador = '';

		if (pDT_CONVERSAO != "") {
			if (Len(pDT_CONVERSAO) < 10) {
				if ((CInt(Right(pDT_CONVERSAO, 2)) >= 10) && (CInt(Right(pDT_CONVERSAO, 2)) <= 99)) {
					wAUX = Left(pDT_CONVERSAO, 6) + "19" & Right(pDT_CONVERSAO,2);
				} else {
					wAUX = Left(pDT_CONVERSAO, 6) + "20" + Right(pDT_CONVERSAO,2);
				}
			} else { 
				wAUX = pDT_CONVERSAO;
			}
		  
			if (pOPER == 1) {
				wSeparador = ".";
			} else {
				wSeparador = "/";
			}
		  
			DT_COBOL = Left(wAUX, 2) + wSeparador +
		             Mid(wAUX, 4,2) + wSeparador +
		   	         Right(wAUX, 4);
		} else {
			DT_COBOL = "";
		}
	},
	
};

//# sourceURL=DataUtil.js