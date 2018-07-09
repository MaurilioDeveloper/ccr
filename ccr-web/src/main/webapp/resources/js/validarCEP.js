function validarCEP(cep) {
	var valor = cep;
	var tamanho;
	
	valor = valor.replace( '.', '' );
	valor = valor.replace( '-', '' );
	tamanho = valor.length + 1;

	for (var i = 0; i < valor.length; i++) {
		if (isNaN(valor)) {
			if (valor.charAt(i) != '.' || valor.charAt(i) != '-') {
				return 'O CEP informado contém dígitos inválidos. Corrija-o!';
			}
		}
	}
	
	if (valor != '' && tamanho != 9) {
		return 'Nº de dígitos do CEP menor que o normal ou em branco. Redigite-o!';
	}
	 
	if (valor == '00000000' || valor == '11111111' || valor == '22222222' ||
		valor == '33333333' || valor == '44444444' || valor == '55555555' ||
		valor == '66666666' || valor == '77777777' || valor == '88888888' ||
 		valor == '99999999') {
		
		return 'CEP inválido!';
	}
	
	return '';
}
//# sourceURL=validarCEP.js