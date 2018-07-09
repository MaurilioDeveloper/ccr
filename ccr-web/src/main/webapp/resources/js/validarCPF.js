function validarCPF(cpf) {
	cpf = cpf.replace(".", "");
	cpf = cpf.replace(".", "");
	cpf = cpf.replace(".", "");
	cpf = cpf.replace("-", "");
	
	while (cpf.indexOf("_") > 0) {
		cpf = cpf.replace("_", "");
	}
	
	rcpf1 = cpf.substr(0, 9);
	rcpf2 = cpf.substr(9, 2);
	
	if (cpf == '') {
		return 'Digite o CPF para continuar!';
	}

	if (cpf.length < 11) {
		return 'Nº de dígitos do CPF menor que o normal. Redigite!';
	}

	if (cpf == '00000000000' || cpf == '11111111111' || cpf == '22222222222'
			|| cpf == '33333333333' || cpf == '44444444444'
			|| cpf == '55555555555' || cpf == '66666666666'
			|| cpf == '77777777777' || cpf == '88888888888'
			|| cpf == '99999999999') {
		return 'Este Tipo de CPF é Inválido! Redigite-o!';
	}

	d1 = 0;
	for (var i = 0; i < 9; i++) {
		d1 += rcpf1.charAt(i) * (10 - i);
	}

	d1 = 11 - (d1 % 11);
	if (d1 > 9) {
		d1 = 0;
	}

	if (rcpf2.charAt(0) != d1) {
		return 'CPF com Dígito Verificador inválido. Redigite-o!';
	}

	d1 *= 2;
	for (var i = 0; i < 9; i++) {
		d1 += rcpf1.charAt(i) * (11 - i);
	}

	d1 = 11 - (d1 % 11);
	if (d1 > 9) {
		d1 = 0;
	}

	if (rcpf2.charAt(1) != d1) {
		return 'CPF com Dígito Verificador inválido. Redigite-o!';
	}

	return '';
}
//# sourceURL=validarCPF.js