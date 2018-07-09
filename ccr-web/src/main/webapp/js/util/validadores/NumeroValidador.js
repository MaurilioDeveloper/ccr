/**
 * @autor f620600
 * Verifica se pelo menos um dos campos obrigatorios foi informado
 */
var MaiorQueZeroValidator = Class.create(Validator, {
	
	arrayCampos: null,
	
	/**
	 * Construtor.
	 * 
	 */
	initialize: function() {
		this.id = "maiorQueZero";
		this.message = "Valor do campo deve ser maior que zero";
	},
	
	validate: function(value) {
		value = parseFloat(this.limpar(value, '0123456789'));		
		return value > 0;
	}
});
