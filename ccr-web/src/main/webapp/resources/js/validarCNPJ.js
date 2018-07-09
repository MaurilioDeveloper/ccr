function validarCNPJ(cnpj) {

	if (cnpj == '') {
		return 'Digite o CNPJ para continuar!';
	} else {
		if (cnpj.length < 14) {
			return 'Nº de dígitos do CNPJ menor que o normal. Redigite!';
		}
	}

	var numeros, digitos, soma, i, resultado, pos, tamanho, digitos_iguais;

	cnpj = cnpj.replace('.', '');
	cnpj = cnpj.replace('.', '');
	cnpj = cnpj.replace('.', '');
	cnpj = cnpj.replace('-', '');
	cnpj = cnpj.replace('/', '');

	digitos_iguais = 1;

	for (i = 0; i < cnpj.length - 1; i++) {
		if (cnpj.charAt(i) != cnpj.charAt(i + 1)) {
			digitos_iguais = 0;
			break;
		}
	}

	if (!digitos_iguais) {
		tamanho = cnpj.length - 2;
		numeros = cnpj.substring(0, tamanho);
		digitos = cnpj.substring(tamanho);
		soma = 0;
		pos = tamanho - 7;
		for (i = tamanho; i >= 1; i--) {
			soma += numeros.charAt(tamanho - i) * pos--;
			if (pos < 2) {
				pos = 9;
			}
		}

		resultado = soma % 11 < 2 ? 0 : 11 - soma % 11;

		if (resultado != digitos.charAt(0)) {
			return 'CNPJ com Dígito Verificador inválido. Redigite-o!';
		}

		tamanho = tamanho + 1;
		numeros = cnpj.substring(0, tamanho);
		soma = 0;
		pos = tamanho - 7;
		for (i = tamanho; i >= 1; i--) {
			soma += numeros.charAt(tamanho - i) * pos--;
			if (pos < 2) {
				pos = 9;
			}
		}

		resultado = soma % 11 < 2 ? 0 : 11 - soma % 11;

		if (resultado != digitos.charAt(1)) {
			return 'CNPJ com Dígito Verificador inválido. Redigite-o!';
		}

		return '';

	} else {
		return 'CNPJ com Dígito Verificador inválido. Redigite-o!';
	}
}