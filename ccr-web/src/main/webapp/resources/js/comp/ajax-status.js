/************************************************************************
 * Classe AjaxStatus													*
 ************************************************************************
 * Define algoritmos e estruturas para iteracao com o broker.			*
 ************************************************************************
 * @author eduardo														*
 ************************************************************************
 * Dependencias: prototype.js 1.7.1										*
 * Utilizacao:															*
 * var status = new AjaxStatus;											*
 * status.start();														*
 * status.stop();														*
 ************************************************************************/

var AjaxStatus = Class.create({

	/**
	 * Construtor
	 * 
	 * @param id Id do componente visual. Se nao for informado o id default sera ajaxStatus
	 */
	initialize: function(id) {
		if (id) { 
			this.id = id;
		} else {
			this.id = 'ajaxStatus';
		}
	},
	
	/**
	 * Exibe o ajax status.
	 */
	start: function() {
		$('#'+this.id).modal('show');
	},
	
	/**
	 * Fecha o ajax status.
	 */
	stop: function() {
		$('#'+this.id).modal('hide');
	}
});

