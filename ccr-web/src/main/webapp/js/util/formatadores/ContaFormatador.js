/**
 * @author c110503
 * 
 * Scritp criado para formatar contas.
 * 
 */
window.contaFormatador = {
	formatadorNumeroContaCompleto : function(valor) {
		var Mascara = '9999.999.99999999-9';
		var boleanoMascara;
		var exp = /\-|\.|\/|\(|\)| /g;
		var mascaraSemMascara = Mascara.replace(exp, '');
		campoSoNumeros = new String(valor);

		// Criar metodo fora para o tratamento
		if (mascaraSemMascara.length !== campoSoNumeros.length) {
			var caract = mascaraSemMascara.length - campoSoNumeros.length;
			var valorCompleto = mascaraSemMascara.substring(0, caract) + ''
					+ campoSoNumeros;
			campoSoNumeros = valorCompleto;
		}

		var posicaoCampo = 0;
		var NovoValorCampo = '';
		var TamanhoMascara = campoSoNumeros.length;

		for ( var i = 0; i <= TamanhoMascara; i++) {

			boleanoMascara = ((Mascara.charAt(i) == '-')
					|| (Mascara.charAt(i) == '.') || (Mascara.charAt(i) == '/'));
			boleanoMascara = boleanoMascara
					|| ((Mascara.charAt(i) == '(')
							|| (Mascara.charAt(i) == ')') || (Mascara.charAt(i) == ' '));

			if (boleanoMascara) {
				NovoValorCampo += Mascara.charAt(i);
				TamanhoMascara++;
			} else {
				NovoValorCampo += campoSoNumeros.charAt(posicaoCampo);
				posicaoCampo++;
			}
		}

		return NovoValorCampo;
	},
};