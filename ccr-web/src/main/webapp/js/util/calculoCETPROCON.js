/**
 * @author c110503
 * 
 * Scritp criado para calcular o CET com os valores em tela.
 * 
 */
window.calculoCETPROCON = {

	calculaCET : function(meses, valorPrestacao, valorFinanciado, dataContrato,
			dataPrestacao) {
		console.log("calculaCET");
		console.log("mes --> " + meses);
		console.log("valorPrestacao --> " + valorPrestacao);
		console.log("valorFinanciado --> " + valorFinanciado);
		console.log("dataContrato --> " + dataContrato);
		console.log("dataPrestacao --> " + dataPrestacao);
		
		vnum_mes = meses;
		vvlr_pres = String(valorPrestacao);
		vvlr_prin = String(valorFinanciado);
		//vvlr_acess 		= form1.vvlr_acess.value;
		// COMPATIBILIDADE FIREFOX
		data_contrato = dataContrato;
		parcela = dataPrestacao;

		vvlr_pres = vvlr_pres.replace(".", "");
		vvlr_pres = vvlr_pres.replace(",", ".");

		vvlr_prin = vvlr_prin.replace(".", "");
		vvlr_prin = vvlr_prin.replace(",", ".");

		console.log("VAR");
		console.log("vnum_mes -->" + vnum_mes);
		console.log("vvlr_pres -->" + vvlr_pres);
		console.log("vvlr_prin -->" + vvlr_prin);
		console.log("data_contrato -->" + data_contrato);
		console.log("parcela -->" + parcela);

		
		if ((vnum_mes != '') && (vvlr_pres != '') && (vvlr_prin != '')) {

			if ((vvlr_pres * vnum_mes) < vvlr_prin) {
				console.log('Total de Prestacoes menor que valor Principal!');
				return -1;
			}
			if (parseInt(vvlr_pres) > parseInt(vvlr_prin)) {
				console.log('Juros ao mes superior a 100%!');
				return -1;
			}

			//var v2 = parseInt(vvlr_prin) + parseInt(vvlr_acess);
			var v2 = parseInt(vvlr_prin);
			var a = 0;
			var b = 0;
			var p = vvlr_pres;
			var vdif_dc = new Array();
			var primeiraParcela = parcela;
			var vvlr_tot = 0;
			var ultimaParcela = this.descobreUltimaParcela(primeiraParcela, parcela,
					vnum_mes);

			//parcela		= primeiraParcela;
			vdif_dc[0] = this.calculaDiferencaEntreDatas(data_contrato,
					ultimaParcela);
			var n = (vnum_mes) * (-1);
			//var n=(parseInt(vdif_dc[0][0]) / 30)*(-1);
			//alert(vdif_dc[0][0] + '\n' + n + '\n' + ultimaParcela);
			var vjur1 = 1;
			var vjur2 = 0;
			var vjur_fin1 = 1;
			var vjur_fin2 = 0;
			var vjur = 0.5;
			var vjuranox = 0;
			var vjur_fin = 0.5;
			var vjuranox_fin = 0;
			//alert(v2);
			while (Math.abs(v2 - a) > 0.00001) {
				a = p * ((1 - (Math.pow((1 + vjur), n))) / vjur);
				if (a < v2)
					vjur1 = vjur;
				else
					vjur2 = vjur;
				vjur = (vjur1 + vjur2) / 2;
			}
			vjuranox = (Math.pow((1 + parseFloat(vjur)), 12) - 1) * 100;
			vjuranox = String(this.Arredonda(vjuranox, 5));
			vjur = vjur * 100;
			vjur = String(this.Arredonda(vjur, 5));

			while (Math.abs(vvlr_prin - b) > 0.00001) {
				b = p * ((1 - (Math.pow((1 + vjur_fin), n))) / vjur_fin);
				if (b < vvlr_prin)
					vjur_fin1 = vjur_fin;
				else
					vjur_fin2 = vjur_fin;
				vjur_fin = (vjur_fin1 + vjur_fin2) / 2;
				//alert('b: ' + b + '\nprinc: ' + v2 + '\nv2-b: ' + (v2-b));
			}
			vjuranox_fin = (Math.pow((1 + parseFloat(vjur_fin)), 12) - 1) * 100;
			vjuranox_fin = String(this.Arredonda(vjuranox_fin, 5));
			vjur_fin = vjur_fin * 100;
			vjur_fin = String(this.Arredonda(vjur_fin, 5));

			vvlr_pres = String(this.Arredonda(vvlr_pres, 2));
			vvlr_pres = vvlr_pres.replace(".", ",");
			vvlr_prin = String(this.Arredonda(vvlr_prin, 2));
			vvlr_prin = vvlr_prin.replace(".", ",");
			//vvlr_acess = String(Arredonda(vvlr_acess,2));
			//vvlr_acess = vvlr_acess.replace(".",",");

			//alert('vjur: ' + vjur + '\nvjurano: ' + vjuranox + '\nvjur2: ' + vjur_fin + '\nvjurano2: ' + vjuranox_fin);
			console.log("vjur -->" + vjur);
			console.log("vjuranox -->" + vjuranox);
			var arrayCET = [this.Arredonda(vjur,2),this.Arredonda(vjuranox,2)];
			return arrayCET;
			
		}
		

	},

	/**
	 * 
	 */
	descobreUltimaParcela : function(primeiraParcela, parcela, vnum_mes) {
		pp_dia = primeiraParcela.substring(0, 2);
		pp_mes = primeiraParcela.substring(3, 5);
		pp_ano = primeiraParcela.substring(6, 10);
		p_dia = parcela.substring(0, 2);
		p_mes = parcela.substring(3, 5);
		p_ano = parcela.substring(6, 10);
		up_dia = pp_dia;
		up_mes = parseInt(p_mes) + (parseInt(vnum_mes) - 1);
		up_ano = p_ano;

		if (up_mes > 12) {
			up_mes = up_mes - 12;
			up_ano = parseInt(up_ano) + 1;
		}
		if ((up_mes == 4) || (up_mes == 6) || (up_mes == 9) || (up_mes == 11)) {
			if (up_dia > 30) {
				up_dia = 30;
			} else {
				up_dia = pp_dia;
			}
		}
		if (up_mes == 2) {
			if (up_dia > 28) {
				if (up_ano % 4 != 0) {
					up_dia = 28;
				} else {
					up_dia = 29;
				}
			}
		}
		ultDiaStr = String(up_dia);
		ultMesStr = String(up_mes);
		if (String(up_dia).length < 2)
			ultDiaStr = "0" + String(up_dia);
		if (String(up_mes).length < 2)
			ultMesStr = "0" + String(up_mes);
		vdata = ultDiaStr + '/' + ultMesStr + '/' + up_ano;

		return vdata;
	},

	calculaDiferencaEntreDatas : function(dataInicio, dataFim) {
		initDia = dataInicio.substring(0, 2);
		initMes = dataInicio.substring(3, 5);
		initAno = dataInicio.substring(6, 10);

		fimDia = dataFim.substring(0, 2);
		fimMes = dataFim.substring(3, 5);
		fimAno = dataFim.substring(6, 10);
		vdi_v = new Date(initMes + '/' + initDia + '/' + initAno);
		vdf_v = new Date(fimMes + '/' + fimDia + '/' + fimAno);
		vdi = Date.parse(vdi_v);
		vdf = Date.parse(vdf_v);

		vdif_data = (((((vdf - vdi) / 1000) / 60) / 60) / 24);
		vdif_data2 = vdf - vdi;

		return new Array(vdif_data, vdif_data2);
	},

	Arredonda : function(valor, casas) {

		var novo = Math.round(valor * Math.pow(10, casas))
				/ Math.pow(10, casas);

		return (novo);

	},

};