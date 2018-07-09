define('configuracoes', {
	'urlBaseRest' : '/ccr-web/consignado/',
	'imgDir' : 'resources/img',
	
	preparaAtributos: function (attrs, tipos) {		
		if (tipos && tipos instanceof Object) {
			for (var atributo in attrs) {
				var nome = atributo, valor = attrs[atributo]; 
				
				if (valor == null || valor == undefined || $.trim(String(valor)) == '') {
					continue;
				} else {					
					if (valor instanceof Object) {
						for (var atributoCampo in valor) {
							var nomeObj = nome + '_' + atributoCampo, valorObj = valor[atributoCampo];
							
							if (valorObj == null || valorObj == undefined || $.trim(String(valorObj)) == '') {
								continue;
							} else {
								if (tipos.hasOwnProperty(nomeObj))
									valor[atributoCampo] = this.formata(valorObj, tipos[nomeObj]);
							}
						}						
						
						attrs[atributo] = valor;
					} else {
						if (tipos.hasOwnProperty(nome))
							attrs[atributo] = this.formata(valor, tipos[nome]);							
					}
				}
			}
		}
		
		return attrs;
	},
	
	formata: function (valor, formatacao) {
		var valorFmt = '';
		switch (formatacao.toUpperCase()) {
			case 'DATA': 
				valorFmt = preparaDataBD(valor);
				break;
			case 'DATA_BARRAMENTO': 
				valorFmt = preparaDataSIBAR(valor);
				break;
			case 'VALOR':
			case 'TAXA':
				valorFmt = purificaMoeda(valor);
				break;
			case 'NUMERO':	
				valorFmt = String(valor);			
				break;
			case 'CHAR':
				valorFmt = String(valor).toUpperCase().replace(/[\'\"#$\&\*\/\\\~\^\`\´]/g, '');					
				break;
			case 'CPF':	
				valorFmt = String(valor).replace(/[.-]/g, '');					
				break;
			case 'CNPJ':	
				valorFmt = String(valor).replace(/[.\-\/\_]/g, '');					
				break;
			default:
				valorFmt = valor;
			
		}
		
		return valorFmt;
	}
});