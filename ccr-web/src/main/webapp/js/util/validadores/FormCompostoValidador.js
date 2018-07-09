/**
 * @autor f620600
 * Verifica se pelo menos um dos campos obrigatorios foi informado
 */
var FormCompostoValidator = Class.create(Validator, {
	
	arrayCampos: null,
	
	/**
	 * Construtor.
	 * 
	 */
	initialize: function(arrayCampos) {
		this.id = "formComposto";
		this.message = "Informe no mínimo um campo";
		this.arrayCampos = arrayCampos;
	},
	
	validate: function() {
		var valido = false;
		var _this = this;
		
		$.each(_this.arrayCampos, function (){
			if (!this.hasOwnProperty('campo'))
				return true; // continue pro prox campo
				
			var value = this.campo.val();
			
			if (typeof value == 'string') {
				if (value.trim() != '') {					
					valido = true;	
					return false;
				}
			} else {
				if (value != null) {
					valido = true;
					return false;
				}
			}
		});
		
		return valido;
	}
});
