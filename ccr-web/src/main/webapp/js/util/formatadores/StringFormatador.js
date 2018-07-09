window.StringFormatador = {
		limpar : function(valor, validos) {
			// retira caracteres invalidos da string
			var result = "";
			var aux;
			for ( var i = 0; i < valor.length; i++) {
				aux = validos.indexOf(valor.substring(i, i + 1));
				if (aux >= 0) {
					result += aux;
				}
			}

			return result;
		},
};